package mod.agus.jcoderz.dx.ssa;

import java.util.HashSet;
import java.util.List;
import mod.agus.jcoderz.dx.rop.code.CstInsn;
import mod.agus.jcoderz.dx.rop.code.LocalItem;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.ssa.SsaInsn;

public class MoveParamCombiner {
    private final SsaMethod ssaMeth;

    public static void process(SsaMethod ssaMethod) {
        new MoveParamCombiner(ssaMethod).run();
    }

    private MoveParamCombiner(SsaMethod ssaMethod) {
        this.ssaMeth = ssaMethod;
    }

    private void run() {
        final RegisterSpec[] registerSpecArr = new RegisterSpec[this.ssaMeth.getParamWidth()];
        final HashSet hashSet = new HashSet();
        this.ssaMeth.forEachInsn(new SsaInsn.Visitor() {

            private Object RegisterMapper;

            @Override // mod.agus.jcoderz.dx.ssa.SsaInsn.Visitor
            public void visitMoveInsn(NormalSsaInsn normalSsaInsn) {
            }

            @Override // mod.agus.jcoderz.dx.ssa.SsaInsn.Visitor
            public void visitPhiInsn(PhiInsn phiInsn) {
            }

            @Override // mod.agus.jcoderz.dx.ssa.SsaInsn.Visitor
            public void visitNonMoveInsn(NormalSsaInsn normalSsaInsn) {
                if (normalSsaInsn.getOpcode().getOpcode() == 3) {
                    int paramIndex = MoveParamCombiner.this.getParamIndex(normalSsaInsn);
                    if (registerSpecArr[paramIndex] == null) {
                        registerSpecArr[paramIndex] = normalSsaInsn.getResult();
                        return;
                    }
                    final RegisterSpec registerSpec = registerSpecArr[paramIndex];
                    final RegisterSpec result = normalSsaInsn.getResult();
                    LocalItem localItem = registerSpec.getLocalItem();
                    LocalItem localItem2 = result.getLocalItem();
                    if (localItem != null) {
                        if (localItem2 == null) {
                            localItem2 = localItem;
                        } else if (localItem.equals(localItem2)) {
                            localItem2 = localItem;
                        } else {
                            return;
                        }
                    }
                    MoveParamCombiner.this.ssaMeth.getDefinitionForRegister(registerSpec.getReg()).setResultLocal(localItem2);
                    RegisterMapper registerMapper = new RegisterMapper() {
                        /* class mod.agus.jcoderz.dx.ssa.MoveParamCombiner.AnonymousClass1.AnonymousClass1 */

                        @Override // mod.agus.jcoderz.dx.ssa.RegisterMapper
                        public int getNewRegisterCount() {
                            return MoveParamCombiner.this.ssaMeth.getRegCount();
                        }

                        @Override // mod.agus.jcoderz.dx.ssa.RegisterMapper
                        public RegisterSpec map(RegisterSpec registerSpec) {
                            if (registerSpec.getReg() == result.getReg()) {
                                return registerSpec;
                            }
                            return registerSpec;
                        }
                    };
                    List<SsaInsn> useListForRegister = MoveParamCombiner.this.ssaMeth.getUseListForRegister(result.getReg());
                    for (int size = useListForRegister.size() - 1; size >= 0; size--) {
                        useListForRegister.get(size).mapSourceRegisters((mod.agus.jcoderz.dx.ssa.RegisterMapper) RegisterMapper);
                    }
                    hashSet.add(normalSsaInsn);
                }
            }
        });
        this.ssaMeth.deleteInsns(hashSet);
    }

    private int getParamIndex(NormalSsaInsn normalSsaInsn) {
        return ((CstInteger) ((CstInsn) normalSsaInsn.getOriginalRopInsn()).getConstant()).getValue();
    }
}
