package mod.agus.jcoderz.multidex;

import java.io.IOException;
import java.io.InputStream;

public interface ClassPathElement {
    char SEPARATOR_CHAR = '/';

    void close() throws IOException;

    Iterable<String> list();

    InputStream open(String str) throws IOException;
}
