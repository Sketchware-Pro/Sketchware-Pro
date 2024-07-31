package mod.hey.studios.moreblock.importer;

import static mod.SketchwareUtil.getDip;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.core.content.res.ResourcesCompat;

import com.besome.sketch.beans.MoreBlockCollectionBean;
import com.sketchware.remod.R;
import com.sketchware.remod.databinding.ManageCollectionPopupImportMoreBlockListItemBinding;

import java.util.ArrayList;

import a.a.a.aB;
import mod.SketchwareUtil;
import mod.hey.studios.util.Helper;
import mod.jbk.util.BlockUtil;

public class MoreblockImporterDialog extends aB {

    private final Activity act;
    private ArrayList<MoreBlockCollectionBean> list;

    public MoreblockImporterDialog(Activity act, ArrayList<MoreBlockCollectionBean> beanList, CallBack callback) {
        super(act);
        this.act = act;
        list = new ArrayList<>(beanList);

        Adapter la = new Adapter();

        b("Select a more block");
        a(R.drawable.more_block_96dp);

        SearchView searchView = new SearchView(act);

        searchView.setQueryHint("Search...");
        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(false);
        searchView.setFocusableInTouchMode(true);

        {
            LinearLayout.LayoutParams searchViewParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            searchViewParams.setMargins(
                    0,
                    (int) getDip(5),
                    0,
                    (int) getDip(10)
            );
            searchView.setLayoutParams(searchViewParams);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (query.isEmpty()) {
                    //just return the bean list
                    list = new ArrayList<>(beanList);
                } else {
                    list = new ArrayList<>();

                    for (MoreBlockCollectionBean bean : beanList) {
                        if (bean.name.toLowerCase().contains(query.toLowerCase())
                                || bean.spec.toLowerCase().contains(query.toLowerCase())) {
                            list.add(bean);
                        }
                    }
                }

                la.resetPos();
                la.notifyDataSetChanged();

                return false;
            }
        });

        ListView lw = new ListView(act);
        {
            LinearLayout.LayoutParams listViewParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            listViewParams.setMargins(
                    0,
                    0,
                    0,
                    (int) getDip(18)
            );
            lw.setLayoutParams(listViewParams);
            lw.setDivider(ResourcesCompat.getDrawable(act.getResources(), android.R.color.transparent, act.getTheme()));
            lw.setDividerHeight((int) getDip(10));
        }

        LinearLayout ln = new LinearLayout(act);
        ln.setOrientation(LinearLayout.VERTICAL);
        ln.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        ln.addView(searchView);
        ln.addView(lw);

        lw.setAdapter(la);

        a(ln);
        b(act.getString(R.string.common_word_select), v -> {
            MoreBlockCollectionBean selectedBean = la.getSelectedItem();

            if (selectedBean == null) {
                SketchwareUtil.toastError("Select a more block");
            } else {
                callback.onSelected(selectedBean);
                dismiss();
            }
        });
        a(act.getString(R.string.common_word_cancel), Helper.getDialogDismissListener(this));
    }

    public interface CallBack {
        void onSelected(MoreBlockCollectionBean bean);
    }

    private class Adapter extends BaseAdapter {

        private int selectedPos = -1;

        public MoreBlockCollectionBean getSelectedItem() {
            return selectedPos != -1 ? getItem(selectedPos) : null;
        }

        public void resetPos() {
            selectedPos = -1;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public MoreBlockCollectionBean getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ManageCollectionPopupImportMoreBlockListItemBinding binding;

            if (convertView == null) {
                binding = ManageCollectionPopupImportMoreBlockListItemBinding.inflate(act.getLayoutInflater(), parent, false);
                convertView = binding.getRoot();
                convertView.setTag(binding);
            } else {
                binding = (ManageCollectionPopupImportMoreBlockListItemBinding) convertView.getTag();
            }

            binding.imgSelected.setVisibility(position == selectedPos ? View.VISIBLE : View.GONE);

            binding.tvBlockName.setText(getItem(position).name);
            binding.blockArea.removeAllViews();
            BlockUtil.loadMoreblockPreview(binding.blockArea, getItem(position).spec);

            View.OnClickListener listener = v -> {
                selectedPos = position;
                notifyDataSetChanged();
            };

            binding.getRoot().setOnClickListener(listener);
            binding.blockArea.setOnClickListener(listener);

            return convertView;
        }

    }
}
