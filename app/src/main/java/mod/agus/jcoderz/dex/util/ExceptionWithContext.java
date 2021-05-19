package mod.agus.jcoderz.dex.util;

import java.io.PrintStream;
import java.io.PrintWriter;

public class ExceptionWithContext extends RuntimeException {
    private final StringBuffer context;

    public ExceptionWithContext(String str) {
        this(str, null);
    }

    public ExceptionWithContext(Throwable th) {
        this(null, th);
    }

    public ExceptionWithContext(String str, Throwable th) {
        super(str == null ? th != null ? th.getMessage() : null : str, th);
        if (th instanceof ExceptionWithContext) {
            String stringBuffer = ((ExceptionWithContext) th).context.toString();
            this.context = new StringBuffer(stringBuffer.length() + 200);
            this.context.append(stringBuffer);
            return;
        }
        this.context = new StringBuffer(200);
    }

    public static ExceptionWithContext withContext(Throwable th, String str) {
        ExceptionWithContext exceptionWithContext;
        if (th instanceof ExceptionWithContext) {
            exceptionWithContext = (ExceptionWithContext) th;
        } else {
            exceptionWithContext = new ExceptionWithContext(th);
        }
        exceptionWithContext.addContext(str);
        return exceptionWithContext;
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintStream printStream) {
        super.printStackTrace(printStream);
        printStream.println(this.context);
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintWriter printWriter) {
        super.printStackTrace(printWriter);
        printWriter.println(this.context);
    }

    public void addContext(String str) {
        if (str == null) {
            throw new NullPointerException("str == null");
        }
        this.context.append(str);
        if (!str.endsWith("\n")) {
            this.context.append('\n');
        }
    }

    public String getContext() {
        return this.context.toString();
    }

    public void printContext(PrintStream printStream) {
        printStream.println(getMessage());
        printStream.print(this.context);
    }

    public void printContext(PrintWriter printWriter) {
        printWriter.println(getMessage());
        printWriter.print(this.context);
    }
}
