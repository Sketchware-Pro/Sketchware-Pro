package mod.tsd.ui;

import android.content.Context;

import java.lang.String;

import com.google.android.material.color.MaterialColors;

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
	
	public static String setColorTransparency(String hex,String transparency){
		return "#".concat(transparency).concat(hex.substring((int)(1), (int)(hex.length())));
	}
}