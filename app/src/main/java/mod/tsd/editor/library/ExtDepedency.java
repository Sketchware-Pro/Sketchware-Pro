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
}