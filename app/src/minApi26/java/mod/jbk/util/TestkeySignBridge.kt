package mod.jbk.util

import mod.alucard.tn.apksigner.ApkSigner
import java.io.IOException
import java.security.GeneralSecurityException

object TestkeySignBridge {
    @JvmStatic
    @Throws(
        GeneralSecurityException::class,
        IOException::class,
        ClassNotFoundException::class,
        IllegalAccessException::class,
        InstantiationException::class
    )
    fun signWithTestkey(inputPath: String, outputPath: String) {
        val signer = ApkSigner()
        signer.signWithTestKey(inputPath, outputPath, null)
    }
}
