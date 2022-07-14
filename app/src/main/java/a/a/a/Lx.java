package a.a.a;

import android.text.TextUtils;

import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.beans.ViewBean;

import java.util.ArrayList;
import java.util.Iterator;

import mod.agus.jcoderz.editor.event.ManageEvent;
import mod.hey.studios.build.BuildSettings;
import mod.hey.studios.moreblock.ReturnMoreblockManager;
import mod.hilal.saif.components.ComponentsHandler;

public class Lx {

    /**
     * @return Content of a <code>settings.gradle</code> file, with indentation
     */
    public static String a() {
        return "include ':app'\r\n";
    }

    /**
     * @return Content of a <code>build.gradle</code> file for the module ':app', with indentation
     */
    public static String a(int compileSdkVersion, int minSdkVersion, int targetSdkVersion, jq metadata) {
        String content = "plugins {\r\n" +
                "id 'com.android.application'\r\n" +
                "}\r\n" +
                "\r\n" +
                "android {\r\n" +
                "compileSdkVersion " + compileSdkVersion + "\r\n" +
                "\r\n";
        if (new BuildSettings(metadata.sc_id)
                .getValue(BuildSettings.SETTING_NO_HTTP_LEGACY, BuildSettings.SETTING_GENERIC_VALUE_FALSE)
                .equals(BuildSettings.SETTING_GENERIC_VALUE_FALSE)) {
            content += "useLibrary 'org.apache.http.legacy'\r\n" +
                    "\r\n";
        }
        content += "defaultConfig {\r\n" +
                "applicationId \"" + metadata.packageName + "\"\r\n" +
                "minSdkVersion " + minSdkVersion + "\r\n" +
                "targetSdkVersion " + targetSdkVersion + "\r\n" +
                "versionCode " + metadata.versionCode + "\r\n" +
                "versionName \"" + metadata.versionName + "\"\r\n" +
                "}\r\n" +
                "\r\n" +
                "buildTypes {\r\n" +
                "release {\r\n" +
                "minifyEnabled false\r\n" +
                "proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'\r\n" +
                "}\r\n" +
                "}\r\n" +
                "}\r\n" +
                "\r\n" +
                "dependencies {\r\n" +
                "implementation fileTree(dir: 'libs', include: ['*.jar'])\r\n";

        if (metadata.g) {
            content += "implementation 'androidx.appcompat:appcompat:1.2.0'\r\n" +
                    "implementation 'com.google.android.material:material:1.4.0'\r\n";
        }

        if (metadata.isFirebaseAuthUsed) {
            content += "implementation 'com.google.firebase:firebase-auth:19.0.0'\r\n";
        }

        if (metadata.isFirebaseDatabaseUsed) {
            content += "implementation 'com.google.firebase:firebase-database:19.0.0'\r\n";
        }

        if (metadata.isFirebaseStorageUsed) {
            content += "implementation 'com.google.firebase:firebase-storage:19.0.0'\r\n";
        }

        if (metadata.isAdMobEnabled) {
            content += "implementation 'com.google.android.gms:play-services-ads:20.1.0'\r\n";
        }

        if (metadata.isMapUsed) {
            content += "implementation 'com.google.android.gms:play-services-maps:17.0.1'\r\n";
        }

        if (metadata.isGlideUsed) {
            content += "implementation 'com.github.bumptech.glide:glide:4.12.0'\r\n";
        }

        if (metadata.isGsonUsed) {
            content += "implementation 'com.google.code.gson:gson:2.8.7'\r\n";
        }

        if (metadata.isHttp3Used) {
            content += "implementation 'com.squareup.okhttp3:okhttp:3.9.1'\r\n";
        }

        return j(content + "}\r\n");
    }

    /**
     * @return Code to be added to <code>onActivityResult</code> for a component
     */
    public static String a(int componentId, String componentName, String onSuccessLogic, String onCancelledLogic) {
        String componentLogic;
        switch (componentId) {
            case ComponentBean.COMPONENT_TYPE_FILE_PICKER:
                componentLogic = "ArrayList<String> _filePath = new ArrayList<>();\r\n" +
                        "if (_data != null) {\r\n" +
                        "if (_data.getClipData() != null) {\r\n" +
                        "for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {\r\n" +
                        "ClipData.Item _item = _data.getClipData().getItemAt(_index);\r\n" +
                        "_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));\r\n" +
                        "}\r\n" +
                        "}\r\n" +
                        "else {\r\n" +
                        "_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));\r\n" +
                        "}\r\n" +
                        "}";
                break;

            case ComponentBean.COMPONENT_TYPE_CAMERA:
                componentLogic = " String _filePath = _file_" + componentName + ".getAbsolutePath();\r\n";
                break;

            case ComponentBean.COMPONENT_TYPE_FIREBASE_AUTH_GOOGLE_LOGIN:
                componentLogic = "Task<GoogleSignInAccount> _task = GoogleSignIn.getSignedInAccountFromIntent(_data);\r\n";
                break;

            case 35:
                componentLogic = "String _filePath = file_" + componentName + ".getAbsolutePath();\r\n";
                break;

            default:
                componentLogic = "";
                break;
        }

        return "case REQ_CD_" + componentName.toUpperCase() + ":\r\n" +
                "if (_resultCode == Activity.RESULT_OK) {\r\n" +
                componentLogic + "\r\n" +
                onSuccessLogic + "\r\n" +
                "}\r\n" +
                "else {\r\n" +
                onCancelledLogic + "\r\n" +
                "}\r\n" +
                "break;";
    }

    /**
     * @return Code to initialize a widget
     */
    public static String a(ViewBean bean) {
        String type;
        if (!bean.convert.isEmpty()) {
            type = bean.convert;
        } else {
            type = bean.getClassInfo().a();
        }

        return "final " + type + " " + bean.id + " = _view.findViewById(R.id." + bean.id + ");";
    }

    /**
     * @param widgetName The list widget's name
     * @return The adapter's class name (e.g. List_filesAdapter from list_files)
     */
    public static String a(String widgetName) {
        return widgetName.substring(0, 1).toUpperCase() +
                widgetName.substring(1) +
                "Adapter";
    }

    /**
     * @return A component request code constant declaration
     */
    public static String a(String componentName, int value) {
        return "public final int REQ_CD_" + componentName.toUpperCase() + " = " + value + ";";
    }

    public static String getEventCode(String targetId, String eventName, String eventLogic) {
        switch (eventName) {
            case "onClick":
                return "@Override\r\n" +
                        "public void onClick(View _view) {\r\n"
                        + eventLogic + "\r\n" +
                        "}";

            case "onBackPressed":
                return "@Override\r\n" +
                        "public void onBackPressed() {\r\n"
                        + eventLogic + "\r\n" +
                        "}";

            case "onPostCreate":
                return "@Override\r\n" +
                        "protected void onPostCreate(Bundle _savedInstanceState) {\r\n" +
                        "super.onPostCreate(_savedInstanceState);\r\n"
                        + eventLogic + "\r\n" +
                        "}";

            case "onStart":
                return "@Override\r\n" +
                        "public void onStart() {\r\n" +
                        "super.onStart();\r\n"
                        + eventLogic + "\r\n" +
                        "}";

            case "onStop":
                return "@Override\r\n" +
                        "public void onStop() {\r\n" +
                        "super.onStop();\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onDestroy":
                return "@Override\r\n" +
                        "public void onDestroy() {\r\n" +
                        "super.onDestroy();\r\n"
                        + eventLogic + "\r\n" +
                        "}";

            case "onResume":
                return "@Override\r\n" +
                        "public void onResume() {\r\n" +
                        "super.onResume();\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onPause":
                return "@Override\r\n" +
                        "public void onPause() {\r\n" +
                        "super.onPause();\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onCheckedChange":
                return "@Override\r\n" +
                        "public void onCheckedChanged(CompoundButton _param1, boolean _param2) {\r\n" +
                        "final boolean _isChecked = _param2;\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onItemSelected":
                return "@Override\r\n" +
                        "public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {\r\n" +
                        "final int _position = _param3;\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onNothingSelected":
                return "@Override\r\n" +
                        "public void onNothingSelected(AdapterView<?> _param1) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onItemClicked":
                return "@Override\r\n" +
                        "public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {\r\n" +
                        "final int _position = _param3;\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onItemLongClicked":
                return "@Override\r\n" +
                        "public boolean onItemLongClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {\r\n" +
                        "final int _position = _param3;\r\n" +
                        eventLogic + "\r\n" +
                        "return true;\r\n" +
                        "}";

            case "beforeTextChanged":
                return "@Override\r\n" +
                        "public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "afterTextChanged":
                return "@Override\r\n" +
                        "public void afterTextChanged(Editable _param1) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onTextChanged":
                return "@Override\r\n" +
                        "public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {\r\n" +
                        "final String _charSeq = _param1.toString();\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onProgressChanged":
                return "@Override\r\n" +
                        "public void onProgressChanged(SeekBar _param1, int _param2, boolean _param3) {\r\n" +
                        "final int _progressValue = _param2;\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onStartTrackingTouch":
                return "@Override\r\n" +
                        "public void onStartTrackingTouch(SeekBar _param1) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onStopTrackingTouch":
                // Why's the parameter named <code>_param2</code> even if it's the first parameter
                return "@Override\r\n" +
                        "public void onStopTrackingTouch(SeekBar _param2) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onDateChange":
                return "@Override\r\n" +
                        "public void onSelectedDayChange(CalendarView _param1, int _param2, int _param3, int _param4) {\r\n" +
                        "final int _year = _param2;\r\n" +
                        "final int _month = _param3;\r\n" +
                        "final int _day = _param4;\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onPageStarted":
                return "@Override\r\n" +
                        "public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {\r\n" +
                        "final String _url = _param2;\r\n" +
                        eventLogic + "\r\n" +
                        "super.onPageStarted(_param1, _param2, _param3);\r\n" +
                        "}";

            case "onPageFinished":
                return "@Override\r\n" +
                        "public void onPageFinished(WebView _param1, String _param2) {\r\n" +
                        "final String _url = _param2;\r\n" +
                        eventLogic + "\r\n" +
                        "super.onPageFinished(_param1, _param2);\r\n" +
                        "}";

            case "onAnimationStart":
                return "@Override\r\n" +
                        "public void onAnimationStart(Animator _param1) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onAnimationCancel":
                return "@Override\r\n" +
                        "public void onAnimationCancel(Animator _param1) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onAnimationEnd":
                return "@Override\r\n" +
                        "public void onAnimationEnd(Animator _param1) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onAnimationRepeat":
                return "@Override\r\n" +
                        "public void onAnimationRepeat(Animator _param1) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onChildAdded":
                return "@Override\r\n" +
                        "public void onChildAdded(DataSnapshot _param1, String _param2) {\r\n" +
                        "GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};\r\n" +
                        "final String _childKey = _param1.getKey();\r\n" +
                        "final HashMap<String, Object> _childValue = _param1.getValue(_ind);\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onChildChanged":
                return "@Override\r\n" +
                        "public void onChildChanged(DataSnapshot _param1, String _param2) {\r\n" +
                        "GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};\r\n" +
                        "final String _childKey = _param1.getKey();\r\n" +
                        "final HashMap<String, Object> _childValue = _param1.getValue(_ind);\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onChildRemoved":
                return "@Override\r\n" +
                        "public void onChildRemoved(DataSnapshot _param1) {\r\n" +
                        "GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};\r\n" +
                        "final String _childKey = _param1.getKey();\r\n" +
                        "final HashMap<String, Object> _childValue = _param1.getValue(_ind);\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onChildMoved":
                return "@Override\r\n" +
                        "public void onChildMoved(DataSnapshot _param1, String _param2) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onCancelled":
                return "@Override\r\n" +
                        "public void onCancelled(DatabaseError _param1) {\r\n" +
                        "final int _errorCode = _param1.getCode();\r\n" +
                        "final String _errorMessage = _param1.getMessage();\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onSensorChanged":
                return "@Override\r\n" +
                        "public void onSensorChanged(SensorEvent _param1) {\r\n" +
                        "float[] _rotationMatrix = new float[16];\r\n" +
                        "SensorManager.getRotationMatrixFromVector(_rotationMatrix, _param1.values);\r\n" +
                        "float[] _remappedRotationMatrix = new float[16];\r\n" +
                        "SensorManager.remapCoordinateSystem(_rotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Z, _remappedRotationMatrix);\r\n" +
                        "float[] _orientations = new float[3];\r\n" +
                        "SensorManager.getOrientation(_remappedRotationMatrix, _orientations);\r\n" +
                        "for (int _i = 0; _i < 3; _i++) {\r\n" +
                        "_orientations[_i] = (float)(Math.toDegrees(_orientations[_i]));\r\n" +
                        "}\r\n" +
                        "final double _x = _orientations[0];\r\n" +
                        "final double _y = _orientations[1];\r\n" +
                        "final double _z = _orientations[2];\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onAccuracyChanged":
                return "@Override\r\n" +
                        "public void onAccuracyChanged(Sensor _param1, int _param2) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onCreateUserComplete":
            case "onSignInUserComplete":
                return "@Override\r\n" +
                        "public void onComplete(Task<AuthResult> _param1) {\r\n" +
                        "final boolean _success = _param1.isSuccessful();\r\n" +
                        "final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : \"\";\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onResetPasswordEmailSent":
                return "@Override\r\n" +
                        "public void onComplete(Task<Void> _param1) {\r\n" +
                        "final boolean _success = _param1.isSuccessful();\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onUploadProgress":
                return "@Override\r\n" +
                        "public void onProgress(UploadTask.TaskSnapshot _param1) {\r\n" +
                        "double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onDownloadProgress":
                return "@Override\r\n" +
                        "public void onProgress(FileDownloadTask.TaskSnapshot _param1) {\r\n" +
                        "double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onUploadSuccess":
                return "@Override\r\n" +
                        "public void onComplete(Task<Uri> _param1) {\r\n" +
                        "final String _downloadUrl = _param1.getResult().toString();\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onDownloadSuccess":
                return "@Override\r\n" +
                        "public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {\r\n" +
                        "final long _totalByteCount = _param1.getTotalByteCount();\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onDeleteSuccess":
                return "@Override\r\n" +
                        "public void onSuccess(Object _param1) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onFailure":
                return "@Override\r\n" +
                        "public void onFailure(Exception _param1) {\r\n" +
                        "final String _message = _param1.getMessage();\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onResponse":
                return "@Override\r\n" +
                        "public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {\r\n" +
                        "final String _tag = _param1;\r\n" +
                        "final String _response = _param2;\r\n" +
                        "final HashMap<String, Object> _responseHeaders = _param3;\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onErrorResponse":
                return "@Override\r\n" +
                        "public void onErrorResponse(String _param1, String _param2) {\r\n" +
                        "final String _tag = _param1;\r\n" +
                        "final String _message = _param2;\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onSpeechResult":
                return "@Override\r\n" +
                        "public void onResults(Bundle _param1) {\r\n" +
                        "final ArrayList<String> _results = _param1.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);\r\n" +
                        "final String _result = _results.get(0);\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onSpeechError":
                return "@Override\r\n" +
                        "public void onError(int _param1) {\r\n" +
                        "final String _errorMessage;\r\n" +
                        "switch (_param1) {\r\n" +
                        "case SpeechRecognizer.ERROR_AUDIO:\r\n" +
                        "_errorMessage = \"audio error\";\r\n" +
                        "break;\r\n" +
                        "\r\n" +
                        "case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:\r\n" +
                        "_errorMessage = \"speech timeout\";\r\n" +
                        "break;\r\n" +
                        "\r\n" +
                        "case SpeechRecognizer.ERROR_NO_MATCH:\r\n" +
                        "_errorMessage = \"speech no match\";\r\n" +
                        "break;\r\n" +
                        "\r\n" +
                        "case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:\r\n" +
                        "_errorMessage = \"recognizer busy\";\r\n" +
                        "break;\r\n" +
                        "\r\n" +
                        "case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:\r\n" +
                        "_errorMessage = \"recognizer insufficient permissions\";\r\n" +
                        "break;\r\n" +
                        "\r\n" +
                        "default:\r\n" +
                        "_errorMessage = \"recognizer other error\";\r\n" +
                        "break;\r\n" +
                        "}\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onConnected":
                return "@Override\r\n" +
                        "public void onConnected(String _param1, HashMap<String, Object> _param2) {\r\n" +
                        "final String _tag = _param1;\r\n" +
                        "final HashMap<String, Object> _deviceData = _param2;\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onDataReceived":
                return "@Override\r\n" +
                        "public void onDataReceived(String _param1, byte[] _param2, int _param3) {\r\n" +
                        "final String _tag = _param1;\r\n" +
                        "final String _data = new String(_param2, 0, _param3);\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onDataSent":
                return "@Override\r\n" +
                        "public void onDataSent(String _param1, byte[] _param2) {\r\n" +
                        "final String _tag = _param1;\r\n" +
                        "final String _data = new String(_param2);\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onConnectionError":
                return "@Override\r\n" +
                        "public void onConnectionError(String _param1, String _param2, String _param3) {\r\n" +
                        "final String _tag = _param1;\r\n" +
                        "final String _connectionState = _param2;\r\n" +
                        "final String _errorMessage = _param3;\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onConnectionStopped":
                return "@Override\r\n" +
                        "public void onConnectionStopped(String _param1) {\r\n" +
                        "final String _tag = _param1;\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onMapReady":
                return eventLogic;

            case "onMarkerClicked":
                return "@Override\r\n" +
                        "public boolean onMarkerClick(Marker _param1) {\r\n" +
                        "final String _id = _param1.getTag().toString();\r\n" +
                        eventLogic + "\r\n" +
                        "return false;\r\n" +
                        "}";

            case "onLocationChanged":
                return "@Override\r\n" +
                        "public void onLocationChanged(Location _param1) {\r\n" +
                        "final double _lat = _param1.getLatitude();\r\n" +
                        "final double _lng = _param1.getLongitude();\r\n" +
                        "final double _acc = _param1.getAccuracy();\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            default:
                return ManageEvent.f(targetId, eventName, eventLogic);
        }
    }

