package a.a.a;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.android.sdklib.build.ApkBuilder;
import com.android.tools.r8.D8;
import com.github.megatronking.stringfog.plugin.StringFogClassInjector;
import com.github.megatronking.stringfog.plugin.StringFogMappingPrinter;

import org.spongycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import kellinwood.security.zipsigner.ZipSigner;
import kellinwood.security.zipsigner.optional.CustomKeySigner;
import kellinwood.security.zipsigner.optional.KeyStoreFileManager;
import mod.agus.jcoderz.builder.DexMerge;
import mod.agus.jcoderz.command.ProcessingFiles;
import mod.agus.jcoderz.dex.Dex;
import mod.agus.jcoderz.dx.command.dexer.Main;
import mod.agus.jcoderz.dx.merge.CollisionPolicy;
import mod.agus.jcoderz.dx.merge.DexMerger;
import mod.agus.jcoderz.editor.library.ExtLibSelected;
import mod.agus.jcoderz.editor.manage.library.locallibrary.ManageLocalLibrary;
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.alucard.tn.compiler.ResourceCompiler;
import mod.hey.studios.build.BuildSettings;
import mod.hey.studios.project.ProjectSettings;
import mod.hey.studios.project.proguard.ProguardHandler;
import mod.hey.studios.util.SystemLogPrinter;
import mod.jbk.util.LogUtil;
import proguard.ProGuard;

public class Dp {

    public final static String TAG = "AppBuilder";
    /**
     * Default minSdkVersion (?)
     */
    public final String a = "21";
    /**
     * Default targetSdkVersion (?)
     */
    public final String b = "28";
    public final String c = File.separator;
    public final String m = "libs";
    public File aapt2Dir;
    public BuildSettings build_settings;
    /**
     * Command(s) to execute after extracting AAPT/AAPT2 (fill in 2 with the file name before using)
     */
    public String[] d = {"chmod", "744", ""};
    public Context e;
    public yq f;
    public FilePathUtil fpu;
    public oB g;
    public File h;
    /**
     * File object that represents aapt
     */
    public File i;
    public Fp j;
    public StringBuffer k = new StringBuffer();
    /**
     * Extracted built-in libraries directory
     */
    public File l;
    public DexMerge merge;
    public ManageLocalLibrary mll;
    public Kp n;
    public String o;
    public ProguardHandler proguard;
    public ProjectSettings settings;
    ArrayList<String> dexesGenerated;
    ArrayList<String> extraDexes;

    public Dp(Context context, yq yqVar) {
        SystemLogPrinter.start();
        e = context;
        f = yqVar;
        g = new oB(false);
        j = new Fp();
        h = new File(context.getFilesDir(), "tmp");
        if (!h.exists()) {
            h.mkdir();
        }
        i = new File(h, "aapt");
        aapt2Dir = new File(h, "aapt2");
        l = new File(context.getFilesDir(), "libs");
        n = new Kp();
        o = new File(l, "android.jar").getAbsolutePath();
        mll = new ManageLocalLibrary(f.b);
        fpu = new FilePathUtil();
        settings = new ProjectSettings(f.b);
        proguard = new ProguardHandler(f.b);
        build_settings = new BuildSettings(f.b);
        o = build_settings.getValue("android_jar", o);
    }

    public void a() throws Exception {
        long currentTimeMillis = System.currentTimeMillis();
        b();
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        Log.d(TAG, "aapt took " + currentTimeMillis2 + " ms");
    }

    public void a(iI iIVar, String str) {
        ZipSigner b2 = iIVar.b(new CB().a(str));
        yq yqVar = f;
        b2.signZip(yqVar.G, yqVar.I);
    }

    public final void a(String str, ArrayList<String> arrayList) throws Exception {
        dexLibraries(str, arrayList);
    }

    /**
     * Copy a file from assets to assets
     * @param source Source file to copy
     * @param to     Target to copy the file to
     */
    public final boolean a(String source, String to) {
        long j2;
        File file = new File(to);
        long a2 = g.a(e, source);
        if (file.exists()) {
            j2 = file.length();
        } else {
            j2 = 0;
        }
        if (a2 == j2) {
            return false;
        }
        g.a(file);
        g.a(e, source, to);
        return true;
    }

