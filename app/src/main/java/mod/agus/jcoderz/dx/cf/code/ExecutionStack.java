package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dex.util.ExceptionWithContext;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;
import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.MutabilityControl;

public final class ExecutionStack extends MutabilityControl {
    private final boolean[] local;
    private final TypeBearer[] stack;
    private int stackPtr;


    public ExecutionStack(int i) {
        super(true);
        boolean z;
        if (i != 0) {
            z = true;
        } else {
            z = false;
        }
        this.stack = new TypeBearer[i];
        this.local = new boolean[i];
        this.stackPtr = 0;
    }

    public ExecutionStack copy() {
        ExecutionStack executionStack = new ExecutionStack(this.stack.length);
        System.arraycopy(this.stack, 0, executionStack.stack, 0, this.stack.length);
        System.arraycopy(this.local, 0, executionStack.local, 0, this.local.length);
        executionStack.stackPtr = this.stackPtr;
        return executionStack;
    }

    public void annotate(ExceptionWithContext exceptionWithContext) {
        int i = this.stackPtr - 1;
        int i2 = 0;
        while (i2 <= i) {
            exceptionWithContext.addContext("stack[" + (i2 == i ? "top0" : Hex.u2(i - i2)) + "]: " + stackElementString(this.stack[i2]));
            i2++;
        }
    }

    public void makeInitialized(Type type) {
        if (this.stackPtr != 0) {
            throwIfImmutable();
            Type initializedType = type.getInitializedType();
            for (int i = 0; i < this.stackPtr; i++) {
                if (this.stack[i] == type) {
                    this.stack[i] = initializedType;
                }
            }
        }
    }

    public int getMaxStack() {
        return this.stack.length;
    }

    public int size() {
        return this.stackPtr;
    }

    public void clear() {
        throwIfImmutable();
        for (int i = 0; i < this.stackPtr; i++) {
            this.stack[i] = null;
            this.local[i] = false;
        }
        this.stackPtr = 0;
    }

    public void push(TypeBearer typeBearer) {
        throwIfImmutable();
        try {
            TypeBearer frameType = typeBearer.getFrameType();
            int category = frameType.getType().getCategory();
            if (this.stackPtr + category > this.stack.length) {
                throwSimException("overflow");
                return;
            }
            if (category == 2) {
                this.stack[this.stackPtr] = null;
                this.stackPtr++;
            }
            this.stack[this.stackPtr] = frameType;
            this.stackPtr++;
        } catch (NullPointerException e) {
            throw new NullPointerException("type == null");
        }
    }

    public void setLocal() {
        throwIfImmutable();
        this.local[this.stackPtr] = true;
    }

    public TypeBearer peek(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("n < 0");
        } else if (i >= this.stackPtr) {
            return throwSimException("underflow");
        } else {
            return this.stack[(this.stackPtr - i) - 1];
        }
    }

    public boolean peekLocal(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("n < 0");
        } else if (i < this.stackPtr) {
            return this.local[(this.stackPtr - i) - 1];
        } else {
            throw new SimException("stack: underflow");
        }
    }

    public Type peekType(int i) {
        return peek(i).getType();
    }

    public TypeBearer pop() {
        throwIfImmutable();
        TypeBearer peek = peek(0);
        this.stack[this.stackPtr - 1] = null;
        this.local[this.stackPtr - 1] = false;
        this.stackPtr -= peek.getType().getCategory();
        return peek;
    }

    public void change(int i, TypeBearer typeBearer) {
        throwIfImmutable();
        try {
            TypeBearer frameType = typeBearer.getFrameType();
            int i2 = (this.stackPtr - i) - 1;
            TypeBearer typeBearer2 = this.stack[i2];
            if (typeBearer2 == null || typeBearer2.getType().getCategory() != frameType.getType().getCategory()) {
                throwSimException("incompatible substitution: " + stackElementString(typeBearer2) + " -> " + stackElementString(frameType));
            }
            this.stack[i2] = frameType;
        } catch (NullPointerException e) {
            throw new NullPointerException("type == null");
        }
    }

    public ExecutionStack merge(ExecutionStack executionStack) {
        try {
            return Merger.mergeStack(this, executionStack);
        } catch (SimException e) {
            e.addContext("underlay stack:");
            annotate(e);
            e.addContext("overlay stack:");
            executionStack.annotate(e);
            throw e;
        }
    }

    private static String stackElementString(TypeBearer typeBearer) {
        if (typeBearer == null) {
            return "<invalid>";
        }
        return typeBearer.toString();
    }

    private static TypeBearer throwSimException(String str) {
        throw new SimException("stack: " + str);
    }
}
