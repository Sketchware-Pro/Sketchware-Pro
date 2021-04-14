package mod.agus.jcoderz.dx.io;

import mod.agus.jcoderz.dex.DexException;
import mod.agus.jcoderz.dx.io.instructions.DecodedInstruction;

public final class CodeReader {
    private static int[] $SWITCH_TABLE$mod$agus$jcoderz$dx$io$IndexType;
    private Visitor fallbackVisitor = null;
    private Visitor fieldVisitor = null;
    private Visitor methodVisitor = null;
    private Visitor stringVisitor = null;
    private Visitor typeVisitor = null;

    public interface Visitor {
        void visit(DecodedInstruction[] decodedInstructionArr, DecodedInstruction decodedInstruction);
    }

    static int[] $SWITCH_TABLE$mod$agus$jcoderz$dx$io$IndexType() {
        int[] iArr = $SWITCH_TABLE$mod$agus$jcoderz$dx$io$IndexType;
        if (iArr == null) {
            iArr = new int[IndexType.values().length];
            try {
                iArr[IndexType.FIELD_OFFSET.ordinal()] = 10;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[IndexType.FIELD_REF.ordinal()] = 7;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[IndexType.INLINE_METHOD.ordinal()] = 8;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[IndexType.METHOD_REF.ordinal()] = 6;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[IndexType.NONE.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[IndexType.STRING_REF.ordinal()] = 5;
            } catch (NoSuchFieldError e6) {
            }
            try {
                iArr[IndexType.TYPE_REF.ordinal()] = 4;
            } catch (NoSuchFieldError e7) {
            }
            try {
                iArr[IndexType.UNKNOWN.ordinal()] = 1;
            } catch (NoSuchFieldError e8) {
            }
            try {
                iArr[IndexType.VARIES.ordinal()] = 3;
            } catch (NoSuchFieldError e9) {
            }
            try {
                iArr[IndexType.VTABLE_OFFSET.ordinal()] = 9;
            } catch (NoSuchFieldError e10) {
            }
            $SWITCH_TABLE$mod$agus$jcoderz$dx$io$IndexType = iArr;
        }
        return iArr;
    }

    public void setAllVisitors(Visitor visitor) {
        this.fallbackVisitor = visitor;
        this.stringVisitor = visitor;
        this.typeVisitor = visitor;
        this.fieldVisitor = visitor;
        this.methodVisitor = visitor;
    }

    public void setFallbackVisitor(Visitor visitor) {
        this.fallbackVisitor = visitor;
    }

    public void setStringVisitor(Visitor visitor) {
        this.stringVisitor = visitor;
    }

    public void setTypeVisitor(Visitor visitor) {
        this.typeVisitor = visitor;
    }

    public void setFieldVisitor(Visitor visitor) {
        this.fieldVisitor = visitor;
    }

    public void setMethodVisitor(Visitor visitor) {
        this.methodVisitor = visitor;
    }

    public void visitAll(DecodedInstruction[] decodedInstructionArr) throws DexException {
        for (DecodedInstruction decodedInstruction : decodedInstructionArr) {
            if (decodedInstruction != null) {
                callVisit(decodedInstructionArr, decodedInstruction);
            }
        }
    }

    public void visitAll(short[] sArr) throws DexException {
        visitAll(DecodedInstruction.decodeAll(sArr));
    }

    private void callVisit(DecodedInstruction[] decodedInstructionArr, DecodedInstruction decodedInstruction) {
        Visitor visitor = null;
        switch ($SWITCH_TABLE$mod$agus$jcoderz$dx$io$IndexType()[OpcodeInfo.getIndexType(decodedInstruction.getOpcode()).ordinal()]) {
            case 4:
                visitor = this.typeVisitor;
                break;
            case 5:
                visitor = this.stringVisitor;
                break;
            case 6:
                visitor = this.methodVisitor;
                break;
            case 7:
                visitor = this.fieldVisitor;
                break;
        }
        if (visitor == null) {
            visitor = this.fallbackVisitor;
        }
        if (visitor != null) {
            visitor.visit(decodedInstructionArr, decodedInstruction);
        }
    }
}
