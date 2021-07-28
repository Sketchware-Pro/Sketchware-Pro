package in.zitter.kotlinc;
import org.jetbrains.kotlin.cli.jvm.K2JVMCompiler;
public class Main{
  K2JVMCompiler ktc;
  String[] args;

  public void Kotlinc (String KotlinHome , String cp , String output , String TargetVersion ,String argsFile){


    ktc.main(this.args);
  }
}
