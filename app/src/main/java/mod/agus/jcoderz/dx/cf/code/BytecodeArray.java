package mod.agus.jcoderz.dx.cf.code;

import java.util.ArrayList;
import mod.agus.jcoderz.dx.cf.iface.ParseException;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.ConstantPool;
import mod.agus.jcoderz.dx.rop.cst.CstDouble;
import mod.agus.jcoderz.dx.rop.cst.CstFloat;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.rop.cst.CstKnownNull;
import mod.agus.jcoderz.dx.rop.cst.CstLiteralBits;
import mod.agus.jcoderz.dx.rop.cst.CstLong;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.Bits;
import mod.agus.jcoderz.dx.util.ByteArray;
import mod.agus.jcoderz.dx.util.Hex;

public final class BytecodeArray {
    public static final Visitor EMPTY_VISITOR = new BaseVisitor();
    private final ByteArray bytes;
    private final ConstantPool pool;

    public interface Visitor {
        int getPreviousOffset();

        void setPreviousOffset(int i);

        void visitBranch(int i, int i2, int i3, int i4);

        void visitConstant(int i, int i2, int i3, Constant constant, int i4);

        void visitInvalid(int i, int i2, int i3);

        void visitLocal(int i, int i2, int i3, int i4, Type type, int i5);

        void visitNewarray(int i, int i2, CstType cstType, ArrayList<Constant> arrayList);

        void visitNoArgs(int i, int i2, int i3, Type type);

        void visitSwitch(int i, int i2, int i3, SwitchList switchList, int i4);
    }

    public BytecodeArray(ByteArray byteArray, ConstantPool constantPool) {
        if (byteArray == null) {
            throw new NullPointerException("bytes == null");
        } else if (constantPool == null) {
            throw new NullPointerException("pool == null");
        } else {
            this.bytes = byteArray;
            this.pool = constantPool;
        }
    }

    public ByteArray getBytes() {
        return this.bytes;
    }

    public int size() {
        return this.bytes.size();
    }

    public int byteLength() {
        return this.bytes.size() + 4;
    }

    public void forEach(Visitor visitor) {
        int size = this.bytes.size();
        int i = 0;
        while (i < size) {
            i += parseInstruction(i, visitor);
        }
    }

    public int[] getInstructionOffsets() {
        int size = this.bytes.size();
        int[] makeBitSet = Bits.makeBitSet(size);
        int i = 0;
        while (i < size) {
            Bits.set(makeBitSet, i, true);
            i += parseInstruction(i, null);
        }
        return makeBitSet;
    }

    public void processWorkSet(int[] iArr, Visitor visitor) {
        if (visitor == null) {
            throw new NullPointerException("visitor == null");
        }
        while (true) {
            int findFirst = Bits.findFirst(iArr, 0);
            if (findFirst >= 0) {
                Bits.clear(iArr, findFirst);
                parseInstruction(findFirst, visitor);
                visitor.setPreviousOffset(findFirst);
            } else {
                return;
            }
        }
    }

