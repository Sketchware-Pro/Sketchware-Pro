package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.util.MutabilityControl;

public final class RegisterSpecSet extends MutabilityControl {
    public static final RegisterSpecSet EMPTY = new RegisterSpecSet(0);
    private int size;
    private final RegisterSpec[] specs;

    public RegisterSpecSet(int i) {
        super(true);
        boolean z;
        if (i != 0) {
            z = true;
        } else {
            z = false;
        }
        this.specs = new RegisterSpec[i];
        this.size = 0;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof RegisterSpecSet)) {
            return false;
        }
        RegisterSpecSet registerSpecSet = (RegisterSpecSet) obj;
        RegisterSpec[] registerSpecArr = registerSpecSet.specs;
        int length = this.specs.length;
        if (!(length == registerSpecArr.length && size() == registerSpecSet.size())) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            RegisterSpec registerSpec = this.specs[i];
            RegisterSpec registerSpec2 = registerSpecArr[i];
            if (registerSpec != registerSpec2 && (registerSpec == null || !registerSpec.equals(registerSpec2))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int length = this.specs.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            RegisterSpec registerSpec = this.specs[i2];
            i = (i * 31) + (registerSpec == null ? 0 : registerSpec.hashCode());
        }
        return i;
    }

    public String toString() {
        boolean z = false;
        int length = this.specs.length;
        StringBuffer stringBuffer = new StringBuffer(length * 25);
        stringBuffer.append('{');
        for (int i = 0; i < length; i++) {
            RegisterSpec registerSpec = this.specs[i];
            if (registerSpec != null) {
                if (z) {
                    stringBuffer.append(", ");
                } else {
                    z = true;
                }
                stringBuffer.append(registerSpec);
            }
        }
        stringBuffer.append('}');
        return stringBuffer.toString();
    }

    public int getMaxSize() {
        return this.specs.length;
    }

    public int size() {
        int i = this.size;
        if (i < 0) {
            int length = this.specs.length;
            i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                if (this.specs[i2] != null) {
                    i++;
                }
            }
            this.size = i;
        }
        return i;
    }

    public RegisterSpec get(int i) {
        try {
            return this.specs[i];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("bogus reg");
        }
    }

    public RegisterSpec get(RegisterSpec registerSpec) {
        return get(registerSpec.getReg());
    }

    public RegisterSpec findMatchingLocal(RegisterSpec registerSpec) {
        int length = this.specs.length;
        for (int i = 0; i < length; i++) {
            RegisterSpec registerSpec2 = this.specs[i];
            if (registerSpec2 != null && registerSpec.matchesVariable(registerSpec2)) {
                return registerSpec2;
            }
        }
        return null;
    }

    public RegisterSpec localItemToSpec(LocalItem localItem) {
        int length = this.specs.length;
        for (int i = 0; i < length; i++) {
            RegisterSpec registerSpec = this.specs[i];
            if (registerSpec != null && localItem.equals(registerSpec.getLocalItem())) {
                return registerSpec;
            }
        }
        return null;
    }

    public void remove(RegisterSpec registerSpec) {
        try {
            this.specs[registerSpec.getReg()] = null;
            this.size = -1;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("bogus reg");
        }
    }

    public void put(RegisterSpec registerSpec) {
        int i = 0;
        RegisterSpec registerSpec2;
        throwIfImmutable();
        if (registerSpec == null) {
            throw new NullPointerException("spec == null");
        }
        this.size = -1;
        try {
            int reg = registerSpec.getReg();
            this.specs[reg] = registerSpec;
            if (reg > 0 && (registerSpec2 = this.specs[reg - 1]) != null && registerSpec2.getCategory() == 2) {
                this.specs[i] = null;
            }
            if (registerSpec.getCategory() == 2) {
                this.specs[reg + 1] = null;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("spec.getReg() out of range");
        }
    }

    public void putAll(RegisterSpecSet registerSpecSet) {
        int maxSize = registerSpecSet.getMaxSize();
        for (int i = 0; i < maxSize; i++) {
            RegisterSpec registerSpec = registerSpecSet.get(i);
            if (registerSpec != null) {
                put(registerSpec);
            }
        }
    }

    public void intersect(RegisterSpecSet registerSpecSet, boolean z) {
        RegisterSpec intersect;
        throwIfImmutable();
        RegisterSpec[] registerSpecArr = registerSpecSet.specs;
        int length = this.specs.length;
        int min = Math.min(length, registerSpecArr.length);
        this.size = -1;
        for (int i = 0; i < min; i++) {
            RegisterSpec registerSpec = this.specs[i];
            if (!(registerSpec == null || (intersect = registerSpec.intersect(registerSpecArr[i], z)) == registerSpec)) {
                this.specs[i] = intersect;
            }
        }
        while (min < length) {
            this.specs[min] = null;
            min++;
        }
    }

    public RegisterSpecSet withOffset(int i) {
        int length = this.specs.length;
        RegisterSpecSet registerSpecSet = new RegisterSpecSet(length + i);
        for (int i2 = 0; i2 < length; i2++) {
            RegisterSpec registerSpec = this.specs[i2];
            if (registerSpec != null) {
                registerSpecSet.put(registerSpec.withOffset(i));
            }
        }
        registerSpecSet.size = this.size;
        if (isImmutable()) {
            registerSpecSet.setImmutable();
        }
        return registerSpecSet;
    }

    public RegisterSpecSet mutableCopy() {
        int length = this.specs.length;
        RegisterSpecSet registerSpecSet = new RegisterSpecSet(length);
        for (int i = 0; i < length; i++) {
            RegisterSpec registerSpec = this.specs[i];
            if (registerSpec != null) {
                registerSpecSet.put(registerSpec);
            }
        }
        registerSpecSet.size = this.size;
        return registerSpecSet;
    }
}
