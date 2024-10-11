package mod.jbk.util;

import mod.alucard.tn.apksigner.ApkSigner;

public class TestkeySignBridge {
    private TestkeySignBridge() {
    }

    public static void signWithTestkey(String inputPath, String outputPath) {
        ApkSigner signer = new ApkSigner();
        signer.signWithTestKey(inputPath, outputPath, null);
    }
}
