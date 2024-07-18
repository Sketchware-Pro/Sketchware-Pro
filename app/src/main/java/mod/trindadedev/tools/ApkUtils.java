package mod.trindadedev.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ApkUtils {

    private String apkPath;

    public ApkUtils() {
    }

    public void setApkPath(String path) {
        this.apkPath = path;
    }

    public String getSHA1() {
        return getSignature("SHA-1");
    }

    public String getSHA256() {
        return getSignature("SHA-256");
    }

    private String getSignature(String algorithm) {
        FileInputStream fis = null;
        try {
            File apkFile = new File(apkPath);
            if (!apkFile.exists()) {
                return null;
            }

            MessageDigest digest = MessageDigest.getInstance(algorithm);
            fis = new FileInputStream(apkFile);
            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }

            byte[] hashBytes = digest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02X:", b));
            }
            // Remove the last colon
            sb.setLength(sb.length() - 1);

            return sb.toString();
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}