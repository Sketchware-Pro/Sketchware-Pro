package mod.jbk.util;

import org.spongycastle.jce.provider.BouncyCastleProvider;

import java.io.IOException;
import java.security.GeneralSecurityException;

import kellinwood.security.zipsigner.ZipSigner;
import kellinwood.security.zipsigner.optional.KeyStoreFileManager;

public class TestkeySignBridge {
    private TestkeySignBridge() {
    }

    public static void signWithTestkey(String inputPath, String outputPath) throws GeneralSecurityException, IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        ZipSigner zipSigner = new ZipSigner();
        KeyStoreFileManager.setProvider(new BouncyCastleProvider());
        zipSigner.setKeymode(ZipSigner.KEY_TESTKEY);
        zipSigner.signZip(inputPath, outputPath);
    }
}
