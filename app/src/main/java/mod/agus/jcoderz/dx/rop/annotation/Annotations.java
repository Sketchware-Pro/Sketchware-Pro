package mod.agus.jcoderz.dx.rop.annotation;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;

import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.util.MutabilityControl;

public final class Annotations extends MutabilityControl implements Comparable<Annotations> {
    public static final Annotations EMPTY = new Annotations();

    static {
        EMPTY.setImmutable();
    }

    private final TreeMap<CstType, Annotation> annotations = new TreeMap<>();

    public static Annotations combine(Annotations annotations2, Annotations annotations3) {
        Annotations annotations4 = new Annotations();
        annotations4.addAll(annotations2);
        annotations4.addAll(annotations3);
        annotations4.setImmutable();
        return annotations4;
    }

    public static Annotations combine(Annotations annotations2, Annotation annotation) {
        Annotations annotations3 = new Annotations();
        annotations3.addAll(annotations2);
        annotations3.add(annotation);
        annotations3.setImmutable();
        return annotations3;
    }

    public int hashCode() {
        return this.annotations.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Annotations)) {
            return false;
        }
        return this.annotations.equals(((Annotations) obj).annotations);
    }

    public int compareTo(Annotations annotations2) {
        Iterator<Annotation> it = this.annotations.values().iterator();
        Iterator<Annotation> it2 = annotations2.annotations.values().iterator();
        while (it.hasNext() && it2.hasNext()) {
            int compareTo = it.next().compareTo(it2.next());
            if (compareTo != 0) {
                return compareTo;
            }
        }
        if (it.hasNext()) {
            return 1;
        }
        if (it2.hasNext()) {
            return -1;
        }
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("annotations{");
        boolean z = true;
        for (Annotation annotation : this.annotations.values()) {
            if (z) {
                z = false;
            } else {
                sb.append(", ");
            }
            sb.append(annotation.toHuman());
        }
        sb.append("}");
        return sb.toString();
    }

    public int size() {
        return this.annotations.size();
    }

    public void add(Annotation annotation) {
        throwIfImmutable();
        if (annotation == null) {
            throw new NullPointerException("annotation == null");
        }
        CstType type = annotation.getType();
        if (this.annotations.containsKey(type)) {
            throw new IllegalArgumentException("duplicate type: " + type.toHuman());
        }
        this.annotations.put(type, annotation);
    }

    public void addAll(Annotations annotations2) {
        throwIfImmutable();
        if (annotations2 == null) {
            throw new NullPointerException("toAdd == null");
        }
        for (Annotation annotation : annotations2.annotations.values()) {
            add(annotation);
        }
    }

    public Collection<Annotation> getAnnotations() {
        return Collections.unmodifiableCollection(this.annotations.values());
    }
}
