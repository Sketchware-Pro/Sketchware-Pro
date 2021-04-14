package mod.agus.jcoderz.multidex;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ArchivePathElement implements ClassPathElement {
    private final ZipFile archive;

    static class DirectoryEntryException extends IOException {
        DirectoryEntryException() {
        }
    }

    public ArchivePathElement(ZipFile zipFile) {
        this.archive = zipFile;
    }

    @Override // mod.agus.jcoderz.multidex.ClassPathElement
    public InputStream open(String str) throws IOException {
        ZipEntry entry = this.archive.getEntry(str);
        if (entry == null) {
            throw new FileNotFoundException("File \"" + str + "\" not found");
        } else if (!entry.isDirectory()) {
            return this.archive.getInputStream(entry);
        } else {
            throw new DirectoryEntryException();
        }
    }

    @Override // mod.agus.jcoderz.multidex.ClassPathElement
    public void close() throws IOException {
        this.archive.close();
    }

    @Override // mod.agus.jcoderz.multidex.ClassPathElement
    public Iterable<String> list() {
        return new Iterable<String>() {

            @Override // java.lang.Iterable
            public Spliterator<String> spliterator() {
                return null;
            }

            @Override // java.lang.Iterable
            public Iterator<String> iterator() {
                return new Iterator<String>() {
                    Enumeration<? extends ZipEntry> delegate;
                    ZipEntry next = null;

                    {
                        this.delegate = ArchivePathElement.this.archive.entries();
                    }

                    public boolean hasNext() {
                        while (this.next == null && this.delegate.hasMoreElements()) {
                            this.next = (ZipEntry) this.delegate.nextElement();
                            if (this.next.isDirectory()) {
                                this.next = null;
                            }
                        }
                        if (this.next != null) {
                            return true;
                        }
                        return false;
                    }

                    @Override // java.util.Iterator
                    public String next() {
                        if (hasNext()) {
                            String name = this.next.getName();
                            this.next = null;
                            return name;
                        }
                        throw new NoSuchElementException();
                    }

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }
}
