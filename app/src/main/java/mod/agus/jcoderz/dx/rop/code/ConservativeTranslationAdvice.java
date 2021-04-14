package mod.agus.jcoderz.dx.rop.code;

public final class ConservativeTranslationAdvice implements TranslationAdvice {
    public static final ConservativeTranslationAdvice THE_ONE = new ConservativeTranslationAdvice();

    private ConservativeTranslationAdvice() {
    }

    @Override // mod.agus.jcoderz.dx.rop.code.TranslationAdvice
    public boolean hasConstantOperation(Rop rop, RegisterSpec registerSpec, RegisterSpec registerSpec2) {
        return false;
    }

    @Override // mod.agus.jcoderz.dx.rop.code.TranslationAdvice
    public boolean requiresSourcesInOrder(Rop rop, RegisterSpecList registerSpecList) {
        return false;
    }

    @Override // mod.agus.jcoderz.dx.rop.code.TranslationAdvice
    public int getMaxOptimalRegisterCount() {
        return Integer.MAX_VALUE;
    }
}
