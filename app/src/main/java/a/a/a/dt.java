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

import mod.hasrat.lib.BaseTextWatcher;
import mod.hey.studios.moreblock.MoreblockValidator;
import mod.hey.studios.moreblock.ReturnMoreblockManager;
import mod.hey.studios.util.Helper;

@SuppressLint("ViewConstructor")
public class dt extends LinearLayout {

    private final ArrayList<Pair<String, String>> variablesSpecAndNamePair = new ArrayList<>();
    private boolean customVariableInvalid = false;
    private Activity activity;
    private RelativeLayout blockContainer;
    private LinearLayout removeArea;
    private TextInputLayout tilBlockName;
    private EditText blockName;
    private EditText labelText;
    private EditText variableName;
    private gt addVariable;
    private ZB variableNameValidator;
    private ZB labelTextValidator;
    private MoreblockValidator blockNameValidator;
    private Rs block;
    private RadioGroup radio_mb_type;

    public dt(Activity activity) {
        super(activity);
        initialize(activity);
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
        ((TextView) findViewById(R.id.tv_title_blockname)).setText(Helper.getResString(R.string.logic_editor_more_block_title_name_of_block));
        ((TextView) findViewById(R.id.tv_title_add_variable)).setText(Helper.getResString(R.string.logic_editor_more_block_title_add_variable));
        blockNameValidator = new MoreblockValidator(activity, tilBlockName, uq.b, uq.a(), new ArrayList<>());
        labelTextValidator = new ZB(activity, tilLabelText, uq.b, uq.a(), new ArrayList<>());
        variableNameValidator = new ZB(activity, tilVariableName, uq.b, uq.a(), new ArrayList<>());
        blockName = findViewById(R.id.ed_name);
        labelText = findViewById(R.id.ed_label);
        variableName = findViewById(R.id.ed_variable_name);
        tilBlockName.setHint(Helper.getResString(R.string.logic_editor_more_block_hint_enter_new_block_name));
        tilVariableName.setHint(Helper.getResString(R.string.logic_editor_more_block_hint_enter_variable_name));
        tilLabelText.setHint(Helper.getResString(R.string.logic_editor_more_block_hint_enter_block_label));
        blockName.setPrivateImeOptions("defaultInputmode=english;");
        labelText.setPrivateImeOptions("defaultInputmode=english;");
        variableName.setPrivateImeOptions("defaultInputmode=english;");
        blockName.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("") || blockNameValidator.b()) {
                    updateBlockPreview(blockContainer, removeArea, block, s.toString(), variablesSpecAndNamePair);
                }
            }
        });

        Button addVariable = findViewById(R.id.add_variable);
        addVariable.setText(Helper.getResString(R.string.logic_editor_more_block_button_add));
        addVariable.setOnClickListener(view -> {
            if (!mB.a()) {
                if (variableNameValidator.b() && blockNameValidator.b()) {
                    Pair<String, String> variableSpec = this.addVariable.getSelectedItem();
                    String variableType = variableSpec.first;
                    String fullSpec = variableType;
                    if (variableSpec.second.length() > 0) {
                        fullSpec = variableType + "." + variableSpec.second;
                    }

                    variablesSpecAndNamePair.add(new Pair<>(fullSpec, variableName.getText().toString()));
                    updateBlockPreview(blockContainer, removeArea, block, blockName.getText().toString(), variablesSpecAndNamePair);

                    ArrayList<String> reservedVariableNames = new ArrayList<>(Arrays.asList(uq.a()));

                    for (Pair<String, String> variable : variablesSpecAndNamePair) {
                        if (!variable.first.equals("t")) {
                            reservedVariableNames.add(variable.second);
                        }
                    }

                    variableNameValidator.a(reservedVariableNames.toArray(new String[0]));
                    variableName.setText("");
                }

            }
        });
        Button addLabel = findViewById(R.id.add_label);
        addLabel.setText(Helper.getResString(R.string.logic_editor_more_block_button_add));

        final EditText customVariableSpec = findViewById(R.id.parameter);
        final EditText customVariableName = findViewById(R.id.name);
        final Button addCustomVariable = findViewById(R.id.add);

        final TextInputLayout p_input = (TextInputLayout) customVariableSpec.getParent().getParent();
        p_input.setHint("Parameter: m.name");
        final TextInputLayout n_input = (TextInputLayout) customVariableName.getParent().getParent();
        n_input.setHint("Variable name");
        customVariableSpec.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                final String s = sequence.toString();

                if (s.matches("[mldb]\\.[a-zA-Z]+")) {
                    customVariableInvalid = false;
                } else {
                    customVariableInvalid = !s.equals("");
                }
                p_input.setError("Invalid format");
                p_input.setErrorEnabled(customVariableInvalid);
            }
        });

        addCustomVariable.setOnClickListener(v -> {
            if (!customVariableInvalid && !customVariableName.getText().toString().equals("") && !customVariableSpec.getText().toString().equals("")) {
                variablesSpecAndNamePair.add(new Pair<>(customVariableSpec.getText().toString(), customVariableName.getText().toString()));
                updateBlockPreview(blockContainer, removeArea, block, blockName.getText().toString(), variablesSpecAndNamePair);
                customVariableSpec.setText("");
                customVariableName.setText("");

                ArrayList<String> prohibitedVariableNames = new ArrayList<>(Arrays.asList(uq.a()));
                for (Pair<String, String> variable : variablesSpecAndNamePair) {
                    if (!(variable.first).equals("t")) {
                        prohibitedVariableNames.add(variable.second);
                    }
                }
                variableNameValidator.a(prohibitedVariableNames.toArray(new String[0]));
            }
        });
        addLabel.setOnClickListener(v -> {
            if (!mB.a()) {
                if (labelTextValidator.b() && blockNameValidator.b()) {
                    variablesSpecAndNamePair.add(new Pair<>("t", labelText.getText().toString()));
                    updateBlockPreview(blockContainer, removeArea, block, blockName.getText().toString(), variablesSpecAndNamePair);
                    labelText.setText("");
                }
            }
        });
        block = new Rs(activity, 0, "", " ", "definedFunc");
        blockContainer.addView(block);
    }

    private void updateBlockPreview(ViewGroup blockArea, ViewGroup removeArea, Rs rs, String blockName, ArrayList<Pair<String, String>> variables) {
        blockArea.removeAllViews();
        blockArea.addView(rs);

        StringBuilder fullSpec = new StringBuilder(blockName);
        for (Pair<String, String> parameter : variables) {
            String parameterType = parameter.first;
            String parameterName = parameter.second;

            switch (parameter.first) {
                case "b":
                    fullSpec.append(" %b.").append(parameterName);
                    break;

                case "d":
                    fullSpec.append(" %d.").append(parameterName);
                    break;

                case "s":
                    fullSpec.append(" %s.").append(parameterName);
                    break;

                default:
                    if (parameterType.length() > 2 && parameterType.contains(".")) {
                        fullSpec.append(" %").append(parameterType).append(".").append(parameterName);
                    } else {
                        fullSpec.append(" ").append(parameterName);
                    }
                    break;
            }
        }
        rs.setSpec(fullSpec.toString());

        int validParametersI = 0;
        for (int i = 0; i < variables.size(); ) {
            Pair<String, String> parameter = variables.get(i);
            String parameterType = parameter.first;

            Rs block;
            if (parameterType.equals("b")) {
                block = new Rs(activity, variables.indexOf(parameter) + 1, parameter.second, "b", "getArg");
            } else if (parameterType.equals("d")) {
                block = new Rs(activity, variables.indexOf(parameter) + 1, parameter.second, "d", "getArg");
            } else if (parameterType.equals("s")) {
                block = new Rs(activity, variables.indexOf(parameter) + 1, parameter.second, "s", "getArg");
            } else if (parameterType.length() > 2) {
                String customType = parameterType.substring(parameterType.indexOf(".") + 1);
                String letter = kq.a(customType);
                block = new Rs(activity, variables.indexOf(parameter) + 1, parameter.second, letter, kq.b(customType), "getArg");
            } else {
                ++i;
                continue;
            }

            blockArea.addView(block);
            rs.a((Ts) rs.V.get(validParametersI), block);
            ++validParametersI;

            ++i;
        }

        rs.k();
        removeArea.removeAllViews();

        for (int i = 0; i < rs.ka.size(); ++i) {
            View view = rs.ka.get(i);

            int width;
            if (rs.la.get(i).equals("label")) {
                TextView textView = (TextView) view;

                Rect rect = new Rect();
                textView.getPaint().getTextBounds(textView.getText().toString(), 0, textView.getText().length(), rect);
                width = rect.width();
            } else if (view instanceof Rs) {
                width = ((Rs) view).getWidthSum();
            } else {
                width = 0;
            }

            width += wB.a(activity, 4.0f);

            ImageView removeIcon = new ImageView(activity);
            removeIcon.setImageResource(R.drawable.ic_remove_grey600_24dp);
            removeIcon.setScaleType(ScaleType.CENTER_INSIDE);
            removeIcon.setPadding(0, (int) wB.a(activity, 4.0F), 0, (int) wB.a(activity, 4.0F));
            removeIcon.setLayoutParams(new LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT));
            removeArea.addView(removeIcon);
            if (i == 0 && this.blockName.getText().length() > 0) {
                removeIcon.setVisibility(View.INVISIBLE);
                removeIcon.setEnabled(false);
            } else {
                removeIcon.setOnClickListener(v -> {
                    int indexOfChild;
                    if (this.blockName.getText().length() > 0) {
                        indexOfChild = removeArea.indexOfChild(v) - 1;
                    } else {
                        indexOfChild = removeArea.indexOfChild(v);
                    }

                    variables.remove(indexOfChild);
                    ArrayList<String> reservedVariableNames = new ArrayList<>(Arrays.asList(uq.a()));

                    for (Pair<String, String> parameter : variables) {
                        if (!parameter.first.equals("t")) {
                            reservedVariableNames.add(parameter.second);
                        }
                    }

                    variableNameValidator.a(reservedVariableNames.toArray(new String[0]));
                    updateBlockPreview(blockArea, removeArea, rs, blockName, variables);
                });
            }
        }

    }

    public boolean a() {
        return blockName.getText().toString().isEmpty() && variablesSpecAndNamePair.size() == 0;
    }

    public boolean b() {
        if (!blockName.getText().toString().isEmpty() && blockNameValidator.b()) {
            return true;
        } else {
            bB.b(getContext(), Helper.getResString(R.string.logic_editor_message_name_requied), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public Pair<String, String> getBlockInformation() {
        String var1 = blockName.getText().toString().trim();
        return new Pair<>(ReturnMoreblockManager.injectMbType(var1, var1, getType()), ReturnMoreblockManager.injectMbType(block.T, var1, getType()));
    }

    private String getType() {
        return ReturnMoreblockManager.getMbTypeFromRadioButton(radio_mb_type);
    }

    private void initRefresh() {
        radio_mb_type.setOnCheckedChangeListener((radioGroup, i) -> refresh(getType()));
    }

    private void refresh(String type) {
        type = ReturnMoreblockManager.getPreviewType(type);
        Rs var2 = new Rs(activity, 0, "", type, "definedFunc");
        block = var2;
        updateBlockPreview(blockContainer, removeArea, var2, blockName.getText().toString(), variablesSpecAndNamePair);
    }

    public void setFuncNameValidator(ArrayList<String> var1) {
        blockNameValidator = new MoreblockValidator(activity, tilBlockName, uq.b, uq.a(), var1);
    }

}
