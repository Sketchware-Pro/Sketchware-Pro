package a.a.a;

import static com.google.android.material.color.MaterialColors.harmonizeWithPrimary;

import android.content.Context;
import android.view.ContextThemeWrapper;

import pro.sketchware.R;
import pro.sketchware.SketchApplication;
import pro.sketchware.menu.DefaultExtraMenuBean;

public class kq {
    public static int a(String opcode, String blockType) {
        Context context = new ContextThemeWrapper(SketchApplication.getContext(), R.style.Theme_SketchwarePro);
        return a(context, opcode, blockType);
    }

    public static int a(Context context, String opcode, String blockType) {
        int viewType = harmonizeWithPrimary(context, 0xff4a6cd4);

        if (blockType.equals("h")) {
            return harmonizeWithPrimary(context, 0xffc88330);
        }

        return switch (opcode) {
            case "getResStr" -> harmonizeWithPrimary(context, 0xff7c83db);
            case "getVar" -> switch (blockType) {
                case "v" -> viewType;
                case "p" -> harmonizeWithPrimary(context, 0xff2ca5e2);
                case "l" -> harmonizeWithPrimary(context, 0xffcc5b22);
                default -> harmonizeWithPrimary(context, 0xffee7d16);
            };
            case "addListInt", "insertListInt", "deleteList", "getAtListInt", "indexListInt",
                 "lengthList", "containListInt", "clearList", "addListStr", "insertListStr",
                 "getAtListStr", "indexListStr", "containListStr", "addListMap", "insertListMap",
                 "getAtListMap", "setListMap", "containListMap", "addMapToList", "insertMapToList",
                 "getMapInList" -> harmonizeWithPrimary(context, 0xffcc5b22);
            case "setVarBoolean", "setVarInt", "increaseInt", "decreaseInt", "setVarString",
                 "mapCreateNew", "mapPut", "mapGet", "mapContainKey", "mapRemoveKey", "mapSize",
                 "mapIsEmpty", "mapClear", "mapGetAllKeys" ->
                    harmonizeWithPrimary(context, 0xffee7d16);
            case "repeat", "forever", "break", "if", "ifElse" ->
                    harmonizeWithPrimary(context, 0xffe1a92a);
            case "true", "false", "<", "=", ">", "&&", "||", "not", "+", "-", "*", "/", "%",
                 "random", "stringLength", "stringJoin", "stringIndex", "stringLastIndex",
                 "stringSub", "stringEquals", "stringContains", "stringReplace",
                 "stringReplaceFirst", "stringReplaceAll", "toNumber", "trim", "toUpperCase",
                 "toLowerCase", "toString", "toStringWithDecimal", "toStringFormat",
                 "addSourceDirectly", "strToMap", "mapToStr", "strToListMap", "listMapToStr" ->
                    harmonizeWithPrimary(context, 0xff5cb722);
            case "mathGetDip", "mathGetDisplayWidth", "mathGetDisplayHeight", "mathPi", "mathE",
                 "mathPow", "mathMin", "mathMax", "mathSqrt", "mathAbs", "mathRound", "mathCeil",
                 "mathFloor", "mathSin", "mathCos", "mathTan", "mathAsin", "mathAcos", "mathAtan",
                 "mathExp", "mathLog", "mathLog10", "mathToRadian", "mathToDegree" ->
                    harmonizeWithPrimary(context, 0xff23b9a9);
            case "viewOnClick", "isDrawerOpen", "openDrawer", "closeDrawer", "setEnable",
                 "getEnable", "setVisible", "setClickable", "setText", "setTypeface", "getText",
                 "setBgColor", "setBgResource", "setTextColor", "setImage", "setColorFilter",
                 "requestFocus", "setRotate", "getRotate", "setAlpha", "getAlpha",
                 "setTranslationX", "getTranslationX", "setTranslationY", "getTranslationY",
                 "setScaleX", "getScaleX", "setScaleY", "getScaleY", "getLocationX", "getLocationY",
                 "setChecked", "getChecked", "seekBarGetMax", "seekBarGetProgress", "seekBarSetMax",
                 "seekBarSetProgress", "setThumbResource", "setTrackResource", "listSetData",
                 "listSetCustomViewData", "listRefresh", "listSetItemChecked",
                 "listGetCheckedPosition", "listGetCheckedPositions", "listGetCheckedCount",
                 "listSmoothScrollTo", "spnSetData", "spnRefresh", "spnSetSelection",
                 "spnGetSelection", "webViewLoadUrl", "webViewGetUrl", "webViewSetCacheMode",
                 "webViewCanGoBack", "webViewCanGoForward", "webViewGoBack", "webViewGoForward",
                 "webViewClearCache", "webViewClearHistory", "webViewStopLoading", "webViewZoomIn",
                 "webViewZoomOut", "calendarViewGetDate", "calendarViewSetDate",
                 "calendarViewSetMinDate", "calnedarViewSetMaxDate", "adViewLoadAd",
                 "setImageFilePath", "setImageUrl", "setHint", "setHintTextColor",
                 "progressBarSetIndeterminate", "mapViewSetMapType", "mapViewMoveCamera",
                 "mapViewZoomTo", "mapViewZoomIn", "mapViewZoomOut", "mapViewAddMarker",
                 "mapViewSetMarkerInfo", "mapViewSetMarkerPosition", "mapViewSetMarkerColor",
                 "mapViewSetMarkerIcon", "mapViewSetMarkerVisible" -> viewType;
            case "doToast", "copyToClipboard", "setTitle", "intentSetAction", "intentSetData",
                 "intentSetScreen", "intentPutExtra", "intentSetFlags", "startActivity",
                 "intentGetString", "finishActivity", "fileGetData", "fileSetData",
                 "fileRemoveData", "calendarGetNow", "calendarAdd", "calendarSet", "calendarFormat",
                 "calendarDiff", "calendarGetTime", "calendarSetTime", "vibratorAction",
                 "timerAfter", "timerEvery", "timerCancel", "dialogSetTitle", "dialogSetMessage",
                 "dialogOkButton", "dialogCancelButton", "dialogNeutralButton", "dialogShow",
                 "dialogDismiss", "mediaplayerCreate", "mediaplayerStart", "mediaplayerPause",
                 "mediaplayerSeek", "mediaplayerGetCurrent", "mediaplayerGetDuration",
                 "mediaplayerIsPlaying", "mediaplayerSetLooping", "mediaplayerIsLooping",
                 "mediaplayerReset", "mediaplayerRelease", "soundpoolCreate", "soundpoolLoad",
                 "soundpoolStreamPlay", "soundpoolStreamStop", "objectanimatorSetTarget",
                 "objectanimatorSetProperty", "objectanimatorSetValue", "objectanimatorSetFromTo",
                 "objectanimatorSetDuration", "objectanimatorSetRepeatMode",
                 "objectanimatorSetRepeatCount", "objectanimatorSetInterpolator",
                 "objectanimatorStart", "objectanimatorCancel", "objectanimatorIsRunning",
                 "firebaseAdd", "firebaseDelete", "firebasePush", "firebaseGetPushKey",
                 "firebaseGetChildren", "firebaseauthCreateUser", "firebaseauthSignInUser",
                 "firebaseauthSignInAnonymously", "firebaseauthIsLoggedIn",
                 "firebaseauthGetCurrentUser", "firebaseauthGetUid", "firebaseauthResetPassword",
                 "firebaseauthSignOutUser", "firebaseStartListen", "firebaseStopListen",
                 "gyroscopeStartListen", "gyroscopeStopListen", "interstitialadCreate",
                 "interstitialadLoadAd", "interstitialadShow", "firebasestorageUploadFile",
                 "firebasestorageDownloadFile", "firebasestorageDelete", "camerastarttakepicture",
                 "filepickerstartpickfiles", "requestnetworkSetParams", "requestnetworkSetHeaders",
                 "requestnetworkStartRequestNetwork", "textToSpeechSetPitch",
                 "textToSpeechSetSpeechRate", "textToSpeechSpeak", "textToSpeechIsSpeaking",
                 "textToSpeechStop", "textToSpeechShutdown", "speechToTextStartListening",
                 "speechToTextStopListening", "speechToTextShutdown",
                 "bluetoothConnectReadyConnection", "bluetoothConnectReadyConnectionToUuid",
                 "bluetoothConnectStartConnection", "bluetoothConnectStartConnectionToUuid",
                 "bluetoothConnectStopConnection", "bluetoothConnectSendData",
                 "bluetoothConnectIsBluetoothEnabled", "bluetoothConnectIsBluetoothActivated",
                 "bluetoothConnectActivateBluetooth", "bluetoothConnectGetPairedDevices",
                 "bluetoothConnectGetRandomUuid", "locationManagerRequestLocationUpdates",
                 "locationManagerRemoveUpdates" -> harmonizeWithPrimary(context, 0xff2ca5e2);
            case "fileutildelete", "fileutilcopy", "fileutilwrite", "fileutilread", "fileutilmove",
                 "fileutilisexist", "fileutilmakedir", "fileutillistdir", "fileutilisdir",
                 "fileutilisfile", "fileutillength", "fileutilStartsWith", "fileutilEndsWith",
                 "fileutilGetLastSegmentPath", "getExternalStorageDir", "getPackageDataDir",
                 "getPublicDir", "resizeBitmapFileRetainRatio", "resizeBitmapFileToSquare",
                 "resizeBitmapFileToCircle", "resizeBitmapFileWithRoundedBorder",
                 "cropBitmapFileFromCenter", "rotateBitmapFile", "scaleBitmapFile",
                 "skewBitmapFile", "setBitmapFileColorFilter", "setBitmapFileBrightness",
                 "setBitmapFileContrast", "getJpegRotate" ->
                    harmonizeWithPrimary(context, 0xffa1887f);
            default -> 0xff8a55d7;
        };
    }