    public int parseInstruction(int i, Visitor visitor) {
        Visitor visitor2;
        int i2;
        int i3 = 0;
        if (visitor == null) {
            visitor2 = EMPTY_VISITOR;
        } else {
            visitor2 = visitor;
        }
        try {
            int unsignedByte = this.bytes.getUnsignedByte(i);
            ByteOps.opInfo(unsignedByte);
            switch (unsignedByte) {
                case 0:
                    visitor2.visitNoArgs(unsignedByte, i, 1, Type.VOID);
                    return 1;
                case 1:
                    visitor2.visitConstant(18, i, 1, CstKnownNull.THE_ONE, 0);
                    return 1;
                case 2:
                    visitor2.visitConstant(18, i, 1, CstInteger.VALUE_M1, -1);
                    return 1;
                case 3:
                    visitor2.visitConstant(18, i, 1, CstInteger.VALUE_0, 0);
                    return 1;
                case 4:
                    visitor2.visitConstant(18, i, 1, CstInteger.VALUE_1, 1);
                    return 1;
                case 5:
                    visitor2.visitConstant(18, i, 1, CstInteger.VALUE_2, 2);
                    return 1;
                case 6:
                    visitor2.visitConstant(18, i, 1, CstInteger.VALUE_3, 3);
                    return 1;
                case 7:
                    visitor2.visitConstant(18, i, 1, CstInteger.VALUE_4, 4);
                    return 1;
                case 8:
                    visitor2.visitConstant(18, i, 1, CstInteger.VALUE_5, 5);
                    return 1;
                case 9:
                    visitor2.visitConstant(18, i, 1, CstLong.VALUE_0, 0);
                    return 1;
                case 10:
                    visitor2.visitConstant(18, i, 1, CstLong.VALUE_1, 0);
                    return 1;
                case 11:
                    visitor2.visitConstant(18, i, 1, CstFloat.VALUE_0, 0);
                    return 1;
                case 12:
                    visitor2.visitConstant(18, i, 1, CstFloat.VALUE_1, 0);
                    return 1;
                case 13:
                    visitor2.visitConstant(18, i, 1, CstFloat.VALUE_2, 0);
                    return 1;
                case 14:
                    visitor2.visitConstant(18, i, 1, CstDouble.VALUE_0, 0);
                    return 1;
                case 15:
                    visitor2.visitConstant(18, i, 1, CstDouble.VALUE_1, 0);
                    return 1;
                case 16:
                    int i4 = this.bytes.getByte(i + 1);
                    visitor2.visitConstant(18, i, 2, CstInteger.make(i4), i4);
                    return 2;
                case 17:
                    int i5 = this.bytes.getShort(i + 1);
                    visitor2.visitConstant(18, i, 3, CstInteger.make(i5), i5);
                    return 3;
                case 18:
                    Constant constant = this.pool.get(this.bytes.getUnsignedByte(i + 1));
                    if (constant instanceof CstInteger) {
                        i3 = ((CstInteger) constant).getValue();
                    }
                    visitor2.visitConstant(18, i, 2, constant, i3);
                    return 2;
                case 19:
                    Constant constant2 = this.pool.get(this.bytes.getUnsignedShort(i + 1));
                    if (constant2 instanceof CstInteger) {
                        i3 = ((CstInteger) constant2).getValue();
                    }
                    visitor2.visitConstant(18, i, 3, constant2, i3);
                    return 3;
                case 20:
                    visitor2.visitConstant(20, i, 3, this.pool.get(this.bytes.getUnsignedShort(i + 1)), 0);
                    return 3;
                case 21:
                    visitor2.visitLocal(21, i, 2, this.bytes.getUnsignedByte(i + 1), Type.INT, 0);
                    return 2;
                case 22:
                    visitor2.visitLocal(21, i, 2, this.bytes.getUnsignedByte(i + 1), Type.LONG, 0);
                    return 2;
                case 23:
                    visitor2.visitLocal(21, i, 2, this.bytes.getUnsignedByte(i + 1), Type.FLOAT, 0);
                    return 2;
                case 24:
                    visitor2.visitLocal(21, i, 2, this.bytes.getUnsignedByte(i + 1), Type.DOUBLE, 0);
                    return 2;
                case 25:
                    visitor2.visitLocal(21, i, 2, this.bytes.getUnsignedByte(i + 1), Type.OBJECT, 0);
                    return 2;
                case 26:
                case 27:
                case 28:
                case 29:
                    visitor2.visitLocal(21, i, 1, unsignedByte - 26, Type.INT, 0);
                    return 1;
                case 30:
                case 31:
                case 32:
                case 33:
                    visitor2.visitLocal(21, i, 1, unsignedByte - 30, Type.LONG, 0);
                    return 1;
                case 34:
                case 35:
                case 36:
                case 37:
                    visitor2.visitLocal(21, i, 1, unsignedByte - 34, Type.FLOAT, 0);
                    return 1;
                case 38:
                case 39:
                case 40:
                case 41:
                    visitor2.visitLocal(21, i, 1, unsignedByte - 38, Type.DOUBLE, 0);
                    return 1;
                case 42:
                case 43:
                case 44:
                case 45:
                    visitor2.visitLocal(21, i, 1, unsignedByte - 42, Type.OBJECT, 0);
                    return 1;
                case 46:
                    visitor2.visitNoArgs(46, i, 1, Type.INT);
                    return 1;
                case 47:
                    visitor2.visitNoArgs(46, i, 1, Type.LONG);
                    return 1;
                case 48:
                    visitor2.visitNoArgs(46, i, 1, Type.FLOAT);
                    return 1;
                case 49:
                    visitor2.visitNoArgs(46, i, 1, Type.DOUBLE);
                    return 1;
                case 50:
                    visitor2.visitNoArgs(46, i, 1, Type.OBJECT);
                    return 1;
                case 51:
                    visitor2.visitNoArgs(46, i, 1, Type.BYTE);
                    return 1;
                case 52:
                    visitor2.visitNoArgs(46, i, 1, Type.CHAR);
                    return 1;
                case 53:
                    visitor2.visitNoArgs(46, i, 1, Type.SHORT);
                    return 1;
                case 54:
                    visitor2.visitLocal(54, i, 2, this.bytes.getUnsignedByte(i + 1), Type.INT, 0);
                    return 2;
                case 55:
                    visitor2.visitLocal(54, i, 2, this.bytes.getUnsignedByte(i + 1), Type.LONG, 0);
                    return 2;
                case 56:
                    visitor2.visitLocal(54, i, 2, this.bytes.getUnsignedByte(i + 1), Type.FLOAT, 0);
                    return 2;
                case 57:
                    visitor2.visitLocal(54, i, 2, this.bytes.getUnsignedByte(i + 1), Type.DOUBLE, 0);
                    return 2;
                case 58:
                    visitor2.visitLocal(54, i, 2, this.bytes.getUnsignedByte(i + 1), Type.OBJECT, 0);
                    return 2;
                case 59:
                case 60:
                case 61:
                case 62:
                    visitor2.visitLocal(54, i, 1, unsignedByte - 59, Type.INT, 0);
                    return 1;
                case 63:
                case 64:
                case 65:
                case 66:
                    visitor2.visitLocal(54, i, 1, unsignedByte - 63, Type.LONG, 0);
                    return 1;
                case 67:
                case 68:
                case 69:
                case 70:
                    visitor2.visitLocal(54, i, 1, unsignedByte - 67, Type.FLOAT, 0);
                    return 1;
                case 71:
                case 72:
                case 73:
                case 74:
                    visitor2.visitLocal(54, i, 1, unsignedByte - 71, Type.DOUBLE, 0);
                    return 1;
                case 75:
                case 76:
                case 77:
                case 78:
                    visitor2.visitLocal(54, i, 1, unsignedByte - 75, Type.OBJECT, 0);
                    return 1;
                case 79:
                    visitor2.visitNoArgs(79, i, 1, Type.INT);
                    return 1;
                case 80:
                    visitor2.visitNoArgs(79, i, 1, Type.LONG);
                    return 1;
                case 81:
                    visitor2.visitNoArgs(79, i, 1, Type.FLOAT);
                    return 1;
                case 82:
                    visitor2.visitNoArgs(79, i, 1, Type.DOUBLE);
                    return 1;
                case 83:
                    visitor2.visitNoArgs(79, i, 1, Type.OBJECT);
                    return 1;
                case 84:
                    visitor2.visitNoArgs(79, i, 1, Type.BYTE);
                    return 1;
                case 85:
                    visitor2.visitNoArgs(79, i, 1, Type.CHAR);
                    return 1;
                case 86:
                    visitor2.visitNoArgs(79, i, 1, Type.SHORT);
                    return 1;
                case 87:
                case 88:
                case 89:
                case 90:
                case 91:
                case 92:
                case 93:
                case 94:
                case 95:
                    visitor2.visitNoArgs(unsignedByte, i, 1, Type.VOID);
                    return 1;
                case 96:
                case 100:
                case 104:
                case 108:
                case 112:
                case 116:
                case 120:
                case 122:
                case 124:
                case 126:
                case 128:
                case 130:
                    visitor2.visitNoArgs(unsignedByte, i, 1, Type.INT);
                    return 1;
                case 97:
                case 101:
                case 105:
                case 109:
                case 113:
                case 117:
                case 121:
                case 123:
                case 125:
                case 127:
                case 129:
                case 131:
                    visitor2.visitNoArgs(unsignedByte - 1, i, 1, Type.LONG);
                    return 1;
                case 98:
                case 102:
                case 106:
                case 110:
                case 114:
                case 118:
                    visitor2.visitNoArgs(unsignedByte - 2, i, 1, Type.FLOAT);
                    return 1;
                case 99:
                case 103:
                case 107:
                case 111:
                case 115:
                case 119:
                    visitor2.visitNoArgs(unsignedByte - 3, i, 1, Type.DOUBLE);
                    return 1;
                case 132:
                    visitor2.visitLocal(unsignedByte, i, 3, this.bytes.getUnsignedByte(i + 1), Type.INT, this.bytes.getByte(i + 2));
                    return 3;
                case 133:
                case 140:
                case 143:
                    visitor2.visitNoArgs(unsignedByte, i, 1, Type.LONG);
                    return 1;
                case 134:
                case 137:
                case 144:
                    visitor2.visitNoArgs(unsignedByte, i, 1, Type.FLOAT);
                    return 1;
                case 135:
                case 138:
                case 141:
                    visitor2.visitNoArgs(unsignedByte, i, 1, Type.DOUBLE);
                    return 1;
                case 136:
                case 139:
                case 142:
                case 145:
                case 146:
                case 147:
                case 148:
                case 149:
                case 150:
                case 151:
                case 152:
                case 190:
                    visitor2.visitNoArgs(unsignedByte, i, 1, Type.INT);
                    return 1;
                case 153:
                case 154:
                case 155:
                case 156:
                case 157:
                case 158:
                case 159:
                case 160:
                case 161:
                case 162:
                case 163:
                case 164:
                case 165:
                case 166:
                case 167:
                case 168:
                case 198:
                case 199:
                    visitor2.visitBranch(unsignedByte, i, 3, this.bytes.getShort(i + 1) + i);
                    return 3;
                case 169:
                    visitor2.visitLocal(unsignedByte, i, 2, this.bytes.getUnsignedByte(i + 1), Type.RETURN_ADDRESS, 0);
                    return 2;
                case 170:
                    return parseTableswitch(i, visitor2);
                case 171:
                    return parseLookupswitch(i, visitor2);
                case 172:
                    visitor2.visitNoArgs(172, i, 1, Type.INT);
                    return 1;
                case 173:
                    visitor2.visitNoArgs(172, i, 1, Type.LONG);
                    return 1;
                case 174:
                    visitor2.visitNoArgs(172, i, 1, Type.FLOAT);
                    return 1;
                case 175:
                    visitor2.visitNoArgs(172, i, 1, Type.DOUBLE);
                    return 1;
                case 176:
                    visitor2.visitNoArgs(172, i, 1, Type.OBJECT);
                    return 1;
                case 177:
                case 191:
                case 194:
                case 195:
                    visitor2.visitNoArgs(unsignedByte, i, 1, Type.VOID);
                    return 1;
                case 178:
                case 179:
                case 180:
                case 181:
                case 182:
                case 183:
                case 184:
                case 187:
                case 189:
                case 192:
                case 193:
                    visitor2.visitConstant(unsignedByte, i, 3, this.pool.get(this.bytes.getUnsignedShort(i + 1)), 0);
                    return 3;
                case 185:
                    visitor2.visitConstant(unsignedByte, i, 5, this.pool.get(this.bytes.getUnsignedShort(i + 1)), this.bytes.getUnsignedByte(i + 3) | (this.bytes.getUnsignedByte(i + 4) << 8));
                    return 5;
                case 186:
                    throw new ParseException("invokedynamic not supported");
                case 188:
                    return parseNewarray(i, visitor2);
                case 196:
                    return parseWide(i, visitor2);
                case 197:
                    int unsignedShort = this.bytes.getUnsignedShort(i + 1);
                    visitor2.visitConstant(unsignedByte, i, 4, this.pool.get(unsignedShort), this.bytes.getUnsignedByte(i + 3));
                    return 4;
                case 200:
                case 201:
                    int i6 = this.bytes.getInt(i + 1) + i;
                    if (unsignedByte == 200) {
                        i2 = 167;
                    } else {
                        i2 = 168;
                    }
                    visitor2.visitBranch(i2, i, 5, i6);
                    return 5;
                default:
                    visitor2.visitInvalid(unsignedByte, i, 1);
                    return 1;
            }
        } catch (SimException e) {
            e.addContext("...at bytecode offset " + Hex.u4(i));
            throw e;
        } catch (RuntimeException e2) {
            SimException simException = new SimException(e2);
            simException.addContext("...at bytecode offset " + Hex.u4(i));
            throw simException;
        }
    }

