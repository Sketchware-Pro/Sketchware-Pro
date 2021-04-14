package mod.agus.jcoderz.dx.dex.file;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstArray;
import mod.agus.jcoderz.dx.rop.cst.CstLiteralBits;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.cst.Zeroes;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.ByteArrayAnnotatedOutput;
import mod.agus.jcoderz.dx.util.Writers;

public final class ClassDataItem extends OffsettedItem {
    private final ArrayList<EncodedMethod> directMethods;
    private byte[] encodedForm;
    private final ArrayList<EncodedField> instanceFields;
    private final ArrayList<EncodedField> staticFields;
    private final HashMap<EncodedField, Constant> staticValues;
    private CstArray staticValuesConstant;
    private final CstType thisClass;
    private final ArrayList<EncodedMethod> virtualMethods;

    public ClassDataItem(CstType cstType) {
        super(1, -1);
        if (cstType == null) {
            throw new NullPointerException("thisClass == null");
        }
        this.thisClass = cstType;
        this.staticFields = new ArrayList<>(20);
        this.staticValues = new HashMap<>(40);
        this.instanceFields = new ArrayList<>(20);
        this.directMethods = new ArrayList<>(20);
        this.virtualMethods = new ArrayList<>(20);
        this.staticValuesConstant = null;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public ItemType itemType() {
        return ItemType.TYPE_CLASS_DATA_ITEM;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.OffsettedItem
    public String toHuman() {
        return toString();
    }

    public boolean isEmpty() {
        return this.staticFields.isEmpty() && this.instanceFields.isEmpty() && this.directMethods.isEmpty() && this.virtualMethods.isEmpty();
    }

    public void addStaticField(EncodedField encodedField, Constant constant) {
        if (encodedField == null) {
            throw new NullPointerException("field == null");
        } else if (this.staticValuesConstant != null) {
            throw new UnsupportedOperationException("static fields already sorted");
        } else {
            this.staticFields.add(encodedField);
            this.staticValues.put(encodedField, constant);
        }
    }

    public void addInstanceField(EncodedField encodedField) {
        if (encodedField == null) {
            throw new NullPointerException("field == null");
        }
        this.instanceFields.add(encodedField);
    }

    public void addDirectMethod(EncodedMethod encodedMethod) {
        if (encodedMethod == null) {
            throw new NullPointerException("method == null");
        }
        this.directMethods.add(encodedMethod);
    }

    public void addVirtualMethod(EncodedMethod encodedMethod) {
        if (encodedMethod == null) {
            throw new NullPointerException("method == null");
        }
        this.virtualMethods.add(encodedMethod);
    }

    public ArrayList<EncodedMethod> getMethods() {
        ArrayList<EncodedMethod> arrayList = new ArrayList<>(this.directMethods.size() + this.virtualMethods.size());
        arrayList.addAll(this.directMethods);
        arrayList.addAll(this.virtualMethods);
        return arrayList;
    }

    public void debugPrint(Writer writer, boolean z) {
        PrintWriter printWriterFor = Writers.printWriterFor(writer);
        int size = this.staticFields.size();
        for (int i = 0; i < size; i++) {
            printWriterFor.println("  sfields[" + i + "]: " + this.staticFields.get(i));
        }
        int size2 = this.instanceFields.size();
        for (int i2 = 0; i2 < size2; i2++) {
            printWriterFor.println("  ifields[" + i2 + "]: " + this.instanceFields.get(i2));
        }
        int size3 = this.directMethods.size();
        for (int i3 = 0; i3 < size3; i3++) {
            printWriterFor.println("  dmeths[" + i3 + "]:");
            this.directMethods.get(i3).debugPrint(printWriterFor, z);
        }
        int size4 = this.virtualMethods.size();
        for (int i4 = 0; i4 < size4; i4++) {
            printWriterFor.println("  vmeths[" + i4 + "]:");
            this.virtualMethods.get(i4).debugPrint(printWriterFor, z);
        }
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public void addContents(DexFile dexFile) {
        if (!this.staticFields.isEmpty()) {
            getStaticValuesConstant();
            Iterator<EncodedField> it = this.staticFields.iterator();
            while (it.hasNext()) {
                it.next().addContents(dexFile);
            }
        }
        if (!this.instanceFields.isEmpty()) {
            Collections.sort(this.instanceFields);
            Iterator<EncodedField> it2 = this.instanceFields.iterator();
            while (it2.hasNext()) {
                it2.next().addContents(dexFile);
            }
        }
        if (!this.directMethods.isEmpty()) {
            Collections.sort(this.directMethods);
            Iterator<EncodedMethod> it3 = this.directMethods.iterator();
            while (it3.hasNext()) {
                it3.next().addContents(dexFile);
            }
        }
        if (!this.virtualMethods.isEmpty()) {
            Collections.sort(this.virtualMethods);
            Iterator<EncodedMethod> it4 = this.virtualMethods.iterator();
            while (it4.hasNext()) {
                it4.next().addContents(dexFile);
            }
        }
    }

    public CstArray getStaticValuesConstant() {
        if (this.staticValuesConstant == null && this.staticFields.size() != 0) {
            this.staticValuesConstant = makeStaticValuesConstant();
        }
        return this.staticValuesConstant;
    }

    private CstArray makeStaticValuesConstant() {
        Collections.sort(this.staticFields);
        int size = this.staticFields.size();
        while (size > 0) {
            Constant constant = this.staticValues.get(this.staticFields.get(size - 1));
            if (constant instanceof CstLiteralBits) {
                if (((CstLiteralBits) constant).getLongBits() != 0) {
                    break;
                }
            } else if (constant != null) {
                break;
            }
            size--;
        }
        if (size == 0) {
            return null;
        }
        CstArray.List list = new CstArray.List(size);
        for (int i = 0; i < size; i++) {
            EncodedField encodedField = this.staticFields.get(i);
            Constant constant2 = this.staticValues.get(encodedField);
            if (constant2 == null) {
                constant2 = Zeroes.zeroFor(encodedField.getRef().getType());
            }
            list.set(i, constant2);
        }
        list.setImmutable();
        return new CstArray(list);
    }

    @Override // mod.agus.jcoderz.dx.dex.file.OffsettedItem
    protected void place0(Section section, int i) {
        ByteArrayAnnotatedOutput byteArrayAnnotatedOutput = new ByteArrayAnnotatedOutput();
        encodeOutput(section.getFile(), byteArrayAnnotatedOutput);
        this.encodedForm = byteArrayAnnotatedOutput.toByteArray();
        setWriteSize(this.encodedForm.length);
    }

    private void encodeOutput(DexFile dexFile, AnnotatedOutput annotatedOutput) {
        boolean annotates = annotatedOutput.annotates();
        if (annotates) {
            annotatedOutput.annotate(0, String.valueOf(offsetString()) + " class data for " + this.thisClass.toHuman());
        }
        encodeSize(dexFile, annotatedOutput, "static_fields", this.staticFields.size());
        encodeSize(dexFile, annotatedOutput, "instance_fields", this.instanceFields.size());
        encodeSize(dexFile, annotatedOutput, "direct_methods", this.directMethods.size());
        encodeSize(dexFile, annotatedOutput, "virtual_methods", this.virtualMethods.size());
        encodeList(dexFile, annotatedOutput, "static_fields", this.staticFields);
        encodeList(dexFile, annotatedOutput, "instance_fields", this.instanceFields);
        encodeList(dexFile, annotatedOutput, "direct_methods", this.directMethods);
        encodeList(dexFile, annotatedOutput, "virtual_methods", this.virtualMethods);
        if (annotates) {
            annotatedOutput.endAnnotation();
        }
    }

    private static void encodeSize(DexFile dexFile, AnnotatedOutput annotatedOutput, String str, int i) {
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(String.format("  %-21s %08x", String.valueOf(str) + "_size:", Integer.valueOf(i)));
        }
        annotatedOutput.writeUleb128(i);
    }

    private static void encodeList(DexFile dexFile, AnnotatedOutput annotatedOutput, String str, ArrayList<? extends EncodedMember> arrayList) {
        int size = arrayList.size();
        if (size != 0) {
            if (annotatedOutput.annotates()) {
                annotatedOutput.annotate(0, "  " + str + ":");
            }
            int i = 0;
            for (int i2 = 0; i2 < size; i2++) {
                i = ((EncodedMember) arrayList.get(i2)).encode(dexFile, annotatedOutput, i, i2);
            }
        }
    }

    @Override // mod.agus.jcoderz.dx.dex.file.OffsettedItem
    public void writeTo0(DexFile dexFile, AnnotatedOutput annotatedOutput) {
        if (annotatedOutput.annotates()) {
            encodeOutput(dexFile, annotatedOutput);
        } else {
            annotatedOutput.write(this.encodedForm);
        }
    }
}