    /**
     * @return One or more lines which declare a Type's needed fields.
     * Example for a Camera Component:
     * <pre>
     * private File _file_&lt;component name&gt;;
     * </pre>
     */
    public static String a(String typeName, String typeInstanceName, AccessModifier accessModifier, String... parameters) {
        String fieldDeclaration = accessModifier.getName();

        if (typeName.equals("include") || typeName.equals("#")) {
            fieldDeclaration = "";
        } else {
            String initializer = getInitializer(typeName, typeInstanceName, parameters);
            String builtInType = mq.e(typeName);
            if (initializer.length() <= 0) {
                if (!(builtInType.equals("") || builtInType.equals("RewardedVideoAd") || builtInType.equals("FirebaseCloudMessage") || builtInType.equals("FragmentStatePagerAdapter"))) {
                    fieldDeclaration += " " + builtInType + " " + typeInstanceName + ";";
                } else {
                    switch (typeName) {
                        case "FirebaseCloudMessage":
                            fieldDeclaration = "";
                            break;
                        case "FragmentStatePagerAdapter":
                            fieldDeclaration += " " + a(typeInstanceName + "Fragment") + " " + typeInstanceName + ";";
                            break;
                        case "RewardedVideoAd":
                            fieldDeclaration += " RewardedAd " + typeInstanceName + ";";
                            break;
                        default:
                            fieldDeclaration += " " + typeName + " " + typeInstanceName + ";";
                            break;
                    }
                }
            } else {
                String typeNameOfField = builtInType;

                if (builtInType.equals("") && "Videos".equals(typeName)) {
                    typeNameOfField = "Intent";
                }

                fieldDeclaration += " " + typeNameOfField + " " + typeInstanceName + " = " + initializer + ";";
            }

            switch (typeName) {
                case "FirebaseDB":
                    fieldDeclaration += "\r\nprivate ChildEventListener _" + typeInstanceName + "_child_listener;";
                    break;

                case "Gyroscope":
                    fieldDeclaration += "\r\nprivate SensorEventListener _" + typeInstanceName + "_sensor_listener;";
                    break;

                case "FirebaseAuth":
                    fieldDeclaration += "\r\nprivate OnCompleteListener<AuthResult> _" + typeInstanceName + "_create_user_listener;\r\n" +
                            "private OnCompleteListener<AuthResult> _" + typeInstanceName + "_sign_in_listener;\r\n" +
                            "private OnCompleteListener<Void> _" + typeInstanceName + "_reset_password_listener;\r\n" +
                            // Fields/Events added by Agus
                            "private OnCompleteListener<Void> " + typeInstanceName + "_updateEmailListener;\r\n" +
                            "private OnCompleteListener<Void> " + typeInstanceName + "_updatePasswordListener;\r\n" +
                            "private OnCompleteListener<Void> " + typeInstanceName + "_emailVerificationSentListener;\r\n" +
                            "private OnCompleteListener<Void> " + typeInstanceName + "_deleteUserListener;\r\n" +
                            "private OnCompleteListener<Void> " + typeInstanceName + "_updateProfileListener;\r\n" +
                            "private OnCompleteListener<AuthResult> " + typeInstanceName + "_phoneAuthListener;\r\n" +
                            "private OnCompleteListener<AuthResult> " + typeInstanceName + "_googleSignInListener;\r\n";
                    break;

                case "InterstitialAd":
                    fieldDeclaration += "\r\nprivate InterstitialAdLoadCallback _" + typeInstanceName + "_interstitial_ad_load_callback;";
                    fieldDeclaration += "\r\nprivate FullScreenContentCallback _" + typeInstanceName + "_full_screen_content_callback;";
                    break;

                case "RewardedVideoAd":
                    fieldDeclaration += "\r\nprivate OnUserEarnedRewardListener _" + typeInstanceName + "_on_user_earned_reward_listener;";
                    fieldDeclaration += "\r\nprivate RewardedAdLoadCallback _" + typeInstanceName + "_rewarded_ad_load_callback;";
                    fieldDeclaration += "\r\nprivate FullScreenContentCallback _" + typeInstanceName + "_full_screen_content_callback;";
                    break;

                case "FirebaseStorage":
                    fieldDeclaration += "\r\nprivate OnCompleteListener<Uri> _" + typeInstanceName + "_upload_success_listener;\r\n" +
                            "private OnSuccessListener<FileDownloadTask.TaskSnapshot> _" + typeInstanceName + "_download_success_listener;\r\n" +
                            "private OnSuccessListener _" + typeInstanceName + "_delete_success_listener;\r\n" +
                            "private OnProgressListener _" + typeInstanceName + "_upload_progress_listener;\r\n" +
                            "private OnProgressListener _" + typeInstanceName + "_download_progress_listener;\r\n" +
                            "private OnFailureListener _" + typeInstanceName + "_failure_listener;\r\n";
                    break;

                case "Camera":
                    fieldDeclaration += "\r\nprivate File _file_" + typeInstanceName + ";";
                    break;

                case "RequestNetwork":
                    fieldDeclaration += "\r\nprivate RequestNetwork.RequestListener _" + typeInstanceName + "_request_listener;";
                    break;

                case "BluetoothConnect":
                    fieldDeclaration += "\r\nprivate BluetoothConnect.BluetoothConnectionListener _" + typeInstanceName + "_bluetooth_connection_listener;";
                    break;

                case "LocationManager":
                    fieldDeclaration += "\r\nprivate LocationListener _" + typeInstanceName + "_location_listener;";
                    break;

                case "MapView":
                    fieldDeclaration += "\r\nprivate GoogleMapController _" + typeInstanceName + "_controller;";
                    break;

                case "Videos":
                    fieldDeclaration += "\r\nprivate File file_" + typeInstanceName + ";";
                    break;

                case "FirebaseCloudMessage":
                    fieldDeclaration += "\r\nprivate OnCompleteListener " + typeInstanceName + "_onCompleteListener;";
                    break;

                case "com.facebook.ads.InterstitialAd":
                    fieldDeclaration += "\r\nprivate InterstitialAdListener " + typeInstanceName + "_InterstitialAdListener;";
                    break;

                case "PhoneAuthProvider.OnVerificationStateChangedCallbacks":
                    fieldDeclaration += "private PhoneAuthProvider.ForceResendingToken " + typeInstanceName + "_resendToken;";
                    break;

                case "DynamicLink":
                    fieldDeclaration += "\r\nprivate OnSuccessListener " + typeInstanceName + "_onSuccessLink;"
                            + "\r\nprivate OnFailureListener " + typeInstanceName + "_onFailureLink;";
                    break;

                case "com.facebook.ads.AdView":
                    fieldDeclaration += "\r\nprivate AdListener " + typeInstanceName + "_AdListener;";
                    break;

                case "TimePickerDialog":
                    fieldDeclaration += "\r\nprivate TimePickerDialog.OnTimeSetListener " + typeInstanceName + "_listener;";
                    break;

                default:
                    fieldDeclaration = ComponentsHandler.extraVar(typeName, fieldDeclaration, typeInstanceName);
                    break;
            }
        }

        return fieldDeclaration;
    }

    /**
     * @return Code of a More Block
     */
    public static String getMoreBlockCode(String moreBlockName, String moreBlockSpec, String moreBlockLogic) {
        String code = "public " + ReturnMoreblockManager.getMbTypeCode(moreBlockName) + " _" +
                ReturnMoreblockManager.getMbName(moreBlockName) + "(";
        ArrayList<String> parameterSpecs = FB.c(moreBlockSpec);
        boolean isFirstParameter = true;

        processingParameters:
        for (String parameterSpec : parameterSpecs) {
            // Avoid label spec parts
            if (parameterSpec.charAt(0) == '%') {
                char parameterType = parameterSpec.charAt(1);
                switch (parameterType) {
                    case 'b':
                        String str = code;
                        if (!isFirstParameter) {
                            str += ", ";
                        }

                        code = str + "final boolean _" + parameterSpec.substring(3);
                        break;

                    case 'd':
                        str = code;
                        if (!isFirstParameter) {
                            str += ", ";
                        }

                        code = str + "final double _" + parameterSpec.substring(3);
                        break;

                    case 's':
                        str = code;
                        if (!isFirstParameter) {
                            str += ", ";
                        }

                        code = str + "final String _" + parameterSpec.substring(3);
                        break;

                    default:
                        if (parameterType == 'm') {
                            str = code;
                            if (!isFirstParameter) {
                                str += ", ";
                            }

                            int lastIndexOfPeriod = parameterSpec.lastIndexOf(".");
                            code = str +
                                    "final " + mq.e(mq.b(parameterSpec.substring(3, lastIndexOfPeriod))) + " _" +
                                    parameterSpec.substring(lastIndexOfPeriod + 1);
                            break;
                        } else {
                            continue processingParameters;
                        }
                }

                isFirstParameter = false;
            }
        }

        return code + ") {\r\n" +
                moreBlockLogic + "\r\n" +
                "}\r\n";
    }

    /**
     * @return Code of an adapter for a ListView
     */
    public static String getListAdapterCode(String widgetName, String itemResourceName, ArrayList<ViewBean> views, String onBindCustomViewLogic) {
        String className = a(widgetName);

        String initializers = "";
        StringBuilder initializersBuilder = new StringBuilder(initializers);
        for (ViewBean bean : views) {
            initializersBuilder.append(a(bean)).append("\r\n");
        }
        initializers = initializersBuilder.toString();

        String baseCode = "public class " + className + " extends BaseAdapter {\r\n" +
                "\r\n" +
                "ArrayList<HashMap<String, Object>> _data;\r\n" +
                "\r\n" +
                "public " + className + "(ArrayList<HashMap<String, Object>> _arr) {\r\n" +
                "_data = _arr;\r\n" +
                "}\r\n" +
                "\r\n" +
                "@Override\r\n" +
                "public int getCount() {\r\n" +
                "return _data.size();\r\n" +
                "}\r\n" +
                "\r\n" +
                "@Override\r\n" +
                "public HashMap<String, Object> getItem(int _index) {\r\n" +
                "return _data.get(_index);\r\n" +
                "}\r\n" +
                "\r\n" +
                "@Override\r\n" +
                "public long getItemId(int _index) {\r\n" +
                "return _index;\r\n" +
                "}\r\n" +
                "\r\n" +
                "@Override\r\n" +
                "public View getView(final int _position, View _v, ViewGroup _container) {\r\n" +
                "LayoutInflater _inflater = getLayoutInflater();\r\n" +
                "View _view = _v;\r\n" +
                "if (_view == null) {\r\n" +
                "_view = _inflater.inflate(R.layout." + itemResourceName + ", null);\r\n" +
                "}\r\n";

        if (!TextUtils.isEmpty(initializers)) {
            baseCode += "\r\n" +
                    initializers;
        }

        if (!TextUtils.isEmpty(onBindCustomViewLogic.trim())) {
            baseCode += "\r\n" +
                    onBindCustomViewLogic + "\r\n";
        }

        return baseCode + "\r\n" +
                "return _view;\r\n" +
                "}\r\n" +
                "}\r\n";
    }

    /**
     * @return An initializer for that component/variable.
     * Example initializer for an Intent component: <code>new Intent()</code>
     * Example initializer for a boolean variable: <code>false</code>
     */
    public static String getInitializer(String name, String componentName, String... parameters) {
        switch (name) {
            case "boolean":
                return "false";

            case "double":
                return "0";

            case "String":
                return "\"\"";

            case "Map":
                return "new HashMap<>()";

            case "ListInt":
            case "ListString":
            case "ListMap":
                return "new ArrayList<>()";

            case "Intent":
                return "new Intent()";

            case "Calendar":
                return "Calendar.getInstance()";

            case "FirebaseDB":
                String reference = "";
                if (parameters[0] != null && !parameters[0].isEmpty()) {
                    reference = parameters[0].replace(";", "");
                }
                return "_firebase.getReference(\"" + reference + "\")";

            case "ObjectAnimator":
                return "new ObjectAnimator()";

            case "FirebaseStorage":
                reference = "";
                if (parameters[0] != null && !parameters[0].isEmpty()) {
                    reference = parameters[0].replace(";", "");
                }
                return "_firebase_storage.getReference(\"" + reference + "\")";

            case "Camera":
                return "new Intent(MediaStore.ACTION_IMAGE_CAPTURE)";

            case "FilePicker":
                return "new Intent(Intent.ACTION_GET_CONTENT)";

            default:
                return "";
        }
    }

    public static void a(StringBuilder stringBuilder, int indentSize) {
        for (int i = 0; i < indentSize; ++i) {
            stringBuilder.append('\t');
        }
    }

