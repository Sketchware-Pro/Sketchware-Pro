package mod.agus.jcoderz.java;

import com.besome.sketch.design.DesignActivity;

import org.eclipse.jdt.internal.compiler.batch.Main;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import a.a.a.Dp;
import a.a.a.Jp;
import mod.agus.jcoderz.lib.FileUtil;

public class JavaCompiler {

    private final StringWriter errWriter = new StringWriter();
    private final DesignActivity.a mDialog;
    private final Dp mDp;
    private final StringWriter outWriter = new StringWriter();

    public JavaCompiler(Dp dp, DesignActivity.a aVar) {
        this.mDp = dp;
        this.mDialog = aVar;
    }

    public void compileJava() {
        String stringBuffer;
        String value = this.mDp.settings.getValue("java_version", "1.7");
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("-source");
        arrayList.add(value.isEmpty() ? "1.7" : value);
        arrayList.add("-target");
        arrayList.add(value.isEmpty() ? "1.7" : value);
        arrayList.add("-nowarn");
        arrayList.add("-deprecation");
        arrayList.add("-inlineJSR");
        arrayList.add("-genericsignature");
        arrayList.add("-O");
        arrayList.add("-d");
        arrayList.add(this.mDp.f.u);
        arrayList.add("-bootclasspath");
        String d = this.mDp.d();
        if (d.endsWith(":"))
            stringBuffer = d + this.mDp.l.getAbsolutePath() + this.mDp.c + "rt.jar";
        else {
            stringBuffer = d + ":" + this.mDp.l.getAbsolutePath() + this.mDp.c + "rt.jar";
        }
        arrayList.add(stringBuffer);
        arrayList.add("-cp");
        arrayList.add(stringBuffer);
        arrayList.add("-proc:none");
        arrayList.add("-parameters");
        arrayList.add("-sourcepath");
        arrayList.add(this.mDp.f.y);
        arrayList.add(this.mDp.f.o);
        arrayList.add(this.mDp.f.q);
        if (FileUtil.isExistFile(this.mDp.fpu.getPathJava(this.mDp.f.b))) {
            arrayList.add(this.mDp.fpu.getPathJava(this.mDp.f.b));
        }
        if (FileUtil.isExistFile(this.mDp.fpu.getPathBroadcast(this.mDp.f.b))) {
            arrayList.add(this.mDp.fpu.getPathBroadcast(this.mDp.f.b));
        }
        if (FileUtil.isExistFile(this.mDp.fpu.getPathService(this.mDp.f.b))) {
            arrayList.add(this.mDp.fpu.getPathService(this.mDp.f.b));
        }
        for (Jp next : this.mDp.n.a()) {
            if (next.c()) {
                arrayList.add(this.mDp.f.v + this.mDp.c + next.b().replace(".", this.mDp.c) + this.mDp.c + "R.java");
            }
        }
        arrayList.addAll(this.mDp.mll.getGenLocalLibrary());
        try {
            Main main = new Main(new PrintWriter(this.outWriter), new PrintWriter(this.errWriter), false);
            main.compile((String[]) arrayList.toArray(new String[0]));
            if (main.globalErrorsCount > 0) {
                this.mDialog.a(this.errWriter.toString());
            }
        } catch (Exception e) {
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            this.mDialog.a(stringWriter.toString());
        }
    }
}