    private int parseTableswitch(int i, Visitor visitor) {
        int i2 = (i + 4) & -4;
        int i3 = 0;
        for (int i4 = i + 1; i4 < i2; i4++) {
            i3 = (i3 << 8) | this.bytes.getUnsignedByte(i4);
        }
        int i5 = i + this.bytes.getInt(i2);
        int i6 = this.bytes.getInt(i2 + 4);
        int i7 = this.bytes.getInt(i2 + 8);
        int i8 = (i7 - i6) + 1;
        int i9 = i2 + 12;
        if (i6 > i7) {
            throw new SimException("low / high inversion");
        }
        SwitchList switchList = new SwitchList(i8);
        for (int i10 = 0; i10 < i8; i10++) {
            i9 += 4;
            switchList.add(i6 + i10, this.bytes.getInt(i9) + i);
        }
        switchList.setDefaultTarget(i5);
        switchList.removeSuperfluousDefaults();
        switchList.setImmutable();
        int i11 = i9 - i;
        visitor.visitSwitch(171, i, i11, switchList, i3);
        return i11;
    }

    private int parseLookupswitch(int i, Visitor visitor) {
        int i2 = (i + 4) & -4;
        int i3 = 0;
        for (int i4 = i + 1; i4 < i2; i4++) {
            i3 = (i3 << 8) | this.bytes.getUnsignedByte(i4);
        }
        int i5 = i + this.bytes.getInt(i2);
        int i6 = this.bytes.getInt(i2 + 4);
        int i7 = i2 + 8;
        SwitchList switchList = new SwitchList(i6);
        for (int i8 = 0; i8 < i6; i8++) {
            i7 += 8;
            switchList.add(this.bytes.getInt(i7), this.bytes.getInt(i7 + 4) + i);
        }
        switchList.setDefaultTarget(i5);
        switchList.removeSuperfluousDefaults();
        switchList.setImmutable();
        int i9 = i7 - i;
        visitor.visitSwitch(171, i, i9, switchList, i3);
        return i9;
    }

