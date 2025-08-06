package mod.agus.jcoderz.editor.manage.permission;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;

import dev.chrisbanes.insetter.Insetter;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.ManagePermissionBinding;
import pro.sketchware.databinding.ViewItemPermissionBinding;
import pro.sketchware.utility.FilePathUtil;
import pro.sketchware.utility.FileResConfig;
import pro.sketchware.utility.FileUtil;

public class ManagePermissionActivity extends BaseAppCompatActivity {
    private PermissionsAdapter adapter;
    private ArrayList<String> arrayList;
    private FileResConfig frc;
    private String numProj;

    private LinearLayoutManager layoutManager;
    private ManagePermissionBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        enableEdgeToEdgeNoContrast();
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
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
        Insetter.builder()
                .margin(WindowInsetsCompat.Type.navigationBars())
                .applyToView(binding.scrollToTopButton);
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
        binding.toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_reset) {
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Reset permissions")
                        .setMessage("Are you sure you want to reset all permissions? This cannot be undone!")
                        .setPositiveButton("Reset", (dialog, which) -> {
                            FileUtil.writeFile(new FilePathUtil().getPathPermission(numProj), "[]");
                            //As FileResConfig only refreshes permissions during <init>()V, this is required.
                            frc = new FileResConfig(numProj);
                            setItems();
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .show();
            }
            return true;
        });
        binding.scrollToTopButton.setOnClickListener(view -> {
            binding.scrollToTopButton.hide();
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
                    if (!frc.getPermissionList().contains(Helper.getText(button))) {
                        frc.listFilePermission.add(Helper.getText(button));
                    }
                } else {
                    frc.listFilePermission.remove(Helper.getText(button));
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
