package mod.agus.jcoderz.editor.event;

public class ManageDefineBlockComponent {

    public static String a(String name) {
        switch (name) {
            case "PhoneAuthCredential":
                return "PhoneAuthCredential";

            case "GoogleSignInAccount":
                return "GoogleSignInAccount";

            case "FirebasePhoneAuth":
                return "FirebasePhoneAuth";

            default:
                return name;
        }
    }

    public static String b(String name) {
        switch (name) {
            case "PhoneAuthCredential":
            case "GoogleSignInAccount":
            case "FirebasePhoneAuth":
                return "p";

            default:
                return "v";
        }
    }
}
