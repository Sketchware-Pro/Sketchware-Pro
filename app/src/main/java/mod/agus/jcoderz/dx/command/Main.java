package mod.agus.jcoderz.dx.command;

public class Main {
    private static final String USAGE_MESSAGE = "usage:\n  dx --dex [--debug] [--verbose] [--positions=<style>] [--no-locals]\n  [--no-optimize] [--statistics] [--[no-]optimize-list=<file>] [--no-strict]\n  [--keep-classes] [--output=<file>] [--dump-to=<file>] [--dump-width=<n>]\n  [--dump-method=<name>[*]] [--verbose-dump] [--no-files] [--core-library]\n  [--num-threads=<n>] [--incremental] [--force-jumbo] [--no-warning]\n  [--multi-dex [--main-dex-list=<file> [--minimal-main-dex]]\n  [--input-list=<file>]\n  [<file>.class | <file>.{zip,jar,apk} | <directory>] ...\n    Convert a set of classfiles into a dex file, optionally embedded in a\n    jar/zip. Output name must end with one of: .dex .jar .zip .apk or be a directory.\n    Positions options: none, important, lines.\n    --multi-dex: allows to generate several dex files if needed. This option is \n    exclusive with --incremental, causes --num-threads to be ignored and only\n    supports folder or archive output.\n    --main-dex-list=<file>: <file> is a list of class file names, classes defined by\n    those class files are put in classes.dex.\n    --minimal-main-dex: only classes selected by --main-dex-list are to be put in\n    the main dex.\n    --input-list: <file> is a list of inputs.\n    Each line in <file> must end with one of: .class .jar .zip .apk or be a directory.\n  dx --annotool --annotation=<class> [--element=<element types>]\n  [--print=<print types>]\n  dx --dump [--debug] [--strict] [--bytes] [--optimize]\n  [--basic-blocks | --rop-blocks | --ssa-blocks | --dot] [--ssa-step=<step>]\n  [--width=<n>] [<file>.class | <file>.txt] ...\n    Dump classfiles, or transformations thereof, in a human-oriented format.\n  dx --find-usages <file.dex> <declaring type> <member>\n    Find references and declarations to a field or method.\n    declaring type: a class name in internal form, like Ljava/lang/Object;\n    member: a field or method name, like hashCode\n  dx -J<option> ... <arguments, in one of the above forms>\n    Pass VM-specific options to the virtual machine that runs dx.\n  dx --version\n    Print the version of this tool (1.11).\n  dx --help\n    Print this message.";

    private Main() {

    }

    public static int main(String[] strArr) {
        boolean z;
        boolean z2 = false;
        int i = 0;
        boolean z3 = false;
        while (true) {
            try {
                if (i >= strArr.length) {
                    z2 = z3;
                    z = false;
                    break;
                }
                String str = strArr[i];
                if (str.equals("--") || !str.startsWith("--")) {
                    z = true;
                } else {
                    if (str.equals("--dex")) {
                        mod.agus.jcoderz.dx.command.dexer.Main.main(without(strArr, i));
                        z3 = true;
                    } else if (str.equals("--dump")) {
                        mod.agus.jcoderz.dx.command.dump.Main.main(without(strArr, i));
                        z = false;
                        z2 = true;
                        break;
                    } else if (str.equals("--annotool")) {
                        mod.agus.jcoderz.dx.command.annotool.Main.main(without(strArr, i));
                        z = false;
                        z2 = true;
                        break;
                    } else if (str.equals("--find-usages")) {
                        mod.agus.jcoderz.dx.command.findusages.Main.main(without(strArr, i));
                        z = false;
                        z2 = true;
                        break;
                    } else if (str.equals("--version")) {
                        version();
                        z = false;
                        z2 = true;
                        break;
                    } else if (str.equals("--help")) {
                        z = true;
                        z2 = true;
                        break;
                    } else {
                        z3 = false;
                    }
                    i++;
                }
            } catch (UsageException e) {
                z2 = z3;
                z = true;
                if (!z2) {
                }
                if (!z) {
                }
            } catch (RuntimeException e2) {
            } catch (Throwable th) {
            }
        }
        if (!z2) {
            System.err.println("error: no command specified");
            z = true;
        }
        if (!z) {
            return 1;
        }
        usage();
        return 1;
    }

    private static int version() {
        System.err.println("dx version 1.11");
        return 0;
    }

    private static void usage() {
        System.err.println(USAGE_MESSAGE);
    }

    private static String[] without(String[] strArr, int i) {
        int length = strArr.length - 1;
        String[] strArr2 = new String[length];
        System.arraycopy(strArr, 0, strArr2, 0, i);
        System.arraycopy(strArr, i + 1, strArr2, i, length - i);
        return strArr2;
    }
}
