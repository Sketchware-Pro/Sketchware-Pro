package mod.agus.jcoderz.dx.rop.code;

import java.util.BitSet;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.util.FixedSizeList;

public final class RegisterSpecList extends FixedSizeList implements TypeList {
    public static final RegisterSpecList EMPTY = new RegisterSpecList(0);

    public static RegisterSpecList make(RegisterSpec registerSpec) {
        RegisterSpecList registerSpecList = new RegisterSpecList(1);
        registerSpecList.set(0, registerSpec);
        return registerSpecList;
    }

    public static RegisterSpecList make(RegisterSpec registerSpec, RegisterSpec registerSpec2) {
        RegisterSpecList registerSpecList = new RegisterSpecList(2);
        registerSpecList.set(0, registerSpec);
        registerSpecList.set(1, registerSpec2);
        return registerSpecList;
    }

    public static RegisterSpecList make(RegisterSpec registerSpec, RegisterSpec registerSpec2, RegisterSpec registerSpec3) {
        RegisterSpecList registerSpecList = new RegisterSpecList(3);
        registerSpecList.set(0, registerSpec);
        registerSpecList.set(1, registerSpec2);
        registerSpecList.set(2, registerSpec3);
        return registerSpecList;
    }

    public static RegisterSpecList make(RegisterSpec registerSpec, RegisterSpec registerSpec2, RegisterSpec registerSpec3, RegisterSpec registerSpec4) {
        RegisterSpecList registerSpecList = new RegisterSpecList(4);
        registerSpecList.set(0, registerSpec);
        registerSpecList.set(1, registerSpec2);
        registerSpecList.set(2, registerSpec3);
        registerSpecList.set(3, registerSpec4);
        return registerSpecList;
    }

    public RegisterSpecList(int i) {
        super(i);
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeList
    public Type getType(int i) {
        return get(i).getType().getType();
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeList
    public int getWordCount() {
        int size = size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            i += getType(i2).getCategory();
        }
        return i;
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeList
    public TypeList withAddedType(Type type) {
        throw new UnsupportedOperationException("unsupported");
    }

    public RegisterSpec get(int i) {
        return (RegisterSpec) get0(i);
    }

    public RegisterSpec specForRegister(int i) {
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            RegisterSpec registerSpec = get(i2);
            if (registerSpec.getReg() == i) {
                return registerSpec;
            }
        }
        return null;
    }

    public int indexOfRegister(int i) {
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            if (get(i2).getReg() == i) {
                return i2;
            }
        }
        return -1;
    }

    public void set(int i, RegisterSpec registerSpec) {
        set0(i, registerSpec);
    }

    public int getRegistersSize() {
        int i;
        int size = size();
        int i2 = 0;
        int i3 = 0;
        while (i2 < size) {
            RegisterSpec registerSpec = (RegisterSpec) get0(i2);
            if (registerSpec == null || (i = registerSpec.getNextReg()) <= i3) {
                i = i3;
            }
            i2++;
            i3 = i;
        }
        return i3;
    }

    public RegisterSpecList withFirst(RegisterSpec registerSpec) {
        int size = size();
        RegisterSpecList registerSpecList = new RegisterSpecList(size + 1);
        for (int i = 0; i < size; i++) {
            registerSpecList.set0(i + 1, get0(i));
        }
        registerSpecList.set0(0, registerSpec);
        if (isImmutable()) {
            registerSpecList.setImmutable();
        }
        return registerSpecList;
    }

    public RegisterSpecList withoutFirst() {
        int size = size() - 1;
        if (size == 0) {
            return EMPTY;
        }
        RegisterSpecList registerSpecList = new RegisterSpecList(size);
        for (int i = 0; i < size; i++) {
            registerSpecList.set0(i, get0(i + 1));
        }
        if (!isImmutable()) {
            return registerSpecList;
        }
        registerSpecList.setImmutable();
        return registerSpecList;
    }

    public RegisterSpecList withoutLast() {
        int size = size() - 1;
        if (size == 0) {
            return EMPTY;
        }
        RegisterSpecList registerSpecList = new RegisterSpecList(size);
        for (int i = 0; i < size; i++) {
            registerSpecList.set0(i, get0(i));
        }
        if (!isImmutable()) {
            return registerSpecList;
        }
        registerSpecList.setImmutable();
        return registerSpecList;
    }

    public RegisterSpecList subset(BitSet bitSet) {
        int size = size() - bitSet.cardinality();
        if (size == 0) {
            return EMPTY;
        }
        RegisterSpecList registerSpecList = new RegisterSpecList(size);
        int i = 0;
        for (int i2 = 0; i2 < size(); i2++) {
            if (!bitSet.get(i2)) {
                registerSpecList.set0(i, get0(i2));
                i++;
            }
        }
        if (isImmutable()) {
            registerSpecList.setImmutable();
        }
        return registerSpecList;
    }

    public RegisterSpecList withOffset(int i) {
        int size = size();
        if (size == 0) {
            return this;
        }
        RegisterSpecList registerSpecList = new RegisterSpecList(size);
        for (int i2 = 0; i2 < size; i2++) {
            RegisterSpec registerSpec = (RegisterSpec) get0(i2);
            if (registerSpec != null) {
                registerSpecList.set0(i2, registerSpec.withOffset(i));
            }
        }
        if (isImmutable()) {
            registerSpecList.setImmutable();
        }
        return registerSpecList;
    }

    public RegisterSpecList withExpandedRegisters(int i, boolean z, BitSet bitSet) {
        int size = size();
        if (size == 0) {
            return this;
        }
        Expander expander = new Expander(this, bitSet, i, z, null);
        for (int i2 = 0; i2 < size; i2++) {
            expander.expandRegister(i2);
        }
        return expander.getResult();
    }

    private static class Expander {
        private int base;
        private BitSet compatRegs;
        private boolean duplicateFirst;
        private RegisterSpecList regSpecList;
        private RegisterSpecList result;

        private Expander(RegisterSpecList registerSpecList, BitSet bitSet, int i, boolean z) {
            this.regSpecList = registerSpecList;
            this.compatRegs = bitSet;
            this.base = i;
            this.result = new RegisterSpecList(registerSpecList.size());
            this.duplicateFirst = z;
        }

        /* synthetic */ Expander(RegisterSpecList registerSpecList, BitSet bitSet, int i, boolean z, Expander expander) {
            this(registerSpecList, bitSet, i, z);
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private void expandRegister(int i) {
            expandRegister(i, (RegisterSpec) this.regSpecList.get0(i));
        }

        private void expandRegister(int i, RegisterSpec registerSpec) {
            boolean z = true;
            if (this.compatRegs != null && this.compatRegs.get(i)) {
                z = false;
            }
            if (z) {
                registerSpec = registerSpec.withReg(this.base);
                if (!this.duplicateFirst) {
                    this.base += registerSpec.getCategory();
                }
                this.duplicateFirst = false;
            }
            this.result.set0(i, registerSpec);
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private RegisterSpecList getResult() {
            if (this.regSpecList.isImmutable()) {
                this.result.setImmutable();
            }
            return this.result;
        }
    }
}
