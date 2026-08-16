package mod.alucard.tn.apksigner;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.apksig.ApkSigner.SignerConfig;
import com.android.apksig.KeyConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import mod.jbk.build.BuiltInLibraries;

public class ApkSigner {

    private static final File EXTRACTED_TESTKEY_FILES_DIRECTORY = new File(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH, "testkey");

    public void signWithTestKey(@NonNull String inputPath, @NonNull String outputPath, @Nullable LogCallback callback) {
        try (LogWriter logger = new LogWriter(callback)) {
            long savedTimeMillis = System.currentTimeMillis();
            logger.write("Signing APK with testkey using direct ApkSigner API...");

            File keyFile = new File(EXTRACTED_TESTKEY_FILES_DIRECTORY, "testkey.pk8");
            File certFile = new File(EXTRACTED_TESTKEY_FILES_DIRECTORY, "testkey.x509.pem");

            PrivateKey privateKey = readPrivateKey(keyFile);
            X509Certificate certificate = readCertificate(certFile);

            KeyConfig keyConfig = new KeyConfig.Jca(privateKey);
            SignerConfig signerConfig = new SignerConfig.Builder(
                    "CERT", keyConfig, Collections.singletonList(certificate)
            ).build();

            com.android.apksig.ApkSigner signer = new com.android.apksig.ApkSigner.Builder(
                    Collections.singletonList(signerConfig)
            )
                    .setInputApk(new File(inputPath))
                    .setOutputApk(new File(outputPath))
                    .setV1SigningEnabled(true)
                    .setV2SigningEnabled(true)
                    .setV3SigningEnabled(true)
                    .build();

            signer.sign();

            logger.write("Signing APK took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
        } catch (Exception e) {
            LogCallback.errorCount.incrementAndGet();
            if (callback != null) {
                callback.onNewLineLogged("Failed to sign APK with testkey: " + Log.getStackTraceString(e));
            }
        }
    }

    public void signWithKeyStore(@NonNull String inputFilePath, @NonNull String outputFilePath,
                                 @NonNull String keyStorePath, @NonNull String keyStorePassword,
                                 @NonNull String keyStoreKeyAlias, @NonNull String keyPassword, @Nullable LogCallback callback) {
        try (LogWriter logger = new LogWriter(callback)) {
            long savedTimeMillis = System.currentTimeMillis();
            logger.write("Signing APK with Keystore using direct ApkSigner API...");

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            try (InputStream is = new FileInputStream(keyStorePath)) {
                keyStore.load(is, keyStorePassword.toCharArray());
            }

            PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyStoreKeyAlias, keyPassword.toCharArray());
            Certificate[] certChain = keyStore.getCertificateChain(keyStoreKeyAlias);
            List<X509Certificate> certificates = new ArrayList<>();
            if (certChain != null) {
                for (Certificate cert : certChain) {
                    if (cert instanceof X509Certificate) {
                        certificates.add((X509Certificate) cert);
                    }
                }
            }

            KeyConfig keyConfig = new KeyConfig.Jca(privateKey);
            SignerConfig signerConfig = new SignerConfig.Builder(
                    keyStoreKeyAlias, keyConfig, certificates
            ).build();

            com.android.apksig.ApkSigner signer = new com.android.apksig.ApkSigner.Builder(
                    Collections.singletonList(signerConfig)
            )
                    .setInputApk(new File(inputFilePath))
                    .setOutputApk(new File(outputFilePath))
                    .setV1SigningEnabled(true)
                    .setV2SigningEnabled(true)
                    .setV3SigningEnabled(true)
                    .build();

            signer.sign();

            logger.write("Signing APK took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
        } catch (Exception e) {
            LogCallback.errorCount.incrementAndGet();
            if (callback != null) {
                callback.onNewLineLogged("Failed to sign APK with Keystore: " + Log.getStackTraceString(e));
            }
        }
    }

    private PrivateKey readPrivateKey(File keyFile) throws Exception {
        byte[] keyBytes = Files.readAllBytes(keyFile.toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    private X509Certificate readCertificate(File certFile) throws Exception {
        try (InputStream is = new FileInputStream(certFile)) {
            return (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(is);
        }
    }

    public interface LogCallback {
        AtomicInteger errorCount = new AtomicInteger(0);

        void onNewLineLogged(String line);
    }

    private static class LogWriter extends OutputStream {

        private final LogCallback mCallback;
        private String mCache = "";

        private LogWriter(LogCallback callback) {
            mCallback = callback;
        }

        @Override
        public void write(int b) {
            if (isLoggingDisabled()) return;

            mCache += (char) b;

            if (((char) b) == '\n') {
                mCallback.onNewLineLogged(mCache);
                mCache = "";
            }
        }

        private void write(String s) {
            if (isLoggingDisabled()) return;

            for (byte b : s.getBytes()) {
                write(b);
            }
        }

        private boolean isLoggingDisabled() {
            return mCallback == null;
        }
    }
}
