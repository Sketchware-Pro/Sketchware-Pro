package mod.agus.jcoderz.dx.cf.direct;

import java.util.ArrayList;

import mod.agus.jcoderz.dx.cf.code.ByteOps;
import mod.agus.jcoderz.dx.cf.code.BytecodeArray;
import mod.agus.jcoderz.dx.cf.code.SwitchList;
import mod.agus.jcoderz.dx.cf.iface.ParseObserver;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstDouble;
import mod.agus.jcoderz.dx.rop.cst.CstFloat;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.rop.cst.CstKnownNull;
import mod.agus.jcoderz.dx.rop.cst.CstLong;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.ByteArray;
import mod.agus.jcoderz.dx.util.Hex;

public class CodeObserver implements BytecodeArray.Visitor {
    private final ByteArray bytes;
    private final ParseObserver observer;

    public CodeObserver(ByteArray byteArray, ParseObserver parseObserver) {
        if (byteArray == null) {
            throw new NullPointerException("bytes == null");
        } else if (parseObserver == null) {
            throw new NullPointerException("observer == null");
        } else {
            this.bytes = byteArray;
            this.observer = parseObserver;
        }
    }

    @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
    public void visitInvalid(int i, int i2, int i3) {
        this.observer.parsed(this.bytes, i2, i3, header(i2));
    }

    @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
    public void visitNoArgs(int i, int i2, int i3, Type type) {
        this.observer.parsed(this.bytes, i2, i3, header(i2));
    }

    @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
    public void visitLocal(int i, int i2, int i3, int i4, Type type, int i5) {
        boolean z = true;
        String u1 = i3 <= 3 ? Hex.u1(i4) : Hex.u2(i4);
        if (i3 != 1) {
            z = false;
        }
        String str = "";
        if (i == 132) {
            str = ", #" + (i3 <= 3 ? Hex.s1(i5) : Hex.s2(i5));
        }
        String str2 = "";
        if (type.isCategory2()) {
            str2 = (z ? "," : " //") + " category-2";
        }
        this.observer.parsed(this.bytes, i2, i3, header(i2) + (z ? " // " : " ") + u1 + str + str2);
    }

    @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
    public void visitConstant(int i, int i2, int i3, Constant constant, int i4) {
        if (constant instanceof CstKnownNull) {
            visitNoArgs(i, i2, i3, null);
        } else if (constant instanceof CstInteger) {
            visitLiteralInt(i, i2, i3, i4);
        } else if (constant instanceof CstLong) {
            visitLiteralLong(i, i2, i3, ((CstLong) constant).getValue());
        } else if (constant instanceof CstFloat) {
            visitLiteralFloat(i, i2, i3, ((CstFloat) constant).getIntBits());
        } else if (constant instanceof CstDouble) {
            visitLiteralDouble(i, i2, i3, ((CstDouble) constant).getLongBits());
        } else {
            String str = "";
            if (i4 != 0) {
                if (i == 197) {
                    str = ", " + Hex.u1(i4);
                } else {
                    str = ", " + Hex.u2(i4);
                }
            }
            this.observer.parsed(this.bytes, i2, i3, header(i2) + " " + constant + str);
        }
    }

    @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
    public void visitBranch(int i, int i2, int i3, int i4) {
        this.observer.parsed(this.bytes, i2, i3, header(i2) + " " + (i3 <= 3 ? Hex.u2(i4) : Hex.u4(i4)));
    }

    @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
    public void visitSwitch(int i, int i2, int i3, SwitchList switchList, int i4) {
        int size = switchList.size();
        StringBuffer stringBuffer = new StringBuffer((size * 20) + 100);
        stringBuffer.append(header(i2));
        if (i4 != 0) {
            stringBuffer.append(" // padding: " + Hex.u4(i4));
        }
        stringBuffer.append('\n');
        for (int i5 = 0; i5 < size; i5++) {
            stringBuffer.append("  ");
            stringBuffer.append(Hex.s4(switchList.getValue(i5)));
            stringBuffer.append(": ");
            stringBuffer.append(Hex.u2(switchList.getTarget(i5)));
            stringBuffer.append('\n');
        }
        stringBuffer.append("  default: ");
        stringBuffer.append(Hex.u2(switchList.getDefaultTarget()));
        this.observer.parsed(this.bytes, i2, i3, stringBuffer.toString());
    }

    @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
    public void visitNewarray(int i, int i2, CstType cstType, ArrayList<Constant> arrayList) {
        this.observer.parsed(this.bytes, i, i2, header(i) + (i2 == 1 ? " // " : " ") + cstType.getClassType().getComponentType().toHuman());
    }

    @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
    public int getPreviousOffset() {
        return -1;
    }

    @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
    public void setPreviousOffset(int i) {
    }

    private String header(int i) {
        int unsignedByte = this.bytes.getUnsignedByte(i);
        String opName = ByteOps.opName(unsignedByte);
        if (unsignedByte == 196) {
            opName = opName + " " + ByteOps.opName(this.bytes.getUnsignedByte(i + 1));
        }
        return Hex.u2(i) + ": " + opName;
    }

    private void visitLiteralInt(int i, int i2, int i3, int i4) {
        String str;
        String str2 = i3 == 1 ? " // " : " ";
        int unsignedByte = this.bytes.getUnsignedByte(i2);
        if (i3 == 1 || unsignedByte == 16) {
            str = "#" + Hex.s1(i4);
        } else if (unsignedByte == 17) {
            str = "#" + Hex.s2(i4);
        } else {
            str = "#" + Hex.s4(i4);
        }
        this.observer.parsed(this.bytes, i2, i3, header(i2) + str2 + str);
    }

    private void visitLiteralLong(int i, int i2, int i3, long j) {
        String s8;
        String str = i3 == 1 ? " // " : " #";
        if (i3 == 1) {
            s8 = Hex.s1((int) j);
        } else {
            s8 = Hex.s8(j);
        }
        this.observer.parsed(this.bytes, i2, i3, header(i2) + str + s8);
    }

    private void visitLiteralFloat(int i, int i2, int i3, int i4) {
        this.observer.parsed(this.bytes, i2, i3, header(i2) + (i3 != 1 ? " #" + Hex.u4(i4) : "") + " // " + Float.intBitsToFloat(i4));
    }

    private void visitLiteralDouble(int i, int i2, int i3, long j) {
        this.observer.parsed(this.bytes, i2, i3, header(i2) + (i3 != 1 ? " #" + Hex.u8(j) : "") + " // " + Double.longBitsToDouble(j));
    }
}
