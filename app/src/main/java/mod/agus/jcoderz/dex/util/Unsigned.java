package mod.agus.jcoderz.dex.util;

public final class Unsigned {
    private Unsigned() {
    }

    public static int compare(short s, short s2) {
        if (s == s2) {
            return 0;
        }
        return (s & 65535) < (65535 & s2) ? -1 : 1;
    }

    public static int compare(int i, int i2) {
        if (i == i2) {
            return 0;
        }
        return (((long) i) & 4294967295L) < (((long) i2) & 4294967295L) ? -1 : 1;
    }
}
