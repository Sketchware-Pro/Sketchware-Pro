package mod.agus.jcoderz.editor.event;

import java.util.ArrayList;

import a.a.a.Gx;
import a.a.a.Lx;
import mod.hilal.saif.events.EventsHandler;
import pro.sketchware.R;

public class ManageEvent {

    /**
     * Used in {@link a.a.a.oq#c(Gx)} to retrieve extra Events of Components.
     */
    public static void a(Gx gx, ArrayList<String> events) {
        if (gx.a("RatingBar")) {
            events.add("onRatingChanged");
        }
        if (gx.a("TimePicker")) {
            events.add("onTimeChanged");
        }
        if (gx.a("DatePicker")) {
            events.add("onDateChanged");
        }
        if (gx.a("VideoView")) {
            events.add("onPrepared");
            events.add("onError");
            events.add("onCompletion");
        }
        if (gx.a("SearchView")) {
            events.add("onQueryTextSubmit");
            events.add("onQueryTextChanged");
        }
        if (gx.a("ListView")) {
            events.add("onScrollChanged");
            events.add("onScrolled");
        }
        if (gx.a("RecyclerView")) {
            events.add("onBindCustomView");
            events.add("onRecyclerScrollChanged");
            events.add("onRecyclerScrolled");
        }
        if (gx.a("GridView")) {
            events.add("onItemClicked");
            events.add("onItemLongClicked");
            events.add("onBindCustomView");
        }
        if (gx.a("Spinner")) {
            events.add("onBindCustomView");
        }
        if (gx.a("ViewPager")) {
            events.add("onBindCustomView");
            events.add("onPageScrolled");
            events.add("onPageSelected");
            events.add("onPageChanged");
        }
        if (gx.a("TabLayout")) {
            events.add("onTabSelected");
            events.add("onTabUnselected");
            events.add("onTabReselected");
        }
        if (gx.a("BottomNavigationView")) {
            events.add("onNavigationItemSelected");
        }
        if (gx.a("PatternLockView")) {
            events.add("onPatternLockStarted");
            events.add("onPatternLockProgress");
            events.add("onPatternLockComplete");
            events.add("onPatternLockCleared");
        }
        if (gx.a("WaveSideBar")) {
            events.add("onLetterSelected");
        }
        EventsHandler.addEvents(gx, events);
    }

    /**
     * Used in {@link a.a.a.oq#b(Gx)} to get extra listeners for Components and Widgets.
     */
    public static void b(Gx gx, ArrayList<String> listeners) {
        if (gx.a("RatingBar")) {
            listeners.add("OnRatingBarChangeListener");
        }
        if (gx.a("TimePicker")) {
            listeners.add("OnTimeChangeListener");
        }
        if (gx.a("DatePicker")) {
            listeners.add("OnDateChangeListener");
        }
        if (gx.a("VideoView")) {
            listeners.add("OnPreparedListener");
            listeners.add("OnErrorListener");
            listeners.add("OnCompletionListener");
        }
        if (gx.a("SearchView")) {
            listeners.add("OnQueryTextListener");
        }
        if (gx.a("TimePickerDialog")) {
            listeners.add("OnTimeSetListener");
        }
        if (gx.a("DatePickerDialog")) {
            listeners.add("OnDateSetListener");
        }
        if (gx.a("FragmentAdapter")) {
            listeners.add("FragmentStatePagerAdapter");
        }
        if (gx.a("RewardedVideoAd")) {
            listeners.add("rewardedAdLoadCallback");
            listeners.add("fullScreenContentCallback");
            listeners.add("onUserEarnedRewardListener");
        }
        if (gx.a("ListView")) {
            listeners.add("OnScrollListener");
        }
        if (gx.a("RecyclerView")) {
            listeners.add("OnRecyclerScrollListener");
        }
        if (gx.a("GridView")) {
            listeners.add("OnGridItemClickListener");
            listeners.add("OnGridItemLongClickListener");
        }
        if (gx.a("ViewPager")) {
            listeners.add("OnPageChangeListener");
            listeners.add("OnAdapterChangeListener");
        }
        if (gx.a("TabLayout")) {
            listeners.add("OnTabSelectedListener");
        }
        if (gx.a("BottomNavigationView")) {
            listeners.add("OnNavigationItemSelected");
        }
        if (gx.a("PatternLockView")) {
            listeners.add("PatternLockViewListener");
        }
        if (gx.a("WaveSideBar")) {
            listeners.add("OnLetterSelectedListener");
        }
        if (gx.a("FirebaseAuth")) {
            listeners.add("authUpdateEmailComplete");
            listeners.add("authUpdatePasswordComplete");
            listeners.add("authEmailVerificationSent");
            listeners.add("authDeleteUserComplete");
            listeners.add("authsignInWithPhoneAuth");
            listeners.add("authUpdateProfileComplete");
            listeners.add("googleSignInListener");
        }
        if (gx.a("FirebasePhoneAuth")) {
            listeners.add("OnVerificationStateChangedListener");
        }
        if (gx.a("FirebaseDynamicLink")) {
            listeners.add("OnSuccessListener");
            listeners.add("OnFailureListener");
        }
        if (gx.a("FirebaseCloudMessage")) {
            listeners.add("OnCompleteListenerFCM");
        }
        if (gx.a("FBAdsBanner")) {
            listeners.add("FBAdsBanner_AdListener");
        }
        if (gx.a("FBAdsInterstitial")) {
            listeners.add("FBAdsInterstitial_InterstitialAdListener");
        }
        EventsHandler.addListeners(gx, listeners);
    }

