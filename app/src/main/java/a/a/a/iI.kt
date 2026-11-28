package a.a.a

import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.io.OutputStream
import java.math.BigInteger
import java.nio.ByteBuffer
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.security.SecureRandom
import java.security.Security
import java.security.cert.Certificate
import java.security.cert.X509Certificate
import java.util.Date
import kellinwood.security.zipsigner.ZipSigner
import kellinwood.security.zipsigner.optional.JksKeyStore
import kellinwood.security.zipsigner.optional.LoadKeystoreException
import org.spongycastle.jce.X509Principal
import org.spongycastle.jce.provider.BouncyCastleProvider
import org.spongycastle.x509.X509V3CertificateGenerator

class iI {
    @JvmField
    var a: KeyStore

    @JvmField
    var b: ByteBuffer? = null

    init {
        Security.addProvider(BouncyCastleProvider())
        a = JksKeyStore()
    }

    fun a(): String {
        val aliases = a.aliases()
        return if (aliases.hasMoreElements()) aliases.nextElement() else ""
    }

    @Throws(Exception::class)
    fun a(inputStream: InputStream?, str: String) {
        if (inputStream == null) {
            return
        }
        try {
            try {
                a.load(inputStream, str.toCharArray())
            } catch (e: Exception) {
                e.printStackTrace()
                throw Exception(e.message)
            }
        } finally {
            try {
                inputStream.close()
            } catch (_: Exception) {
            }
        }
    }

    @Throws(Exception::class)
    fun a(str: String, str2: String) {
        a(FileInputStream(File(str)), str2)
    }

    @Throws(Exception::class)
    fun a(str: String, str2: String, i: Int, str3: String, str4: String) {
        val bytes = a(str2, i, str3, str4)
        val file = File(wq.i())
        if (!file.exists()) {
            file.mkdirs()
        }
        oB().a(str, bytes)
    }

    @Throws(Exception::class)
    private fun a(str: String): ByteArray {
        val buffer = ByteBuffer.allocate(8192)
        b = buffer
        a.store(object : OutputStream() {
            override fun write(i: Int) {
                b?.put(i.toByte())
            }
        }, str.toCharArray())
        val bytes = ByteArray(buffer.position())
        System.arraycopy(buffer.array(), 0, bytes, 0, buffer.position())
        return bytes
    }

    @Throws(Exception::class)
    fun a(str: String, i: Int, str2: String, str3: String): ByteArray {
        try {
            val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
            keyPairGenerator.initialize(1024, SecureRandom.getInstance("SHA1PRNG"))
            val generateKeyPair = keyPairGenerator.generateKeyPair()
            val certGen = X509V3CertificateGenerator()
            val principal = X509Principal(str)
            var serial = SecureRandom().nextInt()
            if (serial < 0) {
                serial *= -1
            }
            certGen.setSerialNumber(BigInteger.valueOf(serial.toLong()))
            certGen.setIssuerDN(principal)
            certGen.setNotBefore(Date(System.currentTimeMillis()))
            certGen.setNotAfter(Date(System.currentTimeMillis() + i * 31536000000L))
            certGen.setSubjectDN(principal)
            certGen.setPublicKey(generateKeyPair.public)
            certGen.setSignatureAlgorithm("MD5WithRSAEncryption")
            val cert = certGen.generateX509Certificate(generateKeyPair.private)
            a = JksKeyStore()
            a.load(null, str3.toCharArray())
            a.setKeyEntry(str2, generateKeyPair.private, str3.toCharArray(), arrayOf<Certificate>(cert))
            return a(str3)
        } catch (e: LoadKeystoreException) {
            Log.e("ERROR", "Failed to access keystore. incorrect passward")
            throw e
        }
    }

    @Throws(Exception::class)
    fun b(str: String): ZipSigner {
        val zipSigner = ZipSigner()
        zipSigner.issueLoadingCertAndKeysProgressEvent()
        val alias = a()
        if (alias.isEmpty()) {
            throw IllegalStateException("No key alias found in keystore")
        }
        val cert = a.getCertificate(alias) as? X509Certificate
            ?: throw IllegalStateException("Missing X509Certificate for alias $alias")
        val key = a.getKey(alias, str.toCharArray()) as? PrivateKey
            ?: throw IllegalStateException("Missing PrivateKey for alias $alias")
        zipSigner.setKeys(
            "custom",
            cert,
            key,
            "SHA1WITHRSA",
            null
        )
        return zipSigner
    }
}
