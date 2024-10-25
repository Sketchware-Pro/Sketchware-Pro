package com.besome.sketch.editor.property;

import static mod.bobur.StringEditorActivity.convertXmlToListMap;
import static mod.bobur.StringEditorActivity.isXmlStringsContains;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.besome.sketch.beans.ProjectFileBean;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;
import com.sketchware.remod.databinding.PropertyPopupInputTextBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import a.a.a.Kw;
import a.a.a.OB;
import a.a.a.SB;
import a.a.a.TB;
import a.a.a._B;
import a.a.a.aB;
import a.a.a.lC;
import a.a.a.yB;
import a.a.a.jC;
import a.a.a.mB;
import a.a.a.uq;
import a.a.a.wB;
import pro.sketchware.utility.FilePathUtil;
import pro.sketchware.utility.FileUtil;
import mod.hey.studios.util.Helper;

@SuppressLint("ViewConstructor")
public class PropertyInputItem extends RelativeLayout implements View.OnClickListener {

    private Context context;
    private String key = "";
    private String value = "";
    private final String stringsStart = "@string/";
    private ImageView imgLeftIcon;
    private int icon;
    private TextView tvName;
    private TextView tvValue;
    private View propertyItem;
    private View propertyMenuItem;
    private String sc_id;
    private ProjectFileBean projectFileBean;
    private Kw valueChangeListener;
    private List<String> keysList = new ArrayList<>();
    private final ArrayList<HashMap<String, Object>> StringsListMap = new ArrayList<>();

    public PropertyInputItem(Context context, boolean z) {
        super(context);
        initialize(context, z);
    }

    private void setIcon(ImageView imageView) {
        switch (key) {
            case "property_id" -> icon = R.drawable.rename_96_blue;
            case "property_text" -> icon = R.drawable.abc_96;
            case "property_hint" -> icon = R.drawable.help_96_blue;
            case "property_weight", "property_weight_sum" -> icon = R.drawable.one_to_many_48;
            case "property_rotate" -> icon = R.drawable.ic_reset_color_32dp;
            case "property_lines", "property_max", "property_progress" ->
                    icon = R.drawable.numbers_48;
            case "property_alpha" -> icon = R.drawable.opacity_48;
            case "property_translation_x" -> icon = R.drawable.swipe_right_48;
            case "property_translation_y" -> icon = R.drawable.swipe_down_48;
            case "property_scale_x", "property_scale_y" -> icon = R.drawable.resize_48;
            case "property_inject" -> icon = R.drawable.ic_property_inject;
            case "property_convert" -> icon = R.drawable.ic_property_convert;
        }
        imageView.setImageResource(icon);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
        int identifier = getResources().getIdentifier(key, "string", getContext().getPackageName());
        if (identifier > 0) {
            tvName.setText(Helper.getResString(identifier));
            if (propertyMenuItem.getVisibility() == VISIBLE) {
                setIcon(findViewById(R.id.img_icon));
                ((TextView) findViewById(R.id.tv_title)).setText(Helper.getResString(identifier));
                return;
            }
            setIcon(imgLeftIcon);
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        tvValue.setText(value);
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            switch (key) {
                case "property_id" -> showViewIdDialog();
                case "property_text", "property_hint" -> showTextInputDialog(9999 , false);
                case "property_weight", "property_weight_sum", "property_rotate", "property_lines",
                     "property_max", "property_progress" -> showNumberInputDialog();
                case "property_alpha" -> showNumberDecimalInputDialog(0, 1);
                case "property_translation_x", "property_translation_y" ->
                        showNumberDecimalInputDialog(-9999, 9999);
                case "property_scale_x", "property_scale_y" -> showNumberDecimalInputDialog(0, 99);
                case "property_convert" ->
                        showAutoCompleteDialog(getResources().getStringArray(R.array.property_convert_options));
                case "property_inject" -> showTextInputDialog(1000, true);
            }
        }
    }

    public void setOnPropertyValueChangeListener(Kw onPropertyValueChangeListener) {
        valueChangeListener = onPropertyValueChangeListener;
    }

