package mod.RockAriful.AndroXStudio;

import android.os.Handler;
import android.os.Looper;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

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
        void onComplete(boolean success);
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
                    callback.onComplete(true);
                } catch (GitAPIException | JGitInternalException e) {
                    FileUtil.deleteFile(FileUtil.getExternalStorageDir()+"/.sketchware/.github_temp/".concat(name));
                    e.printStackTrace();
                    callback.onComplete(false);
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
}
