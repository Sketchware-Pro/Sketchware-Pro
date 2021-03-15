//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package a.a.a;

import com.besome.sketch.beans.ViewBean;
import java.util.ArrayList;
import java.util.Iterator;
import mod.agus.jcoderz.editor.event.ManageEvent;
import mod.agus.jcoderz.editor.event.ManageEventComponent;
import mod.agus.jcoderz.handle.code.CodeResult;
import mod.agus.jcoderz.handle.component.ConstVarWidget;
import mod.agus.jcoderz.lib.TypeVarComponent;
import mod.hey.studios.build.BuildSettings;
import mod.hey.studios.moreblock.ReturnMoreblockManager;

public class Lx {

    enum a {
        a, // = MODE_PRIVATE
        b, // = MODE_PROTECTED
        c // = MODE_PUBLIC
    }

    public static String a() {
        return "include ':app'";
    }

    public static String a(int compileSdkVersion, int minSdkVersion, int var2, jq var3) {
        StringBuilder var4 = new StringBuilder();
        String startString = "apply plugin: 'com.android.application'\r\n\nandroid {\r\n";
        if ((new BuildSettings(var3.sc_id)).getValue("no_http_legacy", "false").equals("false")) {
            startString += "useLibrary 'org.apache.http.legacy'\r\n";
        }
        startString += "compileSdkVersion ";

        var4.append(startString);
        var4.append(compileSdkVersion);
        var4.append("\r\n");
        var4.append("\r\n");
        var4.append("defaultConfig {");
        var4.append("\r\n");
        var4.append("applicationId \"");
        var4.append(var3.a);
        var4.append("\"");
        var4.append("\r\n");
        var4.append("minSdkVersion ");
        var4.append(minSdkVersion);
        var4.append("\r\n");
        var4.append("targetSdkVersion ");
        var4.append(var2);
        var4.append("\r\n");
        var4.append("versionCode ");
        var4.append(var3.c);
        var4.append("\r\n");
        var4.append("versionName \"");
        var4.append(var3.d);
        var4.append("\"");
        var4.append("\r\n");
        var4.append("}");
        var4.append("\r\n");
        var4.append("buildTypes {");
        var4.append("\r\n");
        var4.append("release {");
        var4.append("\r\n");
        var4.append("minifyEnabled false");
        var4.append("\r\n");
        var4.append("proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'");
        var4.append("\r\n");
        var4.append("}");
        var4.append("\r\n");
        var4.append("}");
        var4.append("\r\n");
        var4.append("}");
        var4.append("\r\n");
        var4.append("\r\n");
        var4.append("dependencies {");
        var4.append("\r\n");
        var4.append("implementation fileTree(dir: 'libs', include: ['*.jar'])");
        var4.append("\r\n");
        String var9 = var4.toString();
        startString = var9;
        StringBuilder var10;
        if (var3.g) {
            var10 = new StringBuilder();
            var10.append(var9);
            var10.append("implementation 'androidx.appcompat:appcompat:1.2.0'\r\n");
            var9 = var10.toString();
            var10 = new StringBuilder();
            var10.append(var9);
            var10.append("implementation 'com.google.android.material:material:1.3.0-alpha04'\r\n");
            startString = var10.toString();
        }

        var9 = startString;
        if (var3.i) {
            var4 = new StringBuilder();
            var4.append(startString);
            var4.append("implementation 'com.google.firebase:firebase-auth:19.0.0'\r\n");
            var9 = var4.toString();
        }

        String var6 = var9;
        if (var3.j) {
            var10 = new StringBuilder();
            var10.append(var9);
            var10.append("implementation 'com.google.firebase:firebase-database:19.0.0'\r\n");
            var6 = var10.toString();
        }

        startString = var6;
        if (var3.k) {
            var10 = new StringBuilder();
            var10.append(var6);
            var10.append("implementation 'com.google.firebase:firebase-storage:19.0.0'\r\n");
            startString = var10.toString();
        }

        var9 = startString;
        if (var3.l) {
            var4 = new StringBuilder();
            var4.append(startString);
            var4.append("implementation 'com.google.android.gms:play-services-ads:18.2.0'\r\n");
            var9 = var4.toString();
        }

        startString = var9;
        StringBuilder var7;
        if (var3.m) {
            var7 = new StringBuilder();
            var7.append(var9);
            var7.append("implementation 'com.google.android.gms:play-services-maps:17.0.0'\r\n");
            startString = var7.toString();
        }

        var7 = new StringBuilder();
        var7.append(startString);
        var7.append("implementation 'com.github.bumptech.glide:glide:3.7.0'\r\n");
        String var8 = var7.toString();
        var10 = new StringBuilder();
        var10.append(var8);
        var10.append("implementation 'com.google.code.gson:gson:2.8.0'\r\n");
        var8 = var10.toString();
        var10 = new StringBuilder();
        var10.append(var8);
        var10.append("implementation 'com.squareup.okhttp3:okhttp:3.9.1'\r\n");
        startString = var10.toString();
        var7 = new StringBuilder();
        var7.append(startString);
        var7.append("}\r\n");
        return j(var7.toString());
    }

    public static String a(int var0, String var1, String var2, String var3) {
        String var4;
        if (var0 == 16) {
            var4 = "ArrayList<String> _filePath = new ArrayList<>();\r\nif (_data != null) {\r\nif (_data.getClipData() != null) {\r\nfor (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {\r\nClipData.Item _item = _data.getClipData().getItemAt(_index);\r\n_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));\r\n}\r\n}\r\nelse {\r\n_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));\r\n}\r\n}";
        } else if (var0 == 15) {
            var4 = " String _filePath = _file_" +
                    var1 +
                    ".getAbsolutePath();" +
                    "\r\n";
        } else {
            var4 = CodeResult.a(var0, var1);
        }

        String var5 = "case REQ_CD_" +
                var1.toUpperCase() +
                ":" +
                "\r\n" +
                "if (_resultCode == Activity.RESULT_OK) {" +
                "\r\n" +
                var4 +
                "\r\n" +
                "%s" +
                "\r\n" +
                "}" +
                "\r\n" +
                "else {" +
                "\r\n" +
                "%s" +
                "\r\n" +
                "}" +
                "\r\n" +
                "break;";
        return String.format(var5, var2, var3);
    }

    public static String a(ViewBean var0) {
        String var1;
        if (!var0.convert.isEmpty()) {
            var1 = var0.convert;
        } else {
            var1 = var0.getClassInfo().a();
        }

        String var2;
        var2 = "final " + var1 + " " + var0.id + " = (" + var1 + ") _view.findViewById(R.id." + var0.id + ");";

        return var2;
    }

    public static String a(String var0) {
        return var0.substring(0, 1).toUpperCase() +
                var0.substring(1) +
                "Adapter";
    }

    public static String a(String var0, int var1) {
        return "public final int REQ_CD_" +
                var0.toUpperCase() +
                " = " +
                var1 +
                ";";
    }

    public static String a(String var0, String var1) {
        String var2;
        byte var3;
        label251: {
            var2 = ManageEvent.f(var0, var1);
            switch(var0.hashCode()) {
                case -2117913147:
                    if (var0.equals("onStartTrackingTouch")) {
                        var3 = 17;
                        break label251;
                    }
                    break;
                case -2107467445:
                    if (var0.equals("afterTextChanged")) {
                        var3 = 14;
                        break label251;
                    }
                    break;
                case -2067423513:
                    if (var0.equals("onSpeechError")) {
                        var3 = 49;
                        break label251;
                    }
                    break;
                case -1865337024:
                    if (var0.equals("onResponse")) {
                        var3 = 46;
                        break label251;
                    }
                    break;
                case -1809154262:
                    if (var0.equals("onDataReceived")) {
                        var3 = 51;
                        break label251;
                    }
                    break;
                case -1779618840:
                    if (var0.equals("onProgressChanged")) {
                        var3 = 16;
                        break label251;
                    }
                    break;
                case -1708629179:
                    if (var0.equals("onSignInUserComplete")) {
                        var3 = 34;
                        break label251;
                    }
                    break;
                case -1401315045:
                    if (var0.equals("onDestroy")) {
                        var3 = 5;
                        break label251;
                    }
                    break;
                case -1384106084:
                    if (var0.equals("onAccuracyChanged")) {
                        var3 = 32;
                        break label251;
                    }
                    break;
                case -1358405466:
                    if (var0.equals("onMapReady")) {
                        var3 = 55;
                        break label251;
                    }
                    break;
                case -1351902487:
                    if (var0.equals("onClick")) {
                        var3 = 0;
                        break label251;
                    }
                    break;
                case -1340212393:
                    if (var0.equals("onPause")) {
                        var3 = 7;
                        break label251;
                    }
                    break;
                case -1336895037:
                    if (var0.equals("onStart")) {
                        var3 = 3;
                        break label251;
                    }
                    break;
                case -1215328199:
                    if (var0.equals("onDeleteSuccess")) {
                        var3 = 44;
                        break label251;
                    }
                    break;
                case -1153785290:
                    if (var0.equals("onAnimationEnd")) {
                        var3 = 24;
                        break label251;
                    }
                    break;
                case -1111243300:
                    if (var0.equals("onBackPressed")) {
                        var3 = 1;
                        break label251;
                    }
                    break;
                case -1012956543:
                    if (var0.equals("onStop")) {
                        var3 = 4;
                        break label251;
                    }
                    break;
                case -891988931:
                    if (var0.equals("onDateChange")) {
                        var3 = 19;
                        break label251;
                    }
                    break;
                case -837428873:
                    if (var0.equals("onChildChanged")) {
                        var3 = 27;
                        break label251;
                    }
                    break;
                case -821066400:
                    if (var0.equals("onLocationChanged")) {
                        var3 = 57;
                        break label251;
                    }
                    break;
                case -749253875:
                    if (var0.equals("onUploadProgress")) {
                        var3 = 40;
                        break label251;
                    }
                    break;
                case -732782352:
                    if (var0.equals("onConnectionStopped")) {
                        var3 = 54;
                        break label251;
                    }
                    break;
                case -719893013:
                    if (var0.equals("onConnectionError")) {
                        var3 = 53;
                        break label251;
                    }
                    break;
                case -672992515:
                    if (var0.equals("onAnimationStart")) {
                        var3 = 22;
                        break label251;
                    }
                    break;
                case -609996822:
                    if (var0.equals("onConnected")) {
                        var3 = 50;
                        break label251;
                    }
                    break;
                case -584901992:
                    if (var0.equals("onCheckedChange")) {
                        var3 = 8;
                        break label251;
                    }
                    break;
                case -536246231:
                    if (var0.equals("onResetPasswordEmailSent")) {
                        var3 = 35;
                        break label251;
                    }
                    break;
                case -507667891:
                    if (var0.equals("onItemSelected")) {
                        var3 = 9;
                        break label251;
                    }
                    break;
                case -505277536:
                    if (var0.equals("onPageFinished")) {
                        var3 = 21;
                        break label251;
                    }
                    break;
                case -484536541:
                    if (var0.equals("onChildRemoved")) {
                        var3 = 28;
                        break label251;
                    }
                    break;
                case -376002870:
                    if (var0.equals("onErrorResponse")) {
                        var3 = 47;
                        break label251;
                    }
                    break;
                case 80616227:
                    if (var0.equals("onUploadSuccess")) {
                        var3 = 42;
                        break label251;
                    }
                    break;
                case 136827711:
                    if (var0.equals("onAnimationCancel")) {
                        var3 = 23;
                        break label251;
                    }
                    break;
                case 204442875:
                    if (var0.equals("onPostCreate")) {
                        var3 = 2;
                        break label251;
                    }
                    break;
                case 249705131:
                    if (var0.equals("onFailure")) {
                        var3 = 45;
                        break label251;
                    }
                    break;
                case 264008033:
                    if (var0.equals("onDataSent")) {
                        var3 = 52;
                        break label251;
                    }
                    break;
                case 372583555:
                    if (var0.equals("onChildAdded")) {
                        var3 = 26;
                        break label251;
                    }
                    break;
                case 378110312:
                    if (var0.equals("onTextChanged")) {
                        var3 = 15;
                        break label251;
                    }
                    break;
                case 384010806:
                    if (var0.equals("onChildMoved")) {
                        var3 = 29;
                        break label251;
                    }
                    break;
                case 445802034:
                    if (var0.equals("onCancelled")) {
                        var3 = 30;
                        break label251;
                    }
                    break;
                case 570020448:
                    if (var0.equals("onAnimationRepeat")) {
                        var3 = 25;
                        break label251;
                    }
                    break;
                case 601233006:
                    if (var0.equals("onAdClosed")) {
                        var3 = 39;
                        break label251;
                    }
                    break;
                case 694589214:
                    if (var0.equals("onSpeechResult")) {
                        var3 = 48;
                        break label251;
                    }
                    break;
                case 805710389:
                    if (var0.equals("onItemClicked")) {
                        var3 = 11;
                        break label251;
                    }
                    break;
                case 861234439:
                    if (var0.equals("onAdLoaded")) {
                        var3 = 36;
                        break label251;
                    }
                    break;
                case 863618555:
                    if (var0.equals("onSensorChanged")) {
                        var3 = 31;
                        break label251;
                    }
                    break;
                case 948174187:
                    if (var0.equals("onAdOpened")) {
                        var3 = 38;
                        break label251;
                    }
                    break;
                case 956173256:
                    if (var0.equals("beforeTextChanged")) {
                        var3 = 13;
                        break label251;
                    }
                    break;
                case 1348442836:
                    if (var0.equals("onDownloadProgress")) {
                        var3 = 41;
                        break label251;
                    }
                    break;
                case 1395209852:
                    if (var0.equals("onDownloadSuccess")) {
                        var3 = 43;
                        break label251;
                    }
                    break;
                case 1463983852:
                    if (var0.equals("onResume")) {
                        var3 = 6;
                        break label251;
                    }
                    break;
                case 1586033095:
                    if (var0.equals("onStopTrackingTouch")) {
                        var3 = 18;
                        break label251;
                    }
                    break;
                case 1633718655:
                    if (var0.equals("onCreateUserComplete")) {
                        var3 = 33;
                        break label251;
                    }
                    break;
                case 1705537961:
                    if (var0.equals("onNothingSelected")) {
                        var3 = 10;
                        break label251;
                    }
                    break;
                case 1710477203:
                    if (var0.equals("onPageStarted")) {
                        var3 = 20;
                        break label251;
                    }
                    break;
                case 1803231982:
                    if (var0.equals("onMarkerClicked")) {
                        var3 = 56;
                        break label251;
                    }
                    break;
                case 1855724576:
                    if (var0.equals("onAdFailedToLoad")) {
                        var3 = 37;
                        break label251;
                    }
                    break;
                case 1979400473:
                    if (var0.equals("onItemLongClicked")) {
                        var3 = 12;
                        break label251;
                    }
            }

            var3 = -1;
        }

        var0 = var1;
        switch(var3) {
            case 0:
                var0 = String.format("@Override\r\npublic void onClick(View _view) {\r\n%s\r\n}", var1);
            case 55:
                var1 = var0;
                if (var0.equals("")) {
                    var1 = var2;
                }

                return var1;
            case 1:
                var0 = String.format("@Override\r\npublic void onBackPressed() {\r\n%s\r\n}", var1);
                break;
            case 2:
                var0 = String.format("@Override\r\nprotected void onPostCreate(Bundle _savedInstanceState) {\r\nsuper.onPostCreate(_savedInstanceState);\r\n%s\r\n}", var1);
                break;
            case 3:
                var0 = String.format("@Override\r\npublic void onStart() {\r\nsuper.onStart();\r\n%s\r\n}", var1);
                break;
            case 4:
                var0 = String.format("@Override\r\npublic void onStop() {\r\nsuper.onStop();\r\n%s\r\n}", var1);
                break;
            case 5:
                var0 = String.format("@Override\r\npublic void onDestroy() {\r\nsuper.onDestroy();\r\n%s\r\n}", var1);
                break;
            case 6:
                var0 = String.format("@Override\r\npublic void onResume() {\r\nsuper.onResume();\r\n%s\r\n}", var1);
                break;
            case 7:
                var0 = String.format("@Override\r\npublic void onPause() {\r\nsuper.onPause();\r\n%s\r\n}", var1);
                break;
            case 8:
                var0 = String.format("@Override\r\npublic void onCheckedChanged(CompoundButton _param1, boolean _param2)  {\r\nfinal boolean _isChecked = _param2;\r\n%s\r\n}", var1);
                break;
            case 9:
                var0 = String.format("@Override\r\npublic void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {\r\nfinal int _position = _param3;\r\n%s\r\n}", var1);
                break;
            case 10:
                var0 = String.format("@Override\r\npublic void onNothingSelected(AdapterView<?> _param1) {\r\n%s\r\n}", var1);
                break;
            case 11:
                var0 = String.format("@Override\r\npublic void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {\r\nfinal int _position = _param3;\r\n%s\r\n}", var1);
                break;
            case 12:
                var0 = String.format("@Override\r\npublic boolean onItemLongClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {\r\nfinal int _position = _param3;\r\n%s\r\nreturn true;\r\n}", var1);
                break;
            case 13:
                var0 = String.format("@Override\r\npublic void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {\r\n%s\r\n}", var1);
                break;
            case 14:
                var0 = String.format("@Override\r\npublic void afterTextChanged(Editable _param1) {\r\n%s\r\n}", var1);
                break;
            case 15:
                var0 = String.format("@Override\r\npublic void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {\r\nfinal String _charSeq = _param1.toString();\r\n%s\r\n}", var1);
                break;
            case 16:
                var0 = String.format("@Override\r\npublic void onProgressChanged (SeekBar _param1, int _param2, boolean _param3) {\r\nfinal int _progressValue = _param2;\r\n%s\r\n}", var1);
                break;
            case 17:
                var0 = String.format("@Override\r\npublic void onStartTrackingTouch(SeekBar _param1) {\r\n%s\r\n}", var1);
                break;
            case 18:
                var0 = String.format("@Override\r\npublic void onStopTrackingTouch(SeekBar _param2) {\r\n%s\r\n}", var1);
                break;
            case 19:
                var0 = String.format("@Override\r\npublic void onSelectedDayChange(CalendarView _param1, int _param2, int _param3, int _param4) {\r\nfinal int _year = _param2;\r\nfinal int _month = _param3;\r\nfinal int _day = _param4;\r\n%s\r\n}", var1);
                break;
            case 20:
                var0 = String.format("@Override\r\npublic void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {\r\nfinal String _url = _param2;\r\n%s\r\nsuper.onPageStarted(_param1, _param2, _param3);\r\n}", var1);
                break;
            case 21:
                var0 = String.format("@Override\r\npublic void onPageFinished(WebView _param1, String _param2) {\r\nfinal String _url = _param2;\r\n%s\r\nsuper.onPageFinished(_param1, _param2);\r\n}", var1);
                break;
            case 22:
                var0 = String.format("@Override\r\npublic void onAnimationStart(Animator _param1) {\r\n%s\r\n}", var1);
                break;
            case 23:
                var0 = String.format("@Override\r\npublic void onAnimationCancel(Animator _param1) {\r\n%s\r\n}", var1);
                break;
            case 24:
                var0 = String.format("@Override\r\npublic void onAnimationEnd(Animator _param1) {\r\n%s\r\n}", var1);
                break;
            case 25:
                var0 = String.format("@Override\r\npublic void onAnimationRepeat(Animator _param1) {\r\n%s\r\n}", var1);
                break;
            case 26:
                var0 = String.format("@Override\r\npublic void onChildAdded(DataSnapshot _param1, String _param2) {\r\nGenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};\r\nfinal String _childKey = _param1.getKey();\r\nfinal HashMap<String, Object> _childValue = _param1.getValue(_ind);\r\n%s\r\n}", var1);
                break;
            case 27:
                var0 = String.format("@Override\r\npublic void onChildChanged(DataSnapshot _param1, String _param2) {\r\nGenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};\r\nfinal String _childKey = _param1.getKey();\r\nfinal HashMap<String, Object> _childValue = _param1.getValue(_ind);\r\n%s\r\n}", var1);
                break;
            case 28:
                var0 = String.format("@Override\r\npublic void onChildRemoved(DataSnapshot _param1) {\r\nGenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};\r\nfinal String _childKey = _param1.getKey();\r\nfinal HashMap<String, Object> _childValue = _param1.getValue(_ind);\r\n%s\r\n}", var1);
                break;
            case 29:
                var0 = String.format("@Override\r\npublic void onChildMoved(DataSnapshot _param1, String _param2) {\r\n%s\r\n}", var1);
                break;
            case 30:
                var0 = String.format("@Override\r\npublic void onCancelled(DatabaseError _param1) {\r\nfinal int _errorCode = _param1.getCode();\r\nfinal String _errorMessage = _param1.getMessage();\r\n%s\r\n}", var1);
                break;
            case 31:
                var0 = String.format("@Override\r\npublic void onSensorChanged(SensorEvent _param1) {\r\nfloat[] _rotationMatrix = new float[16];\r\nSensorManager.getRotationMatrixFromVector(_rotationMatrix, _param1.values);\r\nfloat[] _remappedRotationMatrix = new float[16];\r\nSensorManager.remapCoordinateSystem(_rotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Z, _remappedRotationMatrix);\r\nfloat[] _orientations = new float[3];\r\nSensorManager.getOrientation(_remappedRotationMatrix, _orientations);\r\nfor(int _i = 0; _i < 3; _i++) {\r\n_orientations[_i] = (float)(Math.toDegrees(_orientations[_i]));\r\n}\r\nfinal double _x = _orientations[0];\r\nfinal double _y = _orientations[1];\r\nfinal double _z = _orientations[2];\r\n%s\r\n}", var1);
                break;
            case 32:
                var0 = String.format("@Override\r\npublic void onAccuracyChanged(Sensor _param1, int _param2) {\r\n%s\r\n}", var1);
                break;
            case 33:
            case 34:
                var0 = String.format("@Override\r\npublic void onComplete(Task<AuthResult> _param1) {\r\nfinal boolean _success = _param1.isSuccessful();\r\nfinal String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : \"\";\r\n%s\r\n}", var1);
                break;
            case 35:
                var0 = String.format("@Override\r\npublic void onComplete(Task<Void> _param1) {\r\nfinal boolean _success = _param1.isSuccessful();\r\n%s\r\n}", var1);
                break;
            case 36:
                var0 = String.format("@Override\r\npublic void onAdLoaded() {\r\n%s\r\n}", var1);
                break;
            case 37:
                var0 = String.format("@Override\r\npublic void onAdFailedToLoad(int _param1) {\r\nfinal int _errorCode = _param1;\r\n%s\r\n}", var1);
                break;
            case 38:
                var0 = String.format("@Override\r\npublic void onAdOpened() {\r\n%s\r\n}", var1);
                break;
            case 39:
                var0 = String.format("@Override\r\npublic void onAdClosed() {\r\n%s\r\n}", var1);
                break;
            case 40:
                var0 = String.format("@Override\r\npublic void onProgress(UploadTask.TaskSnapshot _param1) {\r\ndouble _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();\r\n%s\r\n}", var1);
                break;
            case 41:
                var0 = String.format("@Override\r\npublic void onProgress(FileDownloadTask.TaskSnapshot _param1) {\r\ndouble _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();\r\n%s\r\n}", var1);
                break;
            case 42:
                var0 = String.format("@Override\r\npublic void onComplete(Task<Uri> _param1) {\r\nfinal String _downloadUrl = _param1.getResult().toString();\r\n%s\r\n}", var1);
                break;
            case 43:
                var0 = String.format("@Override\r\npublic void onSuccess(FileDownloadTask.TaskSnapshot _param1) {\r\nfinal long _totalByteCount = _param1.getTotalByteCount();\r\n%s\r\n}", var1);
                break;
            case 44:
                var0 = String.format("@Override\r\npublic void onSuccess(Object _param1) {\r\n%s\r\n}", var1);
                break;
            case 45:
                var0 = String.format("@Override\r\npublic void onFailure(Exception _param1) {\r\nfinal String _message = _param1.getMessage();\r\n%s\r\n}", var1);
                break;
            case 46:
                var0 = String.format("@Override\r\npublic void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {\r\nfinal String _tag = _param1;\r\nfinal String _response = _param2;\r\nfinal HashMap<String, Object> _responseHeaders = _param3;\r\n%s\r\n}", var1);
                break;
            case 47:
                var0 = String.format("@Override\r\npublic void onErrorResponse(String _param1, String _param2) {\r\nfinal String _tag = _param1;\r\nfinal String _message = _param2;\r\n%s\r\n}", var1);
                break;
            case 48:
                var0 = String.format("@Override\r\npublic void onResults(Bundle _param1) {\r\nfinal ArrayList<String> _results = _param1.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);\r\nfinal String _result = _results.get(0);\r\n%s\r\n}", var1);
                break;
            case 49:
                var0 = String.format("@Override\r\npublic void onError(int _param1) {\r\nfinal String _errorMessage;\r\nswitch (_param1) {\r\ncase SpeechRecognizer.ERROR_AUDIO:\r\n_errorMessage = \"audio error\";\r\nbreak;\r\ncase SpeechRecognizer.ERROR_SPEECH_TIMEOUT:\r\n_errorMessage = \"speech timeout\";\r\nbreak;\r\ncase SpeechRecognizer.ERROR_NO_MATCH:\r\n_errorMessage = \"speech no match\";\r\nbreak;\r\ncase SpeechRecognizer.ERROR_RECOGNIZER_BUSY:\r\n_errorMessage = \"recognizer busy\";\r\nbreak;\r\ncase SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:\r\n_errorMessage = \"recognizer insufficient permissions\";\r\nbreak;\r\ndefault:\r\n_errorMessage = \"recognizer other error\";\r\nbreak;\r\n}\r\n%s\r\n}", var1);
                break;
            case 50:
                var0 = String.format("@Override\r\npublic void onConnected(String _param1, HashMap<String, Object> _param2) {\r\nfinal String _tag = _param1;\r\nfinal HashMap<String, Object> _deviceData = _param2;\r\n%s\r\n}", var1);
                break;
            case 51:
                var0 = String.format("@Override\r\npublic void onDataReceived(String _param1, byte[] _param2, int _param3) {\r\nfinal String _tag = _param1;\r\nfinal String _data = new String(_param2, 0, _param3);\r\n%s\r\n}", var1);
                break;
            case 52:
                var0 = String.format("@Override\r\npublic void onDataSent(String _param1, byte[] _param2) {\r\nfinal String _tag = _param1;\r\nfinal String _data = new String(_param2);\r\n%s\r\n}", var1);
                break;
            case 53:
                var0 = String.format("@Override\r\npublic void onConnectionError(String _param1, String _param2, String _param3) {\r\nfinal String _tag = _param1;\r\nfinal String _connectionState = _param2;\r\nfinal String _errorMessage = _param3;\r\n%s\r\n}", var1);
                break;
            case 54:
                var0 = String.format("@Override\r\npublic void onConnectionStopped(String _param1) {\r\nfinal String _tag = _param1;\r\n%s\r\n}", var1);
                break;
            case 56:
                var0 = String.format("@Override\r\npublic boolean onMarkerClick(Marker _param1) {\r\nfinal String _id = _param1.getTag().toString();\r\n%s\r\nreturn false;\r\n}", var1);
                break;
            case 57:
                var0 = String.format("@Override\r\npublic void onLocationChanged(Location _param1) {\r\nfinal double _lat = _param1.getLatitude();\r\nfinal double _lng = _param1.getLongitude();\r\nfinal double _acc = _param1.getAccuracy();\r\n%s\r\n}", var1);
                break;
            default:
                var0 = var2;
        }

        var1 = var0;
        return var1;
    }

