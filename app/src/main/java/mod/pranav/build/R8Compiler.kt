package mod.pranav.build

import a.a.a.yq

import com.android.tools.r8.CompilationMode
import com.android.tools.r8.OutputMode
import com.android.tools.r8.R8
import com.android.tools.r8.R8Command
import com.android.tools.r8.origin.Origin

import java.nio.file.Paths
import java.nio.file.Files

class R8Compiler(
    private val rules: MutableList<String>,
    private val configs: Array<String>,
    val libs: Array<String>,
    private val inputs: Array<String>,
    private val minApi: Int,
    val yq: yq
) {

    fun compile() {
        val output = Paths.get(yq.binDirectoryPath, "dex")
        Files.createDirectories(output)
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
        R8.run(command)
    }
}
