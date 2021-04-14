package mod.agus.jcoderz.dx.ssa.back;

import mod.agus.jcoderz.dx.ssa.BasicRegisterMapper;
import mod.agus.jcoderz.dx.ssa.RegisterMapper;
import mod.agus.jcoderz.dx.ssa.SsaMethod;

public class NullRegisterAllocator extends RegisterAllocator {
    public NullRegisterAllocator(SsaMethod ssaMethod, InterferenceGraph interferenceGraph) {
        super(ssaMethod, interferenceGraph);
    }

    @Override // mod.agus.jcoderz.dx.ssa.back.RegisterAllocator
    public boolean wantsParamsMovedHigh() {
        return false;
    }

    @Override // mod.agus.jcoderz.dx.ssa.back.RegisterAllocator
    public RegisterMapper allocateRegisters() {
        int regCount = this.ssaMeth.getRegCount();
        BasicRegisterMapper basicRegisterMapper = new BasicRegisterMapper(regCount);
        for (int i = 0; i < regCount; i++) {
            basicRegisterMapper.addMapping(i, i * 2, 2);
        }
        return basicRegisterMapper;
    }
}
