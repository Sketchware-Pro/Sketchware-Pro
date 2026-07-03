package a.a.a;




// =========================================================
// /*ConstantArrays*/ uq = SW Array Constants
// =========================================================

// PURPOSE:
    // Central registry of all string-array constants used
    // by the Sketchware Pro.
    // Feeds autocomplete dropdowns, block palettes,
    // and property spinners throughout the app.

// STRUCTURE:
    // Split across three files for size management:
        // uq.java                → all short constants + static accessors   (this file)
        // uq_ReservedWords.java  → local sub-arrays that assemble field b
        // uq_CodeView.java       → raw data for CODEVIEW_THEME + CODEVIEW_LANGUAGE
    // CODEVIEW_THEME, CODEVIEW_LANGUAGE, and field b are still public fields on uq.
    // The split files are internal data holders only — callers always use uq.

// USAGE:
    // All fields are public static final — access directly:
        // String[] reserved = uq.b;
        // String[] themes   = uq.CODEVIEW_THEME;
    // Static accessor methods exist for the 3 most-used arrays.

// TODO:
    // ======= RENAME =======
        // 1.  class uq                         → ConstantArrays
        
        // 2.  field a  (COMPONENT_NAMES)       → COMPONENT_NAMES
        // 3.  field b  (RESERVED_WORDS)        → RESERVED_WORDS
        // 4.  field c  (INTENT_ACTIONS)        → INTENT_ACTIONS
        // 5.  field d  (INTENT_FLAGS)          → INTENT_FLAGS
        // 6.  field e  (CALENDAR_FIELDS)       → CALENDAR_FIELDS
        // 7.  field f  (CALENDAR_DAYS)         → CALENDAR_DAYS
        // 8.  field g  (VIEW_VISIBILITY)       → VIEW_VISIBILITY
        // 9.  field h  (WEBVIEW_CACHE_MODES)   → WEBVIEW_CACHE_MODES
        // 10. field i  (ANIMATOR_PROPERTIES)   → ANIMATOR_PROPERTIES
        // 11. field j  (ANIMATOR_REPEAT_MODES) → ANIMATOR_REPEAT_MODES
        // 12. field k  (INTERPOLATOR_TYPES)    → INTERPOLATOR_TYPES
        // 13. field l  (ENVIRONMENT_DIRS)      → ENVIRONMENT_DIRS
        // 14. field m  (BUILT_IN_METHODS)      → BUILT_IN_METHODS
        // 15. field n  (HTTP_METHODS)          → HTTP_METHODS
        // 16. field o  (REQUEST_PARAM_TYPES)   → REQUEST_PARAM_TYPES
        // 17. field p  (LOCATION_PROVIDERS)    → LOCATION_PROVIDERS
        // 18. field q  (MAP_TYPES)             → MAP_TYPES
        // 19. field r  (HUE_VALUES)            → HUE_VALUES
        
        // 20. method a()                       → getBuiltInMethods ()
        // 21. method b()                       → getIntentActions ()
        // 22. method c()                       → getIntentFlags ()

// =========================================================

public class /*ConstantArrays*/ uq {




    // =========================================================
    // FIELDS
    // =========================================================

    // Sketchware built-in component names shown in the block palette.
    public static final String[] /*COMPONENT_NAMES*/ a = {
        "Intent",           "SharedPreferences", "Calendar",        "Vibrator",
        "Timer",            "SoundPool",         "MediaPlayer",     "Dialog",
        "ObjectAnimator",   "Firebase DB",       "Interstitial Ad", "Firebase Storage",
        "Camera",           "FilePicker",        "RequestNetwork",  "TextToSpeech",
        "SpeechToText",     "BluetoothConnect",  "ProgressDialog",  "RewardedVideoAd",
        "TimePickerDialog", "Notification"
    };

    // RESERVED_WORDS — assembled once at class-load time by uq_ReservedWords.build().
    // Functionally identical to the original field b.
    // See uq_ReservedWords for the breakdown and issue annotations.
    public static final String[] /*RESERVED_WORDS*/ b = uq_ReservedWords.build();

