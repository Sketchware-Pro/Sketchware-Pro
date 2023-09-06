package mod.agus.jcoderz.editor.manage.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.elevation.SurfaceColors;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.sketchware.remod.R;

import java.util.ArrayList;

import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileResConfig;
import mod.agus.jcoderz.lib.FileUtil;

public class ManagePermissionActivity extends Activity {
    private PermissionsAdapter adapter;
    private ArrayList<String> arrayList;
    private FileResConfig frc;
    private String numProj;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ExtendedFloatingActionButton fab;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;

    private void setItems() {
        Parcelable rvSavedState = recyclerView.getLayoutManager().onSaveInstanceState();
        arrayList = ListPermission.getPermissions();
        PermissionsAdapter listAdapter = new PermissionsAdapter(arrayList);
        adapter = listAdapter;
        recyclerView.setAdapter(listAdapter);
        recyclerView.getLayoutManager().onRestoreInstanceState(rvSavedState);
    }

    private void checkFile() {
        String pathPermission = new FilePathUtil().getPathPermission(numProj);
        if (!FileUtil.isExistFile(pathPermission)) {
            FileUtil.writeFile(pathPermission, "");
        }
    }

    private void setUpSearchView() {
        TextInputEditText searchEditText = findViewById(R.id.searchInput);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void onTextChanged(CharSequence newText, int start, int before, int count) {
                String lowerCase = newText.toString().toLowerCase();
                ArrayList<String> filter = new ArrayList<>();
                for (String next : arrayList) {
                    if (next.toLowerCase().contains(lowerCase)) {
                        filter.add(next);
                    }
                }
                adapter.setFilter(filter);
            }
        });
    }

    public void initButtons() {
        MaterialButton resetPermissions = findViewById(R.id.resetPermissions);
        resetPermissions.setOnClickListener(view -> new AlertDialog.Builder(ManagePermissionActivity.this)
                .setTitle("Reset permissions")
                .setMessage("Are you sure you want to reset all permissions? This cannot be undone!")
                .setPositiveButton("Reset", (dialog, which) -> {
                    FileUtil.writeFile(new FilePathUtil().getPathPermission(numProj), "[]");
                    //As FileResConfig only refreshes permissions during <init>()V, this is required.
                    frc = new FileResConfig(numProj);
                    setItems();
                }));
        fab.setOnClickListener(view -> {
            fab.hide();
            appBarLayout.setExpanded(true);
            recyclerView.smoothScrollToPosition(0);
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_permission);
        collapsingToolbar = findViewById(R.id.collapsingToolbar);
        appBarLayout = findViewById(R.id.appBarLayout);
        fab = findViewById(R.id.fab);
        layoutManager = new LinearLayoutManager(this);


        if (getIntent().hasExtra("sc_id")) {
            numProj = getIntent().getStringExtra("sc_id");
            frc = new FileResConfig(numProj);
        }
        arrayList = new ArrayList<>();
        initRecyclerView();
        checkFile();
        setItems();
        setUpSearchView();
        initButtons();


        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        collapsingToolbar.setStatusBarScrimColor(SurfaceColors.SURFACE_2.getColor(this));
        collapsingToolbar.setContentScrimColor(SurfaceColors.SURFACE_2.getColor(this));
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.main_content);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    if (!fab.isShown()) fab.show();
                } else if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                    fab.hide();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        FileUtil.writeFile(new FilePathUtil().getPathPermission(numProj), new Gson().toJson(frc.getPermissionList()));
        super.onBackPressed();
    }

    private class PermissionsAdapter extends RecyclerView.Adapter<PermissionsAdapter.ViewHolder> {

        private ArrayList<String> namePerm;

        public PermissionsAdapter(ArrayList<String> arrayList) {
            namePerm = arrayList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.view_item_permission, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String permission = namePerm.get(position);
            holder.checkBox.setText(permission);
            holder.checkBox.setSelected(true);
            holder.checkBox.setOnCheckedChangeListener(null); // Important to avoid infinite loops
            holder.checkBox.setChecked(frc.getPermissionList().contains(permission));
            holder.checkBox.setOnCheckedChangeListener((button, checked) -> {
                if (checked) {
                    if (!frc.getPermissionList().contains(button.getText().toString())) {
                        frc.listFilePermission.add(button.getText().toString());
                    }
                } else {
                    frc.listFilePermission.remove(button.getText().toString());
                }
            });
        }

        @Override
        public int getItemCount() {
            return namePerm.size();
        }

        public void setFilter(ArrayList<String> filter) {
            namePerm = new ArrayList<>(filter);
            notifyDataSetChanged();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            CheckBox checkBox;

            public ViewHolder(View itemView) {
                super(itemView);
                checkBox = itemView.findViewById(R.id.checkbox_content);
            }
        }
    }
}
