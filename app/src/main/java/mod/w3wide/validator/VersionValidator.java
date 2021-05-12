package mod.w3wide.validator;

import android.content.Context;
import android.text.Spanned;
import com.google.android.material.textfield.TextInputLayout;
import java.util.regex.Pattern;
import a.a.a.MB;

public class VersionValidator extends MB {
  
    private Pattern mExtra = Pattern.compile("^[a-zA-Z0-9_]*");
    
    public VersionValidator(Context context, TextInputLayout textInputLayout) {
        super(context, textInputLayout);
    }

    public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        return null;
    }

  public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    String mChar = charSequence.toString();
    if (this.mExtra.matcher(mChar).matches()) {
      this.b.setErrorEnabled(false);
      this.d = true;
    } else if (mChar.contains(" ")) {
      this.b.setErrorEnabled(true);
      this.b.setError("Space is not allowed here to prevent crash.");
      this.d = false;
    } else {
      this.b.setErrorEnabled(true);
      this.b.setError("only use letters (a-zA-Z), numbers and special character (_).");
      this.d = false;
    }
  }
}
