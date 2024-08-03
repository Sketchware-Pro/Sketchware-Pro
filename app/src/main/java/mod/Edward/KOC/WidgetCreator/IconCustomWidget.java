package mod.Edward.KOC.WidgetCreator;

import static com.besome.sketch.beans.ViewBean.getViewTypeResId;

import static mod.SketchwareUtil.dpToPx;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.ViewEditor;
import com.besome.sketch.editor.view.palette.IconBase;
import com.besome.sketch.editor.view.palette.PaletteWidget;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import a.a.a.ViewEditorFragment;
import a.a.a.aB;
import a.a.a.xB;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;

public class IconCustomWidget extends IconBase {


    public static ArrayList<HashMap<String, Object>> ListMap = new ArrayList<>();
    public static String WidgetFilePath = "/storage/emulated/0/.sketchware/resources/widgets/widgets.json";
    public static String TitlesFilePath = "/storage/emulated/0/.sketchware/resources/widgets/titles.json";
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
    public static boolean IsConvertCorrect = false;

    public IconCustomWidget(HashMap<String, Object> map, Context context) {
        super(context);
        MapInfo.putAll(map);
        type = (int) MapInfo.get("type");
        Title = MapInfo.get("title").toString();
        setWidgetImage(getViewTypeResId(type));
        setWidgetName(MapInfo.get("title").toString());
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
        viewBean.convert = MapInfo.get("name").toString();
        switch(viewBean.type) {
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
            case 7, 10, 12, 14, 36 ,39:
                viewBean.layout.width = ViewGroup.LayoutParams.MATCH_PARENT;
            case 8:
                viewBean.text.text = Title;
                layoutBean.width = -1;
                break;
        }
        viewBean.inject = MapInfo.get("inject").toString();
        return viewBean;
    }