    /**
     * Used in {@link a.a.a.oq#b(String)} to get extra listeners' Events.
     */
    public static void c(String eventName, ArrayList<String> list) {
        switch (eventName) {
            case "rewardedAdLoadCallback":
                list.add("onRewardAdLoaded");
                list.add("onRewardAdFailedToLoad");
                return;

            case "onUserEarnedRewardListener":
                list.add("onUserEarnedReward");
                return;

            case "OnCompletionListener":
                list.add("onCompletion");
                return;

            case "OnDateSetListener":
                list.add("onDateSet");
                return;

            case "OnQueryTextListener":
                list.add("onQueryTextSubmit");
                list.add("onQueryTextChanged");
                return;

            case "OnVerificationStateChangedListener":
                list.add("onVerificationCompleted");
                list.add("onVerificationFailed");
                list.add("onCodeSent");
                return;

            case "OnScrollListener":
                list.add("onScrollChanged");
                list.add("onScrolled");
                return;

            case "authsignInWithPhoneAuth":
                list.add("signInWithPhoneAuthComplete");
                return;

            case "FragmentStatePagerAdapter":
                list.add("onTabAdded");
                list.add("onFragmentAdded");
                return;

            case "OnTimeSetListener":
                list.add("onTimeSet");
                return;

            case "OnFailureListener":
                list.add("onFailureLink");
                return;

            case "authUpdatePasswordComplete":
                list.add("onUpdatePasswordComplete");
                return;

            case "OnSuccessListener":
                list.add("onSuccessLink");
                return;

            case "OnGridItemClickListener":
                list.add("onItemClicked");
                return;

            case "OnRecyclerScrollListener":
                list.add("onRecyclerScrollChanged");
                list.add("onRecyclerScrolled");
                return;

            case "OnTimeChangeListener":
                list.add("onTimeChanged");
                return;

            case "authDeleteUserComplete":
                list.add("onDeleteUserComplete");
                return;

            case "authUpdateProfileComplete":
                list.add("onUpdateProfileComplete");
                return;

            case "OnPageChangeListener":
                list.add("onPageScrolled");
                list.add("onPageSelected");
                list.add("onPageChanged");
                return;

            case "OnErrorListener":
                list.add("onError");
                return;

            case "authEmailVerificationSent":
                list.add("onEmailVerificationSent");
                return;

            case "authUpdateEmailComplete":
                list.add("onUpdateEmailComplete");
                return;

            case "OnPreparedListener":
                list.add("onPrepared");
                return;

            case "OnNavigationItemSelected":
                list.add("onNavigationItemSelected");
                return;

            case "OnLetterSelectedListener":
                list.add("onLetterSelected");
                return;

            case "FBAdsInterstitial_InterstitialAdListener":
                list.add("FBAdsInterstitial_onError");
                list.add("FBAdsInterstitial_onAdLoaded");
                list.add("FBAdsInterstitial_onAdClicked");
                list.add("FBAdsInterstitial_onLoggingImpression");
                list.add("FBAdsInterstitial_onInterstitialDisplayed");
                list.add("FBAdsInterstitial_onInterstitialDismissed");
                return;

            case "OnRatingBarChangeListener":
                list.add("onRatingChanged");
                return;

            case "OnTabSelectedListener":
                list.add("onTabSelected");
                list.add("onTabUnselected");
                list.add("onTabReselected");
                return;

            case "OnDateChangeListener":
                list.add("onDateChanged");
                return;

            case "OnCompleteListenerFCM":
                list.add("onCompleteRegister");
                return;

            case "PatternLockViewListener":
                list.add("onPatternLockStarted");
                list.add("onPatternLockProgress");
                list.add("onPatternLockComplete");
                list.add("onPatternLockCleared");
                return;

            case "FBAdsBanner_AdListener":
                list.add("FBAdsBanner_onError");
                list.add("FBAdsBanner_onAdLoaded");
                list.add("FBAdsBanner_onAdClicked");
                list.add("FBAdsBanner_onLoggingImpression");
                return;

            case "OnGridItemLongClickListener":
                list.add("onItemLongClicked");
                return;

            case "googleSignInListener":
                list.add("onGoogleSignIn");
                return;

            default:
                EventsHandler.addEventsToListener(eventName, list);
        }
    }

    public static int d(String str) {
        return switch (str) {
            case "onUpdateProfileComplete" -> R.drawable.ic_mtrl_user_edit;
            case "FBAdsBanner_onLoggingImpression", "FBAdsInterstitial_onLoggingImpression" ->
                    R.drawable.event_fbads_impression;
            case "onEmailVerificationSent" -> R.drawable.ic_mtrl_sms_check;
            case "onDateChanged", "onDateSet" -> R.drawable.ic_mtrl_date_changed;
            case "onFailureLink" -> R.drawable.ic_mtrl_link_fail;
            case "onDeleteUserComplete" -> R.drawable.ic_mtrl_user_remove;
            case "onCompletion" -> R.drawable.ic_mtrl_vid_completed;
            case "onQueryTextChanged", "onQueryTextSubmit" -> R.drawable.ic_mtrl_search;
            case "onUpdateEmailComplete" -> R.drawable.ic_mtrl_email_sent;
            case "onRestoreInstanceState" -> R.drawable.ic_mtrl_warning;
            case "onError" -> R.drawable.ic_mtrl_vid_error;
            case "onVerificationCompleted" -> R.drawable.ic_mtrl_verified_user;
            case "onSaveInstanceState", "onCreateOptionsMenu", "onCreateContextMenu",
                 "onContextItemSelected", "onOptionsItemSelected" ->
                    R.drawable.bg_event_type_activity;
            case "onSuccessLink" -> R.drawable.ic_mtrl_link_check;
            case "onPatternLockCleared", "onPatternLockProgress", "onPatternLockStarted",
                 "onPatternLockComplete" -> R.drawable.ic_mtrl_pattern;
            case "FBAdsInterstitial_onAdClicked", "FBAdsBanner_onAdClicked" ->
                    R.drawable.event_fbads_click;
            case "onAccountPickerCancelled" -> R.drawable.ic_mtrl_user_delete;
            case "onVerificationFailed" -> R.drawable.ic_mtrl_gpp_bad;
            case "onGoogleSignIn" -> R.drawable.event_google_signin;
            case "onAccountPicker" -> R.drawable.ic_mtrl_group;
            case "onFragmentAdded" -> R.drawable.ic_mtrl_viewpager;
            case "onTimeSet", "onTimeChanged" -> R.drawable.ic_mtrl_timer;
            case "signInWithPhoneAuthComplete", "onCodeSent" -> R.drawable.ic_mtrl_email_sent;
            case "FBAdsInterstitial_onInterstitialDismissed" -> R.drawable.event_fbads_dismiss;
            case "onRatingChanged" -> R.drawable.ic_mtrl_star;
            case "FBAdsInterstitial_onInterstitialDisplayed" -> R.drawable.event_fbads_displayed;
            case "onLetterSelected" -> R.drawable.ic_mtrl_abc_click;
            case "onScrollChanged", "onPageChanged", "onPageScrolled", "onRecyclerScrolled",
                 "onRecyclerScrollChanged", "onNavigationItemSelected", "onScrolled",
                 "onTabUnselected", "onPageSelected", "onTabSelected", "onTabReselected" ->
                    R.drawable.ic_mtrl_touch_long;
            case "onPrepared" -> R.drawable.ic_mtrl_vid_prepared;
            case "FBAdsInterstitial_onError", "FBAdsBanner_onError" -> R.drawable.event_fbads_error;
            case "FBAdsInterstitial_onAdLoaded", "FBAdsBanner_onAdLoaded" ->
                    R.drawable.event_fbads_loaded;
            case "onTabAdded" -> R.drawable.ic_mtrl_post_added;
            case "onCompleteRegister" -> R.drawable.ic_mtrl_user_register_complete;
            case "onUpdatePasswordComplete" -> R.drawable.ic_mtrl_password;
            case "onUserEarnedReward" -> R.drawable.ic_mtrl_payment;
            default -> EventsHandler.getIcon(str);
        };
    }