    public static String a(String var0, String var1, a var2, String... var3) {
        int var4 = Kx.a[var2.ordinal()];
        String var7;
        if (var4 != 1) {
            if (var4 != 2) {
                if (var4 != 3) {
                    var7 = "";
                } else {
                    var7 = "public";
                }
            } else {
                var7 = "protected";
            }
        } else {
            var7 = "private";
        }

        if (!var0.equals("include") && !var0.equals("#")) {
            String var5 = a(var0, var1, var3);
            StringBuilder var8;
            if (var5.length() <= 0) {
                if (!mq.e(var0).equals("") && !mq.e(var0).equals("FirebaseCloudMessage")) {
                    var8 = new StringBuilder();
                    var8.append(var7);
                    var8.append(" ");
                    var8.append(mq.e(var0));
                    var8.append(" ");
                    var8.append(var1);
                    var8.append(";");
                    var7 = var8.toString();
                } else {
                    var7 = ConstVarWidget.a(var7, var0, var1);
                }
            } else {
                var8 = new StringBuilder();
                var8.append(var7);
                var8.append(" ");
                if (!mq.e(var0).equals("")) {
                    var8.append(mq.e(var0));
                } else {
                    var8.append(ConstVarWidget.b(var0));
                }

                var8.append(" ");
                var8.append(var1);
                var8.append(" = ");
                var8.append(var5);
                var8.append(";");
                var7 = var8.toString();
            }

            String var9 = ManageEventComponent.a(var0, var7, var1);
            StringBuilder var6;
            switch (var0) {
                case "FirebaseDB":
                    var6 = new StringBuilder();
                    var6.append(var9);
                    var6.append("\r\nprivate ChildEventListener _");
                    var6.append(var1);
                    var6.append("_child_listener;");
                    var7 = var6.toString();
                    break;
                case "Gyroscope":
                    var6 = new StringBuilder();
                    var6.append(var9);
                    var6.append("\r\nprivate SensorEventListener _");
                    var6.append(var1);
                    var6.append("_sensor_listener;");
                    var7 = var6.toString();
                    break;
                case "FirebaseAuth":
                    var6 = new StringBuilder();
                    var6.append(var9);
                    var6.append("\r\nprivate OnCompleteListener<AuthResult> _");
                    var6.append(var1);
                    var6.append("_create_user_listener;");
                    var6.append("\r\n");
                    var6.append("private OnCompleteListener<AuthResult> _");
                    var6.append(var1);
                    var6.append("_sign_in_listener;");
                    var6.append("\r\n");
                    var6.append("private OnCompleteListener<Void> _");
                    var6.append(var1);
                    var6.append("_reset_password_listener;");
                    var7 = var6.toString();
                    break;
                case "InterstitialAd":
                    var6 = new StringBuilder();
                    var6.append(var9);
                    var6.append("\r\nprivate AdListener _");
                    var6.append(var1);
                    var6.append("_ad_listener;");
                    var7 = var6.toString();
                    break;
                case "FirebaseStorage":
                    var6 = new StringBuilder();
                    var6.append(var9);
                    var6.append("\r\nprivate OnCompleteListener<Uri> _");
                    var6.append(var1);
                    var6.append("_upload_success_listener;");
                    var6.append("\r\n");
                    var6.append("private OnSuccessListener<FileDownloadTask.TaskSnapshot> _");
                    var6.append(var1);
                    var6.append("_download_success_listener;");
                    var6.append("\r\n");
                    var6.append("private OnSuccessListener _");
                    var6.append(var1);
                    var6.append("_delete_success_listener;");
                    var6.append("\r\n");
                    var6.append("private OnProgressListener _");
                    var6.append(var1);
                    var6.append("_upload_progress_listener;");
                    var6.append("\r\n");
                    var6.append("private OnProgressListener _");
                    var6.append(var1);
                    var6.append("_download_progress_listener;");
                    var6.append("\r\n");
                    var6.append("private OnFailureListener _");
                    var6.append(var1);
                    var6.append("_failure_listener;");
                    var7 = var6.toString();
                    break;
                case "RequestNetwork":
                    var6 = new StringBuilder();
                    var6.append(var9);
                    var6.append("\r\nprivate RequestNetwork.RequestListener _");
                    var6.append(var1);
                    var6.append("_request_listener;");
                    var7 = var6.toString();
                    break;
                case "BluetoothConnect":
                    var6 = new StringBuilder();
                    var6.append(var9);
                    var6.append("\r\nprivate BluetoothConnect.BluetoothConnectionListener _");
                    var6.append(var1);
                    var6.append("_bluetooth_connection_listener;");
                    var7 = var6.toString();
                    break;
                case "LocationManager":
                    var6 = new StringBuilder();
                    var6.append(var9);
                    var6.append("\r\nprivate LocationListener _");
                    var6.append(var1);
                    var6.append("_location_listener;");
                    var7 = var6.toString();
                    break;
                case "Camera":
                    var6 = new StringBuilder();
                    var6.append(var9);
                    var6.append("\r\nprivate File _file_");
                    var6.append(var1);
                    var6.append(";");
                    var7 = var6.toString();
                    break;
                case "MapView":
                    var6 = new StringBuilder();
                    var6.append(var9);
                    var6.append("\r\nprivate GoogleMapController _");
                    var6.append(var1);
                    var6.append("_controller;");
                    var7 = var6.toString();
                    break;
                case "RewardedVideoAd":
                    var6 = new StringBuilder();
                    var6.append(var9);
                    var6.append("\r\nprivate RewardedVideoAdListener  ");
                    var6.append(var1);
                    var6.append("_listener;");
                    var7 = var6.toString();
                    break;
                default:
                    var7 = var9;
                    if (var0.equals("TimePickerDialog")) {
                        var6 = new StringBuilder();
                        var6.append(var9);
                        var6.append("\r\nprivate TimePickerDialog.OnTimeSetListener ");
                        var6.append(var1);
                        var6.append("_listener;");
                        var7 = var6.toString();
                    }
                    break;
            }
        } else {
            var7 = "";
        }

        return var7;
    }

    public static String a(String var0, String var1, String var2) {
        var0 = "public " +
                ReturnMoreblockManager.getMbTypeCode(var0) +
                " _" +
                ReturnMoreblockManager.getMbName(var0) +
                " (";
        ArrayList<String> var10 = FB.c(var1);
        boolean var4 = true;

        StringBuilder var8;
        StringBuilder var9;
        for(int var5 = 0; var5 < var10.size(); ++var5) {
            String var6 = var10.get(var5);
            if (var6.charAt(0) == '%') {
                if (var6.charAt(1) == 'b') {
                    var1 = var0;
                    if (!var4) {
                        var9 = new StringBuilder();
                        var9.append(var0);
                        var9.append(", ");
                        var1 = var9.toString();
                    }

                    var8 = new StringBuilder();
                    var8.append(var1);
                    var8.append("final boolean _");
                    var8.append(var6.substring(3));
                    var0 = var8.toString();
                } else if (var6.charAt(1) == 'd') {
                    var1 = var0;
                    if (!var4) {
                        var9 = new StringBuilder();
                        var9.append(var0);
                        var9.append(", ");
                        var1 = var9.toString();
                    }

                    var8 = new StringBuilder();
                    var8.append(var1);
                    var8.append("final double _");
                    var8.append(var6.substring(3));
                    var0 = var8.toString();
                } else if (var6.charAt(1) == 's') {
                    var1 = var0;
                    if (!var4) {
                        var9 = new StringBuilder();
                        var9.append(var0);
                        var9.append(", ");
                        var1 = var9.toString();
                    }

                    var8 = new StringBuilder();
                    var8.append(var1);
                    var8.append("final String _");
                    var8.append(var6.substring(3));
                    var0 = var8.toString();
                } else {
                    if (var6.charAt(1) != 'm') {
                        continue;
                    }

                    var1 = var0;
                    if (!var4) {
                        var9 = new StringBuilder();
                        var9.append(var0);
                        var9.append(", ");
                        var1 = var9.toString();
                    }

                    var0 = var6.substring(3, var6.lastIndexOf("."));
                    String var7 = var6.substring(var6.lastIndexOf(".") + 1);
                    var0 = var1 +
                            "final " +
                            mq.e(mq.b(var0)) +
                            " _" +
                            var7;
                }

                var4 = false;
            }
        }

        var9 = new StringBuilder();
        var9.append(var0);
        var9.append(") {\r\n");
        var1 = var9.toString();
        var8 = new StringBuilder();
        var8.append(var1);
        var8.append(var2);
        var8.append("\r\n");
        var1 = var8.toString();
        var8 = new StringBuilder();
        var8.append(var1);
        var8.append("}\r\n");
        return var8.toString();
    }

