package mod.agus.jcoderz.dx.dex.cf;

import java.io.PrintStream;

import mod.agus.jcoderz.dx.dex.code.DalvCode;
import mod.agus.jcoderz.dx.rop.code.RopMethod;

public final class CodeStatistics {
    private static final boolean DEBUG = false;
    public static int dexRunningDeltaInsns = 0;
    public static int dexRunningDeltaRegisters = 0;
    public static int dexRunningTotalInsns = 0;
    public static int runningDeltaInsns = 0;
    public static int runningDeltaRegisters = 0;
    public static int runningOriginalBytes = 0;
    public static int runningTotalInsns = 0;

    private CodeStatistics() {
    }

    public static void updateOriginalByteCount(int i) {
        runningOriginalBytes += i;
    }

    public static void updateDexStatistics(DalvCode dalvCode, DalvCode dalvCode2) {
        dexRunningDeltaInsns += dalvCode2.getInsns().codeSize() - dalvCode.getInsns().codeSize();
        dexRunningDeltaRegisters += dalvCode2.getInsns().getRegistersSize() - dalvCode.getInsns().getRegistersSize();
        dexRunningTotalInsns += dalvCode2.getInsns().codeSize();
    }

    public static void updateRopStatistics(RopMethod ropMethod, RopMethod ropMethod2) {
        int effectiveInstructionCount = ropMethod.getBlocks().getEffectiveInstructionCount();
        int regCount = ropMethod.getBlocks().getRegCount();
        int effectiveInstructionCount2 = ropMethod2.getBlocks().getEffectiveInstructionCount();
        runningDeltaInsns = (effectiveInstructionCount2 - effectiveInstructionCount) + runningDeltaInsns;
        runningDeltaRegisters += ropMethod2.getBlocks().getRegCount() - regCount;
        runningTotalInsns += effectiveInstructionCount2;
    }

    public static void dumpStatistics(PrintStream printStream) {
        printStream.printf("Optimizer Delta Rop Insns: %d total: %d (%.2f%%) Delta Registers: %d\n", Integer.valueOf(runningDeltaInsns), Integer.valueOf(runningTotalInsns), Double.valueOf(((double) (((float) runningDeltaInsns) / ((float) (runningTotalInsns + Math.abs(runningDeltaInsns))))) * 100.0d), Integer.valueOf(runningDeltaRegisters));
        printStream.printf("Optimizer Delta Dex Insns: Insns: %d total: %d (%.2f%%) Delta Registers: %d\n", Integer.valueOf(dexRunningDeltaInsns), Integer.valueOf(dexRunningTotalInsns), Double.valueOf(((double) (((float) dexRunningDeltaInsns) / ((float) (dexRunningTotalInsns + Math.abs(dexRunningDeltaInsns))))) * 100.0d), Integer.valueOf(dexRunningDeltaRegisters));
        printStream.printf("Original bytecode byte count: %d\n", Integer.valueOf(runningOriginalBytes));
    }
}
