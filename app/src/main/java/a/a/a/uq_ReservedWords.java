package a.a.a;

import java.util.Arrays;
import java.util.stream.Stream;




// =========================================================
// uq_ReservedWords = Word Lists builder for uq.b
// =========================================================

// PURPOSE:
    // Assembles uq.b (RESERVED_WORDS) from categorized local sub-arrays.
    // Sub-arrays exist only inside build() as local variables —
    // garbage collected immediately after assembly.
    // Only the final flat array survives, stored as uq.b.

// BELONGS TO:
    // uq (ConstantArrays) — see uq.java

// USAGE:
    // Do NOT use this class directly from outside uq.
    // Access the assembled array via:
        // uq.b

// KNOWN ISSUES IN THE DATA:
    // TYPO:      [52] "LayoutInfater"  → should be "LayoutInflater"
    // DUPLICATE: [68] "CalendarView"   → appears again at [83], preserved as-is
    // NOTE:      [78] "none"           → not a Java keyword, likely a Sketchware placeholder
    // NOTE:      [84] "gyroscope"      → not a standard Android class name

// =========================================================

class uq_ReservedWords {




    // =========================================================
    // BUILDER
    // =========================================================

    // Assembles and returns a flat array identical to the original uq.b.
    // Called once at class-load time from uq.b's field initializer.
    static String[] build() {

        // [0–47] Core Java language keywords.
        String[] javaKeywords = {
            "abstract",    "boolean",    "break",         "byte",       "case",
            "catch",       "char",       "class",         "const",      "continue",
            "default",     "do",         "double",        "else",       "extends",
            "final",       "finally",    "float",         "for",        "goto",
            "if",          "implements", "import",        "instanceof", "int",
            "interface",   "long",       "native",        "new",        "null",
            "package",     "private",    "protected",     "public",     "return",
            "short",       "static",     "super",         "switch",     "synchronized",
            "this",        "throw",      "throws",        "transient",  "try",
            "void",        "volatile",   "while"
        };

        // [48–49] Java built-in annotation names.
        String[] javaAnnotations = {
            "Override", "Deprecated"
        };

        // [50–84] Android core: framework classes, lifecycle methods, literals, misc.
        // Original order preserved exactly — including interleaved literals, a typo at [52] and
        // the duplicate CalendarView (first at [68], again at [83]).
        String[] androidCore = {
            "Activity",      "Bundle",
            "LayoutInfater",                                  // TYPO: [52] should be LayoutInflater
            "Toolbar",       "DrawerLayout",   "FloatingActionButton",
            "View",          "Context",        "EditText",
            "onCreate",      "onClick",
            "LinearLayout",  "FrameLayout",    "RelativeLayout",
            "TextView",      "Spinner",        "CheckBox",
            "WebView",       "CalendarView",   "ImageView",   // [68] first CalendarView
            "Button",        "ArrayList",      "String",
            "Intent",        "SharedPreferences", "Calendar",
            "true",          "false",
            "none",                                           // NOTE: not a Java keyword — Sketchware placeholder
            "SeekBar",       "Switch",         "root",
            "R",             "CalendarView",                  // DUPLICATE: [83] second CalendarView
            "gyroscope"                                       // NOTE: not a standard Android class name
        };

        // [85–109] Sketchware-specific component class names.
        String[] swComponents = {
            "FirebaseDatabase",       "DatabaseReference",       "FirebaseStorage",
            "StorageReference",       "File",                    "AdView",
            "RequestNetwork",         "MediaController",         "NetworkRequest",
            "RequestNetworkController","ProgressBar",            "TextToSpeech",
            "SpeechRecognizer",       "BluetoothConnect",        "BluetoothController",
            "GoogleMapController",    "MapView",                 "GoogleMap",
            "LocationListener",       "LocationManager",         "ProgressDialog",
            "RewardedVideoAd",        "DatePickerDialog",        "TimePickerDialog",
            "Notification"
        };

        // [110–143] Standard and extended Android View class names.
        String[] androidViews = {
            "ListView",         "CardView",               "GridView",
            "VideoView",        "SearchView",             "RadioButton",
            "RatingBar",        "DatePicker",             "TimePicker",
            "DigitalClock",     "AnalogClock",            "RecyclerView",
            "ViewPager",        "SwipeRefreshLayout",     "CoordinatorLayout",
            "TabLayout",        "TextInputLayout",        "BottomNavigationView",
            "ImageButton",      "ShimmerButton",          "ShimmerTextView",
            "CircleImageView",  "AutoCompleteTextView",   "MultiAutoCompleteTextView",
            "BadgeView",        "BubbleLayout",           "PatternLockView",
            "WaveSideBar",      "BottomAppBar",           "BottomSheetBehavior",
            "NavigationView",   "NestedScrollView",       "CollapsingToolbarLayout",
            "AppBarLayout"
        };

        return Stream.of(
            javaKeywords,
            javaAnnotations,
            androidCore,
            swComponents,
            androidViews
        )
        .flatMap(Arrays::stream)
        .toArray(String[]::new);

    }




}


