package extensions.anbui.daydream.project;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import extensions.anbui.daydream.configs.Configs;

public class ProjectDataDecryptor {

    //Since it is encrypted, it needs to be decrypted before reading.
    public static String decryptProjectFile(String path) {
        try {
            // Readfile
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            byte[] encrypted = new byte[(int) file.length()];
            if (fis.read(encrypted) != encrypted.length) {
                fis.close();
                Log.e("DecryptError", "Error reading file.");
                return "";
            }
            fis.close();

            // Key and IV
            byte[] key = Configs.encryptionKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec iv = new IvParameterSpec(key);

            // AES Decryption
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(encrypted);
            return new String(original, StandardCharsets.UTF_8);

        } catch (Exception e) {
            Log.e("DecryptError", "Decryption failed: " + e.getMessage(), e);
            return "";
        }
    }
}
