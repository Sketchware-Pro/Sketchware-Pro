package mod.agus.jcoderz.dx.cf.cst;

import java.util.BitSet;

import mod.agus.jcoderz.dx.cf.iface.ParseException;
import mod.agus.jcoderz.dx.cf.iface.ParseObserver;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstDouble;
import mod.agus.jcoderz.dx.rop.cst.CstFieldRef;
import mod.agus.jcoderz.dx.rop.cst.CstFloat;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.rop.cst.CstInterfaceMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstLong;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstNat;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.cst.StdConstantPool;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.ByteArray;
import mod.agus.jcoderz.dx.util.Hex;

public final class ConstantPoolParser {
    private final ByteArray bytes;
    private final int[] offsets;
    private final StdConstantPool pool;
    private int endOffset = -1;
    private ParseObserver observer;

    public ConstantPoolParser(ByteArray byteArray) {
        int unsignedShort = byteArray.getUnsignedShort(8);
        this.bytes = byteArray;
        this.pool = new StdConstantPool(unsignedShort);
        this.offsets = new int[unsignedShort];
    }

    public void setObserver(ParseObserver parseObserver) {
        this.observer = parseObserver;
    }

    public int getEndOffset() {
        parseIfNecessary();
        return this.endOffset;
    }

    public StdConstantPool getPool() {
        parseIfNecessary();
        return this.pool;
    }

    private void parseIfNecessary() {
        if (this.endOffset < 0) {
            parse();
        }
    }

    private void parse() {
        int i;
        determineOffsets();
        if (this.observer != null) {
            this.observer.parsed(this.bytes, 8, 2, "constant_pool_count: " + Hex.u2(this.offsets.length));
            this.observer.parsed(this.bytes, 10, 0, "\nconstant_pool:");
            this.observer.changeIndent(1);
        }
        BitSet bitSet = new BitSet(this.offsets.length);
        for (int i2 = 1; i2 < this.offsets.length; i2++) {
            if (this.offsets[i2] != 0 && this.pool.getOrNull(i2) == null) {
                parse0(i2, bitSet);
            }
        }
        if (this.observer != null) {
            for (int i3 = 1; i3 < this.offsets.length; i3++) {
                Constant orNull = this.pool.getOrNull(i3);
                if (orNull != null) {
                    int i4 = this.offsets[i3];
                    int i5 = this.endOffset;
                    int i6 = i3 + 1;
                    while (true) {
                        if (i6 >= this.offsets.length) {
                            i = i5;
                            break;
                        }
                        int i7 = this.offsets[i6];
                        if (i7 != 0) {
                            i = i7;
                            break;
                        }
                        i6++;
                    }
                    this.observer.parsed(this.bytes, i4, i - i4, bitSet.get(i3) ? Hex.u2(i3) + ": utf8{\"" + orNull.toHuman() + "\"}" : Hex.u2(i3) + ": " + orNull.toString());
                }
            }
            this.observer.changeIndent(-1);
            this.observer.parsed(this.bytes, this.endOffset, 0, "end constant_pool");
        }
    }

    private void determineOffsets() {
        int i;
        int i2 = 10;
        int i3 = 1;
        while (i3 < this.offsets.length) {
            this.offsets[i3] = i2;
            int unsignedByte = this.bytes.getUnsignedByte(i2);
            switch (unsignedByte) {
                case 1:
                    i2 += this.bytes.getUnsignedShort(i2 + 1) + 3;
                    i = 1;
                    break;
                case 2:
                case 13:
                case 14:
                case 17:
                default:
                    try {
                        throw new ParseException("unknown tag byte: " + Hex.u1(unsignedByte));
                    } catch (ParseException e) {
                        e.addContext("...while preparsing cst " + Hex.u2(i3) + " at offset " + Hex.u4(i2));
                        throw e;
                    }
                case 3:
                case 4:
                case 9:
                case 10:
                case 11:
                case 12:
                    i2 += 5;
                    i = 1;
                    break;
                case 5:
                case 6:
                    i = 2;
                    i2 += 9;
                    break;
                case 7:
                case 8:
                    i2 += 3;
                    i = 1;
                    break;
                case 15:
                    throw new ParseException("MethodHandle not supported");
                case 16:
                    throw new ParseException("MethodType not supported");
                case 18:
                    throw new ParseException("InvokeDynamic not supported");
            }
            i3 += i;
        }
        this.endOffset = i2;
    }

    private Constant parse0(int i, BitSet bitSet) {
        Constant orNull = this.pool.getOrNull(i);
        if (orNull == null) {
            int i2 = this.offsets[i];
            try {
                int unsignedByte = this.bytes.getUnsignedByte(i2);
                switch (unsignedByte) {
                    case 1:
                        orNull = parseUtf8(i2);
                        bitSet.set(i);
                        break;
                    case 2:
                    case 13:
                    case 14:
                    case 17:
                    default:
                        throw new ParseException("unknown tag byte: " + Hex.u1(unsignedByte));
                    case 3:
                        orNull = CstInteger.make(this.bytes.getInt(i2 + 1));
                        break;
                    case 4:
                        orNull = CstFloat.make(this.bytes.getInt(i2 + 1));
                        break;
                    case 5:
                        orNull = CstLong.make(this.bytes.getLong(i2 + 1));
                        break;
                    case 6:
                        orNull = CstDouble.make(this.bytes.getLong(i2 + 1));
                        break;
                    case 7:
                        orNull = new CstType(Type.internClassName(((CstString) parse0(this.bytes.getUnsignedShort(i2 + 1), bitSet)).getString()));
                        break;
                    case 8:
                        orNull = parse0(this.bytes.getUnsignedShort(i2 + 1), bitSet);
                        break;
                    case 9:
                        orNull = new CstFieldRef((CstType) parse0(this.bytes.getUnsignedShort(i2 + 1), bitSet), (CstNat) parse0(this.bytes.getUnsignedShort(i2 + 3), bitSet));
                        break;
                    case 10:
                        orNull = new CstMethodRef((CstType) parse0(this.bytes.getUnsignedShort(i2 + 1), bitSet), (CstNat) parse0(this.bytes.getUnsignedShort(i2 + 3), bitSet));
                        break;
                    case 11:
                        orNull = new CstInterfaceMethodRef((CstType) parse0(this.bytes.getUnsignedShort(i2 + 1), bitSet), (CstNat) parse0(this.bytes.getUnsignedShort(i2 + 3), bitSet));
                        break;
                    case 12:
                        orNull = new CstNat((CstString) parse0(this.bytes.getUnsignedShort(i2 + 1), bitSet), (CstString) parse0(this.bytes.getUnsignedShort(i2 + 3), bitSet));
                        break;
                    case 15:
                        throw new ParseException("MethodHandle not supported");
                    case 16:
                        throw new ParseException("MethodType not supported");
                    case 18:
                        throw new ParseException("InvokeDynamic not supported");
                }
                this.pool.set(i, orNull);
            } catch (ParseException e) {
                e.addContext("...while parsing cst " + Hex.u2(i) + " at offset " + Hex.u4(i2));
                throw e;
            } catch (RuntimeException e2) {
                ParseException parseException = new ParseException(e2);
                parseException.addContext("...while parsing cst " + Hex.u2(i) + " at offset " + Hex.u4(i2));
                throw parseException;
            }
        }
        return orNull;
    }

    private CstString parseUtf8(int i) {
        int i2 = i + 3;
        try {
            return new CstString(this.bytes.slice(i2, this.bytes.getUnsignedShort(i + 1) + i2));
        } catch (IllegalArgumentException e) {
            throw new ParseException(e);
        }
    }
}
