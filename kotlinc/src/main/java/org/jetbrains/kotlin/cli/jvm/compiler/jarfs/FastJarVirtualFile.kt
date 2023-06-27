/*
 * This file is part of Cosmic IDE.
 * Cosmic IDE is a free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * Cosmic IDE is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with Foobar. If not, see <https://www.gnu.org/licenses/>.
 */
package org.jetbrains.kotlin.cli.jvm.compiler.jarfs

import com.intellij.openapi.util.io.BufferExposingByteArrayInputStream
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileSystem
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

internal class FastJarVirtualFile(
    private val handler: FastJarHandler,
    private val name: CharSequence,
    private val length: Int,
    private val parent: FastJarVirtualFile?,
    private val entryDescription: ZipEntryDescription?,
) : VirtualFile() {

    private var myChildrenArray = EMPTY_ARRAY
    private val myChildrenList: MutableList<VirtualFile> = mutableListOf()

    init {
        parent?.myChildrenList?.add(this)
    }

    fun initChildrenArrayFromList() {
        myChildrenArray = myChildrenList.toTypedArray()
        myChildrenList.clear()
    }

    override fun getName(): String {
        return name.toString()
    }

    override fun getNameSequence(): CharSequence {
        return name
    }

    override fun getFileSystem(): VirtualFileSystem {
        return handler.fileSystem
    }

    override fun getPath(): String {
        if (parent == null) {
            return FileUtil.toSystemIndependentName(handler.file.path) + "!/"
        }
        val parentPath = parent.path
        val answer = StringBuilder(parentPath.length + 1 + name.length)
        answer.append(parentPath)
        if (answer[answer.length - 1] != '/') {
            answer.append('/')
        }
        answer.append(name)
        return answer.toString()
    }

    override fun isWritable(): Boolean {
        return false
    }

    override fun isDirectory(): Boolean {
        return length < 0
    }

    override fun isValid(): Boolean {
        return true
    }

    override fun getParent(): VirtualFile? {
        return parent
    }

    override fun getChildren(): Array<VirtualFile> {
        return myChildrenArray
    }

    @Throws(IOException::class)
    override fun getOutputStream(
        requestor: Any,
        newModificationStamp: Long,
        newTimeStamp: Long
    ): OutputStream {
        throw UnsupportedOperationException("JarFileSystem is read-only")
    }

    @Throws(IOException::class)
    override fun contentsToByteArray(): ByteArray {
        if (entryDescription == null) return EMPTY_BYTE_ARRAY
        return handler.contentsToByteArray(entryDescription)
    }

    override fun getTimeStamp(): Long = 0

    override fun getLength(): Long = length.toLong()

    override fun refresh(asynchronous: Boolean, recursive: Boolean, postRunnable: Runnable?) {}

    @Throws(IOException::class)
    override fun getInputStream(): InputStream {
        return BufferExposingByteArrayInputStream(contentsToByteArray())
    }

    override fun getModificationStamp(): Long {
        return 0
    }
}

private val EMPTY_BYTE_ARRAY = ByteArray(0)