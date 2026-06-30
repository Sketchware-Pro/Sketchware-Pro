package mod.agus.jcoderz.beans;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

import pro.sketchware.R;

public class ViewBeans {

    // ─── ORIGINAL WIDGETS (19–48) ────────────────────────────────────────────
    public static final int VIEW_TYPE_WIDGET_RADIOBUTTON = 19;
    public static final int VIEW_TYPE_WIDGET_RATINGBAR = 20;
    public static final int VIEW_TYPE_WIDGET_VIDEOVIEW = 21;
    public static final int VIEW_TYPE_WIDGET_SEARCHVIEW = 22;
    public static final int VIEW_TYPE_WIDGET_AUTOCOMPLETETEXTVIEW = 23;
    public static final int VIEW_TYPE_WIDGET_MULTIAUTOCOMPLETETEXTVIEW = 24;
    public static final int VIEW_TYPE_WIDGET_GRIDVIEW = 25;
    public static final int VIEW_TYPE_WIDGET_ANALOGCLOCK = 26;
    public static final int VIEW_TYPE_WIDGET_DATEPICKER = 27;
    public static final int VIEW_TYPE_WIDGET_TIMEPICKER = 28;
    public static final int VIEW_TYPE_WIDGET_DIGITALCLOCK = 29;
    public static final int VIEW_TYPE_LAYOUT_TABLAYOUT = 30;
    public static final int VIEW_TYPE_LAYOUT_VIEWPAGER = 31;
    public static final int VIEW_TYPE_LAYOUT_BOTTOMNAVIGATIONVIEW = 32;
    public static final int VIEW_TYPE_WIDGET_BADGEVIEW = 33;
    public static final int VIEW_TYPE_WIDGET_PATTERNLOCKVIEW = 34;
    public static final int VIEW_TYPE_WIDGET_WAVESIDEBAR = 35;
    public static final int VIEW_TYPE_LAYOUT_CARDVIEW = 36;
    public static final int VIEW_TYPE_LAYOUT_COLLAPSINGTOOLBARLAYOUT = 37;
    public static final int VIEW_TYPE_LAYOUT_TEXTINPUTLAYOUT = 38;
    public static final int VIEW_TYPE_LAYOUT_SWIPEREFRESHLAYOUT = 39;
    public static final int VIEW_TYPE_LAYOUT_RADIOGROUP = 40;
    public static final int VIEW_TYPE_WIDGET_MATERIALBUTTON = 41;
    public static final int VIEW_TYPE_WIDGET_SIGNINBUTTON = 42;
    public static final int VIEW_TYPE_WIDGET_CIRCLEIMAGEVIEW = 43;
    public static final int VIEW_TYPE_WIDGET_LOTTIEANIMATIONVIEW = 44;
    public static final int VIEW_TYPE_WIDGET_YOUTUBEPLAYERVIEW = 45;
    public static final int VIEW_TYPE_WIDGET_OTPVIEW = 46;
    public static final int VIEW_TYPE_WIDGET_CODEVIEW = 47;
    public static final int VIEW_TYPE_WIDGET_RECYCLERVIEW = 48;

    // ─── MATERIAL / EXTENDED BUTTONS (49–56) ─────────────────────────────────
    public static final int VIEW_TYPE_WIDGET_EXTENDEDfab = 49;
    public static final int VIEW_TYPE_WIDGET_FLOATINGACTIONBUTTON = 50;
    public static final int VIEW_TYPE_WIDGET_CHIP = 51;
    public static final int VIEW_TYPE_WIDGET_CHIPGROUP = 52;
    public static final int VIEW_TYPE_WIDGET_TOGGLEBUTTON = 53;
    public static final int VIEW_TYPE_WIDGET_MATERIALSWITCH = 54;
    public static final int VIEW_TYPE_WIDGET_MATERIALTEXTVIEW = 55;
    public static final int VIEW_TYPE_WIDGET_SHAPEABLEIMAGEVIEW = 56;

    // ─── INPUTS & SLIDERS (57–65) ─────────────────────────────────────────────
    public static final int VIEW_TYPE_WIDGET_SLIDER = 57;
    public static final int VIEW_TYPE_WIDGET_RANGESLIDER = 58;
    public static final int VIEW_TYPE_WIDGET_NUMBERPICKER = 59;
    public static final int VIEW_TYPE_WIDGET_AUTOCOMPLETETEXTVIEWDROPDOWN = 60;
    public static final int VIEW_TYPE_WIDGET_EXPOSEDDROPDOWNMENU = 61;
    public static final int VIEW_TYPE_WIDGET_COLORPICKER = 62;
    public static final int VIEW_TYPE_WIDGET_PINVIEW = 63;
    public static final int VIEW_TYPE_WIDGET_SIGNATUREVIEW = 64;
    public static final int VIEW_TYPE_WIDGET_TWOWAYSEEKBAR = 65;

    // ─── PROGRESS & INDICATORS (66–72) ───────────────────────────────────────
    public static final int VIEW_TYPE_WIDGET_CIRCULARPROGRESSBAR = 66;
    public static final int VIEW_TYPE_WIDGET_LINEARPROGRESSINDICATOR = 67;
    public static final int VIEW_TYPE_WIDGET_CIRCULARPROGRESSINDICATOR = 68;
    public static final int VIEW_TYPE_WIDGET_DOTSINDICATOR = 69;
    public static final int VIEW_TYPE_WIDGET_SPRINGLEVELBAR = 70;
    public static final int VIEW_TYPE_WIDGET_ARCPROGRESSBAR = 71;
    public static final int VIEW_TYPE_WIDGET_STEPVIEW = 72;

    // ─── NAVIGATION (73–81) ──────────────────────────────────────────────────
    public static final int VIEW_TYPE_LAYOUT_NAVIGATIONVIEW = 73;
    public static final int VIEW_TYPE_LAYOUT_DRAWERLAYOUT = 74;
    public static final int VIEW_TYPE_LAYOUT_NAVIGATIONRAILVIEW = 75;
    public static final int VIEW_TYPE_LAYOUT_TOOLBAR = 76;
    public static final int VIEW_TYPE_LAYOUT_APPBARLAYOUT = 77;
    public static final int VIEW_TYPE_LAYOUT_COORDINATORLAYOUT = 78;
    public static final int VIEW_TYPE_LAYOUT_NESTEDSCROLLVIEW = 79;
    public static final int VIEW_TYPE_LAYOUT_HORIZONTALSCROLLVIEW = 80;
    public static final int VIEW_TYPE_LAYOUT_VIEWPAGER2 = 81;

    // ─── LISTS & GRIDS (82–87) ───────────────────────────────────────────────
    public static final int VIEW_TYPE_WIDGET_STAGGEREDGRIDVIEW = 82;
    public static final int VIEW_TYPE_WIDGET_EXPANDABLELISTVIEW = 83;
    public static final int VIEW_TYPE_WIDGET_LISTVIEW = 84;
    public static final int VIEW_TYPE_WIDGET_SPINNER = 85;
    public static final int VIEW_TYPE_WIDGET_FLEXBOXLAYOUT = 86;
    public static final int VIEW_TYPE_WIDGET_FLOWLAYOUT = 87;

