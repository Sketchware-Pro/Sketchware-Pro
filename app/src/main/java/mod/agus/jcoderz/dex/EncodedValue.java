package mod.agus.jcoderz.dex;

import mod.agus.jcoderz.dex.util.ByteArrayByteInput;
import mod.agus.jcoderz.dex.util.ByteInput;

public final class EncodedValue implements Comparable<EncodedValue> {
    private final byte[] data;

    public EncodedValue(byte[] bArr) {
        this.data = bArr;
    }

    public ByteInput asByteInput() {
        return new ByteArrayByteInput(this.data);
    }

    public byte[] getBytes() {
        return this.data;
    }

    public void writeTo(Dex.Section section) {
        section.write(this.data);
    }

    public int compareTo(EncodedValue encodedValue) {
        int min = Math.min(this.data.length, encodedValue.data.length);
        for (int i = 0; i < min; i++) {
            if (this.data[i] != encodedValue.data[i]) {
                return (this.data[i] & 255) - (encodedValue.data[i] & 255);
            }
        }
        return this.data.length - encodedValue.data.length;
    }

    public String toString() {
        return Integer.toHexString(this.data[0] & 255) + "...(" + this.data.length + ")";
    }
}
