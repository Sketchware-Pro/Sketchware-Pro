package pro.sketchware.lib.highlighter;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;

import com.besome.sketch.editor.LogicEditorActivity;

import java.util.List;
import java.util.regex.Matcher;

/**
 * A Helper class used in {@link LogicEditorActivity}
 * to (currently) highlight add source directly blocks.
 */
public class SimpleHighlighter {

    private static final int MAX_HIGHLIGHT_LENGTH = 15000;
    private static final long DEBOUNCE_DELAY_MS = 150L;

    private final EditText mEditor;
    private final List<SyntaxScheme> syntaxList;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private boolean mIsHighlighting = false;

    private final Runnable mHighlightRunnable = new Runnable() {
        @Override
        public void run() {
            Editable text = mEditor.getText();
            if (text == null) {
                return;
            }
            if (text.length() > MAX_HIGHLIGHT_LENGTH) {
                removeSpans(text, ForegroundColorSpan.class);
                return;
            }
            mIsHighlighting = true;
            try {
                removeSpans(text, ForegroundColorSpan.class);
                createHighlightSpans(syntaxList, text);
            } finally {
                mIsHighlighting = false;
            }
        }
    };

    public SimpleHighlighter(EditText editor) {
        mEditor = editor;
        syntaxList = SyntaxScheme.JAVA();
        init();
    }

    private void init() {
        mHandler.post(mHighlightRunnable);

        mEditor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mIsHighlighting) {
                    return;
                }
                mHandler.removeCallbacks(mHighlightRunnable);
                mHandler.postDelayed(mHighlightRunnable, DEBOUNCE_DELAY_MS);
            }
        });
    }

    private void createHighlightSpans(List<SyntaxScheme> syntaxList, Editable editable) {
        for (SyntaxScheme scheme : syntaxList) {
            for (Matcher m = scheme.pattern.matcher(editable); m.find(); ) {
                if (scheme == scheme.getPrimarySyntax()) {
                    editable.setSpan(new ForegroundColorSpan(scheme.color), m.start(), m.end() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    editable.setSpan(new ForegroundColorSpan(scheme.color), m.start(), m.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
    }

    private void removeSpans(Editable editable, Class<? extends CharacterStyle> type) {
        CharacterStyle[] spans = editable.getSpans(0, editable.length(), type);
        for (CharacterStyle span : spans) {
            editable.removeSpan(span);
        }
    }
}
