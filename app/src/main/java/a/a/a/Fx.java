package a.a.a;

import android.util.Pair;

import com.besome.sketch.beans.BlockBean;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import mod.hey.studios.editor.manage.block.code.ExtraBlockCode;
import mod.hey.studios.moreblock.ReturnMoreblockManager;

public class Fx {
    public String[] a = new String[]{"repeat", "+", "-", "*", "/", "%", ">", "=", "<", "&&", "||", "not"};
    public String[] b = new String[]{"+", "-", "*", "/", "%", ">", "=", "<", "&&", "||"};
    public String c;
    public String d;
    public jq e;
    public ArrayList<BlockBean> f;
    public Map<String, BlockBean> g = null;
    public ExtraBlockCode mceb;

    public Fx(String var1, jq var2, String var3, ArrayList<BlockBean> var4) {
        c = var1;
        e = var2;
        d = var3;
        f = var4;
        mceb = new ExtraBlockCode(this);
    }

    public String a() {
        g = new HashMap<>();
        ArrayList<BlockBean> beans = f;
        if (beans != null && !beans.isEmpty()) {

            for (BlockBean bean : f) {
                g.put(bean.id, bean);
            }

            return a(f.get(0), "");
        } else {
            return "";
        }
    }

    public final String a(BlockBean bean, String var2) {
        ArrayList<String> params = new ArrayList<>();

        for (int i = 0; i < bean.parameters.size(); i++) {
            String param = bean.parameters.get(i);
            Gx paramInfo = bean.getParamClassInfo().get(i);
            int type;
            if (paramInfo.b("boolean")) {
                type = 0;
            } else if (paramInfo.b("double")) {
                type = 1;
            } else if (paramInfo.b("String")) {
                type = 2;
            } else {
                type = 3;
            }

            params.add(a(param, type, bean.opCode));
        }

        int blockId = -1;
        String opcode = bean.opCode;
        int hash = opcode.hashCode();
        String var8 = "false";
        switch (hash) {
            case -2135695280:
                if (opcode.equals("webViewLoadUrl")) {
                    blockId = 171;
                }
                break;
            case -2120571577:
                if (opcode.equals("mapIsEmpty")) {
                    blockId = 15;
                }
                break;
            case -2114384168:
                if (opcode.equals("firebasestorageDownloadFile")) {
                    blockId = 262;
                }
                break;
            case -2055793167:
                if (opcode.equals("fileutillistdir")) {
                    blockId = 271;
                }
                break;
            case -2037144358:
                if (opcode.equals("bluetoothConnectStartConnectionToUuid")) {
                    blockId = 315;
                }
                break;
            case -2027093331:
                if (opcode.equals("calendarViewSetDate")) {
                    blockId = 184;
                }
                break;
            case -2020761366:
                if (opcode.equals("fileRemoveData")) {
                    blockId = 133;
                }
                break;
            case -1998407506:
                if (opcode.equals("listSetData")) {
                    blockId = 159;
                }
                break;
            case -1989678633:
                if (opcode.equals("mapViewSetMarkerVisible")) {
                    blockId = 198;
                }
                break;
            case -1979147952:
                if (opcode.equals("stringContains")) {
                    blockId = 63;
                }
                break;
            case -1975568730:
                if (opcode.equals("copyToClipboard")) {
                    blockId = 120;
                }
                break;
            case -1966668787:
                if (opcode.equals("firebaseauthSignOutUser")) {
                    blockId = 215;
                }
                break;
            case -1937348542:
                if (opcode.equals("firebaseStartListen")) {
                    blockId = 216;
                }
                break;
            case -1922362317:
                if (opcode.equals("getExternalStorageDir")) {
                    blockId = 278;
                }
                break;
            case -1920517885:
                if (opcode.equals("setVarBoolean")) {
                    blockId = 3;
                }
                break;
            case -1919300188:
                if (opcode.equals("toNumber")) {
                    blockId = 67;
                }
                break;
            case -1910071024:
                if (opcode.equals("objectanimatorSetDuration")) {
                    blockId = 251;
                }
                break;
            case -1886802639:
                if (opcode.equals("soundpoolLoad")) {
                    blockId = 238;
                }
                break;
            case -1834369666:
                if (opcode.equals("setBitmapFileBrightness")) {
                    blockId = 290;
                }
                break;
            case -1812313351:
                if (opcode.equals("setColorFilter")) {
                    blockId = 117;
                }
                break;
            case -1778201036:
                if (opcode.equals("listSmoothScrollTo")) {
                    blockId = 166;
                }
                break;
            case -1776922004:
                if (opcode.equals("toString")) {
                    blockId = 72;
                }
                break;
            case -1749698255:
                if (opcode.equals("mediaplayerPause")) {
                    blockId = 228;
                }
                break;
            case -1747734390:
                if (opcode.equals("mediaplayerReset")) {
                    blockId = 232;
                }
                break;
            case -1746380899:
                if (opcode.equals("mediaplayerStart")) {
                    blockId = 227;
                }
                break;
            case -1718917155:
                if (opcode.equals("mediaplayerSeek")) {
                    blockId = 229;
                }
                break;
            case -1699631195:
                if (opcode.equals("isDrawerOpen")) {
                    blockId = 105;
                }
                break;
            case -1699349926:
                if (opcode.equals("objectanimatorSetRepeatMode")) {
                    blockId = 252;
                }
                break;
            case -1684072208:
                if (opcode.equals("intentSetData")) {
                    blockId = 123;
                }
                break;
            case -1679834825:
                if (opcode.equals("setTrackResource")) {
                    blockId = 242;
                }
                break;
            case -1678257956:
                if (opcode.equals("gridSetCustomViewData")) {
                    blockId = 160;
                }
                break;
            case -1666623936:
                if (opcode.equals("speechToTextShutdown")) {
                    blockId = 311;
                }
                break;
            case -1573371685:
                if (opcode.equals("stringJoin")) {
                    blockId = 58;
                }
                break;
            case -1541653284:
                if (opcode.equals("objectanimatorStart")) {
                    blockId = 255;
                }
                break;
            case -1530840255:
                if (opcode.equals("stringIndex")) {
                    blockId = 59;
                }
                break;
            case -1528850031:
                if (opcode.equals("startActivity")) {
                    blockId = 128;
                }
                break;
            case -1526161572:
                if (opcode.equals("setBgColor")) {
                    blockId = 113;
                }
                break;
            case -1513446476:
                if (opcode.equals("dialogCancelButton")) {
                    blockId = 224;
                }
                break;
            case -1512519571:
                if (opcode.equals("definedFunc")) {
                    blockId = 0;
                }
                break;
            case -1483954587:
                if (opcode.equals("fileutilisdir")) {
                    blockId = 272;
                }
                break;
            case -1477942289:
                if (opcode.equals("mediaplayerIsLooping")) {
                    blockId = 236;
                }
                break;
            case -1471049951:
                if (opcode.equals("fileutilwrite")) {
                    blockId = 265;
                }
                break;
            case -1440042085:
                if (opcode.equals("spnSetSelection")) {
                    blockId = 169;
                }
                break;
            case -1438040951:
                if (opcode.equals("seekBarGetMax")) {
                    blockId = 246;
                }
                break;
            case -1422112391:
                if (opcode.equals("bluetoothConnectIsBluetoothEnabled")) {
                    blockId = 318;
                }
                break;
            case -1405157727:
                if (opcode.equals("fileutilmakedir")) {
                    blockId = 270;
                }
                break;
            case -1385076635:
                if (opcode.equals("dialogShow")) {
                    blockId = 222;
                }
                break;
            case -1384861688:
                if (opcode.equals("getAtListInt")) {
                    blockId = 19;
                }
                break;
            case -1384858251:
                if (opcode.equals("getAtListMap")) {
                    blockId = 29;
                }
                break;
            case -1384851894:
                if (opcode.equals("getAtListStr")) {
                    blockId = 24;
                }
                break;
            case -1377080719:
                if (opcode.equals("decreaseInt")) {
                    blockId = 6;
                }
                break;
            case -1376608975:
                if (opcode.equals("calendarSetTime")) {
                    blockId = 140;
                }
                break;
            case -1361468284:
                if (opcode.equals("viewOnClick")) {
                    blockId = 104;
                }
                break;
            case -1348085287:
                if (opcode.equals("mapViewZoomIn")) {
                    blockId = 191;
                }
                break;
            case -1348084945:
                if (opcode.equals("mapViewZoomTo")) {
                    blockId = 190;
                }
                break;
            case -1304067438:
                if (opcode.equals("firebaseDelete")) {
                    blockId = 206;
                }
                break;
            case -1272546178:
                if (opcode.equals("dialogSetTitle")) {
                    blockId = 220;
                }
                break;
            case -1271141237:
                if (opcode.equals("clearList")) {
                    blockId = 37;
                }
                break;
            case -1249367264:
                if (opcode.equals("getArg")) {
                    blockId = 1;
                }
                break;
            case -1249347599:
                if (opcode.equals("getVar")) {
                    blockId = 2;
                }
                break;
            case -1217704075:
                if (opcode.equals("objectanimatorSetValue")) {
                    blockId = 249;
                }
                break;
            case -1206794099:
                if (opcode.equals("getLocationX")) {
                    blockId = 155;
                }
                break;
            case -1206794098:
                if (opcode.equals("getLocationY")) {
                    blockId = 156;
                }
                break;
            case -1195899442:
                if (opcode.equals("bluetoothConnectSendData")) {
                    blockId = 317;
                }
                break;
            case -1192544266:
                if (opcode.equals("ifElse")) {
                    blockId = 41;
                }
                break;
            case -1185284274:
                if (opcode.equals("gyroscopeStopListen")) {
                    blockId = 219;
                }
                break;
            case -1182878167:
                if (opcode.equals("firebaseauthGetUid")) {
                    blockId = 213;
                }
                break;
            case -1160374245:
                if (opcode.equals("bluetoothConnectReadyConnectionToUuid")) {
                    blockId = 313;
                }
                break;
            case -1149848189:
                if (opcode.equals("toStringFormat")) {
                    blockId = 74;
                }
                break;
            case -1149458632:
                if (opcode.equals("objectanimatorSetRepeatCount")) {
                    blockId = 253;
                }
                break;
            case -1143684675:
                if (opcode.equals("firebaseauthGetCurrentUser")) {
                    blockId = 212;
                }
                break;
            case -1139353316:
                if (opcode.equals("setListMap")) {
                    blockId = 30;
                }
                break;
            case -1137582698:
                if (opcode.equals("toLowerCase")) {
                    blockId = 71;
                }
                break;
            case -1123431291:
                if (opcode.equals("calnedarViewSetMaxDate")) {
                    blockId = 186;
                }
                break;
            case -1107376988:
                if (opcode.equals("webViewGoForward")) {
                    blockId = 177;
                }
                break;
            case -1106141754:
                if (opcode.equals("webViewCanGoBack")) {
                    blockId = 174;
                }
                break;
            case -1094491139:
                if (opcode.equals("seekBarSetMax")) {
                    blockId = 245;
                }
                break;
            case -1083547183:
                if (opcode.equals("spnSetCustomViewData")) {
                    blockId = 160;
                }
                break;
            case -1081400230:
                if (opcode.equals("mapGet")) {
                    blockId = 10;
                }
                break;
            case -1081391085:
                if (opcode.equals("mapPut")) {
                    blockId = 9;
                }
                break;
            case -1081250015:
                if (opcode.equals("mathPi")) {
                    blockId = 83;
                }
                break;
            case -1069525505:
                if (opcode.equals("pagerSetCustomViewData")) {
                    blockId = 160;
                }
                break;
            case -1063598745:
                if (opcode.equals("resizeBitmapFileRetainRatio")) {
                    blockId = 281;
                }
                break;
            case -1043233275:
                if (opcode.equals("mediaplayerGetDuration")) {
                    blockId = 231;
                }
                break;
            case -1033658254:
                if (opcode.equals("mathGetDisplayWidth")) {
                    blockId = 81;
                }
                break;
            case -1021852352:
                if (opcode.equals("objectanimatorCancel")) {
                    blockId = 256;
                }
                break;
            case -1007787615:
                if (opcode.equals("mediaplayerSetLooping")) {
                    blockId = 235;
                }
                break;
            case -996870276:
                if (opcode.equals("insertMapToList")) {
                    blockId = 33;
                }
                break;
            case -995908985:
                if (opcode.equals("soundpoolCreate")) {
                    blockId = 237;
                }
                break;
            case -941420147:
                if (opcode.equals("fileSetFileName")) {
                    blockId = 130;
                }
                break;
            case -938285885:
                if (opcode.equals("random")) {
                    blockId = 56;
                }
                break;
            case -934531685:
                if (opcode.equals("repeat")) {
                    blockId = 39;
                }
                break;
            case -918173448:
                if (opcode.equals("listGetCheckedPosition")) {
                    blockId = 163;
                }
                break;
            case -917343271:
                if (opcode.equals("getJpegRotate")) {
                    blockId = 292;
                }
                break;
            case -911199919:
                if (opcode.equals("objectanimatorSetProperty")) {
                    blockId = 248;
                }
                break;
            case -903177036:
                if (opcode.equals("resizeBitmapFileWithRoundedBorder")) {
                    blockId = 284;
                }
                break;
            case -883988307:
                if (opcode.equals("dialogSetMessage")) {
                    blockId = 221;
                }
                break;
            case -869293886:
                if (opcode.equals("finishActivity")) {
                    blockId = 129;
                }
                break;
            case -854558288:
                if (opcode.equals("setVisible")) {
                    blockId = 141;
                }
                break;
            case -853550561:
                if (opcode.equals("timerCancel")) {
                    blockId = 202;
                }
                break;
            case -831887360:
                if (opcode.equals("textToSpeechShutdown")) {
                    blockId = 308;
                }
                break;
            case -733318734:
                if (opcode.equals("strToListMap")) {
                    blockId = 78;
                }
                break;
            case -697616870:
                if (opcode.equals("camerastarttakepicture")) {
                    blockId = 294;
                }
                break;
            case -677662361:
                if (opcode.equals("forever")) {
                    blockId = 38;
                }
                break;
            case -668992194:
                if (opcode.equals("stringReplaceAll")) {
                    blockId = 66;
                }
                break;
            case -664474111:
                if (opcode.equals("intentSetFlags")) {
                    blockId = 126;
                }
                break;
            case -649691581:
                if (opcode.equals("objectanimatorSetInterpolator")) {
                    blockId = 254;
                }
                break;
            case -636363854:
                if (opcode.equals("webViewGetUrl")) {
                    blockId = 172;
                }
                break;
            case -628607128:
                if (opcode.equals("webViewGoBack")) {
                    blockId = 176;
                }
                break;
            case -621198621:
                if (opcode.equals("speechToTextStartListening")) {
                    blockId = 309;
                }
                break;
            case -602241037:
                if (opcode.equals("fileutilcopy")) {
                    blockId = 266;
                }
                break;
            case -601942961:
                if (opcode.equals("fileutilmove")) {
                    blockId = 267;
                }
                break;
            case -601804268:
                if (opcode.equals("fileutilread")) {
                    blockId = 264;
                }
                break;
            case -578987803:
                if (opcode.equals("setChecked")) {
                    blockId = 157;
                }
                break;
            case -509946902:
                if (opcode.equals("spnRefresh")) {
                    blockId = 168;
                }
                break;
            case -439342016:
                if (opcode.equals("webViewClearHistory")) {
                    blockId = 179;
                }
                break;
            case -437272040:
                if (opcode.equals("bluetoothConnectGetRandomUuid")) {
                    blockId = 322;
                }
                break;
            case -425293664:
                if (opcode.equals("setClickable")) {
                    blockId = 142;
                }
                break;
            case -418212114:
                if (opcode.equals("firebaseGetChildren")) {
                    blockId = 207;
                }
                break;
            case -411705840:
                if (opcode.equals("fileSetData")) {
                    blockId = 132;
                }
                break;
            case -399551817:
                if (opcode.equals("toUpperCase")) {
                    blockId = 70;
                }
                break;
            case -390304998:
                if (opcode.equals("mapViewAddMarker")) {
                    blockId = 193;
                }
                break;
            case -356866884:
                if (opcode.equals("webViewSetCacheMode")) {
                    blockId = 173;
                }
                break;
            case -353129373:
                if (opcode.equals("calendarDiff")) {
                    blockId = 138;
                }
                break;
            case -329562760:
                if (opcode.equals("insertListInt")) {
                    blockId = 18;
                }
                break;
            case -329559323:
                if (opcode.equals("insertListMap")) {
                    blockId = 28;
                }
                break;
            case -329552966:
                if (opcode.equals("insertListStr")) {
                    blockId = 23;
                }
                break;
            case -322651344:
                if (opcode.equals("stringEquals")) {
                    blockId = 62;
                }
                break;
            case -283328259:
                if (opcode.equals("intentPutExtra")) {
                    blockId = 125;
                }
                break;
            case -258774775:
                if (opcode.equals("closeDrawer")) {
                    blockId = 107;
                }
                break;
            case -247015294:
                if (opcode.equals("mediaplayerRelease")) {
                    blockId = 233;
                }
                break;
            case -208762465:
                if (opcode.equals("toStringWithDecimal")) {
                    blockId = 73;
                }
                break;
            case -189292433:
                if (opcode.equals("stringSub")) {
                    blockId = 61;
                }
                break;
            case -152473824:
                if (opcode.equals("firebaseauthIsLoggedIn")) {
                    blockId = 211;
                }
                break;
            case -149850417:
                if (opcode.equals("fileutilisexist")) {
                    blockId = 269;
                }
                break;
            case -133532073:
                if (opcode.equals("stringLength")) {
                    blockId = 57;
                }
                break;
            case -96313603:
                if (opcode.equals("containListInt")) {
                    blockId = 21;
                }
                break;
            case -96310166:
                if (opcode.equals("containListMap")) {
                    blockId = 31;
                }
                break;
            case -96303809:
                if (opcode.equals("containListStr")) {
                    blockId = 26;
                }
                break;
            case -83301935:
                if (opcode.equals("webViewZoomIn")) {
                    blockId = 181;
                }
                break;
            case -83186725:
                if (opcode.equals("openDrawer")) {
                    blockId = 106;
                }
                break;
            case -75125341:
                if (opcode.equals("getText")) {
                    blockId = 112;
                }
                break;
            case -60494417:
                if (opcode.equals("vibratorAction")) {
                    blockId = 199;
                }
                break;
            case -60048101:
                if (opcode.equals("firebaseauthResetPassword")) {
                    blockId = 214;
                }
                break;
            case -24451690:
                if (opcode.equals("dialogOkButton")) {
                    blockId = 223;
                }
                break;
            case -14362103:
                if (opcode.equals("bluetoothConnectIsBluetoothActivated")) {
                    blockId = 319;
                }
                break;
            case -10599306:
                if (opcode.equals("firebaseauthCreateUser")) {
                    blockId = 208;
                }
                break;
            case -9742826:
                if (opcode.equals("firebaseGetPushKey")) {
                    blockId = 205;
                }
                break;
            case 37:
                if (opcode.equals("%")) {
                    blockId = 50;
                }
                break;
            case 42:
                if (opcode.equals("*")) {
                    blockId = 48;
                }
                break;
            case 43:
                if (opcode.equals("+")) {
                    blockId = 46;
                }
                break;
            case 45:
                if (opcode.equals("-")) {
                    blockId = 47;
                }
                break;
            case 47:
                if (opcode.equals("/")) {
                    blockId = 49;
                }
                break;
            case 60:
                if (opcode.equals("<")) {
                    blockId = 52;
                }
                break;
            case 61:
                if (opcode.equals("=")) {
                    blockId = 53;
                }
                break;
            case 62:
                if (opcode.equals(">")) {
                    blockId = 51;
                }
                break;
            case 1216:
                if (opcode.equals("&&")) {
                    blockId = 54;
                }
                break;
            case 3357:
                if (opcode.equals("if")) {
                    blockId = 40;
                }
                break;
            case 3968:
                if (opcode.equals("||")) {
                    blockId = 55;
                }
                break;
            case 109267:
                if (opcode.equals("not")) {
                    blockId = 45;
                }
                break;
            case 3568674:
                if (opcode.equals("trim")) {
                    blockId = 69;
                }
                break;
            case 3569038:
                if (opcode.equals("true")) {
                    blockId = 43;
                }
                break;
            case 8255701:
                if (opcode.equals("calendarFormat")) {
                    blockId = 137;
                }
                break;
            case 16308074:
                if (opcode.equals("resizeBitmapFileToCircle")) {
                    blockId = 283;
                }
                break;
            case 25469951:
                if (opcode.equals("bluetoothConnectActivateBluetooth")) {
                    blockId = 320;
                }
                break;
            case 27679870:
                if (opcode.equals("calendarGetNow")) {
                    blockId = 134;
                }
                break;
            case 56167279:
                if (opcode.equals("setBitmapFileContrast")) {
                    blockId = 291;
                }
                break;
            case 61585857:
                if (opcode.equals("firebasePush")) {
                    blockId = 204;
                }
                break;
            case 94001407:
                if (opcode.equals("break")) {
                    blockId = 42;
                }
                break;
            case 97196323:
                if (opcode.equals("false")) {
                    blockId = 44;
                }
                break;
            case 103668285:
                if (opcode.equals("mathE")) {
                    blockId = 84;
                }
                break;
            case 125431087:
                if (opcode.equals("speechToTextStopListening")) {
                    blockId = 310;
                }
                break;
            case 134874756:
                if (opcode.equals("listSetCustomViewData")) {
                    blockId = 160;
                }
                break;
            case 152967761:
                if (opcode.equals("mapClear")) {
                    blockId = 14;
                }
                break;
            case 163812602:
                if (opcode.equals("cropBitmapFileFromCenter")) {
                    blockId = 285;
                }
                break;
            case 168740282:
                if (opcode.equals("mapToStr")) {
                    blockId = 77;
                }
                break;
            case 182549637:
                if (opcode.equals("setEnable")) {
                    blockId = 108;
                }
                break;
            case 207764385:
                if (opcode.equals("calendarViewGetDate")) {
                    blockId = 183;
                }
                break;
            case 255417137:
                if (opcode.equals("adViewLoadAd")) {
                    blockId = 187;
                }
                break;
            case 262073061:
                if (opcode.equals("bluetoothConnectReadyConnection")) {
                    blockId = 312;
                }
                break;
            case 276674391:
                if (opcode.equals("mapViewMoveCamera")) {
                    blockId = 189;
                }
                break;
            case 297379706:
                if (opcode.equals("textToSpeechSetSpeechRate")) {
                    blockId = 304;
                }
                break;
            case 300372142:
                if (opcode.equals("mathAcos")) {
                    blockId = 97;
                }
                break;
            case 300387327:
                if (opcode.equals("mathAsin")) {
                    blockId = 96;
                }
                break;
            case 300388040:
                if (opcode.equals("mathAtan")) {
                    blockId = 98;
                }
                break;
            case 300433453:
                if (opcode.equals("mathCeil")) {
                    blockId = 91;
                }
                break;
            case 300921928:
                if (opcode.equals("mathSqrt")) {
                    blockId = 88;
                }
                break;
            case 317453636:
                if (opcode.equals("textToSpeechIsSpeaking")) {
                    blockId = 306;
                }
                break;
            case 342026220:
                if (opcode.equals("interstitialadShow")) {
                    blockId = 260;
                }
                break;
            case 348377823:
                if (opcode.equals("soundpoolStreamPlay")) {
                    blockId = 239;
                }
                break;
            case 348475309:
                if (opcode.equals("soundpoolStreamStop")) {
                    blockId = 240;
                }
                break;
            case 362605827:
                if (opcode.equals("recyclerSetCustomViewData")) {
                    blockId = 160;
                }
                break;
            case 389111867:
                if (opcode.equals("spnSetData")) {
                    blockId = 167;
                }
                break;
            case 397166713:
                if (opcode.equals("getEnable")) {
                    blockId = 109;
                }
                break;
            case 401012285:
                if (opcode.equals("getTranslationX")) {
                    blockId = 148;
                }
                break;
            case 401012286:
                if (opcode.equals("getTranslationY")) {
                    blockId = 150;
                }
                break;
            case 404247683:
                if (opcode.equals("calendarAdd")) {
                    blockId = 135;
                }
                break;
            case 404265028:
                if (opcode.equals("calendarSet")) {
                    blockId = 136;
                }
                break;
            case 442768763:
                if (opcode.equals("mapGetAllKeys")) {
                    blockId = 16;
                }
                break;
            case 463560551:
                if (opcode.equals("mapContainKey")) {
                    blockId = 11;
                }
                break;
            case 463594049:
                if (opcode.equals("objectanimatorSetFromTo")) {
                    blockId = 250;
                }
                break;
            case 470160234:
                if (opcode.equals("fileutilGetLastSegmentPath")) {
                    blockId = 277;
                }
                break;
            case 475815924:
                if (opcode.equals("setTextColor")) {
                    blockId = 115;
                }
                break;
            case 481850295:
                if (opcode.equals("resizeBitmapFileToSquare")) {
                    blockId = 282;
                }
                break;
            case 490702942:
                if (opcode.equals("filepickerstartpickfiles")) {
                    blockId = 293;
                }
                break;
            case 501171279:
                if (opcode.equals("mathToDegree")) {
                    blockId = 103;
                }
                break;
            case 530759231:
                if (opcode.equals("progressBarSetIndeterminate")) {
                    blockId = 302;
                }
                break;
            case 548860462:
                if (opcode.equals("webViewClearCache")) {
                    blockId = 178;
                }
                break;
            case 556217437:
                if (opcode.equals("setRotate")) {
                    blockId = 143;
                }
                break;
            case 571046965:
                if (opcode.equals("scaleBitmapFile")) {
                    blockId = 287;
                }
                break;
            case 573208400:
                if (opcode.equals("setScaleX")) {
                    blockId = 151;
                }
                break;
            case 573208401:
                if (opcode.equals("setScaleY")) {
                    blockId = 153;
                }
                break;
            case 573295520:
                if (opcode.equals("listGetCheckedCount")) {
                    blockId = 165;
                }
                break;
            case 601235430:
                if (opcode.equals("currentTime")) {
                    blockId = 68;
                }
                break;
            case 610313513:
                if (opcode.equals("getMapInList")) {
                    blockId = 34;
                }
                break;
            case 615286641:
                if (opcode.equals("dialogNeutralButton")) {
                    blockId = 225;
                }
                break;
            case 657721930:
                if (opcode.equals("setVarInt")) {
                    blockId = 4;
                }
                break;
            case 683193060:
                if (opcode.equals("bluetoothConnectStartConnection")) {
                    blockId = 314;
                }
                break;
            case 725249532:
                if (opcode.equals("intentSetAction")) {
                    blockId = 122;
                }
                break;
            case 726487524:
                if (opcode.equals("mathFloor")) {
                    blockId = 92;
                }
                break;
            case 726877492:
                if (opcode.equals("mapViewSetMarkerIcon")) {
                    blockId = 197;
                }
                break;
            case 726887785:
                if (opcode.equals("mapViewSetMarkerInfo")) {
                    blockId = 194;
                }
                break;
            case 732108347:
                if (opcode.equals("mathLog10")) {
                    blockId = 101;
                }
                break;
            case 737664870:
                if (opcode.equals("mathRound")) {
                    blockId = 90;
                }
                break;
            case 738846120:
                if (opcode.equals("textToSpeechSetPitch")) {
                    blockId = 303;
                }
                break;
            case 747168008:
                if (opcode.equals("mapCreateNew")) {
                    blockId = 8;
                }
                break;
            case 754442829:
                if (opcode.equals("increaseInt")) {
                    blockId = 5;
                }
                break;
            case 762282303:
                if (opcode.equals("indexListInt")) {
                    blockId = 20;
                }
                break;
            case 762292097:
                if (opcode.equals("indexListStr")) {
                    blockId = 25;
                }
                break;
            case 770834513:
                if (opcode.equals("getRotate")) {
                    blockId = 144;
                }
                break;
            case 787825476:
                if (opcode.equals("getScaleX")) {
                    blockId = 152;
                }
                break;
            case 787825477:
                if (opcode.equals("getScaleY")) {
                    blockId = 154;
                }
                break;
            case 797861524:
                if (opcode.equals("addMapToList")) {
                    blockId = 32;
                }
                break;
            case 836692861:
                if (opcode.equals("mapSize")) {
                    blockId = 13;
                }
                break;
            case 840973386:
                if (opcode.equals("mathAbs")) {
                    blockId = 89;
                }
                break;
            case 840975711:
                if (opcode.equals("mathCos")) {
                    blockId = 94;
                }
                break;
            case 840977909:
                if (opcode.equals("mathExp")) {
                    blockId = 99;
                }
                break;
            case 840984348:
                if (opcode.equals("mathLog")) {
                    blockId = 100;
                }
                break;
            case 840984892:
                if (opcode.equals("mathMax")) {
                    blockId = 87;
                }
                break;
            case 840985130:
                if (opcode.equals("mathMin")) {
                    blockId = 86;
                }
                break;
            case 840988208:
                if (opcode.equals("mathPow")) {
                    blockId = 85;
                }
                break;
            case 840990896:
                if (opcode.equals("mathSin")) {
                    blockId = 93;
                }
                break;
            case 840991609:
                if (opcode.equals("mathTan")) {
                    blockId = 95;
                }
                break;
            case 845089750:
                if (opcode.equals("setVarString")) {
                    blockId = 7;
                }
                break;
            case 848786445:
                if (opcode.equals("objectanimatorSetTarget")) {
                    blockId = 247;
                }
                break;
            case 858248741:
                if (opcode.equals("calendarGetTime")) {
                    blockId = 139;
                }
                break;
            case 898187172:
                if (opcode.equals("mathToRadian")) {
                    blockId = 102;
                }
                break;
            case 932259189:
                if (opcode.equals("setBgResource")) {
                    blockId = 114;
                }
                break;
            case 937017988:
                if (opcode.equals("gyroscopeStartListen")) {
                    blockId = 218;
                }
                break;
            case 948234497:
                if (opcode.equals("webViewStopLoading")) {
                    blockId = 180;
                }
                break;
            case 950609198:
                if (opcode.equals("setBitmapFileColorFilter")) {
                    blockId = 289;
                }
                break;
            case 1053179400:
                if (opcode.equals("mapViewSetMarkerColor")) {
                    blockId = 196;
                }
                break;
            case 1068548733:
                if (opcode.equals("mathGetDip")) {
                    blockId = 80;
                }
                break;
            case 1086207657:
                if (opcode.equals("fileutildelete")) {
                    blockId = 268;
                }
                break;
            case 1088879149:
                if (opcode.equals("setHintTextColor")) {
                    blockId = 298;
                }
                break;
            case 1090517587:
                if (opcode.equals("getPackageDataDir")) {
                    blockId = 279;
                }
                break;
            case 1102670563:
                if (opcode.equals("requestnetworkSetHeaders")) {
                    blockId = 300;
                }
                break;
            case 1129709718:
                if (opcode.equals("setImageUrl")) {
                    blockId = 296;
                }
                break;
            case 1142897724:
                if (opcode.equals("firebaseauthSignInUser")) {
                    blockId = 209;
                }
                break;
            case 1156598140:
                if (opcode.equals("fileutilEndsWith")) {
                    blockId = 276;
                }
                break;
            case 1159035162:
                if (opcode.equals("mapViewZoomOut")) {
                    blockId = 192;
                }
                break;
            case 1160674468:
                if (opcode.equals("lengthList")) {
                    blockId = 36;
                }
                break;
            case 1162069698:
                if (opcode.equals("setThumbResource")) {
                    blockId = 241;
                }
                break;
            case 1179719371:
                if (opcode.equals("stringLastIndex")) {
                    blockId = 60;
                }
                break;
            case 1187505507:
                if (opcode.equals("stringReplace")) {
                    blockId = 64;
                }
                break;
            case 1216249183:
                if (opcode.equals("firebasestorageDelete")) {
                    blockId = 263;
                }
                break;
            case 1219071185:
                if (opcode.equals("firebasestorageUploadFile")) {
                    blockId = 261;
                }
                break;
            case 1219299503:
                if (opcode.equals("objectanimatorIsRunning")) {
                    blockId = 257;
                }
                break;
            case 1220078450:
                if (opcode.equals("addSourceDirectly")) {
                    blockId = 75;
                }
                break;
            case 1236956449:
                if (opcode.equals("mediaplayerCreate")) {
                    blockId = 226;
                }
                break;
            case 1240510514:
                if (opcode.equals("intentSetScreen")) {
                    blockId = 124;
                }
                break;
            case 1242107556:
                if (opcode.equals("fileutilisfile")) {
                    blockId = 273;
                }
                break;
            case 1252547704:
                if (opcode.equals("listMapToStr")) {
                    blockId = 79;
                }
                break;
            case 1280029577:
                if (opcode.equals("requestFocus")) {
                    blockId = 118;
                }
                break;
            case 1303367340:
                if (opcode.equals("textToSpeechStop")) {
                    blockId = 307;
                }
                break;
            case 1305932583:
                if (opcode.equals("spnGetSelection")) {
                    blockId = 170;
                }
                break;
            case 1311764809:
                if (opcode.equals("setTranslationX")) {
                    blockId = 147;
                }
                break;
            case 1311764810:
                if (opcode.equals("setTranslationY")) {
                    blockId = 149;
                }
                break;
            case 1313527577:
                if (opcode.equals("setTypeface")) {
                    blockId = 111;
                }
                break;
            case 1315302372:
                if (opcode.equals("fileutillength")) {
                    blockId = 274;
                }
                break;
            case 1330354473:
                if (opcode.equals("firebaseauthSignInAnonymously")) {
                    blockId = 210;
                }
                break;
            case 1343794064:
                if (opcode.equals("listSetItemChecked")) {
                    blockId = 162;
                }
                break;
            case 1348133645:
                if (opcode.equals("stringReplaceFirst")) {
                    blockId = 65;
                }
                break;
            case 1387622940:
                if (opcode.equals("setAlpha")) {
                    blockId = 145;
                }
                break;
            case 1395026457:
                if (opcode.equals("setImage")) {
                    blockId = 116;
                }
                break;
            case 1397501021:
                if (opcode.equals("listRefresh")) {
                    blockId = 161;
                }
                break;
            case 1405084438:
                if (opcode.equals("setTitle")) {
                    blockId = 121;
                }
                break;
            case 1410284340:
                if (opcode.equals("seekBarSetProgress")) {
                    blockId = 243;
                }
                break;
            case 1431171391:
                if (opcode.equals("mapRemoveKey")) {
                    blockId = 12;
                }
                break;
            case 1437288110:
                if (opcode.equals("getPublicDir")) {
                    blockId = 280;
                }
                break;
            case 1470831563:
                if (opcode.equals("intentGetString")) {
                    blockId = 127;
                }
                break;
            case 1498864168:
                if (opcode.equals("seekBarGetProgress")) {
                    blockId = 244;
                }
                break;
            case 1601394299:
                if (opcode.equals("listGetCheckedPositions")) {
                    blockId = 164;
                }
                break;
            case 1633341847:
                if (opcode.equals("timerAfter")) {
                    blockId = 200;
                }
                break;
            case 1635356258:
                if (opcode.equals("requestnetworkStartRequestNetwork")) {
                    blockId = 301;
                }
                break;
            case 1637498582:
                if (opcode.equals("timerEvery")) {
                    blockId = 201;
                }
                break;
            case 1695890133:
                if (opcode.equals("fileutilStartsWith")) {
                    blockId = 275;
                }
                break;
            case 1712613410:
                if (opcode.equals("webViewZoomOut")) {
                    blockId = 182;
                }
                break;
            case 1749552744:
                if (opcode.equals("textToSpeechSpeak")) {
                    blockId = 305;
                }
                break;
            case 1764351209:
                if (opcode.equals("deleteList")) {
                    blockId = 35;
                }
                break;
            case 1775620400:
                if (opcode.equals("strToMap")) {
                    blockId = 76;
                }
                break;
            case 1779174257:
                if (opcode.equals("getChecked")) {
                    blockId = 158;
                }
                break;
            case 1792552710:
                if (opcode.equals("rotateBitmapFile")) {
                    blockId = 286;
                }
                break;
            case 1814870108:
                if (opcode.equals("doToast")) {
                    blockId = 119;
                }
                break;
            case 1820536363:
                if (opcode.equals("interstitialadCreate")) {
                    blockId = 258;
                }
                break;
            case 1823151876:
                if (opcode.equals("fileGetData")) {
                    blockId = 131;
                }
                break;
            case 1848365301:
                if (opcode.equals("mapViewSetMapType")) {
                    blockId = 188;
                }
                break;
            case 1873103950:
                if (opcode.equals("locationManagerRemoveUpdates")) {
                    blockId = 324;
                }
                break;
            case 1883337723:
                if (opcode.equals("mathGetDisplayHeight")) {
                    blockId = 82;
                }
                break;
            case 1885231494:
                if (opcode.equals("webViewCanGoForward")) {
                    blockId = 175;
                }
                break;
            case 1908132964:
                if (opcode.equals("mapViewSetMarkerPosition")) {
                    blockId = 195;
                }
                break;
            case 1908582864:
                if (opcode.equals("firebaseStopListen")) {
                    blockId = 217;
                }
                break;
            case 1923980937:
                if (opcode.equals("requestnetworkSetParams")) {
                    blockId = 299;
                }
                break;
            case 1941634330:
                if (opcode.equals("firebaseAdd")) {
                    blockId = 203;
                }
                break;
            case 1948735400:
                if (opcode.equals("getAlpha")) {
                    blockId = 146;
                }
                break;
            case 1964823036:
                if (opcode.equals("bluetoothConnectStopConnection")) {
                    blockId = 316;
                }
                break;
            case 1973523807:
                if (opcode.equals("mediaplayerIsPlaying")) {
                    blockId = 234;
                }
                break;
            case 1974249461:
                if (opcode.equals("skewBitmapFile")) {
                    blockId = 288;
                }
                break;
            case 1976325370:
                if (opcode.equals("setImageFilePath")) {
                    blockId = 295;
                }
                break;
            case 1984630281:
                if (opcode.equals("setHint")) {
                    blockId = 297;
                }
                break;
            case 1984984239:
                if (opcode.equals("setText")) {
                    blockId = 110;
                }
                break;
            case 2017929665:
                if (opcode.equals("calendarViewSetMinDate")) {
                    blockId = 185;
                }
                break;
            case 2075310296:
                if (opcode.equals("interstitialadLoadAd")) {
                    blockId = 259;
                }
                break;
            case 2090179216:
                if (opcode.equals("addListInt")) {
                    blockId = 17;
                }
                break;
            case 2090182653:
                if (opcode.equals("addListMap")) {
                    blockId = 27;
                }
                break;
            case 2090189010:
                if (opcode.equals("addListStr")) {
                    blockId = 22;
                }
                break;
            case 2127377128:
                if (opcode.equals("mediaplayerGetCurrent")) {
                    blockId = 230;
                }
                break;
            case 2130649194:
                if (opcode.equals("bluetoothConnectGetPairedDevices")) {
                    blockId = 321;
                }
                break;
            case 2138225950:
                if (opcode.equals("locationManagerRequestLocationUpdates")) {
                    blockId = 323;
                }
        }

        String var9;
        opcode = "0";
        var9 = "";
        String var10;
        String var11;
        String var12;
        String var16;
        StringBuilder var24;
        StringBuilder var25;
        StringBuilder var26;
        StringBuilder var27;

        String var18 = mceb.getCodeExtraBlock(bean, "\"\"");
        opcode = var18;
        switch (blockId) {
            case 0:
                if (bean.parameters.size() <= 0) {
                    hash = bean.spec.indexOf(" ");
                    if (hash < 0) {
                        opcode = bean.type;
                        var18 = "_" + bean.spec + "()" + ReturnMoreblockManager.getMbEnd(bean.type);
                    } else {
                        opcode = bean.type;
                        var18 = "_" + bean.spec.substring(0, hash) + "()" + ReturnMoreblockManager.getMbEnd(bean.type);
                    }
                } else {
                    hash = bean.spec.indexOf(" ");
                    opcode = bean.spec.substring(0, hash);
                    opcode = "_" + opcode + "(";
                    hash = 0;
                    boolean var17 = false;

                    for (boolean var13 = true; hash < params.size(); var13 = false) {
                        var18 = opcode;
                        if (!var13) {
                            var26 = new StringBuilder();
                            var26.append(opcode);
                            var26.append(", ");
                            var18 = opcode + ", ";
                        }

                        var9 = params.get(hash);
                        if (var9.length() <= 0) {
                            Gx var20 = bean.getParamClassInfo().get(hash);
                            if (var20.b("boolean")) {
                                opcode = var18 + "true";
                            } else if (var20.b("double")) {
                                opcode = var18 + "0";
                            } else {
                                opcode = var18;
                                if (var20.b("String")) {
                                    var17 = true;
                                    opcode = var18;
                                }
                            }
                        } else {
                            opcode = var18 + var9;
                        }

                        ++hash;
                    }

                    var9 = bean.type;
                    var18 = opcode + ")" + ReturnMoreblockManager.getMbEnd(var9);
                    opcode = var9;
                    if (var17) {
                        var18 = var9;
                        break;
                    }
                }

                var9 = var18;
                var18 = opcode;
                opcode = var9;
                break;
            case 1:
                opcode = bean.spec;
                var26 = new StringBuilder();
                var26.append("_");
                var26.append(opcode);
                opcode = var26.toString();
                var18 = var9;
                break;
            case 2:
                opcode = bean.spec;
                var18 = var9;
                break;
            case 3:
                var8 = params.get(0);
                var18 = params.get(1);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = "false";
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s = %s;", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 4:
                var8 = params.get(0);
                var18 = params.get(1);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s = %s;", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 5:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s++;", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 6:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s--;", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 7:
                opcode = params.get(0);
                var18 = params.get(1);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s = %s;", opcode, var18);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 8:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s = new HashMap<>();", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 9:
                var11 = params.get(0);
                var18 = params.get(1);
                var8 = params.get(2);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = "";
                }

                var18 = var8;
                if (var8.length() <= 0) {
                    var18 = "";
                }

                if (!var11.isEmpty()) {
                    opcode = String.format("%s.put(%s, %s);", var11, opcode, var18);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 10:
                var8 = params.get(0);
                var18 = params.get(1);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = "";
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.get(%s).toString()", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 11:
                var8 = params.get(0);
                var18 = params.get(1);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = "";
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.containsKey(%s)", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 12:
                var8 = params.get(0);
                var18 = params.get(1);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = "";
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.remove(%s);", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 13:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.size()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 14:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.clear();", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 15:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.isEmpty()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 16:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("SketchwareUtil.getAllKeysFromMap(%s, %s);", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 17:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.add(Double.valueOf(%s));", var8, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 18:
                var8 = params.get(0);
                opcode = params.get(1);
                var11 = params.get(2);
                var18 = var9;
                if (!var8.isEmpty()) {
                    var18 = var9;
                    if (!opcode.isEmpty()) {
                        if (!var11.isEmpty()) {
                            opcode = String.format("%s.add((int)(%s), Double.valueOf(%s));", var11, opcode, var8);
                            var18 = var9;
                            break;
                        }

                        var18 = var9;
                    }
                }
                break;
            case 19:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.get((int)(%s)).doubleValue()", var8, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 20:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.indexOf(%s)", var8, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 21:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.contains(%s)", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 22:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.add(%s);", var8, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 23:
                opcode = params.get(0);
                var8 = params.get(1);
                var11 = params.get(2);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    var18 = var9;
                    if (!var8.isEmpty()) {
                        if (!var11.isEmpty()) {
                            opcode = String.format("%s.add((int)(%s), %s);", var11, var8, opcode);
                            var18 = var9;
                            break;
                        }

                        var18 = var9;
                    }
                }
                break;
            case 24:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.get((int)(%s))", var8, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 25:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.indexOf(%s)", var8, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 26:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.contains(%s)", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 27:
                var18 = params.get(0);
                var8 = params.get(1);
                var11 = params.get(2);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = "";
                }

                var18 = var8;
                if (var8.length() <= 0) {
                    var18 = "";
                }

                if (!var11.isEmpty()) {
                    var25 = new StringBuilder();
                    var25.append("{\r\n");
                    var25.append("HashMap<String, Object> _item = new HashMap<>();");
                    var25.append("\r\n");
                    var25.append(String.format("_item.put(%s, %s);", opcode, var18));
                    var25.append("\r\n");
                    var25.append(String.format("%s.add(_item);", var11));
                    var25.append("\r\n");
                    var25.append("}");
                    var25.append("\r\n");
                    opcode = var25.toString();
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 28:
                var8 = params.get(0);
                var10 = params.get(1);
                var11 = params.get(2);
                var16 = params.get(3);
                var18 = var8;
                if (var8.length() <= 0) {
                    var18 = "";
                }

                var8 = var10;
                if (var10.length() <= 0) {
                    var8 = "";
                }

                if (!var11.isEmpty()) {
                    opcode = var11;
                }

                if (!var16.isEmpty()) {
                    var24 = new StringBuilder();
                    var24.append("{\r\n");
                    var24.append("HashMap<String, Object> _item = new HashMap<>();");
                    var24.append("\r\n");
                    var24.append(String.format("_item.put(%s, %s);", var18, var8));
                    var24.append("\r\n");
                    var24.append(String.format("%s.add((int)%s, _item);", var16, opcode));
                    var24.append("\r\n");
                    var24.append("}");
                    var24.append("\r\n");
                    opcode = var24.toString();
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 29:
                var18 = params.get(0);
                var8 = params.get(1);
                var11 = params.get(2);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                var18 = var8;
                if (var8.length() <= 0) {
                    var18 = "";
                }

                if (!var11.isEmpty()) {
                    opcode = String.format("%s.get((int)%s).get(%s).toString()", var11, opcode, var18);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 30:
                var8 = params.get(0);
                var10 = params.get(1);
                var11 = params.get(2);
                var16 = params.get(3);
                var18 = var8;
                if (var8.length() <= 0) {
                    var18 = "";
                }

                var8 = var10;
                if (var10.length() <= 0) {
                    var8 = "";
                }

                if (!var11.isEmpty()) {
                    opcode = var11;
                }

                if (!var16.isEmpty()) {
                    opcode = String.format("%s.get((int)%s).put(%s, %s);", var16, opcode, var18, var8);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 31:
                var11 = params.get(0);
                var18 = params.get(1);
                var8 = params.get(2);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                var18 = var8;
                if (var8.length() <= 0) {
                    var18 = "";
                }

                if (!var11.isEmpty()) {
                    opcode = String.format("%s.get((int)%s).containsKey(%s)", var11, opcode, var18);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 32:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.add(%s);", var8, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 33:
                var8 = params.get(0);
                var18 = params.get(1);
                var11 = params.get(2);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                var18 = var9;
                if (!var8.isEmpty()) {
                    if (!var11.isEmpty()) {
                        opcode = String.format("%s.add((int)%s, %s);", var11, opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 34:
                var18 = params.get(0);
                var8 = params.get(1);
                var11 = params.get(2);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                var18 = var9;
                if (!var8.isEmpty()) {
                    if (!var11.isEmpty()) {
                        opcode = String.format("%s = %s.get((int)%s);", var11, var8, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 35:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.remove((int)(%s));", var8, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 36:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.size()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 37:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.clear();", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 38:
                hash = bean.subStack1;
                if (hash >= 0) {
                    opcode = a(String.valueOf(hash), "");
                } else {
                    opcode = "";
                }

                opcode = String.format("while(true) {\r\n%s\r\n}", opcode);
                var18 = var9;
                break;
            case 39:
                var8 = params.get(0);
                hash = bean.subStack1;
                if (hash >= 0) {
                    var18 = a(String.valueOf(hash), "");
                } else {
                    var18 = "";
                }

                if (!var8.isEmpty()) {
                    opcode = var8;
                }

                var8 = "_repeat" + bean.id;
                opcode = String.format("for(int %s = 0; %s < (int)(%s); %s++) {\r\n%s\r\n}", var8, var8, opcode, var8, var18);
                var18 = var9;
                break;
            case 40:
                var8 = params.get(0);
                hash = bean.subStack1;
                if (hash >= 0) {
                    opcode = a(String.valueOf(hash), "");
                } else {
                    opcode = "";
                }

                var18 = var8;
                if (var8.length() <= 0) {
                    var18 = "true";
                }

                opcode = String.format("if (%s) {\r\n%s\r\n}", var18, opcode);
                var18 = var9;
                break;
            case 41:
                var11 = params.get(0);
                hash = bean.subStack1;
                if (hash >= 0) {
                    opcode = a(String.valueOf(hash), "");
                } else {
                    opcode = "";
                }

                hash = bean.subStack2;
                if (hash >= 0) {
                    var18 = a(String.valueOf(hash), "");
                } else {
                    var18 = "";
                }

                var8 = var11;
                if (var11.length() <= 0) {
                    var8 = "true";
                }

                opcode = String.format("if (%s) {\r\n%s\r\n}\r\nelse {\r\n%s\r\n}", var8, opcode, var18);
                var18 = var9;
                break;
            case 42:
                opcode = "break;";
                var18 = var9;
                break;
            case 43:
            case 44:
                opcode = bean.opCode;
                var18 = var9;
                break;
            case 45:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("!%s", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
                var11 = params.get(0);
                var8 = params.get(1);
                var18 = var11;
                if (var11.length() <= 0) {
                    var18 = "0";
                }

                if (!var8.isEmpty()) {
                    opcode = var8;
                }

                opcode = String.format("%s %s %s", var18, bean.opCode, opcode);
                var18 = var9;
                break;
            case 53:
                var11 = params.get(0);
                var8 = params.get(1);
                var18 = var11;
                if (var11.length() <= 0) {
                    var18 = "0";
                }

                if (!var8.isEmpty()) {
                    opcode = var8;
                }

                opcode = String.format("%s == %s", var18, opcode);
                var18 = var9;
                break;
            case 54:
            case 55:
                var18 = params.get(0);
                var8 = params.get(1);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = "true";
                }

                var18 = var8;
                if (var8.length() <= 0) {
                    var18 = "true";
                }

                opcode = String.format("%s %s %s", opcode, bean.opCode, var18);
                var18 = var9;
                break;
            case 56:
                var8 = params.get(0);
                var11 = params.get(1);
                var18 = var8;
                if (var8.length() <= 0) {
                    var18 = "0";
                }

                if (!var11.isEmpty()) {
                    opcode = var11;
                }

                opcode = String.format("SketchwareUtil.getRandom((int)(%s), (int)(%s))", var18, opcode);
                var18 = var9;
                break;
            case 57:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.length()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 58:
                opcode = String.format("%s.concat(%s)", params.get(0), params.get(1));
                var18 = var9;
                break;
            case 59:
                opcode = params.get(0);
                opcode = String.format("%s.indexOf(%s)", params.get(1), opcode);
                var18 = var9;
                break;
            case 60:
                opcode = params.get(0);
                opcode = String.format("%s.lastIndexOf(%s)", params.get(1), opcode);
                var18 = var9;
                break;
            case 61:
                var10 = params.get(0);
                var8 = params.get(1);
                var11 = params.get(2);
                var18 = var8;
                if (var8.length() <= 0) {
                    var18 = "0";
                }

                if (!var11.isEmpty()) {
                    opcode = var11;
                }

                opcode = String.format("%s.substring((int)(%s), (int)(%s))", var10, var18, opcode);
                var18 = var9;
                break;
            case 62:
                opcode = String.format("%s.equals(%s)", params.get(0), params.get(1));
                var18 = var9;
                break;
            case 63:
                opcode = String.format("%s.contains(%s)", params.get(0), params.get(1));
                var18 = var9;
                break;
            case 64:
                opcode = String.format("%s.replace(%s, %s)", params.get(0), params.get(1), params.get(2));
                var18 = var9;
                break;
            case 65:
                opcode = String.format("%s.replaceFirst(%s, %s)", params.get(0), params.get(1), params.get(2));
                var18 = var9;
                break;
            case 66:
                opcode = String.format("%s.replaceAll(%s, %s)", params.get(0), params.get(1), params.get(2));
                var18 = var9;
                break;
            case 67:
                var18 = params.get(0);
                if (!var18.isEmpty() && !var18.equals("\"\"")) {
                    opcode = var18;
                } else {
                    opcode = "\"0\"";
                }
                opcode = String.format("Double.parseDouble(%s)", opcode);
                var18 = var9;
                break;
            case 68:
                opcode = "System.currentTimeMillis()";
                var18 = var9;
                break;
            case 69:
                opcode = String.format("%s.trim()", params.get(0));
                var18 = var9;
                break;
            case 70:
                opcode = String.format("%s.toUpperCase()", params.get(0));
                var18 = var9;
                break;
            case 71:
                opcode = String.format("%s.toLowerCase()", params.get(0));
                var18 = var9;
                break;
            case 72:
                var18 = params.get(0);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                opcode = String.format("String.valueOf((long)(%s))", opcode);
                var18 = var9;
                break;
            case 73:
                var18 = params.get(0);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                opcode = String.format("String.valueOf(%s)", opcode);
                var18 = var9;
                break;
            case 74:
                var8 = params.get(0);
                var11 = params.get(1);
                var18 = var11;
                if (var11.length() <= 0) {
                    var18 = "";
                }

                if (!var8.isEmpty()) {
                    opcode = var8;
                }

                opcode = String.format("new DecimalFormat(%s).format(%s)", var18, opcode);
                var18 = var9;
                break;
            case 75:
                var8 = bean.parameters.get(0);
                var18 = var9;
                if (var8 != null) {
                    var18 = var9;
                    opcode = var8;
                    if (!var8.isEmpty()) {
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 76:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s = new Gson().fromJson(%s, new TypeToken<HashMap<String, Object>>(){}.getType());", var8, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 77:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("new Gson().toJson(%s)", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 78:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s = new Gson().fromJson(%s, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());", var8, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 79:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("new Gson().toJson(%s)", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 80:
                var18 = params.get(0);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                opcode = String.format("SketchwareUtil.getDip(getApplicationContext(), (int)(%s))", opcode);
                var18 = var9;
                break;
            case 81:
                opcode = "SketchwareUtil.getDisplayWidthPixels(getApplicationContext())";
                var18 = var9;
                break;
            case 82:
                opcode = "SketchwareUtil.getDisplayHeightPixels(getApplicationContext())";
                var18 = var9;
                break;
            case 83:
                opcode = "Math.PI";
                var18 = var9;
                break;
            case 84:
                opcode = "Math.E";
                var18 = var9;
                break;
            case 85:
                var8 = params.get(0);
                var11 = params.get(1);
                var18 = var11;
                if (var11.length() <= 0) {
                    var18 = "0";
                }

                if (!var8.isEmpty()) {
                    opcode = var8;
                }

                opcode = String.format("Math.pow(%s, %s)", opcode, var18);
                var18 = var9;
                break;
            case 86:
                var8 = params.get(0);
                var11 = params.get(1);
                var18 = var11;
                if (var11.length() <= 0) {
                    var18 = "0";
                }

                if (!var8.isEmpty()) {
                    opcode = var8;
                }

                opcode = String.format("Math.min(%s, %s)", opcode, var18);
                var18 = var9;
                break;
            case 87:
                var8 = params.get(0);
                var11 = params.get(1);
                var18 = var11;
                if (var11.length() <= 0) {
                    var18 = "0";
                }

                if (!var8.isEmpty()) {
                    opcode = var8;
                }

                opcode = String.format("Math.max(%s, %s)", opcode, var18);
                var18 = var9;
                break;
            case 88:
                var18 = params.get(0);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = "1";
                }

                opcode = String.format("Math.sqrt(%s)", opcode);
                var18 = var9;
                break;
            case 89:
                var18 = params.get(0);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                opcode = String.format("Math.abs(%s)", opcode);
                var18 = var9;
                break;
            case 90:
                var18 = params.get(0);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                opcode = String.format("Math.round(%s)", opcode);
                var18 = var9;
                break;
            case 91:
                var18 = params.get(0);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                opcode = String.format("Math.ceil(%s)", opcode);
                var18 = var9;
                break;
            case 92:
                var18 = params.get(0);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                opcode = String.format("Math.floor(%s)", opcode);
                var18 = var9;
                break;
            case 93:
                var18 = params.get(0);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                opcode = String.format("Math.sin(%s)", opcode);
                var18 = var9;
                break;
            case 94:
                var18 = params.get(0);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                opcode = String.format("Math.cos(%s)", opcode);
                var18 = var9;
                break;
            case 95:
                var18 = params.get(0);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                opcode = String.format("Math.tan(%s)", opcode);
                var18 = var9;
                break;
            case 96:
                var18 = params.get(0);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                opcode = String.format("Math.asin(%s)", opcode);
                var18 = var9;
                break;
            case 97:
                var18 = params.get(0);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                opcode = String.format("Math.acos(%s)", opcode);
                var18 = var9;
                break;
            case 98:
                var18 = params.get(0);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                opcode = String.format("Math.atan(%s)", opcode);
                var18 = var9;
                break;
            case 99:
                var18 = params.get(0);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                opcode = String.format("Math.exp(%s)", opcode);
                var18 = var9;
                break;
            case 100:
                var18 = params.get(0);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                opcode = String.format("Math.log(%s)", opcode);
                var18 = var9;
                break;
            case 101:
                var18 = params.get(0);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                opcode = String.format("Math.log10(%s)", opcode);
                var18 = var9;
                break;
            case 102:
                var18 = params.get(0);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                opcode = String.format("Math.toRadians(%s)", opcode);
                var18 = var9;
                break;
            case 103:
                var18 = params.get(0);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                opcode = String.format("Math.toDegrees(%s)", opcode);
                var18 = var9;
                break;
            case 104:
                var18 = params.get(0);
                hash = bean.subStack1;
                if (hash >= 0) {
                    opcode = a(String.valueOf(hash), "");
                } else {
                    opcode = "";
                }

                if (!var18.isEmpty()) {
                    opcode = String.format("%s.setOnClickListener(new View.OnClickListener() {\n@Override\npublic void onClick(View _view) {\n%s\n}\n});", var18, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 105:
                if (!e.a(c).a) {
                    var18 = var9;
                    opcode = var8;
                } else {
                    opcode = "_drawer.isDrawerOpen(GravityCompat.START)";
                    var18 = var9;
                }
                break;
            case 106:
                if (e.a(c).a) {
                    opcode = "_drawer.openDrawer(GravityCompat.START);";
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 107:
                if (e.a(c).a) {
                    opcode = "_drawer.closeDrawer(GravityCompat.START);";
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 108:
                var8 = params.get(0);
                var18 = params.get(1);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = "true";
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.setEnabled(%s);", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 109:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.isEnabled()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 110:
                opcode = params.get(0);
                var18 = params.get(1);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.setText(%s);", opcode, var18);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 111:
                var8 = params.get(0);
                var11 = params.get(1);
                var18 = params.get(2);
                if (var18.length() <= 0) {
                    var18 = opcode;
                } else {
                    Pair<Integer, String>[] var22 = sq.a("property_text_style");
                    int var4 = var22.length;
                    opcode = var18;
                    hash = 0;

                    while (true) {
                        var18 = opcode;
                        if (hash >= var4) {
                            break;
                        }

                        Pair<Integer, String> var15 = var22[hash];
                        var18 = opcode;
                        if (var15.second.equals(opcode)) {
                            var27 = new StringBuilder();
                            var27.append(var15.first);
                            var27.append("");
                            var18 = var27.toString();
                        }

                        ++hash;
                        opcode = var18;
                    }
                }

                if (var11.length() <= 0) {
                    var18 = var9;
                } else {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.setTypeface(Typeface.createFromAsset(getAssets(),\"fonts/%s.ttf\"), %s);", var8, var11, var18);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 112:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.getText().toString()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 113:
                var8 = params.get(0);
                var18 = params.get(1);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = "0xFF000000";
                }

                var18 = var9;
                if (!var8.isEmpty()) {
                    if (!opcode.isEmpty()) {
                        opcode = String.format("%s.setBackgroundColor(%s);", var8, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 114:
                var11 = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!var11.isEmpty()) {
                    if (!var8.isEmpty()) {
                        if (!var8.equals("NONE")) {
                            opcode = var8;
                            if (var8.endsWith(".9")) {
                                opcode = var8.replaceAll("\\.9", "");
                            }

                            opcode = "R.drawable." + opcode;
                        }

                        opcode = String.format("%s.setBackgroundResource(%s);", var11, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 115:
                var8 = params.get(0);
                var18 = params.get(1);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = "0xFF000000";
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.setTextColor(%s);", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 116:
                var8 = params.get(0);
                var18 = params.get(1);
                opcode = var18;
                if (var18.endsWith(".9")) {
                    opcode = var18.replaceAll("\\.9", "");
                }

                var18 = var9;
                if (!var8.isEmpty()) {
                    if (!opcode.isEmpty()) {
                        opcode = String.format("%s.setImageResource(R.drawable.%s);", var8, opcode.toLowerCase());
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 117:
                var8 = params.get(0);
                var18 = params.get(1);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = "0x00000000";
                }

                if (!var8.equals("\"\"")) {
                    opcode = String.format("%s.setColorFilter(%s, PorterDuff.Mode.MULTIPLY);", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 118:
                opcode = params.get(0);
                opcode = String.format("%s.requestFocus();", opcode);
                var18 = var9;
                break;
            case 119:
                opcode = String.format("SketchwareUtil.showMessage(getApplicationContext(), %s);", params.get(0));
                var18 = var9;
                break;
            case 120:
                opcode = String.format("((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(\"clipboard\", %s));", params.get(0));
                var18 = var9;
                break;
            case 121:
                opcode = String.format("setTitle(%s);", params.get(0));
                var18 = var9;
                break;
            case 122:
                var8 = params.get(0);
                var11 = params.get(1);
                opcode = var18;
                if (!var11.isEmpty()) {
                    if (var11.equals("\"\"")) {
                        opcode = var18;
                    } else {
                        opcode = "Intent." + var11;
                    }
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.setAction(%s);", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 123:
                opcode = params.get(0);
                var18 = params.get(1);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.setData(Uri.parse(%s));", opcode, var18);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 124:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!var8.isEmpty()) {
                    if (!opcode.isEmpty()) {
                        opcode = String.format("%s.setClass(getApplicationContext(), %s.class);", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 125:
                var18 = params.get(0);
                opcode = params.get(1);
                var8 = params.get(2);
                if (!var18.isEmpty()) {
                    opcode = String.format("%s.putExtra(%s, %s);", var18, opcode, var8);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 126:
                var8 = params.get(0);
                var18 = params.get(1);
                if (var18.length() <= 0) {
                    opcode = "";
                } else {
                    opcode = "Intent.FLAG_ACTIVITY_" + var18;
                }

                var18 = var9;
                if (!var8.isEmpty()) {
                    if (!opcode.isEmpty()) {
                        opcode = String.format("%s.setFlags(%s);", var8, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 127:
                opcode = String.format("getIntent().getStringExtra(%s)", params.get(0));
                var18 = var9;
                break;
            case 128:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("startActivity(%s);", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 129:
                opcode = "finish();";
                var18 = var9;
                break;
            case 130:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s = getApplicationContext().getSharedPreferences(%s, Activity.MODE_PRIVATE);", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 131:
                opcode = params.get(0);
                var18 = params.get(1);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.getString(%s, \"\")", opcode, var18);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 132:
                opcode = params.get(0);
                var18 = params.get(1);
                var8 = params.get(2);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.edit().putString(%s, %s).commit();", opcode, var18, var8);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 133:
                opcode = params.get(0);
                var18 = params.get(1);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.edit().remove(%s).commit();", opcode, var18);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 134:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s = Calendar.getInstance();", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 135:
                var11 = params.get(0);
                var8 = params.get(1);
                var18 = params.get(2);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                var18 = var9;
                if (!var11.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.add(Calendar.%s, (int)(%s));", var11, var8, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 136:
                var11 = params.get(0);
                var8 = params.get(1);
                var18 = params.get(2);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                var18 = var9;
                if (!var11.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.set(Calendar.%s, (int)(%s));", var11, var8, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 137:
                var8 = params.get(0);
                var18 = params.get(1);
                opcode = (!var18.isEmpty() && !var18.equals("\"\"")) ? var18 : "\"yyyy/MM/dd hh:mm:ss\"";

                if (!var8.isEmpty()) {
                    opcode = String.format("new SimpleDateFormat(%s).format(%s.getTime())", opcode, var8);
                }

                var18 = var9;
                break;
            case 138:
                opcode = params.get(0);
                var8 = params.get(1);
                if (!opcode.isEmpty() && !var8.isEmpty()) {
                    opcode = String.format("(long)(%s.getTimeInMillis() - %s.getTimeInMillis())", opcode, var8);
                    var18 = var9;
                    break;
                }
                var18 = var9;
                break;
            case 139:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.getTimeInMillis()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 140:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.setTimeInMillis((long)(%s));", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 141:
                var8 = params.get(0);
                var18 = params.get(1);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = "VISIBLE";
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.setVisibility(View.%s);", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 142:
                var8 = params.get(0);
                var18 = params.get(1);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = "true";
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.setClickable(%s);", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 143:
                var8 = params.get(0);
                var18 = params.get(1);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.setRotation((float)(%s));", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 144:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.getRotation()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 145:
                var8 = params.get(0);
                var18 = params.get(1);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.setAlpha((float)(%s));", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 146:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.getAlpha()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 147:
                var8 = params.get(0);
                var18 = params.get(1);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.setTranslationX((float)(%s));", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 148:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.getTranslationX()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 149:
                var8 = params.get(0);
                var18 = params.get(1);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.setTranslationY((float)(%s));", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 150:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.getTranslationY()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 151:
                var8 = params.get(0);
                var18 = params.get(1);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.setScaleX((float)(%s));", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 152:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.getScaleX()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 153:
                var8 = params.get(0);
                var18 = params.get(1);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.setScaleY((float)(%s));", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 154:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.getScaleY()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 155:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("SketchwareUtil.getLocationX(%s)", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 156:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("SketchwareUtil.getLocationY(%s)", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 157:
                var8 = params.get(0);
                var18 = params.get(1);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = "false";
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.setChecked(%s);", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 158:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.isChecked()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 159:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.setAdapter(new ArrayAdapter<String>(getBaseContext(), %s, %s));", opcode, "android.R.layout.simple_list_item_1", var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 160:
            case 325:
            case 326:
            case 327:
            case 328:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        var18 = Lx.a(opcode);
                        String code = "%s.setAdapter(new " + var18 + "(%s));";
                        opcode = String.format(code, opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 161:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("((BaseAdapter)%s.getAdapter()).notifyDataSetChanged();", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 162:
                var11 = params.get(0);
                var18 = params.get(1);
                var8 = params.get(2);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                var18 = var8;
                if (var8.length() <= 0) {
                    var18 = "false";
                }

                if (!var11.isEmpty()) {
                    opcode = String.format("%s.setItemChecked((int)(%s), %s);", var11, opcode, var18);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 163:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.getCheckedItemPosition()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 164:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s = SketchwareUtil.getCheckedItemPositionsToArray(%s);", var8, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 165:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.getCheckedItemCount()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 166:
                opcode = params.get(0);
                var18 = params.get(1);
                if (!var18.isEmpty()) {
                    opcode = String.format("%s.smoothScrollToPosition((int)(%s));", opcode, var18);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 167:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, %s));", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 168:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("((ArrayAdapter)%s.getAdapter()).notifyDataSetChanged();", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 169:
                var8 = params.get(0);
                var18 = params.get(1);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.setSelection((int)(%s));", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 170:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.getSelectedItemPosition()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 171:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.loadUrl(%s);", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 172:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.getUrl()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 173:
                var8 = params.get(0);
                var18 = params.get(1);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = "LOAD_DEFAULT";
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.getSettings().setCacheMode(WebSettings.%s);", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 174:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.canGoBack()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 175:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.canGoForward()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 176:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.goBack();", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 177:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.goForward();", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 178:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.clearCache(true);", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 179:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.clearHistory();", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 180:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.stopLoading();", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 181:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.zoomIn();", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 182:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.zoomOut();", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 183:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.getDate()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 184:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.setDate((long)(%s), true, true);", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 185:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.setMinDate((long)(%s));", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 186:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.setMaxDate((long)(%s));", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 187:
                var8 = params.get(0);
                opcode = e.t.stream().map(device -> ".addTestDevice(\"" + device + "\")\n").collect(Collectors.joining());

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.loadAd(new AdRequest.Builder()%s.build());", var8, opcode);
                }
                var18 = var9;
                break;
            case 188:
                var8 = params.get(0);
                var18 = params.get(1);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = uq.q[0];
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("_%s_controller.setMapType(GoogleMap.%s);", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 189:
                var11 = params.get(0);
                var8 = params.get(1);
                var18 = var8;
                if (var8.length() <= 0) {
                    var18 = "0";
                }

                var8 = params.get(2);
                if (!var8.isEmpty()) {
                    opcode = var8;
                }

                if (!var11.isEmpty()) {
                    opcode = String.format("_%s_controller.moveCamera(%s, %s);", var11, var18, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 190:
                var8 = params.get(0);
                var18 = params.get(1);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("_%s_controller.zoomTo(%s);", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 191:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("_%s_controller.zoomIn();", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 192:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("_%s_controller.zoomOut();", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 193:
                var11 = params.get(0);
                var10 = params.get(1);
                var18 = params.get(2);
                var8 = var18;
                if (var18.length() <= 0) {
                    var8 = "0";
                }

                var18 = params.get(3);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                var18 = var9;
                if (!var11.isEmpty()) {
                    if (!var10.isEmpty()) {
                        opcode = String.format("_%s_controller.addMarker(%s, %s, %s);", var11, var10, var8, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 194:
                opcode = params.get(0);
                var11 = params.get(1);
                var8 = params.get(2);
                var10 = params.get(3);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var11.isEmpty()) {
                        opcode = String.format("_%s_controller.setMarkerInfo(%s, %s, %s);", opcode, var11, var8, var10);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 195:
                var11 = params.get(0);
                var10 = params.get(1);
                var18 = params.get(2);
                var8 = var18;
                if (var18.length() <= 0) {
                    var8 = "0";
                }

                var18 = params.get(3);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                var18 = var9;
                if (!var11.isEmpty()) {
                    if (!var10.isEmpty()) {
                        opcode = String.format("_%s_controller.setMarkerPosition(%s, %s, %s);", var11, var10, var8, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 196:
                var10 = params.get(0);
                var11 = params.get(1);
                var18 = params.get(2);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = uq.r[0];
                }

                var18 = params.get(3);
                var8 = var18;
                if (var18.length() <= 0) {
                    var8 = "1";
                }

                var18 = var9;
                if (!var10.isEmpty()) {
                    if (!var11.isEmpty()) {
                        opcode = String.format("_%s_controller.setMarkerColor(%s, BitmapDescriptorFactory.%s, %s);", var10, var11, opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 197:
                var8 = params.get(0);
                var11 = params.get(1);
                var18 = params.get(2);
                opcode = var18;
                if (var18.endsWith(".9")) {
                    opcode = var18.replaceAll("\\.9", "");
                }

                var18 = var9;
                if (!var8.isEmpty()) {
                    var18 = var9;
                    if (!var11.isEmpty()) {
                        if (!opcode.isEmpty()) {
                            opcode = String.format("_%s_controller.setMarkerIcon(%s, R.drawable.%s);", var8, var11, opcode.toLowerCase());
                            var18 = var9;
                            break;
                        }

                        var18 = var9;
                    }
                }
                break;
            case 198:
                var8 = params.get(0);
                var11 = params.get(1);
                var18 = params.get(2);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = "true";
                }

                var18 = var9;
                if (!var8.isEmpty()) {
                    if (!var11.isEmpty()) {
                        opcode = String.format("_%s_controller.setMarkerVisible(%s, %s);", var8, var11, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 199:
                var8 = params.get(0);
                var18 = params.get(1);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.vibrate((long)(%s));", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 200:
                String timer = params.get(0);
                var8 = params.get(1);
                hash = bean.subStack1;
                if (hash >= 0) {
                    var18 = a(String.valueOf(hash), "");
                } else {
                    var18 = "";
                }

                if (!var8.isEmpty()) {
                    opcode = var8;
                }

                if (!timer.isEmpty()) {
                    opcode = String.format("%s = new TimerTask() {\n@Override\npublic void run() {\nrunOnUiThread(new Runnable() {\n@Override\npublic void run() {\n%s\n}\n});\n}\n};\n_timer.schedule(%s, (int)(%s));", timer, var18, timer, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 201:
                var12 = params.get(0);
                var11 = params.get(1);
                var10 = params.get(2);
                hash = bean.subStack1;
                var18 = (hash >= 0) ? a(String.valueOf(hash), "") : "";

                var8 = var10.isEmpty() ? "0" : var10;
                opcode = var11.isEmpty() ? opcode : var11;

                if (!var12.isEmpty()) {
                    opcode = String.format("%s = new TimerTask() {\n@Override\npublic void run() {\nrunOnUiThread(new Runnable() {\n@Override\npublic void run() {\n%s\n}\n});\n}\n};\n_timer.scheduleAtFixedRate(%s, (int)(%s), (int)(%s));", var12, var18, var12, opcode, var8);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 202:
                timer = params.get(0);
                if (!timer.isEmpty()) {
                    opcode = String.format("%s.cancel();", timer);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 203:
                var8 = params.get(0);
                var18 = params.get(1);
                var11 = params.get(2);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = "";
                }

                var18 = var9;
                if (!var8.isEmpty()) {
                    if (!var11.isEmpty()) {
                        opcode = String.format("%s.child(%s).updateChildren(%s);", var8, opcode, var11);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 204:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.push().updateChildren(%s);", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 205:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.push().getKey()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 206:
                var8 = params.get(0);
                var18 = params.get(1);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = "";
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.child(%s).removeValue();", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 207:
                var8 = params.get(0);
                var11 = params.get(1);
                hash = bean.subStack1;
                if (hash >= 0) {
                    opcode = a(String.valueOf(hash), "");
                } else {
                    opcode = "";
                }

                var18 = var9;
                if (!var11.isEmpty()) {
                    if (!var8.isEmpty()) {
                        var26 = new StringBuilder();
                        var26.append(String.format("%s.addListenerForSingleValueEvent(new ValueEventListener() {", var8));
                        var26.append("\n@Override\npublic void onDataChange(DataSnapshot _dataSnapshot) {\n");
                        var26.append(String.format("%s = new ArrayList<>();", var11));
                        var26.append("\ntry {\nGenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};\nfor (DataSnapshot _data : _dataSnapshot.getChildren()) {\nHashMap<String, Object> _map = _data.getValue(_ind);\n");
                        var26.append(String.format("%s.add(_map);", var11));
                        var26.append("\n}\n}\ncatch (Exception _e) {\n_e.printStackTrace();\n}\n");
                        var26.append(opcode);
                        var26.append("\n}\n@Override\npublic void onCancelled(DatabaseError _databaseError) {\n}\n});");
                        opcode = var26.toString();
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 208:
                opcode = params.get(0);
                var8 = params.get(1);
                var11 = params.get(2);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    var18 = var9;
                    if (!var8.equals("\"\"")) {
                        if (!var11.equals("\"\"")) {
                            opcode = String.format("%s.createUserWithEmailAndPassword(%s, %s).addOnCompleteListener(%s.this, %s);", opcode, var8, var11, c, "_" + opcode + "_create_user_listener");
                            var18 = var9;
                            break;
                        }

                        var18 = var9;
                    }
                }
                break;
            case 209:
                var8 = params.get(0);
                opcode = params.get(1);
                var11 = params.get(2);
                var18 = var9;
                if (!var8.isEmpty()) {
                    var18 = var9;
                    if (!opcode.equals("\"\"")) {
                        if (!var11.equals("\"\"")) {
                            opcode = String.format("%s.signInWithEmailAndPassword(%s, %s).addOnCompleteListener(%s.this, %s);", var8, opcode, var11, c, "_" + var8 + "_sign_in_listener");
                            var18 = var9;
                            break;
                        }

                        var18 = var9;
                    }
                }
                break;
            case 210:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.signInAnonymously().addOnCompleteListener(%s.this, %s);", opcode, c, "_" + opcode + "_sign_in_listener");
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 211:
                var18 = var9;
                opcode = var8;
                if (e.a(c).b) {
                    opcode = "(FirebaseAuth.getInstance().getCurrentUser() != null)";
                    var18 = var9;
                }
                break;
            case 212:
                var18 = var9;
                if (e.a(c).b) {
                    opcode = "FirebaseAuth.getInstance().getCurrentUser().getEmail()";
                    var18 = var9;
                    break;
                }
                break;
            case 213:
                var18 = var9;
                if (e.a(c).b) {
                    opcode = "FirebaseAuth.getInstance().getCurrentUser().getUid()";
                    var18 = var9;
                    break;
                }
                break;
            case 214:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.equals("\"\"")) {
                        opcode = String.format("%s.sendPasswordResetEmail(%s).addOnCompleteListener(%s);", opcode, var8, "_" + opcode + "_reset_password_listener");
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 215:
                var18 = var9;
                if (e.a(c).b) {
                    opcode = "FirebaseAuth.getInstance().signOut();";
                    var18 = var9;
                    break;
                }
                break;
            case 216:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.addChildEventListener(_%s_child_listener);", opcode, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 217:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.removeEventListener(_%s_child_listener);", opcode, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 218:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.registerListener(_%s_sensor_listener, %s.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);", opcode, opcode, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 219:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.unregisterListener(_%s_sensor_listener);", opcode, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 220:
                String dialog = params.get(0);
                String title = params.get(1);
                title = (!title.isEmpty() && !title.equals("\"\"")) ? title : "\"Title\"";

                if (!dialog.isEmpty()) {
                    opcode = String.format("%s.setTitle(%s);", dialog, title);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 221:
                dialog = params.get(0);
                String message = params.get(1);
                opcode = (!message.isEmpty() && !message.equals("\"\"")) ? message : "\"Message\"";

                if (!dialog.isEmpty()) {
                    opcode = String.format("%s.setMessage(%s);", dialog, message);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 222:
                dialog = params.get(0);
                if (!dialog.isEmpty()) {
                    opcode = String.format("%s.create().show();", dialog);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 223:
                var11 = params.get(0);
                var8 = params.get(1);
                hash = bean.subStack1;
                opcode = (hash >= 0) ? a(String.valueOf(hash), "") : "";

                var18 = (!var8.isEmpty() && var8.equals("\"\"")) ? "\"OK\"" : var8;

                if (!var11.isEmpty()) {
                    opcode = String.format("%s.setPositiveButton(%s, new DialogInterface.OnClickListener() {\n@Override\npublic void onClick(DialogInterface _dialog, int _which) {\n%s\n}\n});", var11, var18, opcode);
                }
                var18 = var9;
                break;
            case 224:
                var11 = params.get(0);
                var8 = params.get(1);
                hash = bean.subStack1;
                opcode = (hash >= 0) ? a(String.valueOf(hash), "") : "";

                var18 = (!var8.isEmpty() && var8.equals("\"\"")) ? "\"Cancel\"" : var8;

                if (!var11.isEmpty()) {
                    opcode = String.format("%s.setNegativeButton(%s, new DialogInterface.OnClickListener() {\n@Override\npublic void onClick(DialogInterface _dialog, int _which) {\n%s\n}\n});", var11, var18, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 225:
                var11 = params.get(0);
                var8 = params.get(1);
                hash = bean.subStack1;
                opcode = (hash >= 0) ? a(String.valueOf(hash), "") : "";

                var18 = (!var8.isEmpty() && var8.equals("\"\"")) ? "\"Neutral\"" : var8;

                if (!var11.isEmpty()) {
                    opcode = String.format("%s.setNeutralButton(%s, new DialogInterface.OnClickListener() {\n@Override\npublic void onClick(DialogInterface _dialog, int _which) {\n%s\n}\n});", var11, var18, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 226:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s = MediaPlayer.create(getApplicationContext(), R.raw.%s);", opcode, var8.toLowerCase());
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 227:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.start();", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 228:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.pause();", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 229:
                var8 = params.get(0);
                var18 = params.get(1);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.seekTo((int)(%s));", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 230:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.getCurrentPosition()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 231:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.getDuration()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 232:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.reset();", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 233:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.release();", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 234:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.isPlaying()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 235:
                var8 = params.get(0);
                var18 = params.get(1);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = "false";
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.setLooping(%s);", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 236:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.isLooping()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 237:
                var8 = params.get(0);
                var18 = params.get(1);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = "1";
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s = new SoundPool((int)(%s), AudioManager.STREAM_MUSIC, 0);", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 238:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!var8.isEmpty()) {
                    if (!opcode.isEmpty()) {
                        opcode = String.format("%s.load(getApplicationContext(), R.raw.%s, 1)", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 239:
                var11 = params.get(0);
                var8 = params.get(1);
                var18 = params.get(2);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                var18 = var9;
                if (!var8.isEmpty()) {
                    if (!var11.isEmpty()) {
                        opcode = String.format("%s.play((int)(%s), 1.0f, 1.0f, 1, (int)(%s), 1.0f)", var11, var8, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 240:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!var8.isEmpty()) {
                    if (!opcode.isEmpty()) {
                        opcode = String.format("%s.stop((int)(%s));", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 241:
                var8 = params.get(0);
                var18 = params.get(1);
                opcode = var18;
                if (var18.endsWith(".9")) {
                    opcode = var18.replaceAll("\\.9", "");
                }

                var18 = var9;
                if (!var8.isEmpty()) {
                    if (!opcode.isEmpty()) {
                        opcode = String.format("%s.setThumbResource(R.drawable.%s);", var8, opcode.toLowerCase());
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 242:
                var8 = params.get(0);
                var18 = params.get(1);
                opcode = var18;
                if (var18.endsWith(".9")) {
                    opcode = var18.replaceAll("\\.9", "");
                }

                var18 = var9;
                if (!var8.isEmpty()) {
                    if (!opcode.isEmpty()) {
                        opcode = String.format("%s.setTrackResource(R.drawable.%s);", var8, opcode.toLowerCase());
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 243:
                var8 = params.get(0);
                var18 = params.get(1);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.setProgress((int)%s);", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 244:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.getProgress()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 245:
                var8 = params.get(0);
                var18 = params.get(1);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.setMax((int)%s);", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 246:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.getMax()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 247:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.setTarget(%s);", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 248:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.setPropertyName(\"%s\");", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 249:
                var8 = params.get(0);
                var18 = params.get(1);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.setFloatValues((float)(%s));", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 250:
                var10 = params.get(0);
                var8 = params.get(1);
                var11 = params.get(2);
                var18 = var11;
                if (var11.length() <= 0) {
                    var18 = "0";
                }

                if (!var8.isEmpty()) {
                    opcode = var8;
                }

                if (!var10.isEmpty()) {
                    opcode = String.format("%s.setFloatValues((float)(%s), (float)(%s));", var10, opcode, var18);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 251:
                var8 = params.get(0);
                var18 = params.get(1);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.setDuration((int)(%s));", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 252:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.setRepeatMode(ValueAnimator.%s);", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 253:
                var8 = params.get(0);
                var18 = params.get(1);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.setRepeatCount((int)(%s));", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 254:
                var18 = params.get(0);
                var8 = params.get(1);
                if (var8.equals("Accelerate")) {
                    opcode = "new AccelerateInterpolator()";
                } else {
                    opcode = "new LinearInterpolator()";
                }

                if (var8.equals("Decelerate")) {
                    opcode = "new DecelerateInterpolator()";
                }

                if (var8.equals("AccelerateDeccelerate")) {
                    opcode = "new AccelerateDecelerateInterpolator()";
                }

                if (var8.equals("Bounce")) {
                    opcode = "new BounceInterpolator()";
                }

                if (!var18.isEmpty()) {
                    opcode = String.format("%s.setInterpolator(%s);", var18, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 255:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.start();", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 256:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.cancel();", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 257:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.isRunning()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 258:
            case 259:
            case 260:
                opcode = "";
                var18 = var9;
                break;
            case 261:
                var8 = params.get(0);
                opcode = params.get(1);
                var11 = params.get(2);

                if (!var8.isEmpty() && !opcode.equals("\"\"") && !var11.equals("\"\"")) {
                    opcode = String.format(
                            "%s.child(%s).putFile(Uri.fromFile(new File(%s))).addOnFailureListener(_%s_failure_listener).addOnProgressListener(_%s_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {\n@Override\npublic Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {\nreturn %s.child(%s).getDownloadUrl();\n}}).addOnCompleteListener(_%s_upload_success_listener);",
                            var8, var11, opcode, var8, var8, var8, var11, var8
                    );
                    var18 = var9;
                    break;
                }
            case 262:
                var8 = params.get(0);
                opcode = params.get(1);
                var11 = params.get(2);
                var18 = var9;

                if (!var8.isEmpty() && !opcode.equals("\"\"") && !var11.equals("\"\"")) {
                    opcode = String.format(
                            "_firebase_storage.getReferenceFromUrl(%s).getFile(new File(%s)).addOnSuccessListener(_%s_download_success_listener).addOnFailureListener(_%s_failure_listener).addOnProgressListener(_%s_download_progress_listener);",
                            opcode, var11, var8, var8, var8
                    );
                }
                break;
            case 263:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.equals("\"\"")) {
                        opcode = String.format("_firebase_storage.getReferenceFromUrl(%s).delete().addOnSuccessListener(_%s_delete_success_listener).addOnFailureListener(_%s_failure_listener);", var8, opcode, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 264:
                opcode = params.get(0);
                if (!opcode.equals("\"\"")) {
                    opcode = String.format("FileUtil.readFile(%s)", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 265:
                opcode = params.get(0);
                var18 = params.get(1);
                if (!var18.equals("\"\"")) {
                    opcode = String.format("FileUtil.writeFile(%s, %s);", var18, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 266:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.equals("\"\"")) {
                    if (!var8.equals("\"\"")) {
                        opcode = String.format("FileUtil.copyFile(%s, %s);", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 267:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.equals("\"\"")) {
                    if (!var8.equals("\"\"")) {
                        opcode = String.format("FileUtil.moveFile(%s, %s);", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 268:
                opcode = params.get(0);
                if (!opcode.equals("\"\"")) {
                    opcode = String.format("FileUtil.deleteFile(%s);", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 269:
                opcode = params.get(0);
                if (!opcode.equals("\"\"")) {
                    opcode = String.format("FileUtil.isExistFile(%s)", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 270:
                opcode = params.get(0);
                if (!opcode.equals("\"\"")) {
                    opcode = String.format("FileUtil.makeDir(%s);", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 271:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.equals("\"\"")) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("FileUtil.listDir(%s, %s);", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 272:
                opcode = params.get(0);
                if (!opcode.equals("\"\"")) {
                    opcode = String.format("FileUtil.isDirectory(%s)", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 273:
                opcode = params.get(0);
                if (!opcode.equals("\"\"")) {
                    opcode = String.format("FileUtil.isFile(%s)", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 274:
                opcode = params.get(0);
                if (!opcode.equals("\"\"")) {
                    opcode = String.format("FileUtil.getFileLength(%s)", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 275:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.equals("\"\"")) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.startsWith(%s)", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 276:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.equals("\"\"")) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.endsWith(%s)", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 277:
                opcode = params.get(0);
                if (!opcode.equals("\"\"")) {
                    opcode = String.format("Uri.parse(%s).getLastPathSegment()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 278:
                opcode = "FileUtil.getExternalStorageDir()";
                var18 = var9;
                break;
            case 279:
                opcode = "FileUtil.getPackageDataDir(getApplicationContext())";
                var18 = var9;
                break;
            case 280:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("FileUtil.getPublicDir(Environment.%s)", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 281:
                var8 = params.get(0);
                var11 = params.get(1);
                var18 = params.get(2);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = "1024";
                }

                var18 = var9;
                if (!var8.equals("\"\"")) {
                    if (!var11.equals("\"\"")) {
                        opcode = String.format("FileUtil.resizeBitmapFileRetainRatio(%s, %s, %s);", var8, var11, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 282:
                var8 = params.get(0);
                var11 = params.get(1);
                var18 = params.get(2);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = "1024";
                }

                var18 = var9;
                if (!var8.equals("\"\"")) {
                    if (!var11.equals("\"\"")) {
                        opcode = String.format("FileUtil.resizeBitmapFileToSquare(%s, %s, %s);", var8, var11, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 283:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.equals("\"\"")) {
                    if (!var8.equals("\"\"")) {
                        opcode = String.format("FileUtil.resizeBitmapFileToCircle(%s, %s);", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 284:
                var8 = params.get(0);
                var11 = params.get(1);
                var18 = params.get(2);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                var18 = var9;
                if (!var8.equals("\"\"")) {
                    if (!var11.equals("\"\"")) {
                        opcode = String.format("FileUtil.resizeBitmapFileWithRoundedBorder(%s, %s, %s);", var8, var11, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 285:
                var10 = params.get(0);
                var11 = params.get(1);
                var8 = params.get(2);
                var18 = params.get(3);
                opcode = var8;
                if (var8.length() <= 0) {
                    opcode = "1024";
                }

                var8 = var18;
                if (var18.length() <= 0) {
                    var8 = "1024";
                }

                var18 = var9;
                if (!var10.equals("\"\"")) {
                    if (!var11.equals("\"\"")) {
                        opcode = String.format("FileUtil.cropBitmapFileFromCenter(%s, %s, %s, %s);", var10, var11, opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 286:
                var8 = params.get(0);
                var11 = params.get(1);
                var18 = params.get(2);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                var18 = var9;
                if (!var8.equals("\"\"")) {
                    if (!var11.equals("\"\"")) {
                        opcode = String.format("FileUtil.rotateBitmapFile(%s, %s, %s);", var8, var11, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 287:
                var11 = params.get(0);
                var10 = params.get(1);
                var8 = params.get(2);
                var18 = params.get(3);
                opcode = var8;
                if (var8.length() <= 0) {
                    opcode = "1";
                }

                var8 = var18;
                if (var18.length() <= 0) {
                    var8 = "1";
                }

                var18 = var9;
                if (!var11.equals("\"\"")) {
                    if (!var10.equals("\"\"")) {
                        opcode = String.format("FileUtil.scaleBitmapFile(%s, %s, %s, %s);", var11, var10, opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 288:
                var12 = params.get(0);
                var10 = params.get(1);
                var18 = params.get(2);
                var11 = params.get(3);
                var8 = var18;
                if (var18.length() <= 0) {
                    var8 = "0";
                }

                if (!var11.isEmpty()) {
                    opcode = var11;
                }

                var18 = var9;
                if (!var12.equals("\"\"")) {
                    if (!var10.equals("\"\"")) {
                        opcode = String.format("FileUtil.skewBitmapFile(%s, %s, %s, %s);", var12, var10, var8, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 289:
                var8 = params.get(0);
                var11 = params.get(1);
                var18 = params.get(2);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = "0x00000000";
                }

                var18 = var9;
                if (!var8.equals("\"\"")) {
                    if (!var11.equals("\"\"")) {
                        opcode = String.format("FileUtil.setBitmapFileColorFilter(%s, %s, %s);", var8, var11, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 290:
                var11 = params.get(0);
                var8 = params.get(1);
                var18 = params.get(2);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                var18 = var9;
                if (!var11.equals("\"\"")) {
                    if (!var8.equals("\"\"")) {
                        opcode = String.format("FileUtil.setBitmapFileBrightness(%s, %s, %s);", var11, var8, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 291:
                var8 = params.get(0);
                var11 = params.get(1);
                var18 = params.get(2);
                if (!var18.isEmpty()) {
                    opcode = var18;
                }

                var18 = var9;
                if (!var8.equals("\"\"")) {
                    if (!var11.equals("\"\"")) {
                        opcode = String.format("FileUtil.setBitmapFileContrast(%s, %s, %s);", var8, var11, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 292:
                opcode = params.get(0);
                if (!opcode.equals("\"\"")) {
                    opcode = String.format("FileUtil.getJpegRotate(%s)", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 293:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("startActivityForResult(%s, REQ_CD_%s);", opcode, opcode.toUpperCase());
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 294:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("startActivityForResult(%s, REQ_CD_%s);", opcode, opcode.toUpperCase());
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 295:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.equals("\"\"")) {
                        opcode = String.format("%s.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(%s, 1024, 1024));", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 296:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.equals("\"\"")) {
                        opcode = String.format("Glide.with(getApplicationContext()).load(Uri.parse(%s)).into(%s);", var8, opcode);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 297:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.equals("\"\"")) {
                        opcode = String.format("%s.setHint(%s);", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 298:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.equals("\"\"")) {
                        opcode = String.format("%s.setHintTextColor(%s);", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 299:
                opcode = params.get(0);
                var8 = params.get(1);
                var11 = params.get(2);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    var18 = var9;
                    if (!var8.isEmpty()) {
                        if (!var11.isEmpty()) {
                            opcode = String.format("%s.setParams(%s, RequestNetworkController.%s);", opcode, var8, var11);
                            var18 = var9;
                            break;
                        }

                        var18 = var9;
                    }
                }
                break;
            case 300:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.setHeaders(%s);", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 301:
                var11 = params.get(0);
                var8 = params.get(1);
                opcode = params.get(2);
                var10 = params.get(3);
                var18 = var9;
                if (!var11.isEmpty()) {
                    var18 = var9;
                    if (!var8.isEmpty()) {
                        var18 = var9;
                        if (!opcode.equals("\"\"")) {
                            if (!var10.isEmpty()) {
                                opcode = String.format("%s.startRequestNetwork(RequestNetworkController.%s, %s, %s, _%s_request_listener);", var11, var8, opcode, var10, var11);
                                var18 = var9;
                                break;
                            }

                            var18 = var9;
                        }
                    }
                }
                break;
            case 302:
                var8 = params.get(0);
                var18 = params.get(1);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = "false";
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.setIndeterminate(%s);", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 303:
                var8 = params.get(0);
                var18 = params.get(1);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = "1";
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.setPitch((float)%s);", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 304:
                var8 = params.get(0);
                var18 = params.get(1);
                opcode = var18;
                if (var18.length() <= 0) {
                    opcode = "1";
                }

                if (!var8.isEmpty()) {
                    opcode = String.format("%s.setSpeechRate((float)%s);", var8, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 305:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.speak(%s, TextToSpeech.QUEUE_ADD, null);", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 306:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.isSpeaking()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 307:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.stop();", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 308:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.shutdown();", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 309:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("Intent _intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);\n_intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());\n_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);\n_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());\n%s.startListening(_intent);", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 310:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.stopListening();", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 311:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.cancel();\n%s.destroy();", opcode, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 312:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.readyConnection(_%s_bluetooth_connection_listener, %s);", opcode, opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 313:
                opcode = params.get(0);
                var8 = params.get(1);
                var11 = params.get(2);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    var18 = var9;
                    if (!var8.isEmpty()) {
                        if (!var11.isEmpty()) {
                            opcode = String.format("%s.readyConnection(_%s_bluetooth_connection_listener, %s, %s);", opcode, opcode, var8, var11);
                            var18 = var9;
                            break;
                        }

                        var18 = var9;
                    }
                }
                break;
            case 314:
                opcode = params.get(0);
                var8 = params.get(1);
                var11 = params.get(2);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    var18 = var9;
                    if (!var8.isEmpty()) {
                        if (!var11.isEmpty()) {
                            opcode = String.format("%s.startConnection(_%s_bluetooth_connection_listener, %s, %s);", opcode, opcode, var8, var11);
                            var18 = var9;
                            break;
                        }

                        var18 = var9;
                    }
                }
                break;
            case 315:
                opcode = params.get(0);
                var11 = params.get(1);
                var8 = params.get(2);
                var10 = params.get(3);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    var18 = var9;
                    if (!var11.isEmpty()) {
                        var18 = var9;
                        if (!var8.isEmpty()) {
                            if (!var10.isEmpty()) {
                                opcode = String.format("%s.startConnection(_%s_bluetooth_connection_listener, %s, %s, %s);", opcode, opcode, var11, var8, var10);
                                var18 = var9;
                                break;
                            }

                            var18 = var9;
                        }
                    }
                }
                break;
            case 316:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.stopConnection(_%s_bluetooth_connection_listener, %s);", opcode, opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 317:
                var8 = params.get(0);
                opcode = params.get(1);
                var11 = params.get(2);
                var18 = var9;
                if (!var8.isEmpty()) {
                    var18 = var9;
                    if (!opcode.isEmpty()) {
                        if (!var11.isEmpty()) {
                            opcode = String.format("%s.sendData(_%s_bluetooth_connection_listener, %s, %s);", var8, var8, opcode, var11);
                            var18 = var9;
                            break;
                        }

                        var18 = var9;
                    }
                }
                break;
            case 318:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.isBluetoothEnabled()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 319:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.isBluetoothActivated()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 320:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.activateBluetooth();", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 321:
                opcode = params.get(0);
                var8 = params.get(1);
                var18 = var9;
                if (!opcode.isEmpty()) {
                    if (!var8.isEmpty()) {
                        opcode = String.format("%s.getPairedDevices(%s);", opcode, var8);
                        var18 = var9;
                        break;
                    }

                    var18 = var9;
                }
                break;
            case 322:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.getRandomUUID()", opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
            case 323:
                var10 = params.get(0);
                var8 = params.get(1);
                var18 = var8;
                if (var8.length() <= 0) {
                    var18 = uq.p[0];
                }

                var11 = params.get(2);
                var8 = var11;
                if (var11.length() <= 0) {
                    var8 = "1000";
                }

                var11 = params.get(3);
                if (!var11.isEmpty()) {
                    opcode = var11;
                }

                if (!var10.isEmpty()) {
                    if (e.g) {
                        opcode = String.format("if (ContextCompat.checkSelfPermission(%s.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {\n%s.requestLocationUpdates(LocationManager.%s, %s, %s, _%s_location_listener);\n}", c, var10, var18, var8, opcode, var10);
                        var18 = var9;
                    } else {
                        opcode = String.format("if (Build.VERSION.SDK_INT >= 23) {if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {\n%s.requestLocationUpdates(LocationManager.%s, %s, %s, _%s_location_listener);\n}\n}\nelse {\n%s.requestLocationUpdates(LocationManager.%s, %s, %s, _%s_location_listener);\n}", var10, var18, var8, opcode, var10, var10, var18, var8, opcode, var10);
                        var18 = var9;
                    }
                    break;
                }

                var18 = var9;
                break;
            case 324:
                opcode = params.get(0);
                if (!opcode.isEmpty()) {
                    opcode = String.format("%s.removeUpdates(_%s_location_listener);", opcode, opcode);
                    var18 = var9;
                    break;
                }

                var18 = var9;
                break;
        }

        var9 = opcode;
        StringBuilder var14;
        if (b(bean.opCode, var2)) {
            var14 = new StringBuilder();
            var14.append("(");
            var14.append(opcode);
            var14.append(")");
            var9 = var14.toString();
        }

        var2 = var9;
        if (bean.nextBlock >= 0) {
            var14 = new StringBuilder();
            var14.append(var9);
            var14.append("\r\n");
            var14.append(a(String.valueOf(bean.nextBlock), var18));
            var2 = var14.toString();
        }

        return var2;
    }

    public final String a(String var1) {
        StringBuilder var2 = new StringBuilder(4096);
        CharBuffer var3 = CharBuffer.wrap(var1);

        for (int var4 = 0; var4 < var3.length(); ++var4) {
            char var5 = var3.get(var4);
            if (var5 == '"') {
                var2.append("\\\"");
            } else if (var5 == '\\') {
                if (var4 < var3.length() - 1) {
                    int var6 = var4 + 1;
                    var5 = var3.get(var6);
                    if (var5 != 'n' && var5 != 't') {
                        var2.append("\\\\");
                    } else {
                        var2.append("\\").append(var5);
                        var4 = var6;
                    }
                } else {
                    var2.append("\\\\");
                }
            } else if (var5 == '\n') {
                var2.append("\\n");
            } else {
                var2.append(var5);
            }
        }

        return var2.toString();
    }

    public final String a(String var1, int var2, String var3) {
        if (!var1.isEmpty() && var1.charAt(0) == '@') {
            var3 = a(var1.substring(1), var3);
            var1 = var3;
            if (var2 == 2) {
                var1 = var3;
                if (var3.length() <= 0) {
                    var1 = "\"\"";
                }
            }

            return var1;
        } else {
            StringBuilder var6;
            if (var2 == 2) {
                var6 = new StringBuilder();
                var6.append("\"");
                var6.append(a(var1));
                var6.append("\"");
                return var6.toString();
            } else {
                var3 = var1;
                if (var2 == 1) {
                    try {
                        Integer.parseInt(var1);
                        return var1;
                    } catch (NumberFormatException var5) {
                        try {
                            Double.parseDouble(var1);
                            var6 = new StringBuilder();
                            var6.append(var1);
                            var6.append("d");
                            var3 = var6.toString();
                        } catch (NumberFormatException var4) {
                            var3 = var1;
                        }
                    }
                }

                return var3;
            }
        }
    }

    public final String a(String var1, String var2) {
        return !g.containsKey(var1) ? "" : a(g.get(var1), var2);
    }

    public final boolean b(String var1, String var2) {
        boolean var11 = Arrays.asList(a).contains(var2);
        boolean var10 = Arrays.asList(b).contains(var1);
        return var11 && var10;
    }
}
