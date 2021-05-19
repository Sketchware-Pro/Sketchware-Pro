package mod.agus.jcoderz.dx.dex.file;

import java.io.PrintWriter;

import mod.agus.jcoderz.dex.Leb128;
import mod.agus.jcoderz.dx.rop.code.AccessFlags;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstFieldRef;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class EncodedField extends EncodedMember implements Comparable<EncodedField> {
    private final CstFieldRef field;


    public EncodedField(CstFieldRef cstFieldRef, int i) {
        super(i);
        if (cstFieldRef == null) {
            throw new NullPointerException("field == null");
        }
        this.field = cstFieldRef;
    }

    @Override // java.lang.Comparable
    public int compareTo(EncodedField encodedField) {
        return compareTo(encodedField);
    }

    public int hashCode() {
        return this.field.hashCode();
    }

    public boolean equals(Object obj) {
        return (obj instanceof EncodedField) && compareTo((EncodedField) obj) == 0;
    }

    public int compareToTwo(EncodedField encodedField) {
        return this.field.compareTo((Constant) encodedField.field);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(100);
        stringBuffer.append(getClass().getName());
        stringBuffer.append('{');
        stringBuffer.append(Hex.u2(getAccessFlags()));
        stringBuffer.append(' ');
        stringBuffer.append(this.field);
        stringBuffer.append('}');
        return stringBuffer.toString();
    }

    @Override // mod.agus.jcoderz.dx.dex.file.EncodedMember
    public void addContents(DexFile dexFile) {
        dexFile.getFieldIds().intern(this.field);
    }

    @Override // mod.agus.jcoderz.dx.dex.file.EncodedMember
    public CstString getName() {
        return this.field.getNat().getName();
    }

    @Override // mod.agus.jcoderz.dx.util.ToHuman
    public String toHuman() {
        return this.field.toHuman();
    }

    @Override // mod.agus.jcoderz.dx.dex.file.EncodedMember
    public void debugPrint(PrintWriter printWriter, boolean z) {
        printWriter.println(toString());
    }

    public CstFieldRef getRef() {
        return this.field;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.EncodedMember
    public int encode(DexFile dexFile, AnnotatedOutput annotatedOutput, int i, int i2) {
        int indexOf = dexFile.getFieldIds().indexOf(this.field);
        int i3 = indexOf - i;
        int accessFlags = getAccessFlags();
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(0, String.format("  [%x] %s", Integer.valueOf(i2), this.field.toHuman()));
            annotatedOutput.annotate(Leb128.unsignedLeb128Size(i3), "    field_idx:    " + Hex.u4(indexOf));
            annotatedOutput.annotate(Leb128.unsignedLeb128Size(accessFlags), "    access_flags: " + AccessFlags.fieldString(accessFlags));
        }
        annotatedOutput.writeUleb128(i3);
        annotatedOutput.writeUleb128(accessFlags);
        return indexOf;
    }
}
