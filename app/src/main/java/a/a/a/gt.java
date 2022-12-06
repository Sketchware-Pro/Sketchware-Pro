package a.a.a;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressLint("ViewConstructor")
public class gt extends LinearLayout {

    private HashMap<Integer, ArrayList<VariableItem>> allVariablesWithCategoryIndex;
    private ArrayList<VariableItem> variableItems;
    private ArrayList<VariableItem> viewsVariableList;
    private ArrayList<VariableItem> componentsVariableList;
    private VariableItemAdapter variableItemAdapter;
    private TextView tvPreview;
    private Dialog dialog;
    private VariableItem selectedVariableItem;
    private CategoryItemAdapter categoryItemAdapter;

    public gt(Activity var1) {
        super(var1);
        initialize(var1);
    }

    private void setPreview(VariableItem variableItem) {
        selectedVariableItem = variableItem;
        tvPreview.setText(getTypeName(variableItem.type, variableItem.name));
    }

    private String getTypeName(String type, String name) {
        byte var4;
        byte var5;
        label77:
        {
            int var3 = type.hashCode();
            var4 = 0;
            if (var3 != 98) {
                if (var3 != 100) {
                    if (var3 == 115 && type.equals("s")) {
                        var5 = 2;
                        break label77;
                    }
                } else if (type.equals("d")) {
                    var5 = 1;
                    break label77;
                }
            } else if (type.equals("b")) {
                var5 = 0;
                break label77;
            }

            var5 = -1;
        }

        if (var5 != 0) {
            if (var5 != 1) {
                if (var5 != 2) {
                    type = kq.b(name);
                } else {
                    type = xB.b().a(getContext(), R.string.logic_variable_type_string);
                }
            } else {
                type = xB.b().a(getContext(), R.string.logic_variable_type_number);
            }
        } else {
            type = xB.b().a(getContext(), R.string.logic_variable_type_boolean);
        }

        label67:
        {
            switch (name.hashCode()) {
                case -823676088:
                    if (name.equals("varInt")) {
                        var5 = var4;
                        break label67;
                    }
                    break;
                case -823672651:
                    if (name.equals("varMap")) {
                        var5 = 3;
                        break label67;
                    }
                    break;
                case -823666294:
                    if (name.equals("varStr")) {
                        var5 = 2;
                        break label67;
                    }
                    break;
                case 181944945:
                    if (name.equals("listInt")) {
                        var5 = 4;
                        break label67;
                    }
                    break;
                case 181948382:
                    if (name.equals("listMap")) {
                        var5 = 6;
                        break label67;
                    }
                    break;
                case 181954739:
                    if (name.equals("listStr")) {
                        var5 = 5;
                        break label67;
                    }
                    break;
                case 235637425:
                    if (name.equals("varBool")) {
                        var5 = 1;
                        break label67;
                    }
            }

            var5 = -1;
        }

        switch (var5) {
            case 0:
                type = xB.b().a(getContext(), R.string.logic_variable_type_number);
                break;
            case 1:
                type = xB.b().a(getContext(), R.string.logic_variable_type_boolean);
                break;
            case 2:
                type = xB.b().a(getContext(), R.string.logic_variable_type_string);
                break;
            case 3:
                type = xB.b().a(getContext(), R.string.logic_variable_type_map);
                break;
            case 4:
                type = xB.b().a(getContext(), R.string.logic_variable_type_list_number);
                break;
            case 5:
                type = xB.b().a(getContext(), R.string.logic_variable_type_list_string);
                break;
            case 6:
                type = xB.b().a(getContext(), R.string.logic_variable_type_list_map);
        }

        return type;
    }

    private void initializeComponentItems() {
        componentsVariableList.add(new VariableItem("m", "intent", R.drawable.widget_intent));
        componentsVariableList.add(new VariableItem("m", "file", R.drawable.widget_shared_preference));
        componentsVariableList.add(new VariableItem("m", "calendar", R.drawable.widget_calendar));
        componentsVariableList.add(new VariableItem("m", "vibrator", R.drawable.widget_vibrator));
        componentsVariableList.add(new VariableItem("m", "timer", R.drawable.widget_timer));
        componentsVariableList.add(new VariableItem("m", "dialog", R.drawable.widget_alertdialog));
        componentsVariableList.add(new VariableItem("m", "mediaplayer", R.drawable.widget_mediaplayer));
        componentsVariableList.add(new VariableItem("m", "soundpool", R.drawable.widget_soundpool));
        componentsVariableList.add(new VariableItem("m", "objectanimator", R.drawable.widget_objectanimator));
        componentsVariableList.add(new VariableItem("m", "firebase", R.drawable.widget_firebase));
        componentsVariableList.add(new VariableItem("m", "firebaseauth", R.drawable.widget_firebase));
        componentsVariableList.add(new VariableItem("m", "firebasestorage", R.drawable.widget_firebase));
        componentsVariableList.add(new VariableItem("m", "camera", R.drawable.widget_camera));
        componentsVariableList.add(new VariableItem("m", "filepicker", R.drawable.widget_file));
        componentsVariableList.add(new VariableItem("m", "requestnetwork", R.drawable.widget_network_request));
        componentsVariableList.add(new VariableItem("m", "texttospeech", R.drawable.widget_text_to_speech));
        componentsVariableList.add(new VariableItem("m", "speechtotext", R.drawable.widget_speech_to_text));
        componentsVariableList.add(new VariableItem("m", "locationmanager", R.drawable.widget_location));
        componentsVariableList.add(new VariableItem("m", "videoad", R.drawable.widget_media_controller));
        componentsVariableList.add(new VariableItem("m", "progressdialog", R.drawable.widget_progress_dialog));
        componentsVariableList.add(new VariableItem("m", "timepickerdialog", R.drawable.widget_timer));
        componentsVariableList.add(new VariableItem("m", "notification", R.drawable.widget_notification));
    }

