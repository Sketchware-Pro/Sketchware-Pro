package mod.agus.jcoderz.lib;

public class TypeClassWidget {
    public static String a(String str) {
        if (str.equals("RadioButton")) {
            return "View.Clickable.TextView.Button.CompoundButton.RadioButton";
        }
        if (str.equals("RatingBar")) {
            return "View.Clickable.RatingBar";
        }
        if (str.equals("SearchView")) {
            return "View.ViewGroup.LinearLayoutCompat.SearchView";
        }
        if (str.equals("VideoView")) {
            return "View.VideoView";
        }
        if (str.equals("AutoCompleteTextView")) {
            return "View.Clickable.TextView.EditText.AutoCompleteTextView";
        }
        if (str.equals("MultiAutoCompleteTextView")) {
            return "View.Clickable.TextView.EditText.MultiAutoCompleteTextView";
        }
        if (str.equals("GridView")) {
            return "View.ViewGroup.AdapterView.AbsListView.GridView";
        }
        if (str.equals("AnalogClock")) {
            return "View.Clickable.AnalogClock";
        }
        if (str.equals("DigitalClock")) {
            return "View.Clickable.DigitalClock";
        }
        if (str.equals("DatePicker")) {
            return "View.Clickable.DatePicker";
        }
        if (str.equals("TimePicker")) {
            return "View.Clickable.TimePicker";
        }
        if (str.equals("TabLayout")) {
            return "View.TabLayout";
        }
        if (str.equals("ViewPager")) {
            return "View.ViewGroup.AdapterView.ViewPager";
        }
        if (str.equals("BottomNavigationView")) {
            return "View.ViewGroup.BottomNavigationView";
        }
        if (str.equals("BadgeView")) {
            return "View.Clickable.FrameLayout.BadgeView";
        }
        if (str.equals("PatternLockView")) {
            return "View.PatternLockView";
        }
        if (str.equals("WaveSideBar")) {
            return "View.FrameLayout.RelativeLayout.WaveSideBar";
        }
        if (str.equals("SignInButton")) {
            return "View.Clickable.Button.SignInButton";
        }
        if (str.equals("MaterialButton")) {
            return "View.Clickable.TextView.Button";
        }
        if (str.equals("CircleImageView")) {
            return "View.Clickable.ImageView.CircleImageView";
        }
        if (str.equals("CollapsingToolbarLayout")) {
            return "View.ViewGroup.LinearLayout.CollapsingToolbarLayout";
        }
        if (str.equals("SwipeRefreshLayout")) {
            return "View.ViewGroup.LinearLayout.SwipeRefreshLayout";
        }
        if (str.equals("TextInputLayout")) {
            return "View.ViewGroup.LinearLayout.TextInputLayout";
        }
        if (str.equals("RadioGroup")) {
            return "View.ViewGroup.LinearLayout.RadioGroup";
        }
        if (str.equals("CardView")) {
            return "View.Clickable.ViewGroup.LinearLayout.CardView";
        }
        if (str.equals("LottieAnimationView")) {
            return "View.Clickable.LottieAnimation";
        }
        if (str.equals("YoutubePlayerView")) {
            return "View.Clickable.YoutubePlayer";
        }
        if (str.equals("OTPView")) {
            return "View.Clickable.OTPView";
        }
        if (str.equals("RecyclerView")) {
            return "View.RecyclerView";
        }
        return "";
    }
}
