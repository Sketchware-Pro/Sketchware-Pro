package mod.hilal.saif.activities.tools;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.sketchware.remod.Resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import a.a.a.Zx;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.lib.PCP;
import mod.w3wide.highlighter.SimpleHighlighter;

public class BlocksManagerCreatorActivity extends AppCompatActivity {

    private final ArrayList<String> id_detector = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> blocksList = new ArrayList<>();
    private EditText code;
    private EditText colour;
    private HorizontalScrollView hscroll1;
    /**
     * Current mode of this activity, "edit" if editing a block, "add" if creating a new block and "insert" if inserting a block above another
     */
    private String mod = "";
    /**
     * Position of current editing/adding/inserting block in palette
     */
    private int n = 0;
    private EditText name;
    private TextView page_title;
    private String pallet_colour = "";
    private LinearLayout parameters_hold;
    private String path = "";
    private EditText spec;
    private EditText spec2;
    private TextInputLayout spec2_lay;
    private EditText type;
    private EditText typename;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Resources.layout.blocks_manager_creator);
        initialize();
        initializeLogic();
    }

    private void initialize() {
        ScrollView vscroll1 = findViewById(Resources.id.vscroll1);
        ImageView back_icon = findViewById(Resources.id.back_ico);
        page_title = findViewById(Resources.id.page_title);
        TextInputLayout name_lay = findViewById(Resources.id.name_lay);
        name = findViewById(Resources.id.name);
        LinearLayout select_type = findViewById(Resources.id.select_type);
        type = findViewById(Resources.id.type);
        typename = findViewById(Resources.id.type_name);
        spec = findViewById(Resources.id.spec);
        hscroll1 = findViewById(Resources.id.hscroll1);
        spec2_lay = findViewById(Resources.id.spec_2lay);
        LinearLayout color_selector = findViewById(Resources.id.colour_selector);
        colour = findViewById(Resources.id.color);
        parameters_hold = findViewById(Resources.id.parameter_holder);
        spec2 = findViewById(Resources.id.spec2);
        code = findViewById(Resources.id.code);
        MaterialButton cancel = findViewById(Resources.id.cancel);
        MaterialButton save = findViewById(Resources.id.save);
        LinearLayout reset = findViewById(Resources.id.reset);

        AlertDialog.Builder block_type_dialog = new AlertDialog.Builder(this);

        back_icon.setOnClickListener(Helper.getBackPressedClickListener(this));
        Helper.applyRippleToToolbarView(back_icon);

        name.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String string = s.toString();
                if (!id_detector.contains(string)) {
                    name_lay.setErrorEnabled(false);
                    save.setEnabled(true);
                } else if (!mod.equals("edit")) {
                    name_lay.setErrorEnabled(true);
                    name_lay.setError("Block name already in use");
                    save.setEnabled(false);
                } else {
                    HashMap<String, Object> savedBlocksListBlock = blocksList.get(n);
                    Object blockNameObject = savedBlocksListBlock.get("name");

                    if (!string.equals(blockNameObject)) {
                        name_lay.setErrorEnabled(true);
                        name_lay.setError("Block name already in use");
                        save.setEnabled(false);
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

        select_type.setOnClickListener(v -> {
            List<String> types = Arrays.asList(
                    "regular",
                    "c",
                    "e",
                    "s",
                    "b",
                    "d",
                    "v",
                    "a",
                    "f",
                    "l",
                    "p",
                    "h"
            );
            List<String> choices = Arrays.asList(
                    "Regular block (regular)",
                    "if block (c)",
                    "if-else block (e)",
                    "String (s)",
                    "Boolean (b)",
                    "Number (d)",
                    "Variable (v)",
                    "Map (a)",
                    "stop block (f)",
                    "List (l)",
                    "Component (p)",
                    "Header (h)"
            );
            AtomicInteger choice = new AtomicInteger();
            block_type_dialog.setTitle("Block type")
                    .setSingleChoiceItems(choices.toArray(new String[0]),
                            types.indexOf(type.getText().toString()), (dialog, which) -> choice.set(which))
                    .setPositiveButton(Resources.string.common_word_save, (dialog, which) -> type.setText(types.get(choice.get())))
                    .setNegativeButton(Resources.string.common_word_cancel, null)
                    .create().show();
        });

        type.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("e")) {
                    AutoTransition transition = new AutoTransition();
                    transition.setDuration(300L);
                    TransitionManager.beginDelayedTransition(vscroll1, transition);
                    spec2_lay.setVisibility(View.VISIBLE);
                } else {
                    AutoTransition transition = new AutoTransition();
                    transition.setDuration(300L);
                    TransitionManager.beginDelayedTransition(vscroll1, transition);
                    spec2_lay.setVisibility(View.GONE);
                }
                _updateBlockSpec(s.toString(), colour.getText().toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

        spec.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Matcher matcher = Pattern.compile("%[smdb]\\.?[a-zA-Z]*").matcher(s.toString());
                while (matcher.find()) {
                    try {
                        spec.getEditableText().setSpan(new ForegroundColorSpan(Color.WHITE),
                                matcher.start(), matcher.end(), 33);
                    } catch (Exception ignored) {
                    }
                    try {
                        spec.getEditableText().setSpan(new BackgroundColorSpan(0x18000000),
                                matcher.start(), matcher.end(), 33);
                    } catch (Exception ignored) {
                    }
                    try {
                        spec.getEditableText().setSpan(new RelativeSizeSpan(-5),
                                matcher.start(), matcher.end(), 33);
                    } catch (Exception ignored) {
                    }
                    try {
                        spec.getEditableText().setSpan(new StyleSpan(1), matcher.start(),
                                matcher.end(), 33);
                    } catch (Exception ignored) {
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

        color_selector.setOnClickListener(v -> {
            View inflate = getLayoutInflater().inflate(Resources.layout.color_picker, null);
            Zx zx = new Zx(inflate, BlocksManagerCreatorActivity.this, 0, true, false);
            zx.a(new PCP(colour));
            zx.setAnimationStyle(Resources.anim.abc_fade_in);
            zx.showAtLocation(inflate, Gravity.CENTER, 0, 0);
        });

        colour.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _updateBlockSpec(type.getText().toString(), s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

        cancel.setOnClickListener(Helper.getBackPressedClickListener(this));
        save.setOnClickListener(v -> {
            if (type.getText().toString().equals("")) {
                type.setText(" ");
            }
            if (mod.equals("add")) {
                _AddBlock();
            }
            if (mod.equals("insert")) {
                _insertBlockAt(n);
            }
            if (mod.equals("edit")) {
                _editBlock(n);
            }
        });

        reset.setOnClickListener(v -> colour.setText(pallet_colour));
        spec.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    break;

                case MotionEvent.ACTION_UP:
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
            v.onTouchEvent(event);
            return true;
        });
    }

    private void initializeLogic() {
        _inputsProp();
        _addParams();
        _receiveIntents();
        new SimpleHighlighter(code);
    }

    private View _k(final String str, String str2) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setPadding(
                (int) SketchwareUtil.getDip(8),
                0,
                (int) SketchwareUtil.getDip(8),
                0
        );
        textView.setTextColor(0xff006064);
        textView.setText(str2);
        textView.setTextSize(14.0f);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setOnClickListener(v -> {
            StringBuilder sb = new StringBuilder(spec.getText().toString());
            int selectionStart = spec.getSelectionStart();
            sb.insert(selectionStart, str);
            spec.setText(sb);
            spec.setSelection(selectionStart + str.length());
        });
        return textView;
    }

    private void _inputsProp() {
        name.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        name.setSingleLine(true);
        type.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        type.setSingleLine(true);
        typename.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        typename.setSingleLine(true);
        spec.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        spec.setSingleLine(true);
        colour.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        colour.setSingleLine(true);
        spec2.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        spec2.setSingleLine(true);
        code.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        code.setSingleLine(false);
        hscroll1.setVerticalScrollBarEnabled(false);
        hscroll1.setHorizontalScrollBarEnabled(false);
        spec2_lay.setVisibility(View.GONE);
    }

    private void _addParams() {
        parameters_hold.addView(_k("%s.inputOnly ", "inputOnly"));
        parameters_hold.addView(_k("%s ", "string"));
        parameters_hold.addView(_k("%b ", "boolean"));
        parameters_hold.addView(_k("%d ", "number"));
        parameters_hold.addView(_k("%m.varMap ", "map"));
        parameters_hold.addView(_k("%m.view ", "view"));
        parameters_hold.addView(_k("%m.textview ", "textView"));
        parameters_hold.addView(_k("%m.edittext ", "editText"));
        parameters_hold.addView(_k("%m.imageview ", "ImageView"));
        parameters_hold.addView(_k("%m.listview ", "listView"));
        parameters_hold.addView(_k("%m.list ", "list"));
        parameters_hold.addView(_k("%m.listMap ", "listMap"));
        parameters_hold.addView(_k("%m.listStr ", "listString"));
        parameters_hold.addView(_k("%m.listInt ", "listNumber"));
        parameters_hold.addView(_k("%m.intent ", "intent"));
        parameters_hold.addView(_k("%m.color ", "color"));
        parameters_hold.addView(_k("%m.activity ", "activity"));
        parameters_hold.addView(_k("%m.resource ", "resource"));
        parameters_hold.addView(_k("%m.customViews ", "custom views"));
        parameters_hold.addView(_k("%m.layout ", "layout"));
        parameters_hold.addView(_k("%m.anim ", "anim"));
        parameters_hold.addView(_k("%m.drawable ", "drawable"));
    }

    private void _receiveIntents() {
        mod = getIntent().getStringExtra("mode");
        path = getIntent().getStringExtra("path");
        pallet_colour = getIntent().getStringExtra("color");
        _getList();
        if (mod.equals("add")) {
            n = Integer.parseInt(getIntent().getStringExtra("pallet"));
            colour.setText(pallet_colour);
            page_title.setText("Add a new block");
            return;
        }
        n = Integer.parseInt(getIntent().getStringExtra("pos"));
        colour.setText(pallet_colour);
        page_title.setText("Insert block");
        if (mod.equals("edit")) {
            page_title.setText("Edit block");
            _fillUpInputs(n);
        }
    }

    private void _fillUpInputs(int position) {
        HashMap<String, Object> block = blocksList.get(position);

        Object nameObject = block.get("name");
        if (nameObject instanceof String) {
            name.setText((String) nameObject);
        } else {
            name.setError("Invalid name block data");
        }

        Object typeObject = block.get("type");
        if (typeObject instanceof String) {
            String typeString = (String) typeObject;

            if (typeString.equals(" ")) {
                type.setText("regular");
            } else {
                type.setText(typeString);
            }
        } else {
            type.setError("Invalid type block data");
        }

        Object typeName = block.get("typeName");
        if (typeName != null) {
            if (typeName instanceof String) {
                typename.setText((String) typeName);
            } else {
                typename.setError("Invalid typeName block data");
            }
        }

        Object specObject = block.get("spec");
        if (specObject instanceof String) {
            spec.setText((String) specObject);
        } else {
            spec.setError("Invalid spec block data");
        }

        Object spec2Object = block.get("spec2");
        if (spec2Object != null) {
            if (spec2Object instanceof String) {
                spec2.setText((String) spec2Object);
            } else {
                spec2.setError("Invalid spec2 block data");
            }
        }

        Object colorObject = block.get("color");
        if (colorObject != null) {
            if (colorObject instanceof String) {
                colour.setText((String) colorObject);
            } else {
                colour.setError("Invalid color block data");
            }
        } else {
            colour.setText(pallet_colour);
        }

        Object codeObject = block.get("code");
        if (codeObject instanceof String) {
            code.setText((String) codeObject);
        } else {
            code.setHint("(Invalid code block data)");
        }
    }

    private void _getList() {
        try {
            blocksList = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);

            if (blocksList != null) {
                for (int i = 0, blocksListSize = blocksList.size(); i < blocksListSize; i++) {
                    HashMap<String, Object> block = blocksList.get(i);
                    Object name = block.get("name");

                    if (name instanceof String) {
                        id_detector.add((String) name);
                    } else {
                        SketchwareUtil.toastError("Custom Block #" + i + " in current palette has an invalid name");
                    }
                }
                return;
            }
        } catch (JsonParseException ignored) {
        }
        SketchwareUtil.toastError("Invalid blocks file detected!");
        blocksList = new ArrayList<>();
    }

    private void _updateBlockSpec(String specId, String color) {
        switch (specId) {
            case " ":
            case "regular":
                spec.setBackgroundResource(Resources.drawable.block_ori);
                break;

            case "b":
                spec.setBackgroundResource(Resources.drawable.block_boolean);
                break;

            case "c":
            case "e":
                spec.setBackgroundResource(Resources.drawable.if_else);
                break;

            case "d":
                spec.setBackgroundResource(Resources.drawable.block_num);
                break;

            case "f":
                spec.setBackgroundResource(Resources.drawable.block_stop);
                break;

            default:
                spec.setBackgroundResource(Resources.drawable.block_string);
                break;
        }
        try {
            spec.getBackground().setColorFilter(Color.parseColor(color), PorterDuff.Mode.MULTIPLY);
            spec.setTag(color);
        } catch (Exception e) {
            try {
                spec.getBackground().setColorFilter(Color.parseColor(pallet_colour), PorterDuff.Mode.MULTIPLY);
                spec.setTag(pallet_colour);
            } catch (Exception e2) {
                spec.getBackground().setColorFilter(Color.parseColor("#8c8c8c"), PorterDuff.Mode.MULTIPLY);
                spec.setTag("#8c8c8c");
            }
        }
    }

    private void _AddBlock() {
        HashMap<String, Object> tempMap = new HashMap<>();
        tempMap.put("name", name.getText().toString());
        if (type.getText().toString().equals("regular")) {
            tempMap.put("type", " ");
        } else if (type.getText().toString().equals("")) {
            tempMap.put("type", " ");
        } else {
            tempMap.put("type", type.getText().toString());
        }
        tempMap.put("typeName", typename.getText().toString());
        tempMap.put("spec", spec.getText().toString());
        tempMap.put("color", spec.getTag());
        if (type.getText().toString().equals("e")) {
            tempMap.put("spec2", spec2.getText().toString());
        }
        tempMap.put("code", code.getText().toString());
        tempMap.put("palette", String.valueOf(n));
        blocksList.add(tempMap);
        FileUtil.writeFile(path, new Gson().toJson(blocksList));
        SketchwareUtil.toast("Saved");
        finish();
    }

    private void _insertBlockAt(int position) {
        HashMap<String, Object> tempMap = new HashMap<>();
        tempMap.put("name", name.getText().toString());
        if (type.getText().toString().equals("regular") || type.getText().toString().equals("")) {
            tempMap.put("type", " ");
        } else {
            tempMap.put("type", type.getText().toString());
        }
        tempMap.put("typeName", typename.getText().toString());
        tempMap.put("spec", spec.getText().toString());
        tempMap.put("color", spec.getTag());
        if (type.getText().toString().equals("e")) {
            tempMap.put("spec2", spec2.getText().toString());
        }
        tempMap.put("code", code.getText().toString());
        tempMap.put("palette", blocksList.get(position).get("palette"));
        blocksList.add(position, tempMap);
        FileUtil.writeFile(path, new Gson().toJson(blocksList));
        SketchwareUtil.toast("Saved");
        finish();
    }

    private void _editBlock(int position) {
        HashMap<String, Object> tempMap = blocksList.get(position);
        tempMap.put("name", name.getText().toString());
        if (type.getText().toString().equals("regular") || type.getText().toString().equals("")) {
            tempMap.put("type", " ");
        } else {
            tempMap.put("type", type.getText().toString());
        }
        tempMap.put("typeName", typename.getText().toString());
        tempMap.put("spec", spec.getText().toString());
        tempMap.put("color", spec.getTag());
        if (type.getText().toString().equals("e")) {
            tempMap.put("spec2", spec2.getText().toString());
        }
        tempMap.put("code", code.getText().toString());
        FileUtil.writeFile(path, new Gson().toJson(blocksList));
        SketchwareUtil.toast("Saved");
        finish();
    }
}
