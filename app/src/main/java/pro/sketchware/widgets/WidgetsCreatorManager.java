package pro.sketchware.widgets;

import static pro.sketchware.utility.SketchwareUtil.dpToPx;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.ViewEditor;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import a.a.a.ViewEditorFragment;
import a.a.a.aB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.WidgetsCreatorDialogBinding;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class WidgetsCreatorManager {

    private ArrayList<HashMap<String, Object>> widgetsListMap = new ArrayList<>();
    private final String widgetsFilePath = "/storage/emulated/0/.sketchware/resources/widgets/widgets.json";
    private final ArrayList<String> categoriesList = new ArrayList<>();
    private final ArrayList<String> mainCategories = new ArrayList<>(Arrays.asList(
            "Layouts", "AndroidX", "Widgets", "List", "Library", "Google", "Date & Time"
    ));
    private final List<String> availableWidgetsNames = Arrays.asList(
            "BottomNavigationView", "Button", "CardView", "CheckBox", "CodeView", "EditText", "GridView",
            "HScrollView", "ImageView", "LinearLayout", "ListView", "MapView", "MaterialButton", "ProgressBar",
            "RadioButton", "RecyclerView", "RelativeLayout", "ScrollView", "SeekBar", "Spinner", "Switch", "SwipeRefreshLayout", "TabLayout",
            "TextInputLayout", "TextView", "VideoView", "ViewPager", "WebView"
    );
    private final List<String> availableWidgetsTypes = new ArrayList<>();
    private final ViewEditor viewEditor;
    private final ViewEditorFragment viewEditorFragment;
    private final Context context;

    public WidgetsCreatorManager(ViewEditorFragment viewEditorFragment) {
        this.viewEditorFragment = viewEditorFragment;
        this.viewEditor = viewEditorFragment.viewEditor;
        context = viewEditorFragment.requireContext();
        initialize();
    }

    public void initialize() {
        initializeAvailableWidgetTypesList();
        initializeCategoriesList();
        if (FileUtil.isExistFile(widgetsFilePath)) {
            loadCustomWidgets();
        } else {
            createWidgetsFile();
        }
    }

    private void loadCustomWidgets() {
        try {
            widgetsListMap = new Gson().fromJson(
                    FileUtil.readFile(widgetsFilePath),
                    new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType()
            );
            validateWidgets();
        } catch (Exception e) {
            SketchwareUtil.toastError("Error loading widgets: " + e.getMessage());
            createWidgetsFile();
        }
    }

    private void validateWidgets() {
        Iterator<HashMap<String, Object>> iterator = widgetsListMap.iterator();
        while (iterator.hasNext()) {
            HashMap<String, Object> widget = iterator.next();
            if (!canAddWidget(widget)) {
                iterator.remove();
                SketchwareUtil.toastError("Failed to get custom widget " + widget.get("title"));
            }
        }
    }

    private boolean canAddWidget(HashMap<String, Object> map) {
        List<String> keysToCheck = Arrays.asList("Class", "title", "name", "inject", "type", "position");
        boolean containsAllKeys = map.keySet().containsAll(keysToCheck);
        Object value = map.get("type");
        if (value == null) {
            return false;
        }
        int intValue;
        if (value instanceof Number) {
            intValue = ((Number) value).intValue();
        } else {
            try {
                intValue = Integer.parseInt(value.toString());
            } catch (NumberFormatException e) {
                return false;
            }
        }
        boolean isContainedInTypes = availableWidgetsTypes.contains(String.valueOf(intValue));
        return isContainedInTypes && containsAllKeys;
    }

    private void initializeAvailableWidgetTypesList() {
        for (String widgetName : availableWidgetsNames) {
            availableWidgetsTypes.add(String.valueOf(ViewBean.getViewTypeByTypeName(widgetName)));
        }
    }

    private void initializeCategoriesList() {
        categoriesList.clear();
        Objects.requireNonNull(categoriesList).addAll(mainCategories);
        for (HashMap<String, Object> map : widgetsListMap) {
            String Class = map.get("Class").toString();
            if (!categoriesList.contains(Class)) {
                categoriesList.add(Class);
            }
        }
    }

    private void createWidgetsFile() {
        if (widgetsListMap != null) widgetsListMap.clear();
        FileUtil.writeFile(widgetsFilePath, new Gson().toJson(widgetsListMap));
    }

    public void showWidgetsCreatorDialog() {
        aB dialog = new aB((Activity) context);
        dialog.b(Helper.getResString(R.string.create_new_widget));
        WidgetsCreatorDialogBinding binding = WidgetsCreatorDialogBinding.inflate(LayoutInflater.from(context));
        View inflate = binding.getRoot();

        clearErrorOnTextChanged(binding.widgetType, binding.inputType);
        clearErrorOnTextChanged(binding.widgetName, binding.inputName);
        clearErrorOnTextChanged(binding.widgetTitle, binding.inputTitle);
        clearErrorOnTextChanged(binding.addWidgetTo, binding.inputClass);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                binding.widgetName.getContext(),
                android.R.layout.simple_dropdown_item_1line,
                getSuggestions(context)
        );

        binding.widgetName.setAdapter(adapter);

        binding.widgetType.setLongClickable(false);
        binding.addWidgetTo.setLongClickable(false);

        binding.widgetType.setOnClickListener(v -> showTypeViewSelectorDialog(availableWidgetsNames, availableWidgetsTypes, binding.widgetType));
        binding.addWidgetTo.setOnClickListener(v -> {
            List<String> types = new ArrayList<>(categoriesList);
            showCategorySelectorDialog(types, binding.addWidgetTo);
        });

        dialog.b(Helper.getResString(R.string.create), v -> {
            try {
                String widgetTitle = Objects.requireNonNull(binding.widgetTitle.getText()).toString().trim();
                String widgetName = Objects.requireNonNull(binding.widgetName.getText()).toString().trim();
                String widgetType = Objects.requireNonNull(binding.widgetType.getText()).toString().trim();
                String widgetInject = Objects.requireNonNull(binding.injectCode.getText()).toString().trim();
                String widgetClass = Objects.requireNonNull(binding.addWidgetTo.getText()).toString().trim();

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
                Object positionObject = widgetsListMap.isEmpty() ? 0 : widgetsListMap.get(widgetsListMap.size() - 1).get("position");
                int position;
                if (positionObject instanceof Number) {
                    position = ((Number) positionObject).intValue();
                } else {
                    position = Integer.parseInt(Objects.requireNonNull(positionObject).toString());
                }

                map.put("position", position + 1);

                widgetsListMap.add(map);
                FileUtil.writeFile(widgetsFilePath, new Gson().toJson(widgetsListMap));
                if (!categoriesList.contains(widgetClass)) {
                    categoriesList.add(widgetClass);
                }
                viewEditorFragment.e();
                dialog.dismiss();
            } catch (Exception e) {
                SketchwareUtil.toastError("Failed: " + e.getMessage());
            }
        });

        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));

        dialog.a(inflate);
        dialog.show();
    }

    public static void clearErrorOnTextChanged(final EditText editText, final TextInputLayout textInputLayout) {
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

        ListView listView = new ListView(context);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_single_choice, choices);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setItemChecked(choice.get(), true);

        int maxHeightPx = dpToPx(350);

        LinearLayout.LayoutParams listParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                maxHeightPx
        );

        listParams.setMargins(0, dpToPx(15), 0, dpToPx(10));
        listView.setLayoutParams(listParams);

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

        if (!(choice.get() >= 0 && choice.get() < choices.size() && choices.get(choice.get()).equals(type.getText().toString()))) {
            newEditText.setText(type.getText().toString());
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
        for (HashMap<String, Object> map : widgetsListMap) {
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
        ArrayList<String> myArrayListCopy = new ArrayList<>(categoriesList);
        myArrayListCopy.removeAll(mainCategories);

        if (!myArrayListCopy.isEmpty()) {
            try {
                for (String item : myArrayListCopy) {
                    viewEditor.paletteWidget.extraTitle(item, 1);
                    addWidgetsByTitle(item);
                }
            } catch (Exception ignored) {}
        }
    }

    public void deleteWidgetMap(Context context, int position) {
        aB aBDialog = new aB((Activity) context);
        aBDialog.b(xB.b().a(context, R.string.view_widget_favorites_delete_title));
        aBDialog.a(R.drawable.ic_mtrl_delete);
        aBDialog.a(xB.b().a(context, R.string.view_widget_favorites_delete_message));
        aBDialog.b(xB.b().a(context, R.string.common_word_delete), v -> {
            for (Iterator<HashMap<String, Object>> iterator = widgetsListMap.iterator(); iterator.hasNext(); ) {
                HashMap<String, Object> map = iterator.next();
                Object positionValue = map.get("position");

                if (positionValue != null) {
                    int positionIntValue = (positionValue instanceof Double) ? ((Double) positionValue).intValue() : (int) positionValue;
                    if (positionIntValue == position) {
                        iterator.remove();
                        String Class = Objects.requireNonNull(map.get("Class")).toString();
                        if (isClassEmpty(Class) && !mainCategories.contains(Class)) {
                            categoriesList.remove(Class);
                        }
                        break;
                    }
                }
            }
            FileUtil.writeFile(widgetsFilePath, new Gson().toJson(widgetsListMap));
            viewEditorFragment.e();
            aBDialog.dismiss();
        });
        aBDialog.a(xB.b().a(context, R.string.common_word_cancel), Helper.getDialogDismissListener(aBDialog));
        aBDialog.show();
    }

    private boolean isClassEmpty(String str) {
        if (!widgetsListMap.isEmpty()) {
            for (HashMap<String, Object> map : widgetsListMap) {
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

    private List<String> getSuggestions(Context context) {
        return Arrays.asList(context.getResources().getStringArray(R.array.property_convert_options));
    }

}