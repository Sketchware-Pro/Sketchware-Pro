package mod.hilal.saif.activities.tools;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
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

import java.util.ArrayList;
import java.util.HashMap;
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
    private final ArrayList<String> temp = new ArrayList<>();
    private AlertDialog.Builder block_type_dialog;
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
    private TextInputLayout name_lay;
    private TextView page_title;
    private String pallet_colour = "";
    private LinearLayout parameters_hold;
    private String path = "";
    private MaterialButton save;
    private EditText spec;
    private EditText spec2;
    private TextInputLayout spec2_lay;
    private HashMap<String, Object> tempMap = new HashMap<>();
    private double tempN = 0.0d;
    private EditText type;
    private EditText typename;
    private ScrollView vscroll1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(2131427814);
        initialize(savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {
        vscroll1 = findViewById(2131232383);
        ImageView back_icon = findViewById(2131232560);
        page_title = findViewById(2131231582);
        name_lay = findViewById(2131232563);
        name = findViewById(2131231561);
        LinearLayout select_type = findViewById(2131232565);
        type = findViewById(2131232566);
        typename = findViewById(2131232568);
        spec = findViewById(2131232553);
        hscroll1 = findViewById(2131232570);
        spec2_lay = findViewById(2131232571);
        LinearLayout color_selector = findViewById(2131232573);
        colour = findViewById(2131230904);
        parameters_hold = findViewById(2131232576);
        spec2 = findViewById(2131232577);
        code = findViewById(2131232578);
        MaterialButton cancel = findViewById(2131232351);
        save = findViewById(2131232528);
        LinearLayout reset = findViewById(2131232581);
        block_type_dialog = new AlertDialog.Builder(this);
        back_icon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        Helper.applyRippleToToolbarView(back_icon);
        name.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String charSequence2 = s.toString();
                if (!id_detector.contains(charSequence2)) {
                    name_lay.setErrorEnabled(false);
                    save.setEnabled(true);
                } else if (!mod.equals("edit")) {
                    name_lay.setErrorEnabled(true);
                    name_lay.setError("Block name already in use");
                    save.setEnabled(false);
                } else if (!charSequence2.equals(blocksList.get(n).get("name").toString())) {
                    name_lay.setErrorEnabled(true);
                    name_lay.setError("Block name already in use");
                    save.setEnabled(false);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
        select_type.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                temp.clear();
                tempN = 0.0d;
                temp.add("Regular block (regular)");
                temp.add("if block (c)");
                temp.add("if-else block (e)");
                temp.add("String (s)");
                temp.add("Boolean (b)");
                temp.add("Number (d)");
                temp.add("Variable (v)");
                temp.add("Map (a)");
                temp.add("stop block (f)");
                temp.add("List (l)");
                temp.add("Component (p)");
                temp.add("Header (h)");
                block_type_dialog.setTitle("Block type")
                        .setSingleChoiceItems(temp.toArray(new String[0]), -1, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                tempN = which;
                            }
                        })
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (tempN == 0.0d) {
                                    type.setText("regular");
                                } else {
                                    type.setText(temp.get((int) tempN).split("\\(")[1].split("\\)")[0]);
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create().show();
            }
        });
        type.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("e")) {
                    AutoTransition autoTransition = new AutoTransition();
                    autoTransition.setDuration(300L);
                    TransitionManager.beginDelayedTransition(vscroll1, autoTransition);
                    spec2_lay.setVisibility(View.VISIBLE);
                } else {
                    AutoTransition autoTransition2 = new AutoTransition();
                    autoTransition2.setDuration(300L);
                    TransitionManager.beginDelayedTransition(vscroll1, autoTransition2);
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
                        spec.getEditableText().setSpan(new ForegroundColorSpan(-1), matcher.start(), matcher.end(), 33);
                    } catch (Exception ignored) {
                    }
                    try {
                        spec.getEditableText().setSpan(new BackgroundColorSpan(402653184), matcher.start(), matcher.end(), 33);
                    } catch (Exception ignored) {
                    }
                    try {
                        spec.getEditableText().setSpan(new RelativeSizeSpan(-5.0f), matcher.start(), matcher.end(), 33);
                    } catch (Exception ignored) {
                    }
                    try {
                        spec.getEditableText().setSpan(new StyleSpan(1), matcher.start(), matcher.end(), 33);
                    } catch (Exception ignored) {
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
        color_selector.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                View inflate = getLayoutInflater().inflate(2131427373, null);
                Zx zx = new Zx(inflate, BlocksManagerCreatorActivity.this, 0, true, false);
                zx.a(new PCP(colour));
                zx.setAnimationStyle(2130771968);
                zx.showAtLocation(inflate, 17, 0, 0);
            }
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
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                colour.setText(pallet_colour);
            }
        });
        spec.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case 0:
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case 1:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                v.onTouchEvent(event);
                return true;
            }
        });
    }

    private void initializeLogic() {
        _inputsProp();
        _addParams();
        _receiveIntents();
        _setHighlighter(code);
    }

    private View _k(final String str, String str2) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.0f));
        textView.setPadding((int) SketchwareUtil.getDip(8), (int) SketchwareUtil.getDip(0), (int) SketchwareUtil.getDip(8), (int) SketchwareUtil.getDip(0));
        textView.setTextColor(Color.parseColor("#006064"));
        textView.setText(str2);
        textView.setTextSize(14.0f);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                StringBuilder sb = new StringBuilder(spec.getText().toString());
                int selectionStart = spec.getSelectionStart();
                sb.insert(selectionStart, str);
                spec.setText(sb);
                spec.setSelection(selectionStart + str.length());
            }
        });
        return textView;
    }

    private void _inputsProp() {
        name.setInputType(524288);
        name.setSingleLine(true);
        type.setInputType(524288);
        type.setSingleLine(true);
        typename.setInputType(524288);
        typename.setSingleLine(true);
        spec.setInputType(524288);
        spec.setSingleLine(true);
        colour.setInputType(524288);
        colour.setSingleLine(true);
        spec2.setInputType(524288);
        spec2.setSingleLine(true);
        code.setInputType(524288);
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
        name.setText(blocksList.get(position).get("name").toString());
        if (blocksList.get(position).get("type").toString().equals(" ")) {
            type.setText("regular");
        } else {
            type.setText(blocksList.get(position).get("type").toString());
        }
        if (blocksList.get(position).containsKey("typeName")) {
            typename.setText(blocksList.get(position).get("typeName").toString());
        }
        spec.setText(blocksList.get(position).get("spec").toString());
        if (blocksList.get(position).containsKey("spec2")) {
            spec2.setText(blocksList.get(position).get("spec2").toString());
        }
        if (blocksList.get(position).containsKey("color")) {
            colour.setText(blocksList.get(position).get("color").toString());
        } else {
            colour.setText(pallet_colour);
        }
        code.setText(blocksList.get(position).get("code").toString());
    }

    private void _getList() {
        try {
            blocksList = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
            for (int i = 0; i < blocksList.size(); i++) {
                id_detector.add(blocksList.get(i).get("name").toString());
            }
        } catch (Exception ignored) {
        }
    }

    private void _updateBlockSpec(String specId, String color) {
        switch (specId) {
            case " ":
            case "regular":
                spec.setBackgroundResource(2131166371);
                break;

            case "b":
                spec.setBackgroundResource(2131166369);
                break;

            case "c":
            case "e":
                spec.setBackgroundResource(2131166374);
                break;

            case "d":
                spec.setBackgroundResource(2131166370);
                break;

            case "f":
                spec.setBackgroundResource(2131166372);
                break;

            default:
                spec.setBackgroundResource(2131166373);
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
        tempMap = new HashMap<>();
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

    private void _setHighlighter(Edittext text) {
      
      SimpleHighlighter codeview = new SimpleHighlighter(text);
    
       }


    private void _insertBlockAt(int position) {
        tempMap = new HashMap<>();
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
        tempMap.put("palette", blocksList.get(position).get("palette").toString());
        blocksList.add(position, tempMap);
        FileUtil.writeFile(path, new Gson().toJson(blocksList));
        SketchwareUtil.toast("Saved");
        finish();
    }

    private void _editBlock(int position) {
        tempMap = new HashMap<>();
        tempMap = blocksList.get(position);
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
