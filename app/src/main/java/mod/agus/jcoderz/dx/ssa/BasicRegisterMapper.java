package mod.agus.jcoderz.dx.ssa;

import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.util.IntList;

public class BasicRegisterMapper extends RegisterMapper {
    private IntList oldToNew;
    private int runningCountNewRegisters;

    public BasicRegisterMapper(int i) {
        this.oldToNew = new IntList(i);
    }

    @Override // mod.agus.jcoderz.dx.ssa.RegisterMapper
    public int getNewRegisterCount() {
        return this.runningCountNewRegisters;
    }

    @Override // mod.agus.jcoderz.dx.ssa.RegisterMapper
    public RegisterSpec map(RegisterSpec registerSpec) {
        int i;
        if (registerSpec == null) {
            return null;
        }
        try {
            i = this.oldToNew.get(registerSpec.getReg());
        } catch (IndexOutOfBoundsException e) {
            i = -1;
        }
        if (i >= 0) {
            return registerSpec.withReg(i);
        }
        throw new RuntimeException("no mapping specified for register");
    }

    public int oldToNew(int i) {
        if (i >= this.oldToNew.size()) {
            return -1;
        }
        return this.oldToNew.get(i);
    }

    public String toHuman() {
        StringBuilder sb = new StringBuilder();
        sb.append("Old\tNew\n");
        int size = this.oldToNew.size();
        for (int i = 0; i < size; i++) {
            sb.append(i);
            sb.append('\t');
            sb.append(this.oldToNew.get(i));
            sb.append('\n');
        }
        sb.append("new reg count:");
        sb.append(this.runningCountNewRegisters);
        sb.append('\n');
        return sb.toString();
    }

    public void addMapping(int i, int i2, int i3) {
        if (i >= this.oldToNew.size()) {
            for (int size = i - this.oldToNew.size(); size >= 0; size--) {
                this.oldToNew.add(-1);
            }
        }
        this.oldToNew.set(i, i2);
        if (this.runningCountNewRegisters < i2 + i3) {
            this.runningCountNewRegisters = i2 + i3;
        }
    }
}
