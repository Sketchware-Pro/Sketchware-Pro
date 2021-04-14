package mod.agus.jcoderz.dx.ssa;

import java.util.ArrayList;
import mod.agus.jcoderz.dx.rop.code.Insn;
import mod.agus.jcoderz.dx.rop.code.PlainCstInsn;
import mod.agus.jcoderz.dx.rop.code.PlainInsn;
import mod.agus.jcoderz.dx.rop.code.RegOps;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.Rop;
import mod.agus.jcoderz.dx.rop.code.Rops;
import mod.agus.jcoderz.dx.rop.code.TranslationAdvice;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstLiteralBits;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;
import mod.agus.jcoderz.dx.ssa.SsaInsn;

public class LiteralOpUpgrader {
    private final SsaMethod ssaMeth;

    public static void process(SsaMethod ssaMethod) {
        new LiteralOpUpgrader(ssaMethod).run();
    }

    private LiteralOpUpgrader(SsaMethod ssaMethod) {
        this.ssaMeth = ssaMethod;
    }

    public static boolean isConstIntZeroOrKnownNull(RegisterSpec registerSpec) {
        TypeBearer typeBearer = registerSpec.getTypeBearer();
        if (!(typeBearer instanceof CstLiteralBits)) {
            return false;
        }
        if (((CstLiteralBits) typeBearer).getLongBits() == 0) {
            return true;
        }
        return false;
    }

    private void run() {
        final TranslationAdvice advice = Optimizer.getAdvice();
        this.ssaMeth.forEachInsn(new SsaInsn.Visitor() {
            /* class mod.agus.jcoderz.dx.ssa.LiteralOpUpgrader.AnonymousClass1 */

            @Override // mod.agus.jcoderz.dx.ssa.SsaInsn.Visitor
            public void visitMoveInsn(NormalSsaInsn normalSsaInsn) {
            }

            @Override // mod.agus.jcoderz.dx.ssa.SsaInsn.Visitor
            public void visitPhiInsn(PhiInsn phiInsn) {
            }

            @Override // mod.agus.jcoderz.dx.ssa.SsaInsn.Visitor
            public void visitNonMoveInsn(NormalSsaInsn normalSsaInsn) {
                Rop opcode = normalSsaInsn.getOriginalRopInsn().getOpcode();
                RegisterSpecList sources = normalSsaInsn.getSources();
                if (LiteralOpUpgrader.this.tryReplacingWithConstant(normalSsaInsn) || sources.size() != 2) {
                    return;
                }
                if (opcode.getBranchingness() == 4) {
                    if (LiteralOpUpgrader.isConstIntZeroOrKnownNull(sources.get(0))) {
                        LiteralOpUpgrader.this.replacePlainInsn(normalSsaInsn, sources.withoutFirst(), RegOps.flippedIfOpcode(opcode.getOpcode()), null);
                    } else if (LiteralOpUpgrader.isConstIntZeroOrKnownNull(sources.get(1))) {
                        LiteralOpUpgrader.this.replacePlainInsn(normalSsaInsn, sources.withoutLast(), opcode.getOpcode(), null);
                    }
                } else if (advice.hasConstantOperation(opcode, sources.get(0), sources.get(1))) {
                    normalSsaInsn.upgradeToLiteral();
                } else if (opcode.isCommutative() && advice.hasConstantOperation(opcode, sources.get(1), sources.get(0))) {
                    normalSsaInsn.setNewSources(RegisterSpecList.make(sources.get(1), sources.get(0)));
                    normalSsaInsn.upgradeToLiteral();
                }
            }
        });
    }

    private boolean tryReplacingWithConstant(NormalSsaInsn normalSsaInsn) {
        Rop opcode = normalSsaInsn.getOriginalRopInsn().getOpcode();
        RegisterSpec result = normalSsaInsn.getResult();
        if (!(result == null || this.ssaMeth.isRegALocal(result) || opcode.getOpcode() == 5)) {
            TypeBearer typeBearer = normalSsaInsn.getResult().getTypeBearer();
            if (typeBearer.isConstant() && typeBearer.getBasicType() == 6) {
                replacePlainInsn(normalSsaInsn, RegisterSpecList.EMPTY, 5, (Constant) typeBearer);
                if (opcode.getOpcode() == 56) {
                    ArrayList<SsaInsn> insns = this.ssaMeth.getBlocks().get(normalSsaInsn.getBlock().getPredecessors().nextSetBit(0)).getInsns();
                    replacePlainInsn((NormalSsaInsn) insns.get(insns.size() - 1), RegisterSpecList.EMPTY, 6, null);
                }
                return true;
            }
        }
        return false;
    }

    private void replacePlainInsn(NormalSsaInsn normalSsaInsn, RegisterSpecList registerSpecList, int i, Constant constant) {
        Insn plainCstInsn;
        Insn originalRopInsn = normalSsaInsn.getOriginalRopInsn();
        Rop ropFor = Rops.ropFor(i, normalSsaInsn.getResult(), registerSpecList, constant);
        if (constant == null) {
            plainCstInsn = new PlainInsn(ropFor, originalRopInsn.getPosition(), normalSsaInsn.getResult(), registerSpecList);
        } else {
            plainCstInsn = new PlainCstInsn(ropFor, originalRopInsn.getPosition(), normalSsaInsn.getResult(), registerSpecList, constant);
        }
        NormalSsaInsn normalSsaInsn2 = new NormalSsaInsn(plainCstInsn, normalSsaInsn.getBlock());
        ArrayList<SsaInsn> insns = normalSsaInsn.getBlock().getInsns();
        this.ssaMeth.onInsnRemoved(normalSsaInsn);
        insns.set(insns.lastIndexOf(normalSsaInsn), normalSsaInsn2);
        this.ssaMeth.onInsnAdded(normalSsaInsn2);
    }
}