    /**
     * @return Descriptions for Events added by Agus
     */
    public static String e(String eventName) {
        return switch (eventName) {
            case "FBAdsBanner_onLoggingImpression", "FBAdsInterstitial_onLoggingImpression" ->
                    "onLoggingImpression";
            case "onScrolled", "onRecyclerScrolled" -> "onScroll";
            case "onFailureLink" -> "onFailure";
            case "onSaveInstanceState" -> "On activity save instance state";
            case "onCreateOptionsMenu" -> "On create options menu";
            case "onVerificationCompleted" -> "onVerificationCompleted";
            case "onRecyclerScrollChanged", "onScrollChanged" -> "onScrollStateChanged";
            case "onCreateContextMenu" -> "On create context menu";
            case "onRestoreInstanceState" -> "On activity restore instance state";
            case "onContextItemSelected" -> "On context menu selected";
            case "onSuccessLink" -> "onSuccess";
            case "onAccountPickerCancelled" -> "onAccountCancelled";
            case "onFragmentAdded" -> "Return Fragment";
            case "onPageChanged" -> "onPageScrollStateChanged";
            case "FBAdsInterstitial_onInterstitialDismissed" -> "onInterstitialDismissed";
            case "FBAdsInterstitial_onInterstitialDisplayed" -> "onInterstitialDisplayed";
            case "onOptionsItemSelected" -> "On options menu selected";
            case "onBannerAdClicked", "FBAdsBanner_onAdClicked", "FBAdsInterstitial_onAdClicked" ->
                    "onAdClicked";
            case "FBAdsBanner_onError", "FBAdsInterstitial_onError" -> "onError";
            case "onBannerAdLoaded", "FBAdsBanner_onAdLoaded", "FBAdsInterstitial_onAdLoaded" ->
                    "onAdLoaded";
            case "onTabAdded" -> "Return Title";
            case "onCompleteRegister" -> "onComplete";
            case "onBannerAdFailedToLoad", "onInterstitialAdFailedToLoad" -> "onAdFailedToLoad";
            case "onBannerAdClosed" -> "onAdClosed";
            case "onRewardAdLoaded" -> "onRewardedAdLoaded";
            case "onRewardAdFailedToLoad" -> "onRewardedAdFailedToLoad";
            case "onUpdateProfileComplete", "onEmailVerificationSent", "onDateChanged",
                 "onDeleteUserComplete", "onNavigationItemSelected", "onDateSet", "onCompletion",
                 "onPatternLockCleared", "onQueryTextChanged", "onUpdateEmailComplete", "onError",
                 "onPageScrolled", "onVerificationFailed", "onGoogleSignIn", "onAccountPicker",
                 "onTimeSet", "signInWithPhoneAuthComplete", "onRatingChanged",
                 "onPatternLockProgress", "onTabSelected", "onTabReselected", "onLetterSelected",
                 "onTabUnselected", "onPageSelected", "onPrepared", "onQueryTextSubmit",
                 "onCodeSent", "onTimeChanged", "onUpdatePasswordComplete",
                 "onAdDismissedFullScreenContent", "onAdFailedToShowFullScreenContent",
                 "onAdShowedFullScreenContent", "onUserEarnedReward" -> eventName;
            default -> EventsHandler.getDesc(eventName);
        };
    }

