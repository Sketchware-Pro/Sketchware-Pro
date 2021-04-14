package mod.agus.jcoderz.dex;

public final class ClassData {
    private final Method[] directMethods;
    private final Field[] instanceFields;
    private final Field[] staticFields;
    private final Method[] virtualMethods;

    public ClassData(Field[] fieldArr, Field[] fieldArr2, Method[] methodArr, Method[] methodArr2) {
        this.staticFields = fieldArr;
        this.instanceFields = fieldArr2;
        this.directMethods = methodArr;
        this.virtualMethods = methodArr2;
    }

    public Field[] getStaticFields() {
        return this.staticFields;
    }

    public Field[] getInstanceFields() {
        return this.instanceFields;
    }

    public Method[] getDirectMethods() {
        return this.directMethods;
    }

    public Method[] getVirtualMethods() {
        return this.virtualMethods;
    }

    public Field[] allFields() {
        Field[] fieldArr = new Field[(this.staticFields.length + this.instanceFields.length)];
        System.arraycopy(this.staticFields, 0, fieldArr, 0, this.staticFields.length);
        System.arraycopy(this.instanceFields, 0, fieldArr, this.staticFields.length, this.instanceFields.length);
        return fieldArr;
    }

    public Method[] allMethods() {
        Method[] methodArr = new Method[(this.directMethods.length + this.virtualMethods.length)];
        System.arraycopy(this.directMethods, 0, methodArr, 0, this.directMethods.length);
        System.arraycopy(this.virtualMethods, 0, methodArr, this.directMethods.length, this.virtualMethods.length);
        return methodArr;
    }

    public static class Field {
        private final int accessFlags;
        private final int fieldIndex;

        public Field(int i, int i2) {
            this.fieldIndex = i;
            this.accessFlags = i2;
        }

        public int getFieldIndex() {
            return this.fieldIndex;
        }

        public int getAccessFlags() {
            return this.accessFlags;
        }
    }

    public static class Method {
        private final int accessFlags;
        private final int codeOffset;
        private final int methodIndex;

        public Method(int i, int i2, int i3) {
            this.methodIndex = i;
            this.accessFlags = i2;
            this.codeOffset = i3;
        }

        public int getMethodIndex() {
            return this.methodIndex;
        }

        public int getAccessFlags() {
            return this.accessFlags;
        }

        public int getCodeOffset() {
            return this.codeOffset;
        }
    }
}