    private void initialize(Activity activity) {
        wB.a(activity, this, R.layout.var_type_spinner);
        tvPreview = findViewById(R.id.tv_preview);
        LinearLayout spinnerDialogLayout = (LinearLayout) wB.a(activity, R.layout.var_type_spinner_dialog);
        RecyclerView varTypeCategory = spinnerDialogLayout.findViewById(R.id.var_type_category);
        RecyclerView varTypeList = spinnerDialogLayout.findViewById(R.id.var_type_list);
        ((TextView) spinnerDialogLayout.findViewById(R.id.tv_title)).setText(xB.b().a(activity, R.string.logic_editor_more_block_title_add_variable_type));
        allVariablesWithCategoryIndex = new HashMap<>();
        variableItems = new ArrayList<>();
        viewsVariableList = new ArrayList<>();
        componentsVariableList = new ArrayList<>();
        initializeVariableItems();
        initializeViewsItems();
        initializeComponentItems();
        allVariablesWithCategoryIndex.put(0, variableItems);
        allVariablesWithCategoryIndex.put(1, viewsVariableList);
        allVariablesWithCategoryIndex.put(2, componentsVariableList);
        varTypeCategory.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, 1, false);
        varTypeCategory.setLayoutManager(layoutManager);
        categoryItemAdapter = new CategoryItemAdapter();
        categoryItemAdapter.setData(allVariablesWithCategoryIndex);
        varTypeCategory.setAdapter(categoryItemAdapter);
        varTypeList.setHasFixedSize(true);
        varTypeList.setLayoutManager(layoutManager);
        variableItemAdapter = new VariableItemAdapter();
        varTypeList.setAdapter(variableItemAdapter);
        categoryItemAdapter.layoutPosition = 0;
        variableItemAdapter.setData(allVariablesWithCategoryIndex.get(0));
        setPreview(variableItems.get(0));
        dialog = new Dialog(activity);
        if (spinnerDialogLayout.getParent() != null) {
            ((ViewGroup) spinnerDialogLayout.getParent()).removeView(spinnerDialogLayout);
        }

