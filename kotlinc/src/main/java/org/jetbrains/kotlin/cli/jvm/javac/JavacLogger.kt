/*
 * This file is part of Cosmic IDE.
 * Cosmic IDE is a free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * Cosmic IDE is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with Foobar. If not, see <https://www.gnu.org/licenses/>.
 */

package org.jetbrains.kotlin.cli.jvm.javac

import com.sun.tools.javac.util.Context
import com.sun.tools.javac.util.Log
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.GroupingMessageCollector
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import java.io.PrintWriter
import java.io.Writer

class JavacLogger(
    context: Context,
    errorWriter: PrintWriter,
    @Suppress("UNUSED_PARAMETER") warningWriter: PrintWriter,
    infoWriter: PrintWriter
) : Log(context, infoWriter, errorWriter) {

    override fun printLines(kind: WriterKind, message: String, vararg args: Any?) {}

    companion object {
        fun preRegister(context: Context, messageCollector: MessageCollector) {
            context.put(
                logKey,
                Context.Factory<Log> {
                    JavacLogger(
                        it,
                        PrintWriter(
                            MessageCollectorAdapter(
                                messageCollector,
                                CompilerMessageSeverity.ERROR
                            )
                        ),
                        PrintWriter(
                            MessageCollectorAdapter(
                                messageCollector,
                                CompilerMessageSeverity.WARNING
                            )
                        ),
                        PrintWriter(
                            MessageCollectorAdapter(
                                messageCollector,
                                CompilerMessageSeverity.INFO
                            )
                        )
                    )
                }
            )
        }
    }
}

private class MessageCollectorAdapter(
    private val messageCollector: MessageCollector,
    private val severity: CompilerMessageSeverity
) : Writer() {
    override fun write(buffer: CharArray, offset: Int, length: Int) {
        if (length == 1 && buffer[0] == '\n') return

        messageCollector.report(severity, String(buffer, offset, length))
    }

    override fun flush() {
        (messageCollector as? GroupingMessageCollector)?.flush()
    }

    override fun close() = flush()
}
