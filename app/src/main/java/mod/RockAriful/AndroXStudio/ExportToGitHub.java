package mod.RockAriful.AndroXStudio;

import android.content.*;
import androidx.core.content.FileProvider;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.sketchware.remod.BuildConfig;
import com.sketchware.remod.R;
import mod.SketchwareUtil;
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
    private Context mContext;
    private String export_src_full_path;
    private String export_src_filename;
    private String sc_id ="";
    private String exportedSourcesZipPath = "";
    private HashMap<String, Object> sc_metadata = null;
    private yq project_metadata = null;
    
    
    public ExportToGitHub(Context  context, final String _sc_id){
        mContext = context;
        sc_id = _sc_id;
        sc_metadata = lC.b(sc_id);
        project_metadata = new yq(mContext, wq.d(sc_id), sc_metadata);
        initializeOutputDirectories();
        exportSrc();
    }
    
    
    
    public static String exportSrc() {
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
            project_metadata.a(mContext, wq.e(xq.a(sc_id) ? "600" : sc_id));

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

            String exportedSourcesZipPath = wq.s() + File.separator + ".github_src" + File.separator + exportedFilename;
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
            
            String filePath = export_src_full_path + File.separator + export_src_filename;
            _UnZip(exportedSourcesZipPath,exportedSourcesZipPath.replace(".zip",""));
            SketchwareUtil.toast(filePath + ": export to : " +exportedSourcesZipPath);
            FileUtil.deleteFile(exportedSourcesZipPath);
          // runOnUiThread(() -> initializeAfterExportedSourceViews(exportedFilename));
        } catch (Exception e) {
            
        }
      return exportedSourcesZipPath.replace(".zip","");
    }
    
    private void initializeOutputDirectories() {
        
        export_src_postfix = File.separator + "sketchware" + File.separator + ".github_src";
        /* /sdcard/sketchware/signed_apk */
        export_src_full_path = wq.s() + File.separator + ".github_src";
        /* Check if they exist, if not, create them */
        file_utility.f(export_src_full_path);
    }
    
    public void _UnZip(final String _fileZip, final String _destDir) {
		try
		{
			java.io.File outdir = new java.io.File(_destDir);
			java.util.zip.ZipInputStream zin = new java.util.zip.ZipInputStream(new java.io.FileInputStream(_fileZip));
			java.util.zip.ZipEntry entry;
			String name, dir;
			while ((entry = zin.getNextEntry()) != null)
			{
				name = entry.getName();
				if(entry.isDirectory())
				{
					mkdirs(outdir, name);
					continue;
				}
				
				dir = dirpart(name);
				if(dir != null)
				mkdirs(outdir, dir);
				
				extractFile(zin, outdir, name);
			}
			zin.close();
		}
		catch (java.io.IOException e)
		{
			e.printStackTrace();
		}
	}
	private static void extractFile(java.util.zip.ZipInputStream in, java.io.File outdir, String name) throws java.io.IOException
	{
		byte[] buffer = new byte[4096];
		java.io.BufferedOutputStream out = new java.io.BufferedOutputStream(new java.io.FileOutputStream(new java.io.File(outdir, name)));
		int count = -1;
		while ((count = in.read(buffer)) != -1)
		out.write(buffer, 0, count);
		out.close();
	}
	
	private static void mkdirs(java.io.File outdir, String path)
	{
		java.io.File d = new java.io.File(outdir, path);
		if(!d.exists())
		d.mkdirs();
	}
	
	private static String dirpart(String name)
	{
		int s = name.lastIndexOf(java.io.File.separatorChar);
		return s == -1 ? null : name.substring(0, s);
	}
    
}
