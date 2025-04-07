package a.a.a;

import pro.sketchware.menu.DefaultExtraMenuBean;

public class kq {
    public static int a(String opcode, String blockType) {
        int viewType = 0xff4a6cd4;

        if (blockType.equals("h")) {
            return 0xffc88330;
        }

        switch (opcode) {
            case "getResStr":
                return 0xff7c83db;
            case "getVar":
                return switch (blockType) {
                    case "v" -> viewType;
                    case "p" -> 0xff2ca5e2;
                    case "l" -> 0xffcc5b22;
                    default -> 0xffee7d16;
                };
            case "addListInt":
            case "insertListInt":
            case "deleteList":
            case "getAtListInt":
            case "indexListInt":
            case "lengthList":
            case "containListInt":
            case "clearList":
            case "addListStr":
            case "insertListStr":
            case "getAtListStr":
            case "indexListStr":
            case "containListStr":
            case "addListMap":
            case "insertListMap":
            case "getAtListMap":
            case "setListMap":
            case "containListMap":
            case "addMapToList":
            case "insertMapToList":
            case "getMapInList":
                return 0xffcc5b22;
            case "setVarBoolean":
            case "setVarInt":
            case "increaseInt":
            case "decreaseInt":
            case "setVarString":
            case "mapCreateNew":
            case "mapPut":
            case "mapGet":
            case "mapContainKey":
            case "mapRemoveKey":
            case "mapSize":
            case "mapIsEmpty":
            case "mapClear":
            case "mapGetAllKeys":
                return 0xffee7d16;
            case "repeat":
            case "forever":
            case "break":
            case "if":
            case "ifElse":
                return 0xffe1a92a;
            case "true":
            case "false":
            case "<":
            case "=":
            case ">":
            case "&&":
            case "||":
            case "not":
            case "+":
            case "-":
            case "*":
            case "/":
            case "%":
            case "random":
            case "stringLength":
            case "stringJoin":
            case "stringIndex":
            case "stringLastIndex":
            case "stringSub":
            case "stringEquals":
            case "stringContains":
            case "stringReplace":
            case "stringReplaceFirst":
            case "stringReplaceAll":
            case "toNumber":
            case "trim":
            case "toUpperCase":
            case "toLowerCase":
            case "toString":
            case "toStringWithDecimal":
            case "toStringFormat":
            case "addSourceDirectly":
            case "strToMap":
            case "mapToStr":
            case "strToListMap":
            case "listMapToStr":
                return 0xff5cb722;
            case "mathGetDip":
            case "mathGetDisplayWidth":
            case "mathGetDisplayHeight":
            case "mathPi":
            case "mathE":
            case "mathPow":
            case "mathMin":
            case "mathMax":
            case "mathSqrt":
            case "mathAbs":
            case "mathRound":
            case "mathCeil":
            case "mathFloor":
            case "mathSin":
            case "mathCos":
            case "mathTan":
            case "mathAsin":
            case "mathAcos":
            case "mathAtan":
            case "mathExp":
            case "mathLog":
            case "mathLog10":
            case "mathToRadian":
            case "mathToDegree":
                return 0xff23b9a9;
            case "viewOnClick":
            case "isDrawerOpen":
            case "openDrawer":
            case "closeDrawer":
            case "setEnable":
            case "getEnable":
            case "setVisible":
            case "setClickable":
            case "setText":
            case "setTypeface":
            case "getText":
            case "setBgColor":
            case "setBgResource":
            case "setTextColor":
            case "setImage":
            case "setColorFilter":
            case "requestFocus":
            case "setRotate":
            case "getRotate":
            case "setAlpha":
            case "getAlpha":
            case "setTranslationX":
            case "getTranslationX":
            case "setTranslationY":
            case "getTranslationY":
            case "setScaleX":
            case "getScaleX":
            case "setScaleY":
            case "getScaleY":
            case "getLocationX":
            case "getLocationY":
            case "setChecked":
            case "getChecked":
            case "seekBarGetMax":
            case "seekBarGetProgress":
            case "seekBarSetMax":
            case "seekBarSetProgress":
            case "setThumbResource":
            case "setTrackResource":
            case "listSetData":
            case "listSetCustomViewData":
            case "listRefresh":
            case "listSetItemChecked":
            case "listGetCheckedPosition":
            case "listGetCheckedPositions":
            case "listGetCheckedCount":
            case "listSmoothScrollTo":
            case "spnSetData":
            case "spnRefresh":
            case "spnSetSelection":
            case "spnGetSelection":
            case "webViewLoadUrl":
            case "webViewGetUrl":
            case "webViewSetCacheMode":
            case "webViewCanGoBack":
            case "webViewCanGoForward":
            case "webViewGoBack":
            case "webViewGoForward":
            case "webViewClearCache":
            case "webViewClearHistory":
            case "webViewStopLoading":
            case "webViewZoomIn":
            case "webViewZoomOut":
            case "calendarViewGetDate":
            case "calendarViewSetDate":
            case "calendarViewSetMinDate":
            case "calnedarViewSetMaxDate":
            case "adViewLoadAd":
            case "setImageFilePath":
            case "setImageUrl":
            case "setHint":
            case "setHintTextColor":
            case "progressBarSetIndeterminate":
            case "mapViewSetMapType":
            case "mapViewMoveCamera":
            case "mapViewZoomTo":
            case "mapViewZoomIn":
            case "mapViewZoomOut":
            case "mapViewAddMarker":
            case "mapViewSetMarkerInfo":
            case "mapViewSetMarkerPosition":
            case "mapViewSetMarkerColor":
            case "mapViewSetMarkerIcon":
            case "mapViewSetMarkerVisible":
                return viewType;
            case "doToast":
            case "copyToClipboard":
            case "setTitle":
            case "intentSetAction":
            case "intentSetData":
            case "intentSetScreen":
            case "intentPutExtra":
            case "intentSetFlags":
            case "startActivity":
            case "intentGetString":
            case "finishActivity":
            case "fileGetData":
            case "fileSetData":
            case "fileRemoveData":
            case "calendarGetNow":
            case "calendarAdd":
            case "calendarSet":
            case "calendarFormat":
            case "calendarDiff":
            case "calendarGetTime":
            case "calendarSetTime":
            case "vibratorAction":
            case "timerAfter":
            case "timerEvery":
            case "timerCancel":
            case "dialogSetTitle":
            case "dialogSetMessage":
            case "dialogOkButton":
            case "dialogCancelButton":
            case "dialogNeutralButton":
            case "dialogShow":
            case "dialogDismiss":
            case "mediaplayerCreate":
            case "mediaplayerStart":
            case "mediaplayerPause":
            case "mediaplayerSeek":
            case "mediaplayerGetCurrent":
            case "mediaplayerGetDuration":
            case "mediaplayerIsPlaying":
            case "mediaplayerSetLooping":
            case "mediaplayerIsLooping":
            case "mediaplayerReset":
            case "mediaplayerRelease":
            case "soundpoolCreate":
            case "soundpoolLoad":
            case "soundpoolStreamPlay":
            case "soundpoolStreamStop":
            case "objectanimatorSetTarget":
            case "objectanimatorSetProperty":
            case "objectanimatorSetValue":
            case "objectanimatorSetFromTo":
            case "objectanimatorSetDuration":
            case "objectanimatorSetRepeatMode":
            case "objectanimatorSetRepeatCount":
            case "objectanimatorSetInterpolator":
            case "objectanimatorStart":
            case "objectanimatorCancel":
            case "objectanimatorIsRunning":
            case "firebaseAdd":
            case "firebaseDelete":
            case "firebasePush":
            case "firebaseGetPushKey":
            case "firebaseGetChildren":
            case "firebaseauthCreateUser":
            case "firebaseauthSignInUser":
            case "firebaseauthSignInAnonymously":
            case "firebaseauthIsLoggedIn":
            case "firebaseauthGetCurrentUser":
            case "firebaseauthGetUid":
            case "firebaseauthResetPassword":
            case "firebaseauthSignOutUser":
            case "firebaseStartListen":
            case "firebaseStopListen":
            case "gyroscopeStartListen":
            case "gyroscopeStopListen":
            case "interstitialadCreate":
            case "interstitialadLoadAd":
            case "interstitialadShow":
            case "firebasestorageUploadFile":
            case "firebasestorageDownloadFile":
            case "firebasestorageDelete":
            case "camerastarttakepicture":
            case "filepickerstartpickfiles":
            case "requestnetworkSetParams":
            case "requestnetworkSetHeaders":
            case "requestnetworkStartRequestNetwork":
            case "textToSpeechSetPitch":
            case "textToSpeechSetSpeechRate":
            case "textToSpeechSpeak":
            case "textToSpeechIsSpeaking":
            case "textToSpeechStop":
            case "textToSpeechShutdown":
            case "speechToTextStartListening":
            case "speechToTextStopListening":
            case "speechToTextShutdown":
            case "bluetoothConnectReadyConnection":
            case "bluetoothConnectReadyConnectionToUuid":
            case "bluetoothConnectStartConnection":
            case "bluetoothConnectStartConnectionToUuid":
            case "bluetoothConnectStopConnection":
            case "bluetoothConnectSendData":
            case "bluetoothConnectIsBluetoothEnabled":
            case "bluetoothConnectIsBluetoothActivated":
            case "bluetoothConnectActivateBluetooth":
            case "bluetoothConnectGetPairedDevices":
            case "bluetoothConnectGetRandomUuid":
            case "locationManagerRequestLocationUpdates":
            case "locationManagerRemoveUpdates":
                return 0xff2ca5e2;
            case "fileutildelete":
            case "fileutilcopy":
            case "fileutilwrite":
            case "fileutilread":
            case "fileutilmove":
            case "fileutilisexist":
            case "fileutilmakedir":
            case "fileutillistdir":
            case "fileutilisdir":
            case "fileutilisfile":
            case "fileutillength":
            case "fileutilStartsWith":
            case "fileutilEndsWith":
            case "fileutilGetLastSegmentPath":
            case "getExternalStorageDir":
            case "getPackageDataDir":
            case "getPublicDir":
            case "resizeBitmapFileRetainRatio":
            case "resizeBitmapFileToSquare":
            case "resizeBitmapFileToCircle":
            case "resizeBitmapFileWithRoundedBorder":
            case "cropBitmapFileFromCenter":
            case "rotateBitmapFile":
            case "scaleBitmapFile":
            case "skewBitmapFile":
            case "setBitmapFileColorFilter":
            case "setBitmapFileBrightness":
            case "setBitmapFileContrast":
            case "getJpegRotate":
                return 0xffa1887f;
            default:
                return 0xff8a55d7;
        }

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
                 "vibrator", "firebasestorage", "onesignal", "phoneauth", "fbadbanner",
                 "googlelogin", "dynamiclink", "fbadinterstitial", "cloudmessage" -> "p";
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
            case "onesignal" -> "OneSignal";
            case "customViews" -> "CustomView";
            case "asynctask" -> "AsyncTask";
            case "activity" -> "Context";
            case "otpview" -> "OTPView";
            case "lottie" -> "LottieAnimation";
            case "phoneauth" -> "FirebasePhoneAuth";
            case "fbadbanner" -> "FBAdsBanner";
            case "codeview" -> "CodeView";
            case "recyclerview" -> "RecyclerView";
            case "resource" -> "Image";
            case "googlelogin" -> "FirebaseGoogleSignIn";
            case "dynamiclink" -> "FirebaseDynamicLink";
            case "youtubeview" -> "YoutubePlayer";
            case "cardview" -> "CardView";
            case "radiogroup" -> "RadioGroup";
            case "color" -> "Color";
            case "fbadinterstitial" -> "FBAdsInterstitial";
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