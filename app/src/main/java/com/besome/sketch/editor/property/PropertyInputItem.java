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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.besome.sketch.beans.ProjectFileBean;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import a.a.a.Kw;
import a.a.a.OB;
import a.a.a.SB;
import a.a.a.TB;
import a.a.a._B;
import a.a.a.aB;
import a.a.a.jC;
import a.a.a.lC;
import a.a.a.mB;
import a.a.a.uq;
import a.a.a.wB;
import a.a.a.yB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.PropertyPopupInputTextBinding;
import pro.sketchware.lib.highlighter.SyntaxScheme;
import pro.sketchware.utility.FileUtil;

@SuppressLint("ViewConstructor")
public class PropertyInputItem extends RelativeLayout implements View.OnClickListener {

    private final String stringsStart = "@string/";
    private final ArrayList<HashMap<String, Object>> stringsListMap = new ArrayList<>();
    private Context context;
    private String typeView = "";
    private String key = "";
    private String value = "";
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

    public PropertyInputItem(Context context, boolean z) {
        super(context);
        initialize(context, z);
    }

    private void setIcon(ImageView imageView) {
        switch (key) {
            case "property_id" -> icon = R.drawable.ic_mtrl_id;
            case "property_text" -> icon = R.drawable.ic_mtrl_type;
            case "property_hint" -> icon = R.drawable.ic_mtrl_bulb;
            case "property_weight", "property_weight_sum" -> icon = R.drawable.ic_mtrl_weight;
            case "property_rotate" -> icon = R.drawable.ic_mtrl_rotate_90;
            case "property_lines" -> icon = R.drawable.ic_mtrl_numbers;
            case "property_progress" -> icon = R.drawable.ic_mtrl_prog_min;
            case "property_max" -> icon = R.drawable.ic_mtrl_circle;
            case "property_alpha" -> icon = R.drawable.ic_mtrl_opacity_full;
            case "property_translation_x" -> icon = R.drawable.ic_mtrl_move_x;
            case "property_translation_y" -> icon = R.drawable.ic_mtrl_move_y;
            case "property_scale_y" -> icon = R.drawable.ic_mtrl_scale_y;
            case "property_scale_x" -> icon = R.drawable.ic_mtrl_scale_x;
            case "property_inject" -> icon = R.drawable.ic_mtrl_code;
            case "property_convert" -> icon = R.drawable.ic_mtrl_switch;
        }
        imageView.setImageResource(icon);
    }