    public static void GetWidgetsListMap() {
        if (FileUtil.isExistFile(WidgetFilePath)) {
            try {
                ListMap = new Gson().fromJson(FileUtil.readFile(WidgetFilePath), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                CheckWidgetsListMap();
            } catch (Exception e) {
                CreateWidgetsFile();
            }
        } else {
            CreateWidgetsFile();
        }
        if (FileUtil.isExistFile(TitlesFilePath)) {
            try {
                myArrayList = new Gson().fromJson(FileUtil.readFile(TitlesFilePath), new TypeToken<ArrayList<String>>() {
                }.getType());
            } catch (Exception e) {
                CreateTitlesFile();
            }
        } else {
            CreateTitlesFile();
        }
    }

    public static void CheckWidgetsListMap() {
        int position = 0;
        for (HashMap<String, Object> map : ListMap) {
            if (!CanAddWidget(map)) {
                ListMap.remove(position);
                SketchwareUtil.toastError("Failed to get custom widget " + position + "#");
            }
            position++;
        }
    }

    public static void CreateTitlesFile() {
        if (myArrayList != null) {
            myArrayList.clear();
        }
        myArrayList.add("Layouts");
        myArrayList.add("AndroidX");
        myArrayList.add("Widgets");
        myArrayList.add("List");
        myArrayList.add("Library");
        myArrayList.add("Google");
        myArrayList.add("Date & Time");
        FileUtil.writeFile(TitlesFilePath, new Gson().toJson(myArrayList));
    }

    public static void CreateWidgetsFile() {
        if (ListMap != null) {
            ListMap.clear();
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("Class", "Layouts");
        map.put("title", "RelativeLayout");
        map.put("name", "RelativeLayout");
        map.put("inject", "");
        map.put("type", 0);
        map.put("position", 0);
        ListMap.add(map);
        FileUtil.writeFile(WidgetFilePath, new Gson().toJson(ListMap));
    }

    public static void AddCustomWidgets(Context context, PaletteWidget paletteWidget) {

        MaterialCardView cardView = new MaterialCardView(context);
        MarginLayoutParams layoutParams = new MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(dpToPx(5), 0, dpToPx(5), dpToPx(2));
        cardView.setLayoutParams(layoutParams);

        cardView.setRadius(28);
        cardView.setCardElevation(10);
        cardView.setStrokeWidth(0);


        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                dpToPx(30),
                dpToPx(30)));
        imageView.setImageResource(R.drawable.plus_96);

        TextView textView = new TextView(context);
        textView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setTextSize(dpToPx(4));
        textView.setText(Helper.getResString(R.string.create_new_widget));

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setGravity(Gravity.CENTER);
        int padding = dpToPx(4);
        linearLayout.setPadding(padding, padding, padding, padding);

        linearLayout.addView(imageView);
        linearLayout.addView(textView);

        cardView.addView(linearLayout);
        paletteWidget.AddCustomWidgets(cardView);
        cardView.setOnClickListener(view -> {
            final AlertDialog dialog = new AlertDialog.Builder(context)
                    .setView(R.layout.widgets_creator_dialog)
                    .setCancelable(true)
                    .create();
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            View inflate = LayoutInflater.from(context).inflate(R.layout.widgets_creator_dialog, null);
            dialog.setView(inflate);
            final TextView dialogTitle2 = inflate.findViewById(R.id.dialog_title2);
            final TextView dialog_title = inflate.findViewById(R.id.dialog_title);
            final MaterialButton btnCancel = inflate.findViewById(R.id.dialog_btn_cancel);
            final MaterialButton btnEntre = inflate.findViewById(R.id.dialog_btn_entre);
            final TextInputEditText type = inflate.findViewById(R.id.widget_type);
            final TextInputEditText name = inflate.findViewById(R.id.widget_name);
            final TextInputEditText title = inflate.findViewById(R.id.widget_title);
            final TextInputEditText inject = inflate.findViewById(R.id.inject_code);
            final TextInputEditText add = inflate.findViewById(R.id.add_widget_to);
            final TextInputLayout input_title = inflate.findViewById(R.id.input_title);
            final TextInputLayout input_name = inflate.findViewById(R.id.input_name);
            final TextInputLayout input_type = inflate.findViewById(R.id.input_type);
            final TextInputLayout input_class = inflate.findViewById(R.id.input_class);
            clearErrorOnTextChanged(type, input_type);
            clearErrorOnTextChanged(name, input_name);
            clearErrorOnTextChanged(title, input_title);
            clearErrorOnTextChanged(add, input_class);

            dialogTitle2.setText(R.string.by_edward_koc);
            dialogTitle2.setTextSize(10);
            dialog_title.setText(Helper.getResString(R.string.create_new_widget));
            btnEntre.setText(Helper.getResString(R.string.create));
            btnCancel.setText(Helper.getResString(R.string.common_word_cancel));

            type.setLongClickable(false);
            add.setLongClickable(false);

            type.setOnClickListener(v ->{
                ShowAlertDialog(context ,choices_array, types_array, type);
            });
            add.setOnClickListener(v ->{
                List<String> types = new ArrayList<>(myArrayList);
                ShowAlertDialog(context, types, add);
            });
            btnEntre.setOnClickListener(v ->{
                try {
                    String WidgetTitle = title.getText().toString().trim();
                    String WidgetName = name.getText().toString().trim();
                    String WidgetType = type.getText().toString().trim();
                    String WidgetInject = inject.getText().toString().trim();
                    String WidgetClass = add.getText().toString().trim();
                    if (WidgetTitle.isEmpty()) {
                        input_title.setError(Helper.getResString(R.string.title_required));
                        return;
                    }
                    if (WidgetName.isEmpty()) {
                        input_name.setError(Helper.getResString(R.string.name_required));
                        return;
                    }
                    if (!IsConvertCorrect) {
                        return;
                    }
                    if (WidgetType.isEmpty()) {
                        input_type.setError(Helper.getResString(R.string.type_required));
                        return;
                    }
                    if (WidgetClass.isEmpty()) {
                        input_class.setError(Helper.getResString(R.string.class_required));
                        return;
                    }
                    HashMap<String, Object> Map = new HashMap<>();
                    Map.put("Class", WidgetClass);
                    Map.put("title", WidgetTitle);
                    Map.put("name", WidgetName);
                    Map.put("inject", WidgetInject);
                    Map.put("type", Integer.parseInt(WidgetType));
                    Object positionObject = ListMap.isEmpty() ? 0 : ListMap.get(ListMap.size() - 1).get("position");
                    int position;
                    if (positionObject instanceof Number) {
                        position = ((Number) positionObject).intValue();
                    } else {
                        position = Integer.parseInt(positionObject.toString());
                    }

                    Map.put("position", position + 1);

                    ListMap.add(Map);
                    FileUtil.writeFile(WidgetFilePath, new Gson().toJson(ListMap));
                    if (!myArrayList.contains(WidgetClass)) {
                        myArrayList.add(WidgetClass);
                        FileUtil.writeFile(TitlesFilePath, new Gson().toJson(myArrayList));
                    }
                    ViewEditorFragment.e();
                    dialog.dismiss();
                } catch (Exception e) {
                    SketchwareUtil.toastError("Failed :" + e.getMessage());
                }
            });
            btnCancel.setOnClickListener(v -> dialog.dismiss());
            dialog.show();
        });
    }