    private int parseNewarray(int i, Visitor visitor) {
        CstType cstType;
        int i2;
        int i3;
        boolean z;
        int unsignedByte = this.bytes.getUnsignedByte(i + 1);
        switch (unsignedByte) {
            case 4:
                cstType = CstType.BOOLEAN_ARRAY;
                break;
            case 5:
                cstType = CstType.CHAR_ARRAY;
                break;
            case 6:
                cstType = CstType.FLOAT_ARRAY;
                break;
            case 7:
                cstType = CstType.DOUBLE_ARRAY;
                break;
            case 8:
                cstType = CstType.BYTE_ARRAY;
                break;
            case 9:
                cstType = CstType.SHORT_ARRAY;
                break;
            case 10:
                cstType = CstType.INT_ARRAY;
                break;
            case 11:
                cstType = CstType.LONG_ARRAY;
                break;
            default:
                throw new SimException("bad newarray code " + Hex.u1(unsignedByte));
        }
        int previousOffset = visitor.getPreviousOffset();
        ConstantParserVisitor constantParserVisitor = new ConstantParserVisitor();
        if (previousOffset >= 0) {
            parseInstruction(previousOffset, constantParserVisitor);
            if ((constantParserVisitor.cst instanceof CstInteger) && previousOffset + constantParserVisitor.length == i) {
                i2 = constantParserVisitor.value;
                int i4 = i + 2;
                ArrayList<Constant> arrayList = new ArrayList<>();
                if (i2 == 0) {
                    int i5 = i4;
                    i3 = 0;
                    while (true) {
                        int i6 = i5 + 1;
                        if (this.bytes.getUnsignedByte(i5) == 89) {
                            parseInstruction(i6, constantParserVisitor);
                            if (constantParserVisitor.length != 0 && (constantParserVisitor.cst instanceof CstInteger) && constantParserVisitor.value == i3) {
                                int i7 = constantParserVisitor.length + i6;
                                parseInstruction(i7, constantParserVisitor);
                                if (constantParserVisitor.length != 0 && (constantParserVisitor.cst instanceof CstLiteralBits)) {
                                    int i8 = constantParserVisitor.length + i7;
                                    arrayList.add(constantParserVisitor.cst);
                                    i5 = i8 + 1;
                                    int unsignedByte2 = this.bytes.getUnsignedByte(i8);
                                    switch (unsignedByte) {
                                        case 4:
                                        case 8:
                                            if (unsignedByte2 != 84) {
                                                z = true;
                                                break;
                                            }
                                            z = false;
                                            break;
                                        case 5:
                                            if (unsignedByte2 != 85) {
                                                z = true;
                                                break;
                                            }
                                            z = false;
                                            break;
                                        case 6:
                                            if (unsignedByte2 != 81) {
                                                z = true;
                                                break;
                                            }
                                            z = false;
                                            break;
                                        case 7:
                                            if (unsignedByte2 != 82) {
                                                z = true;
                                                break;
                                            }
                                            z = false;
                                            break;
                                        case 9:
                                            if (unsignedByte2 != 86) {
                                                z = true;
                                                break;
                                            }
                                            z = false;
                                            break;
                                        case 10:
                                            if (unsignedByte2 != 79) {
                                                z = true;
                                                break;
                                            }
                                            z = false;
                                            break;
                                        case 11:
                                            if (unsignedByte2 != 80) {
                                                z = true;
                                                break;
                                            }
                                            z = false;
                                            break;
                                        default:
                                            z = true;
                                            break;
                                    }
                                    if (!z) {
                                        i3++;
                                        i4 = i5;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    i3 = 0;
                }
                if (i3 >= 2 || i3 != i2) {
                    visitor.visitNewarray(i, 2, cstType, null);
                    return 2;
                }
                visitor.visitNewarray(i, i4 - i, cstType, arrayList);
                return i4 - i;
            }
        }
        i2 = 0;
        i3 = 0;
        int i42 = i + 2;
        ArrayList<Constant> arrayList2 = new ArrayList<>();
        if (i2 == 0) {
        }
        if (i3 >= 2) {
        }
        visitor.visitNewarray(i, 2, cstType, null);
        return 2;
    }

    private int parseWide(int i, Visitor visitor) {
        int unsignedByte = this.bytes.getUnsignedByte(i + 1);
        int unsignedShort = this.bytes.getUnsignedShort(i + 2);
        switch (unsignedByte) {
            case 21:
                visitor.visitLocal(21, i, 4, unsignedShort, Type.INT, 0);
                return 4;
            case 22:
                visitor.visitLocal(21, i, 4, unsignedShort, Type.LONG, 0);
                return 4;
            case 23:
                visitor.visitLocal(21, i, 4, unsignedShort, Type.FLOAT, 0);
                return 4;
            case 24:
                visitor.visitLocal(21, i, 4, unsignedShort, Type.DOUBLE, 0);
                return 4;
            case 25:
                visitor.visitLocal(21, i, 4, unsignedShort, Type.OBJECT, 0);
                return 4;
            case 54:
                visitor.visitLocal(54, i, 4, unsignedShort, Type.INT, 0);
                return 4;
            case 55:
                visitor.visitLocal(54, i, 4, unsignedShort, Type.LONG, 0);
                return 4;
            case 56:
                visitor.visitLocal(54, i, 4, unsignedShort, Type.FLOAT, 0);
                return 4;
            case 57:
                visitor.visitLocal(54, i, 4, unsignedShort, Type.DOUBLE, 0);
                return 4;
            case 58:
                visitor.visitLocal(54, i, 4, unsignedShort, Type.OBJECT, 0);
                return 4;
            case 132:
                visitor.visitLocal(unsignedByte, i, 6, unsignedShort, Type.INT, this.bytes.getShort(i + 4));
                return 6;
            case 169:
                visitor.visitLocal(unsignedByte, i, 4, unsignedShort, Type.RETURN_ADDRESS, 0);
                return 4;
            default:
                visitor.visitInvalid(196, i, 1);
                return 1;
        }
    }

    public static class BaseVisitor implements Visitor {
        private int previousOffset = -1;

        BaseVisitor() {
        }

        @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
        public void visitInvalid(int i, int i2, int i3) {
        }

        @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
        public void visitNoArgs(int i, int i2, int i3, Type type) {
        }

        @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
        public void visitLocal(int i, int i2, int i3, int i4, Type type, int i5) {
        }

        @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
        public void visitConstant(int i, int i2, int i3, Constant constant, int i4) {
        }

        @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
        public void visitBranch(int i, int i2, int i3, int i4) {
        }

        @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
        public void visitSwitch(int i, int i2, int i3, SwitchList switchList, int i4) {
        }

        @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
        public void visitNewarray(int i, int i2, CstType cstType, ArrayList<Constant> arrayList) {
        }

        @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
        public void setPreviousOffset(int i) {
            this.previousOffset = i;
        }

        @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
        public int getPreviousOffset() {
            return this.previousOffset;
        }
    }


    public class ConstantParserVisitor extends BaseVisitor {
        Constant cst;
        int length;
        int value;

        ConstantParserVisitor() {
        }

        private void clear() {
            this.length = 0;
        }

        @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.BaseVisitor, mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
        public void visitInvalid(int i, int i2, int i3) {
            clear();
        }

        @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.BaseVisitor, mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
        public void visitNoArgs(int i, int i2, int i3, Type type) {
            clear();
        }

        @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.BaseVisitor, mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
        public void visitLocal(int i, int i2, int i3, int i4, Type type, int i5) {
            clear();
        }

        @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.BaseVisitor, mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
        public void visitConstant(int i, int i2, int i3, Constant constant, int i4) {
            this.cst = constant;
            this.length = i3;
            this.value = i4;
        }

        @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.BaseVisitor, mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
        public void visitBranch(int i, int i2, int i3, int i4) {
            clear();
        }

        @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.BaseVisitor, mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
        public void visitSwitch(int i, int i2, int i3, SwitchList switchList, int i4) {
            clear();
        }

        @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.BaseVisitor, mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
        public void visitNewarray(int i, int i2, CstType cstType, ArrayList<Constant> arrayList) {
            clear();
        }

        @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.BaseVisitor, mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
        public void setPreviousOffset(int i) {
        }

        @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.BaseVisitor, mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
        public int getPreviousOffset() {
            return -1;
        }
    }
}
