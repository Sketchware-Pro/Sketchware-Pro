package mod.jbk.util;

import java.io.IOException;
import java.security.GeneralSecurityException;

import mod.yamenher.ApkSignerUtils;

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