    // ─── IMAGE & MEDIA (88–97) ───────────────────────────────────────────────
    public static final int VIEW_TYPE_WIDGET_GLIDEIMAGEVIEW = 88;
    public static final int VIEW_TYPE_WIDGET_PICASSOIMAGEVIEW = 89;
    public static final int VIEW_TYPE_WIDGET_GIFIMAGEVIEW = 90;
    public static final int VIEW_TYPE_WIDGET_PHOTOVIEW = 91;
    public static final int VIEW_TYPE_WIDGET_ZOOMABLEIMAGEVIEW = 92;
    public static final int VIEW_TYPE_WIDGET_EXOPLAYERVIEW = 93;
    public static final int VIEW_TYPE_WIDGET_MAPVIEW = 94;
    public static final int VIEW_TYPE_WIDGET_QRCODEVIEW = 95;
    public static final int VIEW_TYPE_WIDGET_BARCODESCANVIEW = 96;
    public static final int VIEW_TYPE_WIDGET_CAMERAXPREVIEWVIEW = 97;

    // ─── CHARTS & DATA VIZ (98–107) ──────────────────────────────────────────
    public static final int VIEW_TYPE_WIDGET_LINECHART = 98;
    public static final int VIEW_TYPE_WIDGET_BARCHART = 99;
    public static final int VIEW_TYPE_WIDGET_PIECHART = 100;
    public static final int VIEW_TYPE_WIDGET_RADARCHART = 101;
    public static final int VIEW_TYPE_WIDGET_BUBBLECHART = 102;
    public static final int VIEW_TYPE_WIDGET_SCATTERCHART = 103;
    public static final int VIEW_TYPE_WIDGET_COMBINEDCHART = 104;
    public static final int VIEW_TYPE_WIDGET_CANDLESTICKCHART = 105;
    public static final int VIEW_TYPE_WIDGET_HORIZONTALBARCHART = 106;
    public static final int VIEW_TYPE_WIDGET_SPARKLINEVIEW = 107;

    // ─── DIALOGS & OVERLAYS (108–114) ────────────────────────────────────────
    public static final int VIEW_TYPE_WIDGET_BOTTOMSHEETDIALOG = 108;
    public static final int VIEW_TYPE_WIDGET_MATERIALDIALOG = 109;
    public static final int VIEW_TYPE_WIDGET_SNACKBAR = 110;
    public static final int VIEW_TYPE_WIDGET_TOOLTIP = 111;
    public static final int VIEW_TYPE_WIDGET_POPUPMENU = 112;
    public static final int VIEW_TYPE_WIDGET_CONTEXTMENU = 113;
    public static final int VIEW_TYPE_WIDGET_SHOWCASEVIEW = 114;

    // ─── TEXT DISPLAY (115–122) ───────────────────────────────────────────────
    public static final int VIEW_TYPE_WIDGET_MARQUEETEXTVIEW = 115;
    public static final int VIEW_TYPE_WIDGET_EXPANDABLETEXTVIEW = 116;
    public static final int VIEW_TYPE_WIDGET_HTMLTEXTVIEW = 117;
    public static final int VIEW_TYPE_WIDGET_AUTOSIZETEXTVIEW = 118;
    public static final int VIEW_TYPE_WIDGET_TYPEWRITERTEXTVIEW = 119;
    public static final int VIEW_TYPE_WIDGET_BADGEDTEXTVIEW = 120;
    public static final int VIEW_TYPE_WIDGET_COUNTUPTEXTVIEW = 121;
    public static final int VIEW_TYPE_WIDGET_COUNTDOWNTIMER = 122;

    // ─── ANIMATION & EFFECTS (123–131) ───────────────────────────────────────
    public static final int VIEW_TYPE_WIDGET_RIVE = 123;
    public static final int VIEW_TYPE_WIDGET_DOTLOTTIEVIEW = 124;
    public static final int VIEW_TYPE_WIDGET_CONFETTIVIEW = 125;
    public static final int VIEW_TYPE_WIDGET_SHIMMERFRAMELAYOUT = 126;
    public static final int VIEW_TYPE_WIDGET_SKELETONVIEW = 127;
    public static final int VIEW_TYPE_WIDGET_PARTICLEVIEW = 128;
    public static final int VIEW_TYPE_WIDGET_GLPARTICLEVIEW = 129;
    public static final int VIEW_TYPE_WIDGET_RIPPLEBACKGROUND = 130;
    public static final int VIEW_TYPE_WIDGET_PULSATINGVIEW = 131;

    // ─── SWIPE & GESTURE (132–138) ───────────────────────────────────────────
    public static final int VIEW_TYPE_WIDGET_SWIPEREVEALAYOUT = 132;
    public static final int VIEW_TYPE_WIDGET_SWIPECARDVIEW = 133;
    public static final int VIEW_TYPE_WIDGET_DRAGGABLEVIEW = 134;
    public static final int VIEW_TYPE_WIDGET_PINCHZOOMVIEW = 135;
    public static final int VIEW_TYPE_WIDGET_SWIPEMENULISTVIEW = 136;
    public static final int VIEW_TYPE_WIDGET_SLIDETOACTIONVIEW = 137;
    public static final int VIEW_TYPE_WIDGET_DRAGTOSORTVIEW = 138;

    // ─── CALENDAR & DATE / TIME (139–144) ────────────────────────────────────
    public static final int VIEW_TYPE_WIDGET_MATERIALCALENDARVIEW = 139;
    public static final int VIEW_TYPE_WIDGET_COMPACTCALENDARVIEW = 140;
    public static final int VIEW_TYPE_WIDGET_HEATMAPVIEW = 141;
    public static final int VIEW_TYPE_WIDGET_CLOCKVIEW = 142;
    public static final int VIEW_TYPE_WIDGET_FLIPCLOCKVIEW = 143;
    public static final int VIEW_TYPE_WIDGET_COUNTDOWNVIEW = 144;

    // ─── TAGS & BADGES (145–149) ──────────────────────────────────────────────
    public static final int VIEW_TYPE_WIDGET_TAGSVIEW = 145;
    public static final int VIEW_TYPE_WIDGET_TAGCLOUD = 146;
    public static final int VIEW_TYPE_WIDGET_FLOWTAGLAYOUT = 147;
    public static final int VIEW_TYPE_WIDGET_MATERIALBADGE = 148;
    public static final int VIEW_TYPE_WIDGET_NOTIFICATIONBADGE = 149;

