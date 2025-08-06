package pro.sketchware.fragments.settings.block.selector.details;

import static pro.sketchware.utility.GsonUtils.getGson;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import a.a.a.qA;
import mod.hey.studios.util.Helper;
import pro.sketchware.databinding.DialogAddCustomActivityBinding;
import pro.sketchware.databinding.DialogSelectorActionsBinding;
import pro.sketchware.databinding.FragmentBlockSelectorManagerBinding;
import pro.sketchware.fragments.settings.block.selector.BlockSelectorConsts;
import pro.sketchware.fragments.settings.block.selector.Selector;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class BlockSelectorDetailsFragment extends qA {

    private final int index;
    private final List<Selector> selectors;
    private FragmentBlockSelectorManagerBinding binding;
    private BlockSelectorDetailsAdapter adapter;

    public BlockSelectorDetailsFragment(int index, List<Selector> selectors) {
        this.index = index;
        this.selectors = selectors;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBlockSelectorManagerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configureToolbar(binding.toolbar);
        handleInsetts(binding.getRoot());

        adapter = new BlockSelectorDetailsAdapter(
                (selector, indexA) -> showActionsDialog(indexA)
        );

        adapter.submitList(selectors.get(index).getData());
        binding.list.setAdapter(adapter);

        binding.createNew.setOnClickListener(v -> showCreateEditDialog(false, 0));
    }

    private void showCreateEditDialog(boolean isEdit, int indexA) {
        DialogAddCustomActivityBinding dialogBinding = DialogAddCustomActivityBinding.inflate(LayoutInflater.from(requireContext()));
        dialogBinding.activityNameInputLayout.setHint("Name");
        if (isEdit) {
            dialogBinding.activityNameInput.setText(selectors.get(index).getData().get(indexA));
        }

        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(requireActivity());
        dialog.setTitle("New Selector Item");
        dialog.setPositiveButton("Create", (v, which) -> {
            String newItem = Helper.getText(dialogBinding.activityNameInput);
            if (newItem != null && !newItem.isEmpty()) {
                if (!isEdit) {
                    selectors.get(index).getData().add(newItem);
                } else {
                    selectors.get(index).getData().set(indexA, newItem);
                }
                saveAll();
                adapter.notifyDataSetChanged();
            }
            v.dismiss();
        });
        dialog.setNegativeButton("Cancel", null);
        dialog.setView(dialogBinding.getRoot());
        dialog.show();
    }

    private void showActionsDialog(int indexA) {
        DialogSelectorActionsBinding dialogBinding = DialogSelectorActionsBinding.inflate(LayoutInflater.from(requireContext()));

        AlertDialog dialog = new MaterialAlertDialogBuilder(requireActivity()).create();
        dialog.setTitle("Actions");
        dialog.setView(dialogBinding.getRoot());

        dialogBinding.edit.setOnClickListener(v -> {
            dialog.dismiss();
            showCreateEditDialog(true, indexA);
        });

        dialogBinding.export.setVisibility(View.GONE);

        dialogBinding.delete.setOnClickListener(v -> {
            dialog.dismiss();
            showConfirmationDialog(
                    "Are you sure you want to delete this Selector Item?",
                    confirmDialog -> {
                        selectors.get(index).getData().remove(indexA);
                        saveAll();
                        adapter.notifyDataSetChanged();
                        confirmDialog.dismiss();
                    },
                    DialogInterface::dismiss
            );
        });

        dialog.show();
    }

    private void showConfirmationDialog(String message, OnDialogClickListener onConfirm, OnDialogClickListener onCancel) {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(requireActivity());
        dialog.setTitle("Attention");
        dialog.setMessage(message);
        dialog.setPositiveButton("Yes", (v, which) -> onConfirm.onClick(v));
        dialog.setNegativeButton("Cancel", (v, which) -> onCancel.onClick(v));

        dialog.show();
    }

    private void saveAll() {
        FileUtil.writeFile(BlockSelectorConsts.BLOCK_SELECTORS_FILE.getAbsolutePath(), getGson().toJson(selectors));
        SketchwareUtil.toast("Saved!");
    }

    @Override
    public void configureToolbar(@NonNull MaterialToolbar toolbar) {
        super.configureToolbar(toolbar);
        if (!selectors.get(index).getName().isEmpty()) {
            toolbar.setTitle(selectors.get(index).getName());
        }
        toolbar.getMenu().clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @FunctionalInterface
    public interface OnDialogClickListener {
        void onClick(DialogInterface dialog);
    }
}