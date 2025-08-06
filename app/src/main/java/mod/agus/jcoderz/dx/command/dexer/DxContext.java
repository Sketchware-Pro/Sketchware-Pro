package mod.agus.jcoderz.dx.command.dexer;

import mod.agus.jcoderz.dx.dex.cf.CodeStatistics;
import mod.agus.jcoderz.dx.dex.cf.OptimizerOptions;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * State used by a single invocation of {@link Main}.
 */
public class DxContext {
    public final CodeStatistics codeStatistics = new CodeStatistics();
    public final OptimizerOptions optimizerOptions = new OptimizerOptions();
    public final PrintStream out;
    public final PrintStream err;

    @SuppressWarnings("IOResourceOpenedButNotSafelyClosed")
    final PrintStream noop = new PrintStream(new OutputStream() {
        @Override
        public void write(int b) throws IOException {
            // noop;
        }
    });

    public DxContext(OutputStream out, OutputStream err) {
        this.out = new PrintStream(out);
        this.err = new PrintStream(err);
    }

    public DxContext() {
        this(System.out, System.err);
    }
}