    public void runAapt2() {
        /*
        Field[] fields = f.getClass().getDeclaredFields();
        StringBuilder toLog = new StringBuilder("Dumping yq's fields: ");
        for (int i = 0; i < f.getClass().getDeclaredFields().length; i++) {
            if (toLog.toString().length() > 4096) {
                Log.d(TAG, toLog.substring(0, 4096));
                String saved = toLog.substring(4096);
                toLog = new StringBuilder(saved);
            }
            if (i == 0) {
                toLog.append(fields[i].getName()).append("=");
                try {
                    toLog.append(fields[i].get(f).toString());
                } catch (IllegalAccessException e) {
                    toLog.append("???");
                } catch (NullPointerException e) {
                    toLog.append("null");
                }
            } else if (i == (fields.length - 1)) {
                toLog.append(", ").append(fields[i].getName()).append("=");
                try {
                    toLog.append(fields[i].get(f).toString());
                } catch (IllegalAccessException e) {
                    toLog.append("???");
                } catch (NullPointerException e) {
                    toLog.append("null");
                }
                Log.d(TAG, toLog.toString());
            } else {
                toLog.append(", ").append(fields[i].getName()).append("=");
                try {
                    toLog.append(fields[i].get(f).toString());
                } catch (IllegalAccessException e) {
                    toLog.append("???");
                } catch (NullPointerException e) {
                    toLog.append("null");
                }
            }
        }
        */

        ResourceCompiler compiler = new ResourceCompiler(this, aapt2Dir);
        compiler.compile();
        compiler.link();
    }

    public void b() throws Exception {
        if (true) {
            runAapt2();
            return;
        }
        String str;
        String str2;
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(i.getAbsolutePath());
        arrayList.add("package");
        String e2 = e();
        if (!e2.isEmpty()) {
            arrayList.add("--extra-packages");
            arrayList.add(e2);
        }
        arrayList.add("--min-sdk-version");
        arrayList.add(settings.getValue("min_sdk", "21"));
        arrayList.add("--target-sdk-version");
        arrayList.add(settings.getValue("target_sdk", "28"));
        arrayList.add("--version-code");
        String str3 = f.l;
        if (str3 == null || str3.isEmpty()) {
            str = "1";
        } else {
            str = f.l;
        }
        arrayList.add(str);
        arrayList.add("--version-name");
        String str4 = f.m;
        if (str4 == null || str4.isEmpty()) {
            str2 = "1.0";
        } else {
            str2 = f.m;
        }
        arrayList.add(str2);
        arrayList.add("--auto-add-overlay");
        arrayList.add("--generate-dependencies");
        arrayList.add("-f");
        arrayList.add("-m");
        arrayList.add("--non-constant-id");
        arrayList.add("--output-text-symbols");
        arrayList.add(f.t);
        if (f.N.g) {
            arrayList.add("--no-version-vectors");
        }
        arrayList.add("-S");
        arrayList.add(f.w);
        for (String s : mll.getResLocalLibrary()) {
            arrayList.add("-S");
            arrayList.add(s);
        }
        if (FileUtil.isExistFile(fpu.getPathResource(f.b))) {
            arrayList.add("-S");
            arrayList.add(fpu.getPathResource(f.b));
        }
        arrayList.add("-A");
        arrayList.add(f.A);
        if (FileUtil.isExistFile(fpu.getPathAssets(f.b))) {
            arrayList.add("-A");
            arrayList.add(fpu.getPathAssets(f.b));
        }
        for (String str5 : mll.getAssets()) {
            arrayList.add("-A");
            arrayList.add(str5);
        }
        for (Jp next : n.a()) {
            if (next.c()) {
                String str6 = l.getAbsolutePath() + c + "libs" + c + next.a() + c + "res";
                arrayList.add("-S");
                arrayList.add(str6);
            }
        }
        for (Jp next2 : n.a()) {
            if (next2.d()) {
                String str7 = l.getAbsolutePath() + c + "libs" + c + next2.a() + c + "assets";
                arrayList.add("-A");
                arrayList.add(str7);
            }
        }
        arrayList.add("-J");
        arrayList.add(f.v);
        arrayList.add("-G");
        arrayList.add(f.aapt_rules);
        arrayList.add("-M");
        arrayList.add(f.r);
        arrayList.add("-I");
        arrayList.add(o);
        arrayList.add("-F");
        arrayList.add(f.C);
        if (j.a(arrayList.toArray(new String[0])) != 0) {
            throw new zy(j.a.toString());
        }
    }

