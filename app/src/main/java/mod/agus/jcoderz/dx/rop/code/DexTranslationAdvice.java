package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.rop.type.Type;

public final class DexTranslationAdvice implements TranslationAdvice {
    private static final int MIN_INVOKE_IN_ORDER = 6;
    public static final DexTranslationAdvice NO_SOURCES_IN_ORDER = new DexTranslationAdvice(true);
    public static final DexTranslationAdvice THE_ONE = new DexTranslationAdvice();
    private final boolean disableSourcesInOrder;

    private DexTranslationAdvice() {
        this.disableSourcesInOrder = false;
    }

    private DexTranslationAdvice(boolean z) {
        this.disableSourcesInOrder = z;
    }

    @Override // mod.agus.jcoderz.dx.rop.code.TranslationAdvice
    public boolean hasConstantOperation(Rop rop, RegisterSpec registerSpec, RegisterSpec registerSpec2) {
        if (registerSpec.getType() != Type.INT) {
            return false;
        }
        if (registerSpec2.getTypeBearer() instanceof CstInteger) {
            CstInteger cstInteger = (CstInteger) registerSpec2.getTypeBearer();
            switch (rop.getOpcode()) {
                case 14:
                case 16:
                case 17:
                case 18:
                case 20:
                case 21:
                case 22:
                    return cstInteger.fitsIn16Bits();
                case 15:
                    return CstInteger.make(-cstInteger.getValue()).fitsIn16Bits();
                case 19:
                default:
                    return false;
                case 23:
                case 24:
                case 25:
                    return cstInteger.fitsIn8Bits();
            }
        } else if (!(registerSpec.getTypeBearer() instanceof CstInteger) || rop.getOpcode() != 15) {
            return false;
        } else {
            return ((CstInteger) registerSpec.getTypeBearer()).fitsIn16Bits();
        }
    }

    @Override // mod.agus.jcoderz.dx.rop.code.TranslationAdvice
    public boolean requiresSourcesInOrder(Rop rop, RegisterSpecList registerSpecList) {
        return !this.disableSourcesInOrder && rop.isCallLike() && totalRopWidth(registerSpecList) >= 6;
    }

    private int totalRopWidth(RegisterSpecList registerSpecList) {
        int size = registerSpecList.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            i += registerSpecList.get(i2).getCategory();
        }
        return i;
    }

    @Override // mod.agus.jcoderz.dx.rop.code.TranslationAdvice
    public int getMaxOptimalRegisterCount() {
        return 16;
    }
}
