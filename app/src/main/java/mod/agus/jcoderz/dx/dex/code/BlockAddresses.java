package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dx.rop.code.BasicBlock;
import mod.agus.jcoderz.dx.rop.code.BasicBlockList;
import mod.agus.jcoderz.dx.rop.code.RopMethod;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;

public final class BlockAddresses {
    private final CodeAddress[] ends;
    private final CodeAddress[] lasts;
    private final CodeAddress[] starts;

    public BlockAddresses(RopMethod ropMethod) {
        int maxLabel = ropMethod.getBlocks().getMaxLabel();
        this.starts = new CodeAddress[maxLabel];
        this.lasts = new CodeAddress[maxLabel];
        this.ends = new CodeAddress[maxLabel];
        setupArrays(ropMethod);
    }

    public CodeAddress getStart(BasicBlock basicBlock) {
        return this.starts[basicBlock.getLabel()];
    }

    public CodeAddress getStart(int i) {
        return this.starts[i];
    }

    public CodeAddress getLast(BasicBlock basicBlock) {
        return this.lasts[basicBlock.getLabel()];
    }

    public CodeAddress getLast(int i) {
        return this.lasts[i];
    }

    public CodeAddress getEnd(BasicBlock basicBlock) {
        return this.ends[basicBlock.getLabel()];
    }

    public CodeAddress getEnd(int i) {
        return this.ends[i];
    }

    private void setupArrays(RopMethod ropMethod) {
        BasicBlockList blocks = ropMethod.getBlocks();
        int size = blocks.size();
        for (int i = 0; i < size; i++) {
            BasicBlock basicBlock = blocks.get(i);
            int label = basicBlock.getLabel();
            this.starts[label] = new CodeAddress(basicBlock.getInsns().get(0).getPosition());
            SourcePosition position = basicBlock.getLastInsn().getPosition();
            this.lasts[label] = new CodeAddress(position);
            this.ends[label] = new CodeAddress(position);
        }
    }
}
