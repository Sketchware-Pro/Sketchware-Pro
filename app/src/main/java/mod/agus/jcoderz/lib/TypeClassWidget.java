package mod.agus.jcoderz.lib;

public class TypeClassWidget {
    public static String a(String str) {
        switch (str) {
            case "RadioButton":
                return "View.Clickable.TextView.Button.CompoundButton.RadioButton";
        
            case "RatingBar":
                return "View.Clickable.RatingBar";
        
            case "SearchView":
                return "View.ViewGroup.LinearLayoutCompat.SearchView";
        
            case "VideoView":
                return "View.VideoView";
        
            case "AutoCompleteTextView":
                return "View.Clickable.TextView.EditText.AutoCompleteTextView";
        
            case "MultiAutoCompleteTextView":
                return "View.Clickable.TextView.EditText.MultiAutoCompleteTextView";
        
            case "GridView":
                return "View.ViewGroup.AdapterView.AbsListView.GridView";
        
            case "AnalogClock":
                return "View.Clickable.AnalogClock";
        
            case "DigitalClock":
                return "View.Clickable.DigitalClock";
        
            case "DatePicker":
                return "View.Clickable.DatePicker";
        
            case "TimePicker":
                return "View.Clickable.TimePicker";
        
            case "TabLayout":
                return "View.TabLayout";
        
            case "ViewPager":
                return "View.ViewGroup.AdapterView.ViewPager";
        
            case "BottomNavigationView":
                return "View.ViewGroup.BottomNavigationView";
        
            case "BadgeView":
                return "View.Clickable.FrameLayout.BadgeView";
        
            case "PatternLockView":
                return "View.PatternLockView";
        
            case "WaveSideBar":
                return "View.FrameLayout.RelativeLayout.WaveSideBar";
        
            case "SignInButton":
                return "View.Clickable.Button.SignInButton";
        
            case "MaterialButton":
                return "View.Clickable.TextView.Button";
        
            case "CircleImageView":
                return "View.Clickable.ImageView.CircleImageView";
        
            case "CollapsingToolbarLayout":
                return "View.ViewGroup.LinearLayout.CollapsingToolbarLayout";
        
            case "SwipeRefreshLayout":
                return "View.ViewGroup.LinearLayout.SwipeRefreshLayout";
        
            case "TextInputLayout":
                return "View.ViewGroup.LinearLayout.TextInputLayout";
        
            case "RadioGroup":
                return "View.ViewGroup.LinearLayout.RadioGroup";
        
            case "CardView":
                return "View.Clickable.ViewGroup.LinearLayout.CardView";
        
            case "LottieAnimationView":
                return "View.Clickable.LottieAnimation";
        
            case "YoutubePlayerView":
                return "View.Clickable.YoutubePlayer";
        
            case "OTPView":
                return "View.Clickable.OTPView";
        
            case "RecyclerView":
                return "View.RecyclerView";

            default:
                return "";
        }
    }
}
