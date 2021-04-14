package mod.agus.jcoderz.dx.io.instructions;

import java.io.EOFException;
import mod.agus.jcoderz.dex.DexException;
import mod.agus.jcoderz.dx.io.IndexType;
import mod.agus.jcoderz.dx.io.OpcodeInfo;
import mod.agus.jcoderz.dx.util.Hex;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

public enum InstructionCodec {
    FORMAT_00X {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            return new ZeroRegisterDecodedInstruction(this, i, 0, null, 0, 0);
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(decodedInstruction.getOpcodeUnit());
        }
    },
    FORMAT_10X {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            return new ZeroRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, (long) InstructionCodec.byte1(i));
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(decodedInstruction.getOpcodeUnit());
        }
    },
    FORMAT_12X {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            return new TwoRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, 0, InstructionCodec.nibble2(i), InstructionCodec.nibble3(i));
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcodeUnit(), InstructionCodec.makeByte(decodedInstruction.getA(), decodedInstruction.getB())));
        }
    },
    FORMAT_11N {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            return new OneRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, (long) ((InstructionCodec.nibble3(i) << 28) >> 28), InstructionCodec.nibble2(i));
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcodeUnit(), InstructionCodec.makeByte(decodedInstruction.getA(), decodedInstruction.getLiteralNibble())));
        }
    },
    FORMAT_11X {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            return new OneRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, 0, InstructionCodec.byte1(i));
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()));
        }
    },
    FORMAT_10T {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            return new ZeroRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, ((byte) InstructionCodec.byte1(i)) + (codeInput.cursor() - 1), 0);
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getTargetByte(codeOutput.cursor())));
        }
    },
    FORMAT_20T {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            return new ZeroRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, ((short) codeInput.read()) + (codeInput.cursor() - 1), (long) InstructionCodec.byte1(i));
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(decodedInstruction.getOpcodeUnit(), decodedInstruction.getTargetUnit(codeOutput.cursor()));
        }
    },
    FORMAT_20BC {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            return new ZeroRegisterDecodedInstruction(this, InstructionCodec.byte0(i), codeInput.read(), IndexType.VARIES, 0, (long) InstructionCodec.byte1(i));
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getLiteralByte()), decodedInstruction.getIndexUnit());
        }
    },
    FORMAT_22X {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            return new TwoRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, 0, InstructionCodec.byte1(i), codeInput.read());
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), decodedInstruction.getBUnit());
        }
    },
    FORMAT_21T {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            return new OneRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, ((short) codeInput.read()) + (codeInput.cursor() - 1), 0, InstructionCodec.byte1(i));
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), decodedInstruction.getTargetUnit(codeOutput.cursor()));
        }
    },
    FORMAT_21S {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            return new OneRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, (long) ((short) codeInput.read()), InstructionCodec.byte1(i));
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), decodedInstruction.getLiteralUnit());
        }
    },
    FORMAT_21H {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            int byte0 = InstructionCodec.byte0(i);
            return new OneRegisterDecodedInstruction(this, byte0, 0, null, 0, ((long) ((short) codeInput.read())) << (byte0 == 21 ? 16 : ExternalAnnotationProvider.NULLABLE), InstructionCodec.byte1(i));
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            int opcode = decodedInstruction.getOpcode();
            codeOutput.write(InstructionCodec.codeUnit(opcode, decodedInstruction.getA()), (short) ((int) (decodedInstruction.getLiteral() >> (opcode == 21 ? 16 : ExternalAnnotationProvider.NULLABLE))));
        }
    },
    FORMAT_21C {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            int byte0 = InstructionCodec.byte0(i);
            return new OneRegisterDecodedInstruction(this, byte0, codeInput.read(), OpcodeInfo.getIndexType(byte0), 0, 0, InstructionCodec.byte1(i));
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), decodedInstruction.getIndexUnit());
        }
    },
    FORMAT_23X {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            int byte0 = InstructionCodec.byte0(i);
            int byte1 = InstructionCodec.byte1(i);
            int read = codeInput.read();
            return new ThreeRegisterDecodedInstruction(this, byte0, 0, null, 0, 0, byte1, InstructionCodec.byte0(read), InstructionCodec.byte1(read));
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), InstructionCodec.codeUnit(decodedInstruction.getB(), decodedInstruction.getC()));
        }
    },
    FORMAT_22B {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            int byte0 = InstructionCodec.byte0(i);
            int byte1 = InstructionCodec.byte1(i);
            int read = codeInput.read();
            return new TwoRegisterDecodedInstruction(this, byte0, 0, null, 0, (long) ((byte) InstructionCodec.byte1(read)), byte1, InstructionCodec.byte0(read));
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), InstructionCodec.codeUnit(decodedInstruction.getB(), decodedInstruction.getLiteralByte()));
        }
    },
    FORMAT_22T {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            return new TwoRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, ((short) codeInput.read()) + (codeInput.cursor() - 1), 0, InstructionCodec.nibble2(i), InstructionCodec.nibble3(i));
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), InstructionCodec.makeByte(decodedInstruction.getA(), decodedInstruction.getB())), decodedInstruction.getTargetUnit(codeOutput.cursor()));
        }
    },
    FORMAT_22S {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            return new TwoRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, (long) ((short) codeInput.read()), InstructionCodec.nibble2(i), InstructionCodec.nibble3(i));
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), InstructionCodec.makeByte(decodedInstruction.getA(), decodedInstruction.getB())), decodedInstruction.getLiteralUnit());
        }
    },
    FORMAT_22C {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            int byte0 = InstructionCodec.byte0(i);
            return new TwoRegisterDecodedInstruction(this, byte0, codeInput.read(), OpcodeInfo.getIndexType(byte0), 0, 0, InstructionCodec.nibble2(i), InstructionCodec.nibble3(i));
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), InstructionCodec.makeByte(decodedInstruction.getA(), decodedInstruction.getB())), decodedInstruction.getIndexUnit());
        }
    },
    FORMAT_22CS {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            return new TwoRegisterDecodedInstruction(this, InstructionCodec.byte0(i), codeInput.read(), IndexType.FIELD_OFFSET, 0, 0, InstructionCodec.nibble2(i), InstructionCodec.nibble3(i));
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), InstructionCodec.makeByte(decodedInstruction.getA(), decodedInstruction.getB())), decodedInstruction.getIndexUnit());
        }
    },
    FORMAT_30T {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            int byte0 = InstructionCodec.byte0(i);
            int byte1 = InstructionCodec.byte1(i);
            return new ZeroRegisterDecodedInstruction(this, byte0, 0, null, codeInput.readInt() + (codeInput.cursor() - 1), (long) byte1);
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            int target = decodedInstruction.getTarget(codeOutput.cursor());
            codeOutput.write(decodedInstruction.getOpcodeUnit(), InstructionCodec.unit0(target), InstructionCodec.unit1(target));
        }
    },
    FORMAT_32X {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            return new TwoRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, (long) InstructionCodec.byte1(i), codeInput.read(), codeInput.read());
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(decodedInstruction.getOpcodeUnit(), decodedInstruction.getAUnit(), decodedInstruction.getBUnit());
        }
    },
    FORMAT_31I {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            return new OneRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, (long) codeInput.readInt(), InstructionCodec.byte1(i));
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            int literalInt = decodedInstruction.getLiteralInt();
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), InstructionCodec.unit0(literalInt), InstructionCodec.unit1(literalInt));
        }
    },
    FORMAT_31T {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            int cursor = codeInput.cursor() - 1;
            int byte0 = InstructionCodec.byte0(i);
            int byte1 = InstructionCodec.byte1(i);
            int readInt = cursor + codeInput.readInt();
            switch (byte0) {
                case 43:
                case 44:
                    codeInput.setBaseAddress(readInt, cursor);
                    break;
            }
            return new OneRegisterDecodedInstruction(this, byte0, 0, null, readInt, 0, byte1);
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            int target = decodedInstruction.getTarget(codeOutput.cursor());
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), InstructionCodec.unit0(target), InstructionCodec.unit1(target));
        }
    },
    FORMAT_31C {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            int byte0 = InstructionCodec.byte0(i);
            return new OneRegisterDecodedInstruction(this, byte0, codeInput.readInt(), OpcodeInfo.getIndexType(byte0), 0, 0, InstructionCodec.byte1(i));
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            int index = decodedInstruction.getIndex();
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), InstructionCodec.unit0(index), InstructionCodec.unit1(index));
        }
    },
    FORMAT_35C {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            return InstructionCodec.decodeRegisterList(this, i, codeInput);
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            InstructionCodec.encodeRegisterList(decodedInstruction, codeOutput);
        }
    },
    FORMAT_35MS {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            return InstructionCodec.decodeRegisterList(this, i, codeInput);
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            InstructionCodec.encodeRegisterList(decodedInstruction, codeOutput);
        }
    },
    FORMAT_35MI {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            return InstructionCodec.decodeRegisterList(this, i, codeInput);
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            InstructionCodec.encodeRegisterList(decodedInstruction, codeOutput);
        }
    },
    FORMAT_3RC {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            return InstructionCodec.decodeRegisterRange(this, i, codeInput);
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            InstructionCodec.encodeRegisterRange(decodedInstruction, codeOutput);
        }
    },
    FORMAT_3RMS {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            return InstructionCodec.decodeRegisterRange(this, i, codeInput);
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            InstructionCodec.encodeRegisterRange(decodedInstruction, codeOutput);
        }
    },
    FORMAT_3RMI {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            return InstructionCodec.decodeRegisterRange(this, i, codeInput);
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            InstructionCodec.encodeRegisterRange(decodedInstruction, codeOutput);
        }
    },
    FORMAT_51L {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            return new OneRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, codeInput.readLong(), InstructionCodec.byte1(i));
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            long literal = decodedInstruction.getLiteral();
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), InstructionCodec.unit0(literal), InstructionCodec.unit1(literal), InstructionCodec.unit2(literal), InstructionCodec.unit3(literal));
        }
    },
    FORMAT_PACKED_SWITCH_PAYLOAD {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            int baseAddressForCursor = codeInput.baseAddressForCursor() - 1;
            int read = codeInput.read();
            int readInt = codeInput.readInt();
            int[] iArr = new int[read];
            for (int i2 = 0; i2 < read; i2++) {
                iArr[i2] = codeInput.readInt() + baseAddressForCursor;
            }
            return new PackedSwitchPayloadDecodedInstruction(this, i, readInt, iArr);
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            PackedSwitchPayloadDecodedInstruction packedSwitchPayloadDecodedInstruction = (PackedSwitchPayloadDecodedInstruction) decodedInstruction;
            int[] targets = packedSwitchPayloadDecodedInstruction.getTargets();
            int baseAddressForCursor = codeOutput.baseAddressForCursor();
            codeOutput.write(packedSwitchPayloadDecodedInstruction.getOpcodeUnit());
            codeOutput.write(InstructionCodec.asUnsignedUnit(targets.length));
            codeOutput.writeInt(packedSwitchPayloadDecodedInstruction.getFirstKey());
            for (int i : targets) {
                codeOutput.writeInt(i - baseAddressForCursor);
            }
        }
    },
    FORMAT_SPARSE_SWITCH_PAYLOAD {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            int baseAddressForCursor = codeInput.baseAddressForCursor() - 1;
            int read = codeInput.read();
            int[] iArr = new int[read];
            int[] iArr2 = new int[read];
            for (int i2 = 0; i2 < read; i2++) {
                iArr[i2] = codeInput.readInt();
            }
            for (int i3 = 0; i3 < read; i3++) {
                iArr2[i3] = codeInput.readInt() + baseAddressForCursor;
            }
            return new SparseSwitchPayloadDecodedInstruction(this, i, iArr, iArr2);
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            SparseSwitchPayloadDecodedInstruction sparseSwitchPayloadDecodedInstruction = (SparseSwitchPayloadDecodedInstruction) decodedInstruction;
            int[] keys = sparseSwitchPayloadDecodedInstruction.getKeys();
            int[] targets = sparseSwitchPayloadDecodedInstruction.getTargets();
            int baseAddressForCursor = codeOutput.baseAddressForCursor();
            codeOutput.write(sparseSwitchPayloadDecodedInstruction.getOpcodeUnit());
            codeOutput.write(InstructionCodec.asUnsignedUnit(targets.length));
            for (int i : keys) {
                codeOutput.writeInt(i);
            }
            for (int i2 : targets) {
                codeOutput.writeInt(i2 - baseAddressForCursor);
            }
        }
    },
    FORMAT_FILL_ARRAY_DATA_PAYLOAD {
        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException {
            int i2 = 0;
            int read = codeInput.read();
            int readInt = codeInput.readInt();
            switch (read) {
                case 1:
                    byte[] bArr = new byte[readInt];
                    int i3 = 0;
                    int i4 = 0;
                    boolean z = true;
                    while (i4 < readInt) {
                        if (z) {
                            i3 = codeInput.read();
                        }
                        bArr[i4] = (byte) (i3 & 255);
                        int i5 = i3 >> 8;
                        i4++;
                        z = !z;
                        i3 = i5;
                    }
                    return new FillArrayDataPayloadDecodedInstruction((InstructionCodec) this, i, bArr);
                case 2:
                    short[] sArr = new short[readInt];
                    while (i2 < readInt) {
                        sArr[i2] = (short) codeInput.read();
                        i2++;
                    }
                    return new FillArrayDataPayloadDecodedInstruction((InstructionCodec) this, i, sArr);
                case 3:
                case 5:
                case 6:
                case 7:
                default:
                    throw new DexException("bogus element_width: " + Hex.u2(read));
                case 4:
                    int[] iArr = new int[readInt];
                    while (i2 < readInt) {
                        iArr[i2] = codeInput.readInt();
                        i2++;
                    }
                    return new FillArrayDataPayloadDecodedInstruction((InstructionCodec) this, i, iArr);
                case 8:
                    long[] jArr = new long[readInt];
                    while (i2 < readInt) {
                        jArr[i2] = codeInput.readLong();
                        i2++;
                    }
                    return new FillArrayDataPayloadDecodedInstruction(this, i, jArr);
            }
        }

        @Override // mod.agus.jcoderz.dx.io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            FillArrayDataPayloadDecodedInstruction fillArrayDataPayloadDecodedInstruction = (FillArrayDataPayloadDecodedInstruction) decodedInstruction;
            short elementWidthUnit = fillArrayDataPayloadDecodedInstruction.getElementWidthUnit();
            Object data = fillArrayDataPayloadDecodedInstruction.getData();
            codeOutput.write(fillArrayDataPayloadDecodedInstruction.getOpcodeUnit());
            codeOutput.write(elementWidthUnit);
            codeOutput.writeInt(fillArrayDataPayloadDecodedInstruction.getSize());
            switch (elementWidthUnit) {
                case 1:
                    codeOutput.write((byte[]) data);
                    return;
                case 2:
                    codeOutput.write((short[]) data);
                    return;
                case 3:
                case 5:
                case 6:
                case 7:
                default:
                    throw new DexException("bogus element_width: " + Hex.u2(elementWidthUnit));
                case 4:
                    codeOutput.write((int[]) data);
                    return;
                case 8:
                    codeOutput.write((long[]) data);
                    return;
            }
        }
    };

    InstructionCodec() {

    }

    public abstract DecodedInstruction decode(int i, CodeInput codeInput) throws EOFException;

    public abstract void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput);

    InstructionCodec(InstructionCodec instructionCodec) {
        this();
    }

    public static DecodedInstruction decodeRegisterList(InstructionCodec instructionCodec, int i, CodeInput codeInput) throws EOFException {
        int byte0 = byte0(i);
        int nibble2 = nibble2(i);
        int nibble3 = nibble3(i);
        int read = codeInput.read();
        int read2 = codeInput.read();
        int nibble0 = nibble0(read2);
        int nibble1 = nibble1(read2);
        int nibble22 = nibble2(read2);
        int nibble32 = nibble3(read2);
        IndexType indexType = OpcodeInfo.getIndexType(byte0);
        switch (nibble3) {
            case 0:
                return new ZeroRegisterDecodedInstruction(instructionCodec, byte0, read, indexType, 0, 0);
            case 1:
                return new OneRegisterDecodedInstruction(instructionCodec, byte0, read, indexType, 0, 0, nibble0);
            case 2:
                return new TwoRegisterDecodedInstruction(instructionCodec, byte0, read, indexType, 0, 0, nibble0, nibble1);
            case 3:
                return new ThreeRegisterDecodedInstruction(instructionCodec, byte0, read, indexType, 0, 0, nibble0, nibble1, nibble22);
            case 4:
                return new FourRegisterDecodedInstruction(instructionCodec, byte0, read, indexType, 0, 0, nibble0, nibble1, nibble22, nibble32);
            case 5:
                return new FiveRegisterDecodedInstruction(instructionCodec, byte0, read, indexType, 0, 0, nibble0, nibble1, nibble22, nibble32, nibble2);
            default:
                throw new DexException("bogus registerCount: " + Hex.uNibble(nibble3));
        }
    }

    public static void encodeRegisterList(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
        codeOutput.write(codeUnit(decodedInstruction.getOpcode(), makeByte(decodedInstruction.getE(), decodedInstruction.getRegisterCount())), decodedInstruction.getIndexUnit(), codeUnit(decodedInstruction.getA(), decodedInstruction.getB(), decodedInstruction.getC(), decodedInstruction.getD()));
    }

    public static DecodedInstruction decodeRegisterRange(InstructionCodec instructionCodec, int i, CodeInput codeInput) throws EOFException {
        int byte0 = byte0(i);
        int byte1 = byte1(i);
        return new RegisterRangeDecodedInstruction(instructionCodec, byte0, codeInput.read(), OpcodeInfo.getIndexType(byte0), 0, 0, codeInput.read(), byte1);
    }

    public static void encodeRegisterRange(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
        codeOutput.write(codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getRegisterCount()), decodedInstruction.getIndexUnit(), decodedInstruction.getAUnit());
    }

    public static short codeUnit(int i, int i2) {
        if ((i & -256) != 0) {
            throw new IllegalArgumentException("bogus lowByte");
        } else if ((i2 & -256) == 0) {
            return (short) ((i2 << 8) | i);
        } else {
            throw new IllegalArgumentException("bogus highByte");
        }
    }

    private static short codeUnit(int i, int i2, int i3, int i4) {
        if ((i & -16) != 0) {
            throw new IllegalArgumentException("bogus nibble0");
        } else if ((i2 & -16) != 0) {
            throw new IllegalArgumentException("bogus nibble1");
        } else if ((i3 & -16) != 0) {
            throw new IllegalArgumentException("bogus nibble2");
        } else if ((i4 & -16) == 0) {
            return (short) ((i2 << 4) | i | (i3 << 8) | (i4 << 12));
        } else {
            throw new IllegalArgumentException("bogus nibble3");
        }
    }

    public static int makeByte(int i, int i2) {
        if ((i & -16) != 0) {
            throw new IllegalArgumentException("bogus lowNibble");
        } else if ((i2 & -16) == 0) {
            return (i2 << 4) | i;
        } else {
            throw new IllegalArgumentException("bogus highNibble");
        }
    }

    public static short asUnsignedUnit(int i) {
        if ((-65536 & i) == 0) {
            return (short) i;
        }
        throw new IllegalArgumentException("bogus unsigned code unit");
    }

    public static short unit0(int i) {
        return (short) i;
    }

    public static short unit1(int i) {
        return (short) (i >> 16);
    }

    public static short unit0(long j) {
        return (short) ((int) j);
    }

    public static short unit1(long j) {
        return (short) ((int) (j >> 16));
    }

    public static short unit2(long j) {
        return (short) ((int) (j >> 32));
    }

    public static short unit3(long j) {
        return (short) ((int) (j >> 48));
    }

    public static int byte0(int i) {
        return i & 255;
    }

    public static int byte1(int i) {
        return (i >> 8) & 255;
    }

    private static int byte2(int i) {
        return (i >> 16) & 255;
    }

    private static int byte3(int i) {
        return i >>> 24;
    }

    private static int nibble0(int i) {
        return i & 15;
    }

    private static int nibble1(int i) {
        return (i >> 4) & 15;
    }

    public static int nibble2(int i) {
        return (i >> 8) & 15;
    }

    public static int nibble3(int i) {
        return (i >> 12) & 15;
    }
}
