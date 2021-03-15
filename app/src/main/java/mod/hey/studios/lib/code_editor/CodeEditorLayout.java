package mod.hey.studios.lib.code_editor;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build.VERSION;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;

import a.a.a.wB;

public class CodeEditorLayout extends LinearLayout implements TextWatcher {
    private static final int DEFAULT_LAYOUT = 2131427798;
    private static final int EDITTEXT_RES = 2131232454;
    private static final boolean INDENTABLE = true;
    private static final String INDENT_STR = "    ";
    private static final double KEYBOARD_RATIO = 0.25D;
    private static final int NOWRAP_LAYOUT = 2131427805;
    private static final int SCROLLVIEW_RES = 2131232505;
    private static final int UPDATE_DELAY = 128;
    private final CodeEditorLayout.BeforeTextChangeInfo beforeInfo = new CodeEditorLayout.BeforeTextChangeInfo();
    private boolean complete_brackets;
    private final Context context;
    public boolean dark_theme;
    private CodeEditorEditText editText;
    private List<ColorScheme> highlightList;
    private ScrollView scrollView;
    private SharedPreferences sharedpref;
    private int type;
    private Runnable updateHighlight;
    private boolean word_wrap;

    private boolean enableddialog;

    public CodeEditorLayout(Context var1) {
        super(var1);
        this.context = var1;
        this.loadPreferences();
    }

    public CodeEditorLayout(Context var1, AttributeSet var2) {
        super(var1, var2);
        this.context = var1;
        this.loadPreferences();
    }

    public CodeEditorLayout(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.context = var1;
        this.loadPreferences();
    }

    public static void a(StringBuilder var0, int var1) {
        for(int var2 = 0; var2 < var1; ++var2) {
            var0.append('\t');
        }

    }

    private void checkHighlight() {
        CodeEditorEditText var1 = this.editText;
        if (this.updateHighlight == null) {
            this.updateHighlight = new Runnable() {
                @Override
                public void run() {
                    CodeEditorLayout.this.highlightText();
                }
            };
        }

        var1.removeCallbacks(this.updateHighlight);
        var1.postDelayed(this.updateHighlight, UPDATE_DELAY);
    }

    private int getCurrentCursorLine() {
        int var1 = Selection.getSelectionStart(this.editText.getText());
        Layout var2 = this.editText.getLayout();
        if (var2 == null) {
            var1 = -1;
        } else if (var1 != -1) {
            var1 = var2.getLineForOffset(var1);
        }

        return var1;
    }

    private String getLine(int var1, boolean var2) {
        Editable var3 = this.editText.getEditableText();

        String var10;
        label39: {
            int var4;
            int var5;
            try {
                var4 = this.editText.getLayout().getLineEnd(var1);
                var5 = this.editText.getLayout().getLineStart(var1);
            } catch (Exception var8) {
                break label39;
            }

            byte var9;
            label30: {
                label29: {
                    if (var2) {
                        try {
                            if (var4 == this.editText.length()) {
                                break label29;
                            }
                        } catch (Exception var7) {
                            break label39;
                        }
                    }

                    var9 = 1;
                    break label30;
                }

                var9 = 0;
            }

            try {
                var10 = var3.subSequence(var5, var4 - var9).toString();
                return var10;
            } catch (Exception ignored) {
            }
        }

        var10 = "";
        return var10;
    }

    private void highlightText() {
        CodeEditorEditText var1 = this.editText;
        int var2 = this.scrollView.getScrollY();
        int var3 = this.scrollView.getHeight();
        int var4 = var1.getLayout().getLineForVertical(var2);
        int var5 = var1.getLayout().getLineStart(var4);
        var4 = var1.getLayout().getLineStart(var4 + 1);
        var3 = var1.getLayout().getLineForVertical(var2 + var3);
        var2 = var1.getLayout().getLineEnd(var3);
        if (var3 == 0) {
            var3 = var2;
        } else {
            var3 = var1.getLayout().getLineStart(var3 - 1);
        }

        if (var1.getSelectionStart() < var5) {
            var1.setSelection(var4);
        }

        if (var1.getSelectionStart() > var2) {
            var1.setSelection(var3);
        }

        Editable var10 = var1.getEditableText();
        var3 = var10.length();

        ForegroundColorSpan[] var11 = (ForegroundColorSpan[])var10.getSpans(0, var3, ForegroundColorSpan.class);

        for(var3 = 0; var3 < var11.length; ++var3) {
            var10.removeSpan(var11[var3]);
        }

        for (Object o : (Collection) this.highlightList) {
            ColorScheme var8 = (ColorScheme) o;
            Matcher var12 = var8.pattern.matcher(var10);
            var12.region(var5, var2);

            while (var12.find()) {
                var10.setSpan(new ForegroundColorSpan(var8.color), var12.start(), var12.end(), 33);
            }
        }

    }

