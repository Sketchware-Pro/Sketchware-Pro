package mod.agus.jcoderz.dex;

import mod.agus.jcoderz.dex.util.ExceptionWithContext;

public class DexException extends ExceptionWithContext {
    public DexException(String str) {
        super(str);
    }

    public DexException(Throwable th) {
        super(th);
    }
}
