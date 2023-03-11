package mod.RockAriful.AndroXStudio;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.appbar.AppBarLayout;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import a.a.a.yB;
import a.a.a.lC;

import mod.RockAriful.AndroXStudio.*;
import com.sketchware.remod.R;
import mod.SketchwareUtil; 
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;

import io.github.rosemoe.sora.langs.java.JavaLanguage;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.component.Magnifier;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;

import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.*;
import java.io.BufferedWriter; 
import java.io.File; 
import java.io.FileOutputStream; 
import java.io.OutputStreamWriter; 
import java.text.MessageFormat; 
import java.util.Date; 
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean; 
 
import org.eclipse.jgit.api.CloneCommand; 
import org.eclipse.jgit.api.Git; 
import org.eclipse.jgit.api.ResetCommand.ResetType; 
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.errors.GitAPIException; 
import org.eclipse.jgit.lib.Constants; 
import org.eclipse.jgit.revwalk.RevCommit; 
import org.eclipse.jgit.transport.CredentialsProvider; 
import org.eclipse.jgit.transport.PushResult; 
import org.eclipse.jgit.transport.RefSpec; 
import org.eclipse.jgit.transport.*;
import org.eclipse.jgit.transport.RemoteRefUpdate; 
import org.eclipse.jgit.transport.RemoteRefUpdate.Status; 
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider; 
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.util.FileUtils; 

import org.eclipse.jgit.revwalk.RevCommitList;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.internal.storage.file.*;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.diff.*;
import org.eclipse.jgit.treewalk.*;

public class GithubLogActivity extends AppCompatActivity {
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private String sc_id = "";
	private AlertDialog prog;
	private io.github.rosemoe.sora.widget.CodeEditor editor;
	

