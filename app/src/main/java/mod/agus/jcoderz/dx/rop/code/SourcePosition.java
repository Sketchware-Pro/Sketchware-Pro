package mod.agus.jcoderz.dx.rop.code;

import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.util.Hex;

public final class SourcePosition {
    public static final SourcePosition NO_INFO = new SourcePosition(null, -1, -1);
    private final int address;
    private final int line;
    private final CstString sourceFile;

    public SourcePosition(CstString cstString, int i, int i2) {
        if (i < -1) {
            throw new IllegalArgumentException("address < -1");
        } else if (i2 < -1) {
            throw new IllegalArgumentException("line < -1");
        } else {
            this.sourceFile = cstString;
            this.address = i;
            this.line = i2;
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(50);
        if (this.sourceFile != null) {
            stringBuffer.append(this.sourceFile.toHuman());
            stringBuffer.append(":");
        }
        if (this.line >= 0) {
            stringBuffer.append(this.line);
        }
        stringBuffer.append(ExternalAnnotationProvider.NO_ANNOTATION);
        if (this.address < 0) {
            stringBuffer.append("????");
        } else {
            stringBuffer.append(Hex.u2(this.address));
        }
        return stringBuffer.toString();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SourcePosition)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        SourcePosition sourcePosition = (SourcePosition) obj;
        return this.address == sourcePosition.address && sameLineAndFile(sourcePosition);
    }

    public int hashCode() {
        return this.sourceFile.hashCode() + this.address + this.line;
    }

    public boolean sameLine(SourcePosition sourcePosition) {
        return this.line == sourcePosition.line;
    }

    public boolean sameLineAndFile(SourcePosition sourcePosition) {
        return this.line == sourcePosition.line && (this.sourceFile == sourcePosition.sourceFile || (this.sourceFile != null && this.sourceFile.equals(sourcePosition.sourceFile)));
    }

    public CstString getSourceFile() {
        return this.sourceFile;
    }

    public int getAddress() {
        return this.address;
    }

    public int getLine() {
        return this.line;
    }
}
