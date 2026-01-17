package mod.agus.jcoderz.editor.manage.permission;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

import dev.chrisbanes.insetter.Insetter;
import pro.sketchware.R;
import pro.sketchware.databinding.ManagePermissionBinding;
import pro.sketchware.databinding.ViewItemPermissionBinding;
import pro.sketchware.lib.base.BaseTextWatcher;
import pro.sketchware.utility.FilePathUtil;
import pro.sketchware.utility.FileResConfig;
import pro.sketchware.utility.FileUtil;

public class ManagePermissionActivity extends BaseAppCompatActivity {
    private PermissionsAdapter adapter;
    private ArrayList<String> arrayList;
    private ArrayList<String> filteredList;
    private FileResConfig frc;
    private String numProj;
    private ManagePermissionBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        enableEdgeToEdgeNoContrast();
        super.onCreate(savedInstanceState);
        binding = ManagePermissionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        numProj = getIntent().getStringExtra("sc_id");
        frc = new FileResConfig(numProj);

        String path = new FilePathUtil().getPathPermission(numProj);
        if (!FileUtil.isExistFile(path)) FileUtil.writeFile(path, "");

        initViews();
        setupRecyclerView();
        loadAndSortData();

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FileUtil.writeFile(new FilePathUtil().getPathPermission(numProj), new Gson().toJson(frc.getPermissionList()));
                finish();
            }
        });
    }

    private void initViews() {
        binding.toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        binding.toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_reset) showResetDialog();
            return true;
        });

        Insetter.builder().margin(WindowInsetsCompat.Type.navigationBars())
                .applyToView(binding.scrollToTopButton);

        binding.searchInput.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(s.toString());
            }
        });

        binding.scrollToTopButton.setOnClickListener(v -> {
            binding.scrollToTopButton.hide();
            binding.appBarLayout.setExpanded(true);
            binding.recyclerView.smoothScrollToPosition(0);
        });
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();
        filteredList = new ArrayList<>();
        adapter = new PermissionsAdapter(filteredList);
        binding.recyclerView.setAdapter(adapter);

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
                if (dy > 0 && !binding.scrollToTopButton.isShown()) {
                    binding.scrollToTopButton.show();
                } else if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                    binding.scrollToTopButton.hide();
                }
            }
        });
    }

    private void loadAndSortData() {
        arrayList = ListPermission.getPermissions();
        sortList(arrayList);
        filteredList.clear();
        filteredList.addAll(arrayList);
        adapter.notifyDataSetChanged();
    }

    private void sortList(ArrayList<String> list) {
        list.sort(Comparator.comparing((String s) -> !frc.getPermissionList().contains(s))
                .thenComparing(String.CASE_INSENSITIVE_ORDER));
    }

    private void filterList(String query) {
        String lowerCaseQuery = query.toLowerCase();
        ArrayList<String> filtered = new ArrayList<>();

        for (String item : arrayList) {
            if (item.toLowerCase().contains(lowerCaseQuery)) {
                filtered.add(item);
            }
        }
        sortList(filtered);

        filteredList.clear();
        filteredList.addAll(filtered);
        adapter.notifyDataSetChanged();
    }

    private void showResetDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Reset permissions")
                .setMessage("Are you sure you want to reset all permissions?")
                .setPositiveButton("Reset", (dialog, which) -> {
                    FileUtil.writeFile(new FilePathUtil().getPathPermission(numProj), "[]");
                    frc = new FileResConfig(numProj);
                    loadAndSortData();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private class PermissionsAdapter extends RecyclerView.Adapter<PermissionsAdapter.ViewHolder> {
        private final ArrayList<String> displayList;

        public PermissionsAdapter(ArrayList<String> list) {
            displayList = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(ViewItemPermissionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String permission = displayList.get(position);
            holder.binding.checkboxContent.setOnCheckedChangeListener(null);
            holder.binding.checkboxContent.setText(permission);
            holder.binding.checkboxContent.setChecked(frc.getPermissionList().contains(permission));

            holder.binding.checkboxContent.setOnCheckedChangeListener((button, isChecked) -> {
                if (isChecked) {
                    if (!frc.getPermissionList().contains(permission)) {
                        frc.listFilePermission.add(permission);
                    }
                } else {
                    frc.listFilePermission.remove(permission);
                }
                binding.getRoot().postDelayed(() -> {
                    sortList(arrayList);
                    filterList(Objects.requireNonNull(binding.searchInput.getText()).toString());
                }, 500);
            });
        }

        @Override
        public int getItemCount() {
            return displayList.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            ViewItemPermissionBinding binding;

            ViewHolder(ViewItemPermissionBinding b) {
                super(b.getRoot());
                binding = b;
            }
        }
    }
}