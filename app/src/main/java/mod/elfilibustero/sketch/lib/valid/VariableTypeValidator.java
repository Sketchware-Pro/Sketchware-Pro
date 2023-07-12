package mod.elfilibustero.sketch.lib.valid;

import a.a.a.MB;
import android.content.Context;
import java.util.regex.Pattern;
import com.google.android.material.textfield.TextInputLayout;

public class VariableTypeValidator extends MB {
  
  private Pattern PATTERN_TYPE = Pattern.compile("^([a-zA-Z]+)(<([a-zA-Z<> ,]+)>)?$");
  
  public VariableTypeValidator(Context context, TextInputLayout textInputLayout) {
    super(context, textInputLayout);
  }
  
  @Override
  public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
    String variableName = charSequence.toString().trim();
    if (!PATTERN_TYPE.matcher(variableName).matches()) {
      this.b.setErrorEnabled(true);
      this.b.setError("Error invalid variable type");
      this.d = false;
      return;
    }
    this.b.setErrorEnabled(false);
    this.b.setError(null);
    this.d = true;
  }
  
  public boolean isValid(){
    return this.b();
  }

}