    private void initEditorColors(ColorTheme var1) {
        this.scrollView.setBackgroundColor(var1.BACKGROUND_COLOR);
        this.editText.setTextColor(var1.TEXT_COLOR);
        this.editText.setLineHighlightColor(var1.LINE_HIGHLIGHT_COLOR);
        this.editText.setLineNumbersColor(var1.LINE_NUMBERS_COLOR);
    }

    private void initialize(int var1) {
        View var2 = wB.a(this.context, this, var1);
        this.editText = (CodeEditorEditText)var2.findViewById(EDITTEXT_RES);
        this.scrollView = (ScrollView)var2.findViewById(SCROLLVIEW_RES);
        this.editText.setRawInputType(524433);
        if (this.context instanceof Activity) {
            ((Activity)this.context).getWindow().setSoftInputMode(2);
        }

    }

    public static String j(String var0) {
        StringBuilder var1 = new StringBuilder(4096);
        char[] var15 = var0.toCharArray();
        int var2 = var15.length;
        int var3 = 0;
        boolean var4 = false;
        boolean var5 = false;
        boolean var6 = false;
        int var7 = 0;
        boolean var8 = false;

        boolean var18;
        for(boolean var9 = false; var3 < var2; var8 = var18) {
            boolean var16;
            int var17;
            label85: {
                char var10 = var15[var3];
                if (var4) {
                    if (var10 == '\n') {
                        var1.append(var10);
                        a(var1, var7);
                        var4 = false;
                    } else {
                        var1.append(var10);
                    }
                } else {
                    int var11;
                    char var12;
                    if (var5) {
                        label82: {
                            if (var10 == '*') {
                                var11 = var3 + 1;
                                var12 = var15[var11];
                                if (var12 == '/') {
                                    var1.append(var10);
                                    var1.append(var12);
                                    var3 = var11;
                                    var5 = false;
                                    break label82;
                                }
                            }

                            var1.append(var10);
                        }
                    } else if (var6) {
                        var1.append(var10);
                        var6 = false;
                    } else if (var10 == '\\') {
                        var1.append(var10);
                        var6 = true;
                    } else if (var8) {
                        if (var10 == '\'') {
                            var1.append(var10);
                            var8 = false;
                        } else {
                            var1.append(var10);
                        }
                    } else if (var9) {
                        if (var10 == '"') {
                            var1.append(var10);
                            var9 = false;
                        } else {
                            var1.append(var10);
                        }
                    } else {
                        label91: {
                            if (var10 == '/') {
                                var11 = var3 + 1;
                                var12 = var15[var11];
                                if (var12 == '/') {
                                    var1.append(var10);
                                    var1.append(var12);
                                    var4 = true;
                                    var3 = var11;
                                    break label91;
                                }

                                if (var12 == '*') {
                                    var1.append(var10);
                                    var1.append(var12);
                                    var5 = true;
                                    var3 = var11;
                                    break label91;
                                }
                            }

                            if (var10 != '\n') {
                                if (var10 == '\'') {
                                    var8 = true;
                                }

                                if (var10 == '"') {
                                    var9 = true;
                                }

                                if (var10 == '{') {
                                    var11 = var7 + 1;
                                } else {
                                    var11 = var7;
                                }

                                var7 = var11;
                                if (var10 == '}') {
                                    --var11;
                                    var7 = var11;
                                    if (var1.charAt(-1 + var1.length()) == '\t') {
                                        var1.deleteCharAt(-1 + var1.length());
                                        var7 = var11;
                                    }
                                }

                                var1.append(var10);
                                boolean var13 = var5;
                                boolean var14 = var8;
                                var17 = var3;
                                var5 = var4;
                                var16 = var13;
                                var8 = var6;
                                var6 = var14;
                                break label85;
                            }

                            var1.append(var10);
                            a(var1, var7);
                        }
                    }
                }

                var16 = var6;
                var6 = var8;
                var8 = var16;
                var16 = var5;
                var5 = var4;
                var17 = var3;
            }

            var18 = var6;
            var3 = var17 + 1;
            var4 = var5;
            var5 = var16;
            var6 = var8;
        }

        return var1.toString();
    }

