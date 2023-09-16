package mod.hey.studios.moreblock.importer;

import static mod.SketchwareUtil.getDip;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.besome.sketch.beans.MoreBlockCollectionBean;
import com.sketchware.remod.R;

import java.util.ArrayList;

import a.a.a.aB;
import mod.SketchwareUtil;
import mod.hey.studios.util.Helper;
import mod.jbk.util.BlockUtil;

public class MoreblockImporterDialog extends aB {

    private final ArrayList<MoreBlockCollectionBean> internalList;
    private final CallBack callback;

    private final Activity act;
    private ArrayList<MoreBlockCollectionBean> list;
    private Adapter la;

    public MoreblockImporterDialog(Activity act, ArrayList<MoreBlockCollectionBean> beanList, CallBack callback) {
        super(act);
        this.act = act;
        internalList = beanList;
        list = new ArrayList<>(beanList);
        this.callback = callback;
    }

    public void show() {
        super.b("Select a More Block");
        super.a(R.drawable.more_block_96dp);

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
                    //just return the internal list
                    list = new ArrayList<>(internalList);
                } else {
                    list = new ArrayList<>();

                    for (MoreBlockCollectionBean bean : internalList) {
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

        la = new Adapter();
        lw.setAdapter(la);

        LinearLayout ln = new LinearLayout(act);
        ln.setOrientation(LinearLayout.VERTICAL);
        ln.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        ln.addView(searchView);
        ln.addView(lw);

        dialog.a(ln);
        dialog.b(act.getString(R.string.common_word_select), (d, which) -> {
            MoreBlockCollectionBean selectedBean = la.getSelectedItem();

            if (selectedBean == null) {
                SketchwareUtil.toastError("Select a More Block");
            } else {
                callback.onSelected(selectedBean);
                d.dismiss();
            }
        });
        dialog.a(act.getString(R.string.common_word_cancel), (d, which) -> Helper.getDialogDismissListener(d));
        dialog.show();
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
            if (convertView == null) {
                convertView = act.getLayoutInflater().inflate(R.layout.manage_collection_popup_import_more_block_list_item, parent, false);
            }

            FrameLayout blockArea = convertView.findViewById(R.id.block_area);
            TextView title = convertView.findViewById(R.id.tv_block_name);
            ImageView selected = convertView.findViewById(R.id.img_selected);

            if (position == selectedPos) {
                selected.setVisibility(View.VISIBLE);
            } else {
                selected.setVisibility(View.GONE);
            }

            title.setText(getItem(position).name);
            blockArea.removeAllViews();
            BlockUtil.loadMoreblockPreview(blockArea, getItem(position).spec);

            View.OnClickListener listener = v -> {
                selectedPos = position;
                notifyDataSetChanged();
            };

            convertView.findViewById(R.id.layout_item).setOnClickListener(listener);
            blockArea.setOnClickListener(listener);
            title.setOnClickListener(listener);

            return convertView;
        }
    }
}
