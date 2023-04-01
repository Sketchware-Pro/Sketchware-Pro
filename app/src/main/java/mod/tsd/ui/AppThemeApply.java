package mod.tsd.ui;

import com.sketchware.remod.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import com.google.android.material.color.DynamicColors;

public class AppThemeApply {
	public static void setUpTheme(Context context){
		SharedPreferences MaterialThemeEnable = context.getSharedPreferences("MaterialThemeEnable",Context.MODE_PRIVATE);
    	SharedPreferences MaterialTheme = context.getSharedPreferences("MaterialTheme", Context.MODE_PRIVATE);
		
    	if (MaterialThemeEnable.getBoolean("MaterialThemeEnable",false)){
        	// DynamicColors will be automatically applied using SketchApplication Application class
        } else {
        	if (MaterialTheme.getString("MaterialTheme","Red") == "Red"){
        		context.setTheme(R.style.MaterialTheme);
        	}
        	else if (MaterialTheme.getString("MaterialTheme","Red") == "Red Dark") {
        		context.setTheme(R.style.MaterialThemeDark);
        	} else {
        		context.setTheme(R.style.MaterialTheme);
        	}
        }
    }
}