    public void setOrientationItem(int orientationItem) {
        if (orientationItem == 0) {
            propertyItem.setVisibility(GONE);
            propertyMenuItem.setVisibility(VISIBLE);
        } else {
            propertyItem.setVisibility(VISIBLE);
            propertyMenuItem.setVisibility(GONE);
        }
    }

    private void initialize(Context context, boolean z) {
        this.context = context;
        wB.a(context, this, R.layout.property_input_item);
        tvName = findViewById(R.id.tv_name);
        tvValue = findViewById(R.id.tv_value);
        imgLeftIcon = findViewById(R.id.img_left_icon);
        propertyItem = findViewById(R.id.property_item);
        propertyMenuItem = findViewById(R.id.property_menu_item);
        if (z) {
            setSoundEffectsEnabled(true);
            setOnClickListener(this);
        }
    }

    private void showViewIdDialog() {
        aB dialog = new aB((Activity) getContext());
        dialog.b(tvName.getText().toString());
        dialog.a(icon);
        View view = wB.a(getContext(), R.layout.property_popup_input_text);
        EditText input = view.findViewById(R.id.ed_input);
        input.setPrivateImeOptions("defaultInputmode=english;");
        input.setLines(1);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        input.setImeOptions(EditorInfo.IME_ACTION_DONE);
        _B validator = new _B(context, view.findViewById(R.id.ti_input), uq.b, uq.a(), jC.a(sc_id).a(projectFileBean), value);
        validator.a(value);
        dialog.a(view);
        dialog.b(Helper.getResString(R.string.common_word_save), v -> {
            if (validator.b()) {
                setValue(input.getText().toString());
                if (valueChangeListener != null) valueChangeListener.a(key, value);
                dialog.dismiss();
            }
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    public void a(String projectId, ProjectFileBean projectFileBean) {
        sc_id = projectId;
        this.projectFileBean = projectFileBean;
    }

    private void showNumberInputDialog() {
        aB dialog = new aB((Activity) getContext());
        dialog.b(tvName.getText().toString());
        dialog.a(icon);
        View view = wB.a(getContext(), R.layout.property_popup_input_text);
        EditText input = view.findViewById(R.id.ed_input);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        input.setText(value);
        TB validator = new TB(context, view.findViewById(R.id.ti_input), 0,
                (key.equals("property_max") || key.equals("property_progress")) ? 0x7fffffff : 999);
        dialog.a(view);
        dialog.b(Helper.getResString(R.string.common_word_save), v -> {
            if (validator.b()) {
                setValue(input.getText().toString());
                if (valueChangeListener != null) valueChangeListener.a(key, value);
                dialog.dismiss();
            }
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void showTextInputDialog(int maxValue, boolean isInject) {
        aB dialog = new aB((Activity) getContext());
        dialog.b(tvName.getText().toString());
        dialog.a(icon);

        View view = wB.a(getContext(), R.layout.property_popup_input_text);
        EditText input = view.findViewById(R.id.ed_input);
        TextInputLayout textInputLayout = view.findViewById(R.id.ti_input);
        TextInputLayout textAutoCompleteInput = view.findViewById(R.id.ti_auto_complete_input);
        MaterialAutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.ed_ti_auto_complete_input);
        SB lengthValidator;

        if (isInject) {
            lengthValidator = new SB(context, textInputLayout, 0, maxValue);
            textAutoCompleteInput.setVisibility(View.GONE);
        } else {
            loadStringsListMap();
            setupTextWatcher(textAutoCompleteInput, autoCompleteTextView);

            lengthValidator = new SB(context, textAutoCompleteInput, 0, maxValue);
            textAutoCompleteInput.setVisibility(View.VISIBLE);
            textInputLayout.setVisibility(View.GONE);

            dialog.a(view);
            dialog.configureDefaultButton(context.getString(R.string.strings_xml), v -> {
                autoCompleteTextView.setText(stringsStart);
                autoCompleteTextView.setSelection(stringsStart.length());
                autoCompleteTextView.requestFocus();
            });

            setupAutoCompleteTextView(autoCompleteTextView);
        }

        lengthValidator.a(value);
        dialog.a(view);
        dialog.b(Helper.getResString(R.string.common_word_save), v -> handleSave(lengthValidator, input, autoCompleteTextView, textAutoCompleteInput, isInject, dialog));
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void setupAutoCompleteTextView(MaterialAutoCompleteTextView autoCompleteTextView) {
        keysList = new ArrayList<>();
        List<String> mergedList = new ArrayList<>();

        for (HashMap<String, Object> map : StringsListMap) {
            String keyValue = map.get("key").toString();
            keysList.add(stringsStart + keyValue);
            mergedList.add(stringsStart + keyValue + " ( " + map.get("text") + " )");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, mergedList);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String value = autoCompleteTextView.getText().toString();
            autoCompleteTextView.setText(value.substring(0, value.indexOf(" (")));
            autoCompleteTextView.setSelection(autoCompleteTextView.getText().length());
        });
    }

    private void handleSave(SB lengthValidator, EditText input,
                            MaterialAutoCompleteTextView autoCompleteTextView, TextInputLayout textAutoCompleteInput,
                            boolean isInject, aB dialog) {
        if (lengthValidator.b() && textAutoCompleteInput.getError() == null) {
            if (isInject) {
                setValue(input.getText().toString());
            } else {
                setValue(autoCompleteTextView.getText().toString());
            }
            if (valueChangeListener != null) valueChangeListener.a(key, value);
            dialog.dismiss();
        }
    }

    private void loadStringsListMap() {
        FilePathUtil fpu = new FilePathUtil();
        String filePath = fpu.getPathResource(sc_id) + "/values/strings.xml";
        convertXmlToListMap(FileUtil.readFile(filePath), StringsListMap);

        if (!isXmlStringsContains(StringsListMap, "app_name")) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("key", "app_name");
            map.put("text", yB.c(lC.b(sc_id), "my_app_name"));
            StringsListMap.add(0, map);
        }
    }

    public void setupTextWatcher(TextInputLayout textAutoCompleteInput, MaterialAutoCompleteTextView editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();
                boolean foundMatch = false;

                if (!text.startsWith(stringsStart)) {
                    textAutoCompleteInput.setError(null);
                    return;
                }

                for (String str : keysList) {
                    if (str.startsWith(text)) {
                        foundMatch = true;
                        break;
                    }
                }

                textAutoCompleteInput.setError(foundMatch ? null : "Not found in strings.xml");
            }
        });
    }

