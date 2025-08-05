package extensions.anbui.sketchware.project;

public class ProjectUtils {
    public static String convertJavaNameToXMLName(String javaName) {
        //Example: HomePlusActivity.java
        if (javaName.endsWith(".java")) {
            //1: _Home_Plus_Activity.java
            //2: _home_plus_activity.java
            //3: home_plus_activity.java
            //4: home_plus
            return javaName.replaceAll("([A-Z])", "_$1")
                    .toLowerCase()
                    .replaceFirst("_", "")
                    .replace("_activity.java", "");
        }
        return javaName;
    }
}
