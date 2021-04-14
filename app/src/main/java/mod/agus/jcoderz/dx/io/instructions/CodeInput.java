package mod.agus.jcoderz.dx.io.instructions;

import java.io.EOFException;

public interface CodeInput extends CodeCursor {
    boolean hasMore();

    int read() throws EOFException;

    int readInt() throws EOFException;

    long readLong() throws EOFException;
}
