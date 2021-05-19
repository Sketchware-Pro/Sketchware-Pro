package id.indosw.mod.codeviewrevo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ReplacementSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.MultiAutoCompleteTextView;

import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeViewRevo extends AppCompatMultiAutoCompleteTextView {

    private static final Pattern PATTERN_LINE = Pattern.compile("(^.+$)+", 8);
    private static final Pattern PATTERN_TRAILING_WHITE_SPACE = Pattern.compile("[\\t ]+$", 8);
    private static final String TAG = "CodeViewRevo";
    private final float displayDensity;
    private final TextWatcher mEditorTextWatcher;
    private final SortedMap<Integer, Integer> mErrorHashSet;
    private final Map<Pattern, Integer> mSyntaxPatternMap;
    private final Handler mUpdateHandler;
    private final Runnable mUpdateRunnable;
    private boolean hasErrors;
    private MultiAutoCompleteTextView.Tokenizer mAutoCompleteTokenizer;
    private List<Character> mIndentCharacterList;
    private boolean mRemoveErrorsWhenTextChanged;
    private int mUpdateDelayTime;
    private boolean modified;
    private int tabWidth;
    private int tabWidthInCharacters;

    public CodeViewRevo(Context context) {
        super(context, null);
        this.mUpdateDelayTime = 500;
        this.modified = true;
        this.mUpdateHandler = new Handler();
        this.displayDensity = getResources().getDisplayMetrics().density;
        this.mErrorHashSet = new TreeMap();
        this.mSyntaxPatternMap = new HashMap();
        this.mIndentCharacterList = Arrays.asList('{', '+', '-', '*', '/', '=');
        this.mUpdateRunnable = new Runnable() {
            /* class id.indosw.mod.codeviewrevo.CodeViewRevo.AnonymousClass2 */

            public void run() {
                CodeViewRevo.this.highlightWithoutChange(CodeViewRevo.this.getText());
            }
        };
        this.mEditorTextWatcher = new TextWatcher() {
            /* class id.indosw.mod.codeviewrevo.CodeViewRevo.AnonymousClass3 */
            private int count;
            private int start;

            public void beforeTextChanged(CharSequence charSequence, int start2, int before, int count2) {
                this.start = start2;
                this.count = count2;
            }

            public void onTextChanged(CharSequence charSequence, int start2, int before, int count2) {
            }

            public void afterTextChanged(Editable editable) {
                CodeViewRevo.this.cancelHighlighterRender();
                if (CodeViewRevo.this.getSyntaxPatternsSize() > 0) {
                    CodeViewRevo.this.convertTabs(editable, this.start, this.count);
                    if (CodeViewRevo.this.modified) {
                        CodeViewRevo.this.mUpdateHandler.postDelayed(CodeViewRevo.this.mUpdateRunnable, (long) CodeViewRevo.this.mUpdateDelayTime);
                        if (CodeViewRevo.this.mRemoveErrorsWhenTextChanged) {
                            CodeViewRevo.this.removeAllErrorLines();
                        }
                    }
                }
            }
        };
        initEditorView();
    }

    public CodeViewRevo(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mUpdateDelayTime = 500;
        this.modified = true;
        this.mUpdateHandler = new Handler();
        this.displayDensity = getResources().getDisplayMetrics().density;
        this.mErrorHashSet = new TreeMap();
        this.mSyntaxPatternMap = new HashMap();
        this.mIndentCharacterList = Arrays.asList('{', '+', '-', '*', '/', '=');
        this.mUpdateRunnable = new Runnable() {
            /* class id.indosw.mod.codeviewrevo.CodeViewRevo.AnonymousClass2 */

            public void run() {
                CodeViewRevo.this.highlightWithoutChange(CodeViewRevo.this.getText());
            }
        };
        this.mEditorTextWatcher = new TextWatcher() {
            /* class id.indosw.mod.codeviewrevo.CodeViewRevo.AnonymousClass3 */
            private int count;
            private int start;

            public void beforeTextChanged(CharSequence charSequence, int start2, int before, int count2) {
                this.start = start2;
                this.count = count2;
            }

            public void onTextChanged(CharSequence charSequence, int start2, int before, int count2) {
            }

            public void afterTextChanged(Editable editable) {
                CodeViewRevo.this.cancelHighlighterRender();
                if (CodeViewRevo.this.getSyntaxPatternsSize() > 0) {
                    CodeViewRevo.this.convertTabs(editable, this.start, this.count);
                    if (CodeViewRevo.this.modified) {
                        CodeViewRevo.this.mUpdateHandler.postDelayed(CodeViewRevo.this.mUpdateRunnable, (long) CodeViewRevo.this.mUpdateDelayTime);
                        if (CodeViewRevo.this.mRemoveErrorsWhenTextChanged) {
                            CodeViewRevo.this.removeAllErrorLines();
                        }
                    }
                }
            }
        };
        initEditorView();
    }

    public CodeViewRevo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mUpdateDelayTime = 500;
        this.modified = true;
        this.mUpdateHandler = new Handler();
        this.displayDensity = getResources().getDisplayMetrics().density;
        this.mErrorHashSet = new TreeMap();
        this.mSyntaxPatternMap = new HashMap();
        this.mIndentCharacterList = Arrays.asList('{', '+', '-', '*', '/', '=');
        this.mUpdateRunnable = new Runnable() {
            /* class id.indosw.mod.codeviewrevo.CodeViewRevo.AnonymousClass2 */

            public void run() {
                CodeViewRevo.this.highlightWithoutChange(CodeViewRevo.this.getText());
            }
        };
        this.mEditorTextWatcher = new TextWatcher() {
            /* class id.indosw.mod.codeviewrevo.CodeViewRevo.AnonymousClass3 */
            private int count;
            private int start;

            public void beforeTextChanged(CharSequence charSequence, int start2, int before, int count2) {
                this.start = start2;
                this.count = count2;
            }

            public void onTextChanged(CharSequence charSequence, int start2, int before, int count2) {
            }

            public void afterTextChanged(Editable editable) {
                CodeViewRevo.this.cancelHighlighterRender();
                if (CodeViewRevo.this.getSyntaxPatternsSize() > 0) {
                    CodeViewRevo.this.convertTabs(editable, this.start, this.count);
                    if (CodeViewRevo.this.modified) {
                        CodeViewRevo.this.mUpdateHandler.postDelayed(CodeViewRevo.this.mUpdateRunnable, (long) CodeViewRevo.this.mUpdateDelayTime);
                        if (CodeViewRevo.this.mRemoveErrorsWhenTextChanged) {
                            CodeViewRevo.this.removeAllErrorLines();
                        }
                    }
                }
            }
        };
        initEditorView();
    }

    private void initEditorView() {
        if (this.mAutoCompleteTokenizer == null) {
            this.mAutoCompleteTokenizer = new KeywordTokenizer();
        }
        setTokenizer(this.mAutoCompleteTokenizer);
        setHorizontallyScrolling(true);
        setFilters(new InputFilter[]{new InputFilter() {
            /* class id.indosw.mod.codeviewrevo.CodeViewRevo.AnonymousClass1 */

            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (!CodeViewRevo.this.modified || end - start != 1 || start >= source.length() || dstart >= dest.length() || source.charAt(start) != '\n') {
                    return source;
                }
                return CodeViewRevo.this.autoIndent(source, dest, dstart, dend);
            }
        }});
        addTextChangedListener(this.mEditorTextWatcher);
    }

    /* access modifiers changed from: private */
    public CharSequence autoIndent(CharSequence source, Spanned dest, int dstart, int dend) {
        char c;
        Log.d("CodeViewRevo", "autoIndent: Auto Indent");
        String indent = "";
        int istart = dstart - 1;
        boolean dataBefore = false;
        int pt = 0;
        while (istart > -1 && (c = dest.charAt(istart)) != '\n') {
            if (!(c == ' ' || c == '\t')) {
                if (!dataBefore) {
                    if (this.mIndentCharacterList.contains(Character.valueOf(c))) {
                        pt--;
                    }
                    dataBefore = true;
                }
                if (c == '(') {
                    pt--;
                } else if (c == ')') {
                    pt++;
                }
            }
            istart--;
        }
        if (istart > -1) {
            char charAtCursor = dest.charAt(dstart);
            int istart2 = istart + 1;
            int iend = istart2;
            while (true) {
                if (iend >= dend) {
                    break;
                }
                char c2 = dest.charAt(iend);
                if (charAtCursor == '\n' || c2 != '/' || iend + 1 >= dend || dest.charAt(iend) != c2) {
                    if (c2 != ' ' && c2 != '\t') {
                        break;
                    }
                    iend++;
                } else {
                    iend += 2;
                    break;
                }
            }
            indent = indent + ((Object) dest.subSequence(istart2, iend));
        }
        if (pt < 0) {
            indent = indent + "\t";
        }
        return ((Object) source) + indent;
    }

    private void highlightSyntax(Editable editable) {
        if (!this.mSyntaxPatternMap.isEmpty()) {
            for (Pattern pattern : this.mSyntaxPatternMap.keySet()) {
                int color = this.mSyntaxPatternMap.get(pattern).intValue();
                Matcher m = pattern.matcher(editable);
                while (m.find()) {
                    createForegroundColorSpan(editable, m, color);
                }
            }
        }
    }

    private void highlightErrorLines(Editable editable) {
        if (!this.mErrorHashSet.isEmpty()) {
            int maxErrorLineValue = this.mErrorHashSet.lastKey().intValue();
            int lineNumber = 0;
            Matcher matcher = PATTERN_LINE.matcher(editable);
            while (matcher.find()) {
                if (this.mErrorHashSet.containsKey(Integer.valueOf(lineNumber))) {
                    createBackgroundColorSpan(editable, matcher, this.mErrorHashSet.get(Integer.valueOf(lineNumber)).intValue());
                }
                lineNumber++;
                if (lineNumber > maxErrorLineValue) {
                    return;
                }
            }
        }
    }

    private void createForegroundColorSpan(Editable editable, Matcher matcher, int color) {
        editable.setSpan(new ForegroundColorSpan(color), matcher.start(), matcher.end(), 33);
    }

    private void createBackgroundColorSpan(Editable editable, Matcher matcher, int color) {
        editable.setSpan(new BackgroundColorSpan(color), matcher.start(), matcher.end(), 33);
    }

    private Editable highlight(Editable editable) {
        try {
            if (editable.length() == 0) {
                return editable;
            }
            clearSpans(editable);
            highlightErrorLines(editable);
            highlightSyntax(editable);
            return editable;
        } catch (IllegalStateException e) {
            Log.e("CodeViewRevo", "Highlighter Error Message : " + e.getMessage());
        }
        return editable;
    }

    /* access modifiers changed from: private */
    public void highlightWithoutChange(Editable editable) {
        this.modified = false;
        highlight(editable);
        this.modified = true;
    }

    public void setTextHighlighted(CharSequence text) {
        if (text == null) {
            text = "";
        }
        cancelHighlighterRender();
        removeAllErrorLines();
        this.modified = false;
        setText(highlight(new SpannableStringBuilder(text)));
        this.modified = true;
    }

    public void setTabWidth(int characters) {
        if (this.tabWidthInCharacters != characters) {
            this.tabWidthInCharacters = characters;
            this.tabWidth = Math.round(getPaint().measureText("m") * ((float) characters));
        }
    }

    private void clearSpans(Editable editable) {
        int length = editable.length();
        ForegroundColorSpan[] foregroundSpans = (ForegroundColorSpan[]) editable.getSpans(0, length, ForegroundColorSpan.class);
        int i = foregroundSpans.length;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                break;
            }
            editable.removeSpan(foregroundSpans[i2]);
            i = i2;
        }
        BackgroundColorSpan[] backgroundSpans = (BackgroundColorSpan[]) editable.getSpans(0, length, BackgroundColorSpan.class);
        int i3 = backgroundSpans.length;
        while (true) {
            int i4 = i3 - 1;
            if (i3 > 0) {
                editable.removeSpan(backgroundSpans[i4]);
                i3 = i4;
            } else {
                return;
            }
        }
    }

    public void cancelHighlighterRender() {
        this.mUpdateHandler.removeCallbacks(this.mUpdateRunnable);
    }

    /* access modifiers changed from: private */
    public void convertTabs(Editable editable, int start, int count) {
        if (this.tabWidth >= 1) {
            String s = editable.toString();
            int stop = start + count;
            while (true) {
                int start2 = s.indexOf("\t", start);
                if (start2 > -1 && start2 < stop) {
                    editable.setSpan(new TabWidthSpan(), start2, start2 + 1, 33);
                    start = start2 + 1;
                } else {
                    return;
                }
            }
        }
    }

    public void setSyntaxPatternsMap(Map<Pattern, Integer> syntaxPatterns) {
        if (!this.mSyntaxPatternMap.isEmpty()) {
            this.mSyntaxPatternMap.clear();
        }
        this.mSyntaxPatternMap.putAll(syntaxPatterns);
    }

    public void addSyntaxPattern(Pattern pattern, int Color) {
        this.mSyntaxPatternMap.put(pattern, Integer.valueOf(Color));
    }

    public void removeSyntaxPattern(Pattern pattern) {
        this.mSyntaxPatternMap.remove(pattern);
    }

    public int getSyntaxPatternsSize() {
        return this.mSyntaxPatternMap.size();
    }

    public void resetSyntaxPatternList() {
        this.mSyntaxPatternMap.clear();
    }

    public void clearAutoIndentCharacterList() {
        this.mIndentCharacterList.clear();
    }

    public List<Character> getAutoIndentCharacterList() {
        return this.mIndentCharacterList;
    }

    public void setAutoIndentCharacterList(List<Character> characterList) {
        this.mIndentCharacterList = characterList;
    }

    public void addErrorLine(int lineNum, int color) {
        this.mErrorHashSet.put(Integer.valueOf(lineNum), Integer.valueOf(color));
        this.hasErrors = true;
    }

    public void removeErrorLine(int lineNum) {
        this.mErrorHashSet.remove(Integer.valueOf(lineNum));
        this.hasErrors = this.mErrorHashSet.size() > 0;
    }

    public void removeAllErrorLines() {
        this.mErrorHashSet.clear();
        this.hasErrors = false;
    }

    public int getErrorsSize() {
        return this.mErrorHashSet.size();
    }

    public String getTextWithoutTrailingSpace() {
        return PATTERN_TRAILING_WHITE_SPACE.matcher(getText()).replaceAll("");
    }

    public void setAutoCompleteTokenizer(MultiAutoCompleteTextView.Tokenizer tokenizer) {
        this.mAutoCompleteTokenizer = tokenizer;
    }

    public void setRemoveErrorsWhenTextChanged(boolean removeErrors) {
        this.mRemoveErrorsWhenTextChanged = removeErrors;
    }

    public void reHighlightSyntax() {
        highlightSyntax(getEditableText());
    }

    public void reHighlightErrors() {
        highlightErrorLines(getEditableText());
    }

    public boolean isHasError() {
        return this.hasErrors;
    }

    public int getUpdateDelayTime() {
        return this.mUpdateDelayTime;
    }

    public void setUpdateDelayTime(int time) {
        this.mUpdateDelayTime = time;
    }

    public void showDropDown() {
        getLocationOnScreen(new int[2]);
        getWindowVisibleDisplayFrame(new Rect());
        int position = getSelectionStart();
        Layout layout = getLayout();
        setDropDownVerticalOffset((int) (((float) ((layout.getLineForOffset(position) * 140) + 750)) / this.displayDensity));
        setDropDownHorizontalOffset((int) (layout.getPrimaryHorizontal(position) / this.displayDensity));
        super.showDropDown();
    }

    private final class TabWidthSpan extends ReplacementSpan {
        private TabWidthSpan() {
        }

        public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
            return CodeViewRevo.this.tabWidth;
        }

        public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        }
    }
}