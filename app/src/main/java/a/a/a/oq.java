package a.a.a;

import android.content.Context;

import java.util.ArrayList;

import mod.agus.jcoderz.editor.event.ManageEvent;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.events.EventsHandler;
import pro.sketchware.R;

public class oq {

    /**
     * The known Event names that can be added to all Activities.
     */
    public static final String[] a = EventsHandler.getActivityEvents();

    /**
     * @return The resource ID for an Event's icon
     */
    public static int a(String eventName) {
        return switch (eventName) {
            case "initializeLogic", "onBackPressed", "onPostCreate", "onStart", "onStop",
                 "onDestroy", "onResume", "onPause", "moreBlock" ->
                    R.drawable.bg_event_type_activity;
            case "onBannerAdClicked", "onClick" -> R.drawable.ic_mtrl_touch;
            case "onCheckedChange" -> R.drawable.ic_mtrl_checkbox;
            case "onItemSelected" -> R.drawable.ic_mtrl_pull_down;
            case "onItemClicked" -> R.drawable.ic_mtrl_list;
            case "onItemLongClicked" -> R.drawable.ic_mtrl_touch_long;
            case "onTextChanged" -> R.drawable.ic_mtrl_text_change;
            case "onPageStarted" -> R.drawable.ic_mtrl_progress;
            case "onPageFinished" -> R.drawable.ic_mtrl_preview;
            case "onProgressChanged" -> R.drawable.ic_mtrl_progress_check;
            case "onStartTrackingTouch" -> R.drawable.ic_mtrl_track_started;
            case "onStopTrackingTouch" -> R.drawable.ic_mtrl_target;
            case "onAnimationStart" -> R.drawable.ic_mtrl_sprint;
            case "onAnimationEnd" -> R.drawable.ic_mtrl_anim_end;
            case "onAnimationCancel" -> R.drawable.ic_mtrl_anim_cancel;
            case "onAnimationRepeat" -> R.drawable.ic_mtrl_refresh;
            case "onBindCustomView" -> R.drawable.ic_mtrl_bind;
            case "onDateChange" -> R.drawable.ic_mtrl_date_changed;
            case "onChildAdded" -> R.drawable.ic_mtrl_database_added;
            case "onChildChanged" -> R.drawable.ic_mtrl_database_edit;
            case "onChildMoved" -> R.drawable.ic_mtrl_database_moved;
            case "onChildRemoved", "onDeleteSuccess" -> R.drawable.ic_mtrl_database_off;
            case "onCancelled" -> R.drawable.ic_mtrl_cancel;
            case "onCreateUserComplete" -> R.drawable.ic_mtrl_user_create;
            case "onSignInUserComplete" -> R.drawable.ic_mtrl_signin;
            case "onResetPasswordEmailSent" -> R.drawable.ic_mtrl_reset;
            case "onSensorChanged" -> R.drawable.ic_mtrl_sensor;
            case "onAccuracyChanged" -> R.drawable.ic_mtrl_center;
            case "onInterstitialAdLoaded", "onBannerAdLoaded", "onRewardAdLoaded" ->
                    R.drawable.ic_mtrl_loaded;
            case "onBannerAdFailedToLoad", "onInterstitialAdFailedToLoad",
                 "onAdFailedToShowFullScreenContent", "onRewardAdFailedToLoad", "onFailure" ->
                    R.drawable.ic_mtrl_load_failed;
            case "onAdShowedFullScreenContent", "onBannerAdOpened" -> R.drawable.ic_mtrl_preview;
            case "onAdDismissedFullScreenContent", "onBannerAdClosed" ->
                    R.drawable.ic_mtrl_preview_off;
            case "onUploadProgress" -> R.drawable.ic_mtrl_uploading;
            case "onDownloadProgress" -> R.drawable.ic_mtrl_downloading;
            case "onUploadSuccess" -> R.drawable.ic_mtrl_uploaded;
            case "onDownloadSuccess" -> R.drawable.ic_mtrl_download;
            case "onPictureTaken" -> R.drawable.ic_mtrl_pic_taken;
            case "onPictureTakenCancel" -> R.drawable.ic_mtrl_pic_cancel;
            case "onFilesPicked" -> R.drawable.ic_mtrl_file_picked;
            case "onFilesPickedCancel" -> R.drawable.ic_mtrl_pick_cancel;
            case "onResponse" -> R.drawable.ic_mtrl_sensor;
            case "onErrorResponse" -> R.drawable.ic_mtrl_sensor_cancel;
            case "onSpeechResult" -> R.drawable.ic_mtrl_speech;
            case "onSpeechError" -> R.drawable.ic_mtrl_speech_cancel;
            case "onConnected" -> R.drawable.ic_mtrl_bluetooth_connected;
            case "onDataReceived" -> R.drawable.ic_mtrl_bt_received;
            case "onDataSent" -> R.drawable.ic_mtrl_bt_sent;
            case "onConnectionError" -> R.drawable.ic_mtrl_bt_error;
            case "onConnectionStopped" -> R.drawable.ic_mtrl_bt_cancel;
            case "onLocationChanged" -> R.drawable.ic_mtrl_location_changed;
            case "onMapReady" -> R.drawable.ic_mtrl_map_ready;
            case "onMarkerClicked" -> R.drawable.ic_mtrl_loc_click;
            default -> ManageEvent.d(eventName);
        };
    }

