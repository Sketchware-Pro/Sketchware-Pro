package mod.agus.jcoderz.dx.command.findusages;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import mod.agus.jcoderz.dex.Dex;

public final class Main {
    public static void main(String[] strArr) throws IOException {
        String str = strArr[0];
        String str2 = strArr[1];
        String str3 = strArr[2];
        Dex dex = new Dex(new File(str));
        PrintWriter printWriter = new PrintWriter(System.out);
        new FindUsages(dex, str2, str3, printWriter).findUsages();
        printWriter.flush();
    }
}
