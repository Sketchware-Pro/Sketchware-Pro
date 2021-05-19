package mod.agus.jcoderz.dx.ssa.back;

import java.util.ArrayList;

import mod.agus.jcoderz.dx.rop.code.PlainInsn;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.Rops;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.ssa.NormalSsaInsn;
import mod.agus.jcoderz.dx.ssa.RegisterMapper;
import mod.agus.jcoderz.dx.ssa.SsaBasicBlock;
import mod.agus.jcoderz.dx.ssa.SsaInsn;
import mod.agus.jcoderz.dx.ssa.SsaMethod;
import mod.agus.jcoderz.dx.util.IntIterator;

public abstract class RegisterAllocator {
    protected final InterferenceGraph interference;
    protected final SsaMethod ssaMeth;

    public RegisterAllocator(SsaMethod ssaMethod, InterferenceGraph interferenceGraph) {
        this.ssaMeth = ssaMethod;
        this.interference = interferenceGraph;
    }

    public abstract RegisterMapper allocateRegisters();

    public abstract boolean wantsParamsMovedHigh();

    /* access modifiers changed from: protected */
    public final int getCategoryForSsaReg(int i) {
        SsaInsn definitionForRegister = this.ssaMeth.getDefinitionForRegister(i);
        if (definitionForRegister == null) {
            return 1;
        }
        return definitionForRegister.getResult().getCategory();
    }

    /* access modifiers changed from: protected */
    public final RegisterSpec getDefinitionSpecForSsaReg(int i) {
        SsaInsn definitionForRegister = this.ssaMeth.getDefinitionForRegister(i);
        if (definitionForRegister == null) {
            return null;
        }
        return definitionForRegister.getResult();
    }

    /* access modifiers changed from: protected */
    public boolean isDefinitionMoveParam(int i) {
        SsaInsn definitionForRegister = this.ssaMeth.getDefinitionForRegister(i);
        if (!(definitionForRegister instanceof NormalSsaInsn)) {
            return false;
        }
        return ((NormalSsaInsn) definitionForRegister).getOpcode().getOpcode() == 3;
    }

    /* access modifiers changed from: protected */
    public final RegisterSpec insertMoveBefore(SsaInsn ssaInsn, RegisterSpec registerSpec) {
        SsaBasicBlock block = ssaInsn.getBlock();
        ArrayList<SsaInsn> insns = block.getInsns();
        int indexOf = insns.indexOf(ssaInsn);
        if (indexOf < 0) {
            throw new IllegalArgumentException("specified insn is not in this block");
        } else if (indexOf != insns.size() - 1) {
            throw new IllegalArgumentException("Adding move here not supported:" + ssaInsn.toHuman());
        } else {
            RegisterSpec make = RegisterSpec.make(this.ssaMeth.makeNewSsaReg(), registerSpec.getTypeBearer());
            insns.add(indexOf, SsaInsn.makeFromRop(new PlainInsn(Rops.opMove(make.getType()), SourcePosition.NO_INFO, make, RegisterSpecList.make(registerSpec)), block));
            int reg = make.getReg();
            IntIterator it = block.getLiveOutRegs().iterator();
            while (it.hasNext()) {
                this.interference.add(reg, it.next());
            }
            RegisterSpecList sources = ssaInsn.getSources();
            int size = sources.size();
            for (int i = 0; i < size; i++) {
                this.interference.add(reg, sources.get(i).getReg());
            }
            this.ssaMeth.onInsnsChanged();
            return make;
        }
    }
}
