package mod.hey.studios.compiler.kotlin

import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation
import org.jetbrains.kotlin.cli.common.messages.MessageCollector

class DiagnosticCollector : MessageCollector {
    private val diagnostics = mutableListOf<Diagnostic>()

    override fun clear() {
        diagnostics.clear()
    }

    override fun hasErrors(): Boolean {
        return diagnostics.any { it.severity.isError }
    }

    override fun report(
        severity: CompilerMessageSeverity,
        message: String,
        location: CompilerMessageSourceLocation?
    ) {
        diagnostics += Diagnostic(severity, message, location)
    }

    /**
     * Stringifies only warning (if [showWarnings] is enabled)
     * & error diagnostics, in ecj style.
     */
    fun getDiagnostics(showWarnings: Boolean = true) = buildString {
        diagnostics.filter {
            it.severity.isError || (it.severity.isWarning && showWarnings)
        }.forEachIndexed { index, diagnostic ->
            append("----------")
            append(System.lineSeparator())

            append(index + 1)
            append(". ")
            append(diagnostic)
            append(System.lineSeparator())
        }
    }

    /**
     * Stringifies all diagnostics, in ecj style.
     */
    override fun toString() = buildString {
        diagnostics.forEachIndexed { index, diagnostic ->
            append("----------")
            append(System.lineSeparator())

            append(index + 1)
            append(". ")
            append(diagnostic)
            append(System.lineSeparator())
        }
    }
}
