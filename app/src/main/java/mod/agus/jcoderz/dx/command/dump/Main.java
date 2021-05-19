package mod.agus.jcoderz.dx.command.dump;

import org.eclipse.jdt.internal.compiler.util.SuffixConstants;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import mod.agus.jcoderz.dex.util.FileUtils;
import mod.agus.jcoderz.dx.cf.iface.ParseException;
import mod.agus.jcoderz.dx.util.HexParser;

public class Main {
    static Args parsedArgs = new Args();

    private Main() {
    }

    public static void main(String[] strArr) {
        int i = 0;
        while (i < strArr.length) {
            String str = strArr[i];
            if (str.equals("--") || !str.startsWith("--")) {
                break;
            }
            if (str.equals("--bytes")) {
                parsedArgs.rawBytes = true;
            } else if (str.equals("--basic-blocks")) {
                parsedArgs.basicBlocks = true;
            } else if (str.equals("--rop-blocks")) {
                parsedArgs.ropBlocks = true;
            } else if (str.equals("--optimize")) {
                parsedArgs.optimize = true;
            } else if (str.equals("--ssa-blocks")) {
                parsedArgs.ssaBlocks = true;
            } else if (str.startsWith("--ssa-step=")) {
                parsedArgs.ssaStep = str.substring(str.indexOf(61) + 1);
            } else if (str.equals("--debug")) {
                parsedArgs.debug = true;
            } else if (str.equals("--dot")) {
                parsedArgs.dotDump = true;
            } else if (str.equals("--strict")) {
                parsedArgs.strictParse = true;
            } else if (str.startsWith("--width=")) {
                String substring = str.substring(str.indexOf(61) + 1);
                parsedArgs.width = Integer.parseInt(substring);
            } else if (str.startsWith("--method=")) {
                parsedArgs.method = str.substring(str.indexOf(61) + 1);
            } else {
                System.err.println("unknown option: " + str);
                throw new RuntimeException("usage");
            }
            i++;
        }
        if (i == strArr.length) {
            System.err.println("no input files specified");
            throw new RuntimeException("usage");
        }
        while (i < strArr.length) {
            try {
                String str2 = strArr[i];
                System.out.println("reading " + str2 + "...");
                byte[] readFile = FileUtils.readFile(str2);
                if (!str2.endsWith(SuffixConstants.SUFFIX_STRING_class)) {
                    readFile = HexParser.parse(new String(readFile, StandardCharsets.UTF_8));
                }
                processOne(str2, readFile);
            } catch (ParseException e2) {
                System.err.println("\ntrouble parsing:");
                if (parsedArgs.debug) {
                    e2.printStackTrace();
                } else {
                    e2.printContext(System.err);
                }
            }
            i++;
        }
    }

    private static void processOne(String str, byte[] bArr) {
        if (parsedArgs.dotDump) {
            DotDumper.dump(bArr, str, parsedArgs);
        } else if (parsedArgs.basicBlocks) {
            BlockDumper.dump(bArr, System.out, str, false, parsedArgs);
        } else if (parsedArgs.ropBlocks) {
            BlockDumper.dump(bArr, System.out, str, true, parsedArgs);
        } else if (parsedArgs.ssaBlocks) {
            parsedArgs.optimize = false;
            SsaDumper.dump(bArr, System.out, str, parsedArgs);
        } else {
            ClassDumper.dump(bArr, System.out, str, parsedArgs);
        }
    }
}
