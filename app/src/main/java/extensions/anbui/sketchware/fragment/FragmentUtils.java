package extensions.anbui.sketchware.fragment;

import android.util.Log;

import extensions.anbui.sketchware.java.JavaFileUtils;
import extensions.anbui.sketchware.configs.Configs;
import extensions.anbui.sketchware.project.GetProjectInfo;

public class FragmentUtils {

    public static String returnFragmentgetItem(String input) {

        //Get the last line of the code.
        String[] lines = input.split("\n");
        String lastLine = lines[lines.length - 1].trim();

        //Check if the last line of code starts with return.
        if (lastLine.startsWith("return")) {
            //This means the user added a return at the end and will not add any code after to avoid causing errors.
            return input;
        } else {
            if (Configs.currentProjectID.isEmpty()) {
                return (input + "\nreturn null;");
            } else {
                if (JavaFileUtils.isJavaFileExistInProjectLab(Configs.currentProjectID, "FallbackFragment.java")) {
                    return (input + "\nreturn new " + GetProjectInfo.getPackageName(Configs.currentProjectID) + ".lab.FallbackFragment();");
                } else {
                    prepareFallbackFragmentFile(Configs.currentProjectID);
                    if (JavaFileUtils.isAdded) {
                        return (input + "\nreturn new " + GetProjectInfo.getPackageName(Configs.currentProjectID) + ".lab.FallbackFragment();");
                    } else {
                        return (input + "\nreturn null;");
                    }
                }
            }
        }
    }

    //Add FallbackFragment.java to the project's java folder.
    public static void prepareFallbackFragmentFile(String projectID) {
        String packageName = GetProjectInfo.getPackageName(projectID);
        Log.i("FragmentUtils", "prepareFallbackFragmentFile: " + packageName);
        if (!packageName.isEmpty()) {
            JavaFileUtils.addJavaFileToProjectLab(projectID, "FallbackFragment.java", fallbackFragmentCode(packageName));
        }
    }

    //Prepare content for FallbackFragment.java.
    public static String fallbackFragmentCode (String packagename) {
        return "package " + packagename + ".lab;\n" +
                "\n" +
                "import android.graphics.Color;\n" +
                "import android.os.Bundle;\n" +
                "import android.view.Gravity;\n" +
                "import android.view.LayoutInflater;\n" +
                "import android.view.View;\n" +
                "import android.view.ViewGroup;\n" +
                "import android.widget.TextView;\n" +
                "\n" +
                "import androidx.annotation.NonNull;\n" +
                "import androidx.fragment.app.Fragment;\n" +
                "\n" +
                "public class FallbackFragment extends Fragment {\n" +
                "    @Override\n" +
                "    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {\n" +
                "        TextView tv = new TextView(getContext());\n" +
                "        tv.setText(\"Problem with onFragmentAdded or ViewPager\\n\\nThere seems to be an issue with the return logic in onFragmentAdded. Please verify that each case for the position has a corresponding return statement, and ensure there is a fallback return at the end of the method. Also double-check that setFragmentAdapter and TabCount in your ViewPager setup are correctly configured.\");\n" +
                "        tv.setTextColor(Color.WHITE);\n" +
                "        tv.setGravity(Gravity.CENTER);\n" +
                "        tv.setTextSize(14);\n" +
                "        tv.setBackgroundColor(0xFF000000);\n" +
                "        return tv;\n" +
                "    }\n" +
                "}";
    }
}
