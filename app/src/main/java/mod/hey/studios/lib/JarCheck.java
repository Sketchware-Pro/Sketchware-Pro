package mod.hey.studios.lib;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class JarCheck {
    private static final int chunkLength = 8;
    private static final byte[] expectedMagicNumber = {-54, -2, -70, -66};

    public static boolean checkJar(String str, int i, int i2) {
        boolean z = true;
        ZipInputStream zipInputStream = null;
        try {
            zipInputStream = new ZipInputStream(new FileInputStream(str));
            while (true) {
                ZipEntry nextEntry = zipInputStream.getNextEntry();
                if (nextEntry == null) {
                    break;
                } else if (nextEntry.getName().endsWith(".class")) {
                    byte[] bArr = new byte[chunkLength];
                    int read = zipInputStream.read(bArr, 0, chunkLength);
                    zipInputStream.closeEntry();
                    if (read != chunkLength) {
                        z = false;
                    } else {
                        int i3 = 0;
                        while (true) {
                            if (i3 >= expectedMagicNumber.length) {
                                int i4 = ((bArr[6] & 255) << chunkLength) + (bArr[7] & 255);
                                if (i > i4 || i4 > i2) {
                                    z = false;
                                }
                            } else if (bArr[i3] != expectedMagicNumber[i3]) {
                                z = false;
                                break;
                            } else {
                                i3++;
                            }
                        }
                    }
                }
            }
        } catch (EOFException e) {
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            zipInputStream.close();
            return z;
        } catch (IOException e2) {
            return false;
        }
    }
}
