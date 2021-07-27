package mod.agus.jcoderz.editor.event;

import java.util.ArrayList;

import a.a.a.Gx;
import mod.hilal.saif.events.EventsHandler;

public class ManageEvent {

    public static void a(Gx gx, ArrayList<String> arrayList) {
        if (gx.a("RatingBar")) {
            arrayList.add("onRatingChanged");
        }
        if (gx.a("TimePicker")) {
            arrayList.add("onTimeChanged");
        }
        if (gx.a("DatePicker")) {
            arrayList.add("onDateChanged");
        }
        if (gx.a("VideoView")) {
            arrayList.add("onPrepared");
            arrayList.add("onError");
            arrayList.add("onCompletion");
        }
        if (gx.a("SearchView")) {
            arrayList.add("onQueryTextSubmit");
            arrayList.add("onQueryTextChanged");
        }
        if (gx.a("ListView")) {
            arrayList.add("onScrollChanged");
            arrayList.add("onScrolled");
        }
        if (gx.a("RecyclerView")) {
            arrayList.add("onBindCustomView");
            arrayList.add("onRecyclerScrollChanged");
            arrayList.add("onRecyclerScrolled");
        }
        if (gx.a("GridView")) {
            arrayList.add("onItemClicked");
            arrayList.add("onItemLongClicked");
            arrayList.add("onBindCustomView");
        }
        if (gx.a("Spinner")) {
            arrayList.add("onBindCustomView");
        }
        if (gx.a("ViewPager")) {
            arrayList.add("onBindCustomView");
            arrayList.add("onPageScrolled");
            arrayList.add("onPageSelected");
            arrayList.add("onPageChanged");
        }
        if (gx.a("TabLayout")) {
            arrayList.add("onTabSelected");
            arrayList.add("onTabUnselected");
            arrayList.add("onTabReselected");
        }
        if (gx.a("BottomNavigationView")) {
            arrayList.add("onNavigationItemSelected");
        }
        if (gx.a("PatternLockView")) {
            arrayList.add("onPatternLockStarted");
            arrayList.add("onPatternLockProgress");
            arrayList.add("onPatternLockComplete");
            arrayList.add("onPatternLockCleared");
        }
        if (gx.a("WaveSideBar")) {
            arrayList.add("onLetterSelected");
        }
        EventsHandler.addEvents(gx, arrayList);
    }

    public static void b(Gx gx, ArrayList<String> arrayList) {
        if (gx.a("RatingBar")) {
            arrayList.add("OnRatingBarChangeListener");
        }
        if (gx.a("TimePicker")) {
            arrayList.add("OnTimeChangeListener");
        }
        if (gx.a("DatePicker")) {
            arrayList.add("OnDateChangeListener");
        }
        if (gx.a("VideoView")) {
            arrayList.add("OnPreparedListener");
            arrayList.add("OnErrorListener");
            arrayList.add("OnCompletionListener");
        }
        if (gx.a("SearchView")) {
            arrayList.add("OnQueryTextListener");
        }
        if (gx.a("TimePickerDialog")) {
            arrayList.add("OnTimeSetListener");
        }
        if (gx.a("DatePickerDialog")) {
            arrayList.add("OnDateSetListener");
        }
        if (gx.a("FragmentAdapter")) {
            arrayList.add("FragmentStatePagerAdapter");
        }
        if (gx.a("RewardedVideoAd")) {
            arrayList.add("OnVideoAdListener");
        }
        if (gx.a("ListView")) {
            arrayList.add("OnScrollListener");
        }
        if (gx.a("RecyclerView")) {
            arrayList.add("OnRecyclerScrollListener");
        }
        if (gx.a("GridView")) {
            arrayList.add("OnGridItemClickListener");
            arrayList.add("OnGridItemLongClickListener");
        }
        if (gx.a("ViewPager")) {
            arrayList.add("OnPageChangeListener");
            arrayList.add("OnAdapterChangeListener");
        }
        if (gx.a("TabLayout")) {
            arrayList.add("OnTabSelectedListener");
        }
        if (gx.a("BottomNavigationView")) {
            arrayList.add("OnNavigationItemSelected");
        }
        if (gx.a("PatternLockView")) {
            arrayList.add("PatternLockViewListener");
        }
        if (gx.a("WaveSideBar")) {
            arrayList.add("OnLetterSelectedListener");
        }
        if (gx.a("FirebaseAuth")) {
            arrayList.add("authUpdateEmailComplete");
            arrayList.add("authUpdatePasswordComplete");
            arrayList.add("authEmailVerificationSent");
            arrayList.add("authDeleteUserComplete");
            arrayList.add("authsignInWithPhoneAuth");
            arrayList.add("authUpdateProfileComplete");
            arrayList.add("googleSignInListener");
        }
        if (gx.a("FirebasePhoneAuth")) {
            arrayList.add("OnVerificationStateChangedListener");
        }
        if (gx.a("FirebaseDynamicLink")) {
            arrayList.add("OnSuccessListener");
            arrayList.add("OnFailureListener");
        }
        if (gx.a("FirebaseCloudMessage")) {
            arrayList.add("OnCompleteListenerFCM");
        }
        if (gx.a("FBAdsBanner")) {
            arrayList.add("FBAdsBanner_AdListener");
        }
        if (gx.a("FBAdsInterstitial")) {
            arrayList.add("FBAdsInterstitial_InterstitialAdListener");
        }
        EventsHandler.addListeners(gx, arrayList);
    }

