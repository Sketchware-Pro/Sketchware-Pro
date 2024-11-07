package mod.yamenher;

import android.os.Handler;
import android.os.HandlerThread;
import android.sun.security.provider.JavaKeyStoreProvider;
import android.util.Log;

import com.android.apksig.ApkSigner;
import com.android.apksig.util.DataSources;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pro.sketchware.SketchApplication;

public class ApkSignerUtils {

    private static KeyStore keyStore;
    private static int errors;

    public static void signWithReleaseKeystore(String inputFilePath, String outputFilePath, String keyStorePath, String keyStorePassword, String keyStoreKeyAlias, String keyPassword) {
        errors = 0;
        try {
            keyStore = KeyStore.getInstance("JKS", new JavaKeyStoreProvider());
            loadKeystore(keyStorePath, keyPassword);
            PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyStoreKeyAlias, keyStorePassword.toCharArray());
            Certificate[] certChain = keyStore.getCertificateChain(keyStoreKeyAlias);
            validateCertChain(certChain);

            List<X509Certificate> certificateList = extractCertificates(certChain);
            ApkSigner.SignerConfig signerConfig = new ApkSigner.SignerConfig.Builder(keyStoreKeyAlias, privateKey, certificateList, true).build();

            signApk(Collections.singletonList(signerConfig), inputFilePath, outputFilePath);
        } catch (Exception e) {
            Log.e("ApkSignerUtils", "Signing failed: ", e);
            errors++;
        }
    }

    private static void loadKeystore(String keyStorePath, String password) throws Exception {
        try (FileInputStream fis = new FileInputStream(keyStorePath)) {
            keyStore.load(fis, password.toCharArray());
        }
    }

    private static void validateCertChain(Certificate[] certChain) {
        if (certChain == null || certChain.length == 0) {
            Log.e("ApkSignerUtils", "Certificate chain is empty or null.");
            errors++;
        }
    }

    private static List<X509Certificate> extractCertificates(Certificate[] certChain) {
        List<X509Certificate> certList = new ArrayList<>();
        for (Certificate cert : certChain) {
            if (cert instanceof X509Certificate) {
                certList.add((X509Certificate) cert);
            }
        }
        if (certList.isEmpty()) {
            Log.e("ApkSignerUtils", "No valid X509Certificate found.");
            errors++;
        }
        return certList;
    }

    private static void signApk(List<ApkSigner.SignerConfig> signerConfigs, String inputFilePath, String outputFilePath) throws Exception {
        FileChannel inputChannel = new FileInputStream(inputFilePath).getChannel();
        ApkSigner.Builder builder = new ApkSigner.Builder(signerConfigs)
                .setInputApk(DataSources.asDataSource(inputChannel))
                .setOutputApk(new File(outputFilePath))
                .setV1SigningEnabled(true)
                .setV2SigningEnabled(true)
                .setV3SigningEnabled(true)
                .setAlignFileSize(true)
                .setV4SignatureOutputFile(new File(outputFilePath + ".idsig"))
                .setV4SigningEnabled(true);

        executeInHandlerThread(() -> {
            try {
                builder.build().sign();
            } catch (Exception e) {
                Log.e("ApkSignerUtils", "Error during signing: ", e);
                errors++;
            }
        });
    }

    private static void executeInHandlerThread(Runnable task) {
        HandlerThread thread = new HandlerThread("SigningThread");
        thread.start();
        Handler handler = new Handler(thread.getLooper());
        handler.post(() -> {
            task.run();
            thread.quitSafely();
        });
    }

    public static void signWithTestKey(String inputFilePath, String outputFilePath) {
        errors = 0;
        try {
            File pemFile = new File(SketchApplication.getContext().getFilesDir().getPath() + "/libs/testkey/testkey.x509.pem");
            File pk8File = new File(SketchApplication.getContext().getFilesDir().getPath() + "/libs/testkey/testkey.pk8");

            List<X509Certificate> certs = loadCertificates(pemFile);
            PrivateKey privateKey = loadPrivateKey(pk8File);

            ApkSigner.SignerConfig signerConfig = new ApkSigner.SignerConfig.Builder("testkey", privateKey, certs).build();
            signApk(Collections.singletonList(signerConfig), inputFilePath, outputFilePath);
        } catch (Exception e) {
            Log.e("ApkSignerUtils", "Test key signing failed: ", e);
            errors++;
        }
    }

    private static List<X509Certificate> loadCertificates(File pemFile) throws Exception {
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        List<X509Certificate> certificates = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(pemFile))) {
            StringBuilder pemContent = new StringBuilder();
            String line;
            boolean inCert = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("-----BEGIN CERTIFICATE-----")) {
                    inCert = true;
                } else if (line.startsWith("-----END CERTIFICATE-----")) {
                    inCert = false;
                    byte[] certBytes = Base64.getDecoder().decode(pemContent.toString());
                    certificates.add((X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(certBytes)));
                    pemContent.setLength(0);
                } else if (inCert) {
                    pemContent.append(line.trim());
                }
            }
        }
        return certificates;
    }

    private static PrivateKey loadPrivateKey(File pk8File) throws Exception {
        try (FileInputStream fis = new FileInputStream(pk8File);
             ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            byte[] data = new byte[16384];
            int bytesRead;
            while ((bytesRead = fis.read(data)) != -1) {
                buffer.write(data, 0, bytesRead);
            }
            byte[] keyBytes = buffer.toByteArray();
            String keyContent = new String(keyBytes, StandardCharsets.UTF_8);

            if (keyContent.contains("-----BEGIN")) {
                keyBytes = decodePemKey(keyContent);
            }

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
        }
    }

    private static byte[] decodePemKey(String pem) {
        Matcher matcher = Pattern.compile("-----BEGIN (.+?)-----\\s*(.*?)\\s*-----END (.+?)-----", Pattern.DOTALL).matcher(pem);
        if (matcher.find()) {
            String base64Data = Objects.requireNonNull(matcher.group(2)).replaceAll("\\s", "");
            return Base64.getDecoder().decode(base64Data);
        } else {
            throw new NullPointerException("Not a valid PEM key");
        }
    }

    public static int getErrorsCount() {
        return errors;
    }
}