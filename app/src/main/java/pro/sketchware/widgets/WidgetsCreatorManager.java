package pro.sketchware.widgets;

import static com.besome.sketch.beans.ViewBean.getViewTypeResId;

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

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.ViewEditor;
import com.besome.sketch.editor.view.palette.IconBase;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import pro.sketchware.R;
import pro.sketchware.databinding.WidgetsCreatorDialogBinding;

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
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class WidgetsCreatorManager extends IconBase {

    public static ArrayList<HashMap<String, Object>> ListMap = new ArrayList<>();
    public static String widgetFilePath = "/storage/emulated/0/.sketchware/resources/widgets/widgets.json";
    public static String titlesFilePath = "/storage/emulated/0/.sketchware/resources/widgets/titles.json";
    public static ArrayList<String> myArrayList = new ArrayList<>();
    public static ArrayList<String> itemsToRemove = new ArrayList<>(Arrays.asList(
            "Layouts", "AndroidX", "Widgets", "List", "Library", "Google", "Date & Time"
    ));
    public static List<String> choices_array = Arrays.asList(
            "BottomNavigationView", "Button", "CardView", "CheckBox", "CodeView", "EditText", "GridView",
            "HorizontalScrollView", "ImageView", "LinearLayout", "ListView", "MaterialButton", "ProgressBar",
            "RadioButton", "RecyclerView", "SeekBar", "Spinner", "SwipeRefreshLayout", "TabLayout",
            "TextInputLayout", "TextView", "VerticalScrollView", "VideoView", "ViewPager", "WebView"
    );
    public static List<String> types_array = Arrays.asList(
            "32", "3", "36", "11", "47", "5", "25",
            "2", "6", "0", "9", "41", "8", "19",
            "48", "14", "10", "39", "30", "38",
            "4", "12", "21", "31", "7"
    );
    private final HashMap<String, Object> MapInfo = new HashMap<>();
    public String f = "";
    public int type;
    public String Title;

    public WidgetsCreatorManager(Context context) {
        super(context);
    }

    public WidgetsCreatorManager(HashMap<String, Object> map, Context context) {
        super(context);
        MapInfo.putAll(map);
        type = (int) MapInfo.get("type");
        Title = Objects.requireNonNull(MapInfo.get("title")).toString();
        setWidgetImage(getViewTypeResId(type));
        setWidgetName(Objects.requireNonNull(MapInfo.get("title")).toString());
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = type == 1 ? 0 : type;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutBean.paddingLeft = 8;
        layoutBean.paddingTop = 8;
        layoutBean.paddingRight = 8;
        layoutBean.paddingBottom = 8;
        viewBean.convert = Objects.requireNonNull(MapInfo.get("name")).toString();
        switch (viewBean.type) {
            case 0, 2:
                layoutBean.width = ViewGroup.LayoutParams.MATCH_PARENT;
                viewBean.layout.orientation = VERTICAL;
                break;
            case 3, 13, 4, 11, 19, 41:
                viewBean.text.text = Title;
                break;
            case 5:
                viewBean.text.hint = Title;
                break;
            case 6:
                viewBean.image.resName = f;
                break;
            case 7, 10, 12, 14, 36, 39:
                viewBean.layout.width = ViewGroup.LayoutParams.MATCH_PARENT;
            case 8:
                viewBean.text.text = Title;
                layoutBean.width = -1;
                break;
        }
        viewBean.inject = Objects.requireNonNull(MapInfo.get("inject")).toString();
        viewBean.isCustomWidget = true;
        return viewBean;
    }

    public static void getWidgetsListMap() {
        if (FileUtil.isExistFile(widgetFilePath)) {
            try {
                ListMap = new Gson().fromJson(FileUtil.readFile(widgetFilePath), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                checkWidgetsListMap();
            } catch (Exception e) {
                createWidgetsFile();
            }
        } else {
            createWidgetsFile();
        }
        if (FileUtil.isExistFile(titlesFilePath)) {
            try {
                myArrayList = new Gson().fromJson(FileUtil.readFile(titlesFilePath), new TypeToken<ArrayList<String>>() {
                }.getType());
            } catch (Exception e) {
                createTitlesFile();
            }
        } else {
            createTitlesFile();
        }
    }

    public static void checkWidgetsListMap() {
        int position = 0;
        for (HashMap<String, Object> map : ListMap) {
            if (!canAddWidget(map)) {
                ListMap.remove(position);
                SketchwareUtil.toastError("Failed to get custom widget " + position + "#");
            }
            position++;
        }
    }

    public static void createTitlesFile() {
        if (myArrayList != null) {
            myArrayList.clear();
        }
        Objects.requireNonNull(myArrayList).add("Layouts");
        myArrayList.add("AndroidX");
        myArrayList.add("Widgets");
        myArrayList.add("List");
        myArrayList.add("Library");
        myArrayList.add("Google");
        myArrayList.add("Date & Time");
        FileUtil.writeFile(titlesFilePath, new Gson().toJson(myArrayList));
    }

    public static void createWidgetsFile() {
        if (ListMap != null) ListMap.clear();
        HashMap<String, Object> map = new HashMap<>();
        map.put("Class", "Layouts");
        map.put("title", "RelativeLayout");
        map.put("name", "RelativeLayout");
        map.put("inject", "");
        map.put("type", 0);
        map.put("position", 0);
        ListMap.add(map);
        FileUtil.writeFile(widgetFilePath, new Gson().toJson(ListMap));
    }

    public static void showWidgetsCreatorDialog(Context context) {
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

        binding.widgetType.setOnClickListener(v -> showAlertDialog(context, choices_array, types_array, binding.widgetType));
        binding.addWidgetTo.setOnClickListener(v -> {
            List<String> types = new ArrayList<>(myArrayList);
            showAlertDialog(context, types, binding.addWidgetTo);
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
                Object positionObject = ListMap.isEmpty() ? 0 : ListMap.get(ListMap.size() - 1).get("position");
                int position;
                if (positionObject instanceof Number) {
                    position = ((Number) positionObject).intValue();
                } else {
                    position = Integer.parseInt(Objects.requireNonNull(positionObject).toString());
                }

                map.put("position", position + 1);

                ListMap.add(map);
                FileUtil.writeFile(widgetFilePath, new Gson().toJson(ListMap));
                if (!myArrayList.contains(widgetClass)) {
                    myArrayList.add(widgetClass);
                    FileUtil.writeFile(titlesFilePath, new Gson().toJson(myArrayList));
                }
                ViewEditorFragment.e();
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

    public static void showAlertDialog(Context context, List<String> choices, List<String> types, TextInputEditText type) {
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

    public static void showAlertDialog(Context context, List<String> choices, TextInputEditText type) {
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

    public static void addWidgetsByTitle(ViewEditor viewEditor, String title) {
        for (HashMap<String, Object> map : ListMap) {
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

    public static boolean canAddWidget(HashMap<String, Object> map) {
        List<String> keysToCheck = Arrays.asList("Class", "title", "name", "inject", "type", "position");
        boolean containsAllKeys = map.keySet().containsAll(keysToCheck);
        Object value = map.get("type");
        if (value == null) {
            return false;
        }
        String stringValue = String.valueOf(value);
        boolean isContainedInTypes = types_array.contains(stringValue);
        if (value instanceof Double) {
            double doubleValue = (double) value;
            int intValue = (int) doubleValue;
            isContainedInTypes = isContainedInTypes || types_array.contains(String.valueOf(intValue));
        }
        return isContainedInTypes && containsAllKeys;
    }

    public static void addExtraClasses(ViewEditor viewEditor) {
        ArrayList<String> myArrayListCopy = new ArrayList<>(myArrayList);
        myArrayListCopy.removeAll(itemsToRemove);

        if (!myArrayListCopy.isEmpty()) {
            try {
                for (String item : myArrayListCopy) {
                    viewEditor.paletteWidget.extraTitle(item, 1);
                    addWidgetsByTitle(viewEditor, item);
                }
            } catch (Exception ignored) {
            }
        }
    }

    public static void deleteWidgetMap(Context context, int position) {
        aB aBDialog = new aB((Activity) context);
        aBDialog.b(xB.b().a(context, R.string.view_widget_favorites_delete_title));
        aBDialog.a(R.drawable.ic_mtrl_delete);
        aBDialog.a(xB.b().a(context, R.string.view_widget_favorites_delete_message));
        aBDialog.b(xB.b().a(context, R.string.common_word_delete), v -> {
            for (Iterator<HashMap<String, Object>> iterator = ListMap.iterator(); iterator.hasNext(); ) {
                HashMap<String, Object> map = iterator.next();
                Object positionValue = map.get("position");

                if (positionValue != null) {
                    int positionIntValue = (positionValue instanceof Double) ? ((Double) positionValue).intValue() : (int) positionValue;
                    if (positionIntValue == position) {
                        iterator.remove();
                        String Class = Objects.requireNonNull(map.get("Class")).toString();
                        if (isClassEmpty(Class) && !itemsToRemove.contains(Class)) {
                            myArrayList.remove(Class);
                            FileUtil.writeFile(titlesFilePath, new Gson().toJson(myArrayList));
                        }
                        break;
                    }
                }
            }
            FileUtil.writeFile(widgetFilePath, new Gson().toJson(ListMap));
            ViewEditorFragment.e();
            aBDialog.dismiss();
        });
        aBDialog.a(xB.b().a(context, R.string.common_word_cancel), Helper.getDialogDismissListener(aBDialog));
        aBDialog.show();
    }

    public static boolean isClassEmpty(String str) {
        if (!ListMap.isEmpty()) {
            for (HashMap<String, Object> map : ListMap) {
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

    public static String generateCustomWidgetId(String input) {
        int lastIndex = input.lastIndexOf('.');
        if (lastIndex != -1) {
            input = input.substring(lastIndex + 1);
        }
        return input.substring(0, 1).toLowerCase() + input.substring(1);
    }

    public static List<String> getSuggestions(Context context) {
        return Arrays.asList(context.getResources().getStringArray(R.array.property_convert_options));
    }

}