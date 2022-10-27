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

    private FloatingActionButton fab;
    private String file = "";
    private ArrayList<HashMap<String, Object>> listMap = new ArrayList<>();
    private ListView listview;
    private HashMap<String, Object> map = new HashMap<>();
    private String num = "";
    private String path = "";
    private String type = "";
    private String value = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_custom_attribute);
        initialize(savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {
        fab = findViewById(R.id.add_attr_fab);
        listview = findViewById(R.id.add_attr_listview);
        fab.setOnClickListener(v -> dialog("create", 0));
    }

    private void initializeLogic() {
        if (getIntent().hasExtra("sc_id") && getIntent().hasExtra("file_name") && getIntent().hasExtra("widget_type")) {
            num = getIntent().getStringExtra("sc_id");
            file = getIntent().getStringExtra("file_name");
            type = getIntent().getStringExtra("widget_type");
        }
        path = FileUtil.getExternalStorageDir().concat("/.sketchware/data/".concat(num.concat("/injection/appcompat/".concat(file))));
        if (!FileUtil.isExistFile(path) || FileUtil.readFile(path).equals("")) {
            listMap = new Gson().fromJson(AppCompatInjection.getDefaultActivityInjections(), Helper.TYPE_MAP_LIST);
        } else {
            listMap = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
        }
        listview.setAdapter(new CustomAdapter(listMap));
        ((BaseAdapter) listview.getAdapter()).notifyDataSetChanged();
        initToolbar(type);
    }

    public void initToolbar(String title) {
        ((TextView) findViewById(R.id.tx_toolbar_title)).setText(title);
        ImageView imageView = findViewById(R.id.ig_toolbar_back);
        Helper.applyRippleToToolbarView(imageView);
        imageView.setOnClickListener(Helper.getBackPressedClickListener(this));
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

    private void dialog(final String type, final int position) {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(R.layout.custom_dialog_attribute, null);
        dialog.setView(inflate);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView save = inflate.findViewById(R.id.dialog_btn_save);
        TextView cancel = inflate.findViewById(R.id.dialog_btn_cancel);
        final EditText editText = inflate.findViewById(R.id.dialog_input_res);
        final EditText editText2 = inflate.findViewById(R.id.dialog_input_attr);
        final EditText editText3 = inflate.findViewById(R.id.dialog_input_value);
        if (type.equals("edit")) {
            value = listMap.get(position).get("value").toString();
            editText.setText(value.substring(0, value.indexOf(":")));
            editText2.setText(value.substring(value.indexOf(":") + 1, value.indexOf("=")));
            editText3.setText(value.substring(value.indexOf("\"") + 1, value.length() - 1));
        }
        save.setOnClickListener(v -> {
            if (!editText.getText().toString().trim().equals("") && !editText2.getText().toString().trim().equals("") && !editText3.getText().toString().trim().equals("")) {
                if (type.equals("create")) {
                    map = new HashMap<>();
                    map.put("type", AddCustomAttributeActivity.this.type);
                    map.put("value", editText.getText().toString().concat(":".concat(editText2.getText().toString().concat("=\"".concat(editText3.getText().toString().concat("\""))))));
                    listMap.add(map);
                    SketchwareUtil.toast("Added");
                } else if (type.equals("edit")) {
                    listMap.get(position).put("value", editText.getText().toString().concat(":".concat(editText2.getText().toString().concat("=\"".concat(editText3.getText().toString().concat("\""))))));
                    SketchwareUtil.toast("Saved");
                }
                listview.setAdapter(new CustomAdapter(listMap));
                ((BaseAdapter) listview.getAdapter()).notifyDataSetChanged();
                dialog.dismiss();
                FileUtil.writeFile(path, new Gson().toJson(listMap));
            }
        });
        cancel.setOnClickListener(v -> dialog.dismiss());
        dialog.setOnDismissListener(dialog1 -> SketchwareUtil.hideKeyboard());
        dialog.show();
        editText.requestFocus();
        SketchwareUtil.showKeyboard();
    }

    private class CustomAdapter extends BaseAdapter {

        private final ArrayList<HashMap<String, Object>> _data;

        public CustomAdapter(ArrayList<HashMap<String, Object>> arrayList) {
            _data = arrayList;
        }

        @Override
        public int getCount() {
            return _data.size();
        }

        @Override
        public HashMap<String, Object> getItem(int position) {
            return _data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.custom_view_attribute, null);
            }
            LinearLayout linearLayout = convertView.findViewById(R.id.cus_attr_layout);
            TextView textView = convertView.findViewById(R.id.cus_attr_text);
            final ImageView imageView = convertView.findViewById(R.id.cus_attr_btn);
            imageView.setRotation(90.0f);
            makeup(linearLayout, 10, 5);
            makeup(imageView, 100, 0);
            if (!_data.get(position).containsKey("type") || !listMap.get(position).get("type").toString().equals(type)) {
                linearLayout.setVisibility(View.GONE);
            } else {
                value = listMap.get(position).get("value").toString();
                SpannableString spannableString = new SpannableString(value);
                spannableString.setSpan(new ForegroundColorSpan(0xff7a2e8c), 0, value.indexOf(":"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(0xff212121), value.indexOf(":"), value.indexOf("=") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(0xff45a245), value.indexOf("\""), value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(spannableString);
                linearLayout.setVisibility(View.VISIBLE);
            }
            imageView.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), imageView);
                popupMenu.getMenu().add(Menu.NONE, 0, Menu.NONE, "Edit");
                popupMenu.getMenu().add(Menu.NONE, 1, Menu.NONE, "Delete");
                popupMenu.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == 0) {
                        dialog("edit", position);
                        return true;
                    }
                    listMap.remove(position);
                    FileUtil.writeFile(path, new Gson().toJson(listMap));
                    notifyDataSetChanged();
                    SketchwareUtil.toast("Deleted successfully");
                    return true;
                });
                popupMenu.show();
            });
            return convertView;
        }
    }
}