package mod.hey.studios.lib.code_editor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.Layout;
import android.text.Selection;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupMenu;
import android.widget.ScrollView;

import java.util.List;
import java.util.regex.Matcher;

import a.a.a.Lx;
import a.a.a.wB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.utility.SketchwareUtil;

/**
 * A lightweight Code Editor with syntax highlighting, auto indentation, word wrap and lines.
 *
 * @author Hey! Studios DEV - 28.07.2020
 * <p>
 * <p>
 * <p>
 * //do not use this source to modify this library, use the other workaround i sent.
 */

public class CodeEditorLayout extends LinearLayout implements TextWatcher {

    private static final int DEFAULT_LAYOUT = R.layout.code_editor_layout;
    private static final int NOWRAP_LAYOUT = R.layout.code_editor_layout_nowrap;

    private static final int EDITTEXT_RES = R.id.code_editor_edittext;
    private static final int SCROLLVIEW_RES = R.id.code_editor_layoutScrollView;

    private static final boolean INDENTABLE = true;
    private static final String INDENT_STR = "    ";
    private final static double KEYBOARD_RATIO = 0.25;
    private final static int UPDATE_DELAY = 128;
    private final Context context;
    private final BeforeTextChangeInfo beforeInfo = new BeforeTextChangeInfo();
    public boolean dark_theme;
    private CodeEditorEditText editText;
    private ScrollView scrollView;
    //SharedPref start
    private SharedPreferences sharedpref;
    private boolean exit_confirmation_dialog;
    private boolean word_wrap;
    private boolean complete_brackets;
    private int type;
    private List<ColorScheme> highlightList;
    private Runnable updateHighlight;


    //Options menu start


    public CodeEditorLayout(Context context) {
        super(context);
        this.context = context;
        loadPreferences();

    }

    public CodeEditorLayout(Context context, AttributeSet attr) {
        super(context, attr);
        this.context = context;
        loadPreferences();

    }

    public CodeEditorLayout(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        this.context = context;
        loadPreferences();

    }

    private void setPreference(String key, boolean value) {
        sharedpref.edit().putBoolean(key, value).apply();
    }

    private void setPreference(String key, float value) {
        if (!isInEditMode()) {
            sharedpref.edit().putFloat(key, value).apply();
        }
    }

    private void loadPreferences() {
        sharedpref = context.getSharedPreferences("code_editor_pref", Activity.MODE_PRIVATE);

        dark_theme = sharedpref.getBoolean("dark_theme", false);
        word_wrap = sharedpref.getBoolean("word_wrap", false);
        float text_size = sharedpref.getFloat("text_size", 12f);

        complete_brackets = sharedpref.getBoolean("complete_brackets", true);
        exit_confirmation_dialog = sharedpref.getBoolean("exit_confirmation_dialog", false);

        initialize(word_wrap ? DEFAULT_LAYOUT : NOWRAP_LAYOUT);

        setTextSize(text_size);
    }

    public void onCreateOptionsMenu(View v) {
        final PopupMenu popup = new PopupMenu(context, v);
        Menu menu = popup.getMenu();

        menu.add("Font size")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

        menu.add("Word wrap")
                .setCheckable(true)
                .setChecked(word_wrap)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

        menu.add("Complete brackets")
                .setCheckable(true)
                .setChecked(complete_brackets)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

        menu.add("Dark theme")
                .setCheckable(true)
                .setChecked(dark_theme)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

        menu.add("Pretty print")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

        menu.add("Exit confirmation")
                .setCheckable(true)
                .setChecked(exit_confirmation_dialog)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

        popup.setOnMenuItemClickListener(item -> {
            switch (item.getTitle().toString()) {
                case "Font size":
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    final NumberPicker numPicker = new NumberPicker(context);
                    numPicker.setMinValue(5);
                    numPicker.setMaxValue(20);
                    numPicker.setValue((int) editText.getTextSize());
                    numPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

                    builder.setView(numPicker)
                            .setTitle("Select font size")
                            .setPositiveButton(R.string.common_word_save, (dialog, which) -> {
                                setTextSize(numPicker.getValue());
                                dialog.dismiss();
                            })
                            .setNegativeButton(R.string.common_word_cancel, null)
                            .show();
                    break;

                case "Word wrap":
                    item.setChecked(!item.isChecked());
                    setWordWrap(item.isChecked());
                    break;

                case "Complete brackets":
                    item.setChecked(!item.isChecked());
                    complete_brackets = item.isChecked();
                    setPreference("complete_brackets", complete_brackets);
                    break;

                case "Dark theme":
                    item.setChecked(!item.isChecked());
                    dark_theme = item.isChecked();
                    editText.removeTextChangedListener(this);
                    startHighlighting(type);
                    break;

                case "Pretty print":
                    StringBuilder string = new StringBuilder();
                    String[] lines = getText().split("\n");

                    for (String line : lines) {
                        String tmp = line;
                        tmp = (tmp + "X").trim();
                        string.append(tmp.substring(0, tmp.length() - 1));
                        string.append("\n");
                    }

                    String prettifiedString = string.toString();

                    try {
                        prettifiedString = Lx.j(Lx.j(prettifiedString, false), false);
                    } catch (Exception e) {
                        SketchwareUtil.toastError("Error: Your code contains incorrectly nested parentheses");
                        break;
                    }

                    setText(prettifiedString);
                    break;

                case "Exit confirmation":
                    exit_confirmation_dialog = !item.isChecked();
                    item.setChecked(exit_confirmation_dialog);
                    setPreference("exit_confirmation_dialog", exit_confirmation_dialog);
                    break;
            }
            return true;
        });

        v.setOnClickListener(v1 -> popup.show());
    }

