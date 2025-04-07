package a.a.a;

import com.besome.sketch.beans.ComponentBean;

import java.util.ArrayList;

import mod.hilal.saif.components.ComponentsHandler;
import mod.hilal.saif.events.EventsHandler;
import pro.sketchware.menu.DefaultExtraMenuBean;

public class mq {
    /**
     * @return A built class info ({@link Gx} object)
     */
    public static Gx a(String type, String typeName) {
        switch (type) {
            case "b":
                return new Gx("boolean");

            case "d":
            case "n":
                return new Gx("double");

            case "s":
                if (typeName != null && (typeName.equalsIgnoreCase("inputOnly") ||
                        typeName.equals("inputCode") || typeName.equals("import"))) {
                    return new Gx("Input");
                } else {
                    return new Gx("String");
                }

            case "a":
                return new Gx("Map");

            case "l":
                return new Gx(switch (typeName) {
                    case "List Map" -> "ListMap";
                    case "List String" -> "ListString";
                    case "List Number" -> "ListInt";
                    default -> typeName;
                });

            case "v":
                return new Gx(typeName);

            case "p":
            case "m":
                return new Gx(b(typeName));

            default:
                return null;
        }
    }

    /**
     * @return The Type name of a Component
     */
    public static String a(int componentId) {
        return switch (componentId) {
            case ComponentBean.COMPONENT_TYPE_INTENT -> "Intent";
            case ComponentBean.COMPONENT_TYPE_SHAREDPREF -> "SharedPreferences";
            case ComponentBean.COMPONENT_TYPE_CALENDAR -> "Calendar";
            case ComponentBean.COMPONENT_TYPE_VIBRATOR -> "Vibrator";
            case ComponentBean.COMPONENT_TYPE_TIMERTASK -> "Timer";
            case ComponentBean.COMPONENT_TYPE_FIREBASE -> "FirebaseDB";
            case ComponentBean.COMPONENT_TYPE_DIALOG -> "Dialog";
            case ComponentBean.COMPONENT_TYPE_MEDIAPLAYER -> "MediaPlayer";
            case ComponentBean.COMPONENT_TYPE_SOUNDPOOL -> "SoundPool";
            case ComponentBean.COMPONENT_TYPE_OBJECTANIMATOR -> "ObjectAnimator";
            case ComponentBean.COMPONENT_TYPE_GYROSCOPE -> "Gyroscope";
            case ComponentBean.COMPONENT_TYPE_FIREBASE_AUTH -> "FirebaseAuth";
            case ComponentBean.COMPONENT_TYPE_INTERSTITIAL_AD -> "InterstitialAd";
            case ComponentBean.COMPONENT_TYPE_FIREBASE_STORAGE -> "FirebaseStorage";
            case ComponentBean.COMPONENT_TYPE_CAMERA -> "Camera";
            case ComponentBean.COMPONENT_TYPE_FILE_PICKER -> "FilePicker";
            case ComponentBean.COMPONENT_TYPE_REQUEST_NETWORK -> "RequestNetwork";
            case ComponentBean.COMPONENT_TYPE_TEXT_TO_SPEECH -> "TextToSpeech";
            case ComponentBean.COMPONENT_TYPE_SPEECH_TO_TEXT -> "SpeechToText";
            case ComponentBean.COMPONENT_TYPE_BLUETOOTH_CONNECT -> "BluetoothConnect";
            case ComponentBean.COMPONENT_TYPE_LOCATION_MANAGER -> "LocationManager";
            case ComponentBean.COMPONENT_TYPE_REWARDED_VIDEO_AD -> "RewardedVideoAd";
            case ComponentBean.COMPONENT_TYPE_PROGRESS_DIALOG -> "ProgressDialog";
            case ComponentBean.COMPONENT_TYPE_DATE_PICKER_DIALOG -> "DatePickerDialog";
            case ComponentBean.COMPONENT_TYPE_TIME_PICKER_DIALOG -> "TimePickerDialog";
            case ComponentBean.COMPONENT_TYPE_NOTIFICATION -> "Notification";
            case ComponentBean.COMPONENT_TYPE_FRAGMENT_ADAPTER -> "FragmentStatePagerAdapter";
            case ComponentBean.COMPONENT_TYPE_FIREBASE_AUTH_PHONE ->
                    "PhoneAuthProvider.OnVerificationStateChangedCallbacks";
            case ComponentBean.COMPONENT_TYPE_FIREBASE_DYNAMIC_LINKS -> "DynamicLink";
            case ComponentBean.COMPONENT_TYPE_FIREBASE_CLOUD_MESSAGE -> "FirebaseCloudMessage";
            case ComponentBean.COMPONENT_TYPE_FIREBASE_AUTH_GOOGLE_LOGIN -> "GoogleSignInClient";
            case ComponentBean.COMPONENT_TYPE_ONESIGNAL -> "OSSubscriptionObserver";
            case ComponentBean.COMPONENT_TYPE_FACEBOOK_ADS_BANNER -> "com.facebook.ads.AdView";
            case ComponentBean.COMPONENT_TYPE_FACEBOOK_ADS_INTERSTITIAL ->
                    "com.facebook.ads.InterstitialAd";
            default -> ComponentsHandler.var(componentId);
        };
    }

