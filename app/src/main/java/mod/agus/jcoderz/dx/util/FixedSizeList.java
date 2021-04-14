package mod.agus.jcoderz.dx.util;

import java.util.Arrays;

public class FixedSizeList extends MutabilityControl implements ToHuman {
    private Object[] arr;

    public FixedSizeList(int i) {
        super(i != 0);
        try {
            this.arr = new Object[i];
        } catch (NegativeArraySizeException e) {
            throw new IllegalArgumentException("size < 0");
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Arrays.equals(this.arr, ((FixedSizeList) obj).arr);
    }

    public int hashCode() {
        return Arrays.hashCode(this.arr);
    }

    public String toString() {
        String name = getClass().getName();
        return toString0(String.valueOf(name.substring(name.lastIndexOf(46) + 1)) + '{', ", ", "}", false);
    }

    @Override // mod.agus.jcoderz.dx.util.ToHuman
    public String toHuman() {
        String name = getClass().getName();
        return toString0(String.valueOf(name.substring(name.lastIndexOf(46) + 1)) + '{', ", ", "}", true);
    }

    public String toString(String str, String str2, String str3) {
        return toString0(str, str2, str3, false);
    }

    public String toHuman(String str, String str2, String str3) {
        return toString0(str, str2, str3, true);
    }

    public final int size() {
        return this.arr.length;
    }

    public void shrinkToFit() {
        int i = 0;
        int length = this.arr.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            if (this.arr[i3] != null) {
                i2++;
            }
        }
        if (length != i2) {
            throwIfImmutable();
            Object[] objArr = new Object[i2];
            for (int i4 = 0; i4 < length; i4++) {
                Object obj = this.arr[i4];
                if (obj != null) {
                    objArr[i] = obj;
                    i++;
                }
            }
            this.arr = objArr;
            if (i2 == 0) {
                setImmutable();
            }
        }
    }

    /* access modifiers changed from: protected */
    public final Object get0(int i) {
        try {
            Object obj = this.arr[i];
            if (obj != null) {
                return obj;
            }
            throw new NullPointerException("unset: " + i);
        } catch (ArrayIndexOutOfBoundsException e) {
            return throwIndex(i);
        }
    }

    public final Object getOrNull0(int i) {
        return this.arr[i];
    }

    public final void set0(int i, Object obj) {
        throwIfImmutable();
        try {
            this.arr[i] = obj;
        } catch (ArrayIndexOutOfBoundsException e) {
            throwIndex(i);
        }
    }

    private Object throwIndex(int i) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("n < 0");
        }
        throw new IndexOutOfBoundsException("n >= size()");
    }

    private String toString0(String str, String str2, String str3, boolean z) {
        int length = this.arr.length;
        StringBuffer stringBuffer = new StringBuffer((length * 10) + 10);
        if (str != null) {
            stringBuffer.append(str);
        }
        for (int i = 0; i < length; i++) {
            if (!(i == 0 || str2 == null)) {
                stringBuffer.append(str2);
            }
            if (z) {
                stringBuffer.append(((ToHuman) this.arr[i]).toHuman());
            } else {
                stringBuffer.append(this.arr[i]);
            }
        }
        if (str3 != null) {
            stringBuffer.append(str3);
        }
        return stringBuffer.toString();
    }
}
