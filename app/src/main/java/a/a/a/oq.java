package a.a.a;

import android.content.Context;

import com.sketchware.remod.R;

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
        switch (eventName) {
            case "initializeLogic":
            case "onBackPressed":
            case "onPostCreate":
            case "onStart":
            case "onStop":
            case "onDestroy":
            case "onResume":
            case "onPause":
            case "moreBlock":
                return R.drawable.bg_event_type_activity;

            case "onBannerAdClicked":
            case "onClick":
                return R.drawable.event_on_click_48dp;

            case "onCheckedChange":
                return R.drawable.event_on_check_changed_48dp;

            case "onItemSelected":
                return R.drawable.event_on_item_selected_48dp;

            case "onItemClicked":
                return R.drawable.event_on_item_clicked_48dp;

            case "onItemLongClicked":
                return R.drawable.event_on_item_long_clicked_48dp;

            case "onTextChanged":
                return R.drawable.event_on_text_changed_48dp;

            case "onPageStarted":
                return R.drawable.event_on_page_started_48dp;

            case "onPageFinished":
                return R.drawable.event_on_page_finished_48dp;

            case "onProgressChanged":
                return R.drawable.event_on_progress_changed_48dp;

            case "onStartTrackingTouch":
                return R.drawable.event_on_start_tracking_touch_48dp;

            case "onStopTrackingTouch":
                return R.drawable.event_on_stop_tracking_touch_48dp;

            case "onAnimationStart":
                return R.drawable.event_on_animation_start_48dp;

            case "onAnimationEnd":
                return R.drawable.event_on_animation_end_48dp;

            case "onAnimationCancel":
                return R.drawable.event_on_animation_cancel_48dp;

            case "onAnimationRepeat":
                return R.drawable.event_animation_repeat_48dp;

            case "onBindCustomView":
                return R.drawable.event_on_bind_custom_view_48dp;

            case "onDateChange":
                return R.drawable.event_on_date_changed_48dp;

            case "onChildAdded":
                return R.drawable.event_on_child_added_48dp;

            case "onChildChanged":
                return R.drawable.event_on_child_changed_48dp;

            case "onChildMoved":
                return R.drawable.event_on_child_moved_48dp;

            case "onChildRemoved":
            case "onDeleteSuccess":
                return R.drawable.event_on_child_removed_48dp;

            case "onCancelled":
                return R.drawable.event_on_cancelled_48dp;

            case "onCreateUserComplete":
                return R.drawable.event_on_create_user_complete_48dp;

            case "onSignInUserComplete":
                return R.drawable.event_on_signin_complete_48dp;

            case "onResetPasswordEmailSent":
                return R.drawable.event_on_reset_password_email_sent_48dp;

            case "onSensorChanged":
                return R.drawable.event_on_sensor_changed_48dp;

            case "onAccuracyChanged":
                return R.drawable.event_on_accuracy_changed_48dp;

            case "onInterstitialAdLoaded":
            case "onBannerAdLoaded":
            case "onRewardAdLoaded":
                return R.drawable.event_on_ad_loaded;

            case "onBannerAdFailedToLoad":
            case "onInterstitialAdFailedToLoad":
            case "onAdFailedToShowFullScreenContent":
            case "onRewardAdFailedToLoad":
            case "onFailure":
                return R.drawable.event_on_ad_failed_to_load;

            case "onAdShowedFullScreenContent":
            case "onBannerAdOpened":
                return R.drawable.event_on_ad_opened;

            case "onAdDismissedFullScreenContent":
            case "onBannerAdClosed":
                return R.drawable.event_on_ad_closed;

            case "onUploadProgress":
                return R.drawable.event_on_upload_progress_48dp;

            case "onDownloadProgress":
                return R.drawable.event_on_download_progress_48dp;

            case "onUploadSuccess":
                return R.drawable.event_on_upload_success_48dp;

            case "onDownloadSuccess":
                return R.drawable.event_on_download_success_48dp;

            case "onPictureTaken":
                return R.drawable.event_on_picture_taken_48dp;

            case "onPictureTakenCancel":
                return R.drawable.event_on_picture_taken_cancel_48dp;

            case "onFilesPicked":
                return R.drawable.event_on_file_picked_48dp;

            case "onFilesPickedCancel":
                return R.drawable.event_on_file_picked_cancel_48dp;

            case "onResponse":
                return R.drawable.event_on_response_48dp;

            case "onErrorResponse":
                return R.drawable.event_on_error_response_48dp;

            case "onSpeechResult":
                return R.drawable.event_on_speech_result;

            case "onSpeechError":
                return R.drawable.event_on_speech_error;

            case "onConnected":
                return R.drawable.event_on_connected_96;

            case "onDataReceived":
                return R.drawable.event_on_data_received_96;

            case "onDataSent":
                return R.drawable.event_on_data_sent_96;

            case "onConnectionError":
                return R.drawable.event_on_connection_error_96;

            case "onConnectionStopped":
                return R.drawable.event_on_connection_stopped_96;

            case "onLocationChanged":
                return R.drawable.event_on_location_changed_96;

            case "onMapReady":
                return R.drawable.event_on_map_ready_96;

            case "onMarkerClicked":
                return R.drawable.event_on_marker_clicked_96;

            case "onNothingSelected":
            case "beforeTextChanged":
            case "afterTextChanged":
            default:
                return ManageEvent.d(eventName);
        }
    }

    public static String a(String eventName, Context context) {
        switch (eventName) {
            case "initializeLogic":
                return Helper.getResString(R.string.event_initialize);

            case "onBackPressed":
                return Helper.getResString(R.string.event_onbackpressed);

            case "onPostCreate":
                return Helper.getResString(R.string.event_onpostcreated);

            case "onStart":
                return Helper.getResString(R.string.event_onstart);

            case "onStop":
                return Helper.getResString(R.string.event_onstop);

            case "onDestroy":
                return Helper.getResString(R.string.event_ondestroy);

            case "onResume":
                return Helper.getResString(R.string.event_onresume);

            case "onPause":
                return Helper.getResString(R.string.event_onpause);

            case "onPageStarted":
                return Helper.getResString(R.string.event_onpagestarted);

            case "onPageFinished":
                return Helper.getResString(R.string.event_onpagefinished);

            case "moreBlock":
                return Helper.getResString(R.string.event_definefunc);

            case "onClick":
                return Helper.getResString(R.string.event_onclick);

            case "onCheckedChange":
                return Helper.getResString(R.string.event_oncheckchanged);

            case "onItemSelected":
                return Helper.getResString(R.string.event_onitemselected);

            case "onItemClicked":
                return Helper.getResString(R.string.event_onitemclicked);

            case "onItemLongClicked":
                return Helper.getResString(R.string.event_onitemlongclicked);

            case "onTextChanged":
                return Helper.getResString(R.string.event_ontextchanged);

            case "onProgressChanged":
                return Helper.getResString(R.string.event_onprogresschanged);

            case "onStartTrackingTouch":
                return Helper.getResString(R.string.event_onstarttrackingtouch);

            case "onStopTrackingTouch":
                return Helper.getResString(R.string.event_onstoptrackingtouch);

            case "onAnimationStart":
                return Helper.getResString(R.string.event_onanimationstart);

            case "onAnimationEnd":
                return Helper.getResString(R.string.event_onanimationend);

            case "onAnimationCancel":
                return Helper.getResString(R.string.event_onanimationcancel);

            case "onBindCustomView":
                return Helper.getResString(R.string.event_onbindcustomview);

            case "onDateChange":
                return Helper.getResString(R.string.event_ondatechange);

            case "onChildAdded":
                return Helper.getResString(R.string.event_onchildadded);

            case "onChildChanged":
                return Helper.getResString(R.string.event_onchildchanged);

            case "onChildRemoved":
                return Helper.getResString(R.string.event_onchildremoved);

            case "onCancelled":
                return Helper.getResString(R.string.event_oncancelled);

            case "onSensorChanged":
                return Helper.getResString(R.string.event_onsensorchanged);

            case "onCreateUserComplete":
                return Helper.getResString(R.string.event_oncreateusercomplete);

            case "onSignInUserComplete":
                return Helper.getResString(R.string.event_onsigninusercomplete);

            case "onResetPasswordEmailSent":
                return Helper.getResString(R.string.event_onresetpasswordemailsent);

            case "onInterstitialAdLoaded":
                return Helper.getResString(R.string.event_onadloaded);

            case "onInterstitialAdFailedToLoad":
                return Helper.getResString(R.string.event_onadfailedtoload);

            case "onBannerAdOpened":
                return Helper.getResString(R.string.event_onadopened);

            case "onBannerAdClosed":
                return Helper.getResString(R.string.event_onadclosed);

            case "onUploadProgress":
                return Helper.getResString(R.string.event_onuploadprogress);

            case "onDownloadProgress":
                return Helper.getResString(R.string.event_ondownloadprogress);

            case "onUploadSuccess":
                return Helper.getResString(R.string.event_onuploadsuccess);

            case "onDownloadSuccess":
                return Helper.getResString(R.string.event_ondownloadsuccess);

            case "onDeleteSuccess":
                return Helper.getResString(R.string.event_ondeletesuccess);

            case "onFailure":
                return Helper.getResString(R.string.event_onfailure);

            case "onPictureTaken":
                return Helper.getResString(R.string.event_onpicturetaken);

            case "onPictureTakenCancel":
                return Helper.getResString(R.string.event_onpicturetakencancel);

            case "onFilesPicked":
                return Helper.getResString(R.string.event_onfilespicked);

            case "onFilesPickedCancel":
                return Helper.getResString(R.string.event_onfilespickedcancel);

            case "onResponse":
                return Helper.getResString(R.string.event_on_response);

            case "onErrorResponse":
                return Helper.getResString(R.string.event_on_error_response);

            case "onSpeechResult":
                return Helper.getResString(R.string.event_on_speech_result);

            case "onSpeechError":
                return Helper.getResString(R.string.event_on_speech_error);

            case "onConnected":
                return Helper.getResString(R.string.event_on_connected);

            case "onDataReceived":
                return Helper.getResString(R.string.event_on_data_received);

            case "onDataSent":
                return Helper.getResString(R.string.event_on_data_sent);

            case "onConnectionError":
                return Helper.getResString(R.string.event_on_connection_error);

            case "onConnectionStopped":
                return Helper.getResString(R.string.event_on_connection_stopped);

            case "onMapReady":
                return Helper.getResString(R.string.event_on_map_ready);

            case "onMarkerClicked":
                return Helper.getResString(R.string.event_on_marker_clicked);

            case "onLocationChanged":
                return Helper.getResString(R.string.event_on_location_changed);

            default:
                return ManageEvent.e(eventName);
        }
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
            case "onClickListener":
                eventList.add("onClick");
                break;

            case "onTextChangedListener":
                eventList.add("onTextChanged");
                eventList.add("beforeTextChanged");
                eventList.add("afterTextChanged");
                break;

            case "onCheckChangedListener":
                eventList.add("onCheckedChange");
                break;

            case "onSeekBarChangeListener":
                eventList.add("onProgressChanged");
                eventList.add("onStartTrackingTouch");
                eventList.add("onStopTrackingTouch");
                break;

            case "onItemSelectedListener":
                eventList.add("onItemSelected");
                eventList.add("onNothingSelected");
                break;

            case "onItemClickListener":
                eventList.add("onItemClicked");
                break;

            case "onItemLongClickListener":
                eventList.add("onItemLongClicked");
                break;

            case "webViewClient":
                eventList.add("onPageStarted");
                eventList.add("onPageFinished");
                break;

            case "onDateChangeListener":
                eventList.add("onDateChange");
                break;

            case "animatorListener":
                eventList.add("onAnimationStart");
                eventList.add("onAnimationEnd");
                eventList.add("onAnimationCancel");
                eventList.add("onAnimationRepeat");
                break;

            case "childEventListener":
                eventList.add("onChildAdded");
                eventList.add("onChildChanged");
                eventList.add("onChildMoved");
                eventList.add("onChildRemoved");
                eventList.add("onCancelled");
                break;

            case "sensorEventListener":
                eventList.add("onSensorChanged");
                eventList.add("onAccuracyChanged");
                break;

            case "authCreateUserComplete":
                eventList.add("onCreateUserComplete");
                break;

            case "authSignInUserComplete":
                eventList.add("onSignInUserComplete");
                break;

            case "authResetEmailSent":
                eventList.add("onResetPasswordEmailSent");
                break;

            case "interstitialAdLoadCallback":
                eventList.add("onInterstitialAdLoaded");
                eventList.add("onInterstitialAdFailedToLoad");
                break;

            case "fullScreenContentCallback":
                eventList.add("onAdDismissedFullScreenContent");
                eventList.add("onAdFailedToShowFullScreenContent");
                eventList.add("onAdShowedFullScreenContent");
                break;

            case "bannerAdViewListener":
                eventList.add("onBannerAdLoaded");
                eventList.add("onBannerAdFailedToLoad");
                eventList.add("onBannerAdOpened");
                eventList.add("onBannerAdClicked");
                eventList.add("onBannerAdClosed");
                break;

            case "onUploadProgressListener":
                eventList.add("onUploadProgress");
                break;

            case "onDownloadProgressListener":
                eventList.add("onDownloadProgress");
                break;

            case "onUploadSuccessListener":
                eventList.add("onUploadSuccess");
                break;

            case "onDownloadSuccessListener":
                eventList.add("onDownloadSuccess");
                break;

            case "onDeleteSuccessListener":
                eventList.add("onDeleteSuccess");
                break;

            case "onFailureListener":
                eventList.add("onFailure");
                break;

            case "requestListener":
                eventList.add("onResponse");
                eventList.add("onErrorResponse");
                break;

            case "recognitionListener":
                eventList.add("onSpeechResult");
                eventList.add("onSpeechError");
                break;

            case "bluetoothConnectionListener":
                eventList.add("onConnected");
                eventList.add("onDataReceived");
                eventList.add("onDataSent");
                eventList.add("onConnectionError");
                eventList.add("onConnectionStopped");
                break;

            case "onMapReadyCallback":
                eventList.add("onMapReady");
                break;

            case "onMapMarkerClickListener":
                eventList.add("onMarkerClicked");
                break;

            case "locationListener":
                eventList.add("onLocationChanged");
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
