
package mod.RockAriful.AndroXStudio;

import android.os.Handler;
import android.os.Looper;
import android.content.Context;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.jgit.lib.ProgressMonitor;

import mod.agus.jcoderz.lib.FileUtil;

public class GitHubRepoCloner {

    private String url;
    private String name;
    private String filePath;
    private String username;
    private String password;

    public GitHubRepoCloner(String url, String name, String username, String password) {
        this.url = url;
        this.name = name;
        this.filePath = FileUtil.getExternalStorageDir()+"/.sketchware/.github_temp/";
        this.username = username;
        this.password = password;
    }

    public interface CloneCallback {
        void onComplete(boolean success, String mesg);
        void onProgress(int progress);
    }
/*
    public void cloneRepository(final CloneCallback callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    CloneCommand clone = Git.cloneRepository();
                    clone.setURI(url);
                    clone.setDirectory(new File(filePath, name));
                    clone.setBare(false);
                    clone.setCloneAllBranches(true);
                    clone.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password));
                    clone.call();
					_zip(filePath+name+"/DataSource",filePath+name+"/DataSource.swb");
                    callback.onComplete(true,filePath+name+"/DataSource.swb");
                } catch (GitAPIException | JGitInternalException e) {
                    FileUtil.deleteFile(filePath+name);
                    e.printStackTrace();
                    callback.onComplete(false,e.toString());
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        
                    }
                });
            }
        });
    }
    
    */
    
  public void cloneRepository(final CloneCallback callback) {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    final Handler handler = new Handler(Looper.getMainLooper());

    executor.execute(new Runnable() {
        @Override
        public void run() {
            try {
                CloneCommand clone = Git.cloneRepository();
                clone.setURI(url);
                clone.setDirectory(new File(filePath, name));
                clone.setBare(false);
                clone.setCloneAllBranches(true);
                clone.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password));
                //clone.setProgressMonitor(new TextProgressMonitor(new PrintWriter(System.out)));
                clone.call();

                _zip(filePath+name+"/DataSource",filePath+name+"/DataSource.swb");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onComplete(true,filePath+name+"/DataSource.swb");
                    }
                });
            } catch (GitAPIException | JGitInternalException e) {
                FileUtil.deleteFile(filePath+name);
                e.printStackTrace();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onComplete(false,e.toString());
                    }
                });
            }
        }
    });

    // Count the number of objects cloned so far and update progress accordingly
    final File objectsDir = new File(filePath, name + "/.git/objects");
    final long totalObjects = countObjects(objectsDir);
    final int pollInterval = 1000; // 1 second

    handler.postDelayed(new Runnable() {
        @Override
        public void run() {
            if (!objectsDir.exists()) {
                // The clone operation has completed
                return;
            }

            long currentObjects = countObjects(objectsDir);
            int progress = (int) ((double) currentObjects / totalObjects * 100);

            // Call onProgress with the current progress
            callback.onProgress(progress);

            // Schedule the next check
            handler.postDelayed(this, pollInterval);
        }
    }, pollInterval);
  }

   private long countObjects(File dir) {
    long count = 0;
    for (File file : dir.listFiles()) {
        if (file.isDirectory()) {
            count += countObjects(file);
        } else {
            count++;
        }
    }
    return count;
   }


    public void _zip(final String _source, final String _destination) {
		
      try {
        java.util.zip.ZipOutputStream os = new java.util.zip.ZipOutputStream(new java.io.FileOutputStream(_destination));
	    zip(os, _source, "");
	    os.close();
	  }catch(java.io.IOException e) {
	    e.printStackTrace();
      }
		
	}
	private void zip(java.util.zip.ZipOutputStream os, String filePath, String prefix) throws java.io.IOException {
	 java.io.File file = new java.io.File(filePath);
	 String name = file.getName();
            
		 if (file.isFile()) {
		   java.util.zip.ZipEntry entry = new java.util.zip.ZipEntry(prefix + name);
		   os.putNextEntry(entry);
			
		   java.io.InputStream is = new java.io.FileInputStream(file);
		   int size = is.available();
		   byte[] buff = new byte[size];
		   int len = is.read(buff);
		   os.write(buff, 0, len);
		   is.close();
		   os.closeEntry();
		   return;
		 }
		
		 if (name.equals("DataSource")) {
	       name = "";
	     } else if (!name.isEmpty()) {
		   prefix += name + "/";
	     }
		
		java.io.File[] fileArr = file.listFiles();
		 for (java.io.File subFile : fileArr) {
		   zip(os, subFile.getAbsolutePath(), prefix);
         }
    }
}

