package mod.agus.jcoderz.dex.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public final class FileUtils {
    private FileUtils() {
    }

    public static byte[] readFile(String str) {
        return readFile(new File(str));
    }

    public static byte[] readFile(File file) {
        if (!file.exists()) {
            throw new RuntimeException(file + ": file not found");
        } else if (!file.isFile()) {
            throw new RuntimeException(file + ": not a file");
        } else if (!file.canRead()) {
            throw new RuntimeException(file + ": file not readable");
        } else {
            long length = file.length();
            int i = (int) length;
            if (((long) i) != length) {
                throw new RuntimeException(file + ": file too long");
            }
            byte[] bArr = new byte[i];
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                int i2 = 0;
                while (i > 0) {
                    int read = fileInputStream.read(bArr, i2, i);
                    if (read == -1) {
                        throw new RuntimeException(file + ": unexpected EOF");
                    }
                    i2 += read;
                    i -= read;
                }
                fileInputStream.close();
                return bArr;
            } catch (IOException e) {
                throw new RuntimeException(file + ": trouble reading", e);
            }
        }
    }

    public static boolean hasArchiveSuffix(String str) {
        return str.endsWith(".zip") || str.endsWith(".jar") || str.endsWith(".apk");
    }
}
