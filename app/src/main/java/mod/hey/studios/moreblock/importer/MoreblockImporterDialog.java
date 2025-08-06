package mod.hey.studios.moreblock.importer;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.MoreBlockCollectionBean;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

import mod.hey.studios.util.Helper;
import mod.jbk.util.BlockUtil;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageCollectionPopupImportMoreBlockListItemBinding;
import pro.sketchware.databinding.SearchWithRecyclerViewBinding;
import pro.sketchware.utility.SketchwareUtil;

public class MoreblockImporterDialog extends MaterialAlertDialogBuilder {

    private final ArrayList<MoreBlockCollectionBean> moreBlockCollectionList;
    private final MoreBlockAdapter adapter;

    public MoreblockImporterDialog(Activity activity, ArrayList<MoreBlockCollectionBean> beanList, CallBack callback) {
        super(activity);

        SearchWithRecyclerViewBinding binding = SearchWithRecyclerViewBinding.inflate(LayoutInflater.from(getContext()));

        moreBlockCollectionList = new ArrayList<>(beanList);
        adapter = new MoreBlockAdapter(moreBlockCollectionList);

        setTitle("Select a more block");
        setIcon(R.drawable.more_block_96dp);

        binding.searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString().toLowerCase();
                filterList(query, beanList);
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerView.setAdapter(adapter);

        setPositiveButton(Helper.getResString(R.string.common_word_select), (v, which) -> {
            MoreBlockCollectionBean selectedBean = adapter.getSelectedItem();

            if (selectedBean == null) {
                SketchwareUtil.toastError("Select a more block");
            } else {
                callback.onSelected(selectedBean);
                v.dismiss();
            }
        });

        setNegativeButton(Helper.getResString(R.string.common_word_cancel), null);
        setView(binding.getRoot());
    }

    private void filterList(String query, ArrayList<MoreBlockCollectionBean> beanList) {
        moreBlockCollectionList.clear();

        if (query.isEmpty()) {
            moreBlockCollectionList.addAll(beanList);
        } else {
            for (MoreBlockCollectionBean bean : beanList) {
                if (bean.name.toLowerCase().contains(query) || bean.spec.toLowerCase().contains(query)) {
                    moreBlockCollectionList.add(bean);
                }
            }
        }

        adapter.resetSelectedPosition();
        adapter.notifyDataSetChanged();
    }

    public interface CallBack {
        void onSelected(MoreBlockCollectionBean bean);
    }

    private static class MoreBlockAdapter extends RecyclerView.Adapter<MoreBlockAdapter.ViewHolder> {

        private final ArrayList<MoreBlockCollectionBean> collectionList;
        private int selectedPosition = -1;

        public MoreBlockAdapter(ArrayList<MoreBlockCollectionBean> collectionList) {
            this.collectionList = collectionList;
        }

        public MoreBlockCollectionBean getSelectedItem() {
            return selectedPosition != -1 ? collectionList.get(selectedPosition) : null;
        }

        public void resetSelectedPosition() {
            selectedPosition = -1;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ManageCollectionPopupImportMoreBlockListItemBinding binding = ManageCollectionPopupImportMoreBlockListItemBinding.inflate(
                    LayoutInflater.from(parent.getContext()), parent, false
            );
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            MoreBlockCollectionBean bean = collectionList.get(position);

            holder.binding.imgSelected.setVisibility(position == selectedPosition ? View.VISIBLE : View.GONE);
            holder.binding.tvBlockName.setText(bean.name);

            holder.binding.blockArea.removeAllViews();
            BlockUtil.loadMoreblockPreview(holder.binding.blockArea, bean.spec);

            holder.binding.transparentOverlay.setOnClickListener(v -> {
                selectedPosition = holder.getAbsoluteAdapterPosition();
                notifyDataSetChanged();
            });
        }

        @Override
        public int getItemCount() {
            return collectionList.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            final ManageCollectionPopupImportMoreBlockListItemBinding binding;

            public ViewHolder(@NonNull ManageCollectionPopupImportMoreBlockListItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}
