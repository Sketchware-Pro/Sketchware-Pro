package mod.hasrat.control.logic;

import static android.text.TextUtils.isEmpty;
import static com.besome.sketch.SketchApplication.getContext;
import static mod.SketchwareUtil.dpToPx;
import static mod.SketchwareUtil.getDip;

import android.content.Context;
import android.text.InputType;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import java.util.function.Function;

import a.a.a.ZB;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.eC;
import a.a.a.jC;
import a.a.a.uq;
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

        List<Item> data = new LinkedList<>();
        RemoveAdapter adapter = new RemoveAdapter(logicEditor, data,
                variableName -> logicEditor.o.c(variableName) || projectDataManager.c(javaName, variableName, eventName));
        recyclerView.setAdapter(adapter);

        List<Pair<List<Integer>, String>> variableTypes = List.of(
                new Pair<>(List.of(ExtraMenuBean.VARIABLE_TYPE_BOOLEAN), "Boolean (%d)"),
                new Pair<>(List.of(ExtraMenuBean.VARIABLE_TYPE_NUMBER), "Number (%d)"),
                new Pair<>(List.of(ExtraMenuBean.VARIABLE_TYPE_STRING), "String (%d)"),
                new Pair<>(List.of(ExtraMenuBean.VARIABLE_TYPE_MAP), "Map (%d)"),
                new Pair<>(List.of(5, 6), "Custom Variable (%d)")
        );

        for (Pair<List<Integer>, String> variableType : variableTypes) {
            List<String> variableTypeInstances = new LinkedList<>();

            List<Integer> first = variableType.first;
            for (int i = 0; i < first.size(); i++) {
                Integer type = first.get(i);

                if (i == 0) {
                    variableTypeInstances = getUsedVariable(type);
                } else {
                    variableTypeInstances.addAll(getUsedVariable(type));
                }
            }

            for (int i = 0, size = variableTypeInstances.size(); i < size; i++) {
                String instanceName = variableTypeInstances.get(i);

                if (i == 0) data.add(new Item(String.format(variableType.second, size)));
                data.add(new Item(instanceName, R.string.logic_editor_message_currently_used_variable));
            }
        }

        dialog.setView(recyclerView);
        dialog.setPositiveButton(Helper.getResString(R.string.common_word_remove), v -> {
            for (Item item : data) {
                if (item.type == Item.TYPE_ITEM && item.isChecked) {
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

        RecyclerView recyclerView = new RecyclerView(logicEditor);
        recyclerView.setLayoutManager(new LinearLayoutManager(null));

        List<Item> data = new LinkedList<>();
        RemoveAdapter adapter = new RemoveAdapter(logicEditor, data,
                listName -> logicEditor.o.b(listName) || projectDataManager.b(javaName, listName, eventName));
        recyclerView.setAdapter(adapter);

        List<Pair<Integer, String>> listTypes = List.of(
                new Pair<>(ExtraMenuBean.LIST_TYPE_NUMBER, "List Integer (%d)"),
                new Pair<>(ExtraMenuBean.LIST_TYPE_STRING, "List String (%d)"),
                new Pair<>(ExtraMenuBean.LIST_TYPE_MAP, "List Map (%d)"),
                new Pair<>(4, "List Custom (%d)")
        );

        for (Pair<Integer, String> listType : listTypes) {
            ArrayList<String> lists = getUsedList(listType.first);
            for (int i = 0, size = lists.size(); i < size; i++) {
                String instanceName = lists.get(i);

                if (i == 0) data.add(new Item(String.format(listType.second, size)));
                data.add(new Item(instanceName, R.string.logic_editor_message_currently_used_list));
            }
        }

        dialog.a(recyclerView);
        dialog.b(Helper.getResString(R.string.common_word_remove), v -> {
            for (Item item : data) {
                if (item.type == Item.TYPE_ITEM && item.isChecked) {
                    logicEditor.l(item.text);
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

    private static class RemoveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final Context context;
        private final List<Item> data;
        private final Function<String, Boolean> isInUseChecker;

        /**
         * @param isInUseChecker Function that should return whether the name (parameter) is in use.
         */
        private RemoveAdapter(Context context, List<Item> data, Function<String, Boolean> isInUseChecker) {
            this.context = context;
            this.data = data;
            this.isInUseChecker = isInUseChecker;
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        @NonNull
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == Item.TYPE_TITLE) {
                TextView textView = new TextView(context);
                textView.setLayoutParams(new LinearLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT));
                textView.setPadding(
                        dpToPx(2),
                        dpToPx(4),
                        dpToPx(4),
                        dpToPx(4)
                );
                textView.setTextSize(14);
                return new TitleHolder(textView);
            } else if (viewType == Item.TYPE_ITEM) {
                CheckBox checkBox = new CheckBox(context);
                checkBox.setLayoutParams(new LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT));
                return new CheckBoxHolder(checkBox);
            } else {
                throw new IllegalStateException("Unknown view type " + viewType);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Item item = data.get(position);
            int viewType = holder.getItemViewType();

            if (viewType == Item.TYPE_TITLE) {
                TitleHolder titleHolder = (TitleHolder) holder;
                titleHolder.title.setText(item.text);
            } else if (viewType == Item.TYPE_ITEM) {
                CheckBoxHolder checkBoxHolder = (CheckBoxHolder) holder;
                checkBoxHolder.checkBox.setText(item.text);
                checkBoxHolder.checkBox.setChecked(item.isChecked);

                checkBoxHolder.checkBox.setOnClickListener(v -> {
                    boolean isChecked = checkBoxHolder.checkBox.isChecked();
                    item.isChecked = isChecked;
                    if (item.type == Item.TYPE_ITEM && isChecked) {
                        if (isInUseChecker.apply(item.text)) {
                            //noinspection ConstantConditions Item#inUseMessage can't be null if Item#type is Item#TYPE_ITEM
                            SketchwareUtil.toastError(Helper.getResString(item.inUseMessage), bB.TOAST_WARNING);
                            checkBoxHolder.checkBox.performClick();
                        }
                    }
                });
            } else {
                throw new IllegalStateException("Unknown view type " + viewType);
            }
        }

        @Override
        public int getItemViewType(int position) {
            return data.get(position).type;
        }

        private static class CheckBoxHolder extends RecyclerView.ViewHolder {
            public final CheckBox checkBox;

            public CheckBoxHolder(View itemView) {
                super(itemView);
                checkBox = (CheckBox) itemView;
            }
        }

        private static class TitleHolder extends RecyclerView.ViewHolder {
            public final TextView title;

            public TitleHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView;
            }
        }
    }

    private static class Item {
        public static final int TYPE_TITLE = 0;
        public static final int TYPE_ITEM = 1;

        private final int type;
        private final String text;
        @StringRes
        private final Integer inUseMessage;

        private boolean isChecked = false;

        public Item(String title) {
            type = TYPE_TITLE;
            text = title;
            inUseMessage = null;
        }

        public Item(String itemName, @StringRes int inUseMessage) {
            type = TYPE_ITEM;
            text = itemName;
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
