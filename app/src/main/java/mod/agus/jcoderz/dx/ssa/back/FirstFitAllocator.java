package mod.agus.jcoderz.dx.ssa.back;

import java.util.BitSet;
import mod.agus.jcoderz.dx.rop.code.CstInsn;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.ssa.BasicRegisterMapper;
import mod.agus.jcoderz.dx.ssa.NormalSsaInsn;
import mod.agus.jcoderz.dx.ssa.RegisterMapper;
import mod.agus.jcoderz.dx.ssa.SsaMethod;
import mod.agus.jcoderz.dx.util.BitIntSet;

public class FirstFitAllocator extends RegisterAllocator {
    private static final boolean PRESLOT_PARAMS = true;
    private final BitSet mapped;

    public FirstFitAllocator(SsaMethod ssaMethod, InterferenceGraph interferenceGraph) {
        super(ssaMethod, interferenceGraph);
        this.mapped = new BitSet(ssaMethod.getRegCount());
    }

    @Override // mod.agus.jcoderz.dx.ssa.back.RegisterAllocator
    public boolean wantsParamsMovedHigh() {
        return true;
    }

    @Override // mod.agus.jcoderz.dx.ssa.back.RegisterAllocator
    public RegisterMapper allocateRegisters() {
        int i;
        boolean z;
        int regCount = this.ssaMeth.getRegCount();
        BasicRegisterMapper basicRegisterMapper = new BasicRegisterMapper(regCount);
        int paramWidth = this.ssaMeth.getParamWidth();
        for (int i2 = 0; i2 < regCount; i2++) {
            if (!this.mapped.get(i2)) {
                int categoryForSsaReg = getCategoryForSsaReg(i2);
                BitIntSet bitIntSet = new BitIntSet(regCount);
                this.interference.mergeInterferenceSet(i2, bitIntSet);
                if (isDefinitionMoveParam(i2)) {
                    i = paramNumberFromMoveParam((NormalSsaInsn) this.ssaMeth.getDefinitionForRegister(i2));
                    basicRegisterMapper.addMapping(i2, i, categoryForSsaReg);
                    z = true;
                } else {
                    basicRegisterMapper.addMapping(i2, paramWidth, categoryForSsaReg);
                    i = paramWidth;
                    z = false;
                }
                int i3 = categoryForSsaReg;
                for (int i4 = i2 + 1; i4 < regCount; i4++) {
                    if (!this.mapped.get(i4) && !isDefinitionMoveParam(i4) && !bitIntSet.has(i4) && (!z || i3 >= getCategoryForSsaReg(i4))) {
                        this.interference.mergeInterferenceSet(i4, bitIntSet);
                        i3 = Math.max(i3, getCategoryForSsaReg(i4));
                        basicRegisterMapper.addMapping(i4, i, i3);
                        this.mapped.set(i4);
                    }
                }
                this.mapped.set(i2);
                if (!z) {
                    paramWidth += i3;
                }
            }
        }
        return basicRegisterMapper;
    }

    private int paramNumberFromMoveParam(NormalSsaInsn normalSsaInsn) {
        return ((CstInteger) ((CstInsn) normalSsaInsn.getOriginalRopInsn()).getConstant()).getValue();
    }
}
