package mod.hey.studios.editor.view;

public class IdGenerator {

    public static String getLastPath(String str) {
        if (!str.contains(".")) {
            return str;
        }

        String[] split = str.split("\\.");
        return split[split.length - 1];
    }
}
