package mod.agus.jcoderz.dx.dex;

import mod.agus.jcoderz.dex.DexFormat;

public class DexOptions {
    public static final boolean ALIGN_64BIT_REGS_SUPPORT = true;
    public boolean ALIGN_64BIT_REGS_IN_OUTPUT_FINISHER = true;
    public boolean forceJumbo = false;
    public int targetApiLevel = 13;

    public String getMagic() {
        return DexFormat.apiToMagic(this.targetApiLevel);
    }
}
