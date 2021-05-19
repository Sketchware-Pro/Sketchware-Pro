package mod.agus.jcoderz.dx.ssa;

import java.util.ArrayList;

import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.ssa.back.InterferenceGraph;
import mod.agus.jcoderz.dx.util.BitIntSet;

public class InterferenceRegisterMapper extends BasicRegisterMapper {
    private final ArrayList<BitIntSet> newRegInterference = new ArrayList<>();
    private final InterferenceGraph oldRegInterference;

    public InterferenceRegisterMapper(InterferenceGraph interferenceGraph, int i) {
        super(i);
        this.oldRegInterference = interferenceGraph;
    }

    @Override // mod.agus.jcoderz.dx.ssa.BasicRegisterMapper
    public void addMapping(int i, int i2, int i3) {
        super.addMapping(i, i2, i3);
        addInterfence(i2, i);
        if (i3 == 2) {
            addInterfence(i2 + 1, i);
        }
    }

    public boolean interferes(int i, int i2, int i3) {
        if (i2 >= this.newRegInterference.size()) {
            return false;
        }
        BitIntSet bitIntSet = this.newRegInterference.get(i2);
        if (bitIntSet == null) {
            return false;
        }
        if (i3 == 1) {
            return bitIntSet.has(i);
        }
        return bitIntSet.has(i) || interferes(i, i2 + 1, i3 + -1);
    }

    public boolean interferes(RegisterSpec registerSpec, int i) {
        return interferes(registerSpec.getReg(), i, registerSpec.getCategory());
    }

    private void addInterfence(int i, int i2) {
        this.newRegInterference.ensureCapacity(i + 1);
        while (i >= this.newRegInterference.size()) {
            this.newRegInterference.add(new BitIntSet(i + 1));
        }
        this.oldRegInterference.mergeInterferenceSet(i2, this.newRegInterference.get(i));
    }

    public boolean areAnyPinned(RegisterSpecList registerSpecList, int i, int i2) {
        int size = registerSpecList.size();
        for (int i3 = 0; i3 < size; i3++) {
            RegisterSpec registerSpec = registerSpecList.get(i3);
            int oldToNew = oldToNew(registerSpec.getReg());
            if (oldToNew == i || ((registerSpec.getCategory() == 2 && oldToNew + 1 == i) || (i2 == 2 && oldToNew == i + 1))) {
                return true;
            }
        }
        return false;
    }
}
