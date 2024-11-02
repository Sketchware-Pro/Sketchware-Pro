package pro.sketchware.fragments.settings.selector.block.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import pro.sketchware.databinding.FragmentBlockSelectorManagerBinding;
import pro.sketchware.databinding.DialogAddCustomActivityBinding;
import pro.sketchware.databinding.DialogSelectorActionsBinding;

import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.fragments.settings.selector.block.Selector;
import pro.sketchware.fragments.settings.selector.block.BlockSelectorConsts;

import java.util.List;

import a.a.a.aB;
import a.a.a.qA;

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
            (selector, indexA) -> showActionsDialog(indexA),
            (selector, indexA) -> SketchwareUtil.toast(selectors.get(index).getData().get(indexA))
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

        aB dialog = new aB(requireActivity());
        dialog.b("New Selector Item");
        dialog.b("Create", v -> {
          String newItem = dialogBinding.activityNameInput.getText().toString();
            if (newItem != null && !newItem.isEmpty()) {
                if (!isEdit) {
                    selectors.get(index).getData().add(newItem);
                } else {
                    selectors.get(index).getData().set(indexA, newItem);
                }
                saveAll();
                adapter.notifyDataSetChanged();
            }
            dialog.dismiss();
        });
        dialog.a("Cancel", v -> dialog.dismiss());
        dialog.a(dialogBinding.getRoot());
        dialog.show();
    }

    private void showActionsDialog(int indexA) {
        DialogSelectorActionsBinding dialogBinding = DialogSelectorActionsBinding.inflate(LayoutInflater.from(requireContext()));

        aB dialog = new aB(requireActivity());
        dialog.b("Actions");
        dialog.a(dialogBinding.getRoot());

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
                cancelDialog -> cancelDialog.dismiss()
            );
        });

        dialog.show();
    }

    private void showConfirmationDialog(String message, OnDialogClickListener onConfirm, OnDialogClickListener onCancel) {
        aB dialog = new aB(requireActivity());
        dialog.b("Attention");
        dialog.a(message);
        dialog.b("Yes", v -> onConfirm.onClick(dialog));
        dialog.a("Cancel", v -> onCancel.onClick(dialog));

        dialog.show();
    }

    private void saveAll() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileUtil.writeFile(BlockSelectorConsts.BLOCK_SELECTORS_FILE.getAbsolutePath(), gson.toJson(selectors));
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
        void onClick(aB dialog);
    }
}