    /**
     * @return A parameter class info ({@link Gx}) list
     */
    public static ArrayList<Gx> a(String spec) {
        ArrayList<Gx> paramClass = new ArrayList<>();
        ArrayList<String> specList = FB.c(spec);
        for (String params : specList) {
            if (params.charAt(0) == '%' && params.length() >= 2) {
                String type = String.valueOf(params.charAt(1));
                String typeName;
                if (params.length() > 3) {
                    typeName = params.substring(3);
                } else {
                    typeName = "";
                }
                paramClass.add(a(type, typeName));
            }
        }
        return paramClass;
    }

    /**
     * @return The internal type name of a List taken from its number
     */
    public static String b(int type) {
        return switch (type) {
            case 1 -> "ListInt";
            case 2 -> "ListString";
            case 3 -> "ListMap";
            default -> "";
        };
    }

    /**
     * @return The internal type name of a variable(e.g. View, Component), from its type name(%m.typename), as displayed in the moreblock code.
     */
    public static String b(String name) {
        return switch (name) {
            case "intent", "Intent" -> "Intent";
            case "file", "File", "File (Shared Preferences)" -> "SharedPreferences";
            case "calendar", "Calendar" -> "Calendar";
            case "vibrator", "Vibrator" -> "Vibrator";
            case "Timer" -> "Timer";
            case "dialog", "Dialog" -> "Dialog";
            case "MediaPlayer" -> "MediaPlayer";
            case "SoundPool" -> "SoundPool";
            case "ObjectAnimator" -> "ObjectAnimator";
            case "firebase", "Firebase", "Firebase DB" -> "FirebaseDB";
            case "firebaseauth", "Firebase Auth" -> "FirebaseAuth";
            case "gyroscope", "Gyroscope" -> "Gyroscope";
            case "InterstitialAd" -> "InterstitialAd";
            case "varBool" -> "boolean.SelectBoolean";
            case "varInt" -> "double.SelectDouble";
            case "varStr" -> "String.SelectString";
            case "varMap" -> "Map";
            case "listInt" -> "ListInt";
            case "listStr" -> "ListString";
            case "listMap" -> "ListMap";
            case "list" -> "List";
            case "view" -> "View";
            case "textview" -> "TextView";
            case "edittext" -> "EditText";
            case "imageview" -> "ImageView";
            case "compoundButton" -> "CompoundButton";
            case "checkbox" -> "CheckBox";
            case "switch" -> "Switch";
            case "listSpn" -> "AdapterView";
            case "listview" -> "ListView";
            case "spinner" -> "Spinner";
            case "webview" -> "WebView";
            case "calendarview" -> "CalendarView";
            case "adview" -> "AdView";
            case "mapview" -> "MapView";
            case "timer" -> "Timer";
            case "mediaplayer" -> "MediaPlayer";
            case "soundpool" -> "SoundPool";
            case "objectanimator" -> "ObjectAnimator";
            case "seekbar" -> "SeekBar";
            case "interstitialad" -> "InterstitialAd";
            case "firebasestorage" -> "FirebaseStorage";
            case "camera" -> "Camera";
            case "filepicker" -> "FilePicker";
            case "requestnetwork" -> "RequestNetwork";
            case "progressbar" -> "ProgressBar";
            case "texttospeech" -> "TextToSpeech";
            case "speechtotext" -> "SpeechToText";
            case "bluetoothconnect" -> "BluetoothConnect";
            case "locationmanager" -> "LocationManager";
            case "radiobutton" -> "RadioButton";
            case "ratingbar" -> "RatingBar";
            case "videoview" -> "VideoView";
            case "searchview" -> "SearchView";
            case "gridview" -> "GridView";
            case "actv" -> "AutoCompleteTextView";
            case "mactv" -> "MultiAutoCompleteTextView";
            case "tablayout" -> "TabLayout";
            case "viewpager" -> "ViewPager";
            case "badgeview" -> "BadgeView";
            case "bottomnavigation" -> "BottomNavigationView";
            case "patternview" -> "PatternLockView";
            case "sidebar" -> "WaveSideBar";
            case "circleimageview" -> "CircleImageView";
            case "onesignal" -> "OneSignal";
            case "customViews" -> "CustomView";
            case "asynctask" -> "AsyncTask";
            case "activity" -> "Context";
            case "otpview" -> "OTPView";
            case "lottie" -> "LottieAnimation";
            case "phoneauth" -> "FirebasePhoneAuth";
            case "fbadbanner" -> "FBAdsBanner";
            case "codeview" -> "CodeView";
            case "recyclerview" -> "RecyclerView";
            case "resource" -> "Image";
            case "googlelogin" -> "FirebaseGoogleSignIn";
            case "dynamiclink" -> "FirebaseDynamicLink";
            case "youtubeview" -> "YoutubePlayer";
            case "cardview" -> "CardView";
            case "radiogroup" -> "RadioGroup";
            case "color" -> "Color";
            case "fbadinterstitial" -> "FBAdsInterstitial";
            case "textinputlayout" -> "TextInputLayout";
            case "collapsingtoolbar" -> "CollapsingToolbarLayout";
            case "cloudmessage" -> "FirebaseCloudMessage";
            case "resource_bg" -> "BackgroundImage";
            case "datepicker" -> "DatePicker";
            case "timepicker" -> "TimePicker";
            case "swiperefreshlayout" -> "SwipeRefreshLayout";
            case "signinbutton" -> "SignInButton";
            case "materialButton" -> "MaterialButton";
            case "fragmentAdapter" -> "FragmentAdapter";
            default -> DefaultExtraMenuBean.getName(name);
        };
    }

