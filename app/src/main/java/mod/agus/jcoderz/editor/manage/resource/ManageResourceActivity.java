package mod.agus.jcoderz.editor.manage.resource;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dev.pranav.filepicker.FilePickerCallback;
import dev.pranav.filepicker.FilePickerDialogFragment;
import dev.pranav.filepicker.FilePickerOptions;
import dev.pranav.filepicker.SelectionMode;
import mod.bobur.VectorDrawableLoader;
import mod.hey.studios.code.SrcCodeEditor;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.DialogCreateNewFileLayoutBinding;
import pro.sketchware.databinding.DialogInputLayoutBinding;
import pro.sketchware.databinding.ManageFileBinding;
import pro.sketchware.databinding.ManageJavaItemHsBinding;
import pro.sketchware.utility.FilePathUtil;
import pro.sketchware.utility.FileResConfig;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

@SuppressLint("SetTextI18n")
public class ManageResourceActivity extends BaseAppCompatActivity {

    private CustomAdapter adapter;
    private FilePickerDialogFragment dialog;
    private FilePathUtil fpu;
    private FileResConfig frc;
    private String numProj;
    private String temp;

    private ManageFileBinding binding;

    public static String getLastDirectory(String path) {
        // Get the index of the last occurrence of '/' character
        int lastSlashIndex = path.lastIndexOf('/');

        // Get the substring from the start to the lastSlashIndex
        String parentPath = path.substring(0, lastSlashIndex);

        // Get the index of the last occurrence of '/' in the parentPath
        lastSlashIndex = parentPath.lastIndexOf('/');

        // Extract the last directory name
        return parentPath.substring(lastSlashIndex + 1);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        enableEdgeToEdgeNoContrast();
        super.onCreate(savedInstanceState);
        binding = ManageFileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent().hasExtra("sc_id")) {
            numProj = getIntent().getStringExtra("sc_id");
        }
        Helper.fixFileprovider();
        frc = new FileResConfig(numProj);
        fpu = new FilePathUtil();
        setupDialog();
        checkDir();
        initToolbar();

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                try {
                    temp = temp.substring(0, temp.lastIndexOf("/"));
                    if (temp.contains("resource")) {
                        handleAdapter(temp);
                        handleFab();
                        return;
                    }
                } catch (IndexOutOfBoundsException ignored) {
                }
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void checkDir() {
        String resPath = fpu.getPathResource(numProj);
        File resDir = new File(resPath);
        if (!resDir.exists()) {
            new File(resPath, "anim").mkdirs();
            new File(resPath, "drawable").mkdirs();
            new File(resPath, "drawable-xhdpi").mkdirs();
            new File(resPath, "layout").mkdirs();
            new File(resPath, "menu").mkdirs();
            new File(resPath, "values").mkdirs();
        }
        temp = resPath;
        handleAdapter(temp);
        handleFab();
    }

