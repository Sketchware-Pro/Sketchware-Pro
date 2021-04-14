package mod.agus.jcoderz.dx.dex.cf;

import java.io.PrintStream;

public class CfOptions {
    public String dontOptimizeListFile = null;
    public boolean localInfo = false;
    public boolean optimize = false;
    public String optimizeListFile = null;
    public int positionInfo = 2;
    public boolean statistics;
    public boolean strictNameCheck = true;
    public PrintStream warn = System.err;
}
