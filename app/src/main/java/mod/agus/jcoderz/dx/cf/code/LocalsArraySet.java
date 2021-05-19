package mod.agus.jcoderz.dx.cf.code;

import org.eclipse.jdt.internal.compiler.util.Util;

import java.util.ArrayList;
import java.util.Iterator;

import mod.agus.jcoderz.dex.util.ExceptionWithContext;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;
import mod.agus.jcoderz.dx.util.Hex;

public class LocalsArraySet extends LocalsArray {
    private final OneLocalsArray primary;
    private final ArrayList<LocalsArray> secondaries;

    public LocalsArraySet(int i) {
        super(i != 0);
        this.primary = new OneLocalsArray(i);
        this.secondaries = new ArrayList<>();
    }

    public LocalsArraySet(OneLocalsArray oneLocalsArray, ArrayList<LocalsArray> arrayList) {
        super(oneLocalsArray.getMaxLocals() > 0);
        this.primary = oneLocalsArray;
        this.secondaries = arrayList;
    }

    private LocalsArraySet(LocalsArraySet localsArraySet) {
        super(localsArraySet.getMaxLocals() > 0);
        this.primary = localsArraySet.primary.copy();
        this.secondaries = new ArrayList<>(localsArraySet.secondaries.size());
        int size = localsArraySet.secondaries.size();
        for (int i = 0; i < size; i++) {
            LocalsArray localsArray = localsArraySet.secondaries.get(i);
            if (localsArray == null) {
                this.secondaries.add(null);
            } else {
                this.secondaries.add(localsArray.copy());
            }
        }
    }

    @Override // mod.agus.jcoderz.dx.util.MutabilityControl
    public void setImmutable() {
        this.primary.setImmutable();
        Iterator<LocalsArray> it = this.secondaries.iterator();
        while (it.hasNext()) {
            LocalsArray next = it.next();
            if (next != null) {
                next.setImmutable();
            }
        }
        super.setImmutable();
    }

    @Override // mod.agus.jcoderz.dx.cf.code.LocalsArray
    public LocalsArray copy() {
        return new LocalsArraySet(this);
    }

    @Override // mod.agus.jcoderz.dx.cf.code.LocalsArray
    public void annotate(ExceptionWithContext exceptionWithContext) {
        exceptionWithContext.addContext("(locals array set; primary)");
        this.primary.annotate(exceptionWithContext);
        int size = this.secondaries.size();
        for (int i = 0; i < size; i++) {
            LocalsArray localsArray = this.secondaries.get(i);
            if (localsArray != null) {
                exceptionWithContext.addContext("(locals array set: primary for caller " + Hex.u2(i) + Util.C_PARAM_END);
                localsArray.getPrimary().annotate(exceptionWithContext);
            }
        }
    }

    @Override // mod.agus.jcoderz.dx.util.ToHuman
    public String toHuman() {
        StringBuilder sb = new StringBuilder();
        sb.append("(locals array set; primary)\n");
        sb.append(getPrimary().toHuman());
        sb.append('\n');
        int size = this.secondaries.size();
        for (int i = 0; i < size; i++) {
            LocalsArray localsArray = this.secondaries.get(i);
            if (localsArray != null) {
                sb.append("(locals array set: primary for caller " + Hex.u2(i) + ")\n");
                sb.append(localsArray.getPrimary().toHuman());
                sb.append('\n');
            }
        }
        return sb.toString();
    }

    @Override // mod.agus.jcoderz.dx.cf.code.LocalsArray
    public void makeInitialized(Type type) {
        if (this.primary.getMaxLocals() != 0) {
            throwIfImmutable();
            this.primary.makeInitialized(type);
            Iterator<LocalsArray> it = this.secondaries.iterator();
            while (it.hasNext()) {
                LocalsArray next = it.next();
                if (next != null) {
                    next.makeInitialized(type);
                }
            }
        }
    }

    @Override // mod.agus.jcoderz.dx.cf.code.LocalsArray
    public int getMaxLocals() {
        return this.primary.getMaxLocals();
    }

    @Override // mod.agus.jcoderz.dx.cf.code.LocalsArray
    public void set(int i, TypeBearer typeBearer) {
        throwIfImmutable();
        this.primary.set(i, typeBearer);
        Iterator<LocalsArray> it = this.secondaries.iterator();
        while (it.hasNext()) {
            LocalsArray next = it.next();
            if (next != null) {
                next.set(i, typeBearer);
            }
        }
    }

    @Override // mod.agus.jcoderz.dx.cf.code.LocalsArray
    public void set(RegisterSpec registerSpec) {
        set(registerSpec.getReg(), registerSpec);
    }

    @Override // mod.agus.jcoderz.dx.cf.code.LocalsArray
    public void invalidate(int i) {
        throwIfImmutable();
        this.primary.invalidate(i);
        Iterator<LocalsArray> it = this.secondaries.iterator();
        while (it.hasNext()) {
            LocalsArray next = it.next();
            if (next != null) {
                next.invalidate(i);
            }
        }
    }

    @Override // mod.agus.jcoderz.dx.cf.code.LocalsArray
    public TypeBearer getOrNull(int i) {
        return this.primary.getOrNull(i);
    }

    @Override // mod.agus.jcoderz.dx.cf.code.LocalsArray
    public TypeBearer get(int i) {
        return this.primary.get(i);
    }

    @Override // mod.agus.jcoderz.dx.cf.code.LocalsArray
    public TypeBearer getCategory1(int i) {
        return this.primary.getCategory1(i);
    }

