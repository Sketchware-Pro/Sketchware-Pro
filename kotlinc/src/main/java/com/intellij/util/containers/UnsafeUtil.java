/*
 * This file is part of Cosmic IDE.
 * Cosmic IDE is a free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * Cosmic IDE is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with Foobar. If not, see <https://www.gnu.org/licenses/>.
 */

package com.intellij.util.containers;

import android.annotation.SuppressLint;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class UnsafeUtil {

    public static Unsafe findUnsafe() {
        try {
            return Unsafe.getUnsafe();
        } catch (SecurityException se) {
            Class<Unsafe> type = Unsafe.class;
            try {
                @SuppressLint("DiscouragedPrivateApi") Field field = type.getDeclaredField("theUnsafe");
                field.setAccessible(true);
                return type.cast(field.get(type));
            } catch (Exception e) {
                for (Field field : type.getDeclaredFields()) {
                    if (type.isAssignableFrom(field.getType())) {
                        field.setAccessible(true);
                        try {
                            return type.cast(field.get(type));
                        } catch (IllegalAccessException iae) {
                            throw new RuntimeException(iae);
                        }
                    }
                }
            }
            throw new RuntimeException("Unsafe unavailable");
        }
    }
}
