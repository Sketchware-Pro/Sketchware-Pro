package pro.sketchware.core.debug;

import android.content.Context;
import android.util.AtomicFile;

import androidx.annotation.NonNull;

import org.spongycastle.jce.X509Principal;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.x509.X509V3CertificateGenerator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.github.muntashirakon.adb.AbsAdbConnectionManager;

/** The same private, non-backed-up identity is used for pairing and every later connection. */
final class RemoteAdbClient extends AbsAdbConnectionManager {
    private final PrivateKey privateKey;
    private final Certificate certificate;

    @SuppressWarnings("deprecation")
    RemoteAdbClient(Context context) throws Exception {
        AtomicFile identity = new AtomicFile(new File(context.getNoBackupFilesDir(), "remote-adb-identity"));
        if (identity.getBaseFile().exists()) {
            try (DataInputStream input = new DataInputStream(identity.openRead())) {
                privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(readBytes(input)));
                certificate = CertificateFactory.getInstance("X.509")
                        .generateCertificate(new java.io.ByteArrayInputStream(readBytes(input)));
            }
        } else {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048, new SecureRandom());
            var pair = generator.generateKeyPair();
            privateKey = pair.getPrivate();
            Security.addProvider(new BouncyCastleProvider());
            X509V3CertificateGenerator cert = new X509V3CertificateGenerator();
            X509Principal name = new X509Principal("CN=Sketchware Pro Wireless Debugging");
            cert.setSerialNumber(new BigInteger(128, new SecureRandom()).add(BigInteger.ONE));
            cert.setIssuerDN(name);
            cert.setSubjectDN(name);
            cert.setNotBefore(new Date(System.currentTimeMillis() - 86400000L));
            cert.setNotAfter(new Date(System.currentTimeMillis() + 10L * 365 * 86400000));
            cert.setPublicKey(pair.getPublic());
            cert.setSignatureAlgorithm("SHA256withRSA");
            certificate = cert.generate(privateKey, "SC");
            FileOutputStream file = identity.startWrite();
            try {
                DataOutputStream output = new DataOutputStream(file);
                writeBytes(output, privateKey.getEncoded());
                writeBytes(output, certificate.getEncoded());
                output.flush();
                identity.finishWrite(file);
            } catch (Exception error) {
                identity.failWrite(file);
                throw error;
            }
        }
        // This is the minimum supported REMOTE version, not the version of the programming phone.
        setApi(30);
        setTimeout(15, TimeUnit.SECONDS);
        setThrowOnUnauthorised(true);
    }

    private static byte[] readBytes(DataInputStream input) throws java.io.IOException {
        int size = input.readInt();
        if (size <= 0 || size > 16384) throw new java.io.IOException("Invalid ADB identity");
        byte[] bytes = new byte[size];
        input.readFully(bytes);
        return bytes;
    }

    private static void writeBytes(DataOutputStream output, byte[] bytes) throws java.io.IOException {
        output.writeInt(bytes.length);
        output.write(bytes);
    }

    @NonNull @Override protected PrivateKey getPrivateKey() { return privateKey; }
    @NonNull @Override protected Certificate getCertificate() { return certificate; }
    @NonNull @Override protected String getDeviceName() { return "SketchwarePro"; }
}