        dialog.setContentView(spinnerDialogLayout);
        findViewById(R.id.container).setOnClickListener(view -> {
            if (!mB.a()) {
                dialog.show();
            }
        });
    }

    private void initializeVariableItems() {
        variableItems.add(new VariableItem("b", "", R.drawable.ic_true_false_color_48dp));
        variableItems.add(new VariableItem("d", "", R.drawable.numbers_48));
        variableItems.add(new VariableItem("s", "", R.drawable.abc_96_color));
        variableItems.add(new VariableItem("m", "varMap", R.drawable.ic_map_color_48dp));
        variableItems.add(new VariableItem("m", "listInt", R.drawable.ic_list_color_48dp));
        variableItems.add(new VariableItem("m", "listStr", R.drawable.ic_list_color_48dp));
        variableItems.add(new VariableItem("m", "listMap", R.drawable.ic_list_color_48dp));
    }

    private void initializeViewsItems() {
        viewsVariableList.add(new VariableItem("m", "view", R.drawable.layout_48));
        viewsVariableList.add(new VariableItem("m", "textview", R.drawable.widget_text_view));
        viewsVariableList.add(new VariableItem("m", "imageview", R.drawable.widget_image_view));
        viewsVariableList.add(new VariableItem("m", "checkbox", R.drawable.widget_check_box));
        viewsVariableList.add(new VariableItem("m", "switch", R.drawable.widget_switch));
        viewsVariableList.add(new VariableItem("m", "listview", R.drawable.widget_list_view));
        viewsVariableList.add(new VariableItem("m", "spinner", R.drawable.widget_spinner));
        viewsVariableList.add(new VariableItem("m", "webview", R.drawable.widget_web_view));
        viewsVariableList.add(new VariableItem("m", "seekbar", R.drawable.widget_seek_bar));
        viewsVariableList.add(new VariableItem("m", "progressbar", R.drawable.widget_progress_bar));
        viewsVariableList.add(new VariableItem("m", "calendarview", R.drawable.widget_calendar));
        viewsVariableList.add(new VariableItem("m", "radiobutton", R.drawable.widget_radio_button));
        viewsVariableList.add(new VariableItem("m", "ratingbar", R.drawable.star_filled));
        viewsVariableList.add(new VariableItem("m", "videoview", R.drawable.widget_mediaplayer));
        viewsVariableList.add(new VariableItem("m", "searchview", R.drawable.ic_search_color_96dp));
        viewsVariableList.add(new VariableItem("m", "gridview", R.drawable.grid_3_48));
        viewsVariableList.add(new VariableItem("m", "actv", R.drawable.widget_edit_text));
        viewsVariableList.add(new VariableItem("m", "mactv", R.drawable.widget_edit_text));
        viewsVariableList.add(new VariableItem("m", "viewpager", R.drawable.widget_relative_layout));
        viewsVariableList.add(new VariableItem("m", "badgeview", R.drawable.pro_account_100dp_primary));
    }

    public Pair<String, String> getSelectedItem() {
        return new Pair<>(selectedVariableItem.type, selectedVariableItem.name);
    }

    private class CategoryItemAdapter extends RecyclerView.a<CategoryItemAdapter.ViewHolder> {

        private HashMap<Integer, ArrayList<VariableItem>> integerArrayListHashMap;
        private int layoutPosition;

        public CategoryItemAdapter() {
            layoutPosition = -1;
        }

        @Override
        public int a() {
            return integerArrayListHashMap.size();
        }

        @Override
        public void b(ViewHolder viewHolder, int position) {
            if (position != 0) {
                if (position != 1) {
                    if (position == 2) {
                        viewHolder.icon.setImageResource(R.drawable.component_96);
                        viewHolder.name.setText(xB.b().a(getContext(), R.string.common_word_component));
                    }
                } else {
                    viewHolder.icon.setImageResource(R.drawable.widget_list_view);
                    viewHolder.name.setText(xB.b().a(getContext(), R.string.common_word_view));
                }
            } else {
                viewHolder.icon.setImageResource(R.drawable.variation_48);
                viewHolder.name.setText(xB.b().a(getContext(), R.string.common_word_variable));
            }

            if (layoutPosition == position) {
                viewHolder.container.setBackgroundColor(-1);
            } else {
                viewHolder.container.setBackgroundColor(getResources().getColor(R.color.lighter_grey));
            }

        }

        public void setData(HashMap<Integer, ArrayList<VariableItem>> integerArrayListHashMap) {
            this.integerArrayListHashMap = integerArrayListHashMap;
        }

        @Override
        public ViewHolder b(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.var_type_category, parent, false));
        }

        private class ViewHolder extends RecyclerView.v {

            public LinearLayout container;
            public ImageView icon;
            public TextView name;

            public ViewHolder(View itemVIew) {
                super(itemVIew);
                container = itemVIew.findViewById(R.id.container);
                icon = itemVIew.findViewById(R.id.icon);
                name = itemVIew.findViewById(R.id.name);
                itemVIew.setOnClickListener(view -> {
                    layoutPosition = j();
                    variableItemAdapter.setData(allVariablesWithCategoryIndex.get(layoutPosition));
                    variableItemAdapter.c();
                    CategoryItemAdapter.this.c();
                });
            }
        }
    }

    private class VariableItem {

        public String type;
        public String name;
        @DrawableRes
        public int icon;

        public VariableItem(String type, String name, @DrawableRes int icon) {
            this.type = type;
            this.name = name;
            this.icon = icon;
        }
    }

    private class VariableItemAdapter extends RecyclerView.a<VariableItemAdapter.ViewHolder> {

        private ArrayList<VariableItem> variableItems1;

        public VariableItemAdapter() {
        }

        @Override
        public int a() {
            return variableItems1.size();
        }

        @Override
        public void b(ViewHolder viewHolder, int position) {
            VariableItem variableItem = variableItems1.get(position);
            viewHolder.name.setText(getTypeName(variableItem.type, variableItem.name));
            viewHolder.icon.setImageResource(variableItem.icon);
        }

        public void setData(ArrayList<VariableItem> variableItems) {
            variableItems1 = variableItems;
        }

        @Override
        public ViewHolder b(ViewGroup parent, int viewType) {
            return new ViewHolder(wB.a(getContext(), R.layout.var_type_spinner_item));
        }

        private class ViewHolder extends RecyclerView.v {
            public TextView name;
            public ImageView icon;

            public ViewHolder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.name);
                icon = itemView.findViewById(R.id.icon);
                itemView.setOnClickListener(view -> {
                    setPreview(allVariablesWithCategoryIndex.get(categoryItemAdapter.layoutPosition).get(j()));
                    dialog.hide();
                });
            }
        }
    }
}
