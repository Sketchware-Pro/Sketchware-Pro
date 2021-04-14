package mod.agus.jcoderz.dx.cf.attrib;

import mod.agus.jcoderz.dx.cf.code.LocalVariableList;

public final class AttLocalVariableTypeTable extends BaseLocalVariables {
    public static final String ATTRIBUTE_NAME = "LocalVariableTypeTable";

    public AttLocalVariableTypeTable(LocalVariableList localVariableList) {
        super(ATTRIBUTE_NAME, localVariableList);
    }
}