    public static void c(String str, ArrayList<String> arrayList) {
        switch (str.hashCode()) {
            case -2013506289:
                if (str.equals("OnCompletionListener")) {
                    arrayList.add("onCompletion");
                    return;
                }
                return;
            case -1961315671:
                if (str.equals("OnDateSetListener")) {
                    arrayList.add("onDateSet");
                    return;
                }
                return;
            case -1711602262:
                if (str.equals("OnQueryTextListener")) {
                    arrayList.add("onQueryTextSubmit");
                    arrayList.add("onQueryTextChanged");
                    return;
                }
                return;
            case -1683818607:
                if (str.equals("OnVerificationStateChangedListener")) {
                    arrayList.add("onVerificationCompleted");
                    arrayList.add("onVerificationFailed");
                    arrayList.add("onCodeSent");
                    return;
                }
                return;
            case -1676288384:
                if (str.equals("OnScrollListener")) {
                    arrayList.add("onScrollChanged");
                    arrayList.add("onScrolled");
                    return;
                }
                return;
            case -1641400634:
                if (str.equals("authsignInWithPhoneAuth")) {
                    arrayList.add("signInWithPhoneAuthComplete");
                    return;
                }
                return;
            case -1561524051:
                if (str.equals("FragmentStatePagerAdapter")) {
                    arrayList.add("onTabAdded");
                    arrayList.add("onFragmentAdded");
                    return;
                }
                return;
            case -1381186349:
                if (str.equals("OnVideoAdListener")) {
                    arrayList.add("onRewarded");
                    arrayList.add("onRewardedVideoAdLoaded");
                    arrayList.add("onRewardedVideoAdFailedToLoad");
                    arrayList.add("onRewardedVideoAdOpened");
                    arrayList.add("onRewardedVideoAdClosed");
                    return;
                }
                return;
            case -1305971158:
                if (str.equals("OnTimeSetListener")) {
                    arrayList.add("onTimeSet");
                    return;
                }
                return;
            case -1289633697:
                if (str.equals("OnFailureListener")) {
                    arrayList.add("onFailureLink");
                    return;
                }
                return;
            case -922637403:
                if (str.equals("authUpdatePasswordComplete")) {
                    arrayList.add("onUpdatePasswordComplete");
                    return;
                }
                return;
            case -838515240:
                if (str.equals("OnSuccessListener")) {
                    arrayList.add("onSuccessLink");
                    return;
                }
                return;
            case -758438236:
                if (str.equals("OnGridItemClickListener")) {
                    arrayList.add("onItemClicked");
                    return;
                }
                return;
            case -264740449:
                if (str.equals("OnRecyclerScrollListener")) {
                    arrayList.add("onRecyclerScrollChanged");
                    arrayList.add("onRecyclerScrolled");
                    return;
                }
                return;
            case -237230832:
                if (str.equals("OnTimeChangeListener")) {
                    arrayList.add("onTimeChanged");
                    return;
                }
                return;
            case 242988855:
                if (str.equals("authDeleteUserComplete")) {
                    arrayList.add("onDeleteUserComplete");
                    return;
                }
                return;
            case 261563697:
                if (str.equals("authUpdateProfileComplete")) {
                    arrayList.add("onUpdateProfileComplete");
                    return;
                }
                return;
            case 282753362:
                if (str.equals("OnPageChangeListener")) {
                    arrayList.add("onPageScrolled");
                    arrayList.add("onPageSelected");
                    arrayList.add("onPageChanged");
                    return;
                }
                return;
            case 313462557:
                if (str.equals("OnErrorListener")) {
                    arrayList.add("onError");
                    return;
                }
                return;
            case 370440007:
                if (str.equals("authEmailVerificationSent")) {
                    arrayList.add("onEmailVerificationSent");
                    return;
                }
                return;
            case 413352932:
                if (str.equals("authUpdateEmailComplete")) {
                    arrayList.add("onUpdateEmailComplete");
                    return;
                }
                return;
            case 554934320:
                if (str.equals("OnPreparedListener")) {
                    arrayList.add("onPrepared");
                    return;
                }
                return;
            case 568167393:
                if (str.equals("OnNavigationItemSelected")) {
                    arrayList.add("onNavigationItemSelected");
                    return;
                }
                return;
            case 936528276:
                if (str.equals("OnLetterSelectedListener")) {
                    arrayList.add("onLetterSelected");
                    return;
                }
                return;
            case 1078577634:
                if (str.equals("FBAdsInterstitial_InterstitialAdListener")) {
                    arrayList.add("FBAdsInterstitial_onError");
                    arrayList.add("FBAdsInterstitial_onAdLoaded");
                    arrayList.add("FBAdsInterstitial_onAdClicked");
                    arrayList.add("FBAdsInterstitial_onLoggingImpression");
                    arrayList.add("FBAdsInterstitial_onInterstitialDisplayed");
                    arrayList.add("FBAdsInterstitial_onInterstitialDismissed");
                    return;
                }
                return;
            case 1207833179:
                if (str.equals("OnRatingBarChangeListener")) {
                    arrayList.add("onRatingChanged");
                    return;
                }
                return;
            case 1295135141:
                if (str.equals("OnTabSelectedListener")) {
                    arrayList.add("onTabSelected");
                    arrayList.add("onTabUnselected");
                    arrayList.add("onTabReselected");
                    return;
                }
                return;
            case 1315710001:
                if (str.equals("OnDateChangeListener")) {
                    arrayList.add("onDateChanged");
                    return;
                }
                return;
            case 1448799876:
                if (str.equals("OnCompleteListenerFCM")) {
                    arrayList.add("onCompleteRegister");
                    return;
                }
                return;
            case 1750173396:
                if (str.equals("PatternLockViewListener")) {
                    arrayList.add("onPatternLockStarted");
                    arrayList.add("onPatternLockProgress");
                    arrayList.add("onPatternLockComplete");
                    arrayList.add("onPatternLockCleared");
                    return;
                }
                return;
            case 1995891990:
                if (str.equals("FBAdsBanner_AdListener")) {
                    arrayList.add("FBAdsBanner_onError");
                    arrayList.add("FBAdsBanner_onAdLoaded");
                    arrayList.add("FBAdsBanner_onAdClicked");
                    arrayList.add("FBAdsBanner_onLoggingImpression");
                    return;
                }
                return;
            case 2058196360:
                if (str.equals("OnGridItemLongClickListener")) {
                    arrayList.add("onItemLongClicked");
                    return;
                }
                return;
            case 2139489583:
                if (str.equals("googleSignInListener")) {
                    arrayList.add("onGoogleSignIn");
                    return;
                }
                return;
            default:
                EventsHandler.addEventsToListener(str, arrayList);
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

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public static String e(String str) {
        switch (str.hashCode()) {
            case -2135028390:
                if (str.equals("onUpdateProfileComplete")) {
                    return "onUpdateProfileComplete";
                }
                return "";
            case -2070060406:
                if (!str.equals("FBAdsBanner_onLoggingImpression")) {
                    return "";
                }
                return "onLoggingImpression";
            case -2026152080:
                if (str.equals("onEmailVerificationSent")) {
                    return "onEmailVerificationSent";
                }
                return "";
            case -1927178453:
                if (str.equals("onScrolled")) {
                    return "onScroll";
                }
                return str;
            case -1881852985:
                if (str.equals("onDateChanged")) {
                    return "onDateChanged";
                }
                return str;
            case -1764593907:
                if (str.equals("onRewarded")) {
                    return "onRewarded";
                }
                return str;
            case -1744369339:
                if (str.equals("onFailureLink")) {
                    return "onFailure";
                }
                return "";
            case -1615009874:
                if (str.equals("onDeleteUserComplete")) {
                    return "onDeleteUserComplete";
                }
                return "";
            case -1576048703:
                if (str.equals("onNavigationItemSelected")) {
                    return "onNavigationItemSelected";
                }
                return str;
            case -1521795729:
                if (str.equals("onRewardedVideoAdFailedToLoad")) {
                    return "onVideoAdFailedToLoad";
                }
                return str;
            case -1515385099:
                if (str.equals("onDateSet")) {
                    return "onDateSet";
                }
                return str;
            case -1495579877:
                if (str.equals("onCompletion")) {
                    return "onCompletion";
                }
                return str;
            case -1491459488:
                if (str.equals("onSaveInstanceState")) {
                    return "On activity save instance state";
                }
                return str;
            case -1491259806:
                if (str.equals("onCreateOptionsMenu")) {
                    return "On create options menu";
                }
                return str;
            case -1478332706:
                if (str.equals("onQueryTextChanged")) {
                    return "onQueryTextChanged";
                }
                return str;
            case -1350032819:
                if (str.equals("onUpdateEmailComplete")) {
                    return "onUpdateEmailComplete";
                }
                return "";
            case -1349867671:
                if (str.equals("onError")) {
                    return "onError";
                }
                return str;
            case -1340743215:
                if (str.equals("onVerificationCompleted")) {
                    return "onVerificationCompleted";
                }
                return "";
            case -1321851767:
                if (str.equals("onRecyclerScrollChanged")) {
                    return "onScrollStateCanged";
                }
                return str;
            case -1253184269:
                if (str.equals("onCreateContextMenu")) {
                    return "On create context menu";
                }
                return str;
            case -1186339443:
                if (str.equals("onRestoreInstanceState")) {
                    return "On activity restore instance state";
                }
                return str;
            case -923278914:
                if (str.equals("onContextItemSelected")) {
                    return "On context menu selected";
                }
                return str;
            case -893469302:
                if (str.equals("onRecyclerScrolled")) {
                    return "onScroll";
                }
                return str;
            case -812638467:
                if (str.equals("onRewardedVideoAdClosed")) {
                    return "onVideoAdClosed";
                }
                return str;
            case -789433538:
                if (str.equals("onSuccessLink")) {
                    return "onSuccess";
                }
                return "";
            case -760958320:
                if (str.equals("onPatternLockCleared")) {
                    return "onPatternLockCleared";
                }
                return str;
            case -589155002:
                if (!str.equals("FBAdsInterstitial_onAdClicked")) {
                    return "";
                }
                return "onAdClicked";
            case -552637034:
                if (str.equals("onRewardedVideoAdLoaded")) {
                    return "onVideoAdLoaded";
                }
                return str;
            case -465697286:
                if (str.equals("onRewardedVideoAdOpened")) {
                    return "onVideoAdOpened";
                }
                return str;
            case -359766219:
                if (str.equals("onAccountPickerCancelled")) {
                    return "onAccountCancelled";
                }
                return "";
            case -233781414:
                if (str.equals("onPageScrolled")) {
                    return "onPageScrolled";
                }
                return str;
            case -222902665:
                if (str.equals("onVerificationFailed")) {
                    return "onVerificationFailed";
                }
                return "";
            case -172690726:
                if (str.equals("onGoogleSignIn")) {
                    return "onGoogleSignIn";
                }
                return "";
            case -108704388:
                if (str.equals("onAccountPicker")) {
                    return "onAccountPicked";
                }
                return "";
            case -30415631:
                if (str.equals("onFragmentAdded")) {
                    return "Return Fragment";
                }
                return str;
            case 22340470:
                if (str.equals("onTimeSet")) {
                    return "onTimeSet";
                }
                return str;
            case 51638726:
                if (str.equals("onPageChanged")) {
                    return "onPageScrollStateChanged";
                }
                return str;
            case 140823751:
                if (str.equals("signInWithPhoneAuthComplete")) {
                    return "signInWithPhoneAuthComplete";
                }
                return "";
            case 179352223:
                if (str.equals("FBAdsInterstitial_onInterstitialDismissed")) {
                    return "onInterstitialDismissed";
                }
                return "";
            case 230462136:
                if (str.equals("onRatingChanged")) {
                    return "onRatingChanged";
                }
                return str;
            case 267479767:
                if (str.equals("FBAdsInterstitial_onInterstitialDisplayed")) {
                    return "onInterstitialDisplayed";
                }
                return "";
            case 388714441:
                if (str.equals("onPatternLockProgress")) {
                    return "onPatternLockProgress";
                }
                return str;
            case 631391277:
                if (str.equals("onOptionsItemSelected")) {
                    return "On options menu selected";
                }
                return str;
            case 638018442:
                if (!str.equals("FBAdsInterstitial_onLoggingImpression")) {
                    return "";
                }
                return "onLoggingImpression";
            case 780046181:
                if (str.equals("onPatternLockStarted")) {
                    return "onPatternLockStarted";
                }
                return str;
            case 790347477:
                if (str.equals("onPatternLockComplete")) {
                    return "onPatternLockComplete";
                }
                return str;
            case 823540209:
                if (str.equals("onTabSelected")) {
                    return "onTabSelected";
                }
                return str;
            case 903210916:
                if (str.equals("onTabReselected")) {
                    return "onTabReselected";
                }
                return str;
            case 948149408:
                if (str.equals("onLetterSelected")) {
                    return "onLetterSelected";
                }
                return str;
            case 1226449418:
                if (str.equals("onTabUnselected")) {
                    return "onTabUnselected";
                }
                return str;
            case 1359955401:
                if (str.equals("onPageSelected")) {
                    return "onPageSelected";
                }
                return str;
            case 1424438342:
                if (!str.equals("FBAdsBanner_onAdClicked")) {
                    return "";
                }
                return "onAdClicked";
            case 1490401084:
                if (str.equals("onPrepared")) {
                    return "onPrepared";
                }
                return str;
            case 1530791598:
                if (str.equals("onQueryTextSubmit")) {
                    return "onQueryTextSubmit";
                }
                return str;
            case 1585314250:
                if (!str.equals("FBAdsInterstitial_onError")) {
                    return "";
                }
                return "onError";
            case 1626663686:
                if (!str.equals("FBAdsInterstitial_onAdLoaded")) {
                    return "";
                }
                return "onAdLoaded";
            case 1691618310:
                if (!str.equals("FBAdsBanner_onAdLoaded")) {
                    return "";
                }
                return "onAdLoaded";
            case 1725593802:
                if (!str.equals("FBAdsBanner_onError")) {
                    return "";
                }
                return "onError";
            case 1834797418:
                if (str.equals("onTabAdded")) {
                    return "Return Title";
                }
                return str;
            case 1887055611:
                if (str.equals("onCompleteRegister")) {
                    return "onComplete";
                }
                return "";
            case 1901942308:
                if (str.equals("onCodeSent")) {
                    return "onCodeSent";
                }
                return "";
            case 1921834952:
                if (str.equals("onTimeChanged")) {
                    return "onTimeChanged";
                }
                return str;
            case 2054104968:
                if (str.equals("onScrollChanged")) {
                    return "onScrollStateCanged";
                }
                return str;
            case 2092419228:
                if (str.equals("onUpdatePasswordComplete")) {
                    return "onUpdatePasswordComplete";
                }
                break;
        }
        return EventsHandler.getDesc(str);
    }

    public static String f(String str, String str2) {
        String str3 = "";
        switch (str.hashCode()) {
            case -2135028390:
                if (str.equals("onUpdateProfileComplete")) {
                    str3 = "@Override\r\npublic void onComplete(Task<Void> _param1) {\r\nfinal boolean _success = _param1.isSuccessful();\r\nfinal String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : \"\";\r\n%s\r\n}";
                    break;
                }
                break;
            case -2070060406:
                if (str.equals("FBAdsBanner_onLoggingImpression")) {
                    str3 = "@Override\r\npublic void onLoggingImpression(Ad ad) {\r\n%s\r\n}";
                    break;
                }
                break;
            case -2026152080:
                if (str.equals("onEmailVerificationSent")) {
                    str3 = "@Override\r\npublic void onComplete(Task<Void> _param1) {\r\nfinal boolean _success = _param1.isSuccessful();\r\nfinal String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : \"\";\r\n%s\r\n}";
                    break;
                }
                break;
            case -1927178453:
                if (str.equals("onScrolled")) {
                    str3 = "@Override\npublic void onScroll(AbsListView abs, int _firstVisibleItem, int _visibleItemCount, int _totalItemCount) {\n%s\n}";
                    break;
                }
                break;
            case -1881852985:
                if (str.equals("onDateChanged")) {
                    str3 = "@Override\npublic void onDateChanged(DatePicker _datePicker, int _year, int _month, int _day) {\n%s\n}";
                    break;
                }
                break;
            case -1764593907:
                if (str.equals("onRewarded")) {
                    str3 = "@Override\npublic void onRewarded(RewardItem rewardItem){\nfinal int _rewardItem = rewardItem.getAmount();\n%s\n}";
                    break;
                }
                break;
            case -1744369339:
                if (str.equals("onFailureLink")) {
                    str3 = "@Override\r\npublic void onFailure(Exception _e) {\r\nfinal String _errorMessage = _e.getMessage();\r\n%s\r\n}";
                    break;
                }
                break;
            case -1615009874:
                if (str.equals("onDeleteUserComplete")) {
                    str3 = "@Override\r\npublic void onComplete(Task<Void> _param1) {\r\nfinal boolean _success = _param1.isSuccessful();\r\nfinal String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : \"\";\r\n%s\r\n}";
                    break;
                }
                break;
            case -1576048703:
                if (str.equals("onNavigationItemSelected")) {
                    str3 = "@Override\npublic boolean onNavigationItemSelected(MenuItem item) {\nfinal int _itemId = item.getItemId();\n%s\r\nreturn true;\n}";
                    break;
                }
                break;
            case -1521795729:
                if (str.equals("onRewardedVideoAdFailedToLoad")) {
                    str3 = "@Override\npublic void onRewardedVideoAdFailedToLoad(int errorCode) {\nfinal int _errorCode = errorCode;\n%s\n}";
                    break;
                }
                break;
            case -1515385099:
                if (str.equals("onDateSet")) {
                    str3 = "@Override\npublic void onDateSet(DatePicker _datePicker, int _year, int _month, int _day){\n%s\n}";
                    break;
                }
                break;
            case -1495579877:
                if (str.equals("onCompletion")) {
                    str3 = "@Override\npublic void onCompletion(MediaPlayer _mediaPlayer){\n%s\n}";
                    break;
                }
                break;
            case -1491459488:
                if (str.equals("onSaveInstanceState")) {
                    str3 = "@Override\nprotected void onSaveInstanceState(Bundle outState){\n%s\nsuper.onSaveInstanceState(outState);\n}";
                    break;
                }
                break;
            case -1491259806:
                if (str.equals("onCreateOptionsMenu")) {
                    str3 = "@Override\npublic boolean onCreateOptionsMenu(Menu menu){\n%s\nreturn super.onCreateOptionsMenu(menu);\n}";
                    break;
                }
                break;
            case -1478332706:
                if (str.equals("onQueryTextChanged")) {
                    str3 = "@Override\npublic boolean onQueryTextChange(String _charSeq){\n%s\nreturn true;\n}";
                    break;
                }
                break;
            case -1350032819:
                if (str.equals("onUpdateEmailComplete")) {
                    str3 = "@Override\r\npublic void onComplete(Task<Void> _param1) {\r\nfinal boolean _success = _param1.isSuccessful();\r\nfinal String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : \"\";\r\n%s\r\n}";
                    break;
                }
                break;
            case -1349867671:
                if (str.equals("onError")) {
                    str3 = "@Override\npublic boolean onError(MediaPlayer _mediaPlayer, int _what, int _extra){\n%s\nreturn true;\n}";
                    break;
                }
                break;
            case -1340743215:
                if (str.equals("onVerificationCompleted")) {
                    str3 = "@Override\r\npublic void onVerificationCompleted(PhoneAuthCredential _credential) {\r\n%s\r\n}";
                    break;
                }
                break;
            case -1321851767:
                if (str.equals("onRecyclerScrollChanged")) {
                    str3 = "@Override\npublic void onScrollStateChanged(RecyclerView recyclerView, int _scrollState) {\nsuper.onScrollStateChanged(recyclerView, _scrollState);\n%s\n}";
                    break;
                }
                break;
            case -1253184269:
                if (str.equals("onCreateContextMenu")) {
                    str3 = "@Override\npublic void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){\n%s\nsuper.onCreateContextMenu(menu, view, menuInfo);\n}";
                    break;
                }
                break;
            case -1186339443:
                if (str.equals("onRestoreInstanceState")) {
                    str3 = "@Override\nprotected void onRestoreInstanceState(Bundle savedInstanceState){\n%s\nsuper.onRestoreInstanceState(savedInstanceState);\n}";
                    break;
                }
                break;
            case -923278914:
                if (str.equals("onContextItemSelected")) {
                    str3 = "@Override\npublic boolean onContextItemSelected(MenuItem item){\n%s\nreturn super.onContextItemSelected(item);\n}";
                    break;
                }
                break;
            case -893469302:
                if (str.equals("onRecyclerScrolled")) {
                    str3 = "@Override\npublic void onScrolled(RecyclerView recyclerView, int _offsetX, int _offsetY) {\nsuper.onScrolled(recyclerView, _offsetX, _offsetY);\n%s\n}";
                    break;
                }
                break;
            case -812638467:
                if (str.equals("onRewardedVideoAdClosed")) {
                    str3 = "@Override\npublic void onRewardedVideoAdClosed() {\n%s\n}";
                    break;
                }
                break;
            case -789433538:
                if (str.equals("onSuccessLink")) {
                    str3 = "@Override\r\npublic void onSuccess(PendingDynamicLinkData _pendingDynamicLinkData){\r\nfinal String _link = _pendingDynamicLinkData != null ? _pendingDynamicLinkData.getLink().toString() : \"\";\r\n%s\r\n}";
                    break;
                }
                break;
            case -760958320:
                if (str.equals("onPatternLockCleared")) {
                    str3 = "@Override\npublic void onCleared() {\n%s\n}";
                    break;
                }
                break;
            case -589155002:
                if (str.equals("FBAdsInterstitial_onAdClicked")) {
                    str3 = "@Override\r\npublic void onAdClicked(Ad ad) {\r\n%s\r\n}";
                    break;
                }
                break;
            case -552637034:
                if (str.equals("onRewardedVideoAdLoaded")) {
                    str3 = "@Override\npublic void onRewardedVideoAdLoaded() {\n%s\n}";
                    break;
                }
                break;
            case -465697286:
                if (str.equals("onRewardedVideoAdOpened")) {
                    str3 = "@Override\npublic void onRewardedVideoAdOpened() {\n%s\n}";
                    break;
                }
                break;
            case -233781414:
                if (str.equals("onPageScrolled")) {
                    str3 = "@Override\npublic void onPageScrolled(int _position, float _positionOffset, int _positionOffsetPixels) {\r\n%s\r\n}";
                    break;
                }
                break;
            case -222902665:
                if (str.equals("onVerificationFailed")) {
                    str3 = "@Override\r\npublic void onVerificationFailed(FirebaseException e) {\r\nfinal String _exception = e.getMessage();\r\n%s\r\n}";
                    break;
                }
                break;
            case -172690726:
                if (str.equals("onGoogleSignIn")) {
                    str3 = "@Override\r\npublic void onComplete(Task<AuthResult> task){\r\nfinal boolean _success = task.isSuccessful();\r\nfinal String _errorMessage = task.getException() != null ? task.getException().getMessage() : \"\";\r\n%s\r\n}";
                    break;
                }
                break;
            case -30415631:
                if (str.equals("onFragmentAdded")) {
                    str3 = "@Override\npublic Fragment getItem(int _position) {\r\n%s\r\nreturn null;\n}";
                    break;
                }
                break;
            case 22340470:
                if (str.equals("onTimeSet")) {
                    str3 = "@Override\npublic void onTimeSet(TimePicker _timePicker, int _hour, int _minute){\n%s\n}";
                    break;
                }
                break;
            case 51638726:
                if (str.equals("onPageChanged")) {
                    str3 = "@Override\npublic void onPageScrollStateChanged(int _scrollState) {\r\n%s\r\n}";
                    break;
                }
                break;
            case 140823751:
                if (str.equals("signInWithPhoneAuthComplete")) {
                    str3 = "@Override\r\npublic void onComplete(Task<AuthResult> task){\r\nfinal boolean _success = task.isSuccessful();\r\nfinal String _errorMessage = task.getException() != null ? task.getException().getMessage() : \"\";\r\n%s\r\n}";
                    break;
                }
                break;
            case 179352223:
                if (str.equals("FBAdsInterstitial_onInterstitialDismissed")) {
                    str3 = "@Override\r\npublic void onInterstitialDismissed(Ad ad) {\r\n%s\r\n}";
                    break;
                }
                break;
            case 230462136:
                if (str.equals("onRatingChanged")) {
                    str3 = "@Override\npublic void onRatingChanged(RatingBar _ratingBar, float _value, boolean _fromUser){\n%s\n}";
                    break;
                }
                break;
            case 267479767:
                if (str.equals("FBAdsInterstitial_onInterstitialDisplayed")) {
                    str3 = "@Override\r\npublic void onInterstitialDisplayed(Ad ad) {\r\n%s\r\n}";
                    break;
                }
                break;
            case 388714441:
                if (str.equals("onPatternLockProgress")) {
                    str3 = "@Override\npublic void onProgress(List<PatternLockView.Dot> _pattern) {\n%s\n}";
                    break;
                }
                break;
            case 631391277:
                if (str.equals("onOptionsItemSelected")) {
                    str3 = "@Override\n\tpublic boolean onOptionsItemSelected(MenuItem item){\nfinal int _id = item.getItemId();\nfinal String _title = (String) item.getTitle();\n%s\nreturn super.onOptionsItemSelected(item);\n}\n";
                    break;
                }
                break;
            case 638018442:
                if (str.equals("FBAdsInterstitial_onLoggingImpression")) {
                    str3 = "@Override\r\npublic void onLoggingImpression(Ad ad) {\r\n%s\r\n}";
                    break;
                }
                break;
            case 780046181:
                if (str.equals("onPatternLockStarted")) {
                    str3 = "@Override\npublic void onStarted() {\n%s\n}";
                    break;
                }
                break;
            case 790347477:
                if (str.equals("onPatternLockComplete")) {
                    str3 = "@Override\npublic void onComplete(List<PatternLockView.Dot> _pattern) {\n%s\n}";
                    break;
                }
                break;
            case 823540209:
                if (str.equals("onTabSelected")) {
                    str3 = "@Override\npublic void onTabSelected(TabLayout.Tab tab) {\r\nfinal int _position = tab.getPosition();\n%s\r\n}";
                    break;
                }
                break;
            case 903210916:
                if (str.equals("onTabReselected")) {
                    str3 = "@Override\npublic void onTabReselected(TabLayout.Tab tab) {\r\nfinal int _position = tab.getPosition();\n%s\r\n}";
                    break;
                }
                break;
            case 948149408:
                if (str.equals("onLetterSelected")) {
                    str3 = "@Override\npublic void onLetterSelected(String _index) {\n%s\n}";
                    break;
                }
                break;
            case 1226449418:
                if (str.equals("onTabUnselected")) {
                    str3 = "@Override\npublic void onTabUnselected(TabLayout.Tab tab) {\r\nfinal int _position = tab.getPosition();\n%s\r\n}";
                    break;
                }
                break;
            case 1359955401:
                if (str.equals("onPageSelected")) {
                    str3 = "@Override\npublic void onPageSelected(int _position) {\r\n%s\r\n}";
                    break;
                }
                break;
            case 1424438342:
                if (str.equals("FBAdsBanner_onAdClicked")) {
                    str3 = "@Override\r\npublic void onAdClicked(Ad ad) {\r\n%s\r\n}";
                    break;
                }
                break;
            case 1490401084:
                if (str.equals("onPrepared")) {
                    str3 = "@Override\npublic void onPrepared(MediaPlayer _mediaPlayer){\n%s\n}";
                    break;
                }
                break;
            case 1530791598:
                if (str.equals("onQueryTextSubmit")) {
                    str3 = "@Override\npublic boolean onQueryTextSubmit(String _charSeq){\n%s\nreturn true;\n}";
                    break;
                }
                break;
            case 1585314250:
                if (str.equals("FBAdsInterstitial_onError")) {
                    str3 = "@Override\r\npublic void onError(Ad ad, AdError adError) {\r\nfinal String _errorMsg = adError.getErrorMessage() != null ? adError.getErrorMessage() : \"\";\r\n%s\r\n}";
                    break;
                }
                break;
            case 1626663686:
                if (str.equals("FBAdsInterstitial_onAdLoaded")) {
                    str3 = "@Override\r\npublic void onAdLoaded(Ad ad) {\r\n%s\r\n}";
                    break;
                }
                break;
            case 1691618310:
                if (str.equals("FBAdsBanner_onAdLoaded")) {
                    str3 = "@Override\r\npublic void onAdLoaded(Ad ad) {\r\n%s\r\n}";
                    break;
                }
                break;
            case 1725593802:
                if (str.equals("FBAdsBanner_onError")) {
                    str3 = "@Override\r\npublic void onError(Ad ad, AdError adError) {\r\nfinal String _errorMsg = adError.getErrorMessage() != null ? adError.getErrorMessage() : \"\";\r\n%s\r\n}";
                    break;
                }
                break;
            case 1834797418:
                if (str.equals("onTabAdded")) {
                    str3 = "@Override\npublic CharSequence getPageTitle(int _position) {\r\n%s\r\nreturn null;\n}";
                    break;
                }
                break;
            case 1887055611:
                if (str.equals("onCompleteRegister")) {
                    str3 = "@Override\r\npublic void onComplete(Task<InstanceIdResult> task) {\r\nfinal boolean _success = task.isSuccessful();\r\nfinal String _token = task.getResult().getToken();\r\nfinal String _errorMessage = task.getException() != null ? task.getException().getMessage() : \"\";\r\n%s\r\n}";
                    break;
                }
                break;
            case 1901942308:
                if (str.equals("onCodeSent")) {
                    str3 = "@Override\r\npublic void onCodeSent(String _verificationId, PhoneAuthProvider.ForceResendingToken _token) {\r\n%s\r\n}";
                    break;
                }
                break;
            case 1921834952:
                if (str.equals("onTimeChanged")) {
                    str3 = "@Override\npublic void onTimeChanged(TimePicker _timePicker, int _hour, int _minute) {\n%s\n}";
                    break;
                }
                break;
            case 2054104968:
                if (str.equals("onScrollChanged")) {
                    str3 = "@Override\npublic void onScrollStateChanged(AbsListView abs, int _scrollState) {\r\n%s\r\n}";
                    break;
                }
                break;
            case 2092419228:
                if (str.equals("onUpdatePasswordComplete")) {
                    str3 = "@Override\r\npublic void onComplete(Task<Void> _param1) {\r\nfinal boolean _success = _param1.isSuccessful();\r\nfinal String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : \"\";\r\n%s\r\n}";
                    break;
                }
                break;
            default:
                return EventsHandler.getEventCode(str, str2);
        }
        return j(str3, str2);
    }

    public static String g(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder();
        switch (str.hashCode()) {
            case -2013506289:
                if (str.equals("OnCompletionListener")) {
                    sb.append(str2);
                    sb.append(".setOnCompletionListener(new MediaPlayer.OnCompletionListener(){\n%s\n});");
                    break;
                }
                break;
            case -1961315671:
                if (str.equals("OnDateSetListener")) {
                    sb.append("public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {\n@Override\npublic Dialog onCreateDialog(Bundle savedInstanceState) {\nfinal Calendar c = Calendar.getInstance();\nint year = c.get(Calendar.YEAR);\nint month = c.get(Calendar.MONTH);\nint day = c.get(Calendar.DAY_OF_MONTH);\nreturn new DatePickerDialog(getActivity(), this, year, month, day);\n}\n%s\r\n}");
                    break;
                }
                break;
            case -1711602262:
                if (str.equals("OnQueryTextListener")) {
                    sb.append(str2);
                    sb.append(".setOnQueryTextListener(new SearchView.OnQueryTextListener(){\n%s\n});");
                    break;
                }
                break;
            case -1683818607:
                if (str.equals("OnVerificationStateChangedListener")) {
                    sb.append(str2);
                    sb.append(" = new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){\r\n%s\r\n};");
                    break;
                }
                break;
            case -1676288384:
                if (str.equals("OnScrollListener")) {
                    sb.append(str2);
                    sb.append(".setOnScrollListener(new AbsListView.OnScrollListener() {\n%s\n});");
                    break;
                }
                break;
            case -1641400634:
                if (str.equals("authsignInWithPhoneAuth")) {
                    sb.append(str2);
                    sb.append("_phoneAuthListener = new OnCompleteListener<AuthResult>() {\r\n%s\r\n};");
                    break;
                }
                break;
            case -1561524051:
                if (str.equals("FragmentStatePagerAdapter")) {
                    sb.append("public class MyFragmentAdapter extends FragmentStatePagerAdapter {\n//this method is deprecated, should migrate to viewpager2.");
                    sb.append("\r\n");
                    sb.append("Context context;");
                    sb.append("\r\n");
                    sb.append("int tabCount;");
                    sb.append("\r\n");
                    sb.append("\r\n");
                    sb.append("public MyFragmentAdapter(Context context, FragmentManager fm, int tabCount) {");
                    sb.append("\r\n");
                    sb.append("super(fm);");
                    sb.append("\r\n");
                    sb.append("this.context = context;");
                    sb.append("\r\n");
                    sb.append("this.tabCount = tabCount;");
                    sb.append("\r\n");
                    sb.append("}");
                    sb.append("\r\n");
                    sb.append("\r\n");
                    sb.append("@Override\npublic int getCount(){\nreturn tabCount;\n}");
                    sb.append("\r\n");
                    sb.append("\r\n");
                    sb.append("%s");
                    sb.append("\r\n");
                    sb.append("\r\n");
                    sb.append("}");
                    break;
                }
                break;
            case -1381186349:
                if (str.equals("OnVideoAdListener")) {
                    sb.append(str2);
                    sb.append("_listener = new RewardedVideoAdListener(){\n%s\n");
                    sb.append("@Override\npublic void onRewardedVideoAdLeftApplication() {\r\n}");
                    sb.append("\r\n");
                    sb.append("\r\n");
                    sb.append("@Override\npublic void onRewardedVideoStarted() {\r\n}");
                    sb.append("\r\n");
                    sb.append("\r\n");
                    sb.append("@Override\n\tpublic void onRewardedVideoCompleted() {\r\n}");
                    sb.append("\r\n");
                    sb.append("};");
                    break;
                }
                break;
            case -1305971158:
                if (str.equals("OnTimeSetListener")) {
                    sb.append(str2);
                    sb.append("_listener = new TimePickerDialog.OnTimeSetListener(){\n%s\n};");
                    break;
                }
                break;
            case -1289633697:
                if (str.equals("OnFailureListener")) {
                    sb.append(str2);
                    sb.append("_onFailureLink = new OnFailureListener() {\r\n%s\r\n};");
                    break;
                }
                break;
            case -922637403:
                if (str.equals("authUpdatePasswordComplete")) {
                    sb.append(str2);
                    sb.append("_updatePasswordListener = new OnCompleteListener<Void>() {\r\n%s\r\n};");
                    break;
                }
                break;
            case -838515240:
                if (str.equals("OnSuccessListener")) {
                    sb.append(str2);
                    sb.append("_onSuccessLink = new OnSuccessListener<PendingDynamicLinkData>() {\r\n%s\r\n};");
                    break;
                }
                break;
            case -758438236:
                if (str.equals("OnGridItemClickListener")) {
                    sb.append(str2);
                    sb.append(".setOnItemClickListener(new AdapterView.OnItemClickListener() {\r\n%s\r\n});");
                    break;
                }
                break;
            case -264740449:
                if (str.equals("OnRecyclerScrollListener")) {
                    sb.append(str2);
                    sb.append(".addOnScrollListener(new RecyclerView.OnScrollListener() {\n%s\n});");
                    break;
                }
                break;
            case -237230832:
                if (str.equals("OnTimeChangeListener")) {
                    sb.append(str2);
                    sb.append(".setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {\n%s\n});");
                    break;
                }
                break;
            case 242988855:
                if (str.equals("authDeleteUserComplete")) {
                    sb.append(str2);
                    sb.append("_deleteUserListener = new OnCompleteListener<Void>() {\r\n%s\r\n};");
                    break;
                }
                break;
            case 261563697:
                if (str.equals("authUpdateProfileComplete")) {
                    sb.append(str2);
                    sb.append("_updateProfileListener = new OnCompleteListener<Void>() {\r\n%s\r\n};");
                    break;
                }
                break;
            case 282753362:
                if (str.equals("OnPageChangeListener")) {
                    sb.append(str2);
                    sb.append(".addOnPageChangeListener(new ViewPager.OnPageChangeListener() {\n%s\n});");
                    break;
                }
                break;
            case 313462557:
                if (str.equals("OnErrorListener")) {
                    sb.append(str2);
                    sb.append(".setOnErrorListener(new MediaPlayer.OnErrorListener(){\n%s\n});");
                    break;
                }
                break;
            case 370440007:
                if (str.equals("authEmailVerificationSent")) {
                    sb.append(str2);
                    sb.append("_emailVerificationSentListener = new OnCompleteListener<Void>() {\r\n%s\r\n};");
                    break;
                }
                break;
            case 413352932:
                if (str.equals("authUpdateEmailComplete")) {
                    sb.append(str2);
                    sb.append("_updateEmailListener = new OnCompleteListener<Void>() {\r\n%s\r\n};");
                    break;
                }
                break;
            case 554934320:
                if (str.equals("OnPreparedListener")) {
                    sb.append(str2);
                    sb.append(".setOnPreparedListener(new MediaPlayer.OnPreparedListener(){\n%s\n});");
                    break;
                }
                break;
            case 568167393:
                if (str.equals("OnNavigationItemSelected")) {
                    sb.append(str2);
                    sb.append(".setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {\n%s\n});");
                    break;
                }
                break;
            case 936528276:
                if (str.equals("OnLetterSelectedListener")) {
                    sb.append(str2);
                    sb.append(".setOnLetterSelectedListener(new WaveSideBar.OnLetterSelectedListener() {\n%s\n});");
                    break;
                }
                break;
            case 1078577634:
                if (str.equals("FBAdsInterstitial_InterstitialAdListener")) {
                    sb.append(str2);
                    sb.append("_InterstitialAdListener = new InterstitialAdListener(){\r\n%s\r\n};");
                    break;
                }
                break;
            case 1207833179:
                if (str.equals("OnRatingBarChangeListener")) {
                    sb.append(str2);
                    sb.append(".setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){\n%s\n});");
                    break;
                }
                break;
            case 1295135141:
                if (str.equals("OnTabSelectedListener")) {
                    sb.append(str2);
                    sb.append(".addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {\r\n%s\r\n});");
                    break;
                }
                break;
            case 1315710001:
                if (str.equals("OnDateChangeListener")) {
                    sb.append("Calendar _calendar = Calendar.getInstance();\n");
                    sb.append(str2);
                    sb.append(".init(_calendar.get(Calendar.YEAR),\n_calendar.get(Calendar.MONTH), _calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {\n%s\n});");
                    break;
                }
                break;
            case 1448799876:
                if (str.equals("OnCompleteListenerFCM")) {
                    sb.append(str2);
                    sb.append("_onCompleteListener = new OnCompleteListener<InstanceIdResult>() {\r\n%s\r\n};");
                    break;
                }
                break;
            case 1750173396:
                if (str.equals("PatternLockViewListener")) {
                    sb.append(str2);
                    sb.append(".addPatternLockListener(new PatternLockViewListener() {\n%s\n});");
                    break;
                }
                break;
            case 1995891990:
                if (str.equals("FBAdsBanner_AdListener")) {
                    sb.append(str2);
                    sb.append("_AdListener = new AdListener(){\r\n%s\r\n};");
                    break;
                }
                break;
            case 2058196360:
                if (str.equals("OnGridItemLongClickListener")) {
                    sb.append(str2);
                    sb.append(".setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {\r\n%s\r\n});");
                    break;
                }
                break;
            case 2139489583:
                if (str.equals("googleSignInListener")) {
                    sb.append(str2);
                    sb.append("_googleSignInListener = new OnCompleteListener<AuthResult>() {\r\n%s\r\n};");
                    break;
                }
                break;
            default:
                return EventsHandler.getListenerCode(str, str2, str3);
        }
        return j(sb.toString(), str3);
    }

    public static String h(String str) {
        switch (str.hashCode()) {
            case -2135028390:
                if (str.equals("onUpdateProfileComplete")) {
                    return "%b.success %s.errorMessage";
                }
                break;
            case -2026152080:
                if (str.equals("onEmailVerificationSent")) {
                    return "%b.success %s.errorMessage";
                }
                break;
            case -1927178453:
                if (str.equals("onScrolled")) {
                    return "%d.firstVisibleItem %d.visibleItemCount %d.totalItemCount";
                }
                break;
            case -1881852985:
                if (str.equals("onDateChanged")) {
                    return "%d.year %d.month %d.day";
                }
                break;
            case -1764593907:
                if (str.equals("onRewarded")) {
                    return "%d.rewardItem";
                }
                break;
            case -1744369339:
                if (str.equals("onFailureLink")) {
                    return "%s.errorMessage";
                }
                break;
            case -1615009874:
                if (str.equals("onDeleteUserComplete")) {
                    return "%b.success %s.errorMessage";
                }
                break;
            case -1576048703:
                if (str.equals("onNavigationItemSelected")) {
                    return "%d.itemId";
                }
                break;
            case -1521795729:
                if (str.equals("onRewardedVideoAdFailedToLoad")) {
                    return "%d.errorCode";
                }
                break;
            case -1515385099:
                if (str.equals("onDateSet")) {
                    return "%m.datepicker.datePicker %d.year %d.month %d.day";
                }
                break;
            case -1478332706:
                if (str.equals("onQueryTextChanged")) {
                    return "%s.charSeq";
                }
                break;
            case -1350032819:
                if (str.equals("onUpdateEmailComplete")) {
                    return "%b.success %s.errorMessage";
                }
                break;
            case -1349867671:
                if (str.equals("onError")) {
                    return "%d.what %d.extra";
                }
                break;
            case -1340743215:
                if (str.equals("onVerificationCompleted")) {
                    return "%m.PhoneAuthCredential.credential";
                }
                break;
            case -1321851767:
                if (str.equals("onRecyclerScrollChanged")) {
                    return "%d.scrollState";
                }
                break;
            case -893469302:
                if (str.equals("onRecyclerScrolled")) {
                    return "%d.offsetX %d.offsetY";
                }
                break;
            case -789433538:
                if (str.equals("onSuccessLink")) {
                    return "%s.link";
                }
                break;
            case -233781414:
                if (str.equals("onPageScrolled")) {
                    return "%d.position %d.positionOffset %d.positionOffsetPixels";
                }
                break;
            case -222902665:
                if (str.equals("onVerificationFailed")) {
                    return "%s.exception";
                }
                break;
            case -172690726:
                if (str.equals("onGoogleSignIn")) {
                    return "%b.success %s.errorMessage";
                }
                break;
            case -108704388:
                if (str.equals("onAccountPicker")) {
                    return "%m.GoogleSignInAccount.task";
                }
                break;
            case -30415631:
                if (str.equals("onFragmentAdded")) {
                    return "%d.position";
                }
                break;
            case 22340470:
                if (str.equals("onTimeSet")) {
                    return "%d.hour %d.minute";
                }
                break;
            case 51638726:
                if (str.equals("onPageChanged")) {
                    return "%d.scrollState";
                }
                break;
            case 140823751:
                if (str.equals("signInWithPhoneAuthComplete")) {
                    return "%b.success %s.errorMessage";
                }
                break;
            case 230462136:
                if (str.equals("onRatingChanged")) {
                    return "%d.value";
                }
                break;
            case 388714441:
                if (str.equals("onPatternLockProgress")) {
                    return "%m.listStr.pattern";
                }
                break;
            case 631391277:
                if (str.equals("onOptionsItemSelected")) {
                    return "%d.id %s.title";
                }
                break;
            case 790347477:
                if (str.equals("onPatternLockComplete")) {
                    return "%m.listStr.pattern";
                }
                break;
            case 823540209:
                if (str.equals("onTabSelected")) {
                    return "%d.position";
                }
                break;
            case 903210916:
                if (str.equals("onTabReselected")) {
                    return "%d.position";
                }
                break;
            case 948149408:
                if (str.equals("onLetterSelected")) {
                    return "%s.index";
                }
                break;
            case 1226449418:
                if (str.equals("onTabUnselected")) {
                    return "%d.position";
                }
                break;
            case 1359955401:
                if (str.equals("onPageSelected")) {
                    return "%m.listMap.data %d.position";
                }
                break;
            case 1530791598:
                if (str.equals("onQueryTextSubmit")) {
                    return "%s.charSeq";
                }
                break;
            case 1585314250:
                if (str.equals("FBAdsInterstitial_onError")) {
                    return "%s.errorMsg";
                }
                break;
            case 1725593802:
                if (str.equals("FBAdsBanner_onError")) {
                    return "%s.errorMsg";
                }
                break;
            case 1834797418:
                if (str.equals("onTabAdded")) {
                    return "%d.position";
                }
                break;
            case 1887055611:
                if (str.equals("onCompleteRegister")) {
                    return "%b.success %s.token %s.errorMessage";
                }
                break;
            case 1901942308:
                if (str.equals("onCodeSent")) {
                    return "%s.verificationId %m.FirebasePhoneAuth.token";
                }
                break;
            case 1921834952:
                if (str.equals("onTimeChanged")) {
                    return "%d.hour %d.minute";
                }
                break;
            case 2054104968:
                if (str.equals("onScrollChanged")) {
                    return "%d.scrollState";
                }
                break;
            case 2092419228:
                if (str.equals("onUpdatePasswordComplete")) {
                    return "%b.success %s.errorMessage";
                }
                break;
            default:
                return EventsHandler.getBlocks(str);
        }
        return "";
    }

    public static void h(Gx gx, ArrayList<String> arrayList) {
        if (gx.a("FragmentAdapter")) {
            arrayList.add("onTabAdded");
            arrayList.add("onFragmentAdded");
        }
        if (gx.a("TimePickerDialog")) {
            arrayList.add("onTimeSet");
        }
        if (gx.a("DatePickerDialog")) {
            arrayList.add("onDateSet");
        }
        if (gx.a("RewardedVideoAd")) {
            arrayList.add("onRewarded");
            arrayList.add("onRewardedVideoAdLoaded");
            arrayList.add("onRewardedVideoAdFailedToLoad");
            arrayList.add("onRewardedVideoAdOpened");
            arrayList.add("onRewardedVideoAdClosed");
        }
        if (gx.a("FirebaseAuth")) {
            arrayList.add("onUpdateEmailComplete");
            arrayList.add("onUpdatePasswordComplete");
            arrayList.add("onEmailVerificationSent");
            arrayList.add("onDeleteUserComplete");
            arrayList.add("signInWithPhoneAuthComplete");
            arrayList.add("onUpdateProfileComplete");
            arrayList.add("onGoogleSignIn");
        }
        if (gx.a("FirebasePhoneAuth")) {
            arrayList.add("onVerificationCompleted");
            arrayList.add("onVerificationFailed");
            arrayList.add("onCodeSent");
        }
        if (gx.a("FirebaseDynamicLink")) {
            arrayList.add("onSuccessLink");
            arrayList.add("onFailureLink");
        }
        if (gx.a("FirebaseCloudMessage")) {
            arrayList.add("onCompleteRegister");
        }
        if (gx.a("FBAdsBanner")) {
            arrayList.add("FBAdsBanner_onError");
            arrayList.add("FBAdsBanner_onAdLoaded");
            arrayList.add("FBAdsBanner_onAdClicked");
            arrayList.add("FBAdsBanner_onLoggingImpression");
        }
        if (gx.a("FBAdsInterstitial")) {
            arrayList.add("FBAdsInterstitial_onError");
            arrayList.add("FBAdsInterstitial_onAdLoaded");
            arrayList.add("FBAdsInterstitial_onAdClicked");
            arrayList.add("FBAdsInterstitial_onLoggingImpression");
            arrayList.add("FBAdsInterstitial_onInterstitialDisplayed");
            arrayList.add("FBAdsInterstitial_onInterstitialDismissed");
        }
        if (gx.a("FirebaseGoogleLogin")) {
            arrayList.add("onAccountPicker");
            arrayList.add("onAccountPickerCancelled");
        }
        EventsHandler.addEvents(gx, arrayList);
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

    public static String j(String str, String str2) {
        if (str.isEmpty()) {
            return "";
        }
        return String.format(str, str2);
    }
}
