package dev.aldi.sayuti.editor.injection;

import android.app.AlertDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.HashMap;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;

public class AddCustomAttributeActivity extends AppCompatActivity {

    private ArrayList<HashMap<String, Object>> activityInjections = new ArrayList<>();
    private ListView listView;
    private String activityInjectionsFilePath = "";
    private String widgetType = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_custom_attribute);

        FloatingActionButton fab = findViewById(R.id.add_attr_fab);
        listView = findViewById(R.id.add_attr_listview);
        fab.setOnClickListener(v -> dialog("create", 0));

        TextView title = findViewById(R.id.tx_toolbar_title);
        ImageView back = findViewById(R.id.ig_toolbar_back);
        Helper.applyRippleToToolbarView(back);
        back.setOnClickListener(Helper.getBackPressedClickListener(this));

        if (getIntent().hasExtra("sc_id") && getIntent().hasExtra("file_name") && getIntent().hasExtra("widget_type")) {
            String sc_id = getIntent().getStringExtra("sc_id");
            String activityFilename = getIntent().getStringExtra("file_name");
            widgetType = getIntent().getStringExtra("widget_type");

            title.setText(widgetType);

            activityInjectionsFilePath = FileUtil.getExternalStorageDir() + "/.sketchware/data/" + sc_id + "/injection/appcompat/" + activityFilename;
            if (!FileUtil.isExistFile(activityInjectionsFilePath) || FileUtil.readFile(activityInjectionsFilePath).equals("")) {
                activityInjections = new Gson().fromJson(AppCompatInjection.getDefaultActivityInjections(), Helper.TYPE_MAP_LIST);
            } else {
                activityInjections = new Gson().fromJson(FileUtil.readFile(activityInjectionsFilePath), Helper.TYPE_MAP_LIST);
            }
            listView.setAdapter(new CustomAdapter(activityInjections));
            ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
        } else {
            finish();
        }
    }

    private void makeup(View view, int i2, int i3) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadii(new float[]{(float) i2, (float) i2, (float) i2, (float) i2, (float) i2, (float) i2, (float) i2, (float) i2});
        gradientDrawable.setColor(Color.parseColor("#ffffff"));
        RippleDrawable rippleDrawable = new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{Color.parseColor("#20008DCD")}), gradientDrawable, null);
        view.setElevation((float) i3);
        view.setBackground(rippleDrawable);
        view.setClickable(true);
        view.setFocusable(true);
    }

    private void dialog(String type, int position) {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(R.layout.custom_dialog_attribute, null);
        dialog.setView(inflate);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextView save = inflate.findViewById(R.id.dialog_btn_save);
        TextView cancel = inflate.findViewById(R.id.dialog_btn_cancel);
        EditText namespace = inflate.findViewById(R.id.dialog_input_res);
        EditText name = inflate.findViewById(R.id.dialog_input_attr);
        EditText value = inflate.findViewById(R.id.dialog_input_value);

        if (type.equals("edit")) {
            String injectionValue = activityInjections.get(position).get("value").toString();
            namespace.setText(injectionValue.substring(0, injectionValue.indexOf(":")));
            name.setText(injectionValue.substring(injectionValue.indexOf(":") + 1, injectionValue.indexOf("=")));
            value.setText(injectionValue.substring(injectionValue.indexOf("\"") + 1, injectionValue.length() - 1));
        }

        save.setOnClickListener(v -> {
            String namespaceInput = namespace.getText().toString();
            String nameInput = name.getText().toString();
            String valueInput = value.getText().toString();
            if (!namespaceInput.trim().equals("") && !nameInput.trim().equals("") && !valueInput.trim().equals("")) {
                String newValue = namespaceInput + ":" + nameInput + "=\"" + valueInput + "\"";
                if (type.equals("create")) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("type", widgetType);
                    map.put("value", newValue);
                    activityInjections.add(map);
                    SketchwareUtil.toast("Added");
                } else if (type.equals("edit")) {
                    activityInjections.get(position).put("value", newValue);
                    SketchwareUtil.toast("Saved");
                }
                listView.setAdapter(new CustomAdapter(activityInjections));
                ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
                dialog.dismiss();
                FileUtil.writeFile(activityInjectionsFilePath, new Gson().toJson(activityInjections));
            }
        });
        cancel.setOnClickListener(Helper.getDialogDismissListener(dialog));
        dialog.show();

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        namespace.requestFocus();
    }

    private class CustomAdapter extends BaseAdapter {

        private final ArrayList<HashMap<String, Object>> injections;

        public CustomAdapter(ArrayList<HashMap<String, Object>> arrayList) {
            injections = arrayList;
        }

        @Override
        public int getCount() {
            return injections.size();
        }

        @Override
        public HashMap<String, Object> getItem(int position) {
            return injections.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.custom_view_attribute, parent, false);
            }

            LinearLayout root = convertView.findViewById(R.id.cus_attr_layout);
            TextView injection = convertView.findViewById(R.id.cus_attr_text);
            ImageView options = convertView.findViewById(R.id.cus_attr_btn);

            options.setRotation(90);
            makeup(root, 10, 5);
            makeup(options, 100, 0);

            if (!injections.get(position).containsKey("type") || !activityInjections.get(position).get("type").toString().equals(widgetType)) {
                root.setVisibility(View.GONE);
            } else {
                String value = activityInjections.get(position).get("value").toString();
                SpannableString spannableString = new SpannableString(value);
                spannableString.setSpan(new ForegroundColorSpan(0xff7a2e8c), 0, value.indexOf(":"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(0xff212121), value.indexOf(":"), value.indexOf("=") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(0xff45a245), value.indexOf("\""), value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                injection.setText(spannableString);
                root.setVisibility(View.VISIBLE);
            }
            options.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(AddCustomAttributeActivity.this, options);
                popupMenu.getMenu().add(Menu.NONE, 0, Menu.NONE, "Edit");
                popupMenu.getMenu().add(Menu.NONE, 1, Menu.NONE, "Delete");
                popupMenu.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == 0) {
                        dialog("edit", position);
                    } else {
                        activityInjections.remove(position);
                        FileUtil.writeFile(activityInjectionsFilePath, new Gson().toJson(activityInjections));
                        notifyDataSetChanged();
                        SketchwareUtil.toast("Deleted successfully");
                    }
                    return true;
                });
                popupMenu.show();
            });
            return convertView;
        }
    }
}