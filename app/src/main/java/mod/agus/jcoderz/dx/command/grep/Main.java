package mod.agus.jcoderz.dx.command.grep;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;
import mod.agus.jcoderz.dex.Dex;

public final class Main {
    public static void main(String[] strArr) throws IOException {
        int i = 0;
        if (new Grep(new Dex(new File(strArr[0])), Pattern.compile(strArr[1]), new PrintWriter(System.out)).grep() <= 0) {
            i = 1;
        }
        System.err.println("exit code " + i);
    }
}