    public void setTypeView(String typeView) {
        this.typeView = typeView;
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
                case "property_text", "property_hint" -> showTextInputDialog(9999, false);
                case "property_weight", "property_weight_sum", "property_rotate", "property_lines",
                     "property_max", "property_progress" -> showNumberInputDialog();
                case "property_alpha" -> showNumberDecimalInputDialog(0, 1);
                case "property_translation_x", "property_translation_y" ->
                        showNumberDecimalInputDialog(-9999, 9999);
                case "property_scale_x", "property_scale_y" -> showNumberDecimalInputDialog(0, 99);
                case "property_convert" -> showAutoCompleteDialog();
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

        PropertyPopupInputTextBinding binding = PropertyPopupInputTextBinding.inflate(LayoutInflater.from(getContext()));

        binding.tiInput.setHint(String.format(Helper.getResString(R.string.property_enter_value), "widget ID"));

        binding.edInput.setSingleLine();
        _B validator = new _B(context, binding.tiInput, uq.b, uq.a(), jC.a(sc_id).a(projectFileBean), value);
        validator.a(value);
        dialog.a(binding.getRoot());
        dialog.b(Helper.getResString(R.string.common_word_save), v -> {
            if (validator.b()) {
                setValue(binding.edInput.getText().toString());
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

        PropertyPopupInputTextBinding binding = PropertyPopupInputTextBinding.inflate(LayoutInflater.from(getContext()));
        binding.tiInput.setHint(String.format(Helper.getResString(R.string.property_enter_value), tvName.getText().toString()));

        binding.edInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        binding.edInput.setText(value);

        TB validator = new TB(context, binding.tiInput, 0,
                (key.equals("property_max") || key.equals("property_progress")) ? 0x7fffffff : 999);

        dialog.a(binding.getRoot());
        dialog.b(Helper.getResString(R.string.common_word_save), v -> {
            if (validator.b()) {
                setValue(binding.edInput.getText().toString());
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

        PropertyPopupInputTextBinding binding = PropertyPopupInputTextBinding.inflate(LayoutInflater.from(getContext()));

        binding.tiInput.setHint(String.format(Helper.getResString(R.string.property_enter_value), tvName.getText().toString()));

        SB lengthValidator;

        if (isInject) {
            lengthValidator = new SB(context, binding.tiInput, 0, maxValue);
            binding.tiAutoCompleteInput.setVisibility(View.GONE);
            SyntaxScheme.setXMLHighlighter(binding.edInput);
        } else {
            loadStringsListMap();
            setupTextWatcher(binding.tiAutoCompleteInput, binding.edTiAutoCompleteInput);

            lengthValidator = new SB(context, binding.tiAutoCompleteInput, 0, maxValue);
            binding.tiAutoCompleteInput.setVisibility(View.VISIBLE);
            binding.tiInput.setVisibility(View.GONE);

            dialog.a(binding.getRoot());
            dialog.setDismissOnDefaultButtonClick(false);
            dialog.configureDefaultButton(context.getString(R.string.strings_xml), v -> {
                binding.edTiAutoCompleteInput.setText(stringsStart);
                binding.edTiAutoCompleteInput.setSelection(stringsStart.length());
                binding.edTiAutoCompleteInput.requestFocus();
            });

            setupAutoCompleteTextView(binding.edTiAutoCompleteInput);
        }

        lengthValidator.a(value);
        dialog.a(binding.getRoot());
        dialog.b(Helper.getResString(R.string.common_word_save), v -> handleSave(lengthValidator, binding.edInput, binding.edTiAutoCompleteInput, binding.tiAutoCompleteInput, isInject, dialog));
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void setupAutoCompleteTextView(MaterialAutoCompleteTextView autoCompleteTextView) {
        keysList = new ArrayList<>();
        List<String> mergedList = new ArrayList<>();

        for (HashMap<String, Object> map : stringsListMap) {
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
            if (valueChangeListener != null) {
                String inputText = autoCompleteTextView.getText().toString();

                if (inputText.equals(stringsStart)) {

                    String errorMessage = MessageFormat.format(
                            "Please select a String\n" +
                                    "or remove \"{0}\" and add the value directly",
                            stringsStart
                    );
                    textAutoCompleteInput.setError(errorMessage);
                } else {
                    valueChangeListener.a(key, value);
                    dialog.dismiss();
                }
            }
        }
    }

    private void loadStringsListMap() {
        String filePath = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(sc_id.concat("/files/resource/values/strings.xml"));
        convertXmlToListMap(FileUtil.readFileIfExist(filePath), stringsListMap);

        if (!isXmlStringsContains(stringsListMap, "app_name") && filePath != null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("key", "app_name");
            map.put("text", yB.c(lC.b(sc_id), "my_app_name"));
            stringsListMap.add(0, map);
        }
    }

    public void setupTextWatcher(TextInputLayout textAutoCompleteInput, MaterialAutoCompleteTextView editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();
                if (!text.startsWith(stringsStart) || text.equals(stringsStart)) {
                    textAutoCompleteInput.setError(null);
                    return;
                }

                boolean isExactMatch = keysList.contains(text);
                textAutoCompleteInput.setError(isExactMatch ? null : "Not found in strings.xml");
            }
        });
    }

    private void showNumberDecimalInputDialog(int minValue, int maxValue) {
        aB dialog = new aB((Activity) getContext());
        dialog.b(tvName.getText().toString());
        dialog.a(icon);

        PropertyPopupInputTextBinding binding = PropertyPopupInputTextBinding.inflate(LayoutInflater.from(getContext()));
        binding.tiInput.setHint(String.format(Helper.getResString(R.string.property_enter_value), tvName.getText().toString()));

        binding.edInput.setInputType((minValue < 0)
                ? InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL
                : InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        binding.edInput.setText(value);

        OB validator = new OB(context, binding.tiInput, minValue, maxValue);

        dialog.a(binding.getRoot());
        dialog.b(Helper.getResString(R.string.common_word_save), v -> {
            if (validator.b()) {
                setValue(binding.edInput.getText().toString());
                if (valueChangeListener != null) valueChangeListener.a(key, value);
                dialog.dismiss();
            }
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void showAutoCompleteDialog() {
        aB dialog = new aB((Activity) getContext());
        dialog.b(tvName.getText().toString());
        dialog.a(icon);

        PropertyPopupInputTextBinding binding = PropertyPopupInputTextBinding.inflate(LayoutInflater.from(getContext()));
        MaterialAutoCompleteTextView input = binding.edTiAutoCompleteInput;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, getPrioritizedSuggestions(typeView));
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

    public List<String> getPrioritizedSuggestions(String typeView) {
        List<String> prioritizedSuggestions = new ArrayList<>();

        switch (typeView) {
            case "0":  // LinearLayout
                prioritizedSuggestions.addAll(Arrays.asList(
                        "LinearLayout", "FrameLayout", "RelativeLayout",
                        "androidx.constraintlayout.widget.ConstraintLayout",
                        "TableLayout", "GridLayout",
                        "androidx.coordinatorlayout.widget.CoordinatorLayout",
                        "Space"
                ));
                break;

            case "2":  // HorizontalScrollView
            case "12": // VerticalScrollView
                prioritizedSuggestions.addAll(Arrays.asList(
                        "HorizontalScrollView", "VerticalScrollView", "ScrollView",
                        "androidx.core.widget.NestedScrollView"
                ));
                break;

            case "9":  // ListView
            case "25": // GridView
            case "48": // RecyclerView
                prioritizedSuggestions.addAll(Arrays.asList(
                        "ListView", "GridView", "androidx.recyclerview.widget.RecyclerView",
                        "ExpandableListView", "androidx.viewpager2.widget.ViewPager2"
                ));
                break;

            case "3":  // Button
            case "41": // MaterialButton
                prioritizedSuggestions.addAll(Arrays.asList(
                        "Button", "com.google.android.material.button.MaterialButton",
                        "ImageButton", "ToggleButton", "CompoundButton",
                        "androidx.appcompat.widget.AppCompatButton"
                ));
                break;

            case "4":  // TextView
            case "5":  // EditText
            case "38": // TextInputLayout
                prioritizedSuggestions.addAll(Arrays.asList(
                        "TextView", "EditText",
                        "com.google.android.material.textfield.TextInputLayout",
                        "com.google.android.material.textfield.TextInputEditText",
                        "AutoCompleteTextView",
                        "androidx.appcompat.widget.AppCompatTextView",
                        "androidx.appcompat.widget.AppCompatEditText"
                ));
                break;

            case "21": // VideoView
            case "7":  // WebView
                prioritizedSuggestions.addAll(Arrays.asList(
                        "VideoView", "android.webkit.WebView",
                        "androidx.media.widget.VideoView",
                        "android.webkit.WebViewClient", "android.webkit.WebChromeClient"
                ));
                break;

            case "8":  // ProgressBar
            case "14": // SeekBar
                prioritizedSuggestions.addAll(Arrays.asList(
                        "ProgressBar", "SeekBar",
                        "HorizontalScrollBar", "VerticalSeekBar",
                        "IndeterminateProgressBar"
                ));
                break;

            case "11": // CheckBox
            case "19": // RadioButton
                prioritizedSuggestions.addAll(Arrays.asList(
                        "CheckBox", "RadioButton",
                        "androidx.appcompat.widget.AppCompatCheckBox",
                        "androidx.appcompat.widget.AppCompatRadioButton"
                ));
                break;

            case "30": // TabLayout
            case "31": // ViewPager
                prioritizedSuggestions.addAll(Arrays.asList(
                        "com.google.android.material.tabs.TabLayout",
                        "androidx.viewpager.widget.ViewPager",
                        "androidx.viewpager2.widget.ViewPager2",
                        "androidx.fragment.app.FragmentPagerAdapter",
                        "androidx.fragment.app.FragmentStatePagerAdapter",
                        "com.google.android.material.tabs.TabLayoutMediator"
                ));
                break;

            case "32": // BottomNavigationView
                prioritizedSuggestions.add("com.google.android.material.bottomnavigation.BottomNavigationView");
                break;

            case "36": // CardView
                prioritizedSuggestions.addAll(Arrays.asList(
                        "androidx.cardview.widget.CardView",
                        "com.google.android.material.card.MaterialCardView"
                ));
                break;

            case "39": // SwipeRefreshLayout
                prioritizedSuggestions.addAll(Arrays.asList(
                        "androidx.swiperefreshlayout.widget.SwipeRefreshLayout",
                        "com.google.android.material.progressindicator.CircularProgressIndicator",
                        "com.google.android.material.progressindicator.LinearProgressIndicator"
                ));
                break;
        }

        List<String> allSuggestions = new ArrayList<>(getFullSuggestions());

        allSuggestions.removeAll(prioritizedSuggestions);

        List<String> finalSuggestions = new ArrayList<>(prioritizedSuggestions);
        finalSuggestions.addAll(allSuggestions);

        return finalSuggestions;
    }

    public List<String> getFullSuggestions() {
        return Arrays.asList(getResources().getStringArray(R.array.property_convert_options));
    }

}
