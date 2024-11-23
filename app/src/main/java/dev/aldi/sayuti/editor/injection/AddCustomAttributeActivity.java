package dev.aldi.sayuti.editor.injection;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import pro.sketchware.R;
import pro.sketchware.databinding.ActivityManageCustomAttributeBinding;
import pro.sketchware.databinding.CustomDialogAttributeBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;

import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.FileUtil;
import mod.hey.studios.util.Helper;
import mod.remaker.util.ThemeUtils;
import mod.remaker.view.CustomAttributeView;

public class AddCustomAttributeActivity extends AppCompatActivity {

    private ArrayList<HashMap<String, Object>> activityInjections = new ArrayList<>();

    private String activityInjectionsFilePath = "";
    private String widgetType = "";
    
    private static final String ATTR_REGEX_TEMPLATE = "(android|app)\\s*:\\s*%s";

    private ActivityManageCustomAttributeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageCustomAttributeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addAttrFab.setOnClickListener(v -> dialog("create", 0));

        binding.toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));

        if (getIntent().hasExtra("sc_id") && getIntent().hasExtra("file_name") && getIntent().hasExtra("widget_type")) {
            String sc_id = getIntent().getStringExtra("sc_id");
            String activityFilename = getIntent().getStringExtra("file_name");
            widgetType = getIntent().getStringExtra("widget_type");

            binding.toolbar.setTitle(widgetType);

            activityInjectionsFilePath = FileUtil.getExternalStorageDir() + "/.sketchware/data/" + sc_id + "/injection/appcompat/" + activityFilename;
            if (!FileUtil.isExistFile(activityInjectionsFilePath) || FileUtil.readFile(activityInjectionsFilePath).isEmpty()) {
                activityInjections = new Gson().fromJson(AppCompatInjection.getDefaultActivityInjections(), Helper.TYPE_MAP_LIST);
            } else {
                activityInjections = new Gson().fromJson(FileUtil.readFile(activityInjectionsFilePath), Helper.TYPE_MAP_LIST);
            }
            binding.addAttrListview.setAdapter(new CustomAdapter(activityInjections));
            ((BaseAdapter) binding.addAttrListview.getAdapter()).notifyDataSetChanged();
        } else {
            finish();
        }
    }

    private void dialog(String type, int position) {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setTitle(type.equals("create") ? "Add new attribute" : "Edit attribute");
        CustomDialogAttributeBinding attributeBinding = CustomDialogAttributeBinding.inflate(getLayoutInflater());
        dialog.setView(attributeBinding.getRoot());

        if (type.equals("edit")) {
            String injectionValue = activityInjections.get(position).get("value").toString();
            attributeBinding.inputRes.setText(injectionValue.substring(0, injectionValue.indexOf(":")));
            attributeBinding.inputAttr.setText(injectionValue.substring(injectionValue.indexOf(":") + 1, injectionValue.indexOf("=")));
            attributeBinding.inputValue.setText(injectionValue.substring(injectionValue.indexOf("\"") + 1, injectionValue.length() - 1));
        }

        dialog.setPositiveButton(R.string.common_word_save, (dialog1, which) -> {
            String namespaceInput = attributeBinding.inputRes.getText().toString();
            String nameInput = attributeBinding.inputAttr.getText().toString();
            String valueInput = attributeBinding.inputValue.getText().toString();
            if (!namespaceInput.trim().isEmpty() && !nameInput.trim().isEmpty() && !valueInput.trim().isEmpty()) {
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
                binding.addAttrListview.setAdapter(new CustomAdapter(activityInjections));
                ((BaseAdapter) binding.addAttrListview.getAdapter()).notifyDataSetChanged();
                dialog1.dismiss();
                FileUtil.writeFile(activityInjectionsFilePath, new Gson().toJson(activityInjections));
            }
        });
        dialog.setNegativeButton(R.string.common_word_cancel, (dialog1, which) -> dialog1.dismiss());
        dialog.show();
        Objects.requireNonNull(dialog.create().getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        attributeBinding.inputRes.requestFocus();
    }

    private class CustomAdapter extends BaseAdapter {

        private final ArrayList<HashMap<String, Object>> injections;
        private final ArrayList<HashMap<String, Object>> filtered;

        public CustomAdapter(ArrayList<HashMap<String, Object>> arrayList) {
            injections = arrayList;
            filtered = filterInjections(injections);
        }

        private ArrayList<HashMap<String, Object>> filterInjections(ArrayList<HashMap<String, Object>> arrayList) {
            ArrayList<HashMap<String, Object>> filteredList = new ArrayList<>();
            for (HashMap<String, Object> injection : arrayList) {
                if (injection.containsKey("type") && injection.get("type").toString().equals(widgetType)) {
                    filteredList.add(injection);
                }
            }
            return filteredList;
        }

        @Override
        public int getCount() {
            return filtered.size();
        }

        @Override
        public HashMap<String, Object> getItem(int position) {
            return filtered.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CustomAttributeView attributeView = new CustomAttributeView(parent.getContext());

            int violet = ThemeUtils.getColor(attributeView, R.attr.colorViolet);
            int onSurface = ThemeUtils.getColor(attributeView, R.attr.colorOnSurface);
            int green = ThemeUtils.getColor(attributeView, R.attr.colorGreen);

            String value = getItem(position).get("value").toString();
            SpannableString spannableString = new SpannableString(value);
            spannableString.setSpan(new ForegroundColorSpan(violet), 0, value.indexOf(":"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(onSurface), value.indexOf(":"), value.indexOf("=") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(green), value.indexOf("\""), value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            attributeView.getTextView().setText(spannableString);
            attributeView.getImageView().setRotation(90);
            attributeView.getImageView().setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(AddCustomAttributeActivity.this, attributeView.getImageView());
                popupMenu.getMenu().add(Menu.NONE, 0, Menu.NONE, "Edit");
                if (!hasAttribute("layout_height", value) || !hasAttribute("layout_width", value)) {
                    popupMenu.getMenu().add(Menu.NONE, 1, Menu.NONE, "Delete");
                }
                popupMenu.setOnMenuItemClickListener(item -> {
                    int originalPosition = injections.indexOf(filtered.get(position));

                    if (item.getItemId() == 0) {
                        dialog("edit", originalPosition);
                    } else {
                        if (originalPosition != -1) {
                            injections.remove(originalPosition);
                            filtered.remove(position);
                            FileUtil.writeFile(activityInjectionsFilePath, new Gson().toJson(injections));
                            notifyDataSetChanged();
                            SketchwareUtil.toast("Deleted successfully");
                        }
                    }
                    return true;
                });
                popupMenu.show();
            });

            return attributeView;
        }

        private boolean hasAttribute(String attrName, String attribute) {
            String regex = String.format(ATTR_REGEX_TEMPLATE, Pattern.quote(attrName));
            return Pattern.compile(regex).matcher(attribute).find();
        }
    }
}