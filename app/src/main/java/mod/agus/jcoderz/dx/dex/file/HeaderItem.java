package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class HeaderItem extends IndexedItem {
    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public ItemType itemType() {
        return ItemType.TYPE_HEADER_ITEM;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public int writeSize() {
        return 112;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public void addContents(DexFile dexFile) {
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public void writeTo(DexFile dexFile, AnnotatedOutput annotatedOutput) {
        int fileOffset = dexFile.getMap().getFileOffset();
        Section firstDataSection = dexFile.getFirstDataSection();
        Section lastDataSection = dexFile.getLastDataSection();
        int fileOffset2 = firstDataSection.getFileOffset();
        int fileOffset3 = (lastDataSection.getFileOffset() + lastDataSection.writeSize()) - fileOffset2;
        String magic = dexFile.getDexOptions().getMagic();
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(8, "magic: " + new CstString(magic).toQuoted());
            annotatedOutput.annotate(4, "checksum");
            annotatedOutput.annotate(20, "signature");
            annotatedOutput.annotate(4, "file_size:       " + Hex.u4(dexFile.getFileSize()));
            annotatedOutput.annotate(4, "header_size:     " + Hex.u4(112));
            annotatedOutput.annotate(4, "endian_tag:      " + Hex.u4(305419896));
            annotatedOutput.annotate(4, "link_size:       0");
            annotatedOutput.annotate(4, "link_off:        0");
            annotatedOutput.annotate(4, "map_off:         " + Hex.u4(fileOffset));
        }
        for (int i = 0; i < 8; i++) {
            annotatedOutput.writeByte(magic.charAt(i));
        }
        annotatedOutput.writeZeroes(24);
        annotatedOutput.writeInt(dexFile.getFileSize());
        annotatedOutput.writeInt(112);
        annotatedOutput.writeInt(305419896);
        annotatedOutput.writeZeroes(8);
        annotatedOutput.writeInt(fileOffset);
        dexFile.getStringIds().writeHeaderPart(annotatedOutput);
        dexFile.getTypeIds().writeHeaderPart(annotatedOutput);
        dexFile.getProtoIds().writeHeaderPart(annotatedOutput);
        dexFile.getFieldIds().writeHeaderPart(annotatedOutput);
        dexFile.getMethodIds().writeHeaderPart(annotatedOutput);
        dexFile.getClassDefs().writeHeaderPart(annotatedOutput);
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(4, "data_size:       " + Hex.u4(fileOffset3));
            annotatedOutput.annotate(4, "data_off:        " + Hex.u4(fileOffset2));
        }
        annotatedOutput.writeInt(fileOffset3);
        annotatedOutput.writeInt(fileOffset2);
    }
}
