package mod.hey.studios.compiler.kotlin

import a.a.a.ProjectBuilder
import mod.hey.studios.build.BuildSettings
import mod.hey.studios.compiler.kotlin.KotlinCompilerUtil.*
import mod.jbk.util.LogUtil
import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments
import org.jetbrains.kotlin.cli.jvm.K2JVMCompiler
import org.jetbrains.kotlin.config.Services
import java.io.File

/**
 * Partly adapted from:
 * [https://github.com/tyron12233/CodeAssist/blob/main/build-logic/src/main/java/com/tyron/builder/compiler/incremental/kotlin/IncrementalKotlinCompiler.java]
 *
 * A huge thank you to [tyron][https://github.com/tyron12233] for porting `kotlinc` to Android.
 */
class KotlinCompiler(
    private val builder: ProjectBuilder
) {
    private val workspace = builder.yq

    /**
     * Invokes `kotlinc`.
     */
    @Throws(Throwable::class)
    fun compile() {
        val timeMillis = System.currentTimeMillis()

        val filesToCompile = getFilesToCompile(workspace).apply {
            if (!areAnyKtFilesPresent(workspace)) {
                LogUtil.d(TAG, "No kotlin source files found, skipping kotlinc")
                return
            }
        }

        val mKotlinHome = File(KotlinCompilerBridge.getKotlinHome(workspace)).apply { mkdirs() }
        // Output in the same place as ecj, makes everything easier
        val mClassOutput = File(workspace.compiledClassesPath).apply { mkdirs() }

        val arguments = mutableListOf<String>().apply {
            // Classpath
            add("-cp")
            add(builder.getClasspath())

            // Sources (.java & .kt)
            addAll(filesToCompile.map { it.absolutePath })
        }

        val compiler = K2JVMCompiler()
        val collector = DiagnosticCollector()
        val plugins = getCompilerPlugins(workspace).map(File::getAbsolutePath).toTypedArray()

        val args = K2JVMCompilerArguments().apply {
            compileJava = false
            includeRuntime = false
            noJdk = true
            noReflect = true
            noStdlib = true

            kotlinHome = mKotlinHome.absolutePath
            destination = mClassOutput.absolutePath
            pluginClasspaths = plugins
        }

        LogUtil.d(TAG, "Running kotlinc with these arguments: $arguments")

        compiler.parseArguments(arguments.toTypedArray(), args)
        compiler.exec(collector, Services.EMPTY, args)

        // Log all diagnostics
        LogUtil.d(TAG, "kotlinc MessageCollector: $collector")

        // kotlinc generates some .kotlin_module files that make D8 fail,
        // delete them for now (?) TODO
        File(mClassOutput, "META-INF").deleteRecursively()

        if (collector.hasErrors()) {
            LogUtil.e(TAG, "Failed to compile Kotlin files")
            throw Exception(collector.getDiagnostics(areWarningsEnabled()))
        } else {
            LogUtil.d(
                TAG,
                "Compiling Kotlin files took ${System.currentTimeMillis() - timeMillis} ms"
            )
        }
    }

    private fun areWarningsEnabled(): Boolean {
        return builder.build_settings.getValue(
            BuildSettings.SETTING_NO_WARNINGS,
            BuildSettings.SETTING_GENERIC_VALUE_TRUE
        ) != BuildSettings.SETTING_GENERIC_VALUE_TRUE
    }

    companion object {
        const val TAG = "KotlinCompiler"
    }
}
