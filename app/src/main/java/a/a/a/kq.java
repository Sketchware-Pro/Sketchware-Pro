//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package a.a.a;

import dev.aldi.sayuti.block.ExtraBlockClassInfo;

public class kq {
    public kq() {
    }

    public static int a(String var0, String var1) {
        int var2 = -11899692;
        int var3;
        if (var1.equals("h")) {
            var3 = -3636432;
        } else {
            var3 = var2;
            switch (var0) {
                case "getResStr":
                    var3 = 0xff7c83db;
                    break;
                case "getVar":
                    if (var1.equals("v")) {
                        break;
                    }
                    if (var1.equals("p")) {
                        var3 = -13851166;
                        break;
                    } else if (!var1.equals("l")) {
                        var3 = -1147626;
                        break;
                    }
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
                    var3 = -3384542;
                    break;
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
                    var3 = -1147626;
                    break;
                case "repeat":
                case "forever":
                case "break":
                case "if":
                case "ifElse":
                    var3 = -1988310;
                    break;
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
                    var3 = -10701022;
                    break;
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
                    var3 = -14435927;
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
                    break;
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
                    var3 = -13851166;
                    break;
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
                    var3 = -6190977;
                    break;
                default:
                    var3 = -7711273;
            }
        }

        return var3;
    }

    public static String a(int var0) {
        String var1;
        if (var0 != 1) {
            if (var0 != 2) {
                if (var0 != 3) {
                    var1 = "";
                } else {
                    var1 = "List Map";
                }
            } else {
                var1 = "List String";
            }
        } else {
            var1 = "List Number";
        }

        return var1;
    }

    public static String a(String var0) {
        byte var1;
        label127: {
            switch (var0) {
                case "spinner":
                    var1 = 11;
                    break label127;
                case "soundpool":
                    var1 = 25;
                    break label127;
                case "requestnetwork":
                    var1 = 31;
                    break label127;
                case "objectanimator":
                    var1 = 26;
                    break label127;
                case "adview":
                    var1 = 16;
                    break label127;
                case "dialog":
                    var1 = 23;
                    break label127;
                case "texttospeech":
                    var1 = 32;
                    break label127;
                case "intent":
                    var1 = 18;
                    break label127;
                case "textview":
                    var1 = 5;
                    break label127;
                case "locationmanager":
                    var1 = 35;
                    break label127;
                case "switch":
                    var1 = 9;
                    break label127;
                case "imageview":
                    var1 = 7;
                    break label127;
                case "varMap":
                    var1 = 0;
                    break label127;
                case "firebase":
                    var1 = 27;
                    break label127;
                case "calendarview":
                    var1 = 14;
                    break label127;
                case "speechtotext":
                    var1 = 33;
                    break label127;
                case "calendar":
                    var1 = 20;
                    break label127;
                case "file":
                    var1 = 19;
                    break label127;
                case "view":
                    var1 = 4;
                    break label127;
                case "firebaseauth":
                    var1 = 28;
                    break label127;
                case "timer":
                    var1 = 22;
                    break label127;
                case "listInt":
                    var1 = 1;
                    break label127;
                case "listMap":
                    var1 = 3;
                    break label127;
                case "listStr":
                    var1 = 2;
                    break label127;
                case "gyroscope":
                    var1 = 30;
                    break label127;
                case "mediaplayer":
                    var1 = 24;
                    break label127;
                case "bluetoothconnect":
                    var1 = 34;
                    break label127;
                case "mapview":
                    var1 = 17;
                    break label127;
                case "vibrator":
                    var1 = 21;
                    break label127;
                case "progressbar":
                    var1 = 15;
                    break label127;
                case "webview":
                    var1 = 12;
                    break label127;
                case "listview":
                    var1 = 10;
                    break label127;
                case "checkbox":
                    var1 = 8;
                    break label127;
                case "edittext":
                    var1 = 6;
                    break label127;
                case "firebasestorage":
                    var1 = 29;
                    break label127;
                case "seekbar":
                    var1 = 13;
                    break label127;
            }

            var1 = -1;
        }

        switch (var1) {
            case 0:
                var0 = "a";
                break;
            case 1:
            case 2:
            case 3:
                var0 = "l";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                var0 = "v";
                break;
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
                var0 = "p";
                break;
            default:
                var0 = ExtraBlockClassInfo.getType(var0);
        }

        return var0;
    }