    private void showNumberDecimalInputDialog(int minValue, int maxValue) {
        aB dialog = new aB((Activity) getContext());
        dialog.b(tvName.getText().toString());
        dialog.a(icon);
        View view = wB.a(getContext(), R.layout.property_popup_input_text);
        EditText input = view.findViewById(R.id.ed_input);
        input.setInputType(minValue < 0 ?
                InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL
                : InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setText(value);
        OB validator = new OB(context, view.findViewById(R.id.ti_input), minValue, maxValue);
        dialog.a(view);
        dialog.b(Helper.getResString(R.string.common_word_save), v -> {
            if (validator.b()) {
                setValue(input.getText().toString());
                if (valueChangeListener != null) valueChangeListener.a(key, value);
                dialog.dismiss();
            }
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void showAutoCompleteDialog(String[] options) {
        aB dialog = new aB((Activity) getContext());
        dialog.b(tvName.getText().toString());
        dialog.a(icon);

        PropertyPopupInputTextBinding binding = PropertyPopupInputTextBinding.inflate(LayoutInflater.from(getContext()));
        MaterialAutoCompleteTextView input = binding.edTiAutoCompleteInput;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, options);
        input.setText(value);
        input.setAdapter(adapter);
        binding.tiInput.setVisibility(View.GONE);
        binding.tiAutoCompleteInput.setVisibility(View.VISIBLE);
        SB lengthValidator = new SB(context, binding.tiInput, 0, 99);
        lengthValidator.a(value);
        dialog.a(binding.getRoot());
        dialog.b(Helper.getResString(R.string.common_word_save), v -> {
            if (lengthValidator.b()) {
                setValue(input.getText().toString());
                if (valueChangeListener != null) valueChangeListener.a(key, value);
                dialog.dismiss();
            }
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

}