    public static String f(String targetId, String eventName, String eventLogic) {
        String code;
        final String resetInterstitialAd = targetId.isEmpty() ? "" : targetId + " = null;\r\n";
        final String setAdFullScreenContentCallback = targetId.isEmpty() ? "\r\n" : targetId + " = _param1;\r\n" +
                targetId + ".setFullScreenContentCallback(_" + targetId + "_full_screen_content_callback);\r\n" +
                eventLogic + "\r\n";
        return switch (eventName) {
            case "onUpdateProfileComplete", "onEmailVerificationSent", "onDeleteUserComplete",
                 "onUpdateEmailComplete", "onUpdatePasswordComplete" -> "@Override\r\n" +
                    "public void onComplete(Task<Void> _param1) {\r\n" +
                    "final boolean _success = _param1.isSuccessful();\r\n" +
                    "final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : \"\";\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "FBAdsBanner_onLoggingImpression", "FBAdsInterstitial_onLoggingImpression" ->
                    "@Override\r\n" +
                            "public void onLoggingImpression(Ad ad) {\r\n" +
                            eventLogic + "\r\n" +
                            "}";
            case "onScrolled" -> "@Override\r\n" +
                    "public void onScroll(AbsListView abs, int _firstVisibleItem, int _visibleItemCount, int _totalItemCount) {\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onDateChanged" -> "@Override\r\n" +
                    "public void onDateChanged(DatePicker _datePicker, int _year, int _month, int _day) {\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onFailureLink" -> "@Override\r\n" +
                    "public void onFailure(Exception _e) {\r\n" +
                    "final String _errorMessage = _e.getMessage();\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onNavigationItemSelected" -> "@Override\r\n" +
                    "public boolean onNavigationItemSelected(MenuItem item) {\r\n" +
                    "final int _itemId = item.getItemId();\r\n" +
                    eventLogic + "\r\n" +
                    "return true;\r\n" +
                    "}";
            case "onDateSet" -> "@Override\r\n" +
                    "public void onDateSet(DatePicker _datePicker, int _year, int _month, int _day) {\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onCompletion" -> "@Override\r\n" +
                    "public void onCompletion(MediaPlayer _mediaPlayer) {\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onSaveInstanceState" -> "@Override\r\n" +
                    "protected void onSaveInstanceState(Bundle outState) {\r\n" +
                    eventLogic + "\r\n" +
                    "super.onSaveInstanceState(outState);\r\n" +
                    "}";
            case "onCreateOptionsMenu" -> "@Override\r\n" +
                    "public boolean onCreateOptionsMenu(Menu menu) {\r\n" +
                    eventLogic + "\r\n" +
                    "return super.onCreateOptionsMenu(menu);\r\n" +
                    "}";
            case "onQueryTextChanged" -> "@Override\r\n" +
                    "public boolean onQueryTextChange(String _charSeq) {\r\n" +
                    eventLogic + "\r\n" +
                    "return true;\r\n" +
                    "}";
            case "onError" -> "@Override\r\n" +
                    "public boolean onError(MediaPlayer _mediaPlayer, int _what, int _extra) {\r\n" +
                    eventLogic + "\r\n" +
                    "return true;\r\n" +
                    "}";
            case "onVerificationCompleted" -> "@Override\r\n" +
                    "public void onVerificationCompleted(PhoneAuthCredential _credential) {\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onRecyclerScrollChanged" -> "@Override\r\n" +
                    "public void onScrollStateChanged(RecyclerView recyclerView, int _scrollState) {\r\n" +
                    "super.onScrollStateChanged(recyclerView, _scrollState);\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onCreateContextMenu" -> "@Override\r\n" +
                    "public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {\r\n" +
                    eventLogic + "\r\n" +
                    "super.onCreateContextMenu(menu, view, menuInfo);\r\n" +
                    "}";
            case "onRestoreInstanceState" -> "@Override\r\n" +
                    "protected void onRestoreInstanceState(Bundle savedInstanceState) {\r\n" +
                    eventLogic + "\r\n" +
                    "super.onRestoreInstanceState(savedInstanceState);\r\n" +
                    "}";
            case "onContextItemSelected" -> "@Override\r\n" +
                    "public boolean onContextItemSelected(MenuItem item) {\r\n" +
                    eventLogic + "\r\n" +
                    "return super.onContextItemSelected(item);\r\n" +
                    "}";
            case "onRecyclerScrolled" -> "@Override\r\n" +
                    "public void onScrolled(RecyclerView recyclerView, int _offsetX, int _offsetY) {\r\n" +
                    "super.onScrolled(recyclerView, _offsetX, _offsetY);\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onSuccessLink" -> "@Override\r\n" +
                    "public void onSuccess(PendingDynamicLinkData _pendingDynamicLinkData) {\r\n" +
                    "final String _link = _pendingDynamicLinkData != null ? _pendingDynamicLinkData.getLink().toString() : \"\";\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onPatternLockCleared" -> "@Override\r\n" +
                    "public void onCleared() {\r\n" +
                    eventLogic + "\n" +
                    "}";
            case "FBAdsInterstitial_onAdClicked" -> "@Override\r\n" +
                    "public void onAdClicked(Ad ad) {\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onPageScrolled" -> "@Override\r\n" +
                    "public void onPageScrolled(int _position, float _positionOffset, int _positionOffsetPixels) {\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onVerificationFailed" -> "@Override\r\n" +
                    "public void onVerificationFailed(FirebaseException e) {\r\n" +
                    "final String _exception = e.getMessage();\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onGoogleSignIn" -> "@Override\r\n" +
                    "public void onComplete(Task<AuthResult> task) {\r\n" +
                    "final boolean _success = task.isSuccessful();\r\n" +
                    "final String _errorMessage = task.getException() != null ? task.getException().getMessage() : \"\";\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onFragmentAdded" -> //noinspection DuplicateExpressions
                    "@Override\r\n" +
                            "public Fragment getItem(int _position) {\r\n" +
                            (!eventLogic.isEmpty() ? eventLogic + "\r\n" :
                                    "return null;\r\n") +
                            "}";
            case "onTimeSet" -> "@Override\r\n" +
                    "public void onTimeSet(TimePicker _timePicker, int _hour, int _minute) {\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onPageChanged" -> "@Override\r\n" +
                    "public void onPageScrollStateChanged(int _scrollState) {\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "signInWithPhoneAuthComplete" -> "@Override\r\n" +
                    "public void onComplete(Task<AuthResult> task) {\r\n" +
                    "final boolean _success = task.isSuccessful();\r\n" +
                    "final String _errorMessage = task.getException() != null ? task.getException().getMessage() : \"\";\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "FBAdsInterstitial_onInterstitialDismissed" -> "@Override\r\n" +
                    "public void onInterstitialDismissed(Ad ad) {\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onRatingChanged" -> "@Override\r\n" +
                    "public void onRatingChanged(RatingBar _ratingBar, float _value, boolean _fromUser) {\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "FBAdsInterstitial_onInterstitialDisplayed" -> "@Override\r\n" +
                    "public void onInterstitialDisplayed(Ad ad) {\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onPatternLockProgress" -> "@Override\r\n" +
                    "public void onProgress(List<PatternLockView.Dot> _pattern) {\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onOptionsItemSelected" -> "@Override\r\n" +
                    "public boolean onOptionsItemSelected(MenuItem item) {\r\n" +
                    "final int _id = item.getItemId();\r\n" +
                    "final String _title = (String) item.getTitle();\r\n" +
                    eventLogic + "\r\n" +
                    "return super.onOptionsItemSelected(item);\r\n" +
                    "}";
            case "onPatternLockStarted" -> "@Override\r\n" +
                    "public void onStarted() {\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onPatternLockComplete" -> "@Override\r\n" +
                    "public void onComplete(List<PatternLockView.Dot> _pattern) {\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onTabSelected" -> "@Override\r\n" +
                    "public void onTabSelected(TabLayout.Tab tab) {\r\n" +
                    "final int _position = tab.getPosition();\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onTabReselected" -> "@Override\r\n" +
                    "public void onTabReselected(TabLayout.Tab tab) {\r\n" +
                    "final int _position = tab.getPosition();\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onLetterSelected" -> "@Override\r\n" +
                    "public void onLetterSelected(String _index) {\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onTabUnselected" -> "@Override\r\n" +
                    "public void onTabUnselected(TabLayout.Tab tab) {\r\n" +
                    "final int _position = tab.getPosition();\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onPageSelected" -> "@Override\r\n" +
                    "public void onPageSelected(int _position) {\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "FBAdsBanner_onAdClicked" -> "@Override\r\n" +
                    "public void onAdClicked(Ad ad) {\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onPrepared" -> "@Override\r\n" +
                    "public void onPrepared(MediaPlayer _mediaPlayer) {\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onQueryTextSubmit" -> "@Override\r\n" +
                    "public boolean onQueryTextSubmit(String _charSeq) {\r\n" +
                    eventLogic + "\r\n" +
                    "return true;\r\n" +
                    "}";
            case "FBAdsBanner_onError", "FBAdsInterstitial_onError" -> "@Override\r\n" +
                    "public void onError(Ad ad, AdError adError) {\r\n" +
                    "final String _errorMsg = adError.getErrorMessage() != null ? adError.getErrorMessage() : \"\";\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "FBAdsInterstitial_onAdLoaded", "FBAdsBanner_onAdLoaded" -> "@Override\r\n" +
                    "public void onAdLoaded(Ad ad) {\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onTabAdded" -> "@Override\r\n" +
                    "public CharSequence getPageTitle(int _position) {\r\n" +
                    (!eventLogic.isEmpty() ? eventLogic + "\r\n" :
                            "return \"\";\r\n") +
                    "}";
            case "onCompleteRegister" -> "@Override\r\n" +
                    "public void onComplete(Task<InstanceIdResult> task) {\r\n" +
                    "final boolean _success = task.isSuccessful();\r\n" +
                    "final String _token = task.getResult().getToken();\r\n" +
                    "final String _errorMessage = task.getException() != null ? task.getException().getMessage() : \"\";\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onCodeSent" -> "@Override\r\n" +
                    "public void onCodeSent(String _verificationId, PhoneAuthProvider.ForceResendingToken _token) {\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onTimeChanged" -> "@Override\r\n" +
                    "public void onTimeChanged(TimePicker _timePicker, int _hour, int _minute) {\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onScrollChanged" -> "@Override\r\n" +
                    "public void onScrollStateChanged(AbsListView abs, int _scrollState) {\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onInterstitialAdLoaded" -> {
                code = setAdFullScreenContentCallback;
                yield "@Override\r\n" +
                        "public void onAdLoaded(InterstitialAd _param1) {\r\n" +
                        code +
                        "}";
            }
            case "onBannerAdFailedToLoad", "onInterstitialAdFailedToLoad",
                 "onRewardAdFailedToLoad" -> "@Override\r\n" +
                    "public void onAdFailedToLoad(LoadAdError _param1) {\r\n" +
                    "final int _errorCode = _param1.getCode();\r\n" +
                    "final String _errorMessage = _param1.getMessage();\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onAdDismissedFullScreenContent" -> {
                code = resetInterstitialAd;
                yield "@Override\r\n" +
                        "public void onAdDismissedFullScreenContent() {\r\n" +
                        code +
                        eventLogic + "\r\n" +
                        "}";
            }
            case "onAdFailedToShowFullScreenContent" -> {
                code = resetInterstitialAd;
                yield "@Override\r\n" +
                        "public void onAdFailedToShowFullScreenContent(AdError _adError) {\r\n" +
                        code +
                        "final int _errorCode = _adError.getCode();\r\n" +
                        "final String _errorMessage = _adError.getMessage();\r\n" +
                        eventLogic + "\r\n" +
                        "}";
            }
            case "onAdShowedFullScreenContent" -> "@Override\r\n" +
                    "public void onAdShowedFullScreenContent() {\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onBannerAdClicked" -> "@Override\r\n" +
                    "public void onAdClicked() {\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onBannerAdClosed" -> "@Override\r\n" +
                    "public void onAdClosed() {\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onBannerAdLoaded" -> "@Override\r\n" +
                    "public void onAdLoaded() {\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onBannerAdOpened" -> "@Override\r\n" +
                    "public void onAdOpened() {\r\n" +
                    eventLogic + "\r\n" +
                    "}";
            case "onRewardAdLoaded" -> {
                code = setAdFullScreenContentCallback;
                yield "@Override\r\n" +
                        "public void onAdLoaded(RewardedAd _param1) {\r\n" +
                        code +
                        "}";
            }
            case "onUserEarnedReward" -> eventLogic;
            default -> EventsHandler.getEventCode(targetId, eventName, eventLogic);
        };
    }

