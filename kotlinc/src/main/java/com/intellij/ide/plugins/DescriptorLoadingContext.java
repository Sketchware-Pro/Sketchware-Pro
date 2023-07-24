/*
 * This file is part of Cosmic IDE.
 * Cosmic IDE is a free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * Cosmic IDE is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with Foobar. If not, see <https://www.gnu.org/licenses/>.
 */
package com.intellij.ide.plugins;

import com.itsaky.androidide.zipfs2.ZipFileSystemProvider;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;

import gnu.trove.THashMap;

/**
 * I have modified this class to use {@link com.itsaky.androidide.zipfs.ZipFileSystem} because
 * android doesn't provide a ZipFileSystem
 */

// parentContext is null only for CoreApplicationEnvironment - it is not valid otherwise because in
// this case XML is not interned.
final class DescriptorLoadingContext implements AutoCloseable {
    final DescriptorListLoadingContext parentContext;
    final boolean isBundled;
    final boolean isEssential;
    final PathBasedJdomXIncluder.PathResolver<?> pathResolver;
    private final Map<Path, FileSystem> openedFiles = new THashMap<>();

    DescriptorLoadingContext(
            DescriptorListLoadingContext parentContext,
            boolean isBundled,
            boolean isEssential,
            PathBasedJdomXIncluder.PathResolver<?> pathResolver) {
        this.parentContext = parentContext;
        this.isBundled = isBundled;
        this.isEssential = isEssential;
        this.pathResolver = pathResolver;
    }

    FileSystem open(Path file) throws IOException {
        FileSystem result = openedFiles.get(file);
        if (result == null) {
            result = new ZipFileSystemProvider().newFileSystem(file, Collections.emptyMap());
            openedFiles.put(file, result);
        }
        return result;
    }

    @Override
    public void close() {
        for (FileSystem file : openedFiles.values()) {
            try {
                file.close();
            } catch (IOException ignore) {
                //noinspection UnusedCatchParameter
            }
        }
    }

    public DescriptorLoadingContext copy(boolean isEssential) {
        return new DescriptorLoadingContext(parentContext, isBundled, isEssential, pathResolver);
    }
  }
