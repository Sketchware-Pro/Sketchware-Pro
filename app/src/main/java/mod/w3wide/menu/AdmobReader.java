package mod.w3wide.menu;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import mod.agus.jcoderz.lib.FileUtil;

public class AdmobReader {

    private static final String libPath = (FileUtil.getExternalStorageDir() + "/.sketchware/data/");

    public static String getDecodedLib(String sc_id) {
        try {
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] bytes = "sketchwaresecure".getBytes();
            instance.init(Cipher.DECRYPT_MODE, new SecretKeySpec(bytes, "AES"), new IvParameterSpec(bytes));
            RandomAccessFile randomAccessFile = new RandomAccessFile(libPath + sc_id + "/library", "r");
            byte[] bArr = new byte[((int) randomAccessFile.length())];
            randomAccessFile.readFully(bArr);
            return new String(instance.doFinal(bArr));
        } catch (Exception e) {
            return e.toString();
        }
    }

    public static ArrayList<String> getAdUnits(String sc_id) {
        ArrayList<String> adUnits = new ArrayList<>();
        Matcher matcher = Pattern.compile("\"id\":\"(ca-app-pub-\\d{16}/\\d{10})\"").matcher(getDecodedLib(sc_id));
        while (matcher.find()) {
            adUnits.add(matcher.group(1));
        }
        return adUnits;
    }

    public static ArrayList<String> getTestDevices(String sc_id) {
        ArrayList<String> testDevices = new ArrayList<>();
        Matcher matcher = Pattern.compile("\"deviceId\":\"([a-zA-Z0-9]+)\"").matcher(getDecodedLib(sc_id));
        while (matcher.find()) {
            testDevices.add(matcher.group(1));
        }
        return testDevices;
    }
}