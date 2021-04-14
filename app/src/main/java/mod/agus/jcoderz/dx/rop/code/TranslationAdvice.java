package mod.agus.jcoderz.dx.rop.code;

public interface TranslationAdvice {
    int getMaxOptimalRegisterCount();

    boolean hasConstantOperation(Rop rop, RegisterSpec registerSpec, RegisterSpec registerSpec2);

    boolean requiresSourcesInOrder(Rop rop, RegisterSpecList registerSpecList);
}
