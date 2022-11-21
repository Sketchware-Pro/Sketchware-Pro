package mod.hasrat.control.logic;

import static android.text.TextUtils.isEmpty;
import static com.besome.sketch.SketchApplication.getContext;
import static mod.SketchwareUtil.getDip;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.editor.LogicEditorActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import a.a.a.ZB;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.eC;
import a.a.a.jC;
import a.a.a.uq;
import a.a.a.wB;
import mod.SketchwareUtil;
import mod.hasrat.dialog.SketchDialog;
import mod.hasrat.menu.ExtraMenuBean;
import mod.hey.studios.util.Helper;

public class LogicClickListener implements View.OnClickListener {

    private final eC projectDataManager;
    private final LogicEditorActivity logicEditor;
    private final ProjectFileBean projectFile;
    private final String eventName;
    private final String javaName;

    public LogicClickListener(LogicEditorActivity logicEditor) {
        this.logicEditor = logicEditor;
        projectDataManager = jC.a(logicEditor.B);
        projectFile = logicEditor.M;
        eventName = logicEditor.C + "_" + logicEditor.D;
        javaName = logicEditor.M.getJavaName();
    }

    private ArrayList<String> getUsedVariable(int type) {
        return projectDataManager.e(projectFile.getJavaName(), type);
    }

    private ArrayList<String> getUsedList(int type) {
        return projectDataManager.d(projectFile.getJavaName(), type);
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (!isEmpty(tag)) {
            switch (tag) {
                case "listAddCustom":
                    addCustomList();
                    break;

                case "variableAddNew":
                    addCustomVariable();
                    break;

                case "variableRemove":
                    removeVariable();
                    break;

                case "listRemove":
                    removeList();
                    break;
            }
        }
    }