    // ─── CARDS & PANELS (150–157) ────────────────────────────────────────────
    public static final int VIEW_TYPE_WIDGET_EXPANDABLECARDVIEW = 150;
    public static final int VIEW_TYPE_WIDGET_FLIPCARDVIEW = 151;
    public static final int VIEW_TYPE_WIDGET_ACCORDIONVIEW = 152;
    public static final int VIEW_TYPE_WIDGET_MATERIALCARDVIEW = 153;
    public static final int VIEW_TYPE_WIDGET_BANNERWIDGET = 154;
    public static final int VIEW_TYPE_WIDGET_ALERTBANNER = 155;
    public static final int VIEW_TYPE_WIDGET_INFOBUBBLE = 156;
    public static final int VIEW_TYPE_WIDGET_RIBBONVIEW = 157;

    // ─── LAYOUT CONTAINERS (158–167) ─────────────────────────────────────────
    public static final int VIEW_TYPE_LAYOUT_CONSTRAINTLAYOUT = 158;
    public static final int VIEW_TYPE_LAYOUT_MOTIONLAYOUT = 159;
    public static final int VIEW_TYPE_LAYOUT_GRIDLAYOUT = 160;
    public static final int VIEW_TYPE_LAYOUT_TABLELAYOUT = 161;
    public static final int VIEW_TYPE_LAYOUT_TABLEROW = 162;
    public static final int VIEW_TYPE_LAYOUT_FRAMELAYOUT = 163;
    public static final int VIEW_TYPE_LAYOUT_RELATIVELAYOUT = 164;
    public static final int VIEW_TYPE_LAYOUT_LINEARLAYOUT_H = 165;
    public static final int VIEW_TYPE_LAYOUT_LINEARLAYOUT_V = 166;
    public static final int VIEW_TYPE_LAYOUT_PERCENTRELATIVELAYOUT = 167;

    // ─── WEB & CODE (168–172) ────────────────────────────────────────────────
    public static final int VIEW_TYPE_WIDGET_WEBVIEW = 168;
    public static final int VIEW_TYPE_WIDGET_MARKDOWNVIEW = 169;
    public static final int VIEW_TYPE_WIDGET_SYNTAXHIGHLIGHTVIEW = 170;
    public static final int VIEW_TYPE_WIDGET_HTMLEDITORVIEW = 171;
    public static final int VIEW_TYPE_WIDGET_JSONVIEW = 172;

    // ─── SOCIAL & PAYMENTS (173–178) ─────────────────────────────────────────
    public static final int VIEW_TYPE_WIDGET_FACEBOOKSIGNINBUTTON = 173;
    public static final int VIEW_TYPE_WIDGET_TWITTERSIGNINBUTTON = 174;
    public static final int VIEW_TYPE_WIDGET_GPAYSBUTTON = 175;
    public static final int VIEW_TYPE_WIDGET_PAYTMBUTTON = 176;
    public static final int VIEW_TYPE_WIDGET_SOCIALSHAREVIEW = 177;
    public static final int VIEW_TYPE_WIDGET_SHARESHEET = 178;

    // ─── MISC UTILITY WIDGETS (179–190) ──────────────────────────────────────
    public static final int VIEW_TYPE_WIDGET_COLORPICKERPALETTE = 179;
    public static final int VIEW_TYPE_WIDGET_FILEPICKERVIEW = 180;
    public static final int VIEW_TYPE_WIDGET_IMAGECROPVIEW = 181;
    public static final int VIEW_TYPE_WIDGET_IMAGESLIDER = 182;
    public static final int VIEW_TYPE_WIDGET_BANNERSLIDER = 183;
    public static final int VIEW_TYPE_WIDGET_INFINITECYCLERVIEW = 184;
    public static final int VIEW_TYPE_WIDGET_PARALLAXSCROLLVIEW = 185;
    public static final int VIEW_TYPE_WIDGET_STICKYHEADERLISTVIEW = 186;
    public static final int VIEW_TYPE_WIDGET_INDEXSCROLLBAR = 187;
    public static final int VIEW_TYPE_WIDGET_ALPHABETINDEXVIEW = 188;
    public static final int VIEW_TYPE_WIDGET_EMOJIPICKERWIDGET = 189;
    public static final int VIEW_TYPE_WIDGET_AVATARVIEW = 190;

