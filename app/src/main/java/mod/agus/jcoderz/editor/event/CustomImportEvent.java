package mod.agus.jcoderz.editor.event;

import java.util.ArrayList;
import mod.hilal.saif.events.EventsHandler;

public class CustomImportEvent {

  public static void a(ArrayList importList, String listener) {
    switch (listener) {
      case "OnFailureListener":
        importList.add("com.google.android.gms.tasks.OnFailureListener");
        break;
        
      case "OTPListener":
        importList.add("affan.ahmad.otp.OTPListener");
        break;
        
      case "OnSuccessListener":
        importList.add("com.google.android.gms.tasks.OnSuccessListener");
        break;
        
      case "OnCompleteListenerFCM":
        importList.add("com.google.android.gms.tasks.OnCompleteListener");
        importList.add("com.google.android.gms.tasks.Task");
        break;
    }
    EventsHandler.getImports(importList, str);
  }

}
