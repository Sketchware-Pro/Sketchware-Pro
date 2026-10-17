package mod.pranav.build

import a.a.a.yq
import android.os.Process
import com.android.tools.r8.CompilationMode
import com.android.tools.r8.OutputMode
import com.android.tools.r8.R8
import com.android.tools.r8.R8Command
import com.android.tools.r8.origin.Origin
import com.google.firebase.crashlytics.FirebaseCrashlytics
import mod.hey.studios.project.proguard.ProguardHandler
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.Executors

class R8Compiler(
    private val rules: MutableList<String>,
    private val configs: Array<String>,
    val libs: Array<String>,
    private val inputs: Array<String>,
    private val minApi: Int,
    val yq: yq
) {

    fun compile() {
        val outputDir = File(yq.binDirectoryPath, "dex")
        outputDir.deleteRecursively()
        outputDir.mkdirs()
        val output = outputDir.toPath()
        val command = R8Command.builder()
            .addProgramFiles(inputs.map { Paths.get(it) })
            .addProguardConfiguration(rules, Origin.unknown())
            .addProguardConfigurationFiles(configs.map { Paths.get(it) })
            .setProguardMapOutputPath(Paths.get(yq.proguardMappingPath))
            .setMinApiLevel(minApi)
            .addLibraryFiles(libs.map { Paths.get(it) })
            .setOutput(output, OutputMode.DexIndexed)
            .setMode(CompilationMode.RELEASE)
            .build()

        /*
         * By default, R8 uses every CPU core, which heats up phones and multiplies peak memory use.
         * Use a few low priority threads instead, it's slower but much cooler and more stable.
         */
        val threads = ProguardHandler(yq.sc_id).getR8Threads()
        val executor = Executors.newFixedThreadPool(threads) { runnable ->
            Thread {
                Process.setThreadPriority(Process.THREAD_PRIORITY_DEFAULT)
                runnable.run()
            }
        }

        try {
            R8.run(command, executor)
        } catch (t: Throwable) {
            try {
                val crashlytics = FirebaseCrashlytics.getInstance()
                crashlytics.setCustomKey("compiler_phase", "R8.run")
                crashlytics.setCustomKey("r8_threads", threads)
                crashlytics.setCustomKey("r8_min_api", minApi)
                crashlytics.setCustomKey("r8_inputs_count", inputs.size)
                crashlytics.setCustomKey("r8_libs_count", libs.size)
                crashlytics.log("R8.run failed with ${t.javaClass.simpleName}: ${t.message}")
                crashlytics.recordException(t)
            } catch (_: Throwable) {
            }
            throw t
        } finally {
            executor.shutdown()
        }
    }
}
