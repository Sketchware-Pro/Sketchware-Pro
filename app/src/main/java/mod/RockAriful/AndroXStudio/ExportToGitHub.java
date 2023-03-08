package mod.RockAriful.AndroXStudio;

import androidx.core.content.FileProvider;


import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.sketchware.remod.BuildConfig;
import com.sketchware.remod.R;

import org.spongycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.lang.ref.WeakReference;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

import a.a.a.Dp;
import a.a.a.KB;
import a.a.a.MA;
import a.a.a.aB;
import a.a.a.eC;
import a.a.a.hC;
import a.a.a.iC;
import a.a.a.kC;
import a.a.a.lC;
import a.a.a.oB;
import a.a.a.wq;
import a.a.a.xq;
import a.a.a.yB;
import a.a.a.yq;
import kellinwood.security.zipsigner.ZipSigner;
import kellinwood.security.zipsigner.optional.CustomKeySigner;
import kellinwood.security.zipsigner.optional.LoadKeystoreException;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.compiler.kotlin.KotlinCompilerBridge;
import mod.hey.studios.project.proguard.ProguardHandler;
import mod.hey.studios.project.stringfog.StringfogHandler;
import mod.hey.studios.util.Helper;
import mod.jbk.build.BuildProgressReceiver;
import mod.jbk.build.compiler.bundle.AppBundleCompiler;
import mod.jbk.export.GetKeyStoreCredentialsDialog;
import mod.jbk.util.TestkeySignBridge;


public class ExportToGitHub {
    
    private String export_src_postfix;
    private final oB file_utility = new oB();
    
    private String export_src_full_path;
    private String export_src_filename;
    private String sc_id;
    private HashMap<String, Object> sc_metadata = null;
    private yq project_metadata = null;
    
    
    public ExportToGitHub(Context  context, final String sc_id){
        
        sc_metadata = lC.b(sc_id);
        project_metadata = new yq(getApplicationContext(), wq.d(sc_id), sc_metadata);
        initializeOutputDirectories();
    }
    
    
    
    private void exportSrc() {
        try {
            FileUtil.deleteFile(project_metadata.projectMyscPath);

            hC hCVar = new hC(sc_id);
            kC kCVar = new kC(sc_id);
            eC eCVar = new eC(sc_id);
            iC iCVar = new iC(sc_id);
            hCVar.i();
            kCVar.s();
            eCVar.g();
            eCVar.e();
            iCVar.i();

            /* Extract project type template */
            project_metadata.a(getApplicationContext(), wq.e(xq.a(sc_id) ? "600" : sc_id));

            /* Start generating project files */
            project_metadata.b(hCVar, eCVar, iCVar, true);
            if (yB.a(lC.b(sc_id), "custom_icon")) {
                project_metadata.a(wq.e() + File.separator + sc_id + File.separator + "icon.png");
            }
            project_metadata.a();
            kCVar.b(project_metadata.resDirectoryPath + File.separator + "drawable-xhdpi");
            kCVar.c(project_metadata.resDirectoryPath + File.separator + "raw");
            kCVar.a(project_metadata.assetsPath + File.separator + "fonts");
            project_metadata.f();

            /* It makes no sense that those methods aren't static */
            FilePathUtil util = new FilePathUtil();
            File pathJava = new File(util.getPathJava(sc_id));
            File pathResources = new File(util.getPathResource(sc_id));
            File pathAssets = new File(util.getPathAssets(sc_id));
            File pathNativeLibraries = new File(util.getPathNativelibs(sc_id));

            if (pathJava.exists()) {
                FileUtil.copyDirectory(pathJava, new File(project_metadata.javaFilesPath + File.separator + project_metadata.packageNameAsFolders));
            }
            if (pathResources.exists()) {
                FileUtil.copyDirectory(pathResources, new File(project_metadata.resDirectoryPath));
            }
            if (pathAssets.exists()) {
                FileUtil.copyDirectory(pathAssets, new File(project_metadata.assetsPath));
            }
            if (pathNativeLibraries.exists()) {
                FileUtil.copyDirectory(pathNativeLibraries, new File(project_metadata.generatedFilesPath, "jniLibs"));
            }

            ArrayList<String> toCompress = new ArrayList<>();
            toCompress.add(project_metadata.projectMyscPath);
            String exportedFilename = yB.c(sc_metadata, "my_ws_name") + ".zip";

            String exportedSourcesZipPath = wq.s() + File.separator + "export_src" + File.separator + exportedFilename;
            if (file_utility.e(exportedSourcesZipPath)) {
                file_utility.c(exportedSourcesZipPath);
            }

            ArrayList<String> toExclude = new ArrayList<>();
            if (!new File(new FilePathUtil().getPathJava(sc_id) + File.separator + "SketchApplication.java").exists()) {
                toExclude.add("SketchApplication.java");
            }
            toExclude.add("DebugActivity.java");

            new KB().a(exportedSourcesZipPath, toCompress, toExclude);
            project_metadata.e();
            runOnUiThread(() -> initializeAfterExportedSourceViews(exportedFilename));
        } catch (Exception e) {
            runOnUiThread(() -> {
                Log.e("ProjectExporter", "While trying to export project's sources: "
                        + e.getMessage(), e);
                SketchwareUtil.showAnErrorOccurredDialog(this, Log.getStackTraceString(e));
                layout_export_src.setVisibility(View.GONE);
                loading_export_src.setVisibility(View.GONE);
                btn_export_src.setVisibility(View.VISIBLE);
            });
        }
    }
    
    private void initializeOutputDirectories() {
        signed_apk_postfix = File.separator + "sketchware" + File.separator + "signed_apk";
        export_src_postfix = File.separator + "sketchware" + File.separator + ".github_src";
        /* /sdcard/sketchware/signed_apk */
        String signed_apk_full_path = wq.s() + File.separator + "signed_apk";
        export_src_full_path = wq.s() + File.separator + ".github_src";

        /* Check if they exist, if not, create them */
        file_utility.f(signed_apk_full_path);
        file_utility.f(export_src_full_path);
    }
    
}