    /**
     * @return Code of extra listeners, used in {@link a.a.a.Lx#getListenerCode(String, String, String)}
     */
    public static String g(String listenerName, String targetId, String listenerLogic) {
        return switch (listenerName) {
            case "OnCompletionListener" ->
                    targetId + ".setOnCompletionListener(new MediaPlayer.OnCompletionListener() {\r\n" +
                            listenerLogic + "\r\n" +
                            "});";
            case "OnDateSetListener" ->
                    "public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {\r\n" +
                            "@Override\r\n" +
                            "public Dialog onCreateDialog(Bundle savedInstanceState) {\r\n" +
                            "final Calendar c = Calendar.getInstance();\r\n" +
                            "int year = c.get(Calendar.YEAR);\r\n" +
                            "int month = c.get(Calendar.MONTH);\r\n" +
                            "int day = c.get(Calendar.DAY_OF_MONTH);\r\n" +
                            "return new DatePickerDialog(getActivity(), this, year, month, day);\r\n" +
                            "}\r\n" +
                            listenerLogic + "\r\n" +
                            "}";
            case "OnQueryTextListener" ->
                    targetId + ".setOnQueryTextListener(new SearchView.OnQueryTextListener() {\r\n" +
                            listenerLogic + "\r\n" +
                            "});";
            case "OnVerificationStateChangedListener" ->
                    targetId + " = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {\r\n" +
                            listenerLogic + "\r\n" +
                            "};";
            case "OnScrollListener" ->
                    targetId + ".setOnScrollListener(new AbsListView.OnScrollListener() {\r\n" +
                            listenerLogic + "\r\n" +
                            "});";
            case "authsignInWithPhoneAuth" ->
                    targetId + "_phoneAuthListener = new OnCompleteListener<AuthResult>() {\r\n" +
                            listenerLogic + "\r\n" +
                            "};";
            case "FragmentStatePagerAdapter" -> {
                String className = Lx.a(targetId + "Fragment", false);
                yield "public class " + className + " extends FragmentStatePagerAdapter {\r\n" +
                        "// This class is deprecated, you should migrate to ViewPager2:\r\n" +
                        "// https://developer.android.com/reference/androidx/viewpager2/widget/ViewPager2\r\n" +
                        "Context context;\r\n" +
                        "int tabCount;\r\n" +
                        "\r\n" +
                        "public " + className + "(Context context, FragmentManager manager) {\r\n" +
                        "super(manager);\r\n" +
                        "this.context = context;\r\n" +
                        "}\r\n" +
                        "\r\n" +
                        "public void setTabCount(int tabCount) {\r\n" +
                        "this.tabCount = tabCount;\r\n" +
                        "}\r\n" +
                        "\r\n" +
                        "@Override\r\n" +
                        "public int getCount() {\r\n" +
                        "return tabCount;\r\n" +
                        "}\r\n" +
                        "\r\n" +
                        listenerLogic + "\r\n" +
                        "}";
            }
            case "OnTimeSetListener" ->
                    targetId + "_listener = new TimePickerDialog.OnTimeSetListener() {\r\n" +
                            listenerLogic + "\r\n" +
                            "};";
            case "OnFailureListener" ->
                    targetId + "_onFailureLink = new OnFailureListener() {\r\n" +
                            listenerLogic + "\r\n" +
                            "};";
            case "authUpdatePasswordComplete" ->
                    targetId + "_updatePasswordListener = new OnCompleteListener<Void>() {\r\n" +
                            listenerLogic + "\r\n" +
                            "};";
            case "OnSuccessListener" ->
                    targetId + "_onSuccessLink = new OnSuccessListener<PendingDynamicLinkData>() {\r\n" +
                            listenerLogic + "\r\n" +
                            "};";
            case "OnGridItemClickListener" ->
                    targetId + ".setOnItemClickListener(new AdapterView.OnItemClickListener() {\r\n" +
                            listenerLogic + "\r\n" +
                            "});";
            case "OnRecyclerScrollListener" ->
                    targetId + ".addOnScrollListener(new RecyclerView.OnScrollListener() {\r\n" +
                            listenerLogic + "\r\n" +
                            "});";
            case "OnTimeChangeListener" ->
                    targetId + ".setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {\r\n" +
                            listenerLogic + "\r\n" +
                            "});";
            case "authDeleteUserComplete" ->
                    targetId + "_deleteUserListener = new OnCompleteListener<Void>() {\r\n" +
                            listenerLogic + "\r\n" +
                            "};";
            case "authUpdateProfileComplete" ->
                    targetId + "_updateProfileListener = new OnCompleteListener<Void>() {\r\n" +
                            listenerLogic + "\r\n" +
                            "};";
            case "OnPageChangeListener" ->
                    targetId + ".addOnPageChangeListener(new ViewPager.OnPageChangeListener() {\r\n" +
                            listenerLogic + "\r\n" +
                            "});";
            case "OnErrorListener" ->
                    targetId + ".setOnErrorListener(new MediaPlayer.OnErrorListener() {\r\n" +
                            listenerLogic + "\r\n" +
                            "});";
            case "authEmailVerificationSent" ->
                    targetId + "_emailVerificationSentListener = new OnCompleteListener<Void>() {\r\n" +
                            listenerLogic + "\r\n" +
                            "};";
            case "authUpdateEmailComplete" ->
                    targetId + "_updateEmailListener = new OnCompleteListener<Void>() {\r\n" +
                            listenerLogic + "\r\n" +
                            "};";
            case "OnPreparedListener" ->
                    targetId + ".setOnPreparedListener(new MediaPlayer.OnPreparedListener() {\r\n" +
                            listenerLogic + "\r\n" +
                            "});";
            case "OnNavigationItemSelected" ->
                    targetId + ".setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {\r\n" +
                            listenerLogic + "\r\n" +
                            "});";
            case "OnLetterSelectedListener" ->
                    targetId + ".setOnLetterSelectedListener(new WaveSideBar.OnLetterSelectedListener() {\r\n" +
                            listenerLogic + "\r\n" +
                            "});";
            case "FBAdsInterstitial_InterstitialAdListener" ->
                    targetId + "_InterstitialAdListener = new InterstitialAdListener() {\r\n" +
                            listenerLogic + "\r\n" +
                            "};";
            case "OnRatingBarChangeListener" ->
                    targetId + ".setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {\r\n" +
                            listenerLogic + "\r\n" +
                            "});";
            case "OnTabSelectedListener" ->
                    targetId + ".addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {\r\n" +
                            listenerLogic + "\r\n" +
                            "});";
            case "OnDateChangeListener" -> "Calendar _calendar = Calendar.getInstance();\r\n" +
                    targetId + ".init(_calendar.get(Calendar.YEAR), _calendar.get(Calendar.MONTH), " +
                    "_calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {\r\n" +
                    listenerLogic + "\r\n" +
                    "});";
            case "OnCompleteListenerFCM" ->
                    targetId + "_onCompleteListener = new OnCompleteListener<InstanceIdResult>() {\r\n" +
                            listenerLogic + "\r\n" +
                            "};";
            case "PatternLockViewListener" ->
                    targetId + ".addPatternLockListener(new PatternLockViewListener() {\r\n" +
                            listenerLogic + "\r\n" +
                            "});";
            case "FBAdsBanner_AdListener" -> targetId + "_AdListener = new AdListener() {\r\n" +
                    listenerLogic + "\r\n" +
                    "};";
            case "OnGridItemLongClickListener" ->
                    targetId + ".setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {\r\n" +
                            listenerLogic + "\r\n" +
                            "});";
            case "googleSignInListener" ->
                    targetId + "_googleSignInListener = new OnCompleteListener<AuthResult>() {\r\n" +
                            listenerLogic + "\r\n" +
                            "};";
            case "interstitialAdLoadCallback" ->
                    "_" + targetId + "_interstitial_ad_load_callback = new InterstitialAdLoadCallback() {\r\n" +
                            listenerLogic + "\r\n" +
                            "};";
            case "fullScreenContentCallback" ->
                    "_" + targetId + "_full_screen_content_callback = new FullScreenContentCallback() {\r\n" +
                            listenerLogic + "\r\n" +
                            "};";
            case "bannerAdViewListener" -> targetId + ".setAdListener(new AdListener() {\r\n" +
                    listenerLogic + "\r\n" +
                    "});";
            case "rewardedAdLoadCallback" ->
                    "_" + targetId + "_rewarded_ad_load_callback = new RewardedAdLoadCallback() {\r\n" +
                            listenerLogic + "\r\n" +
                            "};";
            case "onUserEarnedRewardListener" ->
                    "_" + targetId + "_on_user_earned_reward_listener = new OnUserEarnedRewardListener() {\r\n" +
                            "@Override\r\npublic void onUserEarnedReward(RewardItem _param1) {\r\n" +
                            "int _rewardAmount = _param1.getAmount();\r\n" +
                            "String _rewardType = _param1.getType();\r\n" +
                            listenerLogic + "\r\n" +
                            "}\r\n};";
            default -> EventsHandler.getListenerCode(listenerName, targetId, listenerLogic);
        };
    }

