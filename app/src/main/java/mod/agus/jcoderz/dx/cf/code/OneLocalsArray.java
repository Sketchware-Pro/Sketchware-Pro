package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dex.util.ExceptionWithContext;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;
import mod.agus.jcoderz.dx.util.Hex;

public class OneLocalsArray extends LocalsArray {
    private final TypeBearer[] locals;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public OneLocalsArray(int i) {
        super(i != 0);
        this.locals = new TypeBearer[i];
    }

    private static TypeBearer throwSimException(int i, String str) {
        throw new SimException("local " + Hex.u2(i) + ": " + str);
    }

    @Override // mod.agus.jcoderz.dx.cf.code.LocalsArray
    public OneLocalsArray copy() {
        OneLocalsArray oneLocalsArray = new OneLocalsArray(this.locals.length);
        System.arraycopy(this.locals, 0, oneLocalsArray.locals, 0, this.locals.length);
        return oneLocalsArray;
    }

    @Override // mod.agus.jcoderz.dx.cf.code.LocalsArray
    public void annotate(ExceptionWithContext exceptionWithContext) {
        for (int i = 0; i < this.locals.length; i++) {
            TypeBearer typeBearer = this.locals[i];
            exceptionWithContext.addContext("locals[" + Hex.u2(i) + "]: " + (typeBearer == null ? "<invalid>" : typeBearer.toString()));
        }
    }

    @Override // mod.agus.jcoderz.dx.util.ToHuman
    public String toHuman() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.locals.length; i++) {
            TypeBearer typeBearer = this.locals[i];
            sb.append("locals[" + Hex.u2(i) + "]: " + (typeBearer == null ? "<invalid>" : typeBearer.toString()) + "\n");
        }
        return sb.toString();
    }

    @Override // mod.agus.jcoderz.dx.cf.code.LocalsArray
    public void makeInitialized(Type type) {
        int length = this.locals.length;
        if (length != 0) {
            throwIfImmutable();
            Type initializedType = type.getInitializedType();
            for (int i = 0; i < length; i++) {
                if (this.locals[i] == type) {
                    this.locals[i] = initializedType;
                }
            }
        }
    }

    @Override // mod.agus.jcoderz.dx.cf.code.LocalsArray
    public int getMaxLocals() {
        return this.locals.length;
    }

    @Override // mod.agus.jcoderz.dx.cf.code.LocalsArray
    public void set(int i, TypeBearer typeBearer) {
        TypeBearer typeBearer2;
        throwIfImmutable();
        try {
            TypeBearer frameType = typeBearer.getFrameType();
            if (i < 0) {
                throw new IndexOutOfBoundsException("idx < 0");
            }
            if (frameType.getType().isCategory2()) {
                this.locals[i + 1] = null;
            }
            this.locals[i] = frameType;
            if (i != 0 && (typeBearer2 = this.locals[i - 1]) != null && typeBearer2.getType().isCategory2()) {
                this.locals[i - 1] = null;
            }
        } catch (NullPointerException e) {
            throw new NullPointerException("type == null");
        }
    }

    @Override // mod.agus.jcoderz.dx.cf.code.LocalsArray
    public void set(RegisterSpec registerSpec) {
        set(registerSpec.getReg(), registerSpec);
    }

    @Override // mod.agus.jcoderz.dx.cf.code.LocalsArray
    public void invalidate(int i) {
        throwIfImmutable();
        this.locals[i] = null;
    }

    @Override // mod.agus.jcoderz.dx.cf.code.LocalsArray
    public TypeBearer getOrNull(int i) {
        return this.locals[i];
    }

    @Override // mod.agus.jcoderz.dx.cf.code.LocalsArray
    public TypeBearer get(int i) {
        TypeBearer typeBearer = this.locals[i];
        if (typeBearer == null) {
            return throwSimException(i, "invalid");
        }
        return typeBearer;
    }

    @Override // mod.agus.jcoderz.dx.cf.code.LocalsArray
    public TypeBearer getCategory1(int i) {
        TypeBearer typeBearer = get(i);
        Type type = typeBearer.getType();
        if (type.isUninitialized()) {
            return throwSimException(i, "uninitialized instance");
        }
        if (type.isCategory2()) {
            return throwSimException(i, "category-2");
        }
        return typeBearer;
    }

    @Override // mod.agus.jcoderz.dx.cf.code.LocalsArray
    public TypeBearer getCategory2(int i) {
        TypeBearer typeBearer = get(i);
        if (typeBearer.getType().isCategory1()) {
            return throwSimException(i, "category-1");
        }
        return typeBearer;
    }

    @Override // mod.agus.jcoderz.dx.cf.code.LocalsArray
    public LocalsArray merge(LocalsArray localsArray) {
        if (localsArray instanceof OneLocalsArray) {
            return merge((OneLocalsArray) localsArray);
        }
        return localsArray.merge(this);
    }

    public OneLocalsArray merge(OneLocalsArray oneLocalsArray) {
        try {
            return Merger.mergeLocals(this, oneLocalsArray);
        } catch (SimException e) {
            e.addContext("underlay locals:");
            annotate(e);
            e.addContext("overlay locals:");
            oneLocalsArray.annotate(e);
            throw e;
        }
    }

    @Override // mod.agus.jcoderz.dx.cf.code.LocalsArray
    public LocalsArraySet mergeWithSubroutineCaller(LocalsArray localsArray, int i) {
        return new LocalsArraySet(getMaxLocals()).mergeWithSubroutineCaller(localsArray, i);
    }

    @Override // mod.agus.jcoderz.dx.cf.code.LocalsArray
    public OneLocalsArray getPrimary() {
        return this;
    }
}
