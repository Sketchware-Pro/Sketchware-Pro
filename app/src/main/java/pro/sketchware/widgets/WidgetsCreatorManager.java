package pro.sketchware.widgets;

import static pro.sketchware.utility.GsonUtils.getGson;
import static pro.sketchware.utility.SketchwareUtil.dpToPx;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AlertDialog;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.ViewEditor;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import a.a.a.ViewEditorFragment;
import a.a.a.xB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.DialogSelectorActionsBinding;
import pro.sketchware.databinding.WidgetsCreatorDialogBinding;
import pro.sketchware.lib.highlighter.SyntaxScheme;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class WidgetsCreatorManager {

    private ArrayList<HashMap<String, Object>> widgetConfigurationsList = new ArrayList<>();
    private final String widgetResourcesDirectoryPath = "/storage/emulated/0/.sketchware/resources/widgets/";
    private final String widgetsJsonFilePath = widgetResourcesDirectoryPath + "widgets.json";
    private final String widgetExportDirectoryPath = widgetResourcesDirectoryPath + "export/";
    private final ArrayList<String> allCategories = new ArrayList<>();
    private final ArrayList<String> mainCategories = new ArrayList<>(Arrays.asList(
            "Layouts", "AndroidX", "Widgets", "List", "Library", "Google", "Date & Time"
    ));
    private final List<String> availableWidgetsNames = Arrays.asList(
            "BottomNavigationView", "Button", "CardView", "CheckBox", "CodeView", "EditText", "GridView",
            "HScrollView", "ImageView", "LinearLayout", "ListView", "MapView", "MaterialButton", "ProgressBar",
            "RadioButton", "RecyclerView", "RelativeLayout", "ScrollView", "SeekBar", "Spinner", "Switch",
            "SwipeRefreshLayout", "TabLayout", "TextInputLayout", "TextView", "VideoView", "ViewPager", "WebView"
    );
    private final List<String> availableWidgetsTypes = new ArrayList<>();
    private final ViewEditor viewEditor;
    private final ViewEditorFragment viewEditorFragment;
    private final Context context;

    public WidgetsCreatorManager(ViewEditorFragment viewEditorFragment) {
        this.viewEditorFragment = viewEditorFragment;
        viewEditor = viewEditorFragment.viewEditor;
        context = viewEditorFragment.requireContext();
        initialize();
    }

    public void initialize() {
        initializeAvailableWidgetTypesList();
        if (FileUtil.isExistFile(widgetsJsonFilePath)) {
            loadCustomWidgets();
        } else {
            createWidgetsFile();
        }
        initializeCategoriesList();
    }

    private void loadCustomWidgets() {
        try {
            widgetConfigurationsList = getGson().fromJson(
                    FileUtil.readFile(widgetsJsonFilePath),
                    Helper.TYPE_MAP_LIST
            );
            widgetConfigurationsList.removeIf(this::isInvalidWidget);
        } catch (Exception e) {
            SketchwareUtil.toastError("Error loading widgets: " + e.getMessage());
            createWidgetsFile();
        }
    }

    private boolean isInvalidWidget(HashMap<String, Object> widgetData) {
        List<String> requiredKeys = Arrays.asList("Class", "title", "name", "inject", "type", "position");

        if (!widgetData.keySet().containsAll(requiredKeys)) {
            SketchwareUtil.toastError("Missing required keys for widget: " + widgetData.get("title"));
            return true;
        }

        try {
            int typeId = ((Number) widgetData.get("type")).intValue();
            if (!availableWidgetsTypes.contains(String.valueOf(typeId))) {
                SketchwareUtil.toastError("Invalid widget type: " + widgetData.get("title"));
                return true;
            }
        } catch (Exception e) {
            SketchwareUtil.toastError("Invalid type format for widget: " + widgetData.get("title"));
            return true;
        }

        return false;
    }


    private void initializeAvailableWidgetTypesList() {
        for (String widgetName : availableWidgetsNames) {
            availableWidgetsTypes.add(String.valueOf(ViewBean.getViewTypeByTypeName(widgetName)));
        }
    }

    private void initializeCategoriesList() {
        allCategories.clear();
        Objects.requireNonNull(allCategories).addAll(mainCategories);
        for (HashMap<String, Object> map : widgetConfigurationsList) {
            String Class = map.get("Class").toString();
            if (!allCategories.contains(Class)) {
                allCategories.add(Class);
            }
        }
    }

    private void createWidgetsFile() {
        if (widgetConfigurationsList != null) widgetConfigurationsList.clear();
        FileUtil.writeFile(widgetsJsonFilePath, getGson().toJson(widgetConfigurationsList));
    }

    public void showWidgetsCreatorDialog(int position) {
        boolean isEditing = position != -1;
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(context);
        dialog.setTitle(isEditing ? Helper.getResString(R.string.widget_editor) : Helper.getResString(R.string.create_new_widget));
        WidgetsCreatorDialogBinding binding = WidgetsCreatorDialogBinding.inflate(LayoutInflater.from(context));
        View inflate = binding.getRoot();

        clearErrorOnTextChanged(binding.widgetType, binding.inputType);
        clearErrorOnTextChanged(binding.widgetName, binding.inputName);
        clearErrorOnTextChanged(binding.widgetTitle, binding.inputTitle);
        clearErrorOnTextChanged(binding.addWidgetTo, binding.inputClass);

        SyntaxScheme.setXMLHighlighter(binding.injectCode);

        if (isEditing) {
            HashMap<String, Object> map = widgetConfigurationsList.get(position);
            binding.widgetType.setText(map.get("type").toString());
            binding.widgetName.setText(map.get("name").toString());
            binding.widgetTitle.setText(map.get("title").toString());
            binding.addWidgetTo.setText(map.get("Class").toString());
            binding.injectCode.setText(map.get("inject").toString());
        } else {
            dialog.setNeutralButton(R.string.common_word_see_more, (dialog1, which) -> {
                showMorePopUp(dialog1, dialog.create().getButton(which));
            });
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                binding.widgetName.getContext(),
                android.R.layout.simple_dropdown_item_1line,
                getSuggestions()
        );

        binding.widgetName.setAdapter(adapter);

        binding.widgetType.setLongClickable(false);
        binding.addWidgetTo.setLongClickable(false);

        binding.widgetType.setOnClickListener(v -> showTypeViewSelectorDialog(availableWidgetsNames, availableWidgetsTypes, binding.widgetType));
        binding.addWidgetTo.setOnClickListener(v -> {
            List<String> types = new ArrayList<>(allCategories);
            showCategorySelectorDialog(types, binding.addWidgetTo);
        });

        dialog.setPositiveButton(R.string.common_word_save, (v, which) -> {
            try {
                String widgetTitle = Helper.getText(binding.widgetTitle).trim();
                String widgetName = Helper.getText(binding.widgetName).trim();
                String widgetType = Helper.getText(binding.widgetType).trim();
                String widgetInject = Helper.getText(binding.injectCode).trim();
                String widgetClass = Helper.getText(binding.addWidgetTo).trim();

                if (widgetTitle.isEmpty()) {
                    binding.inputTitle.setError(String.format(Helper.getResString(R.string.var_is_required, "Widget title")));
                    return;
                }
                if (widgetName.isEmpty()) {
                    binding.inputName.setError(String.format(Helper.getResString(R.string.var_is_required), "Widget class name"));
                    return;
                }
                if (widgetType.isEmpty()) {
                    binding.inputType.setError(String.format(Helper.getResString(R.string.var_is_required), "Widget type"));
                    return;
                }
                if (widgetClass.isEmpty()) {
                    binding.inputClass.setError(String.format(Helper.getResString(R.string.var_is_required), "Category"));
                    return;
                }

                HashMap<String, Object> map = new HashMap<>();
                map.put("Class", widgetClass);
                map.put("title", widgetTitle);
                map.put("name", widgetName);
                map.put("inject", widgetInject);
                map.put("type", Integer.parseInt(widgetType));
                if (isEditing) {
                    map.put("position", position);
                    widgetConfigurationsList.set(position, map);
                } else {
                    map.put("position", widgetConfigurationsList.size());
                    widgetConfigurationsList.add(map);
                }
                FileUtil.writeFile(widgetsJsonFilePath, getGson().toJson(widgetConfigurationsList));
                if (!allCategories.contains(widgetClass)) {
                    allCategories.add(widgetClass);
                }
                viewEditorFragment.e();
                v.dismiss();
            } catch (Exception e) {
                SketchwareUtil.toastError("Failed: " + e.getMessage());
            }
        });

        dialog.setNegativeButton(R.string.common_word_cancel, null);

        dialog.setView(inflate);
        dialog.show();
    }

    private void showMorePopUp(DialogInterface dialog, View anchorView) {
        PopupMenu popupMenu = new PopupMenu(context, anchorView);
        popupMenu.getMenuInflater().inflate(R.menu.widget_creator_menu_more, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            dialog.dismiss();
            if (menuItem.getItemId() == R.id.import_widgets) {
                DialogProperties properties = new DialogProperties();

                properties.selection_mode = DialogConfigs.MULTI_MODE;
                properties.selection_type = DialogConfigs.FILE_SELECT;
                properties.root = Environment.getExternalStorageDirectory();
                properties.error_dir = Environment.getExternalStorageDirectory();
                properties.offset = Environment.getExternalStorageDirectory();
                properties.extensions = new String[]{"json"};

                FilePickerDialog pickerDialog = new FilePickerDialog(context, properties, R.style.RoundedCornersDialog);

                pickerDialog.setTitle("Select .json widgets files");
                pickerDialog.setDialogSelectionListener(this::importWidgets);

                pickerDialog.show();
            } else {
                String exportFilePath = widgetExportDirectoryPath + "allWidgets.json";
                FileUtil.writeFile(exportFilePath, getGson().toJson(widgetConfigurationsList));
                SketchwareUtil.toast("Exported in " + exportFilePath);
            }
            return true;
        });

        popupMenu.show();
    }

    private void importWidgets(String[] paths) {
        String ERROR_MESSAGE = "The imported widgets file #%s is empty or invalid";
        for (int i = 0; i < paths.length; i++) {
            String path = paths[i];
            String value = FileUtil.readFile(path);

            try {
                Type listType = new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType();
                ArrayList<HashMap<String, Object>> importedWidgets = getGson().fromJson(value, listType);

                if (importedWidgets.isEmpty()) {
                    SketchwareUtil.toastError(String.format(ERROR_MESSAGE, i + 1));
                    continue;
                }

                for (HashMap<String, Object> widget : importedWidgets) {
                    if (isInvalidWidget(widget)) return;
                    widget.put("position", widgetConfigurationsList.size());
                    widgetConfigurationsList.add(widget);
                    String widgetClass = widget.get("Class").toString();
                    if (!allCategories.contains(widgetClass)) {
                        allCategories.add(widgetClass);
                    }
                }

            } catch (Exception e) {
                SketchwareUtil.toastError(String.format(ERROR_MESSAGE, i + 1));
            }
        }

        if (!widgetConfigurationsList.isEmpty()) {
            FileUtil.writeFile(widgetsJsonFilePath, getGson().toJson(widgetConfigurationsList));
            viewEditorFragment.e();
            SketchwareUtil.toast("Imported!");
        }
    }

    public static void clearErrorOnTextChanged(EditText editText, TextInputLayout textInputLayout) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (textInputLayout.getError() != null) {
                    textInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void showTypeViewSelectorDialog(List<String> choices, List<String> types, TextInputEditText type) {
        AtomicInteger choice = new AtomicInteger();
        new MaterialAlertDialogBuilder(context)
                .setTitle(Helper.getResString(R.string.widget_type_title))
                .setSingleChoiceItems(choices.toArray(new String[0]),
                        types.indexOf(Objects.requireNonNull(type.getText()).toString()), (dialog2, which) -> choice.set(which))
                .setPositiveButton(R.string.common_word_save, (dialog2, which) ->
                        type.setText(types.get(choice.get()))
                )
                .setNegativeButton(R.string.common_word_cancel, null)
                .create().show();
    }

    private void showCategorySelectorDialog(List<String> choices, TextInputEditText type) {
        AtomicInteger choice = new AtomicInteger(choices.indexOf(Objects.requireNonNull(type.getText()).toString()));

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        ListView listView = new ListView(context);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_single_choice, choices);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setItemChecked(choice.get(), true);

        if (listView.getParent() != null) {
            ((ViewGroup) listView.getParent()).removeView(listView);
        }
        layout.addView(listView);

        TextInputLayout textInputLayout = new TextInputLayout(context);
        TextInputEditText newEditText = new TextInputEditText(textInputLayout.getContext());
        newEditText.setHint(Helper.getResString(R.string.new_class));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(dpToPx(20), 0, dpToPx(20), 0);
        textInputLayout.setLayoutParams(params);
        if (newEditText.getParent() != null) {
            ((ViewGroup) newEditText.getParent()).removeView(newEditText);
        }
        textInputLayout.addView(newEditText);

        listView.setOnItemClickListener((parent, view, which, id1) -> {
            choice.set(which);
            newEditText.setText("");
            newEditText.clearFocus();
        });
        layout.addView(textInputLayout);

        if (!(choice.get() >= 0 && choice.get() < choices.size() && choices.get(choice.get()).equals(Helper.getText(type)))) {
            newEditText.setText(Helper.getText(type));
        }

        builder.setTitle(Helper.getResString(R.string.add_to))
                .setView(layout)
                .setPositiveButton(R.string.common_word_save, (dialog2, which) -> {
                    String newWidget = Objects.requireNonNull(newEditText.getText()).toString();
                    if (!newWidget.isEmpty()) {
                        choices.add(newWidget);
                        type.setText(newWidget);
                    } else {
                        if (choice.get() >= 0 && choice.get() < choices.size()) {
                            type.setText(choices.get(choice.get()));
                        } else {
                            type.setText("");
                        }
                    }
                })
                .setNegativeButton(R.string.common_word_cancel, null)
                .create().show();

        newEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String newText = s.toString().trim();
                if (!newText.isEmpty()) {
                    for (int i = 0; i < listView.getCount(); i++) {
                        listView.setItemChecked(i, false);
                    }
                } else {
                    if (choice.get() >= 0 && choice.get() < listView.getCount()) {
                        listView.setItemChecked(choice.get(), true);
                    }
                }
            }
        });
    }

    public void addWidgetsByTitle(String title) {
        for (HashMap<String, Object> map : widgetConfigurationsList) {
            try {
                if (Objects.requireNonNull(map.get("Class")).toString().equals(title)) {
                    Object typeObj = map.get("type");
                    if (typeObj instanceof Double) {
                        map.put("type", ((Double) typeObj).intValue());
                    }
                    viewEditor.CreateCustomWidget(map);
                }
            } catch (Exception ignored) {
            }
        }
    }

    public void addExtraClasses() {
        ArrayList<String> myArrayListCopy = new ArrayList<>(allCategories);
        myArrayListCopy.removeAll(mainCategories);

        if (!myArrayListCopy.isEmpty()) {
            try {
                for (String item : myArrayListCopy) {
                    viewEditor.paletteWidget.extraTitle(item, 1);
                    addWidgetsByTitle(item);
                }
            } catch (Exception ignored) {
            }
        }
    }

    public void showActionsDialog(int tag) {
        Activity activity = viewEditorFragment.requireActivity();
        DialogSelectorActionsBinding dialogBinding = DialogSelectorActionsBinding.inflate(LayoutInflater.from(activity));
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(activity);
        dialogBuilder.setTitle("Actions");
        dialogBuilder.setView(dialogBinding.getRoot());

        AlertDialog dialog = dialogBuilder.create();

        int position = getWidgetPosition(tag);

        dialogBinding.edit.setOnClickListener(v -> {
            showWidgetsCreatorDialog(position);
            dialog.dismiss();
        });
        dialogBinding.export.setOnClickListener(v -> {
            HashMap<String, Object> mapToExport = widgetConfigurationsList.get(position);
            String exportFilePath = widgetExportDirectoryPath + mapToExport.get("title") + ".json";
            FileUtil.writeFile(exportFilePath, "[" + getGson().toJson(mapToExport) + "]");
            SketchwareUtil.toast("Exported in " + exportFilePath);
            dialog.dismiss();
        });
        dialogBinding.delete.setOnClickListener(v -> {
            deleteWidgetMap(position);
            dialog.dismiss();
        });

        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    private void deleteWidgetMap(int position) {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(context);
        dialog.setTitle(xB.b().a(context, R.string.view_widget_favorites_delete_title));
        dialog.setIcon(R.drawable.ic_mtrl_delete);
        dialog.setMessage(xB.b().a(context, R.string.view_widget_favorites_delete_message));
        dialog.setPositiveButton(xB.b().a(context, R.string.common_word_delete), (v, which) -> {
            String Class = Objects.requireNonNull(widgetConfigurationsList.get(position).get("Class")).toString();
            widgetConfigurationsList.remove(position);
            if (isClassEmpty(Class) && !mainCategories.contains(Class)) {
                allCategories.remove(Class);
            }
            FileUtil.writeFile(widgetsJsonFilePath, getGson().toJson(widgetConfigurationsList));
            viewEditorFragment.e();
            v.dismiss();
        });
        dialog.setNegativeButton(xB.b().a(context, R.string.common_word_cancel), null);
        dialog.show();
    }

    private int getWidgetPosition(int targetPosition) {
        for (int i = 0; i < widgetConfigurationsList.size(); i++) {
            HashMap<String, Object> widget = widgetConfigurationsList.get(i);
            Object positionValue = widget.get("position");

            if (positionValue != null) {
                int positionIntValue = ((Number) positionValue).intValue();
                if (positionIntValue == targetPosition) {
                    return i;
                }
            }
        }
        return -1;
    }

    private boolean isClassEmpty(String str) {
        if (!widgetConfigurationsList.isEmpty()) {
            for (HashMap<String, Object> map : widgetConfigurationsList) {
                if (map.containsKey("Class")) {
                    String classNameValue = (String) map.get("Class");
                    if (Objects.requireNonNull(classNameValue).equals(str)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public String generateCustomWidgetId(String input) {
        int lastIndex = input.lastIndexOf('.');
        if (lastIndex != -1) {
            input = input.substring(lastIndex + 1);
        }
        return input.substring(0, 1).toLowerCase() + input.substring(1);
    }

    private List<String> getSuggestions() {
        return Arrays.asList(context.getResources().getStringArray(R.array.property_convert_options));
    }

}