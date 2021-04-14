package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dex.util.ExceptionWithContext;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.IntList;

public final class Frame {
    private final LocalsArray locals;
    private final ExecutionStack stack;
    private final IntList subroutines;

    private Frame(LocalsArray localsArray, ExecutionStack executionStack) {
        this(localsArray, executionStack, IntList.EMPTY);
    }

    private Frame(LocalsArray localsArray, ExecutionStack executionStack, IntList intList) {
        if (localsArray == null) {
            throw new NullPointerException("locals == null");
        } else if (executionStack == null) {
            throw new NullPointerException("stack == null");
        } else {
            intList.throwIfMutable();
            this.locals = localsArray;
            this.stack = executionStack;
            this.subroutines = intList;
        }
    }

    public Frame(int i, int i2) {
        this(new OneLocalsArray(i), new ExecutionStack(i2));
    }

    public Frame copy() {
        return new Frame(this.locals.copy(), this.stack.copy(), this.subroutines);
    }

    public void setImmutable() {
        this.locals.setImmutable();
        this.stack.setImmutable();
    }

    public void makeInitialized(Type type) {
        this.locals.makeInitialized(type);
        this.stack.makeInitialized(type);
    }

    public LocalsArray getLocals() {
        return this.locals;
    }

    public ExecutionStack getStack() {
        return this.stack;
    }

    public IntList getSubroutines() {
        return this.subroutines;
    }

    public void initializeWithParameters(StdTypeList stdTypeList) {
        int size = stdTypeList.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            Type type = stdTypeList.get(i2);
            this.locals.set(i, type);
            i += type.getCategory();
        }
    }

    public Frame subFrameForLabel(int i, int i2) {
        LocalsArray localsArray;
        if (this.locals instanceof LocalsArraySet) {
            localsArray = ((LocalsArraySet) this.locals).subArrayForLabel(i2);
        } else {
            localsArray = null;
        }
        try {
            IntList mutableCopy = this.subroutines.mutableCopy();
            if (mutableCopy.pop() != i) {
                throw new RuntimeException("returning from invalid subroutine");
            }
            mutableCopy.setImmutable();
            if (localsArray == null) {
                return null;
            }
            return new Frame(localsArray, this.stack, mutableCopy);
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("returning from invalid subroutine");
        } catch (NullPointerException e2) {
            throw new NullPointerException("can't return from non-subroutine");
        }
    }

    public Frame mergeWith(Frame frame) {
        LocalsArray merge = getLocals().merge(frame.getLocals());
        ExecutionStack merge2 = getStack().merge(frame.getStack());
        IntList mergeSubroutineLists = mergeSubroutineLists(frame.subroutines);
        LocalsArray adjustLocalsForSubroutines = adjustLocalsForSubroutines(merge, mergeSubroutineLists);
        return (adjustLocalsForSubroutines == getLocals() && merge2 == getStack() && this.subroutines == mergeSubroutineLists) ? this : new Frame(adjustLocalsForSubroutines, merge2, mergeSubroutineLists);
    }

    private IntList mergeSubroutineLists(IntList intList) {
        if (this.subroutines.equals(intList)) {
            return this.subroutines;
        }
        IntList intList2 = new IntList();
        int size = this.subroutines.size();
        int size2 = intList.size();
        int i = 0;
        while (i < size && i < size2 && this.subroutines.get(i) == intList.get(i)) {
            intList2.add(i);
            i++;
        }
        intList2.setImmutable();
        return intList2;
    }

    private static LocalsArray adjustLocalsForSubroutines(LocalsArray localsArray, IntList intList) {
        if (!(localsArray instanceof LocalsArraySet)) {
            return localsArray;
        }
        LocalsArraySet localsArraySet = (LocalsArraySet) localsArray;
        if (intList.size() == 0) {
            return localsArraySet.getPrimary();
        }
        return localsArraySet;
    }

    public Frame mergeWithSubroutineCaller(Frame frame, int i, int i2) {
        IntList intList;
        LocalsArraySet mergeWithSubroutineCaller = getLocals().mergeWithSubroutineCaller(frame.getLocals(), i2);
        ExecutionStack merge = getStack().merge(frame.getStack());
        IntList mutableCopy = frame.subroutines.mutableCopy();
        mutableCopy.add(i);
        mutableCopy.setImmutable();
        if (mergeWithSubroutineCaller == getLocals() && merge == getStack() && this.subroutines.equals(mutableCopy)) {
            return this;
        }
        if (this.subroutines.equals(mutableCopy)) {
            intList = this.subroutines;
        } else {
            if (this.subroutines.size() > mutableCopy.size()) {
                intList = this.subroutines;
            } else {
                intList = mutableCopy;
                mutableCopy = this.subroutines;
            }
            int size = intList.size();
            int size2 = mutableCopy.size();
            for (int i3 = size2 - 1; i3 >= 0; i3--) {
                if (mutableCopy.get(i3) != intList.get((size - size2) + i3)) {
                    throw new RuntimeException("Incompatible merged subroutines");
                }
            }
        }
        return new Frame(mergeWithSubroutineCaller, merge, intList);
    }

    public Frame makeNewSubroutineStartFrame(int i, int i2) {
        this.subroutines.mutableCopy().add(i);
        return new Frame(this.locals.getPrimary(), this.stack, IntList.makeImmutable(i)).mergeWithSubroutineCaller(this, i, i2);
    }

    public Frame makeExceptionHandlerStartFrame(CstType cstType) {
        ExecutionStack copy = getStack().copy();
        copy.clear();
        copy.push(cstType);
        return new Frame(getLocals(), copy, this.subroutines);
    }

    public void annotate(ExceptionWithContext exceptionWithContext) {
        this.locals.annotate(exceptionWithContext);
        this.stack.annotate(exceptionWithContext);
    }
}
