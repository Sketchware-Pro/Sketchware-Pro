package mod.trindadedev.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class ApkUtils {

    private String apkPath;

    public ApkUtils() {
    }

    public void setApkPath(String path) {
        apkPath = path;
    }

    public String getSHA1() {
        return getSignature("SHA-1");
    }

    public String getSHA256() {
        return getSignature("SHA-256");
    }

    private String getSignature(String algorithm) {
        if (apkPath == null || apkPath.isEmpty()) {
            return "APK path must be set before computing signature.";
        }

        try (FileInputStream fis = new FileInputStream(apkPath)) {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }

            return byteArrayToHex(digest.digest());
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String byteArrayToHex(byte[] hashBytes) {
        try (Formatter formatter = new Formatter()) {
            for (byte b : hashBytes) {
                formatter.format("%02X:", b);
            }
            String result = formatter.toString();
            return result.substring(0, result.length() - 1);  // Remove the last colon
        }
    }
}