    private void loadPreferences() {
        this.sharedpref = this.context.getSharedPreferences("code_editor_pref", 0);
        this.dark_theme = this.sharedpref.getBoolean("dark_theme", false);
        this.word_wrap = this.sharedpref.getBoolean("word_wrap", false);
        float var1 = this.sharedpref.getFloat("text_size", 12.0F);
        this.complete_brackets = this.sharedpref.getBoolean("complete_brackets", true);
        this.enableddialog = this.sharedpref.getBoolean("enable_dialog",false);
        int var2;
        if (this.word_wrap) {
            var2 = DEFAULT_LAYOUT;
        } else {
            var2 = NOWRAP_LAYOUT;
        }

        this.initialize(var2);
        this.setTextSize(var1);
    }
    private void onOptionsItemSelected(MenuItem var1) {
        String var2 = var1.getTitle().toString();
        if (var2.equals("Font size")) {
            Builder var10 = new Builder(this.context);
            final NumberPicker var7 = new NumberPicker(this.context);
            var7.setMinValue(5);
            var7.setMaxValue(20);
            var7.setValue((int)this.editText.getTextSize());
            var7.setDescendantFocusability(393216);
            var10.setView(var7);
            var10.setTitle("Select font size");
            var10.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface var112, int var21) {
                        CodeEditorLayout.this.setTextSize((float) var7.getValue());
                        var112.dismiss();
                    }
                });
            var10.setNegativeButton("CANCEL", null);
            var10.create().show();
        } else {
            boolean var3;
            switch (var2) {
                case "Word wrap":
                    var3 = !var1.isChecked();

                    var1.setChecked(var3);
                    this.setWordWrap(var1.isChecked());
                    break;
                case "Complete brackets":
                    var3 = !var1.isChecked();

                    var1.setChecked(var3);
                    this.complete_brackets = var1.isChecked();
                    this.setPreference("complete_brackets", this.complete_brackets);
                    break;
                case "Dark theme":
                    var3 = !var1.isChecked();

                    var1.setChecked(var3);
                    this.dark_theme = var1.isChecked();
                    this.editText.removeTextChangedListener(this);
                    this.startHighlighting(this.type);
                    break;
                case "Save Dialog":
                    var3 = !var1.isChecked();
                    var1.setChecked(var3);
                    this.enableddialog = var1.isChecked();
                    this.setPreference("enable_dialog", this.enableddialog);
                    break;
                case "Pretty print":
                    StringBuilder var11 = new StringBuilder();
                    String[] var8 = this.getText().split("\n");

                    for (String s : var8) {
                        String var5 = s;
                        var5 = (var5 + "X").trim();
                        var11.append(var5.substring(0, var5.length() - 1));
                        var11.append("\n");
                    }

                    String var9 = var11.toString();

                    try {
                        var9 = j(j(var9));
                    } catch (Exception var6) {
                        Toast.makeText(this.context, "Error: Your code contains incorrectly nested parentheses", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    this.setText(var9);
                    break;
            }
      
         }
    }

    private String safeSubstring(String var1, int var2, int var3) {
        try {
            var1 = var1.substring(var2, var3);
        } catch (Exception var4) {
            var1 = "";
        }

        return var1;
    }

    private void setListeners() {
        CodeEditorEditText var1 = this.editText;
        if (var1 != null) {
            var1.setOnFocusChangeListener(new OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        InputMethodManager var3 = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (!hasFocus) {
                            var3.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }

                        if (updateHighlight != null) {
                            v.removeCallbacks(updateHighlight);
                            v.postDelayed(updateHighlight, UPDATE_DELAY);
                        }

                    }
                });
            var1.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                    private boolean keyboard;

                    @Override
                    public void onGlobalLayout() {
                        if (updateHighlight != null) {
                            int var1 = scrollView.getRootView().getHeight();
                            boolean var2;
                            var2 = !((double) (var1 - scrollView.getHeight()) / (double) var1 <= KEYBOARD_RATIO);

                            if (!var2) {
                                if (!this.keyboard) {
                                    return;
                                }
                            } else if (this.keyboard) {
                                return;
                            }

                            if (!var2) {
                                removeCallbacks(updateHighlight);
                                postDelayed(updateHighlight, (long)UPDATE_DELAY);
                            }

                            this.keyboard = var2;
                        }

                    }
                });
        }

        if (this.scrollView != null) {
            if (VERSION.SDK_INT >= 23) {
                this.scrollView.setOnScrollChangeListener(new OnScrollChangeListener() {
                        @Override
                        public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                            if (updateHighlight != null) {
                                removeCallbacks(updateHighlight);
                                postDelayed(updateHighlight, (long)UPDATE_DELAY);
                            }

                        }
                    });
            } else {
                this.scrollView.getViewTreeObserver().addOnScrollChangedListener(new OnScrollChangedListener() {
                        @Override
                        public void onScrollChanged() {
                            if (updateHighlight != null) {
                                removeCallbacks(updateHighlight);
                                postDelayed(updateHighlight, (long)UPDATE_DELAY);
                            }

                        }
                    });
            }
        }

    }

    private void setPreference(String var1, float var2) {
        this.sharedpref.edit().putFloat(var1, var2).commit();
    }

    private void setPreference(String var1, boolean var2) {
        this.sharedpref.edit().putBoolean(var1, var2).commit();
    }

    private boolean strOnlyContainsSpaces(String var1) {
        char[] var5 = var1.toCharArray();
        boolean var2;
        if (var5.length < 1) {
            var2 = false;
        } else {
            int var3 = 0;

            while(true) {
                if (var3 >= var5.length) {
                    var2 = true;
                    break;
                }

                char var4 = var5[var3];
                if (!String.valueOf(var4).equals(" ") && !String.valueOf(var4).equals("   ") && !String.valueOf(var4).equals("\t")) {
                    var2 = false;
                    break;
                }

                ++var3;
            }
        }

        return var2;
    }

    @Override
    public void afterTextChanged(Editable var1) {
        if (this.beforeInfo.backspaceClicked) {
            int var2 = this.editText.getSelectionStart();
            if (this.strOnlyContainsSpaces(this.beforeInfo.currentLine) && (this.editText.getLayout().getLineEnd(this.getCurrentCursorLine()) - 1 == var2 || this.editText.length() == var2)) {
                int var3 = this.editText.getLayout().getLineStart(this.getCurrentCursorLine()) - 1;
                if (var3 > -1) {
                    var1.delete(var3, var2);
                }

                this.beforeInfo.backspaceClicked = false;
            }
        }

        CodeEditorEditText var4 = this.editText;
        if (this.updateHighlight != null) {
            var4.removeCallbacks(this.updateHighlight);
            var4.postDelayed(this.updateHighlight, (long)UPDATE_DELAY);
        }

    }

    @Override
    public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {
        this.beforeInfo.currentLine = this.getLine(this.getCurrentCursorLine(), true);
        boolean var5;
        var5 = var4 + 1 == var3;

        this.beforeInfo.backspaceClicked = var5;
    }

    public void decreaseTextSize() {
        this.setTextSize(this.editText.getTextSize() - 2.0F);
    }

    public CodeEditorEditText getEditText() {
        return this.editText;
    }

    public String getText() {
        return this.editText.getText().toString();
    }

    public void increaseTextSize() {
        this.setTextSize(this.editText.getTextSize() + 2.0F);
    }

    public void insert(String var1) {
        this.editText.getText().insert(this.editText.getSelectionStart(), var1);
    }

    public void onCreateOptionsMenu(View view) {
        final PopupMenu var2 = new PopupMenu(this.context, view);
        Menu var3 = var2.getMenu();
        var3.add("Font size").setShowAsAction(0);
        var3.add("Word wrap").setCheckable(true).setChecked(this.word_wrap).setShowAsAction(0);
        var3.add("Complete brackets").setCheckable(true).setChecked(this.complete_brackets).setShowAsAction(0);
        var3.add("Dark theme").setCheckable(true).setChecked(this.dark_theme).setShowAsAction(0);
        var3.add("Pretty print").setShowAsAction(0);
        var3.add("Save Dialog").setCheckable(true).setChecked(enableddialog).setShowAsAction(0);
        var2.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    onOptionsItemSelected(item);
                    return true;
                }
            });
        view.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    var2.show();
                }
            });
    }

    public void onPause() {
        this.editText.removeCallbacks(this.updateHighlight);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String var5 = s.toString();
        final String substring = var5.substring(start, start + count);
        if (substring.equals("\n")) {
            String var6 = this.getLine(this.getCurrentCursorLine() - 1, false);
            String var8;
            if (this.strOnlyContainsSpaces(var6)) {
                var8 = var6;
            } else {
                var8 = var6.substring(0, var6.indexOf(var6.trim()));
            }

            before = this.editText.getSelectionStart();
            if (this.safeSubstring(var5, before - 2, before - 1).equals("{") && this.safeSubstring(var5, before, before + 1).equals("}") || this.safeSubstring(var5, before - 2, before - 1).equals("(") && this.safeSubstring(var5, before, before + 1).equals(")")) {
                this.insert(var8 + INDENT_STR + "\n" + var8);
                this.editText.setSelection(this.editText.getSelectionStart() - "\n".length() - var8.length());
                return;
            }

            String var7;
            label60: {
                if (!var6.endsWith("{")) {
                    var7 = var8;
                    if (!var6.endsWith("(")) {
                        break label60;
                    }
                }

                var7 = var8 + INDENT_STR;
            }

            if (!TextUtils.isEmpty(var7)) {
                this.insert(var7);
            }
        }

        if (this.complete_brackets) {
            switch (substring) {
                case "{":
                    this.insert("}");
                    this.editText.setSelection(this.editText.getSelectionStart() - 1);
                    break;
                case "(":
                    this.insert(")");
                    this.editText.setSelection(this.editText.getSelectionStart() - 1);
                    break;
                case "[":
                    this.insert("]");
                    this.editText.setSelection(this.editText.getSelectionStart() - 1);
                    break;
            }

            before = this.editText.getSelectionStart();
            if (substring.equals("}")) {
                if (this.safeSubstring(var5, before - 2, before - 1).equals("{") && this.safeSubstring(var5, before, before + 1).equals("}")) {
                    this.editText.getEditableText().delete(before, before + 1);
                }
            } else if (substring.equals(")")) {
                if (this.safeSubstring(var5, before - 2, before - 1).equals("(") && this.safeSubstring(var5, before, before + 1).equals(")")) {
                    this.editText.getEditableText().delete(before, before + 1);
                }
            } else if (substring.equals("]") && this.safeSubstring(var5, before - 2, before - 1).equals("[") && this.safeSubstring(var5, before, before + 1).equals("]")) {
                this.editText.getEditableText().delete(before, before + 1);
            }
        }

    }

    public void setText(CharSequence var1) {
        this.editText.setText(var1);
    }

    public void setTextSize(float var1) {
        this.editText.setTextSize(var1);
        this.setPreference("text_size", var1);
    }

    public void setWordWrap(boolean var1) {
        HorizontalScrollView var2;
        if (var1) {
            if (this.editText.getParent() instanceof HorizontalScrollView && this.scrollView.getChildAt(0) instanceof HorizontalScrollView) {
                var2 = (HorizontalScrollView)this.scrollView.getChildAt(0);
                var2.removeView(this.editText);
                this.scrollView.removeView(var2);
                this.scrollView.addView(this.editText);
                this.editText.invalidate();
            }
        } else if (this.editText.getParent() instanceof ScrollView && this.scrollView.getChildAt(0) instanceof EditText) {
            var2 = new HorizontalScrollView(this.context);
            var2.setLayoutParams(new LayoutParams(-1, -2));
            var2.setFillViewport(true);
            var2.setOverScrollMode(2);
            var2.setHorizontalScrollBarEnabled(false);
            this.scrollView.removeView(this.editText);
            this.scrollView.addView(var2);
            var2.addView(this.editText);
            this.editText.invalidate();
        }

        this.setPreference("word_wrap", var1);
    }

    public void start(List<ColorScheme> var1) {
        this.startHighlighting(((ColorScheme)var1.get(0)).color);
    }

    public void startHighlighting(int var1) {
        this.type = var1;
        ColorTheme var2 = ColorTheme.getTheme(this.dark_theme);
        this.setPreference("dark_theme", this.dark_theme);
        if (var1 == 1) {
            this.highlightList = ColorScheme.TYPE_JAVA(var2);
        } else {
            if (var1 != 2) {
                return;
            }

            this.highlightList = ColorScheme.TYPE_XML(var2);
        }

        this.initEditorColors(var2);
        this.checkHighlight();
        this.editText.addTextChangedListener(this);
        this.setListeners();
    }

    private class BeforeTextChangeInfo {
        boolean backspaceClicked;
        String currentLine;
    }
}


