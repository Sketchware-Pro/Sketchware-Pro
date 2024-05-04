package mod.trindade.dev.theme;

import androidx.appcompat.app.AppCompatActivity; 
import androidx.activity.EdgeToEdge;

import android.os.Bundle;

import mod.trindade.dev.theme.AppTheme;

public class ThemedActivity extends AppCompatActivity {

    AppTheme appThemeHelper;

    @Override
    protected void onCreate(Bundle saved) {
       super.onCreate(saved);
       appThemeHelper = new AppTheme(this);
       EdgeToEdge.enable(this);
       setTheme(appThemeHelper.getTheme());
    }
}
