package pro.sketchware.blocks;

import android.util.Pair;

import com.besome.sketch.editor.LogicEditorActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import a.a.a.eC;
import a.a.a.jC;
import mod.hilal.saif.activities.tools.ConfigActivity;

public class ExtraBlocks {

    private final String eventName;
    private final String javaName;
    private final LogicEditorActivity logicEditor;
    private final eC projectDataManager;

    public ExtraBlocks(LogicEditorActivity logicEditor) {
        eventName = logicEditor.eventName;
        this.logicEditor = logicEditor;
        javaName = logicEditor.M.getJavaName();
        projectDataManager = jC.a(logicEditor.scId);
    }

    public static void extraBlocks(ArrayList<HashMap<String, Object>> arrayList) {
        arrayList.add(addBlock("caseStrAnd", " ", "", "case %s:", "#e1a92a", "case %s and"));
        arrayList.add(addBlock("caseNumAnd", " ", "", "case ((int)%s):", "#e1a92a", "case %d and"));
        arrayList.add(addBlock("continue", "f", "", "continue;", "#e1a92a", "continue"));
        arrayList.add(addBlock("isEmpty", "b", "", "%s.isEmpty()", "#e1a92a", "%s isEmpty"));
        arrayList.add(addBlock("fileutilcopydir", " ", "", "FileUtil.copyDir(%1$s, %2$s);", "#a1887f", "copy dir path %s to path %s"));
        arrayList.add(addBlock("instanceOfOperator", "b", "", "%1$s instanceof %2$s", "#e1a92a", "%s instanceOf %s"));
        arrayList.add(addBlock("checkViewVisibility", "b", "", "%s.getVisibility() == View.%s", "#4a6cd4", "visibility of %m.view equals %m.visible"));
        arrayList.add(addBlock("intentHasExtra", "b", "", "getIntent().hasExtra(%s)", "#2ca5e2", "Activity hasExtra key %s"));
        arrayList.add(addBlock("intentSetType", " ", "", "%s.setType(%s);", "#2ca5e2", "%m.intent setType %s"));
        arrayList.add(addBlock("intentRemoveExtra", " ", "", "%s.removeExtra(%s);", "#2ca5e2", "%m.intent removeExtra key %s"));
        arrayList.add(addBlock("fileContainsData", "b", "", "%1$s.contains(%2$s)", "#2ca5e2", "%m.file contains %s"));
        arrayList.add(addBlock("viewGetChildAt", "v", "View", "%1$s.getChildAt(%2$s)", "#4a6cd4", "%m.view getChildAt %d"));
        arrayList.add(addBlock("strParseInteger", "d", "", "Integer.parseInt(%s)", "#5cb722", "parse int %s"));
        arrayList.add(addBlock("stringSubSingle", "s", "", "%1$s.substring(%2$s)", "#5cb722", "%s subString %d"));
        arrayList.add(addBlock("webviewGetProgress", "d", "", "%1$s.getProgress()", "#4a6cd4", "%m.webview getProgress"));
        arrayList.add(addBlock("menuItemSetVisible", " ", "", "%1$s.setVisible(%2$s);", "#4a6cd4", "%m.MenuItem setVisible %b"));
        arrayList.add(addBlock("menuItemSetEnabled", " ", "", "%1$s.setEnabled(%2$s);", "#4a6cd4", "%m.MenuItem setEnabled %b"));
        arrayList.add(addBlock("menuFindItem", "v", "MenuItem", "menu.findItem(%s)", "#4a6cd4", "MenuItem findItem %s.inputOnly"));
        arrayList.add(addBlock("listAddAll", " ", "", "%1$s.addAll(%2$s);", "#cc5b22", "%m.listStr addAll from %m.list"));
        // File Blocks
        arrayList.add(addBlock("fileCanExecute", "b", "", "%s.canExecute()", "#a1887f", "%m.File canExecute"));
        arrayList.add(addBlock("fileCanRead", "b", "", "%s.canRead()", "#a1887f", "%m.File canRead"));
        arrayList.add(addBlock("fileCanWrite", "b", "", "%s.canWrite()", "#a1887f", "%m.File canWrite"));
        arrayList.add(addBlock("fileGetName", "s", "", "%s.getName()", "#a1887f", "%m.File getName"));
        arrayList.add(addBlock("fileGetParent", "s", "", "%s.getParent()", "#a1887f", "%m.File getParent"));
        arrayList.add(addBlock("fileGetPath", "s", "", "%s.getPath()", "#a1887f", "%m.File getPath"));
        arrayList.add(addBlock("fileIsHidden", "b", "", "%s.isHidden()", "#a1887f", "%m.File isHidden"));
        // Basically Command Block
        arrayList.add(addBlock("addPermission", " ", "", "", "#493F5A", "Permission Command Block: add %m.Permission"));
        arrayList.add(addBlock("removePermission", " ", "", "", "#493F5A", "Permission Command Block: remove %m.Permission"));
        arrayList.add(addBlock("addCustomVariable", " ", "", "", "#493F5A", "Custom Variable Block: add variable %s"));
        arrayList.add(addBlock("addInitializer", " ", "", "", "#493F5A", "Initializer Block: add initializer %s"));
        //OtpView Blocks
        arrayList.add(addBlock("otpViewSetFieldCount", " ", "", "%1$s.setFieldCount(%2$s);", "#4a6cd4", "%m.otpview setFieldCount %d"));
        arrayList.add(addBlock("otpViewSetOTPText", " ", "", "%1$s.setOTPText(%2$s);", "#4a6cd4", "%m.otpview setOTPText %s"));
        arrayList.add(addBlock("otpViewGetOTPText", "s", "", "%1$s.getOTPText()", "#4a6cd4", "%m.otpview getOTPText"));
        arrayList.add(addBlock("otpViewSetOTPListener", "c", "", "%1$s.setOTPListener(new OTPListener() {\r\npublic void onOTPEntered(String _otp) {\r\n%2$s\r\n}\r\n});", "#4a6cd4", "%m.otpview onOTPEntered -> _otp"));
        //SignInButton Blocks
        arrayList.add(addBlock("signInButtonSetColorScheme", " ", "", "%1$s.setColorScheme(SignInButton.%2$s);", "#4a6cd4", "%m.signinbutton setColorScheme %m.SignButtonColor"));
        arrayList.add(addBlock("signInButtonSetSize", " ", "", "%1$s.setSize(SignInButton.%2$s);", "#4a6cd4", "%m.signinbutton setSize %m.SignButtonSize"));
        //Admob's AdView Blocks
        arrayList.add(addBlock("bannerAdViewLoadAd", " ", "", "{\r\nAdRequest adRequest = new AdRequest.Builder().build();\r\n%1$s.loadAd(adRequest);\r\n}", "#4a6cd4", "%m.adview load"));
        //RewardedVideoAd
        arrayList.add(addBlock("rewardedVideoAdLoad", " ", "", "RewardedAd.load(%2$s.this, _reward_ad_unit_id, new AdRequest.Builder().build(), _%1$s_rewarded_ad_load_callback);", "#2ca5e2", "%m.videoad load in %m.activity"));
        arrayList.add(addBlock("rewardedVideoAdShow", " ", "", "%1s.show(%2$s.this, _%1$s_on_user_earned_reward_listener);", "#2ca5e2", "%m.videoad show in %m.activity"));
        // Reyaansh's Custom Inbuilt Utility Blocks Library - 300 Blocks
                
        // ─── DEVICE / SYSTEM ───────────────────────────────────────────────────────
        arrayList.add(addBlock("deviceVibrate", " ", "", "((android.os.Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(%s);", "#2ca5e2", "Vibrate device for %d ms"));
        arrayList.add(addBlock("deviceVibratePattern", " ", "", "((android.os.Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(new long[]{0,%1$s,%2$s,%3$s}, -1);", "#2ca5e2", "Vibrate pattern delay %d on %d off %d"));
        arrayList.add(addBlock("deviceCancelVibrate", " ", "", "((android.os.Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).cancel();", "#2ca5e2", "Cancel vibration"));
        arrayList.add(addBlock("getDeviceModel", "s", "", "android.os.Build.MODEL", "#2ca5e2", "device model name"));
        arrayList.add(addBlock("getDeviceBrand", "s", "", "android.os.Build.BRAND", "#2ca5e2", "device brand name"));
        arrayList.add(addBlock("getDeviceManufacturer", "s", "", "android.os.Build.MANUFACTURER", "#2ca5e2", "device manufacturer"));
        arrayList.add(addBlock("getAndroidVersion", "s", "", "android.os.Build.VERSION.RELEASE", "#2ca5e2", "Android version"));
        arrayList.add(addBlock("getSDKInt", "d", "", "android.os.Build.VERSION.SDK_INT", "#2ca5e2", "SDK int"));
        arrayList.add(addBlock("getDeviceID", "s", "", "android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID)", "#2ca5e2", "device ID"));
        arrayList.add(addBlock("getBatteryLevel", "d", "", "((android.os.BatteryManager) getSystemService(Context.BATTERY_SERVICE)).getIntProperty(android.os.BatteryManager.BATTERY_PROPERTY_CAPACITY)", "#2ca5e2", "battery level percent"));
        arrayList.add(addBlock("isDeviceCharging", "z", "", "((android.os.BatteryManager) getSystemService(Context.BATTERY_SERVICE)).isCharging()", "#2ca5e2", "device is charging"));
        arrayList.add(addBlock("getScreenWidth", "d", "", "getResources().getDisplayMetrics().widthPixels", "#2ca5e2", "screen width px"));
        arrayList.add(addBlock("getScreenHeight", "d", "", "getResources().getDisplayMetrics().heightPixels", "#2ca5e2", "screen height px"));
        arrayList.add(addBlock("getScreenDensity", "f", "", "getResources().getDisplayMetrics().density", "#2ca5e2", "screen density"));
        arrayList.add(addBlock("keepScreenOn", " ", "", "getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);", "#2ca5e2", "keep screen on"));
        arrayList.add(addBlock("clearKeepScreenOn", " ", "", "getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);", "#2ca5e2", "allow screen to sleep"));
        arrayList.add(addBlock("setBrightness", " ", "", "{android.view.WindowManager.LayoutParams lp=getWindow().getAttributes();lp.screenBrightness=%s/100f;getWindow().setAttributes(lp);}", "#2ca5e2", "set screen brightness to %d"));
        arrayList.add(addBlock("isAirplaneModeOn", "z", "", "android.provider.Settings.Global.getInt(getContentResolver(), android.provider.Settings.Global.AIRPLANE_MODE_ON, 0) != 0", "#2ca5e2", "airplane mode is on"));
        arrayList.add(addBlock("getTotalRAM", "d", "", "((int)(new android.app.ActivityManager.MemoryInfo(){{((android.app.ActivityManager)getSystemService(Context.ACTIVITY_SERVICE)).getMemoryInfo(this);}}.totalMem/1048576))", "#2ca5e2", "total RAM MB"));
        arrayList.add(addBlock("getAvailableRAM", "d", "", "((int)(new android.app.ActivityManager.MemoryInfo(){{((android.app.ActivityManager)getSystemService(Context.ACTIVITY_SERVICE)).getMemoryInfo(this);}}.availMem/1048576))", "#2ca5e2", "available RAM MB"));
        arrayList.add(addBlock("relaunchApp", " ", "", "{Intent i=getPackageManager().getLaunchIntentForPackage(getPackageName());i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);startActivity(i);}", "#2ca5e2", "relaunch app"));
        arrayList.add(addBlock("getPackageName", "s", "", "getPackageName()", "#2ca5e2", "app package name"));
        arrayList.add(addBlock("getVersionName", "s", "", "getPackageManager().getPackageInfo(getPackageName(),0).versionName", "#2ca5e2", "app version name"));
        arrayList.add(addBlock("getVersionCode", "d", "", "getPackageManager().getPackageInfo(getPackageName(),0).versionCode", "#2ca5e2", "app version code"));
        arrayList.add(addBlock("openAppSettings", " ", "", "{Intent i=new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);i.setData(android.net.Uri.parse(\"package:\"+getPackageName()));startActivity(i);}", "#2ca5e2", "open app settings"));
        arrayList.add(addBlock("isAppInstalled", "z", "", "new java.util.function.Supplier<Boolean>(){public Boolean get(){try{getPackageManager().getPackageInfo(%s,0);return true;}catch(Exception e){return false;}}}.get()", "#2ca5e2", "app %s is installed"));
        arrayList.add(addBlock("getInstalledAppsCount", "d", "", "getPackageManager().getInstalledApplications(android.content.pm.PackageManager.GET_META_DATA).size()", "#2ca5e2", "installed apps count"));
        arrayList.add(addBlock("hideStatusBar", " ", "", "getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);", "#2ca5e2", "hide status bar"));
        arrayList.add(addBlock("showStatusBar", " ", "", "getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);", "#2ca5e2", "show status bar"));
        arrayList.add(addBlock("isTablet", "z", "", "(getResources().getConfiguration().screenLayout & android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK) >= android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE", "#2ca5e2", "device is tablet"));
        
        // ─── CLIPBOARD ─────────────────────────────────────────────────────────────
        arrayList.add(addBlock("setClipboardText", " ", "", "((android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(android.content.ClipData.newPlainText(\"text\", %s));", "#2ca5e2", "Copy %s to clipboard"));
        arrayList.add(addBlock("getClipboardText", "s", "", "((android.content.ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE)).getPrimaryClip().getItemAt(0).getText().toString()", "#2ca5e2", "clipboard text"));
        arrayList.add(addBlock("clearClipboard", " ", "", "((android.content.ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(android.content.ClipData.newPlainText(\"\",\"\"));", "#2ca5e2", "clear clipboard"));
        arrayList.add(addBlock("hasClipboardText", "z", "", "((android.content.ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE)).hasPrimaryClip()", "#2ca5e2", "clipboard has text"));
        
        // ─── MATH & RANDOM ─────────────────────────────────────────────────────────
        arrayList.add(addBlock("getRandomIntRange", "d", "", "new java.util.Random().nextInt((%2$s - %1$s) + 1) + %1$s", "#5cb722", "pick random number between %d and %d"));
        arrayList.add(addBlock("getRandomFloat", "f", "", "new java.util.Random().nextFloat()", "#5cb722", "random float 0 to 1"));
        arrayList.add(addBlock("getRandomBoolean", "z", "", "new java.util.Random().nextBoolean()", "#5cb722", "random boolean"));
        arrayList.add(addBlock("mathAbs", "d", "", "Math.abs(%s)", "#5cb722", "absolute value of %d"));
        arrayList.add(addBlock("mathAbsFloat", "f", "", "Math.abs(%s)", "#5cb722", "absolute float of %f"));
        arrayList.add(addBlock("mathSqrt", "f", "", "(float)Math.sqrt(%s)", "#5cb722", "square root of %d"));
        arrayList.add(addBlock("mathPow", "f", "", "(float)Math.pow(%1$s, %2$s)", "#5cb722", "%d to the power of %d"));
        arrayList.add(addBlock("mathFloor", "d", "", "(int)Math.floor(%s)", "#5cb722", "floor of %f"));
        arrayList.add(addBlock("mathCeil", "d", "", "(int)Math.ceil(%s)", "#5cb722", "ceiling of %f"));
        arrayList.add(addBlock("mathRound", "d", "", "Math.round(%s)", "#5cb722", "round %f"));
        arrayList.add(addBlock("mathMax", "d", "", "Math.max(%1$s, %2$s)", "#5cb722", "max of %d and %d"));
        arrayList.add(addBlock("mathMin", "d", "", "Math.min(%1$s, %2$s)", "#5cb722", "min of %d and %d"));
        arrayList.add(addBlock("mathLog", "f", "", "(float)Math.log(%s)", "#5cb722", "natural log of %d"));
        arrayList.add(addBlock("mathLog10", "f", "", "(float)Math.log10(%s)", "#5cb722", "log10 of %d"));
        arrayList.add(addBlock("mathSin", "f", "", "(float)Math.sin(Math.toRadians(%s))", "#5cb722", "sin of %d degrees"));
        arrayList.add(addBlock("mathCos", "f", "", "(float)Math.cos(Math.toRadians(%s))", "#5cb722", "cos of %d degrees"));
        arrayList.add(addBlock("mathTan", "f", "", "(float)Math.tan(Math.toRadians(%s))", "#5cb722", "tan of %d degrees"));
        arrayList.add(addBlock("mathPI", "f", "", "(float)Math.PI", "#5cb722", "PI value"));
        arrayList.add(addBlock("mathE", "f", "", "(float)Math.E", "#5cb722", "E value"));
        arrayList.add(addBlock("mathClamp", "d", "", "Math.max(%2$s, Math.min(%3$s, %1$s))", "#5cb722", "clamp %d between %d and %d"));
        arrayList.add(addBlock("mathClampFloat", "f", "", "Math.max(%2$sf, Math.min(%3$sf, %1$sf))", "#5cb722", "clamp float %f between %f and %f"));
        arrayList.add(addBlock("mathMap", "f", "", "((%1$s - %2$s) / (float)(%3$s - %2$s)) * (%5$s - %4$s) + %4$s", "#5cb722", "map %f from %d-%d to %d-%d"));
        arrayList.add(addBlock("intToBinary", "s", "", "Integer.toBinaryString(%s)", "#5cb722", "binary string of %d"));
        arrayList.add(addBlock("intToHex", "s", "", "Integer.toHexString(%s)", "#5cb722", "hex string of %d"));
        arrayList.add(addBlock("intToOctal", "s", "", "Integer.toOctalString(%s)", "#5cb722", "octal string of %d"));
        arrayList.add(addBlock("sumIntList", "d", "", "new java.util.function.Supplier<Integer>(){public Integer get(){int t=0;for(int v:(java.util.List<Integer>)%s)t+=v;return t;}}.get()", "#5cb722", "sum of list %m.list"));
        arrayList.add(addBlock("averageIntList", "f", "", "new java.util.function.Supplier<Float>(){public Float get(){int t=0;java.util.List<Integer> l=(java.util.List<Integer>)%s;for(int v:l)t+=v;return t/(float)l.size();}}.get()", "#5cb722", "average of list %m.list"));
        arrayList.add(addBlock("mathFactorial", "d", "", "new java.util.function.Supplier<Integer>(){public Integer get(){int r=1;for(int i=2;i<=%s;i++)r*=i;return r;}}.get()", "#5cb722", "factorial of %d"));
        arrayList.add(addBlock("isPrime", "z", "", "new java.util.function.Supplier<Boolean>(){public Boolean get(){int n=%s;if(n<2)return false;for(int i=2;i<=Math.sqrt(n);i++)if(n%%i==0)return false;return true;}}.get()", "#5cb722", "%d is prime"));
        
        // ─── STRING UTILITIES ───────────────────────────────────────────────────────
        arrayList.add(addBlock("stringToUpperCase", "s", "", "%s.toUpperCase()", "#9c27b0", "uppercase of %s"));
        arrayList.add(addBlock("stringToLowerCase", "s", "", "%s.toLowerCase()", "#9c27b0", "lowercase of %s"));
        arrayList.add(addBlock("stringTrim", "s", "", "%s.trim()", "#9c27b0", "trim spaces from %s"));
        arrayList.add(addBlock("stringReverse", "s", "", "new StringBuilder(%s).reverse().toString()", "#9c27b0", "reverse string %s"));
        arrayList.add(addBlock("stringLength", "d", "", "%s.length()", "#9c27b0", "length of %s"));
        arrayList.add(addBlock("stringContains", "z", "", "%1$s.contains(%2$s)", "#9c27b0", "%s contains %s"));
        arrayList.add(addBlock("stringStartsWith", "z", "", "%1$s.startsWith(%2$s)", "#9c27b0", "%s starts with %s"));
        arrayList.add(addBlock("stringEndsWith", "z", "", "%1$s.endsWith(%2$s)", "#9c27b0", "%s ends with %s"));
        arrayList.add(addBlock("stringIndexOf", "d", "", "%1$s.indexOf(%2$s)", "#9c27b0", "index of %s in %s"));
        arrayList.add(addBlock("stringSubstring", "s", "", "%1$s.substring(%2$s, %3$s)", "#9c27b0", "substring of %s from %d to %d"));
        arrayList.add(addBlock("stringSubstringFrom", "s", "", "%1$s.substring(%2$s)", "#9c27b0", "substring of %s from %d"));
        arrayList.add(addBlock("stringCharAt", "s", "", "String.valueOf(%1$s.charAt(%2$s))", "#9c27b0", "char at %d in %s"));
        arrayList.add(addBlock("stringReplace", "s", "", "%1$s.replace(%2$s, %3$s)", "#9c27b0", "in %s replace %s with %s"));
        arrayList.add(addBlock("stringReplaceAll", "s", "", "%1$s.replaceAll(%2$s, %3$s)", "#9c27b0", "in %s replace all regex %s with %s"));
        arrayList.add(addBlock("stringSplit", "s", "", "java.util.Arrays.asList(%1$s.split(%2$s))", "#9c27b0", "split %s by %s"));
        arrayList.add(addBlock("stringJoin", "s", "", "android.text.TextUtils.join(%1$s, (java.util.List<String>)%2$s)", "#9c27b0", "join list %m.list with separator %s"));
        arrayList.add(addBlock("stringRepeat", "s", "", "new String(new char[%2$s]).replace(\"\\0\", %1$s)", "#9c27b0", "repeat %s %d times"));
        arrayList.add(addBlock("stringIsEmpty", "z", "", "%s.isEmpty()", "#9c27b0", "%s is empty"));
        arrayList.add(addBlock("stringIsNotEmpty", "z", "", "!%s.isEmpty()", "#9c27b0", "%s is not empty"));
        arrayList.add(addBlock("stringCountOccurrences", "d", "", "((%1$s.length() - %1$s.replace(%2$s, \"\").length()) / %2$s.length())", "#9c27b0", "count of %s in %s"));
        arrayList.add(addBlock("stringPadStart", "s", "", "String.format(\"%\"+%2$s+\"s\", %1$s).replace(\" \", %3$s)", "#9c27b0", "pad start of %s to length %d with %s"));
        arrayList.add(addBlock("stringCapitalize", "s", "", "%s.substring(0,1).toUpperCase() + %s.substring(1).toLowerCase()", "#9c27b0", "capitalize %s"));
        arrayList.add(addBlock("stringIsNumeric", "z", "", "%s.matches(\"-?\\\\d+(\\\\.\\\\d+)?\")", "#9c27b0", "%s is numeric"));
        arrayList.add(addBlock("stringIsAlpha", "z", "", "%s.matches(\"[a-zA-Z]+\")", "#9c27b0", "%s is alphabetic only"));
        arrayList.add(addBlock("stringIsEmail", "z", "", "android.util.Patterns.EMAIL_ADDRESS.matcher(%s).matches()", "#9c27b0", "%s is valid email"));
        arrayList.add(addBlock("stringIsURL", "z", "", "android.util.Patterns.WEB_URL.matcher(%s).matches()", "#9c27b0", "%s is valid URL"));
        arrayList.add(addBlock("stringIsPhone", "z", "", "android.util.Patterns.PHONE.matcher(%s).matches()", "#9c27b0", "%s is valid phone number"));
        arrayList.add(addBlock("stringToInt", "d", "", "Integer.parseInt(%s)", "#9c27b0", "parse %s as int"));
        arrayList.add(addBlock("stringToFloat", "f", "", "Float.parseFloat(%s)", "#9c27b0", "parse %s as float"));
        arrayList.add(addBlock("intToString", "s", "", "String.valueOf(%s)", "#9c27b0", "int %d as string"));
        arrayList.add(addBlock("floatToString", "s", "", "String.valueOf(%s)", "#9c27b0", "float %f as string"));
        arrayList.add(addBlock("stringFormat2", "s", "", "String.format(%1$s, %2$s, %3$s)", "#9c27b0", "format string %s with %s and %s"));
        arrayList.add(addBlock("stringBase64Encode", "s", "", "android.util.Base64.encodeToString(%s.getBytes(), android.util.Base64.NO_WRAP)", "#9c27b0", "base64 encode %s"));
        arrayList.add(addBlock("stringBase64Decode", "s", "", "new String(android.util.Base64.decode(%s, android.util.Base64.DEFAULT))", "#9c27b0", "base64 decode %s"));
        arrayList.add(addBlock("stringMD5", "s", "", "new java.util.function.Supplier<String>(){public String get(){try{java.security.MessageDigest md=java.security.MessageDigest.getInstance(\"MD5\");byte[]b=md.digest(%s.getBytes());StringBuilder sb=new StringBuilder();for(byte x:b)sb.append(String.format(\"%02x\",x));return sb.toString();}catch(Exception e){return \"\";}}}.get()", "#9c27b0", "MD5 hash of %s"));
        arrayList.add(addBlock("getStringBetween", "s", "", "{String _s=%1$s;int _a=_s.indexOf(%2$s)+%2$s.length(),_b=_s.indexOf(%3$s,_a);(_a<0||_b<0)?\"\":_s.substring(_a,_b);}", "#9c27b0", "text in %s between %s and %s"));
        arrayList.add(addBlock("stringRemoveSpaces", "s", "", "%s.replaceAll(\"\\\\s+\",\"\")", "#9c27b0", "remove all spaces from %s"));
        arrayList.add(addBlock("stringWordCount", "d", "", "%s.trim().isEmpty()?0:%s.trim().split(\"\\\\s+\").length", "#9c27b0", "word count of %s"));
        
        // ─── VIEW ANIMATIONS ────────────────────────────────────────────────────────
        arrayList.add(addBlock("viewSetAlphaAnimator", " ", "", "%1$s.animate().alpha(%2$sf).setDuration(%3$s).start();", "#4a6cd4", "Animate %m.view alpha to %f duration %d ms"));
        arrayList.add(addBlock("viewTranslateX", " ", "", "%1$s.animate().translationX(%2$sf).setDuration(%3$s).start();", "#4a6cd4", "Animate %m.view translateX to %f in %d ms"));
        arrayList.add(addBlock("viewTranslateY", " ", "", "%1$s.animate().translationY(%2$sf).setDuration(%3$s).start();", "#4a6cd4", "Animate %m.view translateY to %f in %d ms"));
        arrayList.add(addBlock("viewScaleX", " ", "", "%1$s.animate().scaleX(%2$sf).setDuration(%3$s).start();", "#4a6cd4", "Animate %m.view scaleX to %f in %d ms"));
        arrayList.add(addBlock("viewScaleY", " ", "", "%1$s.animate().scaleY(%2$sf).setDuration(%3$s).start();", "#4a6cd4", "Animate %m.view scaleY to %f in %d ms"));
        arrayList.add(addBlock("viewScale", " ", "", "%1$s.animate().scaleX(%2$sf).scaleY(%2$sf).setDuration(%3$s).start();", "#4a6cd4", "Animate %m.view scale to %f in %d ms"));
        arrayList.add(addBlock("viewRotate", " ", "", "%1$s.animate().rotation(%2$sf).setDuration(%3$s).start();", "#4a6cd4", "Animate %m.view rotation to %f in %d ms"));
        arrayList.add(addBlock("viewRotateBy", " ", "", "%1$s.animate().rotationBy(%2$sf).setDuration(%3$s).start();", "#4a6cd4", "Animate %m.view rotate by %f in %d ms"));
        arrayList.add(addBlock("viewFadeIn", " ", "", "%1$s.setAlpha(0f);%1$s.setVisibility(android.view.View.VISIBLE);%1$s.animate().alpha(1f).setDuration(%2$s).start();", "#4a6cd4", "Fade in %m.view over %d ms"));
        arrayList.add(addBlock("viewFadeOut", " ", "", "%1$s.animate().alpha(0f).setDuration(%2$s).withEndAction(()->{%1$s.setVisibility(android.view.View.GONE);}).start();", "#4a6cd4", "Fade out %m.view over %d ms"));
        arrayList.add(addBlock("viewSlideInLeft", " ", "", "%1$s.setTranslationX(-%1$s.getWidth());%1$s.setVisibility(android.view.View.VISIBLE);%1$s.animate().translationX(0).setDuration(%2$s).start();", "#4a6cd4", "Slide in %m.view from left in %d ms"));
        arrayList.add(addBlock("viewSlideInRight", " ", "", "%1$s.setTranslationX(%1$s.getWidth());%1$s.setVisibility(android.view.View.VISIBLE);%1$s.animate().translationX(0).setDuration(%2$s).start();", "#4a6cd4", "Slide in %m.view from right in %d ms"));
        arrayList.add(addBlock("viewSlideInTop", " ", "", "%1$s.setTranslationY(-%1$s.getHeight());%1$s.setVisibility(android.view.View.VISIBLE);%1$s.animate().translationY(0).setDuration(%2$s).start();", "#4a6cd4", "Slide in %m.view from top in %d ms"));
        arrayList.add(addBlock("viewSlideInBottom", " ", "", "%1$s.setTranslationY(%1$s.getHeight());%1$s.setVisibility(android.view.View.VISIBLE);%1$s.animate().translationY(0).setDuration(%2$s).start();", "#4a6cd4", "Slide in %m.view from bottom in %d ms"));
        arrayList.add(addBlock("viewBounce", " ", "", "%1$s.animate().scaleX(1.2f).scaleY(1.2f).setDuration(%2$s/2).withEndAction(()->{%1$s.animate().scaleX(1f).scaleY(1f).setDuration(%2$s/2).start();}).start();", "#4a6cd4", "Bounce %m.view over %d ms"));
        arrayList.add(addBlock("viewShake", " ", "", "{android.view.animation.TranslateAnimation anim=new android.view.animation.TranslateAnimation(0,10,0,0);anim.setDuration(%2$s);anim.setRepeatCount(5);anim.setRepeatMode(android.view.animation.Animation.REVERSE);%1$s.startAnimation(anim);}", "#4a6cd4", "Shake %m.view over %d ms"));
        arrayList.add(addBlock("viewPulse", " ", "", "%1$s.animate().scaleX(0.95f).scaleY(0.95f).setDuration(%2$s/2).withEndAction(()->{%1$s.animate().scaleX(1f).scaleY(1f).setDuration(%2$s/2).start();}).start();", "#4a6cd4", "Pulse %m.view over %d ms"));
        arrayList.add(addBlock("viewFlipHorizontal", " ", "", "%1$s.animate().rotationY(180f).setDuration(%2$s).start();", "#4a6cd4", "Flip %m.view horizontally in %d ms"));
        arrayList.add(addBlock("viewFlipVertical", " ", "", "%1$s.animate().rotationX(180f).setDuration(%2$s).start();", "#4a6cd4", "Flip %m.view vertically in %d ms"));
        arrayList.add(addBlock("viewAnimateWithInterpolator", " ", "", "%1$s.animate().alpha(%2$sf).setDuration(%3$s).setInterpolator(new android.view.animation.BounceInterpolator()).start();", "#4a6cd4", "Animate %m.view alpha %f in %d ms with bounce"));
        
        // ─── VIEW PROPERTIES ────────────────────────────────────────────────────────
        arrayList.add(addBlock("viewSetVisible", " ", "", "%s.setVisibility(android.view.View.VISIBLE);", "#4a6cd4", "set %m.view visible"));
        arrayList.add(addBlock("viewSetGone", " ", "", "%s.setVisibility(android.view.View.GONE);", "#4a6cd4", "set %m.view gone"));
        arrayList.add(addBlock("viewSetInvisible", " ", "", "%s.setVisibility(android.view.View.INVISIBLE);", "#4a6cd4", "set %m.view invisible"));
        arrayList.add(addBlock("viewIsVisible", "z", "", "%s.getVisibility() == android.view.View.VISIBLE", "#4a6cd4", "%m.view is visible"));
        arrayList.add(addBlock("viewToggleVisibility", " ", "", "%1$s.setVisibility(%1$s.getVisibility()==android.view.View.VISIBLE?android.view.View.GONE:android.view.View.VISIBLE);", "#4a6cd4", "toggle visibility of %m.view"));
        arrayList.add(addBlock("viewSetEnabled", " ", "", "%1$s.setEnabled(%2$s);", "#4a6cd4", "set %m.view enabled %z"));
        arrayList.add(addBlock("viewSetAlpha", " ", "", "%1$s.setAlpha(%2$sf);", "#4a6cd4", "set %m.view alpha to %f"));
        arrayList.add(addBlock("viewGetAlpha", "f", "", "%s.getAlpha()", "#4a6cd4", "%m.view alpha"));
        arrayList.add(addBlock("viewSetElevation", " ", "", "%1$s.setElevation(%2$sf);", "#4a6cd4", "set %m.view elevation to %f"));
        arrayList.add(addBlock("viewSetBackgroundColor", " ", "", "%1$s.setBackgroundColor(%2$s);", "#4a6cd4", "set %m.view background color to %d"));
        arrayList.add(addBlock("viewSetBackgroundColorHex", " ", "", "%1$s.setBackgroundColor(android.graphics.Color.parseColor(%2$s));", "#4a6cd4", "set %m.view background color to hex %s"));
        arrayList.add(addBlock("viewSetPadding", " ", "", "%1$s.setPadding(%2$s,%3$s,%4$s,%5$s);", "#4a6cd4", "set %m.view padding left %d top %d right %d bottom %d"));
        arrayList.add(addBlock("viewSetWidth", " ", "", "{android.view.ViewGroup.LayoutParams lp=%1$s.getLayoutParams();lp.width=%2$s;%1$s.setLayoutParams(lp);}", "#4a6cd4", "set %m.view width to %d px"));
        arrayList.add(addBlock("viewSetHeight", " ", "", "{android.view.ViewGroup.LayoutParams lp=%1$s.getLayoutParams();lp.height=%2$s;%1$s.setLayoutParams(lp);}", "#4a6cd4", "set %m.view height to %d px"));
        arrayList.add(addBlock("viewGetWidth", "d", "", "%s.getWidth()", "#4a6cd4", "%m.view width"));
        arrayList.add(addBlock("viewGetHeight", "d", "", "%s.getHeight()", "#4a6cd4", "%m.view height"));
        arrayList.add(addBlock("viewSetRotation", " ", "", "%1$s.setRotation(%2$sf);", "#4a6cd4", "set %m.view rotation to %f degrees"));
        arrayList.add(addBlock("viewSetScaleX", " ", "", "%1$s.setScaleX(%2$sf);", "#4a6cd4", "set %m.view scaleX to %f"));
        arrayList.add(addBlock("viewSetScaleY", " ", "", "%1$s.setScaleY(%2$sf);", "#4a6cd4", "set %m.view scaleY to %f"));
        arrayList.add(addBlock("viewGetX", "f", "", "%s.getX()", "#4a6cd4", "%m.view X position"));
        arrayList.add(addBlock("viewGetY", "f", "", "%s.getY()", "#4a6cd4", "%m.view Y position"));
        arrayList.add(addBlock("viewSetX", " ", "", "%1$s.setX(%2$sf);", "#4a6cd4", "set %m.view X to %f"));
        arrayList.add(addBlock("viewSetY", " ", "", "%1$s.setY(%2$sf);", "#4a6cd4", "set %m.view Y to %f"));
        arrayList.add(addBlock("viewRequestFocus", " ", "", "%s.requestFocus();", "#4a6cd4", "request focus on %m.view"));
        arrayList.add(addBlock("viewClearFocus", " ", "", "%s.clearFocus();", "#4a6cd4", "clear focus on %m.view"));
        arrayList.add(addBlock("viewHasFocus", "z", "", "%s.hasFocus()", "#4a6cd4", "%m.view has focus"));
        arrayList.add(addBlock("viewSetClickable", " ", "", "%1$s.setClickable(%2$s);", "#4a6cd4", "set %m.view clickable %z"));
        arrayList.add(addBlock("viewPerformClick", " ", "", "%s.performClick();", "#4a6cd4", "perform click on %m.view"));
        arrayList.add(addBlock("viewSetTag", " ", "", "%1$s.setTag(%2$s);", "#4a6cd4", "set %m.view tag to %s"));
        arrayList.add(addBlock("viewGetTag", "s", "", "%s.getTag().toString()", "#4a6cd4", "tag of %m.view"));
        arrayList.add(addBlock("viewSetCornerRadius", " ", "", "%1$s.setClipToOutline(true);%1$s.setOutlineProvider(new android.view.ViewOutlineProvider(){public void getOutline(android.view.View v,android.graphics.Outline o){o.setRoundRect(0,0,v.getWidth(),v.getHeight(),%2$sf);}});", "#4a6cd4", "set %m.view corner radius to %f"));
        arrayList.add(addBlock("viewScrollToTop", " ", "", "%s.scrollTo(0,0);", "#4a6cd4", "scroll %m.view to top"));
        arrayList.add(addBlock("viewScrollToBottom", " ", "", "%s.post(()->{%s.scrollTo(0,%s.getHeight());});", "#4a6cd4", "scroll %m.view to bottom"));
        arrayList.add(addBlock("viewBringToFront", " ", "", "%s.bringToFront();", "#4a6cd4", "bring %m.view to front"));
        
        // ─── INTENT / NAVIGATION ─────────────────────────────────────────────────────
        arrayList.add(addBlock("openBrowser", " ", "", "startActivity(new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(%s)));", "#e91e63", "open URL %s in browser"));
        arrayList.add(addBlock("openDialer", " ", "", "startActivity(new Intent(Intent.ACTION_DIAL, android.net.Uri.parse(\"tel:\"+%s)));", "#e91e63", "open dialer with number %s"));
        arrayList.add(addBlock("sendSMS", " ", "", "startActivity(new Intent(Intent.ACTION_VIEW, android.net.Uri.fromParts(\"sms\",%1$s,null)).putExtra(\"sms_body\",%2$s));", "#e91e63", "open SMS to %s with body %s"));
        arrayList.add(addBlock("openEmail", " ", "", "{Intent i=new Intent(Intent.ACTION_SEND);i.setType(\"message/rfc822\");i.putExtra(Intent.EXTRA_EMAIL,new String[]{%1$s});i.putExtra(Intent.EXTRA_SUBJECT,%2$s);i.putExtra(Intent.EXTRA_TEXT,%3$s);startActivity(Intent.createChooser(i,\"Send Email\"));}", "#e91e63", "open email to %s subject %s body %s"));
        arrayList.add(addBlock("openMaps", " ", "", "startActivity(new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(\"geo:%1$s,%2$s?q=%1$s,%2$s\")));", "#e91e63", "open maps at lat %s lng %s"));
        arrayList.add(addBlock("openMapsSearch", " ", "", "startActivity(new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(\"geo:0,0?q=\"+android.net.Uri.encode(%s))));", "#e91e63", "open maps searching for %s"));
        arrayList.add(addBlock("shareText", " ", "", "{Intent i=new Intent(Intent.ACTION_SEND);i.setType(\"text/plain\");i.putExtra(Intent.EXTRA_TEXT,%s);startActivity(Intent.createChooser(i,\"Share via\"));}", "#e91e63", "share text %s"));
        arrayList.add(addBlock("openPlayStore", " ", "", "startActivity(new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(\"market://details?id=\"+%s)));", "#e91e63", "open Play Store for package %s"));
        arrayList.add(addBlock("openPlayStoreThisApp", " ", "", "startActivity(new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(\"market://details?id=\"+getPackageName())));", "#e91e63", "open Play Store for this app"));
        arrayList.add(addBlock("openWifiSettings", " ", "", "startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));", "#e91e63", "open Wi-Fi settings"));
        arrayList.add(addBlock("openBluetoothSettings", " ", "", "startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));", "#e91e63", "open Bluetooth settings"));
        arrayList.add(addBlock("openLocationSettings", " ", "", "startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));", "#e91e63", "open location settings"));
        arrayList.add(addBlock("openDateTimeSettings", " ", "", "startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));", "#e91e63", "open date and time settings"));
        arrayList.add(addBlock("shareImage", " ", "", "{Intent i=new Intent(Intent.ACTION_SEND);i.setType(\"image/*\");i.putExtra(Intent.EXTRA_STREAM,android.net.Uri.parse(%s));startActivity(Intent.createChooser(i,\"Share Image\"));}", "#e91e63", "share image from path %s"));
        arrayList.add(addBlock("openAppByPackage", " ", "", "{Intent i=getPackageManager().getLaunchIntentForPackage(%s);if(i!=null)startActivity(i);}", "#e91e63", "open app with package %s"));
        
        // ─── TOAST & SNACKBAR ───────────────────────────────────────────────────────
        arrayList.add(addBlock("toastShort", " ", "", "android.widget.Toast.makeText(getApplicationContext(),%s,android.widget.Toast.LENGTH_SHORT).show();", "#ff9800", "show short toast %s"));
        arrayList.add(addBlock("toastLong", " ", "", "android.widget.Toast.makeText(getApplicationContext(),%s,android.widget.Toast.LENGTH_LONG).show();", "#ff9800", "show long toast %s"));
        arrayList.add(addBlock("snackbarShort", " ", "", "com.google.android.material.snackbar.Snackbar.make(%1$s,%2$s,com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show();", "#ff9800", "show short snackbar on %m.view with text %s"));
        arrayList.add(addBlock("snackbarLong", " ", "", "com.google.android.material.snackbar.Snackbar.make(%1$s,%2$s,com.google.android.material.snackbar.Snackbar.LENGTH_LONG).show();", "#ff9800", "show long snackbar on %m.view with text %s"));
        arrayList.add(addBlock("snackbarWithAction", " ", "", "com.google.android.material.snackbar.Snackbar.make(%1$s,%2$s,com.google.android.material.snackbar.Snackbar.LENGTH_LONG).setAction(%3$s,v->{}).show();", "#ff9800", "show snackbar on %m.view text %s action %s"));
        
        // ─── SHARED PREFERENCES ─────────────────────────────────────────────────────
        arrayList.add(addBlock("prefSaveString", " ", "", "getSharedPreferences(%1$s,0).edit().putString(%2$s,%3$s).apply();", "#795548", "save string %s to prefs %s with key %s"));
        arrayList.add(addBlock("prefGetString", "s", "", "getSharedPreferences(%1$s,0).getString(%2$s,%3$s)", "#795548", "get string from prefs %s key %s default %s"));
        arrayList.add(addBlock("prefSaveInt", " ", "", "getSharedPreferences(%1$s,0).edit().putInt(%2$s,%3$s).apply();", "#795548", "save int %d to prefs %s key %s"));
        arrayList.add(addBlock("prefGetInt", "d", "", "getSharedPreferences(%1$s,0).getInt(%2$s,%3$s)", "#795548", "get int from prefs %s key %s default %d"));
        arrayList.add(addBlock("prefSaveBoolean", " ", "", "getSharedPreferences(%1$s,0).edit().putBoolean(%2$s,%3$s).apply();", "#795548", "save boolean %z to prefs %s key %s"));
        arrayList.add(addBlock("prefGetBoolean", "z", "", "getSharedPreferences(%1$s,0).getBoolean(%2$s,%3$s)", "#795548", "get boolean from prefs %s key %s default %z"));
        arrayList.add(addBlock("prefSaveFloat", " ", "", "getSharedPreferences(%1$s,0).edit().putFloat(%2$s,%3$s).apply();", "#795548", "save float %f to prefs %s key %s"));
        arrayList.add(addBlock("prefGetFloat", "f", "", "getSharedPreferences(%1$s,0).getFloat(%2$s,%3$s)", "#795548", "get float from prefs %s key %s default %f"));
        arrayList.add(addBlock("prefRemoveKey", " ", "", "getSharedPreferences(%1$s,0).edit().remove(%2$s).apply();", "#795548", "remove key %s from prefs %s"));
        arrayList.add(addBlock("prefClearAll", " ", "", "getSharedPreferences(%s,0).edit().clear().apply();", "#795548", "clear all prefs %s"));
        arrayList.add(addBlock("prefContainsKey", "z", "", "getSharedPreferences(%1$s,0).contains(%2$s)", "#795548", "prefs %s contains key %s"));
        
        // ─── DATE & TIME ─────────────────────────────────────────────────────────────
        arrayList.add(addBlock("getCurrentTimeMillis", "d", "", "(int)System.currentTimeMillis()", "#009688", "current time millis"));
        arrayList.add(addBlock("getCurrentDateFormatted", "s", "", "new java.text.SimpleDateFormat(%s, java.util.Locale.getDefault()).format(new java.util.Date())", "#009688", "current date formatted as %s"));
        arrayList.add(addBlock("formatDate", "s", "", "new java.text.SimpleDateFormat(%2$s,java.util.Locale.getDefault()).format(new java.util.Date(%1$s))", "#009688", "format timestamp %d as %s"));
        arrayList.add(addBlock("getCurrentYear", "d", "", "java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)", "#009688", "current year"));
        arrayList.add(addBlock("getCurrentMonth", "d", "", "java.util.Calendar.getInstance().get(java.util.Calendar.MONTH)+1", "#009688", "current month (1-12)"));
        arrayList.add(addBlock("getCurrentDay", "d", "", "java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH)", "#009688", "current day of month"));
        arrayList.add(addBlock("getCurrentHour", "d", "", "java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)", "#009688", "current hour (24h)"));
        arrayList.add(addBlock("getCurrentMinute", "d", "", "java.util.Calendar.getInstance().get(java.util.Calendar.MINUTE)", "#009688", "current minute"));
        arrayList.add(addBlock("getCurrentSecond", "d", "", "java.util.Calendar.getInstance().get(java.util.Calendar.SECOND)", "#009688", "current second"));
        arrayList.add(addBlock("getDayOfWeek", "d", "", "java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_WEEK)", "#009688", "day of week (1=Sun)"));
        arrayList.add(addBlock("getDayOfYear", "d", "", "java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_YEAR)", "#009688", "day of year"));
        arrayList.add(addBlock("getWeekOfYear", "d", "", "java.util.Calendar.getInstance().get(java.util.Calendar.WEEK_OF_YEAR)", "#009688", "week of year"));
        arrayList.add(addBlock("isLeapYear", "z", "", "new java.util.GregorianCalendar().isLeapYear(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR))", "#009688", "current year is leap year"));
        arrayList.add(addBlock("daysBetween", "d", "", "(int)((java.util.concurrent.TimeUnit.MILLISECONDS.toDays(%2$s-%1$s)))", "#009688", "days between timestamp %d and %d"));
        arrayList.add(addBlock("addDaysToTimestamp", "d", "", "(int)(%1$s + java.util.concurrent.TimeUnit.DAYS.toMillis(%2$s))", "#009688", "add %d days to timestamp %d"));
        arrayList.add(addBlock("timestampToDateString", "s", "", "new java.text.SimpleDateFormat(\"dd/MM/yyyy\",java.util.Locale.getDefault()).format(new java.util.Date(%s))", "#009688", "timestamp %d as dd/MM/yyyy"));
        arrayList.add(addBlock("timestampToTimeString", "s", "", "new java.text.SimpleDateFormat(\"HH:mm:ss\",java.util.Locale.getDefault()).format(new java.util.Date(%s))", "#009688", "timestamp %d as HH:mm:ss"));
        
        // ─── NETWORK ─────────────────────────────────────────────────────────────────
        arrayList.add(addBlock("isNetworkAvailable", "z", "", "((android.net.ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo()!=null&&((android.net.ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo().isConnected()", "#607d8b", "network is available"));
        arrayList.add(addBlock("isWifiConnected", "z", "", "((android.net.ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo()!=null&&((android.net.ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo().getType()==android.net.ConnectivityManager.TYPE_WIFI", "#607d8b", "Wi-Fi is connected"));
        arrayList.add(addBlock("isMobileDataConnected", "z", "", "((android.net.ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo()!=null&&((android.net.ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo().getType()==android.net.ConnectivityManager.TYPE_MOBILE", "#607d8b", "mobile data is connected"));
        arrayList.add(addBlock("openWifiPanel", " ", "", "startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));", "#607d8b", "open Wi-Fi panel"));
        arrayList.add(addBlock("getIPAddress", "s", "", "new java.util.function.Supplier<String>(){public String get(){try{java.util.Enumeration<java.net.NetworkInterface> e=java.net.NetworkInterface.getNetworkInterfaces();while(e.hasMoreElements()){java.net.NetworkInterface n=e.nextElement();java.util.Enumeration<java.net.InetAddress> a=n.getInetAddresses();while(a.hasMoreElements()){java.net.InetAddress i=a.nextElement();if(!i.isLoopbackAddress()&&i instanceof java.net.Inet4Address)return i.getHostAddress();}}return \"\";}catch(Exception ex){return \"\";}}}.get()", "#607d8b", "device IP address"));
        
        // ─── FILE & STORAGE ──────────────────────────────────────────────────────────
        arrayList.add(addBlock("writeTextToFile", " ", "", "try{java.io.FileWriter fw=new java.io.FileWriter(%1$s);fw.write(%2$s);fw.close();}catch(Exception e){e.printStackTrace();}", "#8bc34a", "write %s to file at path %s"));
        arrayList.add(addBlock("appendTextToFile", " ", "", "try{java.io.FileWriter fw=new java.io.FileWriter(%1$s,true);fw.write(%2$s);fw.close();}catch(Exception e){e.printStackTrace();}", "#8bc34a", "append %s to file at path %s"));
        arrayList.add(addBlock("readTextFromFile", "s", "", "new java.util.function.Supplier<String>(){public String get(){try{java.io.BufferedReader br=new java.io.BufferedReader(new java.io.FileReader(%s));StringBuilder sb=new StringBuilder();String l;while((l=br.readLine())!=null)sb.append(l).append(\"\\n\");br.close();return sb.toString();}catch(Exception e){return \"\";}}}.get()", "#8bc34a", "read text from file at path %s"));
        arrayList.add(addBlock("deleteFile", "z", "", "new java.io.File(%s).delete()", "#8bc34a", "delete file at path %s"));
        arrayList.add(addBlock("fileExists", "z", "", "new java.io.File(%s).exists()", "#8bc34a", "file exists at path %s"));
        arrayList.add(addBlock("isDirectory", "z", "", "new java.io.File(%s).isDirectory()", "#8bc34a", "path %s is a directory"));
        arrayList.add(addBlock("createDirectory", "z", "", "new java.io.File(%s).mkdirs()", "#8bc34a", "create directory at path %s"));
        arrayList.add(addBlock("getFileSize", "d", "", "(int)new java.io.File(%s).length()", "#8bc34a", "file size bytes at path %s"));
        arrayList.add(addBlock("getFileName", "s", "", "new java.io.File(%s).getName()", "#8bc34a", "file name at path %s"));
        arrayList.add(addBlock("getParentPath", "s", "", "new java.io.File(%s).getParent()", "#8bc34a", "parent path of %s"));
        arrayList.add(addBlock("getExternalStoragePath", "s", "", "android.os.Environment.getExternalStorageDirectory().getAbsolutePath()", "#8bc34a", "external storage path"));
        arrayList.add(addBlock("getInternalStoragePath", "s", "", "getFilesDir().getAbsolutePath()", "#8bc34a", "internal files path"));
        arrayList.add(addBlock("getCachePath", "s", "", "getCacheDir().getAbsolutePath()", "#8bc34a", "cache directory path"));
        arrayList.add(addBlock("getAvailableStorageMB", "d", "", "(int)(new android.os.StatFs(android.os.Environment.getExternalStorageDirectory().getPath()).getAvailableBlocksLong()*new android.os.StatFs(android.os.Environment.getExternalStorageDirectory().getPath()).getBlockSizeLong()/1048576)", "#8bc34a", "available external storage MB"));
        arrayList.add(addBlock("renameFile", "z", "", "new java.io.File(%1$s).renameTo(new java.io.File(%2$s))", "#8bc34a", "rename file %s to %s"));
        arrayList.add(addBlock("listFilesInDir", "s", "", "java.util.Arrays.toString(new java.io.File(%s).list())", "#8bc34a", "list files in directory %s"));
        arrayList.add(addBlock("copyFile", " ", "", "try{java.nio.file.Files.copy(java.nio.file.Paths.get(%1$s),java.nio.file.Paths.get(%2$s),java.nio.file.StandardCopyOption.REPLACE_EXISTING);}catch(Exception e){e.printStackTrace();}", "#8bc34a", "copy file from %s to %s"));
        arrayList.add(addBlock("getFileExtension", "s", "", "{String _p=%s;int _i=_p.lastIndexOf('.');_i>=0?_p.substring(_i+1):\"\"}", "#8bc34a", "file extension of path %s"));
        
        // ─── COLOR ────────────────────────────────────────────────────────────────────
        arrayList.add(addBlock("colorParseHex", "d", "", "android.graphics.Color.parseColor(%s)", "#f44336", "parse hex color %s"));
        arrayList.add(addBlock("colorRGB", "d", "", "android.graphics.Color.rgb(%1$s,%2$s,%3$s)", "#f44336", "color from R %d G %d B %d"));
        arrayList.add(addBlock("colorARGB", "d", "", "android.graphics.Color.argb(%1$s,%2$s,%3$s,%4$s)", "#f44336", "color from A %d R %d G %d B %d"));
        arrayList.add(addBlock("colorGetRed", "d", "", "android.graphics.Color.red(%s)", "#f44336", "red channel of color %d"));
        arrayList.add(addBlock("colorGetGreen", "d", "", "android.graphics.Color.green(%s)", "#f44336", "green channel of color %d"));
        arrayList.add(addBlock("colorGetBlue", "d", "", "android.graphics.Color.blue(%s)", "#f44336", "blue channel of color %d"));
        arrayList.add(addBlock("colorGetAlpha", "d", "", "android.graphics.Color.alpha(%s)", "#f44336", "alpha channel of color %d"));
        arrayList.add(addBlock("colorToHex", "s", "", "String.format(\"#%06X\",(0xFFFFFF & %s))", "#f44336", "color %d as hex string"));
        arrayList.add(addBlock("colorLighten", "d", "", "android.graphics.Color.argb(android.graphics.Color.alpha(%1$s),Math.min(255,android.graphics.Color.red(%1$s)+%2$s),Math.min(255,android.graphics.Color.green(%1$s)+%2$s),Math.min(255,android.graphics.Color.blue(%1$s)+%2$s))", "#f44336", "lighten color %d by %d"));
        arrayList.add(addBlock("colorDarken", "d", "", "android.graphics.Color.argb(android.graphics.Color.alpha(%1$s),Math.max(0,android.graphics.Color.red(%1$s)-%2$s),Math.max(0,android.graphics.Color.green(%1$s)-%2$s),Math.max(0,android.graphics.Color.blue(%1$s)-%2$s))", "#f44336", "darken color %d by %d"));
        arrayList.add(addBlock("colorSetAlpha", "d", "", "android.graphics.Color.argb(%2$s,android.graphics.Color.red(%1$s),android.graphics.Color.green(%1$s),android.graphics.Color.blue(%1$s))", "#f44336", "set alpha %d on color %d"));
        
        // ─── NOTIFICATION ─────────────────────────────────────────────────────────────
        arrayList.add(addBlock("createNotificationChannel", " ", "", "if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.O){android.app.NotificationChannel ch=new android.app.NotificationChannel(%1$s,%2$s,android.app.NotificationManager.IMPORTANCE_DEFAULT);((android.app.NotificationManager)getSystemService(android.app.NotificationManager.class)).createNotificationChannel(ch);}", "#673ab7", "create notification channel id %s name %s"));
        arrayList.add(addBlock("showNotification", " ", "", "{android.app.NotificationCompat.Builder b=new android.app.NotificationCompat.Builder(this,%1$s).setSmallIcon(android.R.drawable.ic_dialog_info).setContentTitle(%2$s).setContentText(%3$s).setAutoCancel(true);((android.app.NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE)).notify(%4$s,b.build());}", "#673ab7", "show notification channel %s title %s text %s id %d"));
        arrayList.add(addBlock("cancelNotification", " ", "", "((android.app.NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE)).cancel(%s);", "#673ab7", "cancel notification id %d"));
        arrayList.add(addBlock("cancelAllNotifications", " ", "", "((android.app.NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();", "#673ab7", "cancel all notifications"));
        
        // ─── DIALOG ───────────────────────────────────────────────────────────────────
        arrayList.add(addBlock("showAlertDialog", " ", "", "new android.app.AlertDialog.Builder(this).setTitle(%1$s).setMessage(%2$s).setPositiveButton(%3$s,null).show();", "#3f51b5", "show alert title %s message %s button %s"));
        arrayList.add(addBlock("showConfirmDialog", " ", "", "new android.app.AlertDialog.Builder(this).setTitle(%1$s).setMessage(%2$s).setPositiveButton(\"OK\",(d,w)->{}).setNegativeButton(\"Cancel\",null).show();", "#3f51b5", "show confirm dialog title %s message %s"));
        arrayList.add(addBlock("showProgressDialog", " ", "", "{android.app.ProgressDialog pd=new android.app.ProgressDialog(this);pd.setMessage(%s);pd.setCancelable(false);pd.show();}", "#3f51b5", "show progress dialog message %s"));
        arrayList.add(addBlock("showInputDialog", " ", "", "{android.widget.EditText et=new android.widget.EditText(this);new android.app.AlertDialog.Builder(this).setTitle(%1$s).setView(et).setPositiveButton(\"OK\",null).setNegativeButton(\"Cancel\",null).show();}", "#3f51b5", "show input dialog title %s"));
        
        // ─── AUDIO ────────────────────────────────────────────────────────────────────
        arrayList.add(addBlock("setRingerModeNormal", " ", "", "((android.media.AudioManager)getSystemService(Context.AUDIO_SERVICE)).setRingerMode(android.media.AudioManager.RINGER_MODE_NORMAL);", "#00bcd4", "set ringer mode to normal"));
        arrayList.add(addBlock("setRingerModeSilent", " ", "", "((android.media.AudioManager)getSystemService(Context.AUDIO_SERVICE)).setRingerMode(android.media.AudioManager.RINGER_MODE_SILENT);", "#00bcd4", "set ringer mode to silent"));
        arrayList.add(addBlock("setRingerModeVibrate", " ", "", "((android.media.AudioManager)getSystemService(Context.AUDIO_SERVICE)).setRingerMode(android.media.AudioManager.RINGER_MODE_VIBRATE);", "#00bcd4", "set ringer mode to vibrate"));
        arrayList.add(addBlock("getRingerMode", "d", "", "((android.media.AudioManager)getSystemService(Context.AUDIO_SERVICE)).getRingerMode()", "#00bcd4", "current ringer mode"));
        arrayList.add(addBlock("getMediaVolume", "d", "", "((android.media.AudioManager)getSystemService(Context.AUDIO_SERVICE)).getStreamVolume(android.media.AudioManager.STREAM_MUSIC)", "#00bcd4", "current media volume"));
        arrayList.add(addBlock("getMaxMediaVolume", "d", "", "((android.media.AudioManager)getSystemService(Context.AUDIO_SERVICE)).getStreamMaxVolume(android.media.AudioManager.STREAM_MUSIC)", "#00bcd4", "max media volume"));
        arrayList.add(addBlock("setMediaVolume", " ", "", "((android.media.AudioManager)getSystemService(Context.AUDIO_SERVICE)).setStreamVolume(android.media.AudioManager.STREAM_MUSIC,%s,0);", "#00bcd4", "set media volume to %d"));
        arrayList.add(addBlock("isMusicPlaying", "z", "", "((android.media.AudioManager)getSystemService(Context.AUDIO_SERVICE)).isMusicActive()", "#00bcd4", "music is playing"));
        arrayList.add(addBlock("playSoundEffect", " ", "", "((android.media.AudioManager)getSystemService(Context.AUDIO_SERVICE)).playSoundEffect(android.media.AudioManager.FX_KEY_CLICK);", "#00bcd4", "play click sound effect"));
        
        // ─── RUNTIME / UTILITY ───────────────────────────────────────────────────────
        arrayList.add(addBlock("runOnMainThread", " ", "", "runOnUiThread(()->{%s});", "#ff5722", "run on main thread: %s"));
        arrayList.add(addBlock("runDelayed", " ", "", "new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(()->{%1$s},%2$s);", "#ff5722", "run after %d ms: %s"));
        arrayList.add(addBlock("runRepeating", " ", "", "{android.os.Handler _h=new android.os.Handler(android.os.Looper.getMainLooper());Runnable _r=new Runnable(){public void run(){%1$s;_h.postDelayed(this,%2$s);}};_h.postDelayed(_r,%2$s);}", "#ff5722", "repeat every %d ms: %s"));
        arrayList.add(addBlock("logDebug", " ", "", "android.util.Log.d(\"DEBUG\",%s);", "#ff5722", "log debug: %s"));
        arrayList.add(addBlock("logError", " ", "", "android.util.Log.e(\"ERROR\",%s);", "#ff5722", "log error: %s"));
        arrayList.add(addBlock("logInfo", " ", "", "android.util.Log.i(\"INFO\",%s);", "#ff5722", "log info: %s"));
        arrayList.add(addBlock("tryBlock", " ", "", "try{%s}catch(Exception e){android.util.Log.e(\"TRY\",e.getMessage());}", "#ff5722", "try: %s catch log error"));
        arrayList.add(addBlock("dpToPx", "d", "", "(int)(android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP,%s,getResources().getDisplayMetrics()))", "#ff5722", "%d dp as pixels"));
        arrayList.add(addBlock("pxToDp", "f", "", "(%s/getResources().getDisplayMetrics().density)", "#ff5722", "%d px as dp"));
        arrayList.add(addBlock("spToPx", "d", "", "(int)(android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_SP,%s,getResources().getDisplayMetrics()))", "#ff5722", "%d sp as pixels"));
        arrayList.add(addBlock("getColorFromRes", "d", "", "androidx.core.content.ContextCompat.getColor(this,%s)", "#ff5722", "color resource %d"));
        arrayList.add(addBlock("getStringFromRes", "s", "", "getString(%s)", "#ff5722", "string resource %d"));
        arrayList.add(addBlock("getDimenFromRes", "f", "", "getResources().getDimension(%s)", "#ff5722", "dimension resource %d"));
        arrayList.add(addBlock("copyListToList", " ", "", "((java.util.List)%1$s).addAll((java.util.List)%2$s);", "#ff5722", "copy all items from list %m.list to list %m.list"));
        arrayList.add(addBlock("shuffleList", " ", "", "java.util.Collections.shuffle((java.util.List)%s);", "#ff5722", "shuffle list %m.list"));
        arrayList.add(addBlock("sortListAsc", " ", "", "java.util.Collections.sort((java.util.List)%s);", "#ff5722", "sort list %m.list ascending"));
        arrayList.add(addBlock("sortListDesc", " ", "", "java.util.Collections.sort((java.util.List)%s,java.util.Collections.reverseOrder());", "#ff5722", "sort list %m.list descending"));
        arrayList.add(addBlock("reverseList", " ", "", "java.util.Collections.reverse((java.util.List)%s);", "#ff5722", "reverse list %m.list"));
        arrayList.add(addBlock("listContains", "z", "", "((java.util.List)%1$s).contains(%2$s)", "#ff5722", "list %m.list contains %s"));
        arrayList.add(addBlock("uniqueList", " ", "", "{java.util.List _l=(java.util.List)%s;java.util.List _n=new java.util.ArrayList(new java.util.LinkedHashSet(_l));_l.clear();_l.addAll(_n);}", "#ff5722", "remove duplicates from list %m.list"));
        arrayList.add(addBlock("swapListItems", " ", "", "java.util.Collections.swap((java.util.List)%1$s,%2$s,%3$s);", "#ff5722", "swap items at index %d and %d in list %m.list"));
        arrayList.add(addBlock("listIndexOf", "d", "", "((java.util.List)%1$s).indexOf(%2$s)", "#ff5722", "index of %s in list %m.list"));
        arrayList.add(addBlock("listFrequency", "d", "", "java.util.Collections.frequency((java.util.List)%1$s,%2$s)", "#ff5722", "count of %s in list %m.list"));
        arrayList.add(addBlock("subList", " ", "", "new java.util.ArrayList(((java.util.List)%1$s).subList(%2$s,%3$s))", "#ff5722", "sub-list of %m.list from %d to %d"));
        arrayList.add(addBlock("joinListToString", "s", "", "android.text.TextUtils.join(%1$s,(java.util.List)%2$s)", "#ff5722", "join list %m.list with separator %s"));
        arrayList.add(addBlock("fillList", " ", "", "java.util.Collections.fill((java.util.List)%1$s,%2$s);", "#ff5722", "fill list %m.list with %s"));
        arrayList.add(addBlock("nCopiesList", " ", "", "(java.util.List)java.util.Collections.nCopies(%2$s,%1$s)", "#ff5722", "list of %d copies of %s"));
        
        // ─── PERMISSIONS ─────────────────────────────────────────────────────────────
        arrayList.add(addBlock("hasPermission", "z", "", "androidx.core.content.ContextCompat.checkSelfPermission(this,%s)==android.content.pm.PackageManager.PERMISSION_GRANTED", "#9e9e9e", "permission %s is granted"));
        arrayList.add(addBlock("requestPermission", " ", "", "androidx.core.app.ActivityCompat.requestPermissions(this,new String[]{%1$s},%2$s);", "#9e9e9e", "request permission %s with request code %d"));
        arrayList.add(addBlock("hasCameraPermission", "z", "", "androidx.core.content.ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)==android.content.pm.PackageManager.PERMISSION_GRANTED", "#9e9e9e", "camera permission is granted"));
        arrayList.add(addBlock("hasStoragePermission", "z", "", "androidx.core.content.ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)==android.content.pm.PackageManager.PERMISSION_GRANTED", "#9e9e9e", "storage permission is granted"));
        arrayList.add(addBlock("hasLocationPermission", "z", "", "androidx.core.content.ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)==android.content.pm.PackageManager.PERMISSION_GRANTED", "#9e9e9e", "location permission is granted"));
        arrayList.add(addBlock("hasMicrophonePermission", "z", "", "androidx.core.content.ContextCompat.checkSelfPermission(this,android.Manifest.permission.RECORD_AUDIO)==android.content.pm.PackageManager.PERMISSION_GRANTED", "#9e9e9e", "microphone permission is granted"));
        
        // ─── KEYBOARD ─────────────────────────────────────────────────────────────────
        arrayList.add(addBlock("showKeyboard", " ", "", "((android.view.inputmethod.InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(%s,android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT);", "#00897b", "show keyboard for %m.view"));
        arrayList.add(addBlock("hideKeyboard", " ", "", "((android.view.inputmethod.InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(%1$s.getWindowToken(),0);", "#00897b", "hide keyboard from %m.view"));
        arrayList.add(addBlock("toggleKeyboard", " ", "", "((android.view.inputmethod.InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(android.view.inputmethod.InputMethodManager.SHOW_FORCED,0);", "#00897b", "toggle keyboard"));
        arrayList.add(addBlock("isKeyboardVisible", "z", "", "((android.view.inputmethod.InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).isAcceptingText()", "#00897b", "keyboard is visible"));
        
        // ─── EDITTEXT HELPERS ─────────────────────────────────────────────────────────
        arrayList.add(addBlock("editTextSetHint", " ", "", "%1$s.setHint(%2$s);", "#1565c0", "set hint %s on %m.edittext"));
        arrayList.add(addBlock("editTextSetMaxLength", " ", "", "%1$s.setFilters(new android.text.InputFilter[]{new android.text.InputFilter.LengthFilter(%2$s)});", "#1565c0", "set max length %d on %m.edittext"));
        arrayList.add(addBlock("editTextSetTextSize", " ", "", "%1$s.setTextSize(%2$sf);", "#1565c0", "set text size %f on %m.edittext"));
        arrayList.add(addBlock("editTextSetTextColor", " ", "", "%1$s.setTextColor(android.graphics.Color.parseColor(%2$s));", "#1565c0", "set text color %s on %m.edittext"));
        arrayList.add(addBlock("editTextSelectAll", " ", "", "%s.selectAll();", "#1565c0", "select all text in %m.edittext"));
        arrayList.add(addBlock("editTextCursor", " ", "", "%1$s.setSelection(%2$s);", "#1565c0", "set cursor at position %d in %m.edittext"));
        arrayList.add(addBlock("editTextClearText", " ", "", "%s.setText(\"\");", "#1565c0", "clear text in %m.edittext"));
        arrayList.add(addBlock("editTextAppendText", " ", "", "%1$s.append(%2$s);", "#1565c0", "append %s to %m.edittext"));
        arrayList.add(addBlock("editTextSetInputTypeNumber", " ", "", "%s.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);", "#1565c0", "set %m.edittext input type to number"));
        arrayList.add(addBlock("editTextSetInputTypeText", " ", "", "%s.setInputType(android.text.InputType.TYPE_CLASS_TEXT);", "#1565c0", "set %m.edittext input type to text"));
        arrayList.add(addBlock("editTextSetInputTypePassword", " ", "", "%s.setInputType(android.text.InputType.TYPE_CLASS_TEXT|android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);", "#1565c0", "set %m.edittext input type to password"));
        arrayList.add(addBlock("editTextGetCursorPos", "d", "", "%s.getSelectionStart()", "#1565c0", "cursor position in %m.edittext"));
        
        // ─── RECYCLER / LIST ──────────────────────────────────────────────────────────
        arrayList.add(addBlock("recyclerSetLinearVertical", " ", "", "%s.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));", "#ad1457", "set %m.recycler layout to linear vertical"));
        arrayList.add(addBlock("recyclerSetLinearHorizontal", " ", "", "%s.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this,androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,false));", "#ad1457", "set %m.recycler layout to linear horizontal"));
        arrayList.add(addBlock("recyclerSetGrid", " ", "", "%1$s.setLayoutManager(new androidx.recyclerview.widget.GridLayoutManager(this,%2$s));", "#ad1457", "set %m.recycler layout to grid %d cols"));
        arrayList.add(addBlock("recyclerSetStaggeredGrid", " ", "", "%1$s.setLayoutManager(new androidx.recyclerview.widget.StaggeredGridLayoutManager(%2$s,androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL));", "#ad1457", "set %m.recycler to staggered grid %d cols"));
        arrayList.add(addBlock("recyclerScrollToTop", " ", "", "%s.scrollToPosition(0);", "#ad1457", "scroll %m.recycler to top"));
        arrayList.add(addBlock("recyclerScrollToPosition", " ", "", "%1$s.scrollToPosition(%2$s);", "#ad1457", "scroll %m.recycler to position %d"));
        arrayList.add(addBlock("recyclerScrollToBottom", " ", "", "%s.scrollToPosition(%s.getAdapter().getItemCount()-1);", "#ad1457", "scroll %m.recycler to bottom"));
        arrayList.add(addBlock("recyclerSetItemDecoration", " ", "", "%1$s.addItemDecoration(new androidx.recyclerview.widget.DividerItemDecoration(this,androidx.recyclerview.widget.DividerItemDecoration.VERTICAL));", "#ad1457", "add divider to %m.recycler"));
        arrayList.add(addBlock("recyclerDisableScrolling", " ", "", "%s.setNestedScrollingEnabled(false);", "#ad1457", "disable nested scrolling on %m.recycler"));
        arrayList.add(addBlock("recyclerEnableScrolling", " ", "", "%s.setNestedScrollingEnabled(true);", "#ad1457", "enable nested scrolling on %m.recycler"));
        arrayList.add(addBlock("recyclerGetItemCount", "d", "", "%s.getAdapter().getItemCount()", "#ad1457", "item count in %m.recycler"));
        
        // ─── BITMAP / IMAGE ───────────────────────────────────────────────────────────
        arrayList.add(addBlock("bitmapFromFile", "s", "", "android.graphics.BitmapFactory.decodeFile(%s)", "#e65100", "load bitmap from file path %s"));
        arrayList.add(addBlock("bitmapGetWidth", "d", "", "((android.graphics.Bitmap)%s).getWidth()", "#e65100", "bitmap %s width"));
        arrayList.add(addBlock("bitmapGetHeight", "d", "", "((android.graphics.Bitmap)%s).getHeight()", "#e65100", "bitmap %s height"));
        arrayList.add(addBlock("bitmapScale", " ", "", "android.graphics.Bitmap.createScaledBitmap((android.graphics.Bitmap)%1$s,%2$s,%3$s,true)", "#e65100", "scale bitmap %s to %d x %d"));
        arrayList.add(addBlock("bitmapRotate", " ", "", "{android.graphics.Matrix m=new android.graphics.Matrix();m.postRotate(%2$s);android.graphics.Bitmap.createBitmap((android.graphics.Bitmap)%1$s,0,0,((android.graphics.Bitmap)%1$s).getWidth(),((android.graphics.Bitmap)%1$s).getHeight(),m,true);}", "#e65100", "rotate bitmap %s by %f degrees"));
        arrayList.add(addBlock("imageViewSetBitmapFromPath", " ", "", "%1$s.setImageBitmap(android.graphics.BitmapFactory.decodeFile(%2$s));", "#e65100", "set %m.imageview image from path %s"));
        arrayList.add(addBlock("imageViewSetColorFilter", " ", "", "%1$s.setColorFilter(android.graphics.Color.parseColor(%2$s));", "#e65100", "set %m.imageview color filter to hex %s"));
        arrayList.add(addBlock("imageViewClearColorFilter", " ", "", "%s.clearColorFilter();", "#e65100", "clear color filter on %m.imageview"));
        arrayList.add(addBlock("imageViewSetScaleTypeFit", " ", "", "%s.setScaleType(android.widget.ImageView.ScaleType.FIT_CENTER);", "#e65100", "set %m.imageview scale type fit center"));
        arrayList.add(addBlock("imageViewSetScaleTypeCrop", " ", "", "%s.setScaleType(android.widget.ImageView.ScaleType.CENTER_CROP);", "#e65100", "set %m.imageview scale type center crop"));
        
        // ─── SECURITY ─────────────────────────────────────────────────────────────────
        arrayList.add(addBlock("isDeviceRooted", "z", "", "new java.io.File(\"/system/app/Superuser.apk\").exists()||new java.io.File(\"/sbin/su\").exists()||new java.io.File(\"/system/bin/su\").exists()", "#b71c1c", "device is rooted"));
        arrayList.add(addBlock("isRunningOnEmulator", "z", "", "android.os.Build.FINGERPRINT.startsWith(\"generic\")||android.os.Build.MODEL.contains(\"Emulator\")||android.os.Build.MODEL.contains(\"Android SDK\")", "#b71c1c", "running on emulator"));
        arrayList.add(addBlock("generateUUID", "s", "", "java.util.UUID.randomUUID().toString()", "#b71c1c", "generate UUID"));
        arrayList.add(addBlock("hashSHA256", "s", "", "new java.util.function.Supplier<String>(){public String get(){try{java.security.MessageDigest md=java.security.MessageDigest.getInstance(\"SHA-256\");byte[]b=md.digest(%s.getBytes(\"UTF-8\"));StringBuilder sb=new StringBuilder();for(byte x:b)sb.append(String.format(\"%02x\",x));return sb.toString();}catch(Exception e){return \"\";}}}.get()", "#b71c1c", "SHA-256 hash of %s"));
        arrayList.add(addBlock("encryptXOR", "s", "", "new java.util.function.Supplier<String>(){public String get(){byte[]b=%1$s.getBytes();byte[]k=%2$s.getBytes();StringBuilder sb=new StringBuilder();for(int i=0;i<b.length;i++)sb.append((char)(b[i]^k[i%%k.length]));return android.util.Base64.encodeToString(sb.toString().getBytes(),android.util.Base64.NO_WRAP);}}.get()", "#b71c1c", "XOR encrypt %s with key %s"));
        
        // ─── MISCELLANEOUS ────────────────────────────────────────────────────────────
        arrayList.add(addBlock("finishActivity", " ", "", "finish();", "#546e7a", "finish current activity"));
        arrayList.add(addBlock("finishAndRemoveTask", " ", "", "finishAndRemoveTask();", "#546e7a", "finish and remove task"));
        arrayList.add(addBlock("exitApp", " ", "", "System.exit(0);", "#546e7a", "exit app"));
        arrayList.add(addBlock("hideNavigationBar", " ", "", "getWindow().getDecorView().setSystemUiVisibility(android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);", "#546e7a", "hide navigation bar"));
        arrayList.add(addBlock("showNavigationBar", " ", "", "getWindow().getDecorView().setSystemUiVisibility(android.view.View.SYSTEM_UI_FLAG_VISIBLE);", "#546e7a", "show navigation bar"));
        arrayList.add(addBlock("setStatusBarColor", " ", "", "if(android.os.Build.VERSION.SDK_INT>=21)getWindow().setStatusBarColor(android.graphics.Color.parseColor(%s));", "#546e7a", "set status bar color to hex %s"));
        arrayList.add(addBlock("setNavigationBarColor", " ", "", "if(android.os.Build.VERSION.SDK_INT>=21)getWindow().setNavigationBarColor(android.graphics.Color.parseColor(%s));", "#546e7a", "set navigation bar color to hex %s"));
        arrayList.add(addBlock("immersiveFullscreen", " ", "", "getWindow().getDecorView().setSystemUiVisibility(android.view.View.SYSTEM_UI_FLAG_FULLSCREEN|android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);", "#546e7a", "set immersive fullscreen mode"));
        arrayList.add(addBlock("allowNetworkOnMainThread", " ", "", "android.os.StrictMode.setThreadPolicy(new android.os.StrictMode.ThreadPolicy.Builder().permitAll().build());", "#546e7a", "allow network on main thread (dev only)"));
        arrayList.add(addBlock("nullSafeString", "s", "", "%s != null ? %s : \"\"", "#546e7a", "null-safe string from %s"));
        arrayList.add(addBlock("nullCheck", "z", "", "%s == null", "#546e7a", "%s is null"));
        arrayList.add(addBlock("notNullCheck", "z", "", "%s != null", "#546e7a", "%s is not null"));
        arrayList.add(addBlock("ternaryString", "s", "", "(%1$s) ? %2$s : %3$s", "#546e7a", "if %z then %s else %s"));
        arrayList.add(addBlock("ternaryInt", "d", "", "(%1$s) ? %2$s : %3$s", "#546e7a", "if %z then %d else %d"));
        arrayList.add(addBlock("objectToString", "s", "", "String.valueOf(%s)", "#546e7a", "object %s as string"));
        arrayList.add(addBlock("getLocaleLanguage", "s", "", "java.util.Locale.getDefault().getLanguage()", "#546e7a", "device locale language"));
        arrayList.add(addBlock("getLocaleCountry", "s", "", "java.util.Locale.getDefault().getCountry()", "#546e7a", "device locale country"));
        arrayList.add(addBlock("setWindowTransparent", " ", "", "getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT));", "#546e7a", "set window background transparent"));
        arrayList.add(addBlock("overrideTransitionFade", " ", "", "overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);", "#546e7a", "override transition with fade"));
        arrayList.add(addBlock("overrideTransitionSlide", " ", "", "overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);", "#546e7a", "override transition with slide"));
        arrayList.add(addBlock("printLog", " ", "", "android.util.Log.d(\"LOG\",\"Line: \"+new Throwable().getStackTrace()[0].getLineNumber()+\" | \"+%s);", "#546e7a", "print log with line number: %s"));
        arrayList.add(addBlock("getActivityClass", "s", "", "getClass().getSimpleName()", "#546e7a", "current activity class name"));
        arrayList.add(addBlock("isFirstInstall", "z", "", "getPackageManager().getPackageInfo(getPackageName(),0).firstInstallTime==getPackageManager().getPackageInfo(getPackageName(),0).lastUpdateTime", "#546e7a", "app is freshly installed (not updated)"));   
    }

