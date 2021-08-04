package mod.agus.jcoderz.editor.event;

import mod.hilal.saif.components.ComponentsHandler;

public class ManageEventComponent {

    public static String tabBreak = "\r\n";

    public static String a(String componentId, String previousFields, String componentName) {
        switch (componentId) {
            case "Videos":
                return previousFields + tabBreak
                        + "private File file_" + componentName + ";";

            case "FirebaseCloudMessage":
                return previousFields + tabBreak
                        + "private OnCompleteListener " + componentName + "_onCompleteListener;";

            case "com.facebook.ads.InterstitialAd":
                return previousFields + tabBreak
                        + "private InterstitialAdListener " + componentName + "_InterstitialAdListener;";

            case "PhoneAuthProvider.OnVerificationStateChangedCallbacks":
                return previousFields + tabBreak
                        + "private PhoneAuthProvider.ForceResendingToken " + componentName + "_resendToken;";

            case "DynamicLink":
                return previousFields + tabBreak
                        + "private OnSuccessListener " + componentName + "_onSuccessLink;" + tabBreak
                        + "private OnFailureListener " + componentName + "_onFailureLink;";

            case "com.facebook.ads.AdView":
                return previousFields + tabBreak
                        + "private AdListener " + componentName + "_AdListener;";

            case "RewardedVideoAd":
                // Shouldn't it be "private RewardedVideoAdListener _"?
                return previousFields + "\r\nprivate RewardedVideoAdListener  " + componentName + "_listener;";

            case "TimePickerDialog":
                return previousFields + "\r\nprivate TimePickerDialog.OnTimeSetListener " + componentName + "_listener;";

            default:
                return ComponentsHandler.extraVar(componentId, previousFields, componentName);
        }
    }

    public static String b(String componentId, String componentName) {
        switch (componentId) {
            case "Videos":
                return "file_" + componentName + " = FileUtil.createNewPictureFile(getApplicationContext());" + tabBreak
                        + "Uri _uri_" + componentName + " = null;" + tabBreak
                        + "if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {" + tabBreak
                        + "_uri_" + componentName + " = FileProvider.getUriForFile(getApplicationContext(), " +
                        "getApplicationContext().getPackageName() + \".provider\", file_" + componentName + ");" + tabBreak
                        + "}" + tabBreak
                        + "else {" + tabBreak
                        + "_uri_" + componentName + " = Uri.fromFile(file_" + componentName + ");" + tabBreak
                        + componentName + ".putExtra(MediaStore.EXTRA_OUTPUT, _uri_" + componentName + ");" + tabBreak
                        + componentName + ".addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);";

            case "TimePickerDialog":
                return componentName + " = new TimePickerDialog(this, " + componentName + "_listener, Calendar.HOUR_OF_DAY, Calendar.MINUTE, false);";

            case "DatePickerDialog":
                return componentName + " = new DatePickerDialog(this);";

            default:
                return ComponentsHandler.defineExtraVar(componentId, componentName);
        }
    }
}