    /**
     * Map that stores both a view's type and type name.
     */
    static BiMap<Integer, String> views = new ImmutableBiMap.Builder<Integer, String>()
            // ── Original ──────────────────────────────────────────────────────
            .put(VIEW_TYPE_WIDGET_RADIOBUTTON, "RadioButton")
            .put(VIEW_TYPE_WIDGET_RATINGBAR, "RatingBar")
            .put(VIEW_TYPE_WIDGET_VIDEOVIEW, "VideoView")
            .put(VIEW_TYPE_WIDGET_SEARCHVIEW, "SearchView")
            .put(VIEW_TYPE_WIDGET_AUTOCOMPLETETEXTVIEW, "AutoCompleteTextView")
            .put(VIEW_TYPE_WIDGET_MULTIAUTOCOMPLETETEXTVIEW, "MultiAutoCompleteTextView")
            .put(VIEW_TYPE_WIDGET_GRIDVIEW, "GridView")
            .put(VIEW_TYPE_WIDGET_ANALOGCLOCK, "AnalogClock")
            .put(VIEW_TYPE_WIDGET_DATEPICKER, "DatePicker")
            .put(VIEW_TYPE_WIDGET_TIMEPICKER, "TimePicker")
            .put(VIEW_TYPE_WIDGET_DIGITALCLOCK, "DigitalClock")
            .put(VIEW_TYPE_LAYOUT_TABLAYOUT, "TabLayout")
            .put(VIEW_TYPE_LAYOUT_VIEWPAGER, "ViewPager")
            .put(VIEW_TYPE_LAYOUT_BOTTOMNAVIGATIONVIEW, "BottomNavigationView")
            .put(VIEW_TYPE_WIDGET_BADGEVIEW, "BadgeView")
            .put(VIEW_TYPE_WIDGET_PATTERNLOCKVIEW, "PatternLockView")
            .put(VIEW_TYPE_WIDGET_WAVESIDEBAR, "WaveSideBar")
            .put(VIEW_TYPE_LAYOUT_CARDVIEW, "CardView")
            .put(VIEW_TYPE_LAYOUT_COLLAPSINGTOOLBARLAYOUT, "CollapsingToolbarLayout")
            .put(VIEW_TYPE_LAYOUT_TEXTINPUTLAYOUT, "TextInputLayout")
            .put(VIEW_TYPE_LAYOUT_SWIPEREFRESHLAYOUT, "SwipeRefreshLayout")
            .put(VIEW_TYPE_LAYOUT_RADIOGROUP, "RadioGroup")
            .put(VIEW_TYPE_WIDGET_MATERIALBUTTON, "MaterialButton")
            .put(VIEW_TYPE_WIDGET_SIGNINBUTTON, "SignInButton")
            .put(VIEW_TYPE_WIDGET_CIRCLEIMAGEVIEW, "CircleImageView")
            .put(VIEW_TYPE_WIDGET_LOTTIEANIMATIONVIEW, "LottieAnimationView")
            .put(VIEW_TYPE_WIDGET_YOUTUBEPLAYERVIEW, "YoutubePlayerView")
            .put(VIEW_TYPE_WIDGET_OTPVIEW, "OTPView")
            .put(VIEW_TYPE_WIDGET_CODEVIEW, "CodeView")
            .put(VIEW_TYPE_WIDGET_RECYCLERVIEW, "RecyclerView")
            // ── Material / Extended Buttons ───────────────────────────────────
            .put(VIEW_TYPE_WIDGET_EXTENDEDfab, "ExtendedFloatingActionButton")
            .put(VIEW_TYPE_WIDGET_FLOATINGACTIONBUTTON, "FloatingActionButton")
            .put(VIEW_TYPE_WIDGET_CHIP, "Chip")
            .put(VIEW_TYPE_WIDGET_CHIPGROUP, "ChipGroup")
            .put(VIEW_TYPE_WIDGET_TOGGLEBUTTON, "ToggleButton")
            .put(VIEW_TYPE_WIDGET_MATERIALSWITCH, "MaterialSwitch")
            .put(VIEW_TYPE_WIDGET_MATERIALTEXTVIEW, "MaterialTextView")
            .put(VIEW_TYPE_WIDGET_SHAPEABLEIMAGEVIEW, "ShapeableImageView")
            // ── Inputs & Sliders ──────────────────────────────────────────────
            .put(VIEW_TYPE_WIDGET_SLIDER, "Slider")
            .put(VIEW_TYPE_WIDGET_RANGESLIDER, "RangeSlider")
            .put(VIEW_TYPE_WIDGET_NUMBERPICKER, "NumberPicker")
            .put(VIEW_TYPE_WIDGET_AUTOCOMPLETETEXTVIEWDROPDOWN, "AutoCompleteTextViewDropdown")
            .put(VIEW_TYPE_WIDGET_EXPOSEDDROPDOWNMENU, "ExposedDropdownMenu")
            .put(VIEW_TYPE_WIDGET_COLORPICKER, "ColorPicker")
            .put(VIEW_TYPE_WIDGET_PINVIEW, "PinView")
            .put(VIEW_TYPE_WIDGET_SIGNATUREVIEW, "SignatureView")
            .put(VIEW_TYPE_WIDGET_TWOWAYSEEKBAR, "TwoWaySeekBar")
            // ── Progress & Indicators ─────────────────────────────────────────
            .put(VIEW_TYPE_WIDGET_CIRCULARPROGRESSBAR, "CircularProgressBar")
            .put(VIEW_TYPE_WIDGET_LINEARPROGRESSINDICATOR, "LinearProgressIndicator")
            .put(VIEW_TYPE_WIDGET_CIRCULARPROGRESSINDICATOR, "CircularProgressIndicator")
            .put(VIEW_TYPE_WIDGET_DOTSINDICATOR, "DotsIndicator")
            .put(VIEW_TYPE_WIDGET_SPRINGLEVELBAR, "SpringLevelBar")
            .put(VIEW_TYPE_WIDGET_ARCPROGRESSBAR, "ArcProgressBar")
            .put(VIEW_TYPE_WIDGET_STEPVIEW, "StepView")
            // ── Navigation ────────────────────────────────────────────────────
            .put(VIEW_TYPE_LAYOUT_NAVIGATIONVIEW, "NavigationView")
            .put(VIEW_TYPE_LAYOUT_DRAWERLAYOUT, "DrawerLayout")
            .put(VIEW_TYPE_LAYOUT_NAVIGATIONRAILVIEW, "NavigationRailView")
            .put(VIEW_TYPE_LAYOUT_TOOLBAR, "Toolbar")
            .put(VIEW_TYPE_LAYOUT_APPBARLAYOUT, "AppBarLayout")
            .put(VIEW_TYPE_LAYOUT_COORDINATORLAYOUT, "CoordinatorLayout")
            .put(VIEW_TYPE_LAYOUT_NESTEDSCROLLVIEW, "NestedScrollView")
            .put(VIEW_TYPE_LAYOUT_HORIZONTALSCROLLVIEW, "HorizontalScrollView")
            .put(VIEW_TYPE_LAYOUT_VIEWPAGER2, "ViewPager2")
            // ── Lists & Grids ─────────────────────────────────────────────────
            .put(VIEW_TYPE_WIDGET_STAGGEREDGRIDVIEW, "StaggeredGridView")
            .put(VIEW_TYPE_WIDGET_EXPANDABLELISTVIEW, "ExpandableListView")
            .put(VIEW_TYPE_WIDGET_LISTVIEW, "ListView")
            .put(VIEW_TYPE_WIDGET_SPINNER, "Spinner")
            .put(VIEW_TYPE_WIDGET_FLEXBOXLAYOUT, "FlexboxLayout")
            .put(VIEW_TYPE_WIDGET_FLOWLAYOUT, "FlowLayout")
            // ── Image & Media ─────────────────────────────────────────────────
            .put(VIEW_TYPE_WIDGET_GLIDEIMAGEVIEW, "GlideImageView")
            .put(VIEW_TYPE_WIDGET_PICASSOIMAGEVIEW, "PicassoImageView")
            .put(VIEW_TYPE_WIDGET_GIFIMAGEVIEW, "GifImageView")
            .put(VIEW_TYPE_WIDGET_PHOTOVIEW, "PhotoView")
            .put(VIEW_TYPE_WIDGET_ZOOMABLEIMAGEVIEW, "ZoomableImageView")
            .put(VIEW_TYPE_WIDGET_EXOPLAYERVIEW, "ExoPlayerView")
            .put(VIEW_TYPE_WIDGET_MAPVIEW, "MapView")
            .put(VIEW_TYPE_WIDGET_QRCODEVIEW, "QRCodeView")
            .put(VIEW_TYPE_WIDGET_BARCODESCANVIEW, "BarcodeScanView")
            .put(VIEW_TYPE_WIDGET_CAMERAXPREVIEWVIEW, "CameraXPreviewView")
            // ── Charts & Data Viz ─────────────────────────────────────────────
            .put(VIEW_TYPE_WIDGET_LINECHART, "LineChart")
            .put(VIEW_TYPE_WIDGET_BARCHART, "BarChart")
            .put(VIEW_TYPE_WIDGET_PIECHART, "PieChart")
            .put(VIEW_TYPE_WIDGET_RADARCHART, "RadarChart")
            .put(VIEW_TYPE_WIDGET_BUBBLECHART, "BubbleChart")
            .put(VIEW_TYPE_WIDGET_SCATTERCHART, "ScatterChart")
            .put(VIEW_TYPE_WIDGET_COMBINEDCHART, "CombinedChart")
            .put(VIEW_TYPE_WIDGET_CANDLESTICKCHART, "CandleStickChart")
            .put(VIEW_TYPE_WIDGET_HORIZONTALBARCHART, "HorizontalBarChart")
            .put(VIEW_TYPE_WIDGET_SPARKLINEVIEW, "SparklineView")
            // ── Dialogs & Overlays ────────────────────────────────────────────
            .put(VIEW_TYPE_WIDGET_BOTTOMSHEETDIALOG, "BottomSheetDialog")
            .put(VIEW_TYPE_WIDGET_MATERIALDIALOG, "MaterialDialog")
            .put(VIEW_TYPE_WIDGET_SNACKBAR, "Snackbar")
            .put(VIEW_TYPE_WIDGET_TOOLTIP, "Tooltip")
            .put(VIEW_TYPE_WIDGET_POPUPMENU, "PopupMenu")
            .put(VIEW_TYPE_WIDGET_CONTEXTMENU, "ContextMenu")
            .put(VIEW_TYPE_WIDGET_SHOWCASEVIEW, "ShowcaseView")
            // ── Text Display ──────────────────────────────────────────────────
            .put(VIEW_TYPE_WIDGET_MARQUEETEXTVIEW, "MarqueeTextView")
            .put(VIEW_TYPE_WIDGET_EXPANDABLETEXTVIEW, "ExpandableTextView")
            .put(VIEW_TYPE_WIDGET_HTMLTEXTVIEW, "HtmlTextView")
            .put(VIEW_TYPE_WIDGET_AUTOSIZETEXTVIEW, "AutoSizeTextView")
            .put(VIEW_TYPE_WIDGET_TYPEWRITERTEXTVIEW, "TypeWriterTextView")
            .put(VIEW_TYPE_WIDGET_BADGEDTEXTVIEW, "BadgedTextView")
            .put(VIEW_TYPE_WIDGET_COUNTUPTEXTVIEW, "CountUpTextView")
            .put(VIEW_TYPE_WIDGET_COUNTDOWNTIMER, "CountDownTimer")
            // ── Animation & Effects ───────────────────────────────────────────
            .put(VIEW_TYPE_WIDGET_RIVE, "RiveView")
            .put(VIEW_TYPE_WIDGET_DOTLOTTIEVIEW, "DotLottieView")
            .put(VIEW_TYPE_WIDGET_CONFETTIVIEW, "ConfettiView")
            .put(VIEW_TYPE_WIDGET_SHIMMERFRAMELAYOUT, "ShimmerFrameLayout")
            .put(VIEW_TYPE_WIDGET_SKELETONVIEW, "SkeletonView")
            .put(VIEW_TYPE_WIDGET_PARTICLEVIEW, "ParticleView")
            .put(VIEW_TYPE_WIDGET_GLPARTICLEVIEW, "GLParticleView")
            .put(VIEW_TYPE_WIDGET_RIPPLEBACKGROUND, "RippleBackground")
            .put(VIEW_TYPE_WIDGET_PULSATINGVIEW, "PulsatingView")
            // ── Swipe & Gesture ───────────────────────────────────────────────
            .put(VIEW_TYPE_WIDGET_SWIPEREVEALAYOUT, "SwipeRevealLayout")
            .put(VIEW_TYPE_WIDGET_SWIPECARDVIEW, "SwipeCardView")
            .put(VIEW_TYPE_WIDGET_DRAGGABLEVIEW, "DraggableView")
            .put(VIEW_TYPE_WIDGET_PINCHZOOMVIEW, "PinchZoomView")
            .put(VIEW_TYPE_WIDGET_SWIPEMENULISTVIEW, "SwipeMenuListView")
            .put(VIEW_TYPE_WIDGET_SLIDETOACTIONVIEW, "SlideToActionView")
            .put(VIEW_TYPE_WIDGET_DRAGTOSORTVIEW, "DragToSortView")
            // ── Calendar & Date / Time ────────────────────────────────────────
            .put(VIEW_TYPE_WIDGET_MATERIALCALENDARVIEW, "MaterialCalendarView")
            .put(VIEW_TYPE_WIDGET_COMPACTCALENDARVIEW, "CompactCalendarView")
            .put(VIEW_TYPE_WIDGET_HEATMAPVIEW, "HeatMapView")
            .put(VIEW_TYPE_WIDGET_CLOCKVIEW, "ClockView")
            .put(VIEW_TYPE_WIDGET_FLIPCLOCKVIEW, "FlipClockView")
            .put(VIEW_TYPE_WIDGET_COUNTDOWNVIEW, "CountDownView")
            // ── Tags & Badges ─────────────────────────────────────────────────
            .put(VIEW_TYPE_WIDGET_TAGSVIEW, "TagsView")
            .put(VIEW_TYPE_WIDGET_TAGCLOUD, "TagCloud")
            .put(VIEW_TYPE_WIDGET_FLOWTAGLAYOUT, "FlowTagLayout")
            .put(VIEW_TYPE_WIDGET_MATERIALBADGE, "MaterialBadge")
            .put(VIEW_TYPE_WIDGET_NOTIFICATIONBADGE, "NotificationBadge")
            // ── Cards & Panels ────────────────────────────────────────────────
            .put(VIEW_TYPE_WIDGET_EXPANDABLECARDVIEW, "ExpandableCardView")
            .put(VIEW_TYPE_WIDGET_FLIPCARDVIEW, "FlipCardView")
            .put(VIEW_TYPE_WIDGET_ACCORDIONVIEW, "AccordionView")
            .put(VIEW_TYPE_WIDGET_MATERIALCARDVIEW, "MaterialCardView")
            .put(VIEW_TYPE_WIDGET_BANNERWIDGET, "BannerWidget")
            .put(VIEW_TYPE_WIDGET_ALERTBANNER, "AlertBanner")
            .put(VIEW_TYPE_WIDGET_INFOBUBBLE, "InfoBubble")
            .put(VIEW_TYPE_WIDGET_RIBBONVIEW, "RibbonView")
            // ── Layout Containers ─────────────────────────────────────────────
            .put(VIEW_TYPE_LAYOUT_CONSTRAINTLAYOUT, "ConstraintLayout")
            .put(VIEW_TYPE_LAYOUT_MOTIONLAYOUT, "MotionLayout")
            .put(VIEW_TYPE_LAYOUT_GRIDLAYOUT, "GridLayout")
            .put(VIEW_TYPE_LAYOUT_TABLELAYOUT, "TableLayout")
            .put(VIEW_TYPE_LAYOUT_TABLEROW, "TableRow")
            .put(VIEW_TYPE_LAYOUT_FRAMELAYOUT, "FrameLayout")
            .put(VIEW_TYPE_LAYOUT_RELATIVELAYOUT, "RelativeLayout")
            .put(VIEW_TYPE_LAYOUT_LINEARLAYOUT_H, "LinearLayoutH")
            .put(VIEW_TYPE_LAYOUT_LINEARLAYOUT_V, "LinearLayoutV")
            .put(VIEW_TYPE_LAYOUT_PERCENTRELATIVELAYOUT, "PercentRelativeLayout")
            // ── Web & Code ────────────────────────────────────────────────────
            .put(VIEW_TYPE_WIDGET_WEBVIEW, "WebView")
            .put(VIEW_TYPE_WIDGET_MARKDOWNVIEW, "MarkdownView")
            .put(VIEW_TYPE_WIDGET_SYNTAXHIGHLIGHTVIEW, "SyntaxHighlightView")
            .put(VIEW_TYPE_WIDGET_HTMLEDITORVIEW, "HtmlEditorView")
            .put(VIEW_TYPE_WIDGET_JSONVIEW, "JsonView")
            // ── Social & Payments ─────────────────────────────────────────────
            .put(VIEW_TYPE_WIDGET_FACEBOOKSIGNINBUTTON, "FacebookSignInButton")
            .put(VIEW_TYPE_WIDGET_TWITTERSIGNINBUTTON, "TwitterSignInButton")
            .put(VIEW_TYPE_WIDGET_GPAYSBUTTON, "GPayButton")
            .put(VIEW_TYPE_WIDGET_PAYTMBUTTON, "PaytmButton")
            .put(VIEW_TYPE_WIDGET_SOCIALSHAREVIEW, "SocialShareView")
            .put(VIEW_TYPE_WIDGET_SHARESHEET, "ShareSheet")
            // ── Misc Utility Widgets ──────────────────────────────────────────
            .put(VIEW_TYPE_WIDGET_COLORPICKERPALETTE, "ColorPickerPalette")
            .put(VIEW_TYPE_WIDGET_FILEPICKERVIEW, "FilePickerView")
            .put(VIEW_TYPE_WIDGET_IMAGECROPVIEW, "ImageCropView")
            .put(VIEW_TYPE_WIDGET_IMAGESLIDER, "ImageSlider")
            .put(VIEW_TYPE_WIDGET_BANNERSLIDER, "BannerSlider")
            .put(VIEW_TYPE_WIDGET_INFINITECYCLERVIEW, "InfiniteCycleView")
            .put(VIEW_TYPE_WIDGET_PARALLAXSCROLLVIEW, "ParallaxScrollView")
            .put(VIEW_TYPE_WIDGET_STICKYHEADERLISTVIEW, "StickyHeaderListView")
            .put(VIEW_TYPE_WIDGET_INDEXSCROLLBAR, "IndexScrollBar")
            .put(VIEW_TYPE_WIDGET_ALPHABETINDEXVIEW, "AlphabetIndexView")
            .put(VIEW_TYPE_WIDGET_EMOJIPICKERWIDGET, "EmojiPickerWidget")
            .put(VIEW_TYPE_WIDGET_AVATARVIEW, "AvatarView")
            .build();

