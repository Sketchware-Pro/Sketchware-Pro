package mod.agus.jcoderz.dx.io.instructions;

public interface CodeCursor {
    int baseAddressForCursor();

    int cursor();

    void setBaseAddress(int i, int i2);
}