    public static String a(String eventName, Context context) {
        return switch (eventName) {
            case "initializeLogic" -> Helper.getResString(R.string.event_initialize);
            case "onBackPressed" -> Helper.getResString(R.string.event_onbackpressed);
            case "onPostCreate" -> Helper.getResString(R.string.event_onpostcreated);
            case "onStart" -> Helper.getResString(R.string.event_onstart);
            case "onStop" -> Helper.getResString(R.string.event_onstop);
            case "onDestroy" -> Helper.getResString(R.string.event_ondestroy);
            case "onResume" -> Helper.getResString(R.string.event_onresume);
            case "onPause" -> Helper.getResString(R.string.event_onpause);
            case "onPageStarted" -> Helper.getResString(R.string.event_onpagestarted);
            case "onPageFinished" -> Helper.getResString(R.string.event_onpagefinished);
            case "moreBlock" -> Helper.getResString(R.string.event_definefunc);
            case "onClick" -> Helper.getResString(R.string.event_onclick);
            case "onCheckedChange" -> Helper.getResString(R.string.event_oncheckchanged);
            case "onItemSelected" -> Helper.getResString(R.string.event_onitemselected);
            case "onItemClicked" -> Helper.getResString(R.string.event_onitemclicked);
            case "onItemLongClicked" -> Helper.getResString(R.string.event_onitemlongclicked);
            case "onTextChanged" -> Helper.getResString(R.string.event_ontextchanged);
            case "onProgressChanged" -> Helper.getResString(R.string.event_onprogresschanged);
            case "onStartTrackingTouch" -> Helper.getResString(R.string.event_onstarttrackingtouch);
            case "onStopTrackingTouch" -> Helper.getResString(R.string.event_onstoptrackingtouch);
            case "onAnimationStart" -> Helper.getResString(R.string.event_onanimationstart);
            case "onAnimationEnd" -> Helper.getResString(R.string.event_onanimationend);
            case "onAnimationCancel" -> Helper.getResString(R.string.event_onanimationcancel);
            case "onBindCustomView" -> Helper.getResString(R.string.event_onbindcustomview);
            case "onDateChange" -> Helper.getResString(R.string.event_ondatechange);
            case "onChildAdded" -> Helper.getResString(R.string.event_onchildadded);
            case "onChildChanged" -> Helper.getResString(R.string.event_onchildchanged);
            case "onChildRemoved" -> Helper.getResString(R.string.event_onchildremoved);
            case "onCancelled" -> Helper.getResString(R.string.event_oncancelled);
            case "onSensorChanged" -> Helper.getResString(R.string.event_onsensorchanged);
            case "onCreateUserComplete" -> Helper.getResString(R.string.event_oncreateusercomplete);
            case "onSignInUserComplete" -> Helper.getResString(R.string.event_onsigninusercomplete);
            case "onResetPasswordEmailSent" ->
                    Helper.getResString(R.string.event_onresetpasswordemailsent);
            case "onInterstitialAdLoaded" -> Helper.getResString(R.string.event_onadloaded);
            case "onInterstitialAdFailedToLoad" ->
                    Helper.getResString(R.string.event_onadfailedtoload);
            case "onBannerAdOpened" -> Helper.getResString(R.string.event_onadopened);
            case "onBannerAdClosed" -> Helper.getResString(R.string.event_onadclosed);
            case "onUploadProgress" -> Helper.getResString(R.string.event_onuploadprogress);
            case "onDownloadProgress" -> Helper.getResString(R.string.event_ondownloadprogress);
            case "onUploadSuccess" -> Helper.getResString(R.string.event_onuploadsuccess);
            case "onDownloadSuccess" -> Helper.getResString(R.string.event_ondownloadsuccess);
            case "onDeleteSuccess" -> Helper.getResString(R.string.event_ondeletesuccess);
            case "onFailure" -> Helper.getResString(R.string.event_onfailure);
            case "onPictureTaken" -> Helper.getResString(R.string.event_onpicturetaken);
            case "onPictureTakenCancel" -> Helper.getResString(R.string.event_onpicturetakencancel);
            case "onFilesPicked" -> Helper.getResString(R.string.event_onfilespicked);
            case "onFilesPickedCancel" -> Helper.getResString(R.string.event_onfilespickedcancel);
            case "onResponse" -> Helper.getResString(R.string.event_on_response);
            case "onErrorResponse" -> Helper.getResString(R.string.event_on_error_response);
            case "onSpeechResult" -> Helper.getResString(R.string.event_on_speech_result);
            case "onSpeechError" -> Helper.getResString(R.string.event_on_speech_error);
            case "onConnected" -> Helper.getResString(R.string.event_on_connected);
            case "onDataReceived" -> Helper.getResString(R.string.event_on_data_received);
            case "onDataSent" -> Helper.getResString(R.string.event_on_data_sent);
            case "onConnectionError" -> Helper.getResString(R.string.event_on_connection_error);
            case "onConnectionStopped" -> Helper.getResString(R.string.event_on_connection_stopped);
            case "onMapReady" -> Helper.getResString(R.string.event_on_map_ready);
            case "onMarkerClicked" -> Helper.getResString(R.string.event_on_marker_clicked);
            case "onLocationChanged" -> Helper.getResString(R.string.event_on_location_changed);
            default -> ManageEvent.e(eventName);
        };
    }

