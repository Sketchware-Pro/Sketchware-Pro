package id.indosw.mod.codeviewrevo;

import android.widget.MultiAutoCompleteTextView;

public class KeywordTokenizer implements MultiAutoCompleteTextView.Tokenizer {

    public int findTokenStart(CharSequence charSequence, int cursor) {
        String sequenceStr = charSequence.toString().substring(0, cursor);
        int index = Math.max(0, Math.max(sequenceStr.lastIndexOf(" "), Math.max(sequenceStr.lastIndexOf("\n"), sequenceStr.lastIndexOf("("))));
        if (index == 0) {
            return 0;
        }
        return index + 1 < charSequence.length() ? index + 1 : index;
    }

    public int findTokenEnd(CharSequence charSequence, int cursor) {
        return charSequence.length();
    }

    public CharSequence terminateToken(CharSequence charSequence) {
        return charSequence;
    }
}