    public static String a(String var0, String var1, ArrayList<ViewBean> var2, String var3) {
        String var4 = a(var0);
        Iterator<ViewBean> var7 = var2.iterator();

        StringBuilder var6;
        for(var0 = ""; var7.hasNext(); var0 = var6.toString()) {
            ViewBean var5 = var7.next();
            var6 = new StringBuilder();
            var6.append(var0);
            var6.append(a(var5));
            var6.append("\r\n");
        }

        return "public class " +
                var4 +
                " extends BaseAdapter {" +
                "\r\n" +
                "ArrayList<HashMap<String, Object>> _data;" +
                "\r\n" +
                "public " +
                var4 +
                "(ArrayList<HashMap<String, Object>> _arr) {" +
                "\r\n" +
                "_data = _arr;" +
                "\r\n" +
                "}" +
                "\r\n" +
                "\r\n" +
                "@Override" +
                "\r\n" +
                "public int getCount() {" +
                "\r\n" +
                "return _data.size();" +
                "\r\n" +
                "}" +
                "\r\n" +
                "\r\n" +
                "@Override" +
                "\r\n" +
                "public HashMap<String, Object> getItem(int _index) {" +
                "\r\n" +
                "return _data.get(_index);" +
                "\r\n" +
                "}" +
                "\r\n" +
                "\r\n" +
                "@Override" +
                "\r\n" +
                "public long getItemId(int _index) {" +
                "\r\n" +
                "return _index;" +
                "\r\n" +
                "}" +
                "\r\n" +
                "@Override" +
                "\r\n" +
                "public View getView(final int _position, View _v, ViewGroup _container) {" +
                "\r\n" +
                "LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);" +
                "\r\n" +
                "View _view = _v;" +
                "\r\n" +
                "if (_view == null) {" +
                "\r\n" +
                "_view = _inflater.inflate(R.layout." +
                var1 +
                ", null);" +
                "\r\n" +
                "}" +
                "\r\n" +
                "\r\n" +
                var0 +
                "\r\n" +
                var3 +
                "\r\n" +
                "\r\n" +
                "return _view;" +
                "\r\n" +
                "}" +
                "\r\n" +
                "}";
    }

    public static String a(String var0, String var1, String... var2) {
        byte var3;
        label84: {
            var1 = TypeVarComponent.b(var0);
            switch(var0.hashCode()) {
                case -2099895620:
                    if (var0.equals("Intent")) {
                        var3 = 7;
                        break label84;
                    }
                    break;
                case -1936496017:
                    if (var0.equals("ListString")) {
                        var3 = 5;
                        break label84;
                    }
                    break;
                case -1908172204:
                    if (var0.equals("FirebaseStorage")) {
                        var3 = 11;
                        break label84;
                    }
                    break;
                case -1808118735:
                    if (var0.equals("String")) {
                        var3 = 2;
                        break label84;
                    }
                    break;
                case -1325958191:
                    if (var0.equals("double")) {
                        var3 = 1;
                        break label84;
                    }
                    break;
                case -596330166:
                    if (var0.equals("FilePicker")) {
                        var3 = 13;
                        break label84;
                    }
                    break;
                case -113680546:
                    if (var0.equals("Calendar")) {
                        var3 = 8;
                        break label84;
                    }
                    break;
                case 77116:
                    if (var0.equals("Map")) {
                        var3 = 3;
                        break label84;
                    }
                    break;
                case 64711720:
                    if (var0.equals("boolean")) {
                        var3 = 0;
                        break label84;
                    }
                    break;
                case 1779003621:
                    if (var0.equals("FirebaseDB")) {
                        var3 = 9;
                        break label84;
                    }
                    break;
                case 1799376742:
                    if (var0.equals("ObjectAnimator")) {
                        var3 = 10;
                        break label84;
                    }
                    break;
                case 1846598225:
                    if (var0.equals("ListInt")) {
                        var3 = 4;
                        break label84;
                    }
                    break;
                case 1846601662:
                    if (var0.equals("ListMap")) {
                        var3 = 6;
                        break label84;
                    }
                    break;
                case 2011082565:
                    if (var0.equals("Camera")) {
                        var3 = 12;
                        break label84;
                    }
            }

            var3 = -1;
        }

        StringBuilder var4;
        switch(var3) {
            case 0:
                var0 = "false";
                break;
            case 1:
                var0 = "0";
                break;
            case 2:
                var0 = "\"\"";
                break;
            case 3:
                var0 = "new HashMap<>()";
                break;
            case 4:
            case 5:
            case 6:
                var0 = "new ArrayList<>()";
                break;
            case 7:
                var0 = "new Intent()";
                break;
            case 8:
                var0 = "Calendar.getInstance()";
                break;
            case 9:
                if (var2[0] != null && !var2[0].isEmpty()) {
                    var0 = var2[0].replace(";", "");
                } else {
                    var0 = "";
                }

                var4 = new StringBuilder();
                var4.append("_firebase.getReference(\"");
                var4.append(var0);
                var4.append("\")");
                var0 = var4.toString();
                break;
            case 10:
                var0 = "new ObjectAnimator()";
                break;
            case 11:
                var0 = "";
                if (var2[0] != null) {
                    var0 = "";
                    if (!var2[0].isEmpty()) {
                        var0 = var2[0].replace(";", "");
                    }
                }

                var4 = new StringBuilder();
                var4.append("_firebase_storage.getReference(\"");
                var4.append(var0);
                var4.append("\")");
                var0 = var4.toString();
                break;
            case 12:
                var0 = "new Intent(MediaStore.ACTION_IMAGE_CAPTURE)";
                break;
            case 13:
                var0 = "new Intent(Intent.ACTION_GET_CONTENT)";
                break;
            default:
                var0 = var1;
        }

        return var0;
    }

