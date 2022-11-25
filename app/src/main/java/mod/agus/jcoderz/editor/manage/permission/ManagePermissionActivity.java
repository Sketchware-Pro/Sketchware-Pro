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
    private ListAdapter adapter;
    private ArrayList<String> arrayList;
    private FileResConfig frc;
    private ListView lv;
    private String numProj;
    private SearchView sv;

    private void setItems() {
        Parcelable lvSavedState = lv.onSaveInstanceState();
        arrayList = ListPermission.getPermissions();
        ListAdapter listAdapter = new ListAdapter(arrayList);
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
                ArrayList<String> filter = new ArrayList<>();
                for (String next : arrayList) {
                    if (next.toLowerCase().contains(lowerCase)) {
                        filter.add(next);
                    }
                }
                adapter.setFilter(filter);
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
        resetPermissions.setOnClickListener(v ->
                new AlertDialog.Builder(this)
                        .setTitle("Reset permissions")
                        .setMessage("Are you sure you want to reset all permissions? This cannot be undone!")
                        .setPositiveButton("Reset", (dialog, which) -> {
                            FileUtil.writeFile(new FilePathUtil().getPathPermission(numProj), "[]");
                            //As FileResConfig only refreshes permissions during <init>()V, this is required.
                            frc = new FileResConfig(numProj);
                            setItems();
                        })
                        .setNegativeButton("Cancel", null)
                        .show());
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

    private class ListAdapter extends BaseAdapter {
        private ArrayList<String> namePerm;

        public ListAdapter(ArrayList<String> arrayList) {
            namePerm = arrayList;
        }

        @Override
        public int getCount() {
            return namePerm.size();
        }

        @Override
        public String getItem(int position) {
            return namePerm.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.view_item_permission, parent, false);
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
            checkBox.setChecked(frc.getPermissionList().contains(namePerm.get(position)));
            return convertView;
        }

        public void setFilter(ArrayList<String> filter) {
            ArrayList<String> arrayList2 = new ArrayList<>();
            namePerm = arrayList2;
            arrayList2.addAll(filter);
            notifyDataSetChanged();
        }
    }
}
