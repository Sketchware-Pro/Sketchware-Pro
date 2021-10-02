package a.a.a;

import android.content.Context;

import java.util.ArrayList;

import a.a.a.Gx;
import a.a.a.xB;
import mod.agus.jcoderz.editor.event.ManageEvent;
import mod.hilal.saif.events.EventsHandler;

public class oq {
    public static final String[] a;

    static {
        String[] var10000 = new String[] {"onBackPressed", "onPostCreate", "onStart", "onResume", "onPause", "onStop", "onDestroy", "onSaveInstanceState", "onRestoreInstanceState", "onCreateOptionsMenu", "onOptionsItemSelected", "onCreateContextMenu", "onContextItemSelected"};
        a = EventsHandler.getActivityEvents();
    }

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
                return 2131165333;

            case "onBannerAdClicked":
            case "onClick":
                return 2131165565;

            case "onCheckedChange":
                return 2131165560;

            case "onItemSelected":
                return 2131165582;

            case "onItemClicked":
                return 2131165580;

            case "onItemLongClicked":
                return 2131165581;

            case "onTextChanged":
                return 2131165601;

            case "onPageStarted":
                return 2131165588;

            case "onPageFinished":
                return 2131165587;

            case "onProgressChanged":
                return 2131165591;

            case "onStartTrackingTouch":
                return 2131165599;

            case "onStopTrackingTouch":
                return 2131165600;

            case "onAnimationStart":
                return 2131165557;

            case "onAnimationEnd":
                return 2131165556;

            case "onAnimationCancel":
                return 2131165555;

            case "onAnimationRepeat":
                return 2131165549;

            case "onBindCustomView":
                return 2131165558;

            case "onDateChange":
                return 2131165572;

            case "onChildAdded":
                return 2131165561;

            case "onChildChanged":
                return 2131165562;

            case "onChildMoved":
                return 2131165563;

            case "onChildRemoved":
            case "onDeleteSuccess":
                return 2131165564;

            case "onCancelled":
                return 2131165559;

            case "onCreateUserComplete":
                return 2131165569;

            case "onSignInUserComplete":
                return 2131165595;

            case "onResetPasswordEmailSent":
                return 2131165592;

            case "onSensorChanged":
                return 2131165594;

            case "onAccuracyChanged":
                return 2131165550;

            case "onInterstitialAdLoaded":
            case "onBannerAdLoaded":
                return 2131165553;

            case "onBannerAdFailedToLoad":
            case "onInterstitialAdFailedToLoad":
            case "onAdFailedToShowFullScreenContent":
            case "onFailure":
                return 2131165552;

            case "onAdShowedFullScreenContent":
            case "onBannerAdOpened":
                return 2131165554;

            case "onAdDismissedFullScreenContent":
            case "onBannerAdClosed":
                return 2131165551;

            case "onUploadProgress":
                return 2131165602;

            case "onDownloadProgress":
                return 2131165573;

            case "onUploadSuccess":
                return 2131165603;

            case "onDownloadSuccess":
                return 2131165574;

            case "onPictureTaken":
                return 2131165589;

            case "onPictureTakenCancel":
                return 2131165590;

            case "onFilesPicked":
                return 2131165576;

            case "onFilesPickedCancel":
                return 2131165577;

            case "onResponse":
                return 2131165593;

            case "onErrorResponse":
                return 2131165575;

            case "onSpeechResult":
                return 2131165598;

            case "onSpeechError":
                return 2131165597;

            case "onConnected":
                return 2131165566;

            case "onDataReceived":
                return 2131165570;

            case "onDataSent":
                return 2131165571;

            case "onConnectionError":
                return 2131165567;

            case "onConnectionStopped":
                return 2131165568;

            case "onLocationChanged":
                return 2131165583;

            case "onMapReady":
                return 2131165584;

            case "onMarkerClicked":
                return 2131165585;

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
        return EventsHandler.getActivityEvents();
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
//new start
        if (classInfo.a("InterstitialAd")) {
            eventList.add("onInterstitialAdLoaded");
            eventList.add("onInterstitialAdFailedToLoad");
            eventList.add("onAdDismissedFullScreenContent");
            eventList.add("onAdFailedToShowFullScreenContent");
            eventList.add("onAdShowedFullScreenContent");
        }
//new end
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

        return (String[]) eventList.toArray(new String[eventList.size()]);
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
//new start
        if (classInfo.a("InterstitialAd")) {
            eventList.add("interstitialAdLoadCallback");
            eventList.add("fullScreenContentCallback");
        }

        if (classInfo.a("AdView")) {
            eventList.add("bannerAdViewListener");
        }
//new end
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

        return (String[]) eventList.toArray(new String[eventList.size()]);
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
//new start
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
//new end
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

        return (String[]) eventList.toArray(new String[eventList.size()]);
    }

    public static String[] c(Gx classInfo) {
        ArrayList<String> eventList = new ArrayList();
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
//new start
        if (classInfo.a("AdView")) {
            eventList.add("onBannerAdLoaded");
            eventList.add("onBannerAdFailedToLoad");
            eventList.add("onBannerAdOpened");
            eventList.add("onBannerAdClicked");
            eventList.add("onBannerAdClosed");
        }
//new end
        return (String[]) eventList.toArray(new String[eventList.size()]);
    }
}