    private void addCustomVariable() {
        aB dialog = new aB(logicEditor);
        dialog.a(R.drawable.abc_96_color);
        dialog.b("Add a new custom variable");

        LinearLayout root = new LinearLayout(logicEditor);
        root.setOrientation(LinearLayout.VERTICAL);

        TextInputLayout modifierLayout = commonTextInputLayout();
        EditText modifier = commonEditText("private, public or public static (optional)");
        modifierLayout.addView(modifier);
        modifierLayout.setHelperText("Enter modifier e.g. private, public, public static, or empty (package private).");
        modifierLayout.setPadding(0, 0, 0, (int) getDip(8));
        root.addView(modifierLayout);

        TextInputLayout typeLayout = commonTextInputLayout();
        EditText type = commonEditText("Type, e.g. File");
        typeLayout.addView(type);
        root.addView(typeLayout);

        TextInputLayout nameLayout = commonTextInputLayout();
        EditText name = commonEditText("Name, e.g. file");
        nameLayout.addView(name);
        root.addView(nameLayout);

        TextInputLayout initializerLayout = commonTextInputLayout();
        EditText initializer = commonEditText("Initializer, e.g. new File() (optional)");
        initializerLayout.addView(initializer);
        root.addView(initializerLayout);

        ZB validator = new ZB(getContext(), nameLayout, uq.b, uq.a(), projectDataManager.a(projectFile));

        dialog.a(root);
        dialog.b(Helper.getResString(R.string.common_word_add), view -> {
            String variableModifier = modifier.getText().toString();
            variableModifier = isEmpty(variableModifier) ? "" : variableModifier + " ";
            String variableType = type.getText().toString();
            String variableName = name.getText().toString();
            String variableInitializer = initializer.getText().toString();

            boolean validType = !isEmpty(variableType);
            boolean validName = !isEmpty(variableName);
            boolean getsInitialized = !isEmpty(variableInitializer);

            if (validType) {
                typeLayout.setError(null);
            } else {
                if (validName) typeLayout.requestFocus();
                typeLayout.setError("Type can't be empty");
            }

            CharSequence nameError = nameLayout.getError();
            if (nameError == null || "Name can't be empty".contentEquals(nameError)) {
                if (validName) {
                    nameLayout.setError(null);
                } else {
                    nameLayout.requestFocus();
                    nameLayout.setError("Name can't be empty");
                }
            }

            if (validName && validType && validator.b()) {
                String toAdd = variableModifier + variableType + " " + variableName;
                if (getsInitialized) {
                    toAdd += " = " + variableInitializer;
                }
                logicEditor.b(6, toAdd);
                dialog.dismiss();
            }
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        modifierLayout.requestFocus();
    }

    private void removeVariable() {
        SketchDialog dialog = new SketchDialog(logicEditor);
        dialog.setTitle(Helper.getResString(R.string.logic_editor_title_remove_variable));
        dialog.setIcon(R.drawable.delete_96);

        RecyclerView recyclerView = new RecyclerView(logicEditor);
        recyclerView.setLayoutManager(new LinearLayoutManager(null));

        List<RemoveAdapter.Item> data = new LinkedList<>();
        RemoveAdapter adapter = new RemoveAdapter(logicEditor, data);
        recyclerView.setAdapter(adapter);

        ArrayList<String> bools = getUsedVariable(ExtraMenuBean.VARIABLE_TYPE_BOOLEAN);
        for (int i = 0, boolsSize = bools.size(); i < boolsSize; i++) {
            String booleanName = bools.get(i);
            if (i == 0) data.add(new RemoveAdapter.Item("Boolean (" + boolsSize + ")"));
            data.add(new RemoveAdapter.Item(booleanName,
                    logicEditor.o.c(booleanName) || projectDataManager.c(javaName, booleanName, eventName),
                    R.string.logic_editor_message_currently_used_variable));
        }

        ArrayList<String> numbers = getUsedVariable(ExtraMenuBean.VARIABLE_TYPE_NUMBER);
        for (int i = 0, intsSize = numbers.size(); i < intsSize; i++) {
            String number = numbers.get(i);
            if (i == 0) data.add(new RemoveAdapter.Item("Number (" + intsSize + ")"));
            data.add(new RemoveAdapter.Item(number,
                    logicEditor.o.c(number) || projectDataManager.c(javaName, number, eventName),
                    R.string.logic_editor_message_currently_used_variable));
        }

        ArrayList<String> strs = getUsedVariable(ExtraMenuBean.VARIABLE_TYPE_STRING);
        for (int i = 0, strsSize = strs.size(); i < strsSize; i++) {
            String string = strs.get(i);
            if (i == 0) data.add(new RemoveAdapter.Item("String (" + strsSize + ")"));
            data.add(new RemoveAdapter.Item(string,
                    logicEditor.o.c(string) || projectDataManager.c(javaName, string, eventName),
                    R.string.logic_editor_message_currently_used_variable));
        }

        ArrayList<String> maps = getUsedVariable(ExtraMenuBean.VARIABLE_TYPE_MAP);
        for (int i = 0, mapSize = maps.size(); i < mapSize; i++) {
            String map = maps.get(i);
            if (i == 0) data.add(new RemoveAdapter.Item("Map (" + mapSize + ")"));
            data.add(new RemoveAdapter.Item(map,
                    logicEditor.o.c(map) || projectDataManager.c(javaName, map, eventName),
                    R.string.logic_editor_message_currently_used_variable));
        }

        ArrayList<String> vars = getUsedVariable(5);
        vars.addAll(getUsedVariable(6));
        for (int i = 0, varsSize = vars.size(); i < varsSize; i++) {
            String var = vars.get(i);
            if (i == 0) data.add(new RemoveAdapter.Item("Custom Variable (" + varsSize + ")"));
            data.add(new RemoveAdapter.Item(var,
                    logicEditor.o.c(var) || projectDataManager.c(javaName, var, eventName),
                    R.string.logic_editor_message_currently_used_variable));
        }

        dialog.setView(recyclerView);
        dialog.setPositiveButton(Helper.getResString(R.string.common_word_remove), v -> {
            for (RemoveAdapter.Item item : data) {
                if (item.type == RemoveAdapter.Item.TYPE_ITEM && item.isChecked) {
                    logicEditor.m(item.text);
                }
            }
            dialog.dismiss();
        });
        dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), null);
        dialog.show();
    }

