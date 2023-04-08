package mod.tsd.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import java.lang.String;

import com.google.android.material.color.MaterialColors;

import com.sketchware.remod.R;

public class MaterialColorsHelper {
	/* MaterialColorsHelper */
	public static String colorPrimary = "#A13F28";
	public static String colorOnPrimary = "#FFFFFF";
	public static String colorPrimaryContainer = "#FFDAD2";
	public static String colorOnPrimaryContainer = "#3D0600";
	public static String colorSurface = "#40000E";
	public static String colorOnSurface = "#FFDADB";
	
	
	public static String getMaterialColor(Context context,int res,String error){
		return String.format("#%08X", (0xFFFFFFFF & MaterialColors.getColor(context,res,error)));
	}
	public static String getMaterialColor(Context context,int res){
		return String.format("#%08X", (0xFFFFFFFF & MaterialColors.getColor(context,res,"Passed color in parameter doesn't exists.")));
	}
	public static int getMaterialColorInt(Context context,int res){
		return MaterialColors.getColor(context,res,"Passed color in parameter doesn't exists.");
	}
	
	public static String setColorTransparency(String hex,String transparency,Context context){
		return "#".concat(transparency).concat(hex.substring((int)(3), (int)(hex.length())));
	}
	
	public static void setUpToolbarNavigationIconColor(Context context,Drawable icon){
		icon.setTint(MaterialColorsHelper.getMaterialColorInt(context,R.attr.colorOnPrimary));
	}
}