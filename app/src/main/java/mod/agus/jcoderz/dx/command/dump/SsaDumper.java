package mod.agus.jcoderz.dx.command.dump;

import org.eclipse.jdt.internal.compiler.codegen.ConstantPool;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;

import mod.agus.jcoderz.dx.cf.code.ConcreteMethod;
import mod.agus.jcoderz.dx.cf.code.Ropper;
import mod.agus.jcoderz.dx.cf.iface.Member;
import mod.agus.jcoderz.dx.cf.iface.Method;
import mod.agus.jcoderz.dx.rop.code.AccessFlags;
import mod.agus.jcoderz.dx.rop.code.DexTranslationAdvice;
import mod.agus.jcoderz.dx.rop.code.RopMethod;
import mod.agus.jcoderz.dx.ssa.Optimizer;
import mod.agus.jcoderz.dx.ssa.SsaBasicBlock;
import mod.agus.jcoderz.dx.ssa.SsaInsn;
import mod.agus.jcoderz.dx.ssa.SsaMethod;
import mod.agus.jcoderz.dx.util.ByteArray;
import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.IntList;

public class SsaDumper extends BlockDumper {
    private SsaDumper(byte[] bArr, PrintStream printStream, String str, Args args) {
        super(bArr, printStream, str, true, args);
    }

    public static void dump(byte[] bArr, PrintStream printStream, String str, Args args) {
        new SsaDumper(bArr, printStream, str, args).dump();
    }

    @Override
    // mod.agus.jcoderz.dx.command.dump.BaseDumper, mod.agus.jcoderz.dx.command.dump.BlockDumper, mod.agus.jcoderz.dx.cf.iface.ParseObserver
    public void endParsingMember(ByteArray byteArray, int i, String str, String str2, Member member) {
        SsaMethod ssaMethod;
        if ((member instanceof Method) && shouldDumpMethod(str) && (member.getAccessFlags() & 1280) == 0) {
            ConcreteMethod concreteMethod = new ConcreteMethod((Method) member, this.classFile, true, true);
            DexTranslationAdvice dexTranslationAdvice = DexTranslationAdvice.THE_ONE;
            RopMethod convert = Ropper.convert(concreteMethod, dexTranslationAdvice, this.classFile.getMethods());
            boolean isStatic = AccessFlags.isStatic(concreteMethod.getAccessFlags());
            int computeParamWidth = computeParamWidth(concreteMethod, isStatic);
            if (this.args.ssaStep == null) {
                ssaMethod = Optimizer.debugNoRegisterAllocation(convert, computeParamWidth, isStatic, true, dexTranslationAdvice, EnumSet.allOf(Optimizer.OptionalStep.class));
            } else if ("edge-split".equals(this.args.ssaStep)) {
                ssaMethod = Optimizer.debugEdgeSplit(convert, computeParamWidth, isStatic, true, dexTranslationAdvice);
            } else if ("phi-placement".equals(this.args.ssaStep)) {
                ssaMethod = Optimizer.debugPhiPlacement(convert, computeParamWidth, isStatic, true, dexTranslationAdvice);
            } else if ("renaming".equals(this.args.ssaStep)) {
                ssaMethod = Optimizer.debugRenaming(convert, computeParamWidth, isStatic, true, dexTranslationAdvice);
            } else if ("dead-code".equals(this.args.ssaStep)) {
                ssaMethod = Optimizer.debugDeadCodeRemover(convert, computeParamWidth, isStatic, true, dexTranslationAdvice);
            } else {
                ssaMethod = null;
            }
            StringBuffer stringBuffer = new StringBuffer((int) ConstantPool.CONSTANTPOOL_INITIAL_SIZE);
            stringBuffer.append("first ");
            stringBuffer.append(Hex.u2(ssaMethod.blockIndexToRopLabel(ssaMethod.getEntryBlockIndex())));
            stringBuffer.append('\n');
            ArrayList arrayList = (ArrayList) ssaMethod.getBlocks().clone();
            Collections.sort(arrayList, SsaBasicBlock.LABEL_COMPARATOR);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                SsaBasicBlock ssaBasicBlock = (SsaBasicBlock) it.next();
                stringBuffer.append("block ").append(Hex.u2(ssaBasicBlock.getRopLabel())).append('\n');
                BitSet predecessors = ssaBasicBlock.getPredecessors();
                for (int nextSetBit = predecessors.nextSetBit(0); nextSetBit >= 0; nextSetBit = predecessors.nextSetBit(nextSetBit + 1)) {
                    stringBuffer.append("  pred ");
                    stringBuffer.append(Hex.u2(ssaMethod.blockIndexToRopLabel(nextSetBit)));
                    stringBuffer.append('\n');
                }
                stringBuffer.append("  live in:" + ssaBasicBlock.getLiveInRegs());
                stringBuffer.append("\n");
                Iterator<SsaInsn> it2 = ssaBasicBlock.getInsns().iterator();
                while (it2.hasNext()) {
                    stringBuffer.append("  ");
                    stringBuffer.append(it2.next().toHuman());
                    stringBuffer.append('\n');
                }
                if (ssaBasicBlock.getSuccessors().cardinality() == 0) {
                    stringBuffer.append("  returns\n");
                } else {
                    int primarySuccessorRopLabel = ssaBasicBlock.getPrimarySuccessorRopLabel();
                    IntList ropLabelSuccessorList = ssaBasicBlock.getRopLabelSuccessorList();
                    int size = ropLabelSuccessorList.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        stringBuffer.append("  next ");
                        stringBuffer.append(Hex.u2(ropLabelSuccessorList.get(i2)));
                        if (size != 1 && primarySuccessorRopLabel == ropLabelSuccessorList.get(i2)) {
                            stringBuffer.append(" *");
                        }
                        stringBuffer.append('\n');
                    }
                }
                stringBuffer.append("  live out:" + ssaBasicBlock.getLiveOutRegs());
                stringBuffer.append("\n");
            }
            this.suppressDump = false;
            setAt(byteArray, 0);
            parsed(byteArray, 0, byteArray.size(), stringBuffer.toString());
            this.suppressDump = true;
        }
    }
}
