package mod.agus.jcoderz.dx.command.grep;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import mod.agus.jcoderz.dex.ClassData;
import mod.agus.jcoderz.dex.ClassDef;
import mod.agus.jcoderz.dex.Dex;
import mod.agus.jcoderz.dex.EncodedValueReader;
import mod.agus.jcoderz.dx.io.CodeReader;
import mod.agus.jcoderz.dx.io.instructions.DecodedInstruction;

public final class Grep {
    private final CodeReader codeReader = new CodeReader();
    private int count = 0;
    private ClassDef currentClass;
    private ClassData.Method currentMethod;
    private final Dex dex;
    private final PrintWriter out;
    private final Set<Integer> stringIds;

    public Grep(Dex dex2, Pattern pattern, PrintWriter printWriter) {
        this.dex = dex2;
        this.out = printWriter;
        this.stringIds = getStringIds(dex2, pattern);
        this.codeReader.setStringVisitor(new CodeReader.Visitor() {

            @Override // mod.agus.jcoderz.dx.io.CodeReader.Visitor
            public void visit(DecodedInstruction[] decodedInstructionArr, DecodedInstruction decodedInstruction) {
                Grep.this.encounterString(decodedInstruction.getIndex());
            }
        });
    }

    private void readArray(EncodedValueReader encodedValueReader) {
        int readArray = encodedValueReader.readArray();
        for (int i = 0; i < readArray; i++) {
            switch (encodedValueReader.peek()) {
                case 23:
                    encounterString(encodedValueReader.readString());
                    break;
                case 28:
                    readArray(encodedValueReader);
                    break;
            }
        }
    }

    private void encounterString(int i) {
        if (this.stringIds.contains(Integer.valueOf(i))) {
            this.out.println(String.valueOf(location()) + " " + this.dex.strings().get(i));
            this.count++;
        }
    }

    private String location() {
        String str = this.dex.typeNames().get(this.currentClass.getTypeIndex());
        if (this.currentMethod != null) {
            return String.valueOf(str) + "." + this.dex.strings().get(this.dex.methodIds().get(this.currentMethod.getMethodIndex()).getNameIndex());
        }
        return str;
    }

    public int grep() {
        for (ClassDef classDef : this.dex.classDefs()) {
            this.currentClass = classDef;
            this.currentMethod = null;
            if (classDef.getClassDataOffset() != 0) {
                ClassData readClassData = this.dex.readClassData(classDef);
                int staticValuesOffset = classDef.getStaticValuesOffset();
                if (staticValuesOffset != 0) {
                    readArray(new EncodedValueReader(this.dex.open(staticValuesOffset)));
                }
                ClassData.Method[] allMethods = readClassData.allMethods();
                for (ClassData.Method method : allMethods) {
                    this.currentMethod = method;
                    if (method.getCodeOffset() != 0) {
                        this.codeReader.visitAll(this.dex.readCode(method).getInstructions());
                    }
                }
            }
        }
        this.currentClass = null;
        this.currentMethod = null;
        return this.count;
    }

    private Set<Integer> getStringIds(Dex dex2, Pattern pattern) {
        HashSet hashSet = new HashSet();
        int i = 0;
        for (String str : dex2.strings()) {
            if (pattern.matcher(str).find()) {
                hashSet.add(Integer.valueOf(i));
            }
            i++;
        }
        return hashSet;
    }
}
