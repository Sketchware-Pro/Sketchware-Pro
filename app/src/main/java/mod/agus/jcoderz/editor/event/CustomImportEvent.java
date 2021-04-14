package mod.agus.jcoderz.editor.event;

import java.util.ArrayList;
import mod.hilal.saif.events.EventsHandler;

public class CustomImportEvent {
    public static void a(ArrayList arrayList, String str) {
        switch (str.hashCode()) {
            case -1289633697:
                if (str.equals("OnFailureListener")) {
                    arrayList.add("com.google.android.gms.tasks.OnFailureListener");
                    break;
                }
                break;
            case -1004134177:
                if (str.equals("OTPListener")) {
                    arrayList.add("affan.ahmad.otp.OTPListener");
                    break;
                }
                break;
            case -838515240:
                if (str.equals("OnSuccessListener")) {
                    arrayList.add("com.google.android.gms.tasks.OnSuccessListener");
                    break;
                }
                break;
            case 1448799876:
                if (str.equals("OnCompleteListenerFCM")) {
                    arrayList.add("com.google.android.gms.tasks.OnCompleteListener");
                    arrayList.add("com.google.android.gms.tasks.Task");
                    break;
                }
                break;
        }
        EventsHandler.getImports(arrayList, str);
    }
}
