package mod.agus.jcoderz.dx.dex.code;

import java.util.ArrayList;
import java.util.HashSet;
import mod.agus.jcoderz.dx.dex.code.CatchTable;
import mod.agus.jcoderz.dx.rop.code.BasicBlock;
import mod.agus.jcoderz.dx.rop.code.BasicBlockList;
import mod.agus.jcoderz.dx.rop.code.RopMethod;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.util.IntList;

public final class StdCatchBuilder implements CatchBuilder {
    private static final int MAX_CATCH_RANGE = 65535;
    private final BlockAddresses addresses;
    private final RopMethod method;
    private final int[] order;

    public StdCatchBuilder(RopMethod ropMethod, int[] iArr, BlockAddresses blockAddresses) {
        if (ropMethod == null) {
            throw new NullPointerException("method == null");
        } else if (iArr == null) {
            throw new NullPointerException("order == null");
        } else if (blockAddresses == null) {
            throw new NullPointerException("addresses == null");
        } else {
            this.method = ropMethod;
            this.order = iArr;
            this.addresses = blockAddresses;
        }
    }

    @Override // mod.agus.jcoderz.dx.dex.code.CatchBuilder
    public CatchTable build() {
        return build(this.method, this.order, this.addresses);
    }

    @Override // mod.agus.jcoderz.dx.dex.code.CatchBuilder
    public boolean hasAnyCatches() {
        BasicBlockList blocks = this.method.getBlocks();
        int size = blocks.size();
        for (int i = 0; i < size; i++) {
            if (blocks.get(i).getLastInsn().getCatches().size() != 0) {
                return true;
            }
        }
        return false;
    }

    @Override // mod.agus.jcoderz.dx.dex.code.CatchBuilder
    public HashSet<Type> getCatchTypes() {
        HashSet<Type> hashSet = new HashSet<>(20);
        BasicBlockList blocks = this.method.getBlocks();
        int size = blocks.size();
        for (int i = 0; i < size; i++) {
            TypeList catches = blocks.get(i).getLastInsn().getCatches();
            int size2 = catches.size();
            for (int i2 = 0; i2 < size2; i2++) {
                hashSet.add(catches.getType(i2));
            }
        }
        return hashSet;
    }

    public static CatchTable build(RopMethod ropMethod, int[] iArr, BlockAddresses blockAddresses) {
        CatchHandlerList catchHandlerList;
        BasicBlock basicBlock = null;
        int length = iArr.length;
        BasicBlockList blocks = ropMethod.getBlocks();
        ArrayList arrayList = new ArrayList(length);
        CatchHandlerList catchHandlerList2 = CatchHandlerList.EMPTY;
        int i = 0;
        BasicBlock basicBlock2 = null;
        while (i < length) {
            BasicBlock labelToBlock = blocks.labelToBlock(iArr[i]);
            if (!labelToBlock.canThrow()) {
                labelToBlock = basicBlock2;
                catchHandlerList = catchHandlerList2;
            } else {
                CatchHandlerList handlersFor = handlersFor(labelToBlock, blockAddresses);
                if (catchHandlerList2.size() == 0) {
                    basicBlock = labelToBlock;
                    catchHandlerList = handlersFor;
                } else if (!catchHandlerList2.equals(handlersFor) || !rangeIsValid(basicBlock2, labelToBlock, blockAddresses)) {
                    if (catchHandlerList2.size() != 0) {
                        arrayList.add(makeEntry(basicBlock2, basicBlock, catchHandlerList2, blockAddresses));
                    }
                    basicBlock = labelToBlock;
                    catchHandlerList = handlersFor;
                } else {
                    basicBlock = labelToBlock;
                    labelToBlock = basicBlock2;
                    catchHandlerList = catchHandlerList2;
                }
            }
            i++;
            catchHandlerList2 = catchHandlerList;
            basicBlock2 = labelToBlock;
        }
        if (catchHandlerList2.size() != 0) {
            arrayList.add(makeEntry(basicBlock2, basicBlock, catchHandlerList2, blockAddresses));
        }
        int size = arrayList.size();
        if (size == 0) {
            return CatchTable.EMPTY;
        }
        CatchTable catchTable = new CatchTable(size);
        for (int i2 = 0; i2 < size; i2++) {
            catchTable.set(i2, (CatchTable.Entry) arrayList.get(i2));
        }
        catchTable.setImmutable();
        return catchTable;
    }

    private static CatchHandlerList handlersFor(BasicBlock basicBlock, BlockAddresses blockAddresses) {
        IntList successors = basicBlock.getSuccessors();
        int size = successors.size();
        int primarySuccessor = basicBlock.getPrimarySuccessor();
        TypeList catches = basicBlock.getLastInsn().getCatches();
        int size2 = catches.size();
        if (size2 == 0) {
            return CatchHandlerList.EMPTY;
        }
        if ((primarySuccessor != -1 || size == size2) && (primarySuccessor == -1 || (size == size2 + 1 && primarySuccessor == successors.get(size2)))) {
            int i = 0;
            while (true) {
                if (i >= size2) {
                    break;
                } else if (catches.getType(i).equals(Type.OBJECT)) {
                    size2 = i + 1;
                    break;
                } else {
                    i++;
                }
            }
            CatchHandlerList catchHandlerList = new CatchHandlerList(size2);
            for (int i2 = 0; i2 < size2; i2++) {
                catchHandlerList.set(i2, new CstType(catches.getType(i2)), blockAddresses.getStart(successors.get(i2)).getAddress());
            }
            catchHandlerList.setImmutable();
            return catchHandlerList;
        }
        throw new RuntimeException("shouldn't happen: weird successors list");
    }

    private static CatchTable.Entry makeEntry(BasicBlock basicBlock, BasicBlock basicBlock2, CatchHandlerList catchHandlerList, BlockAddresses blockAddresses) {
        return new CatchTable.Entry(blockAddresses.getLast(basicBlock).getAddress(), blockAddresses.getEnd(basicBlock2).getAddress(), catchHandlerList);
    }

    private static boolean rangeIsValid(BasicBlock basicBlock, BasicBlock basicBlock2, BlockAddresses blockAddresses) {
        if (basicBlock == null) {
            throw new NullPointerException("start == null");
        } else if (basicBlock2 == null) {
            throw new NullPointerException("end == null");
        } else {
            return blockAddresses.getEnd(basicBlock2).getAddress() - blockAddresses.getLast(basicBlock).getAddress() <= 65535;
        }
    }
}
