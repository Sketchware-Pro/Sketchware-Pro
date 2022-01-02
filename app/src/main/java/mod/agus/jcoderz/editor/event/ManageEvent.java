package mod.agus.jcoderz.editor.event;

import java.util.ArrayList;

import a.a.a.Gx;
import a.a.a.Lx;
import mod.hilal.saif.events.EventsHandler;

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
            listeners.add("OnVideoAdListener");
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

            case "OnVideoAdListener":
                list.add("onRewarded");
                list.add("onRewardedVideoAdLoaded");
                list.add("onRewardedVideoAdFailedToLoad");
                list.add("onRewardedVideoAdOpened");
                list.add("onRewardedVideoAdClosed");
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
        switch (str) {
            case "onUpdateProfileComplete":
                return 2131166348;

            case "FBAdsBanner_onLoggingImpression":
                return 2131166342;

            case "onEmailVerificationSent":
                return 2131166335;

            case "onDateChanged":
                return 2131165572;

            case "onRewarded":
                return 2131166301;

            case "onFailureLink":
                return 2131166336;

            case "onDeleteUserComplete":
                return 2131166333;

            case "onRewardedVideoAdFailedToLoad":
                return 2131165552;

            case "onDateSet":
                return 2131165572;

            case "onCompletion":
                return 2131165579;

            case "onQueryTextChanged":
                return 2131165849;

            case "onUpdateEmailComplete":
                return 2131166334;

            case "onError":
                return 2131165548;

            case "onVerificationCompleted":
                return 2131166350;

            case "onSaveInstanceState":
            case "onCreateOptionsMenu":
            case "onCreateContextMenu":
            case "onContextItemSelected":
            case "onOptionsItemSelected":
                return 2131165333;

            case "onRestoreInstanceState":
                return 2131165548;

            case "onRewardedVideoAdClosed":
                return 2131165551;

            case "onSuccessLink":
                return 2131166337;

            case "onPatternLockCleared":
            case "onPatternLockProgress":
                return 2131166309;

            case "FBAdsInterstitial_onAdClicked":
                return 2131166338;

            case "onRewardedVideoAdLoaded":
                return 2131165553;

            case "onRewardedVideoAdOpened":
                return 2131165554;

            case "onAccountPickerCancelled":
                return 2131166345;

            case "onVerificationFailed":
                return 2131166349;

            case "onGoogleSignIn":
                return 2131166346;

            case "onAccountPicker":
                return 2131166344;

            case "onFragmentAdded":
                return 2131166304;

            case "onTimeSet":
                return 2131166276;

            case "signInWithPhoneAuthComplete":
                return 2131166331;

            case "FBAdsInterstitial_onInterstitialDismissed":
                return 2131166339;

            case "onRatingChanged":
                return 2131166177;

            case "FBAdsInterstitial_onInterstitialDisplayed":
                return 2131166340;

            case "FBAdsInterstitial_onLoggingImpression":
                return 2131166342;

            case "onPatternLockStarted":
            case "onPatternLockComplete":
                return 2131166309;

            case "onLetterSelected":
                return 2131166313;

            case "onScrollChanged":
            case "onPageChanged":
            case "onPageScrolled":
            case "onRecyclerScrolled":
            case "onRecyclerScrollChanged":
            case "onNavigationItemSelected":
            case "onScrolled":
            case "onTabUnselected":
            case "onPageSelected":
            case "onTabSelected":
            case "onTabReselected":
                return 2131165581;

            case "FBAdsBanner_onAdClicked":
                return 2131166338;

            case "onPrepared":
                return 2131165588;

            case "onQueryTextSubmit":
                return 2131165849;

            case "FBAdsInterstitial_onError":
                return 2131166341;

            case "FBAdsInterstitial_onAdLoaded":
            case "FBAdsBanner_onAdLoaded":
                return 2131166343;

            case "FBAdsBanner_onError":
                return 2131166341;

            case "onTabAdded":
                return 2131166303;

            case "onCompleteRegister":
                return 2131166332;

            case "onCodeSent":
                return 2131166331;

            case "onTimeChanged":
                return 2131166276;

            case "onUpdatePasswordComplete":
                return 2131166347;

            default:
                return EventsHandler.getIcon(str);
        }
    }

    /**
     * @return Descriptions for Events added by Agus
     */
    public static String e(String eventName) {
        switch (eventName) {
            case "FBAdsBanner_onLoggingImpression":
            case "FBAdsInterstitial_onLoggingImpression":
                return "onLoggingImpression";

            case "onScrolled":
            case "onRecyclerScrolled":
                return "onScroll";

            case "onFailureLink":
                return "onFailure";

            case "onRewardedVideoAdFailedToLoad":
                return "onVideoAdFailedToLoad";

            case "onSaveInstanceState":
                return "On activity save instance state";

            case "onCreateOptionsMenu":
                return "On create options menu";

            case "onVerificationCompleted":
                return "onVerificationCompleted";

            case "onRecyclerScrollChanged":
            case "onScrollChanged":
                // Nice typo, Agus
                return "onScrollStateCanged";

            case "onCreateContextMenu":
                return "On create context menu";

            case "onRestoreInstanceState":
                return "On activity restore instance state";

            case "onContextItemSelected":
                return "On context menu selected";

            case "onRewardedVideoAdClosed":
                return "onVideoAdClosed";

            case "onSuccessLink":
                return "onSuccess";

            case "onRewardedVideoAdLoaded":
                return "onVideoAdLoaded";

            case "onRewardedVideoAdOpened":
                return "onVideoAdOpened";

            case "onAccountPickerCancelled":
                return "onAccountCancelled";

            case "onFragmentAdded":
                return "Return Fragment";

            case "onPageChanged":
                return "onPageScrollStateChanged";

            case "FBAdsInterstitial_onInterstitialDismissed":
                return "onInterstitialDismissed";

            case "FBAdsInterstitial_onInterstitialDisplayed":
                return "onInterstitialDisplayed";

            case "onOptionsItemSelected":
                return "On options menu selected";

            case "onBannerAdClicked":
            case "FBAdsBanner_onAdClicked":
            case "FBAdsInterstitial_onAdClicked":
                return "onAdClicked";

            case "FBAdsBanner_onError":
            case "FBAdsInterstitial_onError":
                return "onError";

            case "onBannerAdLoaded":
            case "FBAdsBanner_onAdLoaded":
            case "FBAdsInterstitial_onAdLoaded":
                return "onAdLoaded";

            case "onTabAdded":
                return "Return Title";

            case "onCompleteRegister":
                return "onComplete";

            case "onBannerAdFailedToLoad":
            case "onInterstitialAdFailedToLoad":
                return "onAdFailedToLoad";

            case "onBannerAdClosed":
                return "onAdClosed";

            case "onUpdateProfileComplete":
            case "onEmailVerificationSent":
            case "onDateChanged":
            case "onRewarded":
            case "onDeleteUserComplete":
            case "onNavigationItemSelected":
            case "onDateSet":
            case "onCompletion":
            case "onPatternLockCleared":
            case "onQueryTextChanged":
            case "onUpdateEmailComplete":
            case "onError":
            case "onPageScrolled":
            case "onVerificationFailed":
            case "onGoogleSignIn":
            case "onAccountPicker":
            case "onTimeSet":
            case "signInWithPhoneAuthComplete":
            case "onRatingChanged":
            case "onPatternLockProgress":
            case "onTabSelected":
            case "onTabReselected":
            case "onLetterSelected":
            case "onTabUnselected":
            case "onPageSelected":
            case "onPrepared":
            case "onQueryTextSubmit":
            case "onCodeSent":
            case "onTimeChanged":
            case "onUpdatePasswordComplete":
            case "onAdDismissedFullScreenContent":
            case "onAdFailedToShowFullScreenContent":
            case "onAdShowedFullScreenContent":
                return eventName;

            default:
                return EventsHandler.getDesc(eventName);
        }
    }

    public static String f(String targetId, String eventName, String eventLogic) {
        switch (eventName) {
            case "onUpdateProfileComplete":
            case "onEmailVerificationSent":
            case "onDeleteUserComplete":
            case "onUpdateEmailComplete":
            case "onUpdatePasswordComplete":
                return "@Override\r\n" +
                        "public void onComplete(Task<Void> _param1) {\r\n" +
                        "final boolean _success = _param1.isSuccessful();\r\n" +
                        "final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : \"\";\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "FBAdsBanner_onLoggingImpression":
            case "FBAdsInterstitial_onLoggingImpression":
                return "@Override\r\n" +
                        "public void onLoggingImpression(Ad ad) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onScrolled":
                return "@Override\r\n" +
                        "public void onScroll(AbsListView abs, int _firstVisibleItem, int _visibleItemCount, int _totalItemCount) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onDateChanged":
                return "@Override\r\n" +
                        "public void onDateChanged(DatePicker _datePicker, int _year, int _month, int _day) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onRewarded":
                return "@Override\r\n" +
                        "public void onRewarded(RewardItem rewardItem) {\r\n" +
                        "final int _rewardItem = rewardItem.getAmount();\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onFailureLink":
                return "@Override\r\n" +
                        "public void onFailure(Exception _e) {\r\n" +
                        "final String _errorMessage = _e.getMessage();\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onNavigationItemSelected":
                return "@Override\r\n" +
                        "public boolean onNavigationItemSelected(MenuItem item) {\r\n" +
                        "final int _itemId = item.getItemId();\r\n" +
                        eventLogic + "\r\n" +
                        "return true;\r\n" +
                        "}";

            case "onRewardedVideoAdFailedToLoad":
                return "@Override\r\n" +
                        "public void onRewardedVideoAdFailedToLoad(int errorCode) {\r\n" +
                        "final int _errorCode = errorCode;\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onDateSet":
                return "@Override\r\n" +
                        "public void onDateSet(DatePicker _datePicker, int _year, int _month, int _day) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onCompletion":
                return "@Override\r\n" +
                        "public void onCompletion(MediaPlayer _mediaPlayer) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onSaveInstanceState":
                return "@Override\r\n" +
                        "protected void onSaveInstanceState(Bundle outState) {\r\n" +
                        eventLogic + "\r\n" +
                        "super.onSaveInstanceState(outState);\r\n" +
                        "}";

            case "onCreateOptionsMenu":
                return "@Override\r\n" +
                        "public boolean onCreateOptionsMenu(Menu menu) {\r\n" +
                        eventLogic + "\r\n" +
                        "return super.onCreateOptionsMenu(menu);\r\n" +
                        "}";

            case "onQueryTextChanged":
                return "@Override\r\n" +
                        "public boolean onQueryTextChange(String _charSeq) {\r\n" +
                        eventLogic + "\r\n" +
                        "return true;\r\n" +
                        "}";

            case "onError":
                return "@Override\r\n" +
                        "public boolean onError(MediaPlayer _mediaPlayer, int _what, int _extra) {\r\n" +
                        eventLogic + "\r\n" +
                        "return true;\r\n" +
                        "}";

            case "onVerificationCompleted":
                return "@Override\r\n" +
                        "public void onVerificationCompleted(PhoneAuthCredential _credential) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onRecyclerScrollChanged":
                return "@Override\r\n" +
                        "public void onScrollStateChanged(RecyclerView recyclerView, int _scrollState) {\r\n" +
                        "super.onScrollStateChanged(recyclerView, _scrollState);\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onCreateContextMenu":
                return "@Override\r\n" +
                        "public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {\r\n" +
                        eventLogic + "\r\n" +
                        "super.onCreateContextMenu(menu, view, menuInfo);\r\n" +
                        "}";

            case "onRestoreInstanceState":
                return "@Override\r\n" +
                        "protected void onRestoreInstanceState(Bundle savedInstanceState) {\r\n" +
                        eventLogic + "\r\n" +
                        "super.onRestoreInstanceState(savedInstanceState);\r\n" +
                        "}";

            case "onContextItemSelected":
                return "@Override\r\n" +
                        "public boolean onContextItemSelected(MenuItem item) {\r\n" +
                        eventLogic + "\r\n" +
                        "return super.onContextItemSelected(item);\r\n" +
                        "}";

            case "onRecyclerScrolled":
                return "@Override\r\n" +
                        "public void onScrolled(RecyclerView recyclerView, int _offsetX, int _offsetY) {\r\n" +
                        "super.onScrolled(recyclerView, _offsetX, _offsetY);\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onSuccessLink":
                return "@Override\r\n" +
                        "public void onSuccess(PendingDynamicLinkData _pendingDynamicLinkData) {\r\n" +
                        "final String _link = _pendingDynamicLinkData != null ? _pendingDynamicLinkData.getLink().toString() : \"\";\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onPatternLockCleared":
                return "@Override\r\n" +
                        "public void onCleared() {\r\n" +
                        eventLogic + "\n" +
                        "}";

            case "FBAdsInterstitial_onAdClicked":
                return "@Override\r\n" +
                        "public void onAdClicked(Ad ad) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onRewardedVideoAdClosed":
            case "onRewardedVideoAdLoaded":
            case "onRewardedVideoAdOpened":
                return "@Override\r\n" +
                        "public void " + eventName + "() {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onPageScrolled":
                return "@Override\r\n" +
                        "public void onPageScrolled(int _position, float _positionOffset, int _positionOffsetPixels) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onVerificationFailed":
                return "@Override\r\n" +
                        "public void onVerificationFailed(FirebaseException e) {\r\n" +
                        "final String _exception = e.getMessage();\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onGoogleSignIn":
                return "@Override\r\n" +
                        "public void onComplete(Task<AuthResult> task) {\r\n" +
                        "final boolean _success = task.isSuccessful();\r\n" +
                        "final String _errorMessage = task.getException() != null ? task.getException().getMessage() : \"\";\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onFragmentAdded":
                return "@Override\r\n" +
                        "public Fragment getItem(int _position) {\r\n" +
                        eventLogic + "\r\n" +
                        "return null;\r\n" +
                        "}";

            case "onTimeSet":
                return "@Override\r\n" +
                        "public void onTimeSet(TimePicker _timePicker, int _hour, int _minute) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onPageChanged":
                return "@Override\r\n" +
                        "public void onPageScrollStateChanged(int _scrollState) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "signInWithPhoneAuthComplete":
                return "@Override\r\n" +
                        "public void onComplete(Task<AuthResult> task) {\r\n" +
                        "final boolean _success = task.isSuccessful();\r\n" +
                        "final String _errorMessage = task.getException() != null ? task.getException().getMessage() : \"\";\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "FBAdsInterstitial_onInterstitialDismissed":
                return "@Override\r\n" +
                        "public void onInterstitialDismissed(Ad ad) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onRatingChanged":
                return "@Override\r\n" +
                        "public void onRatingChanged(RatingBar _ratingBar, float _value, boolean _fromUser) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "FBAdsInterstitial_onInterstitialDisplayed":
                return "@Override\r\n" +
                        "public void onInterstitialDisplayed(Ad ad) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onPatternLockProgress":
                return "@Override\r\n" +
                        "public void onProgress(List<PatternLockView.Dot> _pattern) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onOptionsItemSelected":
                return "@Override\r\n" +
                        "public boolean onOptionsItemSelected(MenuItem item) {\r\n" +
                        "final int _id = item.getItemId();\r\n" +
                        "final String _title = (String) item.getTitle();\r\n" +
                        eventLogic + "\r\n" +
                        "return super.onOptionsItemSelected(item);\r\n" +
                        "}";

            case "onPatternLockStarted":
                return "@Override\r\n" +
                        "public void onStarted() {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onPatternLockComplete":
                return "@Override\r\n" +
                        "public void onComplete(List<PatternLockView.Dot> _pattern) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onTabSelected":
                return "@Override\r\n" +
                        "public void onTabSelected(TabLayout.Tab tab) {\r\n" +
                        "final int _position = tab.getPosition();\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onTabReselected":
                return "@Override\r\n" +
                        "public void onTabReselected(TabLayout.Tab tab) {\r\n" +
                        "final int _position = tab.getPosition();\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onLetterSelected":
                return "@Override\r\n" +
                        "public void onLetterSelected(String _index) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onTabUnselected":
                return "@Override\r\n" +
                        "public void onTabUnselected(TabLayout.Tab tab) {\r\n" +
                        "final int _position = tab.getPosition();\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onPageSelected":
                return "@Override\r\n" +
                        "public void onPageSelected(int _position) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "FBAdsBanner_onAdClicked":
                return "@Override\r\n" +
                        "public void onAdClicked(Ad ad) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onPrepared":
                return "@Override\r\n" +
                        "public void onPrepared(MediaPlayer _mediaPlayer) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onQueryTextSubmit":
                return "@Override\r\n" +
                        "public boolean onQueryTextSubmit(String _charSeq) {\r\n" +
                        eventLogic + "\r\n" +
                        "return true;\r\n" +
                        "}";

            case "FBAdsBanner_onError":
            case "FBAdsInterstitial_onError":
                return "@Override\r\n" +
                        "public void onError(Ad ad, AdError adError) {\r\n" +
                        "final String _errorMsg = adError.getErrorMessage() != null ? adError.getErrorMessage() : \"\";\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "FBAdsInterstitial_onAdLoaded":
            case "FBAdsBanner_onAdLoaded":
                return "@Override\r\n" +
                        "public void onAdLoaded(Ad ad) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onTabAdded":
                return "@Override\r\n" +
                        "public CharSequence getPageTitle(int _position) {\r\n" +
                        eventLogic + "\r\n" +
                        "return null;\r\n" +
                        "}";

            case "onCompleteRegister":
                return "@Override\r\n" +
                        "public void onComplete(Task<InstanceIdResult> task) {\r\n" +
                        "final boolean _success = task.isSuccessful();\r\n" +
                        "final String _token = task.getResult().getToken();\r\n" +
                        "final String _errorMessage = task.getException() != null ? task.getException().getMessage() : \"\";\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onCodeSent":
                return "@Override\r\n" +
                        "public void onCodeSent(String _verificationId, PhoneAuthProvider.ForceResendingToken _token) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onTimeChanged":
                return "@Override\r\n" +
                        "public void onTimeChanged(TimePicker _timePicker, int _hour, int _minute) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onScrollChanged":
                return "@Override\r\n" +
                        "public void onScrollStateChanged(AbsListView abs, int _scrollState) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onInterstitialAdLoaded":
                String eventCode;

                if (targetId.equals("")) {
                    eventCode = "\r\n";
                } else {
                    eventCode = targetId + " = _param1;\r\n" +
                            eventLogic + "\r\n";
                }

                return "@Override\r\n" +
                        "public void onAdLoaded(InterstitialAd _param1) {\r\n" +
                        eventCode +
                        "}";

            case "onBannerAdFailedToLoad":
            case "onInterstitialAdFailedToLoad":
                return "@Override\r\n" +
                        "public void onAdFailedToLoad(LoadAdError _param1) {\r\n" +
                        "final int _errorCode = _param1.getCode();\r\n" +
                        "final String _errorMessage = _param1.getMessage();\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onAdDismissedFullScreenContent":
                return "@Override\r\n" +
                        "public void onAdDismissedFullScreenContent() {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onAdFailedToShowFullScreenContent":
                return "@Override\r\n" +
                        "public void onAdFailedToShowFullScreenContent(AdError _adError) {\r\n" +
                        "final int _errorCode = _adError.getCode();\r\n" +
                        "final String _errorMessage = _adError.getMessage();\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onAdShowedFullScreenContent":
                return "@Override\r\n" +
                        "public void onAdShowedFullScreenContent() {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onBannerAdClicked":
                return "@Override\r\n" +
                        "public void onAdClicked() {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onBannerAdClosed":
                return "@Override\r\n" +
                        "public void onAdClosed() {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onBannerAdLoaded":
                return "@Override\r\n" +
                        "public void onAdLoaded() {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onBannerAdOpened":
                return "@Override\r\n" +
                        "public void onAdOpened() {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            default:
                return EventsHandler.getEventCode(targetId, eventName, eventLogic);
        }
    }

    /**
     * @return Code of extra listeners, used in {@link a.a.a.Lx#d(String, String, String)}
     */
    public static String g(String listenerName, String targetId, String listenerLogic) {
        switch (listenerName) {
            case "OnCompletionListener":
                return targetId + ".setOnCompletionListener(new MediaPlayer.OnCompletionListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "OnDateSetListener":
                return "public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {\r\n" +
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

            case "OnQueryTextListener":
                return targetId + ".setOnQueryTextListener(new SearchView.OnQueryTextListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "OnVerificationStateChangedListener":
                return targetId + " = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "OnScrollListener":
                return targetId + ".setOnScrollListener(new AbsListView.OnScrollListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "authsignInWithPhoneAuth":
                return targetId + "_phoneAuthListener = new OnCompleteListener<AuthResult>() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "FragmentStatePagerAdapter":
                String className = Lx.a(targetId + "Fragment");
                return "public class " + className +" extends FragmentStatePagerAdapter {\r\n" +
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

            case "OnVideoAdListener":
                return targetId + "_listener = new RewardedVideoAdListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "\r\n" +
                        "@Override\r\n" +
                        "public void onRewardedVideoAdLeftApplication() {\r\n" +
                        "}\r\n" +
                        "\r\n" +
                        "@Override\r\n" +
                        "public void onRewardedVideoStarted() {\r\n" +
                        "}\r\n" +
                        "\r\n" +
                        "@Override\r\n" +
                        "public void onRewardedVideoCompleted() {\r\n" +
                        "}\r\n" +
                        "};";

            case "OnTimeSetListener":
                return targetId + "_listener = new TimePickerDialog.OnTimeSetListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "OnFailureListener":
                return targetId + "_onFailureLink = new OnFailureListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "authUpdatePasswordComplete":
                return targetId + "_updatePasswordListener = new OnCompleteListener<Void>() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "OnSuccessListener":
                return targetId + "_onSuccessLink = new OnSuccessListener<PendingDynamicLinkData>() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "OnGridItemClickListener":
                return targetId + ".setOnItemClickListener(new AdapterView.OnItemClickListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "OnRecyclerScrollListener":
                return targetId + ".addOnScrollListener(new RecyclerView.OnScrollListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "OnTimeChangeListener":
                return targetId + ".setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "authDeleteUserComplete":
                return targetId + "_deleteUserListener = new OnCompleteListener<Void>() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "authUpdateProfileComplete":
                return targetId + "_updateProfileListener = new OnCompleteListener<Void>() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "OnPageChangeListener":
                return targetId + ".addOnPageChangeListener(new ViewPager.OnPageChangeListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "OnErrorListener":
                return targetId + ".setOnErrorListener(new MediaPlayer.OnErrorListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "authEmailVerificationSent":
                return targetId + "_emailVerificationSentListener = new OnCompleteListener<Void>() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "authUpdateEmailComplete":
                return targetId + "_updateEmailListener = new OnCompleteListener<Void>() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "OnPreparedListener":
                return targetId + ".setOnPreparedListener(new MediaPlayer.OnPreparedListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "OnNavigationItemSelected":
                return targetId + ".setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "OnLetterSelectedListener":
                return targetId + ".setOnLetterSelectedListener(new WaveSideBar.OnLetterSelectedListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "FBAdsInterstitial_InterstitialAdListener":
                return targetId + "_InterstitialAdListener = new InterstitialAdListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "OnRatingBarChangeListener":
                return targetId + ".setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "OnTabSelectedListener":
                return targetId + ".addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "OnDateChangeListener":
                return "Calendar _calendar = Calendar.getInstance();\r\n" +
                        targetId + ".init(_calendar.get(Calendar.YEAR), _calendar.get(Calendar.MONTH), " +
                        "_calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "OnCompleteListenerFCM":
                return targetId + "_onCompleteListener = new OnCompleteListener<InstanceIdResult>() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "PatternLockViewListener":
                return targetId + ".addPatternLockListener(new PatternLockViewListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "FBAdsBanner_AdListener":
                return targetId + "_AdListener = new AdListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "OnGridItemLongClickListener":
                return targetId + ".setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "googleSignInListener":
                return targetId + "_googleSignInListener = new OnCompleteListener<AuthResult>() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "interstitialAdLoadCallback":
                return "_" + targetId + "_interstitial_ad_load_callback = new InterstitialAdLoadCallback() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "fullScreenContentCallback":
                return "_" + targetId + "_full_screen_content_callback = new FullScreenContentCallback() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "bannerAdViewListener":
                return targetId + ".setAdListener(new AdListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            default:
                return EventsHandler.getListenerCode(listenerName, targetId, listenerLogic);
        }
    }

    public static String h(String eventName) {
        switch (eventName) {
            case "onUpdateProfileComplete":
            case "onEmailVerificationSent":
            case "onDeleteUserComplete":
            case "onUpdateEmailComplete":
            case "onGoogleSignIn":
            case "signInWithPhoneAuthComplete":
            case "onUpdatePasswordComplete":
                return "%b.success %s.errorMessage";

            case "onScrolled":
                return "%d.firstVisibleItem %d.visibleItemCount %d.totalItemCount";

            case "onDateChanged":
                return "%d.year %d.month %d.day";

            case "onRewarded":
                return "%d.rewardItem";

            case "onFailureLink":
                return "%s.errorMessage";

            case "onNavigationItemSelected":
                return "%d.itemId";

            case "onRewardedVideoAdFailedToLoad":
                return "%d.errorCode";

            case "onDateSet":
                return "%m.datepicker.datePicker %d.year %d.month %d.day";

            case "onQueryTextChanged":
            case "onQueryTextSubmit":
                return "%s.charSeq";

            case "onError":
                return "%d.what %d.extra";

            case "onVerificationCompleted":
                return "%m.PhoneAuthCredential.credential";

            case "onRecyclerScrollChanged":
                return "%d.scrollState";

            case "onRecyclerScrolled":
                return "%d.offsetX %d.offsetY";

            case "onSuccessLink":
                return "%s.link";

            case "onPageScrolled":
                return "%d.position %d.positionOffset %d.positionOffsetPixels";

            case "onVerificationFailed":
                return "%s.exception";

            case "onAccountPicker":
                return "%m.GoogleSignInAccount.task";

            case "onTimeSet":
                return "%d.hour %d.minute";

            case "onPageChanged":
                return "%d.scrollState";

            case "onRatingChanged":
                return "%d.value";

            case "onPatternLockProgress":
                return "%m.listStr.pattern";

            case "onOptionsItemSelected":
                return "%d.id %s.title";

            case "onPatternLockComplete":
                return "%m.listStr.pattern";

            case "onTabSelected":
            case "onTabReselected":
            case "onTabAdded":
            case "onTabUnselected":
            case "onFragmentAdded":
                return "%d.position";

            case "onLetterSelected":
                return "%s.index";

            case "onPageSelected":
                return "%m.listMap.data %d.position";

            case "FBAdsInterstitial_onError":
            case "FBAdsBanner_onError":
                return "%s.errorMsg";

            case "onCompleteRegister":
                return "%b.success %s.token %s.errorMessage";

            case "onCodeSent":
                return "%s.verificationId %m.FirebasePhoneAuth.token";

            case "onTimeChanged":
                return "%d.hour %d.minute";

            case "onScrollChanged":
                return "%d.scrollState";

            case "onBannerAdFailedToLoad":
            case "onInterstitialAdFailedToLoad":
            case "onAdFailedToShowFullScreenContent":
                return "%d.errorCode %d.errorMessage";

            default:
                return EventsHandler.getBlocks(eventName);
        }
    }

    public static void h(Gx gx, ArrayList<String> list) {
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
        if (gx.a("RewardedVideoAd")) {
            list.add("onRewarded");
            list.add("onRewardedVideoAdLoaded");
            list.add("onRewardedVideoAdFailedToLoad");
            list.add("onRewardedVideoAdOpened");
            list.add("onRewardedVideoAdClosed");
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
        switch (eventName) {
            case "onUpdateProfileComplete":
            case "onEmailVerificationSent":
            case "onDeleteUserComplete":
            case "onUpdateEmailComplete":
            case "onGoogleSignIn":
            case "onUpdatePasswordComplete":
            case "signInWithPhoneAuthComplete":
                return "When " + targetId + " " + eventName + " %b.success %s.errorMessage";

            case "onScrolled":
                return "When " + targetId + " " + eventName + " %d.firstVisibleItem %d.visibleItemCount %d.totalItemCount";

            case "onDateChanged":
            case "onDateSet":
                return "When " + targetId + " " + eventName + " %d.year %d.month %d.day";

            case "onRewarded":
                return "onRewarded " + "%d.rewardItem";

            case "onFailureLink":
                return "When " + targetId + " " + eventName + " %s.errorMessage";

            case "onNavigationItemSelected":
                return "When " + targetId + " " + eventName + " %d.itemId";

            case "onRewardedVideoAdFailedToLoad":
                return "onRewardedVideoAdFailedToLoad " + "%d.errorCode";

            case "onPrepared":
            case "onCompletion":
            case "onPatternLockCleared":
            case "onAccountPickerCancelled":
            case "onPatternLockStarted":
                return "When " + targetId + " " + eventName;

            case "onSaveInstanceState":
            case "onCreateOptionsMenu":
            case "onCreateContextMenu":
            case "onRestoreInstanceState":
            case "onContextItemSelected":
                return "When " + eventName;

            case "onQueryTextChanged":
            case "onQueryTextSubmit":
                return "When " + targetId + " " + eventName + " %s.charSeq";

            case "onError":
                return "When " + targetId + " " + eventName + " %d.what %d.extra";

            case "onVerificationCompleted":
                return "When " + targetId + " " + eventName + " %m.PhoneAuthCredential.credential";

            case "onRecyclerScrollChanged":
                return "When " + " " + targetId + " " + eventName + " %d.scrollState";

            case "onRecyclerScrolled":
                return "When " + " " + targetId + " " + eventName + " %d.offsetX %d.offsetY";

            case "onRewardedVideoAdClosed":
                return "onRewardedVideoAdClosed";

            case "onSuccessLink":
                return "When " + targetId + " " + eventName + " %s.link";

            case "onRewardedVideoAdLoaded":
                return "onRewardedVideoAdLoaded";

            case "onRewardedVideoAdOpened":
                return "onRewardedVideoAdOpened";

            case "onPageScrolled":
                return "When " + targetId + " " + eventName + " %d.position %d.positionOffset %d.positionOffsetPixels";

            case "onVerificationFailed":
                return "When " + targetId + " " + eventName + " %s.exception";

            case "onAccountPicker":
                return "When " + targetId + " " + eventName + " %m.GoogleSignInAccount.task";

            case "onFragmentAdded":
                return "Fragment getItem %d.position";

            case "onTimeSet":
                return "When " + targetId + " " + eventName + " %d.hour %d.minute";

            case "onPageChanged":
                return "When " + targetId + " onPageScrollStateChanged %d.scrollState";

            case "FBAdsBanner_onAdClicked":
            case "FBAdsBanner_onAdLoaded":
            case "FBAdsBanner_onLoggingImpression":
            case "FBAdsInterstitial_onAdClicked":
            case "FBAdsInterstitial_onAdLoaded":
            case "FBAdsInterstitial_onLoggingImpression":
            case "FBAdsInterstitial_onInterstitialDismissed":
            case "FBAdsInterstitial_onInterstitialDisplayed":
                return targetId + ": " + eventName.split("_")[1];

            case "onRatingChanged":
                return "When " + targetId + " " + eventName + " %d.value";

            case "onPatternLockProgress":
                return "When " + targetId + " " + eventName + " %m.listStr.pattern";

            case "onOptionsItemSelected":
                return "When " + targetId + " %d.id %s.title";

            case "onPatternLockComplete":
                return "When " + targetId + " " + eventName + " %m.listStr.pattern";

            case "onTabSelected":
            case "onTabUnselected":
            case "onTabReselected":
            case "onPageSelected":
                return targetId + ": " + eventName + " %d.position";

            case "onLetterSelected":
                return "When " + targetId + " " + eventName + " %s.index";

            case "FBAdsInterstitial_onError":
            case "FBAdsBanner_onError":
                return targetId + ": onError %s.errorMsg";

            case "onTabAdded":
                return "CharSequence getPageTitle %d.position";

            case "onCompleteRegister":
                return "When " + targetId + " " + eventName + " %b.success %s.token %s.errorMessage";

            case "onCodeSent":
                return "When " + targetId + " " + eventName + " %s.verificationId %m.FirebasePhoneAuth.token";

            case "onTimeChanged":
                return "When " + targetId + " " + eventName + " %d.hour %d.minute";

            case "onScrollChanged":
                return "When " + targetId + " " + eventName + " %d.scrollState";

            case "onBannerAdFailedToLoad":
            case "onInterstitialAdFailedToLoad":
                return targetId + ": onAdFailedToLoad %d.errorCode %s.errorMessage";

            case "onInterstitialAdLoaded":
            case "onBannerAdLoaded":
                return targetId + ": onAdLoaded";

            case "onAdFailedToShowFullScreenContent":
                return targetId + ": " + eventName + " %d.errorCode %s.errorMessage";

            case "onAdDismissedFullScreenContent":
            case "onAdShowedFullScreenContent":
                return targetId + ": " + eventName;

            case "onBannerAdOpened":
                return targetId + ": onAdOpened";

            case "onBannerAdClicked":
                return targetId + ": onAdClicked";

            case "onBannerAdClosed":
                return targetId + ": onAdClosed";

            default:
                return EventsHandler.getSpec(targetId, eventName);
        }
    }
}
