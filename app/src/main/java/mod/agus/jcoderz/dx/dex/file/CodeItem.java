package mod.agus.jcoderz.dx.dex.file;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import mod.agus.jcoderz.dex.util.ExceptionWithContext;
import mod.agus.jcoderz.dx.dex.code.DalvCode;
import mod.agus.jcoderz.dx.dex.code.DalvInsnList;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class CodeItem extends OffsettedItem {
    private static final int ALIGNMENT = 4;
    private static final int HEADER_SIZE = 16;
    private CatchStructs catches;
    private final DalvCode code;
    private DebugInfoItem debugInfo;
    private final boolean isStatic;
    private final CstMethodRef ref;
    private final TypeList throwsList;

    public CodeItem(CstMethodRef cstMethodRef, DalvCode dalvCode, boolean z, TypeList typeList) {
        super(4, -1);
        if (cstMethodRef == null) {
            throw new NullPointerException("ref == null");
        } else if (dalvCode == null) {
            throw new NullPointerException("code == null");
        } else if (typeList == null) {
            throw new NullPointerException("throwsList == null");
        } else {
            this.ref = cstMethodRef;
            this.code = dalvCode;
            this.isStatic = z;
            this.throwsList = typeList;
            this.catches = null;
            this.debugInfo = null;
        }
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public ItemType itemType() {
        return ItemType.TYPE_CODE_ITEM;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public void addContents(DexFile dexFile) {
        MixedItemSection byteData = dexFile.getByteData();
        TypeIdsSection typeIds = dexFile.getTypeIds();
        if (this.code.hasPositions() || this.code.hasLocals()) {
            this.debugInfo = new DebugInfoItem(this.code, this.isStatic, this.ref);
            byteData.add(this.debugInfo);
        }
        if (this.code.hasAnyCatches()) {
            Iterator<Type> it = this.code.getCatchTypes().iterator();
            while (it.hasNext()) {
                typeIds.intern(it.next());
            }
            this.catches = new CatchStructs(this.code);
        }
        Iterator<Constant> it2 = this.code.getInsnConstants().iterator();
        while (it2.hasNext()) {
            dexFile.internIfAppropriate(it2.next());
        }
    }

    public String toString() {
        return "CodeItem{" + toHuman() + "}";
    }

    @Override // mod.agus.jcoderz.dx.dex.file.OffsettedItem
    public String toHuman() {
        return this.ref.toHuman();
    }

    public CstMethodRef getRef() {
        return this.ref;
    }

    public void debugPrint(PrintWriter printWriter, String str, boolean z) {
        printWriter.println(String.valueOf(this.ref.toHuman()) + ":");
        DalvInsnList insns = this.code.getInsns();
        printWriter.println("regs: " + Hex.u2(getRegistersSize()) + "; ins: " + Hex.u2(getInsSize()) + "; outs: " + Hex.u2(getOutsSize()));
        try {
            insns.debugPrint(printWriter, str, z);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str2 = String.valueOf(str) + "  ";
        if (this.catches != null) {
            printWriter.print(str);
            printWriter.println("catches");
            this.catches.debugPrint(printWriter, str2);
        }
        if (this.debugInfo != null) {
            printWriter.print(str);
            printWriter.println("debug info");
            this.debugInfo.debugPrint(printWriter, str2);
        }
    }

    @Override // mod.agus.jcoderz.dx.dex.file.OffsettedItem
    protected void place0(Section section, int i) {
        int i2;
        final DexFile file = section.getFile();
        this.code.assignIndices(new DalvCode.AssignIndicesCallback() {
            /* class mod.agus.jcoderz.dx.dex.file.CodeItem.1 */

            @Override // mod.agus.jcoderz.dx.dex.code.DalvCode.AssignIndicesCallback
            public int getIndex(Constant constant) {
                IndexedItem findItemOrNull = file.findItemOrNull(constant);
                if (findItemOrNull == null) {
                    return -1;
                }
                return findItemOrNull.getIndex();
            }
        });
        if (this.catches != null) {
            this.catches.encode(file);
            i2 = this.catches.writeSize();
        } else {
            i2 = 0;
        }
        int codeSize = this.code.getInsns().codeSize();
        if ((codeSize & 1) != 0) {
            codeSize++;
        }
        setWriteSize(i2 + (codeSize * 2) + 16);
    }

    @Override // mod.agus.jcoderz.dx.dex.file.OffsettedItem
    protected void writeTo0(DexFile dexFile, AnnotatedOutput annotatedOutput) {
        boolean annotates = annotatedOutput.annotates();
        int registersSize = getRegistersSize();
        int outsSize = getOutsSize();
        int insSize = getInsSize();
        int codeSize = this.code.getInsns().codeSize();
        boolean z = (codeSize & 1) != 0;
        int triesSize = this.catches == null ? 0 : this.catches.triesSize();
        int absoluteOffset = this.debugInfo == null ? 0 : this.debugInfo.getAbsoluteOffset();
        if (annotates) {
            annotatedOutput.annotate(0, String.valueOf(offsetString()) + ' ' + this.ref.toHuman());
            annotatedOutput.annotate(2, "  registers_size: " + Hex.u2(registersSize));
            annotatedOutput.annotate(2, "  ins_size:       " + Hex.u2(insSize));
            annotatedOutput.annotate(2, "  outs_size:      " + Hex.u2(outsSize));
            annotatedOutput.annotate(2, "  tries_size:     " + Hex.u2(triesSize));
            annotatedOutput.annotate(4, "  debug_off:      " + Hex.u4(absoluteOffset));
            annotatedOutput.annotate(4, "  insns_size:     " + Hex.u4(codeSize));
            if (this.throwsList.size() != 0) {
                annotatedOutput.annotate(0, "  throws " + StdTypeList.toHuman(this.throwsList));
            }
        }
        annotatedOutput.writeShort(registersSize);
        annotatedOutput.writeShort(insSize);
        annotatedOutput.writeShort(outsSize);
        annotatedOutput.writeShort(triesSize);
        annotatedOutput.writeInt(absoluteOffset);
        annotatedOutput.writeInt(codeSize);
        writeCodes(dexFile, annotatedOutput);
        if (this.catches != null) {
            if (z) {
                if (annotates) {
                    annotatedOutput.annotate(2, "  padding: 0");
                }
                annotatedOutput.writeShort(0);
            }
            this.catches.writeTo(dexFile, annotatedOutput);
        }
        if (annotates && this.debugInfo != null) {
            annotatedOutput.annotate(0, "  debug info");
            this.debugInfo.annotateTo(dexFile, annotatedOutput, "    ");
        }
    }

    private void writeCodes(DexFile dexFile, AnnotatedOutput annotatedOutput) {
        try {
            this.code.getInsns().writeTo(annotatedOutput);
        } catch (RuntimeException e) {
            throw ExceptionWithContext.withContext(e, "...while writing instructions for " + this.ref.toHuman());
        }
    }

    private int getInsSize() {
        return this.ref.getParameterWordCount(this.isStatic);
    }

    private int getOutsSize() {
        return this.code.getInsns().getOutsSize();
    }

    private int getRegistersSize() {
        return this.code.getInsns().getRegistersSize();
    }
}
