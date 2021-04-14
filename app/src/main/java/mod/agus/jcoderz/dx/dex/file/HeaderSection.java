package mod.agus.jcoderz.dx.dex.file;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import mod.agus.jcoderz.dx.rop.cst.Constant;

public final class HeaderSection extends UniformItemSection {
    private final List<HeaderItem> list;

    public HeaderSection(DexFile dexFile) {
        super(null, dexFile, 4);
        HeaderItem headerItem = new HeaderItem();
        headerItem.setIndex(0);
        this.list = Collections.singletonList(headerItem);
    }

    @Override // mod.agus.jcoderz.dx.dex.file.UniformItemSection
    public IndexedItem get(Constant constant) {
        return null;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Section
    public Collection<? extends Item> items() {
        return this.list;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.UniformItemSection
    protected void orderItems() {
    }
}
