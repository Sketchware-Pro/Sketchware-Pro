package com.besome.sketch.editor.makeblock;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

import a.a.a.kq;
import a.a.a.mB;
import a.a.a.wB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.VarTypeItemBinding;
import pro.sketchware.databinding.VarTypeSelectorDialogBinding;

@SuppressLint("ViewConstructor")
public class VariableItemView extends LinearLayout {

    private ArrayList<VariableItem> variableItems;
    private ArrayList<VariableItem> viewsVariableList;
    private ArrayList<VariableItem> componentsVariableList;
    private VariableItem selectedVariableItem;

    private TextView tvPreview;
    private AlertDialog dialog;

    public VariableItemView(Activity activity) {
        super(activity);
        initialize(activity);
    }

    private void setPreview(VariableItem variableItem) {
        selectedVariableItem = variableItem;
        tvPreview.setText(getTypeName(variableItem.type, variableItem.name));
    }

    private String getTypeName(String type, String name) {
        switch (type) {
            case "b":
                type = xB.b().a(getContext(), R.string.logic_variable_type_boolean);
                break;

            case "d":
                type = xB.b().a(getContext(), R.string.logic_variable_type_number);
                break;

            case "s":
                type = xB.b().a(getContext(), R.string.logic_variable_type_string);
                break;

            default:
                type = kq.b(name);
        }

        switch (name) {
            case "varInt":
                type = xB.b().a(getContext(), R.string.logic_variable_type_number);
                break;

            case "varBool":
                type = xB.b().a(getContext(), R.string.logic_variable_type_boolean);
                break;

            case "varStr":
                type = xB.b().a(getContext(), R.string.logic_variable_type_string);
                break;

            case "varMap":
                type = xB.b().a(getContext(), R.string.logic_variable_type_map);
                break;

            case "listInt":
                type = xB.b().a(getContext(), R.string.logic_variable_type_list_number);
                break;

            case "listStr":
                type = xB.b().a(getContext(), R.string.logic_variable_type_list_string);
                break;

            case "listMap":
                type = xB.b().a(getContext(), R.string.logic_variable_type_list_map);
                break;

            default:
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
        variableItems = new ArrayList<>();
        viewsVariableList = new ArrayList<>();
        componentsVariableList = new ArrayList<>();
        initializeVariableItems();
        initializeViewsItems();
        initializeComponentItems();
        setPreview(variableItems.get(0));

        findViewById(R.id.container).setOnClickListener(view -> {
            if (!mB.a()) {
                showVarTypeSelectorDialog(activity);
            }
        });
    }

    // New Ui For Type Var Dialog
    private void showVarTypeSelectorDialog(Activity activity) {

        var builder = new MaterialAlertDialogBuilder(activity);
        VarTypeSelectorDialogBinding binding =
                VarTypeSelectorDialogBinding.inflate(LayoutInflater.from(activity));

        builder.setTitle(Helper.getResString(R.string.logic_editor_more_block_title_add_variable_type));

        VariableItemAdapter adapter = new VariableItemAdapter();
        binding.list.setLayoutManager(new LinearLayoutManager(activity));
        binding.list.setAdapter(adapter);

        adapter.setData(variableItems);
        adapter.notifyDataSetChanged();

        binding.navRail.setOnItemSelectedListener(
                item -> {
                    int itemId = item.getItemId();
                    if (itemId == R.id.variables) {
                        // variables list
                        adapter.setData(variableItems);
                    } else if (itemId == R.id.views) {
                        // views list
                        adapter.setData(viewsVariableList);
                    } else {
                        // components list
                        adapter.setData(componentsVariableList);
                    }
                    adapter.notifyDataSetChanged();
                    return true;
                });

        builder.setView(binding.getRoot());
        dialog = builder.create();
        dialog.show();
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

    private record VariableItem(String type, String name, @DrawableRes int icon) {
    }

    private class VariableItemAdapter extends RecyclerView.Adapter<VariableItemAdapter.ViewHolder> {
        private ArrayList<VariableItem> variables;

        @Override
        public int getItemCount() {
            return variables.size();
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            VariableItem variableItem = variables.get(position);
            viewHolder.binding.name.setText(getTypeName(variableItem.type, variableItem.name));
            viewHolder.binding.icon.setImageResource(variableItem.icon);
        }

        public void setData(ArrayList<VariableItem> variableItems) {
            variables = variableItems;
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            VarTypeItemBinding binding = VarTypeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(binding);
        }

        private class ViewHolder extends RecyclerView.ViewHolder {
            public final VarTypeItemBinding binding;

            public ViewHolder(VarTypeItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
                binding.cardView.setOnClickListener(view -> {
                    setPreview(variables.get(getAbsoluteAdapterPosition()));
                    dialog.dismiss();
                });
            }
        }
    }
}
