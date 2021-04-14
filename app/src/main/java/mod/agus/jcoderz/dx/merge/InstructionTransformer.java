package mod.agus.jcoderz.dx.merge;

import mod.agus.jcoderz.dex.DexException;
import mod.agus.jcoderz.dex.DexIndexOverflowException;
import mod.agus.jcoderz.dx.io.CodeReader;
import mod.agus.jcoderz.dx.io.instructions.DecodedInstruction;
import mod.agus.jcoderz.dx.io.instructions.ShortArrayCodeOutput;

final class InstructionTransformer {
    private IndexMap indexMap;
    private int mappedAt;
    private DecodedInstruction[] mappedInstructions;
    private final CodeReader reader = new CodeReader();

    public InstructionTransformer() {
        this.reader.setAllVisitors(new GenericVisitor(this, null));
        this.reader.setStringVisitor(new StringVisitor(this, null));
        this.reader.setTypeVisitor(new TypeVisitor(this, null));
        this.reader.setFieldVisitor(new FieldVisitor(this, null));
        this.reader.setMethodVisitor(new MethodVisitor(this, null));
    }

    public short[] transform(IndexMap indexMap2, short[] sArr) throws DexException {
        DecodedInstruction[] decodeAll = DecodedInstruction.decodeAll(sArr);
        int length = decodeAll.length;
        this.indexMap = indexMap2;
        this.mappedInstructions = new DecodedInstruction[length];
        this.mappedAt = 0;
        this.reader.visitAll(decodeAll);
        ShortArrayCodeOutput shortArrayCodeOutput = new ShortArrayCodeOutput(length);
        DecodedInstruction[] decodedInstructionArr = this.mappedInstructions;
        for (DecodedInstruction decodedInstruction : decodedInstructionArr) {
            if (decodedInstruction != null) {
                decodedInstruction.encode(shortArrayCodeOutput);
            }
        }
        this.indexMap = null;
        return shortArrayCodeOutput.getArray();
    }

    private class GenericVisitor implements CodeReader.Visitor {
        private GenericVisitor() {
        }

        GenericVisitor(InstructionTransformer instructionTransformer, GenericVisitor genericVisitor) {
            this();
        }

        @Override // mod.agus.jcoderz.dx.io.CodeReader.Visitor
        public void visit(DecodedInstruction[] decodedInstructionArr, DecodedInstruction decodedInstruction) {
            DecodedInstruction[] decodedInstructionArr2 = InstructionTransformer.this.mappedInstructions;
            InstructionTransformer instructionTransformer = InstructionTransformer.this;
            int i = instructionTransformer.mappedAt;
            instructionTransformer.mappedAt = i + 1;
            decodedInstructionArr2[i] = decodedInstruction;
        }
    }

    private class StringVisitor implements CodeReader.Visitor {
        private StringVisitor() {
        }

        StringVisitor(InstructionTransformer instructionTransformer, StringVisitor stringVisitor) {
            this();
        }

        @Override // mod.agus.jcoderz.dx.io.CodeReader.Visitor
        public void visit(DecodedInstruction[] decodedInstructionArr, DecodedInstruction decodedInstruction) {
            int adjustString = InstructionTransformer.this.indexMap.adjustString(decodedInstruction.getIndex());
            InstructionTransformer.jumboCheck(decodedInstruction.getOpcode() == 27, adjustString);
            DecodedInstruction[] decodedInstructionArr2 = InstructionTransformer.this.mappedInstructions;
            InstructionTransformer instructionTransformer = InstructionTransformer.this;
            int i = instructionTransformer.mappedAt;
            instructionTransformer.mappedAt = i + 1;
            decodedInstructionArr2[i] = decodedInstruction.withIndex(adjustString);
        }
    }

    private class FieldVisitor implements CodeReader.Visitor {
        private FieldVisitor() {
        }

        FieldVisitor(InstructionTransformer instructionTransformer, FieldVisitor fieldVisitor) {
            this();
        }

        @Override // mod.agus.jcoderz.dx.io.CodeReader.Visitor
        public void visit(DecodedInstruction[] decodedInstructionArr, DecodedInstruction decodedInstruction) {
            int adjustField = InstructionTransformer.this.indexMap.adjustField(decodedInstruction.getIndex());
            InstructionTransformer.jumboCheck(decodedInstruction.getOpcode() == 27, adjustField);
            DecodedInstruction[] decodedInstructionArr2 = InstructionTransformer.this.mappedInstructions;
            InstructionTransformer instructionTransformer = InstructionTransformer.this;
            int i = instructionTransformer.mappedAt;
            instructionTransformer.mappedAt = i + 1;
            decodedInstructionArr2[i] = decodedInstruction.withIndex(adjustField);
        }
    }

    private class TypeVisitor implements CodeReader.Visitor {
        private TypeVisitor() {
        }

        TypeVisitor(InstructionTransformer instructionTransformer, TypeVisitor typeVisitor) {
            this();
        }

        @Override // mod.agus.jcoderz.dx.io.CodeReader.Visitor
        public void visit(DecodedInstruction[] decodedInstructionArr, DecodedInstruction decodedInstruction) {
            int adjustType = InstructionTransformer.this.indexMap.adjustType(decodedInstruction.getIndex());
            InstructionTransformer.jumboCheck(decodedInstruction.getOpcode() == 27, adjustType);
            DecodedInstruction[] decodedInstructionArr2 = InstructionTransformer.this.mappedInstructions;
            InstructionTransformer instructionTransformer = InstructionTransformer.this;
            int i = instructionTransformer.mappedAt;
            instructionTransformer.mappedAt = i + 1;
            decodedInstructionArr2[i] = decodedInstruction.withIndex(adjustType);
        }
    }

    private class MethodVisitor implements CodeReader.Visitor {
        private MethodVisitor() {
        }

        MethodVisitor(InstructionTransformer instructionTransformer, MethodVisitor methodVisitor) {
            this();
        }

        @Override // mod.agus.jcoderz.dx.io.CodeReader.Visitor
        public void visit(DecodedInstruction[] decodedInstructionArr, DecodedInstruction decodedInstruction) {
            int adjustMethod = InstructionTransformer.this.indexMap.adjustMethod(decodedInstruction.getIndex());
            InstructionTransformer.jumboCheck(decodedInstruction.getOpcode() == 27, adjustMethod);
            DecodedInstruction[] decodedInstructionArr2 = InstructionTransformer.this.mappedInstructions;
            InstructionTransformer instructionTransformer = InstructionTransformer.this;
            int i = instructionTransformer.mappedAt;
            instructionTransformer.mappedAt = i + 1;
            decodedInstructionArr2[i] = decodedInstruction.withIndex(adjustMethod);
        }
    }

    public static void jumboCheck(boolean z, int i) {
        if (!z && i > 65535) {
            throw new DexIndexOverflowException("Cannot merge new index " + i + " into a non-jumbo instruction!");
        }
    }
}
