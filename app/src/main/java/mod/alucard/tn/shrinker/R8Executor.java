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
import mod.agus.jcoderz.lib.FileUtil;

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
    
    public ArrayList getSourceFile(File file, ArrayList arrayList) {
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            file = files[i];
            if (file.isDirectory()) {
                getSourceFile(file, arrayList);
            } else if (file.getName().endsWith(".class")) {
                arrayList.add(file.getAbsolutePath());
            }
        }
        return arrayList;
    }
    
    public void compile() throws zy {
        String r8InOutputPath = mDp.f.t + File.separator + "Shrinked" + File.separator + "Dexes";
        if (!FileUtil.isExistFile(r8InOutputPath)) {
            FileUtil.makeDir(mDp.f.t + File.separator + "Shrinked" + File.separator + "Dexes");
        }
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
        args.add(r8InOutputPath);
        args.add("--pg-conf");
        args.add(getProguardRulesPath());
        args.addAll(getSourceFile(new File(mDp.f.t + File.separator + "classes"), new ArrayList<>()));
        try {
            R8.main((String[]) args.toArray(new String[0]));
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
