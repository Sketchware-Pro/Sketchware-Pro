package dev.aldi.sayuti.editor.manage;

import java.util.ArrayList;

import mod.hilal.saif.components.ComponentsHandler;

public class ImportClass {

    /**
     * Adds imports of a Component to a list of imports.
     */
    public static void a(String componentName, ArrayList<String> imports) {
        ComponentsHandler.getImports(componentName, imports);
        switch (componentName) {
            case "ViewPager":
                imports.add("androidx.viewpager.widget.ViewPager");
                imports.add("androidx.viewpager.widget.PagerAdapter");
                imports.add("androidx.viewpager.widget.ViewPager.OnPageChangeListener");
                imports.add("androidx.viewpager.widget.ViewPager.OnAdapterChangeListener");
                break;

            case "CollapsingToolbarLayout":
                imports.add("com.google.android.material.appbar.CollapsingToolbarLayout");
                break;

            case "SwipeRefreshLayout":
                imports.add("androidx.swiperefreshlayout.widget.SwipeRefreshLayout");
                imports.add("androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener");
                break;

            case "PatternLockView":
                imports.add("com.andrognito.patternlockview.PatternLockView");
                imports.add("com.andrognito.patternlockview.utils.*");
                imports.add("com.andrognito.patternlockview.listener.*");
                break;

            case "WaveSideBar":
                imports.add("com.sayuti.lib.WaveSideBar");
                imports.add("com.sayuti.lib.WaveSideBar.OnLetterSelectedListener");
                break;

            case "CodeView":
                imports.add("br.tiagohm.codeview.CodeView");
                imports.add("br.tiagohm.codeview.Theme");
                imports.add("br.tiagohm.codeview.Language");
                imports.add("br.tiagohm.codeview.CodeView.OnHighlightListener");
                break;

            case "BottomSheetDialog":
                imports.add("com.google.android.material.bottomsheet.BottomSheetDialog");
                break;

            case "LottieAnimationView":
                imports.add("com.airbnb.lottie.*");
                break;

            case "CircleImageView":
                imports.add("de.hdodenhof.circleimageview.*");
                break;

            case "YouTubePlayerView":
                imports.add("com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.*");
                imports.add("com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener");
                imports.add("com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils");
                imports.add("com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView");
                imports.add("com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer");
                imports.add("com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener");
                break;

            case "OTPView":
                imports.add("affan.ahmad.otp.*");
                break;

            case "MaterialButton":
                imports.add("com.google.android.material.button.*");
                break;

            case "MaterialCardView":
                imports.add("com.google.android.material.card.*");
                break;

            case "Chip":
                imports.add("com.google.android.material.chip.*");
                break;

            case "NavigationView":
                imports.add("com.google.android.material.navigation.NavigationView");
                imports.add("com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener");
                break;

            case "TabLayout":
                imports.add("com.google.android.material.tabs.TabLayout");
                imports.add("com.google.android.material.tabs.TabLayout.OnTabSelectedListener");
                break;

            case "CardView":
                imports.add("androidx.cardview.widget.CardView");
                break;

            case "TextInputLayout":
            case "TextInputEditText":
                imports.add("com.google.android.material.textfield.*");
                break;

            case "SignInButton":
                imports.add("com.google.android.gms.common.SignInButton");
                break;

            case "RecyclerView":
                imports.add("androidx.recyclerview.widget.*");
                imports.add("androidx.recyclerview.widget.RecyclerView");
                imports.add("androidx.recyclerview.widget.RecyclerView.Adapter");
                imports.add("androidx.recyclerview.widget.RecyclerView.ViewHolder");
                break;

            case "BottomNavigationView":
                imports.add("com.google.android.material.bottomnavigation.BottomNavigationView");
                imports.add("com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener");
                break;

            case "FirebaseCloudMessage":
                imports.add("com.google.android.gms.tasks.OnCompleteListener");
                imports.add("com.google.android.gms.tasks.Task");
                imports.add("com.google.firebase.iid.FirebaseInstanceId");
                imports.add("com.google.firebase.iid.InstanceIdResult");
                imports.add("com.google.firebase.messaging.FirebaseMessaging");
                break;

            case "OSSubscriptionObserver":
                imports.add("com.onesignal.OSSubscriptionObserver");
                imports.add("com.onesignal.OneSignal");
                imports.add("org.json.JSONObject");
                break;

            case "PhoneAuthProvider.OnVerificationStateChangedCallbacks":
                imports.add("com.google.android.gms.tasks.OnCompleteListener");
                imports.add("com.google.android.gms.tasks.Task");
                imports.add("com.google.firebase.FirebaseException");
                imports.add("com.google.firebase.FirebaseTooManyRequestsException");
                imports.add("com.google.firebase.auth.AuthResult");
                imports.add("com.google.firebase.auth.FirebaseAuth");
                imports.add("com.google.firebase.auth.FirebaseAuthInvalidCredentialsException");
                imports.add("com.google.firebase.auth.FirebaseUser");
                imports.add("com.google.firebase.auth.PhoneAuthCredential");
                imports.add("com.google.firebase.auth.PhoneAuthProvider");
                imports.add("java.util.concurrent.TimeUnit");
                break;

            case "GoogleSignInClient":
                imports.add("com.google.android.gms.auth.api.signin.GoogleSignIn");
                imports.add("com.google.android.gms.auth.api.signin.GoogleSignInAccount");
                imports.add("com.google.android.gms.auth.api.signin.GoogleSignInClient");
                imports.add("com.google.android.gms.auth.api.signin.GoogleSignInOptions");
                imports.add("com.google.android.gms.common.api.ApiException");
                imports.add("com.google.android.gms.tasks.Task");
                imports.add("com.google.firebase.auth.AuthCredential");
                imports.add("com.google.firebase.auth.AuthResult");
                imports.add("com.google.firebase.auth.FirebaseAuth");
                imports.add("com.google.firebase.auth.FirebaseUser");
                imports.add("com.google.firebase.auth.GoogleAuthProvider");
                break;

            case "DynamicLink":
                imports.add("com.google.android.gms.tasks.OnSuccessListener");
                imports.add("com.google.android.gms.tasks.OnFailureListener");
                imports.add("com.google.firebase.dynamiclinks.DynamicLink");
                imports.add("com.google.firebase.dynamiclinks.FirebaseDynamicLinks");
                imports.add("com.google.firebase.dynamiclinks.PendingDynamicLinkData");
                imports.add("com.google.firebase.dynamiclinks.ShortDynamicLink");
                break;

            case "RewardedVideoAd":
                imports.add("com.google.android.gms.ads.MobileAds");
                imports.add("com.google.android.gms.ads.reward.RewardItem");
                imports.add("com.google.android.gms.ads.reward.RewardedVideoAd");
                imports.add("com.google.android.gms.ads.reward.RewardedVideoAdListener");
                break;

            case "com.facebook.ads.AdView":
            case "com.facebook.ads.InterstitialAd":
                imports.add("com.facebook.ads.*");
                break;
        }
    }
}
