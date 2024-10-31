package a.a.a;

import android.util.Pair;
import com.besome.sketch.beans.BlockBean;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
        g = new HashMap();
        ArrayList var1 = f;
        if (var1 != null && var1.size() > 0) {
            Iterator var2 = f.iterator();

            while(var2.hasNext()) {
                BlockBean var3 = (BlockBean)var2.next();
                g.put(var3.id, var3);
            }

            StringBuilder var4 = new StringBuilder(4096);
            var4.append(a((BlockBean)f.get(0), ""));
            return var4.toString();
        } else {
            return "";
        }
    }

    public final String a(BlockBean blockBean, String var2) {
        ArrayList var3 = new ArrayList();

        int var4;
        String var5;
        int var7;
        for(var4 = 0; var4 < blockBean.parameters.size(); ++var4) {
            var5 = (String)blockBean.parameters.get(var4);
            Gx var6 = (Gx)blockBean.getParamClassInfo().get(var4);
            if (var6.b("boolean")) {
                var7 = 0;
            } else if (var6.b("double")) {
                var7 = 1;
            } else if (var6.b("String")) {
                var7 = 2;
            } else {
                var7 = 3;
            }

            var3.add(a(var5, var7, blockBean.opCode));
        }

        String var8;
        short var19;
        label3430: {
            var5 = blockBean.opCode;
            var7 = var5.hashCode();
            var8 = "false";
            switch (var7) {
                case -2135695280:
                    if (var5.equals("webViewLoadUrl")) {
                        var19 = 171;
                        break label3430;
                    }
                    break;
                case -2120571577:
                    if (var5.equals("mapIsEmpty")) {
                        var19 = 15;
                        break label3430;
                    }
                    break;
                case -2114384168:
                    if (var5.equals("firebasestorageDownloadFile")) {
                        var19 = 262;
                        break label3430;
                    }
                    break;
                case -2055793167:
                    if (var5.equals("fileutillistdir")) {
                        var19 = 271;
                        break label3430;
                    }
                    break;
                case -2037144358:
                    if (var5.equals("bluetoothConnectStartConnectionToUuid")) {
                        var19 = 315;
                        break label3430;
                    }
                    break;
                case -2027093331:
                    if (var5.equals("calendarViewSetDate")) {
                        var19 = 184;
                        break label3430;
                    }
                    break;
                case -2020761366:
                    if (var5.equals("fileRemoveData")) {
                        var19 = 133;
                        break label3430;
                    }
                    break;
                case -1998407506:
                    if (var5.equals("listSetData")) {
                        var19 = 159;
                        break label3430;
                    }
                    break;
                case -1989678633:
                    if (var5.equals("mapViewSetMarkerVisible")) {
                        var19 = 198;
                        break label3430;
                    }
                    break;
                case -1979147952:
                    if (var5.equals("stringContains")) {
                        var19 = 63;
                        break label3430;
                    }
                    break;
                case -1975568730:
                    if (var5.equals("copyToClipboard")) {
                        var19 = 120;
                        break label3430;
                    }
                    break;
                case -1966668787:
                    if (var5.equals("firebaseauthSignOutUser")) {
                        var19 = 215;
                        break label3430;
                    }
                    break;
                case -1937348542:
                    if (var5.equals("firebaseStartListen")) {
                        var19 = 216;
                        break label3430;
                    }
                    break;
                case -1922362317:
                    if (var5.equals("getExternalStorageDir")) {
                        var19 = 278;
                        break label3430;
                    }
                    break;
                case -1920517885:
                    if (var5.equals("setVarBoolean")) {
                        var19 = 3;
                        break label3430;
                    }
                    break;
                case -1919300188:
                    if (var5.equals("toNumber")) {
                        var19 = 67;
                        break label3430;
                    }
                    break;
                case -1910071024:
                    if (var5.equals("objectanimatorSetDuration")) {
                        var19 = 251;
                        break label3430;
                    }
                    break;
                case -1886802639:
                    if (var5.equals("soundpoolLoad")) {
                        var19 = 238;
                        break label3430;
                    }
                    break;
                case -1834369666:
                    if (var5.equals("setBitmapFileBrightness")) {
                        var19 = 290;
                        break label3430;
                    }
                    break;
                case -1812313351:
                    if (var5.equals("setColorFilter")) {
                        var19 = 117;
                        break label3430;
                    }
                    break;
                case -1778201036:
                    if (var5.equals("listSmoothScrollTo")) {
                        var19 = 166;
                        break label3430;
                    }
                    break;
                case -1776922004:
                    if (var5.equals("toString")) {
                        var19 = 72;
                        break label3430;
                    }
                    break;
                case -1749698255:
                    if (var5.equals("mediaplayerPause")) {
                        var19 = 228;
                        break label3430;
                    }
                    break;
                case -1747734390:
                    if (var5.equals("mediaplayerReset")) {
                        var19 = 232;
                        break label3430;
                    }
                    break;
                case -1746380899:
                    if (var5.equals("mediaplayerStart")) {
                        var19 = 227;
                        break label3430;
                    }
                    break;
                case -1718917155:
                    if (var5.equals("mediaplayerSeek")) {
                        var19 = 229;
                        break label3430;
                    }
                    break;
                case -1699631195:
                    if (var5.equals("isDrawerOpen")) {
                        var19 = 105;
                        break label3430;
                    }
                    break;
                case -1699349926:
                    if (var5.equals("objectanimatorSetRepeatMode")) {
                        var19 = 252;
                        break label3430;
                    }
                    break;
                case -1684072208:
                    if (var5.equals("intentSetData")) {
                        var19 = 123;
                        break label3430;
                    }
                    break;
                case -1679834825:
                    if (var5.equals("setTrackResource")) {
                        var19 = 242;
                        break label3430;
                    }
                    break;
                case -1678257956:
                    if (var5.equals("gridSetCustomViewData")) {
                        var19 = 160;
                        break label3430;
                    }
                    break;
                case -1666623936:
                    if (var5.equals("speechToTextShutdown")) {
                        var19 = 311;
                        break label3430;
                    }
                    break;
                case -1573371685:
                    if (var5.equals("stringJoin")) {
                        var19 = 58;
                        break label3430;
                    }
                    break;
                case -1541653284:
                    if (var5.equals("objectanimatorStart")) {
                        var19 = 255;
                        break label3430;
                    }
                    break;
                case -1530840255:
                    if (var5.equals("stringIndex")) {
                        var19 = 59;
                        break label3430;
                    }
                    break;
                case -1528850031:
                    if (var5.equals("startActivity")) {
                        var19 = 128;
                        break label3430;
                    }
                    break;
                case -1526161572:
                    if (var5.equals("setBgColor")) {
                        var19 = 113;
                        break label3430;
                    }
                    break;
                case -1513446476:
                    if (var5.equals("dialogCancelButton")) {
                        var19 = 224;
                        break label3430;
                    }
                    break;
                case -1512519571:
                    if (var5.equals("definedFunc")) {
                        var19 = 0;
                        break label3430;
                    }
                    break;
                case -1483954587:
                    if (var5.equals("fileutilisdir")) {
                        var19 = 272;
                        break label3430;
                    }
                    break;
                case -1477942289:
                    if (var5.equals("mediaplayerIsLooping")) {
                        var19 = 236;
                        break label3430;
                    }
                    break;
                case -1471049951:
                    if (var5.equals("fileutilwrite")) {
                        var19 = 265;
                        break label3430;
                    }
                    break;
                case -1440042085:
                    if (var5.equals("spnSetSelection")) {
                        var19 = 169;
                        break label3430;
                    }
                    break;
                case -1438040951:
                    if (var5.equals("seekBarGetMax")) {
                        var19 = 246;
                        break label3430;
                    }
                    break;
                case -1422112391:
                    if (var5.equals("bluetoothConnectIsBluetoothEnabled")) {
                        var19 = 318;
                        break label3430;
                    }
                    break;
                case -1405157727:
                    if (var5.equals("fileutilmakedir")) {
                        var19 = 270;
                        break label3430;
                    }
                    break;
                case -1385076635:
                    if (var5.equals("dialogShow")) {
                        var19 = 222;
                        break label3430;
                    }
                    break;
                case -1384861688:
                    if (var5.equals("getAtListInt")) {
                        var19 = 19;
                        break label3430;
                    }
                    break;
                case -1384858251:
                    if (var5.equals("getAtListMap")) {
                        var19 = 29;
                        break label3430;
                    }
                    break;
                case -1384851894:
                    if (var5.equals("getAtListStr")) {
                        var19 = 24;
                        break label3430;
                    }
                    break;
                case -1377080719:
                    if (var5.equals("decreaseInt")) {
                        var19 = 6;
                        break label3430;
                    }
                    break;
                case -1376608975:
                    if (var5.equals("calendarSetTime")) {
                        var19 = 140;
                        break label3430;
                    }
                    break;
                case -1361468284:
                    if (var5.equals("viewOnClick")) {
                        var19 = 104;
                        break label3430;
                    }
                    break;
                case -1348085287:
                    if (var5.equals("mapViewZoomIn")) {
                        var19 = 191;
                        break label3430;
                    }
                    break;
                case -1348084945:
                    if (var5.equals("mapViewZoomTo")) {
                        var19 = 190;
                        break label3430;
                    }
                    break;
                case -1304067438:
                    if (var5.equals("firebaseDelete")) {
                        var19 = 206;
                        break label3430;
                    }
                    break;
                case -1272546178:
                    if (var5.equals("dialogSetTitle")) {
                        var19 = 220;
                        break label3430;
                    }
                    break;
                case -1271141237:
                    if (var5.equals("clearList")) {
                        var19 = 37;
                        break label3430;
                    }
                    break;
                case -1249367264:
                    if (var5.equals("getArg")) {
                        var19 = 1;
                        break label3430;
                    }
                    break;
                case -1249347599:
                    if (var5.equals("getVar")) {
                        var19 = 2;
                        break label3430;
                    }
                    break;
                case -1217704075:
                    if (var5.equals("objectanimatorSetValue")) {
                        var19 = 249;
                        break label3430;
                    }
                    break;
                case -1206794099:
                    if (var5.equals("getLocationX")) {
                        var19 = 155;
                        break label3430;
                    }
                    break;
                case -1206794098:
                    if (var5.equals("getLocationY")) {
                        var19 = 156;
                        break label3430;
                    }
                    break;
                case -1195899442:
                    if (var5.equals("bluetoothConnectSendData")) {
                        var19 = 317;
                        break label3430;
                    }
                    break;
                case -1192544266:
                    if (var5.equals("ifElse")) {
                        var19 = 41;
                        break label3430;
                    }
                    break;
                case -1185284274:
                    if (var5.equals("gyroscopeStopListen")) {
                        var19 = 219;
                        break label3430;
                    }
                    break;
                case -1182878167:
                    if (var5.equals("firebaseauthGetUid")) {
                        var19 = 213;
                        break label3430;
                    }
                    break;
                case -1160374245:
                    if (var5.equals("bluetoothConnectReadyConnectionToUuid")) {
                        var19 = 313;
                        break label3430;
                    }
                    break;
                case -1149848189:
                    if (var5.equals("toStringFormat")) {
                        var19 = 74;
                        break label3430;
                    }
                    break;
                case -1149458632:
                    if (var5.equals("objectanimatorSetRepeatCount")) {
                        var19 = 253;
                        break label3430;
                    }
                    break;
                case -1143684675:
                    if (var5.equals("firebaseauthGetCurrentUser")) {
                        var19 = 212;
                        break label3430;
                    }
                    break;
                case -1139353316:
                    if (var5.equals("setListMap")) {
                        var19 = 30;
                        break label3430;
                    }
                    break;
                case -1137582698:
                    if (var5.equals("toLowerCase")) {
                        var19 = 71;
                        break label3430;
                    }
                    break;
                case -1123431291:
                    if (var5.equals("calnedarViewSetMaxDate")) {
                        var19 = 186;
                        break label3430;
                    }
                    break;
                case -1107376988:
                    if (var5.equals("webViewGoForward")) {
                        var19 = 177;
                        break label3430;
                    }
                    break;
                case -1106141754:
                    if (var5.equals("webViewCanGoBack")) {
                        var19 = 174;
                        break label3430;
                    }
                    break;
                case -1094491139:
                    if (var5.equals("seekBarSetMax")) {
                        var19 = 245;
                        break label3430;
                    }
                    break;
                case -1083547183:
                    if (var5.equals("spnSetCustomViewData")) {
                        var19 = 160;
                        break label3430;
                    }
                    break;
                case -1081400230:
                    if (var5.equals("mapGet")) {
                        var19 = 10;
                        break label3430;
                    }
                    break;
                case -1081391085:
                    if (var5.equals("mapPut")) {
                        var19 = 9;
                        break label3430;
                    }
                    break;
                case -1081250015:
                    if (var5.equals("mathPi")) {
                        var19 = 83;
                        break label3430;
                    }
                    break;
                case -1069525505:
                    if (var5.equals("pagerSetCustomViewData")) {
                        var19 = 160;
                        break label3430;
                    }
                    break;
                case -1063598745:
                    if (var5.equals("resizeBitmapFileRetainRatio")) {
                        var19 = 281;
                        break label3430;
                    }
                    break;
                case -1043233275:
                    if (var5.equals("mediaplayerGetDuration")) {
                        var19 = 231;
                        break label3430;
                    }
                    break;
                case -1033658254:
                    if (var5.equals("mathGetDisplayWidth")) {
                        var19 = 81;
                        break label3430;
                    }
                    break;
                case -1021852352:
                    if (var5.equals("objectanimatorCancel")) {
                        var19 = 256;
                        break label3430;
                    }
                    break;
                case -1007787615:
                    if (var5.equals("mediaplayerSetLooping")) {
                        var19 = 235;
                        break label3430;
                    }
                    break;
                case -996870276:
                    if (var5.equals("insertMapToList")) {
                        var19 = 33;
                        break label3430;
                    }
                    break;
                case -995908985:
                    if (var5.equals("soundpoolCreate")) {
                        var19 = 237;
                        break label3430;
                    }
                    break;
                case -941420147:
                    if (var5.equals("fileSetFileName")) {
                        var19 = 130;
                        break label3430;
                    }
                    break;
                case -938285885:
                    if (var5.equals("random")) {
                        var19 = 56;
                        break label3430;
                    }
                    break;
                case -934531685:
                    if (var5.equals("repeat")) {
                        var19 = 39;
                        break label3430;
                    }
                    break;
                case -918173448:
                    if (var5.equals("listGetCheckedPosition")) {
                        var19 = 163;
                        break label3430;
                    }
                    break;
                case -917343271:
                    if (var5.equals("getJpegRotate")) {
                        var19 = 292;
                        break label3430;
                    }
                    break;
                case -911199919:
                    if (var5.equals("objectanimatorSetProperty")) {
                        var19 = 248;
                        break label3430;
                    }
                    break;
                case -903177036:
                    if (var5.equals("resizeBitmapFileWithRoundedBorder")) {
                        var19 = 284;
                        break label3430;
                    }
                    break;
                case -883988307:
                    if (var5.equals("dialogSetMessage")) {
                        var19 = 221;
                        break label3430;
                    }
                    break;
                case -869293886:
                    if (var5.equals("finishActivity")) {
                        var19 = 129;
                        break label3430;
                    }
                    break;
                case -854558288:
                    if (var5.equals("setVisible")) {
                        var19 = 141;
                        break label3430;
                    }
                    break;
                case -853550561:
                    if (var5.equals("timerCancel")) {
                        var19 = 202;
                        break label3430;
                    }
                    break;
                case -831887360:
                    if (var5.equals("textToSpeechShutdown")) {
                        var19 = 308;
                        break label3430;
                    }
                    break;
                case -733318734:
                    if (var5.equals("strToListMap")) {
                        var19 = 78;
                        break label3430;
                    }
                    break;
                case -697616870:
                    if (var5.equals("camerastarttakepicture")) {
                        var19 = 294;
                        break label3430;
                    }
                    break;
                case -677662361:
                    if (var5.equals("forever")) {
                        var19 = 38;
                        break label3430;
                    }
                    break;
                case -668992194:
                    if (var5.equals("stringReplaceAll")) {
                        var19 = 66;
                        break label3430;
                    }
                    break;
                case -664474111:
                    if (var5.equals("intentSetFlags")) {
                        var19 = 126;
                        break label3430;
                    }
                    break;
                case -649691581:
                    if (var5.equals("objectanimatorSetInterpolator")) {
                        var19 = 254;
                        break label3430;
                    }
                    break;
                case -636363854:
                    if (var5.equals("webViewGetUrl")) {
                        var19 = 172;
                        break label3430;
                    }
                    break;
                case -628607128:
                    if (var5.equals("webViewGoBack")) {
                        var19 = 176;
                        break label3430;
                    }
                    break;
                case -621198621:
                    if (var5.equals("speechToTextStartListening")) {
                        var19 = 309;
                        break label3430;
                    }
                    break;
                case -602241037:
                    if (var5.equals("fileutilcopy")) {
                        var19 = 266;
                        break label3430;
                    }
                    break;
                case -601942961:
                    if (var5.equals("fileutilmove")) {
                        var19 = 267;
                        break label3430;
                    }
                    break;
                case -601804268:
                    if (var5.equals("fileutilread")) {
                        var19 = 264;
                        break label3430;
                    }
                    break;
                case -578987803:
                    if (var5.equals("setChecked")) {
                        var19 = 157;
                        break label3430;
                    }
                    break;
                case -509946902:
                    if (var5.equals("spnRefresh")) {
                        var19 = 168;
                        break label3430;
                    }
                    break;
                case -439342016:
                    if (var5.equals("webViewClearHistory")) {
                        var19 = 179;
                        break label3430;
                    }
                    break;
                case -437272040:
                    if (var5.equals("bluetoothConnectGetRandomUuid")) {
                        var19 = 322;
                        break label3430;
                    }
                    break;
                case -425293664:
                    if (var5.equals("setClickable")) {
                        var19 = 142;
                        break label3430;
                    }
                    break;
                case -418212114:
                    if (var5.equals("firebaseGetChildren")) {
                        var19 = 207;
                        break label3430;
                    }
                    break;
                case -411705840:
                    if (var5.equals("fileSetData")) {
                        var19 = 132;
                        break label3430;
                    }
                    break;
                case -399551817:
                    if (var5.equals("toUpperCase")) {
                        var19 = 70;
                        break label3430;
                    }
                    break;
                case -390304998:
                    if (var5.equals("mapViewAddMarker")) {
                        var19 = 193;
                        break label3430;
                    }
                    break;
                case -356866884:
                    if (var5.equals("webViewSetCacheMode")) {
                        var19 = 173;
                        break label3430;
                    }
                    break;
                case -353129373:
                    if (var5.equals("calendarDiff")) {
                        var19 = 138;
                        break label3430;
                    }
                    break;
                case -329562760:
                    if (var5.equals("insertListInt")) {
                        var19 = 18;
                        break label3430;
                    }
                    break;
                case -329559323:
                    if (var5.equals("insertListMap")) {
                        var19 = 28;
                        break label3430;
                    }
                    break;
                case -329552966:
                    if (var5.equals("insertListStr")) {
                        var19 = 23;
                        break label3430;
                    }
                    break;
                case -322651344:
                    if (var5.equals("stringEquals")) {
                        var19 = 62;
                        break label3430;
                    }
                    break;
                case -283328259:
                    if (var5.equals("intentPutExtra")) {
                        var19 = 125;
                        break label3430;
                    }
                    break;
                case -258774775:
                    if (var5.equals("closeDrawer")) {
                        var19 = 107;
                        break label3430;
                    }
                    break;
                case -247015294:
                    if (var5.equals("mediaplayerRelease")) {
                        var19 = 233;
                        break label3430;
                    }
                    break;
                case -208762465:
                    if (var5.equals("toStringWithDecimal")) {
                        var19 = 73;
                        break label3430;
                    }
                    break;
                case -189292433:
                    if (var5.equals("stringSub")) {
                        var19 = 61;
                        break label3430;
                    }
                    break;
                case -152473824:
                    if (var5.equals("firebaseauthIsLoggedIn")) {
                        var19 = 211;
                        break label3430;
                    }
                    break;
                case -149850417:
                    if (var5.equals("fileutilisexist")) {
                        var19 = 269;
                        break label3430;
                    }
                    break;
                case -133532073:
                    if (var5.equals("stringLength")) {
                        var19 = 57;
                        break label3430;
                    }
                    break;
                case -96313603:
                    if (var5.equals("containListInt")) {
                        var19 = 21;
                        break label3430;
                    }
                    break;
                case -96310166:
                    if (var5.equals("containListMap")) {
                        var19 = 31;
                        break label3430;
                    }
                    break;
                case -96303809:
                    if (var5.equals("containListStr")) {
                        var19 = 26;
                        break label3430;
                    }
                    break;
                case -83301935:
                    if (var5.equals("webViewZoomIn")) {
                        var19 = 181;
                        break label3430;
                    }
                    break;
                case -83186725:
                    if (var5.equals("openDrawer")) {
                        var19 = 106;
                        break label3430;
                    }
                    break;
                case -75125341:
                    if (var5.equals("getText")) {
                        var19 = 112;
                        break label3430;
                    }
                    break;
                case -60494417:
                    if (var5.equals("vibratorAction")) {
                        var19 = 199;
                        break label3430;
                    }
                    break;
                case -60048101:
                    if (var5.equals("firebaseauthResetPassword")) {
                        var19 = 214;
                        break label3430;
                    }
                    break;
                case -24451690:
                    if (var5.equals("dialogOkButton")) {
                        var19 = 223;
                        break label3430;
                    }
                    break;
                case -14362103:
                    if (var5.equals("bluetoothConnectIsBluetoothActivated")) {
                        var19 = 319;
                        break label3430;
                    }
                    break;
                case -10599306:
                    if (var5.equals("firebaseauthCreateUser")) {
                        var19 = 208;
                        break label3430;
                    }
                    break;
                case -9742826:
                    if (var5.equals("firebaseGetPushKey")) {
                        var19 = 205;
                        break label3430;
                    }
                    break;
                case 37:
                    if (var5.equals("%")) {
                        var19 = 50;
                        break label3430;
                    }
                    break;
                case 42:
                    if (var5.equals("*")) {
                        var19 = 48;
                        break label3430;
                    }
                    break;
                case 43:
                    if (var5.equals("+")) {
                        var19 = 46;
                        break label3430;
                    }
                    break;
                case 45:
                    if (var5.equals("-")) {
                        var19 = 47;
                        break label3430;
                    }
                    break;
                case 47:
                    if (var5.equals("/")) {
                        var19 = 49;
                        break label3430;
                    }
                    break;
                case 60:
                    if (var5.equals("<")) {
                        var19 = 52;
                        break label3430;
                    }
                    break;
                case 61:
                    if (var5.equals("=")) {
                        var19 = 53;
                        break label3430;
                    }
                    break;
                case 62:
                    if (var5.equals(">")) {
                        var19 = 51;
                        break label3430;
                    }
                    break;
                case 1216:
                    if (var5.equals("&&")) {
                        var19 = 54;
                        break label3430;
                    }
                    break;
                case 3357:
                    if (var5.equals("if")) {
                        var19 = 40;
                        break label3430;
                    }
                    break;
                case 3968:
                    if (var5.equals("||")) {
                        var19 = 55;
                        break label3430;
                    }
                    break;
                case 109267:
                    if (var5.equals("not")) {
                        var19 = 45;
                        break label3430;
                    }
                    break;
                case 3568674:
                    if (var5.equals("trim")) {
                        var19 = 69;
                        break label3430;
                    }
                    break;
                case 3569038:
                    if (var5.equals("true")) {
                        var19 = 43;
                        break label3430;
                    }
                    break;
                case 8255701:
                    if (var5.equals("calendarFormat")) {
                        var19 = 137;
                        break label3430;
                    }
                    break;
                case 16308074:
                    if (var5.equals("resizeBitmapFileToCircle")) {
                        var19 = 283;
                        break label3430;
                    }
                    break;
                case 25469951:
                    if (var5.equals("bluetoothConnectActivateBluetooth")) {
                        var19 = 320;
                        break label3430;
                    }
                    break;
                case 27679870:
                    if (var5.equals("calendarGetNow")) {
                        var19 = 134;
                        break label3430;
                    }
                    break;
                case 56167279:
                    if (var5.equals("setBitmapFileContrast")) {
                        var19 = 291;
                        break label3430;
                    }
                    break;
                case 61585857:
                    if (var5.equals("firebasePush")) {
                        var19 = 204;
                        break label3430;
                    }
                    break;
                case 94001407:
                    if (var5.equals("break")) {
                        var19 = 42;
                        break label3430;
                    }
                    break;
                case 97196323:
                    if (var5.equals("false")) {
                        var19 = 44;
                        break label3430;
                    }
                    break;
                case 103668285:
                    if (var5.equals("mathE")) {
                        var19 = 84;
                        break label3430;
                    }
                    break;
                case 125431087:
                    if (var5.equals("speechToTextStopListening")) {
                        var19 = 310;
                        break label3430;
                    }
                    break;
                case 134874756:
                    if (var5.equals("listSetCustomViewData")) {
                        var19 = 160;
                        break label3430;
                    }
                    break;
                case 152967761:
                    if (var5.equals("mapClear")) {
                        var19 = 14;
                        break label3430;
                    }
                    break;
                case 163812602:
                    if (var5.equals("cropBitmapFileFromCenter")) {
                        var19 = 285;
                        break label3430;
                    }
                    break;
                case 168740282:
                    if (var5.equals("mapToStr")) {
                        var19 = 77;
                        break label3430;
                    }
                    break;
                case 182549637:
                    if (var5.equals("setEnable")) {
                        var19 = 108;
                        break label3430;
                    }
                    break;
                case 207764385:
                    if (var5.equals("calendarViewGetDate")) {
                        var19 = 183;
                        break label3430;
                    }
                    break;
                case 255417137:
                    if (var5.equals("adViewLoadAd")) {
                        var19 = 187;
                        break label3430;
                    }
                    break;
                case 262073061:
                    if (var5.equals("bluetoothConnectReadyConnection")) {
                        var19 = 312;
                        break label3430;
                    }
                    break;
                case 276674391:
                    if (var5.equals("mapViewMoveCamera")) {
                        var19 = 189;
                        break label3430;
                    }
                    break;
                case 297379706:
                    if (var5.equals("textToSpeechSetSpeechRate")) {
                        var19 = 304;
                        break label3430;
                    }
                    break;
                case 300372142:
                    if (var5.equals("mathAcos")) {
                        var19 = 97;
                        break label3430;
                    }
                    break;
                case 300387327:
                    if (var5.equals("mathAsin")) {
                        var19 = 96;
                        break label3430;
                    }
                    break;
                case 300388040:
                    if (var5.equals("mathAtan")) {
                        var19 = 98;
                        break label3430;
                    }
                    break;
                case 300433453:
                    if (var5.equals("mathCeil")) {
                        var19 = 91;
                        break label3430;
                    }
                    break;
                case 300921928:
                    if (var5.equals("mathSqrt")) {
                        var19 = 88;
                        break label3430;
                    }
                    break;
                case 317453636:
                    if (var5.equals("textToSpeechIsSpeaking")) {
                        var19 = 306;
                        break label3430;
                    }
                    break;
                case 342026220:
                    if (var5.equals("interstitialadShow")) {
                        var19 = 260;
                        break label3430;
                    }
                    break;
                case 348377823:
                    if (var5.equals("soundpoolStreamPlay")) {
                        var19 = 239;
                        break label3430;
                    }
                    break;
                case 348475309:
                    if (var5.equals("soundpoolStreamStop")) {
                        var19 = 240;
                        break label3430;
                    }
                    break;
                case 362605827:
                    if (var5.equals("recyclerSetCustomViewData")) {
                        var19 = 160;
                        break label3430;
                    }
                    break;
                case 389111867:
                    if (var5.equals("spnSetData")) {
                        var19 = 167;
                        break label3430;
                    }
                    break;
                case 397166713:
                    if (var5.equals("getEnable")) {
                        var19 = 109;
                        break label3430;
                    }
                    break;
                case 401012285:
                    if (var5.equals("getTranslationX")) {
                        var19 = 148;
                        break label3430;
                    }
                    break;
                case 401012286:
                    if (var5.equals("getTranslationY")) {
                        var19 = 150;
                        break label3430;
                    }
                    break;
                case 404247683:
                    if (var5.equals("calendarAdd")) {
                        var19 = 135;
                        break label3430;
                    }
                    break;
                case 404265028:
                    if (var5.equals("calendarSet")) {
                        var19 = 136;
                        break label3430;
                    }
                    break;
                case 442768763:
                    if (var5.equals("mapGetAllKeys")) {
                        var19 = 16;
                        break label3430;
                    }
                    break;
                case 463560551:
                    if (var5.equals("mapContainKey")) {
                        var19 = 11;
                        break label3430;
                    }
                    break;
                case 463594049:
                    if (var5.equals("objectanimatorSetFromTo")) {
                        var19 = 250;
                        break label3430;
                    }
                    break;
                case 470160234:
                    if (var5.equals("fileutilGetLastSegmentPath")) {
                        var19 = 277;
                        break label3430;
                    }
                    break;
                case 475815924:
                    if (var5.equals("setTextColor")) {
                        var19 = 115;
                        break label3430;
                    }
                    break;
                case 481850295:
                    if (var5.equals("resizeBitmapFileToSquare")) {
                        var19 = 282;
                        break label3430;
                    }
                    break;
                case 490702942:
                    if (var5.equals("filepickerstartpickfiles")) {
                        var19 = 293;
                        break label3430;
                    }
                    break;
                case 501171279:
                    if (var5.equals("mathToDegree")) {
                        var19 = 103;
                        break label3430;
                    }
                    break;
                case 530759231:
                    if (var5.equals("progressBarSetIndeterminate")) {
                        var19 = 302;
                        break label3430;
                    }
                    break;
                case 548860462:
                    if (var5.equals("webViewClearCache")) {
                        var19 = 178;
                        break label3430;
                    }
                    break;
                case 556217437:
                    if (var5.equals("setRotate")) {
                        var19 = 143;
                        break label3430;
                    }
                    break;
                case 571046965:
                    if (var5.equals("scaleBitmapFile")) {
                        var19 = 287;
                        break label3430;
                    }
                    break;
                case 573208400:
                    if (var5.equals("setScaleX")) {
                        var19 = 151;
                        break label3430;
                    }
                    break;
                case 573208401:
                    if (var5.equals("setScaleY")) {
                        var19 = 153;
                        break label3430;
                    }
                    break;
                case 573295520:
                    if (var5.equals("listGetCheckedCount")) {
                        var19 = 165;
                        break label3430;
                    }
                    break;
                case 601235430:
                    if (var5.equals("currentTime")) {
                        var19 = 68;
                        break label3430;
                    }
                    break;
                case 610313513:
                    if (var5.equals("getMapInList")) {
                        var19 = 34;
                        break label3430;
                    }
                    break;
                case 615286641:
                    if (var5.equals("dialogNeutralButton")) {
                        var19 = 225;
                        break label3430;
                    }
                    break;
                case 657721930:
                    if (var5.equals("setVarInt")) {
                        var19 = 4;
                        break label3430;
                    }
                    break;
                case 683193060:
                    if (var5.equals("bluetoothConnectStartConnection")) {
                        var19 = 314;
                        break label3430;
                    }
                    break;
                case 725249532:
                    if (var5.equals("intentSetAction")) {
                        var19 = 122;
                        break label3430;
                    }
                    break;
                case 726487524:
                    if (var5.equals("mathFloor")) {
                        var19 = 92;
                        break label3430;
                    }
                    break;
                case 726877492:
                    if (var5.equals("mapViewSetMarkerIcon")) {
                        var19 = 197;
                        break label3430;
                    }
                    break;
                case 726887785:
                    if (var5.equals("mapViewSetMarkerInfo")) {
                        var19 = 194;
                        break label3430;
                    }
                    break;
                case 732108347:
                    if (var5.equals("mathLog10")) {
                        var19 = 101;
                        break label3430;
                    }
                    break;
                case 737664870:
                    if (var5.equals("mathRound")) {
                        var19 = 90;
                        break label3430;
                    }
                    break;
                case 738846120:
                    if (var5.equals("textToSpeechSetPitch")) {
                        var19 = 303;
                        break label3430;
                    }
                    break;
                case 747168008:
                    if (var5.equals("mapCreateNew")) {
                        var19 = 8;
                        break label3430;
                    }
                    break;
                case 754442829:
                    if (var5.equals("increaseInt")) {
                        var19 = 5;
                        break label3430;
                    }
                    break;
                case 762282303:
                    if (var5.equals("indexListInt")) {
                        var19 = 20;
                        break label3430;
                    }
                    break;
                case 762292097:
                    if (var5.equals("indexListStr")) {
                        var19 = 25;
                        break label3430;
                    }
                    break;
                case 770834513:
                    if (var5.equals("getRotate")) {
                        var19 = 144;
                        break label3430;
                    }
                    break;
                case 787825476:
                    if (var5.equals("getScaleX")) {
                        var19 = 152;
                        break label3430;
                    }
                    break;
                case 787825477:
                    if (var5.equals("getScaleY")) {
                        var19 = 154;
                        break label3430;
                    }
                    break;
                case 797861524:
                    if (var5.equals("addMapToList")) {
                        var19 = 32;
                        break label3430;
                    }
                    break;
                case 836692861:
                    if (var5.equals("mapSize")) {
                        var19 = 13;
                        break label3430;
                    }
                    break;
                case 840973386:
                    if (var5.equals("mathAbs")) {
                        var19 = 89;
                        break label3430;
                    }
                    break;
                case 840975711:
                    if (var5.equals("mathCos")) {
                        var19 = 94;
                        break label3430;
                    }
                    break;
                case 840977909:
                    if (var5.equals("mathExp")) {
                        var19 = 99;
                        break label3430;
                    }
                    break;
                case 840984348:
                    if (var5.equals("mathLog")) {
                        var19 = 100;
                        break label3430;
                    }
                    break;
                case 840984892:
                    if (var5.equals("mathMax")) {
                        var19 = 87;
                        break label3430;
                    }
                    break;
                case 840985130:
                    if (var5.equals("mathMin")) {
                        var19 = 86;
                        break label3430;
                    }
                    break;
                case 840988208:
                    if (var5.equals("mathPow")) {
                        var19 = 85;
                        break label3430;
                    }
                    break;
                case 840990896:
                    if (var5.equals("mathSin")) {
                        var19 = 93;
                        break label3430;
                    }
                    break;
                case 840991609:
                    if (var5.equals("mathTan")) {
                        var19 = 95;
                        break label3430;
                    }
                    break;
                case 845089750:
                    if (var5.equals("setVarString")) {
                        var19 = 7;
                        break label3430;
                    }
                    break;
                case 848786445:
                    if (var5.equals("objectanimatorSetTarget")) {
                        var19 = 247;
                        break label3430;
                    }
                    break;
                case 858248741:
                    if (var5.equals("calendarGetTime")) {
                        var19 = 139;
                        break label3430;
                    }
                    break;
                case 898187172:
                    if (var5.equals("mathToRadian")) {
                        var19 = 102;
                        break label3430;
                    }
                    break;
                case 932259189:
                    if (var5.equals("setBgResource")) {
                        var19 = 114;
                        break label3430;
                    }
                    break;
                case 937017988:
                    if (var5.equals("gyroscopeStartListen")) {
                        var19 = 218;
                        break label3430;
                    }
                    break;
                case 948234497:
                    if (var5.equals("webViewStopLoading")) {
                        var19 = 180;
                        break label3430;
                    }
                    break;
                case 950609198:
                    if (var5.equals("setBitmapFileColorFilter")) {
                        var19 = 289;
                        break label3430;
                    }
                    break;
                case 1053179400:
                    if (var5.equals("mapViewSetMarkerColor")) {
                        var19 = 196;
                        break label3430;
                    }
                    break;
                case 1068548733:
                    if (var5.equals("mathGetDip")) {
                        var19 = 80;
                        break label3430;
                    }
                    break;
                case 1086207657:
                    if (var5.equals("fileutildelete")) {
                        var19 = 268;
                        break label3430;
                    }
                    break;
                case 1088879149:
                    if (var5.equals("setHintTextColor")) {
                        var19 = 298;
                        break label3430;
                    }
                    break;
                case 1090517587:
                    if (var5.equals("getPackageDataDir")) {
                        var19 = 279;
                        break label3430;
                    }
                    break;
                case 1102670563:
                    if (var5.equals("requestnetworkSetHeaders")) {
                        var19 = 300;
                        break label3430;
                    }
                    break;
                case 1129709718:
                    if (var5.equals("setImageUrl")) {
                        var19 = 296;
                        break label3430;
                    }
                    break;
                case 1142897724:
                    if (var5.equals("firebaseauthSignInUser")) {
                        var19 = 209;
                        break label3430;
                    }
                    break;
                case 1156598140:
                    if (var5.equals("fileutilEndsWith")) {
                        var19 = 276;
                        break label3430;
                    }
                    break;
                case 1159035162:
                    if (var5.equals("mapViewZoomOut")) {
                        var19 = 192;
                        break label3430;
                    }
                    break;
                case 1160674468:
                    if (var5.equals("lengthList")) {
                        var19 = 36;
                        break label3430;
                    }
                    break;
                case 1162069698:
                    if (var5.equals("setThumbResource")) {
                        var19 = 241;
                        break label3430;
                    }
                    break;
                case 1179719371:
                    if (var5.equals("stringLastIndex")) {
                        var19 = 60;
                        break label3430;
                    }
                    break;
                case 1187505507:
                    if (var5.equals("stringReplace")) {
                        var19 = 64;
                        break label3430;
                    }
                    break;
                case 1216249183:
                    if (var5.equals("firebasestorageDelete")) {
                        var19 = 263;
                        break label3430;
                    }
                    break;
                case 1219071185:
                    if (var5.equals("firebasestorageUploadFile")) {
                        var19 = 261;
                        break label3430;
                    }
                    break;
                case 1219299503:
                    if (var5.equals("objectanimatorIsRunning")) {
                        var19 = 257;
                        break label3430;
                    }
                    break;
                case 1220078450:
                    if (var5.equals("addSourceDirectly")) {
                        var19 = 75;
                        break label3430;
                    }
                    break;
                case 1236956449:
                    if (var5.equals("mediaplayerCreate")) {
                        var19 = 226;
                        break label3430;
                    }
                    break;
                case 1240510514:
                    if (var5.equals("intentSetScreen")) {
                        var19 = 124;
                        break label3430;
                    }
                    break;
                case 1242107556:
                    if (var5.equals("fileutilisfile")) {
                        var19 = 273;
                        break label3430;
                    }
                    break;
                case 1252547704:
                    if (var5.equals("listMapToStr")) {
                        var19 = 79;
                        break label3430;
                    }
                    break;
                case 1280029577:
                    if (var5.equals("requestFocus")) {
                        var19 = 118;
                        break label3430;
                    }
                    break;
                case 1303367340:
                    if (var5.equals("textToSpeechStop")) {
                        var19 = 307;
                        break label3430;
                    }
                    break;
                case 1305932583:
                    if (var5.equals("spnGetSelection")) {
                        var19 = 170;
                        break label3430;
                    }
                    break;
                case 1311764809:
                    if (var5.equals("setTranslationX")) {
                        var19 = 147;
                        break label3430;
                    }
                    break;
                case 1311764810:
                    if (var5.equals("setTranslationY")) {
                        var19 = 149;
                        break label3430;
                    }
                    break;
                case 1313527577:
                    if (var5.equals("setTypeface")) {
                        var19 = 111;
                        break label3430;
                    }
                    break;
                case 1315302372:
                    if (var5.equals("fileutillength")) {
                        var19 = 274;
                        break label3430;
                    }
                    break;
                case 1330354473:
                    if (var5.equals("firebaseauthSignInAnonymously")) {
                        var19 = 210;
                        break label3430;
                    }
                    break;
                case 1343794064:
                    if (var5.equals("listSetItemChecked")) {
                        var19 = 162;
                        break label3430;
                    }
                    break;
                case 1348133645:
                    if (var5.equals("stringReplaceFirst")) {
                        var19 = 65;
                        break label3430;
                    }
                    break;
                case 1387622940:
                    if (var5.equals("setAlpha")) {
                        var19 = 145;
                        break label3430;
                    }
                    break;
                case 1395026457:
                    if (var5.equals("setImage")) {
                        var19 = 116;
                        break label3430;
                    }
                    break;
                case 1397501021:
                    if (var5.equals("listRefresh")) {
                        var19 = 161;
                        break label3430;
                    }
                    break;
                case 1405084438:
                    if (var5.equals("setTitle")) {
                        var19 = 121;
                        break label3430;
                    }
                    break;
                case 1410284340:
                    if (var5.equals("seekBarSetProgress")) {
                        var19 = 243;
                        break label3430;
                    }
                    break;
                case 1431171391:
                    if (var5.equals("mapRemoveKey")) {
                        var19 = 12;
                        break label3430;
                    }
                    break;
                case 1437288110:
                    if (var5.equals("getPublicDir")) {
                        var19 = 280;
                        break label3430;
                    }
                    break;
                case 1470831563:
                    if (var5.equals("intentGetString")) {
                        var19 = 127;
                        break label3430;
                    }
                    break;
                case 1498864168:
                    if (var5.equals("seekBarGetProgress")) {
                        var19 = 244;
                        break label3430;
                    }
                    break;
                case 1601394299:
                    if (var5.equals("listGetCheckedPositions")) {
                        var19 = 164;
                        break label3430;
                    }
                    break;
                case 1633341847:
                    if (var5.equals("timerAfter")) {
                        var19 = 200;
                        break label3430;
                    }
                    break;
                case 1635356258:
                    if (var5.equals("requestnetworkStartRequestNetwork")) {
                        var19 = 301;
                        break label3430;
                    }
                    break;
                case 1637498582:
                    if (var5.equals("timerEvery")) {
                        var19 = 201;
                        break label3430;
                    }
                    break;
                case 1695890133:
                    if (var5.equals("fileutilStartsWith")) {
                        var19 = 275;
                        break label3430;
                    }
                    break;
                case 1712613410:
                    if (var5.equals("webViewZoomOut")) {
                        var19 = 182;
                        break label3430;
                    }
                    break;
                case 1749552744:
                    if (var5.equals("textToSpeechSpeak")) {
                        var19 = 305;
                        break label3430;
                    }
                    break;
                case 1764351209:
                    if (var5.equals("deleteList")) {
                        var19 = 35;
                        break label3430;
                    }
                    break;
                case 1775620400:
                    if (var5.equals("strToMap")) {
                        var19 = 76;
                        break label3430;
                    }
                    break;
                case 1779174257:
                    if (var5.equals("getChecked")) {
                        var19 = 158;
                        break label3430;
                    }
                    break;
                case 1792552710:
                    if (var5.equals("rotateBitmapFile")) {
                        var19 = 286;
                        break label3430;
                    }
                    break;
                case 1814870108:
                    if (var5.equals("doToast")) {
                        var19 = 119;
                        break label3430;
                    }
                    break;
                case 1820536363:
                    if (var5.equals("interstitialadCreate")) {
                        var19 = 258;
                        break label3430;
                    }
                    break;
                case 1823151876:
                    if (var5.equals("fileGetData")) {
                        var19 = 131;
                        break label3430;
                    }
                    break;
                case 1848365301:
                    if (var5.equals("mapViewSetMapType")) {
                        var19 = 188;
                        break label3430;
                    }
                    break;
                case 1873103950:
                    if (var5.equals("locationManagerRemoveUpdates")) {
                        var19 = 324;
                        break label3430;
                    }
                    break;
                case 1883337723:
                    if (var5.equals("mathGetDisplayHeight")) {
                        var19 = 82;
                        break label3430;
                    }
                    break;
                case 1885231494:
                    if (var5.equals("webViewCanGoForward")) {
                        var19 = 175;
                        break label3430;
                    }
                    break;
                case 1908132964:
                    if (var5.equals("mapViewSetMarkerPosition")) {
                        var19 = 195;
                        break label3430;
                    }
                    break;
                case 1908582864:
                    if (var5.equals("firebaseStopListen")) {
                        var19 = 217;
                        break label3430;
                    }
                    break;
                case 1923980937:
                    if (var5.equals("requestnetworkSetParams")) {
                        var19 = 299;
                        break label3430;
                    }
                    break;
                case 1941634330:
                    if (var5.equals("firebaseAdd")) {
                        var19 = 203;
                        break label3430;
                    }
                    break;
                case 1948735400:
                    if (var5.equals("getAlpha")) {
                        var19 = 146;
                        break label3430;
                    }
                    break;
                case 1964823036:
                    if (var5.equals("bluetoothConnectStopConnection")) {
                        var19 = 316;
                        break label3430;
                    }
                    break;
                case 1973523807:
                    if (var5.equals("mediaplayerIsPlaying")) {
                        var19 = 234;
                        break label3430;
                    }
                    break;
                case 1974249461:
                    if (var5.equals("skewBitmapFile")) {
                        var19 = 288;
                        break label3430;
                    }
                    break;
                case 1976325370:
                    if (var5.equals("setImageFilePath")) {
                        var19 = 295;
                        break label3430;
                    }
                    break;
                case 1984630281:
                    if (var5.equals("setHint")) {
                        var19 = 297;
                        break label3430;
                    }
                    break;
                case 1984984239:
                    if (var5.equals("setText")) {
                        var19 = 110;
                        break label3430;
                    }
                    break;
                case 2017929665:
                    if (var5.equals("calendarViewSetMinDate")) {
                        var19 = 185;
                        break label3430;
                    }
                    break;
                case 2075310296:
                    if (var5.equals("interstitialadLoadAd")) {
                        var19 = 259;
                        break label3430;
                    }
                    break;
                case 2090179216:
                    if (var5.equals("addListInt")) {
                        var19 = 17;
                        break label3430;
                    }
                    break;
                case 2090182653:
                    if (var5.equals("addListMap")) {
                        var19 = 27;
                        break label3430;
                    }
                    break;
                case 2090189010:
                    if (var5.equals("addListStr")) {
                        var19 = 22;
                        break label3430;
                    }
                    break;
                case 2127377128:
                    if (var5.equals("mediaplayerGetCurrent")) {
                        var19 = 230;
                        break label3430;
                    }
                    break;
                case 2130649194:
                    if (var5.equals("bluetoothConnectGetPairedDevices")) {
                        var19 = 321;
                        break label3430;
                    }
                    break;
                case 2138225950:
                    if (var5.equals("locationManagerRequestLocationUpdates")) {
                        var19 = 323;
                        break label3430;
                    }
                default:
                    if (var5.equals("getResStr")) {
                        var19 = -2;
                        break label3430;
                    }
            }

            var19 = -1;
        }

        String var9;
        String var18;
        label3539: {
            var18 = "\"\"";
            var5 = "0";
            var9 = "";
            String var10;
            String var11;
            String var12;
            String var16;
            StringBuilder var24;
            StringBuilder var25;
            StringBuilder var26;
            StringBuilder var27;
            switch (var19) {
                case -2:
                    var5 = "getString(R.string." + blockBean.spec + ")";
                    var18 = var9;
                    break label3539;
                case 0:
                    if (blockBean.parameters.size() <= 0) {
                        var7 = blockBean.spec.indexOf(" ");
                        if (var7 < 0) {
                            var26 = new StringBuilder();
                            var26.append("_");
                            var26.append(blockBean.spec);
                            var26.append("()");
                            var5 = blockBean.type;
                            var26.append(ReturnMoreblockManager.getMbEnd(var5));
                            var18 = var26.toString();
                        } else {
                            var26 = new StringBuilder();
                            var26.append("_");
                            var26.append(blockBean.spec.substring(0, var7));
                            var26.append("()");
                            var5 = blockBean.type;
                            var26.append(ReturnMoreblockManager.getMbEnd(var5));
                            var18 = var26.toString();
                        }
                    } else {
                        var7 = blockBean.spec.indexOf(" ");
                        var5 = blockBean.spec.substring(0, var7);
                        var26 = new StringBuilder();
                        var26.append("_");
                        var26.append(var5);
                        var26.append("(");
                        var5 = var26.toString();
                        var7 = 0;
                        boolean var17 = false;

                        for(boolean var13 = true; var7 < var3.size(); var13 = false) {
                            var18 = var5;
                            if (!var13) {
                                var26 = new StringBuilder();
                                var26.append(var5);
                                var26.append(", ");
                                var18 = var26.toString();
                            }

                            var9 = (String)var3.get(var7);
                            if (var9.length() <= 0) {
                                Gx var20 = (Gx)blockBean.getParamClassInfo().get(var7);
                                if (var20.b("boolean")) {
                                    var27 = new StringBuilder();
                                    var27.append(var18);
                                    var27.append("true");
                                    var5 = var27.toString();
                                } else if (var20.b("double")) {
                                    var27 = new StringBuilder();
                                    var27.append(var18);
                                    var27.append("0");
                                    var5 = var27.toString();
                                } else {
                                    var5 = var18;
                                    if (var20.b("String")) {
                                        var17 = true;
                                        var5 = var18;
                                    }
                                }
                            } else {
                                var27 = new StringBuilder();
                                var27.append(var18);
                                var27.append(var9);
                                var5 = var27.toString();
                            }

                            ++var7;
                        }

                        var26 = new StringBuilder();
                        var26.append(var5);
                        var26.append(")");
                        var9 = blockBean.type;
                        var26.append(ReturnMoreblockManager.getMbEnd(var9));
                        var18 = var26.toString();
                        var5 = var9;
                        if (var17) {
                            var18 = var9;
                            break;
                        }
                    }

                    var9 = var18;
                    var18 = var5;
                    var5 = var9;
                    break label3539;
                case 1:
                    var5 = blockBean.spec;
                    var26 = new StringBuilder();
                    var26.append("_");
                    var26.append(var5);
                    var5 = var26.toString();
                    var18 = var9;
                    break label3539;
                case 2:
                    var5 = blockBean.spec;
                    var18 = var9;
                    break label3539;
                case 3:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = "false";
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s = %s;", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 4:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s = %s;", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 5:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s++;", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 6:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s--;", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 7:
                    var5 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    if (var5.length() > 0) {
                        var5 = String.format("%s = %s;", var5, var18);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 8:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s = new HashMap<>();", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 9:
                    var11 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var8 = (String)var3.get(2);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = "";
                    }

                    var18 = var8;
                    if (var8.length() <= 0) {
                        var18 = "";
                    }

                    if (var11.length() > 0) {
                        var5 = String.format("%s.put(%s, %s);", var11, var5, var18);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 10:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = "";
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.get(%s).toString()", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 11:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = "";
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.containsKey(%s)", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 12:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = "";
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.remove(%s);", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 13:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.size()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 14:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.clear();", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 15:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.isEmpty()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 16:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("SketchwareUtil.getAllKeysFromMap(%s, %s);", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 17:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.add(Double.valueOf(%s));", var8, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 18:
                    var8 = (String)var3.get(0);
                    var5 = (String)var3.get(1);
                    var11 = (String)var3.get(2);
                    var18 = var9;
                    if (var8.length() > 0) {
                        var18 = var9;
                        if (var5.length() > 0) {
                            if (var11.length() > 0) {
                                var5 = String.format("%s.add((int)(%s), Double.valueOf(%s));", var11, var5, var8);
                                var18 = var9;
                                break label3539;
                            }

                            var18 = var9;
                        }
                    }
                    break;
                case 19:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.get((int)(%s)).doubleValue()", var8, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 20:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.indexOf(%s)", var8, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 21:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.contains(%s)", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 22:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.add(%s);", var8, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 23:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var11 = (String)var3.get(2);
                    var18 = var9;
                    if (var5.length() > 0) {
                        var18 = var9;
                        if (var8.length() > 0) {
                            if (var11.length() > 0) {
                                var5 = String.format("%s.add((int)(%s), %s);", var11, var8, var5);
                                var18 = var9;
                                break label3539;
                            }

                            var18 = var9;
                        }
                    }
                    break;
                case 24:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.get((int)(%s))", var8, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 25:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.indexOf(%s)", var8, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 26:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.contains(%s)", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 27:
                    var18 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var11 = (String)var3.get(2);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = "";
                    }

                    var18 = var8;
                    if (var8.length() <= 0) {
                        var18 = "";
                    }

                    if (var11.length() > 0) {
                        var25 = new StringBuilder();
                        var25.append("{\r\n");
                        var25.append(String.format("HashMap<String, Object> _item = new HashMap<>();"));
                        var25.append("\r\n");
                        var25.append(String.format("_item.put(%s, %s);", var5, var18));
                        var25.append("\r\n");
                        var25.append(String.format("%s.add(_item);", var11));
                        var25.append("\r\n");
                        var25.append("}");
                        var25.append("\r\n");
                        var5 = var25.toString();
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 28:
                    var8 = (String)var3.get(0);
                    var10 = (String)var3.get(1);
                    var11 = (String)var3.get(2);
                    var16 = (String)var3.get(3);
                    var18 = var8;
                    if (var8.length() <= 0) {
                        var18 = "";
                    }

                    var8 = var10;
                    if (var10.length() <= 0) {
                        var8 = "";
                    }

                    if (var11.length() > 0) {
                        var5 = var11;
                    }

                    if (var16.length() > 0) {
                        var24 = new StringBuilder();
                        var24.append("{\r\n");
                        var24.append(String.format("HashMap<String, Object> _item = new HashMap<>();"));
                        var24.append("\r\n");
                        var24.append(String.format("_item.put(%s, %s);", var18, var8));
                        var24.append("\r\n");
                        var24.append(String.format("%s.add((int)%s, _item);", var16, var5));
                        var24.append("\r\n");
                        var24.append("}");
                        var24.append("\r\n");
                        var5 = var24.toString();
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 29:
                    var18 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var11 = (String)var3.get(2);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var18 = var8;
                    if (var8.length() <= 0) {
                        var18 = "";
                    }

                    if (var11.length() > 0) {
                        var5 = String.format("%s.get((int)%s).get(%s).toString()", var11, var5, var18);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 30:
                    var8 = (String)var3.get(0);
                    var10 = (String)var3.get(1);
                    var11 = (String)var3.get(2);
                    var16 = (String)var3.get(3);
                    var18 = var8;
                    if (var8.length() <= 0) {
                        var18 = "";
                    }

                    var8 = var10;
                    if (var10.length() <= 0) {
                        var8 = "";
                    }

                    if (var11.length() > 0) {
                        var5 = var11;
                    }

                    if (var16.length() > 0) {
                        var5 = String.format("%s.get((int)%s).put(%s, %s);", var16, var5, var18, var8);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 31:
                    var11 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var8 = (String)var3.get(2);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var18 = var8;
                    if (var8.length() <= 0) {
                        var18 = "";
                    }

                    if (var11.length() > 0) {
                        var5 = String.format("%s.get((int)%s).containsKey(%s)", var11, var5, var18);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 32:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.add(%s);", var8, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 33:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var11 = (String)var3.get(2);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var18 = var9;
                    if (var8.length() > 0) {
                        if (var11.length() > 0) {
                            var5 = String.format("%s.add((int)%s, %s);", var11, var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 34:
                    var18 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var11 = (String)var3.get(2);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var18 = var9;
                    if (var8.length() > 0) {
                        if (var11.length() > 0) {
                            var5 = String.format("%s = %s.get((int)%s);", var11, var8, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 35:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.remove((int)(%s));", var8, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 36:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.size()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 37:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.clear();", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 38:
                    var7 = blockBean.subStack1;
                    if (var7 >= 0) {
                        var5 = a(String.valueOf(var7), "");
                    } else {
                        var5 = "";
                    }

                    var5 = String.format("while(true) {\r\n%s\r\n}", var5);
                    var18 = var9;
                    break label3539;
                case 39:
                    var8 = (String)var3.get(0);
                    var7 = blockBean.subStack1;
                    if (var7 >= 0) {
                        var18 = a(String.valueOf(var7), "");
                    } else {
                        var18 = "";
                    }

                    if (var8.length() > 0) {
                        var5 = var8;
                    }

                    var25 = new StringBuilder();
                    var25.append("_repeat");
                    var25.append(blockBean.id);
                    var8 = var25.toString();
                    var5 = String.format("for(int %s = 0; %s < (int)(%s); %s++) {\r\n%s\r\n}", var8, var8, var5, var8, var18);
                    var18 = var9;
                    break label3539;
                case 40:
                    var8 = (String)var3.get(0);
                    var7 = blockBean.subStack1;
                    if (var7 >= 0) {
                        var5 = a(String.valueOf(var7), "");
                    } else {
                        var5 = "";
                    }

                    var18 = var8;
                    if (var8.length() <= 0) {
                        var18 = "true";
                    }

                    var5 = String.format("if (%s) {\r\n%s\r\n}", var18, var5);
                    var18 = var9;
                    break label3539;
                case 41:
                    var11 = (String)var3.get(0);
                    var7 = blockBean.subStack1;
                    if (var7 >= 0) {
                        var5 = a(String.valueOf(var7), "");
                    } else {
                        var5 = "";
                    }

                    var7 = blockBean.subStack2;
                    if (var7 >= 0) {
                        var18 = a(String.valueOf(var7), "");
                    } else {
                        var18 = "";
                    }

                    var8 = var11;
                    if (var11.length() <= 0) {
                        var8 = "true";
                    }

                    var5 = String.format("if (%s) {\r\n%s\r\n}\r\nelse {\r\n%s\r\n}", var8, var5, var18);
                    var18 = var9;
                    break label3539;
                case 42:
                    var5 = "break;";
                    var18 = var9;
                    break label3539;
                case 43:
                case 44:
                    var5 = blockBean.opCode;
                    var18 = var9;
                    break label3539;
                case 45:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("!%s", var5);
                        var18 = var9;
                        break label3539;
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
                    var11 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var11;
                    if (var11.length() <= 0) {
                        var18 = "0";
                    }

                    if (var8.length() > 0) {
                        var5 = var8;
                    }

                    var5 = String.format("%s %s %s", var18, blockBean.opCode, var5);
                    var18 = var9;
                    break label3539;
                case 53:
                    var11 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var11;
                    if (var11.length() <= 0) {
                        var18 = "0";
                    }

                    if (var8.length() > 0) {
                        var5 = var8;
                    }

                    var5 = String.format("%s == %s", var18, var5);
                    var18 = var9;
                    break label3539;
                case 54:
                case 55:
                    var18 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = "true";
                    }

                    var18 = var8;
                    if (var8.length() <= 0) {
                        var18 = "true";
                    }

                    var5 = String.format("%s %s %s", var5, blockBean.opCode, var18);
                    var18 = var9;
                    break label3539;
                case 56:
                    var8 = (String)var3.get(0);
                    var11 = (String)var3.get(1);
                    var18 = var8;
                    if (var8.length() <= 0) {
                        var18 = "0";
                    }

                    if (var11.length() > 0) {
                        var5 = var11;
                    }

                    var5 = String.format("SketchwareUtil.getRandom((int)(%s), (int)(%s))", var18, var5);
                    var18 = var9;
                    break label3539;
                case 57:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.length()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 58:
                    var5 = String.format("%s.concat(%s)", (String)var3.get(0), (String)var3.get(1));
                    var18 = var9;
                    break label3539;
                case 59:
                    var5 = (String)var3.get(0);
                    var5 = String.format("%s.indexOf(%s)", (String)var3.get(1), var5);
                    var18 = var9;
                    break label3539;
                case 60:
                    var5 = (String)var3.get(0);
                    var5 = String.format("%s.lastIndexOf(%s)", (String)var3.get(1), var5);
                    var18 = var9;
                    break label3539;
                case 61:
                    var10 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var11 = (String)var3.get(2);
                    var18 = var8;
                    if (var8.length() <= 0) {
                        var18 = "0";
                    }

                    if (var11.length() > 0) {
                        var5 = var11;
                    }

                    var5 = String.format("%s.substring((int)(%s), (int)(%s))", var10, var18, var5);
                    var18 = var9;
                    break label3539;
                case 62:
                    var5 = String.format("%s.equals(%s)", (String)var3.get(0), (String)var3.get(1));
                    var18 = var9;
                    break label3539;
                case 63:
                    var5 = String.format("%s.contains(%s)", (String)var3.get(0), (String)var3.get(1));
                    var18 = var9;
                    break label3539;
                case 64:
                    var5 = String.format("%s.replace(%s, %s)", (String)var3.get(0), (String)var3.get(1), (String)var3.get(2));
                    var18 = var9;
                    break label3539;
                case 65:
                    var5 = String.format("%s.replaceFirst(%s, %s)", (String)var3.get(0), (String)var3.get(1), (String)var3.get(2));
                    var18 = var9;
                    break label3539;
                case 66:
                    var5 = String.format("%s.replaceAll(%s, %s)", (String)var3.get(0), (String)var3.get(1), (String)var3.get(2));
                    var18 = var9;
                    break label3539;
                case 67:
                    label2252: {
                        var18 = (String)var3.get(0);
                        if (var18.length() > 0) {
                            var5 = var18;
                            if (!var18.equals("\"\"")) {
                                break label2252;
                            }
                        }

                        var5 = "\"0\"";
                    }

                    var5 = String.format("Double.parseDouble(%s)", var5);
                    var18 = var9;
                    break label3539;
                case 68:
                    var5 = "System.currentTimeMillis()";
                    var18 = var9;
                    break label3539;
                case 69:
                    var5 = String.format("%s.trim()", (String)var3.get(0));
                    var18 = var9;
                    break label3539;
                case 70:
                    var5 = String.format("%s.toUpperCase()", (String)var3.get(0));
                    var18 = var9;
                    break label3539;
                case 71:
                    var5 = String.format("%s.toLowerCase()", (String)var3.get(0));
                    var18 = var9;
                    break label3539;
                case 72:
                    var18 = (String)var3.get(0);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var5 = String.format("String.valueOf((long)(%s))", var5);
                    var18 = var9;
                    break label3539;
                case 73:
                    var18 = (String)var3.get(0);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var5 = String.format("String.valueOf(%s)", var5);
                    var18 = var9;
                    break label3539;
                case 74:
                    var8 = (String)var3.get(0);
                    var11 = (String)var3.get(1);
                    var18 = var11;
                    if (var11.length() <= 0) {
                        var18 = "";
                    }

                    if (var8.length() > 0) {
                        var5 = var8;
                    }

                    var5 = String.format("new DecimalFormat(%s).format(%s)", var18, var5);
                    var18 = var9;
                    break label3539;
                case 75:
                    var8 = (String)blockBean.parameters.get(0);
                    var18 = var9;
                    if (var8 != null) {
                        var18 = var9;
                        var5 = var8;
                        if (var8.length() > 0) {
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 76:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s = new Gson().fromJson(%s, new TypeToken<HashMap<String, Object>>(){}.getType());", var8, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 77:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("new Gson().toJson(%s)", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 78:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s = new Gson().fromJson(%s, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());", var8, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 79:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("new Gson().toJson(%s)", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 80:
                    var18 = (String)var3.get(0);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var5 = String.format("SketchwareUtil.getDip(getApplicationContext(), (int)(%s))", var5);
                    var18 = var9;
                    break label3539;
                case 81:
                    var5 = "SketchwareUtil.getDisplayWidthPixels(getApplicationContext())";
                    var18 = var9;
                    break label3539;
                case 82:
                    var5 = "SketchwareUtil.getDisplayHeightPixels(getApplicationContext())";
                    var18 = var9;
                    break label3539;
                case 83:
                    var5 = "Math.PI";
                    var18 = var9;
                    break label3539;
                case 84:
                    var5 = "Math.E";
                    var18 = var9;
                    break label3539;
                case 85:
                    var8 = (String)var3.get(0);
                    var11 = (String)var3.get(1);
                    var18 = var11;
                    if (var11.length() <= 0) {
                        var18 = "0";
                    }

                    if (var8.length() > 0) {
                        var5 = var8;
                    }

                    var5 = String.format("Math.pow(%s, %s)", var5, var18);
                    var18 = var9;
                    break label3539;
                case 86:
                    var8 = (String)var3.get(0);
                    var11 = (String)var3.get(1);
                    var18 = var11;
                    if (var11.length() <= 0) {
                        var18 = "0";
                    }

                    if (var8.length() > 0) {
                        var5 = var8;
                    }

                    var5 = String.format("Math.min(%s, %s)", var5, var18);
                    var18 = var9;
                    break label3539;
                case 87:
                    var8 = (String)var3.get(0);
                    var11 = (String)var3.get(1);
                    var18 = var11;
                    if (var11.length() <= 0) {
                        var18 = "0";
                    }

                    if (var8.length() > 0) {
                        var5 = var8;
                    }

                    var5 = String.format("Math.max(%s, %s)", var5, var18);
                    var18 = var9;
                    break label3539;
                case 88:
                    var18 = (String)var3.get(0);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = "1";
                    }

                    var5 = String.format("Math.sqrt(%s)", var5);
                    var18 = var9;
                    break label3539;
                case 89:
                    var18 = (String)var3.get(0);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var5 = String.format("Math.abs(%s)", var5);
                    var18 = var9;
                    break label3539;
                case 90:
                    var18 = (String)var3.get(0);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var5 = String.format("Math.round(%s)", var5);
                    var18 = var9;
                    break label3539;
                case 91:
                    var18 = (String)var3.get(0);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var5 = String.format("Math.ceil(%s)", var5);
                    var18 = var9;
                    break label3539;
                case 92:
                    var18 = (String)var3.get(0);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var5 = String.format("Math.floor(%s)", var5);
                    var18 = var9;
                    break label3539;
                case 93:
                    var18 = (String)var3.get(0);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var5 = String.format("Math.sin(%s)", var5);
                    var18 = var9;
                    break label3539;
                case 94:
                    var18 = (String)var3.get(0);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var5 = String.format("Math.cos(%s)", var5);
                    var18 = var9;
                    break label3539;
                case 95:
                    var18 = (String)var3.get(0);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var5 = String.format("Math.tan(%s)", var5);
                    var18 = var9;
                    break label3539;
                case 96:
                    var18 = (String)var3.get(0);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var5 = String.format("Math.asin(%s)", var5);
                    var18 = var9;
                    break label3539;
                case 97:
                    var18 = (String)var3.get(0);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var5 = String.format("Math.acos(%s)", var5);
                    var18 = var9;
                    break label3539;
                case 98:
                    var18 = (String)var3.get(0);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var5 = String.format("Math.atan(%s)", var5);
                    var18 = var9;
                    break label3539;
                case 99:
                    var18 = (String)var3.get(0);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var5 = String.format("Math.exp(%s)", var5);
                    var18 = var9;
                    break label3539;
                case 100:
                    var18 = (String)var3.get(0);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var5 = String.format("Math.log(%s)", var5);
                    var18 = var9;
                    break label3539;
                case 101:
                    var18 = (String)var3.get(0);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var5 = String.format("Math.log10(%s)", var5);
                    var18 = var9;
                    break label3539;
                case 102:
                    var18 = (String)var3.get(0);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var5 = String.format("Math.toRadians(%s)", var5);
                    var18 = var9;
                    break label3539;
                case 103:
                    var18 = (String)var3.get(0);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var5 = String.format("Math.toDegrees(%s)", var5);
                    var18 = var9;
                    break label3539;
                case 104:
                    var18 = (String)var3.get(0);
                    var7 = blockBean.subStack1;
                    if (var7 >= 0) {
                        var5 = a(String.valueOf(var7), "");
                    } else {
                        var5 = "";
                    }

                    if (var18.length() > 0) {
                        var5 = String.format("%s.setOnClickListener(new View.OnClickListener() {\n@Override\npublic void onClick(View _view) {\n%s\n}\n});", var18, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 105:
                    if (!e.a(c).a) {
                        var18 = var9;
                        var5 = var8;
                    } else {
                        var5 = "_drawer.isDrawerOpen(GravityCompat.START)";
                        var18 = var9;
                    }
                    break label3539;
                case 106:
                    if (e.a(c).a) {
                        var5 = "_drawer.openDrawer(GravityCompat.START);";
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 107:
                    if (e.a(c).a) {
                        var5 = "_drawer.closeDrawer(GravityCompat.START);";
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 108:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = "true";
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.setEnabled(%s);", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 109:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.isEnabled()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 110:
                    var5 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.setText(%s);", var5, var18);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 111:
                    var8 = (String)var3.get(0);
                    var11 = (String)var3.get(1);
                    var18 = (String)var3.get(2);
                    if (var18.length() <= 0) {
                        var18 = var5;
                    } else {
                        Pair[] var22 = sq.a("property_text_style");
                        var4 = var22.length;
                        var5 = var18;
                        var7 = 0;

                        while(true) {
                            var18 = var5;
                            if (var7 >= var4) {
                                break;
                            }

                            Pair var15 = var22[var7];
                            var18 = var5;
                            if (var15.second.equals(var5)) {
                                var27 = new StringBuilder();
                                var27.append(var15.first);
                                var27.append("");
                                var18 = var27.toString();
                            }

                            ++var7;
                            var5 = var18;
                        }
                    }

                    if (var11.length() <= 0) {
                        var18 = var9;
                    } else {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.setTypeface(Typeface.createFromAsset(getAssets(),\"fonts/%s.ttf\"), %s);", var8, var11, var18);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 112:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.getText().toString()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 113:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = "0xFF000000";
                    }

                    var18 = var9;
                    if (var8.length() > 0) {
                        if (var5.length() > 0) {
                            var5 = String.format("%s.setBackgroundColor(%s);", var8, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 114:
                    var11 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var11.length() > 0) {
                        if (var8.length() > 0) {
                            if (!var8.equals("NONE")) {
                                var26 = new StringBuilder();
                                var26.append("R.drawable.");
                                var5 = var8;
                                if (var8.endsWith(".9")) {
                                    var5 = var8.replaceAll("\\.9", "");
                                }

                                var26.append(var5);
                                var5 = var26.toString();
                            }

                            var5 = String.format("%s.setBackgroundResource(%s);", var11, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 115:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = "0xFF000000";
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.setTextColor(%s);", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 116:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var5 = var18;
                    if (var18.endsWith(".9")) {
                        var5 = var18.replaceAll("\\.9", "");
                    }

                    var18 = var9;
                    if (var8.length() > 0) {
                        if (var5.length() > 0) {
                            var5 = String.format("%s.setImageResource(R.drawable.%s);", var8, var5.toLowerCase());
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 117:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = "0x00000000";
                    }

                    if (!var8.equals("\"\"")) {
                        var5 = String.format("%s.setColorFilter(%s, PorterDuff.Mode.MULTIPLY);", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 118:
                    var5 = (String)var3.get(0);
                    var5.equals("\"\"");
                    var5 = String.format("%s.requestFocus();", var5);
                    var18 = var9;
                    break label3539;
                case 119:
                    var5 = String.format("SketchwareUtil.showMessage(getApplicationContext(), %s);", (String)var3.get(0));
                    var18 = var9;
                    break label3539;
                case 120:
                    var5 = String.format("((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(\"clipboard\", %s));", (String)var3.get(0));
                    var18 = var9;
                    break label3539;
                case 121:
                    var5 = String.format("setTitle(%s);", (String)var3.get(0));
                    var18 = var9;
                    break label3539;
                case 122:
                    var8 = (String)var3.get(0);
                    var11 = (String)var3.get(1);
                    var5 = var18;
                    if (var11.length() > 0) {
                        if (var11.equals("\"\"")) {
                            var5 = var18;
                        } else {
                            var27 = new StringBuilder();
                            var27.append("Intent.");
                            var27.append(var11);
                            var5 = var27.toString();
                        }
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.setAction(%s);", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 123:
                    var5 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.setData(Uri.parse(%s));", var5, var18);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 124:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var8.length() > 0) {
                        if (var5.length() > 0) {
                            var5 = String.format("%s.setClass(getApplicationContext(), %s.class);", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 125:
                    var18 = (String)var3.get(0);
                    var5 = (String)var3.get(1);
                    var8 = (String)var3.get(2);
                    if (var18.length() > 0) {
                        var5 = String.format("%s.putExtra(%s, %s);", var18, var5, var8);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 126:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    if (var18.length() <= 0) {
                        var5 = "";
                    } else {
                        var27 = new StringBuilder();
                        var27.append("Intent.FLAG_ACTIVITY_");
                        var27.append(var18);
                        var5 = var27.toString();
                    }

                    var18 = var9;
                    if (var8.length() > 0) {
                        if (var5.length() > 0) {
                            var5 = String.format("%s.setFlags(%s);", var8, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 127:
                    var5 = String.format("getIntent().getStringExtra(%s)", (String)var3.get(0));
                    var18 = var9;
                    break label3539;
                case 128:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("startActivity(%s);", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 129:
                    var5 = "finish();";
                    var18 = var9;
                    break label3539;
                case 130:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s = getApplicationContext().getSharedPreferences(%s, Activity.MODE_PRIVATE);", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 131:
                    var5 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.getString(%s, \"\")", var5, var18);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 132:
                    var5 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var8 = (String)var3.get(2);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.edit().putString(%s, %s).commit();", var5, var18, var8);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 133:
                    var5 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.edit().remove(%s).commit();", var5, var18);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 134:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s = Calendar.getInstance();", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 135:
                    var11 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = (String)var3.get(2);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var18 = var9;
                    if (var11.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.add(Calendar.%s, (int)(%s));", var11, var8, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 136:
                    var11 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = (String)var3.get(2);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var18 = var9;
                    if (var11.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.set(Calendar.%s, (int)(%s));", var11, var8, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 137:
                    label3015: {
                        var8 = (String)var3.get(0);
                        var18 = (String)var3.get(1);
                        if (var18.length() > 0) {
                            var5 = var18;
                            if (!var18.equals("\"\"")) {
                                break label3015;
                            }
                        }

                        var5 = "\"yyyy/MM/dd hh:mm:ss\"";
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("new SimpleDateFormat(%s).format(%s.getTime())", var5, var8);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 138:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("(long)(%s.getTimeInMillis() - %s.getTimeInMillis())", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 139:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.getTimeInMillis()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 140:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.setTimeInMillis((long)(%s));", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 141:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = "VISIBLE";
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.setVisibility(View.%s);", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 142:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = "true";
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.setClickable(%s);", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 143:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.setRotation((float)(%s));", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 144:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.getRotation()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 145:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.setAlpha((float)(%s));", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 146:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.getAlpha()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 147:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.setTranslationX((float)(%s));", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 148:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.getTranslationX()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 149:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.setTranslationY((float)(%s));", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 150:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.getTranslationY()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 151:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.setScaleX((float)(%s));", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 152:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.getScaleX()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 153:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.setScaleY((float)(%s));", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 154:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.getScaleY()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 155:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("SketchwareUtil.getLocationX(%s)", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 156:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("SketchwareUtil.getLocationY(%s)", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 157:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = "false";
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.setChecked(%s);", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 158:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.isChecked()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 159:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.setAdapter(new ArrayAdapter<String>(getBaseContext(), %s, %s));", var5, "android.R.layout.simple_list_item_1", var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 160:
                case 325:
                case 326:
                case 327:
                case 328:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var18 = Lx.a(var5);
                            var24 = new StringBuilder();
                            var24.append("%s.setAdapter(new ");
                            var24.append(var18);
                            var24.append("(%s));");
                            var5 = String.format(var24.toString(), var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 161:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("((BaseAdapter)%s.getAdapter()).notifyDataSetChanged();", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 162:
                    var11 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var8 = (String)var3.get(2);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var18 = var8;
                    if (var8.length() <= 0) {
                        var18 = "false";
                    }

                    if (var11.length() > 0) {
                        var5 = String.format("%s.setItemChecked((int)(%s), %s);", var11, var5, var18);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 163:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.getCheckedItemPosition()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 164:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s = SketchwareUtil.getCheckedItemPositionsToArray(%s);", var8, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 165:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.getCheckedItemCount()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 166:
                    var5 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var5.length();
                    if (var18.length() > 0) {
                        var5 = String.format("%s.smoothScrollToPosition((int)(%s));", var5, var18);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 167:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, %s));", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 168:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("((ArrayAdapter)%s.getAdapter()).notifyDataSetChanged();", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 169:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.setSelection((int)(%s));", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 170:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.getSelectedItemPosition()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 171:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.loadUrl(%s);", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 172:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.getUrl()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 173:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = "LOAD_DEFAULT";
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.getSettings().setCacheMode(WebSettings.%s);", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 174:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.canGoBack()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 175:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.canGoForward()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 176:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.goBack();", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 177:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.goForward();", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 178:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.clearCache(true);", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 179:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.clearHistory();", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 180:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.stopLoading();", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 181:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.zoomIn();", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 182:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.zoomOut();", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 183:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.getDate()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 184:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.setDate((long)(%s), true, true);", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 185:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.setMinDate((long)(%s));", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 186:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.setMaxDate((long)(%s));", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 187:
                    var8 = (String)var3.get(0);
                    if (e.t.size() > 0) {
                        Iterator var23 = e.t.iterator();
                        var5 = "";

                        while(true) {
                            var18 = var5;
                            if (!var23.hasNext()) {
                                break;
                            }

                            var18 = (String)var23.next();
                            StringBuilder var21 = new StringBuilder();
                            var21.append(var5);
                            var21.append(".addTestDevice(\"");
                            var21.append(var18);
                            var21.append("\")\n");
                            var5 = var21.toString();
                        }
                    } else {
                        var18 = "";
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.loadAd(new AdRequest.Builder()%s.build());", var8, var18);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 188:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = uq.q[0];
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("_%s_controller.setMapType(GoogleMap.%s);", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 189:
                    var11 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var8;
                    if (var8.length() <= 0) {
                        var18 = "0";
                    }

                    var8 = (String)var3.get(2);
                    if (var8.length() > 0) {
                        var5 = var8;
                    }

                    if (var11.length() > 0) {
                        var5 = String.format("_%s_controller.moveCamera(%s, %s);", var11, var18, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 190:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("_%s_controller.zoomTo(%s);", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 191:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("_%s_controller.zoomIn();", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 192:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("_%s_controller.zoomOut();", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 193:
                    var11 = (String)var3.get(0);
                    var10 = (String)var3.get(1);
                    var18 = (String)var3.get(2);
                    var8 = var18;
                    if (var18.length() <= 0) {
                        var8 = "0";
                    }

                    var18 = (String)var3.get(3);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var18 = var9;
                    if (var11.length() > 0) {
                        if (var10.length() > 0) {
                            var5 = String.format("_%s_controller.addMarker(%s, %s, %s);", var11, var10, var8, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 194:
                    var5 = (String)var3.get(0);
                    var11 = (String)var3.get(1);
                    var8 = (String)var3.get(2);
                    var10 = (String)var3.get(3);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var11.length() > 0) {
                            var5 = String.format("_%s_controller.setMarkerInfo(%s, %s, %s);", var5, var11, var8, var10);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 195:
                    var11 = (String)var3.get(0);
                    var10 = (String)var3.get(1);
                    var18 = (String)var3.get(2);
                    var8 = var18;
                    if (var18.length() <= 0) {
                        var8 = "0";
                    }

                    var18 = (String)var3.get(3);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var18 = var9;
                    if (var11.length() > 0) {
                        if (var10.length() > 0) {
                            var5 = String.format("_%s_controller.setMarkerPosition(%s, %s, %s);", var11, var10, var8, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 196:
                    var10 = (String)var3.get(0);
                    var11 = (String)var3.get(1);
                    var18 = (String)var3.get(2);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = uq.r[0];
                    }

                    var18 = (String)var3.get(3);
                    var8 = var18;
                    if (var18.length() <= 0) {
                        var8 = "1";
                    }

                    var18 = var9;
                    if (var10.length() > 0) {
                        if (var11.length() > 0) {
                            var5 = String.format("_%s_controller.setMarkerColor(%s, BitmapDescriptorFactory.%s, %s);", var10, var11, var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 197:
                    var8 = (String)var3.get(0);
                    var11 = (String)var3.get(1);
                    var18 = (String)var3.get(2);
                    var5 = var18;
                    if (var18.endsWith(".9")) {
                        var5 = var18.replaceAll("\\.9", "");
                    }

                    var18 = var9;
                    if (var8.length() > 0) {
                        var18 = var9;
                        if (var11.length() > 0) {
                            if (var5.length() > 0) {
                                var5 = String.format("_%s_controller.setMarkerIcon(%s, R.drawable.%s);", var8, var11, var5.toLowerCase());
                                var18 = var9;
                                break label3539;
                            }

                            var18 = var9;
                        }
                    }
                    break;
                case 198:
                    var8 = (String)var3.get(0);
                    var11 = (String)var3.get(1);
                    var18 = (String)var3.get(2);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = "true";
                    }

                    var18 = var9;
                    if (var8.length() > 0) {
                        if (var11.length() > 0) {
                            var5 = String.format("_%s_controller.setMarkerVisible(%s, %s);", var8, var11, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 199:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.vibrate((long)(%s));", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 200:
                    var11 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var7 = blockBean.subStack1;
                    if (var7 >= 0) {
                        var18 = a(String.valueOf(var7), "");
                    } else {
                        var18 = "";
                    }

                    if (var8.length() > 0) {
                        var5 = var8;
                    }

                    if (var11.length() > 0) {
                        var5 = String.format("%s = new TimerTask() {\n@Override\npublic void run() {\nrunOnUiThread(new Runnable() {\n@Override\npublic void run() {\n%s\n}\n});\n}\n};\n_timer.schedule(%s, (int)(%s));", var11, var18, var11, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 201:
                    var12 = (String)var3.get(0);
                    var11 = (String)var3.get(1);
                    var10 = (String)var3.get(2);
                    var7 = blockBean.subStack1;
                    if (var7 >= 0) {
                        var18 = a(String.valueOf(var7), "");
                    } else {
                        var18 = "";
                    }

                    var8 = var10;
                    if (var10.length() <= 0) {
                        var8 = "0";
                    }

                    if (var11.length() > 0) {
                        var5 = var11;
                    }

                    if (var12.length() > 0) {
                        var5 = String.format("%s = new TimerTask() {\n@Override\npublic void run() {\nrunOnUiThread(new Runnable() {\n@Override\npublic void run() {\n%s\n}\n});\n}\n};\n_timer.scheduleAtFixedRate(%s, (int)(%s), (int)(%s));", var12, var18, var12, var5, var8);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 202:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.cancel();", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 203:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var11 = (String)var3.get(2);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = "";
                    }

                    var18 = var9;
                    if (var8.length() > 0) {
                        if (var11.length() > 0) {
                            var5 = String.format("%s.child(%s).updateChildren(%s);", var8, var5, var11);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 204:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.push().updateChildren(%s);", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 205:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.push().getKey()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 206:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = "";
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.child(%s).removeValue();", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 207:
                    var8 = (String)var3.get(0);
                    var11 = (String)var3.get(1);
                    var7 = blockBean.subStack1;
                    if (var7 >= 0) {
                        var5 = a(String.valueOf(var7), "");
                    } else {
                        var5 = "";
                    }

                    var18 = var9;
                    if (var11.length() > 0) {
                        if (var8.length() > 0) {
                            var26 = new StringBuilder();
                            var26.append(String.format("%s.addListenerForSingleValueEvent(new ValueEventListener() {", var8));
                            var26.append("\n@Override\npublic void onDataChange(DataSnapshot _dataSnapshot) {\n");
                            var26.append(String.format("%s = new ArrayList<>();", var11));
                            var26.append("\ntry {\nGenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};\nfor (DataSnapshot _data : _dataSnapshot.getChildren()) {\nHashMap<String, Object> _map = _data.getValue(_ind);\n");
                            var26.append(String.format("%s.add(_map);", var11));
                            var26.append("\n}\n}\ncatch (Exception _e) {\n_e.printStackTrace();\n}\n");
                            var26.append(var5);
                            var26.append("\n}\n@Override\npublic void onCancelled(DatabaseError _databaseError) {\n}\n});");
                            var5 = var26.toString();
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 208:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var11 = (String)var3.get(2);
                    var18 = var9;
                    if (var5.length() > 0) {
                        var18 = var9;
                        if (!var8.equals("\"\"")) {
                            if (!var11.equals("\"\"")) {
                                var26 = new StringBuilder();
                                var26.append("_");
                                var26.append(var5);
                                var26.append("_create_user_listener");
                                var18 = var26.toString();
                                var5 = String.format("%s.createUserWithEmailAndPassword(%s, %s).addOnCompleteListener(%s.this, %s);", var5, var8, var11, c, var18);
                                var18 = var9;
                                break label3539;
                            }

                            var18 = var9;
                        }
                    }
                    break;
                case 209:
                    var8 = (String)var3.get(0);
                    var5 = (String)var3.get(1);
                    var11 = (String)var3.get(2);
                    var18 = var9;
                    if (var8.length() > 0) {
                        var18 = var9;
                        if (!var5.equals("\"\"")) {
                            if (!var11.equals("\"\"")) {
                                var26 = new StringBuilder();
                                var26.append("_");
                                var26.append(var8);
                                var26.append("_sign_in_listener");
                                var18 = var26.toString();
                                var5 = String.format("%s.signInWithEmailAndPassword(%s, %s).addOnCompleteListener(%s.this, %s);", var8, var5, var11, c, var18);
                                var18 = var9;
                                break label3539;
                            }

                            var18 = var9;
                        }
                    }
                    break;
                case 210:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var26 = new StringBuilder();
                        var26.append("_");
                        var26.append(var5);
                        var26.append("_sign_in_listener");
                        var18 = var26.toString();
                        var5 = String.format("%s.signInAnonymously().addOnCompleteListener(%s.this, %s);", var5, c, var18);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 211:
                    var18 = var9;
                    var5 = var8;
                    if (e.a(c).b) {
                        var5 = String.format("(FirebaseAuth.getInstance().getCurrentUser() != null)");
                        var18 = var9;
                    }
                    break label3539;
                case 212:
                    var18 = var9;
                    if (e.a(c).b) {
                        var5 = String.format("FirebaseAuth.getInstance().getCurrentUser().getEmail()");
                        var18 = var9;
                        break label3539;
                    }
                    break;
                case 213:
                    var18 = var9;
                    if (e.a(c).b) {
                        var5 = String.format("FirebaseAuth.getInstance().getCurrentUser().getUid()");
                        var18 = var9;
                        break label3539;
                    }
                    break;
                case 214:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (!var8.equals("\"\"")) {
                            var26 = new StringBuilder();
                            var26.append("_");
                            var26.append(var5);
                            var26.append("_reset_password_listener");
                            var5 = String.format("%s.sendPasswordResetEmail(%s).addOnCompleteListener(%s);", var5, var8, var26.toString());
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 215:
                    var18 = var9;
                    if (e.a(c).b) {
                        var5 = String.format("FirebaseAuth.getInstance().signOut();");
                        var18 = var9;
                        break label3539;
                    }
                    break;
                case 216:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.addChildEventListener(_%s_child_listener);", var5, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 217:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.removeEventListener(_%s_child_listener);", var5, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 218:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.registerListener(_%s_sensor_listener, %s.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);", var5, var5, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 219:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.unregisterListener(_%s_sensor_listener);", var5, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 220:
                    label3002: {
                        var8 = (String)var3.get(0);
                        var18 = (String)var3.get(1);
                        if (var18.length() > 0) {
                            var5 = var18;
                            if (!var18.equals("\"\"")) {
                                break label3002;
                            }
                        }

                        var5 = "\"Title\"";
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.setTitle(%s);", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 221:
                    label2997: {
                        var8 = (String)var3.get(0);
                        var18 = (String)var3.get(1);
                        if (var18.length() > 0) {
                            var5 = var18;
                            if (!var18.equals("\"\"")) {
                                break label2997;
                            }
                        }

                        var5 = "\"Message\"";
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.setMessage(%s);", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 222:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.create().show();", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 223:
                    var11 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var7 = blockBean.subStack1;
                    if (var7 >= 0) {
                        var5 = a(String.valueOf(var7), "");
                    } else {
                        var5 = "";
                    }

                    label2991: {
                        if (var8.length() > 0) {
                            var18 = var8;
                            if (!var8.equals("\"\"")) {
                                break label2991;
                            }
                        }

                        var18 = "\"OK\"";
                    }

                    if (var11.length() > 0) {
                        var5 = String.format("%s.setPositiveButton(%s, new DialogInterface.OnClickListener() {\n@Override\npublic void onClick(DialogInterface _dialog, int _which) {\n%s\n}\n});", var11, var18, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 224:
                    var11 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var7 = blockBean.subStack1;
                    if (var7 >= 0) {
                        var5 = a(String.valueOf(var7), "");
                    } else {
                        var5 = "";
                    }

                    label2985: {
                        if (var8.length() > 0) {
                            var18 = var8;
                            if (!var8.equals("\"\"")) {
                                break label2985;
                            }
                        }

                        var18 = "\"Cancel\"";
                    }

                    if (var11.length() > 0) {
                        var5 = String.format("%s.setNegativeButton(%s, new DialogInterface.OnClickListener() {\n@Override\npublic void onClick(DialogInterface _dialog, int _which) {\n%s\n}\n});", var11, var18, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 225:
                    var11 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var7 = blockBean.subStack1;
                    if (var7 >= 0) {
                        var5 = a(String.valueOf(var7), "");
                    } else {
                        var5 = "";
                    }

                    label2979: {
                        if (var8.length() > 0) {
                            var18 = var8;
                            if (!var8.equals("\"\"")) {
                                break label2979;
                            }
                        }

                        var18 = "\"Neutral\"";
                    }

                    if (var11.length() > 0) {
                        var5 = String.format("%s.setNeutralButton(%s, new DialogInterface.OnClickListener() {\n@Override\npublic void onClick(DialogInterface _dialog, int _which) {\n%s\n}\n});", var11, var18, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 226:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s = MediaPlayer.create(getApplicationContext(), R.raw.%s);", var5, var8.toLowerCase());
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 227:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.start();", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 228:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.pause();", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 229:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.seekTo((int)(%s));", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 230:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.getCurrentPosition()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 231:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.getDuration()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 232:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.reset();", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 233:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.release();", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 234:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.isPlaying()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 235:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = "false";
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.setLooping(%s);", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 236:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.isLooping()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 237:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = "1";
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s = new SoundPool((int)(%s), AudioManager.STREAM_MUSIC, 0);", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 238:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var8.length() > 0) {
                        if (var5.length() > 0) {
                            var5 = String.format("%s.load(getApplicationContext(), R.raw.%s, 1)", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 239:
                    var11 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = (String)var3.get(2);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var18 = var9;
                    if (var8.length() > 0) {
                        if (var11.length() > 0) {
                            var5 = String.format("%s.play((int)(%s), 1.0f, 1.0f, 1, (int)(%s), 1.0f)", var11, var8, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 240:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var8.length() > 0) {
                        if (var5.length() > 0) {
                            var5 = String.format("%s.stop((int)(%s));", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 241:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var5 = var18;
                    if (var18.endsWith(".9")) {
                        var5 = var18.replaceAll("\\.9", "");
                    }

                    var18 = var9;
                    if (var8.length() > 0) {
                        if (var5.length() > 0) {
                            var5 = String.format("%s.setThumbResource(R.drawable.%s);", var8, var5.toLowerCase());
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 242:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var5 = var18;
                    if (var18.endsWith(".9")) {
                        var5 = var18.replaceAll("\\.9", "");
                    }

                    var18 = var9;
                    if (var8.length() > 0) {
                        if (var5.length() > 0) {
                            var5 = String.format("%s.setTrackResource(R.drawable.%s);", var8, var5.toLowerCase());
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 243:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.setProgress((int)%s);", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 244:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.getProgress()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 245:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.setMax((int)%s);", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 246:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.getMax()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 247:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.setTarget(%s);", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 248:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.setPropertyName(\"%s\");", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 249:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.setFloatValues((float)(%s));", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 250:
                    var10 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var11 = (String)var3.get(2);
                    var18 = var11;
                    if (var11.length() <= 0) {
                        var18 = "0";
                    }

                    if (var8.length() > 0) {
                        var5 = var8;
                    }

                    if (var10.length() > 0) {
                        var5 = String.format("%s.setFloatValues((float)(%s), (float)(%s));", var10, var5, var18);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 251:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.setDuration((int)(%s));", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 252:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.setRepeatMode(ValueAnimator.%s);", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 253:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.setRepeatCount((int)(%s));", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 254:
                    var18 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    if (var8.equals("Accelerate")) {
                        var5 = "new AccelerateInterpolator()";
                    } else {
                        var5 = "new LinearInterpolator()";
                    }

                    if (var8.equals("Decelerate")) {
                        var5 = "new DecelerateInterpolator()";
                    }

                    if (var8.equals("AccelerateDeccelerate")) {
                        var5 = "new AccelerateDecelerateInterpolator()";
                    }

                    if (var8.equals("Bounce")) {
                        var5 = "new BounceInterpolator()";
                    }

                    if (var18.length() > 0) {
                        var5 = String.format("%s.setInterpolator(%s);", var18, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 255:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.start();", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 256:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.cancel();", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 257:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.isRunning()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 258:
                    var5 = "";
                    var18 = var9;
                    break label3539;
                case 259:
                    var5 = "";
                    var18 = var9;
                    break label3539;
                case 260:
                    var5 = "";
                    var18 = var9;
                    break label3539;
                case 261:
                    var8 = (String)var3.get(0);
                    var5 = (String)var3.get(1);
                    var11 = (String)var3.get(2);
                    var18 = var9;
                    if (var8.length() > 0) {
                        var18 = var9;
                        if (!var5.equals("\"\"")) {
                            if (!var11.equals("\"\"")) {
                                var5 = String.format("%s.child(%s).putFile(Uri.fromFile(new File(%s))).addOnFailureListener(_%s_failure_listener).addOnProgressListener(_%s_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {\n@Override\npublic Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {\nreturn %s.child(%s).getDownloadUrl();\n}}).addOnCompleteListener(_%s_upload_success_listener);", var8, var11, var5, var8, var8, var8, var11, var8);
                                var18 = var9;
                                break label3539;
                            }

                            var18 = var9;
                        }
                    }
                    break;
                case 262:
                    var8 = (String)var3.get(0);
                    var5 = (String)var3.get(1);
                    var11 = (String)var3.get(2);
                    var18 = var9;
                    if (var8.length() > 0) {
                        var18 = var9;
                        if (!var5.equals("\"\"")) {
                            if (!var11.equals("\"\"")) {
                                var5 = String.format("_firebase_storage.getReferenceFromUrl(%s).getFile(new File(%s)).addOnSuccessListener(_%s_download_success_listener).addOnFailureListener(_%s_failure_listener).addOnProgressListener(_%s_download_progress_listener);", var5, var11, var8, var8, var8);
                                var18 = var9;
                                break label3539;
                            }

                            var18 = var9;
                        }
                    }
                    break;
                case 263:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (!var8.equals("\"\"")) {
                            var5 = String.format("_firebase_storage.getReferenceFromUrl(%s).delete().addOnSuccessListener(_%s_delete_success_listener).addOnFailureListener(_%s_failure_listener);", var8, var5, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 264:
                    var5 = (String)var3.get(0);
                    if (!var5.equals("\"\"")) {
                        var5 = String.format("FileUtil.readFile(%s)", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 265:
                    var5 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    if (!var18.equals("\"\"")) {
                        var5 = String.format("FileUtil.writeFile(%s, %s);", var18, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 266:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (!var5.equals("\"\"")) {
                        if (!var8.equals("\"\"")) {
                            var5 = String.format("FileUtil.copyFile(%s, %s);", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 267:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (!var5.equals("\"\"")) {
                        if (!var8.equals("\"\"")) {
                            var5 = String.format("FileUtil.moveFile(%s, %s);", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 268:
                    var5 = (String)var3.get(0);
                    if (!var5.equals("\"\"")) {
                        var5 = String.format("FileUtil.deleteFile(%s);", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 269:
                    var5 = (String)var3.get(0);
                    if (!var5.equals("\"\"")) {
                        var5 = String.format("FileUtil.isExistFile(%s)", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 270:
                    var5 = (String)var3.get(0);
                    if (!var5.equals("\"\"")) {
                        var5 = String.format("FileUtil.makeDir(%s);", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 271:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (!var5.equals("\"\"")) {
                        if (var8.length() > 0) {
                            var5 = String.format("FileUtil.listDir(%s, %s);", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 272:
                    var5 = (String)var3.get(0);
                    if (!var5.equals("\"\"")) {
                        var5 = String.format("FileUtil.isDirectory(%s)", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 273:
                    var5 = (String)var3.get(0);
                    if (!var5.equals("\"\"")) {
                        var5 = String.format("FileUtil.isFile(%s)", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 274:
                    var5 = (String)var3.get(0);
                    if (!var5.equals("\"\"")) {
                        var5 = String.format("FileUtil.getFileLength(%s)", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 275:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (!var5.equals("\"\"")) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.startsWith(%s)", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 276:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (!var5.equals("\"\"")) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.endsWith(%s)", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 277:
                    var5 = (String)var3.get(0);
                    if (!var5.equals("\"\"")) {
                        var5 = String.format("Uri.parse(%s).getLastPathSegment()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 278:
                    var5 = "FileUtil.getExternalStorageDir()";
                    var18 = var9;
                    break label3539;
                case 279:
                    var5 = "FileUtil.getPackageDataDir(getApplicationContext())";
                    var18 = var9;
                    break label3539;
                case 280:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("FileUtil.getPublicDir(Environment.%s)", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 281:
                    var8 = (String)var3.get(0);
                    var11 = (String)var3.get(1);
                    var18 = (String)var3.get(2);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = "1024";
                    }

                    var18 = var9;
                    if (!var8.equals("\"\"")) {
                        if (!var11.equals("\"\"")) {
                            var5 = String.format("FileUtil.resizeBitmapFileRetainRatio(%s, %s, %s);", var8, var11, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 282:
                    var8 = (String)var3.get(0);
                    var11 = (String)var3.get(1);
                    var18 = (String)var3.get(2);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = "1024";
                    }

                    var18 = var9;
                    if (!var8.equals("\"\"")) {
                        if (!var11.equals("\"\"")) {
                            var5 = String.format("FileUtil.resizeBitmapFileToSquare(%s, %s, %s);", var8, var11, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 283:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (!var5.equals("\"\"")) {
                        if (!var8.equals("\"\"")) {
                            var5 = String.format("FileUtil.resizeBitmapFileToCircle(%s, %s);", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 284:
                    var8 = (String)var3.get(0);
                    var11 = (String)var3.get(1);
                    var18 = (String)var3.get(2);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var18 = var9;
                    if (!var8.equals("\"\"")) {
                        if (!var11.equals("\"\"")) {
                            var5 = String.format("FileUtil.resizeBitmapFileWithRoundedBorder(%s, %s, %s);", var8, var11, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 285:
                    var10 = (String)var3.get(0);
                    var11 = (String)var3.get(1);
                    var8 = (String)var3.get(2);
                    var18 = (String)var3.get(3);
                    var5 = var8;
                    if (var8.length() <= 0) {
                        var5 = "1024";
                    }

                    var8 = var18;
                    if (var18.length() <= 0) {
                        var8 = "1024";
                    }

                    var18 = var9;
                    if (!var10.equals("\"\"")) {
                        if (!var11.equals("\"\"")) {
                            var5 = String.format("FileUtil.cropBitmapFileFromCenter(%s, %s, %s, %s);", var10, var11, var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 286:
                    var8 = (String)var3.get(0);
                    var11 = (String)var3.get(1);
                    var18 = (String)var3.get(2);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var18 = var9;
                    if (!var8.equals("\"\"")) {
                        if (!var11.equals("\"\"")) {
                            var5 = String.format("FileUtil.rotateBitmapFile(%s, %s, %s);", var8, var11, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 287:
                    var11 = (String)var3.get(0);
                    var10 = (String)var3.get(1);
                    var8 = (String)var3.get(2);
                    var18 = (String)var3.get(3);
                    var5 = var8;
                    if (var8.length() <= 0) {
                        var5 = "1";
                    }

                    var8 = var18;
                    if (var18.length() <= 0) {
                        var8 = "1";
                    }

                    var18 = var9;
                    if (!var11.equals("\"\"")) {
                        if (!var10.equals("\"\"")) {
                            var5 = String.format("FileUtil.scaleBitmapFile(%s, %s, %s, %s);", var11, var10, var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 288:
                    var12 = (String)var3.get(0);
                    var10 = (String)var3.get(1);
                    var18 = (String)var3.get(2);
                    var11 = (String)var3.get(3);
                    var8 = var18;
                    if (var18.length() <= 0) {
                        var8 = "0";
                    }

                    if (var11.length() > 0) {
                        var5 = var11;
                    }

                    var18 = var9;
                    if (!var12.equals("\"\"")) {
                        if (!var10.equals("\"\"")) {
                            var5 = String.format("FileUtil.skewBitmapFile(%s, %s, %s, %s);", var12, var10, var8, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 289:
                    var8 = (String)var3.get(0);
                    var11 = (String)var3.get(1);
                    var18 = (String)var3.get(2);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = "0x00000000";
                    }

                    var18 = var9;
                    if (!var8.equals("\"\"")) {
                        if (!var11.equals("\"\"")) {
                            var5 = String.format("FileUtil.setBitmapFileColorFilter(%s, %s, %s);", var8, var11, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 290:
                    var11 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = (String)var3.get(2);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var18 = var9;
                    if (!var11.equals("\"\"")) {
                        if (!var8.equals("\"\"")) {
                            var5 = String.format("FileUtil.setBitmapFileBrightness(%s, %s, %s);", var11, var8, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 291:
                    var8 = (String)var3.get(0);
                    var11 = (String)var3.get(1);
                    var18 = (String)var3.get(2);
                    if (var18.length() > 0) {
                        var5 = var18;
                    }

                    var18 = var9;
                    if (!var8.equals("\"\"")) {
                        if (!var11.equals("\"\"")) {
                            var5 = String.format("FileUtil.setBitmapFileContrast(%s, %s, %s);", var8, var11, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 292:
                    var5 = (String)var3.get(0);
                    if (!var5.equals("\"\"")) {
                        var5 = String.format("FileUtil.getJpegRotate(%s)", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 293:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("startActivityForResult(%s, REQ_CD_%s);", var5, var5.toUpperCase());
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 294:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("startActivityForResult(%s, REQ_CD_%s);", var5, var5.toUpperCase());
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 295:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (!var8.equals("\"\"")) {
                            var5 = String.format("%s.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(%s, 1024, 1024));", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 296:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (!var8.equals("\"\"")) {
                            var5 = String.format("Glide.with(getApplicationContext()).load(Uri.parse(%s)).into(%s);", var8, var5);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 297:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (!var8.equals("\"\"")) {
                            var5 = String.format("%s.setHint(%s);", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 298:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (!var8.equals("\"\"")) {
                            var5 = String.format("%s.setHintTextColor(%s);", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 299:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var11 = (String)var3.get(2);
                    var18 = var9;
                    if (var5.length() > 0) {
                        var18 = var9;
                        if (var8.length() > 0) {
                            if (var11.length() > 0) {
                                var5 = String.format("%s.setParams(%s, RequestNetworkController.%s);", var5, var8, var11);
                                var18 = var9;
                                break label3539;
                            }

                            var18 = var9;
                        }
                    }
                    break;
                case 300:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.setHeaders(%s);", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 301:
                    var11 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var5 = (String)var3.get(2);
                    var10 = (String)var3.get(3);
                    var18 = var9;
                    if (var11.length() > 0) {
                        var18 = var9;
                        if (var8.length() > 0) {
                            var18 = var9;
                            if (!var5.equals("\"\"")) {
                                if (var10.length() > 0) {
                                    var5 = String.format("%s.startRequestNetwork(RequestNetworkController.%s, %s, %s, _%s_request_listener);", var11, var8, var5, var10, var11);
                                    var18 = var9;
                                    break label3539;
                                }

                                var18 = var9;
                            }
                        }
                    }
                    break;
                case 302:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = "false";
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.setIndeterminate(%s);", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 303:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = "1";
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.setPitch((float)%s);", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 304:
                    var8 = (String)var3.get(0);
                    var18 = (String)var3.get(1);
                    var5 = var18;
                    if (var18.length() <= 0) {
                        var5 = "1";
                    }

                    if (var8.length() > 0) {
                        var5 = String.format("%s.setSpeechRate((float)%s);", var8, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 305:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.speak(%s, TextToSpeech.QUEUE_ADD, null);", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 306:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.isSpeaking()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 307:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.stop();", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 308:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.shutdown();", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 309:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("Intent _intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);\n_intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());\n_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);\n_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());\n%s.startListening(_intent);", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 310:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.stopListening();", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 311:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.cancel();\n%s.destroy();", var5, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 312:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.readyConnection(_%s_bluetooth_connection_listener, %s);", var5, var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 313:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var11 = (String)var3.get(2);
                    var18 = var9;
                    if (var5.length() > 0) {
                        var18 = var9;
                        if (var8.length() > 0) {
                            if (var11.length() > 0) {
                                var5 = String.format("%s.readyConnection(_%s_bluetooth_connection_listener, %s, %s);", var5, var5, var8, var11);
                                var18 = var9;
                                break label3539;
                            }

                            var18 = var9;
                        }
                    }
                    break;
                case 314:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var11 = (String)var3.get(2);
                    var18 = var9;
                    if (var5.length() > 0) {
                        var18 = var9;
                        if (var8.length() > 0) {
                            if (var11.length() > 0) {
                                var5 = String.format("%s.startConnection(_%s_bluetooth_connection_listener, %s, %s);", var5, var5, var8, var11);
                                var18 = var9;
                                break label3539;
                            }

                            var18 = var9;
                        }
                    }
                    break;
                case 315:
                    var5 = (String)var3.get(0);
                    var11 = (String)var3.get(1);
                    var8 = (String)var3.get(2);
                    var10 = (String)var3.get(3);
                    var18 = var9;
                    if (var5.length() > 0) {
                        var18 = var9;
                        if (var11.length() > 0) {
                            var18 = var9;
                            if (var8.length() > 0) {
                                if (var10.length() > 0) {
                                    var5 = String.format("%s.startConnection(_%s_bluetooth_connection_listener, %s, %s, %s);", var5, var5, var11, var8, var10);
                                    var18 = var9;
                                    break label3539;
                                }

                                var18 = var9;
                            }
                        }
                    }
                    break;
                case 316:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.stopConnection(_%s_bluetooth_connection_listener, %s);", var5, var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 317:
                    var8 = (String)var3.get(0);
                    var5 = (String)var3.get(1);
                    var11 = (String)var3.get(2);
                    var18 = var9;
                    if (var8.length() > 0) {
                        var18 = var9;
                        if (var5.length() > 0) {
                            if (var11.length() > 0) {
                                var5 = String.format("%s.sendData(_%s_bluetooth_connection_listener, %s, %s);", var8, var8, var5, var11);
                                var18 = var9;
                                break label3539;
                            }

                            var18 = var9;
                        }
                    }
                    break;
                case 318:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.isBluetoothEnabled()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 319:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.isBluetoothActivated()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 320:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.activateBluetooth();", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 321:
                    var5 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var9;
                    if (var5.length() > 0) {
                        if (var8.length() > 0) {
                            var5 = String.format("%s.getPairedDevices(%s);", var5, var8);
                            var18 = var9;
                            break label3539;
                        }

                        var18 = var9;
                    }
                    break;
                case 322:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.getRandomUUID()", var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 323:
                    var10 = (String)var3.get(0);
                    var8 = (String)var3.get(1);
                    var18 = var8;
                    if (var8.length() <= 0) {
                        var18 = uq.p[0];
                    }

                    var11 = (String)var3.get(2);
                    var8 = var11;
                    if (var11.length() <= 0) {
                        var8 = "1000";
                    }

                    var11 = (String)var3.get(3);
                    if (var11.length() > 0) {
                        var5 = var11;
                    }

                    if (var10.length() > 0) {
                        if (e.g) {
                            var5 = String.format("if (ContextCompat.checkSelfPermission(%s.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {\n%s.requestLocationUpdates(LocationManager.%s, %s, %s, _%s_location_listener);\n}", c, var10, var18, var8, var5, var10);
                            var18 = var9;
                        } else {
                            var5 = String.format("if (Build.VERSION.SDK_INT >= 23) {if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {\n%s.requestLocationUpdates(LocationManager.%s, %s, %s, _%s_location_listener);\n}\n}\nelse {\n%s.requestLocationUpdates(LocationManager.%s, %s, %s, _%s_location_listener);\n}", var10, var18, var8, var5, var10, var10, var18, var8, var5, var10);
                            var18 = var9;
                        }
                        break label3539;
                    }

                    var18 = var9;
                    break;
                case 324:
                    var5 = (String)var3.get(0);
                    if (var5.length() > 0) {
                        var5 = String.format("%s.removeUpdates(_%s_location_listener);", var5, var5);
                        var18 = var9;
                        break label3539;
                    }

                    var18 = var9;
                    break;
                default:
                    var18 = var9;
            }

            var18 = mceb.getCodeExtraBlock(blockBean, var18);
            var5 = var18;
        }

        var9 = var5;
        StringBuilder var14;
        if (b(blockBean.opCode, var2)) {
            var14 = new StringBuilder();
            var14.append("(");
            var14.append(var5);
            var14.append(")");
            var9 = var14.toString();
        }

        var2 = var9;
        if (blockBean.nextBlock >= 0) {
            var14 = new StringBuilder();
            var14.append(var9);
            var14.append("\r\n");
            var14.append(a(String.valueOf(blockBean.nextBlock), var18));
            var2 = var14.toString();
        }

        return var2;
    }

    public final String a(String var1) {
        StringBuilder var2 = new StringBuilder(4096);
        CharBuffer var3 = CharBuffer.wrap(var1);

        for(int var4 = 0; var4 < var3.length(); ++var4) {
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
                        StringBuilder var7 = new StringBuilder();
                        var7.append("\\");
                        var7.append(var5);
                        var2.append(var7.toString());
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
        if (var1.length() > 0 && var1.charAt(0) == '@') {
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
        return !g.containsKey(var1) ? "" : a((BlockBean)g.get(var1), var2);
    }

    public final boolean b(String var1, String var2) {
        String[] var3 = a;
        int var4 = var3.length;
        boolean var5 = false;
        int var6 = 0;

        boolean var11;
        while(true) {
            if (var6 >= var4) {
                var11 = false;
                break;
            }

            if (var3[var6].equals(var2)) {
                var11 = true;
                break;
            }

            ++var6;
        }

        String[] var9 = b;
        int var7 = var9.length;
        var4 = 0;

        boolean var10;
        while(true) {
            if (var4 >= var7) {
                var10 = false;
                break;
            }

            if (var9[var4].equals(var1)) {
                var10 = true;
                break;
            }

            ++var4;
        }

        boolean var8 = var5;
        if (var11) {
            var8 = var5;
            if (var10) {
                var8 = true;
            }
        }

        return var8;
    }
}
