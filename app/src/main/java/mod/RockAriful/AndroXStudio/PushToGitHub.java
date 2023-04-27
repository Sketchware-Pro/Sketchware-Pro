package mod.RockAriful.AndroXStudio;

import com.sketchware.remod.R;
import mod.SketchwareUtil; 
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;

import android.content.*;
import android.app.*;
import android.app.Activity;
import android.graphics.*;
import android.graphics.drawable.*;

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import java.util.List;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.concurrent.*;
import android.os.*;

import android.widget.*;
import android.view.*;
import android.view.View;

import a.a.a.yB;
import a.a.a.lC;

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
    private static Activity mContext;
    private static AlertDialog prog;
    private static String _FilePATH ="";
    private static String _RepositoryURL ="";
    private static String _setRefSpecs ="";
    private static String _UserName ="";
    private static String _AccessToken ="";
    private static String _CommitMessage ="";
    
    
    public PushToGitHub(Activity context,  final String _sc_id, final boolean _Force){
        mContext = context;
        sc_id = _sc_id;
        
       if(_Force){
         new Thread(() -> {
	 	  _Uber_progress(true);
           HashMap<String, Object> projectInfo = lC.b(sc_id);
           _FilePATH = FileUtil.getExternalStorageDir()+"/sketchware/.github_src/"+yB.c(projectInfo, "my_ws_name");
           mContext.runOnUiThread(() ->
              FatchString()
            );
         }).start();
            
       }else{
        new Thread(() -> {
	 	  _Uber_progress(true);
           _FilePATH = new ExportForGitHub(mContext,sc_id).exportSrc();
           mContext.runOnUiThread(() ->
              FatchString()
            );
         }).start();
       }
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
    
    public static boolean pushREPO(final String _setMessage, final String _Fileformat) {
       
         if(!FileUtil.isExistFile(_FilePATH)){
    	   SketchwareUtil.toastError(_FilePATH+" Not Exist!");
           _Uber_progress(false);
  	     return false;
    	 }
         try(Git git = Git.init().setDirectory(new File(_FilePATH)).call()){
             
         }catch(GitAPIException e){
			 SketchwareUtil.toastError(e.toString());
             _Uber_progress(false);
             return false;
         }
         
		ExecutorService executor = Executors.newSingleThreadExecutor();
		final Handler handler = new Handler(Looper.getMainLooper());
        
		 executor.execute(new Runnable() {
		    @Override
		   public void run() {
		     try(Git git = Git.open(new File(_FilePATH))) {

		         if (_Fileformat.isEmpty()) {
                   git.add().addFilepattern(".").call();
                   SketchwareUtil.toastError("Format file empty");
                 }else{
                     
                  if (_FilePATH.isEmpty()) {
                   Result = "FilePath not exist!";
                   isSucces = false;
                   return;   
                  }
                   List<String> fileNames = Arrays.asList(_Fileformat.split(";\\s*"));
                   if(!fileNames.isEmpty()){
  			      for (String fileName : fileNames) {
 			        try{
                      boolean fileFound = Files.walk(Paths.get(_FilePATH))
                      .filter(p -> p.getFileName().toString().equals(fileName))
                      .findFirst()
                      .isPresent();
                        
                      if (fileFound) {  
    			       Files.walk(Paths.get(_FilePATH))
     		          .filter(p -> p.getFileName().toString().equals(fileName))
         	 	     .forEach(p -> {
          	          try {
                         git.add().addFilepattern(p.toString()).call();
					 	SketchwareUtil.toastError(p.toString());	  
             	       } catch (Exception e) {
                          SketchwareUtil.toastError(e.toString());
                          Result = e.toString();
                          isSucces = false;
                          return;
            	        }
          	         });
                      }else{
                       SketchwareUtil.toastError("Invalid files reference, No files found!");
                      }
    			     } catch (IOException e) {
    		           SketchwareUtil.toastError(e.toString());
                       Result = e.toString();
                       isSucces = false;
                       return;
     		  	  }     
 				   }
                   }else{
                    SketchwareUtil.toastError("Invalid files reference, No files found!");
		     	   Result = "Invalid files reference, No files found!";
                    isSucces = false;
                    return;
                   }
                  
                 }
	 	      
                    
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
    			      Result = "Successfully Pushed! Status : " + update.getStatus().toString()+" with :- "+update.getNewObjectId().getName();
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
                      _Uber_progress(false);
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
    
    public static void _Uber_progress(final boolean _ifShow) {
		if (_ifShow) {
            mContext.runOnUiThread(new Runnable() {
              public void run() {
                prog = new AlertDialog.Builder(mContext).create();
				prog.setCancelable(false);
				prog.setCanceledOnTouchOutside(false);
				prog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
				prog.getWindow().setDimAmount(0.4f);
				View inflate = mContext.getLayoutInflater().inflate(R.layout.rockariful_github_loading, null);
				prog.setView(inflate);
				prog.show();
 	  	   }
			});
		}
		else {
			if (prog != null){
				prog.dismiss();
			}
		}
	}
}
