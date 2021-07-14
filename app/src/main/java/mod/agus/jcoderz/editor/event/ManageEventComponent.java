package mod.agus.jcoderz.editor.event;

import mod.hilal.saif.components.ComponentsHandler;

public class ManageEventComponent {

    public static String tabBreak = "\r\n";

    public static String a(String componentId, String str2, String componentName) {
        switch (componentId) {
            case "Videos":
                return str2 + tabBreak
                        + "private File file_" + componentName + ";";

            case "FirebaseCloudMessage":
                return str2 + tabBreak
                        + "private OnCompleteListener " + componentName + "_onCompleteListener;";

            case "com.facebook.ads.InterstitialAd":
                return str2 + tabBreak
                        + "private InterstitialAdListener " + componentName + "_InterstitialAdListener;";

            case "FirebaseAuth":
                return str2 + tabBreak
                        + "private OnCompleteListener<Void> " + componentName + "_updateEmailListener;" + tabBreak
                        + "private OnCompleteListener<Void> " + componentName + "_updatePasswordListener;" + tabBreak
                        + "private OnCompleteListener<Void> " + componentName + "_emailVerificationSentListener;" + tabBreak
                        + "private OnCompleteListener<Void> " + componentName + "_deleteUserListener;" + tabBreak
                        + "private OnCompleteListener<Void> " + componentName + "_updateProfileListener;" + tabBreak
                        + "private OnCompleteListener<AuthResult> " + componentName + "_phoneAuthListener;" + tabBreak
                        + "private OnCompleteListener<AuthResult> " + componentName + "_googleSignInListener;";

            case "PhoneAuthProvider.OnVerificationStateChangedCallbacks":
                return str2 + tabBreak
                        + "private PhoneAuthProvider.ForceResendingToken " + componentName + "_resendToken;";

            case "DynamicLink":
                return str2 + tabBreak
                        + "private OnSuccessListener " + componentName + "_onSuccessLink;" + tabBreak
                        + "private OnFailureListener " + componentName + "_onFailureLink;";

            case "com.facebook.ads.AdView":
                return str2 + tabBreak
                        + "private AdListener " + componentName + "_AdListener;";

            default:
                return ComponentsHandler.extraVar(componentId, str2, componentName);
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