    // Intent action strings.
    public static final String[] /*INTENT_ACTIONS*/ c = {
        "ACTION_DIAL",          "ACTION_CALL",      "ACTION_VIEW",      "ACTION_MAIN",
        "ACTION_PICK",          "ACTION_SEND",      "ACTION_SENDTO",    "ACTION_SEND_MULTIPLE",
        "ACTION_SET_WALLPAPER", "ACTION_SEARCH",    "ACTION_SCREEN_ON", "ACTION_SCREEN_OFF"
    };

    // Intent launch flags.
    public static final String[] /*INTENT_FLAGS*/ d = {"SINGLE_TOP", "CLEAR_TOP"};

    // Calendar field names used in Calendar.get() / Calendar.set() calls.
    public static final String[] /*CALENDAR_FIELDS*/ e = {
        "YEAR", "MONTH", "DAY_OF_MONTH", "HOUR", "MINUTE", "SECOND"
    };

    // Calendar day-of-week constants.
    public static final String[] /*CALENDAR_DAYS*/ f = {
        "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"
    };

    // View visibility states.
    public static final String[] /*VIEW_VISIBILITY*/ g = {"VISIBLE", "INVISIBLE", "GONE"};

    // WebView cache mode options.
    public static final String[] /*WEBVIEW_CACHE_MODES*/ h = {
        "LOAD_DEFAULT", "LOAD_CACHE_ELSE_NETWORK", "LOAD_NO_CACHE", "LOAD_CACHE_ONLY"
    };

    // ObjectAnimator animatable property names.
    public static final String[] /*ANIMATOR_PROPERTIES*/ i = {
        "rotation", "translationX", "translationY", "alpha", "scaleX", "scaleY"
    };

    // ObjectAnimator repeat mode options.
    public static final String[] /*ANIMATOR_REPEAT_MODES*/ j = {"RESTART", "REVERSE"};

    // TimeInterpolator type names.
    public static final String[] /*INTERPOLATOR_TYPES*/ k = {
        "Linear", "Accelerate", "Decelerate", "AccelerateDecelerate", "Bounce"
    };

    // Environment public directory names (Environment.DIRECTORY_*).
    public static final String[] /*ENVIRONMENT_DIRS*/ l = {
        "DIRECTORY_MUSIC",    "DIRECTORY_PODCASTS",      "DIRECTORY_RINGTONES",
        "DIRECTORY_ALARMS",   "DIRECTORY_NOTIFICATIONS", "DIRECTORY_PICTURES",
        "DIRECTORY_MOVIES",   "DIRECTORY_DOWNLOADS",     "DIRECTORY_DCIM",
        "DIRECTORY_DOCUMENT"
    };

    // Sketchware built-in method names available in every generated Activity.
    public static final String[] /*BUILT_IN_METHODS*/ m = {
        "onCreate",        "setContentView",        "initialize",
        "initializeLogic", "getRandom",             "showMessage",
        "getDip",          "getDisplayWidthPixels", "getDisplayHeightPixels"
    };

    // HTTP request method names.
    public static final String[] /*HTTP_METHODS*/ n = {"GET", "POST", "PUT", "DELETE"};

    // RequestNetwork parameter type options.
    public static final String[] /*REQUEST_PARAM_TYPES*/ o = {"REQUEST_PARAM", "REQUEST_BODY"};

    // LocationManager provider type names.
    public static final String[] /*LOCATION_PROVIDERS*/ p = {"GPS_PROVIDER", "NETWORK_PROVIDER"};

    // GoogleMap map type constants.
    public static final String[] /*MAP_TYPES*/ q = {
        "MAP_TYPE_NONE", "MAP_TYPE_NORMAL", "MAP_TYPE_SATELLITE", "MAP_TYPE_TERRAIN", "MAP_TYPE_HYBRID"
    };

    // HSV hue range names used in color pickers.
    public static final String[] /*HUE_VALUES*/ r = {
        "HUE_RED",   "HUE_ORANGE", "HUE_YELLOW", "HUE_GREEN",   "HUE_CYAN",
        "HUE_AZURE", "HUE_BLUE",   "HUE_VIOLET", "HUE_MAGENTA", "HUE_ROSE"
    };

    // TextInputLayout box style modes.
    public static final String[] TIL_BOX_MODE = {"NONE", "OUTLINED", "FILLED"};

