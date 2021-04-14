package mod.agus.jcoderz.dx.ssa;

import mod.agus.jcoderz.dx.util.BitIntSet;
import mod.agus.jcoderz.dx.util.IntSet;
import mod.agus.jcoderz.dx.util.ListIntSet;

public final class SetFactory {
    private static final int DOMFRONT_SET_THRESHOLD_SIZE = 3072;
    private static final int INTERFERENCE_SET_THRESHOLD_SIZE = 3072;
    private static final int LIVENESS_SET_THRESHOLD_SIZE = 3072;

    static IntSet makeDomFrontSet(int i) {
        if (i <= 3072) {
            return new BitIntSet(i);
        }
        return new ListIntSet();
    }

    public static IntSet makeInterferenceSet(int i) {
        if (i <= 3072) {
            return new BitIntSet(i);
        }
        return new ListIntSet();
    }

    static IntSet makeLivenessSet(int i) {
        if (i <= 3072) {
            return new BitIntSet(i);
        }
        return new ListIntSet();
    }
}