    public static String a(boolean var0, int var1) {
        byte var2 = 0;
        byte var3 = 0;
        ArrayList<String> var4 = new ArrayList<>();
        ArrayList<String> var5 = new ArrayList<>();
        String var6;
        String var7;
        String var8;
        String var9;
        StringBuilder var10;
        StringBuilder var11;
        if (var0) {
            if ((var1 & 1) == 1) {
                var4.add("ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED");
                var5.add("Manifest.permission.CALL_PHONE");
            }

            if ((var1 & 16) == 16) {
                var4.add("ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED");
                var5.add("Manifest.permission.CAMERA");
            }

            if ((var1 & 32) == 32) {
                var4.add("ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED");
                var5.add("Manifest.permission.READ_EXTERNAL_STORAGE");
            }

            if ((var1 & 64) == 64) {
                var4.add("ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED");
                var5.add("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            }

            if ((var1 & 128) == 128) {
                var4.add("ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED");
                var5.add("Manifest.permission.RECORD_AUDIO");
            }

            if ((var1 & 1024) == 1024) {
                var4.add("ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED");
                var5.add("Manifest.permission.ACCESS_FINE_LOCATION");
            }

            var6 = "if (";

            for(var1 = 0; var1 < var4.size(); ++var1) {
                var8 = var4.get(var1);
                var7 = var6;
                if (var1 != 0) {
                    var11 = new StringBuilder();
                    var11.append(var6);
                    var11.append("\r\n|| ");
                    var7 = var11.toString();
                }

                var10 = new StringBuilder();
                var10.append(var7);
                var10.append(var8);
                var6 = var10.toString();
            }

            var11 = new StringBuilder();
            var11.append(var6);
            var11.append(") {\r\n");
            var9 = var11.toString();
            var6 = "ActivityCompat.requestPermissions(this, new String[] {";

            for(var1 = var3; var1 < var5.size(); ++var1) {
                var8 = var5.get(var1);
                var7 = var6;
                if (var1 != 0) {
                    var11 = new StringBuilder();
                    var11.append(var6);
                    var11.append(", ");
                    var7 = var11.toString();
                }

                var10 = new StringBuilder();
                var10.append(var7);
                var10.append(var8);
                var6 = var10.toString();
            }

            var11 = new StringBuilder();
            var11.append(var6);
            var11.append("}, 1000);");
            var6 = var11.toString();
            var11 = new StringBuilder();
            var11.append(var9);
            var11.append(var6);
            var11.append("\r\n");
            var7 = var11.toString();
            var10 = new StringBuilder();
            var10.append(var7);
            var10.append("}\r\n");
            var7 = var10.toString();
            var10 = new StringBuilder();
            var10.append(var7);
            var10.append("else {\r\n");
            var7 = var10.toString();
            var10 = new StringBuilder();
            var10.append(var7);
            var10.append("initializeLogic();\r\n");
            var7 = var10.toString();
            var10 = new StringBuilder();
            var10.append(var7);
            var10.append("}\r\n");
            var6 = var10.toString();
        } else {
            if ((var1 & 1) == 1) {
                var4.add("checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED");
                var5.add("Manifest.permission.CALL_PHONE");
            }

            if ((var1 & 16) == 16) {
                var4.add("checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED");
                var5.add("Manifest.permission.CAMERA");
            }

            if ((var1 & 32) == 32) {
                var4.add("checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED");
                var5.add("Manifest.permission.READ_EXTERNAL_STORAGE");
            }

            if ((var1 & 64) == 64) {
                var4.add("checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED");
                var5.add("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            }

            if ((var1 & 128) == 128) {
                var4.add("checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED");
                var5.add("Manifest.permission.RECORD_AUDIO");
            }

            if ((var1 & 1024) == 1024) {
                var4.add("checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED");
                var5.add("Manifest.permission.ACCESS_FINE_LOCATION");
            }

            var10 = new StringBuilder();
            var10.append("if (Build.VERSION.SDK_INT >= 23) {\r\n");
            var10.append("if (");
            var6 = var10.toString();

            for(var1 = 0; var1 < var4.size(); ++var1) {
                var8 = var4.get(var1);
                var7 = var6;
                if (var1 != 0) {
                    var11 = new StringBuilder();
                    var11.append(var6);
                    var11.append("\r\n|| ");
                    var7 = var11.toString();
                }

                var10 = new StringBuilder();
                var10.append(var7);
                var10.append(var8);
                var6 = var10.toString();
            }

            var11 = new StringBuilder();
            var11.append(var6);
            var11.append(") {\r\n");
            var9 = var11.toString();
            var6 = "requestPermissions(new String[] {";

            for(var1 = var2; var1 < var5.size(); ++var1) {
                var8 = var5.get(var1);
                var7 = var6;
                if (var1 != 0) {
                    var11 = new StringBuilder();
                    var11.append(var6);
                    var11.append(", ");
                    var7 = var11.toString();
                }

                var10 = new StringBuilder();
                var10.append(var7);
                var10.append(var8);
                var6 = var10.toString();
            }

            var11 = new StringBuilder();
            var11.append(var6);
            var11.append("}, 1000);");
            var6 = var11.toString();
            var11 = new StringBuilder();
            var11.append(var9);
            var11.append(var6);
            var11.append("\r\n");
            var6 = var11.toString();
            var11 = new StringBuilder();
            var11.append(var6);
            var11.append("}\r\n");
            var6 = var11.toString();
            var11 = new StringBuilder();
            var11.append(var6);
            var11.append("else {\r\n");
            var7 = var11.toString();
            var10 = new StringBuilder();
            var10.append(var7);
            var10.append("initializeLogic();\r\n");
            var7 = var10.toString();
            var10 = new StringBuilder();
            var10.append(var7);
            var10.append("}\r\n");
            var7 = var10.toString();
            var10 = new StringBuilder();
            var10.append(var7);
            var10.append("}\r\n");
            var6 = var10.toString();
            var11 = new StringBuilder();
            var11.append(var6);
            var11.append("else {\r\n");
            var6 = var11.toString();
            var11 = new StringBuilder();
            var11.append(var6);
            var11.append("initializeLogic();\r\n");
            var6 = var11.toString();
            var11 = new StringBuilder();
            var11.append(var6);
            var11.append("}\r\n");
            var6 = var11.toString();
        }

        return var6;
    }

    public static void a(StringBuilder var0, int var1) {
        for(int var2 = 0; var2 < var1; ++var2) {
            var0.append('\t');
        }

    }

    public static String b(String var0) {
        return "package " +
                var0 +
                ";" +
                "\r\n" +
                "\nimport android.app.Activity;\nimport android.bluetooth.BluetoothAdapter;\nimport android.bluetooth.BluetoothDevice;\nimport android.content.Intent;\n\nimport java.util.ArrayList;\nimport java.util.HashMap;\nimport java.util.Set;\nimport java.util.UUID;\n\npublic class BluetoothConnect {\nprivate static final String DEFAULT_UUID = \"00001101-0000-1000-8000-00805F9B34FB\";\n\nprivate Activity activity;\n\nprivate BluetoothAdapter bluetoothAdapter;\n\npublic BluetoothConnect(Activity activity) {\nthis.activity = activity;\nthis.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();\n}\n\npublic boolean isBluetoothEnabled() {\nif(bluetoothAdapter != null) return true;\n\nreturn false;\n}\n\npublic boolean isBluetoothActivated() {\nif(bluetoothAdapter == null) return false;\n\nreturn bluetoothAdapter.isEnabled();\n}\n\npublic void activateBluetooth() {\nIntent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);\nactivity.startActivity(intent);\n}\n\npublic String getRandomUUID() {\nreturn String.valueOf(UUID.randomUUID());\n}\n\npublic void getPairedDevices(ArrayList<HashMap<String, Object>> results) {\nSet<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();\n\nif(pairedDevices.size() > 0) {\nfor(BluetoothDevice device : pairedDevices) {\nHashMap<String, Object> result = new HashMap<>();\nresult.put(\"name\", device.getName());\nresult.put(\"address\", device.getAddress());\n\nresults.add(result);\n}\n}\n}\n\npublic void readyConnection(BluetoothConnectionListener listener, String tag) {\nif(BluetoothController.getInstance().getState().equals(BluetoothController.STATE_NONE)) {\nBluetoothController.getInstance().start(this, listener, tag, UUID.fromString(DEFAULT_UUID), bluetoothAdapter);\n}\n}\n\npublic void readyConnection(BluetoothConnectionListener listener, String uuid, String tag) {\nif(BluetoothController.getInstance().getState().equals(BluetoothController.STATE_NONE)) {\nBluetoothController.getInstance().start(this, listener, tag, UUID.fromString(uuid), bluetoothAdapter);\n}\n}\n\n\npublic void startConnection(BluetoothConnectionListener listener, String address, String tag) {\nBluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);\n\nBluetoothController.getInstance().connect(device, this, listener, tag, UUID.fromString(DEFAULT_UUID), bluetoothAdapter);\n}\n\npublic void startConnection(BluetoothConnectionListener listener, String uuid, String address, String tag) {\nBluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);\n\nBluetoothController.getInstance().connect(device, this, listener, tag, UUID.fromString(uuid), bluetoothAdapter);\n}\n\npublic void stopConnection(BluetoothConnectionListener listener, String tag) {\nBluetoothController.getInstance().stop(this, listener, tag);\n}\n\npublic void sendData(BluetoothConnectionListener listener, String data, String tag) {\nString state = BluetoothController.getInstance().getState();\n\nif(!state.equals(BluetoothController.STATE_CONNECTED)) {\nlistener.onConnectionError(tag, state, \"Bluetooth is not connected yet\");\nreturn;\n}\n\nBluetoothController.getInstance().write(data.getBytes());\n}\n\npublic Activity getActivity() {\nreturn activity;\n}\n\npublic interface BluetoothConnectionListener {\nvoid onConnected(String tag, HashMap<String, Object> deviceData);\nvoid onDataReceived(String tag, byte[] data, int bytes);\nvoid onDataSent(String tag, byte[] data);\nvoid onConnectionError(String tag, String connectionState, String message);\nvoid onConnectionStopped(String tag);\n}\n}";
    }

    public static String b(String var0, String var1, String var2) {
        byte var3;
        label68: {
            switch(var0.hashCode()) {
                case -1401315045:
                    if (var0.equals("onDestroy")) {
                        var3 = 5;
                        break label68;
                    }
                    break;
                case -1340212393:
                    if (var0.equals("onPause")) {
                        var3 = 3;
                        break label68;
                    }
                    break;
                case -1336895037:
                    if (var0.equals("onStart")) {
                        var3 = 1;
                        break label68;
                    }
                    break;
                case -1111243300:
                    if (var0.equals("onBackPressed")) {
                        var3 = 0;
                        break label68;
                    }
                    break;
                case -1012956543:
                    if (var0.equals("onStop")) {
                        var3 = 4;
                        break label68;
                    }
                    break;
                case 1463983852:
                    if (var0.equals("onResume")) {
                        var3 = 2;
                        break label68;
                    }
            }

            var3 = -1;
        }

        StringBuilder var4;
        if (var3 != 0) {
            if (var3 != 1) {
                if (var3 != 2) {
                    if (var3 != 3) {
                        if (var3 != 4) {
                            if (var3 == 5 && var1.equals("MapView")) {
                                var4 = new StringBuilder();
                                var4.append(var2);
                                var4.append(".onDestroy();");
                                var0 = var4.toString();
                            } else {
                                var0 = "";
                            }
                        } else if (!var1.equals("MapView")) {
                            var0 = "";
                        } else {
                            var4 = new StringBuilder();
                            var4.append(var2);
                            var4.append(".onStop();");
                            var0 = var4.toString();
                        }
                    } else if (!var1.equals("MapView")) {
                        var0 = "";
                    } else {
                        var4 = new StringBuilder();
                        var4.append(var2);
                        var4.append(".onPause();");
                        var0 = var4.toString();
                    }
                } else if (!var1.equals("MapView")) {
                    var0 = "";
                } else {
                    var4 = new StringBuilder();
                    var4.append(var2);
                    var4.append(".onResume();");
                    var0 = var4.toString();
                }
            } else if (!var1.equals("MapView")) {
                var0 = "";
            } else {
                var4 = new StringBuilder();
                var4.append(var2);
                var4.append(".onStart();");
                var0 = var4.toString();
            }
        } else if (!var1.equals("DrawerLayout")) {
            var0 = "";
        } else {
            var4 = new StringBuilder();
            var4.append("if (");
            var4.append(var2);
            var4.append(".isDrawerOpen(GravityCompat.START)) {");
            var4.append("\r\n");
            var4.append(var2);
            var4.append(".closeDrawer(GravityCompat.START);");
            var4.append("\r\n");
            var4.append("}");
            var4.append("\r\n");
            var4.append("else {");
            var4.append("\r\n");
            var4.append("super.onBackPressed();");
            var4.append("\r\n");
            var4.append("}");
            var0 = var4.toString();
        }

        return var0;
    }

    public static String b(String var0, String var1, boolean var2) {
        StringBuilder var3 = new StringBuilder();
        if (!var0.equals("include") && !var0.equals("#")) {
            var3.append(var1);
            var3.append(" = (");
            var3.append(mq.e(var0));
            if (var2) {
                var3.append(") _view.findViewById(R.id.");
            } else {
                var3.append(") findViewById(R.id.");
            }

            var3.append(var1);
            var3.append(");");
        }

        String var4 = var3.toString();
        String var6 = var4;
        StringBuilder var7;
        if (var0.equals("WebView")) {
            var3 = new StringBuilder();
            var3.append(var4);
            var3.append("\r\n");
            var6 = var3.toString();
            var7 = new StringBuilder();
            var7.append(var6);
            var7.append(var1);
            var7.append(".getSettings().setJavaScriptEnabled(true);");
            var7.append("\r\n");
            var7.append(var1);
            var7.append(".getSettings().setSupportZoom(true);");
            var6 = var7.toString();
        }

        var4 = var6;
        if (var0.equals("MapView")) {
            var7 = new StringBuilder();
            var7.append(var6);
            var7.append("\r\n");
            var6 = var7.toString();
            var7 = new StringBuilder();
            var7.append(var6);
            var7.append(var1);
            var7.append(".onCreate(_savedInstanceState);");
            var7.append("\r\n");
            var4 = var7.toString();
        }

        var6 = var4;
        if (var0.equals("VideoView")) {
            StringBuilder var5 = new StringBuilder();
            var5.append(var4);
            var5.append("\r\n");
            var6 = var5.toString();
            var5 = new StringBuilder();
            var5.append(var6);
            var5.append("MediaController ");
            var5.append(var1);
            var5.append("_controller = new MediaController(this);");
            var5.append("\r\n");
            var5.append(var1);
            var5.append(".setMediaController(");
            var5.append(var1);
            var5.append("_controller);");
            var6 = var5.toString();
        }

        return var6;
    }

    public static String b(String var0, String var1, String... var2) {
        byte var3;
        label87: {
            switch(var0.hashCode()) {
                case -1965257499:
                    if (var0.equals("Gyroscope")) {
                        var3 = 3;
                        break label87;
                    }
                    break;
                case -1884914774:
                    if (var0.equals("TextToSpeech")) {
                        var3 = 8;
                        break label87;
                    }
                    break;
                case -1042830870:
                    if (var0.equals("SpeechToText")) {
                        var3 = 9;
                        break label87;
                    }
                    break;
                case -1014653761:
                    if (var0.equals("RequestNetwork")) {
                        var3 = 7;
                        break label87;
                    }
                    break;
                case -596330166:
                    if (var0.equals("FilePicker")) {
                        var3 = 5;
                        break label87;
                    }
                    break;
                case -294086120:
                    if (var0.equals("LocationManager")) {
                        var3 = 11;
                        break label87;
                    }
                    break;
                case -105717264:
                    if (var0.equals("RewardedVideoAd")) {
                        var3 = 13;
                        break label87;
                    }
                    break;
                case 225459311:
                    if (var0.equals("FirebaseAuth")) {
                        var3 = 4;
                        break label87;
                    }
                    break;
                case 313126659:
                    if (var0.equals("TimePickerDialog")) {
                        var3 = 12;
                        break label87;
                    }
                    break;
                case 1170382393:
                    if (var0.equals("Vibrator")) {
                        var3 = 1;
                        break label87;
                    }
                    break;
                case 1512362620:
                    if (var0.equals("BluetoothConnect")) {
                        var3 = 10;
                        break label87;
                    }
                    break;
                case 1616304435:
                    if (var0.equals("SharedPreferences")) {
                        var3 = 0;
                        break label87;
                    }
                    break;
                case 2011082565:
                    if (var0.equals("Camera")) {
                        var3 = 6;
                        break label87;
                    }
                    break;
                case 2046749032:
                    if (var0.equals("Dialog")) {
                        var3 = 2;
                        break label87;
                    }
            }

            var3 = -1;
        }

        var0 = ManageEventComponent.b(var0, var1);
        StringBuilder var4;
        StringBuilder var5;
        switch(var3) {
            case 0:
                if (var2[0] != null && !var2[0].isEmpty()) {
                    var0 = var2[0].replace(";", var0);
                }

                var5 = new StringBuilder();
                var5.append(var1);
                var5.append(" = getSharedPreferences(\"");
                var5.append(var0);
                var5.append("\", Activity.MODE_PRIVATE);");
                var0 = var5.toString();
                break;
            case 1:
                var4 = new StringBuilder();
                var4.append(var1);
                var4.append(" = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);");
                var0 = var4.toString();
                break;
            case 2:
                var4 = new StringBuilder();
                var4.append(var1);
                var4.append(" = new AlertDialog.Builder(this);");
                var0 = var4.toString();
                break;
            case 3:
                var4 = new StringBuilder();
                var4.append(var1);
                var4.append(" = (SensorManager) getSystemService(Context.SENSOR_SERVICE);");
                var4.append("\r\n");
                var4.append("if (");
                var4.append(var1);
                var4.append(".getDefaultSensor(Sensor.TYPE_GYROSCOPE) == null) {");
                var4.append("\r\n");
                var4.append("SketchwareUtil.showMessage(getApplicationContext(), \"Gyroscope is not supported on this device\");");
                var4.append("\r\n");
                var4.append("}");
                var0 = var4.toString();
                break;
            case 4:
                var4 = new StringBuilder();
                var4.append(var1);
                var4.append(" = FirebaseAuth.getInstance();");
                var0 = var4.toString();
                break;
            case 5:
                if (var2[0] != null && var2[0].length() > 0) {
                    var0 = var2[0].replace(";", var0);
                } else {
                    var0 = "*/*";
                }

                var5 = new StringBuilder();
                var5.append(var1);
                var5.append(".setType(\"");
                var5.append(var0);
                var5.append("\");");
                var5.append("\r\n");
                var5.append(var1);
                var5.append(".putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);");
                var0 = var5.toString();
                break;
            case 6:
                var4 = new StringBuilder();
                var4.append("_file_");
                var4.append(var1);
                var4.append(" = FileUtil.createNewPictureFile(getApplicationContext());");
                var4.append("\r\n");
                var4.append("Uri _uri_");
                var4.append(var1);
                var4.append(" = null;");
                var4.append("\r\n");
                var4.append("if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {");
                var4.append("\r\n");
                var4.append("_uri_");
                var4.append(var1);
                var4.append("= FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + \".provider\", _file_");
                var4.append(var1);
                var4.append(");");
                var4.append("\r\n");
                var4.append("}");
                var4.append("\r\n");
                var4.append("else {");
                var4.append("\r\n");
                var4.append("_uri_");
                var4.append(var1);
                var4.append(" = Uri.fromFile(_file_");
                var4.append(var1);
                var4.append(");");
                var4.append("\r\n");
                var4.append("}");
                var4.append("\r\n");
                var4.append(var1);
                var4.append(".putExtra(MediaStore.EXTRA_OUTPUT, _uri_");
                var4.append(var1);
                var4.append(");");
                var4.append("\r\n");
                var4.append(var1);
                var4.append(".addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);");
                var0 = var4.toString();
                break;
            case 7:
                var4 = new StringBuilder();
                var4.append(var1);
                var4.append(" = new RequestNetwork(this);");
                var0 = var4.toString();
                break;
            case 8:
                var4 = new StringBuilder();
                var4.append(var1);
                var4.append(" = new TextToSpeech(getApplicationContext(), null);");
                var0 = var4.toString();
                break;
            case 9:
                var4 = new StringBuilder();
                var4.append(var1);
                var4.append(" = SpeechRecognizer.createSpeechRecognizer(this);");
                var0 = var4.toString();
                break;
            case 10:
                var4 = new StringBuilder();
                var4.append(var1);
                var4.append(" = new BluetoothConnect(this);");
                var0 = var4.toString();
                break;
            case 11:
                var4 = new StringBuilder();
                var4.append(var1);
                var4.append(" = (LocationManager) getSystemService(Context.LOCATION_SERVICE);");
                var0 = var4.toString();
                break;
            case 12:
                var4 = new StringBuilder();
                var4.append(var1);
                var4.append(" = new TimePickerDialog(this, ");
                var4.append(var1);
                var4.append("_listener, Calendar.getInstance().HOUR_OF_DAY, Calendar.getInstance().MINUTE, false);");
                var0 = var4.toString();
                break;
            case 13:
                var4 = new StringBuilder();
                var4.append(var1);
                var4.append(" = MobileAds.getRewardedVideoAdInstance(this);");
                var0 = var4.toString();
        }

        return var0;
    }

    public static String c(String var0) {
        return "package " +
                var0 +
                ";" +
                "\r\n" +
                "\nimport android.bluetooth.BluetoothAdapter;\nimport android.bluetooth.BluetoothDevice;\nimport android.bluetooth.BluetoothServerSocket;\nimport android.bluetooth.BluetoothSocket;\n\nimport java.io.InputStream;\nimport java.io.OutputStream;\nimport java.util.HashMap;\nimport java.util.UUID;\n\npublic class BluetoothController {\npublic static final String STATE_NONE       = \"none\";\npublic static final String STATE_LISTEN     = \"listen\";\npublic static final String STATE_CONNECTING = \"connecting\";\npublic static final String STATE_CONNECTED  = \"connected\";\n\nprivate AcceptThread acceptThread;\nprivate ConnectThread connectThread;\nprivate ConnectedThread connectedThread;\n\nprivate String state = STATE_NONE;\n\nprivate static BluetoothController instance;\n\npublic static synchronized BluetoothController getInstance() {\nif(instance == null) {\ninstance = new BluetoothController();\n}\n\nreturn instance;\n}\n\npublic synchronized void start(BluetoothConnect bluetoothConnect, BluetoothConnect.BluetoothConnectionListener listener, String tag, UUID uuid, BluetoothAdapter bluetoothAdapter) {\nif (connectThread != null) {\nconnectThread.cancel();\nconnectThread = null;\n}\n\nif (connectedThread != null) {\nconnectedThread.cancel();\nconnectedThread = null;\n}\n\nif (acceptThread != null) {\nacceptThread.cancel();\nacceptThread = null;\n}\n\nacceptThread = new AcceptThread(bluetoothConnect, listener, tag, uuid, bluetoothAdapter);\nacceptThread.start();}\n\npublic synchronized void connect(BluetoothDevice device, BluetoothConnect bluetoothConnect, BluetoothConnect.BluetoothConnectionListener listener, String tag, UUID uuid, BluetoothAdapter bluetoothAdapter) {\nif (state.equals(STATE_CONNECTING)) {\nif (connectThread != null) {\nconnectThread.cancel();\nconnectThread = null;\n}\n}\n\nif (connectedThread != null) {\nconnectedThread.cancel();\nconnectedThread = null;\n}\n\nconnectThread = new ConnectThread(device, bluetoothConnect, listener, tag, uuid, bluetoothAdapter);\nconnectThread.start();\n}\n\npublic synchronized void connected(BluetoothSocket socket, final BluetoothDevice device, BluetoothConnect bluetoothConnect, final BluetoothConnect.BluetoothConnectionListener listener, final String tag) {\nif (connectThread != null) {\nconnectThread.cancel();\nconnectThread = null;\n}\n\nif (connectedThread != null) {\nconnectedThread.cancel();\nconnectedThread = null;\n}\n\nif (acceptThread != null) {\nacceptThread.cancel();\nacceptThread = null;\n}\n\nconnectedThread = new ConnectedThread(socket, bluetoothConnect, listener, tag);\nconnectedThread.start();\n\nbluetoothConnect.getActivity().runOnUiThread(new Runnable() {\n@Override\npublic void run() {\nHashMap<String, Object> deviceMap = new HashMap<>();\ndeviceMap.put(\"name\", device.getName());\ndeviceMap.put(\"address\", device.getAddress());\n\nlistener.onConnected(tag, deviceMap);\n}\n});\n}\n\npublic synchronized void stop(BluetoothConnect bluetoothConnect, final BluetoothConnect.BluetoothConnectionListener listener, final String tag) {\nif (connectThread != null) {\nconnectThread.cancel();\nconnectThread = null;\n}\n\nif (connectedThread != null) {\nconnectedThread.cancel();\nconnectedThread = null;\n}\n\nif (acceptThread != null) {\nacceptThread.cancel();\nacceptThread = null;\n}\n\nstate = STATE_NONE;\n\nbluetoothConnect.getActivity().runOnUiThread(new Runnable() {\n@Override\npublic void run() {\nlistener.onConnectionStopped(tag);\n}\n});\n}\n\npublic void write(byte[] out) {\nConnectedThread r;\n\nsynchronized (this) {\nif (!state.equals(STATE_CONNECTED)) return;\nr = connectedThread;\n}\n\nr.write(out);\n}\n\npublic void connectionFailed(BluetoothConnect bluetoothConnect, final BluetoothConnect.BluetoothConnectionListener listener, final String tag, final String message) {\nstate = STATE_NONE;\n\nbluetoothConnect.getActivity().runOnUiThread(new Runnable() {\n@Override\npublic void run() {\nlistener.onConnectionError(tag, state, message);\n}\n});\n}\n\npublic void connectionLost(BluetoothConnect bluetoothConnect, final BluetoothConnect.BluetoothConnectionListener listener, final String tag) {\nstate = STATE_NONE;\n\nbluetoothConnect.getActivity().runOnUiThread(new Runnable() {\n@Override\npublic void run() {\nlistener.onConnectionError(tag, state, \"Bluetooth connection is disconnected\");\n}\n});\n}\n\npublic String getState() {\nreturn state;\n}\n\nprivate class AcceptThread extends Thread {\nprivate BluetoothServerSocket serverSocket;\n\nprivate BluetoothConnect bluetoothConnect;\nprivate BluetoothConnect.BluetoothConnectionListener listener;\nprivate String tag;\n\npublic AcceptThread(BluetoothConnect bluetoothConnect, BluetoothConnect.BluetoothConnectionListener listener, String tag, UUID uuid, BluetoothAdapter bluetoothAdapter) {\nthis.bluetoothConnect = bluetoothConnect;\nthis.listener = listener;\nthis.tag = tag;\n\ntry {\nserverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(tag, uuid);\n} catch (Exception e) {\ne.printStackTrace();\n}\n\nstate = STATE_LISTEN;\n}\n\n@Override\npublic void run() {\nBluetoothSocket bluetoothSocket;\n\nwhile (!state.equals(STATE_CONNECTED)) {\ntry {\nbluetoothSocket = serverSocket.accept();\n} catch (Exception e) {\ne.printStackTrace();\nbreak;\n}\n\nif (bluetoothSocket != null) {\nsynchronized (BluetoothController.this) {\nswitch (state) {\ncase STATE_LISTEN:\ncase STATE_CONNECTING:\nconnected(bluetoothSocket, bluetoothSocket.getRemoteDevice(), bluetoothConnect, listener, tag);\nbreak;\ncase STATE_NONE:\ncase STATE_CONNECTED:\ntry {\nbluetoothSocket.close();\n} catch (Exception e) {\ne.printStackTrace();\n}\nbreak;\n}\n}\n}\n}\n}\n\npublic void cancel() {\ntry {\nserverSocket.close();\n} catch (Exception e) {\ne.printStackTrace();\n}\n}\n}\n\nprivate class ConnectThread extends Thread {\nprivate BluetoothDevice device;\nprivate BluetoothSocket socket;\n\nprivate BluetoothConnect bluetoothConnect;\nprivate BluetoothConnect.BluetoothConnectionListener listener;\nprivate String tag;\nprivate BluetoothAdapter bluetoothAdapter;\n\npublic ConnectThread(BluetoothDevice device, BluetoothConnect bluetoothConnect, BluetoothConnect.BluetoothConnectionListener listener, String tag, UUID uuid, BluetoothAdapter bluetoothAdapter) {\nthis.device = device;\nthis.bluetoothConnect = bluetoothConnect;\nthis.listener = listener;\nthis.tag = tag;\nthis.bluetoothAdapter = bluetoothAdapter;\n\ntry {\nsocket = device.createRfcommSocketToServiceRecord(uuid);\n} catch (Exception e) {\ne.printStackTrace();\n}\n\nstate = STATE_CONNECTING;\n}\n\n@Override\npublic void run() {\nbluetoothAdapter.cancelDiscovery();\n\ntry {\nsocket.connect();\n} catch (Exception e) {\ntry {\nsocket.close();\n} catch (Exception e2) {\ne2.printStackTrace();\n}\nconnectionFailed(bluetoothConnect, listener, tag, e.getMessage());\nreturn;\n}\n\nsynchronized (BluetoothController.this) {\nconnectThread = null;\n}\n\nconnected(socket, device, bluetoothConnect, listener, tag);\n}\n\npublic void cancel() {\ntry {\nsocket.close();\n} catch (Exception e) {\ne.printStackTrace();\n}\n}\n}\n\nprivate class ConnectedThread extends Thread {\nprivate BluetoothSocket socket;\nprivate InputStream inputStream;\nprivate OutputStream outputStream;\n\nprivate BluetoothConnect bluetoothConnect;\nprivate BluetoothConnect.BluetoothConnectionListener listener;\nprivate String tag;\n\npublic ConnectedThread(BluetoothSocket socket, BluetoothConnect bluetoothConnect, BluetoothConnect.BluetoothConnectionListener listener, String tag) {\nthis.bluetoothConnect = bluetoothConnect;\nthis.listener = listener;\nthis.tag = tag;\n\nthis.socket = socket;\n\ntry {\ninputStream = socket.getInputStream();\noutputStream = socket.getOutputStream();\n} catch (Exception e) {\ne.printStackTrace();\n}\n\nstate = STATE_CONNECTED;\n}\n\npublic void run() {\nwhile (state.equals(STATE_CONNECTED)) {\ntry {\nfinal byte[] buffer = new byte[1024];\nfinal int bytes = inputStream.read(buffer);\n\nbluetoothConnect.getActivity().runOnUiThread(new Runnable() {\n@Override\npublic void run() {\nlistener.onDataReceived(tag, buffer, bytes);\n}\n});\n} catch (Exception e) {\ne.printStackTrace();\nconnectionLost(bluetoothConnect, listener, tag);\nbreak;\n}\n}\n}\n\npublic void write(final byte[] buffer) {\ntry {\noutputStream.write(buffer);\n\nbluetoothConnect.getActivity().runOnUiThread(new Runnable() {\n@Override\npublic void run() {\nlistener.onDataSent(tag, buffer);\n}\n});\n} catch (Exception e) {\ne.printStackTrace();\n}\n}\n\npublic void cancel() {\ntry {\nsocket.close();\n} catch (Exception e) {\ne.printStackTrace();\n}\n}\n}\n}";
    }

    public static String c(String var0, String var1) {
        String var2 = "// Top-level build file where you can add configuration options common to all sub-projects/modules.\r\nbuildscript {\r\n\trepositories {\r\n\t\tgoogle()\r\n\t\tjcenter()\r\n\t}\r\n\tdependencies {\r\n\t\tclasspath 'com.android.tools.build:gradle:" +
                var0 +
                "'" +
                "\r\n" +
                "\t\tclasspath 'com.google.gms:google-services:" +
                var1 +
                "'" +
                "\r\n" +
                "\t\t// NOTE: Do not place your application dependencies here; they belong" +
                "\r\n" +
                "\t\t// in the individual module build.gradle files" +
                "\r\n" +
                "\t}" +
                "\r\n" +
                "}" +
                "\r\n" +
                "\r\n" +
                "allprojects {" +
                "\r\n" +
                "\trepositories {" +
                "\r\n" +
                "\t\tgoogle()" +
                "\r\n" +
                "\t\tjcenter()" +
                "\r\n" +
                "\t}" +
                "\r\n" +
                "}" +
                "\r\n\r\ntask clean(type: Delete) {\r\n\tdelete rootProject.buildDir\r\n}\r\n";
        return j(var2);
    }

    public static String c(String var0, String var1, String var2) {
        StringBuilder var3 = new StringBuilder();
        if (!var0.equals("include") && !var0.equals("#")) {
            var3.append("_drawer_");
            var3.append(var1);
            var3.append(" = (");
            var3.append(mq.e(var0));
            var3.append(") ");
            var3.append(var2);
            var3.append(".findViewById(R.id.");
            var3.append(var1);
            var3.append(");");
        }

        return var3.toString();
    }

    public static String d(String var0) {
        int var1 = var0.hashCode();
        if (var1 != -1908172204) {
            if (var1 != 80811813) {
                if (var1 == 1779003621 && var0.equals("FirebaseDB")) {
                    var0 = "private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();";
                    return var0;
                }
            } else if (var0.equals("Timer")) {
                var0 = "private Timer _timer = new Timer();";
                return var0;
            }
        } else if (var0.equals("FirebaseStorage")) {
            var0 = "private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();";
            return var0;
        }

        var0 = "";
        return var0;
    }

    public static String d(String var0, String var1, String var2) {
        String var3;
        byte var4;
        label131: {
            var3 = ManageEvent.g(var0, var1, var2);
            switch(var0.hashCode()) {
                case -2054042947:
                    if (var0.equals("onClickListener")) {
                        var4 = 0;
                        break label131;
                    }
                    break;
                case -2020041772:
                    if (var0.equals("sensorEventListener")) {
                        var4 = 10;
                        break label131;
                    }
                    break;
                case -1990409668:
                    if (var0.equals("onTextChangedListener")) {
                        var4 = 5;
                        break label131;
                    }
                    break;
                case -1907134451:
                    if (var0.equals("onDeleteSuccessListener")) {
                        var4 = 20;
                        break label131;
                    }
                    break;
                case -1362091184:
                    if (var0.equals("onDownloadSuccessListener")) {
                        var4 = 19;
                        break label131;
                    }
                    break;
                case -1353514613:
                    if (var0.equals("recognitionListener")) {
                        var4 = 23;
                        break label131;
                    }
                    break;
                case -933396353:
                    if (var0.equals("onFailureListener")) {
                        var4 = 21;
                        break label131;
                    }
                    break;
                case -924274776:
                    if (var0.equals("onDownloadProgressListener")) {
                        var4 = 18;
                        break label131;
                    }
                    break;
                case -829278715:
                    if (var0.equals("onMapMarkerClickListener")) {
                        var4 = 26;
                        break label131;
                    }
                    break;
                case -827956745:
                    if (var0.equals("adListener")) {
                        var4 = 15;
                        break label131;
                    }
                    break;
                case -803249912:
                    if (var0.equals("authCreateUserComplete")) {
                        var4 = 12;
                        break label131;
                    }
                    break;
                case -744728252:
                    if (var0.equals("webViewClient")) {
                        var4 = 11;
                        break label131;
                    }
                    break;
                case -359785373:
                    if (var0.equals("requestListener")) {
                        var4 = 22;
                        break label131;
                    }
                    break;
                case -332388831:
                    if (var0.equals("onItemSelectedListener")) {
                        var4 = 2;
                        break label131;
                    }
                    break;
                case -291578101:
                    if (var0.equals("onMapReadyCallback")) {
                        var4 = 25;
                        break label131;
                    }
                    break;
                case -291013445:
                    if (var0.equals("animatorListener")) {
                        var4 = 8;
                        break label131;
                    }
                    break;
                case -80069142:
                    if (var0.equals("onItemClickListener")) {
                        var4 = 3;
                        break label131;
                    }
                    break;
                case 149369550:
                    if (var0.equals("authSignInUserComplete")) {
                        var4 = 13;
                        break label131;
                    }
                    break;
                case 165600064:
                    if (var0.equals("onSeekBarChangeListener")) {
                        var4 = 6;
                        break label131;
                    }
                    break;
                case 462657998:
                    if (var0.equals("onItemLongClickListener")) {
                        var4 = 4;
                        break label131;
                    }
                    break;
                case 670396663:
                    if (var0.equals("onUploadSuccessListener")) {
                        var4 = 16;
                        break label131;
                    }
                    break;
                case 1118236689:
                    if (var0.equals("onDateChangeListener")) {
                        var4 = 7;
                        break label131;
                    }
                    break;
                case 1280727296:
                    if (var0.equals("bluetoothConnectionListener")) {
                        var4 = 24;
                        break label131;
                    }
                    break;
                case 1538933641:
                    if (var0.equals("locationListener")) {
                        var4 = 27;
                        break label131;
                    }
                    break;
                case 1560580237:
                    if (var0.equals("authResetEmailSent")) {
                        var4 = 14;
                        break label131;
                    }
                    break;
                case 1842370015:
                    if (var0.equals("onCheckChangedListener")) {
                        var4 = 1;
                        break label131;
                    }
                    break;
                case 1953306337:
                    if (var0.equals("onUploadProgressListener")) {
                        var4 = 17;
                        break label131;
                    }
                    break;
                case 2139736498:
                    if (var0.equals("childEventListener")) {
                        var4 = 9;
                        break label131;
                    }
            }

            var4 = -1;
        }

        StringBuilder var5;
        switch(var4) {
            case 0:
                var5 = new StringBuilder();
                var5.append(var1);
                var5.append(".setOnClickListener(new View.OnClickListener() {");
                var5.append("\r\n");
                var5.append("%s");
                var5.append("\r\n");
                var5.append("});");
                var0 = String.format(var5.toString(), var2);
                break;
            case 1:
                var5 = new StringBuilder();
                var5.append(var1);
                var5.append(".setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {");
                var5.append("\r\n");
                var5.append("%s");
                var5.append("\r\n");
                var5.append("});");
                var0 = String.format(var5.toString(), var2);
                break;
            case 2:
                var5 = new StringBuilder();
                var5.append(var1);
                var5.append(".setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {");
                var5.append("\r\n");
                var5.append("%s");
                var5.append("\r\n");
                var5.append("});");
                var0 = String.format(var5.toString(), var2);
                break;
            case 3:
                var5 = new StringBuilder();
                var5.append(var1);
                var5.append(".setOnItemClickListener(new AdapterView.OnItemClickListener() {");
                var5.append("\r\n");
                var5.append("%s");
                var5.append("\r\n");
                var5.append("});");
                var0 = String.format(var5.toString(), var2);
                break;
            case 4:
                var5 = new StringBuilder();
                var5.append(var1);
                var5.append(".setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {");
                var5.append("\r\n");
                var5.append("%s");
                var5.append("\r\n");
                var5.append("});");
                var0 = String.format(var5.toString(), var2);
                break;
            case 5:
                var5 = new StringBuilder();
                var5.append(var1);
                var5.append(".addTextChangedListener(new TextWatcher() {");
                var5.append("\r\n");
                var5.append("%s");
                var5.append("\r\n");
                var5.append("});");
                var0 = String.format(var5.toString(), var2);
                break;
            case 6:
                var5 = new StringBuilder();
                var5.append(var1);
                var5.append(".setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {");
                var5.append("\r\n");
                var5.append("%s");
                var5.append("\r\n");
                var5.append("});");
                var0 = String.format(var5.toString(), var2);
                break;
            case 7:
                var5 = new StringBuilder();
                var5.append(var1);
                var5.append(".setOnDateChangeListener(new CalendarView.OnDateChangeListener() {");
                var5.append("\r\n");
                var5.append("%s");
                var5.append("\r\n");
                var5.append("});");
                var0 = String.format(var5.toString(), var2);
                break;
            case 8:
                var5 = new StringBuilder();
                var5.append(var1);
                var5.append(".addListener(new Animator.AnimatorListener() {");
                var5.append("\r\n");
                var5.append("%s");
                var5.append("\r\n");
                var5.append("});");
                var0 = String.format(var5.toString(), var2);
                break;
            case 9:
                var5 = new StringBuilder();
                var5.append("_");
                var5.append(var1);
                var5.append("_child_listener = new ChildEventListener() {");
                var5.append("\r\n");
                var5.append("%s");
                var5.append("\r\n");
                var5.append("};");
                var5.append("\r\n");
                var5.append(var1);
                var5.append(".addChildEventListener(_");
                var5.append(var1);
                var5.append("_child_listener);");
                var0 = String.format(var5.toString(), var2);
                break;
            case 10:
                var5 = new StringBuilder();
                var5.append("_");
                var5.append(var1);
                var5.append("_sensor_listener = new SensorEventListener() {");
                var5.append("\r\n");
                var5.append("%s");
                var5.append("\r\n");
                var5.append("};");
                var5.append("\r\n");
                var5.append(var1);
                var5.append(".registerListener(_");
                var5.append(var1);
                var5.append("_sensor_listener, ");
                var5.append(var1);
                var5.append(".getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);");
                var0 = String.format(var5.toString(), var2);
                break;
            case 11:
                var5 = new StringBuilder();
                var5.append(var1);
                var5.append(".setWebViewClient(new WebViewClient() {");
                var5.append("\r\n");
                var5.append("%s");
                var5.append("\r\n");
                var5.append("});");
                var0 = String.format(var5.toString(), var2);
                break;
            case 12:
                var5 = new StringBuilder();
                var5.append("_");
                var5.append(var1);
                var5.append("_create_user_listener = new OnCompleteListener<AuthResult>() {");
                var5.append("\r\n");
                var5.append("%s");
                var5.append("\r\n");
                var5.append("};");
                var0 = String.format(var5.toString(), var2);
                break;
            case 13:
                var5 = new StringBuilder();
                var5.append("_");
                var5.append(var1);
                var5.append("_sign_in_listener = new OnCompleteListener<AuthResult>() {");
                var5.append("\r\n");
                var5.append("%s");
                var5.append("\r\n");
                var5.append("};");
                var0 = String.format(var5.toString(), var2);
                break;
            case 14:
                var5 = new StringBuilder();
                var5.append("_");
                var5.append(var1);
                var5.append("_reset_password_listener = new OnCompleteListener<Void>() {");
                var5.append("\r\n");
                var5.append("%s");
                var5.append("\r\n");
                var5.append("};");
                var0 = String.format(var5.toString(), var2);
                break;
            case 15:
                var5 = new StringBuilder();
                var5.append("_");
                var5.append(var1);
                var5.append("_ad_listener = new AdListener() {");
                var5.append("\r\n");
                var5.append("%s");
                var5.append("\r\n");
                var5.append("};");
                var0 = String.format(var5.toString(), var2);
                break;
            case 16:
                var5 = new StringBuilder();
                var5.append("_");
                var5.append(var1);
                var5.append("_upload_success_listener = new OnCompleteListener<Uri>() {");
                var5.append("\r\n");
                var5.append("%s");
                var5.append("\r\n");
                var5.append("};");
                var0 = String.format(var5.toString(), var2);
                break;
            case 17:
                var5 = new StringBuilder();
                var5.append("_");
                var5.append(var1);
                var5.append("_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {");
                var5.append("\r\n");
                var5.append("%s");
                var5.append("\r\n");
                var5.append("};");
                var0 = String.format(var5.toString(), var2);
                break;
            case 18:
                var5 = new StringBuilder();
                var5.append("_");
                var5.append(var1);
                var5.append("_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {");
                var5.append("\r\n");
                var5.append("%s");
                var5.append("\r\n");
                var5.append("};");
                var0 = String.format(var5.toString(), var2);
                break;
            case 19:
                var5 = new StringBuilder();
                var5.append("_");
                var5.append(var1);
                var5.append("_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {");
                var5.append("\r\n");
                var5.append("%s");
                var5.append("\r\n");
                var5.append("};");
                var0 = String.format(var5.toString(), var2);
                break;
            case 20:
                var5 = new StringBuilder();
                var5.append("_");
                var5.append(var1);
                var5.append("_delete_success_listener = new OnSuccessListener() {");
                var5.append("\r\n");
                var5.append("%s");
                var5.append("\r\n");
                var5.append("};");
                var0 = String.format(var5.toString(), var2);
                break;
            case 21:
                var5 = new StringBuilder();
                var5.append("_");
                var5.append(var1);
                var5.append("_failure_listener = new OnFailureListener() {");
                var5.append("\r\n");
                var5.append("%s");
                var5.append("\r\n");
                var5.append("};");
                var0 = String.format(var5.toString(), var2);
                break;
            case 22:
                var5 = new StringBuilder();
                var5.append("_");
                var5.append(var1);
                var5.append("_request_listener = new RequestNetwork.RequestListener() {");
                var5.append("\r\n");
                var5.append("%s");
                var5.append("\r\n");
                var5.append("};");
                var0 = String.format(var5.toString(), var2);
                break;
            case 23:
                var5 = new StringBuilder();
                var5.append(var1);
                var5.append(".setRecognitionListener(new RecognitionListener() {");
                var5.append("\r\n");
                var5.append("@Override");
                var5.append("\r\n");
                var5.append("public void onReadyForSpeech(Bundle _param1) {");
                var5.append("\r\n");
                var5.append("}");
                var5.append("\r\n");
                var5.append("@Override");
                var5.append("\r\n");
                var5.append("public void onBeginningOfSpeech() {");
                var5.append("\r\n");
                var5.append("}");
                var5.append("\r\n");
                var5.append("@Override");
                var5.append("\r\n");
                var5.append("public void onRmsChanged(float _param1) {");
                var5.append("\r\n");
                var5.append("}");
                var5.append("\r\n");
                var5.append("@Override");
                var5.append("\r\n");
                var5.append("public void onBufferReceived(byte[] _param1) {");
                var5.append("\r\n");
                var5.append("}");
                var5.append("\r\n");
                var5.append("@Override");
                var5.append("\r\n");
                var5.append("public void onEndOfSpeech() {");
                var5.append("\r\n");
                var5.append("}");
                var5.append("\r\n");
                var5.append("@Override");
                var5.append("\r\n");
                var5.append("public void onPartialResults(Bundle _param1) {");
                var5.append("\r\n");
                var5.append("}");
                var5.append("\r\n");
                var5.append("@Override");
                var5.append("\r\n");
                var5.append("public void onEvent(int _param1, Bundle _param2) {");
                var5.append("\r\n");
                var5.append("}");
                var5.append("\r\n");
                var5.append("%s");
                var5.append("\r\n");
                var5.append("});");
                var0 = String.format(var5.toString(), var2);
                break;
            case 24:
                var5 = new StringBuilder();
                var5.append("_");
                var5.append(var1);
                var5.append("_bluetooth_connection_listener = new BluetoothConnect.BluetoothConnectionListener() {");
                var5.append("\r\n");
                var5.append("%s");
                var5.append("\r\n");
                var5.append("};");
                var0 = String.format(var5.toString(), var2);
                break;
            case 25:
                var5 = new StringBuilder();
                var5.append("_");
                var5.append(var1);
                var5.append("_controller = new GoogleMapController(");
                var5.append(var1);
                var5.append(", new OnMapReadyCallback() {");
                var5.append("\r\n");
                var5.append("@Override");
                var5.append("\r\n");
                var5.append("public void onMapReady(GoogleMap _googleMap) {");
                var5.append("\r\n");
                var5.append("_");
                var5.append(var1);
                var5.append("_controller.setGoogleMap(_googleMap);");
                var5.append("\r\n");
                var5.append("%s");
                var5.append("\r\n");
                var5.append("}");
                var5.append("\r\n");
                var5.append("});");
                var0 = String.format(var5.toString(), var2);
                break;
            case 26:
                var5 = new StringBuilder();
                var5.append("_");
                var5.append(var1);
                var5.append("_controller.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {");
                var5.append("\r\n");
                var5.append("%s");
                var5.append("\r\n");
                var5.append("});");
                var0 = String.format(var5.toString(), var2);
                break;
            case 27:
                var5 = new StringBuilder();
                var5.append("_");
                var5.append(var1);
                var5.append("_location_listener = new LocationListener() {");
                var5.append("\r\n");
                var5.append("%s");
                var5.append("\r\n");
                var5.append("@Override\npublic void onStatusChanged(String provider, int status, Bundle extras) {}\n@Override\npublic void onProviderEnabled(String provider) {}\n@Override\npublic void onProviderDisabled(String provider) {}\n};");
                var0 = String.format(var5.toString(), var2);
                break;
            default:
                var0 = "";
        }

        var1 = var0;
        if (var0.equals("")) {
            var1 = var3;
        }

        return var1;
    }

    public static String e(String var0) {
        return "package " +
                var0 +
                ";\n\nimport android.content.ContentResolver;\nimport android.content.ContentUris;\nimport android.content.Context;\nimport android.database.Cursor;\nimport android.graphics.Bitmap;\nimport android.graphics.BitmapFactory;\nimport android.graphics.Canvas;\nimport android.graphics.ColorFilter;\nimport android.graphics.ColorMatrix;\nimport android.graphics.ColorMatrixColorFilter;\nimport android.graphics.LightingColorFilter;\nimport android.graphics.Matrix;\nimport android.graphics.Paint;\nimport android.graphics.PorterDuff;\nimport android.graphics.PorterDuffXfermode;\nimport android.graphics.Rect;\nimport android.graphics.RectF;\nimport android.media.ExifInterface;\nimport android.net.Uri;\nimport android.os.Environment;\nimport android.provider.DocumentsContract;\nimport android.provider.MediaStore;\nimport android.text.TextUtils;\n\nimport java.io.File;\nimport java.io.FileInputStream;\nimport java.io.FileOutputStream;\nimport java.io.FileReader;\nimport java.io.FileWriter;\nimport java.io.IOException;\nimport java.net.URLDecoder;\nimport java.text.SimpleDateFormat;\nimport java.util.ArrayList;\nimport java.util.Date;\n\npublic class FileUtil {\n\n\tprivate static void createNewFile(String path) {\n\t\tint lastSep = path.lastIndexOf(File.separator);\n\t\tif (lastSep > 0) {\n\t\t\tString dirPath = path.substring(0, lastSep);\n\t\t\tmakeDir(dirPath);\n\t\t}\n\n\t\tFile file = new File(path);\n\n\t\ttry {\n\t\t\tif (!file.exists())\n\t\t\t\tfile.createNewFile();\n\t\t} catch (IOException e) {\n\t\t\te.printStackTrace();\n\t\t}\n\t}\n\n\tpublic static String readFile(String path) {\n\t\tcreateNewFile(path);\n\n\t\tStringBuilder sb = new StringBuilder();\n\t\tFileReader fr = null;\n\t\ttry {\n\t\t\tfr = new FileReader(new File(path));\n\n\t\t\tchar[] buff = new char[1024];\n\t\t\tint length = 0;\n\n\t\t\twhile ((length = fr.read(buff)) > 0) {\n\t\t\t\tsb.append(new String(buff, 0, length));\n\t\t\t}\n\t\t} catch (IOException e) {\n\t\t\te.printStackTrace();\n\t\t} finally {\n\t\t\tif (fr != null) {\n\t\t\t\ttry {\n\t\t\t\t\tfr.close();\n\t\t\t\t} catch (Exception e) {\n\t\t\t\t\te.printStackTrace();\n\t\t\t\t}\n\t\t\t}\n\t\t}\n\n\t\treturn sb.toString();\n\t}\n\n\tpublic static void writeFile(String path, String str) {\n\t\tcreateNewFile(path);\n\t\tFileWriter fileWriter = null;\n\n\t\ttry {\n\t\t\tfileWriter = new FileWriter(new File(path), false);\n\t\t\tfileWriter.write(str);\n\t\t\tfileWriter.flush();\n\t\t} catch (IOException e) {\n\t\t\te.printStackTrace();\n\t\t} finally {\n\t\t\ttry {\n\t\t\t\tif (fileWriter != null)\n\t\t\t\t\tfileWriter.close();\n\t\t\t} catch (IOException e) {\n\t\t\t\te.printStackTrace();\n\t\t\t}\n\t\t}\n\t}\n\n\tpublic static void copyFile(String sourcePath, String destPath) {\n\t\tif (!isExistFile(sourcePath)) return;\n\t\tcreateNewFile(destPath);\n\n\t\tFileInputStream fis = null;\n\t\tFileOutputStream fos = null;\n\n\t\ttry {\n\t\t\tfis = new FileInputStream(sourcePath);\n\t\t\tfos = new FileOutputStream(destPath, false);\n\n\t\t\tbyte[] buff = new byte[1024];\n\t\t\tint length = 0;\n\n\t\t\twhile ((length = fis.read(buff)) > 0) {\n\t\t\t\tfos.write(buff, 0, length);\n\t\t\t}\n\t\t} catch (IOException e) {\n\t\t\te.printStackTrace();\n\t\t} finally {\n\t\t\tif (fis != null) {\n\t\t\t\ttry {\n\t\t\t\t\tfis.close();\n\t\t\t\t} catch (IOException e) {\n\t\t\t\t\te.printStackTrace();\n\t\t\t\t}\n\t\t\t}\n\t\t\tif (fos != null) {\n\t\t\t\ttry {\n\t\t\t\t\tfos.close();\n\t\t\t\t} catch (IOException e) {\n\t\t\t\t\te.printStackTrace();\n\t\t\t\t}\n\t\t\t}\n\t\t}\n\t}\n\n\tpublic static void moveFile(String sourcePath, String destPath) {\n\t\tcopyFile(sourcePath, destPath);\n\t\tdeleteFile(sourcePath);\n\t}\n\n\tpublic static void deleteFile(String path) {\n\t\tFile file = new File(path);\n\n\t\tif (!file.exists()) return;\n\n\t\tif (file.isFile()) {\n\t\t\tfile.delete();\n\t\t\treturn;\n\t\t}\n\n\t\tFile[] fileArr = file.listFiles();\n\n\t\tif (fileArr != null) {\n\t\t\tfor (File subFile : fileArr) {\n\t\t\t\tif (subFile.isDirectory()) {\n\t\t\t\t\tdeleteFile(subFile.getAbsolutePath());\n\t\t\t\t}\n\n\t\t\t\tif (subFile.isFile()) {\n\t\t\t\t\tsubFile.delete();\n\t\t\t\t}\n\t\t\t}\n\t\t}\n\n\t\tfile.delete();\n\t}\n\n\tpublic static boolean isExistFile(String path) {\n\t\tFile file = new File(path);\n\t\treturn file.exists();\n\t}\n\n\tpublic static void makeDir(String path) {\n\t\tif (!isExistFile(path)) {\n\t\t\tFile file = new File(path);\n\t\t\tfile.mkdirs();\n\t\t}\n\t}\n\n\tpublic static void listDir(String path, ArrayList<String> list) {\n\t\tFile dir = new File(path);\n\t\tif (!dir.exists() || dir.isFile()) return;\n\n\t\tFile[] listFiles = dir.listFiles();\n\t\tif (listFiles == null || listFiles.length <= 0) return;\n\n\t\tif (list == null) return;\n\t\tlist.clear();\n\t\tfor (File file : listFiles) {\n\t\t\tlist.add(file.getAbsolutePath());\n\t\t}\n\t}\n\n\tpublic static boolean isDirectory(String path) {\n\t\tif (!isExistFile(path)) return false;\n\t\treturn new File(path).isDirectory();\n\t}\n\n\tpublic static boolean isFile(String path) {\n\t\tif (!isExistFile(path)) return false;\n\t\treturn new File(path).isFile();\n\t}\n\n\tpublic static long getFileLength(String path) {\n\t\tif (!isExistFile(path)) return 0;\n\t\treturn new File(path).length();\n\t}\n\n\tpublic static String getExternalStorageDir() {\n\t\treturn Environment.getExternalStorageDirectory().getAbsolutePath();\n\t}\n\n\tpublic static String getPackageDataDir(Context context) {\n\t\treturn context.getExternalFilesDir(null).getAbsolutePath();\n\t}\n\n\tpublic static String getPublicDir(String type) {\n\t\treturn Environment.getExternalStoragePublicDirectory(type).getAbsolutePath();\n\t}\n\n\tpublic static String convertUriToFilePath(final Context context, final Uri uri) {\n\t\tString path = null;\n\t\tif (DocumentsContract.isDocumentUri(context, uri)) {\n\t\t\tif (isExternalStorageDocument(uri)) {\n\t\t\t\tfinal String docId = DocumentsContract.getDocumentId(uri);\n\t\t\t\tfinal String[] split = docId.split(\":\");\n\t\t\t\tfinal String type = split[0];\n\n\t\t\t\tif (\"primary\".equalsIgnoreCase(type)) {\n\t\t\t\t\tpath = Environment.getExternalStorageDirectory() + \"/\" + split[1];\n\t\t\t\t}\n\t\t\t} else if (isDownloadsDocument(uri)) {\n\t\t\t\tfinal String id = DocumentsContract.getDocumentId(uri);\n\n\t\t\t\tif (!TextUtils.isEmpty(id)) {\n\t\t\t\t\tif (id.startsWith(\"raw:\")) {\n\t\t\t\t\t\treturn id.replaceFirst(\"raw:\", \"\");\n\t\t\t\t\t}\n\t\t\t\t}\n\n\t\t\t\tfinal Uri contentUri = ContentUris\n\t\t\t\t\t\t.withAppendedId(Uri.parse(\"content://downloads/public_downloads\"), Long.valueOf(id));\n\n\t\t\t\tpath = getDataColumn(context, contentUri, null, null);\n\t\t\t} else if (isMediaDocument(uri)) {\n\t\t\t\tfinal String docId = DocumentsContract.getDocumentId(uri);\n\t\t\t\tfinal String[] split = docId.split(\":\");\n\t\t\t\tfinal String type = split[0];\n\n\t\t\t\tUri contentUri = null;\n\t\t\t\tif (\"image\".equals(type)) {\n\t\t\t\t\tcontentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;\n\t\t\t\t} else if (\"video\".equals(type)) {\n\t\t\t\t\tcontentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;\n\t\t\t\t} else if (\"audio\".equals(type)) {\n\t\t\t\t\tcontentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;\n\t\t\t\t}\n\n\t\t\t\tfinal String selection = \"_id=?\";\n\t\t\t\tfinal String[] selectionArgs = new String[]{\n\t\t\t\t\t\tsplit[1]\n\t\t\t\t};\n\n\t\t\t\tpath = getDataColumn(context, contentUri, selection, selectionArgs);\n\t\t\t}\n\t\t} else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(uri.getScheme())) {\n\t\t\tpath = getDataColumn(context, uri, null, null);\n\t\t} else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(uri.getScheme())) {\n\t\t\tpath = uri.getPath();\n\t\t}\n\n\t\tif (path != null) {\n\t\t\ttry {\n\t\t\t\treturn URLDecoder.decode(path, \"UTF-8\");\n\t\t\t}catch(Exception e){\n\t\t\t\treturn null;\n\t\t\t}\n\t\t}\n\t\treturn null;\n\t}\n\n\tprivate static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {\n\t\tCursor cursor = null;\n\n\t\tfinal String column = MediaStore.Images.Media.DATA;\n\t\tfinal String[] projection = {\n\t\t\t\tcolumn\n\t\t};\n\n\t\ttry {\n\t\t\tcursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);\n\t\t\tif (cursor != null && cursor.moveToFirst()) {\n\t\t\t\tfinal int column_index = cursor.getColumnIndexOrThrow(column);\n\t\t\t\treturn cursor.getString(column_index);\n\t\t\t}\n\t\t} catch (Exception e) {\n\n\t\t} finally {\n\t\t\tif (cursor != null) {\n\t\t\t\tcursor.close();\n\t\t\t}\n\t\t}\n\t\treturn null;\n\t}\n\n\n\tprivate static boolean isExternalStorageDocument(Uri uri) {\n\t\treturn \"com.android.externalstorage.documents\".equals(uri.getAuthority());\n\t}\n\n\tprivate static boolean isDownloadsDocument(Uri uri) {\n\t\treturn \"com.android.providers.downloads.documents\".equals(uri.getAuthority());\n\t}\n\n\tprivate static boolean isMediaDocument(Uri uri) {\n\t\treturn \"com.android.providers.media.documents\".equals(uri.getAuthority());\n\t}\n\n\tprivate static void saveBitmap(Bitmap bitmap, String destPath) {\n\t\tFileOutputStream out = null;\n\t\tFileUtil.createNewFile(destPath);\n\t\ttry {\n\t\t\tout = new FileOutputStream(new File(destPath));\n\t\t\tbitmap.compress(Bitmap.CompressFormat.PNG, 100, out);\n\t\t} catch (Exception e) {\n\t\t\te.printStackTrace();\n\t\t} finally {\n\t\t\ttry {\n\t\t\t\tif (out != null) {\n\t\t\t\t\tout.close();\n\t\t\t\t}\n\t\t\t} catch (IOException e) {\n\t\t\t\te.printStackTrace();\n\t\t\t}\n\t\t}\n\t}\n\n\tpublic static Bitmap getScaledBitmap(String path, int max) {\n\t\tBitmap src = BitmapFactory.decodeFile(path);\n\n\t\tint width = src.getWidth();\n\t\tint height = src.getHeight();\n\t\tfloat rate = 0.0f;\n\n\t\tif (width > height) {\n\t\t\trate = max / (float) width;\n\t\t\theight = (int) (height * rate);\n\t\t\twidth = max;\n\t\t} else {\n\t\t\trate = max / (float) height;\n\t\t\twidth = (int) (width * rate);\n\t\t\theight = max;\n\t\t}\n\n\t\treturn Bitmap.createScaledBitmap(src, width, height, true);\n\t}\n\n\tpublic static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {\n\t\tfinal int width = options.outWidth;\n\t\tfinal int height = options.outHeight;\n\t\tint inSampleSize = 1;\n\n\t\tif (height > reqHeight || width > reqWidth) {\n\t\t\tfinal int halfHeight = height / 2;\n\t\t\tfinal int halfWidth = width / 2;\n\n\t\t\twhile ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {\n\t\t\t\tinSampleSize *= 2;\n\t\t\t}\n\t\t}\n\n\t\treturn inSampleSize;\n\t}\n\n\tpublic static Bitmap decodeSampleBitmapFromPath(String path, int reqWidth, int reqHeight) {\n\t\tfinal BitmapFactory.Options options = new BitmapFactory.Options();\n\t\toptions.inJustDecodeBounds = true;\n\t\tBitmapFactory.decodeFile(path, options);\n\n\t\toptions.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);\n\n\t\toptions.inJustDecodeBounds = false;\n\t\treturn BitmapFactory.decodeFile(path, options);\n\t}\n\n\tpublic static void resizeBitmapFileRetainRatio(String fromPath, String destPath, int max) {\n\t\tif (!isExistFile(fromPath)) return;\n\t\tBitmap bitmap = getScaledBitmap(fromPath, max);\n\t\tsaveBitmap(bitmap, destPath);\n\t}\n\n\tpublic static void resizeBitmapFileToSquare(String fromPath, String destPath, int max) {\n\t\tif (!isExistFile(fromPath)) return;\n\t\tBitmap src = BitmapFactory.decodeFile(fromPath);\n\t\tBitmap bitmap = Bitmap.createScaledBitmap(src, max, max, true);\n\t\tsaveBitmap(bitmap, destPath);\n\t}\n\n\tpublic static void resizeBitmapFileToCircle(String fromPath, String destPath) {\n\t\tif (!isExistFile(fromPath)) return;\n\t\tBitmap src = BitmapFactory.decodeFile(fromPath);\n\t\tBitmap bitmap = Bitmap.createBitmap(src.getWidth(),\n\t\t\t\tsrc.getHeight(), Bitmap.Config.ARGB_8888);\n\t\tCanvas canvas = new Canvas(bitmap);\n\n\t\tfinal int color = 0xff424242;\n\t\tfinal Paint paint = new Paint();\n\t\tfinal Rect rect = new Rect(0, 0, src.getWidth(), src.getHeight());\n\n\t\tpaint.setAntiAlias(true);\n\t\tcanvas.drawARGB(0, 0, 0, 0);\n\t\tpaint.setColor(color);\n\t\tcanvas.drawCircle(src.getWidth() / 2, src.getHeight() / 2,\n\t\t\t\tsrc.getWidth() / 2, paint);\n\t\tpaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));\n\t\tcanvas.drawBitmap(src, rect, rect, paint);\n\n\t\tsaveBitmap(bitmap, destPath);\n\t}\n\n\tpublic static void resizeBitmapFileWithRoundedBorder(String fromPath, String destPath, int pixels) {\n\t\tif (!isExistFile(fromPath)) return;\n\t\tBitmap src = BitmapFactory.decodeFile(fromPath);\n\t\tBitmap bitmap = Bitmap.createBitmap(src.getWidth(), src\n\t\t\t\t.getHeight(), Bitmap.Config.ARGB_8888);\n\t\tCanvas canvas = new Canvas(bitmap);\n\n\t\tfinal int color = 0xff424242;\n\t\tfinal Paint paint = new Paint();\n\t\tfinal Rect rect = new Rect(0, 0, src.getWidth(), src.getHeight());\n\t\tfinal RectF rectF = new RectF(rect);\n\t\tfinal float roundPx = pixels;\n\n\t\tpaint.setAntiAlias(true);\n\t\tcanvas.drawARGB(0, 0, 0, 0);\n\t\tpaint.setColor(color);\n\t\tcanvas.drawRoundRect(rectF, roundPx, roundPx, paint);\n\n\t\tpaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));\n\t\tcanvas.drawBitmap(src, rect, rect, paint);\n\n\t\tsaveBitmap(bitmap, destPath);\n\t}\n\n\tpublic static void cropBitmapFileFromCenter(String fromPath, String destPath, int w, int h) {\n\t\tif (!isExistFile(fromPath)) return;\n\t\tBitmap src = BitmapFactory.decodeFile(fromPath);\n\n\t\tint width = src.getWidth();\n\t\tint height = src.getHeight();\n\n\t\tif (width < w && height < h)\n\t\t\treturn;\n\n\t\tint x = 0;\n\t\tint y = 0;\n\n\t\tif (width > w)\n\t\t\tx = (width - w) / 2;\n\n\t\tif (height > h)\n\t\t\ty = (height - h) / 2;\n\n\t\tint cw = w;\n\t\tint ch = h;\n\n\t\tif (w > width)\n\t\t\tcw = width;\n\n\t\tif (h > height)\n\t\t\tch = height;\n\n\t\tBitmap bitmap = Bitmap.createBitmap(src, x, y, cw, ch);\n\t\tsaveBitmap(bitmap, destPath);\n\t}\n\n\tpublic static void rotateBitmapFile(String fromPath, String destPath, float angle) {\n\t\tif (!isExistFile(fromPath)) return;\n\t\tBitmap src = BitmapFactory.decodeFile(fromPath);\n\t\tMatrix matrix = new Matrix();\n\t\tmatrix.postRotate(angle);\n\t\tBitmap bitmap = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);\n\t\tsaveBitmap(bitmap, destPath);\n\t}\n\n\tpublic static void scaleBitmapFile(String fromPath, String destPath, float x, float y) {\n\t\tif (!isExistFile(fromPath)) return;\n\t\tBitmap src = BitmapFactory.decodeFile(fromPath);\n\t\tMatrix matrix = new Matrix();\n\t\tmatrix.postScale(x, y);\n\n\t\tint w = src.getWidth();\n\t\tint h = src.getHeight();\n\n\t\tBitmap bitmap = Bitmap.createBitmap(src, 0, 0, w, h, matrix, true);\n\t\tsaveBitmap(bitmap, destPath);\n\t}\n\n\tpublic static void skewBitmapFile(String fromPath, String destPath, float x, float y) {\n\t\tif (!isExistFile(fromPath)) return;\n\t\tBitmap src = BitmapFactory.decodeFile(fromPath);\n\t\tMatrix matrix = new Matrix();\n\t\tmatrix.postSkew(x, y);\n\n\t\tint w = src.getWidth();\n\t\tint h = src.getHeight();\n\n\t\tBitmap bitmap = Bitmap.createBitmap(src, 0, 0, w, h, matrix, true);\n\t\tsaveBitmap(bitmap, destPath);\n\t}\n\n\tpublic static void setBitmapFileColorFilter(String fromPath, String destPath, int color) {\n\t\tif (!isExistFile(fromPath)) return;\n\t\tBitmap src = BitmapFactory.decodeFile(fromPath);\n\t\tBitmap bitmap = Bitmap.createBitmap(src, 0, 0,\n\t\t\t\tsrc.getWidth() - 1, src.getHeight() - 1);\n\t\tPaint p = new Paint();\n\t\tColorFilter filter = new LightingColorFilter(color, 1);\n\t\tp.setColorFilter(filter);\n\t\tCanvas canvas = new Canvas(bitmap);\n\t\tcanvas.drawBitmap(bitmap, 0, 0, p);\n\t\tsaveBitmap(bitmap, destPath);\n\t}\n\n\tpublic static void setBitmapFileBrightness(String fromPath, String destPath, float brightness) {\n\t\tif (!isExistFile(fromPath)) return;\n\t\tBitmap src = BitmapFactory.decodeFile(fromPath);\n\t\tColorMatrix cm = new ColorMatrix(new float[]\n\t\t\t\t{\n\t\t\t\t\t\t1, 0, 0, 0, brightness,\n\t\t\t\t\t\t0, 1, 0, 0, brightness,\n\t\t\t\t\t\t0, 0, 1, 0, brightness,\n\t\t\t\t\t\t0, 0, 0, 1, 0\n\t\t\t\t});\n\n\t\tBitmap bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());\n\t\tCanvas canvas = new Canvas(bitmap);\n\t\tPaint paint = new Paint();\n\t\tpaint.setColorFilter(new ColorMatrixColorFilter(cm));\n\t\tcanvas.drawBitmap(src, 0, 0, paint);\n\t\tsaveBitmap(bitmap, destPath);\n\t}\n\n\tpublic static void setBitmapFileContrast(String fromPath, String destPath, float contrast) {\n\t\tif (!isExistFile(fromPath)) return;\n\t\tBitmap src = BitmapFactory.decodeFile(fromPath);\n\t\tColorMatrix cm = new ColorMatrix(new float[]\n\t\t\t\t{\n\t\t\t\t\t\tcontrast, 0, 0, 0, 0,\n\t\t\t\t\t\t0, contrast, 0, 0, 0,\n\t\t\t\t\t\t0, 0, contrast, 0, 0,\n\t\t\t\t\t\t0, 0, 0, 1, 0\n\t\t\t\t});\n\n\t\tBitmap bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());\n\t\tCanvas canvas = new Canvas(bitmap);\n\t\tPaint paint = new Paint();\n\t\tpaint.setColorFilter(new ColorMatrixColorFilter(cm));\n\t\tcanvas.drawBitmap(src, 0, 0, paint);\n\n\t\tsaveBitmap(bitmap, destPath);\n\t}\n\n\tpublic static int getJpegRotate(String filePath) {\n\t\tint rotate = 0;\n\t\ttry {\n\t\t\tExifInterface exif = new ExifInterface(filePath);\n\t\t\tint iOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);\n\n\t\t\tswitch (iOrientation) {\n\t\t\t\tcase ExifInterface.ORIENTATION_ROTATE_90:\n\t\t\t\t\trotate = 90;\n\t\t\t\t\tbreak;\n\t\t\t\tcase ExifInterface.ORIENTATION_ROTATE_180:\n\t\t\t\t\trotate = 180;\n\t\t\t\t\tbreak;\n\t\t\t\tcase ExifInterface.ORIENTATION_ROTATE_270:\n\t\t\t\t\trotate = 270;\n\t\t\t\t\tbreak;\n\t\t\t}\n\t\t}\n\t\tcatch (IOException e) {\n\t\t\treturn 0;\n\t\t}\n\n\t\treturn rotate;\n\t}\n\tpublic static File createNewPictureFile(Context context) {\n\t\tSimpleDateFormat date = new SimpleDateFormat(\"yyyyMMdd_HHmmss\");\n\t\tString fileName = date.format(new Date()) + \".jpg\";\n\t\treturn new File(context.getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator + fileName);\n\t}\n}\n";
    }

    public static String f(String var0) {
        return "package " +
                var0 +
                ";" +
                "\r\n" +
                "\nimport com.google.android.gms.maps.CameraUpdateFactory;\nimport com.google.android.gms.maps.GoogleMap;\nimport com.google.android.gms.maps.MapView;\nimport com.google.android.gms.maps.OnMapReadyCallback;\nimport com.google.android.gms.maps.model.BitmapDescriptorFactory;\nimport com.google.android.gms.maps.model.LatLng;\nimport com.google.android.gms.maps.model.Marker;\nimport com.google.android.gms.maps.model.MarkerOptions;\n\nimport java.util.HashMap;\n\npublic class GoogleMapController {\n\nprivate GoogleMap googleMap;\nprivate MapView mapView;\nprivate HashMap<String, Marker> mapMarker;\nprivate GoogleMap.OnMarkerClickListener onMarkerClickListener;\n\npublic GoogleMapController(MapView mapView, OnMapReadyCallback onMapReadyCallback) {\nthis.mapView = mapView;\nmapMarker = new HashMap<>();\n\nthis.mapView.getMapAsync(onMapReadyCallback);\n}\n\npublic void setGoogleMap(GoogleMap googleMap) {\nthis.googleMap = googleMap;\n\nif (onMarkerClickListener != null) {\nthis.googleMap.setOnMarkerClickListener(onMarkerClickListener);\n}\n}\n\npublic GoogleMap getGoogleMap() {\nreturn googleMap;\n}\n\npublic void setMapType(int _mapType) {\nif (googleMap == null) return;\n\ngoogleMap.setMapType(_mapType);\n}\n\npublic void setOnMarkerClickListener(GoogleMap.OnMarkerClickListener onMarkerClickListener) {\nthis.onMarkerClickListener = onMarkerClickListener;\n\nif (googleMap != null) {\nthis.googleMap.setOnMarkerClickListener(onMarkerClickListener);\n}\n}\n\npublic void addMarker(String id, double lat, double lng) {\nif (googleMap == null) return;\n\nMarkerOptions markerOptions = new MarkerOptions();\nmarkerOptions.position(new LatLng(lat, lng));\nMarker marker = googleMap.addMarker(markerOptions);\nmarker.setTag(id);\nmapMarker.put(id, marker);\n}\n\npublic Marker getMarker(String id) {\nreturn mapMarker.get(id);\n}\n\npublic void setMarkerInfo(String id, String title, String snippet) {\nMarker marker = mapMarker.get(id);\nif (marker == null) return;\n\nmarker.setTitle(title);\nmarker.setSnippet(snippet);\n}\n\npublic void setMarkerPosition(String id, double lat, double lng) {\nMarker marker = mapMarker.get(id);\nif (marker == null) return;\n\nmarker.setPosition(new LatLng(lat, lng));\n}\n\npublic void setMarkerColor(String id, float color, double alpha) {\nMarker marker = mapMarker.get(id);\nif (marker == null) return;\n\nmarker.setAlpha((float) alpha);\nmarker.setIcon(BitmapDescriptorFactory.defaultMarker(color));\n}\n\npublic void setMarkerIcon(String id, int resIcon) {\nMarker marker = mapMarker.get(id);\nif (marker == null) return;\n\nmarker.setIcon(BitmapDescriptorFactory.fromResource(resIcon));\n}\n\npublic void setMarkerVisible(String id, boolean visible) {\nMarker marker = mapMarker.get(id);\nif (marker == null) return;\n\nmarker.setVisible(visible);\n}\n\n\npublic void moveCamera(double lat, double lng) {\nif (googleMap == null) return;\n\ngoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));\n}\n\npublic void zoomTo(double zoom) {\nif (googleMap == null) return;\n\ngoogleMap.moveCamera(CameraUpdateFactory.zoomTo((float) zoom));\n}\n\npublic void zoomIn() {\nif (googleMap == null) return;\n\ngoogleMap.moveCamera(CameraUpdateFactory.zoomIn());\n}\n\npublic void zoomOut() {\nif (googleMap == null) return;\n\ngoogleMap.moveCamera(CameraUpdateFactory.zoomOut());\n}\n}";
    }

    public static String g(String var0) {
        return "package " +
                var0 +
                ";\n\nimport com.google.gson.Gson;\n\nimport java.io.IOException;\nimport java.security.cert.CertificateException;\nimport java.util.HashMap;\nimport java.util.concurrent.TimeUnit;\n\nimport javax.net.ssl.HostnameVerifier;\nimport javax.net.ssl.SSLContext;\nimport javax.net.ssl.SSLSession;\nimport javax.net.ssl.SSLSocketFactory;\nimport javax.net.ssl.TrustManager;\nimport javax.net.ssl.X509TrustManager;\n\nimport okhttp3.Call;\nimport okhttp3.Callback;\nimport okhttp3.FormBody;\nimport okhttp3.Headers;\nimport okhttp3.HttpUrl;\nimport okhttp3.OkHttpClient;\nimport okhttp3.Request;\nimport okhttp3.RequestBody;\nimport okhttp3.Response;\n\npublic class RequestNetworkController {\npublic static final String GET      = \"GET\";\npublic static final String POST     = \"POST\";\npublic static final String PUT      = \"PUT\";\npublic static final String DELETE   = \"DELETE\";\n\npublic static final int REQUEST_PARAM = 0;\npublic static final int REQUEST_BODY  = 1;\n\nprivate static final int SOCKET_TIMEOUT = 15000;\nprivate static final int READ_TIMEOUT   = 25000;\n\nprotected OkHttpClient client;\n\nprivate static RequestNetworkController mInstance;\n\npublic static synchronized RequestNetworkController getInstance() {\nif(mInstance == null) {\nmInstance = new RequestNetworkController();\n}\nreturn mInstance;\n}\n\nprivate OkHttpClient getClient() {\nif (client == null) {\nOkHttpClient.Builder builder = new OkHttpClient.Builder();\n\ntry {\nfinal TrustManager[] trustAllCerts = new TrustManager[]{\nnew X509TrustManager() {\n@Override\npublic void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)\nthrows CertificateException {\n}\n\n@Override\npublic void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)\nthrows CertificateException {\n}\n\n@Override\npublic java.security.cert.X509Certificate[] getAcceptedIssuers() {\nreturn new java.security.cert.X509Certificate[]{};\n}\n}\n};\n\nfinal SSLContext sslContext = SSLContext.getInstance(\"TLS\");\nsslContext.init(null, trustAllCerts, new java.security.SecureRandom());\nfinal SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();\nbuilder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);\nbuilder.connectTimeout(SOCKET_TIMEOUT, TimeUnit.MILLISECONDS);\nbuilder.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);\nbuilder.writeTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);\nbuilder.hostnameVerifier(new HostnameVerifier() {\n@Override\npublic boolean verify(String hostname, SSLSession session) {\nreturn true;\n}\n});\n} catch (Exception e) {\n}\n\nclient = builder.build();\n}\n\nreturn client;\n}\n\npublic void execute(final RequestNetwork requestNetwork, String method, String url, final String tag, final RequestNetwork.RequestListener requestListener) {\nRequest.Builder reqBuilder = new Request.Builder();\nHeaders.Builder headerBuilder = new Headers.Builder();\n\nif(requestNetwork.getHeaders().size() > 0) {\nHashMap<String, Object> headers = requestNetwork.getHeaders();\n\nfor(HashMap.Entry<String, Object> header : headers.entrySet()) {\nheaderBuilder.add(header.getKey(), String.valueOf(header.getValue()));\n}\n}\n\ntry {\nif (requestNetwork.getRequestType() == REQUEST_PARAM) {\nif (method.equals(GET)) {\nHttpUrl.Builder httpBuilder;\n\ntry {\nhttpBuilder = HttpUrl.parse(url).newBuilder();\n} catch (NullPointerException ne) {\nthrow new NullPointerException(\"unexpected url: \" + url);\n}\n\nif (requestNetwork.getParams().size() > 0) {\nHashMap<String, Object> params = requestNetwork.getParams();\n\nfor (HashMap.Entry<String, Object> param : params.entrySet()) {\nhttpBuilder.addQueryParameter(param.getKey(), String.valueOf(param.getValue()));\n}\n}\n\nreqBuilder.url(httpBuilder.build()).headers(headerBuilder.build()).get();\n} else {\nFormBody.Builder formBuilder = new FormBody.Builder();\nif (requestNetwork.getParams().size() > 0) {\nHashMap<String, Object> params = requestNetwork.getParams();\n\nfor (HashMap.Entry<String, Object> param : params.entrySet()) {\nformBuilder.add(param.getKey(), String.valueOf(param.getValue()));\n}\n}\n\nRequestBody reqBody = formBuilder.build();\n\nreqBuilder.url(url).headers(headerBuilder.build()).method(method, reqBody);\n}\n} else {\nRequestBody reqBody = RequestBody.create(okhttp3.MediaType.parse(\"application/json\"), new Gson().toJson(requestNetwork.getParams()));\n\nif (method.equals(GET)) {\nreqBuilder.url(url).headers(headerBuilder.build()).get();\n} else {\nreqBuilder.url(url).headers(headerBuilder.build()).method(method, reqBody);\n}\n}\n\nRequest req = reqBuilder.build();\n\ngetClient().newCall(req).enqueue(new Callback() {\n@Override\npublic void onFailure(Call call, final IOException e) {\nrequestNetwork.getActivity().runOnUiThread(new Runnable() {\n@Override\npublic void run() {\nrequestListener.onErrorResponse(tag, e.getMessage());\n}\n});\n}\n\n@Override\npublic void onResponse(Call call, final Response response) throws IOException {\nfinal String responseBody = response.body().string().trim();\nrequestNetwork.getActivity().runOnUiThread(new Runnable() {\n@Override\npublic void run() {\nHeaders b = response.headers();\nHashMap<String, Object> map = new HashMap<>();\nfor(String s : b.names()) {\nmap.put(s, b.get(s) != null ? b.get(s) : \"null\");\n}\nrequestListener.onResponse(tag, responseBody, map);\n}\n});\n}\n});\n} catch (Exception e) {\nrequestListener.onErrorResponse(tag, e.getMessage());\n}\n}\n}";
    }

    public static String h(String var0) {
        return "package " +
                var0 +
                ";" +
                "\r\n" +
                "\nimport android.app.Activity;\n\nimport java.util.HashMap;\n\npublic class RequestNetwork {\nprivate HashMap<String, Object> params = new HashMap<>();\nprivate HashMap<String, Object> headers = new HashMap<>();\n\nprivate Activity activity;\n\nprivate int requestType = 0;\n\npublic RequestNetwork(Activity activity) {\nthis.activity = activity;\n}\n\npublic void setHeaders(HashMap<String, Object> headers) {\nthis.headers = headers;\n}\n\npublic void setParams(HashMap<String, Object> params, int requestType) {\nthis.params = params;\nthis.requestType = requestType;\n}\n\npublic HashMap<String, Object> getParams() {\nreturn params;\n}\n\npublic HashMap<String, Object> getHeaders() {\nreturn headers;\n}\n\npublic Activity getActivity() {\nreturn activity;\n}\n\npublic int getRequestType() {\nreturn requestType;\n}\n\npublic void startRequestNetwork(String method, String url, String tag, RequestListener requestListener) {\nRequestNetworkController.getInstance().execute(this, method, url, tag, requestListener);\n}\n\npublic interface RequestListener {\npublic void onResponse(String tag, String response, HashMap<String, Object> responseHeaders);\npublic void onErrorResponse(String tag, String message);\n}\n}\n";
    }

    public static String i(String var0) {
        return "package " +
                var0 +
                ";" +
                "\r\n" +
                "import android.app.*;\nimport android.content.*;\nimport android.graphics.drawable.*;\nimport android.net.*;\nimport android.util.*;\nimport android.view.*;\nimport android.view.inputmethod.*;\nimport android.widget.*;\n\nimport java.io.*;\nimport java.util.*;\n\npublic class SketchwareUtil {\n\n\tpublic static int TOP = 1;\n\tpublic static int CENTER = 2;\n\tpublic static int BOTTOM = 3;\n\n\tpublic static void CustomToast(Context _context, String _message, int _textColor, int _textSize, int _bgColor, int _radius, int _gravity) {\n\t\tToast _toast = Toast.makeText(_context, _message, Toast.LENGTH_SHORT);\n\t\tView _view = _toast.getView();\n\t\tTextView _textView = _view.findViewById(android.R.id.message);\n\t\t_textView.setTextSize(_textSize);\n\t\t_textView.setTextColor(_textColor);\n\t\t_textView.setGravity(Gravity.CENTER);\n\t\t\n\t\tGradientDrawable _gradientDrawable = new GradientDrawable();\n\t\t_gradientDrawable.setColor(_bgColor);\n\t\t_gradientDrawable.setCornerRadius(_radius);\n\t\t_view.setBackgroundDrawable(_gradientDrawable);\n\t\t_view.setPadding(15, 10, 15, 10);\n\t\t_view.setElevation(10);\n\n\t\tswitch (_gravity) {\n\t\t\tcase 1:\n\t\t\t\t_toast.setGravity(Gravity.TOP, 0, 150);\n\t\t\t\tbreak;\n\n\t\t\tcase 2:\n\t\t\t\t_toast.setGravity(Gravity.CENTER, 0, 0);\n\t\t\t\tbreak;\n\n\t\t\tcase 3:\n\t\t\t\t_toast.setGravity(Gravity.BOTTOM, 0, 150);\n\t\t\t\tbreak;\n\t\t}\n\t\t_toast.show();\n\t}\n\n\tpublic static void CustomToastWithIcon(Context _context, String _message, int _textColor, int _textSize, int _bgColor, int _radius, int _gravity, int _icon) {\n\t\tToast _toast = Toast.makeText(_context, _message, Toast.LENGTH_SHORT);\n\t\tView _view = _toast.getView();\n\t\tTextView _textView = (TextView) _view.findViewById(android.R.id.message);\n\t\t_textView.setTextSize(_textSize);\n\t\t_textView.setTextColor(_textColor);\n\t\t_textView.setCompoundDrawablesWithIntrinsicBounds(_icon, 0, 0, 0);\n\t\t_textView.setGravity(Gravity.CENTER);\n\t\t_textView.setCompoundDrawablePadding(10);\n\t\t\n\t\tGradientDrawable _gradientDrawable = new GradientDrawable();\n\t\t_gradientDrawable.setColor(_bgColor);\n\t\t_gradientDrawable.setCornerRadius(_radius);\n\t\t_view.setBackgroundDrawable(_gradientDrawable);\n\t\t_view.setPadding(10, 10, 10, 10);\n\t\t_view.setElevation(10);\n\n\t\tswitch (_gravity) {\n\t\t\tcase 1:\n\t\t\t\t_toast.setGravity(Gravity.TOP, 0, 150);\n\t\t\t\tbreak;\n\n\t\t\tcase 2:\n\t\t\t\t_toast.setGravity(Gravity.CENTER, 0, 0);\n\t\t\t\tbreak;\n\n\t\t\tcase 3:\n\t\t\t\t_toast.setGravity(Gravity.BOTTOM, 0, 150);\n\t\t\t\tbreak;\n\t\t}\n\t\t_toast.show();\n\t}\n\n\tpublic static void sortListMap(final ArrayList<HashMap<String, Object>> listMap, final String key, final boolean isNumber, final boolean ascending) {\n\t\tCollections.sort(listMap, new Comparator<HashMap<String, Object>>() {\n\t\t\tpublic int compare(HashMap<String, Object> _compareMap1, HashMap<String, Object> _compareMap2) {\n\t\t\t\tif (isNumber) {\n\t\t\t\t\tint _count1 = Integer.valueOf(_compareMap1.get(key).toString());\n\t\t\t\t\tint _count2 = Integer.valueOf(_compareMap2.get(key).toString());\n\t\t\t\t\tif (ascending) {\n\t\t\t\t\t\treturn _count1 < _count2 ? -1 : _count1 < _count2 ? 1 : 0;\n\t\t\t\t\t} else {\n\t\t\t\t\t\treturn _count1 > _count2 ? -1 : _count1 > _count2 ? 1 : 0;\n\t\t\t\t\t}\n\t\t\t\t} else {\n\t\t\t\t\tif (ascending) {\n\t\t\t\t\t\treturn (_compareMap1.get(key).toString()).compareTo(_compareMap2.get(key).toString());\n\t\t\t\t\t} else {\n\t\t\t\t\t\treturn (_compareMap2.get(key).toString()).compareTo(_compareMap1.get(key).toString());\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t}\n\t\t});\n\t}\n\n\tpublic static void CropImage(Activity _activity, String _path, int _requestCode) {\n\t\ttry {\n\t\t\tIntent _intent = new Intent(\"com.android.camera.action.CROP\");\n\t\t\tFile _file = new File(_path);\n\t\t\tUri _contentUri = Uri.fromFile(_file);\n\t\t\t_intent.setDataAndType(_contentUri, \"image/*\");\n\t\t\t_intent.putExtra(\"crop\", \"true\");\n\t\t\t_intent.putExtra(\"aspectX\", 1);\n\t\t\t_intent.putExtra(\"aspectY\", 1);\n\t\t\t_intent.putExtra(\"outputX\", 280);\n\t\t\t_intent.putExtra(\"outputY\", 280);\n\t\t\t_intent.putExtra(\"return-data\", false);\n\t\t\t_activity.startActivityForResult(_intent, _requestCode);\n\t\t} catch (ActivityNotFoundException _e) {\n\t\t\tToast.makeText(_activity, \"Your device doesn't support the crop action!\", Toast.LENGTH_SHORT).show();\n\t\t}\n\t}\n\n\tpublic static boolean isConnected(Context _context) {\n\t\tConnectivityManager _connectivityManager = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);\n\t\tNetworkInfo _activeNetworkInfo = _connectivityManager.getActiveNetworkInfo();\n\t\treturn _activeNetworkInfo != null && _activeNetworkInfo.isConnected();\n\t}\n\n\tpublic static String copyFromInputStream(InputStream _inputStream) {\n\t\tByteArrayOutputStream _outputStream = new ByteArrayOutputStream();\n\t\tbyte[] _buf = new byte[1024];\n\t\tint _i;\n\t\ttry {\n\t\t\twhile ((_i = _inputStream.read(_buf)) != -1){\n\t\t\t\t_outputStream.write(_buf, 0, _i);\n\t\t\t}\n\t\t\t_outputStream.close();\n\t\t\t_inputStream.close();\n\t\t} catch (IOException _e) {\n\t\t}\n\t\t\n\t\treturn _outputStream.toString();\n\t}\n\n\tpublic static void hideKeyboard(Context _context) {\n\t\tInputMethodManager _inputMethodManager = (InputMethodManager) _context.getSystemService(Context.INPUT_METHOD_SERVICE);\n\t\t_inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);\n\t}\n\t\n\tpublic static void showKeyboard(Context _context) {\n\t\tInputMethodManager _inputMethodManager = (InputMethodManager) _context.getSystemService(Context.INPUT_METHOD_SERVICE);\n\t\t_inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);\n\t}\n\t\n\tpublic static void showMessage(Context _context, String _s) {\n\t\tToast.makeText(_context, _s, Toast.LENGTH_SHORT).show();\n\t}\n\n\tpublic static int getLocationX(View _view) {\n\t\tint _location[] = new int[2];\n\t\t_view.getLocationInWindow(_location);\n\t\treturn _location[0];\n\t}\n\n\tpublic static int getLocationY(View _view) {\n\t\tint _location[] = new int[2];\n\t\t_view.getLocationInWindow(_location);\n\t\treturn _location[1];\n\t}\n\n\tpublic static int getRandom(int _min, int _max) {\n\t\tRandom random = new Random();\n\t\treturn random.nextInt(_max - _min + 1) + _min;\n\t}\n\n\tpublic static ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {\n\t\tArrayList<Double> _result = new ArrayList<Double>();\n\t\tSparseBooleanArray _arr = _list.getCheckedItemPositions();\n\t\tfor (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {\n\t\t\tif (_arr.valueAt(_iIdx))\n\t\t\t\t_result.add((double) _arr.keyAt(_iIdx));\n\t\t}\n\t\treturn _result;\n\t}\n\n\tpublic static float getDip(Context _context, int _input) {\n\t\treturn TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, _context.getResources().getDisplayMetrics());\n\t}\n\n\tpublic static int getDisplayWidthPixels(Context _context) {\n\t\treturn _context.getResources().getDisplayMetrics().widthPixels;\n\t}\n\n\tpublic static int getDisplayHeightPixels(Context _context) {\n\t\treturn _context.getResources().getDisplayMetrics().heightPixels;\n\t}\n\n\tpublic static void getAllKeysFromMap(Map<String, Object> _map, ArrayList<String> _output) {\n\t\tif (_output == null) return;\n\t\t_output.clear();\n\t\tif (_map == null || _map.size() < 1) return;\n\t\tIterator _itr = _map.entrySet().iterator();\n\t\twhile (_itr.hasNext()) {\n\t\t\tMap.Entry<String, String> _entry = (Map.Entry) _itr.next();\n\t\t\t_output.add(_entry.getKey());\n\t\t}\n\t}\n}\n";
    }

    public static String j(String var0) {
        StringBuilder var1 = new StringBuilder(4096);
        char[] var15 = var0.toCharArray();
        int var2 = var15.length;
        int var3 = 0;
        boolean var4 = false;
        boolean var5 = false;
        boolean var6 = false;
        int var7 = 0;
        boolean var8 = false;

        int var20;
        for(boolean var9 = false; var3 < var2; var7 = var20) {
            boolean var17;
            boolean var18;
            int var19;
            label81: {
                char var10 = var15[var3];
                boolean var11;
                int var16;
                if (var4) {
                    if (var10 == '\n') {
                        var1.append(var10);
                        a(var1, var7);
                        var11 = false;
                        var16 = var3;
                    } else {
                        var1.append(var10);
                        var11 = true;
                        var16 = var3;
                    }
                } else {
                    char var14;
                    if (var5) {
                        label78: {
                            if (var10 == '*') {
                                var20 = var3 + 1;
                                var14 = var15[var20];
                                if (var14 == '/') {
                                    var1.append(var10);
                                    var1.append(var14);
                                    var5 = false;
                                    var11 = false;
                                    var16 = var20;
                                    break label78;
                                }
                            }

                            var1.append(var10);
                            var11 = false;
                            var16 = var3;
                        }
                    } else if (var6) {
                        var1.append(var10);
                        var6 = false;
                        var11 = false;
                        var16 = var3;
                    } else if (var10 == '\\') {
                        var1.append(var10);
                        var6 = true;
                        var11 = false;
                        var16 = var3;
                    } else if (var8) {
                        if (var10 == '\'') {
                            var1.append(var10);
                            var8 = false;
                            var11 = false;
                            var16 = var3;
                        } else {
                            var1.append(var10);
                            var11 = false;
                            var16 = var3;
                        }
                    } else if (var9) {
                        if (var10 == '"') {
                            var1.append(var10);
                            var9 = false;
                            var11 = false;
                            var16 = var3;
                        } else {
                            var1.append(var10);
                            var11 = false;
                            var16 = var3;
                        }
                    } else {
                        label87: {
                            if (var10 == '/') {
                                var20 = var3 + 1;
                                var14 = var15[var20];
                                if (var14 == '/') {
                                    var1.append(var10);
                                    var1.append(var14);
                                    var16 = var20;
                                    var11 = true;
                                    break label87;
                                }

                                if (var14 == '*') {
                                    var1.append(var10);
                                    var1.append(var14);
                                    var5 = true;
                                    var11 = false;
                                    var16 = var20;
                                    break label87;
                                }
                            }

                            if (var10 != '\n') {
                                if (var10 == '\'') {
                                    var8 = true;
                                }

                                if (var10 == '"') {
                                    var9 = true;
                                }

                                if (var10 == '{') {
                                    var20 = var7 + 1;
                                } else {
                                    var20 = var7;
                                }

                                var7 = var20;
                                if (var10 == '}') {
                                    --var20;
                                    var7 = var20;
                                    if (var1.charAt(var1.length() - 1) == '\t') {
                                        var1.deleteCharAt(var1.length() - 1);
                                        var7 = var20;
                                    }
                                }

                                var1.append(var10);
                                var20 = var3;
                                var5 = var8;
                                var3 = var7;
                                var18 = false;
                                var6 = false;
                                var19 = var20;
                                break label81;
                            }

                            var1.append(var10);
                            a(var1, var7);
                            var11 = false;
                            var16 = var3;
                        }
                    }
                }

                var17 = var5;
                boolean var13 = var6;
                var5 = var8;
                var19 = var16;
                var4 = var11;
                var6 = var17;
                var18 = var13;
                var3 = var7;
            }

            var20 = var3;
            var17 = var18;
            var18 = var6;
            int var21 = var19 + 1;
            var8 = var5;
            var6 = var17;
            var5 = var18;
            var3 = var21;
        }

        return var1.toString();
    }

    public static String pagerAdapter(String var0, String var1, ArrayList<ViewBean> var2, String var3) {
        String var4 = a(var0);
        Iterator<ViewBean> var7 = var2.iterator();

        StringBuilder var6;
        for(var0 = ""; var7.hasNext(); var0 = var6.toString()) {
            ViewBean var5 = var7.next();
            var6 = new StringBuilder();
            var6.append(var0);
            var6.append(a(var5));
            var6.append("\r\n");
        }

        return "public class " +
                var4 +
                " extends PagerAdapter {" +
                "\r\n" +
                "Context _context;" +
                "\r\n" +
                "ArrayList<HashMap<String, Object>> _data;" +
                "\r\n" +
                "public " +
                var4 +
                "(Context _ctx, ArrayList<HashMap<String, Object>> _arr) {" +
                "\r\n" +
                "_context = _ctx;" +
                "\r\n" +
                "_data = _arr;" +
                "\r\n" +
                "}" +
                "\r\n" +
                "\r\n" +
                "public " +
                var4 +
                "(ArrayList<HashMap<String, Object>> _arr) {" +
                "\r\n" +
                "_context = getApplicationContext();" +
                "\r\n" +
                "_data = _arr;" +
                "\r\n" +
                "}" +
                "\r\n" +
                "\r\n" +
                "@Override" +
                "\r\n" +
                "public int getCount() {" +
                "\r\n" +
                "return _data.size();" +
                "\r\n" +
                "}" +
                "\r\n" +
                "\r\n" +
                "@Override" +
                "\r\n" +
                "public boolean isViewFromObject(View _view, Object _object) {" +
                "\r\n" +
                "return _view == _object;" +
                "\r\n" +
                "}" +
                "\r\n" +
                "\r\n" +
                "@Override" +
                "\r\n" +
                "public void destroyItem(ViewGroup _container, int _position, Object _object) {" +
                "\r\n" +
                "_container.removeView((View) _object);" +
                "\r\n" +
                "}" +
                "\r\n" +
                "\r\n" +
                "@Override" +
                "\r\n" +
                "public int getItemPosition(Object _object) {" +
                "\r\n" +
                "return super.getItemPosition(_object);" +
                "\r\n" +
                "}" +
                "\r\n" +
                "\r\n" +
                "@Override" +
                "\r\n" +
                "public CharSequence getPageTitle(int pos) {" +
                "\r\n" +
                "return onTabLayoutNewTabAdded(pos);" +
                "\r\n" +
                "}" +
                "\r\n" +
                "\r\n" +
                "@Override" +
                "\r\n" +
                "public Object instantiateItem(ViewGroup _container,  final int _position) {" +
                "\r\n" +
                "View _view = LayoutInflater.from(_context).inflate(R.layout." +
                var1 +
                ", _container, false);" +
                "\r\n" +
                "\r\n" +
                var0 +
                "\r\n" +
                var3 +
                "\r\n" +
                "\r\n" +
                "_container.addView(_view);" +
                "\r\n" +
                "return _view;" +
                "\r\n" +
                "}" +
                "\r\n" +
                "}";
    }

    public static String recyclerViewAdapter(String var0, String var1, ArrayList<?> var2, String var3) {
        String var4 = a(var0);
        Iterator<?> var7 = var2.iterator();

        StringBuilder var6;
        for(var0 = ""; var7.hasNext(); var0 = var6.toString()) {
            ViewBean var5 = (ViewBean)var7.next();
            var6 = new StringBuilder();
            var6.append(var0);
            var6.append(a(var5));
            var6.append("\r\n");
        }

        return "public class " +
                var4 +
                " extends RecyclerView.Adapter<" +
                var4 +
                ".ViewHolder>" +
                " {" +
                "\r\n" +
                "ArrayList<HashMap<String, Object>> _data;" +
                "\r\n" +
                "public " +
                var4 +
                "(ArrayList<HashMap<String, Object>> _arr) {" +
                "\r\n" +
                "_data = _arr;" +
                "\r\n" +
                "}" +
                "\r\n" +
                "\r\n" +
                "@Override" +
                "\r\n" +
                "public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {" +
                "\r\n" +
                "LayoutInflater _inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);" +
                "\r\n" +
                "View _v = _inflater.inflate(R.layout." +
                var1 +
                ", null);" +
                "\r\n" +
                "RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);" +
                "\r\n" +
                "_v.setLayoutParams(_lp);" +
                "\r\n" +
                "return new ViewHolder(_v);" +
                "\r\n" +
                "}" +
                "\r\n" +
                "\r\n" +
                "@Override" +
                "\r\n" +
                "public void onBindViewHolder(ViewHolder _holder, final int _position) {" +
                "\r\n" +
                "View _view = _holder.itemView;" +
                "\r\n" +
                "\r\n" +
                var0 +
                "\r\n" +
                var3 +
                "\r\n" +
                "}" +
                "\r\n" +
                "\r\n" +
                "@Override" +
                "\r\n" +
                "public int getItemCount() {" +
                "\r\n" +
                "return _data.size();" +
                "\r\n" +
                "}" +
                "\r\n" +
                "\r\n" +
                "public class ViewHolder extends RecyclerView.ViewHolder {" +
                "\r\n" +
                "public ViewHolder(View v) {" +
                "\r\n" +
                "super(v);" +
                "\r\n" +
                "}" +
                "\r\n" +
                "}" +
                "\r\n" +
                "\r\n" +
                "}";
    }
}
