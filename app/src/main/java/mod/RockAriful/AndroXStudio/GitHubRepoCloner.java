
package mod.RockAriful.AndroXStudio;

import android.os.Handler;
import android.os.Looper;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import mod.SketchwareUtil;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mod.agus.jcoderz.lib.FileUtil;

public class GitHubRepoCloner {

    private String url;
    private String name;
    private String username;
    private String password;

    public GitHubRepoCloner(String url, String name, String username, String password) {
        this.url = url;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public interface CloneCallback {
        void onComplete(boolean success, String mesg);
    }

    public void cloneRepository(final CloneCallback callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    CloneCommand clone = Git.cloneRepository();
                    clone.setURI(url);
                    clone.setDirectory(new File(FileUtil.getExternalStorageDir()+"/.sketchware/.github_temp/", name));
                    clone.setBare(false);
                    clone.setCloneAllBranches(true);
                    clone.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password));
                    clone.call();
                    
                    clone.setProgressMonitor(new CloneProgressMonitor(new CloneCommand.Callback() {
   			 	 @Override
 			  	   public void cloneComplete(Repository repository) {
        
      				  try {
                         callback.onComplete(true,repository.getDirectory().getAbsolutePath());
       				 } catch (IOException e) {
         			    e.printStackTrace();
                         callback.onComplete(false,e.toString());
            			 SketchwareUtil.toastError("An error occurred while restoring project: " + e.getMessage());
       				 }
   				   }

  				    @Override
  				     public void onError(Throwable throwable) {
       				 SketchwareUtil.toastError("An error occurred while cloning repository: " + throwable.getMessage());
                        callback.onComplete(false,throwable.getMessage());
  				     }
					}));

					try {
   				   clone.call();
					} catch (GitAPIException e) {
  				   e.printStackTrace();
                     callback.onComplete(false,e.toString());
 				    SketchwareUtil.toastError("An error occurred while cloning repository: " + e.getMessage());
					}



                } catch (GitAPIException | JGitInternalException e) {
                    FileUtil.deleteFile(FileUtil.getExternalStorageDir()+"/.sketchware/.github_temp/".concat(name));
                    e.printStackTrace();
                    callback.onComplete(false,e.toString());
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // update UI if necessary
                    }
                });
            }
        });
    }
    
    public void _zip(final String _source, final String _destination) {
		
		try {
			java.util.zip.ZipOutputStream os = new java.util.zip.ZipOutputStream(new java.io.FileOutputStream(_destination));
					zip(os, _source, null);
					os.close();
		}
		
		catch(java.io.IOException e) {}
	}
	private void zip(java.util.zip.ZipOutputStream os, String filePath, String name) throws java.io.IOException
		{
				java.io.File file = new java.io.File(filePath);
				java.util.zip.ZipEntry entry = new java.util.zip.ZipEntry((name != null ? name + java.io.File.separator : "") + file.getName() + (file.isDirectory() ? java.io.File.separator : ""));
				os.putNextEntry(entry);
				
				if(file.isFile()) {
						java.io.InputStream is = new java.io.FileInputStream(file);
						int size = is.available();
						byte[] buff = new byte[size];
						int len = is.read(buff);
						os.write(buff, 0, len);
						return;
				}
				
				java.io.File[] fileArr = file.listFiles();
				for(java.io.File subFile : fileArr) {
						zip(os, subFile.getAbsolutePath(), entry.getName());
				}
	}
}