    public void b(String str, String str2) {
        Security.addProvider(new BouncyCastleProvider());
        CustomKeySigner.signZip(new ZipSigner(), wq.j(), str.toCharArray(), str2, str.toCharArray(), "SHA1WITHRSA", f.G, f.I);
    }

    public boolean isD8Enabled() {
        return build_settings.getValue("dexer", "Dx").equals("D8");
    }

    public String getDxRunningText() {
        return (isD8Enabled() ? "D8" : "Dx") + " is running...";
    }

    /**
     * Compile Java code
     * @throws Exception Thrown if the compiler has any problems compiling
     */
    public void c() throws Exception {
        if (isD8Enabled()) {
            long savedTimeMillis = System.currentTimeMillis();
            ArrayList<String> args = new ArrayList<>();
            args.add("--release");
            args.add("--intermediate");
            args.add("--min-api");
            args.add(settings.getValue("min_sdk", "21"));
            args.add("--lib");
            args.add(o);
            args.add("--output");
            args.add(f.t);
            if (proguard.isProguardEnabled()) {
                args.add(f.classes_proguard);
            } else {
                args.addAll(ProcessingFiles.getListResource(f.u));
            }
            try {
                Log.d(TAG, "Running D8 with these arguments: " + args.toString());
                D8.main(args.toArray(new String[0]));
                Log.d(TAG, "D8 took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
            } catch (Exception e) {
                Log.e(TAG, "D8 error: " + e.getMessage(), e);
                throw e;
            }
        } else {
            Main.dexOutputArrays = new ArrayList<>();
            Main.dexOutputFutures = new ArrayList<>();
            long savedTimeMillis = System.currentTimeMillis();
            ArrayList<String> args = new ArrayList<>();
            args.add("--debug");
            args.add("--verbose");
            args.add("--multi-dex");
            args.add("--output=" + f.t);
            if (proguard.isProguardEnabled()) {
                args.add(f.classes_proguard);
            } else {
                args.add(f.u);
            }
            try {
                Log.d(TAG, "Running Dx with these arguments: " + args.toString());
                Main.main(args.toArray(new String[0]));
                Log.d(TAG, "Dx took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
            } catch (Exception e) {
                Log.e(TAG, "Dx error: " + e.getMessage(), e);
                throw e;
            }
        }
        findExtraDexes();
    }

    public final String d() {
        StringBuilder sb = new StringBuilder();
        sb.append(f.v).append(":").append(o);
        if (!build_settings.getValue("no_http_legacy", "false").equals("true")) {
            sb.append(":");
            sb.append(l.getAbsolutePath());
            sb.append(c);
            sb.append("libs");
            sb.append(c);
            sb.append("http-legacy-android-28");
            sb.append(c);
            sb.append("classes.jar");
        }
        sb.append(":");
        sb.append(l.getAbsolutePath());
        sb.append(c);
        sb.append("jdk");
        sb.append(c);
        sb.append("rt.jar");
        StringBuilder sb2 = new StringBuilder(sb.toString());
        for (Jp next : n.a()) {
            sb2.append(":").append(l.getAbsolutePath()).append(c).append("libs").append(c).append(next.a()).append(c).append("classes.jar");
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append(mll.getJarLocalLibrary());
        if (!build_settings.getValue("classpath", "").equals("")) {
            sb3.append(":");
            sb3.append(build_settings.getValue("classpath", ""));
        }
        return sb3.toString();
    }

    public final String classpath() {
        StringBuilder sb = new StringBuilder();
        sb.append(f.v).append(":").append(o);
        if (!build_settings.getValue("no_http_legacy", "false").equals("true")) {
            sb.append(":");
            sb.append(l.getAbsolutePath());
            sb.append(c);
            sb.append("libs");
            sb.append(c);
            sb.append("http-legacy-android-28");
            sb.append(c);
            sb.append("classes.jar");
        }
        sb.append(":");
        sb.append(l.getAbsolutePath());
        sb.append(c);
        sb.append("jdk");
        sb.append(c);
        sb.append("rt.jar");
        StringBuilder sb2 = new StringBuilder(sb.toString());
        for (Jp next : n.a()) {
            sb2.append(":").append(l.getAbsolutePath()).append(c).append("libs").append(c).append(next.a()).append(c).append("classes.jar");
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        for (HashMap<String, Object> hashMap : mll.list) {
            String obj = hashMap.get("name").toString();
            if (hashMap.containsKey("jarPath") && !proguard.libIsProguardFMEnabled(obj)) {
                sb3.append(":");
                sb3.append(hashMap.get("jarPath").toString());
            }
        }
        if (!build_settings.getValue("classpath", "").equals("")) {
            sb3.append(":");
            sb3.append(build_settings.getValue("classpath", ""));
        }
        return sb3.toString();
    }

    public final void dexLibraries(String str, ArrayList<String> arrayList) throws Exception {
        dexesGenerated = new ArrayList<>();
        int findLastDexNo = findLastDexNo();
        ArrayList<Dex> arrayList2 = new ArrayList<>();
        int i2 = 0;
        for (String s : arrayList) {
            Dex dex = new Dex(new FileInputStream(s));
            int size = dex.methodIds().size();
            if (size + i2 >= 65536) {
                mergeDexes(str.replace("classes2.dex", "classes" + findLastDexNo + ".dex"), arrayList2);
                arrayList2.clear();
                arrayList2.add(dex);
                i2 = size;
                findLastDexNo++;
            } else {
                arrayList2.add(dex);
                i2 += size;
            }
        }
        if (arrayList2.size() > 0) {
            mergeDexes(str.replace("classes2.dex", "classes" + findLastDexNo + ".dex"), arrayList2);
        }
    }

    /**
     * Get extra packages used in this project, needed for AAPT/AAPT2.
     */
    public final String e() {
        Iterator<Jp> it = n.a().iterator();
        StringBuilder str = new StringBuilder();
        while (it.hasNext()) {
            Jp next = it.next();
            if (next.c()) {
                str.append(next.b()).append(":");
            }
        }
        return str + mll.getPackageNameLocalLibrary();
    }

    /**
     * Run javac to compile Java classes from Java source code
     * @throws Throwable Thrown when Eclipse has problems compiling
     */
    public void f() throws Throwable {
        long currentTimeMillis = System.currentTimeMillis();
        /* System.out for Eclipse compiler */
        PrintWriter printWriter = new PrintWriter(new Dp.b());
        Dp.a aVar = new Dp.a();
        /* System.err for Eclipse compiler */
        PrintWriter printWriter2 = new PrintWriter(aVar);
        try {
            ArrayList<String> args = new ArrayList<>();
            args.add("-" + build_settings.getValue("java_ver", "1.7"));
            args.add("-nowarn");
            if (build_settings.getValue("no_warn", "false").equals("false")) {
                args.add("-deprecation");
            }
            args.add("-d");
            args.add(f.u);
            args.add("-cp");
            args.add(d());
            args.add("-proc:none");
            args.add("-sourcepath");
            args.add(f.y);
            args.add(f.o);
            args.add(f.q);
            if (FileUtil.isExistFile(fpu.getPathJava(f.b))) {
                args.add(fpu.getPathJava(f.b));
            }
            if (FileUtil.isExistFile(fpu.getPathBroadcast(f.b))) {
                args.add(fpu.getPathBroadcast(f.b));
            }
            if (FileUtil.isExistFile(fpu.getPathService(f.b))) {
                args.add(fpu.getPathService(f.b));
            }

            /* Adding built-in libraries' R.java files */
            for (Jp next : n.a()) {
                if (next.c()) {
                    args.add(f.v + File.separator + next.b().replace(".", File.separator) + File.separator + "R.java");
                }
            }
            /* Adding local libraries' R.java files */
            args.addAll(mll.getGenLocalLibrary());
            org.eclipse.jdt.internal.compiler.batch.Main main = new org.eclipse.jdt.internal.compiler.batch.Main(printWriter, printWriter2, false, null, null);
            LogUtil.log(TAG, "Running Eclipse compiler with these arguments: ", "About to log Eclipse compiler's arguments in multiple lines because of length.", args);
            main.compile(args.toArray(new String[0]));
            if (main.globalErrorsCount <= 0) {
                try {
                    aVar.close();
                    printWriter.close();
                    printWriter2.close();
                    LogUtil.log(TAG, "System.err of Eclipse compiler: ", "About to log System.err of Eclipse compiler on multiple lines because of length.", k.toString());
                    Log.d(TAG, "System.err of Eclipse compiler: " + k.toString());
                } catch (IOException ignored) {
                }
                Log.d(TAG, "javac took " + (System.currentTimeMillis() - currentTimeMillis) + " ms");
                return;
            }
            throw new zy(k.toString());
        } catch (Exception e3) {
            Log.e(TAG, e3.getMessage(), e3);
            throw e3;
        }
    }

    public final void findExtraDexes() {
        extraDexes = new ArrayList<>();
        ArrayList<String> arrayList = new ArrayList<>();
        FileUtil.listDir(f.t, arrayList);
        for (String str : arrayList) {
            if (str.contains("classes") && str.contains(".dex") && !Uri.parse(str).getLastPathSegment().equals("classes.dex")) {
                extraDexes.add(str);
            }
        }
    }

    public final int findLastDexNo() {
        ArrayList<String> arrayList = new ArrayList<>();
        FileUtil.listDir(f.t, arrayList);
        ArrayList<String> arrayList2 = new ArrayList<>();
        for (String str : arrayList) {
            if (str.contains("classes") && str.contains(".dex")) {
                arrayList2.add(Uri.parse(str).getLastPathSegment());
            }
        }
        if (arrayList2.size() == 1 && arrayList2.get(0).equals("classes.dex")) {
            return 2;
        }
        int i2 = 1;
        for (String str2 : arrayList2) {
            String replace = str2.replace("classes", "").replace(".dex", "");
            i2 = Math.max(i2, replace.isEmpty() ? 1 : java.lang.Integer.parseInt(replace));
        }
        return i2 + 1;
    }

    public void g() {
        ApkBuilder apkBuilder = new ApkBuilder(new File(f.G), new File(f.C), new File(f.E), null, null, System.out);
        for (HashMap<String, Object> hashMap : mll.list) {
            apkBuilder.addResourcesFromJar(new File(hashMap.get("jarPath").toString()));
        }
        File file = new File(Environment.getExternalStorageDirectory(),
                ".sketchware/data/".concat(f.b.concat("/files/native_libs")));
        if (FileUtil.isExistFile(file.getAbsolutePath())) {
            apkBuilder.addNativeLibraries(file);
        }
        for (String str : mll.getNativeLibs()) {
            apkBuilder.addNativeLibraries(new File(str));
        }
        for (String str2 : extraDexes) {
            apkBuilder.addFile(new File(str2), Uri.parse(str2).getLastPathSegment());
        }
        for (String str3 : dexesGenerated) {
            apkBuilder.addFile(new File(str3), Uri.parse(str3).getLastPathSegment());
        }
        apkBuilder.setDebugMode(false);
        apkBuilder.sealApk();
    }

    public String getJava() {
        if (proguard.isProguardEnabled()) {
            ArrayList<String> arrayList = new ArrayList<>();
            FileUtil.listDir(f.classes_proguard, arrayList);
            if (arrayList.size() > 0) {
                return f.classes_proguard;
            }
        }
        return f.u;
    }

    public void h() throws Exception {
        long currentTimeMillis = System.currentTimeMillis();
        ArrayList<String> arrayList = new ArrayList<>();
        if (Build.VERSION.SDK_INT <= 23) {
            arrayList.add(l.getAbsolutePath() + c + "dexs" + c + "multidex-2.0.1" + ".dex");
        }
        if (!build_settings.getValue("no_http_legacy", "false").equals("true")) {
            arrayList.add(l.getAbsolutePath() + c + "dexs" + c + "http-legacy-android-28" + ".dex");
        }
        for (Jp next : n.a()) {
            arrayList.add(l.getAbsolutePath() + c + "dexs" + c + next.a() + ".dex");
        }
        for (HashMap<String, Object> hashMap : mll.list) {
            String obj = hashMap.get("name").toString();
            if (hashMap.containsKey("dexPath") && !proguard.libIsProguardFMEnabled(obj)) {
                arrayList.add(hashMap.get("dexPath").toString());
            }
        }
        arrayList.addAll(mll.getExtraDexes());
        a(f.F, arrayList);
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        Log.d(TAG, "Library dex files merge took " + currentTimeMillis2 + " ms");
    }

    public void i() throws Exception {
        String aaptPathInAssets;
        String aapt2PathInAssets;
        if (GB.a().toLowerCase().matches("x86")) {
            aaptPathInAssets = "aapt/aapt-x86";
            aapt2PathInAssets = "aapt/aapt2-x86";
        } else {
            aaptPathInAssets = "aapt/aapt-arm";
            aapt2PathInAssets = "aapt/aapt2-arm";
        }
        try {
            if (a(aaptPathInAssets, i.getAbsolutePath())) {
                d[2] = i.getAbsolutePath();
                j.a(d);
            }
            if (a(aapt2PathInAssets, aapt2Dir.getAbsolutePath())) {
                d[2] = aapt2Dir.getAbsolutePath();
                j.a(d);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            throw new By("Couldn't extract AAPT binaries! Message: " + e.getMessage());
        }
    }

    public void j() {
        if (!g.e(l.getAbsolutePath())) {
            g.f(l.getAbsolutePath());
        }
        String absolutePath = new File(l, "android.jar.zip").getAbsolutePath();
        String absolutePath2 = new File(l, "dexs.zip").getAbsolutePath();
        String absolutePath3 = new File(l, "libs.zip").getAbsolutePath();
        String absolutePath4 = new File(l, "dexs").getAbsolutePath();
        String absolutePath5 = new File(l, "libs").getAbsolutePath();
        if (a("libs" + File.separator + "android.jar.zip", absolutePath)) {
            g.c(l.getAbsolutePath() + c + "android.jar");
            new KB().a(absolutePath, l.getAbsolutePath());
        }
        if (a("libs" + File.separator + "dexs.zip", absolutePath2)) {
            g.b(absolutePath4);
            g.f(absolutePath4);
            new KB().a(absolutePath2, absolutePath4);
        }
        if (a("libs" + File.separator + "libs.zip", absolutePath3)) {
            g.b(absolutePath5);
            g.f(absolutePath5);
            new KB().a(absolutePath3, absolutePath5);
        }
        String str = "libs" + File.separator + "jdk.zip";
        String absolutePath6 = new File(l, "jdk.zip").getAbsolutePath();
        if (a(str, absolutePath6)) {
            String absolutePath7 = new File(l, "jdk").getAbsolutePath();
            g.b(absolutePath7);
            g.f(absolutePath7);
            new KB().a(absolutePath6, absolutePath7);
        }
        if (f.N.g) {
            n.a("appcompat-1.0.0");
            n.a("coordinatorlayout-1.0.0");
            n.a("material-1.0.0");
        }
        if (f.N.h) {
            n.a("firebase-common-19.0.0");
        }
        if (f.N.i) {
            n.a("firebase-auth-19.0.0");
        }
        if (f.N.j) {
            n.a("firebase-database-19.0.0");
        }
        if (f.N.k) {
            n.a("firebase-storage-19.0.0");
        }
        if (f.N.m) {
            n.a("play-services-maps-17.0.0");
        }
        if (f.N.l) {
            n.a("play-services-ads-18.2.0");
        }
        if (f.N.o) {
            n.a("gson-2.8.0");
        }
        if (f.N.n) {
            n.a("glide-4.11.0");
        }
        if (f.N.p) {
            n.a("okhttp-3.9.1");
        }
        ExtLibSelected.a(f.N.x, n);
    }

    public boolean k() {
        ZipSigner zipSigner = new ZipSigner();
        zipSigner.addAutoKeyObserver(new Cp(this));
        KeyStoreFileManager.setProvider(new BouncyCastleProvider());
        zipSigner.setKeymode("testkey");
        yq yqVar = f;
        zipSigner.signZip(yqVar.G, yqVar.H);
        return true;
    }

    public final void mergeDexes(String str, ArrayList<Dex> arrayList) throws Exception {
        new DexMerger(arrayList.toArray(new Dex[0]), CollisionPolicy.KEEP_FIRST).merge().writeTo(new File(str));
        dexesGenerated.add(str);
    }

    public void proguardAddLibConfigs(java.util.List<String> list) {
        for (Jp jp : n.a()) {
            String str = l.getAbsolutePath() + c + jp.a() + c + "proguard.txt";
            if (FileUtil.isExistFile(str)) {
                list.add("-include");
                list.add(str);
            }
        }
    }

    public void proguardAddRjavaRules(java.util.List<String> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("# R.java rules");
        for (Jp jp : n.a()) {
            if (jp.c() && !jp.b().isEmpty()) {
                sb.append("\n");
                sb.append("-keep class ");
                sb.append(jp.b());
                sb.append(".** { *; }");
            }
        }
        for (HashMap<String, Object> hashMap : mll.list) {
            String obj = hashMap.get("name").toString();
            if (hashMap.containsKey("packageName") && !proguard.libIsProguardFMEnabled(obj)) {
                sb.append("\n");
                sb.append("-keep class ");
                sb.append(hashMap.get("packageName").toString());
                sb.append(".** { *; }");
            }
        }
        sb.append("\n");
        FileUtil.writeFile(f.rules_generated, sb.toString());
        list.add("-include");
        list.add(f.rules_generated);
    }

    public void runProguard() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("-include");
        arrayList.add(ProguardHandler.ANDROID_PROGUARD_RULES_PATH);
        arrayList.add("-include");
        arrayList.add(f.aapt_rules);
        arrayList.add("-include");
        arrayList.add(proguard.getCustomProguardRules());
        proguardAddLibConfigs(arrayList);
        proguardAddRjavaRules(arrayList);
        for (String str : mll.getPgRules()) {
            arrayList.add("-include");
            arrayList.add(str);
        }
        arrayList.add("-injars");
        arrayList.add(f.u);
        for (HashMap<String, Object> hashMap : mll.list) {
            String obj = hashMap.get("name").toString();
            if (hashMap.containsKey("jarPath") && proguard.libIsProguardFMEnabled(obj)) {
                arrayList.add("-injars");
                arrayList.add(hashMap.get("jarPath").toString());
            }
        }
        arrayList.add("-libraryjars");
        arrayList.add(classpath());
        arrayList.add("-outjars");
        arrayList.add(f.classes_proguard);
        if (proguard.isDebugFilesEnabled()) {
            arrayList.add("-printseeds");
            arrayList.add(f.printseeds);
            arrayList.add("-printusage");
            arrayList.add(f.printusage);
            arrayList.add("-printmapping");
            arrayList.add(f.printmapping);
        }
        ProGuard.main(arrayList.toArray(new String[0]));
    }

    public void runStringfog() {
        try {
            File file = new File(f.t);
            File file2 = new File(f.u);
            StringFogMappingPrinter stringFogMappingPrinter = new StringFogMappingPrinter(new File(file.getAbsolutePath(),
                    "stringFogMapping.txt"));
            StringFogClassInjector stringFogClassInjector = new StringFogClassInjector(new String[0],
                    "UTF-8",
                    "com.github.megatronking.stringfog.xor.StringFogImpl",
                    "com.github.megatronking.stringfog.xor.StringFogImpl",
                    stringFogMappingPrinter);
            stringFogMappingPrinter.startMappingOutput();
            stringFogMappingPrinter.ouputInfo("UTF-8", "com.github.megatronking.stringfog.xor.StringFogImpl");
            stringFogClassInjector.doFog2ClassInDir(file2);
            KB.a(e, "stringfog/stringfog.zip", f.u);
        } catch (Exception e2) {
            Log.e("Stringfog", e2.toString());
        }
    }

    /** System.err for Eclipse compiler */
    class a extends java.io.OutputStream {

        public a() {
        }

        @Override
        public void write(int i) {
            k.append((char) i);
        }
    }

    /** System.out for Eclipse compiler */
    class b extends java.io.OutputStream {

        private StringBuffer mBuffer;

        public b() {
            mBuffer = new StringBuffer();
        }

        @Override
        public void write(int i) {
            if (mBuffer.length() > 1023) {
                Log.d(TAG, mBuffer.toString());
                mBuffer = new StringBuffer(i);
                return;
            }
            mBuffer.append(i);
        }
    }
}