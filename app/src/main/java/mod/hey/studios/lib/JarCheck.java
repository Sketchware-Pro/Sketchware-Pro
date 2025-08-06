package mod.hey.studios.lib;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

// used to check the java version of a JAR file

public final class JarCheck {

    private static final int chunkLength = 8;
    private static final byte[] expectedMagicNumber =
            {(byte) 0xca, (byte) 0xfe, (byte) 0xba, (byte) 0xbe};

    public static boolean checkJar(String jarFilename, int low, int high) {
        boolean success = true;
        FileInputStream fis;
        ZipInputStream zip = null;

        try {
            try {
                fis = new FileInputStream(jarFilename);
                zip = new ZipInputStream(fis);

                entryLoop:
                while (true) {
                    ZipEntry entry = zip.getNextEntry();

                    if (entry == null) break;

                    String elementName = entry.getName();
                    if (!elementName.endsWith(".class")) continue;

                    byte[] chunk = new byte[chunkLength];
                    int bytesRead = zip.read(chunk, 0, chunkLength);
                    zip.closeEntry();

                    if (bytesRead != chunkLength) {
                        success = false;
                        continue;
                    }

                    for (int i = 0; i < expectedMagicNumber.length; i++) {
                        if (chunk[i] != expectedMagicNumber[i]) {
                            success = false;
                            continue entryLoop;
                        }
                    }

                    int major =
                            ((chunk[chunkLength - 2] & 0xff) << 8) +
                                    (chunk[chunkLength - 1] & 0xff);

                    if (!(low <= major && major <= high)) {
                        success = false;
                    }
                }
            } catch (EOFException ignored) {
            }

            zip.close();
            return success;
        } catch (IOException e) {
            return false;
        }
    }
}
