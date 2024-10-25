package a.a.a;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Rect;
import android.text.Editable;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sketchware.remod.R;
import com.sketchware.remod.databinding.MakeBlockLayoutBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import mod.hasrat.lib.BaseTextWatcher;
import mod.hey.studios.moreblock.MoreblockValidator;
import mod.hey.studios.moreblock.ReturnMoreblockManager;
import mod.hey.studios.util.Helper;
import mod.jbk.util.BlockUtil;

@SuppressLint("ViewConstructor")
public class dt extends LinearLayout {

    private static final Pattern CUSTOM_VARIABLE_PATTERN = Pattern.compile("[mldb]\\.[a-zA-Z]+");
    private final ArrayList<Pair<String, String>> variablesSpecAndNamePair = new ArrayList<>();
    private boolean customVariableInvalid;
    private Activity activity;
    private gt addVariable;
    private ZB variableNameValidator;
    private ZB labelTextValidator;
    private MoreblockValidator blockNameValidator;
    private Rs block;

    private MakeBlockLayoutBinding binding;

    public dt(Activity activity) {
        super(activity);
        initialize(activity);
    }

    private void initialize(Activity activity) {
        this.activity = activity;
        binding = MakeBlockLayoutBinding.inflate(activity.getLayoutInflater(), this, true);
        initRefresh();
        addVariable = new gt(activity);
        binding.varTypeSpinner.addView(addVariable);
        binding.tvTitleBlockname.setText(Helper.getResString(R.string.logic_editor_more_block_title_name_of_block));
        binding.tvTitleAddVariable.setText(Helper.getResString(R.string.logic_editor_more_block_title_add_variable));
        blockNameValidator = new MoreblockValidator(activity, binding.tiName, uq.b, uq.a(), new ArrayList<>());
        labelTextValidator = new ZB(activity, binding.tiLabel, uq.b, uq.a(), new ArrayList<>());
        variableNameValidator = new ZB(activity, binding.tiVariableName, uq.b, uq.a(), new ArrayList<>());
        binding.tiName.setHint(Helper.getResString(R.string.logic_editor_more_block_hint_enter_new_block_name));
        binding.tiVariableName.setHint(Helper.getResString(R.string.logic_editor_more_block_hint_enter_variable_name));
        binding.tiLabel.setHint(Helper.getResString(R.string.logic_editor_more_block_hint_enter_block_label));
        binding.edName.setPrivateImeOptions("defaultInputmode=english;");
        binding.edLabel.setPrivateImeOptions("defaultInputmode=english;");
        binding.edVariableName.setPrivateImeOptions("defaultInputmode=english;");
        binding.edName.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty() || blockNameValidator.b()) {
                    updateBlockPreview(binding.blockArea, binding.removeArea, block, s.toString(), variablesSpecAndNamePair);
                }
            }
        });

        binding.addVariable.setText(Helper.getResString(R.string.logic_editor_more_block_button_add));
        binding.addVariable.setOnClickListener(view -> {
            if (!mB.a()) {
                if (variableNameValidator.b() && blockNameValidator.b()) {
                    Pair<String, String> variableSpec = addVariable.getSelectedItem();
                    String variableType = variableSpec.first;
                    String fullSpec = variableType;
                    if (!variableSpec.second.isEmpty()) {
                        fullSpec = variableType + "." + variableSpec.second;
                    }

                    variablesSpecAndNamePair.add(new Pair<>(fullSpec, binding.edVariableName.getText().toString()));
                    updateBlockPreview(binding.blockArea, binding.removeArea, block, binding.edName.getText().toString(), variablesSpecAndNamePair);

                    ArrayList<String> reservedVariableNames = new ArrayList<>(Arrays.asList(uq.a()));

                    for (Pair<String, String> variable : variablesSpecAndNamePair) {
                        if (!variable.first.equals("t")) {
                            reservedVariableNames.add(variable.second);
                        }
                    }

                    variableNameValidator.a(reservedVariableNames.toArray(new String[0]));
                    binding.edVariableName.setText("");
                }

            }
        });
        binding.addLabel.setText(Helper.getResString(R.string.logic_editor_more_block_button_add));
        binding.parameter.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                final String s = sequence.toString();

                if (CUSTOM_VARIABLE_PATTERN.matcher(s).matches()) {
                    customVariableInvalid = false;
                } else {
                    customVariableInvalid = !s.isEmpty();
                }
                binding.tiParameter.setError("Invalid format");
                binding.tiParameter.setErrorEnabled(customVariableInvalid);
            }
        });

        binding.add.setOnClickListener(v -> {
            if (!customVariableInvalid && !binding.name.getText().toString().isEmpty() && !binding.parameter.getText().toString().isEmpty()) {
                variablesSpecAndNamePair.add(new Pair<>(binding.parameter.getText().toString(), binding.name.getText().toString()));
                updateBlockPreview(binding.blockArea, binding.removeArea, block, binding.edName.getText().toString(), variablesSpecAndNamePair);
                binding.parameter.setText("");
                binding.name.setText("");

                ArrayList<String> prohibitedVariableNames = new ArrayList<>(Arrays.asList(uq.a()));
                for (Pair<String, String> variable : variablesSpecAndNamePair) {
                    if (!(variable.first).equals("t")) {
                        prohibitedVariableNames.add(variable.second);
                    }
                }
                variableNameValidator.a(prohibitedVariableNames.toArray(new String[0]));
            }
        });
        binding.addLabel.setOnClickListener(v -> {
            if (!mB.a()) {
                if (labelTextValidator.b() && blockNameValidator.b()) {
                    variablesSpecAndNamePair.add(new Pair<>("t", binding.edLabel.getText().toString()));
                    updateBlockPreview(binding.blockArea, binding.removeArea, block, binding.edName.getText().toString(), variablesSpecAndNamePair);
                    binding.edLabel.setText("");
                }
            }
        });
        block = new Rs(activity, 0, "", " ", "definedFunc");
        binding.blockArea.addView(block);
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
        var spec = fullSpec.toString();
        rs.setSpec(spec);
        BlockUtil.loadPreviewBlockVariables(blockArea, rs, spec);
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

            width += (int) wB.a(activity, 4.0f);

            ImageView removeIcon = new ImageView(activity);
            removeIcon.setImageResource(R.drawable.ic_remove_grey600_24dp);
            removeIcon.setScaleType(ScaleType.CENTER_INSIDE);
            removeIcon.setPadding(0, (int) wB.a(activity, 4.0F), 0, (int) wB.a(activity, 4.0F));
            removeIcon.setLayoutParams(new LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT));
            removeArea.addView(removeIcon);
            if (i == 0 && binding.edName.getText().length() > 0) {
                removeIcon.setVisibility(View.INVISIBLE);
                removeIcon.setEnabled(false);
            } else {
                removeIcon.setOnClickListener(v -> {
                    int indexOfChild;
                    if (binding.edName.getText().length() > 0) {
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
        return binding.edName.getText().toString().isEmpty() && variablesSpecAndNamePair.isEmpty();
    }

    public boolean b() {
        if (!binding.edName.getText().toString().isEmpty() && blockNameValidator.b()) {
            return true;
        } else {
            bB.b(getContext(), Helper.getResString(R.string.logic_editor_message_name_requied), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public Pair<String, String> getBlockInformation() {
        String var1 = binding.edName.getText().toString().trim();
        return new Pair<>(ReturnMoreblockManager.injectMbType(var1, var1, getType()), ReturnMoreblockManager.injectMbType(block.T, var1, getType()));
    }

    private String getType() {
        return ReturnMoreblockManager.getMbTypeFromRadioButton(binding.radioMbType);
    }

    private void initRefresh() {
        binding.radioMbType.setOnCheckedChangeListener((radioGroup, i) -> refresh(getType()));
    }

    private void refresh(String type) {
        type = ReturnMoreblockManager.getPreviewType(type);
        Rs var2 = new Rs(activity, 0, "", type, "definedFunc");
        block = var2;
        updateBlockPreview(binding.blockArea, binding.removeArea, var2, binding.edName.getText().toString(), variablesSpecAndNamePair);
    }

    public void setFuncNameValidator(ArrayList<String> var1) {
        blockNameValidator = new MoreblockValidator(activity, binding.tiName, uq.b, uq.a(), var1);
    }

}
