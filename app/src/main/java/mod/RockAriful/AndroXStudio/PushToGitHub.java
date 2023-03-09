package mod.RockAriful.AndroXStudio;

import com.sketchware.remod.R;
import mod.SketchwareUtil; 
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;

import android.content.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.concurrent.*;
import android.os.*;

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


public class PushToGitHub {
    
    private ArrayList<HashMap<String, Object>> JsonMAP = new ArrayList<>();
	private static String Result ="";
    private static String sc_id ="";
	private static boolean isSucces = false;
    private Context mContext;
    
    private static String _FilePATH ="";
    private static String _RepositoryURL ="";
    private static String _setRefSpecs ="";
    private static String _UserName ="";
    private static String _AccessToken ="";
    private static String _CommitMessage ="";
    
    
    public PushToGitHub(Context  context,  final String _sc_id){
        mContext = context;
        sc_id = _sc_id;
        
        new Thread(() -> {
           _FilePATH = new ExportForGitHub(mContext,sc_id).exportSrc();
            runOnUiThread(() ->
              FatchString()
            );
         }).start();
    }
    
    public void  FatchString(){
        try{
			JsonMAP = new Gson().fromJson(FileUtil.readFile(FileUtil.getExternalStorageDir()+"/.sketchware/data/"+sc_id+"/github_config"), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
	    	_RepositoryURL = JsonMAP.get((int)0).get("repository").toString();
	    	_setRefSpecs = JsonMAP.get((int)0).get("RefSpecs").toString();
			_UserName = JsonMAP.get((int)0).get("username").toString();
	    	_AccessToken = JsonMAP.get((int)0).get("token").toString();
		}catch(Exception e){}
    }
    
    public static boolean pushREPO(final String _setMessage) {
       
         if(!FileUtil.isExistFile(_FilePATH)){
    	   SketchwareUtil.toastError(_FilePATH+" Not Exist!");
  	     return false;
    	 }
         try(Git git = Git.init().setDirectory(new File(_FilePATH)).call()){
             
         }catch(GitAPIException e){
			 SketchwareUtil.toastError(e.toString());
             return false;
         }
         
		ExecutorService executor = Executors.newSingleThreadExecutor();
		final Handler handler = new Handler(Looper.getMainLooper());
        
		 executor.execute(new Runnable() {
		    @Override
		   public void run() {
		     try(Git git = Git.open(new File(_FilePATH))) {
		         
	 	        git.add().addFilepattern(".").call();
	 	        git.commit().setMessage(_setMessage).call();
	 	        
	 	        PushCommand push = git.push();
                 push.setCredentialsProvider(new UsernamePasswordCredentialsProvider(_UserName, _AccessToken));
  		       push.setRemote(_RepositoryURL);
 	 	       push.setRefSpecs(new RefSpec(_setRefSpecs));
 		        push.setForce(true);
 			
					          
		  	 Iterable<PushResult> results = push.call();
	   		for (PushResult r : results) {
   			  for(RemoteRefUpdate update : r.getRemoteUpdates()) {
	     	 	  //System.out.println("Having result: " + update);
    		 	  if(update.getStatus() != RemoteRefUpdate.Status.OK && update.getStatus() != RemoteRefUpdate.Status.UP_TO_DATE) {
		  	        Result = "Push failed: "+ update.getStatus();
	 	             isSucces = false;
	    		     throw new RuntimeException(Result);
   	 		    }else{
    			      Result = "Successfully Pushed  & " + update.getStatus().toString();
	    		      isSucces = true;
		    		}
	   	 	 }
	   		}
					             
	    	 }catch(IOException | GitAPIException | JGitInternalException e) {
			    Result = e.getMessage();
			    isSucces = false;
			    e.printStackTrace();
			 }
				        
		  	handler.post(new Runnable() {
				 @Override
				  public void run() {
					  if(isSucces){
					     SketchwareUtil.toast(Result);
					  }else{
					     SketchwareUtil.toastError(Result);
					  }
				  }
			   });
		   }
		});
        return isSucces;
	}
}
