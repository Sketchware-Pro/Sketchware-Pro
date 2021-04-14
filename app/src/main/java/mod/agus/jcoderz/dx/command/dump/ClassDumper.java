package mod.agus.jcoderz.dx.command.dump;

import java.io.PrintStream;
import mod.agus.jcoderz.dx.cf.direct.DirectClassFile;
import mod.agus.jcoderz.dx.cf.direct.StdAttributeFactory;
import mod.agus.jcoderz.dx.util.ByteArray;

public final class ClassDumper extends BaseDumper {
    public static void dump(byte[] bArr, PrintStream printStream, String str, Args args) {
        new ClassDumper(bArr, printStream, str, args).dump();
    }

    private ClassDumper(byte[] bArr, PrintStream printStream, String str, Args args) {
        super(bArr, printStream, str, args);
    }

    public void dump() {
        byte[] bytes = getBytes();
        ByteArray byteArray = new ByteArray(bytes);
        DirectClassFile directClassFile = new DirectClassFile(byteArray, getFilePath(), getStrictParse());
        directClassFile.setAttributeFactory(StdAttributeFactory.THE_ONE);
        directClassFile.setObserver(this);
        directClassFile.getMagic();
        int at = getAt();
        if (at != bytes.length) {
            parsed(byteArray, at, bytes.length - at, "<extra data at end of file>");
        }
    }
}
