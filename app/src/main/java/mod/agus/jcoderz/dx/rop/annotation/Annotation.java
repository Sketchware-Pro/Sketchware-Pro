package mod.agus.jcoderz.dx.rop.annotation;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.util.MutabilityControl;
import mod.agus.jcoderz.dx.util.ToHuman;

public final class Annotation extends MutabilityControl implements Comparable<Annotation>, ToHuman {
    private final TreeMap<CstString, NameValuePair> elements;
    private final CstType type;
    private final AnnotationVisibility visibility;

    public Annotation(CstType cstType, AnnotationVisibility annotationVisibility) {
        if (cstType == null) {
            throw new NullPointerException("type == null");
        } else if (annotationVisibility == null) {
            throw new NullPointerException("visibility == null");
        } else {
            this.type = cstType;
            this.visibility = annotationVisibility;
            this.elements = new TreeMap<>();
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Annotation)) {
            return false;
        }
        Annotation annotation = (Annotation) obj;
        if (!this.type.equals(annotation.type) || this.visibility != annotation.visibility) {
            return false;
        }
        return this.elements.equals(annotation.elements);
    }

    public int hashCode() {
        return (((this.type.hashCode() * 31) + this.elements.hashCode()) * 31) + this.visibility.hashCode();
    }

    public int compareTo(Annotation annotation) {
        int compareTo = this.type.compareTo((Constant) annotation.type);
        if (compareTo != 0) {
            return compareTo;
        }
        int compareTo2 = this.visibility.compareTo((AnnotationVisibility) annotation.visibility);
        if (compareTo2 != 0) {
            return compareTo2;
        }
        Iterator<NameValuePair> it = this.elements.values().iterator();
        Iterator<NameValuePair> it2 = annotation.elements.values().iterator();
        while (it.hasNext() && it2.hasNext()) {
            int compareTo3 = it.next().compareTo(it2.next());
            if (compareTo3 != 0) {
                return compareTo3;
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
        return toHuman();
    }

    @Override // mod.agus.jcoderz.dx.util.ToHuman
    public String toHuman() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.visibility.toHuman());
        sb.append("-annotation ");
        sb.append(this.type.toHuman());
        sb.append(" {");
        boolean z = true;
        for (NameValuePair nameValuePair : this.elements.values()) {
            if (z) {
                z = false;
            } else {
                sb.append(", ");
            }
            sb.append(nameValuePair.getName().toHuman());
            sb.append(": ");
            sb.append(nameValuePair.getValue().toHuman());
        }
        sb.append("}");
        return sb.toString();
    }

    public CstType getType() {
        return this.type;
    }

    public AnnotationVisibility getVisibility() {
        return this.visibility;
    }

    public void put(NameValuePair nameValuePair) {
        throwIfImmutable();
        if (nameValuePair == null) {
            throw new NullPointerException("pair == null");
        }
        this.elements.put(nameValuePair.getName(), nameValuePair);
    }

    public void add(NameValuePair nameValuePair) {
        throwIfImmutable();
        if (nameValuePair == null) {
            throw new NullPointerException("pair == null");
        }
        CstString name = nameValuePair.getName();
        if (this.elements.get(name) != null) {
            throw new IllegalArgumentException("name already added: " + name);
        }
        this.elements.put(name, nameValuePair);
    }

    public Collection<NameValuePair> getNameValuePairs() {
        return Collections.unmodifiableCollection(this.elements.values());
    }
}
