package mod.w3wide.tools;
import android.app.*;
import android.os.*;
import android.net.*;
import android.util.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
public class Cleaner {
	static boolean cashe = false;
	static double temp2 = 0;
	static double temp = 0;
	static String filepath = "";
	static String tmp = "";
	static double cachesize = 0;
	static ArrayList<String> tempPath = new ArrayList<>();
	static ArrayList<String> cacheFiles = new ArrayList<>();
	static ArrayList<String> temppat2 = new ArrayList<>();
    
    public String Cleaner () {
		temppat2.clear();
		cacheFiles.clear();
		tempPath.clear();
		FileUtil.listDir("/storage/emulated/0/.sketchware/mysc/", tempPath);
        
        
		temp = 0;
		cachesize = 0;
        // Finds all cashe files and stores it in a list
		for(int _repeat13 = 0; _repeat13 < (int)(tempPath.size()); _repeat13++) {
			if (FileUtil.isExistFile(tempPath.get((int)(temp)).concat("/bin/"))) {
				cashe = true;
				cacheFiles.add(tempPath.get((int)(temp)).concat("/bin/"));
			}
			else {
				//ngl just added for timepass
			}
			temp++;
		}
        
        // deletes all the junk files
		temp = 0;
		try {
			for(int _repeat49 = 0; _repeat49 < (int)(cacheFiles.size()); _repeat49++) {
				tempPath.clear();
				FileUtil.listDir(cacheFiles.get((int)(temp)), tempPath);
				temp2 = 0;
				for(int _repeat75 = 0; _repeat75 < (int)(tempPath.size()); _repeat75++) {
					if (!FileUtil.isDirectory(tempPath.get((int)(temp2)).concat("/"))) {
						FileUtil.deleteFile(tempPath.get((int)(temp2)));
					}
					temp2++;
				}
				temp++;
			}
			return "Cleared Cache";
		} catch(Exception e) {
            // sometimes weird errors may crash the app
			return "An error occurred while clearing cache!";
		}
	}
}
