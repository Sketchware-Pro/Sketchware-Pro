package mod.agus.jcoderz.multidex;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import mod.agus.jcoderz.dx.cf.direct.DirectClassFile;
import mod.agus.jcoderz.dx.cf.direct.StdAttributeFactory;

public class Path {
    static final /* synthetic */ boolean $assertionsDisabled = (!Path.class.desiredAssertionStatus());
    private final ByteArrayOutputStream baos = new ByteArrayOutputStream(40960);
    private final String definition;
    private final byte[] readBuffer = new byte[20480];
    List<ClassPathElement> elements = new ArrayList();

    Path(String str) throws IOException {
        this.definition = str;
        for (String str2 : str.split(Pattern.quote(File.pathSeparator))) {
            try {
                addElement(getClassPathElement(new File(str2)));
            } catch (IOException e) {
                throw new IOException("Wrong classpath: " + e.getMessage(), e);
            }
        }
    }

    static ClassPathElement getClassPathElement(File file) throws IOException {
        if (file.isDirectory()) {
            return new FolderPathElement(file);
        }
        if (file.isFile()) {
            return new ArchivePathElement(new ZipFile(file));
        }
        if (file.exists()) {
            throw new IOException("\"" + file.getPath() + "\" is not a directory neither a zip file");
        }
        throw new FileNotFoundException("File \"" + file.getPath() + "\" not found");
    }

    private static byte[] readStream(InputStream inputStream, ByteArrayOutputStream byteArrayOutputStream, byte[] bArr) throws IOException {
        while (true) {
            try {
                int read = inputStream.read(bArr);
                if (read < 0) {
                    inputStream.close();
                    return byteArrayOutputStream.toByteArray();
                }
                byteArrayOutputStream.write(bArr, 0, read);
            } catch (Throwable th) {
                inputStream.close();
                throw th;
            }
        }
    }

    public String toString() {
        return this.definition;
    }

    public Iterable<ClassPathElement> getElements() {
        return this.elements;
    }

    private void addElement(ClassPathElement classPathElement) {
        if ($assertionsDisabled || classPathElement != null) {
            this.elements.add(classPathElement);
            return;
        }
        throw new AssertionError();
    }

    public synchronized DirectClassFile getClass(String str) throws FileNotFoundException {
        DirectClassFile directClassFile;
        DirectClassFile directClassFile2 = null;
        Iterator<ClassPathElement> it = this.elements.iterator();
        while (true) {
            if (!it.hasNext()) {
                directClassFile = directClassFile2;
                break;
            }
            try {
                InputStream open = it.next().open(str);
                try {
                    byte[] readStream = readStream(open, this.baos, this.readBuffer);
                    this.baos.reset();
                    directClassFile = new DirectClassFile(readStream, str, false);
                    try {
                        directClassFile.setAttributeFactory(StdAttributeFactory.THE_ONE);
                        try {
                            open.close();
                            break;
                        } catch (IOException e) {
                        }
                    } catch (Throwable th) {
                        th = th;
                        open.close();
                        throw th;
                    }
                } catch (Throwable th2) {
                    Throwable th3 = th2;
                    open.close();
                    throw th3;
                }
            } catch (Throwable e2) {
                directClassFile = directClassFile2;
            }
            directClassFile2 = directClassFile;
        }
        if (directClassFile == null) {
            throw new FileNotFoundException("File \"" + str + "\" not found");
        }
        return directClassFile;
    }
}
