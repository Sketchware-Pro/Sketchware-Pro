package mod.agus.jcoderz.editor.manage.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sketchware.remod.R;

import java.util.ArrayList;

import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileResConfig;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;

public class ManagePermissionActivity extends Activity {
    public ListAdapter adapter;
    public ArrayList<String> arrayList;
    public FileResConfig frc;
    public ListView lv;
    public String numProj;
    public SearchView sv;

    private void setItems() {
        Parcelable lvSavedState = lv.onSaveInstanceState();
        arrayList = ListPermission.getPermissions();
        ListAdapter listAdapter = new ListAdapter(arrayList, numProj);
        adapter = listAdapter;
        lv.setAdapter(listAdapter);
        lv.onRestoreInstanceState(lvSavedState);
    }

    private void checkFile() {
        String pathPermission = new FilePathUtil().getPathPermission(numProj);
        if (!FileUtil.isExistFile(pathPermission)) {
            FileUtil.writeFile(pathPermission, "");
        }
    }

    private void setUpSearchView() {
        sv.setActivated(true);
        sv.setQueryHint("Search for a permission");
        sv.onActionViewExpanded();
        sv.setIconifiedByDefault(true);
        sv.clearFocus();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                String lowerCase = newText.toLowerCase();
                ArrayList<String> arrayList = new ArrayList<>();
                for (String next : ManagePermissionActivity.this.arrayList) {
                    if (next.toLowerCase().contains(lowerCase)) {
                        arrayList.add(next);
                    }
                }
                adapter.setFilter(arrayList);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
        });
    }

    public void initToolbar() {
        ((TextView) findViewById(R.id.tx_toolbar_title)).setText("Permission Manager");
        ImageView back = findViewById(R.id.ig_toolbar_back);
        Helper.applyRipple(this, back);
        back.setOnClickListener(Helper.getBackPressedClickListener(this));
        ImageView resetPermissions = findViewById(R.id.ig_toolbar_load_file);
        resetPermissions.setVisibility(View.VISIBLE);
        resetPermissions.setImageResource(R.drawable.ic_restore_white_24dp);
        resetPermissions.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(ManagePermissionActivity.this);
            builder.setTitle("Reset permissions");
            builder.setMessage("Are you sure you want to reset all permissions? This cannot be undone!");
            builder.setPositiveButton("Reset", (dialog, which) -> {
                FileUtil.writeFile(new FilePathUtil().getPathPermission(numProj), "[]");
                //As FileResConfig only refreshes permissions during <init>()V, this is required.
                frc = new FileResConfig(numProj);
                setItems();
            });
            builder.setNegativeButton("Cancel", null);
            builder.create().show();
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_permission);
        if (getIntent().hasExtra("sc_id")) {
            numProj = getIntent().getStringExtra("sc_id");
            frc = new FileResConfig(numProj);
        }
        sv = findViewById(R.id.search_perm);
        lv = findViewById(R.id.main_content);
        arrayList = new ArrayList<>();
        checkFile();
        setItems();
        setUpSearchView();
        initToolbar();
    }

    @Override
    public void onBackPressed() {
        FileUtil.writeFile(new FilePathUtil().getPathPermission(numProj), new Gson().toJson(frc.getPermissionList()));
        super.onBackPressed();
    }

    public class ListAdapter extends BaseAdapter {
        public ArrayList<String> namePerm;
        public String numProj;

        public ListAdapter(ArrayList<String> arrayList, String str) {
            namePerm = arrayList;
            numProj = str;
        }

        @Override
        public int getCount() {
            return namePerm.size();
        }

        @Override
        public String getItem(int i) {
            return namePerm.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.view_item_permission, null);
            }

            CheckBox checkBox = convertView.findViewById(R.id.checkbox_content);
            checkBox.setText(namePerm.get(position));
            checkBox.setOnCheckedChangeListener((button, checked) -> {
                if (checked) {
                    if (!frc.getPermissionList().contains(button.getText().toString())) {
                        frc.listFilePermission.add(button.getText().toString());
                    }
                } else {
                    frc.listFilePermission.remove(button.getText().toString());
                }
            });
            handleChecked(checkBox, position);
            return convertView;
        }

        public void handleChecked(CheckBox checkBox, int i) {
            checkBox.setChecked(frc.getPermissionList().contains(namePerm.get(i)));
        }

        public void setFilter(ArrayList<String> filter) {
            ArrayList<String> arrayList2 = new ArrayList<>();
            namePerm = arrayList2;
            arrayList2.addAll(filter);
            notifyDataSetChanged();
        }
    }
}