    /**
     * @return The Type name of the Variable taken from its ID
     */
    public static String c(int type) {
        return switch (type) {
            case 0 -> "boolean";
            case 1 -> "double";
            case 2 -> "String";
            case 3 -> "Map";
            default -> "";
        };
    }

    /**
     * @return Imports needed for a type
     */
    public static ArrayList<String> getImportsByTypeName(String name, String convert) {
        ArrayList<String> importList = new ArrayList<>();
        ComponentsHandler.getImports(name, importList);

        switch (name) {
            case "Map":
                importList.add("java.util.HashMap");
                return importList;

            case "ListInt":
            case "ListString":
                importList.add("java.util.ArrayList");
                return importList;

            case "ListMap":
                importList.add("java.util.ArrayList");
                importList.add("java.util.HashMap");
                return importList;

            case "LinearLayout":
                importList.add("android.widget.LinearLayout");
                return importList;

            case "ScrollView":
                importList.add("android.widget.ScrollView");
                return importList;

            case "HorizontalScrollView":
                importList.add("android.widget.HorizontalScrollView");
                return importList;

            case "TextView":
                importList.add("android.widget.TextView");
                return importList;

            case "Button":
                importList.add("android.widget.Button");
                return importList;

            case "ImageView":
                importList.add("android.widget.ImageView");
                return importList;

            case "EditText":
                importList.add("android.widget.EditText");
                return importList;

            case "CompoundButton":
                importList.add("android.widget.CompoundButton");
                return importList;

            case "CheckBox":
                importList.add("android.widget.CheckBox");
                return importList;

            case "Spinner":
                importList.add("android.widget.Spinner");
                importList.add("android.widget.ArrayAdapter");
                return importList;

            case "ListView":
                importList.add("android.widget.ListView");
                importList.add("android.widget.ArrayAdapter");
                importList.add("android.widget.BaseAdapter");
                return importList;

            case "WebView":
                importList.add("android.webkit.WebView");
                importList.add("android.webkit.WebSettings");
                return importList;

            case "Switch":
                importList.add("android.widget.Switch");
                return importList;

            case "SeekBar":
                importList.add("android.widget.SeekBar");
                return importList;

            case "CalendarView":
                importList.add("android.widget.CalendarView");
                return importList;

            case "AdView":
                importList.add("com.google.android.gms.ads.AdView");
                importList.add("com.google.android.gms.ads.AdRequest");
                return importList;

            case "ProgressBar":
                importList.add("android.widget.ProgressBar");
                return importList;

            case "MapView":
                importList.add("com.google.android.gms.maps.MapView");
                importList.add("com.google.android.gms.maps.GoogleMap");
                importList.add("com.google.android.gms.maps.OnMapReadyCallback");
                importList.add("com.google.android.gms.maps.model.Marker");
                importList.add("com.google.android.gms.maps.model.BitmapDescriptorFactory");
                return importList;

            case "Intent":
                importList.add("android.content.Intent");
                importList.add("android.net.Uri");
                return importList;

            case "SharedPreferences":
                importList.add("android.app.Activity");
                importList.add("android.content.SharedPreferences");
                return importList;

            case "Calendar":
                importList.add("java.util.Calendar");
                importList.add("java.text.SimpleDateFormat");
                return importList;

            case "Vibrator":
                importList.add("android.content.Context");
                importList.add("android.os.Vibrator");
                return importList;

            case "Timer":
                importList.add("java.util.Timer");
                importList.add("java.util.TimerTask");
                return importList;

            case "Dialog":
                importList.add("android.app.AlertDialog");
                importList.add("android.content.DialogInterface");
                return importList;

            case "MediaPlayer":
                importList.add("android.media.MediaPlayer");
                return importList;

            case "SoundPool":
                importList.add("android.media.SoundPool");
                return importList;

            case "ObjectAnimator":
                importList.add("android.animation.ObjectAnimator");
                importList.add("android.view.animation.LinearInterpolator");
                importList.add("android.view.animation.AccelerateInterpolator");
                importList.add("android.view.animation.DecelerateInterpolator");
                importList.add("android.view.animation.AccelerateDecelerateInterpolator");
                importList.add("android.view.animation.BounceInterpolator");
                return importList;

            case "FirebaseDB":
                importList.add("com.google.firebase.database.FirebaseDatabase");
                importList.add("com.google.firebase.database.DatabaseReference");
                importList.add("com.google.firebase.database.ValueEventListener");
                importList.add("com.google.firebase.database.DataSnapshot");
                importList.add("com.google.firebase.database.DatabaseError");
                importList.add("com.google.firebase.database.GenericTypeIndicator");
                importList.add("com.google.firebase.database.ChildEventListener");
                importList.add("java.util.HashMap");
                return importList;

            case "FirebaseAuth":
                importList.add("com.google.firebase.auth.AuthResult");
                importList.add("com.google.firebase.auth.FirebaseUser");
                importList.add("com.google.firebase.auth.FirebaseAuth");
                importList.add("com.google.android.gms.tasks.OnCompleteListener");
                importList.add("com.google.android.gms.tasks.Task");
                return importList;

            case "Gyroscope":
                importList.add("android.content.Context");
                importList.add("android.hardware.Sensor");
                importList.add("android.hardware.SensorManager");
                importList.add("android.hardware.SensorEvent");
                importList.add("android.hardware.SensorEventListener");
                return importList;

            case "FloatingActionButton":
                importList.add("com.google.android.material.floatingactionbutton.FloatingActionButton");
                return importList;

            case "Toolbar":
                importList.add("androidx.appcompat.widget.Toolbar");
                importList.add("androidx.annotation.NonNull");
                return importList;

            case "DrawerLayout":
                importList.add("androidx.core.view.GravityCompat");
                importList.add("androidx.drawerlayout.widget.DrawerLayout");
                importList.add("androidx.appcompat.app.ActionBarDrawerToggle");
                return importList;

            case "InterstitialAd":
                importList.add("com.google.android.gms.ads.AdRequest");
                importList.add("com.google.android.gms.ads.interstitial.InterstitialAd");
                importList.add("com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback");
                importList.add("com.google.android.gms.ads.AdError");
                importList.add("com.google.android.gms.ads.FullScreenContentCallback");
                importList.add("com.google.android.gms.ads.LoadAdError");
                return importList;

            case "FirebaseStorage":
                importList.add("com.google.firebase.storage.FileDownloadTask");
                importList.add("com.google.firebase.storage.FirebaseStorage");
                importList.add("com.google.firebase.storage.StorageReference");
                importList.add("com.google.firebase.storage.UploadTask");
                importList.add("com.google.android.gms.tasks.Task");
                importList.add("com.google.firebase.storage.OnProgressListener");
                importList.add("com.google.firebase.storage.FileDownloadTask");
                importList.add("com.google.android.gms.tasks.OnSuccessListener");
                importList.add("com.google.android.gms.tasks.OnFailureListener");
                importList.add("com.google.android.gms.tasks.OnCompleteListener");
                importList.add("com.google.android.gms.tasks.Continuation");
                importList.add("android.net.Uri");
                importList.add("java.io.File");
                return importList;

            case "Camera":
                importList.add("android.content.Intent");
                importList.add("android.net.Uri");
                importList.add("android.provider.MediaStore");
                importList.add("android.os.Build");
                importList.add("androidx.core.content.FileProvider");
                importList.add("java.io.File");
                return importList;

            case "FilePicker":
                importList.add("android.content.Intent");
                importList.add("android.content.ClipData");
                return importList;

            case "TextToSpeech":
                importList.add("android.speech.tts.TextToSpeech");
                return importList;

            case "SpeechToText":
                importList.add("android.speech.SpeechRecognizer");
                importList.add("android.speech.RecognizerIntent");
                importList.add("android.speech.RecognitionListener");
                return importList;

            case "LocationManager":
                importList.add("android.location.Location");
                importList.add("android.location.LocationManager");
                importList.add("android.location.LocationListener");
                return importList;

            case "ViewPager":
                importList.add("androidx.viewpager.widget.ViewPager");
                importList.add("androidx.viewpager.widget.PagerAdapter");
                importList.add("androidx.viewpager.widget.ViewPager.OnPageChangeListener");
                importList.add("androidx.viewpager.widget.ViewPager.OnAdapterChangeListener");
                return importList;

            case "CollapsingToolbarLayout":
                importList.add("com.google.android.material.appbar.CollapsingToolbarLayout");
                return importList;

            case "SwipeRefreshLayout":
                importList.add("androidx.swiperefreshlayout.widget.SwipeRefreshLayout");
                importList.add("androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener");
                return importList;

            case "PatternLockView":
                importList.add("com.andrognito.patternlockview.PatternLockView");
                importList.add("com.andrognito.patternlockview.utils.*");
                importList.add("com.andrognito.patternlockview.listener.*");
                return importList;

            case "WaveSideBar":
                importList.add("com.sayuti.lib.WaveSideBar");
                importList.add("com.sayuti.lib.WaveSideBar.OnLetterSelectedListener");
                return importList;

            case "CodeView":
                importList.add("br.tiagohm.codeview.CodeView");
                importList.add("br.tiagohm.codeview.Theme");
                importList.add("br.tiagohm.codeview.Language");
                importList.add("br.tiagohm.codeview.CodeView.OnHighlightListener");
                return importList;

            case "BottomSheetDialog":
                importList.add("com.google.android.material.bottomsheet.BottomSheetDialog");
                return importList;

            case "LottieAnimationView":
                importList.add("com.airbnb.lottie.*");
                return importList;

            case "CircleImageView":
                importList.add("de.hdodenhof.circleimageview.*");
                return importList;

            case "YouTubePlayerView":
                importList.add("com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.*");
                importList.add("com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener");
                importList.add("com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils");
                importList.add("com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView");
                importList.add("com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer");
                importList.add("com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener");
                return importList;

            case "OTPView":
                importList.add("affan.ahmad.otp.*");
                return importList;

            case "MaterialButton":
                importList.add("com.google.android.material.button.*");
                return importList;

            case "MaterialCardView":
                importList.add("com.google.android.material.card.*");
                return importList;

            case "Chip":
                importList.add("com.google.android.material.chip.*");
                return importList;

            case "NavigationView":
                importList.add("com.google.android.material.navigation.NavigationView");
                importList.add("com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener");
                return importList;

            case "TabLayout":
                importList.add("com.google.android.material.tabs.TabLayout");
                importList.add("com.google.android.material.tabs.TabLayout.OnTabSelectedListener");
                return importList;

            case "CardView":
                importList.add("androidx.cardview.widget.CardView");
                return importList;

            case "TextInputLayout":
            case "TextInputEditText":
                importList.add("com.google.android.material.textfield.*");
                return importList;

            case "SignInButton":
                importList.add("com.google.android.gms.common.SignInButton");
                return importList;

            case "RecyclerView":
                importList.add("androidx.recyclerview.widget.*");
                importList.add("androidx.recyclerview.widget.RecyclerView");
                importList.add("androidx.recyclerview.widget.RecyclerView.Adapter");
                importList.add("androidx.recyclerview.widget.RecyclerView.ViewHolder");
                return importList;

            case "BottomNavigationView":
                importList.add("com.google.android.material.bottomnavigation.BottomNavigationView");
                importList.add("com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener");
                return importList;

            case "FirebaseCloudMessage":
                importList.add("com.google.android.gms.tasks.OnCompleteListener");
                importList.add("com.google.android.gms.tasks.Task");
                importList.add("com.google.firebase.iid.FirebaseInstanceId");
                importList.add("com.google.firebase.iid.InstanceIdResult");
                importList.add("com.google.firebase.messaging.FirebaseMessaging");
                return importList;

            case "OSSubscriptionObserver":
                importList.add("com.onesignal.OSSubscriptionObserver");
                importList.add("com.onesignal.OneSignal");
                importList.add("org.json.JSONObject");
                return importList;

            case "PhoneAuthProvider.OnVerificationStateChangedCallbacks":
                importList.add("com.google.android.gms.tasks.OnCompleteListener");
                importList.add("com.google.android.gms.tasks.Task");
                importList.add("com.google.firebase.FirebaseException");
                importList.add("com.google.firebase.FirebaseTooManyRequestsException");
                importList.add("com.google.firebase.auth.AuthResult");
                importList.add("com.google.firebase.auth.FirebaseAuth");
                importList.add("com.google.firebase.auth.FirebaseAuthInvalidCredentialsException");
                importList.add("com.google.firebase.auth.FirebaseUser");
                importList.add("com.google.firebase.auth.PhoneAuthCredential");
                importList.add("com.google.firebase.auth.PhoneAuthProvider");
                importList.add("java.util.concurrent.TimeUnit");
                return importList;

            case "GoogleSignInClient":
                importList.add("com.google.android.gms.auth.api.signin.GoogleSignIn");
                importList.add("com.google.android.gms.auth.api.signin.GoogleSignInAccount");
                importList.add("com.google.android.gms.auth.api.signin.GoogleSignInClient");
                importList.add("com.google.android.gms.auth.api.signin.GoogleSignInOptions");
                importList.add("com.google.android.gms.common.api.ApiException");
                importList.add("com.google.android.gms.tasks.Task");
                importList.add("com.google.firebase.auth.AuthCredential");
                importList.add("com.google.firebase.auth.AuthResult");
                importList.add("com.google.firebase.auth.FirebaseAuth");
                importList.add("com.google.firebase.auth.FirebaseUser");
                importList.add("com.google.firebase.auth.GoogleAuthProvider");
                return importList;

            case "DynamicLink":
                importList.add("com.google.android.gms.tasks.OnSuccessListener");
                importList.add("com.google.android.gms.tasks.OnFailureListener");
                importList.add("com.google.firebase.dynamiclinks.DynamicLink");
                importList.add("com.google.firebase.dynamiclinks.FirebaseDynamicLinks");
                importList.add("com.google.firebase.dynamiclinks.PendingDynamicLinkData");
                importList.add("com.google.firebase.dynamiclinks.ShortDynamicLink");
                return importList;

            case "RewardedVideoAd":
                importList.add("com.google.android.gms.ads.AdError");
                importList.add("com.google.android.gms.ads.MobileAds");
                importList.add("com.google.android.gms.ads.OnUserEarnedRewardListener");
                importList.add("com.google.android.gms.ads.rewarded.RewardItem");
                importList.add("com.google.android.gms.ads.rewarded.RewardedAd");
                importList.add("com.google.android.gms.ads.rewarded.RewardedAdLoadCallback");
                return importList;

            case "com.facebook.ads.AdView":
            case "com.facebook.ads.InterstitialAd":
                importList.add("com.facebook.ads.*");
                return importList;

            default:
                if (convert != null && convert.contains(".")) {
                    importList.add(convert);
                }
                return importList;
        }
    }

