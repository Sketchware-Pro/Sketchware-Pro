package mod.agus.jcoderz.dx.ssa;

import java.util.ArrayList;
import java.util.BitSet;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecSet;
import mod.agus.jcoderz.dx.util.IntList;

public class LocalVariableExtractor {
    private final ArrayList<SsaBasicBlock> blocks;
    private final SsaMethod method;
    private final LocalVariableInfo resultInfo;
    private final BitSet workSet;

    public static LocalVariableInfo extract(SsaMethod ssaMethod) {
        return new LocalVariableExtractor(ssaMethod).doit();
    }

    private LocalVariableExtractor(SsaMethod ssaMethod) {
        if (ssaMethod == null) {
            throw new NullPointerException("method == null");
        }
        ArrayList<SsaBasicBlock> blocks2 = ssaMethod.getBlocks();
        this.method = ssaMethod;
        this.blocks = blocks2;
        this.resultInfo = new LocalVariableInfo(ssaMethod);
        this.workSet = new BitSet(blocks2.size());
    }

    private LocalVariableInfo doit() {
        if (this.method.getRegCount() > 0) {
            int entryBlockIndex = this.method.getEntryBlockIndex();
            while (entryBlockIndex >= 0) {
                this.workSet.clear(entryBlockIndex);
                processBlock(entryBlockIndex);
                entryBlockIndex = this.workSet.nextSetBit(0);
            }
        }
        this.resultInfo.setImmutable();
        return this.resultInfo;
    }

    private void processBlock(int i) {
        RegisterSpecSet registerSpecSet;
        RegisterSpecSet registerSpecSet2;
        boolean z = true;
        RegisterSpecSet mutableCopyOfStarts = this.resultInfo.mutableCopyOfStarts(i);
        SsaBasicBlock ssaBasicBlock = this.blocks.get(i);
        ArrayList<SsaInsn> insns = ssaBasicBlock.getInsns();
        int size = insns.size();
        if (i != this.method.getExitBlockIndex()) {
            SsaInsn ssaInsn = insns.get(size - 1);
            if (!(ssaInsn.getOriginalRopInsn().getCatches().size() != 0) || ssaInsn.getResult() == null) {
                z = false;
            }
            int i2 = size - 1;
            int i3 = 0;
            RegisterSpecSet registerSpecSet3 = mutableCopyOfStarts;
            while (i3 < size) {
                if (!z || i3 != i2) {
                    registerSpecSet2 = registerSpecSet3;
                } else {
                    registerSpecSet3.setImmutable();
                    registerSpecSet2 = registerSpecSet3.mutableCopy();
                }
                SsaInsn ssaInsn2 = insns.get(i3);
                RegisterSpec localAssignment = ssaInsn2.getLocalAssignment();
                if (localAssignment == null) {
                    RegisterSpec result = ssaInsn2.getResult();
                    if (!(result == null || registerSpecSet2.get(result.getReg()) == null)) {
                        registerSpecSet2.remove(registerSpecSet2.get(result.getReg()));
                    }
                } else {
                    RegisterSpec withSimpleType = localAssignment.withSimpleType();
                    if (!withSimpleType.equals(registerSpecSet2.get(withSimpleType))) {
                        RegisterSpec localItemToSpec = registerSpecSet2.localItemToSpec(withSimpleType.getLocalItem());
                        if (!(localItemToSpec == null || localItemToSpec.getReg() == withSimpleType.getReg())) {
                            registerSpecSet2.remove(localItemToSpec);
                        }
                        this.resultInfo.addAssignment(ssaInsn2, withSimpleType);
                        registerSpecSet2.put(withSimpleType);
                    }
                }
                i3++;
                registerSpecSet3 = registerSpecSet2;
            }
            registerSpecSet3.setImmutable();
            IntList successorList = ssaBasicBlock.getSuccessorList();
            int size2 = successorList.size();
            int primarySuccessorIndex = ssaBasicBlock.getPrimarySuccessorIndex();
            for (int i4 = 0; i4 < size2; i4++) {
                int i5 = successorList.get(i4);
                if (i5 == primarySuccessorIndex) {
                    registerSpecSet = registerSpecSet3;
                } else {
                    registerSpecSet = mutableCopyOfStarts;
                }
                if (this.resultInfo.mergeStarts(i5, registerSpecSet)) {
                    this.workSet.set(i5);
                }
            }
        }
    }
}
