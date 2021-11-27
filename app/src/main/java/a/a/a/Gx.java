package a.a.a;

import a.a.a.BV;

public class Gx {
    public String a;
    public String[] b;

    public Gx(String classInfo) {
        a = classInfo;
        b = null;
        e();
    }

    public String a() {
        return a;
    }

    public boolean a(Gx gx) {
        return a(gx.a);
    }

    public boolean a(String classInfo) {
        if (classInfo.equals("!")) {
            return true;
        } else if (classInfo.equals(a)) {
            return true;
        } else {
            String[] classInfoArray = b;
            for (int i = 0; i < classInfoArray.length; ++i) {
                if (classInfoArray[i].equals(classInfo)) {
                    return true;
                }
            }

            return false;
        }
    }

    public boolean b() {
        return a("List");
    }

    public boolean b(String classInfo) {
        return a.equals(classInfo);
    }

    public boolean c() {
        return a("Var");
    }

    public boolean d() {
        return a("View");
    }

    private void e() {
        String classInfos = "";
        switch (a) {
            case "boolean":
                classInfos = "Var.boolean";
                break;

            case "double":
                classInfos = "Var.double";
                break;

            case "String":
                classInfos = "Var.String";
                break;

            case "Map":
                classInfos = "Var.Map";
                break;

            case "ListInt":
                classInfos = "List.ListInt";
                break;

            case "ListString":
                classInfos = "List.ListString";
                break;

            case "ListMap":
                classInfos = "List.ListMap";
                break;

            case "List":
                classInfos = "List";
                break;

            case "View":
                break;

            case "TextView":
                classInfos = "View.Clickable.TextView";
                break;

            case "Button":
                classInfos = "View.Clickable.TextView.Button";
                break;

            case "EditText":
                classInfos = "View.Clickable.TextView.EditText";
                break;

            case "ImageView":
                classInfos = "View.Clickable.ImageView";
                break;

            case "CheckBox":
                classInfos = "View.Clickable.TextView.Button.CompoundButton.CheckBox";
                break;

            case "Spinner":
                classInfos = "View.AdapterView.AbsSpinner.Spinner";
                break;

            case "ListView":
                classInfos = "View.AdapterView.AbsListView.ListView";
                break;

            case "WebView":
                classInfos = "View.AbsoluteLayout.WebView";
                break;

            case "Switch":
                classInfos = "View.Clickable.TextView.Button.CompoundButton.Switch";
                break;

            case "SeekBar":
                classInfos = "View.SeekBar";
                break;

            case "CalendarView":
                classInfos = "View.FrameLayout.CalendarView";
                break;

            case "AdView":
                classInfos = "View.AdView";
                break;

            case "MapView":
                classInfos = "View.MapView";
                break;

            case "FloatingActionButton":
                classInfos = "View.Clickable.FloatingActionButton";
                break;

            case "LinearLayout":
                classInfos = "View.Clickable.ViewGroup.LinearLayout";
                break;

            case "ScrollView":
                classInfos = "View.ViewGroup.FrameLayout.ScrollView";
                break;

            case "HorizontalScrollView":
                classInfos = "View.ViewGroup.FrameLayout.HorizontalScrollView";
                break;

            case "Intent":
                classInfos = "Component.Intent";
                break;

            case "SharedPreferences":
                classInfos = "Component.SharedPreferences";
                break;

            case "Calendar":
                classInfos = "Component.Calendar";
                break;

            case "Vibrator":
                classInfos = "Component.Vibrator";
                break;

            case "Timer":
                classInfos = "Component.Timer";
                break;

            case "Dialog":
                classInfos = "Component.Dialog";
                break;

            case "MediaPlayer":
                classInfos = "Component.MediaPlayer";
                break;

            case "SoundPool":
                classInfos = "Component.SoundPool";
                break;

            case "ObjectAnimator":
                classInfos = "Component.ObjectAnimator";
                break;

            case "FirebaseDB":
                classInfos = "Component.FirebaseDB";
                break;

            case "FirebaseAuth":
                classInfos = "Component.FirebaseAuth";
                break;

            case "Gyroscope":
                classInfos = "Component.Gyroscope";
                break;

            case "FirebaseStorage":
                classInfos = "Component.FirebaseStorage";
                break;

            case "Camera":
                classInfos = "Component.Camera";
                break;

            case "FilePicker":
                classInfos = "Component.FilePicker";
                break;

            case "RequestNetwork":
                classInfos = "Component.RequestNetwork";
                break;

            case "ProgressBar":
                classInfos = "View.SeekBar.ProgressBar";
                break;

            case "TextToSpeech":
                classInfos = "Component.TextToSpeech";
                break;

            case "SpeechToText":
                classInfos = "Component.SpeechToText";
                break;

            case "BluetoothConnect":
                classInfos = "Component.BluetoothConnect";
                break;

            case "LocationManager":
                classInfos = "Component.LocationManager";
                break;

            case "RadioButton":
                classInfos = "View.Clickable.TextView.Button.CompoundButton.RadioButton";
                break;

            default:
                classInfos = BV.a(a);
        }

        b = classInfos.split("\\.");
    }
}
