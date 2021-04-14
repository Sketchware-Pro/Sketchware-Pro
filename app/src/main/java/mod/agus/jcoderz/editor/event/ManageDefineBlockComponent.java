package mod.agus.jcoderz.editor.event;

public class ManageDefineBlockComponent {
    public static String a(String str) {
        switch (str.hashCode()) {
            case 488073485:
                if (str.equals("PhoneAuthCredential")) {
                    return "PhoneAuthCredential";
                }
                return str;
            case 1764136498:
                if (str.equals("GoogleSignInAccount")) {
                    return "GoogleSignInAccount";
                }
                return str;
            case 1774120399:
                if (str.equals("FirebasePhoneAuth")) {
                    return "FirebasePhoneAuth";
                }
                return str;
            default:
                return str;
        }
    }

    public static String b(String str) {
        switch (str.hashCode()) {
            case 488073485:
                if (str.equals("PhoneAuthCredential")) {
                    return "p";
                }
                return "v";
            case 1764136498:
                if (!str.equals("GoogleSignInAccount")) {
                }
                break;
            case 1774120399:
                if (!str.equals("FirebasePhoneAuth")) {
                }
                break;
        }
        return str;
    }
}