    /**
     * @return Content of a generated <code>BluetoothConnect.java</code> file, without indentation
     */
    public static String b(String packageName) {
        return "package " + packageName + ";\r\n" +
                "\r\n" +
                "import android.app.Activity;\r\n" +
                "import android.bluetooth.BluetoothAdapter;\r\n" +
                "import android.bluetooth.BluetoothDevice;\r\n" +
                "import android.content.Intent;\r\n" +
                "\r\n" +
                "import java.util.ArrayList;\r\n" +
                "import java.util.HashMap;\r\n" +
                "import java.util.Set;\r\n" +
                "import java.util.UUID;\r\n" +
                "\r\n" +
                "public class BluetoothConnect {\r\n" +
                "private static final String DEFAULT_UUID = \"00001101-0000-1000-8000-00805F9B34FB\";\r\n" +
                "\r\n" +
                "private Activity activity;\r\n" +
                "\r\n" +
                "private BluetoothAdapter bluetoothAdapter;\r\n" +
                "\r\n" +
                "public BluetoothConnect(Activity activity) {\r\n" +
                "this.activity = activity;\r\n" +
                "this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();\r\n" +
                "}\r\n" +
                "\r\n" +
                "public boolean isBluetoothEnabled() {\r\n" +
                "if (bluetoothAdapter != null) return true;\r\n" +
                "\r\n" +
                "return false;\r\n" +
                "}\r\n" +
                "\r\n" +
                "public boolean isBluetoothActivated() {\r\n" +
                "if (bluetoothAdapter == null) return false;\r\n" +
                "\r\n" +
                "return bluetoothAdapter.isEnabled();\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void activateBluetooth() {\r\n" +
                "Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);\r\n" +
                "activity.startActivity(intent);\r\n" +
                "}\r\n" +
                "\r\n" +
                "public String getRandomUUID() {\r\n" +
                "return String.valueOf(UUID.randomUUID());\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void getPairedDevices(ArrayList<HashMap<String, Object>> results) {\r\n" +
                "Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();\r\n" +
                "\r\n" +
                "if (pairedDevices.size() > 0) {\r\n" +
                "for (BluetoothDevice device : pairedDevices) {\r\n" +
                "HashMap<String, Object> result = new HashMap<>();\r\n" +
                "result.put(\"name\", device.getName());\r\n" +
                "result.put(\"address\", device.getAddress());\r\n" +
                "\r\n" +
                "results.add(result);\r\n" +
                "}\r\n" +
                "}\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void readyConnection(BluetoothConnectionListener listener, String tag) {\r\n" +
                "if (BluetoothController.getInstance().getState().equals(BluetoothController.STATE_NONE)) {\r\n" +
                "BluetoothController.getInstance().start(this, listener, tag, UUID.fromString(DEFAULT_UUID), bluetoothAdapter);\r\n" +
                "}\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void readyConnection(BluetoothConnectionListener listener, String uuid, String tag) {\r\n" +
                "if (BluetoothController.getInstance().getState().equals(BluetoothController.STATE_NONE)) {\r\n" +
                "BluetoothController.getInstance().start(this, listener, tag, UUID.fromString(uuid), bluetoothAdapter);\r\n" +
                "}\r\n" +
                "}\r\n" +
                "\r\n" +
                "\r\n" +
                "public void startConnection(BluetoothConnectionListener listener, String address, String tag) {\r\n" +
                "BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);\r\n" +
                "\r\n" +
                "BluetoothController.getInstance().connect(device, this, listener, tag, UUID.fromString(DEFAULT_UUID), bluetoothAdapter);\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void startConnection(BluetoothConnectionListener listener, String uuid, String address, String tag) {\r\n" +
                "BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);\r\n" +
                "\r\n" +
                "BluetoothController.getInstance().connect(device, this, listener, tag, UUID.fromString(uuid), bluetoothAdapter);\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void stopConnection(BluetoothConnectionListener listener, String tag) {\r\n" +
                "BluetoothController.getInstance().stop(this, listener, tag);\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void sendData(BluetoothConnectionListener listener, String data, String tag) {\r\n" +
                "String state = BluetoothController.getInstance().getState();\r\n" +
                "\r\n" +
                "if (!state.equals(BluetoothController.STATE_CONNECTED)) {\r\n" +
                "listener.onConnectionError(tag, state, \"Bluetooth is not connected yet\");\r\n" +
                "return;\r\n" +
                "}\r\n" +
                "\r\n" +
                "BluetoothController.getInstance().write(data.getBytes());\r\n" +
                "}\r\n" +
                "\r\n" +
                "public Activity getActivity() {\r\n" +
                "return activity;\r\n" +
                "}\r\n" +
                "\r\n" +
                "public interface BluetoothConnectionListener {\r\n" +
                "void onConnected(String tag, HashMap<String, Object> deviceData);\r\n" +
                "void onDataReceived(String tag, byte[] data, int bytes);\r\n" +
                "void onDataSent(String tag, byte[] data);\r\n" +
                "void onConnectionError(String tag, String connectionState, String message);\r\n" +
                "void onConnectionStopped(String tag);\r\n" +
                "}\r\n" +
                "}\r\n";
    }

    public static String b(String eventName, String viewType, String viewId) {
        boolean isMapView = viewType.equals("MapView");
        boolean isAdView = viewType.equals("AdView");
        StringBuilder code = new StringBuilder();

        switch (eventName) {
            case "onBackPressed":
                if (viewType.equals("DrawerLayout")) {
                    code.append("if (").append(viewId).append(".isDrawerOpen(GravityCompat.START)) {\r\n");
                    code.append(viewId).append(".closeDrawer(GravityCompat.START);").append("\r\n");
                    code.append("} else {\r\n");
                    code.append("super.onBackPressed();").append("\r\n");
                    code.append("}");
                }
                break;

            case "onDestroy":
                if (isMapView) {
                    code.append(viewId).append(".onDestroy();");
                }
                if (isAdView) {
                    code.append("if (").append(viewId).append(" != null) {\r\n");
                    code.append(viewId).append(".destroy();\r\n");
                    code.append("}");
                }
                break;

            case "onPause":
                if (isMapView) {
                    code.append(viewId).append(".onPause();");
                }
                if (isAdView) {
                    code.append("if (").append(viewId).append(" != null) {\r\n");
                    code.append(viewId).append(".pause();\r\n");
                    code.append("}");
                }
                break;

            case "onStart":
                if (isMapView) {
                    code.append(viewId).append(".onStart();");
                }
                break;

            case "onResume":
                if (isMapView) {
                    code.append(viewId).append(".onResume();");
                }
                if (isAdView) {
                    code.append("if (").append(viewId).append(" != null) {\r\n");
                    code.append(viewId).append(".resume();\r\n");
                    code.append("}");
                }
                break;

            case "onStop":
                if (isMapView) {
                    code.append(viewId).append(".onStop();");
                }
                break;
        }
        return code.toString();
    }

    /**
     * @return Initializer of a View to be added to _initialize(Bundle)
     */
    public static String getViewInitializer(String type, String name, boolean isInFragment) {
        String initializer = "";

        if (!type.equals("include") && !type.equals("#")) {
            initializer = name + " = " +
                    (isInFragment ? "_view.findViewById(R.id." : "findViewById(R.id.") +
                    name + ");";
        }

        switch (type) {
            case "WebView":
                return initializer + "\r\n" +
                        name + ".getSettings().setJavaScriptEnabled(true);\r\n" +
                        name + ".getSettings().setSupportZoom(true);";

            case "MapView":
                return initializer + "\r\n" +
                        name + ".onCreate(_savedInstanceState);\r\n";

            case "VideoView":
                String mediaControllerName = name + "_controller";
                return initializer + "\r\n" +
                        "MediaController " + mediaControllerName + " = new MediaController(this);\r\n" +
                        name + ".setMediaController(" + mediaControllerName + ");";

            default:
                return initializer;
        }
    }

    /**
     * @return Initializer for a Component that'd appear in <code>_initialize(Bundle)</code>
     */
    public static String b(String componentNameId, String componentName, String... parameters) {
        switch (componentNameId) {
            case "SharedPreferences":
                String preferenceFilename = "";
                if (parameters[0] != null && !parameters[0].isEmpty()) {
                    preferenceFilename = parameters[0].replace(";", "");
                }
                return componentName + " = getSharedPreferences(\"" + preferenceFilename + "\", Activity.MODE_PRIVATE);";

            case "Vibrator":
                return componentName + " = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);";

            case "Dialog":
                return componentName + " = new AlertDialog.Builder(this);";

            case "Gyroscope":
                return componentName + " = (SensorManager) getSystemService(Context.SENSOR_SERVICE);\r\n" +
                        "if (" + componentName + ".getDefaultSensor(Sensor.TYPE_GYROSCOPE) == null) {\r\n" +
                        "SketchwareUtil.showMessage(getApplicationContext(), \"Gyroscope is not supported on this device\");\r\n" +
                        "}";

            case "FirebaseAuth":
                return componentName + " = FirebaseAuth.getInstance();";

            case "FilePicker":
                String mimeType;
                if (parameters[0] != null && parameters[0].length() > 0) {
                    mimeType = parameters[0].replace(";", "");
                } else {
                    mimeType = "*/*";
                }
                return componentName + ".setType(\"" + mimeType + "\");\r\n" +
                        componentName + ".putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);";

            case "Camera":
                return "_file_" + componentName + " = FileUtil.createNewPictureFile(getApplicationContext());\r\n" +
                        "Uri _uri_" + componentName + ";\r\n" +
                        "if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {\r\n" +
                        "_uri_" + componentName + " = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + \".provider\", _file_" + componentName + ");\r\n" +
                        "} else {\r\n" +
                        "_uri_" + componentName + " = Uri.fromFile(_file_" + componentName + ");\r\n" +
                        "}\r\n" +
                        componentName + ".putExtra(MediaStore.EXTRA_OUTPUT, _uri_" + componentName + ");\r\n" +
                        componentName + ".addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);";

            case "RequestNetwork":
                return componentName + " = new RequestNetwork(this);";

            case "TextToSpeech":
                return componentName + " = new TextToSpeech(getApplicationContext(), null);";

            case "SpeechToText":
                return componentName + " = SpeechRecognizer.createSpeechRecognizer(this);";

            case "BluetoothConnect":
                return componentName + " = new BluetoothConnect(this);";

            case "LocationManager":
                return componentName + " = (LocationManager) getSystemService(Context.LOCATION_SERVICE);";

            case "TimePickerDialog":
                return componentName + " = new TimePickerDialog(this, " + componentName + "_listener, Calendar.HOUR_OF_DAY, Calendar.MINUTE, false);";

            case "FragmentStatePagerAdapter":
                return componentName + " = new " + a(componentName + "Fragment") + "(getApplicationContext(), getSupportFragmentManager());";

            case "Videos":
                return "file_" + componentName + " = FileUtil.createNewPictureFile(getApplicationContext());\r\n"
                        + "Uri _uri_" + componentName + " = null;\r\n"
                        + "if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {\r\n"
                        + "_uri_" + componentName + " = FileProvider.getUriForFile(getApplicationContext(), " +
                        "getApplicationContext().getPackageName() + \".provider\", file_" + componentName + ");\r\n"
                        + "}\r\n"
                        + "else {\r\n"
                        + "_uri_" + componentName + " = Uri.fromFile(file_" + componentName + ");\r\n"
                        + componentName + ".putExtra(MediaStore.EXTRA_OUTPUT, _uri_" + componentName + ");\r\n"
                        + componentName + ".addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);";

            case "DatePickerDialog":
                return componentName + " = new DatePickerDialog(this);";

            default:
                return ComponentsHandler.defineExtraVar(componentNameId, componentName);

        }
    }

