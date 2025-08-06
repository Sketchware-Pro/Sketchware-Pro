package mod.hey.studios.compiler.kotlin

import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation
import java.util.Locale

class Diagnostic(
    val severity: CompilerMessageSeverity,
    val message: String,
    val location: CompilerMessageSourceLocation?
) {

    override fun toString() = buildString {
        append(severity.presentableName.uppercase(Locale.ENGLISH))

        location?.let { loc ->
            append(" in ")
            append(loc.path)
            append(" (at ")
            append(loc.line)
            append(":")
            append(loc.column)
            append(")")

            append(System.lineSeparator())
            append(loc.lineContent.orEmpty())
        }

        append(System.lineSeparator())
        append(message)
    }

}
