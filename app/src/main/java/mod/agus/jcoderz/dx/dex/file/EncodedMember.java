package mod.agus.jcoderz.dx.dex.file;

import java.io.PrintWriter;

import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.ToHuman;

public abstract class EncodedMember implements ToHuman {
    private final int accessFlags;

    public EncodedMember(int i) {
        this.accessFlags = i;
    }

    public abstract void addContents(DexFile dexFile);

    public abstract void debugPrint(PrintWriter printWriter, boolean z);

    public abstract int encode(DexFile dexFile, AnnotatedOutput annotatedOutput, int i, int i2);

    public abstract CstString getName();

    public final int getAccessFlags() {
        return this.accessFlags;
    }
}