    /**
     * @return Content of a generated <code>BluetoothController.java</code> file, without indentation
     */
    public static String c(String packageName) {
        return "package " + packageName + ";\r\n" +
                "\r\n" +
                "import android.bluetooth.BluetoothAdapter;\r\n" +
                "import android.bluetooth.BluetoothDevice;\r\n" +
                "import android.bluetooth.BluetoothServerSocket;\r\n" +
                "import android.bluetooth.BluetoothSocket;\r\n" +
                "\r\n" +
                "import java.io.InputStream;\r\n" +
                "import java.io.OutputStream;\r\n" +
                "import java.util.HashMap;\r\n" +
                "import java.util.UUID;\r\n" +
                "\r\n" +
                "public class BluetoothController {\r\n" +
                "public static final String STATE_NONE = \"none\";\r\n" +
                "public static final String STATE_LISTEN = \"listen\";\r\n" +
                "public static final String STATE_CONNECTING = \"connecting\";\r\n" +
                "public static final String STATE_CONNECTED = \"connected\";\r\n" +
                "\r\n" +
                "private AcceptThread acceptThread;\r\n" +
                "private ConnectThread connectThread;\r\n" +
                "private ConnectedThread connectedThread;\r\n" +
                "\r\n" +
                "private String state = STATE_NONE;\r\n" +
                "\r\n" +
                "private static BluetoothController instance;\r\n" +
                "\r\n" +
                "public static synchronized BluetoothController getInstance() {\r\n" +
                "if (instance == null) {\r\n" +
                "instance = new BluetoothController();\r\n" +
                "}\r\n" +
                "\r\n" +
                "return instance;\r\n" +
                "}\r\n" +
                "\r\n" +
                "public synchronized void start(BluetoothConnect bluetoothConnect, BluetoothConnect.BluetoothConnectionListener listener, String tag, UUID uuid, BluetoothAdapter bluetoothAdapter) {\r\n" +
                "if (connectThread != null) {\r\n" +
                "connectThread.cancel();\r\n" +
                "connectThread = null;\r\n" +
                "}\r\n" +
                "\r\n" +
                "if (connectedThread != null) {\r\n" +
                "connectedThread.cancel();\r\n" +
                "connectedThread = null;\r\n" +
                "}\r\n" +
                "\r\n" +
                "if (acceptThread != null) {\r\n" +
                "acceptThread.cancel();\r\n" +
                "acceptThread = null;\r\n" +
                "}\r\n" +
                "\r\n" +
                "acceptThread = new AcceptThread(bluetoothConnect, listener, tag, uuid, bluetoothAdapter);\r\n" +
                "acceptThread.start();}\r\n" +
                "\r\n" +
                "public synchronized void connect(BluetoothDevice device, BluetoothConnect bluetoothConnect, BluetoothConnect.BluetoothConnectionListener listener, String tag, UUID uuid, BluetoothAdapter bluetoothAdapter) {\r\n" +
                "if (state.equals(STATE_CONNECTING)) {\r\n" +
                "if (connectThread != null) {\r\n" +
                "connectThread.cancel();\r\n" +
                "connectThread = null;\r\n" +
                "}\r\n" +
                "}\r\n" +
                "\r\n" +
                "if (connectedThread != null) {\r\n" +
                "connectedThread.cancel();\r\n" +
                "connectedThread = null;\r\n" +
                "}\r\n" +
                "\r\n" +
                "connectThread = new ConnectThread(device, bluetoothConnect, listener, tag, uuid, bluetoothAdapter);\r\n" +
                "connectThread.start();\r\n" +
                "}\r\n" +
                "\r\n" +
                "public synchronized void connected(BluetoothSocket socket, final BluetoothDevice device, BluetoothConnect bluetoothConnect, final BluetoothConnect.BluetoothConnectionListener listener, final String tag) {\r\n" +
                "if (connectThread != null) {\r\n" +
                "connectThread.cancel();\r\n" +
                "connectThread = null;\r\n" +
                "}\r\n" +
                "\r\n" +
                "if (connectedThread != null) {\r\n" +
                "connectedThread.cancel();\r\n" +
                "connectedThread = null;\r\n" +
                "}\r\n" +
                "\r\n" +
                "if (acceptThread != null) {\r\n" +
                "acceptThread.cancel();\r\n" +
                "acceptThread = null;\r\n" +
                "}\r\n" +
                "\r\n" +
                "connectedThread = new ConnectedThread(socket, bluetoothConnect, listener, tag);\r\n" +
                "connectedThread.start();\r\n" +
                "\r\n" +
                "bluetoothConnect.getActivity().runOnUiThread(new Runnable() {\r\n" +
                "@Override\r\n" +
                "public void run() {\r\n" +
                "HashMap<String, Object> deviceMap = new HashMap<>();\r\n" +
                "deviceMap.put(\"name\", device.getName());\r\n" +
                "deviceMap.put(\"address\", device.getAddress());\r\n" +
                "\r\n" +
                "listener.onConnected(tag, deviceMap);\r\n" +
                "}\r\n" +
                "});\r\n" +
                "}\r\n" +
                "\r\n" +
                "public synchronized void stop(BluetoothConnect bluetoothConnect, final BluetoothConnect.BluetoothConnectionListener listener, final String tag) {\r\n" +
                "if (connectThread != null) {\r\n" +
                "connectThread.cancel();\r\n" +
                "connectThread = null;\r\n" +
                "}\r\n" +
                "\r\n" +
                "if (connectedThread != null) {\r\n" +
                "connectedThread.cancel();\r\n" +
                "connectedThread = null;\r\n" +
                "}\r\n" +
                "\r\n" +
                "if (acceptThread != null) {\r\n" +
                "acceptThread.cancel();\r\n" +
                "acceptThread = null;\r\n" +
                "}\r\n" +
                "\r\n" +
                "state = STATE_NONE;\r\n" +
                "\r\n" +
                "bluetoothConnect.getActivity().runOnUiThread(new Runnable() {\r\n" +
                "@Override\r\n" +
                "public void run() {\r\n" +
                "listener.onConnectionStopped(tag);\r\n" +
                "}\r\n" +
                "});\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void write(byte[] out) {\r\n" +
                "ConnectedThread r;\r\n" +
                "\r\n" +
                "synchronized (this) {\r\n" +
                "if (!state.equals(STATE_CONNECTED)) return;\r\n" +
                "r = connectedThread;\r\n" +
                "}\r\n" +
                "\r\n" +
                "r.write(out);\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void connectionFailed(BluetoothConnect bluetoothConnect, final BluetoothConnect.BluetoothConnectionListener listener, final String tag, final String message) {\r\n" +
                "state = STATE_NONE;\r\n" +
                "\r\n" +
                "bluetoothConnect.getActivity().runOnUiThread(new Runnable() {\r\n" +
                "@Override\r\n" +
                "public void run() {\r\n" +
                "listener.onConnectionError(tag, state, message);\r\n" +
                "}\r\n" +
                "});\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void connectionLost(BluetoothConnect bluetoothConnect, final BluetoothConnect.BluetoothConnectionListener listener, final String tag) {\r\n" +
                "state = STATE_NONE;\r\n" +
                "\r\n" +
                "bluetoothConnect.getActivity().runOnUiThread(new Runnable() {\r\n" +
                "@Override\r\n" +
                "public void run() {\r\n" +
                "listener.onConnectionError(tag, state, \"Bluetooth connection is disconnected\");\r\n" +
                "}\r\n" +
                "});\r\n" +
                "}\r\n" +
                "\r\n" +
                "public String getState() {\r\n" +
                "return state;\r\n" +
                "}\r\n" +
                "\r\n" +
                "private class AcceptThread extends Thread {\r\n" +
                "private BluetoothServerSocket serverSocket;\r\n" +
                "\r\n" +
                "private BluetoothConnect bluetoothConnect;\r\n" +
                "private BluetoothConnect.BluetoothConnectionListener listener;\r\n" +
                "private String tag;\r\n" +
                "\r\n" +
                "public AcceptThread(BluetoothConnect bluetoothConnect, BluetoothConnect.BluetoothConnectionListener listener, String tag, UUID uuid, BluetoothAdapter bluetoothAdapter) {\r\n" +
                "this.bluetoothConnect = bluetoothConnect;\r\n" +
                "this.listener = listener;\r\n" +
                "this.tag = tag;\r\n" +
                "\r\n" +
                "try {\r\n" +
                "serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(tag, uuid);\r\n" +
                "} catch (Exception e) {\r\n" +
                "e.printStackTrace();\r\n" +
                "}\r\n" +
                "\r\n" +
                "state = STATE_LISTEN;\r\n" +
                "}\r\n" +
                "\r\n" +
                "@Override\r\n" +
                "public void run() {\r\n" +
                "BluetoothSocket bluetoothSocket;\r\n" +
                "\r\n" +
                "while (!state.equals(STATE_CONNECTED)) {\r\n" +
                "try {\r\n" +
                "bluetoothSocket = serverSocket.accept();\r\n" +
                "} catch (Exception e) {\r\n" +
                "e.printStackTrace();\r\n" +
                "break;\r\n" +
                "}\r\n" +
                "\r\n" +
                "if (bluetoothSocket != null) {\r\n" +
                "synchronized (BluetoothController.this) {\r\n" +
                "switch (state) {\r\n" +
                "case STATE_LISTEN:\r\n" +
                "case STATE_CONNECTING:\r\n" +
                "connected(bluetoothSocket, bluetoothSocket.getRemoteDevice(), bluetoothConnect, listener, tag);\r\n" +
                "break;\r\n" +
                "case STATE_NONE:\r\n" +
                "case STATE_CONNECTED:\r\n" +
                "try {\r\n" +
                "bluetoothSocket.close();\r\n" +
                "} catch (Exception e) {\r\n" +
                "e.printStackTrace();\r\n" +
                "}\r\n" +
                "break;\r\n" +
                "}\r\n" +
                "}\r\n" +
                "}\r\n" +
                "}\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void cancel() {\r\n" +
                "try {\r\n" +
                "serverSocket.close();\r\n" +
                "} catch (Exception e) {\r\n" +
                "e.printStackTrace();\r\n" +
                "}\r\n" +
                "}\r\n" +
                "}\r\n" +
                "\r\n" +
                "private class ConnectThread extends Thread {\r\n" +
                "private BluetoothDevice device;\r\n" +
                "private BluetoothSocket socket;\r\n" +
                "\r\n" +
                "private BluetoothConnect bluetoothConnect;\r\n" +
                "private BluetoothConnect.BluetoothConnectionListener listener;\r\n" +
                "private String tag;\r\n" +
                "private BluetoothAdapter bluetoothAdapter;\r\n" +
                "\r\n" +
                "public ConnectThread(BluetoothDevice device, BluetoothConnect bluetoothConnect, BluetoothConnect.BluetoothConnectionListener listener, String tag, UUID uuid, BluetoothAdapter bluetoothAdapter) {\r\n" +
                "this.device = device;\r\n" +
                "this.bluetoothConnect = bluetoothConnect;\r\n" +
                "this.listener = listener;\r\n" +
                "this.tag = tag;\r\n" +
                "this.bluetoothAdapter = bluetoothAdapter;\r\n" +
                "\r\n" +
                "try {\r\n" +
                "socket = device.createRfcommSocketToServiceRecord(uuid);\r\n" +
                "} catch (Exception e) {\r\n" +
                "e.printStackTrace();\r\n" +
                "}\r\n" +
                "\r\n" +
                "state = STATE_CONNECTING;\r\n" +
                "}\r\n" +
                "\r\n" +
                "@Override\r\n" +
                "public void run() {\r\n" +
                "bluetoothAdapter.cancelDiscovery();\r\n" +
                "\r\n" +
                "try {\r\n" +
                "socket.connect();\r\n" +
                "} catch (Exception e) {\r\n" +
                "try {\r\n" +
                "socket.close();\r\n" +
                "} catch (Exception e2) {\r\n" +
                "e2.printStackTrace();\r\n" +
                "}\r\n" +
                "connectionFailed(bluetoothConnect, listener, tag, e.getMessage());\r\n" +
                "return;\r\n" +
                "}\r\n" +
                "\r\n" +
                "synchronized (BluetoothController.this) {\r\n" +
                "connectThread = null;\r\n" +
                "}\r\n" +
                "\r\n" +
                "connected(socket, device, bluetoothConnect, listener, tag);\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void cancel() {\r\n" +
                "try {\r\n" +
                "socket.close();\r\n" +
                "} catch (Exception e) {\r\n" +
                "e.printStackTrace();\r\n" +
                "}\r\n" +
                "}\r\n" +
                "}\r\n" +
                "\r\n" +
                "private class ConnectedThread extends Thread {\r\n" +
                "private BluetoothSocket socket;\r\n" +
                "private InputStream inputStream;\r\n" +
                "private OutputStream outputStream;\r\n" +
                "\r\n" +
                "private BluetoothConnect bluetoothConnect;\r\n" +
                "private BluetoothConnect.BluetoothConnectionListener listener;\r\n" +
                "private String tag;\r\n" +
                "\r\n" +
                "public ConnectedThread(BluetoothSocket socket, BluetoothConnect bluetoothConnect, BluetoothConnect.BluetoothConnectionListener listener, String tag) {\r\n" +
                "this.bluetoothConnect = bluetoothConnect;\r\n" +
                "this.listener = listener;\r\n" +
                "this.tag = tag;\r\n" +
                "\r\n" +
                "this.socket = socket;\r\n" +
                "\r\n" +
                "try {\r\n" +
                "inputStream = socket.getInputStream();\r\n" +
                "outputStream = socket.getOutputStream();\r\n" +
                "} catch (Exception e) {\r\n" +
                "e.printStackTrace();\r\n" +
                "}\r\n" +
                "\r\n" +
                "state = STATE_CONNECTED;\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void run() {\r\n" +
                "while (state.equals(STATE_CONNECTED)) {\r\n" +
                "try {\r\n" +
                "final byte[] buffer = new byte[1024];\r\n" +
                "final int bytes = inputStream.read(buffer);\r\n" +
                "\r\n" +
                "bluetoothConnect.getActivity().runOnUiThread(new Runnable() {\r\n" +
                "@Override\r\n" +
                "public void run() {\r\n" +
                "listener.onDataReceived(tag, buffer, bytes);\r\n" +
                "}\r\n" +
                "});\r\n" +
                "} catch (Exception e) {\r\n" +
                "e.printStackTrace();\r\n" +
                "connectionLost(bluetoothConnect, listener, tag);\r\n" +
                "break;\r\n" +
                "}\r\n" +
                "}\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void write(final byte[] buffer) {\r\n" +
                "try {\r\n" +
                "outputStream.write(buffer);\r\n" +
                "\r\n" +
                "bluetoothConnect.getActivity().runOnUiThread(new Runnable() {\r\n" +
                "@Override\r\n" +
                "public void run() {\r\n" +
                "listener.onDataSent(tag, buffer);\r\n" +
                "}\r\n" +
                "});\r\n" +
                "} catch (Exception e) {\r\n" +
                "e.printStackTrace();\r\n" +
                "}\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void cancel() {\r\n" +
                "try {\r\n" +
                "socket.close();\r\n" +
                "} catch (Exception e) {\r\n" +
                "e.printStackTrace();\r\n" +
                "}\r\n" +
                "}\r\n" +
                "}\r\n" +
                "}\r\n";
    }

    /**
     * @return A generated top-level <code>build.gradle</code> file, with indentation
     */
    public static String c(String androidGradlePluginVersion, String googleMobileServicesVersion) {
        return "// Top-level build file where you can add configuration options common to all sub-projects/modules.\r\n" +
                "buildscript {\r\n" +
                "    repositories {\r\n" +
                "        google()\r\n" +
                "        jcenter()\r\n" +
                "    }\r\n" +
                "    dependencies {\r\n" +
                "        classpath 'com.android.tools.build:gradle:" + androidGradlePluginVersion + "'\r\n" +
                "        classpath 'com.google.gms:google-services:" + googleMobileServicesVersion + "'\r\n" +
                "        // NOTE: Do not place your application dependencies here; they belong\r\n" +
                "        // in the individual module build.gradle files\r\n" +
                "    }\r\n" +
                "}\r\n" +
                "\r\n" +
                "allprojects {\r\n" +
                "    repositories {\r\n" +
                "        google()\r\n" +
                "        jcenter()\r\n" +
                "    }\r\n" +
                "}\r\n" +
                "\r\n" +
                "task clean(type: Delete) {\r\n" +
                "    delete rootProject.buildDir\r\n" +
                "}\r\n";
    }

    /**
     * @return A single line to initialize a drawer view.
     */
    public static String getDrawerViewInitializer(String type, String viewName, String viewContainerName) {
        String initializer = "";
        if (!type.equals("include") && !type.equals("#")) {
            initializer = "_drawer_" + viewName + " = " + viewContainerName + ".findViewById(R.id." + viewName + ");";
        }

        return initializer;
    }

    /**
     * @return Line declaring a field required for <code>componentName</code>
     */
    public static String d(String componentName) {
        switch (componentName) {
            case "FirebaseDB":
                return "private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();";

            case "Timer":
                return "private Timer _timer = new Timer();";

            case "FirebaseStorage":
                return "private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();";

            case "InterstitialAd":
                return "private String _ad_unit_id;";

            case "RewardedVideoAd":
                return "private String _reward_ad_unit_id;";

            default:
                return "";
        }
    }

    public static String d(String eventName, String componentName, String eventLogic) {
        switch (eventName) {
            case "onClickListener":
                return componentName + ".setOnClickListener(new View.OnClickListener() {\r\n" +
                        eventLogic + "\r\n" +
                        "});";

            case "sensorEventListener":
                String sensorEventListenerName = "_" + componentName + "_sensor_listener";
                return sensorEventListenerName + " = new SensorEventListener() {\r\n"
                        + eventLogic + "\r\n"
                        + "};\r\n"
                        + componentName + ".registerListener(" + sensorEventListenerName + ", " + componentName
                        + ".getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);";

            case "onTextChangedListener":
                return componentName + ".addTextChangedListener(new TextWatcher() {\r\n"
                        + eventLogic + "\r\n"
                        + "});";

            case "onDeleteSuccessListener":
                return "_" + componentName + "_delete_success_listener = new OnSuccessListener() {\r\n"
                        + eventLogic + "\r\n"
                        + "};";

            case "onDownloadSuccessListener":
                return "_" + componentName + "_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {\r\n"
                        + eventLogic + "\r\n"
                        + "};";

            case "recognitionListener":
                return componentName + ".setRecognitionListener(new RecognitionListener() {\r\n"
                        + "@Override\r\n"
                        + "public void onReadyForSpeech(Bundle _param1) {\r\n"
                        + "}\r\n"
                        + "\r\n"
                        + "@Override\r\n"
                        + "public void onBeginningOfSpeech() {\r\n"
                        + "}\r\n"
                        + "\r\n"
                        + "@Override\r\n"
                        + "public void onRmsChanged(float _param1) {\r\n"
                        + "}\r\n"
                        + "\r\n"
                        + "@Override\r\n"
                        + "public void onBufferReceived(byte[] _param1) {\r\n"
                        + "}\r\n"
                        + "\r\n"
                        + "@Override\r\n"
                        + "public void onEndOfSpeech() {\r\n"
                        + "}\r\n"
                        + "\r\n"
                        + "@Override\r\n"
                        + "public void onPartialResults(Bundle _param1) {\r\n"
                        + "}\r\n"
                        + "\r\n"
                        + "@Override\r\n"
                        + "public void onEvent(int _param1, Bundle _param2) {\r\n"
                        + "}\r\n"
                        + "\r\n"
                        + eventLogic + "\r\n"
                        + "});";

            case "onFailureListener":
                return "_" + componentName + "_failure_listener = new OnFailureListener() {\r\n"
                        + eventLogic + "\r\n"
                        + "};";

            case "onDownloadProgressListener":
                return "_" + componentName + "_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {\r\n"
                        + eventLogic + "\r\n"
                        + "};";

            case "onMapMarkerClickListener":
                return "_" + componentName + "_controller.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {\r\n"
                        + eventLogic + "\r\n"
                        + "});";

            case "authCreateUserComplete":
                return "_" + componentName + "_create_user_listener = new OnCompleteListener<AuthResult>() {\r\n"
                        + eventLogic + "\r\n"
                        + "};";

            case "webViewClient":
                return componentName + ".setWebViewClient(new WebViewClient() {\r\n"
                        + eventLogic + "\r\n"
                        + "});";

            case "requestListener":
                return "_" + componentName + "_request_listener = new RequestNetwork.RequestListener() {\r\n"
                        + eventLogic + "\r\n"
                        + "};";

            case "onItemSelectedListener":
                return componentName + ".setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {\r\n"
                        + eventLogic + "\r\n"
                        + "});";

            case "onMapReadyCallback":
                String googleMapControllerName = "_" + componentName + "_controller";
                return googleMapControllerName + " = new GoogleMapController(" + componentName + ", new OnMapReadyCallback() {\r\n"
                        + "@Override\r\n"
                        + "public void onMapReady(GoogleMap _googleMap) {\r\n"
                        + googleMapControllerName + ".setGoogleMap(_googleMap);\r\n"
                        + eventLogic + "\r\n"
                        + "}\r\n"
                        + "});";

            case "animatorListener":
                return componentName + ".addListener(new Animator.AnimatorListener() {\r\n"
                        + eventLogic + "\r\n"
                        + "});";

            case "onItemClickListener":
                return componentName + ".setOnItemClickListener(new AdapterView.OnItemClickListener() {\r\n"
                        + eventLogic + "\r\n"
                        + "});";

            case "authSignInUserComplete":
                return "_" + componentName + "_sign_in_listener = new OnCompleteListener<AuthResult>() {\r\n"
                        + eventLogic + "\r\n"
                        + "};";

            case "onSeekBarChangeListener":
                return componentName + ".setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {\r\n"
                        + eventLogic + "\r\n"
                        + "});";

            case "onItemLongClickListener":
                return componentName + ".setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {\r\n"
                        + eventLogic + "\r\n"
                        + "});";

            case "onUploadSuccessListener":
                return "_" + componentName + "_upload_success_listener = new OnCompleteListener<Uri>() {\r\n"
                        + eventLogic + "\r\n"
                        + "};";

            case "onDateChangeListener":
                return componentName + ".setOnDateChangeListener(new CalendarView.OnDateChangeListener() {\r\n"
                        + eventLogic + "\r\n"
                        + "});";

            case "bluetoothConnectionListener":
                return "_" + componentName + "_bluetooth_connection_listener = new BluetoothConnect.BluetoothConnectionListener() {\r\n"
                        + eventLogic + "\r\n"
                        + "};";

            case "locationListener":
                return "_" + componentName + "_location_listener = new LocationListener() {\r\n"
                        + eventLogic + "\r\n"
                        + "\r\n"
                        + "@Override\r\n"
                        + "public void onStatusChanged(String provider, int status, Bundle extras) {\r\n"
                        + "}\r\n"
                        + "\r\n"
                        + "@Override\r\n"
                        + "public void onProviderEnabled(String provider) {\r\n"
                        + "}\r\n"
                        + "\r\n"
                        + "@Override\r\n"
                        + "public void onProviderDisabled(String provider) {\r\n"
                        + "}\r\n"
                        + "};";

            case "authResetEmailSent":
                return "_" + componentName + "_reset_password_listener = new OnCompleteListener<Void>() {\r\n"
                        + eventLogic + "\r\n"
                        + "};";

            case "onCheckChangedListener":
                return componentName + ".setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {\r\n"
                        + eventLogic + "\r\n"
                        + "});";

            case "onUploadProgressListener":
                return "_" + componentName + "_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {\r\n"
                        + eventLogic + "\r\n"
                        + "};";

            case "childEventListener":
                String childEventListenerName = "_" + componentName + "_child_listener";
                return childEventListenerName + " = new ChildEventListener() {\r\n"
                        + eventLogic + "\r\n" +
                        "};\r\n"
                        + componentName + ".addChildEventListener(" + childEventListenerName + ");";

            default:
                return ManageEvent.g(eventName, componentName, eventLogic);
        }
    }

