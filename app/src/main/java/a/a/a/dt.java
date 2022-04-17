package a.a.a;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Rect;
import android.text.Editable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import mod.hey.studios.moreblock.MoreblockValidator;
import mod.hey.studios.moreblock.ReturnMoreblockManager;
import mod.w3wide.lib.BaseTextWatcher;

@SuppressLint("ViewConstructor")
public class dt extends LinearLayout {

    public static boolean err = false;

    private Activity activity;
    private RelativeLayout blockContainer;
    private LinearLayout removeArea;
    private TextInputLayout tilBlockName;
    private EditText blockName;
    private EditText labelText;
    private EditText variableName;
    private gt addVariable;
    private final ArrayList<Pair<String, String>> l = new ArrayList<>();
    private ZB variableNameValidator;
    private ZB labelTextValidator;
    private MoreblockValidator blockNameValidator;
    private Rs block;
    private RadioGroup radio_mb_type;

    public dt(Activity activity) {
        super(activity);
        initialize(activity);
    }

    public final int a(TextView textView) {
        Rect rect = new Rect();
        textView.getPaint().getTextBounds(textView.getText().toString(), 0, textView.getText().length(), rect);
        return rect.width();
    }

    private void initialize(Activity activity) {
        this.activity = activity;
        LayoutInflater.from(activity).inflate(R.layout.make_block_layout, this);

        radio_mb_type = findViewById(R.id.radio_mb_type);
        initRefresh();

        LinearLayout addVariableContainer = findViewById(R.id.var_type_spinner);
        blockContainer = findViewById(R.id.block_area);
        removeArea = findViewById(R.id.remove_area);
        addVariable = new gt(activity);
        addVariableContainer.addView(addVariable);
        tilBlockName = findViewById(R.id.ti_name);
        TextInputLayout tilLabelText = findViewById(R.id.ti_label);
        TextInputLayout tilVariableName = findViewById(R.id.ti_variable_name);
        ((TextView) findViewById(R.id.tv_title_blockname)).setText(xB.b().a(activity, R.string.logic_editor_more_block_title_name_of_block));
        ((TextView) findViewById(R.id.tv_title_add_variable)).setText(xB.b().a(activity, R.string.logic_editor_more_block_title_add_variable));
        blockNameValidator = new MoreblockValidator(activity, tilBlockName, uq.b, uq.a(), new ArrayList<>());
        labelTextValidator = new ZB(activity, tilLabelText, uq.b, uq.a(), new ArrayList<>());
        variableNameValidator = new ZB(activity, tilVariableName, uq.b, uq.a(), new ArrayList<>());
        blockName = findViewById(R.id.ed_name);
        labelText = findViewById(R.id.ed_label);
        variableName = findViewById(R.id.ed_variable_name);
        tilBlockName.setHint(xB.b().a(activity, R.string.logic_editor_more_block_hint_enter_new_block_name));
        tilVariableName.setHint(xB.b().a(activity, R.string.logic_editor_more_block_hint_enter_variable_name));
        tilLabelText.setHint(xB.b().a(activity, R.string.logic_editor_more_block_hint_enter_block_label));
        blockName.setPrivateImeOptions("defaultInputmode=english;");
        labelText.setPrivateImeOptions("defaultInputmode=english;");
        variableName.setPrivateImeOptions("defaultInputmode=english;");
        blockName.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable var1) {
                if (var1.toString().equals("") || blockNameValidator.b()) {
                    a(blockContainer, removeArea, block, var1.toString(), l);
                }
            }
        });
        Button var4 = findViewById(R.id.add_variable);
        var4.setText(xB.b().a(activity, R.string.logic_editor_more_block_button_add));
        var4.setOnClickListener(view -> {
            if (!mB.a()) {
                if (variableNameValidator.b() && blockNameValidator.b()) {
                    Pair<String, String> var21 = addVariable.getSelectedItem();
                    String var3 = var21.first;
                    String var41 = var3;
                    if (var21.second.length() > 0) {
                        var41 = var3 + "." + var21.second;
                    }

                    l.add(new Pair<>(var41, variableName.getText().toString()));
                    a(blockContainer, removeArea, block, blockName.getText().toString(), l);
                    ArrayList<String> var6 = new ArrayList<>(Arrays.asList(uq.a()));

                    for (Pair<String, String> stringStringPair : l) {
                        if (!stringStringPair.first.equals("t")) {
                            var6.add(stringStringPair.second);
                        }
                    }

                    variableNameValidator.a(var6.toArray(new String[0]));
                    variableName.setText("");
                }

            }
        });
        Button addLabel = findViewById(R.id.add_label);
        addLabel.setText(xB.b().a(activity, R.string.logic_editor_more_block_button_add));

        final EditText parameter = findViewById(R.id.parameter);
        final EditText name = findViewById(R.id.name);
        final Button add = findViewById(R.id.add);

        final TextInputLayout p_input = (TextInputLayout) parameter.getParent().getParent();
        p_input.setHint("Parameter: m.name");
        final TextInputLayout n_input = (TextInputLayout) name.getParent().getParent();
        n_input.setHint("Variable name");
        parameter.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                final String s = sequence.toString();

                if (s.matches("[mldb]\\.[a-zA-Z]+")) {
                    err = false;
                } else {
                    err = !s.equals("");
                }
                p_input.setError("Invalid format");
                p_input.setErrorEnabled(err);
            }
        });

        add.setOnClickListener(v -> {
            if (!err && !name.getText().toString().equals("") && !parameter.getText().toString().equals("")) {
                l.add(new Pair<>(parameter.getText().toString(), name.getText().toString()));
                a(blockContainer, removeArea, block, blockName.getText().toString(), l);
                parameter.setText("");
                name.setText("");
                ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(uq.a()));
                for (Pair<String, String> next : l) {
                    if (!(next.first).equals("t")) {
                        arrayList.add(next.second);
                    }
                }
                variableNameValidator.a(arrayList.toArray(new String[0]));
            }
        });
        addLabel.setOnClickListener(view -> {
            if (!mB.a()) {
                if (labelTextValidator.b() && blockNameValidator.b()) {
                    l.add(new Pair<>("t", labelText.getText().toString()));
                    a(blockContainer, removeArea, block, blockName.getText().toString(), l);
                    labelText.setText("");
                }
            }
        });
        block = new Rs(activity, 0, "", " ", "definedFunc");
        blockContainer.addView(block);
    }

    public final void a(ViewGroup blockArea, ViewGroup removeArea, Rs var3, String var4, ArrayList<Pair<String, String>> var5) {
        blockArea.removeAllViews();
        blockArea.addView(var3);
        Iterator<Pair<String, String>> var6 = var5.iterator();

        String var7;
        String var10;
        StringBuilder var15;
        for (var7 = var4; var6.hasNext(); var7 = var15.toString()) {
            Pair<String, String> var8;
            idk:
            {
                var8 = var6.next();
                StringBuilder var9;
                switch (var8.first) {
                    case "b":
                        var9 = new StringBuilder();
                        var9.append(var7);
                        var10 = " %b.";
                        var15 = var9;
                        break;
                    case "d":
                        var9 = new StringBuilder();
                        var9.append(var7);
                        var10 = " %d.";
                        var15 = var9;
                        break;
                    case "s":
                        var9 = new StringBuilder();
                        var9.append(var7);
                        var10 = " %s.";
                        var15 = var9;
                        break;
                    default:
                        if (var8.first.length() > 2 && var8.first.contains(".")) {
                            StringBuilder var19 = new StringBuilder();
                            var19.append(var7);
                            var19.append(" %");
                            var19.append(var8.first);
                            var19.append(".");
                            var15 = var19;
                            break idk;
                        }

                        var9 = new StringBuilder();
                        var9.append(var7);
                        var10 = " ";
                        var15 = var9;
                        break;
                }

                var15.append(var10);
            }

            var15.append(var8.second);
        }

        var3.setSpec(var7);
        int var11 = var5.size();
        int var12 = 0;

        int var13;
        int var14;
        for (var13 = var12; var12 < var11; var13 = var14) {
            lol:
            {
                Pair<String, String> var16 = var5.get(var12);
                Rs var17;
                if (var16.first.equals("b")) {
                    var17 = new Rs(activity, var5.indexOf(var16) + 1, var16.second, "b", "getArg");
                } else if (var16.first.equals("d")) {
                    var17 = new Rs(activity, var5.indexOf(var16) + 1, var16.second, "d", "getArg");
                } else {
                    if (!var16.first.equals("s")) {
                        var14 = var13;
                        if (var16.first.length() > 2) {
                            var10 = var16.first;
                            String var18 = var10.substring(var10.indexOf(".") + 1);
                            var10 = kq.a(var18);
                            var17 = new Rs(activity, var5.indexOf(var16) + 1, var16.second, var10, kq.b(var18), "getArg");
                            blockArea.addView(var17);
                            var3.a((Ts) var3.V.get(var13), var17);
                            var14 = var13 + 1;
                        }
                        break lol;
                    }

                    var17 = new Rs(activity, var5.indexOf(var16) + 1, var16.second, "s", "getArg");
                }

                blockArea.addView(var17);
                var3.a((Ts) var3.V.get(var13), var17);
                var14 = var13 + 1;
            }

            ++var12;
        }

        var3.k();
        removeArea.removeAllViews();
        var13 = var3.ka.size();

        for (var12 = 0; var12 < var13; ++var12) {
            View var20 = var3.ka.get(var12);
            if (var3.la.get(var12).equals("label")) {
                var14 = a((TextView) var20);
            } else {
                var14 = 0;
            }

            if (var20 instanceof Rs) {
                var14 = ((Rs) var20).getWidthSum();
            }

            var14 = (int) ((float) var14 + wB.a(activity, 4.0F));
            ImageView removeIcon = new ImageView(activity);
            removeIcon.setImageResource(R.drawable.ic_remove_grey600_24dp);
            removeIcon.setScaleType(ScaleType.CENTER_INSIDE);
            removeIcon.setPadding(0, (int) wB.a(activity, 4.0F), 0, (int) wB.a(activity, 4.0F));
            removeIcon.setLayoutParams(new LayoutParams(var14, -1));
            removeArea.addView(removeIcon);
            if (var12 == 0 && blockName.getText().length() > 0) {
                removeIcon.setVisibility(View.INVISIBLE);
                removeIcon.setEnabled(false);
            } else {
                removeIcon.setOnClickListener(view -> {
                    int indexOfChild;
                    if (blockName.getText().length() > 0) {
                        indexOfChild = removeArea.indexOfChild(view) - 1;
                    } else {
                        indexOfChild = removeArea.indexOfChild(view);
                    }

                    var5.remove(indexOfChild);
                    ArrayList<String> reservedTypes = new ArrayList<>(Arrays.asList(uq.a()));

                    for (Pair<String, String> stringStringPair : var5) {
                        if (!stringStringPair.first.equals("t")) {
                            reservedTypes.add(stringStringPair.second);
                        }
                    }

                    variableNameValidator.a(reservedTypes.toArray(new String[0]));
                    a(blockArea, removeArea, var3, var4, var5);
                });
            }
        }

    }

    public boolean a() {
        return blockName.getText().toString().isEmpty() && l.size() == 0;
    }

    public boolean b() {
        if (!blockName.getText().toString().isEmpty() && blockNameValidator.b()) {
            return true;
        } else {
            bB.b(getContext(), xB.b().a(getContext(), R.string.logic_editor_message_name_requied), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public Pair<String, String> getBlockInformation() {
        String var1 = blockName.getText().toString().trim();
        return new Pair<>(ReturnMoreblockManager.injectMbType(var1, var1, getType()), ReturnMoreblockManager.injectMbType(block.T, var1, getType()));
    }

    public String getType() {
        return ReturnMoreblockManager.getMbTypeFromRadioButton(radio_mb_type);
    }

    public void initRefresh() {
        radio_mb_type.setOnCheckedChangeListener((radioGroup, i) -> refresh(getType()));
    }

    public void refresh(String var1) {
        var1 = ReturnMoreblockManager.getPreviewType(var1);
        Rs var2 = new Rs(activity, 0, "", var1, "definedFunc");
        block = var2;
        a(blockContainer, removeArea, var2, blockName.getText().toString(), l);
    }

    public void setFuncNameValidator(ArrayList<String> var1) {
        blockNameValidator = new MoreblockValidator(activity, tilBlockName, uq.b, uq.a(), var1);
    }

}
