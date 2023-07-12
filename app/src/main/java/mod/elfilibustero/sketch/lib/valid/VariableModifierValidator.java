package mod.elfilibustero.sketch.lib.valid;

import a.a.a.MB;
import android.content.Context;
import com.google.android.material.textfield.TextInputLayout;
import java.util.regex.Pattern;

public class VariableModifierValidator extends MB {
  
  private final String PATTERN_MODIFIER = "\\b(private\\s?|public\\s?|protected\\s?)?(static\\s?)?(final\\s?)?\\b";
	
  public VariableModifierValidator(Context context, TextInputLayout textInputLayout) {
    super(context, textInputLayout);
  }
  
  @Override
  public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
    String input = charSequence.toString().toLowerCase();
    String trimmedInput = input.trim();
    String[] words = trimmedInput.split("\\s+");
    String reconsInput = String.join(" ", words);
    if (!input.equals(reconsInput)) {
      this.b.setErrorEnabled(true);
      this.b.setError("Invalid input");
      this.d = false;
      return;
    }
    Pattern modifierPattern = Pattern.compile(PATTERN_MODIFIER);
    if (!modifierPattern.matcher(input).matches()) {
      this.b.setErrorEnabled(true);
      this.b.setError("Error invalid modifier");
      this.d = false;
      return;
    }
    this.b.setErrorEnabled(false);
    this.d = true;
  }
  
  public boolean isValid(){
    return this.b();
  }
}
