package mod.agus.jcoderz.dx.command;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class DxConsole {
    public static PrintStream err = System.err;
    public static final PrintStream noop = new PrintStream(new OutputStream() {

        @Override // java.io.OutputStream
        public void write(int i) throws IOException {
        }
    });
    public static PrintStream out = System.out;
}
