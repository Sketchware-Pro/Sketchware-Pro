package mod.hey.studios.util;

import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import mod.agus.jcoderz.lib.FileUtil;

public class SystemLogPrinter {

    private static final String PATH = FileUtil.getExternalStorageDir().concat("/.sketchware/debug.txt");

    public static void start() {
        if (FileUtil.isExistFile(PATH)) {
            FileUtil.deleteFile(PATH);
        }

        FileUtil.writeFile(PATH, "");
        OutputStream outputStream = new OutputStream() {

            private String mCache;

            @Override
            public void write(int b) {
                if (this.mCache == null) {
                    this.mCache = "";
                }

                if ((char) b == '\n') {
                    String var2 = this.mCache;
                    FileUtil.writeFile(PATH, FileUtil.readFile(PATH) + "\n" + var2);
                    this.mCache = "";
                } else {
                    this.mCache = this.mCache + (char) b;
                }

            }
        };

        try {
            Field out = System.class.getDeclaredField("out");
            Field fieldAccessFlags = Field.class.getDeclaredField("accessFlags");
            fieldAccessFlags.setAccessible(true);
            int outModifiers = out.getModifiers();
            fieldAccessFlags.set(out, outModifiers & -17);
            out.setAccessible(true);
            out.set(null, new PrintStream(outputStream));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            Field out = System.class.getDeclaredField("err");
            Field fieldAccessFlags = Field.class.getDeclaredField("accessFlags");
            fieldAccessFlags.setAccessible(true);
            int outModifiers = out.getModifiers();
            fieldAccessFlags.set(out, outModifiers & -17);
            out.setAccessible(true);
            out.set(null, new PrintStream(outputStream));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
