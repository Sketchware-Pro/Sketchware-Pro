package a.a.a;

import android.content.Context;

import com.sketchware.remod.Resources;

import java.util.ArrayList;

import mod.agus.jcoderz.editor.event.ManageEvent;
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
                return Resources.drawable.bg_event_type_activity;

            case "onBannerAdClicked":
            case "onClick":
                return Resources.drawable.event_on_click_48dp;

            case "onCheckedChange":
                return Resources.drawable.event_on_check_changed_48dp;

            case "onItemSelected":
                return Resources.drawable.event_on_item_selected_48dp;

            case "onItemClicked":
                return Resources.drawable.event_on_item_clicked_48dp;

            case "onItemLongClicked":
                return Resources.drawable.event_on_item_long_clicked_48dp;

            case "onTextChanged":
                return Resources.drawable.event_on_text_changed_48dp;

            case "onPageStarted":
                return Resources.drawable.event_on_page_started_48dp;

            case "onPageFinished":
                return Resources.drawable.event_on_page_finished_48dp;

            case "onProgressChanged":
                return Resources.drawable.event_on_progress_changed_48dp;

            case "onStartTrackingTouch":
                return Resources.drawable.event_on_start_tracking_touch_48dp;

            case "onStopTrackingTouch":
                return Resources.drawable.event_on_stop_tracking_touch_48dp;

            case "onAnimationStart":
                return Resources.drawable.event_on_animation_start_48dp;

            case "onAnimationEnd":
                return Resources.drawable.event_on_animation_end_48dp;

            case "onAnimationCancel":
                return Resources.drawable.event_on_animation_cancel_48dp;

            case "onAnimationRepeat":
                return Resources.drawable.event_animation_repeat_48dp;

            case "onBindCustomView":
                return Resources.drawable.event_on_bind_custom_view_48dp;

            case "onDateChange":
                return Resources.drawable.event_on_date_changed_48dp;

            case "onChildAdded":
                return Resources.drawable.event_on_child_added_48dp;

            case "onChildChanged":
                return Resources.drawable.event_on_child_changed_48dp;

            case "onChildMoved":
                return Resources.drawable.event_on_child_moved_48dp;

            case "onChildRemoved":
            case "onDeleteSuccess":
                return Resources.drawable.event_on_child_removed_48dp;

            case "onCancelled":
                return Resources.drawable.event_on_cancelled_48dp;

            case "onCreateUserComplete":
                return Resources.drawable.event_on_create_user_complete_48dp;

            case "onSignInUserComplete":
                return Resources.drawable.event_on_signin_complete_48dp;

            case "onResetPasswordEmailSent":
                return Resources.drawable.event_on_reset_password_email_sent_48dp;

            case "onSensorChanged":
                return Resources.drawable.event_on_sensor_changed_48dp;

            case "onAccuracyChanged":
                return Resources.drawable.event_on_accuracy_changed_48dp;

            case "onInterstitialAdLoaded":
            case "onBannerAdLoaded":
                return Resources.drawable.event_on_ad_loaded;

            case "onBannerAdFailedToLoad":
            case "onInterstitialAdFailedToLoad":
            case "onAdFailedToShowFullScreenContent":
            case "onFailure":
                return Resources.drawable.event_on_ad_failed_to_load;

            case "onAdShowedFullScreenContent":
            case "onBannerAdOpened":
                return Resources.drawable.event_on_ad_opened;

            case "onAdDismissedFullScreenContent":
            case "onBannerAdClosed":
                return Resources.drawable.event_on_ad_closed;

            case "onUploadProgress":
                return Resources.drawable.event_on_upload_progress_48dp;

            case "onDownloadProgress":
                return Resources.drawable.event_on_download_progress_48dp;

            case "onUploadSuccess":
                return Resources.drawable.event_on_upload_success_48dp;

            case "onDownloadSuccess":
                return Resources.drawable.event_on_download_success_48dp;

            case "onPictureTaken":
                return Resources.drawable.event_on_picture_taken_48dp;

            case "onPictureTakenCancel":
                return Resources.drawable.event_on_picture_taken_cancel_48dp;

            case "onFilesPicked":
                return Resources.drawable.event_on_file_picked_48dp;

            case "onFilesPickedCancel":
                return Resources.drawable.event_on_file_picked_cancel_48dp;

            case "onResponse":
                return Resources.drawable.event_on_response_48dp;

            case "onErrorResponse":
                return Resources.drawable.event_on_error_response_48dp;

            case "onSpeechResult":
                return Resources.drawable.event_on_speech_result;

            case "onSpeechError":
                return Resources.drawable.event_on_speech_error;

            case "onConnected":
                return Resources.drawable.event_on_connected_96;

            case "onDataReceived":
                return Resources.drawable.event_on_data_received_96;

            case "onDataSent":
                return Resources.drawable.event_on_data_sent_96;

            case "onConnectionError":
                return Resources.drawable.event_on_connection_error_96;

            case "onConnectionStopped":
                return Resources.drawable.event_on_connection_stopped_96;

            case "onLocationChanged":
                return Resources.drawable.event_on_location_changed_96;

            case "onMapReady":
                return Resources.drawable.event_on_map_ready_96;

            case "onMarkerClicked":
                return Resources.drawable.event_on_marker_clicked_96;

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
                return xB.b().a(context.getResources(), 2131625330);

            case "onBackPressed":
                return xB.b().a(context.getResources(), 2131625354);

            case "onPostCreate":
                return xB.b().a(context.getResources(), 2131625379);

            case "onStart":
                return xB.b().a(context.getResources(), 2131625385);

            case "onStop":
                return xB.b().a(context.getResources(), 2131625387);

            case "onDestroy":
                return xB.b().a(context.getResources(), 2131625365);

            case "onResume":
                return xB.b().a(context.getResources(), 2131625382);

            case "onPause":
                return xB.b().a(context.getResources(), 2131625376);

            case "onPageStarted":
                return xB.b().a(context.getResources(), 2131625375);

            case "onPageFinished":
                return xB.b().a(context.getResources(), 2131625374);

            case "moreBlock":
                return xB.b().a(context.getResources(), 2131625325);

            case "onClick":
                return xB.b().a(context.getResources(), 2131625361);

            case "onCheckedChange":
                return xB.b().a(context.getResources(), 2131625357);

            case "onItemSelected":
                return xB.b().a(context.getResources(), 2131625373);

            case "onItemClicked":
                return xB.b().a(context.getResources(), 2131625371);

            case "onItemLongClicked":
                return xB.b().a(context.getResources(), 2131625372);

            case "onTextChanged":
                return xB.b().a(context.getResources(), 2131625389);

            case "onProgressChanged":
                return xB.b().a(context.getResources(), 2131625380);

            case "onStartTrackingTouch":
                return xB.b().a(context.getResources(), 2131625386);

            case "onStopTrackingTouch":
                return xB.b().a(context.getResources(), 2131625388);

            case "onAnimationStart":
                return xB.b().a(context.getResources(), 2131625353);

            case "onAnimationEnd":
                return xB.b().a(context.getResources(), 2131625352);

            case "onAnimationCancel":
                return xB.b().a(context.getResources(), 2131625351);

            case "onBindCustomView":
                return xB.b().a(context.getResources(), 2131625355);

            case "onDateChange":
                return xB.b().a(context.getResources(), 2131625363);

            case "onChildAdded":
                return xB.b().a(context.getResources(), 2131625358);

            case "onChildChanged":
                return xB.b().a(context.getResources(), 2131625359);

            case "onChildRemoved":
                return xB.b().a(context.getResources(), 2131625360);

            case "onCancelled":
                return xB.b().a(context.getResources(), 2131625356);

            case "onSensorChanged":
                return xB.b().a(context.getResources(), 2131625383);

            case "onCreateUserComplete":
                return xB.b().a(context.getResources(), 2131625362);

            case "onSignInUserComplete":
                return xB.b().a(context.getResources(), 2131625384);

            case "onResetPasswordEmailSent":
                return xB.b().a(context.getResources(), 2131625381);

            case "onInterstitialAdLoaded":
                return xB.b().a(context.getResources(), 2131625349);

            case "onInterstitialAdFailedToLoad":
                return xB.b().a(context.getResources(), 2131625348);

            case "onBannerAdOpened":
                return xB.b().a(context.getResources(), 2131625350);

            case "onBannerAdClosed":
                return xB.b().a(context.getResources(), 2131625347);

            case "onUploadProgress":
                return xB.b().a(context.getResources(), 2131625390);

            case "onDownloadProgress":
                return xB.b().a(context.getResources(), 2131625366);

            case "onUploadSuccess":
                return xB.b().a(context.getResources(), 2131625391);

            case "onDownloadSuccess":
                return xB.b().a(context.getResources(), 2131625367);

            case "onDeleteSuccess":
                return xB.b().a(context.getResources(), 2131625364);

            case "onFailure":
                return xB.b().a(context.getResources(), 2131625368);

            case "onPictureTaken":
                return xB.b().a(context.getResources(), 2131625377);

            case "onPictureTakenCancel":
                return xB.b().a(context.getResources(), 2131625378);

            case "onFilesPicked":
                return xB.b().a(context.getResources(), 2131625369);

            case "onFilesPickedCancel":
                return xB.b().a(context.getResources(), 2131625370);

            case "onResponse":
                return xB.b().a(context.getResources(), 2131625344);

            case "onErrorResponse":
                return xB.b().a(context.getResources(), 2131625340);

            case "onSpeechResult":
                return xB.b().a(context.getResources(), 2131625346);

            case "onSpeechError":
                return xB.b().a(context.getResources(), 2131625345);

            case "onConnected":
                return xB.b().a(context.getResources(), 2131625335);

            case "onDataReceived":
                return xB.b().a(context.getResources(), 2131625338);

            case "onDataSent":
                return xB.b().a(context.getResources(), 2131625339);

            case "onConnectionError":
                return xB.b().a(context.getResources(), 2131625336);

            case "onConnectionStopped":
                return xB.b().a(context.getResources(), 2131625337);

            case "onMapReady":
                return xB.b().a(context.getResources(), 2131625342);

            case "onMarkerClicked":
                return xB.b().a(context.getResources(), 2131625343);

            case "onLocationChanged":
                return xB.b().a(context.getResources(), 2131625341);

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
