package mod.w3wide.highlighter;

import android.text.Editable;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.Spanned;
import android.text.TextWatcher;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;

public class SimpleHighlighter {

  private EditText mEditor;
  private List<SyntaxScheme> syntaxList = new ArrayList();

  public SimpleHighlighter(EditText editor) {
    this.mEditor = editor;
    this.syntaxList = SyntaxScheme.JAVA();
    init();
  }

  public SimpleHighlighter(EditText editor, List<SyntaxScheme> syntaxList) {
    this.mEditor = editor;
    this.syntaxList = syntaxList;
    init();
  }

  private void init() {
    removeSpans(mEditor.getText(), ForegroundColorSpan.class);
    createHighlightSpans(syntaxList, mEditor.getText());

    mEditor.addTextChangedListener(new TextWatcher() {

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {}

      @Override
      public void afterTextChanged(Editable editable) {
        removeSpans(editable, ForegroundColorSpan.class);
        createHighlightSpans(syntaxList, editable);
      }

    });
  }

  private void createHighlightSpans(List<SyntaxScheme> syntaxList, Editable editable) {
    for(SyntaxScheme scheme : syntaxList) {
      for(Matcher m = scheme.pattern.matcher(editable); m.find();) {
        if (scheme == scheme.getPrimarySyntax()) {
          editable.setSpan(new ForegroundColorSpan(scheme.color), m.start(), m.end()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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
