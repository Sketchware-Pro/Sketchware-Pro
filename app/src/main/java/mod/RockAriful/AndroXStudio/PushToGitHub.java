package mod.RockAriful.AndroXStudio;

import com.sketchware.remod.R;
import mod.SketchwareUtil; 
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;

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
    
	private static String Result ="";
	private static boolean isSucces = false;
    private Context mContext;
    
    
    public PushToGitHub(Context  context){
        mContext = context;
    }
    
    public static boolean _pushREPO(final String _filePATH, final String _setMessage, final String _UserName, final String _PassWord, final String _RemoteURL, final String _setRefSpecs) {
       
            
         if(!FileUtil.isExistFile(_filePATH)){
    	   SketchwareUtil.toastError(_filePATH+" Not Exist!");
  	     return false;
    	 }
         try(Git git = Git.init().setDirectory(new File(_filePATH)).call()){
             
         }catch(GitAPIException e){
			 SketchwareUtil.toastError(e.toString());
             return false;
         }
         
		ExecutorService executor = Executors.newSingleThreadExecutor();
		final Handler handler = new Handler(Looper.getMainLooper());
        
		 executor.execute(new Runnable() {
		    @Override
		   public void run() {
		     try(Git git = Git.open(new File(_filePATH))) {
		         
	 	        git.add().addFilepattern(".").call();
	 	        git.commit().setMessage(_setMessage).call();
	 	        
	 	        PushCommand push = git.push();
                 push.setCredentialsProvider(new UsernamePasswordCredentialsProvider(_UserName, _PassWord));
  		       push.setRemote(_RemoteURL);
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
                         return true;
					  }else{
					     SketchwareUtil.toastError(Result);
                         return false;
					  }
				  }
			   });
		   }
		});
        return false;
	}
}
