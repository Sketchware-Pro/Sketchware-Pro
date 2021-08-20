package a.a.a;

import com.besome.sketch.beans.BlockBean;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import android.util.Pair;

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

    public Fx(String str, jq jqVar, String str2, ArrayList<BlockBean> arrayList) {
        this.c = str;
        this.e = jqVar;
        this.d = str2;
        this.f = arrayList;
        this.mceb = new ExtraBlockCode(this);
    }

    public String a() {
        this.g = new HashMap();
        ArrayList arrayList = this.f;
        if (arrayList == null) {
            return "";
        }
        if (arrayList.size() <= 0) {
            return "";
        }
        Iterator it = this.f.iterator();
        while (it.hasNext()) {
            BlockBean blockBean = (BlockBean) it.next();
            this.g.put(blockBean.id, blockBean);
        }
        StringBuilder stringBuilder = new StringBuilder(4096);
        stringBuilder.append(a((BlockBean) this.f.get(0), ""));
        return stringBuilder.toString();
    }

   public final String a(BlockBean var1, String var2) {
      ArrayList var3 = new ArrayList();

      for(int var4 = 0; var4 < var1.parameters.size(); ++var4) {
         String var702 = (String)var1.parameters.get(var4);
         Gx var703 = (Gx)var1.getParamClassInfo().get(var4);
         byte var704;
         if(var703.b("boolean")) {
            var704 = 0;
         } else if(var703.b("double")) {
            var704 = 1;
         } else if(var703.b("String")) {
            var704 = 2;
         } else {
            var704 = 3;
         }

         var3.add(this.a(var702, var704, var1.opCode));
      }

      String var7;
      short var8;
      label3193: {
         String var5 = var1.opCode;
         int var6 = var5.hashCode();
         var7 = "false";
         switch(var6) {
         case -2135695280:
            if(var5.equals("webViewLoadUrl")) {
               var8 = 171;
               break label3193;
            }
            break;
         case -2120571577:
            if(var5.equals("mapIsEmpty")) {
               var8 = 15;
               break label3193;
            }
            break;
         case -2114384168:
            if(var5.equals("firebasestorageDownloadFile")) {
               var8 = 262;
               break label3193;
            }
            break;
         case -2055793167:
            if(var5.equals("fileutillistdir")) {
               var8 = 271;
               break label3193;
            }
            break;
         case -2037144358:
            if(var5.equals("bluetoothConnectStartConnectionToUuid")) {
               var8 = 315;
               break label3193;
            }
            break;
         case -2027093331:
            if(var5.equals("calendarViewSetDate")) {
               var8 = 184;
               break label3193;
            }
            break;
         case -2020761366:
            if(var5.equals("fileRemoveData")) {
               var8 = 133;
               break label3193;
            }
            break;
         case -1998407506:
            if(var5.equals("listSetData")) {
               var8 = 159;
               break label3193;
            }
            break;
         case -1989678633:
            if(var5.equals("mapViewSetMarkerVisible")) {
               var8 = 198;
               break label3193;
            }
            break;
         case -1979147952:
            if(var5.equals("stringContains")) {
               var8 = 63;
               break label3193;
            }
            break;
         case -1975568730:
            if(var5.equals("copyToClipboard")) {
               var8 = 120;
               break label3193;
            }
            break;
         case -1966668787:
            if(var5.equals("firebaseauthSignOutUser")) {
               var8 = 215;
               break label3193;
            }
            break;
         case -1937348542:
            if(var5.equals("firebaseStartListen")) {
               var8 = 216;
               break label3193;
            }
            break;
         case -1922362317:
            if(var5.equals("getExternalStorageDir")) {
               var8 = 278;
               break label3193;
            }
            break;
         case -1920517885:
            if(var5.equals("setVarBoolean")) {
               var8 = 3;
               break label3193;
            }
            break;
         case -1919300188:
            if(var5.equals("toNumber")) {
               var8 = 67;
               break label3193;
            }
            break;
         case -1910071024:
            if(var5.equals("objectanimatorSetDuration")) {
               var8 = 251;
               break label3193;
            }
            break;
         case -1886802639:
            if(var5.equals("soundpoolLoad")) {
               var8 = 238;
               break label3193;
            }
            break;
         case -1834369666:
            if(var5.equals("setBitmapFileBrightness")) {
               var8 = 290;
               break label3193;
            }
            break;
         case -1812313351:
            if(var5.equals("setColorFilter")) {
               var8 = 117;
               break label3193;
            }
            break;
         case -1778201036:
            if(var5.equals("listSmoothScrollTo")) {
               var8 = 166;
               break label3193;
            }
            break;
         case -1776922004:
            if(var5.equals("toString")) {
               var8 = 72;
               break label3193;
            }
            break;
         case -1749698255:
            if(var5.equals("mediaplayerPause")) {
               var8 = 228;
               break label3193;
            }
            break;
         case -1747734390:
            if(var5.equals("mediaplayerReset")) {
               var8 = 232;
               break label3193;
            }
            break;
         case -1746380899:
            if(var5.equals("mediaplayerStart")) {
               var8 = 227;
               break label3193;
            }
            break;
         case -1718917155:
            if(var5.equals("mediaplayerSeek")) {
               var8 = 229;
               break label3193;
            }
            break;
         case -1699631195:
            if(var5.equals("isDrawerOpen")) {
               var8 = 105;
               break label3193;
            }
            break;
         case -1699349926:
            if(var5.equals("objectanimatorSetRepeatMode")) {
               var8 = 252;
               break label3193;
            }
            break;
         case -1684072208:
            if(var5.equals("intentSetData")) {
               var8 = 123;
               break label3193;
            }
            break;
         case -1679834825:
            if(var5.equals("setTrackResource")) {
               var8 = 242;
               break label3193;
            }
            break;
         case -1678257956:
            if(var5.equals("gridSetCustomViewData")) {
               var8 = 160;
               break label3193;
            }
            break;
         case -1666623936:
            if(var5.equals("speechToTextShutdown")) {
               var8 = 311;
               break label3193;
            }
            break;
         case -1573371685:
            if(var5.equals("stringJoin")) {
               var8 = 58;
               break label3193;
            }
            break;
         case -1541653284:
            if(var5.equals("objectanimatorStart")) {
               var8 = 255;
               break label3193;
            }
            break;
         case -1530840255:
            if(var5.equals("stringIndex")) {
               var8 = 59;
               break label3193;
            }
            break;
         case -1528850031:
            if(var5.equals("startActivity")) {
               var8 = 128;
               break label3193;
            }
            break;
         case -1526161572:
            if(var5.equals("setBgColor")) {
               var8 = 113;
               break label3193;
            }
            break;
         case -1513446476:
            if(var5.equals("dialogCancelButton")) {
               var8 = 224;
               break label3193;
            }
            break;
         case -1512519571:
            if(var5.equals("definedFunc")) {
               var8 = 0;
               break label3193;
            }
            break;
         case -1483954587:
            if(var5.equals("fileutilisdir")) {
               var8 = 272;
               break label3193;
            }
            break;
         case -1477942289:
            if(var5.equals("mediaplayerIsLooping")) {
               var8 = 236;
               break label3193;
            }
            break;
         case -1471049951:
            if(var5.equals("fileutilwrite")) {
               var8 = 265;
               break label3193;
            }
            break;
         case -1440042085:
            if(var5.equals("spnSetSelection")) {
               var8 = 169;
               break label3193;
            }
            break;
         case -1438040951:
            if(var5.equals("seekBarGetMax")) {
               var8 = 246;
               break label3193;
            }
            break;
         case -1422112391:
            if(var5.equals("bluetoothConnectIsBluetoothEnabled")) {
               var8 = 318;
               break label3193;
            }
            break;
         case -1405157727:
            if(var5.equals("fileutilmakedir")) {
               var8 = 270;
               break label3193;
            }
            break;
         case -1385076635:
            if(var5.equals("dialogShow")) {
               var8 = 222;
               break label3193;
            }
            break;
         case -1384861688:
            if(var5.equals("getAtListInt")) {
               var8 = 19;
               break label3193;
            }
            break;
         case -1384858251:
            if(var5.equals("getAtListMap")) {
               var8 = 29;
               break label3193;
            }
            break;
         case -1384851894:
            if(var5.equals("getAtListStr")) {
               var8 = 24;
               break label3193;
            }
            break;
         case -1377080719:
            if(var5.equals("decreaseInt")) {
               var8 = 6;
               break label3193;
            }
            break;
         case -1376608975:
            if(var5.equals("calendarSetTime")) {
               var8 = 140;
               break label3193;
            }
            break;
         case -1361468284:
            if(var5.equals("viewOnClick")) {
               var8 = 104;
               break label3193;
            }
            break;
         case -1348085287:
            if(var5.equals("mapViewZoomIn")) {
               var8 = 191;
               break label3193;
            }
            break;
         case -1348084945:
            if(var5.equals("mapViewZoomTo")) {
               var8 = 190;
               break label3193;
            }
            break;
         case -1304067438:
            if(var5.equals("firebaseDelete")) {
               var8 = 206;
               break label3193;
            }
            break;
         case -1272546178:
            if(var5.equals("dialogSetTitle")) {
               var8 = 220;
               break label3193;
            }
            break;
         case -1271141237:
            if(var5.equals("clearList")) {
               var8 = 37;
               break label3193;
            }
            break;
         case -1249367264:
            if(var5.equals("getArg")) {
               var8 = 1;
               break label3193;
            }
            break;
         case -1249347599:
            if(var5.equals("getVar")) {
               var8 = 2;
               break label3193;
            }
            break;
         case -1217704075:
            if(var5.equals("objectanimatorSetValue")) {
               var8 = 249;
               break label3193;
            }
            break;
         case -1206794099:
            if(var5.equals("getLocationX")) {
               var8 = 155;
               break label3193;
            }
            break;
         case -1206794098:
            if(var5.equals("getLocationY")) {
               var8 = 156;
               break label3193;
            }
            break;
         case -1195899442:
            if(var5.equals("bluetoothConnectSendData")) {
               var8 = 317;
               break label3193;
            }
            break;
         case -1192544266:
            if(var5.equals("ifElse")) {
               var8 = 41;
               break label3193;
            }
            break;
         case -1185284274:
            if(var5.equals("gyroscopeStopListen")) {
               var8 = 219;
               break label3193;
            }
            break;
         case -1182878167:
            if(var5.equals("firebaseauthGetUid")) {
               var8 = 213;
               break label3193;
            }
            break;
         case -1160374245:
            if(var5.equals("bluetoothConnectReadyConnectionToUuid")) {
               var8 = 313;
               break label3193;
            }
            break;
         case -1149848189:
            if(var5.equals("toStringFormat")) {
               var8 = 74;
               break label3193;
            }
            break;
         case -1149458632:
            if(var5.equals("objectanimatorSetRepeatCount")) {
               var8 = 253;
               break label3193;
            }
            break;
         case -1143684675:
            if(var5.equals("firebaseauthGetCurrentUser")) {
               var8 = 212;
               break label3193;
            }
            break;
         case -1139353316:
            if(var5.equals("setListMap")) {
               var8 = 30;
               break label3193;
            }
            break;
         case -1137582698:
            if(var5.equals("toLowerCase")) {
               var8 = 71;
               break label3193;
            }
            break;
         case -1123431291:
            if(var5.equals("calnedarViewSetMaxDate")) {
               var8 = 186;
               break label3193;
            }
            break;
         case -1107376988:
            if(var5.equals("webViewGoForward")) {
               var8 = 177;
               break label3193;
            }
            break;
         case -1106141754:
            if(var5.equals("webViewCanGoBack")) {
               var8 = 174;
               break label3193;
            }
            break;
         case -1094491139:
            if(var5.equals("seekBarSetMax")) {
               var8 = 245;
               break label3193;
            }
            break;
         case -1083547183:
            if(var5.equals("spnSetCustomViewData")) {
               var8 = 160;
               break label3193;
            }
            break;
         case -1081400230:
            if(var5.equals("mapGet")) {
               var8 = 10;
               break label3193;
            }
            break;
         case -1081391085:
            if(var5.equals("mapPut")) {
               var8 = 9;
               break label3193;
            }
            break;
         case -1081250015:
            if(var5.equals("mathPi")) {
               var8 = 83;
               break label3193;
            }
            break;
         case -1069525505:
            if(var5.equals("pagerSetCustomViewData")) {
               var8 = 160;
               break label3193;
            }
            break;
         case -1063598745:
            if(var5.equals("resizeBitmapFileRetainRatio")) {
               var8 = 281;
               break label3193;
            }
            break;
         case -1043233275:
            if(var5.equals("mediaplayerGetDuration")) {
               var8 = 231;
               break label3193;
            }
            break;
         case -1033658254:
            if(var5.equals("mathGetDisplayWidth")) {
               var8 = 81;
               break label3193;
            }
            break;
         case -1021852352:
            if(var5.equals("objectanimatorCancel")) {
               var8 = 256;
               break label3193;
            }
            break;
         case -1007787615:
            if(var5.equals("mediaplayerSetLooping")) {
               var8 = 235;
               break label3193;
            }
            break;
         case -996870276:
            if(var5.equals("insertMapToList")) {
               var8 = 33;
               break label3193;
            }
            break;
         case -995908985:
            if(var5.equals("soundpoolCreate")) {
               var8 = 237;
               break label3193;
            }
            break;
         case -941420147:
            if(var5.equals("fileSetFileName")) {
               var8 = 130;
               break label3193;
            }
            break;
         case -938285885:
            if(var5.equals("random")) {
               var8 = 56;
               break label3193;
            }
            break;
         case -934531685:
            if(var5.equals("repeat")) {
               var8 = 39;
               break label3193;
            }
            break;
         case -918173448:
            if(var5.equals("listGetCheckedPosition")) {
               var8 = 163;
               break label3193;
            }
            break;
         case -917343271:
            if(var5.equals("getJpegRotate")) {
               var8 = 292;
               break label3193;
            }
            break;
         case -911199919:
            if(var5.equals("objectanimatorSetProperty")) {
               var8 = 248;
               break label3193;
            }
            break;
         case -903177036:
            if(var5.equals("resizeBitmapFileWithRoundedBorder")) {
               var8 = 284;
               break label3193;
            }
            break;
         case -883988307:
            if(var5.equals("dialogSetMessage")) {
               var8 = 221;
               break label3193;
            }
            break;
         case -869293886:
            if(var5.equals("finishActivity")) {
               var8 = 129;
               break label3193;
            }
            break;
         case -854558288:
            if(var5.equals("setVisible")) {
               var8 = 141;
               break label3193;
            }
            break;
         case -853550561:
            if(var5.equals("timerCancel")) {
               var8 = 202;
               break label3193;
            }
            break;
         case -831887360:
            if(var5.equals("textToSpeechShutdown")) {
               var8 = 308;
               break label3193;
            }
            break;
         case -733318734:
            if(var5.equals("strToListMap")) {
               var8 = 78;
               break label3193;
            }
            break;
         case -697616870:
            if(var5.equals("camerastarttakepicture")) {
               var8 = 294;
               break label3193;
            }
            break;
         case -677662361:
            if(var5.equals("forever")) {
               var8 = 38;
               break label3193;
            }
            break;
         case -668992194:
            if(var5.equals("stringReplaceAll")) {
               var8 = 66;
               break label3193;
            }
            break;
         case -664474111:
            if(var5.equals("intentSetFlags")) {
               var8 = 126;
               break label3193;
            }
            break;
         case -649691581:
            if(var5.equals("objectanimatorSetInterpolator")) {
               var8 = 254;
               break label3193;
            }
            break;
         case -636363854:
            if(var5.equals("webViewGetUrl")) {
               var8 = 172;
               break label3193;
            }
            break;
         case -628607128:
            if(var5.equals("webViewGoBack")) {
               var8 = 176;
               break label3193;
            }
            break;
         case -621198621:
            if(var5.equals("speechToTextStartListening")) {
               var8 = 309;
               break label3193;
            }
            break;
         case -602241037:
            if(var5.equals("fileutilcopy")) {
               var8 = 266;
               break label3193;
            }
            break;
         case -601942961:
            if(var5.equals("fileutilmove")) {
               var8 = 267;
               break label3193;
            }
            break;
         case -601804268:
            if(var5.equals("fileutilread")) {
               var8 = 264;
               break label3193;
            }
            break;
         case -578987803:
            if(var5.equals("setChecked")) {
               var8 = 157;
               break label3193;
            }
            break;
         case -509946902:
            if(var5.equals("spnRefresh")) {
               var8 = 168;
               break label3193;
            }
            break;
         case -439342016:
            if(var5.equals("webViewClearHistory")) {
               var8 = 179;
               break label3193;
            }
            break;
         case -437272040:
            if(var5.equals("bluetoothConnectGetRandomUuid")) {
               var8 = 322;
               break label3193;
            }
            break;
         case -425293664:
            if(var5.equals("setClickable")) {
               var8 = 142;
               break label3193;
            }
            break;
         case -418212114:
            if(var5.equals("firebaseGetChildren")) {
               var8 = 207;
               break label3193;
            }
            break;
         case -411705840:
            if(var5.equals("fileSetData")) {
               var8 = 132;
               break label3193;
            }
            break;
         case -399551817:
            if(var5.equals("toUpperCase")) {
               var8 = 70;
               break label3193;
            }
            break;
         case -390304998:
            if(var5.equals("mapViewAddMarker")) {
               var8 = 193;
               break label3193;
            }
            break;
         case -356866884:
            if(var5.equals("webViewSetCacheMode")) {
               var8 = 173;
               break label3193;
            }
            break;
         case -353129373:
            if(var5.equals("calendarDiff")) {
               var8 = 138;
               break label3193;
            }
            break;
         case -329562760:
            if(var5.equals("insertListInt")) {
               var8 = 18;
               break label3193;
            }
            break;
         case -329559323:
            if(var5.equals("insertListMap")) {
               var8 = 28;
               break label3193;
            }
            break;
         case -329552966:
            if(var5.equals("insertListStr")) {
               var8 = 23;
               break label3193;
            }
            break;
         case -322651344:
            if(var5.equals("stringEquals")) {
               var8 = 62;
               break label3193;
            }
            break;
         case -283328259:
            if(var5.equals("intentPutExtra")) {
               var8 = 125;
               break label3193;
            }
            break;
         case -258774775:
            if(var5.equals("closeDrawer")) {
               var8 = 107;
               break label3193;
            }
            break;
         case -247015294:
            if(var5.equals("mediaplayerRelease")) {
               var8 = 233;
               break label3193;
            }
            break;
         case -208762465:
            if(var5.equals("toStringWithDecimal")) {
               var8 = 73;
               break label3193;
            }
            break;
         case -189292433:
            if(var5.equals("stringSub")) {
               var8 = 61;
               break label3193;
            }
            break;
         case -152473824:
            if(var5.equals("firebaseauthIsLoggedIn")) {
               var8 = 211;
               break label3193;
            }
            break;
         case -149850417:
            if(var5.equals("fileutilisexist")) {
               var8 = 269;
               break label3193;
            }
            break;
         case -133532073:
            if(var5.equals("stringLength")) {
               var8 = 57;
               break label3193;
            }
            break;
         case -96313603:
            if(var5.equals("containListInt")) {
               var8 = 21;
               break label3193;
            }
            break;
         case -96310166:
            if(var5.equals("containListMap")) {
               var8 = 31;
               break label3193;
            }
            break;
         case -96303809:
            if(var5.equals("containListStr")) {
               var8 = 26;
               break label3193;
            }
            break;
         case -83301935:
            if(var5.equals("webViewZoomIn")) {
               var8 = 181;
               break label3193;
            }
            break;
         case -83186725:
            if(var5.equals("openDrawer")) {
               var8 = 106;
               break label3193;
            }
            break;
         case -75125341:
            if(var5.equals("getText")) {
               var8 = 112;
               break label3193;
            }
            break;
         case -60494417:
            if(var5.equals("vibratorAction")) {
               var8 = 199;
               break label3193;
            }
            break;
         case -60048101:
            if(var5.equals("firebaseauthResetPassword")) {
               var8 = 214;
               break label3193;
            }
            break;
         case -24451690:
            if(var5.equals("dialogOkButton")) {
               var8 = 223;
               break label3193;
            }
            break;
         case -14362103:
            if(var5.equals("bluetoothConnectIsBluetoothActivated")) {
               var8 = 319;
               break label3193;
            }
            break;
         case -10599306:
            if(var5.equals("firebaseauthCreateUser")) {
               var8 = 208;
               break label3193;
            }
            break;
         case -9742826:
            if(var5.equals("firebaseGetPushKey")) {
               var8 = 205;
               break label3193;
            }
            break;
         case 37:
            if(var5.equals("%")) {
               var8 = 50;
               break label3193;
            }
            break;
         case 42:
            if(var5.equals("*")) {
               var8 = 48;
               break label3193;
            }
            break;
         case 43:
            if(var5.equals("+")) {
               var8 = 46;
               break label3193;
            }
            break;
         case 45:
            if(var5.equals("-")) {
               var8 = 47;
               break label3193;
            }
            break;
         case 47:
            if(var5.equals("/")) {
               var8 = 49;
               break label3193;
            }
            break;
         case 60:
            if(var5.equals("<")) {
               var8 = 52;
               break label3193;
            }
            break;
         case 61:
            if(var5.equals("=")) {
               var8 = 53;
               break label3193;
            }
            break;
         case 62:
            if(var5.equals(">")) {
               var8 = 51;
               break label3193;
            }
            break;
         case 1216:
            if(var5.equals("&&")) {
               var8 = 54;
               break label3193;
            }
            break;
         case 3357:
            if(var5.equals("if")) {
               var8 = 40;
               break label3193;
            }
            break;
         case 3968:
            if(var5.equals("||")) {
               var8 = 55;
               break label3193;
            }
            break;
         case 109267:
            if(var5.equals("not")) {
               var8 = 45;
               break label3193;
            }
            break;
         case 3568674:
            if(var5.equals("trim")) {
               var8 = 69;
               break label3193;
            }
            break;
         case 3569038:
            if(var5.equals("true")) {
               var8 = 43;
               break label3193;
            }
            break;
         case 8255701:
            if(var5.equals("calendarFormat")) {
               var8 = 137;
               break label3193;
            }
            break;
         case 16308074:
            if(var5.equals("resizeBitmapFileToCircle")) {
               var8 = 283;
               break label3193;
            }
            break;
         case 25469951:
            if(var5.equals("bluetoothConnectActivateBluetooth")) {
               var8 = 320;
               break label3193;
            }
            break;
         case 27679870:
            if(var5.equals("calendarGetNow")) {
               var8 = 134;
               break label3193;
            }
            break;
         case 56167279:
            if(var5.equals("setBitmapFileContrast")) {
               var8 = 291;
               break label3193;
            }
            break;
         case 61585857:
            if(var5.equals("firebasePush")) {
               var8 = 204;
               break label3193;
            }
            break;
         case 94001407:
            if(var5.equals("break")) {
               var8 = 42;
               break label3193;
            }
            break;
         case 97196323:
            if(var5.equals(var7)) {
               var8 = 44;
               break label3193;
            }
            break;
         case 103668285:
            if(var5.equals("mathE")) {
               var8 = 84;
               break label3193;
            }
            break;
         case 125431087:
            if(var5.equals("speechToTextStopListening")) {
               var8 = 310;
               break label3193;
            }
            break;
         case 134874756:
            if(var5.equals("listSetCustomViewData")) {
               var8 = 160;
               break label3193;
            }
            break;
         case 152967761:
            if(var5.equals("mapClear")) {
               var8 = 14;
               break label3193;
            }
            break;
         case 163812602:
            if(var5.equals("cropBitmapFileFromCenter")) {
               var8 = 285;
               break label3193;
            }
            break;
         case 168740282:
            if(var5.equals("mapToStr")) {
               var8 = 77;
               break label3193;
            }
            break;
         case 182549637:
            if(var5.equals("setEnable")) {
               var8 = 108;
               break label3193;
            }
            break;
         case 207764385:
            if(var5.equals("calendarViewGetDate")) {
               var8 = 183;
               break label3193;
            }
            break;
         case 255417137:
            if(var5.equals("adViewLoadAd")) {
               var8 = 187;
               break label3193;
            }
            break;
         case 262073061:
            if(var5.equals("bluetoothConnectReadyConnection")) {
               var8 = 312;
               break label3193;
            }
            break;
         case 276674391:
            if(var5.equals("mapViewMoveCamera")) {
               var8 = 189;
               break label3193;
            }
            break;
         case 297379706:
            if(var5.equals("textToSpeechSetSpeechRate")) {
               var8 = 304;
               break label3193;
            }
            break;
         case 300372142:
            if(var5.equals("mathAcos")) {
               var8 = 97;
               break label3193;
            }
            break;
         case 300387327:
            if(var5.equals("mathAsin")) {
               var8 = 96;
               break label3193;
            }
            break;
         case 300388040:
            if(var5.equals("mathAtan")) {
               var8 = 98;
               break label3193;
            }
            break;
         case 300433453:
            if(var5.equals("mathCeil")) {
               var8 = 91;
               break label3193;
            }
            break;
         case 300921928:
            if(var5.equals("mathSqrt")) {
               var8 = 88;
               break label3193;
            }
            break;
         case 317453636:
            if(var5.equals("textToSpeechIsSpeaking")) {
               var8 = 306;
               break label3193;
            }
            break;
         case 342026220:
            if(var5.equals("interstitialadShow")) {
               var8 = 260;
               break label3193;
            }
            break;
         case 348377823:
            if(var5.equals("soundpoolStreamPlay")) {
               var8 = 239;
               break label3193;
            }
            break;
         case 348475309:
            if(var5.equals("soundpoolStreamStop")) {
               var8 = 240;
               break label3193;
            }
            break;
         case 362605827:
            if(var5.equals("recyclerSetCustomViewData")) {
               var8 = 160;
               break label3193;
            }
            break;
         case 389111867:
            if(var5.equals("spnSetData")) {
               var8 = 167;
               break label3193;
            }
            break;
         case 397166713:
            if(var5.equals("getEnable")) {
               var8 = 109;
               break label3193;
            }
            break;
         case 401012285:
            if(var5.equals("getTranslationX")) {
               var8 = 148;
               break label3193;
            }
            break;
         case 401012286:
            if(var5.equals("getTranslationY")) {
               var8 = 150;
               break label3193;
            }
            break;
         case 404247683:
            if(var5.equals("calendarAdd")) {
               var8 = 135;
               break label3193;
            }
            break;
         case 404265028:
            if(var5.equals("calendarSet")) {
               var8 = 136;
               break label3193;
            }
            break;
         case 442768763:
            if(var5.equals("mapGetAllKeys")) {
               var8 = 16;
               break label3193;
            }
            break;
         case 463560551:
            if(var5.equals("mapContainKey")) {
               var8 = 11;
               break label3193;
            }
            break;
         case 463594049:
            if(var5.equals("objectanimatorSetFromTo")) {
               var8 = 250;
               break label3193;
            }
            break;
         case 470160234:
            if(var5.equals("fileutilGetLastSegmentPath")) {
               var8 = 277;
               break label3193;
            }
            break;
         case 475815924:
            if(var5.equals("setTextColor")) {
               var8 = 115;
               break label3193;
            }
            break;
         case 481850295:
            if(var5.equals("resizeBitmapFileToSquare")) {
               var8 = 282;
               break label3193;
            }
            break;
         case 490702942:
            if(var5.equals("filepickerstartpickfiles")) {
               var8 = 293;
               break label3193;
            }
            break;
         case 501171279:
            if(var5.equals("mathToDegree")) {
               var8 = 103;
               break label3193;
            }
            break;
         case 530759231:
            if(var5.equals("progressBarSetIndeterminate")) {
               var8 = 302;
               break label3193;
            }
            break;
         case 548860462:
            if(var5.equals("webViewClearCache")) {
               var8 = 178;
               break label3193;
            }
            break;
         case 556217437:
            if(var5.equals("setRotate")) {
               var8 = 143;
               break label3193;
            }
            break;
         case 571046965:
            if(var5.equals("scaleBitmapFile")) {
               var8 = 287;
               break label3193;
            }
            break;
         case 573208400:
            if(var5.equals("setScaleX")) {
               var8 = 151;
               break label3193;
            }
            break;
         case 573208401:
            if(var5.equals("setScaleY")) {
               var8 = 153;
               break label3193;
            }
            break;
         case 573295520:
            if(var5.equals("listGetCheckedCount")) {
               var8 = 165;
               break label3193;
            }
            break;
         case 601235430:
            if(var5.equals("currentTime")) {
               var8 = 68;
               break label3193;
            }
            break;
         case 610313513:
            if(var5.equals("getMapInList")) {
               var8 = 34;
               break label3193;
            }
            break;
         case 615286641:
            if(var5.equals("dialogNeutralButton")) {
               var8 = 225;
               break label3193;
            }
            break;
         case 657721930:
            if(var5.equals("setVarInt")) {
               var8 = 4;
               break label3193;
            }
            break;
         case 683193060:
            if(var5.equals("bluetoothConnectStartConnection")) {
               var8 = 314;
               break label3193;
            }
            break;
         case 725249532:
            if(var5.equals("intentSetAction")) {
               var8 = 122;
               break label3193;
            }
            break;
         case 726487524:
            if(var5.equals("mathFloor")) {
               var8 = 92;
               break label3193;
            }
            break;
         case 726877492:
            if(var5.equals("mapViewSetMarkerIcon")) {
               var8 = 197;
               break label3193;
            }
            break;
         case 726887785:
            if(var5.equals("mapViewSetMarkerInfo")) {
               var8 = 194;
               break label3193;
            }
            break;
         case 732108347:
            if(var5.equals("mathLog10")) {
               var8 = 101;
               break label3193;
            }
            break;
         case 737664870:
            if(var5.equals("mathRound")) {
               var8 = 90;
               break label3193;
            }
            break;
         case 738846120:
            if(var5.equals("textToSpeechSetPitch")) {
               var8 = 303;
               break label3193;
            }
            break;
         case 747168008:
            if(var5.equals("mapCreateNew")) {
               var8 = 8;
               break label3193;
            }
            break;
         case 754442829:
            if(var5.equals("increaseInt")) {
               var8 = 5;
               break label3193;
            }
            break;
         case 762282303:
            if(var5.equals("indexListInt")) {
               var8 = 20;
               break label3193;
            }
            break;
         case 762292097:
            if(var5.equals("indexListStr")) {
               var8 = 25;
               break label3193;
            }
            break;
         case 770834513:
            if(var5.equals("getRotate")) {
               var8 = 144;
               break label3193;
            }
            break;
         case 787825476:
            if(var5.equals("getScaleX")) {
               var8 = 152;
               break label3193;
            }
            break;
         case 787825477:
            if(var5.equals("getScaleY")) {
               var8 = 154;
               break label3193;
            }
            break;
         case 797861524:
            if(var5.equals("addMapToList")) {
               var8 = 32;
               break label3193;
            }
            break;
         case 836692861:
            if(var5.equals("mapSize")) {
               var8 = 13;
               break label3193;
            }
            break;
         case 840973386:
            if(var5.equals("mathAbs")) {
               var8 = 89;
               break label3193;
            }
            break;
         case 840975711:
            if(var5.equals("mathCos")) {
               var8 = 94;
               break label3193;
            }
            break;
         case 840977909:
            if(var5.equals("mathExp")) {
               var8 = 99;
               break label3193;
            }
            break;
         case 840984348:
            if(var5.equals("mathLog")) {
               var8 = 100;
               break label3193;
            }
            break;
         case 840984892:
            if(var5.equals("mathMax")) {
               var8 = 87;
               break label3193;
            }
            break;
         case 840985130:
            if(var5.equals("mathMin")) {
               var8 = 86;
               break label3193;
            }
            break;
         case 840988208:
            if(var5.equals("mathPow")) {
               var8 = 85;
               break label3193;
            }
            break;
         case 840990896:
            if(var5.equals("mathSin")) {
               var8 = 93;
               break label3193;
            }
            break;
         case 840991609:
            if(var5.equals("mathTan")) {
               var8 = 95;
               break label3193;
            }
            break;
         case 845089750:
            if(var5.equals("setVarString")) {
               var8 = 7;
               break label3193;
            }
            break;
         case 848786445:
            if(var5.equals("objectanimatorSetTarget")) {
               var8 = 247;
               break label3193;
            }
            break;
         case 858248741:
            if(var5.equals("calendarGetTime")) {
               var8 = 139;
               break label3193;
            }
            break;
         case 898187172:
            if(var5.equals("mathToRadian")) {
               var8 = 102;
               break label3193;
            }
            break;
         case 932259189:
            if(var5.equals("setBgResource")) {
               var8 = 114;
               break label3193;
            }
            break;
         case 937017988:
            if(var5.equals("gyroscopeStartListen")) {
               var8 = 218;
               break label3193;
            }
            break;
         case 948234497:
            if(var5.equals("webViewStopLoading")) {
               var8 = 180;
               break label3193;
            }
            break;
         case 950609198:
            if(var5.equals("setBitmapFileColorFilter")) {
               var8 = 289;
               break label3193;
            }
            break;
         case 1053179400:
            if(var5.equals("mapViewSetMarkerColor")) {
               var8 = 196;
               break label3193;
            }
            break;
         case 1068548733:
            if(var5.equals("mathGetDip")) {
               var8 = 80;
               break label3193;
            }
            break;
         case 1086207657:
            if(var5.equals("fileutildelete")) {
               var8 = 268;
               break label3193;
            }
            break;
         case 1088879149:
            if(var5.equals("setHintTextColor")) {
               var8 = 298;
               break label3193;
            }
            break;
         case 1090517587:
            if(var5.equals("getPackageDataDir")) {
               var8 = 279;
               break label3193;
            }
            break;
         case 1102670563:
            if(var5.equals("requestnetworkSetHeaders")) {
               var8 = 300;
               break label3193;
            }
            break;
         case 1129709718:
            if(var5.equals("setImageUrl")) {
               var8 = 296;
               break label3193;
            }
            break;
         case 1142897724:
            if(var5.equals("firebaseauthSignInUser")) {
               var8 = 209;
               break label3193;
            }
            break;
         case 1156598140:
            if(var5.equals("fileutilEndsWith")) {
               var8 = 276;
               break label3193;
            }
            break;
         case 1159035162:
            if(var5.equals("mapViewZoomOut")) {
               var8 = 192;
               break label3193;
            }
            break;
         case 1160674468:
            if(var5.equals("lengthList")) {
               var8 = 36;
               break label3193;
            }
            break;
         case 1162069698:
            if(var5.equals("setThumbResource")) {
               var8 = 241;
               break label3193;
            }
            break;
         case 1179719371:
            if(var5.equals("stringLastIndex")) {
               var8 = 60;
               break label3193;
            }
            break;
         case 1187505507:
            if(var5.equals("stringReplace")) {
               var8 = 64;
               break label3193;
            }
            break;
         case 1216249183:
            if(var5.equals("firebasestorageDelete")) {
               var8 = 263;
               break label3193;
            }
            break;
         case 1219071185:
            if(var5.equals("firebasestorageUploadFile")) {
               var8 = 261;
               break label3193;
            }
            break;
         case 1219299503:
            if(var5.equals("objectanimatorIsRunning")) {
               var8 = 257;
               break label3193;
            }
            break;
         case 1220078450:
            if(var5.equals("addSourceDirectly")) {
               var8 = 75;
               break label3193;
            }
            break;
         case 1236956449:
            if(var5.equals("mediaplayerCreate")) {
               var8 = 226;
               break label3193;
            }
            break;
         case 1240510514:
            if(var5.equals("intentSetScreen")) {
               var8 = 124;
               break label3193;
            }
            break;
         case 1242107556:
            if(var5.equals("fileutilisfile")) {
               var8 = 273;
               break label3193;
            }
            break;
         case 1252547704:
            if(var5.equals("listMapToStr")) {
               var8 = 79;
               break label3193;
            }
            break;
         case 1280029577:
            if(var5.equals("requestFocus")) {
               var8 = 118;
               break label3193;
            }
            break;
         case 1303367340:
            if(var5.equals("textToSpeechStop")) {
               var8 = 307;
               break label3193;
            }
            break;
         case 1305932583:
            if(var5.equals("spnGetSelection")) {
               var8 = 170;
               break label3193;
            }
            break;
         case 1311764809:
            if(var5.equals("setTranslationX")) {
               var8 = 147;
               break label3193;
            }
            break;
         case 1311764810:
            if(var5.equals("setTranslationY")) {
               var8 = 149;
               break label3193;
            }
            break;
         case 1313527577:
            if(var5.equals("setTypeface")) {
               var8 = 111;
               break label3193;
            }
            break;
         case 1315302372:
            if(var5.equals("fileutillength")) {
               var8 = 274;
               break label3193;
            }
            break;
         case 1330354473:
            if(var5.equals("firebaseauthSignInAnonymously")) {
               var8 = 210;
               break label3193;
            }
            break;
         case 1343794064:
            if(var5.equals("listSetItemChecked")) {
               var8 = 162;
               break label3193;
            }
            break;
         case 1348133645:
            if(var5.equals("stringReplaceFirst")) {
               var8 = 65;
               break label3193;
            }
            break;
         case 1387622940:
            if(var5.equals("setAlpha")) {
               var8 = 145;
               break label3193;
            }
            break;
         case 1395026457:
            if(var5.equals("setImage")) {
               var8 = 116;
               break label3193;
            }
            break;
         case 1397501021:
            if(var5.equals("listRefresh")) {
               var8 = 161;
               break label3193;
            }
            break;
         case 1405084438:
            if(var5.equals("setTitle")) {
               var8 = 121;
               break label3193;
            }
            break;
         case 1410284340:
            if(var5.equals("seekBarSetProgress")) {
               var8 = 243;
               break label3193;
            }
            break;
         case 1431171391:
            if(var5.equals("mapRemoveKey")) {
               var8 = 12;
               break label3193;
            }
            break;
         case 1437288110:
            if(var5.equals("getPublicDir")) {
               var8 = 280;
               break label3193;
            }
            break;
         case 1470831563:
            if(var5.equals("intentGetString")) {
               var8 = 127;
               break label3193;
            }
            break;
         case 1498864168:
            if(var5.equals("seekBarGetProgress")) {
               var8 = 244;
               break label3193;
            }
            break;
         case 1601394299:
            if(var5.equals("listGetCheckedPositions")) {
               var8 = 164;
               break label3193;
            }
            break;
         case 1633341847:
            if(var5.equals("timerAfter")) {
               var8 = 200;
               break label3193;
            }
            break;
         case 1635356258:
            if(var5.equals("requestnetworkStartRequestNetwork")) {
               var8 = 301;
               break label3193;
            }
            break;
         case 1637498582:
            if(var5.equals("timerEvery")) {
               var8 = 201;
               break label3193;
            }
            break;
         case 1695890133:
            if(var5.equals("fileutilStartsWith")) {
               var8 = 275;
               break label3193;
            }
            break;
         case 1712613410:
            if(var5.equals("webViewZoomOut")) {
               var8 = 182;
               break label3193;
            }
            break;
         case 1749552744:
            if(var5.equals("textToSpeechSpeak")) {
               var8 = 305;
               break label3193;
            }
            break;
         case 1764351209:
            if(var5.equals("deleteList")) {
               var8 = 35;
               break label3193;
            }
            break;
         case 1775620400:
            if(var5.equals("strToMap")) {
               var8 = 76;
               break label3193;
            }
            break;
         case 1779174257:
            if(var5.equals("getChecked")) {
               var8 = 158;
               break label3193;
            }
            break;
         case 1792552710:
            if(var5.equals("rotateBitmapFile")) {
               var8 = 286;
               break label3193;
            }
            break;
         case 1814870108:
            if(var5.equals("doToast")) {
               var8 = 119;
               break label3193;
            }
            break;
         case 1820536363:
            if(var5.equals("interstitialadCreate")) {
               var8 = 258;
               break label3193;
            }
            break;
         case 1823151876:
            if(var5.equals("fileGetData")) {
               var8 = 131;
               break label3193;
            }
            break;
         case 1848365301:
            if(var5.equals("mapViewSetMapType")) {
               var8 = 188;
               break label3193;
            }
            break;
         case 1873103950:
            if(var5.equals("locationManagerRemoveUpdates")) {
               var8 = 324;
               break label3193;
            }
            break;
         case 1883337723:
            if(var5.equals("mathGetDisplayHeight")) {
               var8 = 82;
               break label3193;
            }
            break;
         case 1885231494:
            if(var5.equals("webViewCanGoForward")) {
               var8 = 175;
               break label3193;
            }
            break;
         case 1908132964:
            if(var5.equals("mapViewSetMarkerPosition")) {
               var8 = 195;
               break label3193;
            }
            break;
         case 1908582864:
            if(var5.equals("firebaseStopListen")) {
               var8 = 217;
               break label3193;
            }
            break;
         case 1923980937:
            if(var5.equals("requestnetworkSetParams")) {
               var8 = 299;
               break label3193;
            }
            break;
         case 1941634330:
            if(var5.equals("firebaseAdd")) {
               var8 = 203;
               break label3193;
            }
            break;
         case 1948735400:
            if(var5.equals("getAlpha")) {
               var8 = 146;
               break label3193;
            }
            break;
         case 1964823036:
            if(var5.equals("bluetoothConnectStopConnection")) {
               var8 = 316;
               break label3193;
            }
            break;
         case 1973523807:
            if(var5.equals("mediaplayerIsPlaying")) {
               var8 = 234;
               break label3193;
            }
            break;
         case 1974249461:
            if(var5.equals("skewBitmapFile")) {
               var8 = 288;
               break label3193;
            }
            break;
         case 1976325370:
            if(var5.equals("setImageFilePath")) {
               var8 = 295;
               break label3193;
            }
            break;
         case 1984630281:
            if(var5.equals("setHint")) {
               var8 = 297;
               break label3193;
            }
            break;
         case 1984984239:
            if(var5.equals("setText")) {
               var8 = 110;
               break label3193;
            }
            break;
         case 2017929665:
            if(var5.equals("calendarViewSetMinDate")) {
               var8 = 185;
               break label3193;
            }
            break;
         case 2075310296:
            if(var5.equals("interstitialadLoadAd")) {
               var8 = 259;
               break label3193;
            }
            break;
         case 2090179216:
            if(var5.equals("addListInt")) {
               var8 = 17;
               break label3193;
            }
            break;
         case 2090182653:
            if(var5.equals("addListMap")) {
               var8 = 27;
               break label3193;
            }
            break;
         case 2090189010:
            if(var5.equals("addListStr")) {
               var8 = 22;
               break label3193;
            }
            break;
         case 2127377128:
            if(var5.equals("mediaplayerGetCurrent")) {
               var8 = 230;
               break label3193;
            }
            break;
         case 2130649194:
            if(var5.equals("bluetoothConnectGetPairedDevices")) {
               var8 = 321;
               break label3193;
            }
            break;
         case 2138225950:
            if(var5.equals("locationManagerRequestLocationUpdates")) {
               var8 = 323;
               break label3193;
            }
         }

         var8 = -1;
      }

      String var11;
      label3268: {
         String var9 = "\"\"";
         String var10 = "0";
         var11 = "";
         switch(var8) {
         case 0:
            String var26;
            if(var1.parameters.size() <= 0) {
               int var49 = var1.spec.indexOf(" ");
               if(var49 < 0) {
                  StringBuilder var50 = new StringBuilder();
                  var50.append("_");
                  var50.append(var1.spec);
                  var50.append("()");
                  var11 = var1.type;
                  var50.append(ReturnMoreblockManager.getMbEnd(var11));
                  var26 = var50.toString();
               } else {
                  StringBuilder var55 = new StringBuilder();
                  var55.append("_");
                  var55.append(var1.spec.substring(0, var49));
                  var55.append("()");
                  var11 = var1.type;
                  var55.append(ReturnMoreblockManager.getMbEnd(var11));
                  var26 = var55.toString();
               }
            } else {
               int var12 = var1.spec.indexOf(" ");
               String var13 = var1.spec.substring(0, var12);
               StringBuilder var14 = new StringBuilder();
               var14.append("_");
               var14.append(var13);
               var14.append("(");
               String var18 = var14.toString();
               int var19 = 0;
               boolean var20 = false;

               for(boolean var21 = true; var19 < var3.size(); var21 = false) {
                  if(!var21) {
                     StringBuilder var35 = new StringBuilder();
                     var35.append(var18);
                     var35.append(", ");
                     var18 = var35.toString();
                  }

                  String var38 = (String)var3.get(var19);
                  if(var38.length() <= 0) {
                     Gx var42 = (Gx)var1.getParamClassInfo().get(var19);
                     if(var42.b("boolean")) {
                        StringBuilder var43 = new StringBuilder();
                        var43.append(var18);
                        var43.append("true");
                        var18 = var43.toString();
                     } else if(var42.b("double")) {
                        StringBuilder var46 = new StringBuilder();
                        var46.append(var18);
                        var46.append(var10);
                        var18 = var46.toString();
                     } else if(var42.b("String")) {
                        var20 = true;
                     }
                  } else {
                     StringBuilder var39 = new StringBuilder();
                     var39.append(var18);
                     var39.append(var38);
                     var18 = var39.toString();
                  }

                  ++var19;
               }

               StringBuilder var22 = new StringBuilder();
               var22.append(var18);
               var22.append(")");
               var11 = var1.type;
               var22.append(ReturnMoreblockManager.getMbEnd(var11));
               var26 = var22.toString();
               if(var20) {
                  break;
               }
            }

            var7 = var26;
            break label3268;
         case 1:
            String var60 = var1.spec;
            StringBuilder var61 = new StringBuilder();
            var61.append("_");
            var61.append(var60);
            var7 = var61.toString();
            break label3268;
         case 2:
            var7 = var1.spec;
            break label3268;
         case 3:
            String var64 = (String)var3.get(0);
            String var65 = (String)var3.get(1);
            if(var65.length() <= 0) {
               var65 = var7;
            }

            if(var64.length() > 0) {
               var7 = String.format("%s = %s;", new Object[]{var64, var65});
               break label3268;
            }
            break;
         case 4:
            String var66 = (String)var3.get(0);
            String var67 = (String)var3.get(1);
            if(var67.length() > 0) {
               var10 = var67;
            }

            if(var66.length() > 0) {
               var7 = String.format("%s = %s;", new Object[]{var66, var10});
               break label3268;
            }
            break;
         case 5:
            String var68 = (String)var3.get(0);
            if(var68.length() > 0) {
               var7 = String.format("%s++;", new Object[]{var68});
               break label3268;
            }
            break;
         case 6:
            String var69 = (String)var3.get(0);
            if(var69.length() > 0) {
               var7 = String.format("%s--;", new Object[]{var69});
               break label3268;
            }
            break;
         case 7:
            String var70 = (String)var3.get(0);
            String var71 = (String)var3.get(1);
            if(var70.length() > 0) {
               var7 = String.format("%s = %s;", new Object[]{var70, var71});
               break label3268;
            }
            break;
         case 8:
            String var72 = (String)var3.get(0);
            if(var72.length() > 0) {
               var7 = String.format("%s = new HashMap<>();", new Object[]{var72});
               break label3268;
            }
            break;
         case 9:
            String var73 = (String)var3.get(0);
            String var74 = (String)var3.get(1);
            String var75 = (String)var3.get(2);
            if(var74.length() <= 0) {
               var74 = var11;
            }

            if(var75.length() <= 0) {
               var75 = var11;
            }

            if(var73.length() > 0) {
               var7 = String.format("%s.put(%s, %s);", new Object[]{var73, var74, var75});
               break label3268;
            }
            break;
         case 10:
            String var76 = (String)var3.get(0);
            String var77 = (String)var3.get(1);
            if(var77.length() <= 0) {
               var77 = var11;
            }

            if(var76.length() > 0) {
               var7 = String.format("%s.get(%s).toString()", new Object[]{var76, var77});
               break label3268;
            }
            break;
         case 11:
            String var78 = (String)var3.get(0);
            String var79 = (String)var3.get(1);
            if(var79.length() <= 0) {
               var79 = var11;
            }

            if(var78.length() > 0) {
               var7 = String.format("%s.containsKey(%s)", new Object[]{var78, var79});
               break label3268;
            }
            break;
         case 12:
            String var80 = (String)var3.get(0);
            String var81 = (String)var3.get(1);
            if(var81.length() <= 0) {
               var81 = var11;
            }

            if(var80.length() > 0) {
               var7 = String.format("%s.remove(%s);", new Object[]{var80, var81});
               break label3268;
            }
            break;
         case 13:
            String var82 = (String)var3.get(0);
            if(var82.length() > 0) {
               var7 = String.format("%s.size()", new Object[]{var82});
               break label3268;
            }
            break;
         case 14:
            String var83 = (String)var3.get(0);
            if(var83.length() > 0) {
               var7 = String.format("%s.clear();", new Object[]{var83});
               break label3268;
            }
            break;
         case 15:
            String var84 = (String)var3.get(0);
            if(var84.length() > 0) {
               var7 = String.format("%s.isEmpty()", new Object[]{var84});
               break label3268;
            }
            break;
         case 16:
            String var85 = (String)var3.get(0);
            String var86 = (String)var3.get(1);
            if(var85.length() > 0 && var86.length() > 0) {
               var7 = String.format("SketchwareUtil.getAllKeysFromMap(%s, %s);", new Object[]{var85, var86});
               break label3268;
            }
            break;
         case 17:
            String var87 = (String)var3.get(0);
            String var88 = (String)var3.get(1);
            if(var87.length() > 0 && var88.length() > 0) {
               var7 = String.format("%s.add(Double.valueOf(%s));", new Object[]{var88, var87});
               break label3268;
            }
            break;
         case 18:
            String var89 = (String)var3.get(0);
            String var90 = (String)var3.get(1);
            String var91 = (String)var3.get(2);
            if(var89.length() > 0 && var90.length() > 0 && var91.length() > 0) {
               var7 = String.format("%s.add((int)(%s), Double.valueOf(%s));", new Object[]{var91, var90, var89});
               break label3268;
            }
            break;
         case 19:
            String var92 = (String)var3.get(0);
            String var93 = (String)var3.get(1);
            if(var92.length() > 0 && var93.length() > 0) {
               var7 = String.format("%s.get((int)(%s)).doubleValue()", new Object[]{var93, var92});
               break label3268;
            }
            break;
         case 20:
            String var94 = (String)var3.get(0);
            String var95 = (String)var3.get(1);
            if(var94.length() > 0 && var95.length() > 0) {
               var7 = String.format("%s.indexOf(%s)", new Object[]{var95, var94});
               break label3268;
            }
            break;
         case 21:
            String var96 = (String)var3.get(0);
            String var97 = (String)var3.get(1);
            if(var96.length() > 0 && var97.length() > 0) {
               var7 = String.format("%s.contains(%s)", new Object[]{var96, var97});
               break label3268;
            }
            break;
         case 22:
            String var98 = (String)var3.get(0);
            String var99 = (String)var3.get(1);
            if(var98.length() > 0 && var99.length() > 0) {
               var7 = String.format("%s.add(%s);", new Object[]{var99, var98});
               break label3268;
            }
            break;
         case 23:
            String var100 = (String)var3.get(0);
            String var101 = (String)var3.get(1);
            String var102 = (String)var3.get(2);
            if(var100.length() > 0 && var101.length() > 0 && var102.length() > 0) {
               var7 = String.format("%s.add((int)(%s), %s);", new Object[]{var102, var101, var100});
               break label3268;
            }
            break;
         case 24:
            String var103 = (String)var3.get(0);
            String var104 = (String)var3.get(1);
            if(var103.length() > 0 && var104.length() > 0) {
               var7 = String.format("%s.get((int)(%s))", new Object[]{var104, var103});
               break label3268;
            }
            break;
         case 25:
            String var105 = (String)var3.get(0);
            String var106 = (String)var3.get(1);
            if(var105.length() > 0 && var106.length() > 0) {
               var7 = String.format("%s.indexOf(%s)", new Object[]{var106, var105});
               break label3268;
            }
            break;
         case 26:
            String var107 = (String)var3.get(0);
            String var108 = (String)var3.get(1);
            if(var107.length() > 0 && var108.length() > 0) {
               var7 = String.format("%s.contains(%s)", new Object[]{var107, var108});
               break label3268;
            }
            break;
         case 27:
            String var109 = (String)var3.get(0);
            String var110 = (String)var3.get(1);
            String var111 = (String)var3.get(2);
            if(var109.length() <= 0) {
               var109 = var11;
            }

            if(var110.length() <= 0) {
               var110 = var11;
            }

            if(var111.length() > 0) {
               StringBuilder var112 = new StringBuilder();
               var112.append("{\r\n");
               var112.append(String.format("HashMap<String, Object> _item = new HashMap<>();", new Object[0]));
               var112.append("\r\n");
               var112.append(String.format("_item.put(%s, %s);", new Object[]{var109, var110}));
               var112.append("\r\n");
               var112.append(String.format("%s.add(_item);", new Object[]{var111}));
               var112.append("\r\n");
               var112.append("}");
               var112.append("\r\n");
               var7 = var112.toString();
               break label3268;
            }
            break;
         case 28:
            String var122 = (String)var3.get(0);
            String var123 = (String)var3.get(1);
            String var124 = (String)var3.get(2);
            String var125 = (String)var3.get(3);
            if(var122.length() <= 0) {
               var122 = var11;
            }

            if(var123.length() <= 0) {
               var123 = var11;
            }

            if(var124.length() > 0) {
               var10 = var124;
            }

            if(var125.length() > 0) {
               StringBuilder var126 = new StringBuilder();
               var126.append("{\r\n");
               var126.append(String.format("HashMap<String, Object> _item = new HashMap<>();", new Object[0]));
               var126.append("\r\n");
               var126.append(String.format("_item.put(%s, %s);", new Object[]{var122, var123}));
               var126.append("\r\n");
               var126.append(String.format("%s.add((int)%s, _item);", new Object[]{var125, var10}));
               var126.append("\r\n");
               var126.append("}");
               var126.append("\r\n");
               var7 = var126.toString();
               break label3268;
            }
            break;
         case 29:
            String var136 = (String)var3.get(0);
            String var137 = (String)var3.get(1);
            String var138 = (String)var3.get(2);
            if(var136.length() > 0) {
               var10 = var136;
            }

            if(var137.length() <= 0) {
               var137 = var11;
            }

            if(var138.length() > 0) {
               var7 = String.format("%s.get((int)%s).get(%s).toString()", new Object[]{var138, var10, var137});
               break label3268;
            }
            break;
         case 30:
            String var139 = (String)var3.get(0);
            String var140 = (String)var3.get(1);
            String var141 = (String)var3.get(2);
            String var142 = (String)var3.get(3);
            if(var139.length() <= 0) {
               var139 = var11;
            }

            if(var140.length() <= 0) {
               var140 = var11;
            }

            if(var141.length() > 0) {
               var10 = var141;
            }

            if(var142.length() > 0) {
               var7 = String.format("%s.get((int)%s).put(%s, %s);", new Object[]{var142, var10, var139, var140});
               break label3268;
            }
            break;
         case 31:
            String var143 = (String)var3.get(0);
            String var144 = (String)var3.get(1);
            String var145 = (String)var3.get(2);
            if(var144.length() > 0) {
               var10 = var144;
            }

            if(var145.length() <= 0) {
               var145 = var11;
            }

            if(var143.length() > 0) {
               var7 = String.format("%s.get((int)%s).containsKey(%s)", new Object[]{var143, var10, var145});
               break label3268;
            }
            break;
         case 32:
            String var146 = (String)var3.get(0);
            String var147 = (String)var3.get(1);
            if(var146.length() > 0 && var147.length() > 0) {
               var7 = String.format("%s.add(%s);", new Object[]{var147, var146});
               break label3268;
            }
            break;
         case 33:
            String var148 = (String)var3.get(0);
            String var149 = (String)var3.get(1);
            String var150 = (String)var3.get(2);
            if(var149.length() > 0) {
               var10 = var149;
            }

            if(var148.length() > 0 && var150.length() > 0) {
               var7 = String.format("%s.add((int)%s, %s);", new Object[]{var150, var10, var148});
               break label3268;
            }
            break;
         case 34:
            String var151 = (String)var3.get(0);
            String var152 = (String)var3.get(1);
            String var153 = (String)var3.get(2);
            if(var151.length() > 0) {
               var10 = var151;
            }

            if(var152.length() > 0 && var153.length() > 0) {
               var7 = String.format("%s = %s.get((int)%s);", new Object[]{var153, var152, var10});
               break label3268;
            }
            break;
         case 35:
            String var154 = (String)var3.get(0);
            String var155 = (String)var3.get(1);
            if(var154.length() > 0 && var155.length() > 0) {
               var7 = String.format("%s.remove((int)(%s));", new Object[]{var155, var154});
               break label3268;
            }
            break;
         case 36:
            String var156 = (String)var3.get(0);
            if(var156.length() > 0) {
               var7 = String.format("%s.size()", new Object[]{var156});
               break label3268;
            }
            break;
         case 37:
            String var157 = (String)var3.get(0);
            if(var157.length() > 0) {
               var7 = String.format("%s.clear();", new Object[]{var157});
               break label3268;
            }
            break;
         case 38:
            int var158 = var1.subStack1;
            String var159;
            if(var158 >= 0) {
               var159 = this.a(String.valueOf(var158), var11);
            } else {
               var159 = var11;
            }

            var7 = String.format("while(true) {\r\n%s\r\n}", new Object[]{var159});
            break label3268;
         case 39:
            String var160 = (String)var3.get(0);
            int var161 = var1.subStack1;
            String var162;
            if(var161 >= 0) {
               var162 = this.a(String.valueOf(var161), var11);
            } else {
               var162 = var11;
            }

            if(var160.length() > 0) {
               var10 = var160;
            }

            StringBuilder var163 = new StringBuilder();
            var163.append("_repeat");
            var163.append(var1.id);
            String var166 = var163.toString();
            var7 = String.format("for(int %s = 0; %s < (int)(%s); %s++) {\r\n%s\r\n}", new Object[]{var166, var166, var10, var166, var162});
            break label3268;
         case 40:
            String var167 = (String)var3.get(0);
            int var168 = var1.subStack1;
            String var169;
            if(var168 >= 0) {
               var169 = this.a(String.valueOf(var168), var11);
            } else {
               var169 = var11;
            }

            if(var167.length() <= 0) {
               var167 = "true";
            }

            var7 = String.format("if (%s) {\r\n%s\r\n}", new Object[]{var167, var169});
            break label3268;
         case 41:
            String var170 = (String)var3.get(0);
            int var171 = var1.subStack1;
            String var172;
            if(var171 >= 0) {
               var172 = this.a(String.valueOf(var171), var11);
            } else {
               var172 = var11;
            }

            int var173 = var1.subStack2;
            String var174;
            if(var173 >= 0) {
               var174 = this.a(String.valueOf(var173), var11);
            } else {
               var174 = var11;
            }

            if(var170.length() <= 0) {
               var170 = "true";
            }

            var7 = String.format("if (%s) {\r\n%s\r\n}\r\nelse {\r\n%s\r\n}", new Object[]{var170, var172, var174});
            break label3268;
         case 42:
            var7 = "break;";
            break label3268;
         case 43:
         case 44:
            var7 = var1.opCode;
            break label3268;
         case 45:
            String var175 = (String)var3.get(0);
            if(var175.length() > 0) {
               var7 = String.format("!%s", new Object[]{var175});
               break label3268;
            }
            break;
         case 46:
         case 47:
         case 48:
         case 49:
         case 50:
         case 51:
         case 52:
            String var176 = (String)var3.get(0);
            String var177 = (String)var3.get(1);
            if(var176.length() <= 0) {
               var176 = var10;
            }

            if(var177.length() > 0) {
               var10 = var177;
            }

            Object[] var178 = new Object[]{var176, var1.opCode, var10};
            var7 = String.format("%s %s %s", var178);
            break label3268;
         case 53:
            String var179 = (String)var3.get(0);
            String var180 = (String)var3.get(1);
            if(var179.length() <= 0) {
               var179 = var10;
            }

            if(var180.length() > 0) {
               var10 = var180;
            }

            var7 = String.format("%s == %s", new Object[]{var179, var10});
            break label3268;
         case 54:
         case 55:
            String var181 = (String)var3.get(0);
            String var182 = (String)var3.get(1);
            if(var181.length() <= 0) {
               var181 = "true";
            }

            if(var182.length() <= 0) {
               var182 = "true";
            }

            Object[] var183 = new Object[]{var181, var1.opCode, var182};
            var7 = String.format("%s %s %s", var183);
            break label3268;
         case 56:
            String var184 = (String)var3.get(0);
            String var185 = (String)var3.get(1);
            if(var184.length() <= 0) {
               var184 = var10;
            }

            if(var185.length() > 0) {
               var10 = var185;
            }

            var7 = String.format("SketchwareUtil.getRandom((int)(%s), (int)(%s))", new Object[]{var184, var10});
            break label3268;
         case 57:
            String var186 = (String)var3.get(0);
            if(var186.length() > 0) {
               var7 = String.format("%s.length()", new Object[]{var186});
               break label3268;
            }
            break;
         case 58:
            var7 = String.format("%s.concat(%s)", new Object[]{(String)var3.get(0), (String)var3.get(1)});
            break label3268;
         case 59:
            String var187 = (String)var3.get(0);
            var7 = String.format("%s.indexOf(%s)", new Object[]{(String)var3.get(1), var187});
            break label3268;
         case 60:
            String var188 = (String)var3.get(0);
            var7 = String.format("%s.lastIndexOf(%s)", new Object[]{(String)var3.get(1), var188});
            break label3268;
         case 61:
            String var189 = (String)var3.get(0);
            String var190 = (String)var3.get(1);
            String var191 = (String)var3.get(2);
            if(var190.length() <= 0) {
               var190 = var10;
            }

            if(var191.length() > 0) {
               var10 = var191;
            }

            var7 = String.format("%s.substring((int)(%s), (int)(%s))", new Object[]{var189, var190, var10});
            break label3268;
         case 62:
            var7 = String.format("%s.equals(%s)", new Object[]{(String)var3.get(0), (String)var3.get(1)});
            break label3268;
         case 63:
            var7 = String.format("%s.contains(%s)", new Object[]{(String)var3.get(0), (String)var3.get(1)});
            break label3268;
         case 64:
            var7 = String.format("%s.replace(%s, %s)", new Object[]{(String)var3.get(0), (String)var3.get(1), (String)var3.get(2)});
            break label3268;
         case 65:
            var7 = String.format("%s.replaceFirst(%s, %s)", new Object[]{(String)var3.get(0), (String)var3.get(1), (String)var3.get(2)});
            break label3268;
         case 66:
            var7 = String.format("%s.replaceAll(%s, %s)", new Object[]{(String)var3.get(0), (String)var3.get(1), (String)var3.get(2)});
            break label3268;
         case 67:
            String var192 = (String)var3.get(0);
            if(var192.length() <= 0 || var192.equals(var9)) {
               var192 = "\"0\"";
            }

            var7 = String.format("Double.parseDouble(%s)", new Object[]{var192});
            break label3268;
         case 68:
            var7 = "System.currentTimeMillis()";
            break label3268;
         case 69:
            var7 = String.format("%s.trim()", new Object[]{(String)var3.get(0)});
            break label3268;
         case 70:
            var7 = String.format("%s.toUpperCase()", new Object[]{(String)var3.get(0)});
            break label3268;
         case 71:
            var7 = String.format("%s.toLowerCase()", new Object[]{(String)var3.get(0)});
            break label3268;
         case 72:
            String var193 = (String)var3.get(0);
            if(var193.length() > 0) {
               var10 = var193;
            }

            var7 = String.format("String.valueOf((long)(%s))", new Object[]{var10});
            break label3268;
         case 73:
            String var194 = (String)var3.get(0);
            if(var194.length() > 0) {
               var10 = var194;
            }

            var7 = String.format("String.valueOf(%s)", new Object[]{var10});
            break label3268;
         case 74:
            String var195 = (String)var3.get(0);
            String var196 = (String)var3.get(1);
            if(var196.length() <= 0) {
               var196 = var11;
            }

            if(var195.length() > 0) {
               var10 = var195;
            }

            var7 = String.format("new DecimalFormat(%s).format(%s)", new Object[]{var196, var10});
            break label3268;
         case 75:
            var7 = (String)var1.parameters.get(0);
            if(var7 != null && var7.length() > 0) {
               break label3268;
            }
            break;
         case 76:
            String var197 = (String)var3.get(0);
            String var198 = (String)var3.get(1);
            if(var197.length() > 0 && var198.length() > 0) {
               var7 = String.format("%s = new Gson().fromJson(%s, new TypeToken<HashMap<String, Object>>(){}.getType());", new Object[]{var198, var197});
               break label3268;
            }
            break;
         case 77:
            String var199 = (String)var3.get(0);
            if(var199.length() > 0) {
               var7 = String.format("new Gson().toJson(%s)", new Object[]{var199});
               break label3268;
            }
            break;
         case 78:
            String var200 = (String)var3.get(0);
            String var201 = (String)var3.get(1);
            if(var200.length() > 0 && var201.length() > 0) {
               var7 = String.format("%s = new Gson().fromJson(%s, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());", new Object[]{var201, var200});
               break label3268;
            }
            break;
         case 79:
            String var202 = (String)var3.get(0);
            if(var202.length() > 0) {
               var7 = String.format("new Gson().toJson(%s)", new Object[]{var202});
               break label3268;
            }
            break;
         case 80:
            String var203 = (String)var3.get(0);
            if(var203.length() > 0) {
               var10 = var203;
            }

            var7 = String.format("SketchwareUtil.getDip(getApplicationContext(), (int)(%s))", new Object[]{var10});
            break label3268;
         case 81:
            var7 = "SketchwareUtil.getDisplayWidthPixels(getApplicationContext())";
            break label3268;
         case 82:
            var7 = "SketchwareUtil.getDisplayHeightPixels(getApplicationContext())";
            break label3268;
         case 83:
            var7 = "Math.PI";
            break label3268;
         case 84:
            var7 = "Math.E";
            break label3268;
         case 85:
            String var204 = (String)var3.get(0);
            String var205 = (String)var3.get(1);
            if(var205.length() <= 0) {
               var205 = var10;
            }

            if(var204.length() > 0) {
               var10 = var204;
            }

            var7 = String.format("Math.pow(%s, %s)", new Object[]{var10, var205});
            break label3268;
         case 86:
            String var206 = (String)var3.get(0);
            String var207 = (String)var3.get(1);
            if(var207.length() <= 0) {
               var207 = var10;
            }

            if(var206.length() > 0) {
               var10 = var206;
            }

            var7 = String.format("Math.min(%s, %s)", new Object[]{var10, var207});
            break label3268;
         case 87:
            String var208 = (String)var3.get(0);
            String var209 = (String)var3.get(1);
            if(var209.length() <= 0) {
               var209 = var10;
            }

            if(var208.length() > 0) {
               var10 = var208;
            }

            var7 = String.format("Math.max(%s, %s)", new Object[]{var10, var209});
            break label3268;
         case 88:
            String var210 = (String)var3.get(0);
            if(var210.length() <= 0) {
               var210 = "1";
            }

            var7 = String.format("Math.sqrt(%s)", new Object[]{var210});
            break label3268;
         case 89:
            String var211 = (String)var3.get(0);
            if(var211.length() > 0) {
               var10 = var211;
            }

            var7 = String.format("Math.abs(%s)", new Object[]{var10});
            break label3268;
         case 90:
            String var212 = (String)var3.get(0);
            if(var212.length() > 0) {
               var10 = var212;
            }

            var7 = String.format("Math.round(%s)", new Object[]{var10});
            break label3268;
         case 91:
            String var213 = (String)var3.get(0);
            if(var213.length() > 0) {
               var10 = var213;
            }

            var7 = String.format("Math.ceil(%s)", new Object[]{var10});
            break label3268;
         case 92:
            String var214 = (String)var3.get(0);
            if(var214.length() > 0) {
               var10 = var214;
            }

            var7 = String.format("Math.floor(%s)", new Object[]{var10});
            break label3268;
         case 93:
            String var215 = (String)var3.get(0);
            if(var215.length() > 0) {
               var10 = var215;
            }

            var7 = String.format("Math.sin(%s)", new Object[]{var10});
            break label3268;
         case 94:
            String var216 = (String)var3.get(0);
            if(var216.length() > 0) {
               var10 = var216;
            }

            var7 = String.format("Math.cos(%s)", new Object[]{var10});
            break label3268;
         case 95:
            String var217 = (String)var3.get(0);
            if(var217.length() > 0) {
               var10 = var217;
            }

            var7 = String.format("Math.tan(%s)", new Object[]{var10});
            break label3268;
         case 96:
            String var218 = (String)var3.get(0);
            if(var218.length() > 0) {
               var10 = var218;
            }

            var7 = String.format("Math.asin(%s)", new Object[]{var10});
            break label3268;
         case 97:
            String var219 = (String)var3.get(0);
            if(var219.length() > 0) {
               var10 = var219;
            }

            var7 = String.format("Math.acos(%s)", new Object[]{var10});
            break label3268;
         case 98:
            String var220 = (String)var3.get(0);
            if(var220.length() > 0) {
               var10 = var220;
            }

            var7 = String.format("Math.atan(%s)", new Object[]{var10});
            break label3268;
         case 99:
            String var221 = (String)var3.get(0);
            if(var221.length() > 0) {
               var10 = var221;
            }

            var7 = String.format("Math.exp(%s)", new Object[]{var10});
            break label3268;
         case 100:
            String var222 = (String)var3.get(0);
            if(var222.length() > 0) {
               var10 = var222;
            }

            var7 = String.format("Math.log(%s)", new Object[]{var10});
            break label3268;
         case 101:
            String var223 = (String)var3.get(0);
            if(var223.length() > 0) {
               var10 = var223;
            }

            var7 = String.format("Math.log10(%s)", new Object[]{var10});
            break label3268;
         case 102:
            String var224 = (String)var3.get(0);
            if(var224.length() > 0) {
               var10 = var224;
            }

            var7 = String.format("Math.toRadians(%s)", new Object[]{var10});
            break label3268;
         case 103:
            String var225 = (String)var3.get(0);
            if(var225.length() > 0) {
               var10 = var225;
            }

            var7 = String.format("Math.toDegrees(%s)", new Object[]{var10});
            break label3268;
         case 104:
            String var226 = (String)var3.get(0);
            int var227 = var1.subStack1;
            String var228;
            if(var227 >= 0) {
               var228 = this.a(String.valueOf(var227), var11);
            } else {
               var228 = var11;
            }

            if(var226.length() > 0) {
               var7 = String.format("%s.setOnClickListener(new View.OnClickListener() {\n@Override\npublic void onClick(View _view) {\n%s\n}\n});", new Object[]{var226, var228});
               break label3268;
            }
            break;
         case 105:
            if(this.e.a(this.c).a) {
               var7 = "_drawer.isDrawerOpen(GravityCompat.START)";
            }
            break label3268;
         case 106:
            if(this.e.a(this.c).a) {
               var7 = "_drawer.openDrawer(GravityCompat.START);";
               break label3268;
            }
            break;
         case 107:
            if(this.e.a(this.c).a) {
               var7 = "_drawer.closeDrawer(GravityCompat.START);";
               break label3268;
            }
            break;
         case 108:
            String var229 = (String)var3.get(0);
            String var230 = (String)var3.get(1);
            if(var230.length() <= 0) {
               var230 = "true";
            }

            if(var229.length() > 0) {
               var7 = String.format("%s.setEnabled(%s);", new Object[]{var229, var230});
               break label3268;
            }
            break;
         case 109:
            String var231 = (String)var3.get(0);
            if(var231.length() > 0) {
               var7 = String.format("%s.isEnabled()", new Object[]{var231});
               break label3268;
            }
            break;
         case 110:
            String var232 = (String)var3.get(0);
            String var233 = (String)var3.get(1);
            if(var232.length() > 0) {
               var7 = String.format("%s.setText(%s);", new Object[]{var232, var233});
               break label3268;
            }
            break;
         case 111:
            String var234 = (String)var3.get(0);
            String var235 = (String)var3.get(1);
            String var236 = (String)var3.get(2);
            if(var236.length() > 0) {
               Pair[] var237 = sq.a("property_text_style");
               int var238 = var237.length;
               var10 = var236;

               for(int var239 = 0; var239 < var238; ++var239) {
                  Pair var240 = var237[var239];
                  if(var240.second.equals(var10)) {
                     StringBuilder var241 = new StringBuilder();
                     var241.append(var240.first);
                     var241.append(var11);
                     var10 = var241.toString();
                  }
               }
            }

            if(var235.length() > 0 && var234.length() > 0) {
               var7 = String.format("%s.setTypeface(Typeface.createFromAsset(getAssets(),\"fonts/%s.ttf\"), %s);", new Object[]{var234, var235, var10});
               break label3268;
            }
            break;
         case 112:
            String var244 = (String)var3.get(0);
            if(var244.length() > 0) {
               var7 = String.format("%s.getText().toString()", new Object[]{var244});
               break label3268;
            }
            break;
         case 113:
            String var245 = (String)var3.get(0);
            String var246 = (String)var3.get(1);
            if(var246.length() <= 0) {
               var246 = "0xFF000000";
            }

            if(var245.length() > 0 && var246.length() > 0) {
               var7 = String.format("%s.setBackgroundColor(%s);", new Object[]{var245, var246});
               break label3268;
            }
            break;
         case 114:
            String var247 = (String)var3.get(0);
            String var248 = (String)var3.get(1);
            if(var247.length() > 0 && var248.length() > 0) {
               if(!var248.equals("NONE")) {
                  StringBuilder var249 = new StringBuilder();
                  var249.append("R.drawable.");
                  if(var248.endsWith(".9")) {
                     var248 = var248.replaceAll("\\.9", var11);
                  }

                  var249.append(var248);
                  var10 = var249.toString();
               }

               var7 = String.format("%s.setBackgroundResource(%s);", new Object[]{var247, var10});
               break label3268;
            }
            break;
         case 115:
            String var252 = (String)var3.get(0);
            String var253 = (String)var3.get(1);
            if(var253.length() <= 0) {
               var253 = "0xFF000000";
            }

            if(var252.length() > 0) {
               var7 = String.format("%s.setTextColor(%s);", new Object[]{var252, var253});
               break label3268;
            }
            break;
         case 116:
            String var254 = (String)var3.get(0);
            String var255 = (String)var3.get(1);
            if(var255.endsWith(".9")) {
               var255 = var255.replaceAll("\\.9", var11);
            }

            if(var254.length() > 0 && var255.length() > 0) {
               Object[] var256 = new Object[]{var254, var255.toLowerCase()};
               var7 = String.format("%s.setImageResource(R.drawable.%s);", var256);
               break label3268;
            }
            break;
         case 117:
            String var257 = (String)var3.get(0);
            String var258 = (String)var3.get(1);
            if(var258.length() <= 0) {
               var258 = "0x00000000";
            }

            if(!var257.equals(var9)) {
               var7 = String.format("%s.setColorFilter(%s, PorterDuff.Mode.MULTIPLY);", new Object[]{var257, var258});
               break label3268;
            }
            break;
         case 118:
            String var259 = (String)var3.get(0);
            var259.equals(var9);
            var7 = String.format("%s.requestFocus();", new Object[]{var259});
            break label3268;
         case 119:
            var7 = String.format("SketchwareUtil.showMessage(getApplicationContext(), %s);", new Object[]{(String)var3.get(0)});
            break label3268;
         case 120:
            var7 = String.format("((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(\"clipboard\", %s));", new Object[]{(String)var3.get(0)});
            break label3268;
         case 121:
            var7 = String.format("setTitle(%s);", new Object[]{(String)var3.get(0)});
            break label3268;
         case 122:
            String var261 = (String)var3.get(0);
            String var262 = (String)var3.get(1);
            if(var262.length() > 0 && !var262.equals(var9)) {
               StringBuilder var263 = new StringBuilder();
               var263.append("Intent.");
               var263.append(var262);
               var9 = var263.toString();
            }

            if(var261.length() > 0) {
               var7 = String.format("%s.setAction(%s);", new Object[]{var261, var9});
               break label3268;
            }
            break;
         case 123:
            String var266 = (String)var3.get(0);
            String var267 = (String)var3.get(1);
            if(var266.length() > 0) {
               var7 = String.format("%s.setData(Uri.parse(%s));", new Object[]{var266, var267});
               break label3268;
            }
            break;
         case 124:
            String var268 = (String)var3.get(0);
            String var269 = (String)var3.get(1);
            if(var269.length() > 0 && var268.length() > 0) {
               var7 = String.format("%s.setClass(getApplicationContext(), %s.class);", new Object[]{var268, var269});
               break label3268;
            }
            break;
         case 125:
            String var270 = (String)var3.get(0);
            String var271 = (String)var3.get(1);
            String var272 = (String)var3.get(2);
            if(var270.length() > 0) {
               var7 = String.format("%s.putExtra(%s, %s);", new Object[]{var270, var271, var272});
               break label3268;
            }
            break;
         case 126:
            String var273 = (String)var3.get(0);
            String var274 = (String)var3.get(1);
            String var278;
            if(var274.length() <= 0) {
               var278 = var11;
            } else {
               StringBuilder var275 = new StringBuilder();
               var275.append("Intent.FLAG_ACTIVITY_");
               var275.append(var274);
               var278 = var275.toString();
            }

            if(var273.length() > 0 && var278.length() > 0) {
               var7 = String.format("%s.setFlags(%s);", new Object[]{var273, var278});
               break label3268;
            }
            break;
         case 127:
            var7 = String.format("getIntent().getStringExtra(%s)", new Object[]{(String)var3.get(0)});
            break label3268;
         case 128:
            String var279 = (String)var3.get(0);
            if(var279.length() > 0) {
               var7 = String.format("startActivity(%s);", new Object[]{var279});
               break label3268;
            }
            break;
         case 129:
            var7 = "finish();";
            break label3268;
         case 130:
            String var280 = (String)var3.get(0);
            String var281 = (String)var3.get(1);
            if(var280.length() > 0 && var281.length() > 0) {
               var7 = String.format("%s = getApplicationContext().getSharedPreferences(%s, Activity.MODE_PRIVATE);", new Object[]{var280, var281});
               break label3268;
            }
            break;
         case 131:
            String var282 = (String)var3.get(0);
            String var283 = (String)var3.get(1);
            if(var282.length() > 0) {
               var7 = String.format("%s.getString(%s, \"\")", new Object[]{var282, var283});
               break label3268;
            }
            break;
         case 132:
            String var284 = (String)var3.get(0);
            String var285 = (String)var3.get(1);
            String var286 = (String)var3.get(2);
            if(var284.length() > 0) {
               var7 = String.format("%s.edit().putString(%s, %s).commit();", new Object[]{var284, var285, var286});
               break label3268;
            }
            break;
         case 133:
            String var287 = (String)var3.get(0);
            String var288 = (String)var3.get(1);
            if(var287.length() > 0) {
               var7 = String.format("%s.edit().remove(%s).commit();", new Object[]{var287, var288});
               break label3268;
            }
            break;
         case 134:
            String var289 = (String)var3.get(0);
            if(var289.length() > 0) {
               var7 = String.format("%s = Calendar.getInstance();", new Object[]{var289});
               break label3268;
            }
            break;
         case 135:
            String var290 = (String)var3.get(0);
            String var291 = (String)var3.get(1);
            String var292 = (String)var3.get(2);
            if(var292.length() > 0) {
               var10 = var292;
            }

            if(var290.length() > 0 && var291.length() > 0) {
               var7 = String.format("%s.add(Calendar.%s, (int)(%s));", new Object[]{var290, var291, var10});
               break label3268;
            }
            break;
         case 136:
            String var293 = (String)var3.get(0);
            String var294 = (String)var3.get(1);
            String var295 = (String)var3.get(2);
            if(var295.length() > 0) {
               var10 = var295;
            }

            if(var293.length() > 0 && var294.length() > 0) {
               var7 = String.format("%s.set(Calendar.%s, (int)(%s));", new Object[]{var293, var294, var10});
               break label3268;
            }
            break;
         case 137:
            String var296 = (String)var3.get(0);
            String var297 = (String)var3.get(1);
            if(var297.length() <= 0 || var297.equals(var9)) {
               var297 = "\"yyyy/MM/dd hh:mm:ss\"";
            }

            if(var296.length() > 0) {
               var7 = String.format("new SimpleDateFormat(%s).format(%s.getTime())", new Object[]{var297, var296});
               break label3268;
            }
            break;
         case 138:
            String var298 = (String)var3.get(0);
            String var299 = (String)var3.get(1);
            if(var298.length() > 0 && var299.length() > 0) {
               var7 = String.format("(long)(%s.getTimeInMillis() - %s.getTimeInMillis())", new Object[]{var298, var299});
               break label3268;
            }
            break;
         case 139:
            String var300 = (String)var3.get(0);
            if(var300.length() > 0) {
               var7 = String.format("%s.getTimeInMillis()", new Object[]{var300});
               break label3268;
            }
            break;
         case 140:
            String var301 = (String)var3.get(0);
            String var302 = (String)var3.get(1);
            if(var301.length() > 0 && var302.length() > 0) {
               var7 = String.format("%s.setTimeInMillis((long)(%s));", new Object[]{var301, var302});
               break label3268;
            }
            break;
         case 141:
            String var303 = (String)var3.get(0);
            String var304 = (String)var3.get(1);
            if(var304.length() <= 0) {
               var304 = "VISIBLE";
            }

            if(var303.length() > 0) {
               var7 = String.format("%s.setVisibility(View.%s);", new Object[]{var303, var304});
               break label3268;
            }
            break;
         case 142:
            String var305 = (String)var3.get(0);
            String var306 = (String)var3.get(1);
            if(var306.length() <= 0) {
               var306 = "true";
            }

            if(var305.length() > 0) {
               var7 = String.format("%s.setClickable(%s);", new Object[]{var305, var306});
               break label3268;
            }
            break;
         case 143:
            String var307 = (String)var3.get(0);
            String var308 = (String)var3.get(1);
            if(var308.length() > 0) {
               var10 = var308;
            }

            if(var307.length() > 0) {
               var7 = String.format("%s.setRotation((float)(%s));", new Object[]{var307, var10});
               break label3268;
            }
            break;
         case 144:
            String var309 = (String)var3.get(0);
            if(var309.length() > 0) {
               var7 = String.format("%s.getRotation()", new Object[]{var309});
               break label3268;
            }
            break;
         case 145:
            String var310 = (String)var3.get(0);
            String var311 = (String)var3.get(1);
            if(var311.length() > 0) {
               var10 = var311;
            }

            if(var310.length() > 0) {
               var7 = String.format("%s.setAlpha((float)(%s));", new Object[]{var310, var10});
               break label3268;
            }
            break;
         case 146:
            String var312 = (String)var3.get(0);
            if(var312.length() > 0) {
               var7 = String.format("%s.getAlpha()", new Object[]{var312});
               break label3268;
            }
            break;
         case 147:
            String var313 = (String)var3.get(0);
            String var314 = (String)var3.get(1);
            if(var314.length() > 0) {
               var10 = var314;
            }

            if(var313.length() > 0) {
               var7 = String.format("%s.setTranslationX((float)(%s));", new Object[]{var313, var10});
               break label3268;
            }
            break;
         case 148:
            String var315 = (String)var3.get(0);
            if(var315.length() > 0) {
               var7 = String.format("%s.getTranslationX()", new Object[]{var315});
               break label3268;
            }
            break;
         case 149:
            String var316 = (String)var3.get(0);
            String var317 = (String)var3.get(1);
            if(var317.length() > 0) {
               var10 = var317;
            }

            if(var316.length() > 0) {
               var7 = String.format("%s.setTranslationY((float)(%s));", new Object[]{var316, var10});
               break label3268;
            }
            break;
         case 150:
            String var318 = (String)var3.get(0);
            if(var318.length() > 0) {
               var7 = String.format("%s.getTranslationY()", new Object[]{var318});
               break label3268;
            }
            break;
         case 151:
            String var319 = (String)var3.get(0);
            String var320 = (String)var3.get(1);
            if(var320.length() > 0) {
               var10 = var320;
            }

            if(var319.length() > 0) {
               var7 = String.format("%s.setScaleX((float)(%s));", new Object[]{var319, var10});
               break label3268;
            }
            break;
         case 152:
            String var321 = (String)var3.get(0);
            if(var321.length() > 0) {
               var7 = String.format("%s.getScaleX()", new Object[]{var321});
               break label3268;
            }
            break;
         case 153:
            String var322 = (String)var3.get(0);
            String var323 = (String)var3.get(1);
            if(var323.length() > 0) {
               var10 = var323;
            }

            if(var322.length() > 0) {
               var7 = String.format("%s.setScaleY((float)(%s));", new Object[]{var322, var10});
               break label3268;
            }
            break;
         case 154:
            String var324 = (String)var3.get(0);
            if(var324.length() > 0) {
               var7 = String.format("%s.getScaleY()", new Object[]{var324});
               break label3268;
            }
            break;
         case 155:
            String var325 = (String)var3.get(0);
            if(var325.length() > 0) {
               var7 = String.format("SketchwareUtil.getLocationX(%s)", new Object[]{var325});
               break label3268;
            }
            break;
         case 156:
            String var326 = (String)var3.get(0);
            if(var326.length() > 0) {
               var7 = String.format("SketchwareUtil.getLocationY(%s)", new Object[]{var326});
               break label3268;
            }
            break;
         case 157:
            String var327 = (String)var3.get(0);
            String var328 = (String)var3.get(1);
            if(var328.length() <= 0) {
               var328 = var7;
            }

            if(var327.length() > 0) {
               var7 = String.format("%s.setChecked(%s);", new Object[]{var327, var328});
               break label3268;
            }
            break;
         case 158:
            String var329 = (String)var3.get(0);
            if(var329.length() > 0) {
               var7 = String.format("%s.isChecked()", new Object[]{var329});
               break label3268;
            }
            break;
         case 159:
            String var330 = (String)var3.get(0);
            String var331 = (String)var3.get(1);
            if(var330.length() > 0 && var331.length() > 0) {
               var7 = String.format("%s.setAdapter(new ArrayAdapter<String>(getBaseContext(), %s, %s));", new Object[]{var330, "android.R.layout.simple_list_item_1", var331});
               break label3268;
            }
            break;
         case 160:
         case 325:
         case 326:
         case 327:
         case 328:
            String var332 = (String)var3.get(0);
            String var333 = (String)var3.get(1);
            if(var332.length() > 0 && var333.length() > 0) {
               String var334 = Lx.a(var332);
               StringBuilder var335 = new StringBuilder();
               var335.append("%s.setAdapter(new ");
               var335.append(var334);
               var335.append("(%s));");
               var7 = String.format(var335.toString(), new Object[]{var332, var333});
               break label3268;
            }
            break;
         case 161:
            String var339 = (String)var3.get(0);
            if(var339.length() > 0) {
               var7 = String.format("((BaseAdapter)%s.getAdapter()).notifyDataSetChanged();", new Object[]{var339});
               break label3268;
            }
            break;
         case 162:
            String var340 = (String)var3.get(0);
            String var341 = (String)var3.get(1);
            String var342 = (String)var3.get(2);
            if(var341.length() > 0) {
               var10 = var341;
            }

            if(var342.length() <= 0) {
               var342 = var7;
            }

            if(var340.length() > 0) {
               var7 = String.format("%s.setItemChecked((int)(%s), %s);", new Object[]{var340, var10, var342});
               break label3268;
            }
            break;
         case 163:
            String var343 = (String)var3.get(0);
            if(var343.length() > 0) {
               var7 = String.format("%s.getCheckedItemPosition()", new Object[]{var343});
               break label3268;
            }
            break;
         case 164:
            String var344 = (String)var3.get(0);
            String var345 = (String)var3.get(1);
            if(var344.length() > 0 && var345.length() > 0) {
               var7 = String.format("%s = SketchwareUtil.getCheckedItemPositionsToArray(%s);", new Object[]{var345, var344});
               break label3268;
            }
            break;
         case 165:
            String var346 = (String)var3.get(0);
            if(var346.length() > 0) {
               var7 = String.format("%s.getCheckedItemCount()", new Object[]{var346});
               break label3268;
            }
            break;
         case 166:
            String var347 = (String)var3.get(0);
            String var348 = (String)var3.get(1);
            var347.length();
            if(var348.length() > 0) {
               var7 = String.format("%s.smoothScrollToPosition((int)(%s));", new Object[]{var347, var348});
               break label3268;
            }
            break;
         case 167:
            String var350 = (String)var3.get(0);
            String var351 = (String)var3.get(1);
            if(var350.length() > 0 && var351.length() > 0) {
               var7 = String.format("%s.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, %s));", new Object[]{var350, var351});
               break label3268;
            }
            break;
         case 168:
            String var352 = (String)var3.get(0);
            if(var352.length() > 0) {
               var7 = String.format("((ArrayAdapter)%s.getAdapter()).notifyDataSetChanged();", new Object[]{var352});
               break label3268;
            }
            break;
         case 169:
            String var353 = (String)var3.get(0);
            String var354 = (String)var3.get(1);
            if(var354.length() > 0) {
               var10 = var354;
            }

            if(var353.length() > 0) {
               var7 = String.format("%s.setSelection((int)(%s));", new Object[]{var353, var10});
               break label3268;
            }
            break;
         case 170:
            String var355 = (String)var3.get(0);
            if(var355.length() > 0) {
               var7 = String.format("%s.getSelectedItemPosition()", new Object[]{var355});
               break label3268;
            }
            break;
         case 171:
            String var356 = (String)var3.get(0);
            String var357 = (String)var3.get(1);
            if(var356.length() > 0 && var357.length() > 0) {
               var7 = String.format("%s.loadUrl(%s);", new Object[]{var356, var357});
               break label3268;
            }
            break;
         case 172:
            String var358 = (String)var3.get(0);
            if(var358.length() > 0) {
               var7 = String.format("%s.getUrl()", new Object[]{var358});
               break label3268;
            }
            break;
         case 173:
            String var359 = (String)var3.get(0);
            String var360 = (String)var3.get(1);
            if(var360.length() <= 0) {
               var360 = "LOAD_DEFAULT";
            }

            if(var359.length() > 0) {
               var7 = String.format("%s.getSettings().setCacheMode(WebSettings.%s);", new Object[]{var359, var360});
               break label3268;
            }
            break;
         case 174:
            String var361 = (String)var3.get(0);
            if(var361.length() > 0) {
               var7 = String.format("%s.canGoBack()", new Object[]{var361});
               break label3268;
            }
            break;
         case 175:
            String var362 = (String)var3.get(0);
            if(var362.length() > 0) {
               var7 = String.format("%s.canGoForward()", new Object[]{var362});
               break label3268;
            }
            break;
         case 176:
            String var363 = (String)var3.get(0);
            if(var363.length() > 0) {
               var7 = String.format("%s.goBack();", new Object[]{var363});
               break label3268;
            }
            break;
         case 177:
            String var364 = (String)var3.get(0);
            if(var364.length() > 0) {
               var7 = String.format("%s.goForward();", new Object[]{var364});
               break label3268;
            }
            break;
         case 178:
            String var365 = (String)var3.get(0);
            if(var365.length() > 0) {
               var7 = String.format("%s.clearCache(true);", new Object[]{var365});
               break label3268;
            }
            break;
         case 179:
            String var366 = (String)var3.get(0);
            if(var366.length() > 0) {
               var7 = String.format("%s.clearHistory();", new Object[]{var366});
               break label3268;
            }
            break;
         case 180:
            String var367 = (String)var3.get(0);
            if(var367.length() > 0) {
               var7 = String.format("%s.stopLoading();", new Object[]{var367});
               break label3268;
            }
            break;
         case 181:
            String var368 = (String)var3.get(0);
            if(var368.length() > 0) {
               var7 = String.format("%s.zoomIn();", new Object[]{var368});
               break label3268;
            }
            break;
         case 182:
            String var369 = (String)var3.get(0);
            if(var369.length() > 0) {
               var7 = String.format("%s.zoomOut();", new Object[]{var369});
               break label3268;
            }
            break;
         case 183:
            String var370 = (String)var3.get(0);
            if(var370.length() > 0) {
               var7 = String.format("%s.getDate()", new Object[]{var370});
               break label3268;
            }
            break;
         case 184:
            String var371 = (String)var3.get(0);
            String var372 = (String)var3.get(1);
            if(var371.length() > 0 && var372.length() > 0) {
               var7 = String.format("%s.setDate((long)(%s), true, true);", new Object[]{var371, var372});
               break label3268;
            }
            break;
         case 185:
            String var373 = (String)var3.get(0);
            String var374 = (String)var3.get(1);
            if(var373.length() > 0 && var374.length() > 0) {
               var7 = String.format("%s.setMinDate((long)(%s));", new Object[]{var373, var374});
               break label3268;
            }
            break;
         case 186:
            String var375 = (String)var3.get(0);
            String var376 = (String)var3.get(1);
            if(var375.length() > 0 && var376.length() > 0) {
               var7 = String.format("%s.setMaxDate((long)(%s));", new Object[]{var375, var376});
               break label3268;
            }
            break;
         case 187:
            String var377 = (String)var3.get(0);
            String var378;
            if(this.e.t.size() > 0) {
               Iterator var379 = this.e.t.iterator();

               StringBuilder var381;
               for(var378 = var11; var379.hasNext(); var378 = var381.toString()) {
                  String var380 = (String)var379.next();
                  var381 = new StringBuilder();
                  var381.append(var378);
                  var381.append(".addTestDevice(\"");
                  var381.append(var380);
                  var381.append("\")\n");
               }
            } else {
               var378 = var11;
            }

            if(var377.length() > 0) {
               var7 = String.format("%s.loadAd(new AdRequest.Builder()%s.build());", new Object[]{var377, var378});
               break label3268;
            }
            break;
         case 188:
            String var386 = (String)var3.get(0);
            String var387 = (String)var3.get(1);
            if(var387.length() <= 0) {
               var387 = uq.q[0];
            }

            if(var386.length() > 0) {
               var7 = String.format("_%s_controller.setMapType(GoogleMap.%s);", new Object[]{var386, var387});
               break label3268;
            }
            break;
         case 189:
            String var388 = (String)var3.get(0);
            String var389 = (String)var3.get(1);
            if(var389.length() <= 0) {
               var389 = var10;
            }

            String var390 = (String)var3.get(2);
            if(var390.length() > 0) {
               var10 = var390;
            }

            if(var388.length() > 0) {
               var7 = String.format("_%s_controller.moveCamera(%s, %s);", new Object[]{var388, var389, var10});
               break label3268;
            }
            break;
         case 190:
            String var391 = (String)var3.get(0);
            String var392 = (String)var3.get(1);
            if(var392.length() > 0) {
               var10 = var392;
            }

            if(var391.length() > 0) {
               var7 = String.format("_%s_controller.zoomTo(%s);", new Object[]{var391, var10});
               break label3268;
            }
            break;
         case 191:
            String var393 = (String)var3.get(0);
            if(var393.length() > 0) {
               var7 = String.format("_%s_controller.zoomIn();", new Object[]{var393});
               break label3268;
            }
            break;
         case 192:
            String var394 = (String)var3.get(0);
            if(var394.length() > 0) {
               var7 = String.format("_%s_controller.zoomOut();", new Object[]{var394});
               break label3268;
            }
            break;
         case 193:
            String var395 = (String)var3.get(0);
            String var396 = (String)var3.get(1);
            String var397 = (String)var3.get(2);
            if(var397.length() <= 0) {
               var397 = var10;
            }

            String var398 = (String)var3.get(3);
            if(var398.length() > 0) {
               var10 = var398;
            }

            if(var395.length() > 0 && var396.length() > 0) {
               var7 = String.format("_%s_controller.addMarker(%s, %s, %s);", new Object[]{var395, var396, var397, var10});
               break label3268;
            }
            break;
         case 194:
            String var399 = (String)var3.get(0);
            String var400 = (String)var3.get(1);
            String var401 = (String)var3.get(2);
            String var402 = (String)var3.get(3);
            if(var399.length() > 0 && var400.length() > 0) {
               var7 = String.format("_%s_controller.setMarkerInfo(%s, %s, %s);", new Object[]{var399, var400, var401, var402});
               break label3268;
            }
            break;
         case 195:
            String var403 = (String)var3.get(0);
            String var404 = (String)var3.get(1);
            String var405 = (String)var3.get(2);
            if(var405.length() <= 0) {
               var405 = var10;
            }

            String var406 = (String)var3.get(3);
            if(var406.length() > 0) {
               var10 = var406;
            }

            if(var403.length() > 0 && var404.length() > 0) {
               var7 = String.format("_%s_controller.setMarkerPosition(%s, %s, %s);", new Object[]{var403, var404, var405, var10});
               break label3268;
            }
            break;
         case 196:
            String var407 = (String)var3.get(0);
            String var408 = (String)var3.get(1);
            String var409 = (String)var3.get(2);
            if(var409.length() <= 0) {
               var409 = uq.r[0];
            }

            String var410 = (String)var3.get(3);
            if(var410.length() <= 0) {
               var410 = "1";
            }

            if(var407.length() > 0 && var408.length() > 0) {
               var7 = String.format("_%s_controller.setMarkerColor(%s, BitmapDescriptorFactory.%s, %s);", new Object[]{var407, var408, var409, var410});
               break label3268;
            }
            break;
         case 197:
            String var411 = (String)var3.get(0);
            String var412 = (String)var3.get(1);
            String var413 = (String)var3.get(2);
            if(var413.endsWith(".9")) {
               var413 = var413.replaceAll("\\.9", var11);
            }

            if(var411.length() > 0 && var412.length() > 0 && var413.length() > 0) {
               Object[] var414 = new Object[]{var411, var412, var413.toLowerCase()};
               var7 = String.format("_%s_controller.setMarkerIcon(%s, R.drawable.%s);", var414);
               break label3268;
            }
            break;
         case 198:
            String var415 = (String)var3.get(0);
            String var416 = (String)var3.get(1);
            String var417 = (String)var3.get(2);
            if(var417.length() <= 0) {
               var417 = "true";
            }

            if(var415.length() > 0 && var416.length() > 0) {
               var7 = String.format("_%s_controller.setMarkerVisible(%s, %s);", new Object[]{var415, var416, var417});
               break label3268;
            }
            break;
         case 199:
            String var418 = (String)var3.get(0);
            String var419 = (String)var3.get(1);
            if(var419.length() > 0) {
               var10 = var419;
            }

            if(var418.length() > 0) {
               var7 = String.format("%s.vibrate((long)(%s));", new Object[]{var418, var10});
               break label3268;
            }
            break;
         case 200:
            String var420 = (String)var3.get(0);
            String var421 = (String)var3.get(1);
            int var422 = var1.subStack1;
            String var423;
            if(var422 >= 0) {
               var423 = this.a(String.valueOf(var422), var11);
            } else {
               var423 = var11;
            }

            if(var421.length() > 0) {
               var10 = var421;
            }

            if(var420.length() > 0) {
               var7 = String.format("%s = new TimerTask() {\n@Override\npublic void run() {\nrunOnUiThread(new Runnable() {\n@Override\npublic void run() {\n%s\n}\n});\n}\n};\n_timer.schedule(%s, (int)(%s));", new Object[]{var420, var423, var420, var10});
               break label3268;
            }
            break;
         case 201:
            String var424 = (String)var3.get(0);
            String var425 = (String)var3.get(1);
            String var426 = (String)var3.get(2);
            int var427 = var1.subStack1;
            String var428;
            if(var427 >= 0) {
               var428 = this.a(String.valueOf(var427), var11);
            } else {
               var428 = var11;
            }

            if(var426.length() <= 0) {
               var426 = var10;
            }

            if(var425.length() > 0) {
               var10 = var425;
            }

            if(var424.length() > 0) {
               var7 = String.format("%s = new TimerTask() {\n@Override\npublic void run() {\nrunOnUiThread(new Runnable() {\n@Override\npublic void run() {\n%s\n}\n});\n}\n};\n_timer.scheduleAtFixedRate(%s, (int)(%s), (int)(%s));", new Object[]{var424, var428, var424, var10, var426});
               break label3268;
            }
            break;
         case 202:
            String var429 = (String)var3.get(0);
            if(var429.length() > 0) {
               var7 = String.format("%s.cancel();", new Object[]{var429});
               break label3268;
            }
            break;
         case 203:
            String var430 = (String)var3.get(0);
            String var431 = (String)var3.get(1);
            String var432 = (String)var3.get(2);
            if(var431.length() <= 0) {
               var431 = var11;
            }

            if(var430.length() > 0 && var432.length() > 0) {
               var7 = String.format("%s.child(%s).updateChildren(%s);", new Object[]{var430, var431, var432});
               break label3268;
            }
            break;
         case 204:
            String var433 = (String)var3.get(0);
            String var434 = (String)var3.get(1);
            if(var433.length() > 0 && var434.length() > 0) {
               var7 = String.format("%s.push().updateChildren(%s);", new Object[]{var433, var434});
               break label3268;
            }
            break;
         case 205:
            String var435 = (String)var3.get(0);
            if(var435.length() > 0) {
               var7 = String.format("%s.push().getKey()", new Object[]{var435});
               break label3268;
            }
            break;
         case 206:
            String var436 = (String)var3.get(0);
            String var437 = (String)var3.get(1);
            if(var437.length() <= 0) {
               var437 = var11;
            }

            if(var436.length() > 0) {
               var7 = String.format("%s.child(%s).removeValue();", new Object[]{var436, var437});
               break label3268;
            }
            break;
         case 207:
            String var438 = (String)var3.get(0);
            String var439 = (String)var3.get(1);
            int var440 = var1.subStack1;
            String var441;
            if(var440 >= 0) {
               var441 = this.a(String.valueOf(var440), var11);
            } else {
               var441 = var11;
            }

            if(var439.length() > 0 && var438.length() > 0) {
               StringBuilder var442 = new StringBuilder();
               var442.append(String.format("%s.addListenerForSingleValueEvent(new ValueEventListener() {", new Object[]{var438}));
               var442.append("\n@Override\npublic void onDataChange(DataSnapshot _dataSnapshot) {\n");
               var442.append(String.format("%s = new ArrayList<>();", new Object[]{var439}));
               var442.append("\ntry {\nGenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};\nfor (DataSnapshot _data : _dataSnapshot.getChildren()) {\nHashMap<String, Object> _map = _data.getValue(_ind);\n");
               var442.append(String.format("%s.add(_map);", new Object[]{var439}));
               var442.append("\n}\n}\ncatch (Exception _e) {\n_e.printStackTrace();\n}\n");
               var442.append(var441);
               var442.append("\n}\n@Override\npublic void onCancelled(DatabaseError _databaseError) {\n}\n});");
               var7 = var442.toString();
               break label3268;
            }
            break;
         case 208:
            String var451 = (String)var3.get(0);
            String var452 = (String)var3.get(1);
            String var453 = (String)var3.get(2);
            if(var451.length() > 0 && !var452.equals(var9) && !var453.equals(var9)) {
               StringBuilder var454 = new StringBuilder();
               var454.append("_");
               var454.append(var451);
               var454.append("_create_user_listener");
               String var458 = var454.toString();
               Object[] var459 = new Object[]{var451, var452, var453, this.c, var458};
               var7 = String.format("%s.createUserWithEmailAndPassword(%s, %s).addOnCompleteListener(%s.this, %s);", var459);
               break label3268;
            }
            break;
         case 209:
            String var460 = (String)var3.get(0);
            String var461 = (String)var3.get(1);
            String var462 = (String)var3.get(2);
            if(var460.length() > 0 && !var461.equals(var9) && !var462.equals(var9)) {
               StringBuilder var463 = new StringBuilder();
               var463.append("_");
               var463.append(var460);
               var463.append("_sign_in_listener");
               String var467 = var463.toString();
               Object[] var468 = new Object[]{var460, var461, var462, this.c, var467};
               var7 = String.format("%s.signInWithEmailAndPassword(%s, %s).addOnCompleteListener(%s.this, %s);", var468);
               break label3268;
            }
            break;
         case 210:
            String var469 = (String)var3.get(0);
            if(var469.length() > 0) {
               StringBuilder var470 = new StringBuilder();
               var470.append("_");
               var470.append(var469);
               var470.append("_sign_in_listener");
               String var474 = var470.toString();
               Object[] var475 = new Object[]{var469, this.c, var474};
               var7 = String.format("%s.signInAnonymously().addOnCompleteListener(%s.this, %s);", var475);
               break label3268;
            }
            break;
         case 211:
            if(this.e.a(this.c).b) {
               var7 = String.format("(FirebaseAuth.getInstance().getCurrentUser() != null)", new Object[0]);
            }
            break label3268;
         case 212:
            if(this.e.a(this.c).b) {
               var7 = String.format("FirebaseAuth.getInstance().getCurrentUser().getEmail()", new Object[0]);
               break label3268;
            }
            break;
         case 213:
            if(this.e.a(this.c).b) {
               var7 = String.format("FirebaseAuth.getInstance().getCurrentUser().getUid()", new Object[0]);
               break label3268;
            }
            break;
         case 214:
            String var476 = (String)var3.get(0);
            String var477 = (String)var3.get(1);
            if(var476.length() > 0 && !var477.equals(var9)) {
               StringBuilder var478 = new StringBuilder();
               var478.append("_");
               var478.append(var476);
               var478.append("_reset_password_listener");
               var7 = String.format("%s.sendPasswordResetEmail(%s).addOnCompleteListener(%s);", new Object[]{var476, var477, var478.toString()});
               break label3268;
            }
            break;
         case 215:
            if(this.e.a(this.c).b) {
               var7 = String.format("FirebaseAuth.getInstance().signOut();", new Object[0]);
               break label3268;
            }
            break;
         case 216:
            String var482 = (String)var3.get(0);
            if(var482.length() > 0) {
               var7 = String.format("%s.addChildEventListener(_%s_child_listener);", new Object[]{var482, var482});
               break label3268;
            }
            break;
         case 217:
            String var483 = (String)var3.get(0);
            if(var483.length() > 0) {
               var7 = String.format("%s.removeEventListener(_%s_child_listener);", new Object[]{var483, var483});
               break label3268;
            }
            break;
         case 218:
            String var484 = (String)var3.get(0);
            if(var484.length() > 0) {
               var7 = String.format("%s.registerListener(_%s_sensor_listener, %s.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);", new Object[]{var484, var484, var484});
               break label3268;
            }
            break;
         case 219:
            String var485 = (String)var3.get(0);
            if(var485.length() > 0) {
               var7 = String.format("%s.unregisterListener(_%s_sensor_listener);", new Object[]{var485, var485});
               break label3268;
            }
            break;
         case 220:
            String var486 = (String)var3.get(0);
            String var487 = (String)var3.get(1);
            if(var487.length() <= 0 || var487.equals(var9)) {
               var487 = "\"Title\"";
            }

            if(var486.length() > 0) {
               var7 = String.format("%s.setTitle(%s);", new Object[]{var486, var487});
               break label3268;
            }
            break;
         case 221:
            String var488 = (String)var3.get(0);
            String var489 = (String)var3.get(1);
            if(var489.length() <= 0 || var489.equals(var9)) {
               var489 = "\"Message\"";
            }

            if(var488.length() > 0) {
               var7 = String.format("%s.setMessage(%s);", new Object[]{var488, var489});
               break label3268;
            }
            break;
         case 222:
            String var490 = (String)var3.get(0);
            if(var490.length() > 0) {
               var7 = String.format("%s.create().show();", new Object[]{var490});
               break label3268;
            }
            break;
         case 223:
            String var491 = (String)var3.get(0);
            String var492 = (String)var3.get(1);
            int var493 = var1.subStack1;
            String var494;
            if(var493 >= 0) {
               var494 = this.a(String.valueOf(var493), var11);
            } else {
               var494 = var11;
            }

            if(var492.length() <= 0 || var492.equals(var9)) {
               var492 = "\"OK\"";
            }

            if(var491.length() > 0) {
               var7 = String.format("%s.setPositiveButton(%s, new DialogInterface.OnClickListener() {\n@Override\npublic void onClick(DialogInterface _dialog, int _which) {\n%s\n}\n});", new Object[]{var491, var492, var494});
               break label3268;
            }
            break;
         case 224:
            String var495 = (String)var3.get(0);
            String var496 = (String)var3.get(1);
            int var497 = var1.subStack1;
            String var498;
            if(var497 >= 0) {
               var498 = this.a(String.valueOf(var497), var11);
            } else {
               var498 = var11;
            }

            if(var496.length() <= 0 || var496.equals(var9)) {
               var496 = "\"Cancel\"";
            }

            if(var495.length() > 0) {
               var7 = String.format("%s.setNegativeButton(%s, new DialogInterface.OnClickListener() {\n@Override\npublic void onClick(DialogInterface _dialog, int _which) {\n%s\n}\n});", new Object[]{var495, var496, var498});
               break label3268;
            }
            break;
         case 225:
            String var499 = (String)var3.get(0);
            String var500 = (String)var3.get(1);
            int var501 = var1.subStack1;
            String var502;
            if(var501 >= 0) {
               var502 = this.a(String.valueOf(var501), var11);
            } else {
               var502 = var11;
            }

            if(var500.length() <= 0 || var500.equals(var9)) {
               var500 = "\"Neutral\"";
            }

            if(var499.length() > 0) {
               var7 = String.format("%s.setNeutralButton(%s, new DialogInterface.OnClickListener() {\n@Override\npublic void onClick(DialogInterface _dialog, int _which) {\n%s\n}\n});", new Object[]{var499, var500, var502});
               break label3268;
            }
            break;
         case 226:
            String var503 = (String)var3.get(0);
            String var504 = (String)var3.get(1);
            if(var503.length() > 0 && var504.length() > 0) {
               Object[] var505 = new Object[]{var503, var504.toLowerCase()};
               var7 = String.format("%s = MediaPlayer.create(getApplicationContext(), R.raw.%s);", var505);
               break label3268;
            }
            break;
         case 227:
            String var506 = (String)var3.get(0);
            if(var506.length() > 0) {
               var7 = String.format("%s.start();", new Object[]{var506});
               break label3268;
            }
            break;
         case 228:
            String var507 = (String)var3.get(0);
            if(var507.length() > 0) {
               var7 = String.format("%s.pause();", new Object[]{var507});
               break label3268;
            }
            break;
         case 229:
            String var508 = (String)var3.get(0);
            String var509 = (String)var3.get(1);
            if(var509.length() > 0) {
               var10 = var509;
            }

            if(var508.length() > 0) {
               var7 = String.format("%s.seekTo((int)(%s));", new Object[]{var508, var10});
               break label3268;
            }
            break;
         case 230:
            String var510 = (String)var3.get(0);
            if(var510.length() > 0) {
               var7 = String.format("%s.getCurrentPosition()", new Object[]{var510});
               break label3268;
            }
            break;
         case 231:
            String var511 = (String)var3.get(0);
            if(var511.length() > 0) {
               var7 = String.format("%s.getDuration()", new Object[]{var511});
               break label3268;
            }
            break;
         case 232:
            String var512 = (String)var3.get(0);
            if(var512.length() > 0) {
               var7 = String.format("%s.reset();", new Object[]{var512});
               break label3268;
            }
            break;
         case 233:
            String var513 = (String)var3.get(0);
            if(var513.length() > 0) {
               var7 = String.format("%s.release();", new Object[]{var513});
               break label3268;
            }
            break;
         case 234:
            String var514 = (String)var3.get(0);
            if(var514.length() > 0) {
               var7 = String.format("%s.isPlaying()", new Object[]{var514});
               break label3268;
            }
            break;
         case 235:
            String var515 = (String)var3.get(0);
            String var516 = (String)var3.get(1);
            if(var516.length() <= 0) {
               var516 = var7;
            }

            if(var515.length() > 0) {
               var7 = String.format("%s.setLooping(%s);", new Object[]{var515, var516});
               break label3268;
            }
            break;
         case 236:
            String var517 = (String)var3.get(0);
            if(var517.length() > 0) {
               var7 = String.format("%s.isLooping()", new Object[]{var517});
               break label3268;
            }
            break;
         case 237:
            String var518 = (String)var3.get(0);
            String var519 = (String)var3.get(1);
            if(var519.length() <= 0) {
               var519 = "1";
            }

            if(var518.length() > 0) {
               var7 = String.format("%s = new SoundPool((int)(%s), AudioManager.STREAM_MUSIC, 0);", new Object[]{var518, var519});
               break label3268;
            }
            break;
         case 238:
            String var520 = (String)var3.get(0);
            String var521 = (String)var3.get(1);
            if(var521.length() > 0 && var520.length() > 0) {
               var7 = String.format("%s.load(getApplicationContext(), R.raw.%s, 1)", new Object[]{var520, var521});
               break label3268;
            }
            break;
         case 239:
            String var522 = (String)var3.get(0);
            String var523 = (String)var3.get(1);
            String var524 = (String)var3.get(2);
            if(var524.length() > 0) {
               var10 = var524;
            }

            if(var523.length() > 0 && var522.length() > 0) {
               var7 = String.format("%s.play((int)(%s), 1.0f, 1.0f, 1, (int)(%s), 1.0f)", new Object[]{var522, var523, var10});
               break label3268;
            }
            break;
         case 240:
            String var525 = (String)var3.get(0);
            String var526 = (String)var3.get(1);
            if(var526.length() > 0 && var525.length() > 0) {
               var7 = String.format("%s.stop((int)(%s));", new Object[]{var525, var526});
               break label3268;
            }
            break;
         case 241:
            String var527 = (String)var3.get(0);
            String var528 = (String)var3.get(1);
            if(var528.endsWith(".9")) {
               var528 = var528.replaceAll("\\.9", var11);
            }

            if(var527.length() > 0 && var528.length() > 0) {
               Object[] var529 = new Object[]{var527, var528.toLowerCase()};
               var7 = String.format("%s.setThumbResource(R.drawable.%s);", var529);
               break label3268;
            }
            break;
         case 242:
            String var530 = (String)var3.get(0);
            String var531 = (String)var3.get(1);
            if(var531.endsWith(".9")) {
               var531 = var531.replaceAll("\\.9", var11);
            }

            if(var530.length() > 0 && var531.length() > 0) {
               Object[] var532 = new Object[]{var530, var531.toLowerCase()};
               var7 = String.format("%s.setTrackResource(R.drawable.%s);", var532);
               break label3268;
            }
            break;
         case 243:
            String var533 = (String)var3.get(0);
            String var534 = (String)var3.get(1);
            if(var534.length() > 0) {
               var10 = var534;
            }

            if(var533.length() > 0) {
               var7 = String.format("%s.setProgress((int)%s);", new Object[]{var533, var10});
               break label3268;
            }
            break;
         case 244:
            String var535 = (String)var3.get(0);
            if(var535.length() > 0) {
               var7 = String.format("%s.getProgress()", new Object[]{var535});
               break label3268;
            }
            break;
         case 245:
            String var536 = (String)var3.get(0);
            String var537 = (String)var3.get(1);
            if(var537.length() > 0) {
               var10 = var537;
            }

            if(var536.length() > 0) {
               var7 = String.format("%s.setMax((int)%s);", new Object[]{var536, var10});
               break label3268;
            }
            break;
         case 246:
            String var538 = (String)var3.get(0);
            if(var538.length() > 0) {
               var7 = String.format("%s.getMax()", new Object[]{var538});
               break label3268;
            }
            break;
         case 247:
            String var539 = (String)var3.get(0);
            String var540 = (String)var3.get(1);
            if(var539.length() > 0 && var540.length() > 0) {
               var7 = String.format("%s.setTarget(%s);", new Object[]{var539, var540});
               break label3268;
            }
            break;
         case 248:
            String var541 = (String)var3.get(0);
            String var542 = (String)var3.get(1);
            if(var541.length() > 0 && var542.length() > 0) {
               var7 = String.format("%s.setPropertyName(\"%s\");", new Object[]{var541, var542});
               break label3268;
            }
            break;
         case 249:
            String var543 = (String)var3.get(0);
            String var544 = (String)var3.get(1);
            if(var544.length() > 0) {
               var10 = var544;
            }

            if(var543.length() > 0) {
               var7 = String.format("%s.setFloatValues((float)(%s));", new Object[]{var543, var10});
               break label3268;
            }
            break;
         case 250:
            String var545 = (String)var3.get(0);
            String var546 = (String)var3.get(1);
            String var547 = (String)var3.get(2);
            if(var547.length() <= 0) {
               var547 = var10;
            }

            if(var546.length() > 0) {
               var10 = var546;
            }

            if(var545.length() > 0) {
               var7 = String.format("%s.setFloatValues((float)(%s), (float)(%s));", new Object[]{var545, var10, var547});
               break label3268;
            }
            break;
         case 251:
            String var548 = (String)var3.get(0);
            String var549 = (String)var3.get(1);
            if(var549.length() > 0) {
               var10 = var549;
            }

            if(var548.length() > 0) {
               var7 = String.format("%s.setDuration((int)(%s));", new Object[]{var548, var10});
               break label3268;
            }
            break;
         case 252:
            String var550 = (String)var3.get(0);
            String var551 = (String)var3.get(1);
            if(var550.length() > 0 && var551.length() > 0) {
               var7 = String.format("%s.setRepeatMode(ValueAnimator.%s);", new Object[]{var550, var551});
               break label3268;
            }
            break;
         case 253:
            String var552 = (String)var3.get(0);
            String var553 = (String)var3.get(1);
            if(var553.length() > 0) {
               var10 = var553;
            }

            if(var552.length() > 0) {
               var7 = String.format("%s.setRepeatCount((int)(%s));", new Object[]{var552, var10});
               break label3268;
            }
            break;
         case 254:
            String var554 = (String)var3.get(0);
            String var555 = (String)var3.get(1);
            String var556;
            if(var555.equals("Accelerate")) {
               var556 = "new AccelerateInterpolator()";
            } else {
               var556 = "new LinearInterpolator()";
            }

            if(var555.equals("Decelerate")) {
               var556 = "new DecelerateInterpolator()";
            }

            if(var555.equals("AccelerateDeccelerate")) {
               var556 = "new AccelerateDecelerateInterpolator()";
            }

            if(var555.equals("Bounce")) {
               var556 = "new BounceInterpolator()";
            }

            if(var554.length() > 0) {
               var7 = String.format("%s.setInterpolator(%s);", new Object[]{var554, var556});
               break label3268;
            }
            break;
         case 255:
            String var557 = (String)var3.get(0);
            if(var557.length() > 0) {
               var7 = String.format("%s.start();", new Object[]{var557});
               break label3268;
            }
            break;
         case 256:
            String var558 = (String)var3.get(0);
            if(var558.length() > 0) {
               var7 = String.format("%s.cancel();", new Object[]{var558});
               break label3268;
            }
            break;
         case 257:
            String var559 = (String)var3.get(0);
            if(var559.length() > 0) {
               var7 = String.format("%s.isRunning()", new Object[]{var559});
               break label3268;
            }
            break;
         case 258:
            String var560 = (String)var3.get(0);
            if(var560.length() > 0) {
               var7 = String.format("%s = new InterstitialAd(getApplicationContext());\n%s.setAdListener(_%s_ad_listener);", new Object[]{var560, var560, var560});
               break label3268;
            }
            break;
         case 259:
            String var561 = (String)var3.get(0);
            String var562;
            if(this.e.t.size() > 0) {
               Iterator var565 = this.e.t.iterator();

               StringBuilder var567;
               for(var562 = var11; var565.hasNext(); var562 = var567.toString()) {
                  String var566 = (String)var565.next();
                  var567 = new StringBuilder();
                  var567.append(var562);
                  var567.append(".addTestDevice(\"");
                  var567.append(var566);
                  var567.append("\")\n");
               }
            } else {
               var562 = var11;
            }

            jq var563 = this.e;
            String var564;
            if(!var563.f) {
               var564 = "ca-app-pub-3940256099942544/1033173712";
            } else {
               var564 = var563.s;
            }

            if(var561.length() > 0) {
               var7 = String.format("%s.setAdUnitId(\"%s\");\n%s.loadAd(new AdRequest.Builder()%s.build());", new Object[]{var561, var564, var561, var562});
               break label3268;
            }
            break;
         case 260:
            String var572 = (String)var3.get(0);
            if(var572.length() > 0) {
               var7 = String.format("%s.show();", new Object[]{var572});
               break label3268;
            }
            break;
         case 261:
            String var573 = (String)var3.get(0);
            String var574 = (String)var3.get(1);
            String var575 = (String)var3.get(2);
            if(var573.length() > 0 && !var574.equals(var9) && !var575.equals(var9)) {
               var7 = String.format("%s.child(%s).putFile(Uri.fromFile(new File(%s))).addOnFailureListener(_%s_failure_listener).addOnProgressListener(_%s_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {\n@Override\npublic Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {\nreturn %s.child(%s).getDownloadUrl();\n}}).addOnCompleteListener(_%s_upload_success_listener);", new Object[]{var573, var575, var574, var573, var573, var573, var575, var573});
               break label3268;
            }
            break;
         case 262:
            String var576 = (String)var3.get(0);
            String var577 = (String)var3.get(1);
            String var578 = (String)var3.get(2);
            if(var576.length() > 0 && !var577.equals(var9) && !var578.equals(var9)) {
               var7 = String.format("_firebase_storage.getReferenceFromUrl(%s).getFile(new File(%s)).addOnSuccessListener(_%s_download_success_listener).addOnFailureListener(_%s_failure_listener).addOnProgressListener(_%s_download_progress_listener);", new Object[]{var577, var578, var576, var576, var576});
               break label3268;
            }
            break;
         case 263:
            String var579 = (String)var3.get(0);
            String var580 = (String)var3.get(1);
            if(var579.length() > 0 && !var580.equals(var9)) {
               var7 = String.format("_firebase_storage.getReferenceFromUrl(%s).delete().addOnSuccessListener(_%s_delete_success_listener).addOnFailureListener(_%s_failure_listener);", new Object[]{var580, var579, var579});
               break label3268;
            }
            break;
         case 264:
            String var581 = (String)var3.get(0);
            if(!var581.equals(var9)) {
               var7 = String.format("FileUtil.readFile(%s)", new Object[]{var581});
               break label3268;
            }
            break;
         case 265:
            String var582 = (String)var3.get(0);
            String var583 = (String)var3.get(1);
            if(!var583.equals(var9)) {
               var7 = String.format("FileUtil.writeFile(%s, %s);", new Object[]{var583, var582});
               break label3268;
            }
            break;
         case 266:
            String var584 = (String)var3.get(0);
            String var585 = (String)var3.get(1);
            if(!var584.equals(var9) && !var585.equals(var9)) {
               var7 = String.format("FileUtil.copyFile(%s, %s);", new Object[]{var584, var585});
               break label3268;
            }
            break;
         case 267:
            String var586 = (String)var3.get(0);
            String var587 = (String)var3.get(1);
            if(!var586.equals(var9) && !var587.equals(var9)) {
               var7 = String.format("FileUtil.moveFile(%s, %s);", new Object[]{var586, var587});
               break label3268;
            }
            break;
         case 268:
            String var588 = (String)var3.get(0);
            if(!var588.equals(var9)) {
               var7 = String.format("FileUtil.deleteFile(%s);", new Object[]{var588});
               break label3268;
            }
            break;
         case 269:
            String var589 = (String)var3.get(0);
            if(!var589.equals(var9)) {
               var7 = String.format("FileUtil.isExistFile(%s)", new Object[]{var589});
               break label3268;
            }
            break;
         case 270:
            String var590 = (String)var3.get(0);
            if(!var590.equals(var9)) {
               var7 = String.format("FileUtil.makeDir(%s);", new Object[]{var590});
               break label3268;
            }
            break;
         case 271:
            String var591 = (String)var3.get(0);
            String var592 = (String)var3.get(1);
            if(!var591.equals(var9) && var592.length() > 0) {
               var7 = String.format("FileUtil.listDir(%s, %s);", new Object[]{var591, var592});
               break label3268;
            }
            break;
         case 272:
            String var593 = (String)var3.get(0);
            if(!var593.equals(var9)) {
               var7 = String.format("FileUtil.isDirectory(%s)", new Object[]{var593});
               break label3268;
            }
            break;
         case 273:
            String var594 = (String)var3.get(0);
            if(!var594.equals(var9)) {
               var7 = String.format("FileUtil.isFile(%s)", new Object[]{var594});
               break label3268;
            }
            break;
         case 274:
            String var595 = (String)var3.get(0);
            if(!var595.equals(var9)) {
               var7 = String.format("FileUtil.getFileLength(%s)", new Object[]{var595});
               break label3268;
            }
            break;
         case 275:
            String var596 = (String)var3.get(0);
            String var597 = (String)var3.get(1);
            if(!var596.equals(var9) && var597.length() > 0) {
               var7 = String.format("%s.startsWith(%s)", new Object[]{var596, var597});
               break label3268;
            }
            break;
         case 276:
            String var598 = (String)var3.get(0);
            String var599 = (String)var3.get(1);
            if(!var598.equals(var9) && var599.length() > 0) {
               var7 = String.format("%s.endsWith(%s)", new Object[]{var598, var599});
               break label3268;
            }
            break;
         case 277:
            String var600 = (String)var3.get(0);
            if(!var600.equals(var9)) {
               var7 = String.format("Uri.parse(%s).getLastPathSegment()", new Object[]{var600});
               break label3268;
            }
            break;
         case 278:
            var7 = "FileUtil.getExternalStorageDir()";
            break label3268;
         case 279:
            var7 = "FileUtil.getPackageDataDir(getApplicationContext())";
            break label3268;
         case 280:
            String var601 = (String)var3.get(0);
            if(var601.length() > 0) {
               var7 = String.format("FileUtil.getPublicDir(Environment.%s)", new Object[]{var601});
               break label3268;
            }
            break;
         case 281:
            String var602 = (String)var3.get(0);
            String var603 = (String)var3.get(1);
            String var604 = (String)var3.get(2);
            if(var604.length() <= 0) {
               var604 = "1024";
            }

            if(!var602.equals(var9) && !var603.equals(var9)) {
               var7 = String.format("FileUtil.resizeBitmapFileRetainRatio(%s, %s, %s);", new Object[]{var602, var603, var604});
               break label3268;
            }
            break;
         case 282:
            String var605 = (String)var3.get(0);
            String var606 = (String)var3.get(1);
            String var607 = (String)var3.get(2);
            if(var607.length() <= 0) {
               var607 = "1024";
            }

            if(!var605.equals(var9) && !var606.equals(var9)) {
               var7 = String.format("FileUtil.resizeBitmapFileToSquare(%s, %s, %s);", new Object[]{var605, var606, var607});
               break label3268;
            }
            break;
         case 283:
            String var608 = (String)var3.get(0);
            String var609 = (String)var3.get(1);
            if(!var608.equals(var9) && !var609.equals(var9)) {
               var7 = String.format("FileUtil.resizeBitmapFileToCircle(%s, %s);", new Object[]{var608, var609});
               break label3268;
            }
            break;
         case 284:
            String var610 = (String)var3.get(0);
            String var611 = (String)var3.get(1);
            String var612 = (String)var3.get(2);
            if(var612.length() > 0) {
               var10 = var612;
            }

            if(!var610.equals(var9) && !var611.equals(var9)) {
               var7 = String.format("FileUtil.resizeBitmapFileWithRoundedBorder(%s, %s, %s);", new Object[]{var610, var611, var10});
               break label3268;
            }
            break;
         case 285:
            String var613 = (String)var3.get(0);
            String var614 = (String)var3.get(1);
            String var615 = (String)var3.get(2);
            String var616 = (String)var3.get(3);
            if(var615.length() <= 0) {
               var615 = "1024";
            }

            if(var616.length() <= 0) {
               var616 = "1024";
            }

            if(!var613.equals(var9) && !var614.equals(var9)) {
               var7 = String.format("FileUtil.cropBitmapFileFromCenter(%s, %s, %s, %s);", new Object[]{var613, var614, var615, var616});
               break label3268;
            }
            break;
         case 286:
            String var617 = (String)var3.get(0);
            String var618 = (String)var3.get(1);
            String var619 = (String)var3.get(2);
            if(var619.length() > 0) {
               var10 = var619;
            }

            if(!var617.equals(var9) && !var618.equals(var9)) {
               var7 = String.format("FileUtil.rotateBitmapFile(%s, %s, %s);", new Object[]{var617, var618, var10});
               break label3268;
            }
            break;
         case 287:
            String var620 = (String)var3.get(0);
            String var621 = (String)var3.get(1);
            String var622 = (String)var3.get(2);
            String var623 = (String)var3.get(3);
            if(var622.length() <= 0) {
               var622 = "1";
            }

            if(var623.length() <= 0) {
               var623 = "1";
            }

            if(!var620.equals(var9) && !var621.equals(var9)) {
               var7 = String.format("FileUtil.scaleBitmapFile(%s, %s, %s, %s);", new Object[]{var620, var621, var622, var623});
               break label3268;
            }
            break;
         case 288:
            String var624 = (String)var3.get(0);
            String var625 = (String)var3.get(1);
            String var626 = (String)var3.get(2);
            String var627 = (String)var3.get(3);
            if(var626.length() <= 0) {
               var626 = var10;
            }

            if(var627.length() > 0) {
               var10 = var627;
            }

            if(!var624.equals(var9) && !var625.equals(var9)) {
               var7 = String.format("FileUtil.skewBitmapFile(%s, %s, %s, %s);", new Object[]{var624, var625, var626, var10});
               break label3268;
            }
            break;
         case 289:
            String var628 = (String)var3.get(0);
            String var629 = (String)var3.get(1);
            String var630 = (String)var3.get(2);
            if(var630.length() <= 0) {
               var630 = "0x00000000";
            }

            if(!var628.equals(var9) && !var629.equals(var9)) {
               var7 = String.format("FileUtil.setBitmapFileColorFilter(%s, %s, %s);", new Object[]{var628, var629, var630});
               break label3268;
            }
            break;
         case 290:
            String var631 = (String)var3.get(0);
            String var632 = (String)var3.get(1);
            String var633 = (String)var3.get(2);
            if(var633.length() > 0) {
               var10 = var633;
            }

            if(!var631.equals(var9) && !var632.equals(var9)) {
               var7 = String.format("FileUtil.setBitmapFileBrightness(%s, %s, %s);", new Object[]{var631, var632, var10});
               break label3268;
            }
            break;
         case 291:
            String var634 = (String)var3.get(0);
            String var635 = (String)var3.get(1);
            String var636 = (String)var3.get(2);
            if(var636.length() > 0) {
               var10 = var636;
            }

            if(!var634.equals(var9) && !var635.equals(var9)) {
               var7 = String.format("FileUtil.setBitmapFileContrast(%s, %s, %s);", new Object[]{var634, var635, var10});
               break label3268;
            }
            break;
         case 292:
            String var637 = (String)var3.get(0);
            if(!var637.equals(var9)) {
               var7 = String.format("FileUtil.getJpegRotate(%s)", new Object[]{var637});
               break label3268;
            }
            break;
         case 293:
            String var638 = (String)var3.get(0);
            if(var638.length() > 0) {
               Object[] var639 = new Object[]{var638, var638.toUpperCase()};
               var7 = String.format("startActivityForResult(%s, REQ_CD_%s);", var639);
               break label3268;
            }
            break;
         case 294:
            String var640 = (String)var3.get(0);
            if(var640.length() > 0) {
               Object[] var641 = new Object[]{var640, var640.toUpperCase()};
               var7 = String.format("startActivityForResult(%s, REQ_CD_%s);", var641);
               break label3268;
            }
            break;
         case 295:
            String var642 = (String)var3.get(0);
            String var643 = (String)var3.get(1);
            if(var642.length() > 0 && !var643.equals(var9)) {
               var7 = String.format("%s.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(%s, 1024, 1024));", new Object[]{var642, var643});
               break label3268;
            }
            break;
         case 296:
            String var644 = (String)var3.get(0);
            String var645 = (String)var3.get(1);
            if(var644.length() > 0 && !var645.equals(var9)) {
               var7 = String.format("Glide.with(getApplicationContext()).load(Uri.parse(%s)).into(%s);", new Object[]{var645, var644});
               break label3268;
            }
            break;
         case 297:
            String var646 = (String)var3.get(0);
            String var647 = (String)var3.get(1);
            if(var646.length() > 0 && !var647.equals(var9)) {
               var7 = String.format("%s.setHint(%s);", new Object[]{var646, var647});
               break label3268;
            }
            break;
         case 298:
            String var648 = (String)var3.get(0);
            String var649 = (String)var3.get(1);
            if(var648.length() > 0 && !var649.equals(var9)) {
               var7 = String.format("%s.setHintTextColor(%s);", new Object[]{var648, var649});
               break label3268;
            }
            break;
         case 299:
            String var650 = (String)var3.get(0);
            String var651 = (String)var3.get(1);
            String var652 = (String)var3.get(2);
            if(var650.length() > 0 && var651.length() > 0 && var652.length() > 0) {
               var7 = String.format("%s.setParams(%s, RequestNetworkController.%s);", new Object[]{var650, var651, var652});
               break label3268;
            }
            break;
         case 300:
            String var653 = (String)var3.get(0);
            String var654 = (String)var3.get(1);
            if(var653.length() > 0 && var654.length() > 0) {
               var7 = String.format("%s.setHeaders(%s);", new Object[]{var653, var654});
               break label3268;
            }
            break;
         case 301:
            String var655 = (String)var3.get(0);
            String var656 = (String)var3.get(1);
            String var657 = (String)var3.get(2);
            String var658 = (String)var3.get(3);
            if(var655.length() > 0 && var656.length() > 0 && !var657.equals(var9) && var658.length() > 0) {
               var7 = String.format("%s.startRequestNetwork(RequestNetworkController.%s, %s, %s, _%s_request_listener);", new Object[]{var655, var656, var657, var658, var655});
               break label3268;
            }
            break;
         case 302:
            String var659 = (String)var3.get(0);
            String var660 = (String)var3.get(1);
            if(var660.length() <= 0) {
               var660 = var7;
            }

            if(var659.length() > 0) {
               var7 = String.format("%s.setIndeterminate(%s);", new Object[]{var659, var660});
               break label3268;
            }
            break;
         case 303:
            String var661 = (String)var3.get(0);
            String var662 = (String)var3.get(1);
            if(var662.length() <= 0) {
               var662 = "1";
            }

            if(var661.length() > 0) {
               var7 = String.format("%s.setPitch((float)%s);", new Object[]{var661, var662});
               break label3268;
            }
            break;
         case 304:
            String var663 = (String)var3.get(0);
            String var664 = (String)var3.get(1);
            if(var664.length() <= 0) {
               var664 = "1";
            }

            if(var663.length() > 0) {
               var7 = String.format("%s.setSpeechRate((float)%s);", new Object[]{var663, var664});
               break label3268;
            }
            break;
         case 305:
            String var665 = (String)var3.get(0);
            String var666 = (String)var3.get(1);
            if(var665.length() > 0 && var666.length() > 0) {
               var7 = String.format("%s.speak(%s, TextToSpeech.QUEUE_ADD, null);", new Object[]{var665, var666});
               break label3268;
            }
            break;
         case 306:
            String var667 = (String)var3.get(0);
            if(var667.length() > 0) {
               var7 = String.format("%s.isSpeaking()", new Object[]{var667});
               break label3268;
            }
            break;
         case 307:
            String var668 = (String)var3.get(0);
            if(var668.length() > 0) {
               var7 = String.format("%s.stop();", new Object[]{var668});
               break label3268;
            }
            break;
         case 308:
            String var669 = (String)var3.get(0);
            if(var669.length() > 0) {
               var7 = String.format("%s.shutdown();", new Object[]{var669});
               break label3268;
            }
            break;
         case 309:
            String var670 = (String)var3.get(0);
            if(var670.length() > 0) {
               var7 = String.format("Intent _intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);\n_intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());\n_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);\n_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());\n%s.startListening(_intent);", new Object[]{var670});
               break label3268;
            }
            break;
         case 310:
            String var671 = (String)var3.get(0);
            if(var671.length() > 0) {
               var7 = String.format("%s.stopListening();", new Object[]{var671});
               break label3268;
            }
            break;
         case 311:
            String var672 = (String)var3.get(0);
            if(var672.length() > 0) {
               var7 = String.format("%s.cancel();\n%s.destroy();", new Object[]{var672, var672});
               break label3268;
            }
            break;
         case 312:
            String var673 = (String)var3.get(0);
            String var674 = (String)var3.get(1);
            if(var673.length() > 0 && var674.length() > 0) {
               var7 = String.format("%s.readyConnection(_%s_bluetooth_connection_listener, %s);", new Object[]{var673, var673, var674});
               break label3268;
            }
            break;
         case 313:
            String var675 = (String)var3.get(0);
            String var676 = (String)var3.get(1);
            String var677 = (String)var3.get(2);
            if(var675.length() > 0 && var676.length() > 0 && var677.length() > 0) {
               var7 = String.format("%s.readyConnection(_%s_bluetooth_connection_listener, %s, %s);", new Object[]{var675, var675, var676, var677});
               break label3268;
            }
            break;
         case 314:
            String var678 = (String)var3.get(0);
            String var679 = (String)var3.get(1);
            String var680 = (String)var3.get(2);
            if(var678.length() > 0 && var679.length() > 0 && var680.length() > 0) {
               var7 = String.format("%s.startConnection(_%s_bluetooth_connection_listener, %s, %s);", new Object[]{var678, var678, var679, var680});
               break label3268;
            }
            break;
         case 315:
            String var681 = (String)var3.get(0);
            String var682 = (String)var3.get(1);
            String var683 = (String)var3.get(2);
            String var684 = (String)var3.get(3);
            if(var681.length() > 0 && var682.length() > 0 && var683.length() > 0 && var684.length() > 0) {
               var7 = String.format("%s.startConnection(_%s_bluetooth_connection_listener, %s, %s, %s);", new Object[]{var681, var681, var682, var683, var684});
               break label3268;
            }
            break;
         case 316:
            String var685 = (String)var3.get(0);
            String var686 = (String)var3.get(1);
            if(var685.length() > 0 && var686.length() > 0) {
               var7 = String.format("%s.stopConnection(_%s_bluetooth_connection_listener, %s);", new Object[]{var685, var685, var686});
               break label3268;
            }
            break;
         case 317:
            String var687 = (String)var3.get(0);
            String var688 = (String)var3.get(1);
            String var689 = (String)var3.get(2);
            if(var687.length() > 0 && var688.length() > 0 && var689.length() > 0) {
               var7 = String.format("%s.sendData(_%s_bluetooth_connection_listener, %s, %s);", new Object[]{var687, var687, var688, var689});
               break label3268;
            }
            break;
         case 318:
            String var690 = (String)var3.get(0);
            if(var690.length() > 0) {
               var7 = String.format("%s.isBluetoothEnabled()", new Object[]{var690});
               break label3268;
            }
            break;
         case 319:
            String var691 = (String)var3.get(0);
            if(var691.length() > 0) {
               var7 = String.format("%s.isBluetoothActivated()", new Object[]{var691});
               break label3268;
            }
            break;
         case 320:
            String var692 = (String)var3.get(0);
            if(var692.length() > 0) {
               var7 = String.format("%s.activateBluetooth();", new Object[]{var692});
               break label3268;
            }
            break;
         case 321:
            String var693 = (String)var3.get(0);
            String var694 = (String)var3.get(1);
            if(var693.length() > 0 && var694.length() > 0) {
               var7 = String.format("%s.getPairedDevices(%s);", new Object[]{var693, var694});
               break label3268;
            }
            break;
         case 322:
            String var695 = (String)var3.get(0);
            if(var695.length() > 0) {
               var7 = String.format("%s.getRandomUUID()", new Object[]{var695});
               break label3268;
            }
            break;
         case 323:
            String var696 = (String)var3.get(0);
            String var697 = (String)var3.get(1);
            if(var697.length() <= 0) {
               var697 = uq.p[0];
            }

            String var698 = (String)var3.get(2);
            if(var698.length() <= 0) {
               var698 = "1000";
            }

            String var699 = (String)var3.get(3);
            if(var699.length() > 0) {
               var10 = var699;
            }

            if(var696.length() > 0) {
               if(this.e.g) {
                  Object[] var700 = new Object[]{this.c, var696, var697, var698, var10, var696};
                  var7 = String.format("if (ContextCompat.checkSelfPermission(%s.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {\n%s.requestLocationUpdates(LocationManager.%s, %s, %s, _%s_location_listener);\n}", var700);
               } else {
                  var7 = String.format("if (Build.VERSION.SDK_INT >= 23) {if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {\n%s.requestLocationUpdates(LocationManager.%s, %s, %s, _%s_location_listener);\n}\n}\nelse {\n%s.requestLocationUpdates(LocationManager.%s, %s, %s, _%s_location_listener);\n}", new Object[]{var696, var697, var698, var10, var696, var696, var697, var698, var10, var696});
               }
               break label3268;
            }
            break;
         case 324:
            String var701 = (String)var3.get(0);
            if(var701.length() > 0) {
               var7 = String.format("%s.removeUpdates(_%s_location_listener);", new Object[]{var701, var701});
               break label3268;
            }
         }

         var11 = this.mceb.getCodeExtraBlock(var1, var11);
         var7 = var11;
      }

      if(this.b(var1.opCode, var2)) {
         StringBuilder var27 = new StringBuilder();
         var27.append("(");
         var27.append(var7);
         var27.append(")");
         var7 = var27.toString();
      }

      if(var1.nextBlock >= 0) {
         StringBuilder var31 = new StringBuilder();
         var31.append(var7);
         var31.append("\r\n");
         var31.append(this.a(String.valueOf(var1.nextBlock), var11));
         var7 = var31.toString();
      }

      return var7;
   }
   
   public final String a(String str) {
        StringBuilder stringBuilder = new StringBuilder(4096);
        CharBuffer wrap = CharBuffer.wrap(str);
        int i = 0;
        while (i < wrap.length()) {
            char c = wrap.get(i);
            if (c == '\"') {
                stringBuilder.append("\\\"");
            } else if (c == '\\') {
                if (i < wrap.length() - 1) {
                    int i2 = i + 1;
                    char c2 = wrap.get(i2);
                    if (c2 == 'n' || c2 == 't') {
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("\\");
                        stringBuilder2.append(c2);
                        stringBuilder.append(stringBuilder2.toString());
                        i = i2;
                    } else {
                        stringBuilder.append("\\\\");
                    }
                } else {
                    stringBuilder.append("\\\\");
                }
            } else if (c == '\n') {
                stringBuilder.append("\\n");
            } else {
                stringBuilder.append(c);
            }
            i++;
        }
        return stringBuilder.toString();
    }

    public final String a(String str, int i, String str2) {
        if (str.length() > 0 && str.charAt(0) == '@') {
            String a = a(str.substring(1), str2);
            if (i == 2 && a.length() <= 0) {
                a = "\"\"";
            }
            return a;
        } else if (i == 2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\"");
            stringBuilder.append(a(str));
            stringBuilder.append("\"");
            return stringBuilder.toString();
        } else if (i != 1) {
            return str;
        } else {
        	try {
	            Integer.parseInt(str);
	            return str;
            } catch (NumberFormatException e) {
	            try {
					Double.parseDouble(str);
	                StringBuilder sb = new StringBuilder();
	                sb.append(str);
	                sb.append("d");
	                str = sb.toString();
					return str;
	            } catch (NumberFormatException e2) {
		            return str;
	            }
            }
        }
    }

    public final String a(String str, String str2) {
        return !this.g.containsKey(str) ? "" : a((BlockBean) this.g.get(str), str2);
    }

    public final boolean b(String str, String str2) {
        boolean z;
        boolean z2;
        for (String equals : this.a) {
            if (equals.equals(str2)) {
                z = true;
                break;
            }
        }
        z = false;
        for (String equals2 : this.b) {
            if (equals2.equals(str)) {
                z2 = true;
                break;
            }
        }
        z2 = false;
        return z && z2;
    }
}