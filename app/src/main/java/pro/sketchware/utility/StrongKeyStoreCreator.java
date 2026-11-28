package pro.sketchware.utility;

import org.spongycastle.jce.X509Principal;
import org.spongycastle.x509.X509V3CertificateGenerator;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Date;

import kellinwood.security.zipsigner.optional.KeyStoreFileManager;

/**
 * Generates Google Play-compliant release keystores.
 *
 * <p>Replaces the legacy {@code a.a.a.iI} generator, which signed the self-signed certificate
 * with {@code MD5WithRSAEncryption} (rejected by Play as "key too weak") and passed the
 * certificate validity in years straight into {@link KeyPairGenerator#initialize(int)}.
 *
 * <p>This implementation always uses an RSA 2048-bit key and a SHA-256 based signature,
 * which satisfies Play App Signing upload certificate requirements.
 */
public final class StrongKeyStoreCreator {

    /** Key size in bits. Google Play requires at least 2048 for upload keys. */
    public static final int KEY_SIZE_BITS = 2048;

    /** Digest/signature algorithm for the self-signed certificate. Never SHA-1 or MD5. */
    public static final String CERT_SIGNATURE_ALGORITHM = "SHA256withRSA";

    /** Default validity for newly generated keys, in years. */
    public static final int DEFAULT_VALIDITY_YEARS = 25;

    private static final long MILLIS_PER_YEAR = 1000L * 60L * 60L * 24L * 366L;

    private StrongKeyStoreCreator() {
    }

    /**
     * Creates a new JKS keystore holding one RSA-2048 key with a SHA256withRSA self-signed
     * certificate.
     *
     * @param distinguishedName DN in RFC 2253 form, e.g. {@code CN=myapp, OU=dev, O=me, L=city, ST=state, C=US}
     * @param validityYears     certificate validity in years (clamped to at least 1)
     * @param alias             alias for the private key entry
     * @param password          password for both the keystore and the key
     * @return the serialized JKS keystore bytes
     */
    public static byte[] createKeystore(String distinguishedName, int validityYears,
                                        String alias, char[] password) throws Exception {
        if (alias == null || alias.isEmpty()) {
            throw new IllegalArgumentException("Keystore alias must not be empty");
        }
        if (password == null || password.length == 0) {
            throw new IllegalArgumentException("Keystore password must not be empty");
        }
        int years = Math.max(validityYears, 1);

        KeyPair keyPair = generateKeyPair();
        X509Certificate certificate = generateCertificate(distinguishedName, years, keyPair);

        KeyStore keyStore = KeyStoreFileManager.createKeyStore("release_key.jks", password);
        keyStore.setKeyEntry(alias, keyPair.getPrivate(), password,
                new java.security.cert.Certificate[]{certificate});

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        keyStore.store(output, password);
        return output.toByteArray();
    }

    private static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(KEY_SIZE_BITS, new SecureRandom());
        return generator.generateKeyPair();
    }

    private static X509Certificate generateCertificate(String distinguishedName, int validityYears,
                                                       KeyPair keyPair) throws Exception {
        X509V3CertificateGenerator generator = new X509V3CertificateGenerator();

        BigInteger serialNumber = new BigInteger(64, new SecureRandom()).abs();
        if (serialNumber.signum() == 0) {
            serialNumber = BigInteger.ONE;
        }
        generator.setSerialNumber(serialNumber);

        X509Principal principal = new X509Principal(distinguishedName);
        generator.setIssuerDN(principal);
        generator.setSubjectDN(principal);

        long now = System.currentTimeMillis();
        generator.setNotBefore(new Date(now - 24L * 60L * 60L * 1000L)); // 1 day back to absorb clock skew
        generator.setNotAfter(new Date(now + MILLIS_PER_YEAR * validityYears));

        generator.setPublicKey(keyPair.getPublic());
        generator.setSignatureAlgorithm(CERT_SIGNATURE_ALGORITHM);

        return generator.generate(keyPair.getPrivate());
    }
}
