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
import java.util.Iterator;

import a.a.a.aB;
import a.a.a.FB;
import a.a.a.kq;
import a.a.a.Rs;
import a.a.a.Ts;
import mod.SketchwareUtil;
import mod.hey.studios.moreblock.ReturnMoreblockManager;
import mod.hey.studios.util.Helper;

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

        super.a(ln);
        super.b(act.getString(R.string.common_word_select), v -> {
            MoreBlockCollectionBean selectedBean = la.getSelectedItem();

            if (selectedBean == null) {
                SketchwareUtil.toastError("Select a More Block");
            } else {
                callback.onSelected(selectedBean);

                dismiss();
            }
        });
        super.a(act.getString(R.string.common_word_cancel), Helper.getDialogDismissListener(this));
        super.show();
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

            String spec = getItem(position).spec;
            int blockId = 0;
            var block = new Rs(act, 0, ReturnMoreblockManager.getMbName(spec), ReturnMoreblockManager.getMoreblockType(spec), "definedFunc");
            blockArea.addView(block);
            Iterator<String> specs = FB.c(spec).iterator();

            while (specs.hasNext()) {
                Rs specBlock;
                String specPart = specs.next();

                if (specPart.charAt(0) != '%') {
                    continue;
                }

                char type = specPart.charAt(1);

                switch (type) {
                    case 'b':
                    case 'd':
                    case 's':
                        specBlock = new Rs(act, blockId + 1, specPart.substring(3), Character.toString(type), "getVar");
                        break;
                    case 'm':
                        String specLast = specPart.substring(specPart.lastIndexOf(".") + 1);
                        String specFirst = specPart.substring(specPart.indexOf(".") + 1, specPart.lastIndexOf("."));
                        specBlock = new Rs(act, blockId + 1, specLast, kq.a(specFirst), kq.b(specFirst), "getVar");
                        break;
                    default:
                        continue;
                }

                blockArea.addView(specBlock);
                block.a((Ts) block.V.get(blockId), specBlock);
                blockId++;
            }
            block.k();
            
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
