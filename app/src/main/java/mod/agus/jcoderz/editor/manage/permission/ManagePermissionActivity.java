package mod.agus.jcoderz.editor.manage.permission;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.elevation.SurfaceColors;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.sketchware.remod.R;
import com.sketchware.remod.databinding.ManagePermissionBinding;
import com.sketchware.remod.databinding.ViewItemPermissionBinding;

import java.util.ArrayList;

import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileResConfig;
import mod.agus.jcoderz.lib.FileUtil;
import mod.jbk.util.AddMarginOnApplyWindowInsetsListener;

public class ManagePermissionActivity extends AppCompatActivity {
    private PermissionsAdapter adapter;
    private ArrayList<String> arrayList;
    private FileResConfig frc;
    private String numProj;

    private LinearLayoutManager layoutManager;
    private ManagePermissionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ManagePermissionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
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
    }

    private void initViews() {
        var collapsingToolbar = binding.collapsingToolbar;

        ViewCompat.setOnApplyWindowInsetsListener(binding.scrollToTopButton,
                new AddMarginOnApplyWindowInsetsListener(WindowInsetsCompat.Type.navigationBars(), WindowInsetsCompat.CONSUMED));

        collapsingToolbar.setStatusBarScrimColor(SurfaceColors.SURFACE_2.getColor(this));
        collapsingToolbar.setContentScrimColor(SurfaceColors.SURFACE_2.getColor(this));
    }

    private void setItems() {
        var recyclerView = binding.recyclerView;
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
        var scrollToTopButton = binding.scrollToTopButton;
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
        scrollToTopButton.setOnClickListener(view -> {
            scrollToTopButton.hide();
            binding.appBarLayout.setExpanded(true);
            binding.recyclerView.smoothScrollToPosition(0);
        });
    }

    private void initRecyclerView() {
        var recyclerView = binding.recyclerView;
        var scrollToTopButton = binding.scrollToTopButton;

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    if (!scrollToTopButton.isShown()) scrollToTopButton.show();
                } else if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                    scrollToTopButton.hide();
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
            var listBinding = ViewItemPermissionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            var layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            listBinding.getRoot().setLayoutParams(layoutParams);
            return new ViewHolder(listBinding);
        }


        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            var binding = holder.listBinding;

            String permission = namePerm.get(position);
            binding.checkboxContent.setText(permission);
            binding.checkboxContent.setOnCheckedChangeListener(null); // Important to avoid infinite loops
            binding.checkboxContent.setChecked(frc.getPermissionList().contains(permission));
            binding.checkboxContent.setOnCheckedChangeListener((button, checked) -> {
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
            private final ViewItemPermissionBinding listBinding;

            public ViewHolder(ViewItemPermissionBinding listBinding) {
                super(listBinding.getRoot());
                this.listBinding = listBinding;
            }
        }
    }
}
