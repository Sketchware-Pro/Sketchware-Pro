package mod.agus.jcoderz.handle.component;

public class ConstVarWidget {

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
