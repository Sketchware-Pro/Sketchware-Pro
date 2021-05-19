package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.IntList;

public final class SwitchData extends VariableSizeInsn {
    private final IntList cases;
    private final boolean packed;
    private final CodeAddress[] targets;
    private final CodeAddress user;

    public SwitchData(SourcePosition sourcePosition, CodeAddress codeAddress, IntList intList, CodeAddress[] codeAddressArr) {
        super(sourcePosition, RegisterSpecList.EMPTY);
        if (codeAddress == null) {
            throw new NullPointerException("user == null");
        } else if (intList == null) {
            throw new NullPointerException("cases == null");
        } else if (codeAddressArr == null) {
            throw new NullPointerException("targets == null");
        } else {
            int size = intList.size();
            if (size != codeAddressArr.length) {
                throw new IllegalArgumentException("cases / targets mismatch");
            } else if (size > 65535) {
                throw new IllegalArgumentException("too many cases");
            } else {
                this.user = codeAddress;
                this.cases = intList;
                this.targets = codeAddressArr;
                this.packed = shouldPack(intList);
            }
        }
    }

    private static long packedCodeSize(IntList intList) {
        long j = (((((long) intList.get(intList.size() - 1)) - ((long) intList.get(0))) + 1) * 2) + 4;
        if (j <= 2147483647L) {
            return j;
        }
        return -1;
    }

    private static long sparseCodeSize(IntList intList) {
        return (((long) intList.size()) * 4) + 2;
    }

    private static boolean shouldPack(IntList intList) {
        if (intList.size() < 2) {
            return true;
        }
        long packedCodeSize = packedCodeSize(intList);
        long sparseCodeSize = sparseCodeSize(intList);
        return packedCodeSize >= 0 && packedCodeSize <= (sparseCodeSize * 5) / 4;
    }

    @Override // mod.agus.jcoderz.dx.dex.code.DalvInsn
    public int codeSize() {
        if (this.packed) {
            return (int) packedCodeSize(this.cases);
        }
        return (int) sparseCodeSize(this.cases);
    }

    @Override // mod.agus.jcoderz.dx.dex.code.DalvInsn
    public void writeTo(AnnotatedOutput annotatedOutput) {
        int address;
        int i = 0;
        int address2 = this.user.getAddress();
        int codeSize = Dops.PACKED_SWITCH.getFormat().codeSize();
        int length = this.targets.length;
        if (this.packed) {
            int i2 = length == 0 ? 0 : this.cases.get(0);
            int i3 = ((length == 0 ? 0 : this.cases.get(length - 1)) - i2) + 1;
            annotatedOutput.writeShort(256);
            annotatedOutput.writeShort(i3);
            annotatedOutput.writeInt(i2);
            for (int i4 = 0; i4 < i3; i4++) {
                if (this.cases.get(i) > i2 + i4) {
                    address = codeSize;
                } else {
                    address = this.targets[i].getAddress() - address2;
                    i++;
                }
                annotatedOutput.writeInt(address);
            }
            return;
        }
        annotatedOutput.writeShort(512);
        annotatedOutput.writeShort(length);
        for (int i5 = 0; i5 < length; i5++) {
            annotatedOutput.writeInt(this.cases.get(i5));
        }
        while (i < length) {
            annotatedOutput.writeInt(this.targets[i].getAddress() - address2);
            i++;
        }
    }

    @Override // mod.agus.jcoderz.dx.dex.code.DalvInsn
    public DalvInsn withRegisters(RegisterSpecList registerSpecList) {
        return new SwitchData(getPosition(), this.user, this.cases, this.targets);
    }

    public boolean isPacked() {
        return this.packed;
    }

    /* access modifiers changed from: protected */
    @Override // mod.agus.jcoderz.dx.dex.code.DalvInsn
    public String argString() {
        StringBuffer stringBuffer = new StringBuffer(100);
        int length = this.targets.length;
        for (int i = 0; i < length; i++) {
            stringBuffer.append("\n    ");
            stringBuffer.append(this.cases.get(i));
            stringBuffer.append(": ");
            stringBuffer.append(this.targets[i]);
        }
        return stringBuffer.toString();
    }

    @Override // mod.agus.jcoderz.dx.dex.code.DalvInsn
    public String listingString0(boolean z) {
        int address = this.user.getAddress();
        StringBuffer stringBuffer = new StringBuffer(100);
        int length = this.targets.length;
        stringBuffer.append(this.packed ? "packed" : "sparse");
        stringBuffer.append("-switch-payload // for switch @ ");
        stringBuffer.append(Hex.u2(address));
        for (int i = 0; i < length; i++) {
            int address2 = this.targets[i].getAddress();
            stringBuffer.append("\n  ");
            stringBuffer.append(this.cases.get(i));
            stringBuffer.append(": ");
            stringBuffer.append(Hex.u4(address2));
            stringBuffer.append(" // ");
            stringBuffer.append(Hex.s4(address2 - address));
        }
        return stringBuffer.toString();
    }
}
