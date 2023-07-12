/*
 * This file is part of Cosmic IDE.
 * Cosmic IDE is a free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * Cosmic IDE is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with Foobar. If not, see <https://www.gnu.org/licenses/>.
 */

package org.jetbrains.kotlin.util

import com.intellij.openapi.util.text.StringUtil.isJavaIdentifierPart
import com.intellij.openapi.util.text.StringUtil.isJavaIdentifierStart
import dalvik.system.PathClassLoader
import java.io.File
import java.io.IOError
import java.net.URLClassLoader
import java.nio.file.FileSystemNotFoundException
import java.nio.file.Paths
import java.util.Locale
import java.util.zip.ZipFile

object ServiceLoaderLite {
    private const val SERVICE_DIRECTORY_LOCATION = "META-INF/services/"

    class ServiceLoadingException(val file: File, cause: Throwable) :
        RuntimeException("Error loading services from $file", cause)

    /**
     * Returns implementations for the given `service` declared in META-INF/services of the `classLoader` roots.
     *
     * Note that the behavior is radically different from what Java ServiceLoader does.
     * ServiceLoaderLite doesn't iterate over the whole ClassLoader hierarchy, it takes only the immediate roots of `classLoader`.
     * In fact, this is often the desired behavior.
     */
    fun <Service> loadImplementations(
        service: Class<out Service>,
        classLoader: URLClassLoader
    ): List<Service> {
        val files = classLoader.urLs.map { url ->
            try {
                Paths.get(url.toURI()).toFile()
            } catch (e: FileSystemNotFoundException) {
                throw IllegalArgumentException("Only local URLs are supported, got ${url.protocol}")
            } catch (e: UnsupportedOperationException) {
                throw IllegalArgumentException("Only local URLs are supported, got ${url.protocol}")
            }
        }

        val classpath = classLoader.urLs.joinToString(separator = File.pathSeparator) {
            it.path
        }
        val loader = PathClassLoader(classpath, this::class.java.classLoader)
        return loadImplementations(service, files, loader)
    }

    fun <Service> loadImplementations(
        service: Class<out Service>,
        files: List<File>,
        classLoader: ClassLoader
    ): MutableList<Service> {
        val implementations = mutableListOf<Service>()

        for (className in findImplementations(service, files)) {
            try {
                val instance = classLoader.loadClass(className).getConstructor()
                    .newInstance()
                implementations.add(service.cast(instance)!!)
            } catch (e: ClassNotFoundException) {
                throw ClassNotFoundException("Unable to find class $className in $files")
            }
        }

        return implementations
    }

    inline fun <reified Service : Any> findImplementations(files: List<File>): Set<String> {
        return findImplementations(Service::class.java, files)
    }

    inline fun <reified Service : Any> loadImplementations(classLoader: URLClassLoader): List<Service> {
        return loadImplementations(Service::class.java, classLoader)
    }

    fun findImplementations(service: Class<*>, files: List<File>): Set<String> {
        return files.flatMapTo(linkedSetOf()) { findImplementations(service, it) }
    }

    private fun findImplementations(service: Class<*>, file: File): Set<String> {
        val classIdentifier = getClassIdentifier(service)

        return when {
            file.isDirectory -> findImplementationsInDirectory(classIdentifier, file)
            file.isFile && file.extension.lowercase(Locale.getDefault()) == "jar" -> findImplementationsInJar(
                classIdentifier,
                file
            )

            else -> emptySet()
        }
    }

    private fun findImplementationsInDirectory(classId: String, file: File): Set<String> {
        val serviceFile = File(file, SERVICE_DIRECTORY_LOCATION + classId).takeIf { it.isFile }
            ?: return emptySet()

        try {
            return serviceFile.useLines { parseLines(file, it) }
        } catch (e: IOError) {
            throw ServiceLoadingException(file, e)
        }
    }

    private fun findImplementationsInJar(classId: String, file: File): Set<String> {
        ZipFile(file).use { zipFile ->
            val entry = zipFile.getEntry(SERVICE_DIRECTORY_LOCATION + classId) ?: return emptySet()
            zipFile.getInputStream(entry).use { inputStream ->
                return inputStream.bufferedReader().useLines { parseLines(file, it) }
            }
        }
    }

    private fun parseLines(file: File, lines: Sequence<String>): Set<String> {
        return lines.mapNotNullTo(linkedSetOf()) { parseLine(file, it) }
    }

    private fun parseLine(file: File, line: String): String? {
        val actualLine = line.substringBefore('#').trim().takeIf { it.isNotEmpty() } ?: return null

        actualLine.forEachIndexed { index: Int, c: Char ->
            val isValid =
                if (index == 0) isJavaIdentifierStart(c) else isJavaIdentifierPart(c) || c == '.'
            if (!isValid) {
                val errorText = "Invalid Java identifier: $line"
                throw ServiceLoadingException(file, RuntimeException(errorText))
            }
        }

        return actualLine
    }

    private fun getClassIdentifier(service: Class<*>): String {
        return service.name
    }
}