    // FloatingActionButton options.
    public static final String[] FAB_SIZE    = {"AUTO", "MINI", "NORMAL"};
    public static final String[] FAB_VISIBLE = {"show", "hide"};

    // Menu item show-as-action flags.
    public static final String[] MENU_ACTION = {
        "SHOW_AS_ACTION_ALWAYS",          "SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW",
        "SHOW_AS_ACTION_IF_ROOM",         "SHOW_AS_ACTION_NEVER",
        "SHOW_AS_ACTION_WITH_TEXT"
    };

    // PorterDuff image compositing blend modes.
    public static final String[] PORTER_DUFF = {
        "MULTIPLY", "SCREEN", "ADD", "SRC_IN", "SRC_ATOP", "SRC_OVER"
    };

    // ListView transcript (auto-scroll) modes.
    public static final String[] TRANSCRIPT_MODE = {
        "TRANSCRIPT_MODE_NORMAL", "TRANSCRIPT_MODE_DISABLED", "TRANSCRIPT_MODE_ALWAYS_SCROLL"
    };

    // Scroll state constants.
    public static final String[] LIST_SCROLL_STATES = {
        "SCROLL_STATE_IDLE", "SCROLL_STATE_FLING", "SCROLL_STATE_TOUCH_SCROLL"
    };
    public static final String[] RECYCLER_SCROLL_STATES = {
        "SCROLL_STATE_IDLE", "SCROLL_STATE_SETTLING", "SCROLL_STATE_DRAGGING"
    };

    // GridView column stretch modes.
    public static final String[] GRID_STRETCH_MODE = {
        "STRETCH_COLUMN_WIDTH", "STRETCH_SPACING_UNIFORM", "STRETCH_SPACING"
    };

    // Gravity constants.
    public static final String[] GRAVITY_VERTICAL   = {"TOP", "BOTTOM", "CENTER_VERTICAL"};
    public static final String[] GRAVITY_HORIZONTAL = {"LEFT", "RIGHT", "CENTER_HORIZONTAL"};
    public static final String[] GRAVITY_TOAST      = {"TOP", "CENTER", "BOTTOM"};

    // PatternLockView display modes.
    public static final String[] PATTERNVIEW_MODE = {"CORRECT", "WRONG", "AUTO_DRAW"};

    // ProgressDialog style options.
    public static final String[] PROGRESS_STYLE = {"STYLE_HORIZONTAL", "STYLE_SPINNER"};

    // Common import class path wildcards for the import picker.
    public static final String[] IMPORT_CLASS_PATH = {
        "android.content.pm.*",         "android.database.sqlite.*",  "android.gesture.*",
        "android.graphics.fonts.*",     "android.graphics.pdf.*",     "android.hardware.*",
        "android.inputmethodservice.*", "android.opengl.*",           "android.preference.*",
        "android.print.pdf.*",          "android.provider.*",         "android.security.*",
        "android.service.*",            "android.text.format.*",      "android.text.util.*",
        "android.text.method.*",        "android.transition.*",       "android.view.inputmethod.*",
        "java.math.*",                  "java.nio.*",                 "java.security.*",
        "java.security.spec.*",         "java.sql.*"
    };

    // CodeView theme and language names — data lives in uq_CodeView.
    // These delegate fields preserve uq.CODEVIEW_THEME / uq.CODEVIEW_LANGUAGE
    // access for all existing call sites.
    public static final String[] CODEVIEW_THEME    = uq_CodeView.CODEVIEW_THEME;
    public static final String[] CODEVIEW_LANGUAGE = uq_CodeView.CODEVIEW_LANGUAGE;




    // =========================================================
    // STATIC ACCESSOR METHODS
    // =========================================================

    // Returns the built-in method names array.
    public static String[] /*getBuiltInMethods*/ a() { return /*BUILT_IN_METHODS*/ m; }

    // Returns the Intent actions array.
    public static String[] /*getIntentActions*/ b() { return /*INTENT_ACTIONS*/ c; }

    // Returns the Intent flags array.
    public static String[] /*getIntentFlags*/   c() { return /*INTENT_FLAGS*/   d; }




}


