package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dx.util.IntList;
import mod.agus.jcoderz.dx.util.MutabilityControl;

public final class SwitchList extends MutabilityControl {
    private int size;
    private final IntList targets;
    private final IntList values;

    public SwitchList(int i) {
        super(true);
        this.values = new IntList(i);
        this.targets = new IntList(i + 1);
        this.size = i;
    }

    @Override // mod.agus.jcoderz.dx.util.MutabilityControl
    public void setImmutable() {
        this.values.setImmutable();
        this.targets.setImmutable();
        super.setImmutable();
    }

    public int size() {
        return this.size;
    }

    public int getValue(int i) {
        return this.values.get(i);
    }

    public int getTarget(int i) {
        return this.targets.get(i);
    }

    public int getDefaultTarget() {
        return this.targets.get(this.size);
    }

    public IntList getTargets() {
        return this.targets;
    }

    public IntList getValues() {
        return this.values;
    }

    public void setDefaultTarget(int i) {
        throwIfImmutable();
        if (i < 0) {
            throw new IllegalArgumentException("target < 0");
        } else if (this.targets.size() != this.size) {
            throw new RuntimeException("non-default elements not all set");
        } else {
            this.targets.add(i);
        }
    }

    public void add(int i, int i2) {
        throwIfImmutable();
        if (i2 < 0) {
            throw new IllegalArgumentException("target < 0");
        }
        this.values.add(i);
        this.targets.add(i2);
    }

    public void removeSuperfluousDefaults() {
        int i = 0;
        throwIfImmutable();
        int i2 = this.size;
        if (i2 != this.targets.size() - 1) {
            throw new IllegalArgumentException("incomplete instance");
        }
        int i3 = this.targets.get(i2);
        for (int i4 = 0; i4 < i2; i4++) {
            int i5 = this.targets.get(i4);
            if (i5 != i3) {
                if (i4 != i) {
                    this.targets.set(i, i5);
                    this.values.set(i, this.values.get(i4));
                }
                i++;
            }
        }
        if (i != i2) {
            this.values.shrink(i);
            this.targets.set(i, i3);
            this.targets.shrink(i + 1);
            this.size = i;
        }
    }
}
