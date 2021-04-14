package mod.agus.jcoderz.dx.cf.attrib;

import mod.agus.jcoderz.dx.cf.code.LocalVariableList;
import mod.agus.jcoderz.dx.util.MutabilityException;

public abstract class BaseLocalVariables extends BaseAttribute {
    private final LocalVariableList localVariables;

    public BaseLocalVariables(String str, LocalVariableList localVariableList) {
        super(str);
        try {
            if (localVariableList.isMutable()) {
                throw new MutabilityException("localVariables.isMutable()");
            }
            this.localVariables = localVariableList;
        } catch (NullPointerException e) {
            throw new NullPointerException("localVariables == null");
        }
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.Attribute
    public final int byteLength() {
        return (this.localVariables.size() * 10) + 8;
    }

    public final LocalVariableList getLocalVariables() {
        return this.localVariables;
    }
}