    /**
     * @return Content of a generated <code>FileUtil.java</code> file, with indentation
     */
    public static String e(String packageName) {
        return "package " + packageName + ";\r\n" +
                "\r\n" +
                "import android.content.ContentResolver;\r\n" +
                "import android.content.ContentUris;\r\n" +
                "import android.content.Context;\r\n" +
                "import android.database.Cursor;\r\n" +
                "import android.graphics.Bitmap;\r\n" +
                "import android.graphics.BitmapFactory;\r\n" +
                "import android.graphics.Canvas;\r\n" +
                "import android.graphics.ColorFilter;\r\n" +
                "import android.graphics.ColorMatrix;\r\n" +
                "import android.graphics.ColorMatrixColorFilter;\r\n" +
                "import android.graphics.LightingColorFilter;\r\n" +
                "import android.graphics.Matrix;\r\n" +
                "import android.graphics.Paint;\r\n" +
                "import android.graphics.PorterDuff;\r\n" +
                "import android.graphics.PorterDuffXfermode;\r\n" +
                "import android.graphics.Rect;\r\n" +
                "import android.graphics.RectF;\r\n" +
                "import android.media.ExifInterface;\r\n" +
                "import android.net.Uri;\r\n" +
                "import android.os.Environment;\r\n" +
                "import android.provider.DocumentsContract;\r\n" +
                "import android.provider.MediaStore;\r\n" +
                "import android.text.TextUtils;\r\n" +
                "\r\n" +
                "import java.io.File;\r\n" +
                "import java.io.FileInputStream;\r\n" +
                "import java.io.FileOutputStream;\r\n" +
                "import java.io.FileReader;\r\n" +
                "import java.io.FileWriter;\r\n" +
                "import java.io.IOException;\r\n" +
                "import java.net.URLDecoder;\r\n" +
                "import java.text.SimpleDateFormat;\r\n" +
                "import java.util.ArrayList;\r\n" +
                "import java.util.Date;\r\n" +
                "\r\n" +
                "public class FileUtil {\r\n" +
                "\r\n" +
                "    private static void createNewFile(String path) {\r\n" +
                "        int lastSep = path.lastIndexOf(File.separator);\r\n" +
                "        if (lastSep > 0) {\r\n" +
                "            String dirPath = path.substring(0, lastSep);\r\n" +
                "            makeDir(dirPath);\r\n" +
                "        }\r\n" +
                "\r\n" +
                "        File file = new File(path);\r\n" +
                "\r\n" +
                "        try {\r\n" +
                "            if (!file.exists()) file.createNewFile();\r\n" +
                "        } catch (IOException e) {\r\n" +
                "            e.printStackTrace();\r\n" +
                "        }\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static String readFile(String path) {\r\n" +
                "        createNewFile(path);\r\n" +
                "\r\n" +
                "        StringBuilder sb = new StringBuilder();\r\n" +
                "        FileReader fr = null;\r\n" +
                "        try {\r\n" +
                "            fr = new FileReader(new File(path));\r\n" +
                "\r\n" +
                "            char[] buff = new char[1024];\r\n" +
                "            int length = 0;\r\n" +
                "\r\n" +
                "            while ((length = fr.read(buff)) > 0) {\r\n" +
                "                sb.append(new String(buff, 0, length));\r\n" +
                "            }\r\n" +
                "        } catch (IOException e) {\r\n" +
                "            e.printStackTrace();\r\n" +
                "        } finally {\r\n" +
                "            if (fr != null) {\r\n" +
                "                try {\r\n" +
                "                    fr.close();\r\n" +
                "                } catch (Exception e) {\r\n" +
                "                    e.printStackTrace();\r\n" +
                "                }\r\n" +
                "            }\r\n" +
                "        }\r\n" +
                "\r\n" +
                "        return sb.toString();\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static void writeFile(String path, String str) {\r\n" +
                "        createNewFile(path);\r\n" +
                "        FileWriter fileWriter = null;\r\n" +
                "\r\n" +
                "        try {\r\n" +
                "            fileWriter = new FileWriter(new File(path), false);\r\n" +
                "            fileWriter.write(str);\r\n" +
                "            fileWriter.flush();\r\n" +
                "        } catch (IOException e) {\r\n" +
                "            e.printStackTrace();\r\n" +
                "        } finally {\r\n" +
                "            try {\r\n" +
                "                if (fileWriter != null)\r\n" +
                "                    fileWriter.close();\r\n" +
                "            } catch (IOException e) {\r\n" +
                "                e.printStackTrace();\r\n" +
                "            }\r\n" +
                "        }\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static void copyFile(String sourcePath, String destPath) {\r\n" +
                "        if (!isExistFile(sourcePath)) return;\r\n" +
                "        createNewFile(destPath);\r\n" +
                "\r\n" +
                "        FileInputStream fis = null;\r\n" +
                "        FileOutputStream fos = null;\r\n" +
                "\r\n" +
                "        try {\r\n" +
                "            fis = new FileInputStream(sourcePath);\r\n" +
                "            fos = new FileOutputStream(destPath, false);\r\n" +
                "\r\n" +
                "            byte[] buff = new byte[1024];\r\n" +
                "            int length = 0;\r\n" +
                "\r\n" +
                "            while ((length = fis.read(buff)) > 0) {\r\n" +
                "                fos.write(buff, 0, length);\r\n" +
                "            }\r\n" +
                "        } catch (IOException e) {\r\n" +
                "            e.printStackTrace();\r\n" +
                "        } finally {\r\n" +
                "            if (fis != null) {\r\n" +
                "                try {\r\n" +
                "                    fis.close();\r\n" +
                "                } catch (IOException e) {\r\n" +
                "                    e.printStackTrace();\r\n" +
                "                }\r\n" +
                "            }\r\n" +
                "            if (fos != null) {\r\n" +
                "                try {\r\n" +
                "                    fos.close();\r\n" +
                "                } catch (IOException e) {\r\n" +
                "                    e.printStackTrace();\r\n" +
                "                }\r\n" +
                "            }\r\n" +
                "        }\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static void copyDir(String oldPath, String newPath) {\r\n" +
                "        File oldFile = new File(oldPath);\r\n" +
                "        File[] files = oldFile.listFiles();\r\n" +
                "        File newFile = new File(newPath);\r\n" +
                "        if (!newFile.exists()) {\r\n" +
                "            newFile.mkdirs();\r\n" +
                "        }\r\n" +
                "        for (File file : files) {\r\n" +
                "            if (file.isFile()) {\r\n" +
                "                copyFile(file.getPath(), newPath + \"/\" + file.getName());\r\n" +
                "            } else if (file.isDirectory()) {\r\n" +
                "                copyDir(file.getPath(), newPath + \"/\" + file.getName());\r\n" +
                "            }\r\n" +
                "        }\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static void moveFile(String sourcePath, String destPath) {\r\n" +
                "        copyFile(sourcePath, destPath);\r\n" +
                "        deleteFile(sourcePath);\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static void deleteFile(String path) {\r\n" +
                "        File file = new File(path);\r\n" +
                "\r\n" +
                "        if (!file.exists()) return;\r\n" +
                "\r\n" +
                "        if (file.isFile()) {\r\n" +
                "            file.delete();\r\n" +
                "            return;\r\n" +
                "        }\r\n" +
                "\r\n" +
                "        File[] fileArr = file.listFiles();\r\n" +
                "\r\n" +
                "        if (fileArr != null) {\r\n" +
                "            for (File subFile : fileArr) {\r\n" +
                "                if (subFile.isDirectory()) {\r\n" +
                "                    deleteFile(subFile.getAbsolutePath());\r\n" +
                "                }\r\n" +
                "\r\n" +
                "                if (subFile.isFile()) {\r\n" +
                "                    subFile.delete();\r\n" +
                "                }\r\n" +
                "            }\r\n" +
                "        }\r\n" +
                "\r\n" +
                "        file.delete();\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static boolean isExistFile(String path) {\r\n" +
                "        File file = new File(path);\r\n" +
                "        return file.exists();\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static void makeDir(String path) {\r\n" +
                "        if (!isExistFile(path)) {\r\n" +
                "            File file = new File(path);\r\n" +
                "            file.mkdirs();\r\n" +
                "        }\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static void listDir(String path, ArrayList<String> list) {\r\n" +
                "        File dir = new File(path);\r\n" +
                "        if (!dir.exists() || dir.isFile()) return;\r\n" +
                "\r\n" +
                "        File[] listFiles = dir.listFiles();\r\n" +
                "        if (listFiles == null || listFiles.length <= 0) return;\r\n" +
                "\r\n" +
                "        if (list == null) return;\r\n" +
                "        list.clear();\r\n" +
                "        for (File file : listFiles) {\r\n" +
                "            list.add(file.getAbsolutePath());\r\n" +
                "        }\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static boolean isDirectory(String path) {\r\n" +
                "        if (!isExistFile(path)) return false;\r\n" +
                "        return new File(path).isDirectory();\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static boolean isFile(String path) {\r\n" +
                "        if (!isExistFile(path)) return false;\r\n" +
                "        return new File(path).isFile();\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static long getFileLength(String path) {\r\n" +
                "        if (!isExistFile(path)) return 0;\r\n" +
                "        return new File(path).length();\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static String getExternalStorageDir() {\r\n" +
                "        return Environment.getExternalStorageDirectory().getAbsolutePath();\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static String getPackageDataDir(Context context) {\r\n" +
                "        return context.getExternalFilesDir(null).getAbsolutePath();\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static String getPublicDir(String type) {\r\n" +
                "        return Environment.getExternalStoragePublicDirectory(type).getAbsolutePath();\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static String convertUriToFilePath(final Context context, final Uri uri) {\r\n" +
                "        String path = null;\r\n" +
                "        if (DocumentsContract.isDocumentUri(context, uri)) {\r\n" +
                "            if (isExternalStorageDocument(uri)) {\r\n" +
                "                final String docId = DocumentsContract.getDocumentId(uri);\r\n" +
                "                final String[] split = docId.split(\":\");\r\n" +
                "                final String type = split[0];\r\n" +
                "\r\n" +
                "                if (\"primary\".equalsIgnoreCase(type)) {\r\n" +
                "                    path = Environment.getExternalStorageDirectory() + \"/\" + split[1];\r\n" +
                "                }\r\n" +
                "            } else if (isDownloadsDocument(uri)) {\r\n" +
                "                final String id = DocumentsContract.getDocumentId(uri);\r\n" +
                "\r\n" +
                "                if (!TextUtils.isEmpty(id)) {\r\n" +
                "                    if (id.startsWith(\"raw:\")) {\r\n" +
                "                        return id.replaceFirst(\"raw:\", \"\");\r\n" +
                "                    }\r\n" +
                "                }\r\n" +
                "\r\n" +
                "                final Uri contentUri = ContentUris\r\n" +
                "                        .withAppendedId(Uri.parse(\"content://downloads/public_downloads\"), Long.valueOf(id));\r\n" +
                "\r\n" +
                "                path = getDataColumn(context, contentUri, null, null);\r\n" +
                "            } else if (isMediaDocument(uri)) {\r\n" +
                "                final String docId = DocumentsContract.getDocumentId(uri);\r\n" +
                "                final String[] split = docId.split(\":\");\r\n" +
                "                final String type = split[0];\r\n" +
                "\r\n" +
                "                Uri contentUri = null;\r\n" +
                "                if (\"image\".equals(type)) {\r\n" +
                "                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;\r\n" +
                "                } else if (\"video\".equals(type)) {\r\n" +
                "                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;\r\n" +
                "                } else if (\"audio\".equals(type)) {\r\n" +
                "                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;\r\n" +
                "                }\r\n" +
                "\r\n" +
                "                final String selection = \"_id=?\";\r\n" +
                "                final String[] selectionArgs = new String[]{\r\n" +
                "                        split[1]\r\n" +
                "                };\r\n" +
                "\r\n" +
                "                path = getDataColumn(context, contentUri, selection, selectionArgs);\r\n" +
                "            }\r\n" +
                "        } else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(uri.getScheme())) {\r\n" +
                "            path = getDataColumn(context, uri, null, null);\r\n" +
                "        } else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(uri.getScheme())) {\r\n" +
                "            path = uri.getPath();\r\n" +
                "        }\r\n" +
                "\r\n" +
                "        if (path != null) {\r\n" +
                "            try {\r\n" +
                "                return URLDecoder.decode(path, \"UTF-8\");\r\n" +
                "            } catch(Exception e) {\r\n" +
                "                return null;\r\n" +
                "            }\r\n" +
                "        }\r\n" +
                "        return null;\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {\r\n" +
                "        final String column = MediaStore.Images.Media.DATA;\r\n" +
                "        final String[] projection = {\r\n" +
                "                column\r\n" +
                "        };\r\n" +
                "\r\n" +
                "        try (Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null)) {\r\n" +
                "            if (cursor != null && cursor.moveToFirst()) {\r\n" +
                "                final int column_index = cursor.getColumnIndexOrThrow(column);\r\n" +
                "                return cursor.getString(column_index);\r\n" +
                "            }\r\n" +
                "        } catch (Exception e) {\r\n" +
                "\r\n" +
                "        }\r\n" +
                "        return null;\r\n" +
                "    }\r\n" +
                "\r\n" +
                "\r\n" +
                "    private static boolean isExternalStorageDocument(Uri uri) {\r\n" +
                "        return \"com.android.externalstorage.documents\".equals(uri.getAuthority());\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    private static boolean isDownloadsDocument(Uri uri) {\r\n" +
                "        return \"com.android.providers.downloads.documents\".equals(uri.getAuthority());\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    private static boolean isMediaDocument(Uri uri) {\r\n" +
                "        return \"com.android.providers.media.documents\".equals(uri.getAuthority());\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    private static void saveBitmap(Bitmap bitmap, String destPath) {\r\n" +
                "        FileUtil.createNewFile(destPath);\r\n" +
                "        try (FileOutputStream out = new FileOutputStream(new File(destPath))) {\r\n" +
                "            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);\r\n" +
                "        } catch (Exception e) {\r\n" +
                "            e.printStackTrace();\r\n" +
                "        }\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static Bitmap getScaledBitmap(String path, int max) {\r\n" +
                "        Bitmap src = BitmapFactory.decodeFile(path);\r\n" +
                "\r\n" +
                "        int width = src.getWidth();\r\n" +
                "        int height = src.getHeight();\r\n" +
                "        float rate = 0.0f;\r\n" +
                "\r\n" +
                "        if (width > height) {\r\n" +
                "            rate = max / (float) width;\r\n" +
                "            height = (int) (height * rate);\r\n" +
                "            width = max;\r\n" +
                "        } else {\r\n" +
                "            rate = max / (float) height;\r\n" +
                "            width = (int) (width * rate);\r\n" +
                "            height = max;\r\n" +
                "        }\r\n" +
                "\r\n" +
                "        return Bitmap.createScaledBitmap(src, width, height, true);\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {\r\n" +
                "        final int width = options.outWidth;\r\n" +
                "        final int height = options.outHeight;\r\n" +
                "        int inSampleSize = 1;\r\n" +
                "\r\n" +
                "        if (height > reqHeight || width > reqWidth) {\r\n" +
                "            final int halfHeight = height / 2;\r\n" +
                "            final int halfWidth = width / 2;\r\n" +
                "\r\n" +
                "            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {\r\n" +
                "                inSampleSize *= 2;\r\n" +
                "            }\r\n" +
                "        }\r\n" +
                "\r\n" +
                "        return inSampleSize;\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static Bitmap decodeSampleBitmapFromPath(String path, int reqWidth, int reqHeight) {\r\n" +
                "        final BitmapFactory.Options options = new BitmapFactory.Options();\r\n" +
                "        options.inJustDecodeBounds = true;\r\n" +
                "        BitmapFactory.decodeFile(path, options);\r\n" +
                "\r\n" +
                "        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);\r\n" +
                "\r\n" +
                "        options.inJustDecodeBounds = false;\r\n" +
                "        return BitmapFactory.decodeFile(path, options);\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static void resizeBitmapFileRetainRatio(String fromPath, String destPath, int max) {\r\n" +
                "        if (!isExistFile(fromPath)) return;\r\n" +
                "        Bitmap bitmap = getScaledBitmap(fromPath, max);\r\n" +
                "        saveBitmap(bitmap, destPath);\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static void resizeBitmapFileToSquare(String fromPath, String destPath, int max) {\r\n" +
                "        if (!isExistFile(fromPath)) return;\r\n" +
                "        Bitmap src = BitmapFactory.decodeFile(fromPath);\r\n" +
                "        Bitmap bitmap = Bitmap.createScaledBitmap(src, max, max, true);\r\n" +
                "        saveBitmap(bitmap, destPath);\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static void resizeBitmapFileToCircle(String fromPath, String destPath) {\r\n" +
                "        if (!isExistFile(fromPath)) return;\r\n" +
                "        Bitmap src = BitmapFactory.decodeFile(fromPath);\r\n" +
                "        Bitmap bitmap = Bitmap.createBitmap(src.getWidth(),\r\n" +
                "                src.getHeight(), Bitmap.Config.ARGB_8888);\r\n" +
                "        Canvas canvas = new Canvas(bitmap);\r\n" +
                "\r\n" +
                "        final int color = 0xff424242;\r\n" +
                "        final Paint paint = new Paint();\r\n" +
                "        final Rect rect = new Rect(0, 0, src.getWidth(), src.getHeight());\r\n" +
                "\r\n" +
                "        paint.setAntiAlias(true);\r\n" +
                "        canvas.drawARGB(0, 0, 0, 0);\r\n" +
                "        paint.setColor(color);\r\n" +
                "        canvas.drawCircle(src.getWidth() / 2, src.getHeight() / 2,\r\n" +
                "                src.getWidth() / 2, paint);\r\n" +
                "        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));\r\n" +
                "        canvas.drawBitmap(src, rect, rect, paint);\r\n" +
                "\r\n" +
                "        saveBitmap(bitmap, destPath);\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static void resizeBitmapFileWithRoundedBorder(String fromPath, String destPath, int pixels) {\r\n" +
                "        if (!isExistFile(fromPath)) return;\r\n" +
                "        Bitmap src = BitmapFactory.decodeFile(fromPath);\r\n" +
                "        Bitmap bitmap = Bitmap.createBitmap(src.getWidth(), src\r\n" +
                "                .getHeight(), Bitmap.Config.ARGB_8888);\r\n" +
                "        Canvas canvas = new Canvas(bitmap);\r\n" +
                "\r\n" +
                "        final int color = 0xff424242;\r\n" +
                "        final Paint paint = new Paint();\r\n" +
                "        final Rect rect = new Rect(0, 0, src.getWidth(), src.getHeight());\r\n" +
                "        final RectF rectF = new RectF(rect);\r\n" +
                "        final float roundPx = pixels;\r\n" +
                "\r\n" +
                "        paint.setAntiAlias(true);\r\n" +
                "        canvas.drawARGB(0, 0, 0, 0);\r\n" +
                "        paint.setColor(color);\r\n" +
                "        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);\r\n" +
                "\r\n" +
                "        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));\r\n" +
                "        canvas.drawBitmap(src, rect, rect, paint);\r\n" +
                "\r\n" +
                "        saveBitmap(bitmap, destPath);\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static void cropBitmapFileFromCenter(String fromPath, String destPath, int w, int h) {\r\n" +
                "        if (!isExistFile(fromPath)) return;\r\n" +
                "        Bitmap src = BitmapFactory.decodeFile(fromPath);\r\n" +
                "\r\n" +
                "        int width = src.getWidth();\r\n" +
                "        int height = src.getHeight();\r\n" +
                "\r\n" +
                "        if (width < w && height < h) return;\r\n" +
                "\r\n" +
                "        int x = 0;\r\n" +
                "        int y = 0;\r\n" +
                "\r\n" +
                "        if (width > w) x = (width - w) / 2;\r\n" +
                "\r\n" +
                "        if (height > h) y = (height - h) / 2;\r\n" +
                "\r\n" +
                "        int cw = w;\r\n" +
                "        int ch = h;\r\n" +
                "\r\n" +
                "        if (w > width) cw = width;\r\n" +
                "\r\n" +
                "        if (h > height) ch = height;\r\n" +
                "\r\n" +
                "        Bitmap bitmap = Bitmap.createBitmap(src, x, y, cw, ch);\r\n" +
                "        saveBitmap(bitmap, destPath);\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static void rotateBitmapFile(String fromPath, String destPath, float angle) {\r\n" +
                "        if (!isExistFile(fromPath)) return;\r\n" +
                "        Bitmap src = BitmapFactory.decodeFile(fromPath);\r\n" +
                "        Matrix matrix = new Matrix();\r\n" +
                "        matrix.postRotate(angle);\r\n" +
                "        Bitmap bitmap = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);\r\n" +
                "        saveBitmap(bitmap, destPath);\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static void scaleBitmapFile(String fromPath, String destPath, float x, float y) {\r\n" +
                "        if (!isExistFile(fromPath)) return;\r\n" +
                "        Bitmap src = BitmapFactory.decodeFile(fromPath);\r\n" +
                "        Matrix matrix = new Matrix();\r\n" +
                "        matrix.postScale(x, y);\r\n" +
                "\r\n" +
                "        int w = src.getWidth();\r\n" +
                "        int h = src.getHeight();\r\n" +
                "\r\n" +
                "        Bitmap bitmap = Bitmap.createBitmap(src, 0, 0, w, h, matrix, true);\r\n" +
                "        saveBitmap(bitmap, destPath);\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static void skewBitmapFile(String fromPath, String destPath, float x, float y) {\r\n" +
                "        if (!isExistFile(fromPath)) return;\r\n" +
                "        Bitmap src = BitmapFactory.decodeFile(fromPath);\r\n" +
                "        Matrix matrix = new Matrix();\r\n" +
                "        matrix.postSkew(x, y);\r\n" +
                "\r\n" +
                "        int w = src.getWidth();\r\n" +
                "        int h = src.getHeight();\r\n" +
                "\r\n" +
                "        Bitmap bitmap = Bitmap.createBitmap(src, 0, 0, w, h, matrix, true);\r\n" +
                "        saveBitmap(bitmap, destPath);\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static void setBitmapFileColorFilter(String fromPath, String destPath, int color) {\r\n" +
                "        if (!isExistFile(fromPath)) return;\r\n" +
                "        Bitmap src = BitmapFactory.decodeFile(fromPath);\r\n" +
                "        Bitmap bitmap = Bitmap.createBitmap(src, 0, 0,\r\n" +
                "                src.getWidth() - 1, src.getHeight() - 1);\r\n" +
                "        Paint p = new Paint();\r\n" +
                "        ColorFilter filter = new LightingColorFilter(color, 1);\r\n" +
                "        p.setColorFilter(filter);\r\n" +
                "        Canvas canvas = new Canvas(bitmap);\r\n" +
                "        canvas.drawBitmap(bitmap, 0, 0, p);\r\n" +
                "        saveBitmap(bitmap, destPath);\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static void setBitmapFileBrightness(String fromPath, String destPath, float brightness) {\r\n" +
                "        if (!isExistFile(fromPath)) return;\r\n" +
                "        Bitmap src = BitmapFactory.decodeFile(fromPath);\r\n" +
                "        ColorMatrix cm = new ColorMatrix(new float[]\r\n" +
                "                {\r\n" +
                "                        1, 0, 0, 0, brightness,\r\n" +
                "                        0, 1, 0, 0, brightness,\r\n" +
                "                        0, 0, 1, 0, brightness,\r\n" +
                "                        0, 0, 0, 1, 0\r\n" +
                "                });\r\n" +
                "\r\n" +
                "        Bitmap bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());\r\n" +
                "        Canvas canvas = new Canvas(bitmap);\r\n" +
                "        Paint paint = new Paint();\r\n" +
                "        paint.setColorFilter(new ColorMatrixColorFilter(cm));\r\n" +
                "        canvas.drawBitmap(src, 0, 0, paint);\r\n" +
                "        saveBitmap(bitmap, destPath);\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static void setBitmapFileContrast(String fromPath, String destPath, float contrast) {\r\n" +
                "        if (!isExistFile(fromPath)) return;\r\n" +
                "        Bitmap src = BitmapFactory.decodeFile(fromPath);\r\n" +
                "        ColorMatrix cm = new ColorMatrix(new float[]\r\n" +
                "                {\r\n" +
                "                        contrast, 0, 0, 0, 0,\r\n" +
                "                        0, contrast, 0, 0, 0,\r\n" +
                "                        0, 0, contrast, 0, 0,\r\n" +
                "                        0, 0, 0, 1, 0\r\n" +
                "                });\r\n" +
                "\r\n" +
                "        Bitmap bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());\r\n" +
                "        Canvas canvas = new Canvas(bitmap);\r\n" +
                "        Paint paint = new Paint();\r\n" +
                "        paint.setColorFilter(new ColorMatrixColorFilter(cm));\r\n" +
                "        canvas.drawBitmap(src, 0, 0, paint);\r\n" +
                "\r\n" +
                "        saveBitmap(bitmap, destPath);\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static int getJpegRotate(String filePath) {\r\n" +
                "        int rotate = 0;\r\n" +
                "        try {\r\n" +
                "            ExifInterface exif = new ExifInterface(filePath);\r\n" +
                "            int iOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);\r\n" +
                "\r\n" +
                "            switch (iOrientation) {\r\n" +
                "                case ExifInterface.ORIENTATION_ROTATE_90:\r\n" +
                "                    rotate = 90;\r\n" +
                "                    break;\r\n" +
                "\r\n" +
                "                case ExifInterface.ORIENTATION_ROTATE_180:\r\n" +
                "                    rotate = 180;\r\n" +
                "                    break;\r\n" +
                "\r\n" +
                "                case ExifInterface.ORIENTATION_ROTATE_270:\r\n" +
                "                    rotate = 270;\r\n" +
                "                    break;\r\n" +
                "            }\r\n" +
                "        } catch (IOException e) {\r\n" +
                "            return 0;\r\n" +
                "        }\r\n" +
                "\r\n" +
                "        return rotate;\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static File createNewPictureFile(Context context) {\r\n" +
                "        SimpleDateFormat date = new SimpleDateFormat(\"yyyyMMdd_HHmmss\");\r\n" +
                "        String fileName = date.format(new Date()) + \".jpg\";\r\n" +
                "        return new File(context.getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator + fileName);\r\n" +
                "    }\r\n" +
                "}\r\n";
    }