    public void setWordWrap(boolean b) {
        if (b) {
            if (editText.getParent() instanceof HorizontalScrollView
                    && scrollView.getChildAt(0) instanceof HorizontalScrollView hrz) {
                hrz.removeView(editText);
                scrollView.removeView(hrz);
                scrollView.addView(editText);
                editText.invalidate();
            }
        } else if (editText.getParent() instanceof ScrollView
                && scrollView.getChildAt(0) instanceof EditText) {
            HorizontalScrollView hrz = new HorizontalScrollView(context);
            hrz.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            hrz.setFillViewport(true);
            hrz.setOverScrollMode(OVER_SCROLL_NEVER);
            hrz.setHorizontalScrollBarEnabled(false);

            scrollView.removeView(editText);
            scrollView.addView(hrz);
            hrz.addView(editText);
            editText.invalidate();
        }

        setPreference("word_wrap", b);
    }

    private void initialize(int res) {
        View convertView = wB.a(context, this, res);

        editText = convertView.findViewById(EDITTEXT_RES);
        scrollView = convertView.findViewById(SCROLLVIEW_RES);

        //hide suggestions in edittext
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        //hide keyboard
        if (context instanceof Activity) {
            ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }
    }

    public void setTextSize(float size) {
        editText.setTextSize(size);

        setPreference("text_size", size);
    }

    public void increaseTextSize() {
        setTextSize(editText.getTextSize() + 2.0f);
    }

    public void decreaseTextSize() {
        setTextSize(editText.getTextSize() - 2.0f);
    }

    public CodeEditorEditText getEditText() {
        return editText;
    }

    public String getText() {
        return Helper.getText(editText);
    }

    public void setText(CharSequence c) {
        editText.setText(c);
    }

    public void start(List<ColorScheme> list) {
        //this method might look nonsense, don't worry it's just for
        //backwards compatibility. use startHighlighting(int) instead

        int color = list.get(0).color;
        startHighlighting(color);
    }

    public void startHighlighting(int type) {
        this.type = type;

        ColorTheme colorTheme = ColorTheme.getTheme(dark_theme);
        setPreference("dark_theme", dark_theme);

        if (type == ColorScheme.JAVA) {
            highlightList = ColorScheme.TYPE_JAVA(colorTheme);
        } else if (type == ColorScheme.XML) {
            highlightList = ColorScheme.TYPE_XML(colorTheme);
        } else {
            return;
        }

        initEditorColors(colorTheme);
        checkHighlight();
        editText.addTextChangedListener(this);
        setListeners();
    }

    private void initEditorColors(ColorTheme colorTheme) {
        scrollView.setBackgroundColor(colorTheme.BACKGROUND_COLOR);
        editText.setTextColor(colorTheme.TEXT_COLOR);
        editText.setLineHighlightColor(colorTheme.LINE_HIGHLIGHT_COLOR);
        editText.setLineNumbersColor(colorTheme.LINE_NUMBERS_COLOR);
    }

    private int getCurrentCursorLine() {
        int selectionStart = Selection.getSelectionStart(editText.getText());
        Layout layout = editText.getLayout();

        if (layout == null) {
            return -1;
        }

        if (!(selectionStart == -1)) {
            return layout.getLineForOffset(selectionStart);
        }

        return -1;
    }

