package mod.agus.jcoderz.dx.ssa;

import java.util.BitSet;
import java.util.List;

import mod.agus.jcoderz.dx.cf.code.Merger;
import mod.agus.jcoderz.dx.rop.code.LocalItem;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;

public class PhiTypeResolver {
    private final BitSet worklist;
    SsaMethod ssaMeth;

    private PhiTypeResolver(SsaMethod ssaMethod) {
        this.ssaMeth = ssaMethod;
        this.worklist = new BitSet(ssaMethod.getRegCount());
    }

    public static void process(SsaMethod ssaMethod) {
        new PhiTypeResolver(ssaMethod).run();
    }

    private static boolean equalsHandlesNulls(LocalItem localItem, LocalItem localItem2) {
        return localItem == localItem2 || (localItem != null && localItem.equals(localItem2));
    }

    private void run() {
        int regCount = this.ssaMeth.getRegCount();
        for (int i = 0; i < regCount; i++) {
            SsaInsn definitionForRegister = this.ssaMeth.getDefinitionForRegister(i);
            if (definitionForRegister != null && definitionForRegister.getResult().getBasicType() == 0) {
                this.worklist.set(i);
            }
        }
        while (true) {
            int nextSetBit = this.worklist.nextSetBit(0);
            if (nextSetBit >= 0) {
                this.worklist.clear(nextSetBit);
                if (resolveResultType((PhiInsn) this.ssaMeth.getDefinitionForRegister(nextSetBit))) {
                    List<SsaInsn> useListForRegister = this.ssaMeth.getUseListForRegister(nextSetBit);
                    int size = useListForRegister.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        SsaInsn ssaInsn = useListForRegister.get(i2);
                        RegisterSpec result = ssaInsn.getResult();
                        if (result != null && (ssaInsn instanceof PhiInsn)) {
                            this.worklist.set(result.getReg());
                        }
                    }
                }
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean resolveResultType(PhiInsn phiInsn) {
        phiInsn.updateSourcesToDefinitions(this.ssaMeth);
        RegisterSpecList sources = phiInsn.getSources();
        int i = -1;
        int size = sources.size();
        int i2 = 0;
        RegisterSpec registerSpec = null;
        while (i2 < size) {
            RegisterSpec registerSpec2 = sources.get(i2);
            if (registerSpec2.getBasicType() != 0) {
                i = i2;
            } else {
                registerSpec2 = registerSpec;
            }
            i2++;
            registerSpec = registerSpec2;
        }
        if (registerSpec == null) {
            return false;
        }
        LocalItem localItem = registerSpec.getLocalItem();
        TypeBearer type = registerSpec.getType();
        boolean z = true;
        for (int i3 = 0; i3 < size; i3++) {
            if (i3 != i) {
                RegisterSpec registerSpec3 = sources.get(i3);
                if (registerSpec3.getBasicType() != 0) {
                    z = z && equalsHandlesNulls(localItem, registerSpec3.getLocalItem());
                    type = Merger.mergeType(type, registerSpec3.getType());
                }
            }
        }
        if (type != null) {
            LocalItem localItem2 = z ? localItem : null;
            RegisterSpec result = phiInsn.getResult();
            if (result.getTypeBearer() == type && equalsHandlesNulls(localItem2, result.getLocalItem())) {
                return false;
            }
            phiInsn.changeResultType(type, localItem2);
            return true;
        }
        StringBuilder sb = new StringBuilder();
        for (int i4 = 0; i4 < size; i4++) {
            sb.append(sources.get(i4).toString());
            sb.append(' ');
        }
        throw new RuntimeException("Couldn't map types in phi insn:" + ((Object) sb));
    }
}