    /**
     * @return Content of a <code>GoogleMapController.java</code> file, without indentation
     */
    public static String f(String packageName) {
        return "package " + packageName + ";\r\n" +
                "\r\n" +
                "import com.google.android.gms.maps.CameraUpdateFactory;\r\n" +
                "import com.google.android.gms.maps.GoogleMap;\r\n" +
                "import com.google.android.gms.maps.MapView;\r\n" +
                "import com.google.android.gms.maps.OnMapReadyCallback;\r\n" +
                "import com.google.android.gms.maps.model.BitmapDescriptorFactory;\r\n" +
                "import com.google.android.gms.maps.model.LatLng;\r\n" +
                "import com.google.android.gms.maps.model.Marker;\r\n" +
                "import com.google.android.gms.maps.model.MarkerOptions;\r\n" +
                "\r\n" +
                "import java.util.HashMap;\r\n" +
                "\r\n" +
                "public class GoogleMapController {\r\n" +
                "\r\n" +
                "private GoogleMap googleMap;\r\n" +
                "private MapView mapView;\r\n" +
                "private HashMap<String, Marker> mapMarker;\r\n" +
                "private GoogleMap.OnMarkerClickListener onMarkerClickListener;\r\n" +
                "\r\n" +
                "public GoogleMapController(MapView mapView, OnMapReadyCallback onMapReadyCallback) {\r\n" +
                "this.mapView = mapView;\r\n" +
                "mapMarker = new HashMap<>();\r\n" +
                "\r\n" +
                "this.mapView.getMapAsync(onMapReadyCallback);\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void setGoogleMap(GoogleMap googleMap) {\r\n" +
                "this.googleMap = googleMap;\r\n" +
                "\r\n" +
                "if (onMarkerClickListener != null) {\r\n" +
                "this.googleMap.setOnMarkerClickListener(onMarkerClickListener);\r\n" +
                "}\r\n" +
                "}\r\n" +
                "\r\n" +
                "public GoogleMap getGoogleMap() {\r\n" +
                "return googleMap;\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void setMapType(int _mapType) {\r\n" +
                "if (googleMap == null) return;\r\n" +
                "\r\n" +
                "googleMap.setMapType(_mapType);\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void setOnMarkerClickListener(GoogleMap.OnMarkerClickListener onMarkerClickListener) {\r\n" +
                "this.onMarkerClickListener = onMarkerClickListener;\r\n" +
                "\r\n" +
                "if (googleMap != null) {\r\n" +
                "this.googleMap.setOnMarkerClickListener(onMarkerClickListener);\r\n" +
                "}\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void addMarker(String id, double lat, double lng) {\r\n" +
                "if (googleMap == null) return;\r\n" +
                "\r\n" +
                "MarkerOptions markerOptions = new MarkerOptions();\r\n" +
                "markerOptions.position(new LatLng(lat, lng));\r\n" +
                "Marker marker = googleMap.addMarker(markerOptions);\r\n" +
                "marker.setTag(id);\r\n" +
                "mapMarker.put(id, marker);\r\n" +
                "}\r\n" +
                "\r\n" +
                "public Marker getMarker(String id) {\r\n" +
                "return mapMarker.get(id);\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void setMarkerInfo(String id, String title, String snippet) {\r\n" +
                "Marker marker = mapMarker.get(id);\r\n" +
                "if (marker == null) return;\r\n" +
                "\r\n" +
                "marker.setTitle(title);\r\n" +
                "marker.setSnippet(snippet);\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void setMarkerPosition(String id, double lat, double lng) {\r\n" +
                "Marker marker = mapMarker.get(id);\r\n" +
                "if (marker == null) return;\r\n" +
                "\r\n" +
                "marker.setPosition(new LatLng(lat, lng));\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void setMarkerColor(String id, float color, double alpha) {\r\n" +
                "Marker marker = mapMarker.get(id);\r\n" +
                "if (marker == null) return;\r\n" +
                "\r\n" +
                "marker.setAlpha((float) alpha);\r\n" +
                "marker.setIcon(BitmapDescriptorFactory.defaultMarker(color));\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void setMarkerIcon(String id, int resIcon) {\r\n" +
                "Marker marker = mapMarker.get(id);\r\n" +
                "if (marker == null) return;\r\n" +
                "\r\n" +
                "marker.setIcon(BitmapDescriptorFactory.fromResource(resIcon));\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void setMarkerVisible(String id, boolean visible) {\r\n" +
                "Marker marker = mapMarker.get(id);\r\n" +
                "if (marker == null) return;\r\n" +
                "\r\n" +
                "marker.setVisible(visible);\r\n" +
                "}\r\n" +
                "\r\n" +
                "\r\n" +
                "public void moveCamera(double lat, double lng) {\r\n" +
                "if (googleMap == null) return;\r\n" +
                "\r\n" +
                "googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void zoomTo(double zoom) {\r\n" +
                "if (googleMap == null) return;\r\n" +
                "\r\n" +
                "googleMap.moveCamera(CameraUpdateFactory.zoomTo((float) zoom));\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void zoomIn() {\r\n" +
                "if (googleMap == null) return;\r\n" +
                "\r\n" +
                "googleMap.moveCamera(CameraUpdateFactory.zoomIn());\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void zoomOut() {\r\n" +
                "if (googleMap == null) return;\r\n" +
                "\r\n" +
                "googleMap.moveCamera(CameraUpdateFactory.zoomOut());\r\n" +
                "}\r\n" +
                "}\r\n";
    }

