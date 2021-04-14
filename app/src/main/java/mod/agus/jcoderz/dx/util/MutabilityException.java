package mod.agus.jcoderz.dx.util;

import mod.agus.jcoderz.dex.util.ExceptionWithContext;

public class MutabilityException extends ExceptionWithContext {
    public MutabilityException(String str) {
        super(str);
    }

    public MutabilityException(Throwable th) {
        super(th);
    }

    public MutabilityException(String str, Throwable th) {
        super(str, th);
    }
}
