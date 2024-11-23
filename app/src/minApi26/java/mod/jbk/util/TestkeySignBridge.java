package mod.jbk.util;

import pro.sketchware.utility.apk.ApkSignerUtils;

public class TestkeySignBridge {
    private TestkeySignBridge() {
    }

    public static void signWithTestkey(String inputPath, String outputPath) {
        try {
            ApkSignerUtils.signWithTestKey(inputPath, outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
