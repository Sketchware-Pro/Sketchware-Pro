package mod.agus.jcoderz.dx.dex.file;

import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;
import mod.agus.jcoderz.dx.dex.code.CatchHandlerList;
import mod.agus.jcoderz.dx.dex.code.CatchTable;
import mod.agus.jcoderz.dx.dex.code.DalvCode;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.ByteArrayAnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class CatchStructs {
    private static final int TRY_ITEM_WRITE_SIZE = 8;
    private final DalvCode code;
    private int encodedHandlerHeaderSize = 0;
    private byte[] encodedHandlers = null;
    private TreeMap<CatchHandlerList, Integer> handlerOffsets = null;
    private CatchTable table = null;

    public CatchStructs(DalvCode dalvCode) {
        this.code = dalvCode;
    }

    private void finishProcessingIfNecessary() {
        if (this.table == null) {
            this.table = this.code.getCatches();
        }
    }

    public int triesSize() {
        finishProcessingIfNecessary();
        return this.table.size();
    }

    public void debugPrint(PrintWriter printWriter, String str) {
        annotateEntries(str, printWriter, null);
    }

    public void encode(DexFile dexFile) {
        int i;
        finishProcessingIfNecessary();
        TypeIdsSection typeIds = dexFile.getTypeIds();
        int size = this.table.size();
        this.handlerOffsets = new TreeMap<>();
        for (int i2 = 0; i2 < size; i2++) {
            this.handlerOffsets.put(this.table.get(i2).getHandlers(), null);
        }
        if (this.handlerOffsets.size() > 65535) {
            throw new UnsupportedOperationException("too many catch handlers");
        }
        ByteArrayAnnotatedOutput byteArrayAnnotatedOutput = new ByteArrayAnnotatedOutput();
        this.encodedHandlerHeaderSize = byteArrayAnnotatedOutput.writeUleb128(this.handlerOffsets.size());
        for (Map.Entry<CatchHandlerList, Integer> entry : this.handlerOffsets.entrySet()) {
            CatchHandlerList key = entry.getKey();
            int size2 = key.size();
            boolean catchesAll = key.catchesAll();
            entry.setValue(Integer.valueOf(byteArrayAnnotatedOutput.getCursor()));
            if (catchesAll) {
                byteArrayAnnotatedOutput.writeSleb128(-(size2 - 1));
                i = size2 - 1;
            } else {
                byteArrayAnnotatedOutput.writeSleb128(size2);
                i = size2;
            }
            for (int i3 = 0; i3 < i; i3++) {
                CatchHandlerList.Entry entry2 = key.get(i3);
                byteArrayAnnotatedOutput.writeUleb128(typeIds.indexOf(entry2.getExceptionType()));
                byteArrayAnnotatedOutput.writeUleb128(entry2.getHandler());
            }
            if (catchesAll) {
                byteArrayAnnotatedOutput.writeUleb128(key.get(i).getHandler());
            }
        }
        this.encodedHandlers = byteArrayAnnotatedOutput.toByteArray();
    }

    public int writeSize() {
        return (triesSize() * 8) + this.encodedHandlers.length;
    }

    public void writeTo(DexFile dexFile, AnnotatedOutput annotatedOutput) {
        finishProcessingIfNecessary();
        if (annotatedOutput.annotates()) {
            annotateEntries("  ", null, annotatedOutput);
        }
        int size = this.table.size();
        for (int i = 0; i < size; i++) {
            CatchTable.Entry entry = this.table.get(i);
            int start = entry.getStart();
            int end = entry.getEnd();
            int i2 = end - start;
            if (i2 >= 65536) {
                throw new UnsupportedOperationException("bogus exception range: " + Hex.u4(start) + ".." + Hex.u4(end));
            }
            annotatedOutput.writeInt(start);
            annotatedOutput.writeShort(i2);
            annotatedOutput.writeShort(this.handlerOffsets.get(entry.getHandlers()).intValue());
        }
        annotatedOutput.write(this.encodedHandlers);
    }

    private void annotateEntries(String str, PrintWriter printWriter, AnnotatedOutput annotatedOutput) {
        int i = 0;
        finishProcessingIfNecessary();
        boolean z = annotatedOutput != null;
        int i2 = z ? 6 : 0;
        int i3 = z ? 2 : 0;
        int size = this.table.size();
        String str2 = String.valueOf(str) + "  ";
        if (z) {
            annotatedOutput.annotate(0, String.valueOf(str) + "tries:");
        } else {
            printWriter.println(String.valueOf(str) + "tries:");
        }
        for (int i4 = 0; i4 < size; i4++) {
            CatchTable.Entry entry = this.table.get(i4);
            CatchHandlerList handlers = entry.getHandlers();
            String str3 = String.valueOf(str2) + "try " + Hex.u2or4(entry.getStart()) + ".." + Hex.u2or4(entry.getEnd());
            String human = handlers.toHuman(str2, "");
            if (z) {
                annotatedOutput.annotate(i2, str3);
                annotatedOutput.annotate(i3, human);
            } else {
                printWriter.println(str3);
                printWriter.println(human);
            }
        }
        if (z) {
            annotatedOutput.annotate(0, String.valueOf(str) + "handlers:");
            annotatedOutput.annotate(this.encodedHandlerHeaderSize, String.valueOf(str2) + "size: " + Hex.u2(this.handlerOffsets.size()));
            CatchHandlerList catchHandlerList = null;
            for (Map.Entry<CatchHandlerList, Integer> entry2 : this.handlerOffsets.entrySet()) {
                CatchHandlerList key = entry2.getKey();
                int intValue = entry2.getValue().intValue();
                if (catchHandlerList != null) {
                    annotateAndConsumeHandlers(catchHandlerList, i, intValue - i, str2, printWriter, annotatedOutput);
                }
                catchHandlerList = key;
                i = intValue;
            }
            annotateAndConsumeHandlers(catchHandlerList, i, this.encodedHandlers.length - i, str2, printWriter, annotatedOutput);
        }
    }

    private static void annotateAndConsumeHandlers(CatchHandlerList catchHandlerList, int i, int i2, String str, PrintWriter printWriter, AnnotatedOutput annotatedOutput) {
        String human = catchHandlerList.toHuman(str, String.valueOf(Hex.u2(i)) + ": ");
        if (printWriter != null) {
            printWriter.println(human);
        }
        annotatedOutput.annotate(i2, human);
    }
}
