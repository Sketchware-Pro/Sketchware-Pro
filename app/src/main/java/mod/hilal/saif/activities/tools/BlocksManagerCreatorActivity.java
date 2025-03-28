package mod.hilal.saif.activities.tools;

import static pro.sketchware.utility.GsonUtils.getGson;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import a.a.a.Rs;
import a.a.a.Zx;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.JsonParseException;

import mod.hey.studios.util.Helper;
import mod.hilal.saif.lib.PCP;

import pro.sketchware.R;
import pro.sketchware.databinding.ActivityBlocksManagerCreatorBinding;
import pro.sketchware.lib.base.BaseTextWatcher;
import pro.sketchware.lib.highlighter.SimpleHighlighter;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.PropertiesUtil;
import pro.sketchware.utility.SketchwareUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BlocksManagerCreatorActivity extends BaseAppCompatActivity {

    private ActivityBlocksManagerCreatorBinding binding;
    private final ArrayList<String> id_detector = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> blocksList = new ArrayList<>();
    
    private static final Pattern PARAM_PATTERN = Pattern.compile("%m(?!\\.[\\w]+)");
    
    /**
     * Current mode of this activity, "edit" if editing a block, "add" if creating a new block and "insert" if inserting a block above another
     */
    private String mode = "";
    /**
     * Position of current editing/adding/inserting block in palette
     */
    private int blockPosition = 0;
    private String palletColour = "";
    private String path = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBlocksManagerCreatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialize();
        initializeLogic();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initialize() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        binding.name.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String string = s.toString();
                if (!id_detector.contains(string)) {
                    binding.nameLayout.setError(null);
                    binding.save.setEnabled(true);
                } else if (!mode.equals("edit")) {
                    binding.nameLayout.setError("Block name already in use");
                    binding.save.setEnabled(false);
                } else {
                    HashMap<String, Object> savedBlocksListBlock = blocksList.get(blockPosition);
                    Object blockNameObject = savedBlocksListBlock.get("name");

                    if (!string.equals(blockNameObject)) {
                        binding.nameLayout.setError("Block name already in use");
                        binding.save.setEnabled(false);
                    }
                }
            }
        });

        binding.selectType.setOnClickListener(v -> {
            List<String> types = Arrays.asList(
                    "regular", "c", "e", "s", "b",
                    "d", "v", "a", "f", "l", "p", "h"
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
            new MaterialAlertDialogBuilder(this).setTitle("Block type")
                    .setSingleChoiceItems(choices.toArray(new String[0]),
                            types.indexOf(Helper.getText(binding.type)), (dialog, which) -> choice.set(which))
                    .setPositiveButton(R.string.common_word_save, (dialog, which) -> binding.type.setText(types.get(choice.get())))
                    .setNegativeButton(R.string.common_word_cancel, null)
                    .create().show();
        });

        binding.type.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("e")) {
                    AutoTransition transition = new AutoTransition();
                    transition.setDuration(300L);
                    TransitionManager.beginDelayedTransition(binding.scrollView, transition);
                    binding.spec2InputLayout.setVisibility(View.VISIBLE);
                } else {
                    AutoTransition transition = new AutoTransition();
                    transition.setDuration(300L);
                    TransitionManager.beginDelayedTransition(binding.scrollView, transition);
                    binding.spec2InputLayout.setVisibility(View.GONE);
                }
                updateBlockSpec(s.toString(), Helper.getText(binding.colour));
            }
        });

        binding.spec.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateBlockSpec(Helper.getText(binding.type), Helper.getText(binding.colour));
            }
        });

        binding.openColorPalette.setOnClickListener(v -> {
            Zx zx = new Zx(this, 0, false, false);
            zx.a(new PCP(binding.colour));
            zx.showAtLocation(v, Gravity.CENTER, 0, 0);
        });

        binding.colour.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!PropertiesUtil.isHexColor(s.toString())) {
                    binding.colourLay.setError("Invalid hex color");
                } else {
                    binding.colourLay.setError(null);
                }
                updateBlockSpec(Helper.getText(binding.type), s.toString());
            }
        });

        binding.cancel.setOnClickListener(Helper.getBackPressedClickListener(this));
        binding.save.setOnClickListener(v -> {
            if (!PropertiesUtil.isHexColor(Helper.getText(binding.colour))) {
                SketchwareUtil.showMessage(getApplicationContext(), "Invalid hex color");
                return;
            }
            Matcher matcher = PARAM_PATTERN.matcher(Helper.getText(binding.spec));
            if (matcher.find()) {
                SketchwareUtil.showMessage(getApplicationContext(), "Invalid block params");
                return;
            }
            if (Helper.getText(binding.type).isEmpty()) {
                binding.type.setText(" ");
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

        binding.reset.setOnClickListener(v -> binding.colour.setText(palletColour));
        binding.spec.setOnTouchListener((v, event) -> {
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
        new SimpleHighlighter(binding.code);
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
        textView.setTextColor(MaterialColors.getColor(textView, R.attr.colorPrimary));
        textView.setText(name);
        textView.setTextSize(14.0f);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setOnClickListener(v -> {
            StringBuilder sb = new StringBuilder(Helper.getText(binding.spec));
            int selectionStart = binding.spec.getSelectionStart();
            sb.insert(selectionStart, menu);
            binding.spec.setText(sb);
            binding.spec.setSelection(selectionStart + menu.length());
        });
        return textView;
    }

    private void inputProperties() {
        binding.name.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        binding.name.setSingleLine(true);
        binding.type.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        binding.type.setSingleLine(true);
        binding.typename.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        binding.typename.setSingleLine(true);
        binding.spec.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        binding.spec.setSingleLine(true);
        binding.colour.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        binding.colour.setSingleLine(true);
        binding.spec2.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        binding.spec2.setSingleLine(true);
        binding.code.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        binding.code.setSingleLine(false);
        binding.parameterScrollView.setVerticalScrollBarEnabled(false);
        binding.parameterScrollView.setHorizontalScrollBarEnabled(false);
        binding.spec2InputLayout.setVisibility(View.GONE);
    }

    private void addParameters() {
        binding.parametersHolder.addView(addBlockMenu("%s.inputOnly ", "inputOnly"));
        binding.parametersHolder.addView(addBlockMenu("%s ", "string"));
        binding.parametersHolder.addView(addBlockMenu("%b ", "boolean"));
        binding.parametersHolder.addView(addBlockMenu("%d ", "number"));
        binding.parametersHolder.addView(addBlockMenu("%m.varMap ", "map"));
        binding.parametersHolder.addView(addBlockMenu("%m.view ", "view"));
        binding.parametersHolder.addView(addBlockMenu("%m.textview ", "textView"));
        binding.parametersHolder.addView(addBlockMenu("%m.edittext ", "editText"));
        binding.parametersHolder.addView(addBlockMenu("%m.imageview ", "ImageView"));
        binding.parametersHolder.addView(addBlockMenu("%m.listview ", "listView"));
        binding.parametersHolder.addView(addBlockMenu("%m.list ", "list"));
        binding.parametersHolder.addView(addBlockMenu("%m.listMap ", "listMap"));
        binding.parametersHolder.addView(addBlockMenu("%m.listStr ", "listString"));
        binding.parametersHolder.addView(addBlockMenu("%m.listInt ", "listNumber"));
        binding.parametersHolder.addView(addBlockMenu("%m.intent ", "intent"));
        binding.parametersHolder.addView(addBlockMenu("%m.color ", "color"));
        binding.parametersHolder.addView(addBlockMenu("%m.activity ", "activity"));
        binding.parametersHolder.addView(addBlockMenu("%m.resource ", "resource"));
        binding.parametersHolder.addView(addBlockMenu("%m.customViews ", "custom views"));
        binding.parametersHolder.addView(addBlockMenu("%m.layout ", "layout"));
        binding.parametersHolder.addView(addBlockMenu("%m.anim ", "anim"));
        binding.parametersHolder.addView(addBlockMenu("%m.drawable ", "drawable"));
        binding.parametersHolder.addView(addBlockMenu("%m.ResString ", "ResStrings"));
    }

    private void receiveIntents() {
        mode = getIntent().getStringExtra("mode");
        path = getIntent().getStringExtra("path");
        palletColour = getIntent().getStringExtra("color");
        getBlockList();
        if (mode.equals("add")) {
            blockPosition = Integer.parseInt(getIntent().getStringExtra("pallet"));
            binding.colour.setText(palletColour);
            getSupportActionBar().setTitle("Add a new block");
            return;
        }
        blockPosition = Integer.parseInt(getIntent().getStringExtra("pos"));
        binding.colour.setText(palletColour);
        getSupportActionBar().setTitle("Insert block");
        if (mode.equals("edit")) {
            getSupportActionBar().setTitle("Edit block");
            fillUpInputs(blockPosition);
        }
    }

    private void fillUpInputs(int position) {
        HashMap<String, Object> block = blocksList.get(position);

        Object nameObject = block.get("name");
        if (nameObject instanceof String) {
            binding.name.setText((String) nameObject);
        } else {
            binding.name.setError("Invalid name block data");
        }

        Object typeObject = block.get("type");
        if (typeObject instanceof String) {
            String typeString = (String) typeObject;

            if (typeString.equals(" ")) {
                binding.type.setText("regular");
            } else {
                binding.type.setText(typeString);
            }
        } else {
            binding.type.setError("Invalid type block data");
        }

        Object typeName = block.get("typeName");
        if (typeName != null) {
            if (typeName instanceof String) {
                binding.typename.setText((String) typeName);
            } else {
                binding.typename.setError("Invalid typeName block data");
            }
        }

        Object specObject = block.get("spec");
        if (specObject instanceof String) {
            binding.spec.setText((String) specObject);
        } else {
            binding.spec.setError("Invalid spec block data");
        }

        Object spec2Object = block.get("spec2");
        if (spec2Object != null) {
            if (spec2Object instanceof String) {
                binding.spec2.setText((String) spec2Object);
            } else {
                binding.spec2.setError("Invalid spec2 block data");
            }
        }
        
        Object importsObject = block.get("imports");
        if (importsObject != null) {
            if (importsObject instanceof String) {
                binding.customImport.setText((String) importsObject);
            } else {
                binding.customImport.setError("Invalid imports block data");
            }
        }

        Object colorObject = block.get("color");
        if (colorObject != null) {
            if (colorObject instanceof String) {
                binding.colour.setText((String) colorObject);
            } else {
                binding.colour.setError("Invalid color block data");
            }
        } else {
            binding.colour.setText(palletColour);
        }

        Object codeObject = block.get("code");
        if (codeObject instanceof String) {
            binding.code.setText((String) codeObject);
        } else {
            binding.code.setHint("(Invalid code block data)");
        }
    }

    private void getBlockList() {
        try {
            blocksList = getGson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);

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
        } catch (JsonParseException e) {
        }
        SketchwareUtil.showFailedToParseJsonDialog(this, new File(path), "Custom Blocks", v -> getBlockList());
        blocksList = new ArrayList<>();
    }

    private void updateBlockSpec(String specId, String color) {
        binding.blockArea.removeAllViews();
        var blockType = specId.equalsIgnoreCase("regular") ? " " : specId;
        try {
            var block = new Rs(this, -1, Helper.getText(binding.spec), blockType, Helper.getText(binding.name));
            block.e = PropertiesUtil.isHexColor(color) ? PropertiesUtil.parseColor(color) : Color.parseColor("#F0F0F0");
            binding.blockArea.addView(block);
        } catch (Exception e) {
            var block = new TextView(this);
            block.setTextColor(Color.RED);
            var input = Helper.getText(binding.spec);
            Matcher matcher = PARAM_PATTERN.matcher(input);
            if (matcher.find()) {
                int position = matcher.end();
                //Unable to resolve this error because the Rs class still undecompiled.
                block.setText("Error: '%m' must be followed by '.param' at position " + position);
            } else {
                block.setText(e.toString());
            }
            binding.blockArea.addView(block);
        }
    }

    private void addBlock() {
        HashMap<String, Object> tempMap = new HashMap<>();
        tempMap.put("name", Helper.getText(binding.name));
        if (Helper.getText(binding.type).equals("regular")) {
            tempMap.put("type", " ");
        } else if (Helper.getText(binding.type).isEmpty()) {
            tempMap.put("type", " ");
        } else {
            tempMap.put("type", Helper.getText(binding.type));
        }
        tempMap.put("typeName", Helper.getText(binding.typename));
        tempMap.put("spec", Helper.getText(binding.spec));
        tempMap.put("color", Helper.getText(binding.colour));
        if (Helper.getText(binding.type).equals("e")) {
            tempMap.put("spec2", Helper.getText(binding.spec2));
        }
        if (!TextUtils.isEmpty(Helper.getText(binding.customImport))) {
            tempMap.put("imports", Helper.getText(binding.customImport));
        }
        tempMap.put("code", Helper.getText(binding.code));
        tempMap.put("palette", String.valueOf(blockPosition));
        blocksList.add(tempMap);
        FileUtil.writeFile(path, getGson().toJson(blocksList));
        SketchwareUtil.toast("Saved");
        finish();
    }

    private void insertBlockAt(int position) {
        HashMap<String, Object> tempMap = new HashMap<>();
        tempMap.put("name", Helper.getText(binding.name));
        if (Helper.getText(binding.type).equals("regular") || Helper.getText(binding.type).isEmpty()) {
            tempMap.put("type", " ");
        } else {
            tempMap.put("type", Helper.getText(binding.type));
        }
        tempMap.put("typeName", Helper.getText(binding.typename));
        tempMap.put("spec", Helper.getText(binding.spec));
        tempMap.put("color", Helper.getText(binding.colour));
        if (Helper.getText(binding.type).equals("e")) {
            tempMap.put("spec2", Helper.getText(binding.spec2));
        }
        if (!TextUtils.isEmpty(Helper.getText(binding.customImport))) {
            tempMap.put("imports", Helper.getText(binding.customImport));
        }
        tempMap.put("code", Helper.getText(binding.code));
        tempMap.put("palette", blocksList.get(position).get("palette"));
        blocksList.add(position, tempMap);
        FileUtil.writeFile(path, getGson().toJson(blocksList));
        SketchwareUtil.toast("Saved");
        finish();
    }

    private void editBlock(int position) {
        HashMap<String, Object> tempMap = blocksList.get(position);
        tempMap.put("name", Helper.getText(binding.name));
        if (Helper.getText(binding.type).equals("regular") || Helper.getText(binding.type).isEmpty()) {
            tempMap.put("type", " ");
        } else {
            tempMap.put("type", Helper.getText(binding.type));
        }
        tempMap.put("typeName", Helper.getText(binding.typename));
        tempMap.put("spec", Helper.getText(binding.spec));
        tempMap.put("color", Helper.getText(binding.colour));
        if (Helper.getText(binding.type).equals("e")) {
            tempMap.put("spec2", Helper.getText(binding.spec2));
        }
        tempMap.put("imports", Helper.getText(binding.customImport));
        tempMap.put("code", Helper.getText(binding.code));
        FileUtil.writeFile(path, getGson().toJson(blocksList));
        SketchwareUtil.toast("Saved");
        finish();
    }
}
