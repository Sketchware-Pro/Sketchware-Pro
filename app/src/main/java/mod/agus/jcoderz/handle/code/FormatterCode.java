package mod.agus.jcoderz.handle.code;

public class FormatterCode {

    public static String a(int componentId, String componentName, String onSuccessCode, String onCancelledCode) {
        return "case REQ_CD_" + componentName.toUpperCase() + ":\r\n" +
                "if _resultCode == Activity.RESULT_OK) {\r\n" +
                CodeResult.a(componentId, componentName) + "\r\n" +
                onSuccessCode + "\r\n" +
                "} else {\r\n" +
                onCancelledCode + "\r\n" +
                "}\r\n" +
                "break;";
    }
}
