package mod.agus.jcoderz.editor.event;

import mod.hilal.saif.components.ComponentsHandler;

public class ManageEventComponent {
    public static String tabBreak = "\r\n";

    public static String a(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder();
        switch (str.hashCode()) {
            case -1732810888:
                if (str.equals("Videos")) {
                    sb.append(str2);
                    sb.append(tabBreak);
                    sb.append("private File file_");
                    sb.append(str3);
                    sb.append(";");
                    return sb.toString();
                }
                break;
            case -1231229991:
                if (str.equals("FirebaseCloudMessage")) {
                    sb.append(str2);
                    sb.append(tabBreak);
                    sb.append("private OnCompleteListener ");
                    sb.append(str3);
                    sb.append("_onCompleteListener;");
                    return sb.toString();
                }
                break;
            case -744536376:
                if (str.equals("com.facebook.ads.InterstitialAd")) {
                    sb.append(str2);
                    sb.append(tabBreak);
                    sb.append("private InterstitialAdListener ");
                    sb.append(str3);
                    sb.append("_InterstitialAdListener;");
                    return sb.toString();
                }
                break;
            case 225459311:
                if (str.equals("FirebaseAuth")) {
                    sb.append(str2);
                    sb.append(tabBreak);
                    sb.append("private OnCompleteListener<Void> ");
                    sb.append(str3);
                    sb.append("_updateEmailListener;");
                    sb.append(tabBreak);
                    sb.append("private OnCompleteListener<Void> ");
                    sb.append(str3);
                    sb.append("_updatePasswordListener;");
                    sb.append(tabBreak);
                    sb.append("private OnCompleteListener<Void> ");
                    sb.append(str3);
                    sb.append("_emailVerificationSentListener;");
                    sb.append(tabBreak);
                    sb.append("private OnCompleteListener<Void> ");
                    sb.append(str3);
                    sb.append("_deleteUserListener;");
                    sb.append(tabBreak);
                    sb.append("private OnCompleteListener<Void> ");
                    sb.append(str3);
                    sb.append("_updateProfileListener;");
                    sb.append(tabBreak);
                    sb.append("private OnCompleteListener<AuthResult> ");
                    sb.append(str3);
                    sb.append("_phoneAuthListener;");
                    sb.append(tabBreak);
                    sb.append("private OnCompleteListener<AuthResult> ");
                    sb.append(str3);
                    sb.append("_googleSignInListener;");
                    return sb.toString();
                }
                break;
            case 550741834:
                if (str.equals("PhoneAuthProvider.OnVerificationStateChangedCallbacks")) {
                    sb.append(str2);
                    sb.append(tabBreak);
                    sb.append("private PhoneAuthProvider.ForceResendingToken ");
                    sb.append(str3);
                    sb.append("_resendToken;");
                    return sb.toString();
                }
                break;
            case 1408955577:
                if (str.equals("DynamicLink")) {
                    sb.append(str2);
                    sb.append(tabBreak);
                    sb.append("private OnSuccessListener ");
                    sb.append(str3);
                    sb.append("_onSuccessLink;");
                    sb.append(tabBreak);
                    sb.append("private OnFailureListener ");
                    sb.append(str3);
                    sb.append("_onFailureLink;");
                    return sb.toString();
                }
                break;
            case 1472283236:
                break;
            case 1908493505:
                if (str.equals("com.facebook.ads.AdView")) {
                    sb.append(str2);
                    sb.append(tabBreak);
                    sb.append("private AdListener ");
                    sb.append(str3);
                    sb.append("_AdListener;");
                    return sb.toString();
                }
                break;
        }
        return ComponentsHandler.extraVar(str, str2, str3);
    }

    public static String b(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        switch (str.hashCode()) {
            case -1732810888:
                if (str.equals("Videos")) {
                    sb.append("file_");
                    sb.append(str2);
                    sb.append(" = FileUtil.createNewPictureFile(getApplicationContext());");
                    sb.append(tabBreak);
                    sb.append("Uri _uri_");
                    sb.append(str2);
                    sb.append(" = null;");
                    sb.append(tabBreak);
                    sb.append("if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {");
                    sb.append(tabBreak);
                    sb.append("_uri_");
                    sb.append(str2);
                    sb.append("= FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + \".provider\", file_");
                    sb.append(str2);
                    sb.append(");");
                    sb.append(tabBreak);
                    sb.append("}");
                    sb.append(tabBreak);
                    sb.append("else {");
                    sb.append(tabBreak);
                    sb.append("_uri_");
                    sb.append(str2);
                    sb.append(" = Uri.fromFile(file_");
                    sb.append(str2);
                    sb.append(");");
                    sb.append(tabBreak);
                    sb.append("}");
                    sb.append(tabBreak);
                    sb.append(str2);
                    sb.append(".putExtra(MediaStore.EXTRA_OUTPUT, _uri_");
                    sb.append(str2);
                    sb.append(");");
                    sb.append(tabBreak);
                    sb.append(str2);
                    sb.append(".addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);");
                    return sb.toString();
                }
                break;
            case 313126659:
                if (str.equals("TimePickerDialog")) {
                    sb.append(str2);
                    sb.append("= new TimePickerDialog(this, ");
                    sb.append(str2);
                    sb.append("_listener, Calendar.getInstance().HOUR_OF_DAY, Calendar.getInstance().MINUTE, false);");
                    return sb.toString();
                }
                break;
            case 1472283236:
                if (str.equals("DatePickerDialog")) {
                    sb.append(str2);
                    sb.append("= new DatePickerDialog(this);");
                    return sb.toString();
                }
                break;
        }
        return ComponentsHandler.defineExtraVar(str, str2);
    }
}