    public static String b(int var0) {
        String var1;
        if (var0 != 3) {
            var1 = "";
        } else {
            var1 = "Map";
        }

        return var1;
    }

    public static String b(String var0) {
        byte var1;
        label263: {
            switch (var0) {
                case "spinner":
                    var1 = 17;
                    break label263;
                case "tablayout":
                    var1 = 51;
                    break label263;
                case "soundpool":
                    var1 = 32;
                    break label263;
                case "requestnetwork":
                    var1 = 39;
                    break label263;
                case "filepicker":
                    var1 = 38;
                    break label263;
                case "patternview":
                    var1 = 60;
                    break label263;
                case "objectanimator":
                    var1 = 33;
                    break label263;
                case "viewpager":
                    var1 = 52;
                    break label263;
                case "adview":
                    var1 = 20;
                    break label263;
                case "progressdialog":
                    var1 = 54;
                    break label263;
                case "camera":
                    var1 = 37;
                    break label263;
                case "dialog":
                    var1 = 30;
                    break label263;
                case "datepickerdialog":
                    var1 = 55;
                    break label263;
                case "texttospeech":
                    var1 = 40;
                    break label263;
                case "intent":
                    var1 = 23;
                    break label263;
                case "textview":
                    var1 = 10;
                    break label263;
                case "locationmanager":
                    var1 = 43;
                    break label263;
                case "switch":
                    var1 = 19;
                    break label263;
                case "imageview":
                    var1 = 13;
                    break label263;
                case "varInt":
                    var1 = 0;
                    break label263;
                case "varMap":
                    var1 = 3;
                    break label263;
                case "varStr":
                    var1 = 2;
                    break label263;
                case "searchview":
                    var1 = 47;
                    break label263;
                case "firebase":
                    var1 = 27;
                    break label263;
                case "bottomnavigation":
                    var1 = 59;
                    break label263;
                case "calendarview":
                    var1 = 15;
                    break label263;
                case "speechtotext":
                    var1 = 41;
                    break label263;
                case "calendar":
                    var1 = 25;
                    break label263;
                case "actv":
                    var1 = 49;
                    break label263;
                case "file":
                    var1 = 24;
                    break label263;
                case "list":
                    var1 = 8;
                    break label263;
                case "view":
                    var1 = 9;
                    break label263;
                case "radiobutton":
                    var1 = 44;
                    break label263;
                case "firebaseauth":
                    var1 = 28;
                    break label263;
                case "mactv":
                    var1 = 50;
                    break label263;
                case "timer":
                    var1 = 26;
                    break label263;
                case "listInt":
                    var1 = 4;
                    break label263;
                case "listMap":
                    var1 = 7;
                    break label263;
                case "listStr":
                    var1 = 6;
                    break label263;
                case "varBool":
                    var1 = 1;
                    break label263;
                case "gridview":
                    var1 = 48;
                    break label263;
                case "gyroscope":
                    var1 = 34;
                    break label263;
                case "ratingbar":
                    var1 = 45;
                    break label263;
                case "videoad":
                    var1 = 53;
                    break label263;
                case "mediaplayer":
                    var1 = 31;
                    break label263;
                case "notification":
                    var1 = 57;
                    break label263;
                case "bluetoothconnect":
                    var1 = 42;
                    break label263;
                case "mapview":
                    var1 = 22;
                    break label263;
                case "vibrator":
                    var1 = 29;
                    break label263;
                case "progressbar":
                    var1 = 21;
                    break label263;
                case "webview":
                    var1 = 18;
                    break label263;
                case "interstitialad":
                    var1 = 35;
                    break label263;
                case "videoview":
                    var1 = 46;
                    break label263;
                case "listBool":
                    var1 = 5;
                    break label263;
                case "listview":
                    var1 = 16;
                    break label263;
                case "checkbox":
                    var1 = 12;
                    break label263;
                case "edittext":
                    var1 = 11;
                    break label263;
                case "firebasestorage":
                    var1 = 36;
                    break label263;
                case "timepickerdialog":
                    var1 = 56;
                    break label263;
                case "seekbar":
                    var1 = 14;
                    break label263;
                case "sidebar":
                    var1 = 61;
                    break label263;
                case "badgeview":
                    var1 = 58;
                    break label263;
            }

            var1 = -1;
        }

        switch (var1) {
            case 0:
                var0 = "Number";
                break;
            case 1:
                var0 = "Boolean";
                break;
            case 2:
                var0 = "String";
                break;
            case 3:
                var0 = "Map";
                break;
            case 4:
                var0 = "List Number";
                break;
            case 5:
                var0 = "List Boolean";
                break;
            case 6:
                var0 = "List String";
                break;
            case 7:
                var0 = "List Map";
                break;
            case 8:
                var0 = "List";
                break;
            case 9:
                var0 = "View";
                break;
            case 10:
                var0 = "TextView";
                break;
            case 11:
                var0 = "EditText";
                break;
            case 12:
                var0 = "CheckBox";
                break;
            case 13:
                var0 = "ImageView";
                break;
            case 14:
                var0 = "SeekBar";
                break;
            case 15:
                var0 = "CalendarView";
                break;
            case 16:
                var0 = "ListView";
                break;
            case 17:
                var0 = "Spinner";
                break;
            case 18:
                var0 = "WebView";
                break;
            case 19:
                var0 = "Switch";
                break;
            case 20:
                var0 = "AdView";
                break;
            case 21:
                var0 = "ProgressBar";
                break;
            case 22:
                var0 = "MapView";
                break;
            case 23:
                var0 = "Intent";
                break;
            case 24:
                var0 = "SharedPreferences";
                break;
            case 25:
                var0 = "Calendar";
                break;
            case 26:
                var0 = "Timer";
                break;
            case 27:
                var0 = "Firebase DB";
                break;
            case 28:
                var0 = "Firebase Auth";
                break;
            case 29:
                var0 = "Vibrator";
                break;
            case 30:
                var0 = "Dialog";
                break;
            case 31:
                var0 = "MediaPlayer";
                break;
            case 32:
                var0 = "SoundPool";
                break;
            case 33:
                var0 = "ObjectAnimator";
                break;
            case 34:
                var0 = "Gyroscope";
                break;
            case 35:
                var0 = "InterstitialAd";
                break;
            case 36:
                var0 = "FirebaseStorage";
                break;
            case 37:
                var0 = "Camera";
                break;
            case 38:
                var0 = "FilePicker";
                break;
            case 39:
                var0 = "RequestNetwork";
                break;
            case 40:
                var0 = "TextToSpeech";
                break;
            case 41:
                var0 = "SpeechToText";
                break;
            case 42:
                var0 = "BluetoothConnect";
                break;
            case 43:
                var0 = "LocationManager";
                break;
            case 44:
                var0 = "RadioButton";
                break;
            case 45:
                var0 = "RatingBar";
                break;
            case 46:
                var0 = "VideoView";
                break;
            case 47:
                var0 = "SearchView";
                break;
            case 48:
                var0 = "GridView";
                break;
            case 49:
                var0 = "AutoComplete";
                break;
            case 50:
                var0 = "MultiAutoComplete";
                break;
            case 51:
                var0 = "TabLayout";
                break;
            case 52:
                var0 = "ViewPager";
                break;
            case 53:
                var0 = "VideoAd";
                break;
            case 54:
                var0 = "ProgressDialog";
                break;
            case 55:
                var0 = "DatePickerDialog";
                break;
            case 56:
                var0 = "TimePickerDialog";
                break;
            case 57:
                var0 = "Notification";
                break;
            case 58:
                var0 = "BadgeView";
                break;
            case 59:
                var0 = "BottomNavigation";
                break;
            case 60:
                var0 = "PatternLockView";
                break;
            case 61:
                var0 = "WaveSideBar";
                break;
            default:
                var0 = ExtraBlockClassInfo.getName(var0);
        }

        return var0;
    }
}
