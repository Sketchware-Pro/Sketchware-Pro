package in.zitter.kotlinc;

import in.zitter.kotlinc.Kotlinc;

public class KotlincMain
{
	Kotlinc kt;
	public void runkt(String wdir , boolean nowarn , boolean verbose , boolean progressive , boolean includeruntime , boolean javaparameters , boolean noreflect , boolean nostdlib , String argsfile , String cp , Float jvmtarget){
		kt.nowarn = nowarn;
		kt.verbose = verbose;
		kt.progressive = progressive;
		kt.includeruntime = includeruntime;
		kt.javaparameters = javaparameters;
		kt.noreflect = noreflect;
		kt.nostdlib = nostdlib;
		kt.argsfile = argsfile;
		kt.cp = cp;
		kt.jvmtarget = jvmtarget;
		kt.main(wdir);
	}
	public void runkt(String wdir , boolean nowarn , boolean verbose, boolean progressive , boolean javaparameters , boolean noreflect , boolean nostdlib , String argsfile , String cp , Float jvmtarget){
		kt.nowarn = nowarn;
		kt.verbose = verbose;
		kt.progressive = progressive;
		kt.includeruntime = false;
		kt.javaparameters = javaparameters;
		kt.noreflect = noreflect;
		kt.nostdlib = nostdlib;
		kt.argsfile = argsfile;
		kt.cp = cp;
		kt.jvmtarget = jvmtarget;
		kt.main(wdir);
	}
	public void runkt(String wdir , boolean nowarn , boolean verbose , String argsfile, String cp , Float jvmtarget){
		kt.nowarn = nowarn;
		kt.verbose = verbose;
		kt.progressive = true;
		kt.includeruntime = false;
		kt.javaparameters = false;
		kt.noreflect = false;
		kt.nostdlib = false;
		kt.argsfile = argsfile;
		kt.cp = cp;
		kt.jvmtarget = jvmtarget;
		kt.main(wdir);
	}
	public void runkt(String wdir , String cp){
		kt.nowarn = false;
		kt.verbose = false;
		kt.progressive = true;
		kt.includeruntime = false;
		kt.javaparameters = false;
		kt.noreflect = false;
		kt.nostdlib = false;
		kt.argsfile = null;
		kt.cp = cp;
		kt.jvmtarget = null;
		kt.main(wdir);
	}
}
