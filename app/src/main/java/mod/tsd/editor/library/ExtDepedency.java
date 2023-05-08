package mod.tsd.editor.library;

import a.a.a.jq;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import mod.agus.jcoderz.handle.component.ConstVarComponent;
import mod.agus.jcoderz.editor.library.ExtLibSelected;
import mod.jbk.build.BuiltInLibraries;
import mod.jbk.editor.manage.library.ExcludeBuiltInLibrariesActivity;

public class ExtDepedency {
	public static String dependency;
	public static ConstVarComponent constVarComponent;
	public static List<BuiltInLibraries.BuiltInLibrary> excludedLibraries;
	
	public static String addExtDepedency(jq metadata,String content) {
		constVarComponent = metadata.x;
		excludedLibraries = ExcludeBuiltInLibrariesActivity.getExcludedLibraries(metadata.sc_id);
		
		dependency = content;
		if (shouldAddLib(BuiltInLibraries.CIRCLE_IMAGEVIEW,metadata.sc_id)) {
			if (constVarComponent.isCircleImageViewUsed) {
				dependency += "implementation 'de.hdodenhof:circleimageview:3.1.0'\r\n";
			}
		}
		if (constVarComponent.isYoutubePlayerUsed) {
			dependency += "implementation 'com.pierfrancescosoffritti:androidyoutubeplayer:10.0.5'\r\n";
		}
		if (constVarComponent.isCodeViewUsed) {
			dependency += "implementation 'br.tiagohm:codeview:0.4.0'\r\n";
		}
		if (constVarComponent.isLottieUsed) {
			dependency += "implementation 'com.airbnb:lottie:3.4.0'\r\n";
		}
		if (constVarComponent.isOTPViewUsed) {
			dependency += "implementation 'affan.ahmad:otp:0.1.0'\r\n";
		}
		if (constVarComponent.isOneSignalUsed) {
			dependency += "implementation 'com.onesignal:OneSignal:3.14.0'\r\n";
		}
		if (constVarComponent.isPatternLockViewUsed) {
			dependency += "implementation 'com.andrognito:patternlockview:1.0.0'\r\n";
		}
		if (constVarComponent.isWaveSideBarUsed) {
			//dependency += "implementation 'com.sayuti:lib:3.4.0'\r\n"; not sure which version is used...
		}
		if (constVarComponent.isFBAdsUsed) {
			//dependency += "implementation '\r\n"; i couldnt find its depends
		}
		if (constVarComponent.isFBGoogleUsed) {
			//dependency += "implementation '\r\n"; i couldnt find its depends
		}
		if (constVarComponent.isFCMUsed) {
			//dependency += "implementation '\r\n"; i couldnt find its depends
		}
		return dependency;
	}
	
	public static boolean shouldAddLib(String libraryName,String sc_id) {
		Optional<BuiltInLibraries.BuiltInLibrary> library = BuiltInLibraries.BuiltInLibrary.ofName(libraryName);
        if (!excludedLibraries.contains(library.get())) {
            return true;
        } else {
        	if (ExcludeBuiltInLibrariesActivity.isExcludingEnabled(sc_id)) {
        		return false;
        	} else {
            	return true;
        	}
        }
	}
	
}