    public static void clearErrorOnTextChanged(final EditText editText, final TextInputLayout textInputLayout) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String se = s.toString();
                if (editText.getId() == R.id.widget_name && !se.isEmpty()) {
                    if (Pattern.compile("^[a-zA-Z.]*").matcher(se).matches()) {
                        textInputLayout.setError(null);
                        IsConvertCorrect = true;
                    } else if (se.contains(" ")) {
                        textInputLayout.setError("Spaces aren't allowed to prevent crashes");
                        IsConvertCorrect = false;
                    } else {
                        textInputLayout.setError("Only use letters (a-zA-Z), numbers and Special characters (.)");
                        IsConvertCorrect = false;
                    }
                } else {
                    textInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    /*

    }

     */
    public static void ShowAlertDialog(Context context, List<String> choices, List<String> types, TextInputEditText type) {
        AtomicInteger choice = new AtomicInteger();
        new AlertDialog.Builder(context).setTitle(Helper.getResString(R.string.widget_type_title))
                .setSingleChoiceItems(choices.toArray(new String[0]),
                        types.indexOf(type.getText().toString()), (dialog2, which) -> choice.set(which))
                .setPositiveButton(R.string.common_word_save, (dialog2, which) ->
                        type.setText(types.get(choice.get()))
                )
                .setNegativeButton(R.string.common_word_cancel, null)
                .create().show();
    }

    public static void ShowAlertDialog(Context context, List<String> choices, TextInputEditText type) {
        AtomicInteger choice = new AtomicInteger(choices.indexOf(type.getText().toString()));

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

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
        TextInputEditText newEditText =  new TextInputEditText(textInputLayout.getContext());
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

        builder.setTitle(Helper.getResString(R.string.add_to));
        builder.setView(layout)
                .setPositiveButton(R.string.common_word_save, (dialog2, which) -> {
                    String newWidget = newEditText.getText().toString();
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

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

    public static void AddWidgetsByTitle(ViewEditor viewEditor, String title) {
        for (HashMap<String, Object> map : ListMap) {
            try {
                if (Objects.requireNonNull(map.get("Class")).toString().equals(title)) {
                    Object typeObj = map.get("type");
                    if (typeObj instanceof Double) {
                        map.put("type", ((Double) typeObj).intValue());
                    }
                    viewEditor.CreateCustomWidget(map);
                }
            } catch (Exception ignored) {}
        }
    }

    public static boolean CanAddWidget(HashMap<String, Object> map) {
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

    public static void AddExtraClasses(ViewEditor viewEditor) {
        ArrayList<String> myArrayListCopy = new ArrayList<>(myArrayList);
        myArrayListCopy.removeAll(itemsToRemove);

        if (!myArrayListCopy.isEmpty()) {
            try {
                for (String item : myArrayListCopy) {
                    viewEditor.paletteWidget.extraTitle(item, 1);
                    AddWidgetsByTitle(viewEditor, item);
                }
            } catch (Exception ignored) {
            }
        }
    }

    public static void DeleteWidgetMap(Context context, int _position) {
        aB aBDialog = new aB((Activity) context);
        aBDialog.b(xB.b().a(context, R.string.view_widget_favorites_delete_title));
        aBDialog.a(R.drawable.high_priority_96_red);
        aBDialog.a(xB.b().a(context, R.string.view_widget_favorites_delete_message));
        aBDialog.b(xB.b().a(context, R.string.common_word_delete), v -> {
            for (Iterator<HashMap<String, Object>> iterator = ListMap.iterator(); iterator.hasNext();) {
                HashMap<String, Object> map = iterator.next();
                Object positionValue = map.get("position");

                if (positionValue != null) {
                    int positionIntValue = (positionValue instanceof Double) ? ((Double) positionValue).intValue() : (int) positionValue;
                    if (positionIntValue == _position) {
                        iterator.remove();
                        String Class = map.get("Class").toString();
                        if (IsClassEmpty(Class) && !itemsToRemove.contains(Class)) {
                            myArrayList.remove(Class);
                            FileUtil.writeFile(TitlesFilePath, new Gson().toJson(myArrayList));
                        }
                        break;
                    }
                }
            }
            FileUtil.writeFile(WidgetFilePath, new Gson().toJson(ListMap));
            ViewEditorFragment.e();
            aBDialog.dismiss();
        });
        aBDialog.a(xB.b().a(context, R.string.common_word_cancel), Helper.getDialogDismissListener(aBDialog));
        aBDialog.show();
    }

    public static boolean IsClassEmpty(String str) {
        if (!ListMap.isEmpty()) {
            for (HashMap<String, Object> map : ListMap) {
                if (map.containsKey("Class")) {
                    String classNameValue = (String) map.get("Class");
                    if (classNameValue.equals(str)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static String SubstringCovert(String input) {
        int lastIndex = input.lastIndexOf('.');
        if (lastIndex != -1) {
            return input.substring(lastIndex + 1);
        } else {
            return input;
        }
    }

}