    @Override // mod.agus.jcoderz.dx.cf.code.LocalsArray
    public TypeBearer getCategory2(int i) {
        return this.primary.getCategory2(i);
    }

    private LocalsArraySet mergeWithSet(LocalsArraySet localsArraySet) {
        LocalsArray localsArray;
        LocalsArray localsArray2;
        LocalsArray localsArray3;
        OneLocalsArray merge = this.primary.merge(localsArraySet.getPrimary());
        int size = this.secondaries.size();
        int size2 = localsArraySet.secondaries.size();
        int max = Math.max(size, size2);
        ArrayList arrayList = new ArrayList(max);
        int i = 0;
        boolean z = false;
        while (i < max) {
            if (i < size) {
                localsArray = this.secondaries.get(i);
            } else {
                localsArray = null;
            }
            if (i < size2) {
                localsArray2 = localsArraySet.secondaries.get(i);
            } else {
                localsArray2 = null;
            }
            if (localsArray == localsArray2) {
                localsArray3 = localsArray;
            } else if (localsArray == null) {
                localsArray3 = localsArray2;
            } else if (localsArray2 == null) {
                localsArray3 = localsArray;
            } else {
                try {
                    localsArray3 = localsArray.merge(localsArray2);
                } catch (SimException e) {
                    e.addContext("Merging locals set for caller block " + Hex.u2(i));
                    localsArray3 = null;
                }
            }
            boolean z2 = z || localsArray != localsArray3;
            arrayList.add(localsArray3);
            i++;
            z = z2;
        }
        return (this.primary != merge || z) ? new LocalsArraySet(merge, arrayList) : this;
    }

    private LocalsArraySet mergeWithOne(OneLocalsArray oneLocalsArray) {
        OneLocalsArray merge = this.primary.merge(oneLocalsArray.getPrimary());
        ArrayList arrayList = new ArrayList(this.secondaries.size());
        int size = this.secondaries.size();
        int i = 0;
        boolean z = false;
        while (i < size) {
            LocalsArray localsArray = this.secondaries.get(i);
            LocalsArray localsArray2 = null;
            if (localsArray != null) {
                try {
                    localsArray2 = localsArray.merge(oneLocalsArray);
                } catch (SimException e) {
                    e.addContext("Merging one locals against caller block " + Hex.u2(i));
                }
            }
            boolean z2 = z || localsArray != localsArray2;
            arrayList.add(localsArray2);
            i++;
            z = z2;
        }
        return (this.primary != merge || z) ? new LocalsArraySet(merge, arrayList) : this;
    }

    @Override // mod.agus.jcoderz.dx.cf.code.LocalsArray
    public LocalsArraySet merge(LocalsArray localsArray) {
        LocalsArraySet mergeWithOne;
        try {
            if (localsArray instanceof LocalsArraySet) {
                mergeWithOne = mergeWithSet((LocalsArraySet) localsArray);
            } else {
                mergeWithOne = mergeWithOne((OneLocalsArray) localsArray);
            }
            mergeWithOne.setImmutable();
            return mergeWithOne;
        } catch (SimException e) {
            e.addContext("underlay locals:");
            annotate(e);
            e.addContext("overlay locals:");
            localsArray.annotate(e);
            throw e;
        }
    }

    private LocalsArray getSecondaryForLabel(int i) {
        if (i >= this.secondaries.size()) {
            return null;
        }
        return this.secondaries.get(i);
    }

    @Override // mod.agus.jcoderz.dx.cf.code.LocalsArray
    public LocalsArraySet mergeWithSubroutineCaller(LocalsArray localsArray, int i) {
        LocalsArray merge;
        LocalsArray localsArray2;
        OneLocalsArray oneLocalsArray;
        LocalsArray secondaryForLabel = getSecondaryForLabel(i);
        OneLocalsArray merge2 = this.primary.merge(localsArray.getPrimary());
        if (secondaryForLabel == localsArray) {
            merge = secondaryForLabel;
        } else if (secondaryForLabel == null) {
            merge = localsArray;
        } else {
            merge = secondaryForLabel.merge(localsArray);
        }
        if (merge == secondaryForLabel && merge2 == this.primary) {
            return this;
        }
        int size = this.secondaries.size();
        int max = Math.max(i + 1, size);
        ArrayList arrayList = new ArrayList(max);
        int i2 = 0;
        OneLocalsArray oneLocalsArray2 = null;
        while (i2 < max) {
            if (i2 == i) {
                localsArray2 = merge;
            } else if (i2 < size) {
                localsArray2 = this.secondaries.get(i2);
            } else {
                localsArray2 = null;
            }
            if (localsArray2 != null) {
                oneLocalsArray = oneLocalsArray2 == null ? localsArray2.getPrimary() : oneLocalsArray2.merge(localsArray2.getPrimary());
            } else {
                oneLocalsArray = oneLocalsArray2;
            }
            arrayList.add(localsArray2);
            i2++;
            oneLocalsArray2 = oneLocalsArray;
        }
        LocalsArraySet localsArraySet = new LocalsArraySet(oneLocalsArray2, arrayList);
        localsArraySet.setImmutable();
        return localsArraySet;
    }

    public LocalsArray subArrayForLabel(int i) {
        return getSecondaryForLabel(i);
    }

    @Override // mod.agus.jcoderz.dx.cf.code.LocalsArray
    public OneLocalsArray getPrimary() {
        return this.primary;
    }
}
