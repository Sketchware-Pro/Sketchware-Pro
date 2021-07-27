package mod.alucard.tn.shrinker;

import android.os.Environment;
import android.util.Log;

import com.android.tools.r8.R8;
import com.besome.sketch.design.DesignActivity;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import a.a.a.Dp;
import a.a.a.zy;

public class R8Executor {

    private static final String TAG = "R8Executor";
    private final DesignActivity.a buildingDialog;
    private final Dp mDp;

    public R8Executor(Dp dp, DesignActivity.a Dialog) {
        buildingDialog = Dialog;
        mDp = dp;
    }

    public void preparingEnvironment() {
        long savedTimeMillis = System.currentTimeMillis();
        buildingDialog.c("Compiling classes with R8...");
        Log.d(TAG + ":c", "Compiling classes with R8 took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
    }

    public ArrayList<String> getSourceFile(File file, ArrayList<String> arrayList) {
        if (file == null) {
            return arrayList;
        }

        File[] files = file.listFiles();

        if (files == null) {
            return arrayList;
        }

        for (File value : files) {
            file = value;
            if (file.isDirectory()) {
                getSourceFile(file, arrayList);
            } else if (file.getName().endsWith(".class")) {
                arrayList.add(file.getAbsolutePath());
            }
        }

        return arrayList;
    }

    public void compile() throws zy {
        String R8OutputPath = mDp.f.t;
        String androidJarPath = mDp.settings.getValue("android_sdk", "");
        ArrayList<String> args = new ArrayList<>();

        args.add("--debug");
        args.add("--dex");
        args.add("--lib");

        if (androidJarPath.isEmpty()) {
            androidJarPath = mDp.o;
        }

        args.add(androidJarPath);
        args.add("--output");

        if (R8OutputPath.isEmpty()) {
            R8OutputPath = mDp.f.c + File.separator + "bin";
        }

        args.add(R8OutputPath);
        args.add("--pg-conf");
        args.add(getProguardRulesPath());
        args.addAll(getSourceFile(new File(mDp.f.t + File.separator + "classes"), new ArrayList<>()));

        try {
            R8.main(args.toArray(new String[0]));
        } catch (Exception e) {
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            buildingDialog.c(stringWriter.toString());
            Log.d(TAG, stringWriter.toString());
        }
    }

    private String getProguardRulesPath() {
        return (new File(Environment.getExternalStorageDirectory(), ".sketchware/data/" + mDp.f.b + "/proguard-rules.pro")).getAbsolutePath();
    }
}
