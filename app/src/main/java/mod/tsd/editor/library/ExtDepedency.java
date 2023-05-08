package mod.tsd.editor.library;

import a.a.a.jq;
import mod.agus.jcoderz.handle.component.ConstVarComponent;
import mod.agus.jcoderz.editor.library.ExtLibSelected;

public class ExtDepedency {
	public static String dependency;
	public static ConstVarComponent constVarComponent;
	public static String addExtDepedency(jq metadata,String content) {
		constVarComponent = metadata.x;
		dependency = content;
		if (constVarComponent.isCircleImageViewUsed) {
			dependency += "implementation 'de.hdodenhof:circleimageview:3.1.0'\r\n";
		}
		return dependency;
	}
}