    /**
     * @return Content of a <code>RequestNetworkController.java</code> file, without indentation
     */
    public static String g(String packageName) {
        return "package " + packageName + ";\r\n" +
                "\r\n" +
                "import com.google.gson.Gson;\r\n" +
                "\r\n" +
                "import java.io.IOException;\r\n" +
                "import java.security.SecureRandom;\r\n" +
                "import java.security.cert.CertificateException;\r\n" +
                "import java.security.cert.X509Certificate;\r\n" +
                "import java.util.HashMap;\r\n" +
                "import java.util.concurrent.TimeUnit;\r\n" +
                "\r\n" +
                "import javax.net.ssl.HostnameVerifier;\r\n" +
                "import javax.net.ssl.SSLContext;\r\n" +
                "import javax.net.ssl.SSLSession;\r\n" +
                "import javax.net.ssl.SSLSocketFactory;\r\n" +
                "import javax.net.ssl.TrustManager;\r\n" +
                "import javax.net.ssl.X509TrustManager;\r\n" +
                "\r\n" +
                "import okhttp3.Call;\r\n" +
                "import okhttp3.Callback;\r\n" +
                "import okhttp3.FormBody;\r\n" +
                "import okhttp3.Headers;\r\n" +
                "import okhttp3.HttpUrl;\r\n" +
                "import okhttp3.MediaType;\r\n" +
                "import okhttp3.OkHttpClient;\r\n" +
                "import okhttp3.Request;\r\n" +
                "import okhttp3.RequestBody;\r\n" +
                "import okhttp3.Response;\r\n" +
                "\r\n" +
                "public class RequestNetworkController {\r\n" +
                "public static final String GET = \"GET\";\r\n" +
                "public static final String POST = \"POST\";\r\n" +
                "public static final String PUT = \"PUT\";\r\n" +
                "public static final String DELETE = \"DELETE\";\r\n" +
                "\r\n" +
                "public static final int REQUEST_PARAM = 0;\r\n" +
                "public static final int REQUEST_BODY = 1;\r\n" +
                "\r\n" +
                "private static final int SOCKET_TIMEOUT = 15000;\r\n" +
                "private static final int READ_TIMEOUT = 25000;\r\n" +
                "\r\n" +
                "protected OkHttpClient client;\r\n" +
                "\r\n" +
                "private static RequestNetworkController mInstance;\r\n" +
                "\r\n" +
                "public static synchronized RequestNetworkController getInstance() {\r\n" +
                "if(mInstance == null) {\r\n" +
                "mInstance = new RequestNetworkController();\r\n" +
                "}\r\n" +
                "return mInstance;\r\n" +
                "}\r\n" +
                "\r\n" +
                "private OkHttpClient getClient() {\r\n" +
                "if (client == null) {\r\n" +
                "OkHttpClient.Builder builder = new OkHttpClient.Builder();\r\n" +
                "\r\n" +
                "try {\r\n" +
                "final TrustManager[] trustAllCerts = new TrustManager[]{\r\n" +
                "new X509TrustManager() {\r\n" +
                "@Override\r\n" +
                "public void checkClientTrusted(X509Certificate[] chain, String authType)\r\n" +
                "throws CertificateException {\r\n" +
                "}\r\n" +
                "\r\n" +
                "@Override\r\n" +
                "public void checkServerTrusted(X509Certificate[] chain, String authType)\r\n" +
                "throws CertificateException {\r\n" +
                "}\r\n" +
                "\r\n" +
                "@Override\r\n" +
                "public X509Certificate[] getAcceptedIssuers() {\r\n" +
                "return new X509Certificate[]{};\r\n" +
                "}\r\n" +
                "}\r\n" +
                "};\r\n" +
                "\r\n" +
                "final SSLContext sslContext = SSLContext.getInstance(\"TLS\");\r\n" +
                "sslContext.init(null, trustAllCerts, new SecureRandom());\r\n" +
                "final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();\r\n" +
                "builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);\r\n" +
                "builder.connectTimeout(SOCKET_TIMEOUT, TimeUnit.MILLISECONDS);\r\n" +
                "builder.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);\r\n" +
                "builder.writeTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);\r\n" +
                "builder.hostnameVerifier(new HostnameVerifier() {\r\n" +
                "@Override\r\n" +
                "public boolean verify(String hostname, SSLSession session) {\r\n" +
                "return true;\r\n" +
                "}\r\n" +
                "});\r\n" +
                "} catch (Exception e) {\r\n" +
                "}\r\n" +
                "\r\n" +
                "client = builder.build();\r\n" +
                "}\r\n" +
                "\r\n" +
                "return client;\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void execute(final RequestNetwork requestNetwork, String method, String url, final String tag, final RequestNetwork.RequestListener requestListener) {\r\n" +
                "Request.Builder reqBuilder = new Request.Builder();\r\n" +
                "Headers.Builder headerBuilder = new Headers.Builder();\r\n" +
                "\r\n" +
                "if (requestNetwork.getHeaders().size() > 0) {\r\n" +
                "HashMap<String, Object> headers = requestNetwork.getHeaders();\r\n" +
                "\r\n" +
                "for (HashMap.Entry<String, Object> header : headers.entrySet()) {\r\n" +
                "headerBuilder.add(header.getKey(), String.valueOf(header.getValue()));\r\n" +
                "}\r\n" +
                "}\r\n" +
                "\r\n" +
                "try {\r\n" +
                "if (requestNetwork.getRequestType() == REQUEST_PARAM) {\r\n" +
                "if (method.equals(GET)) {\r\n" +
                "HttpUrl.Builder httpBuilder;\r\n" +
                "\r\n" +
                "try {\r\n" +
                "httpBuilder = HttpUrl.parse(url).newBuilder();\r\n" +
                "} catch (NullPointerException ne) {\r\n" +
                "throw new NullPointerException(\"unexpected url: \" + url);\r\n" +
                "}\r\n" +
                "\r\n" +
                "if (requestNetwork.getParams().size() > 0) {\r\n" +
                "HashMap<String, Object> params = requestNetwork.getParams();\r\n" +
                "\r\n" +
                "for (HashMap.Entry<String, Object> param : params.entrySet()) {\r\n" +
                "httpBuilder.addQueryParameter(param.getKey(), String.valueOf(param.getValue()));\r\n" +
                "}\r\n" +
                "}\r\n" +
                "\r\n" +
                "reqBuilder.url(httpBuilder.build()).headers(headerBuilder.build()).get();\r\n" +
                "} else {\r\n" +
                "FormBody.Builder formBuilder = new FormBody.Builder();\r\n" +
                "if (requestNetwork.getParams().size() > 0) {\r\n" +
                "HashMap<String, Object> params = requestNetwork.getParams();\r\n" +
                "\r\n" +
                "for (HashMap.Entry<String, Object> param : params.entrySet()) {\r\n" +
                "formBuilder.add(param.getKey(), String.valueOf(param.getValue()));\r\n" +
                "}\r\n" +
                "}\r\n" +
                "\r\n" +
                "RequestBody reqBody = formBuilder.build();\r\n" +
                "\r\n" +
                "reqBuilder.url(url).headers(headerBuilder.build()).method(method, reqBody);\r\n" +
                "}\r\n" +
                "} else {\r\n" +
                "RequestBody reqBody = RequestBody.create(MediaType.parse(\"application/json\"), new Gson().toJson(requestNetwork.getParams()));\r\n" +
                "\r\n" +
                "if (method.equals(GET)) {\r\n" +
                "reqBuilder.url(url).headers(headerBuilder.build()).get();\r\n" +
                "} else {\r\n" +
                "reqBuilder.url(url).headers(headerBuilder.build()).method(method, reqBody);\r\n" +
                "}\r\n" +
                "}\r\n" +
                "\r\n" +
                "Request req = reqBuilder.build();\r\n" +
                "\r\n" +
                "getClient().newCall(req).enqueue(new Callback() {\r\n" +
                "@Override\r\n" +
                "public void onFailure(Call call, final IOException e) {\r\n" +
                "requestNetwork.getActivity().runOnUiThread(new Runnable() {\r\n" +
                "@Override\r\n" +
                "public void run() {\r\n" +
                "requestListener.onErrorResponse(tag, e.getMessage());\r\n" +
                "}\r\n" +
                "});\r\n" +
                "}\r\n" +
                "\r\n" +
                "@Override\r\n" +
                "public void onResponse(Call call, final Response response) throws IOException {\r\n" +
                "final String responseBody = response.body().string().trim();\r\n" +
                "requestNetwork.getActivity().runOnUiThread(new Runnable() {\r\n" +
                "@Override\r\n" +
                "public void run() {\r\n" +
                "Headers b = response.headers();\r\n" +
                "HashMap<String, Object> map = new HashMap<>();\r\n" +
                "for (String s : b.names()) {\r\n" +
                "map.put(s, b.get(s) != null ? b.get(s) : \"null\");\r\n" +
                "}\r\n" +
                "requestListener.onResponse(tag, responseBody, map);\r\n" +
                "}\r\n" +
                "});\r\n" +
                "}\r\n" +
                "});\r\n" +
                "} catch (Exception e) {\r\n" +
                "requestListener.onErrorResponse(tag, e.getMessage());\r\n" +
                "}\r\n" +
                "}\r\n" +
                "}\r\n";
    }

    /**
     * @return Content of a <code>RequestNetwork.java</code> file, without indentation
     */
    public static String h(String packageName) {
        return "package " + packageName + ";\r\n" +
                "\r\n" +
                "import android.app.Activity;\r\n" +
                "\r\n" +
                "import java.util.HashMap;\r\n" +
                "\r\n" +
                "public class RequestNetwork {\r\n" +
                "private HashMap<String, Object> params = new HashMap<>();\r\n" +
                "private HashMap<String, Object> headers = new HashMap<>();\r\n" +
                "\r\n" +
                "private Activity activity;\r\n" +
                "\r\n" +
                "private int requestType = 0;\r\n" +
                "\r\n" +
                "public RequestNetwork(Activity activity) {\r\n" +
                "this.activity = activity;\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void setHeaders(HashMap<String, Object> headers) {\r\n" +
                "this.headers = headers;\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void setParams(HashMap<String, Object> params, int requestType) {\r\n" +
                "this.params = params;\r\n" +
                "this.requestType = requestType;\r\n" +
                "}\r\n" +
                "\r\n" +
                "public HashMap<String, Object> getParams() {\r\n" +
                "return params;\r\n" +
                "}\r\n" +
                "\r\n" +
                "public HashMap<String, Object> getHeaders() {\r\n" +
                "return headers;\r\n" +
                "}\r\n" +
                "\r\n" +
                "public Activity getActivity() {\r\n" +
                "return activity;\r\n" +
                "}\r\n" +
                "\r\n" +
                "public int getRequestType() {\r\n" +
                "return requestType;\r\n" +
                "}\r\n" +
                "\r\n" +
                "public void startRequestNetwork(String method, String url, String tag, RequestListener requestListener) {\r\n" +
                "RequestNetworkController.getInstance().execute(this, method, url, tag, requestListener);\r\n" +
                "}\r\n" +
                "\r\n" +
                "public interface RequestListener {\r\n" +
                "public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders);\r\n" +
                "public void onErrorResponse(String tag, String message);\r\n" +
                "}\r\n" +
                "}\r\n";
    }

