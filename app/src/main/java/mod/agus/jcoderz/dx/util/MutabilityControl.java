package mod.agus.jcoderz.dx.util;

public class MutabilityControl {
    private boolean mutable;

    public MutabilityControl() {
        this.mutable = true;
    }

    public MutabilityControl(boolean z) {
        this.mutable = z;
    }

    public void setImmutable() {
        this.mutable = false;
    }

    public final boolean isImmutable() {
        return !this.mutable;
    }

    public final boolean isMutable() {
        return this.mutable;
    }

    public final void throwIfImmutable() {
        if (!this.mutable) {
            throw new MutabilityException("immutable instance");
        }
    }

    public final void throwIfMutable() {
        if (this.mutable) {
            throw new MutabilityException("mutable instance");
        }
    }
}
