package mod.agus.jcoderz.editor.event;

import java.util.ArrayList;

import a.a.a.Gx;
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
        switch (str.hashCode()) {
            case -2135028390:
                if (str.equals("onUpdateProfileComplete")) {
                    return 2131166348;
                }
                break;
            case -2070060406:
                if (str.equals("FBAdsBanner_onLoggingImpression")) {
                    return 2131166342;
                }
                break;
            case -2026152080:
                if (str.equals("onEmailVerificationSent")) {
                    return 2131166335;
                }
                break;
            case -1927178453:
                if (str.equals("onScrolled")) {
                    return 2131165581;
                }
                break;
            case -1881852985:
                if (str.equals("onDateChanged")) {
                    return 2131165572;
                }
                break;
            case -1764593907:
                if (str.equals("onRewarded")) {
                    return 2131166301;
                }
                break;
            case -1744369339:
                if (str.equals("onFailureLink")) {
                    return 2131166336;
                }
                break;
            case -1615009874:
                if (str.equals("onDeleteUserComplete")) {
                    return 2131166333;
                }
                break;
            case -1576048703:
                if (str.equals("onNavigationItemSelected")) {
                    return 2131165581;
                }
                break;
            case -1521795729:
                if (str.equals("onRewardedVideoAdFailedToLoad")) {
                    return 2131165552;
                }
                break;
            case -1515385099:
                if (str.equals("onDateSet")) {
                    return 2131165572;
                }
                break;
            case -1495579877:
                if (str.equals("onCompletion")) {
                    return 2131165579;
                }
                break;
            case -1491459488:
                if (str.equals("onSaveInstanceState")) {
                    return 2131165333;
                }
                break;
            case -1491259806:
                if (str.equals("onCreateOptionsMenu")) {
                    return 2131165333;
                }
                break;
            case -1478332706:
                if (str.equals("onQueryTextChanged")) {
                    return 2131165849;
                }
                break;
            case -1350032819:
                if (str.equals("onUpdateEmailComplete")) {
                    return 2131166334;
                }
                break;
            case -1349867671:
                if (str.equals("onError")) {
                    return 2131165548;
                }
                break;
            case -1340743215:
                if (str.equals("onVerificationCompleted")) {
                    return 2131166350;
                }
                break;
            case -1321851767:
                if (str.equals("onRecyclerScrollChanged")) {
                    return 2131165581;
                }
                break;
            case -1253184269:
                if (str.equals("onCreateContextMenu")) {
                    return 2131165333;
                }
                break;
            case -1186339443:
                if (str.equals("onRestoreInstanceState")) {
                    return 2131165548;
                }
                break;
            case -923278914:
                if (str.equals("onContextItemSelected")) {
                    return 2131165333;
                }
                break;
            case -893469302:
                if (str.equals("onRecyclerScrolled")) {
                    return 2131165581;
                }
                break;
            case -812638467:
                if (str.equals("onRewardedVideoAdClosed")) {
                    return 2131165551;
                }
                break;
            case -789433538:
                if (str.equals("onSuccessLink")) {
                    return 2131166337;
                }
                break;
            case -760958320:
                if (str.equals("onPatternLockCleared")) {
                    return 2131166309;
                }
                break;
            case -589155002:
                if (str.equals("FBAdsInterstitial_onAdClicked")) {
                    return 2131166338;
                }
                break;
            case -552637034:
                if (str.equals("onRewardedVideoAdLoaded")) {
                    return 2131165553;
                }
                break;
            case -465697286:
                if (str.equals("onRewardedVideoAdOpened")) {
                    return 2131165554;
                }
                break;
            case -359766219:
                if (str.equals("onAccountPickerCancelled")) {
                    return 2131166345;
                }
                break;
            case -233781414:
                if (str.equals("onPageScrolled")) {
                    return 2131165581;
                }
                break;
            case -222902665:
                if (str.equals("onVerificationFailed")) {
                    return 2131166349;
                }
                break;
            case -172690726:
                if (str.equals("onGoogleSignIn")) {
                    return 2131166346;
                }
                break;
            case -108704388:
                if (str.equals("onAccountPicker")) {
                    return 2131166344;
                }
                break;
            case -30415631:
                if (str.equals("onFragmentAdded")) {
                    return 2131166304;
                }
                break;
            case 22340470:
                if (str.equals("onTimeSet")) {
                    return 2131166276;
                }
                break;
            case 51638726:
                if (str.equals("onPageChanged")) {
                    return 2131165581;
                }
                break;
            case 140823751:
                if (str.equals("signInWithPhoneAuthComplete")) {
                    return 2131166331;
                }
                break;
            case 179352223:
                if (str.equals("FBAdsInterstitial_onInterstitialDismissed")) {
                    return 2131166339;
                }
                break;
            case 230462136:
                if (str.equals("onRatingChanged")) {
                    return 2131166177;
                }
                break;
            case 267479767:
                if (str.equals("FBAdsInterstitial_onInterstitialDisplayed")) {
                    return 2131166340;
                }
                break;
            case 388714441:
                if (str.equals("onPatternLockProgress")) {
                    return 2131166309;
                }
                break;
            case 631391277:
                if (str.equals("onOptionsItemSelected")) {
                    return 2131165333;
                }
                break;
            case 638018442:
                if (str.equals("FBAdsInterstitial_onLoggingImpression")) {
                    return 2131166342;
                }
                break;
            case 780046181:
                if (str.equals("onPatternLockStarted")) {
                    return 2131166309;
                }
                break;
            case 790347477:
                if (str.equals("onPatternLockComplete")) {
                    return 2131166309;
                }
                break;
            case 823540209:
                if (str.equals("onTabSelected")) {
                    return 2131165581;
                }
                break;
            case 903210916:
                if (str.equals("onTabReselected")) {
                    return 2131165581;
                }
                break;
            case 948149408:
                if (str.equals("onLetterSelected")) {
                    return 2131166313;
                }
                break;
            case 1226449418:
                if (str.equals("onTabUnselected")) {
                    return 2131165581;
                }
                break;
            case 1359955401:
                if (str.equals("onPageSelected")) {
                    return 2131165581;
                }
                break;
            case 1424438342:
                if (str.equals("FBAdsBanner_onAdClicked")) {
                    return 2131166338;
                }
                break;
            case 1490401084:
                if (str.equals("onPrepared")) {
                    return 2131165588;
                }
                break;
            case 1530791598:
                if (str.equals("onQueryTextSubmit")) {
                    return 2131165849;
                }
                break;
            case 1585314250:
                if (str.equals("FBAdsInterstitial_onError")) {
                    return 2131166341;
                }
                break;
            case 1626663686:
                if (str.equals("FBAdsInterstitial_onAdLoaded")) {
                    return 2131166343;
                }
                break;
            case 1691618310:
                if (str.equals("FBAdsBanner_onAdLoaded")) {
                    return 2131166343;
                }
                break;
            case 1725593802:
                if (str.equals("FBAdsBanner_onError")) {
                    return 2131166341;
                }
                break;
            case 1834797418:
                if (str.equals("onTabAdded")) {
                    return 2131166303;
                }
                break;
            case 1887055611:
                if (str.equals("onCompleteRegister")) {
                    return 2131166332;
                }
                break;
            case 1901942308:
                if (str.equals("onCodeSent")) {
                    return 2131166331;
                }
                break;
            case 1921834952:
                if (str.equals("onTimeChanged")) {
                    return 2131166276;
                }
                break;
            case 2054104968:
                if (str.equals("onScrollChanged")) {
                    return 2131165581;
                }
                break;
            case 2092419228:
                if (str.equals("onUpdatePasswordComplete")) {
                    return 2131166347;
                }
                break;
            default:
                return EventsHandler.getIcon(str);
        }
        return EventsHandler.getIcon(str);
    }

    /**
     * @return Descriptions for Events added by Agus
     */
    public static String e(String eventName) {
        switch (eventName) {
            case "onUpdateProfileComplete":
                return "onUpdateProfileComplete";

            case "FBAdsBanner_onLoggingImpression":
            case "FBAdsInterstitial_onLoggingImpression":
                return "onLoggingImpression";

            case "onEmailVerificationSent":
                return "onEmailVerificationSent";

            case "onScrolled":
                return "onScroll";

            case "onDateChanged":
                return "onDateChanged";

            case "onRewarded":
                return "onRewarded";

            case "onFailureLink":
                return "onFailure";

            case "onDeleteUserComplete":
                return "onDeleteUserComplete";

            case "onNavigationItemSelected":
                return "onNavigationItemSelected";

            case "onRewardedVideoAdFailedToLoad":
                return "onVideoAdFailedToLoad";

            case "onDateSet":
                return "onDateSet";

            case "onCompletion":
                return "onCompletion";

            case "onSaveInstanceState":
                return "On activity save instance state";

            case "onCreateOptionsMenu":
                return "On create options menu";

            case "onQueryTextChanged":
                return "onQueryTextChanged";

            case "onUpdateEmailComplete":
                return "onUpdateEmailComplete";

            case "onError":
                return "onError";

            case "onVerificationCompleted":
                return "onVerificationCompleted";

            case "onRecyclerScrollChanged":
                // Nice typo, Agus
                return "onScrollStateCanged";

            case "onCreateContextMenu":
                return "On create context menu";

            case "onRestoreInstanceState":
                return "On activity restore instance state";

            case "onContextItemSelected":
                return "On context menu selected";

            case "onRecyclerScrolled":
                return "onScroll";

            case "onRewardedVideoAdClosed":
                return "onVideoAdClosed";

            case "onSuccessLink":
                return "onSuccess";

            case "onPatternLockCleared":
                return "onPatternLockCleared";

            case "FBAdsInterstitial_onAdClicked":
                return "onAdClicked";

            case "onRewardedVideoAdLoaded":
                return "onVideoAdLoaded";

            case "onRewardedVideoAdOpened":
                return "onVideoAdOpened";

            case "onAccountPickerCancelled":
                return "onAccountCancelled";

            case "onPageScrolled":
                return "onPageScrolled";

            case "onVerificationFailed":
                return "onVerificationFailed";

            case "onGoogleSignIn":
                return "onGoogleSignIn";

            case "onAccountPicker":
                return "onAccountPicked";

            case "onFragmentAdded":
                return "Return Fragment";

            case "onTimeSet":
                return "onTimeSet";

            case "onPageChanged":
                return "onPageScrollStateChanged";

            case "signInWithPhoneAuthComplete":
                return "signInWithPhoneAuthComplete";

            case "FBAdsInterstitial_onInterstitialDismissed":
                return "onInterstitialDismissed";

            case "onRatingChanged":
                return "onRatingChanged";

            case "FBAdsInterstitial_onInterstitialDisplayed":
                return "onInterstitialDisplayed";

            case "onPatternLockProgress":
                return "onPatternLockProgress";

            case "onOptionsItemSelected":
                return "On options menu selected";

            case "onPatternLockStarted":
                return "onPatternLockStarted";

            case "onPatternLockComplete":
                return "onPatternLockComplete";

            case "onTabSelected":
                return "onTabSelected";

            case "onTabReselected":
                return "onTabReselected";

            case "onLetterSelected":
                return "onLetterSelected";

            case "onTabUnselected":
                return "onTabUnselected";

            case "onPageSelected":
                return "onPageSelected";

            case "FBAdsBanner_onAdClicked":
                return "onAdClicked";

            case "onPrepared":
                return "onPrepared";

            case "onQueryTextSubmit":
                return "onQueryTextSubmit";

            case "FBAdsInterstitial_onError":
                return "onError";

            case "FBAdsInterstitial_onAdLoaded":
                return "onAdLoaded";

            case "FBAdsBanner_onAdLoaded":
                return "onAdLoaded";

            case "FBAdsBanner_onError":
                return "onError";

            case "onTabAdded":
                return "Return Title";

            case "onCompleteRegister":
                return "onComplete";

            case "onCodeSent":
                return "onCodeSent";

            case "onTimeChanged":
                return "onTimeChanged";

            case "onScrollChanged":
                // Nice typo, Agus
                return "onScrollStateCanged";

            case "onUpdatePasswordComplete":
                return "onUpdatePasswordComplete";

            default:
                return EventsHandler.getDesc(eventName);
        }
    }

    public static String f(String eventName, String eventLogic) {
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

            case "onRewardedVideoAdClosed":
                return "@Override\r\n" +
                        "public void onRewardedVideoAdClosed() {\r\n" +
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

            case "onRewardedVideoAdLoaded":
                return "@Override\r\n" +
                        "public void onRewardedVideoAdLoaded() {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "onRewardedVideoAdOpened":
                return "@Override\r\n" +
                        "public void onRewardedVideoAdOpened() {\r\n" +
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

            case "FBAdsInterstitial_onError":
                return "@Override\r\n" +
                        "public void onError(Ad ad, AdError adError) {\r\n" +
                        "final String _errorMsg = adError.getErrorMessage() != null ? adError.getErrorMessage() : \"\";\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "FBAdsInterstitial_onAdLoaded":
                return "@Override\r\n" +
                        "public void onAdLoaded(Ad ad) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "FBAdsBanner_onAdLoaded":
                return "@Override\r\n" +
                        "public void onAdLoaded(Ad ad) {\r\n" +
                        eventLogic + "\r\n" +
                        "}";

            case "FBAdsBanner_onError":
                return "@Override\r\n" +
                        "public void onError(Ad ad, AdError adError) {\r\n" +
                        "final String _errorMsg = adError.getErrorMessage() != null ? adError.getErrorMessage() : \"\";\r\n" +
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

            default:
                return EventsHandler.getEventCode(eventName, eventLogic);
        }
    }

    /**
     * @return Code of extra listeners, used in {@link a.a.a.Lx#d(String, String, String)}
     */
    public static String g(String listenerName, String previousCode, String listenerLogic) {
        switch (listenerName) {
            case "OnCompletionListener":
                return previousCode + ".setOnCompletionListener(new MediaPlayer.OnCompletionListener() {\r\n" +
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
                return previousCode + ".setOnQueryTextListener(new SearchView.OnQueryTextListener() {\r\n" +
                        previousCode + "\r\n" +
                        "});";

            case "OnVerificationStateChangedListener":
                return previousCode + " = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "OnScrollListener":
                return previousCode + ".setOnScrollListener(new AbsListView.OnScrollListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "authsignInWithPhoneAuth":
                return previousCode + "_phoneAuthListener = new OnCompleteListener<AuthResult>() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "FragmentStatePagerAdapter":
                return "public class MyFragmentAdapter extends FragmentStatePagerAdapter {\r\n" +
                        "// This class is deprecated, you should migrate to ViewPager2:\r\n" +
                        "// https://developer.android.com/reference/androidx/viewpager2/widget/ViewPager2\r\n" +
                        "Context context;\r\n" +
                        "int tabCount;\r\n" +
                        "\r\n" +
                        "public MyFragmentAdapter(Context context, FragmentManager manager, int tabCount) {\r\n" +
                        "super(manager);\r\n" +
                        "this.context = context;\r\n" +
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
                return previousCode + "_listener = new RewardedVideoAdListener() {\r\n" +
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
                return previousCode + "_listener = new TimePickerDialog.OnTimeSetListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "OnFailureListener":
                return previousCode + "_onFailureLink = new OnFailureListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "authUpdatePasswordComplete":
                return previousCode + "_updatePasswordListener = new OnCompleteListener<Void>() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "OnSuccessListener":
                return previousCode + "_onSuccessLink = new OnSuccessListener<PendingDynamicLinkData>() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "OnGridItemClickListener":
                return previousCode + ".setOnItemClickListener(new AdapterView.OnItemClickListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "OnRecyclerScrollListener":
                return previousCode + ".addOnScrollListener(new RecyclerView.OnScrollListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "OnTimeChangeListener":
                return previousCode + ".setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "authDeleteUserComplete":
                return previousCode + "_deleteUserListener = new OnCompleteListener<Void>() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "authUpdateProfileComplete":
                return previousCode + "_updateProfileListener = new OnCompleteListener<Void>() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "OnPageChangeListener":
                return previousCode + ".addOnPageChangeListener(new ViewPager.OnPageChangeListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "OnErrorListener":
                return previousCode + ".setOnErrorListener(new MediaPlayer.OnErrorListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "authEmailVerificationSent":
                return previousCode + "_emailVerificationSentListener = new OnCompleteListener<Void>() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "authUpdateEmailComplete":
                return previousCode + "_updateEmailListener = new OnCompleteListener<Void>() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "OnPreparedListener":
                return previousCode + ".setOnPreparedListener(new MediaPlayer.OnPreparedListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "setOnItemSelectedListener":
                return previousCode + ".setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";
            case "OnLetterSelectedListener":
                return previousCode + ".setOnLetterSelectedListener(new WaveSideBar.OnLetterSelectedListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "FBAdsInterstitial_InterstitialAdListener":
                return previousCode + "_InterstitialAdListener = new InterstitialAdListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "OnRatingBarChangeListener":
                return previousCode + ".setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "OnTabSelectedListener":
                return previousCode + ".addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "OnDateChangeListener":
                return "Calendar _calendar = Calendar.getInstance();\r\n" +
                        previousCode + ".init(_calendar.get(Calendar.YEAR), _calendar.get(Calendar.MONTH), " +
                        "_calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "OnCompleteListenerFCM":
                return previousCode + "_onCompleteListener = new OnCompleteListener<InstanceIdResult>() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "PatternLockViewListener":
                return previousCode + ".addPatternLockListener(new PatternLockViewListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "FBAdsBanner_AdListener":
                return previousCode + "_AdListener = new AdListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            case "OnGridItemLongClickListener":
                return previousCode + ".setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {\r\n" +
                        listenerLogic + "\r\n" +
                        "});";

            case "googleSignInListener":
                return previousCode + "_googleSignInListener = new OnCompleteListener<AuthResult>() {\r\n" +
                        listenerLogic + "\r\n" +
                        "};";

            default:
                return EventsHandler.getListenerCode(listenerName, previousCode, listenerLogic);
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

            case "onFragmentAdded":
                return "%d.position";

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
                return "%d.position";

            case "onTabReselected":
                return "%d.position";

            case "onLetterSelected":
                return "%s.index";

            case "onTabUnselected":
                return "%d.position";

            case "onPageSelected":
                return "%m.listMap.data %d.position";

            case "onQueryTextSubmit":
                return "%s.charSeq";

            case "FBAdsInterstitial_onError":
                return "%s.errorMsg";

            case "FBAdsBanner_onError":
                return "%s.errorMsg";

            case "onTabAdded":
                return "%d.position";

            case "onCompleteRegister":
                return "%b.success %s.token %s.errorMessage";

            case "onCodeSent":
                return "%s.verificationId %m.FirebasePhoneAuth.token";

            case "onTimeChanged":
                return "%d.hour %d.minute";

            case "onScrollChanged":
                return "%d.scrollState";

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

    public static String i(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        switch (str2.hashCode()) {
            case -2135028390:
                if (str2.equals("onUpdateProfileComplete")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%b.success %s.errorMessage");
                    return sb.toString();
                }
                break;
            case -2070060406:
                if (str2.equals("FBAdsBanner_onLoggingImpression")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    return sb.toString();
                }
                break;
            case -2026152080:
                if (str2.equals("onEmailVerificationSent")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%b.success %s.errorMessage");
                    return sb.toString();
                }
                break;
            case -1927178453:
                if (str2.equals("onScrolled")) {
                    sb.append("When ");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%d.firstVisibleItem %d.visibleItemCount %d.totalItemCount");
                    break;
                }
                break;
            case -1881852985:
                if (str2.equals("onDateChanged")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%d.year %d.month %d.day");
                    break;
                }
                break;
            case -1764593907:
                if (str2.equals("onRewarded")) {
                    sb.append("onRewarded ");
                    sb.append("%d.rewardItem");
                    break;
                }
                break;
            case -1744369339:
                if (str2.equals("onFailureLink")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%s.errorMessage");
                    return sb.toString();
                }
                break;
            case -1615009874:
                if (str2.equals("onDeleteUserComplete")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%b.success %s.errorMessage");
                    return sb.toString();
                }
                break;
            case -1576048703:
                if (str2.equals("onNavigationItemSelected")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%d.itemId");
                    break;
                }
                break;
            case -1521795729:
                if (str2.equals("onRewardedVideoAdFailedToLoad")) {
                    sb.append("onRewardedVideoAdFailedToLoad ");
                    sb.append("%d.errorCode");
                    break;
                }
                break;
            case -1515385099:
                if (str2.equals("onDateSet")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%d.year %d.month %d.day");
                    break;
                }
                break;
            case -1495579877:
                if (str2.equals("onCompletion")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    break;
                }
                break;
            case -1491459488:
                if (str2.equals("onSaveInstanceState")) {
                    sb.append("When ");
                    sb.append(str2);
                    break;
                }
                break;
            case -1491259806:
                if (str2.equals("onCreateOptionsMenu")) {
                    sb.append("When ");
                    sb.append(str2);
                    break;
                }
                break;
            case -1478332706:
                if (str2.equals("onQueryTextChanged")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%s.charSeq");
                    break;
                }
                break;
            case -1350032819:
                if (str2.equals("onUpdateEmailComplete")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%b.success %s.errorMessage");
                    return sb.toString();
                }
                break;
            case -1349867671:
                if (str2.equals("onError")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%d.what %d.extra");
                    break;
                }
                break;
            case -1340743215:
                if (str2.equals("onVerificationCompleted")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%m.PhoneAuthCredential.credential");
                    return sb.toString();
                }
                break;
            case -1321851767:
                if (str2.equals("onRecyclerScrollChanged")) {
                    sb.append("When ");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%d.scrollState");
                    break;
                }
                break;
            case -1253184269:
                if (str2.equals("onCreateContextMenu")) {
                    sb.append("When ");
                    sb.append(str2);
                    break;
                }
                break;
            case -1186339443:
                if (str2.equals("onRestoreInstanceState")) {
                    sb.append("When ");
                    sb.append(str2);
                    break;
                }
                break;
            case -923278914:
                if (str2.equals("onContextItemSelected")) {
                    sb.append("When ");
                    sb.append(str2);
                    break;
                }
                break;
            case -893469302:
                if (str2.equals("onRecyclerScrolled")) {
                    sb.append("When ");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%d.offsetX %d.offsetY");
                    break;
                }
                break;
            case -812638467:
                if (str2.equals("onRewardedVideoAdClosed")) {
                    sb.append("onRewardedVideoAdClosed");
                    break;
                }
                break;
            case -789433538:
                if (str2.equals("onSuccessLink")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%s.link");
                    return sb.toString();
                }
                break;
            case -760958320:
                if (str2.equals("onPatternLockCleared")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    break;
                }
                break;
            case -589155002:
                if (str2.equals("FBAdsInterstitial_onAdClicked")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    return sb.toString();
                }
                break;
            case -552637034:
                if (str2.equals("onRewardedVideoAdLoaded")) {
                    sb.append("onRewardedVideoAdLoaded");
                    break;
                }
                break;
            case -465697286:
                if (str2.equals("onRewardedVideoAdOpened")) {
                    sb.append("onRewardedVideoAdOpened");
                    break;
                }
                break;
            case -359766219:
                if (str2.equals("onAccountPickerCancelled")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    return sb.toString();
                }
                break;
            case -233781414:
                if (str2.equals("onPageScrolled")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%d.position %d.positionOffset %d.positionOffsetPixels");
                    break;
                }
                break;
            case -222902665:
                if (str2.equals("onVerificationFailed")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%s.exception");
                    return sb.toString();
                }
                break;
            case -172690726:
                if (str2.equals("onGoogleSignIn")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%b.success %s.errorMessage");
                    return sb.toString();
                }
                break;
            case -108704388:
                if (str2.equals("onAccountPicker")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%m.GoogleSignInAccount.task");
                    return sb.toString();
                }
                break;
            case -30415631:
                if (str2.equals("onFragmentAdded")) {
                    sb.append("Fragment getItem");
                    sb.append("%d.position");
                    break;
                }
                break;
            case 22340470:
                if (str2.equals("onTimeSet")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%d.hour %d.minute");
                    break;
                }
                break;
            case 51638726:
                if (str2.equals("onPageChanged")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" onPageScrollStateChanged ");
                    sb.append("%d.scrollState");
                    break;
                }
                break;
            case 140823751:
                if (str2.equals("signInWithPhoneAuthComplete")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%b.success %s.errorMessage");
                    return sb.toString();
                }
                break;
            case 179352223:
                if (str2.equals("FBAdsInterstitial_onInterstitialDismissed")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    return sb.toString();
                }
                break;
            case 230462136:
                if (str2.equals("onRatingChanged")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%d.value");
                    break;
                }
                break;
            case 267479767:
                if (str2.equals("FBAdsInterstitial_onInterstitialDisplayed")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    return sb.toString();
                }
                break;
            case 388714441:
                if (str2.equals("onPatternLockProgress")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%m.listStr.pattern");
                    break;
                }
                break;
            case 631391277:
                if (str2.equals("onOptionsItemSelected")) {
                    sb.append("When ");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append("%d.id %s.title");
                    break;
                }
                break;
            case 638018442:
                if (str2.equals("FBAdsInterstitial_onLoggingImpression")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    return sb.toString();
                }
                break;
            case 780046181:
                if (str2.equals("onPatternLockStarted")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    break;
                }
                break;
            case 790347477:
                if (str2.equals("onPatternLockComplete")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%m.listStr.pattern");
                    break;
                }
                break;
            case 823540209:
                if (str2.equals("onTabSelected")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%d.position");
                    break;
                }
                break;
            case 903210916:
                if (str2.equals("onTabReselected")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%d.position");
                    break;
                }
                break;
            case 948149408:
                if (str2.equals("onLetterSelected")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%s.index");
                    break;
                }
                break;
            case 1226449418:
                if (str2.equals("onTabUnselected")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%d.position");
                    break;
                }
                break;
            case 1359955401:
                if (str2.equals("onPageSelected")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%d.position");
                    break;
                }
                break;
            case 1424438342:
                if (str2.equals("FBAdsBanner_onAdClicked")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    return sb.toString();
                }
                break;
            case 1490401084:
                if (str2.equals("onPrepared")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    break;
                }
                break;
            case 1530791598:
                if (str2.equals("onQueryTextSubmit")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%s.charSeq");
                    break;
                }
                break;
            case 1585314250:
                if (str2.equals("FBAdsInterstitial_onError")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%s.errorMsg");
                    return sb.toString();
                }
                break;
            case 1626663686:
                if (str2.equals("FBAdsInterstitial_onAdLoaded")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    return sb.toString();
                }
                break;
            case 1691618310:
                if (str2.equals("FBAdsBanner_onAdLoaded")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    return sb.toString();
                }
                break;
            case 1725593802:
                if (str2.equals("FBAdsBanner_onError")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%s.errorMsg");
                    return sb.toString();
                }
                break;
            case 1834797418:
                if (str2.equals("onTabAdded")) {
                    sb.append("CharSequence getPageTitle");
                    sb.append("%d.position");
                    break;
                }
                break;
            case 1887055611:
                if (str2.equals("onCompleteRegister")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%b.success %s.token %s.errorMessage");
                    return sb.toString();
                }
                break;
            case 1901942308:
                if (str2.equals("onCodeSent")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%s.verificationId %m.FirebasePhoneAuth.token");
                    return sb.toString();
                }
                break;
            case 1921834952:
                if (str2.equals("onTimeChanged")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%d.hour %d.minute");
                    break;
                }
                break;
            case 2054104968:
                if (str2.equals("onScrollChanged")) {
                    sb.append("When ");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%d.scrollState");
                    break;
                }
                break;
            case 2092419228:
                if (str2.equals("onUpdatePasswordComplete")) {
                    sb.append("When");
                    sb.append(" ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(str2);
                    sb.append(" ");
                    sb.append("%b.success %s.errorMessage");
                    return sb.toString();
                }
                break;
            default:
                return EventsHandler.getSpec(str, str2);
        }
        return sb.toString();
    }
}
