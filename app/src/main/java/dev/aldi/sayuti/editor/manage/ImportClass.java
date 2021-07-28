package dev.aldi.sayuti.editor.manage;

import java.util.ArrayList;

import mod.hilal.saif.components.ComponentsHandler;

public class ImportClass {

    public static void a(String componentName, ArrayList<String> imports) {
        ComponentsHandler.getImports(componentName, imports);
        if (componentName.equals("ViewPager")) {
            imports.add("androidx.viewpager.widget.ViewPager");
            imports.add("androidx.viewpager.widget.PagerAdapter");
            imports.add("androidx.viewpager.widget.ViewPager.OnPageChangeListener");
            imports.add("androidx.viewpager.widget.ViewPager.OnAdapterChangeListener");
        }
        if (componentName.equals("CollapsingToolbarLayout")) {
            imports.add("com.google.android.material.appbar.CollapsingToolbarLayout");
        }
        if (componentName.equals("SwipeRefreshLayout")) {
            imports.add("androidx.swiperefreshlayout.widget.SwipeRefreshLayout");
            imports.add("androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener");
        }
        if (componentName.equals("PatternLockView")) {
            imports.add("com.andrognito.patternlockview.PatternLockView");
            imports.add("com.andrognito.patternlockview.utils.*");
            imports.add("com.andrognito.patternlockview.listener.*");
        }
        if (componentName.equals("WaveSideBar")) {
            imports.add("com.sayuti.lib.WaveSideBar");
            imports.add("com.sayuti.lib.WaveSideBar.OnLetterSelectedListener");
        }
        if (componentName.equals("CodeView")) {
            imports.add("br.tiagohm.codeview.CodeView");
            imports.add("br.tiagohm.codeview.Theme");
            imports.add("br.tiagohm.codeview.Language");
            imports.add("br.tiagohm.codeview.CodeView.OnHighlightListener");
        }
        if (componentName.equals("BottomSheetDialog")) {
            imports.add("com.google.android.material.bottomsheet.BottomSheetDialog");
        }
        if (componentName.equals("LottieAnimationView")) {
            imports.add("com.airbnb.lottie.*");
        }
        if (componentName.equals("CircleImageView")) {
            imports.add("de.hdodenhof.circleimageview.*");
        }
        if (componentName.equals("YouTubePlayerView")) {
            imports.add("com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.*");
            imports.add("com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener");
            imports.add("com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils");
            imports.add("com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView");
            imports.add("com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer");
            imports.add("com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener");
        }
        if (componentName.equals("OTPView")) {
            imports.add("affan.ahmad.otp.*");
        }
        if (componentName.equals("MaterialButton")) {
            imports.add("com.google.android.material.button.*");
        }
        if (componentName.equals("MaterialCardView")) {
            imports.add("com.google.android.material.card.*");
        }
        if (componentName.equals("Chip")) {
            imports.add("com.google.android.material.chip.*");
        }
        if (componentName.equals("NavigationView")) {
            imports.add("com.google.android.material.navigation.NavigationView");
            imports.add("com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener");
        }
        if (componentName.equals("TabLayout")) {
            imports.add("com.google.android.material.tabs.TabLayout");
            imports.add("com.google.android.material.tabs.TabLayout.OnTabSelectedListener");
        }
        if (componentName.equals("CardView")) {
            imports.add("androidx.cardview.widget.CardView");
        }
        if (componentName.equals("TextInputLayout")) {
            imports.add("com.google.android.material.textfield.*");
        }
        if (componentName.equals("TextInputEditText")) {
            imports.add("com.google.android.material.textfield.*");
        }
        if (componentName.equals("SignInButton")) {
            imports.add("com.google.android.gms.common.SignInButton");
        }
        if (componentName.equals("RecyclerView")) {
            imports.add("androidx.recyclerview.widget.*");
            imports.add("androidx.recyclerview.widget.RecyclerView");
            imports.add("androidx.recyclerview.widget.RecyclerView.Adapter");
            imports.add("androidx.recyclerview.widget.RecyclerView.ViewHolder");
        }
        if (componentName.equals("BottomNavigationView")) {
            imports.add("com.google.android.material.bottomnavigation.BottomNavigationView");
            imports.add("com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener");
        }
        if (componentName.equals("FirebaseCloudMessage")) {
            imports.add("com.google.android.gms.tasks.OnCompleteListener");
            imports.add("com.google.android.gms.tasks.Task");
            imports.add("com.google.firebase.iid.FirebaseInstanceId");
            imports.add("com.google.firebase.iid.InstanceIdResult");
            imports.add("com.google.firebase.messaging.FirebaseMessaging");
        }
        if (componentName.equals("OSSubscriptionObserver")) {
            imports.add("com.onesignal.OSSubscriptionObserver");
            imports.add("com.onesignal.OneSignal");
            imports.add("org.json.JSONObject");
        }
        if (componentName.equals("PhoneAuthProvider.OnVerificationStateChangedCallbacks")) {
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
        }
        if (componentName.equals("GoogleSignInClient")) {
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
        }
        if (componentName.equals("DynamicLink")) {
            imports.add("com.google.firebase.dynamiclinks.DynamicLink");
            imports.add("com.google.firebase.dynamiclinks.FirebaseDynamicLinks");
            imports.add("com.google.firebase.dynamiclinks.PendingDynamicLinkData");
            imports.add("com.google.firebase.dynamiclinks.ShortDynamicLink");
        }
        if (componentName.equals("RewardedVideoAd")) {
            imports.add("com.google.android.gms.ads.MobileAds");
            imports.add("com.google.android.gms.ads.reward.RewardItem");
            imports.add("com.google.android.gms.ads.reward.RewardedVideoAd");
            imports.add("com.google.android.gms.ads.reward.RewardedVideoAdListener");
        }
        if (componentName.equals("com.facebook.ads.AdView") || componentName.equals("com.facebook.ads.InterstitialAd")) {
            imports.add("com.facebook.ads.*");
        }
    }
}
