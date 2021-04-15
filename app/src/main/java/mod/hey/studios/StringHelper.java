package mod.hey.studios;

import android.os.Build;

public class StringHelper {
    private static void requireNonNull(Object obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
    }

    public static String join(CharSequence charSequence, CharSequence[] charSequenceArr) {
        if (Build.VERSION.SDK_INT >= 26) {
            return String.join(charSequence, charSequenceArr);
        }
        requireNonNull(charSequence);
        requireNonNull(charSequenceArr);
        StringBuilder sb = new StringBuilder();
        for (CharSequence sequence : charSequenceArr) {
            sb.append(sequence);
            sb.append(charSequence);
        }
        sb.setLength(sb.length() - charSequence.length());
        return sb.toString();
    }

    public static String join(CharSequence charSequence, Iterable<? extends CharSequence> iterable) {
        if (Build.VERSION.SDK_INT >= 26) {
            return String.join(charSequence, iterable);
        }
        requireNonNull(charSequence);
        requireNonNull(iterable);
        StringBuilder sb = new StringBuilder();
        for (CharSequence charSequence2 : iterable) {
            sb.append(charSequence2);
            sb.append(charSequence);
        }
        sb.setLength(sb.length() - charSequence.length());
        return sb.toString();
    }
}