    /**
     * @return A block as HashMap&lt;String, Object&gt;.
     */
    private static HashMap<String, Object> addBlock(String name, String type, String typeName, String code, String color, String spec) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("type", type);
        map.put("typeName", typeName);
        map.put("code", code);
        map.put("color", color);
        map.put("palette", "-1");
        map.put("spec", spec);
        return map;
    }

    /**
     * @return A block as HashMap&lt;String, Object&gt; with spec2 (<code>e</code> type blocks)
     */
    private static HashMap<String, Object> addBlock(String name, String type, String typeName, String code, String color, String spec, String spec2) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("type", type);
        map.put("typeName", typeName);
        map.put("code", code);
        map.put("color", color);
        map.put("palette", "-1");
        map.put("spec", spec);
        map.put("spec2", spec2);
        return map;
    }

    public boolean isVariableUsed(int varId) {
        ArrayList<Pair<Integer, String>> arrayList = projectDataManager.k(javaName);
        ArrayList<Integer> variableList = new ArrayList<>();
        for (Pair<Integer, String> intStrPair : arrayList) {
            variableList.add(intStrPair.first);
        }
        return variableList.contains(varId);
    }

    public boolean isListUsed(int listId) {
        ArrayList<Pair<Integer, String>> arrayList = projectDataManager.j(javaName);
        ArrayList<Integer> listVar = new ArrayList<>();
        for (Pair<Integer, String> intStrPair : arrayList) {
            listVar.add(intStrPair.first);
        }
        return listVar.contains(listId);
    }

    public boolean isComponentUsed(int componentId) {
        return projectDataManager.f(javaName, componentId) || ConfigActivity.isSettingEnabled(ConfigActivity.SETTING_SHOW_EVERY_SINGLE_BLOCK);
    }

    public boolean isCustomVarUsed(String variable) {
        if (ConfigActivity.isSettingEnabled(ConfigActivity.SETTING_SHOW_EVERY_SINGLE_BLOCK)) {
            return true;
        }

        ArrayList<String> arrayList = new ArrayList<>();
        for (String variableName : projectDataManager.e(javaName, 5)) {
            Matcher matcher = Pattern.compile("^(\\w+)[\\s]+(\\w+)").matcher(variableName);
            while (matcher.find()) {
                arrayList.add(matcher.group(1));
            }
        }
        return arrayList.contains(variable);
    }

    public void eventBlocks() {
        if (eventName.equals("onCreateOptionsMenu")) {
            logicEditor.a("Menu Item", 0xff555555);
            logicEditor.a(" ", "menuItemSetVisible");
            logicEditor.a(" ", "menuItemSetEnabled");
            logicEditor.a("v", "menuFindItem");
        }
    }

    public void fileBlocks() {
        if (isCustomVarUsed("File")) {
            logicEditor.a("File Blocks", 0xff555555);
            logicEditor.a("b", "fileCanExecute");
            logicEditor.a("b", "fileCanRead");
            logicEditor.a("b", "fileCanWrite");
            logicEditor.a("s", "fileGetName");
            logicEditor.a("s", "fileGetParent");
            logicEditor.a("s", "fileGetPath");
            logicEditor.a("b", "fileIsHidden");
        }
    }
}
