package in.zitter.kotlinc;
import java.io.*;
import org.apache.commons.io.FilenameUtils;
import java.util.*;

public class RecursiveFile
{
	ArrayList<String> list;
	FilenameUtils ext;
	public void KtClass(File[] loc , int index){
		for(File file : loc){
			if(file.isFile()){
				if(ext.getExtention(file.getAbsolutePath() = "kt")){
					list.add(file.getAbsolutePath());
				} 
					
				} else if(file.isDirectory()){
					KtClass(file.listFiles(), index+1);
				}
			}
		}
	
}
