package mod.agus.jcoderz.dx.dex.code;

import java.util.HashSet;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.type.Type;

public final class DalvCode {
    private CatchTable catches;
    private DalvInsnList insns;
    private LocalList locals;
    private final int positionInfo;
    private PositionList positions;
    private CatchBuilder unprocessedCatches;
    private OutputFinisher unprocessedInsns;

    public interface AssignIndicesCallback {
        int getIndex(Constant constant);
    }

    public DalvCode(int i, OutputFinisher outputFinisher, CatchBuilder catchBuilder) {
        if (outputFinisher == null) {
            throw new NullPointerException("unprocessedInsns == null");
        } else if (catchBuilder == null) {
            throw new NullPointerException("unprocessedCatches == null");
        } else {
            this.positionInfo = i;
            this.unprocessedInsns = outputFinisher;
            this.unprocessedCatches = catchBuilder;
            this.catches = null;
            this.positions = null;
            this.locals = null;
            this.insns = null;
        }
    }

    private void finishProcessingIfNecessary() {
        if (this.insns == null) {
            this.insns = this.unprocessedInsns.finishProcessingAndGetList();
            this.positions = PositionList.make(this.insns, this.positionInfo);
            this.locals = LocalList.make(this.insns);
            this.catches = this.unprocessedCatches.build();
            this.unprocessedInsns = null;
            this.unprocessedCatches = null;
        }
    }

    public void assignIndices(AssignIndicesCallback assignIndicesCallback) {
        this.unprocessedInsns.assignIndices(assignIndicesCallback);
    }

    public boolean hasPositions() {
        return this.positionInfo != 1 && this.unprocessedInsns.hasAnyPositionInfo();
    }

    public boolean hasLocals() {
        return this.unprocessedInsns.hasAnyLocalInfo();
    }

    public boolean hasAnyCatches() {
        return this.unprocessedCatches.hasAnyCatches();
    }

    public HashSet<Type> getCatchTypes() {
        return this.unprocessedCatches.getCatchTypes();
    }

    public HashSet<Constant> getInsnConstants() {
        return this.unprocessedInsns.getAllConstants();
    }

    public DalvInsnList getInsns() {
        finishProcessingIfNecessary();
        return this.insns;
    }

    public CatchTable getCatches() {
        finishProcessingIfNecessary();
        return this.catches;
    }

    public PositionList getPositions() {
        finishProcessingIfNecessary();
        return this.positions;
    }

    public LocalList getLocals() {
        finishProcessingIfNecessary();
        return this.locals;
    }
}