    public static String buildClassInfo(int id) {
        return getViewTypeName(id);
    }

    public static int getViewTypeByTypeName(String typeName) {
        return views.inverse().containsKey(typeName) ? views.inverse().get(typeName) : 0;
    }

    public static String getViewTypeName(int id) {
        return views.containsKey(id) ? views.get(id) : "";
    }

    public static int getViewTypeResId(int id) {
        return switch (id) {
            // ── Original mappings ────────────────────────────────────────────
            case VIEW_TYPE_WIDGET_RADIOBUTTON -> R.drawable.ic_mtrl_radio_btn;
            case VIEW_TYPE_WIDGET_RATINGBAR -> R.drawable.ic_mtrl_star;
            case VIEW_TYPE_WIDGET_VIDEOVIEW -> R.drawable.ic_mtrl_video;
            case VIEW_TYPE_WIDGET_SEARCHVIEW -> R.drawable.ic_mtrl_search;
            case VIEW_TYPE_WIDGET_AUTOCOMPLETETEXTVIEW, VIEW_TYPE_WIDGET_MULTIAUTOCOMPLETETEXTVIEW,
                 VIEW_TYPE_LAYOUT_TEXTINPUTLAYOUT -> R.drawable.ic_mtrl_edittext;
            case VIEW_TYPE_WIDGET_GRIDVIEW -> R.drawable.ic_mtrl_grid;
            case VIEW_TYPE_WIDGET_RECYCLERVIEW -> R.drawable.ic_mtrl_list;
            case VIEW_TYPE_WIDGET_ANALOGCLOCK, VIEW_TYPE_WIDGET_TIMEPICKER,
                 VIEW_TYPE_WIDGET_DIGITALCLOCK -> R.drawable.ic_mtrl_time;
            case VIEW_TYPE_WIDGET_DATEPICKER -> R.drawable.ic_mtrl_calendar;
            case VIEW_TYPE_LAYOUT_TABLAYOUT -> R.drawable.ic_mtrl_tabs;
            case VIEW_TYPE_LAYOUT_VIEWPAGER -> R.drawable.ic_mtrl_viewpager;
            case VIEW_TYPE_LAYOUT_BOTTOMNAVIGATIONVIEW -> R.drawable.ic_mtrl_bottom_navigation;
            case VIEW_TYPE_WIDGET_BADGEVIEW -> R.drawable.ic_mtrl_badge;
            case VIEW_TYPE_WIDGET_PATTERNLOCKVIEW -> R.drawable.ic_mtrl_pattern;
            case VIEW_TYPE_WIDGET_WAVESIDEBAR -> R.drawable.ic_mtrl_sidebar;
            case VIEW_TYPE_LAYOUT_CARDVIEW -> R.drawable.ic_mtrl_rectangle;
            case VIEW_TYPE_LAYOUT_COLLAPSINGTOOLBARLAYOUT -> R.drawable.ic_mtrl_collapsing_toolbar;
            case VIEW_TYPE_LAYOUT_SWIPEREFRESHLAYOUT -> R.drawable.ic_mtrl_refresh;
            case VIEW_TYPE_LAYOUT_RADIOGROUP -> R.drawable.ic_mtrl_radio_partitial;
            case VIEW_TYPE_WIDGET_MATERIALBUTTON -> R.drawable.ic_mtrl_button_click;
            case VIEW_TYPE_WIDGET_SIGNINBUTTON -> R.drawable.ic_mtrl_login;
            case VIEW_TYPE_WIDGET_CIRCLEIMAGEVIEW -> R.drawable.ic_mtrl_camera;
            case VIEW_TYPE_WIDGET_LOTTIEANIMATIONVIEW -> R.drawable.ic_mtrl_animation;
            case VIEW_TYPE_WIDGET_YOUTUBEPLAYERVIEW -> R.drawable.ic_mtrl_youtube;
            case VIEW_TYPE_WIDGET_OTPVIEW -> R.drawable.ic_mtrl_password;
            case VIEW_TYPE_WIDGET_CODEVIEW -> R.drawable.ic_mtrl_terminal;
            // ── Material / Extended Buttons ───────────────────────────────────
            case VIEW_TYPE_WIDGET_EXTENDEDfab -> R.drawable.ic_mtrl_button_click;
            case VIEW_TYPE_WIDGET_FLOATINGACTIONBUTTON -> R.drawable.ic_mtrl_button_click;
            case VIEW_TYPE_WIDGET_CHIP, VIEW_TYPE_WIDGET_CHIPGROUP -> R.drawable.ic_mtrl_badge;
            case VIEW_TYPE_WIDGET_TOGGLEBUTTON -> R.drawable.ic_mtrl_radio_btn;
            case VIEW_TYPE_WIDGET_MATERIALSWITCH -> R.drawable.ic_mtrl_radio_btn;
            case VIEW_TYPE_WIDGET_MATERIALTEXTVIEW -> R.drawable.ic_mtrl_edittext;
            case VIEW_TYPE_WIDGET_SHAPEABLEIMAGEVIEW -> R.drawable.ic_mtrl_camera;
            // ── Inputs & Sliders ──────────────────────────────────────────────
            case VIEW_TYPE_WIDGET_SLIDER, VIEW_TYPE_WIDGET_RANGESLIDER,
                 VIEW_TYPE_WIDGET_TWOWAYSEEKBAR -> R.drawable.ic_mtrl_sidebar;
            case VIEW_TYPE_WIDGET_NUMBERPICKER -> R.drawable.ic_mtrl_time;
            case VIEW_TYPE_WIDGET_AUTOCOMPLETETEXTVIEWDROPDOWN,
                 VIEW_TYPE_WIDGET_EXPOSEDDROPDOWNMENU -> R.drawable.ic_mtrl_edittext;
            case VIEW_TYPE_WIDGET_COLORPICKER, VIEW_TYPE_WIDGET_COLORPICKERPALETTE -> R.drawable.ic_mtrl_animation;
            case VIEW_TYPE_WIDGET_PINVIEW -> R.drawable.ic_mtrl_password;
            case VIEW_TYPE_WIDGET_SIGNATUREVIEW -> R.drawable.ic_mtrl_terminal;
            // ── Progress & Indicators ─────────────────────────────────────────
            case VIEW_TYPE_WIDGET_CIRCULARPROGRESSBAR, VIEW_TYPE_WIDGET_CIRCULARPROGRESSINDICATOR,
                 VIEW_TYPE_WIDGET_ARCPROGRESSBAR -> R.drawable.ic_mtrl_refresh;
            case VIEW_TYPE_WIDGET_LINEARPROGRESSINDICATOR -> R.drawable.ic_mtrl_refresh;
            case VIEW_TYPE_WIDGET_DOTSINDICATOR -> R.drawable.ic_mtrl_badge;
            case VIEW_TYPE_WIDGET_SPRINGLEVELBAR -> R.drawable.ic_mtrl_sidebar;
            case VIEW_TYPE_WIDGET_STEPVIEW -> R.drawable.ic_mtrl_tabs;
            // ── Navigation ────────────────────────────────────────────────────
            case VIEW_TYPE_LAYOUT_NAVIGATIONVIEW -> R.drawable.ic_mtrl_bottom_navigation;
            case VIEW_TYPE_LAYOUT_DRAWERLAYOUT -> R.drawable.ic_mtrl_sidebar;
            case VIEW_TYPE_LAYOUT_NAVIGATIONRAILVIEW -> R.drawable.ic_mtrl_bottom_navigation;
            case VIEW_TYPE_LAYOUT_TOOLBAR -> R.drawable.ic_mtrl_collapsing_toolbar;
            case VIEW_TYPE_LAYOUT_APPBARLAYOUT -> R.drawable.ic_mtrl_collapsing_toolbar;
            case VIEW_TYPE_LAYOUT_COORDINATORLAYOUT -> R.drawable.ic_mtrl_rectangle;
            case VIEW_TYPE_LAYOUT_NESTEDSCROLLVIEW -> R.drawable.ic_mtrl_refresh;
            case VIEW_TYPE_LAYOUT_HORIZONTALSCROLLVIEW -> R.drawable.ic_mtrl_viewpager;
            case VIEW_TYPE_LAYOUT_VIEWPAGER2 -> R.drawable.ic_mtrl_viewpager;
            // ── Lists & Grids ─────────────────────────────────────────────────
            case VIEW_TYPE_WIDGET_STAGGEREDGRIDVIEW, VIEW_TYPE_WIDGET_EXPANDABLELISTVIEW,
                 VIEW_TYPE_WIDGET_LISTVIEW -> R.drawable.ic_mtrl_list;
            case VIEW_TYPE_WIDGET_SPINNER -> R.drawable.ic_mtrl_edittext;
            case VIEW_TYPE_WIDGET_FLEXBOXLAYOUT, VIEW_TYPE_WIDGET_FLOWLAYOUT -> R.drawable.ic_mtrl_grid;
            // ── Image & Media ─────────────────────────────────────────────────
            case VIEW_TYPE_WIDGET_GLIDEIMAGEVIEW, VIEW_TYPE_WIDGET_PICASSOIMAGEVIEW,
                 VIEW_TYPE_WIDGET_GIFIMAGEVIEW, VIEW_TYPE_WIDGET_PHOTOVIEW,
                 VIEW_TYPE_WIDGET_ZOOMABLEIMAGEVIEW -> R.drawable.ic_mtrl_camera;
            case VIEW_TYPE_WIDGET_EXOPLAYERVIEW -> R.drawable.ic_mtrl_video;
            case VIEW_TYPE_WIDGET_MAPVIEW -> R.drawable.ic_mtrl_grid;
            case VIEW_TYPE_WIDGET_QRCODEVIEW, VIEW_TYPE_WIDGET_BARCODESCANVIEW -> R.drawable.ic_mtrl_terminal;
            case VIEW_TYPE_WIDGET_CAMERAXPREVIEWVIEW -> R.drawable.ic_mtrl_camera;
            // ── Charts & Data Viz ─────────────────────────────────────────────
            case VIEW_TYPE_WIDGET_LINECHART, VIEW_TYPE_WIDGET_BARCHART, VIEW_TYPE_WIDGET_PIECHART,
                 VIEW_TYPE_WIDGET_RADARCHART, VIEW_TYPE_WIDGET_BUBBLECHART,
                 VIEW_TYPE_WIDGET_SCATTERCHART, VIEW_TYPE_WIDGET_COMBINEDCHART,
                 VIEW_TYPE_WIDGET_CANDLESTICKCHART, VIEW_TYPE_WIDGET_HORIZONTALBARCHART,
                 VIEW_TYPE_WIDGET_SPARKLINEVIEW -> R.drawable.ic_mtrl_star;
            // ── Dialogs & Overlays ────────────────────────────────────────────
            case VIEW_TYPE_WIDGET_BOTTOMSHEETDIALOG, VIEW_TYPE_WIDGET_MATERIALDIALOG -> R.drawable.ic_mtrl_rectangle;
            case VIEW_TYPE_WIDGET_SNACKBAR, VIEW_TYPE_WIDGET_TOOLTIP,
                 VIEW_TYPE_WIDGET_INFOBUBBLE -> R.drawable.ic_mtrl_badge;
            case VIEW_TYPE_WIDGET_POPUPMENU, VIEW_TYPE_WIDGET_CONTEXTMENU -> R.drawable.ic_mtrl_terminal;
            case VIEW_TYPE_WIDGET_SHOWCASEVIEW -> R.drawable.ic_mtrl_animation;
            // ── Text Display ──────────────────────────────────────────────────
            case VIEW_TYPE_WIDGET_MARQUEETEXTVIEW, VIEW_TYPE_WIDGET_EXPANDABLETEXTVIEW,
                 VIEW_TYPE_WIDGET_HTMLTEXTVIEW, VIEW_TYPE_WIDGET_AUTOSIZETEXTVIEW,
                 VIEW_TYPE_WIDGET_TYPEWRITERTEXTVIEW, VIEW_TYPE_WIDGET_BADGEDTEXTVIEW,
                 VIEW_TYPE_WIDGET_COUNTUPTEXTVIEW, VIEW_TYPE_WIDGET_COUNTDOWNTIMER -> R.drawable.ic_mtrl_edittext;
            // ── Animation & Effects ───────────────────────────────────────────
            case VIEW_TYPE_WIDGET_RIVE, VIEW_TYPE_WIDGET_DOTLOTTIEVIEW,
                 VIEW_TYPE_WIDGET_CONFETTIVIEW, VIEW_TYPE_WIDGET_PARTICLEVIEW,
                 VIEW_TYPE_WIDGET_GLPARTICLEVIEW, VIEW_TYPE_WIDGET_RIPPLEBACKGROUND,
                 VIEW_TYPE_WIDGET_PULSATINGVIEW -> R.drawable.ic_mtrl_animation;
            case VIEW_TYPE_WIDGET_SHIMMERFRAMELAYOUT, VIEW_TYPE_WIDGET_SKELETONVIEW -> R.drawable.ic_mtrl_rectangle;
            // ── Swipe & Gesture ───────────────────────────────────────────────
            case VIEW_TYPE_WIDGET_SWIPEREVEALAYOUT, VIEW_TYPE_WIDGET_SWIPECARDVIEW,
                 VIEW_TYPE_WIDGET_DRAGGABLEVIEW, VIEW_TYPE_WIDGET_PINCHZOOMVIEW,
                 VIEW_TYPE_WIDGET_SWIPEMENULISTVIEW, VIEW_TYPE_WIDGET_SLIDETOACTIONVIEW,
                 VIEW_TYPE_WIDGET_DRAGTOSORTVIEW -> R.drawable.ic_mtrl_refresh;
            // ── Calendar & Date / Time ────────────────────────────────────────
            case VIEW_TYPE_WIDGET_MATERIALCALENDARVIEW,
                 VIEW_TYPE_WIDGET_COMPACTCALENDARVIEW -> R.drawable.ic_mtrl_calendar;
            case VIEW_TYPE_WIDGET_HEATMAPVIEW -> R.drawable.ic_mtrl_grid;
            case VIEW_TYPE_WIDGET_CLOCKVIEW, VIEW_TYPE_WIDGET_FLIPCLOCKVIEW,
                 VIEW_TYPE_WIDGET_COUNTDOWNVIEW -> R.drawable.ic_mtrl_time;
            // ── Tags & Badges ─────────────────────────────────────────────────
            case VIEW_TYPE_WIDGET_TAGSVIEW, VIEW_TYPE_WIDGET_TAGCLOUD,
                 VIEW_TYPE_WIDGET_FLOWTAGLAYOUT, VIEW_TYPE_WIDGET_MATERIALBADGE,
                 VIEW_TYPE_WIDGET_NOTIFICATIONBADGE -> R.drawable.ic_mtrl_badge;
            // ── Cards & Panels ────────────────────────────────────────────────
            case VIEW_TYPE_WIDGET_EXPANDABLECARDVIEW, VIEW_TYPE_WIDGET_FLIPCARDVIEW,
                 VIEW_TYPE_WIDGET_ACCORDIONVIEW, VIEW_TYPE_WIDGET_MATERIALCARDVIEW,
                 VIEW_TYPE_WIDGET_BANNERWIDGET, VIEW_TYPE_WIDGET_ALERTBANNER,
                 VIEW_TYPE_WIDGET_RIBBONVIEW -> R.drawable.ic_mtrl_rectangle;
            // ── Layout Containers ─────────────────────────────────────────────
            case VIEW_TYPE_LAYOUT_CONSTRAINTLAYOUT, VIEW_TYPE_LAYOUT_MOTIONLAYOUT,
                 VIEW_TYPE_LAYOUT_GRIDLAYOUT, VIEW_TYPE_LAYOUT_TABLELAYOUT,
                 VIEW_TYPE_LAYOUT_TABLEROW, VIEW_TYPE_LAYOUT_FRAMELAYOUT,
                 VIEW_TYPE_LAYOUT_RELATIVELAYOUT, VIEW_TYPE_LAYOUT_LINEARLAYOUT_H,
                 VIEW_TYPE_LAYOUT_LINEARLAYOUT_V,
                 VIEW_TYPE_LAYOUT_PERCENTRELATIVELAYOUT -> R.drawable.ic_mtrl_rectangle;
            // ── Web & Code ────────────────────────────────────────────────────
            case VIEW_TYPE_WIDGET_WEBVIEW, VIEW_TYPE_WIDGET_MARKDOWNVIEW,
                 VIEW_TYPE_WIDGET_SYNTAXHIGHLIGHTVIEW, VIEW_TYPE_WIDGET_HTMLEDITORVIEW,
                 VIEW_TYPE_WIDGET_JSONVIEW -> R.drawable.ic_mtrl_terminal;
            // ── Social & Payments ─────────────────────────────────────────────
            case VIEW_TYPE_WIDGET_FACEBOOKSIGNINBUTTON, VIEW_TYPE_WIDGET_TWITTERSIGNINBUTTON,
                 VIEW_TYPE_WIDGET_GPAYSBUTTON, VIEW_TYPE_WIDGET_PAYTMBUTTON,
                 VIEW_TYPE_WIDGET_SOCIALSHAREVIEW, VIEW_TYPE_WIDGET_SHARESHEET -> R.drawable.ic_mtrl_login;
            // ── Misc Utility Widgets ──────────────────────────────────────────
            case VIEW_TYPE_WIDGET_FILEPICKERVIEW -> R.drawable.ic_mtrl_terminal;
            case VIEW_TYPE_WIDGET_IMAGECROPVIEW, VIEW_TYPE_WIDGET_IMAGESLIDER,
                 VIEW_TYPE_WIDGET_BANNERSLIDER, VIEW_TYPE_WIDGET_INFINITECYCLERVIEW -> R.drawable.ic_mtrl_camera;
            case VIEW_TYPE_WIDGET_PARALLAXSCROLLVIEW, VIEW_TYPE_WIDGET_STICKYHEADERLISTVIEW,
                 VIEW_TYPE_WIDGET_INDEXSCROLLBAR, VIEW_TYPE_WIDGET_ALPHABETINDEXVIEW -> R.drawable.ic_mtrl_list;
            case VIEW_TYPE_WIDGET_EMOJIPICKERWIDGET -> R.drawable.ic_mtrl_star;
            case VIEW_TYPE_WIDGET_AVATARVIEW -> R.drawable.ic_mtrl_camera;
            default -> id;
        };
    }
}