    /**
     * @return Content of a <code>SketchwareUtil.java</code> file, with indentation
     */
    public static String i(String packageName) {
        return "package " + packageName + ";\r\n" +
                "import android.app.*;\r\n" +
                "import android.content.*;\r\n" +
                "import android.graphics.drawable.*;\r\n" +
                "import android.net.*;\r\n" +
                "import android.util.*;\r\n" +
                "import android.view.*;\r\n" +
                "import android.view.inputmethod.*;\r\n" +
                "import android.widget.*;\r\n" +
                "\r\n" +
                "import java.io.*;\r\n" +
                "import java.util.*;\n" +
                "\r\n" +
                "public class SketchwareUtil {\r\n" +
                "\r\n" +
                "    public static int TOP = 1;\r\n" +
                "    public static int CENTER = 2;\r\n" +
                "    public static int BOTTOM = 3;\r\n" +
                "\r\n" +
                "    public static void CustomToast(Context _context, String _message, int _textColor, int _textSize, int _bgColor, int _radius, int _gravity) {\r\n" +
                "        Toast _toast = Toast.makeText(_context, _message, Toast.LENGTH_SHORT);\r\n" +
                "        View _view = _toast.getView();\r\n" +
                "        TextView _textView = _view.findViewById(android.R.id.message);\r\n" +
                "        _textView.setTextSize(_textSize);\r\n" +
                "        _textView.setTextColor(_textColor);\r\n" +
                "        _textView.setGravity(Gravity.CENTER);\r\n" +
                "\r\n" +
                "        GradientDrawable _gradientDrawable = new GradientDrawable();\r\n" +
                "        _gradientDrawable.setColor(_bgColor);\r\n" +
                "        _gradientDrawable.setCornerRadius(_radius);\r\n" +
                "        _view.setBackgroundDrawable(_gradientDrawable);\r\n" +
                "        _view.setPadding(15, 10, 15, 10);\r\n" +
                "        _view.setElevation(10);\r\n" +
                "\r\n" +
                "        switch (_gravity) {\r\n" +
                "            case 1:\r\n" +
                "                _toast.setGravity(Gravity.TOP, 0, 150);\r\n" +
                "                break;\r\n" +
                "\r\n" +
                "            case 2:\r\n" +
                "                _toast.setGravity(Gravity.CENTER, 0, 0);\r\n" +
                "                break;\r\n" +
                "\r\n" +
                "            case 3:\r\n" +
                "                _toast.setGravity(Gravity.BOTTOM, 0, 150);\r\n" +
                "                break;\r\n" +
                "        }\r\n" +
                "        _toast.show();\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static void CustomToastWithIcon(Context _context, String _message, int _textColor, int _textSize, int _bgColor, int _radius, int _gravity, int _icon) {\r\n" +
                "        Toast _toast = Toast.makeText(_context, _message, Toast.LENGTH_SHORT);\r\n" +
                "        View _view = _toast.getView();\r\n" +
                "        TextView _textView = (TextView) _view.findViewById(android.R.id.message);\r\n" +
                "        _textView.setTextSize(_textSize);\r\n" +
                "        _textView.setTextColor(_textColor);\r\n" +
                "        _textView.setCompoundDrawablesWithIntrinsicBounds(_icon, 0, 0, 0);\r\n" +
                "        _textView.setGravity(Gravity.CENTER);\r\n" +
                "        _textView.setCompoundDrawablePadding(10);\r\n" +
                "\r\n" +
                "        GradientDrawable _gradientDrawable = new GradientDrawable();\r\n" +
                "        _gradientDrawable.setColor(_bgColor);\r\n" +
                "        _gradientDrawable.setCornerRadius(_radius);\r\n" +
                "        _view.setBackgroundDrawable(_gradientDrawable);\r\n" +
                "        _view.setPadding(10, 10, 10, 10);\r\n" +
                "        _view.setElevation(10);\r\n" +
                "\r\n" +
                "        switch (_gravity) {\r\n" +
                "            case 1:\r\n" +
                "                _toast.setGravity(Gravity.TOP, 0, 150);\r\n" +
                "                break;\r\n" +
                "\r\n" +
                "            case 2:\r\n" +
                "                _toast.setGravity(Gravity.CENTER, 0, 0);\r\n" +
                "                break;\r\n" +
                "\r\n" +
                "            case 3:\r\n" +
                "                _toast.setGravity(Gravity.BOTTOM, 0, 150);\r\n" +
                "                break;\r\n" +
                "        }\r\n" +
                "        _toast.show();\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static void sortListMap(final ArrayList<HashMap<String, Object>> listMap, final String key, final boolean isNumber, final boolean ascending) {\r\n" +
                "        Collections.sort(listMap, new Comparator<HashMap<String, Object>>() {\r\n" +
                "            public int compare(HashMap<String, Object> _compareMap1, HashMap<String, Object> _compareMap2) {\r\n" +
                "                if (isNumber) {\r\n" +
                "                    int _count1 = Integer.valueOf(_compareMap1.get(key).toString());\r\n" +
                "                    int _count2 = Integer.valueOf(_compareMap2.get(key).toString());\r\n" +
                "                    if (ascending) {\r\n" +
                "                        return _count1 < _count2 ? -1 : _count1 < _count2 ? 1 : 0;\r\n" +
                "                    } else {\r\n" +
                "                        return _count1 > _count2 ? -1 : _count1 > _count2 ? 1 : 0;\r\n" +
                "                    }\r\n" +
                "                } else {\r\n" +
                "                    if (ascending) {\r\n" +
                "                        return (_compareMap1.get(key).toString()).compareTo(_compareMap2.get(key).toString());\r\n" +
                "                    } else {\r\n" +
                "                        return (_compareMap2.get(key).toString()).compareTo(_compareMap1.get(key).toString());\r\n" +
                "                    }\r\n" +
                "                }\r\n" +
                "            }\r\n" +
                "        });\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static void CropImage(Activity _activity, String _path, int _requestCode) {\r\n" +
                "        try {\r\n" +
                "            Intent _intent = new Intent(\"com.android.camera.action.CROP\");\r\n" +
                "            File _file = new File(_path);\r\n" +
                "            Uri _contentUri = Uri.fromFile(_file);\r\n" +
                "            _intent.setDataAndType(_contentUri, \"image/*\");\r\n" +
                "            _intent.putExtra(\"crop\", \"true\");\r\n" +
                "            _intent.putExtra(\"aspectX\", 1);\r\n" +
                "            _intent.putExtra(\"aspectY\", 1);\r\n" +
                "            _intent.putExtra(\"outputX\", 280);\r\n" +
                "            _intent.putExtra(\"outputY\", 280);\r\n" +
                "            _intent.putExtra(\"return-data\", false);\r\n" +
                "            _activity.startActivityForResult(_intent, _requestCode);\r\n" +
                "        } catch (ActivityNotFoundException _e) {\r\n" +
                "            Toast.makeText(_activity, \"Your device doesn't support the crop action!\", Toast.LENGTH_SHORT).show();\r\n" +
                "        }\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static boolean isConnected(Context _context) {\r\n" +
                "        ConnectivityManager _connectivityManager = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);\r\n" +
                "        NetworkInfo _activeNetworkInfo = _connectivityManager.getActiveNetworkInfo();\r\n" +
                "        return _activeNetworkInfo != null && _activeNetworkInfo.isConnected();\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static String copyFromInputStream(InputStream _inputStream) {\r\n" +
                "        ByteArrayOutputStream _outputStream = new ByteArrayOutputStream();\r\n" +
                "        byte[] _buf = new byte[1024];\r\n" +
                "        int _i;\r\n" +
                "        try {\r\n" +
                "            while ((_i = _inputStream.read(_buf)) != -1){\r\n" +
                "                _outputStream.write(_buf, 0, _i);\r\n" +
                "            }\r\n" +
                "            _outputStream.close();\r\n" +
                "            _inputStream.close();\r\n" +
                "        } catch (IOException _e) {\r\n" +
                "        }\r\n" +
                "        \r\n" +
                "        return _outputStream.toString();\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static void hideKeyboard(Context _context) {\r\n" +
                "        InputMethodManager _inputMethodManager = (InputMethodManager) _context.getSystemService(Context.INPUT_METHOD_SERVICE);\r\n" +
                "        _inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);\r\n" +
                "    }\r\n" +
                "    \r\n" +
                "    public static void showKeyboard(Context _context) {\r\n" +
                "        InputMethodManager _inputMethodManager = (InputMethodManager) _context.getSystemService(Context.INPUT_METHOD_SERVICE);\r\n" +
                "        _inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);\r\n" +
                "    }\r\n" +
                "    \r\n" +
                "    public static void showMessage(Context _context, String _s) {\r\n" +
                "        Toast.makeText(_context, _s, Toast.LENGTH_SHORT).show();\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static int getLocationX(View _view) {\r\n" +
                "        int _location[] = new int[2];\r\n" +
                "        _view.getLocationInWindow(_location);\r\n" +
                "        return _location[0];\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static int getLocationY(View _view) {\r\n" +
                "        int _location[] = new int[2];\r\n" +
                "        _view.getLocationInWindow(_location);\r\n" +
                "        return _location[1];\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static int getRandom(int _min, int _max) {\r\n" +
                "        Random random = new Random();\r\n" +
                "        return random.nextInt(_max - _min + 1) + _min;\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {\r\n" +
                "        ArrayList<Double> _result = new ArrayList<Double>();\r\n" +
                "        SparseBooleanArray _arr = _list.getCheckedItemPositions();\r\n" +
                "        for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {\r\n" +
                "            if (_arr.valueAt(_iIdx))\r\n" +
                "                _result.add((double) _arr.keyAt(_iIdx));\r\n" +
                "        }\r\n" +
                "        return _result;\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static float getDip(Context _context, int _input) {\r\n" +
                "        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, _context.getResources().getDisplayMetrics());\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static int getDisplayWidthPixels(Context _context) {\r\n" +
                "        return _context.getResources().getDisplayMetrics().widthPixels;\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static int getDisplayHeightPixels(Context _context) {\r\n" +
                "        return _context.getResources().getDisplayMetrics().heightPixels;\r\n" +
                "    }\r\n" +
                "\r\n" +
                "    public static void getAllKeysFromMap(Map<String, Object> _map, ArrayList<String> _output) {\r\n" +
                "        if (_output == null) return;\r\n" +
                "        _output.clear();\r\n" +
                "        if (_map == null || _map.size() < 1) return;\r\n" +
                "        for (Map.Entry<String, Object> _entry : _map.entrySet()) {\r\n" +
                "            _output.add(_entry.getKey());\r\n" +
                "        }\r\n" +
                "    }\r\n" +
                "}\r\n";
    }

    /**
     * @return Formatted code
     */
    public static String j(String code) {
        StringBuilder formattedCode = new StringBuilder(4096);
        char[] codeChars = code.toCharArray();
        boolean var4 = false;
        boolean var5 = false;
        boolean var6 = false;
        int var7 = 0;
        boolean var8 = false;

        int var20;
        int codeIndex = 0;
        for (boolean var9 = false; codeIndex < codeChars.length; var7 = var20) {
            boolean var17;
            boolean var18;
            int var19;
            label81:
            {
                char codeBit = codeChars[codeIndex];
                boolean var11;
                int var16;
                if (var4) {
                    if (codeBit == '\n') {
                        formattedCode.append(codeBit);
                        a(formattedCode, var7);
                        var11 = false;
                        var16 = codeIndex;
                    } else {
                        formattedCode.append(codeBit);
                        var11 = true;
                        var16 = codeIndex;
                    }
                } else {
                    if (var5) {
                        label78:
                        {
                            if (codeBit == '*') {
                                var20 = codeIndex + 1;
                                char var14 = codeChars[var20];
                                if (var14 == '/') {
                                    formattedCode.append(codeBit);
                                    formattedCode.append(var14);
                                    var5 = false;
                                    var11 = false;
                                    var16 = var20;
                                    break label78;
                                }
                            }

                            formattedCode.append(codeBit);
                            var11 = false;
                            var16 = codeIndex;
                        }
                    } else if (var6) {
                        formattedCode.append(codeBit);
                        var6 = false;
                        var11 = false;
                        var16 = codeIndex;
                    } else if (codeBit == '\\') {
                        formattedCode.append(codeBit);
                        var6 = true;
                        var11 = false;
                        var16 = codeIndex;
                    } else if (var8) {
                        if (codeBit == '\'') {
                            formattedCode.append(codeBit);
                            var8 = false;
                            var11 = false;
                            var16 = codeIndex;
                        } else {
                            formattedCode.append(codeBit);
                            var11 = false;
                            var16 = codeIndex;
                        }
                    } else if (var9) {
                        if (codeBit == '"') {
                            formattedCode.append(codeBit);
                            var9 = false;
                            var11 = false;
                            var16 = codeIndex;
                        } else {
                            formattedCode.append(codeBit);
                            var11 = false;
                            var16 = codeIndex;
                        }
                    } else {
                        label87:
                        {
                            if (codeBit == '/') {
                                var20 = codeIndex + 1;
                                char var14 = codeChars[var20];
                                if (var14 == '/') {
                                    formattedCode.append(codeBit);
                                    formattedCode.append(var14);
                                    var16 = var20;
                                    var11 = true;
                                    break label87;
                                }

                                if (var14 == '*') {
                                    formattedCode.append(codeBit);
                                    formattedCode.append(var14);
                                    var5 = true;
                                    var11 = false;
                                    var16 = var20;
                                    break label87;
                                }
                            }

                            if (codeBit != '\n') {
                                if (codeBit == '\'') {
                                    var8 = true;
                                }

                                if (codeBit == '"') {
                                    var9 = true;
                                }

                                if (codeBit == '{') {
                                    var20 = var7 + 1;
                                } else {
                                    var20 = var7;
                                }

                                var7 = var20;
                                if (codeBit == '}') {
                                    --var20;
                                    var7 = var20;
                                    if (formattedCode.charAt(formattedCode.length() - 1) == '\t') {
                                        formattedCode.deleteCharAt(formattedCode.length() - 1);
                                        var7 = var20;
                                    }
                                }

                                formattedCode.append(codeBit);
                                var20 = codeIndex;
                                var5 = var8;
                                codeIndex = var7;
                                var18 = false;
                                var6 = false;
                                var19 = var20;
                                break label81;
                            }

                            formattedCode.append(codeBit);
                            a(formattedCode, var7);
                            var11 = false;
                            var16 = codeIndex;
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
                codeIndex = var7;
            }

            var20 = codeIndex;
            var17 = var18;
            var18 = var6;
            int var21 = var19 + 1;
            var8 = var5;
            var6 = var17;
            var5 = var18;
            codeIndex = var21;
        }

        return formattedCode.toString();
    }

    public static String pagerAdapter(String pagerName, String pagerItemLayoutName, ArrayList<ViewBean> pagerItemViews, String onBindCustomViewLogic) {
        String adapterName = a(pagerName);
        Iterator<ViewBean> viewIterator = pagerItemViews.iterator();

        StringBuilder viewsInitializer;
        if (viewIterator.hasNext()) {
            viewsInitializer = new StringBuilder(a(viewIterator.next()))
                    .append("\r\n");
            while (viewIterator.hasNext()) {
                viewsInitializer
                        .append(a(viewIterator.next()))
                        .append("\r\n");
            }
        } else {
            viewsInitializer = new StringBuilder();
        }

        String baseCode = "public class " + adapterName + " extends PagerAdapter {\r\n" +
                "\r\n" +
                "Context _context;\r\n" +
                "ArrayList<HashMap<String, Object>> _data;\r\n" +
                "\r\n" +
                "public " + adapterName + "(Context _ctx, ArrayList<HashMap<String, Object>> _arr) {\r\n" +
                "_context = _ctx;\r\n" +
                "_data = _arr;\r\n" +
                "}\r\n" +
                "\r\n" +
                "public " + adapterName + "(ArrayList<HashMap<String, Object>> _arr) {\r\n" +
                "_context = getApplicationContext();\r\n" +
                "_data = _arr;\r\n" +
                "}\r\n" +
                "\r\n" +
                "@Override\r\n" +
                "public int getCount() {\r\n" +
                "return _data.size();\r\n" +
                "}\r\n" +
                "\r\n" +
                "@Override\r\n" +
                "public boolean isViewFromObject(View _view, Object _object) {\r\n" +
                "return _view == _object;\r\n" +
                "}\r\n" +
                "\r\n" +
                "@Override\r\n" +
                "public void destroyItem(ViewGroup _container, int _position, Object _object) {\r\n" +
                "_container.removeView((View) _object);\r\n" +
                "}\r\n" +
                "\r\n" +
                "@Override\r\n" +
                "public int getItemPosition(Object _object) {\r\n" +
                "return super.getItemPosition(_object);\r\n" +
                "}\r\n" +
                "\r\n" +
                "@Override\r\n" +
                "public CharSequence getPageTitle(int pos) {\r\n" +
                "return onTabLayoutNewTabAdded(pos);\r\n" +
                "}\r\n" +
                "\r\n" +
                "@Override\r\n" +
                "public Object instantiateItem(ViewGroup _container,  final int _position) {\r\n" +
                "View _view = LayoutInflater.from(_context).inflate(R.layout." + pagerItemLayoutName + ", _container, false);\r\n";

        if (!TextUtils.isEmpty(viewsInitializer)) {
            baseCode += "\r\n" +
                    viewsInitializer;
        }

        if (!TextUtils.isEmpty(onBindCustomViewLogic)) {
            baseCode += "\r\n" +
                    onBindCustomViewLogic + "\r\n";
        }

        return baseCode +
                "\r\n" +
                "_container.addView(_view);\r\n" +
                "return _view;\r\n" +
                "}\r\n" +
                "}\r\n";
    }

    public static String recyclerViewAdapter(String recyclerViewName, String itemLayoutName, ArrayList<ViewBean> itemViews, String onBindCustomViewLogic) {
        String adapterName = a(recyclerViewName);
        Iterator<ViewBean> viewIterator = itemViews.iterator();

        StringBuilder viewsInitializer;
        if (viewIterator.hasNext()) {
            viewsInitializer = new StringBuilder(a(viewIterator.next()))
                    .append("\r\n");
            while (viewIterator.hasNext()) {
                viewsInitializer
                        .append(a(viewIterator.next()))
                        .append("\r\n");
            }
        } else {
            viewsInitializer = new StringBuilder();
        }

        String baseCode = "public class " + adapterName + " extends RecyclerView.Adapter<" + adapterName + ".ViewHolder> {\r\n" +
                "\r\n" +
                "ArrayList<HashMap<String, Object>> _data;\r\n" +
                "\r\n" +
                "public " + adapterName + "(ArrayList<HashMap<String, Object>> _arr) {\r\n" +
                "_data = _arr;\r\n" +
                "}\r\n" +
                "\r\n" +
                "@Override\r\n" +
                "public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {\r\n" +
                "LayoutInflater _inflater = getLayoutInflater();\r\n" +
                "View _v = _inflater.inflate(R.layout." + itemLayoutName + ", null);\r\n" +
                "RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);\r\n" +
                "_v.setLayoutParams(_lp);\r\n" +
                "return new ViewHolder(_v);\r\n" +
                "}\r\n" +
                "\r\n" +
                "@Override\r\n" +
                "public void onBindViewHolder(ViewHolder _holder, final int _position) {\r\n" +
                "View _view = _holder.itemView;\r\n";

        if (!TextUtils.isEmpty(viewsInitializer)) {
            baseCode += "\r\n" +
                    viewsInitializer;
        }

        if (!TextUtils.isEmpty(onBindCustomViewLogic)) {
            baseCode += "\r\n" +
                    onBindCustomViewLogic + "\r\n";
        }

        return baseCode +
                "}\r\n" +
                "\r\n" +
                "@Override\r\n" +
                "public int getItemCount() {\r\n" +
                "return _data.size();\r\n" +
                "}\r\n" +
                "\r\n" +
                "public class ViewHolder extends RecyclerView.ViewHolder {\r\n" +
                "public ViewHolder(View v) {\r\n" +
                "super(v);\r\n" +
                "}\r\n" +
                "}\r\n" +
                "}\r\n";
    }

    /**
     * A field's access modifier. Can either be
     * <code>private</code>, <code>protected</code> or <code>public</code>.
     */
    enum AccessModifier {
        /**
         * MODE_PRIVATE
         */
        PRIVATE("private"),
        /**
         * MODE_PROTECTED
         */
        PROTECTED("protected"),
        /**
         * MODE_PUBLIC
         */
        PUBLIC("public");

        private final String name;

        AccessModifier(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
