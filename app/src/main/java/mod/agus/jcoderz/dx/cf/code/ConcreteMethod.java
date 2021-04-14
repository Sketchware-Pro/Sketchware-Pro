package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dx.cf.attrib.AttCode;
import mod.agus.jcoderz.dx.cf.attrib.AttLineNumberTable;
import mod.agus.jcoderz.dx.cf.attrib.AttLocalVariableTable;
import mod.agus.jcoderz.dx.cf.attrib.AttLocalVariableTypeTable;
import mod.agus.jcoderz.dx.cf.iface.AttributeList;
import mod.agus.jcoderz.dx.cf.iface.ClassFile;
import mod.agus.jcoderz.dx.cf.iface.Method;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.rop.cst.CstNat;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Prototype;

public final class ConcreteMethod implements Method {
    private final boolean accSuper;
    private final AttCode attCode;
    private final LineNumberList lineNumbers;
    private final LocalVariableList localVariables;
    private final Method method;
    private final CstString sourceFile;

    public ConcreteMethod(Method method2, ClassFile classFile, boolean z, boolean z2) {
        this(method2, classFile.getAccessFlags(), classFile.getSourceFile(), z, z2);
    }

    public ConcreteMethod(Method method2, int i, CstString cstString, boolean z, boolean z2) {
        LocalVariableList localVariables1;
        LineNumberList lineNumberList;
        LocalVariableList localVariableList;
        this.method = method2;
        this.accSuper = (i & 32) != 0;
        this.sourceFile = cstString;
        this.attCode = (AttCode) method2.getAttributes().findFirst(AttCode.ATTRIBUTE_NAME);
        AttributeList attributes = this.attCode.getAttributes();
        LineNumberList lineNumberList2 = LineNumberList.EMPTY;
        if (z) {
            lineNumberList = lineNumberList2;
            AttLineNumberTable attLineNumberTable = (AttLineNumberTable) attributes.findFirst(AttLineNumberTable.ATTRIBUTE_NAME);
            while (attLineNumberTable != null) {
                LineNumberList concat = LineNumberList.concat(lineNumberList, attLineNumberTable.getLineNumbers());
                attLineNumberTable = (AttLineNumberTable) attributes.findNext(attLineNumberTable);
                lineNumberList = concat;
            }
        } else {
            lineNumberList = lineNumberList2;
        }
        this.lineNumbers = lineNumberList;
        LocalVariableList localVariableList2 = LocalVariableList.EMPTY;
        if (z2) {
            for (AttLocalVariableTable attLocalVariableTable = (AttLocalVariableTable) attributes.findFirst(AttLocalVariableTable.ATTRIBUTE_NAME); attLocalVariableTable != null; attLocalVariableTable = (AttLocalVariableTable) attributes.findNext(attLocalVariableTable)) {
                localVariableList2 = LocalVariableList.concat(localVariableList2, attLocalVariableTable.getLocalVariables());
            }
            LocalVariableList localVariableList3 = LocalVariableList.EMPTY;
            for (AttLocalVariableTypeTable attLocalVariableTypeTable = (AttLocalVariableTypeTable) attributes.findFirst(AttLocalVariableTypeTable.ATTRIBUTE_NAME); attLocalVariableTypeTable != null; attLocalVariableTypeTable = (AttLocalVariableTypeTable) attributes.findNext(attLocalVariableTypeTable)) {
                localVariableList3 = LocalVariableList.concat(localVariableList3, attLocalVariableTypeTable.getLocalVariables());
            }
            if (localVariableList3.size() != 0) {
                localVariableList = LocalVariableList.mergeDescriptorsAndSignatures(localVariableList2, localVariableList3);
                localVariables1 = localVariableList;
            }
        }
        localVariableList = localVariableList2;
        localVariables1 = localVariableList;
        localVariables = localVariables1;
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.Member
    public CstNat getNat() {
        return this.method.getNat();
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.Member
    public CstString getName() {
        return this.method.getName();
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.Member
    public CstString getDescriptor() {
        return this.method.getDescriptor();
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.Member
    public int getAccessFlags() {
        return this.method.getAccessFlags();
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.Member, mod.agus.jcoderz.dx.cf.iface.HasAttribute
    public AttributeList getAttributes() {
        return this.method.getAttributes();
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.Member
    public CstType getDefiningClass() {
        return this.method.getDefiningClass();
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.Method
    public Prototype getEffectiveDescriptor() {
        return this.method.getEffectiveDescriptor();
    }

    public boolean getAccSuper() {
        return this.accSuper;
    }

    public int getMaxStack() {
        return this.attCode.getMaxStack();
    }

    public int getMaxLocals() {
        return this.attCode.getMaxLocals();
    }

    public BytecodeArray getCode() {
        return this.attCode.getCode();
    }

    public ByteCatchList getCatches() {
        return this.attCode.getCatches();
    }

    public LineNumberList getLineNumbers() {
        return this.lineNumbers;
    }

    public LocalVariableList getLocalVariables() {
        return this.localVariables;
    }

    public SourcePosition makeSourcePosistion(int i) {
        return new SourcePosition(this.sourceFile, i, this.lineNumbers.pcToLine(i));
    }
}