    public static String[] a() {
        return a;
    }

    public static String[] a(Gx classInfo) {
        ArrayList<String> eventList = new ArrayList<>();
        ManageEvent.h(classInfo, eventList);
        if (classInfo.a("ObjectAnimator")) {
            eventList.add("onAnimationStart");
            eventList.add("onAnimationEnd");
            eventList.add("onAnimationCancel");
        }

        if (classInfo.a("FirebaseDB")) {
            eventList.add("onChildAdded");
            eventList.add("onChildChanged");
            eventList.add("onChildRemoved");
            eventList.add("onCancelled");
        }

        if (classInfo.a("FirebaseAuth")) {
            eventList.add("onCreateUserComplete");
            eventList.add("onSignInUserComplete");
            eventList.add("onResetPasswordEmailSent");
        }

        if (classInfo.a("Gyroscope")) {
            eventList.add("onSensorChanged");
        }

        if (classInfo.a("InterstitialAd")) {
            eventList.add("onInterstitialAdLoaded");
            eventList.add("onInterstitialAdFailedToLoad");
            eventList.add("onAdDismissedFullScreenContent");
            eventList.add("onAdFailedToShowFullScreenContent");
            eventList.add("onAdShowedFullScreenContent");
        }

        if (classInfo.a("FirebaseStorage")) {
            eventList.add("onUploadProgress");
            eventList.add("onDownloadProgress");
            eventList.add("onUploadSuccess");
            eventList.add("onDownloadSuccess");
            eventList.add("onDeleteSuccess");
            eventList.add("onFailure");
        }

        if (classInfo.a("Camera")) {
            eventList.add("onPictureTaken");
            eventList.add("onPictureTakenCancel");
        }

        if (classInfo.a("FilePicker")) {
            eventList.add("onFilesPicked");
            eventList.add("onFilesPickedCancel");
        }

        if (classInfo.a("RequestNetwork")) {
            eventList.add("onResponse");
            eventList.add("onErrorResponse");
        }

        if (classInfo.a("SpeechToText")) {
            eventList.add("onSpeechResult");
            eventList.add("onSpeechError");
        }

        if (classInfo.a("BluetoothConnect")) {
            eventList.add("onConnected");
            eventList.add("onDataReceived");
            eventList.add("onDataSent");
            eventList.add("onConnectionError");
            eventList.add("onConnectionStopped");
        }

        if (classInfo.a("LocationManager")) {
            eventList.add("onLocationChanged");
        }

        return eventList.toArray(new String[0]);
    }

