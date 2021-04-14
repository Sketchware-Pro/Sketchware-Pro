package mod.agus.jcoderz.dx.dex.file;

import java.io.PrintWriter;
import mod.agus.jcoderz.dex.Leb128;
import mod.agus.jcoderz.dx.dex.code.DalvCode;
import mod.agus.jcoderz.dx.rop.code.AccessFlags;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class EncodedMethod extends EncodedMember implements Comparable<EncodedMethod> {
    private final CodeItem code;
    private final CstMethodRef method;


    @Override // java.lang.Comparable
    public int compareTo(EncodedMethod encodedMethod) {
        return compareTo(encodedMethod);
    }

    public EncodedMethod(CstMethodRef cstMethodRef, int i, DalvCode dalvCode, TypeList typeList) {
        super(i);
        if (cstMethodRef == null) {
            throw new NullPointerException("method == null");
        }
        this.method = cstMethodRef;
        if (dalvCode == null) {
            this.code = null;
        } else {
            this.code = new CodeItem(cstMethodRef, dalvCode, (i & 8) != 0, typeList);
        }
    }

    public boolean equals(Object obj) {
        if ((obj instanceof EncodedMethod) && compareTo((EncodedMethod) obj) == 0) {
            return true;
        }
        return false;
    }

    public int compareToTwo(EncodedMethod encodedMethod) {
        return this.method.compareTo((Constant) encodedMethod.method);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(100);
        stringBuffer.append(getClass().getName());
        stringBuffer.append('{');
        stringBuffer.append(Hex.u2(getAccessFlags()));
        stringBuffer.append(' ');
        stringBuffer.append(this.method);
        if (this.code != null) {
            stringBuffer.append(' ');
            stringBuffer.append(this.code);
        }
        stringBuffer.append('}');
        return stringBuffer.toString();
    }

    @Override // mod.agus.jcoderz.dx.dex.file.EncodedMember
    public void addContents(DexFile dexFile) {
        MethodIdsSection methodIds = dexFile.getMethodIds();
        MixedItemSection wordData = dexFile.getWordData();
        methodIds.intern(this.method);
        if (this.code != null) {
            wordData.add(this.code);
        }
    }

    @Override // mod.agus.jcoderz.dx.util.ToHuman
    public final String toHuman() {
        return this.method.toHuman();
    }

    @Override // mod.agus.jcoderz.dx.dex.file.EncodedMember
    public final CstString getName() {
        return this.method.getNat().getName();
    }

    @Override // mod.agus.jcoderz.dx.dex.file.EncodedMember
    public void debugPrint(PrintWriter printWriter, boolean z) {
        if (this.code == null) {
            printWriter.println(String.valueOf(getRef().toHuman()) + ": abstract or native");
        } else {
            this.code.debugPrint(printWriter, "  ", z);
        }
    }

    public final CstMethodRef getRef() {
        return this.method;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.EncodedMember
    public int encode(DexFile dexFile, AnnotatedOutput annotatedOutput, int i, int i2) {
        boolean z;
        int indexOf = dexFile.getMethodIds().indexOf(this.method);
        int i3 = indexOf - i;
        int accessFlags = getAccessFlags();
        int absoluteOffsetOr0 = OffsettedItem.getAbsoluteOffsetOr0(this.code);
        boolean z2 = absoluteOffsetOr0 != 0;
        if ((accessFlags & 1280) == 0) {
            z = true;
        } else {
            z = false;
        }
        if (z2 != z) {
            throw new UnsupportedOperationException("code vs. access_flags mismatch");
        }
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(0, String.format("  [%x] %s", Integer.valueOf(i2), this.method.toHuman()));
            annotatedOutput.annotate(Leb128.unsignedLeb128Size(i3), "    method_idx:   " + Hex.u4(indexOf));
            annotatedOutput.annotate(Leb128.unsignedLeb128Size(accessFlags), "    access_flags: " + AccessFlags.methodString(accessFlags));
            annotatedOutput.annotate(Leb128.unsignedLeb128Size(absoluteOffsetOr0), "    code_off:     " + Hex.u4(absoluteOffsetOr0));
        }
        annotatedOutput.writeUleb128(i3);
        annotatedOutput.writeUleb128(accessFlags);
        annotatedOutput.writeUleb128(absoluteOffsetOr0);
        return indexOf;
    }
}