    public static String h(String eventName) {
        return switch (eventName) {
            case "onUpdateProfileComplete", "onEmailVerificationSent", "onDeleteUserComplete",
                 "onUpdateEmailComplete", "onGoogleSignIn", "signInWithPhoneAuthComplete",
                 "onUpdatePasswordComplete" -> "%b.success %s.errorMessage";
            case "onScrolled" -> "%d.firstVisibleItem %d.visibleItemCount %d.totalItemCount";
            case "onDateChanged" -> "%d.year %d.month %d.day";
            case "onFailureLink" -> "%s.errorMessage";
            case "onNavigationItemSelected" -> "%d.itemId";
            case "onDateSet" -> "%m.datepicker.datePicker %d.year %d.month %d.day";
            case "onQueryTextChanged", "onQueryTextSubmit" -> "%s.charSeq";
            case "onError" -> "%d.what %d.extra";
            case "onVerificationCompleted" -> "%m.PhoneAuthCredential.credential";
            case "onRecyclerScrollChanged" -> "%d.scrollState";
            case "onRecyclerScrolled" -> "%d.offsetX %d.offsetY";
            case "onSuccessLink" -> "%s.link";
            case "onPageScrolled" -> "%d.position %d.positionOffset %d.positionOffsetPixels";
            case "onVerificationFailed" -> "%s.exception";
            case "onAccountPicker" -> "%m.GoogleSignInAccount.task";
            case "onTimeSet" -> "%d.hour %d.minute";
            case "onPageChanged" -> "%d.scrollState";
            case "onRatingChanged" -> "%d.value";
            case "onPatternLockProgress" -> "%m.listStr.pattern";
            case "onOptionsItemSelected" -> "%d.id %s.title";
            case "onPatternLockComplete" -> "%m.listStr.pattern";
            case "onTabSelected", "onTabReselected", "onTabAdded", "onTabUnselected",
                 "onFragmentAdded" -> "%d.position";
            case "onLetterSelected" -> "%s.index";
            case "onPageSelected" -> "%m.listMap.data %d.position";
            case "FBAdsInterstitial_onError", "FBAdsBanner_onError" -> "%s.errorMsg";
            case "onCompleteRegister" -> "%b.success %s.token %s.errorMessage";
            case "onCodeSent" -> "%s.verificationId %m.FirebasePhoneAuth.token";
            case "onTimeChanged" -> "%d.hour %d.minute";
            case "onScrollChanged" -> "%d.scrollState";
            case "onBannerAdFailedToLoad", "onRewardAdFailedToLoad", "onInterstitialAdFailedToLoad",
                 "onAdFailedToShowFullScreenContent" -> "%d.errorCode %d.errorMessage";
            default -> EventsHandler.getBlocks(eventName);
        };
    }

