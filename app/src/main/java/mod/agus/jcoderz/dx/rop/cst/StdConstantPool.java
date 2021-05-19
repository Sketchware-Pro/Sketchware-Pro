package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dex.util.ExceptionWithContext;
import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.MutabilityControl;

public final class StdConstantPool extends MutabilityControl implements ConstantPool {
    private final Constant[] entries;

    public StdConstantPool(int i) {
        super(i > 1);
        if (i < 1) {
            throw new IllegalArgumentException("size < 1");
        }
        this.entries = new Constant[i];
    }

    private static Constant throwInvalid(int i) {
        throw new ExceptionWithContext("invalid constant pool index " + Hex.u2(i));
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.ConstantPool
    public int size() {
        return this.entries.length;
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.ConstantPool
    public Constant getOrNull(int i) {
        try {
            return this.entries[i];
        } catch (IndexOutOfBoundsException e) {
            return throwInvalid(i);
        }
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.ConstantPool
    public Constant get0Ok(int i) {
        if (i == 0) {
            return null;
        }
        return get(i);
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.ConstantPool
    public Constant get(int i) {
        try {
            Constant constant = this.entries[i];
            if (constant != null) {
                return constant;
            }
            throwInvalid(i);
            return constant;
        } catch (IndexOutOfBoundsException e) {
            return throwInvalid(i);
        }
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.ConstantPool
    public Constant[] getEntries() {
        return this.entries;
    }

    public void set(int i, Constant constant) {
        Constant constant2;
        throwIfImmutable();
        boolean z = constant != null && constant.isCategory2();
        if (i < 1) {
            throw new IllegalArgumentException("n < 1");
        }
        if (z) {
            if (i == this.entries.length - 1) {
                throw new IllegalArgumentException("(n == size - 1) && cst.isCategory2()");
            }
            this.entries[i + 1] = null;
        }
        if (constant != null && this.entries[i] == null && (constant2 = this.entries[i - 1]) != null && constant2.isCategory2()) {
            this.entries[i - 1] = null;
        }
        this.entries[i] = constant;
    }
}