    public static String a(int listType) {
        return switch (listType) {
            case 1 -> "List Number";
            case 2 -> "List String";
            case 3 -> "List Map";
            default -> "";
        };
    }

    public static String a(String blockName) {
        return switch (blockName) {
            case "spinner", "adview", "textview", "switch", "imageview", "calendarview", "view",
                 "progressbar", "webview", "listview", "checkbox", "edittext", "mapview" -> "v";
            case "soundpool", "requestnetwork", "objectanimator", "dialog", "texttospeech",
                 "intent", "locationmanager", "firebase", "speechtotext", "calendar", "file",
                 "firebaseauth", "timer", "gyroscope", "mediaplayer", "bluetoothconnect",
                 "vibrator", "firebasestorage", "phoneauth",
                 "googlelogin", "cloudmessage" -> "p";
            case "varMap" -> "a";
            case "listInt", "listMap", "listStr" -> "l";
            default -> "v";
        };
    }

    public static String b(int var0) {
        return var0 == 3 ? "Map" : "";
    }

    /**
     * @return The placeholder name for block menu from its type name
     */
    public static String b(String typeName) {
        return switch (typeName) {
            case "spinner" -> "Spinner";
            case "tablayout" -> "TabLayout";
            case "soundpool" -> "SoundPool";
            case "requestnetwork" -> "RequestNetwork";
            case "filepicker" -> "FilePicker";
            case "patternview" -> "PatternLockView";
            case "objectanimator" -> "ObjectAnimator";
            case "viewpager" -> "ViewPager";
            case "adview" -> "AdView";
            case "progressdialog" -> "ProgressDialog";
            case "camera" -> "Camera";
            case "dialog" -> "Dialog";
            case "datepickerdialog" -> "DatePickerDialog";
            case "texttospeech" -> "TextToSpeech";
            case "intent" -> "Intent";
            case "textview" -> "TextView";
            case "locationmanager" -> "LocationManager";
            case "switch" -> "Switch";
            case "imageview" -> "ImageView";
            case "varInt" -> "Number";
            case "varMap" -> "Map";
            case "varStr" -> "String";
            case "searchview" -> "SearchView";
            case "firebase" -> "Firebase DB";
            case "bottomnavigation" -> "BottomNavigation";
            case "calendarview" -> "CalendarView";
            case "speechtotext" -> "SpeechToText";
            case "calendar" -> "Calendar";
            case "actv" -> "AutoComplete";
            case "file" -> "SharedPreferences";
            case "list" -> "List";
            case "view" -> "View";
            case "radiobutton" -> "RadioButton";
            case "firebaseauth" -> "Firebase Auth";
            case "mactv" -> "MultiAutoComplete";
            case "timer" -> "Timer";
            case "listInt" -> "List Number";
            case "listMap" -> "List Map";
            case "listStr" -> "List String";
            case "varBool" -> "Boolean";
            case "gridview" -> "GridView";
            case "gyroscope" -> "Gyroscope";
            case "ratingbar" -> "RatingBar";
            case "videoad" -> "VideoAd";
            case "mediaplayer" -> "MediaPlayer";
            case "notification" -> "Notification";
            case "bluetoothconnect" -> "BluetoothConnect";
            case "mapview" -> "MapView";
            case "vibrator" -> "Vibrator";
            case "progressbar" -> "ProgressBar";
            case "webview" -> "WebView";
            case "interstitialad" -> "InterstitialAd";
            case "videoview" -> "VideoView";
            case "listBool" -> "List Boolean";
            case "listview" -> "ListView";
            case "checkbox" -> "CheckBox";
            case "edittext" -> "EditText";
            case "firebasestorage" -> "FirebaseStorage";
            case "timepickerdialog" -> "TimePickerDialog";
            case "seekbar" -> "SeekBar";
            case "sidebar" -> "WaveSideBar";
            case "badgeview" -> "BadgeView";
            case "circleimageview" -> "CircleImageView";
            case "customViews" -> "CustomView";
            case "asynctask" -> "AsyncTask";
            case "activity" -> "Context";
            case "otpview" -> "OTPView";
            case "lottie" -> "LottieAnimation";
            case "phoneauth" -> "FirebasePhoneAuth";
            case "codeview" -> "CodeView";
            case "recyclerview" -> "RecyclerView";
            case "resource" -> "Image";
            case "googlelogin" -> "FirebaseGoogleSignIn";
            case "youtubeview" -> "YoutubePlayer";
            case "cardview" -> "CardView";
            case "radiogroup" -> "RadioGroup";
            case "color" -> "Color";
            case "textinputlayout" -> "TextInputLayout";
            case "collapsingtoolbar" -> "CollapsingToolbarLayout";
            case "cloudmessage" -> "FirebaseCloudMessage";
            case "resource_bg" -> "BackgroundImage";
            case "datepicker" -> "DatePicker";
            case "timepicker" -> "TimePicker";
            case "swiperefreshlayout" -> "SwipeRefreshLayout";
            case "signinbutton" -> "SignInButton";
            case "materialButton" -> "MaterialButton";
            case "fragmentAdapter" -> "FragmentAdapter";
            default -> DefaultExtraMenuBean.getName(typeName);
        };
    }
}