    public static void h(Gx gx, ArrayList<String> list) {
        if (gx.a("RewardedVideoAd")) {
            list.add("onRewardAdLoaded");
            list.add("onRewardAdFailedToLoad");
            list.add("onUserEarnedReward");
            list.add("onAdDismissedFullScreenContent");
            list.add("onAdFailedToShowFullScreenContent");
            list.add("onAdShowedFullScreenContent");
        }
        if (gx.a("FragmentAdapter")) {
            list.add("onTabAdded");
            list.add("onFragmentAdded");
        }
        if (gx.a("TimePickerDialog")) {
            list.add("onTimeSet");
        }
        if (gx.a("DatePickerDialog")) {
            list.add("onDateSet");
        }
        if (gx.a("FirebaseAuth")) {
            list.add("onUpdateEmailComplete");
            list.add("onUpdatePasswordComplete");
            list.add("onEmailVerificationSent");
            list.add("onDeleteUserComplete");
            list.add("signInWithPhoneAuthComplete");
            list.add("onUpdateProfileComplete");
            list.add("onGoogleSignIn");
        }
        if (gx.a("FirebasePhoneAuth")) {
            list.add("onVerificationCompleted");
            list.add("onVerificationFailed");
            list.add("onCodeSent");
        }
        if (gx.a("FirebaseDynamicLink")) {
            list.add("onSuccessLink");
            list.add("onFailureLink");
        }
        if (gx.a("FirebaseCloudMessage")) {
            list.add("onCompleteRegister");
        }
        if (gx.a("FBAdsBanner")) {
            list.add("FBAdsBanner_onError");
            list.add("FBAdsBanner_onAdLoaded");
            list.add("FBAdsBanner_onAdClicked");
            list.add("FBAdsBanner_onLoggingImpression");
        }
        if (gx.a("FBAdsInterstitial")) {
            list.add("FBAdsInterstitial_onError");
            list.add("FBAdsInterstitial_onAdLoaded");
            list.add("FBAdsInterstitial_onAdClicked");
            list.add("FBAdsInterstitial_onLoggingImpression");
            list.add("FBAdsInterstitial_onInterstitialDisplayed");
            list.add("FBAdsInterstitial_onInterstitialDismissed");
        }
        if (gx.a("FirebaseGoogleLogin")) {
            list.add("onAccountPicker");
            list.add("onAccountPickerCancelled");
        }
        EventsHandler.addEvents(gx, list);
    }

