package mod.agus.jcoderz.handle.component;

public class ConstVarWidget {

    /**
     * @return Field declaration for FirebaseCloudMessage (that's just <code>""</code>)
     * or a component with an empty <code>componentNameId</code>
     */
    public static String a(String fieldDeclaration, String componentNameId, String componentName) {
        if ("FirebaseCloudMessage".equals(componentNameId)) {
            return "";
        }
        return fieldDeclaration + " " + componentNameId + " " + componentName + ";";
    }

    /**
     * @return Field type for the Videos component, or the parameter if it's not <code>"Videos"</code>
     */
    public static String b(String componentNameId) {
        if ("Videos".equals(componentNameId)) {
            return "Intent";
        }
        return componentNameId;
    }
}
