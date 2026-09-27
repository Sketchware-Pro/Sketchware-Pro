# ============================================================================
# R8 / ProGuard Rules
# Mode: Unused Code Shrinking + Bytecode Optimization (STRICTLY NO OBFUSCATION)
# ============================================================================

# ----------------------------------------------------------------------------
# 1. Obfuscation & Renaming Disablement
# ----------------------------------------------------------------------------
# Strictly prevent R8/ProGuard from obfuscating or renaming ANY class, method, or field name
-dontobfuscate

# Preserve stack traces, line numbers, parameter names, generic signatures, and inner class structures
-keepattributes SourceFile,LineNumberTable,Signature,InnerClasses,EnclosingMethod,*Annotation*,Exceptions,RuntimeVisibleAnnotations,RuntimeVisibleParameterAnnotations,PermittedSubclasses,Record

# Preserve original source file names for crash reports
-renamesourcefileattribute SourceFile

# ----------------------------------------------------------------------------
# 2. Bytecode Optimization Configuration
# ----------------------------------------------------------------------------
# Allow R8 bytecode optimization passes (method inlining, dead code removal, constant folding)
-optimizationpasses 5
-allowaccessmodification

# Skip arithmetic/cast simplification if needed for precise float/double precision
-optimizations !code/simplification/arithmetic,!code/simplification/cast

# ----------------------------------------------------------------------------
# 3. Android Components & Application Lifecycle
# ----------------------------------------------------------------------------
-keep class * extends android.app.Activity { <init>(...); }
-keep class * extends android.app.Service { <init>(...); }
-keep class * extends android.content.BroadcastReceiver { <init>(...); }
-keep class * extends android.content.ContentProvider { <init>(...); }
-keep class * extends android.app.Application { <init>(...); }
-keep class * extends android.view.View { <init>(...); }
-keep class * extends android.preference.Preference { <init>(...); }
-keep class * extends androidx.preference.Preference { <init>(...); }
-keep class * extends androidx.fragment.app.Fragment { <init>(...); }
-keep class * extends androidx.recyclerview.widget.RecyclerView$ViewHolder { <init>(...); }

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# ----------------------------------------------------------------------------
# 4. R Classes and BuildConfig Constants
# ----------------------------------------------------------------------------
-keep class **.R$* { *; }
-keep class **.R { *; }
-keep class **.BuildConfig { *; }

# ----------------------------------------------------------------------------
# 5. Sketchware Data Beans, Models & Core Packages
# ----------------------------------------------------------------------------
-keep class com.besome.sketch.beans.** { *; }
-keep class mod.** { *; }
-keep class dev.aldi.sayuti.** { *; }
-keep class a.a.a.** { *; }
-keep class next.sketchware.** { *; }
-keep class pro.sketchware.** { *; }

# ----------------------------------------------------------------------------
# 6. Gson & JSON Serialization Models, Parcelable & Serializable
# ----------------------------------------------------------------------------
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
-keep class com.google.gson.** { *; }
-keep class * implements java.io.Serializable { *; }
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
    *;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# ----------------------------------------------------------------------------
# 7. In-App Compilers & Runtime Tooling (StringFog, ASM, ECJ, D8/R8, ZipAlign, Signer)
# ----------------------------------------------------------------------------
-keep class com.github.megatronking.stringfog.** { *; }
-keep class org.objectweb.asm.** { *; }
-keep class org.eclipse.jdt.** { *; }
-keep class com.android.tools.r8.** { *; }
-keep class com.android.apksig.** { *; }
-keep class com.android.dx.** { *; }
-keep class com.android.dex.** { *; }
-keep class org.conscrypt.** { *; }
-keep class com.caverock.androidsvg.** { *; }
-keep class com.github.Iyxan23.zipalign.** { *; }
-keep class io.github.rosemoe.sora.** { *; }
-keep class kellinwood.security.zipsigner.** { *; }

# ----------------------------------------------------------------------------
# 8. Cryptographic Libraries (BouncyCastle, SCPKIX, Woodstox)
# ----------------------------------------------------------------------------
-keep class org.bouncycastle.** { *; }
-keep class org.spongycastle.** { *; }
-keep class com.fasterxml.woodstox.** { *; }
-keep class javax.xml.stream.** { *; }

# ----------------------------------------------------------------------------
# 9. Google Material Components, AndroidX & Firebase
# ----------------------------------------------------------------------------
-keep class com.google.android.material.** { *; }
-keep class androidx.** { *; }
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }

# ----------------------------------------------------------------------------
# 10. Suppress Warnings for Optional Desktop JVM & Internal Third-Party Classes
# ----------------------------------------------------------------------------
-dontwarn org.eclipse.jdt.**
-dontwarn org.jaxen.**
-dontwarn org.jetbrains.annotations.**
-dontwarn org.slf4j.**
-dontwarn org.tukaani.xz.**
-dontwarn sun.misc.**
-dontwarn sun.reflect.**
-dontwarn sun.security.**
-dontwarn nbjavac.**
-dontwarn com.android.sdklib.**
-dontwarn org.jdom2.**
-dontwarn org.apache.commons.compress.**
-dontwarn org.codehaus.stax2.**
-dontwarn org.xmlpull.v1.**
-dontwarn org.kxml2.**

# Suppress optional Desktop Java SE packages and optional logger bridges
-dontwarn java.lang.module.**
-dontwarn java.rmi.**
-dontwarn javax.management.**
-dontwarn javax.naming.**
-dontwarn javax.script.**
-dontwarn javax.swing.**
-dontwarn kotlin.annotations.**
-dontwarn kotlinx.coroutines.internal.intellij.**
-dontwarn org.apache.log.**
-dontwarn org.apache.log4j.**
-dontwarn org.apache.logging.log4j.**

# ----------------------------------------------------------------------------
# 11. R8 Auto-Generated Missing Rules (Optional Desktop/Tools Classes)
# ----------------------------------------------------------------------------
-dontwarn a.a.a.IB
-dontwarn a.a.a.kB
-dontwarn a.a.a.lB
-dontwarn a.a.a.tB
-dontwarn aQute.**
-dontwarn com.android.tools.r8.**
-dontwarn com.google.auto.**
-dontwarn com.google.protobuf.**
-dontwarn com.ibm.icu.**
-dontwarn com.intellij.**
-dontwarn com.sun.**
-dontwarn java.applet.**
-dontwarn java.awt.**
-dontwarn java.beans.**
-dontwarn java.lang.Module
-dontwarn java.lang.management.**
-dontwarn kotlin.**
-dontwarn javassist.**

# ----------------------------------------------------------------------------
# 12. XML Parsers & DOM Interfaces (Prevent R8 Return Type Narrowing Crash)
# ----------------------------------------------------------------------------
-keep class javax.xml.parsers.** { *; }
-keepclassmembers class javax.xml.parsers.** { *; }
-keep class javax.xml.transform.** { *; }
-keepclassmembers class javax.xml.transform.** { *; }
-keep interface org.w3c.dom.** { *; }
-keep interface org.xml.sax.** { *; }




