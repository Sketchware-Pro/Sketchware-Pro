package a.a.a;

import android.content.Context;

import pro.sketchware.R;

import java.util.ArrayList;

import mod.agus.jcoderz.editor.event.ManageEvent;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.events.EventsHandler;

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
            case "initializeLogic", "onBackPressed", "onPostCreate", "onStart", "onStop", "onDestroy", "onResume", "onPause", "moreBlock" ->
                    R.drawable.bg_event_type_activity;
            case "onBannerAdClicked", "onClick" -> R.drawable.event_on_click_48dp;
            case "onCheckedChange" -> R.drawable.event_on_check_changed_48dp;
            case "onItemSelected" -> R.drawable.event_on_item_selected_48dp;
            case "onItemClicked" -> R.drawable.event_on_item_clicked_48dp;
            case "onItemLongClicked" -> R.drawable.event_on_item_long_clicked_48dp;
            case "onTextChanged" -> R.drawable.event_on_text_changed_48dp;
            case "onPageStarted" -> R.drawable.event_on_page_started_48dp;
            case "onPageFinished" -> R.drawable.event_on_page_finished_48dp;
            case "onProgressChanged" -> R.drawable.event_on_progress_changed_48dp;
            case "onStartTrackingTouch" -> R.drawable.event_on_start_tracking_touch_48dp;
            case "onStopTrackingTouch" -> R.drawable.event_on_stop_tracking_touch_48dp;
            case "onAnimationStart" -> R.drawable.event_on_animation_start_48dp;
            case "onAnimationEnd" -> R.drawable.event_on_animation_end_48dp;
            case "onAnimationCancel" -> R.drawable.event_on_animation_cancel_48dp;
            case "onAnimationRepeat" -> R.drawable.event_animation_repeat_48dp;
            case "onBindCustomView" -> R.drawable.event_on_bind_custom_view_48dp;
            case "onDateChange" -> R.drawable.event_on_date_changed_48dp;
            case "onChildAdded" -> R.drawable.event_on_child_added_48dp;
            case "onChildChanged" -> R.drawable.event_on_child_changed_48dp;
            case "onChildMoved" -> R.drawable.event_on_child_moved_48dp;
            case "onChildRemoved", "onDeleteSuccess" -> R.drawable.event_on_child_removed_48dp;
            case "onCancelled" -> R.drawable.event_on_cancelled_48dp;
            case "onCreateUserComplete" -> R.drawable.event_on_create_user_complete_48dp;
            case "onSignInUserComplete" -> R.drawable.event_on_signin_complete_48dp;
            case "onResetPasswordEmailSent" -> R.drawable.event_on_reset_password_email_sent_48dp;
            case "onSensorChanged" -> R.drawable.event_on_sensor_changed_48dp;
            case "onAccuracyChanged" -> R.drawable.event_on_accuracy_changed_48dp;
            case "onInterstitialAdLoaded", "onBannerAdLoaded", "onRewardAdLoaded" ->
                    R.drawable.event_on_ad_loaded;
            case "onBannerAdFailedToLoad", "onInterstitialAdFailedToLoad", "onAdFailedToShowFullScreenContent", "onRewardAdFailedToLoad", "onFailure" ->
                    R.drawable.event_on_ad_failed_to_load;
            case "onAdShowedFullScreenContent", "onBannerAdOpened" -> R.drawable.event_on_ad_opened;
            case "onAdDismissedFullScreenContent", "onBannerAdClosed" ->
                    R.drawable.event_on_ad_closed;
            case "onUploadProgress" -> R.drawable.event_on_upload_progress_48dp;
            case "onDownloadProgress" -> R.drawable.event_on_download_progress_48dp;
            case "onUploadSuccess" -> R.drawable.event_on_upload_success_48dp;
            case "onDownloadSuccess" -> R.drawable.event_on_download_success_48dp;
            case "onPictureTaken" -> R.drawable.event_on_picture_taken_48dp;
            case "onPictureTakenCancel" -> R.drawable.event_on_picture_taken_cancel_48dp;
            case "onFilesPicked" -> R.drawable.event_on_file_picked_48dp;
            case "onFilesPickedCancel" -> R.drawable.event_on_file_picked_cancel_48dp;
            case "onResponse" -> R.drawable.event_on_response_48dp;
            case "onErrorResponse" -> R.drawable.event_on_error_response_48dp;
            case "onSpeechResult" -> R.drawable.event_on_speech_result;
            case "onSpeechError" -> R.drawable.event_on_speech_error;
            case "onConnected" -> R.drawable.event_on_connected_96;
            case "onDataReceived" -> R.drawable.event_on_data_received_96;
            case "onDataSent" -> R.drawable.event_on_data_sent_96;
            case "onConnectionError" -> R.drawable.event_on_connection_error_96;
            case "onConnectionStopped" -> R.drawable.event_on_connection_stopped_96;
            case "onLocationChanged" -> R.drawable.event_on_location_changed_96;
            case "onMapReady" -> R.drawable.event_on_map_ready_96;
            case "onMarkerClicked" -> R.drawable.event_on_marker_clicked_96;
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