    public static String[] b(Gx classInfo) {
        ArrayList<String> eventList = new ArrayList<>();
        ManageEvent.b(classInfo, eventList);
        if (classInfo.a("Clickable")) {
            eventList.add("onClickListener");
            eventList.add("onLongClickListener");
        }

        if (classInfo.a("EditText")) {
            eventList.add("onTextChangedListener");
        }

        if (classInfo.a("CompoundButton")) {
            eventList.add("onCheckChangedListener");
        }

        if (classInfo.b("SeekBar")) {
            eventList.add("onSeekBarChangeListener");
        }

        if (classInfo.a("Spinner")) {
            eventList.add("onItemSelectedListener");
        }

        if (classInfo.a("ListView")) {
            eventList.add("onItemClickListener");
            eventList.add("onItemLongClickListener");
        }

        if (classInfo.a("WebView")) {
            eventList.add("webViewClient");
        }

        if (classInfo.a("CalendarView")) {
            eventList.add("onDateChangeListener");
        }

        if (classInfo.a("MapView")) {
            eventList.add("onMapReadyCallback");
            eventList.add("onMapMarkerClickListener");
        }

        if (classInfo.a("ObjectAnimator")) {
            eventList.add("animatorListener");
        }

        if (classInfo.a("FirebaseDB")) {
            eventList.add("childEventListener");
        }

        if (classInfo.a("FirebaseAuth")) {
            eventList.add("authCreateUserComplete");
            eventList.add("authSignInUserComplete");
            eventList.add("authResetEmailSent");
        }

        if (classInfo.a("Gyroscope")) {
            eventList.add("sensorEventListener");
        }

        if (classInfo.a("InterstitialAd")) {
            eventList.add("interstitialAdLoadCallback");
            eventList.add("fullScreenContentCallback");
        }

        if (classInfo.a("AdView")) {
            eventList.add("bannerAdViewListener");
        }

        if (classInfo.a("FirebaseStorage")) {
            eventList.add("onUploadProgressListener");
            eventList.add("onDownloadProgressListener");
            eventList.add("onUploadSuccessListener");
            eventList.add("onDownloadSuccessListener");
            eventList.add("onDeleteSuccessListener");
            eventList.add("onFailureListener");
        }

        if (classInfo.a("RequestNetwork")) {
            eventList.add("requestListener");
        }

        if (classInfo.a("SpeechToText")) {
            eventList.add("recognitionListener");
        }

        if (classInfo.a("BluetoothConnect")) {
            eventList.add("bluetoothConnectionListener");
        }

        if (classInfo.a("LocationManager")) {
            eventList.add("locationListener");
        }

        return eventList.toArray(new String[0]);
    }

