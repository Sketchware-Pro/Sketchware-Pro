package mod.hilal.saif.activities.tools;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.Spanned;
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
import com.sketchware.remod.R;

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
import mod.hasrat.highlighter.SimpleHighlighter;
import mod.hasrat.lib.BaseTextWatcher;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.lib.PCP;

public class BlocksManagerCreatorActivity extends AppCompatActivity {

    private final ArrayList<String> id_detector = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> blocksList = new ArrayList<>();
    private EditText code;
    private EditText colour;
    private HorizontalScrollView hscroll1;
    /**
     * Current mode of this activity, "edit" if editing a block, "add" if creating a new block and "insert" if inserting a block above another
     */
    private String mode = "";
    /**
     * Position of current editing/adding/inserting block in palette
     */
    private int blockPosition = 0;
    private EditText name;
    private TextView pageTitle;
    private String palletColour = "";
    private LinearLayout parametersHolder;
    private String path = "";
    private EditText spec;
    private EditText spec2;
    private TextInputLayout spec2InputLayout;
    private EditText type;
    private EditText typename;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blocks_manager_creator);
        initialize();
        initializeLogic();
    }

    private void initialize() {
        ScrollView vscroll1 = findViewById(R.id.vscroll1);
        ImageView backIcon = findViewById(R.id.back_ico);
        pageTitle = findViewById(R.id.page_title);
        TextInputLayout nameLayout = findViewById(R.id.name_lay);
        name = findViewById(R.id.name);
        LinearLayout selectType = findViewById(R.id.select_type);
        type = findViewById(R.id.type);
        typename = findViewById(R.id.type_name);
        spec = findViewById(R.id.spec);
        hscroll1 = findViewById(R.id.hscroll1);
        spec2InputLayout = findViewById(R.id.spec_2lay);
        LinearLayout colorSelector = findViewById(R.id.colour_selector);
        colour = findViewById(R.id.color);
        parametersHolder = findViewById(R.id.parameter_holder);
        spec2 = findViewById(R.id.spec2);
        code = findViewById(R.id.code);
        MaterialButton cancel = findViewById(R.id.cancel);
        MaterialButton save = findViewById(R.id.save);
        LinearLayout reset = findViewById(R.id.reset);

        AlertDialog.Builder blockTypeDialog = new AlertDialog.Builder(this);

        backIcon.setOnClickListener(Helper.getBackPressedClickListener(this));
        Helper.applyRippleToToolbarView(backIcon);

        name.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String string = s.toString();
                if (!id_detector.contains(string)) {
                    nameLayout.setErrorEnabled(false);
                    save.setEnabled(true);
                } else if (!mode.equals("edit")) {
                    nameLayout.setErrorEnabled(true);
                    nameLayout.setError("Block name already in use");
                    save.setEnabled(false);
                } else {
                    HashMap<String, Object> savedBlocksListBlock = blocksList.get(blockPosition);
                    Object blockNameObject = savedBlocksListBlock.get("name");

                    if (!string.equals(blockNameObject)) {
                        nameLayout.setErrorEnabled(true);
                        nameLayout.setError("Block name already in use");
                        save.setEnabled(false);
                    }
                }
            }
        });

        selectType.setOnClickListener(v -> {
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
            blockTypeDialog.setTitle("Block type")
                    .setSingleChoiceItems(choices.toArray(new String[0]),
                            types.indexOf(type.getText().toString()), (dialog, which) -> choice.set(which))
                    .setPositiveButton(R.string.common_word_save, (dialog, which) -> type.setText(types.get(choice.get())))
                    .setNegativeButton(R.string.common_word_cancel, null)
                    .create().show();
        });

        type.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("e")) {
                    AutoTransition transition = new AutoTransition();
                    transition.setDuration(300L);
                    TransitionManager.beginDelayedTransition(vscroll1, transition);
                    spec2InputLayout.setVisibility(View.VISIBLE);
                } else {
                    AutoTransition transition = new AutoTransition();
                    transition.setDuration(300L);
                    TransitionManager.beginDelayedTransition(vscroll1, transition);
                    spec2InputLayout.setVisibility(View.GONE);
                }
                updateBlockSpec(s.toString(), colour.getText().toString());
            }
        });

        spec.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Matcher matcher = Pattern.compile("%[smdb]\\.?[a-zA-Z]*").matcher(s.toString());
                while (matcher.find()) {
                    try {
                        spec.getEditableText().setSpan(new ForegroundColorSpan(Color.WHITE),
                                matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } catch (Exception ignored) {
                    }
                    try {
                        spec.getEditableText().setSpan(new BackgroundColorSpan(0x18000000),
                                matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } catch (Exception ignored) {
                    }
                    try {
                        spec.getEditableText().setSpan(new RelativeSizeSpan(-5),
                                matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } catch (Exception ignored) {
                    }
                    try {
                        spec.getEditableText().setSpan(new StyleSpan(1), matcher.start(),
                                matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } catch (Exception ignored) {
                    }
                }
            }
        });

        colorSelector.setOnClickListener(v -> {
            View inflate = getLayoutInflater().inflate(R.layout.color_picker, null);
            Zx zx = new Zx(inflate, BlocksManagerCreatorActivity.this, 0, true, false);
            zx.a(new PCP(colour));
            zx.setAnimationStyle(R.anim.abc_fade_in);
            zx.showAtLocation(inflate, Gravity.CENTER, 0, 0);
        });

        colour.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateBlockSpec(type.getText().toString(), s.toString());
            }
        });

        cancel.setOnClickListener(Helper.getBackPressedClickListener(this));
        save.setOnClickListener(v -> {
            if (type.getText().toString().equals("")) {
                type.setText(" ");
            }
            if (mode.equals("add")) {
                addBlock();
            }
            if (mode.equals("insert")) {
                insertBlockAt(blockPosition);
            }
            if (mode.equals("edit")) {
                editBlock(blockPosition);
            }
        });

        reset.setOnClickListener(v -> colour.setText(palletColour));
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
        inputProperties();
        addParameters();
        receiveIntents();
        new SimpleHighlighter(code);
    }

    private View addBlockMenu(final String menu, String name) {
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
        textView.setText(name);
        textView.setTextSize(14.0f);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setOnClickListener(v -> {
            StringBuilder sb = new StringBuilder(spec.getText().toString());
            int selectionStart = spec.getSelectionStart();
            sb.insert(selectionStart, menu);
            spec.setText(sb);
            spec.setSelection(selectionStart + menu.length());
        });
        return textView;
    }

    private void inputProperties() {
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
        spec2InputLayout.setVisibility(View.GONE);
    }

    private void addParameters() {
        parametersHolder.addView(addBlockMenu("%s.inputOnly ", "inputOnly"));
        parametersHolder.addView(addBlockMenu("%s ", "string"));
        parametersHolder.addView(addBlockMenu("%b ", "boolean"));
        parametersHolder.addView(addBlockMenu("%d ", "number"));
        parametersHolder.addView(addBlockMenu("%m.varMap ", "map"));
        parametersHolder.addView(addBlockMenu("%m.view ", "view"));
        parametersHolder.addView(addBlockMenu("%m.textview ", "textView"));
        parametersHolder.addView(addBlockMenu("%m.edittext ", "editText"));
        parametersHolder.addView(addBlockMenu("%m.imageview ", "ImageView"));
        parametersHolder.addView(addBlockMenu("%m.listview ", "listView"));
        parametersHolder.addView(addBlockMenu("%m.list ", "list"));
        parametersHolder.addView(addBlockMenu("%m.listMap ", "listMap"));
        parametersHolder.addView(addBlockMenu("%m.listStr ", "listString"));
        parametersHolder.addView(addBlockMenu("%m.listInt ", "listNumber"));
        parametersHolder.addView(addBlockMenu("%m.intent ", "intent"));
        parametersHolder.addView(addBlockMenu("%m.color ", "color"));
        parametersHolder.addView(addBlockMenu("%m.activity ", "activity"));
        parametersHolder.addView(addBlockMenu("%m.resource ", "resource"));
        parametersHolder.addView(addBlockMenu("%m.customViews ", "custom views"));
        parametersHolder.addView(addBlockMenu("%m.layout ", "layout"));
        parametersHolder.addView(addBlockMenu("%m.anim ", "anim"));
        parametersHolder.addView(addBlockMenu("%m.drawable ", "drawable"));
    }

    private void receiveIntents() {
        mode = getIntent().getStringExtra("mode");
        path = getIntent().getStringExtra("path");
        palletColour = getIntent().getStringExtra("color");
        getBlockList();
        if (mode.equals("add")) {
            blockPosition = Integer.parseInt(getIntent().getStringExtra("pallet"));
            colour.setText(palletColour);
            pageTitle.setText("Add a new block");
            return;
        }
        blockPosition = Integer.parseInt(getIntent().getStringExtra("pos"));
        colour.setText(palletColour);
        pageTitle.setText("Insert block");
        if (mode.equals("edit")) {
            pageTitle.setText("Edit block");
            fillUpInputs(blockPosition);
        }
    }

    private void fillUpInputs(int position) {
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
            colour.setText(palletColour);
        }

        Object codeObject = block.get("code");
        if (codeObject instanceof String) {
            code.setText((String) codeObject);
        } else {
            code.setHint("(Invalid code block data)");
        }
    }

    private void getBlockList() {
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

    private void updateBlockSpec(String specId, String color) {
        switch (specId) {
            case " ":
            case "regular":
                spec.setBackgroundResource(R.drawable.block_ori);
                break;

            case "b":
                spec.setBackgroundResource(R.drawable.block_boolean);
                break;

            case "c":
            case "e":
                spec.setBackgroundResource(R.drawable.if_else);
                break;

            case "d":
                spec.setBackgroundResource(R.drawable.block_num);
                break;

            case "f":
                spec.setBackgroundResource(R.drawable.block_stop);
                break;

            default:
                spec.setBackgroundResource(R.drawable.block_string);
                break;
        }
        try {
            spec.getBackground().setColorFilter(Color.parseColor(color), PorterDuff.Mode.MULTIPLY);
            spec.setTag(color);
        } catch (Exception e) {
            try {
                spec.getBackground().setColorFilter(Color.parseColor(palletColour), PorterDuff.Mode.MULTIPLY);
                spec.setTag(palletColour);
            } catch (Exception e2) {
                spec.getBackground().setColorFilter(Color.parseColor("#8c8c8c"), PorterDuff.Mode.MULTIPLY);
                spec.setTag("#8c8c8c");
            }
        }
    }

    private void addBlock() {
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
        tempMap.put("palette", String.valueOf(blockPosition));
        blocksList.add(tempMap);
        FileUtil.writeFile(path, new Gson().toJson(blocksList));
        SketchwareUtil.toast("Saved");
        finish();
    }

    private void insertBlockAt(int position) {
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

    private void editBlock(int position) {
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
