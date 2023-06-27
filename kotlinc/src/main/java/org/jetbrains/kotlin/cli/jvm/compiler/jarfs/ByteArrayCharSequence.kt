/*
 * This file is part of Cosmic IDE.
 * Cosmic IDE is a free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * Cosmic IDE is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with Foobar. If not, see <https://www.gnu.org/licenses/>.
 */
package org.jetbrains.kotlin.cli.jvm.compiler.jarfs

class ByteArrayCharSequence(
    private val bytes: ByteArray,
    private val start: Int = 0,
    private val end: Int = bytes.size
) : CharSequence {

    override fun hashCode(): Int {
        error("Do not try computing hashCode ByteArrayCharSequence")
    }

    override fun equals(other: Any?): Boolean {
        error("Do not try comparing ByteArrayCharSequence")
    }

    override val length get() = end - start

    override fun get(index: Int): Char = bytes[index + start].toInt().toChar()

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
        if (startIndex == 0 && endIndex == length) return this
        return ByteArrayCharSequence(bytes, start + startIndex, start + endIndex)
    }

    override fun toString(): String {
        val chars = CharArray(length)

        for (i in 0 until length) {
            chars[i] = bytes[i + start].toInt().toChar()
        }

        return String(chars)
    }
}
