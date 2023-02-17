package mod.jbk.util;

import java.io.IOException;
import java.security.GeneralSecurityException;

import mod.alucard.tn.apksigner.ApkSigner;

public class TestkeySignBridge {
    private TestkeySignBridge() {
    }

    public static void signWithTestkey(String inputPath, String outputPath) throws GeneralSecurityException, IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        ApkSigner signer = new ApkSigner();
        signer.signWithTestKey(inputPath, outputPath, null);
    }
}