    private void addCustomList() {
        aB dialog = new aB(logicEditor);
        dialog.a(R.drawable.add_96_blue);
        dialog.b("Add a new custom List");

        LinearLayout root = new LinearLayout(logicEditor);
        root.setOrientation(LinearLayout.VERTICAL);

        TextInputLayout typeLayout = commonTextInputLayout();
        EditText type = commonEditText("Type, e.g. ArrayList<Data>");
        typeLayout.addView(type);

        TextInputLayout nameLayout = commonTextInputLayout();
        EditText name = commonEditText("Name, e.g. dataList");
        nameLayout.addView(name);

        root.addView(typeLayout);
        root.addView(nameLayout);

        ZB validator = new ZB(getContext(), nameLayout, uq.b, uq.a(), projectDataManager.a(projectFile));

        dialog.a(root);
        dialog.b(Helper.getResString(R.string.common_word_add), view -> {
            String variableType = type.getText().toString();
            String variableName = name.getText().toString();

            boolean validType = !isEmpty(variableType);
            boolean validName = !isEmpty(variableName);

            if (validType) {
                typeLayout.setError(null);
            } else {
                if (validName) typeLayout.requestFocus();
                typeLayout.setError("Type can't be empty");
            }

            CharSequence nameError = nameLayout.getError();
            if (nameError == null || "Name can't be empty".contentEquals(nameError)) {
                if (validName) {
                    nameLayout.setError(null);
                } else {
                    nameLayout.requestFocus();
                    nameLayout.setError("Name can't be empty");
                }
            }

            if (validType && validName && validator.b()) {
                logicEditor.a(4, variableType + " " + variableName + " = new ArrayList<>()");
                dialog.dismiss();
            }
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        typeLayout.requestFocus();
    }

    private void removeList() {
        aB dialog = new aB(logicEditor);
        dialog.b(Helper.getResString(R.string.logic_editor_title_remove_list));
        dialog.a(R.drawable.delete_96);
        View var2 = wB.a(logicEditor, R.layout.property_popup_selector_single);
        ViewGroup viewGroup = var2.findViewById(R.id.rg_content);

        ArrayList<String> listNumbers = getUsedList(ExtraMenuBean.LIST_TYPE_NUMBER);
        for (int i = 0, listIntSize = listNumbers.size(); i < listIntSize; i++) {
            if (i == 0) viewGroup.addView(commonTextView("List Integer (" + listIntSize + ")"));
            viewGroup.addView(getRemoveListCheckBox(listNumbers.get(i)));
        }

        ArrayList<String> listStrs = getUsedList(ExtraMenuBean.LIST_TYPE_STRING);
        for (int i = 0, listStrSize = listStrs.size(); i < listStrSize; i++) {
            if (i == 0) viewGroup.addView(commonTextView("List String (" + listStrSize + ")"));
            viewGroup.addView(getRemoveListCheckBox(listStrs.get(i)));
        }

        ArrayList<String> listMaps = getUsedList(ExtraMenuBean.LIST_TYPE_MAP);
        for (int i = 0, listMapSize = listMaps.size(); i < listMapSize; i++) {
            if (i == 0) viewGroup.addView(commonTextView("List Map (" + listMapSize + ")"));
            viewGroup.addView(getRemoveListCheckBox(listMaps.get(i)));
        }

        ArrayList<String> listCustom = getUsedList(4);
        for (int i = 0, listCustomSize = listCustom.size(); i < listCustomSize; i++) {
            if (i == 0) viewGroup.addView(commonTextView("List Custom (" + listCustomSize + ")"));
            viewGroup.addView(getRemoveListCheckBox(listCustom.get(i)));
        }

        dialog.a(var2);
        dialog.b(Helper.getResString(R.string.common_word_remove), view -> {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                if (viewGroup.getChildAt(i) instanceof CheckBox) {
                    CheckBox list = (CheckBox) viewGroup.getChildAt(i);
                    String listName = list.getText().toString();

                    if (list.isChecked()) {
                        // Since an in-use List can't be checked, just remove it
                        logicEditor.l(listName);
                    }
                }
            }
            dialog.dismiss();
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private TextInputLayout commonTextInputLayout() {
        TextInputLayout textInputLayout = new TextInputLayout(logicEditor);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(
                (int) getDip(8),
                0,
                (int) getDip(8),
                0
        );
        textInputLayout.setLayoutParams(layoutParams);
        return textInputLayout;
    }

    private TextView commonTextView(String text) {
        TextView textView = new TextView(logicEditor);
        textView.setText(text);
        textView.setPadding(
                (int) getDip(2),
                (int) getDip(4),
                (int) getDip(4),
                (int) getDip(4)
        );
        textView.setTextSize(14f);
        return textView;
    }

    private EditText commonEditText(String hint) {
        EditText editText = new EditText(logicEditor);
        editText.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        editText.setPadding(
                (int) getDip(4),
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8)
        );
        editText.setTextSize(16f);
        editText.setTextColor(0xff000000);
        editText.setHint(hint);
        editText.setHintTextColor(0xff607d8b);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setPrivateImeOptions("defaultInputmode=english;");
        return editText;
    }

    private CheckBox getRemoveVariableCheckBox(String variableName) {
        return commonRemoveCheckBox(
                logicEditor.o.c(variableName) || projectDataManager.c(javaName, variableName, eventName),
                variableName,
                R.string.logic_editor_message_currently_used_variable);
    }

    private CheckBox getRemoveListCheckBox(String listName) {
        return commonRemoveCheckBox(
                logicEditor.o.b(listName) || projectDataManager.b(javaName, listName, eventName),
                listName,
                R.string.logic_editor_message_currently_used_list);
    }

    private CheckBox commonRemoveCheckBox(boolean hasUses, String name, int toastMessageId) {
        CheckBox checkBox = new CheckBox(logicEditor);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int) getDip(40),
                1);
        checkBox.setLayoutParams(params);
        checkBox.setText(name);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isChecked()) {
                if (hasUses) {
                    SketchwareUtil.toastError(Helper.getResString(toastMessageId), bB.TOAST_WARNING);
                    buttonView.setChecked(false);
                }
            }
        });
        return checkBox;
    }

    private static class RemoveAdapter extends RecyclerView.a<RemoveAdapter.ViewHolder> {

        private final Context context;
        private final List<Item> data;

        private RemoveAdapter(Context context, List<Item> data) {
            this.context = context;
            this.data = data;
        }

        @Override
        // RecyclerView.Adapter#getItemCount()
        public int a() {
            return data.size();
        }

        @Override
        // RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)
        public ViewHolder b(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.logic_editor_remove_item, parent, false));
        }

        @Override
        // RecyclerView.Adapter#onBindViewHolder(VH, int)
        public void b(ViewHolder holder, int position) {
            Item item = data.get(position);

            if (item.type == Item.TYPE_TITLE) {
                holder.title.setVisibility(View.VISIBLE);
                holder.title.setText(item.text);
                holder.checkBox.setVisibility(View.GONE);
            } else {
                holder.title.setVisibility(View.GONE);
                holder.checkBox.setVisibility(View.VISIBLE);
                holder.checkBox.setText(item.text);
                holder.checkBox.setChecked(item.isChecked);

                holder.checkBox.setOnClickListener(v -> {
                    boolean isChecked = holder.checkBox.isChecked();
                    item.isChecked = isChecked;
                    if (item.type == Item.TYPE_ITEM && isChecked) {
                        //noinspection ConstantConditions Item#isInUse can't be null if Item#type is Item#TYPE_ITEM
                        if (item.isInUse) {
                            //noinspection ConstantConditions Item#inUseMessage can't be null if Item#type is Item#TYPE_ITEM
                            SketchwareUtil.toastError(Helper.getResString(item.inUseMessage), bB.TOAST_WARNING);
                            holder.checkBox.performClick();
                        }
                    }
                });
            }
        }

        private static class ViewHolder extends RecyclerView.v {
            public final TextView title;
            public final CheckBox checkBox;

            public ViewHolder(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.title);
                checkBox = itemView.findViewById(R.id.item);
            }
        }

        private static class Item {
            public static final int TYPE_TITLE = 0;
            public static final int TYPE_ITEM = 1;

            private final int type;
            private final String text;
            private final Boolean isInUse;
            @StringRes
            private final Integer inUseMessage;

            private volatile boolean isChecked = false;

            public Item(String title) {
                type = TYPE_TITLE;
                text = title;
                isInUse = null;
                inUseMessage = null;
            }

            public Item(String itemName, boolean isInUse, @StringRes int inUseMessage) {
                type = TYPE_ITEM;
                text = itemName;
                this.isInUse = isInUse;
                this.inUseMessage = inUseMessage;
            }

            public int getType() {
                return type;
            }

            public String getText() {
                return text;
            }
        }
    }
}
