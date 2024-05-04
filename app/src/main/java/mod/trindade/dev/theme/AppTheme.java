package mod.trindade.dev.theme;

import mod.hilal.saif.activities.tools.ConfigActivity; 

import com.sketchware.remod.R;

import android.content.Context;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class AppTheme {

    public Context mContext;
    public ConfigActivity config;
    
    public ArrayList<String> themes = new ArrayList<>();
    
    public String appThemeName;
    public int appThemeID;
        
    public AppTheme (Context context) {
        mContext = context; 
        appThemeName = config.getAppTheme();    
    }
    
    public void appThemes () {
       addTheme("Sketchware-Default");
       addTheme("Florest");
       addTheme("Red florest");
    }
    
    public void addTheme (String themeName) {
        themes.add(themeName);
    }
    
    public ArrayList<String> getThemes () {
       return themes;
    }
    
       
    public int getTheme () {
       appThemeID = R.style.AppTheme;
       
       if (current("Sketchware-Default")) {
            appThemeID = R.style.AppTheme;
       } else if (current("Florest")) {
            appThemeID = R.style.AppTheme_Green;
       } else if (current("Red florest")) {
            appThemeID = R.style.AppTheme_Red;
       }
       return appThemeID;
    }
    
    public int getMainTheme () {
       appThemeID = R.style.AppTheme_Main;
              
       if (current("Sketchware-Default")) {
            appThemeID = R.style.AppTheme_Main;
       } else if (current("Florest")) {
            appThemeID = R.style.AppTheme_Green_Main;
       } else if (current("Red florest")) {
            appThemeID = R.style.AppTheme_Red_Main;
       }      
       return appThemeID;
    }
    
    public int getTranslucentTheme () {
       appThemeID = R.style.Theme_AppCompat_Light_NoActionBar_Translucent;
              
       if (current("Sketchware-Default")) {
            appThemeID = R.style.Theme_AppCompat_Light_NoActionBar_Translucent;
       } else if (current("Florest")) {
            appThemeID = R.style.Theme_Green_AppCompat_Light_NoActionBar_Translucent;
       } else if (current("Red florest")) {
            appThemeID = R.style.Theme_Red_AppCompat_Light_NoActionBar_Translucent;
       }      
       return appThemeID;
    }
    
    public boolean current (String val) {
       if (appThemeName.equals(val)){ return true; }
       return false;
    }   
}
