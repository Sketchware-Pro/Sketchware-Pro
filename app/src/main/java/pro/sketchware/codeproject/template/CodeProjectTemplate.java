package pro.sketchware.codeproject.template;

import java.io.File;

import pro.sketchware.codeproject.model.CodeProject;
import pro.sketchware.utility.FileUtil;

public class CodeProjectTemplate {

    public static void generate(CodeProject project) {
        String packagePath = project.getPackageName().replace('.', File.separatorChar);
        String javaDir = project.getJavaSourcePath() + File.separator + packagePath;
        String resDir = project.getResPath();
        String layoutDir = resDir + File.separator + "layout";
        String valuesDir = resDir + File.separator + "values";
        String drawableDir = resDir + File.separator + "drawable";

        FileUtil.makeDir(javaDir);
        FileUtil.makeDir(layoutDir);
        FileUtil.makeDir(valuesDir);
        FileUtil.makeDir(drawableDir);

        FileUtil.writeFile(project.getManifestPath(), generateManifest(project));
        FileUtil.writeFile(javaDir + File.separator + "MainActivity.java", generateMainActivity(project));
        FileUtil.writeFile(layoutDir + File.separator + "activity_main.xml", generateActivityMainLayout());
        FileUtil.writeFile(valuesDir + File.separator + "strings.xml", generateStrings(project));
        FileUtil.writeFile(valuesDir + File.separator + "colors.xml", generateColors());
        FileUtil.writeFile(valuesDir + File.separator + "styles.xml", generateStyles());
    }

    private static String generateManifest(CodeProject project) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                + "<manifest xmlns:android=\"http://schemas.android.com/apk/res/android\"\n"
                + "    package=\"" + project.getPackageName() + "\">\n"
                + "\n"
                + "    <uses-permission android:name=\"android.permission.INTERNET\" />\n"
                + "\n"
                + "    <application\n"
                + "        android:allowBackup=\"true\"\n"
                + "        android:label=\"@string/app_name\"\n"
                + "        android:theme=\"@style/AppTheme\">\n"
                + "\n"
                + "        <activity android:name=\".MainActivity\"\n"
                + "            android:exported=\"true\">\n"
                + "            <intent-filter>\n"
                + "                <action android:name=\"android.intent.action.MAIN\" />\n"
                + "                <category android:name=\"android.intent.category.LAUNCHER\" />\n"
                + "            </intent-filter>\n"
                + "        </activity>\n"
                + "\n"
                + "    </application>\n"
                + "</manifest>\n";
    }

    private static String generateMainActivity(CodeProject project) {
        return "package " + project.getPackageName() + ";\n"
                + "\n"
                + "import android.os.Bundle;\n"
                + "import androidx.appcompat.app.AppCompatActivity;\n"
                + "\n"
                + "public class MainActivity extends AppCompatActivity {\n"
                + "\n"
                + "    @Override\n"
                + "    protected void onCreate(Bundle savedInstanceState) {\n"
                + "        super.onCreate(savedInstanceState);\n"
                + "        setContentView(R.layout.activity_main);\n"
                + "    }\n"
                + "}\n";
    }

    private static String generateActivityMainLayout() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                + "<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n"
                + "    android:layout_width=\"match_parent\"\n"
                + "    android:layout_height=\"match_parent\"\n"
                + "    android:orientation=\"vertical\"\n"
                + "    android:gravity=\"center\">\n"
                + "\n"
                + "    <TextView\n"
                + "        android:layout_width=\"wrap_content\"\n"
                + "        android:layout_height=\"wrap_content\"\n"
                + "        android:text=\"Hello World!\"\n"
                + "        android:textSize=\"18sp\" />\n"
                + "\n"
                + "</LinearLayout>\n";
    }

    private static String generateStrings(CodeProject project) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                + "<resources>\n"
                + "    <string name=\"app_name\">" + escapeXml(project.getAppName()) + "</string>\n"
                + "</resources>\n";
    }

    private static String escapeXml(String input) {
        if (input == null) return "";
        // For Android string resources, use backslash escapes for ' and "
        // (aapt2 rejects &apos; and &quot; in <string> values)
        return input
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("'", "\\'")
                .replace("\"", "\\\"");
    }

    private static String generateColors() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                + "<resources>\n"
                + "    <color name=\"colorPrimary\">#3F51B5</color>\n"
                + "    <color name=\"colorPrimaryDark\">#303F9F</color>\n"
                + "    <color name=\"colorAccent\">#FF4081</color>\n"
                + "</resources>\n";
    }

    private static String generateStyles() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                + "<resources>\n"
                + "    <style name=\"AppTheme\" parent=\"Theme.AppCompat.Light.DarkActionBar\">\n"
                + "        <item name=\"colorPrimary\">@color/colorPrimary</item>\n"
                + "        <item name=\"colorPrimaryDark\">@color/colorPrimaryDark</item>\n"
                + "        <item name=\"colorAccent\">@color/colorAccent</item>\n"
                + "    </style>\n"
                + "</resources>\n";
    }
}