    /**
     * @return List of imports required by a listener
     */
    public static ArrayList<String> d(String listener) {
        ArrayList<String> importList = new ArrayList<>();

        switch (listener) {
            case "onClickListener":
                importList.add("android.view.View");
                return importList;

            case "onTextChangedListener":
                importList.add("android.text.Editable");
                importList.add("android.text.TextWatcher");
                return importList;

            case "onCheckChangedListener":
                importList.add("android.widget.CompoundButton");
                return importList;

            case "onItemSelectedListener":
            case "onItemClickListener":
            case "onItemLongClickListener":
                importList.add("android.widget.AdapterView");
                return importList;

            case "webViewClient":
                importList.add("android.webkit.WebViewClient");
                return importList;

            case "animatorListener":
                importList.add("android.animation.Animator");
                return importList;

            case "onUploadSuccessListener":
            case "onDownloadSuccessListener":
            case "onDeleteSuccessListener":
            case "OnSuccessListener":
                importList.add("com.google.android.gms.tasks.OnSuccessListener");
                return importList;

            case "onFailureListener":
            case "OnFailureListener":
                importList.add("com.google.android.gms.tasks.OnFailureListener");
                return importList;

            case "onUploadProgressListener":
            case "onDownloadProgressListener":
                importList.add("com.google.firebase.storage.OnProgressListener");
                return importList;

            case "recognitionListener":
                importList.add("android.speech.RecognitionListener");
                return importList;

            case "onMapReadyCallback":
                importList.add("com.google.android.gms.maps.OnMapReadyCallback");
                return importList;

            case "onMapMarkerClickListener":
                importList.add("com.google.android.gms.maps.GoogleMap");
                return importList;

            case "locationListener":
                importList.add("android.location.LocationListener");
                return importList;

            case "FragmentStatePagerAdapter":
                importList.add("androidx.fragment.app.Fragment");
                importList.add("androidx.fragment.app.FragmentManager");
                importList.add("androidx.fragment.app.FragmentStatePagerAdapter");
                return importList;

            case "OTPListener":
                importList.add("affan.ahmad.otp.OTPListener");
                return importList;

            case "OnCompleteListenerFCM":
                importList.add("com.google.android.gms.tasks.OnCompleteListener");
                importList.add("com.google.android.gms.tasks.Task");
                return importList;

            case "bannerAdViewListener":
                importList.add("com.google.android.gms.ads.AdListener");
                importList.add("com.google.android.gms.ads.LoadAdError");
                return importList;

            case "fullScreenContentCallback":
                importList.add("com.google.android.gms.ads.FullScreenContentCallback");
                return importList;

            case "onUserEarnedRewardListener":
                importList.add("com.google.android.gms.ads.OnUserEarnedRewardListener");
                return importList;

            case "rewardedAdLoadCallback":
                importList.add("com.google.android.gms.ads.rewarded.RewardedAdLoadCallback");
                return importList;

            case "onSeekBarChangeListener":
            case "onDateChangeListener":
            default:
                EventsHandler.getImports(importList, listener);
                return importList;
        }
    }

    /**
     * @return The actual class info from its internal type name, as displayed in the generated code.
     */
    public static String e(String typeName) {
        return switch (typeName) {
            case "double", "double.SelectDouble" -> "double";
            case "Map" -> "HashMap<String, Object>";
            case "ListInt" -> "ArrayList<Double>";
            case "ListString" -> "ArrayList<String>";
            case "ListMap" -> "ArrayList<HashMap<String, Object>>";
            case "Timer" -> "TimerTask";
            case "Gyroscope" -> "SensorManager";
            case "Dialog" -> "AlertDialog.Builder";
            case "FirebaseDB" -> "DatabaseReference";
            case "FirebaseStorage" -> "StorageReference";
            case "Camera", "FilePicker" -> "Intent";
            case "SpeechToText" -> "SpeechRecognizer";
            case "FragmentAdapter" -> "FragmentStatePagerAdapter";
            case "Context" -> "Activity";
            case "ResString", "ResStyle", "ResColor", "ResArray", "ResDimen", "ResBool",
                 "ResInteger", "ResAttr", "ResXml", "Color" -> "int";
            default -> typeName;
        };
    }
}
