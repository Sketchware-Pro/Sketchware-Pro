package mod.agus.jcoderz.dx.dex.cf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashSet;

import mod.agus.jcoderz.dx.rop.code.RopMethod;
import mod.agus.jcoderz.dx.rop.code.TranslationAdvice;
import mod.agus.jcoderz.dx.ssa.Optimizer;

public class OptimizerOptions {
    private static HashSet<String> dontOptimizeList;
    private static HashSet<String> optimizeList;
    private static boolean optimizeListsLoaded;

    private OptimizerOptions() {
    }

    public static void loadOptimizeLists(String str, String str2) {
        if (!optimizeListsLoaded) {
            if (str == null || str2 == null) {
                if (str != null) {
                    optimizeList = loadStringsFromFile(str);
                }
                if (str2 != null) {
                    dontOptimizeList = loadStringsFromFile(str2);
                }
                optimizeListsLoaded = true;
                return;
            }
            throw new RuntimeException("optimize and don't optimize lists  are mutually exclusive.");
        }
    }

    private static HashSet<String> loadStringsFromFile(String str) {
        HashSet<String> hashSet = new HashSet<>();
        try {
            FileReader fileReader = new FileReader(str);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    fileReader.close();
                    return hashSet;
                }
                hashSet.add(readLine);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error with optimize list: " + str, e);
        }
    }

    public static void compareOptimizerStep(RopMethod ropMethod, int i, boolean z, CfOptions cfOptions, TranslationAdvice translationAdvice, RopMethod ropMethod2) {
        EnumSet allOf = EnumSet.allOf(Optimizer.OptionalStep.class);
        allOf.remove(Optimizer.OptionalStep.CONST_COLLECTOR);
        RopMethod optimize = Optimizer.optimize(ropMethod, i, z, cfOptions.localInfo, translationAdvice, allOf);
        int effectiveInstructionCount = ropMethod2.getBlocks().getEffectiveInstructionCount();
        int effectiveInstructionCount2 = optimize.getBlocks().getEffectiveInstructionCount();
        System.err.printf("optimize step regs:(%d/%d/%.2f%%) insns:(%d/%d/%.2f%%)\n", Integer.valueOf(ropMethod2.getBlocks().getRegCount()), Integer.valueOf(optimize.getBlocks().getRegCount()), Double.valueOf(100.0d * ((double) (((float) (optimize.getBlocks().getRegCount() - ropMethod2.getBlocks().getRegCount())) / ((float) optimize.getBlocks().getRegCount())))), Integer.valueOf(effectiveInstructionCount), Integer.valueOf(effectiveInstructionCount2), Double.valueOf(100.0d * ((double) (((float) (effectiveInstructionCount2 - effectiveInstructionCount)) / ((float) effectiveInstructionCount2)))));
    }

    public static boolean shouldOptimize(String str) {
        if (optimizeList != null) {
            return optimizeList.contains(str);
        }
        return dontOptimizeList == null || !dontOptimizeList.contains(str);
    }
}
