package a.a.a;

import com.besome.sketch.beans.ComponentBean;

import java.util.ArrayList;

import dev.aldi.sayuti.block.ExtraBlockClassInfo;
import mod.hilal.saif.components.ComponentsHandler;
import mod.hilal.saif.events.EventsHandler;

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
                switch (typeName) {
                    case "inputOnly":
                    case "inputCode":
                    case "import":
                        return new Gx("Input");

                    default:
                        return new Gx("String");
                }

            case "a":
                return new Gx("Map");

            case "l":
                switch (typeName) {
                    case "List Map":
                        return new Gx("ListMap");

                    case "List String":
                        return new Gx("ListString");

                    case "List Number":
                        return new Gx("ListInt");
                }

            case "v":
                return new Gx(typeName);

            case "p":
            case "m":
                return new Gx(b(typeName));

            default:
                return ExtraBlockClassInfo.getTypeVar(type, typeName);
        }
    }

    /**
     * @return The Type name of a Component
     */
    public static String a(int componentId) {
        switch (componentId) {
            case ComponentBean.COMPONENT_TYPE_INTENT:
                return "Intent";

            case ComponentBean.COMPONENT_TYPE_SHAREDPREF:
                return "SharedPreferences";

            case ComponentBean.COMPONENT_TYPE_CALENDAR:
                return "Calendar";

            case ComponentBean.COMPONENT_TYPE_VIBRATOR:
                return "Vibrator";

            case ComponentBean.COMPONENT_TYPE_TIMERTASK:
                return "Timer";

            case ComponentBean.COMPONENT_TYPE_FIREBASE:
                return "FirebaseDB";

            case ComponentBean.COMPONENT_TYPE_DIALOG:
                return "Dialog";

            case ComponentBean.COMPONENT_TYPE_MEDIAPLAYER:
                return "MediaPlayer";

            case ComponentBean.COMPONENT_TYPE_SOUNDPOOL:
                return "SoundPool";

            case ComponentBean.COMPONENT_TYPE_OBJECTANIMATOR:
                return "ObjectAnimator";

            case ComponentBean.COMPONENT_TYPE_GYROSCOPE:
                return "Gyroscope";

            case ComponentBean.COMPONENT_TYPE_FIREBASE_AUTH:
                return "FirebaseAuth";

            case ComponentBean.COMPONENT_TYPE_INTERSTITIAL_AD:
                return "InterstitialAd";

            case ComponentBean.COMPONENT_TYPE_FIREBASE_STORAGE:
                return "FirebaseStorage";

            case ComponentBean.COMPONENT_TYPE_CAMERA:
                return "Camera";

            case ComponentBean.COMPONENT_TYPE_FILE_PICKER:
                return "FilePicker";

            case ComponentBean.COMPONENT_TYPE_REQUEST_NETWORK:
                return "RequestNetwork";

            case ComponentBean.COMPONENT_TYPE_TEXT_TO_SPEECH:
                return "TextToSpeech";

            case ComponentBean.COMPONENT_TYPE_SPEECH_TO_TEXT:
                return "SpeechToText";

            case ComponentBean.COMPONENT_TYPE_BLUETOOTH_CONNECT:
                return "BluetoothConnect";

            case ComponentBean.COMPONENT_TYPE_LOCATION_MANAGER:
                return "LocationManager";

            case 22:
                return "RewardedVideoAd";

            case 23:
                return "ProgressDialog";

            case 24:
                return "DatePickerDialog";

            case 25:
                return "TimePickerDialog";

            case 26:
                return "Notification";

            case 27:
                return "FragmentStatePagerAdapter";

            case 28:
                return "PhoneAuthProvider.OnVerificationStateChangedCallbacks";

            case 29:
                return "DynamicLink";

            case 30:
                return "FirebaseCloudMessage";

            case 31:
                return "GoogleSignInClient";

            case 32:
                return "OSSubscriptionObserver";

            case 33:
                return "com.facebook.ads.AdView";

            case 34:
                return "com.facebook.ads.InterstitialAd";

            default:
                return ComponentsHandler.var(componentId);
        }
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
        switch (type) {
            case 1:
                return "ListInt";

            case 2:
                return "ListString";

            case 3:
                return "ListMap";

            default:
                return "";
        }
    }

    public static String b(String name) {
        switch (name) {
            case "intent":
            case "Intent":
                return "Intent";

            case "file":
            case "File":
            case "File (Shared Preferences)":
                return "SharedPreferences";

            case "calendar":
            case "Calendar":
                return "Calendar";

            case "vibrator":
            case "Vibrator":
                return "Vibrator";

            case "Timer":
                return "Timer";

            case "dialog":
            case "Dialog":
                return "Dialog";

            case "MediaPlayer":
                return "MediaPlayer";

            case "SoundPool":
                return "SoundPool";

            case "ObjectAnimator":
                return "ObjectAnimator";

            case "firebase":
            case "Firebase":
            case "Firebase DB":
                return "FirebaseDB";

            case "firebaseauth":
            case "Firebase Auth":
                return "FirebaseAuth";

            case "gyroscope":
            case "Gyroscope":
                return "Gyroscope";

            case "InterstitialAd":
                return "InterstitialAd";

            case "varBool":
                return "boolean.SelectBoolean";

            case "varInt":
                return "double.SelectDouble";

            case "varStr":
                return "String.SelectString";

            case "varMap":
                return "Map";

            case "listInt":
                return "ListInt";

            case "listStr":
                return "ListString";

            case "listMap":
                return "ListMap";

            case "list":
                return "List";

            case "view":
                return "View";

            case "textview":
                return "TextView";

            case "edittext":
                return "EditText";

            case "imageview":
                return "ImageView";

            case "compoundButton":
                return "CompoundButton";

            case "checkbox":
                return "CheckBox";

            case "switch":
                return "Switch";

            case "listSpn":
                return "AdapterView";

            case "listview":
                return "ListView";

            case "spinner":
                return "Spinner";

            case "webview":
                return "WebView";

            case "calendarview":
                return "CalendarView";

            case "adview":
                return "AdView";

            case "mapview":
                return "MapView";

            case "timer":
                return "Timer";

            case "mediaplayer":
                return "MediaPlayer";

            case "soundpool":
                return "SoundPool";

            case "objectanimator":
                return "ObjectAnimator";

            case "seekbar":
                return "SeekBar";

            case "interstitialad":
                return "InterstitialAd";

            case "firebasestorage":
                return "FirebaseStorage";

            case "camera":
                return "Camera";

            case "filepicker":
                return "FilePicker";

            case "requestnetwork":
                return "RequestNetwork";

            case "progressbar":
                return "ProgressBar";

            case "texttospeech":
                return "TextToSpeech";

            case "speechtotext":
                return "SpeechToText";

            case "bluetoothconnect":
                return "BluetoothConnect";

            case "locationmanager":
                return "LocationManager";

            case "radiobutton":
                return "RadioButton";

            case "ratingbar":
                return "RatingBar";

            case "videoview":
                return "VideoView";

            case "searchview":
                return "SearchView";

            case "gridview":
                return "GridView";

            case "actv":
                return "AutoCompleteTextView";

            case "mactv":
                return "MultiAutoCompleteTextView";

            case "tablayout":
                return "TabLayout";

            case "viewpager":
                return "ViewPager";

            case "badgeview":
                return "BadgeView";

            case "bottomnavigation":
                return "BottomNavigationView";

            case "patternview":
                return "PatternLockView";

            case "sidebar":
                return "WaveSideBar";

            default:
                return ExtraBlockClassInfo.getName(name);
        }
    }

    /**
     * @return The Type name of the Variable taken from its ID
     */
    public static String c(int type) {
        switch (type) {
            case 0:
                return "boolean";

            case 1:
                return "double";

            case 2:
                return "String";

            case 3:
                return "Map";

            default:
                return "";
        }
    }

    /**
     * @return Imports needed for a type
     */
    public static ArrayList<String> getImportsByTypeName(String name) {
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
                importList.add("com.google.android.gms.ads.rewarded.RewardItem");
                importList.add("com.google.android.gms.ads.rewarded.RewardedAd");
                importList.add("com.google.android.gms.ads.rewarded.RewardedAdLoadCallback");
                return importList;

            case "com.facebook.ads.AdView":
            case "com.facebook.ads.InterstitialAd":
                importList.add("com.facebook.ads.*");
                return importList;

            default:
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

    public static String e(String typeName) {
        switch (typeName) {
            case "double":
            case "double.SelectDouble":
                return "double";

            case "Map":
                return "HashMap<String, Object>";

            case "ListInt":
                return "ArrayList<Double>";

            case "ListString":
                return "ArrayList<String>";

            case "ListMap":
                return "ArrayList<HashMap<String, Object>>";

            case "Timer":
                return "TimerTask";

            case "Gyroscope":
                return "SensorManager";

            case "Dialog":
                return "AlertDialog.Builder";

            case "FirebaseDB":
                return "DatabaseReference";

            case "FirebaseStorage":
                return "StorageReference";

            case "Camera":
            case "FilePicker":
                return "Intent";

            case "SpeechToText":
                return "SpeechRecognizer";

            case "FragmentAdapter":
                return "FragmentStatePagerAdapter";

            case "Context":
                return "Activity";

            case "ResString":
            case "ResStyle":
            case "ResColor":
            case "ResArray":
            case "ResDimen":
            case "ResBool":
            case "ResInteger":
            case "ResAttr":
            case "ResXml":
            case "Color":
                return "int";

            default:
                return typeName;
        }
    }
}