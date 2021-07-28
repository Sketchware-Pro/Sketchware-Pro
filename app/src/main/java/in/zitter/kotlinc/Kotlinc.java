package in.zitter.kotlinc;

import in.zitter.kotlinc.Main;
import java.util.*;

public class Kotlinc
{
	Main main;
	String[] cmd;
	ArrayList<String> flag;
	private Boolean nowarn , verbose , progressive , includeruntime , javaparameters , noreflect , nostdlib;
	String argsfile , cp;
	Float jvmtarget;
	public void main(){
		flag.add(argsfile);
		flag.add(cp);
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