    private void handleAdapter(String str) {
        ArrayList<String> resourceFile = frc.getResourceFile(str);
        //noinspection Java8ListSort
        Collections.sort(resourceFile, String.CASE_INSENSITIVE_ORDER);
        adapter = new CustomAdapter(resourceFile);
        binding.filesListRecyclerView.setAdapter(adapter);

        binding.noContentLayout.setVisibility(resourceFile.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private boolean isInMainDirectory() {
        return temp.equals(fpu.getPathResource(numProj));
    }

    private void handleFab() {
        var optionsButton = binding.showOptionsButton;
        if (isInMainDirectory()) {
            optionsButton.setText(R.string.assets_manager_add_new);
            hideShowOptionsButton(true);
        } else {
            optionsButton.setText(R.string.assets_manager_options);
        }
    }

    private void initToolbar() {
        binding.topAppBar.setTitle(R.string.text_title_menu_resource);
        binding.topAppBar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        binding.showOptionsButton.setOnClickListener(view -> {
            if (isInMainDirectory()) {
                createNewDialog(true);
                return;
            }
            hideShowOptionsButton(false);
        });
        binding.closeButton.setOnClickListener(view -> hideShowOptionsButton(true));
        binding.createNewButton.setOnClickListener(v -> {
            createNewDialog(isInMainDirectory());
            hideShowOptionsButton(true);
        });
        binding.importNewButton.setOnClickListener(v -> {
            dialog.show(getSupportFragmentManager(), "filePicker");
            hideShowOptionsButton(true);
        });

    }

    private void hideShowOptionsButton(boolean isHide) {
        binding.optionsLayout.animate()
                .translationY(isHide ? 300 : 0)
                .alpha(isHide ? 0 : 1)
                .setInterpolator(new OvershootInterpolator());

        binding.showOptionsButton.animate()
                .translationY(isHide ? 0 : 300)
                .alpha(isHide ? 1 : 0)
                .setInterpolator(new OvershootInterpolator());
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.filesListRecyclerView.getAdapter().notifyDataSetChanged();
    }

    private void createNewDialog(boolean isFolder) {
        DialogCreateNewFileLayoutBinding dialogBinding = DialogCreateNewFileLayoutBinding.inflate(getLayoutInflater());
        var inputText = dialogBinding.inputText;

        var dialog = new MaterialAlertDialogBuilder(this)
                .setView(dialogBinding.getRoot())
                .setTitle(isFolder ? R.string.manage_nativelibs_create_new_folder : R.string.manage_resource_create_new_file)
                .setMessage(isFolder ? R.string.manage_resource_enter_new_folder_name : R.string.manage_resource_enter_new_file_name)
                .setNegativeButton(R.string.common_word_cancel, (dialogInterface, i) -> dialogInterface.dismiss())
                .setPositiveButton(R.string.common_word_create, null)
                .create();

        dialogBinding.chipGroupTypes.setVisibility(View.GONE);
        if (!isFolder) {
            dialogBinding.inputText.setText(".xml");
        }

        dialog.setOnShowListener(dialogInterface -> {
            Button positiveButton = ((androidx.appcompat.app.AlertDialog) dialogInterface).getButton(DialogInterface.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(view -> {
                if (Helper.getText(inputText).isEmpty()) {
                    SketchwareUtil.toastError(Helper.getResString(R.string.manage_assets_invalid_name));
                    return;
                }

                String name = Helper.getText(inputText);
                String path;
                if (isFolder) {
                    path = fpu.getPathResource(numProj) + "/" + name;
                } else {
                    path = new File(temp + File.separator + name).getAbsolutePath();
                }

                if (FileUtil.isExistFile(path)) {
                    SketchwareUtil.toastError(Helper.getResString(R.string.manage_resource_file_exists_already));
                    return;
                }
                if (isFolder) {
                    FileUtil.makeDir(path);
                } else {
                    FileUtil.writeFile(path, "<?xml version=\"1.0\" encoding=\"utf-8\"?>");
                }
                handleAdapter(temp);
                SketchwareUtil.toast(Helper.getResString(R.string.manage_resource_created_file_successfully));
                dialog.dismiss();
            });

            dialog.setView(dialogBinding.getRoot());
            dialog.show();

            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            inputText.requestFocus();

            if (!isFolder) {
                inputText.setSelection(0);
            }
        });

        dialog.show();
    }

    private void setupDialog() {
        FilePickerOptions options = new FilePickerOptions();
        options.setSelectionMode(SelectionMode.BOTH);
        options.setMultipleSelection(true);
        options.setTitle(Helper.getResString(R.string.manage_resource_select_resource_files));

        FilePickerCallback callback = new FilePickerCallback() {
            @Override
            public void onFilesSelected(@NotNull List<? extends File> files) {
                if (files.isEmpty()) {
                    SketchwareUtil.toastError(Helper.getResString(R.string.manage_resource_no_files_selected));
                    return;
                }
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    for (File file : files) {
                        try {
                            FileUtil.copyDirectory(file, new File(temp + File.separator + file.getName()));
                        } catch (IOException e) {
                            runOnUiThread(() -> SketchwareUtil.toastError(Helper.getResString(R.string.manage_resource_import_resource_failed, e.getMessage())));
                        }
                    }
                    runOnUiThread(() -> {
                        handleAdapter(temp);
                        handleFab();
                    });
                    executor.shutdown();
                });
            }
        };

        dialog = new FilePickerDialogFragment(options, callback);
    }

    private void showRenameDialog(String path) {
        DialogInputLayoutBinding dialogBinding = DialogInputLayoutBinding.inflate(getLayoutInflater());

        var inputText = dialogBinding.inputText;

        var dialog = new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.assets_manager_rename)
                .setView(dialogBinding.getRoot())
                .setNegativeButton(R.string.common_word_cancel, (dialogInterface, i) -> dialogInterface.dismiss())
                .setPositiveButton(R.string.assets_manager_rename, (dialogInterface, i) -> {
                    if (!Helper.getText(inputText).isEmpty()) {
                        if (FileUtil.renameFile(path, path.substring(0, path.lastIndexOf("/")) + "/" + Helper.getText(inputText))) {
                            SketchwareUtil.toast(Helper.getResString(R.string.common_word_renamed_successfully));
                        } else {
                            SketchwareUtil.toastError(Helper.getResString(R.string.manage_nativelibs_renaming_failed));
                        }
                        handleAdapter(temp);
                        handleFab();
                    }
                    dialogInterface.dismiss();
                })
                .create();


        try {
            inputText.setText(path.substring(path.lastIndexOf("/") + 1));
        } catch (IndexOutOfBoundsException e) {
            inputText.setText(path);
        }

        dialog.show();

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        inputText.requestFocus();
    }

