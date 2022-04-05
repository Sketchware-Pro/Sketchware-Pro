package mod.agus.jcoderz.handle.code;

import java.util.ArrayList;
import java.util.HashMap;

import mod.agus.jcoderz.handle.component.ConstVarComponent;

public class CodeResult {
    /**
     * Simply calls {@link FormatterCode#a(int, String, String, String)}.
     */
    public static String b(int componentId, String componentName, String onSuccessCode, String onCancelledCode) {
        return "case REQ_CD_" + componentName.toUpperCase() + ":\r\n" +
                "if _resultCode == Activity.RESULT_OK) {\r\n" +
                CodeResult.a(componentId, componentName) + "\r\n" +
                onSuccessCode + "\r\n" +
                "} else {\r\n" +
                onCancelledCode + "\r\n" +
                "}\r\n" +
                "break;";
    }

    public static String c(ConstVarComponent component) {
        HashMap<String, ArrayList<String>> param = component.param;
        if (param == null || !param.containsKey("OnResultBillingResponse")) {
            return "";
        }

        ArrayList<String> arrayList = param.get("OnResultBillingResponse");
        return "if (!" + arrayList.get(0) + ".handleActivityResult(_requestCode, _resultCode, _data))";
    }
}
