package mod.agus.jcoderz.dx.io.instructions;

public abstract class BaseCodeCursor implements CodeCursor {
    private final AddressMap baseAddressMap = new AddressMap();
    private int cursor = 0;

    @Override // mod.agus.jcoderz.dx.io.instructions.CodeCursor
    public final int cursor() {
        return this.cursor;
    }

    @Override // mod.agus.jcoderz.dx.io.instructions.CodeCursor
    public final int baseAddressForCursor() {
        int i = this.baseAddressMap.get(this.cursor);
        return i >= 0 ? i : this.cursor;
    }

    @Override // mod.agus.jcoderz.dx.io.instructions.CodeCursor
    public final void setBaseAddress(int i, int i2) {
        this.baseAddressMap.put(i, i2);
    }

    public final void advance(int i) {
        this.cursor += i;
    }
}
