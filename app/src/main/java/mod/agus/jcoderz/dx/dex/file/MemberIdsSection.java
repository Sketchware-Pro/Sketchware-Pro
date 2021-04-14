package mod.agus.jcoderz.dx.dex.file;

import java.util.Formatter;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import mod.agus.jcoderz.dex.DexIndexOverflowException;
import mod.agus.jcoderz.dx.command.dexer.Main;
import mod.agus.jcoderz.dx.rop.code.AccessFlags;

public abstract class MemberIdsSection extends UniformItemSection {
    public MemberIdsSection(String str, DexFile dexFile) {
        super(str, dexFile, 4);
    }

    @Override // mod.agus.jcoderz.dx.dex.file.UniformItemSection
    protected void orderItems() {
        int i = 0;
        if (items().size() > 65536) {
            throw new DexIndexOverflowException(getTooManyMembersMessage());
        }
        Iterator<? extends Item> it = items().iterator();
        while (it.hasNext()) {
            ((MemberIdItem) it.next()).setIndex(i);
            i++;
        }
    }

    private String getTooManyMembersMessage() {
        TreeMap treeMap = new TreeMap();
        Iterator<? extends Item> it = items().iterator();
        while (it.hasNext()) {
            String packageName = ((MemberIdItem) it.next()).getDefiningClass().getPackageName();
            AtomicInteger atomicInteger = (AtomicInteger) treeMap.get(packageName);
            if (atomicInteger == null) {
                atomicInteger = new AtomicInteger();
                treeMap.put(packageName, atomicInteger);
            }
            atomicInteger.incrementAndGet();
        }
        Formatter formatter = new Formatter();
        try {
            formatter.format("Too many %s references: %d; max is %d.%n" + Main.getTooManyIdsErrorMessage() + "%n" + "References by package:", this instanceof MethodIdsSection ? "method" : "field", Integer.valueOf(items().size()), Integer.valueOf((int) AccessFlags.ACC_CONSTRUCTOR));
            for (Iterator iterator = treeMap.entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry entry = (Map.Entry) iterator.next();
                formatter.format("%n%6d %s", Integer.valueOf(((AtomicInteger) entry.getValue()).get()), entry.getKey());
            }
            return formatter.toString();
        } finally {
            formatter.close();
        }
    }
}
