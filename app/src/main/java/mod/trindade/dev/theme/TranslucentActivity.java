package mod.trindade.dev.theme;

import androidx.appcompat.app.AppCompatActivity; 
import android.os.Bundle;

import mod.trindade.dev.theme.AppTheme;

public class TranslucentActivity extends AppCompatActivity {

    AppTheme appThemeHelper;

    @Override
    protected void onCreate(Bundle saved) {
       super.onCreate(saved);
       appThemeHelper = new AppTheme(this);
       setTheme(appThemeHelper.getTranslucentTheme());
    }
}