    public static String[] b(String listenerName) {
        ArrayList<String> eventList = new ArrayList<>();
        ManageEvent.c(listenerName, eventList);

        switch (listenerName) {
            case "onClickListener" -> eventList.add("onClick");
            case "onTextChangedListener" -> {
                eventList.add("onTextChanged");
                eventList.add("beforeTextChanged");
                eventList.add("afterTextChanged");
            }
            case "onCheckChangedListener" -> eventList.add("onCheckedChange");
            case "onSeekBarChangeListener" -> {
                eventList.add("onProgressChanged");
                eventList.add("onStartTrackingTouch");
                eventList.add("onStopTrackingTouch");
            }
            case "onItemSelectedListener" -> {
                eventList.add("onItemSelected");
                eventList.add("onNothingSelected");
            }
            case "onItemClickListener" -> eventList.add("onItemClicked");
            case "onItemLongClickListener" -> eventList.add("onItemLongClicked");
            case "webViewClient" -> {
                eventList.add("onPageStarted");
                eventList.add("onPageFinished");
            }
            case "onDateChangeListener" -> eventList.add("onDateChange");
            case "animatorListener" -> {
                eventList.add("onAnimationStart");
                eventList.add("onAnimationEnd");
                eventList.add("onAnimationCancel");
                eventList.add("onAnimationRepeat");
            }
            case "childEventListener" -> {
                eventList.add("onChildAdded");
                eventList.add("onChildChanged");
                eventList.add("onChildMoved");
                eventList.add("onChildRemoved");
                eventList.add("onCancelled");
            }
            case "sensorEventListener" -> {
                eventList.add("onSensorChanged");
                eventList.add("onAccuracyChanged");
            }
            case "authCreateUserComplete" -> eventList.add("onCreateUserComplete");
            case "authSignInUserComplete" -> eventList.add("onSignInUserComplete");
            case "authResetEmailSent" -> eventList.add("onResetPasswordEmailSent");
            case "interstitialAdLoadCallback" -> {
                eventList.add("onInterstitialAdLoaded");
                eventList.add("onInterstitialAdFailedToLoad");
            }
            case "fullScreenContentCallback" -> {
                eventList.add("onAdDismissedFullScreenContent");
                eventList.add("onAdFailedToShowFullScreenContent");
                eventList.add("onAdShowedFullScreenContent");
            }
            case "bannerAdViewListener" -> {
                eventList.add("onBannerAdLoaded");
                eventList.add("onBannerAdFailedToLoad");
                eventList.add("onBannerAdOpened");
                eventList.add("onBannerAdClicked");
                eventList.add("onBannerAdClosed");
            }
            case "onUploadProgressListener" -> eventList.add("onUploadProgress");
            case "onDownloadProgressListener" -> eventList.add("onDownloadProgress");
            case "onUploadSuccessListener" -> eventList.add("onUploadSuccess");
            case "onDownloadSuccessListener" -> eventList.add("onDownloadSuccess");
            case "onDeleteSuccessListener" -> eventList.add("onDeleteSuccess");
            case "onFailureListener" -> eventList.add("onFailure");
            case "requestListener" -> {
                eventList.add("onResponse");
                eventList.add("onErrorResponse");
            }
            case "recognitionListener" -> {
                eventList.add("onSpeechResult");
                eventList.add("onSpeechError");
            }
            case "bluetoothConnectionListener" -> {
                eventList.add("onConnected");
                eventList.add("onDataReceived");
                eventList.add("onDataSent");
                eventList.add("onConnectionError");
                eventList.add("onConnectionStopped");
            }
            case "onMapReadyCallback" -> eventList.add("onMapReady");
            case "onMapMarkerClickListener" -> eventList.add("onMarkerClicked");
            case "locationListener" -> eventList.add("onLocationChanged");
        }

        return eventList.toArray(new String[0]);
    }

    public static String[] c(Gx classInfo) {
        ArrayList<String> eventList = new ArrayList<>();
        ManageEvent.a(classInfo, eventList);
        if (classInfo.a("Clickable")) {
            eventList.add("onClick");
        }

        if (classInfo.a("EditText")) {
            eventList.add("onTextChanged");
        }

        if (classInfo.a("CompoundButton")) {
            eventList.add("onCheckedChange");
        }

        if (classInfo.b("SeekBar")) {
            eventList.add("onProgressChanged");
            eventList.add("onStartTrackingTouch");
            eventList.add("onStopTrackingTouch");
        }

        if (classInfo.a("Spinner")) {
            eventList.add("onItemSelected");
        }

        if (classInfo.a("ListView")) {
            eventList.add("onItemClicked");
            eventList.add("onItemLongClicked");
            eventList.add("onBindCustomView");
        }

        if (classInfo.a("WebView")) {
            eventList.add("onPageStarted");
            eventList.add("onPageFinished");
        }

        if (classInfo.a("CalendarView")) {
            eventList.add("onDateChange");
        }

        if (classInfo.a("MapView")) {
            eventList.add("onMapReady");
            eventList.add("onMarkerClicked");
        }

        if (classInfo.a("AdView")) {
            eventList.add("onBannerAdLoaded");
            eventList.add("onBannerAdFailedToLoad");
            eventList.add("onBannerAdOpened");
            eventList.add("onBannerAdClicked");
            eventList.add("onBannerAdClosed");
        }

        return eventList.toArray(new String[0]);
    }
}