    public static String i(String targetId, String eventName) {
        return switch (eventName) {
            case "onUpdateProfileComplete", "onEmailVerificationSent", "onDeleteUserComplete",
                 "onUpdateEmailComplete", "onGoogleSignIn", "onUpdatePasswordComplete",
                 "signInWithPhoneAuthComplete" ->
                    "When " + targetId + " " + eventName + " %b.success %s.errorMessage";
            case "onScrolled" ->
                    "When " + targetId + " " + eventName + " %d.firstVisibleItem %d.visibleItemCount %d.totalItemCount";
            case "onDateChanged", "onDateSet" ->
                    "When " + targetId + " " + eventName + " %d.year %d.month %d.day";
            case "onFailureLink" -> "When " + targetId + " " + eventName + " %s.errorMessage";
            case "onNavigationItemSelected" -> "When " + targetId + " " + eventName + " %d.itemId";
            case "onPrepared", "onCompletion", "onPatternLockCleared", "onAccountPickerCancelled",
                 "onPatternLockStarted" -> "When " + targetId + " " + eventName;
            case "onSaveInstanceState", "onCreateOptionsMenu", "onCreateContextMenu",
                 "onRestoreInstanceState", "onContextItemSelected" -> "When " + eventName;
            case "onQueryTextChanged", "onQueryTextSubmit" ->
                    "When " + targetId + " " + eventName + " %s.charSeq";
            case "onError" -> "When " + targetId + " " + eventName + " %d.what %d.extra";
            case "onVerificationCompleted" ->
                    "When " + targetId + " " + eventName + " %m.PhoneAuthCredential.credential";
            case "onRecyclerScrollChanged" ->
                    "When " + " " + targetId + " " + eventName + " %d.scrollState";
            case "onRecyclerScrolled" ->
                    "When " + " " + targetId + " " + eventName + " %d.offsetX %d.offsetY";
            case "onSuccessLink" -> "When " + targetId + " " + eventName + " %s.link";
            case "onPageScrolled" ->
                    "When " + targetId + " " + eventName + " %d.position %d.positionOffset %d.positionOffsetPixels";
            case "onVerificationFailed" -> "When " + targetId + " " + eventName + " %s.exception";
            case "onAccountPicker" ->
                    "When " + targetId + " " + eventName + " %m.GoogleSignInAccount.task";
            case "onFragmentAdded" -> "Fragment getItem %d.position";
            case "onTimeSet" -> "When " + targetId + " " + eventName + " %d.hour %d.minute";
            case "onPageChanged" -> "When " + targetId + " onPageScrollStateChanged %d.scrollState";
            case "FBAdsBanner_onAdClicked", "FBAdsBanner_onAdLoaded",
                 "FBAdsBanner_onLoggingImpression", "FBAdsInterstitial_onAdClicked",
                 "FBAdsInterstitial_onAdLoaded", "FBAdsInterstitial_onLoggingImpression",
                 "FBAdsInterstitial_onInterstitialDismissed",
                 "FBAdsInterstitial_onInterstitialDisplayed" ->
                    targetId + ": " + eventName.split("_")[1];
            case "onRatingChanged" -> "When " + targetId + " " + eventName + " %d.value";
            case "onPatternLockProgress", "onPatternLockComplete" ->
                    "When " + targetId + " " + eventName + " %m.listStr.pattern";
            case "onOptionsItemSelected" -> "When " + targetId + " %d.id %s.title";
            case "onTabSelected", "onTabUnselected", "onTabReselected", "onPageSelected" ->
                    targetId + ": " + eventName + " %d.position";
            case "onLetterSelected" -> "When " + targetId + " " + eventName + " %s.index";
            case "FBAdsInterstitial_onError", "FBAdsBanner_onError" ->
                    targetId + ": onError %s.errorMsg";
            case "onTabAdded" -> "CharSequence getPageTitle %d.position";
            case "onCompleteRegister" ->
                    "When " + targetId + " " + eventName + " %b.success %s.token %s.errorMessage";
            case "onCodeSent" ->
                    "When " + targetId + " " + eventName + " %s.verificationId %m.FirebasePhoneAuth.token";
            case "onTimeChanged" -> "When " + targetId + " " + eventName + " %d.hour %d.minute";
            case "onScrollChanged" -> "When " + targetId + ": " + eventName + " %d.scrollState";
            case "onBannerAdFailedToLoad", "onInterstitialAdFailedToLoad", "onRewardAdFailedToLoad",
                 "onAdFailedToShowFullScreenContent" ->
                    targetId + ": " + eventName + " %d.errorCode %s.errorMessage";
            case "onUserEarnedReward" ->
                    targetId + ": " + eventName + " %d.rewardAmount %s.rewardType";
            case "onInterstitialAdLoaded", "onBannerAdLoaded" -> targetId + ": onAdLoaded";
            case "onAdDismissedFullScreenContent", "onAdShowedFullScreenContent",
                 "onRewardAdLoaded" -> targetId + ": " + eventName;
            case "onBannerAdOpened" -> targetId + ": onAdOpened";
            case "onBannerAdClicked" -> targetId + ": onAdClicked";
            case "onBannerAdClosed" -> targetId + ": onAdClosed";
            default -> EventsHandler.getSpec(targetId, eventName);
        };
    }
}
