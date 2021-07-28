package in.zitter.kotlinc;

import in.zitter.kotlinc.Main;
import java.util.*;
import in.zitter.kotlinc.RecursiveFile;
import java.io.*;

public class Kotlinc
{
	RecursiveFile rf;
	Main main;
	String[] cmd;
	ArrayList<String> flag;
	public Boolean nowarn , verbose , progressive , includeruntime , javaparameters , noreflect , nostdlib;
	public String argsfile , cp;
	public Float jvmtarget;
	public void main(String wdir){
		File file = new File(wdir);
		if(file.exists() && file.isDirectory()){
			File[] ktfile = file.listFiles();
		rf.KtClass(ktfile,0);
		flag.addAll(rf.list);
		if(argsfile != null){
		flag.add("@args");
		flag.add(argsfile);
		}
		if(cp != null){
		flag.add("-cp");
		flag.add(cp);
		}
		if(nowarn= true){
			flag.add("-nowarn");
		}
		if(verbose= true){
			flag.add("-verbose");
		}
		if(progressive= true){
			flag.add("-progressive");
		}
		if(includeruntime=true){
			flag.add("-include-runtime");
		}
		if(javaparameters=true){
			flag.add("-java-parameters");
		}
		if(noreflect=true){
			flag.add("-no-reflect");
		}
		if(nostdlib=true){
			flag.add("-no-stdlib");
		}
		
		main.args = flag.toArray(new String[flag.size()]);
		main.kotlinc();
		}
	}
}
