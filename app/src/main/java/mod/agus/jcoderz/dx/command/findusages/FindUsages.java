package mod.agus.jcoderz.dx.command.findusages;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import mod.agus.jcoderz.dex.ClassData;
import mod.agus.jcoderz.dex.ClassDef;
import mod.agus.jcoderz.dex.Dex;
import mod.agus.jcoderz.dex.FieldId;
import mod.agus.jcoderz.dex.MethodId;
import mod.agus.jcoderz.dx.io.CodeReader;
import mod.agus.jcoderz.dx.io.OpcodeInfo;
import mod.agus.jcoderz.dx.io.instructions.DecodedInstruction;

public final class FindUsages {
    private final CodeReader codeReader = new CodeReader();
    private ClassDef currentClass;
    private ClassData.Method currentMethod;
    private final Dex dex;
    private final Set<Integer> fieldIds;
    private final Set<Integer> methodIds;
    private final PrintWriter out;

    public FindUsages(final Dex dex2, String str, String str2, final PrintWriter printWriter) {
        this.dex = dex2;
        this.out = printWriter;
        HashSet<Integer> hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        Pattern compile = Pattern.compile(str);
        Pattern compile2 = Pattern.compile(str2);
        List<String> strings = dex2.strings();
        for (int i = 0; i < strings.size(); i++) {
            String str3 = strings.get(i);
            if (compile.matcher(str3).matches()) {
                hashSet.add(Integer.valueOf(i));
            }
            if (compile2.matcher(str3).matches()) {
                hashSet2.add(Integer.valueOf(i));
            }
        }
        if (hashSet.isEmpty() || hashSet2.isEmpty()) {
            this.fieldIds = null;
            this.methodIds = null;
            return;
        }
        this.methodIds = new HashSet();
        this.fieldIds = new HashSet();
        for (Integer num : hashSet) {
            int binarySearch = Collections.binarySearch(dex2.typeIds(), Integer.valueOf(num.intValue()));
            if (binarySearch >= 0) {
                this.methodIds.addAll(getMethodIds(dex2, hashSet2, binarySearch));
                this.fieldIds.addAll(getFieldIds(dex2, hashSet2, binarySearch));
            }
        }
        this.codeReader.setFieldVisitor(new CodeReader.Visitor() {

            @Override // mod.agus.jcoderz.dx.io.CodeReader.Visitor
            public void visit(DecodedInstruction[] decodedInstructionArr, DecodedInstruction decodedInstruction) {
                int index = decodedInstruction.getIndex();
                if (FindUsages.this.fieldIds.contains(Integer.valueOf(index))) {
                    printWriter.println(String.valueOf(FindUsages.this.location()) + ": field reference " + dex2.fieldIds().get(index) + " (" + OpcodeInfo.getName(decodedInstruction.getOpcode()) + ")");
                }
            }
        });
        this.codeReader.setMethodVisitor(new CodeReader.Visitor() {

            @Override // mod.agus.jcoderz.dx.io.CodeReader.Visitor
            public void visit(DecodedInstruction[] decodedInstructionArr, DecodedInstruction decodedInstruction) {
                int index = decodedInstruction.getIndex();
                if (FindUsages.this.methodIds.contains(Integer.valueOf(index))) {
                    printWriter.println(String.valueOf(FindUsages.this.location()) + ": method reference " + dex2.methodIds().get(index) + " (" + OpcodeInfo.getName(decodedInstruction.getOpcode()) + ")");
                }
            }
        });
    }

    private String location() {
        String str = this.dex.typeNames().get(this.currentClass.getTypeIndex());
        if (this.currentMethod != null) {
            return String.valueOf(str) + "." + this.dex.strings().get(this.dex.methodIds().get(this.currentMethod.getMethodIndex()).getNameIndex());
        }
        return str;
    }

    public void findUsages() {
        if (!(this.fieldIds == null || this.methodIds == null)) {
            for (ClassDef classDef : this.dex.classDefs()) {
                this.currentClass = classDef;
                this.currentMethod = null;
                if (classDef.getClassDataOffset() != 0) {
                    ClassData readClassData = this.dex.readClassData(classDef);
                    for (ClassData.Field field : readClassData.allFields()) {
                        int fieldIndex = field.getFieldIndex();
                        if (this.fieldIds.contains(Integer.valueOf(fieldIndex))) {
                            this.out.println(String.valueOf(location()) + " field declared " + this.dex.fieldIds().get(fieldIndex));
                        }
                    }
                    ClassData.Method[] allMethods = readClassData.allMethods();
                    for (ClassData.Method method : allMethods) {
                        this.currentMethod = method;
                        int methodIndex = method.getMethodIndex();
                        if (this.methodIds.contains(Integer.valueOf(methodIndex))) {
                            this.out.println(String.valueOf(location()) + " method declared " + this.dex.methodIds().get(methodIndex));
                        }
                        if (method.getCodeOffset() != 0) {
                            this.codeReader.visitAll(this.dex.readCode(method).getInstructions());
                        }
                    }
                }
            }
            this.currentClass = null;
            this.currentMethod = null;
        }
    }

    private Set<Integer> getFieldIds(Dex dex2, Set<Integer> set, int i) {
        HashSet hashSet = new HashSet();
        int i2 = 0;
        for (FieldId fieldId : dex2.fieldIds()) {
            if (set.contains(Integer.valueOf(fieldId.getNameIndex())) && i == fieldId.getDeclaringClassIndex()) {
                hashSet.add(Integer.valueOf(i2));
            }
            i2++;
        }
        return hashSet;
    }

    private Set<Integer> getMethodIds(Dex dex2, Set<Integer> set, int i) {
        Set<Integer> findAssignableTypes = findAssignableTypes(dex2, i);
        HashSet hashSet = new HashSet();
        int i2 = 0;
        for (MethodId methodId : dex2.methodIds()) {
            if (set.contains(Integer.valueOf(methodId.getNameIndex())) && findAssignableTypes.contains(Integer.valueOf(methodId.getDeclaringClassIndex()))) {
                hashSet.add(Integer.valueOf(i2));
            }
            i2++;
        }
        return hashSet;
    }

    private Set<Integer> findAssignableTypes(Dex dex2, int i) {
        HashSet hashSet = new HashSet();
        hashSet.add(Integer.valueOf(i));
        for (ClassDef classDef : dex2.classDefs()) {
            if (hashSet.contains(Integer.valueOf(classDef.getSupertypeIndex()))) {
                hashSet.add(Integer.valueOf(classDef.getTypeIndex()));
            } else {
                short[] interfaces = classDef.getInterfaces();
                int length = interfaces.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    } else if (hashSet.contains(Integer.valueOf(interfaces[i2]))) {
                        hashSet.add(Integer.valueOf(classDef.getTypeIndex()));
                        break;
                    } else {
                        i2++;
                    }
                }
            }
        }
        return hashSet;
    }
}
