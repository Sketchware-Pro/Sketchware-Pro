package in.zitter.kotlinc;
import org.jetbrains.kotlin.cli.jvm.K2JVMCompiler;
public class Main{
  K2JVMCompiler ktc;


  public void Kotlinc (String KotlinHome , String cp , String output , String TargetVersion ,String argsFile){

    String[] args = {"-kotlin-home" , KotlinHome , "-cp" , cp , "-d" , output , "-jvm-target" , TargetVersion , "@argfile" , argsFile};
    ktc.main(args);
  }
}
