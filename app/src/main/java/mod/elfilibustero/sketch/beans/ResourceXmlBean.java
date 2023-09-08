package mod.elfilibustero.sketch.beans;

import com.sketchware.remod.R;
import mod.hey.studios.util.Helper;

public class ResourceXmlBean {
	public static final int RES_TYPE_STRING = 0;
	public static final int RES_TYPE_COLOR = 1;
	public static final int RES_TYPE_STYLE = 2;
	public static final int RES_TYPE_STYLE_ITEM = 3;
	
	public static int getXmlIcon(int resType) {
		switch (resType) {
			case RES_TYPE_STRING:
			return R.drawable.widget_text_view;
			case RES_TYPE_COLOR:
			return R.drawable.color_palette_48;
			case RES_TYPE_STYLE:
			case RES_TYPE_STYLE_ITEM:
			return R.drawable.collage_48;
			default:
			return 0;
		}
	}
	
	public static int getXmlResDesc(int resType) {
		switch (resType) {
			case RES_TYPE_STRING:
			return R.string.manage_xml_description_string;
			case RES_TYPE_COLOR:
			return R.string.manage_xml_description_color;
			case RES_TYPE_STYLE:
			case RES_TYPE_STYLE_ITEM:
			return R.string.manage_xml_description_style;
			default:
			return 0;
		}
	}
	
	public static int getXmlResName(int resType) {
		switch (resType) {
			case RES_TYPE_STRING:
			return R.string.manage_xml_title_string;
			case RES_TYPE_COLOR:
			return R.string.manage_xml_title_color;
			case RES_TYPE_STYLE:
			case RES_TYPE_STYLE_ITEM:
			return R.string.manage_xml_title_style;
			default:
			return 0;
			
		}
	}

	public static String getResFileName(int resType) {
		switch (resType) {
			case RES_TYPE_STRING:
			return "strings";
			case RES_TYPE_COLOR:
			return "colors";
			case RES_TYPE_STYLE:
			case RES_TYPE_STYLE_ITEM:
			return "styles";
			default:
			return "";
		}
	}

	public static String getActivityTitle(int resType) {
		return Helper.getResString(getXmlResName(resType)) + " Manager";
	}
}