    private void showDeleteDialog(int position) {
        new MaterialAlertDialogBuilder(this)
                .setTitle(Helper.getResString(R.string.common_delete_item_title, Uri.fromFile(new File(adapter.getItem(position))).getLastPathSegment()))
                .setMessage((Helper.getResString(FileUtil.isDirectory(adapter.getItem(position)) ? R.string.common_delete_folder_question : R.string.common_delete_file_question) + " " + Helper.getResString(R.string.common_action_cannot_be_undone)))
                .setPositiveButton(R.string.common_word_delete, (dialog, which) -> {
                    FileUtil.deleteFile(frc.listFileResource.get(position));
                    handleAdapter(temp);
                    SketchwareUtil.toast(Helper.getResString(R.string.str_deleted));
                })
                .setNegativeButton(R.string.common_word_cancel, null)
                .create()
                .show();
    }

    private void goEdit(int position) {
        if (frc.listFileResource.get(position).endsWith("xml")) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), SrcCodeEditor.class);
            intent.putExtra("title", Uri.parse(frc.listFileResource.get(position)).getLastPathSegment());
            intent.putExtra("content", frc.listFileResource.get(position));
            intent.putExtra("xml", "");
            if (getIntent().hasExtra("sc_id")) {
                intent.putExtra("sc_id", getIntent().getStringExtra("sc_id"));
            }
            startActivity(intent);
        } else {
            SketchwareUtil.toast(Helper.getResString(R.string.manage_resource_only_xml_editable));
        }
    }

    private void goEdit2(int position) {
        if (frc.listFileResource.get(position).endsWith("xml")) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), SrcCodeEditor.class);
            intent.putExtra("title", Uri.parse(frc.listFileResource.get(position)).getLastPathSegment());
            intent.putExtra("content", frc.listFileResource.get(position));
            intent.putExtra("xml", "");
            startActivity(intent);
        } else {
            SketchwareUtil.toast(Helper.getResString(R.string.manage_resource_only_xml_editable));
        }
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private final ArrayList<String> data;

        public CustomAdapter(ArrayList<String> arrayList) {
            data = arrayList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ManageJavaItemHsBinding binding = ManageJavaItemHsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String path = data.get(position);
            var binding = holder.binding;

            binding.more.setVisibility(FileUtil.isDirectory(path) ? View.GONE : View.VISIBLE);
            binding.title.setText(Uri.parse(path).getLastPathSegment());

            if (FileUtil.isDirectory(path)) {
                binding.icon.setImageResource(R.drawable.ic_mtrl_folder);
            } else {
                try {
                    if (FileUtil.isImageFile(path)) {
                        Glide.with(ManageResourceActivity.this).load(new File(path)).into(binding.icon);
                    } else if (path.endsWith(".xml") && "drawable".equals(getLastDirectory(path))) {
                        if (!new VectorDrawableLoader().setImageVectorFromFile(binding.icon, path)) {
                            // Not a renderable vector (shape, layer-list, ...): show the
                            // file icon instead of keeping the recycled view's old icon.
                            binding.icon.setImageResource(R.drawable.ic_mtrl_file);
                        }
                    } else {
                        binding.icon.setImageResource(R.drawable.ic_mtrl_file);
                    }
                } catch (Exception ignored) {
                    binding.icon.setImageResource(R.drawable.ic_mtrl_file);
                }
            }

            binding.more.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(ManageResourceActivity.this, v);
                popupMenu.inflate(R.menu.popup_menu_double);
                popupMenu.getMenu().getItem(0).setVisible(false);
                popupMenu.setOnMenuItemClickListener(item -> {
                    String title = item.getTitle().toString();
                    if (title.equals(getString(R.string.msg_edit_with))) {
                        if (frc.listFileResource.get(position).endsWith("xml")) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.fromFile(new File(frc.listFileResource.get(position))), "text/plain");
                            startActivity(intent);
                        } else {
                            SketchwareUtil.toast(Helper.getResString(R.string.manage_resource_only_xml_editable));
                        }
                    } else if (title.equals(getString(R.string.common_word_edit))) {
                        goEdit2(position);
                    } else if (title.equals(getString(R.string.common_word_delete))) {
                        showDeleteDialog(position);
                    } else if (title.equals(getString(R.string.assets_manager_rename))) {
                        showRenameDialog(frc.listFileResource.get(position));
                    } else {
                        return false;
                    }
                    return true;
                });
                popupMenu.show();
            });

            binding.getRoot().setOnLongClickListener(v -> {
                if (FileUtil.isDirectory(frc.listFileResource.get(position))) {
                    PopupMenu popupMenu = new PopupMenu(ManageResourceActivity.this, binding.more);
                    popupMenu.getMenu().add(R.string.common_word_delete);
                    popupMenu.setOnMenuItemClickListener(item -> {
                        showDeleteDialog(position);
                        return true;
                    });
                    popupMenu.show();
                } else {
                    binding.more.performClick();
                }
                return true;
            });
            binding.getRoot().setOnClickListener(view -> {
                if (FileUtil.isDirectory(frc.listFileResource.get(position))) {
                    temp = frc.listFileResource.get(position);
                    handleAdapter(temp);
                    handleFab();
                    return;
                }
                goEdit(position);
            });
        }

        public String getItem(int position) {
            return data.get(position);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private final ManageJavaItemHsBinding binding;

            public ViewHolder(ManageJavaItemHsBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}
