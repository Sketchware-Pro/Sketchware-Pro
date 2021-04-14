package mod.agus.jcoderz.dx.cf.iface;

import mod.agus.jcoderz.dex.util.ExceptionWithContext;

public class ParseException extends ExceptionWithContext {
    public ParseException(String str) {
        super(str);
    }

    public ParseException(Throwable th) {
        super(th);
    }

    public ParseException(String str, Throwable th) {
        super(str, th);
    }
}
