package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.annotation.Annotation;

public final class CstAnnotation extends Constant {
    private final Annotation annotation;

    public CstAnnotation(Annotation annotation2) {
        if (annotation2 == null) {
            throw new NullPointerException("annotation == null");
        }
        annotation2.throwIfMutable();
        this.annotation = annotation2;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CstAnnotation)) {
            return false;
        }
        return this.annotation.equals(((CstAnnotation) obj).annotation);
    }

    public int hashCode() {
        return this.annotation.hashCode();
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.Constant
    public int compareTo0(Constant constant) {
        return this.annotation.compareTo(((CstAnnotation) constant).annotation);
    }

    public String toString() {
        return this.annotation.toString();
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.Constant
    public String typeName() {
        return "annotation";
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.Constant
    public boolean isCategory2() {
        return false;
    }

    @Override // mod.agus.jcoderz.dx.util.ToHuman
    public String toHuman() {
        return this.annotation.toString();
    }

    public Annotation getAnnotation() {
        return this.annotation;
    }
}