	private String ProjectNAME = "";
	private String RepositoryPATH = "";
	private String GitHubLast_PATH = "";
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.github_log);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		_app_bar = findViewById(R.id._app_bar);
		_coordinator = findViewById(R.id._coordinator);
		_toolbar = findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		
		editor = findViewById(R.id.editor);
		
	}
	
	private void initializeLogic() {
		sc_id = getIntent().getStringExtra("sc_id");
		
                editor.setTypefaceText(Typeface.MONOSPACE);
                editor.setEditable(false);
                editor.setTextSize(14);                
                editor.getComponent(Magnifier.class).setWithinEditorForcibly(true);
		editor.setColorScheme(new EditorColorScheme());
                editor.setEditorLanguage(new JavaLanguage());
               
		HashMap<String, Object> projectInfo = lC.b(sc_id);
		ProjectNAME = yB.c(projectInfo, "my_ws_name");

		RepositoryPATH = FileUtil.getExternalStorageDir()+"/sketchware/.github_src/"+ProjectNAME+"/.git/";
		GitHubLast_PATH = FileUtil.getExternalStorageDir()+"/.sketchware/data/"+sc_id+"/GitHubLast_changes";
		_Uber_progress(true);
		if (getIntent().getStringExtra("TYPE").equals("LOG")) {
		  setTitle("Show commit logs");
          editor.setText(Html.fromHtml(_FatchCommitLOG()));
          _Uber_progress(false);
		} else {
		  setTitle("GitHub Last Changes");
		  new Thread(() -> {                 
                      _FatchDiff();
             		runOnUiThread(() ->{
                          _Uber_progress(false);
                          _DiffyViewer(editor,FileUtil.readFile(GitHubLast_PATH));
                     });
           	   }).start();

		  
		}
	}
	public String _FatchCommitLOG() {
		 try{
			     
			     StringBuilder AddList = new  StringBuilder();
			     
			    Repository repoo = new FileRepository(RepositoryPATH);
			    Git gits = new Git(repoo);
			    RevWalk walk = new RevWalk(repoo);
			
			    List<Ref> branches = gits.branchList().call();
			
			    for (Ref branch : branches) {
				        String branchName = branch.getName();
				
				        AddList.append("<br><font color='#d81b60'> Commits of branch:</font>" + branch.getName());
				        AddList.append("------------------[START]----------------------");
				
				        Iterable<RevCommit> commits = gits.log().all().call();
				
				        for (RevCommit commit : commits) {
					            boolean foundInThisBranch = false;
					
					            RevCommit targetCommit = walk.parseCommit(repoo.resolve(
					                    commit.getName()));
					            for (Map.Entry<String, Ref> e : repoo.getAllRefs().entrySet()) {
						                if (e.getKey().startsWith(Constants.R_HEADS)) {
							                    if (walk.isMergedInto(targetCommit, walk.parseCommit(
							                            e.getValue().getObjectId()))) {
								                        String foundInBranch = e.getValue().getName();
								                        if (branchName.equals(foundInBranch)) {
									                            foundInThisBranch = true;
									                            break;
									                        }
								                    }
							                }
						            }
					
					            if (foundInThisBranch) {
						                AddList.append("<br>"+commit.getName());
						                AddList.append("<br>"+commit.getAuthorIdent().getName());
						                AddList.append("<br>"+ new Date(commit.getCommitTime() * 1000L));
						                AddList.append("<br>"+commit.getFullMessage());
                                        AddList.append("<br>"+"-----------------------[END]-------------------"+"<br>");
						            }
					        }
				    }			     
			     			     			     			    		
			    return AddList.toString();
			 }catch(GitAPIException | IOException | NullPointerException e){
			     return e.toString();
			 }
	}
	
	
	public void _FatchDiff() {
		   try{
			      Repository repository = new FileRepositoryBuilder()
			            .setGitDir(new File(RepositoryPATH)).build();
			    // Here we get the head commit and it's first parent.
			    // Adjust to your needs to locate the proper commits.
			     RevCommit headCommit = getHeadCommit(repository);
			     RevCommit diffWith = headCommit.getParent(0);
			     FileOutputStream stdout = new FileOutputStream(GitHubLast_PATH);
			     try (DiffFormatter diffFormatter = new DiffFormatter(stdout)) {
				        diffFormatter.setRepository(repository);
				        for (DiffEntry entry : diffFormatter.scan(diffWith, headCommit)) {
					            diffFormatter.format(diffFormatter.toFileHeader(entry));
					        }
				     }
			   }catch(Exception e) {
			       SketchwareUtil.toastError(e.toString());
			   }
		 }
	    private static RevCommit getHeadCommit(Repository repository) throws Exception {
		    try (Git git = new Git(repository)) {
			        Iterable<RevCommit> history = git.log().setMaxCount(1).call();
			        return history.iterator().next();
			    }
		
    	}
        
     public void _DiffyViewer(final io.github.rosemoe.sora.widget.CodeEditor _tv_view, final String _DiffyString) {
		final String[] lines;
		Spannable spannable1 = new SpannableString(_DiffyString);
		android.text.style.ForegroundColorSpan fgSpan = new android.text.style.ForegroundColorSpan(Color.parseColor("#ffffff"));
		android.text.style.BackgroundColorSpan bgSpan = new android.text.style.BackgroundColorSpan(Color.parseColor("#4caf50"));
		android.text.style.BackgroundColorSpan RbgSpan = new android.text.style.BackgroundColorSpan(Color.parseColor("#d81b69"));
		
		lines = _DiffyString.split("\n");
		int x = 0;
		for (int i = 0; i < lines.length; i++) {
		    if (lines[i].substring((int)(0), (int)(1)).replaceAll("\\s","").equals("+")) {
			   int n = _DiffyString.indexOf(lines[i]);
			   x = n+1;
                 try{
				   spannable1.setSpan(android.text.style.CharacterStyle.wrap(fgSpan),n, n+lines[i].length(), 0);
				   spannable1.setSpan(android.text.style.CharacterStyle.wrap(bgSpan), n, n+lines[i].length(), 0);
                 }catch(Exception e){
                   SketchwareUtil.toastError(e.toString());
                 }
			 } else if (lines[i].substring((int)(0), (int)(1)).replaceAll("\\s","").equals("-")) {
                int n = _DiffyString.indexOf(lines[i]);
			   x = n+1;
                try{
                   spannable1.setSpan(android.text.style.CharacterStyle.wrap(fgSpan),n, n+lines[i].length(), 0);
				   spannable1.setSpan(android.text.style.CharacterStyle.wrap(RbgSpan), n, n+lines[i].length(), 0);
                }catch(Exception e){
                  SketchwareUtil.toastError(e.toString());
                }
			 }else{}
		}   
		 _tv_view.setText(spannable1);
 	}
     
     public  void _Uber_progress(final boolean _ifShow) {
		if (_ifShow) {
           prog = new AlertDialog.Builder(this).create();
	   	prog.setCancelable(false);
			prog.setCanceledOnTouchOutside(false);
			prog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
			prog.getWindow().setDimAmount(0.4f);
			View inflate = getLayoutInflater().inflate(R.layout.rockariful_github_loading, null);
			prog.setView(inflate);
			prog.show();
		}
		else {
			if (prog != null){
				prog.dismiss();
			}
		}
	}

}

