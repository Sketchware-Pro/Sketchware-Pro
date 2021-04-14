package mod.agus.jcoderz.dx.rop.type;

import io.github.rosemoe.editor.widget.CodeEditor;
import java.util.HashMap;
import org.eclipse.jdt.internal.compiler.util.Util;

public final class Prototype implements Comparable<Prototype> {
    private static final HashMap<String, Prototype> internTable = new HashMap<>((int) CodeEditor.DEFAULT_CURSOR_BLINK_PERIOD);
    private final String descriptor;
    private StdTypeList parameterFrameTypes;
    private final StdTypeList parameterTypes;
    private final Type returnType;

    public static Prototype intern(String str) {
        Prototype prototype;
        int i;
        if (str == null) {
            throw new NullPointerException("descriptor == null");
        }
        synchronized (internTable) {
            prototype = internTable.get(str);
        }
        if (prototype != null) {
            return prototype;
        }
        Type[] makeParameterArray = makeParameterArray(str);
        int i2 = 1;
        int i3 = 0;
        while (true) {
            char charAt = str.charAt(i2);
            if (charAt == ')') {
                Type internReturnType = Type.internReturnType(str.substring(i2 + 1));
                StdTypeList stdTypeList = new StdTypeList(i3);
                for (int i4 = 0; i4 < i3; i4++) {
                    stdTypeList.set(i4, makeParameterArray[i4]);
                }
                return putIntern(new Prototype(str, internReturnType, stdTypeList));
            }
            int i5 = i2;
            while (charAt == '[') {
                i5++;
                charAt = str.charAt(i5);
            }
            if (charAt == 'L') {
                int indexOf = str.indexOf(59, i5);
                if (indexOf == -1) {
                    throw new IllegalArgumentException("bad descriptor");
                }
                i = indexOf + 1;
            } else {
                i = i5 + 1;
            }
            makeParameterArray[i3] = Type.intern(str.substring(i2, i));
            i3++;
            i2 = i;
        }
    }

    private static Type[] makeParameterArray(String str) {
        int length = str.length();
        if (str.charAt(0) != '(') {
            throw new IllegalArgumentException("bad descriptor");
        }
        int i = 1;
        int i2 = 0;
        while (true) {
            if (i >= length) {
                i = 0;
                break;
            }
            char charAt = str.charAt(i);
            if (charAt == ')') {
                break;
            }
            if (charAt >= 'A' && charAt <= 'Z') {
                i2++;
            }
            i++;
        }
        if (i == 0 || i == length - 1) {
            throw new IllegalArgumentException("bad descriptor");
        } else if (str.indexOf(41, i + 1) == -1) {
            return new Type[i2];
        } else {
            throw new IllegalArgumentException("bad descriptor");
        }
    }

    public static Prototype intern(String str, Type type, boolean z, boolean z2) {
        Prototype intern = intern(str);
        if (z) {
            return intern;
        }
        if (z2) {
            type = type.asUninitialized(Integer.MAX_VALUE);
        }
        return intern.withFirstParameter(type);
    }

    public static Prototype internInts(Type type, int i) {
        StringBuffer stringBuffer = new StringBuffer(100);
        stringBuffer.append(Util.C_PARAM_START);
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append('I');
        }
        stringBuffer.append(Util.C_PARAM_END);
        stringBuffer.append(type.getDescriptor());
        return intern(stringBuffer.toString());
    }

    private Prototype(String str, Type type, StdTypeList stdTypeList) {
        if (str == null) {
            throw new NullPointerException("descriptor == null");
        } else if (type == null) {
            throw new NullPointerException("returnType == null");
        } else if (stdTypeList == null) {
            throw new NullPointerException("parameterTypes == null");
        } else {
            this.descriptor = str;
            this.returnType = type;
            this.parameterTypes = stdTypeList;
            this.parameterFrameTypes = null;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Prototype)) {
            return false;
        }
        return this.descriptor.equals(((Prototype) obj).descriptor);
    }

    public int hashCode() {
        return this.descriptor.hashCode();
    }

    public int compareTo(Prototype prototype) {
        if (this == prototype) {
            return 0;
        }
        int compareTo = this.returnType.compareTo(prototype.returnType);
        if (compareTo != 0) {
            return compareTo;
        }
        int size = this.parameterTypes.size();
        int size2 = prototype.parameterTypes.size();
        int min = Math.min(size, size2);
        for (int i = 0; i < min; i++) {
            int compareTo2 = this.parameterTypes.get(i).compareTo(prototype.parameterTypes.get(i));
            if (compareTo2 != 0) {
                return compareTo2;
            }
        }
        if (size < size2) {
            return -1;
        }
        if (size > size2) {
            return 1;
        }
        return 0;
    }

    public String toString() {
        return this.descriptor;
    }

    public String getDescriptor() {
        return this.descriptor;
    }

    public Type getReturnType() {
        return this.returnType;
    }

    public StdTypeList getParameterTypes() {
        return this.parameterTypes;
    }

    public StdTypeList getParameterFrameTypes() {
        if (this.parameterFrameTypes == null) {
            int size = this.parameterTypes.size();
            StdTypeList stdTypeList = new StdTypeList(size);
            boolean z = false;
            for (int i = 0; i < size; i++) {
                Type type = this.parameterTypes.get(i);
                if (type.isIntlike()) {
                    z = true;
                    type = Type.INT;
                }
                stdTypeList.set(i, type);
            }
            this.parameterFrameTypes = z ? stdTypeList : this.parameterTypes;
        }
        return this.parameterFrameTypes;
    }

    public Prototype withFirstParameter(Type type) {
        String str = "(" + type.getDescriptor() + this.descriptor.substring(1);
        StdTypeList withFirst = this.parameterTypes.withFirst(type);
        withFirst.setImmutable();
        return putIntern(new Prototype(str, this.returnType, withFirst));
    }

    private static Prototype putIntern(Prototype prototype) {
        synchronized (internTable) {
            String descriptor2 = prototype.getDescriptor();
            Prototype prototype2 = internTable.get(descriptor2);
            if (prototype2 != null) {
                return prototype2;
            }
            internTable.put(descriptor2, prototype);
            return prototype;
        }
    }
}