    private String getLine(int lineNo, boolean deleting) {
        Editable e = editText.getEditableText();

        try {
            int end = editText.getLayout().getLineEnd(lineNo);
            return e.subSequence(
                    editText.getLayout().getLineStart(lineNo),
                    end - ((deleting && end == editText.length()) ? 0 : 1)
            ).toString();
        } catch (Exception ex) {
            return "";
        }
    }

    private boolean strOnlyContainsSpaces(String str) {
        char[] chars = str.toCharArray();

        if (chars.length < 1) {
            return false;
        }

        for (char c : chars) {
            if (!String.valueOf(c).equals(" ") && !String.valueOf(c).equals("   ")) //" ", "   "
                return false;
        }

        return true;
    }

    public void insert(String str) {
        editText.getText().insert(editText.getSelectionStart(), str);
    }

    private String safeSubstring(String main, int start, int end) {
        try {
            return main.substring(start, end);
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        beforeInfo.currentLine = getLine(getCurrentCursorLine(), true);
        beforeInfo.backspaceClicked = after + 1 == count;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //Indentation
        if (INDENTABLE) {

            String charSeq = s.toString();

            // if the last character entered is a line break:
            final String substring = charSeq.substring(start, start + count);
            if (substring.equals("\n")) {

                String previousLine = getLine(getCurrentCursorLine() - 1, false);

                String spacesToInsert = "";

                if (strOnlyContainsSpaces(previousLine)) {
                    // if the previous line only contains spaces,
                    // get all of it and indent the next line.
                    spacesToInsert = previousLine;
                } else {
                    // if not, get the spaces from the previous line using trim
                    // and use it to indent the next line.
                    spacesToInsert = previousLine.substring(0, previousLine.indexOf(previousLine.trim()));
                }

                // auto indent when user clicks enter inside {} or ()
                int selection = editText.getSelectionStart();
                if (
                        (safeSubstring(charSeq, selection - 2, selection - 1).equals("{")
                                && safeSubstring(charSeq, selection, selection + 1).equals("}"))
                                ||
                                (safeSubstring(charSeq, selection - 2, selection - 1).equals("(")
                                        && safeSubstring(charSeq, selection, selection + 1).equals(")"))
                ) {
                    // indent :)
                    insert(spacesToInsert + INDENT_STR + "\n" + spacesToInsert);

                    // restore cursor position
                    editText.setSelection(editText.getSelectionStart() - "\n".length() - spacesToInsert.length());

                    return;
                }

                // if the previous line ends with a { or (, increase the indentation
                if (previousLine.endsWith("{") || previousLine.endsWith("(")) {
                    spacesToInsert += INDENT_STR;
                }

                // insert the final indentation
                if (!TextUtils.isEmpty(spacesToInsert)) {
                    insert(spacesToInsert);
                }

            }

            //Complete brackets

            if (complete_brackets) {

                // checks the latest character entered to complete it:
                switch (substring) {
                    case "{":
                        insert("}");
                        editText.setSelection(editText.getSelectionStart() - 1);
                        break;

                    case "(":
                        insert(")");
                        editText.setSelection(editText.getSelectionStart() - 1);
                        break;

                    case "[":
                        insert("]");
                        editText.setSelection(editText.getSelectionStart() - 1);
                        break;
                }

                // Prevents user from closing a bracket that is already closed
                int selection = editText.getSelectionStart();
                switch (substring) {
                    case "}":
                        if (safeSubstring(charSeq, selection - 2, selection - 1).equals("{")) {
                            if (safeSubstring(charSeq, selection, selection + 1).equals("}")) {
                                editText.getEditableText().delete(selection, selection + 1);
                            }
                        }
                        break;

                    case ")":
                        if (safeSubstring(charSeq, selection - 2, selection - 1).equals("(")) {
                            if (safeSubstring(charSeq, selection, selection + 1).equals(")")) {
                                editText.getEditableText().delete(selection, selection + 1);
                            }
                        }
                        break;

                    case "]":
                        if (safeSubstring(charSeq, selection - 2, selection - 1).equals("[")) {
                            if (safeSubstring(charSeq, selection, selection + 1).equals("]")) {
                                editText.getEditableText().delete(selection, selection + 1);
                            }
                        }
                        break;
                }

            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        //Indentation with Backspace
        if (beforeInfo.backspaceClicked) {

            int selection = editText.getSelectionStart();

            if (strOnlyContainsSpaces(beforeInfo.currentLine)
                    && (
                    editText.getLayout().getLineEnd(getCurrentCursorLine()) - 1 == selection
                            ||
                            editText.length() == selection
            )
            ) {

                int deleteStart = editText.getLayout().getLineStart(getCurrentCursorLine()) - 1;

                if (deleteStart > -1) {
                    s.delete(deleteStart, selection);
                }

                beforeInfo.backspaceClicked = false;
            }
        }

        //Syntax highlighting (new, relatively optimized)
        EditText textView = editText;

        if (updateHighlight != null) {
            textView.removeCallbacks(updateHighlight);
            textView.postDelayed(updateHighlight, UPDATE_DELAY);
        }
    }

    private void setListeners() {
        final EditText textView = editText;

        if (textView != null) {

            // onFocusChange
            textView.setOnFocusChangeListener((v, hasFocus) -> {

                // Hide keyboard
                InputMethodManager imm = (InputMethodManager)
                        context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (!hasFocus)
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                if (updateHighlight != null) {
                    textView.removeCallbacks(updateHighlight);
                    textView.postDelayed(updateHighlight, UPDATE_DELAY);
                }
            });

            textView.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        private boolean keyboard;

                        // onGlobalLayout
                        @Override
                        public void onGlobalLayout() {
                            if (updateHighlight != null) {
                                int rootHeight = scrollView.getRootView().getHeight();
                                int height = scrollView.getHeight();

                                boolean shown = (((rootHeight - height) /
                                        (double) rootHeight) >
                                        KEYBOARD_RATIO);

                                if (shown != keyboard) {
                                    if (!shown) {
                                        textView.removeCallbacks(updateHighlight);
                                        textView.postDelayed(updateHighlight,
                                                UPDATE_DELAY);
                                    }

                                    keyboard = shown;
                                }
                            }
                        }
                    });
        }

        if (scrollView != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // onScrollChange
                scrollView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    if (updateHighlight != null) {
                        textView.removeCallbacks(updateHighlight);
                        textView.postDelayed(updateHighlight, UPDATE_DELAY);
                    }
                });

            } else {
                // onScrollChange
                scrollView.getViewTreeObserver()
                        .addOnScrollChangedListener(() -> {
                            if (updateHighlight != null) {
                                textView.removeCallbacks(updateHighlight);
                                textView.postDelayed(updateHighlight, UPDATE_DELAY);
                            }
                        });
            }
        }
    }

    private void checkHighlight() {
        EditText textView = editText;

        if (updateHighlight == null)
            updateHighlight = this::highlightText;

        textView.removeCallbacks(updateHighlight);
        textView.postDelayed(updateHighlight, UPDATE_DELAY);
    }

    public void onPause() {
        editText.removeCallbacks(updateHighlight);
    }

    //Optimized highlighting by Editor in Github
    private void highlightText() {
        EditText textView = editText;

        // Get visible extent
        int top = scrollView.getScrollY();
        int height = scrollView.getHeight();

        int line = textView.getLayout().getLineForVertical(top);
        int start = textView.getLayout().getLineStart(line);
        int first = textView.getLayout().getLineStart(line + 1);

        line = textView.getLayout().getLineForVertical(top + height);
        int end = textView.getLayout().getLineEnd(line);
        int last = (line == 0) ? end :
                textView.getLayout().getLineStart(line - 1);

        // Move selection if outside range
        if (textView.getSelectionStart() < start)
            textView.setSelection(first);

        if (textView.getSelectionStart() > end)
            textView.setSelection(last);

        // Get editable
        Editable editable = textView.getEditableText();

        // Get current spans
        ForegroundColorSpan[] spans =
                editable.getSpans(0, editable.length(),/*start, end,*/ ForegroundColorSpan.class);
        // Remove spans
        for (ForegroundColorSpan span : spans)
            editable.removeSpan(span);

        Matcher matcher;

        // Highlight it
        for (ColorScheme colorScheme : highlightList) {

            matcher = colorScheme.pattern.matcher(editable);
            matcher.region(start, end);
            while (matcher.find()) {
                ForegroundColorSpan span = new ForegroundColorSpan(colorScheme.color);
                editable.setSpan(span, matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    private static class BeforeTextChangeInfo {

        boolean backspaceClicked;
        String currentLine = "";
    }
}
