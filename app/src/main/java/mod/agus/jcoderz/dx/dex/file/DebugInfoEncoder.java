package mod.agus.jcoderz.dx.dex.file;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import mod.agus.jcoderz.dex.util.ExceptionWithContext;
import mod.agus.jcoderz.dx.dex.code.LocalList;
import mod.agus.jcoderz.dx.dex.code.PositionList;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Prototype;
import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.ByteArrayAnnotatedOutput;

public final class DebugInfoEncoder {
    private static final boolean DEBUG = false;
    private int address = 0;
    private AnnotatedOutput annotateTo;
    private final int codeSize;
    private PrintWriter debugPrint;
    private final Prototype desc;
    private final DexFile file;
    private final boolean isStatic;
    private final LocalList.Entry[] lastEntryForReg;
    private int line = 1;
    private final LocalList locals;
    private final ByteArrayAnnotatedOutput output;
    private final PositionList positions;
    private String prefix;
    private final int regSize;
    private boolean shouldConsume;

    public DebugInfoEncoder(PositionList positionList, LocalList localList, DexFile dexFile, int i, int i2, boolean z, CstMethodRef cstMethodRef) {
        this.positions = positionList;
        this.locals = localList;
        this.file = dexFile;
        this.desc = cstMethodRef.getPrototype();
        this.isStatic = z;
        this.codeSize = i;
        this.regSize = i2;
        this.output = new ByteArrayAnnotatedOutput();
        this.lastEntryForReg = new LocalList.Entry[i2];
    }

    private void annotate(int i, String str) {
        if (this.prefix != null) {
            str = String.valueOf(this.prefix) + str;
        }
        if (this.annotateTo != null) {
            AnnotatedOutput annotatedOutput = this.annotateTo;
            if (!this.shouldConsume) {
                i = 0;
            }
            annotatedOutput.annotate(i, str);
        }
        if (this.debugPrint != null) {
            this.debugPrint.println(str);
        }
    }

    public byte[] convert() {
        try {
            return convert0();
        } catch (IOException e) {
            throw ExceptionWithContext.withContext(e, "...while encoding debug info");
        }
    }

    public byte[] convertAndAnnotate(String str, PrintWriter printWriter, AnnotatedOutput annotatedOutput, boolean z) {
        this.prefix = str;
        this.debugPrint = printWriter;
        this.annotateTo = annotatedOutput;
        this.shouldConsume = z;
        return convert();
    }

    private byte[] convert0() throws IOException {
        int i;
        int i2;
        int i3 = 0;
        ArrayList<PositionList.Entry> buildSortedPositions = buildSortedPositions();
        emitHeader(buildSortedPositions, extractMethodArguments());
        this.output.writeByte(7);
        if (!(this.annotateTo == null && this.debugPrint == null)) {
            annotate(1, String.format("%04x: prologue end", Integer.valueOf(this.address)));
        }
        int size = buildSortedPositions.size();
        int size2 = this.locals.size();
        int i4 = 0;
        while (true) {
            int emitLocalsAtAddress = emitLocalsAtAddress(i3);
            int emitPositionsAtAddress = emitPositionsAtAddress(i4, buildSortedPositions);
            if (emitLocalsAtAddress < size2) {
                i = this.locals.get(emitLocalsAtAddress).getAddress();
            } else {
                i = Integer.MAX_VALUE;
            }
            if (emitPositionsAtAddress < size) {
                i2 = buildSortedPositions.get(emitPositionsAtAddress).getAddress();
            } else {
                i2 = Integer.MAX_VALUE;
            }
            int min = Math.min(i2, i);
            if (!(min == Integer.MAX_VALUE || (min == this.codeSize && i == Integer.MAX_VALUE && i2 == Integer.MAX_VALUE))) {
                if (min == i2) {
                    i4 = emitPositionsAtAddress + 1;
                    emitPosition(buildSortedPositions.get(emitPositionsAtAddress));
                    i3 = emitLocalsAtAddress;
                } else {
                    emitAdvancePc(min - this.address);
                    i3 = emitLocalsAtAddress;
                    i4 = emitPositionsAtAddress;
                }
            }
            emitEndSequence();
            return this.output.toByteArray();
        }
    }

    private int emitLocalsAtAddress(int i) throws IOException {
        int size = this.locals.size();
        while (i < size && this.locals.get(i).getAddress() == this.address) {
            int i2 = i + 1;
            LocalList.Entry entry = this.locals.get(i);
            int register = entry.getRegister();
            LocalList.Entry entry2 = this.lastEntryForReg[register];
            if (entry == entry2) {
                i = i2;
            } else {
                this.lastEntryForReg[register] = entry;
                if (!entry.isStart()) {
                    if (entry.getDisposition() != LocalList.Disposition.END_REPLACED) {
                        emitLocalEnd(entry);
                    }
                    i = i2;
                } else if (entry2 == null || !entry.matches(entry2)) {
                    emitLocalStart(entry);
                    i = i2;
                } else if (entry2.isStart()) {
                    throw new RuntimeException("shouldn't happen");
                } else {
                    emitLocalRestart(entry);
                    i = i2;
                }
            }
        }
        return i;
    }

    private int emitPositionsAtAddress(int i, ArrayList<PositionList.Entry> arrayList) throws IOException {
        int size = arrayList.size();
        while (i < size && arrayList.get(i).getAddress() == this.address) {
            emitPosition(arrayList.get(i));
            i++;
        }
        return i;
    }

    private void emitHeader(ArrayList<PositionList.Entry> arrayList, ArrayList<LocalList.Entry> arrayList2) throws IOException {
        int i;
        LocalList.Entry entry;
        String str;
        boolean z = (this.annotateTo == null && this.debugPrint == null) ? false : true;
        int cursor = this.output.getCursor();
        if (arrayList.size() > 0) {
            this.line = arrayList.get(0).getPosition().getLine();
        }
        this.output.writeUleb128(this.line);
        if (z) {
            annotate(this.output.getCursor() - cursor, "line_start: " + this.line);
        }
        int paramBase = getParamBase();
        StdTypeList parameterTypes = this.desc.getParameterTypes();
        int size = parameterTypes.size();
        if (!this.isStatic) {
            Iterator<LocalList.Entry> it = arrayList2.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                LocalList.Entry next = it.next();
                if (paramBase == next.getRegister()) {
                    this.lastEntryForReg[paramBase] = next;
                    break;
                }
            }
            i = paramBase + 1;
        } else {
            i = paramBase;
        }
        int cursor2 = this.output.getCursor();
        this.output.writeUleb128(size);
        if (z) {
            annotate(this.output.getCursor() - cursor2, String.format("parameters_size: %04x", Integer.valueOf(size)));
        }
        int i2 = i;
        for (int i3 = 0; i3 < size; i3++) {
            Type type = parameterTypes.get(i3);
            int cursor3 = this.output.getCursor();
            Iterator<LocalList.Entry> it2 = arrayList2.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    entry = null;
                    break;
                }
                entry = it2.next();
                if (i2 == entry.getRegister()) {
                    if (entry.getSignature() != null) {
                        emitStringIndex(null);
                    } else {
                        emitStringIndex(entry.getName());
                    }
                    this.lastEntryForReg[i2] = entry;
                }
            }
            if (entry == null) {
                emitStringIndex(null);
            }
            if (z) {
                if (entry == null || entry.getSignature() != null) {
                    str = "<unnamed>";
                } else {
                    str = entry.getName().toHuman();
                }
                annotate(this.output.getCursor() - cursor3, "parameter " + str + " " + RegisterSpec.PREFIX + i2);
            }
            i2 += type.getCategory();
        }
        LocalList.Entry[] entryArr = this.lastEntryForReg;
        for (LocalList.Entry entry2 : entryArr) {
            if (!(entry2 == null || entry2.getSignature() == null)) {
                emitLocalStartExtended(entry2);
            }
        }
    }

    private ArrayList<PositionList.Entry> buildSortedPositions() {
        int size = this.positions == null ? 0 : this.positions.size();
        ArrayList<PositionList.Entry> arrayList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(this.positions.get(i));
        }
        Collections.sort(arrayList, new Comparator<PositionList.Entry>() {
            @Override // java.util.Comparator
            public int compare(PositionList.Entry entry, PositionList.Entry entry2) {
                return compare(entry, entry2);
            }

            public int compareTwo(PositionList.Entry entry, PositionList.Entry entry2) {
                return entry.getAddress() - entry2.getAddress();
            }

            public boolean equals(Object obj) {
                return obj == this;
            }
        });
        return arrayList;
    }

    private int getParamBase() {
        return (this.regSize - this.desc.getParameterTypes().getWordCount()) - (this.isStatic ? 0 : 1);
    }

    private ArrayList<LocalList.Entry> extractMethodArguments() {
        ArrayList<LocalList.Entry> arrayList = new ArrayList<>(this.desc.getParameterTypes().size());
        int paramBase = getParamBase();
        BitSet bitSet = new BitSet(this.regSize - paramBase);
        int size = this.locals.size();
        for (int i = 0; i < size; i++) {
            LocalList.Entry entry = this.locals.get(i);
            int register = entry.getRegister();
            if (register >= paramBase && !bitSet.get(register - paramBase)) {
                bitSet.set(register - paramBase);
                arrayList.add(entry);
            }
        }
        Collections.sort(arrayList, new Comparator<LocalList.Entry>() {
            public int compare(LocalList.Entry entry, LocalList.Entry entry2) {
                return compare(entry, entry2);
            }

            public int compareThree(LocalList.Entry entry, LocalList.Entry entry2) {
                return entry.getRegister() - entry2.getRegister();
            }

            public boolean equals(Object obj) {
                return obj == this;
            }
        });
        return arrayList;
    }

    private String entryAnnotationString(LocalList.Entry entry) {
        StringBuilder sb = new StringBuilder();
        sb.append(RegisterSpec.PREFIX);
        sb.append(entry.getRegister());
        sb.append(' ');
        CstString name = entry.getName();
        if (name == null) {
            sb.append("null");
        } else {
            sb.append(name.toHuman());
        }
        sb.append(' ');
        CstType type = entry.getType();
        if (type == null) {
            sb.append("null");
        } else {
            sb.append(type.toHuman());
        }
        CstString signature = entry.getSignature();
        if (signature != null) {
            sb.append(' ');
            sb.append(signature.toHuman());
        }
        return sb.toString();
    }

    private void emitLocalRestart(LocalList.Entry entry) throws IOException {
        int cursor = this.output.getCursor();
        this.output.writeByte(6);
        emitUnsignedLeb128(entry.getRegister());
        if (this.annotateTo != null || this.debugPrint != null) {
            annotate(this.output.getCursor() - cursor, String.format("%04x: +local restart %s", Integer.valueOf(this.address), entryAnnotationString(entry)));
        }
    }

    private void emitStringIndex(CstString cstString) throws IOException {
        if (cstString == null || this.file == null) {
            this.output.writeUleb128(0);
        } else {
            this.output.writeUleb128(this.file.getStringIds().indexOf(cstString) + 1);
        }
    }

    private void emitTypeIndex(CstType cstType) throws IOException {
        if (cstType == null || this.file == null) {
            this.output.writeUleb128(0);
        } else {
            this.output.writeUleb128(this.file.getTypeIds().indexOf(cstType) + 1);
        }
    }

    private void emitLocalStart(LocalList.Entry entry) throws IOException {
        if (entry.getSignature() != null) {
            emitLocalStartExtended(entry);
            return;
        }
        int cursor = this.output.getCursor();
        this.output.writeByte(3);
        emitUnsignedLeb128(entry.getRegister());
        emitStringIndex(entry.getName());
        emitTypeIndex(entry.getType());
        if (this.annotateTo != null || this.debugPrint != null) {
            annotate(this.output.getCursor() - cursor, String.format("%04x: +local %s", Integer.valueOf(this.address), entryAnnotationString(entry)));
        }
    }

    private void emitLocalStartExtended(LocalList.Entry entry) throws IOException {
        int cursor = this.output.getCursor();
        this.output.writeByte(4);
        emitUnsignedLeb128(entry.getRegister());
        emitStringIndex(entry.getName());
        emitTypeIndex(entry.getType());
        emitStringIndex(entry.getSignature());
        if (this.annotateTo != null || this.debugPrint != null) {
            annotate(this.output.getCursor() - cursor, String.format("%04x: +localx %s", Integer.valueOf(this.address), entryAnnotationString(entry)));
        }
    }

    private void emitLocalEnd(LocalList.Entry entry) throws IOException {
        int cursor = this.output.getCursor();
        this.output.writeByte(5);
        this.output.writeUleb128(entry.getRegister());
        if (this.annotateTo != null || this.debugPrint != null) {
            annotate(this.output.getCursor() - cursor, String.format("%04x: -local %s", Integer.valueOf(this.address), entryAnnotationString(entry)));
        }
    }

    private void emitPosition(PositionList.Entry entry) throws IOException {
        int i;
        int i2;
        int line2 = entry.getPosition().getLine();
        int address2 = entry.getAddress();
        int i3 = line2 - this.line;
        int i4 = address2 - this.address;
        if (i4 < 0) {
            throw new RuntimeException("Position entries must be in ascending address order");
        }
        if (i3 < -4 || i3 > 10) {
            emitAdvanceLine(i3);
            i3 = 0;
        }
        int computeOpcode = computeOpcode(i3, i4);
        if ((computeOpcode & -256) > 0) {
            emitAdvancePc(i4);
            int computeOpcode2 = computeOpcode(i3, 0);
            if ((computeOpcode2 & -256) > 0) {
                emitAdvanceLine(i3);
                i = 0;
                computeOpcode = computeOpcode(0, 0);
                i2 = 0;
            } else {
                computeOpcode = computeOpcode2;
                i = i3;
                i2 = 0;
            }
        } else {
            i = i3;
            i2 = i4;
        }
        this.output.writeByte(computeOpcode);
        this.line = i + this.line;
        this.address = i2 + this.address;
        if (this.annotateTo != null || this.debugPrint != null) {
            annotate(1, String.format("%04x: line %d", Integer.valueOf(this.address), Integer.valueOf(this.line)));
        }
    }

    private static int computeOpcode(int i, int i2) {
        if (i >= -4 && i <= 10) {
            return i + 4 + (i2 * 15) + 10;
        }
        throw new RuntimeException("Parameter out of range");
    }

    private void emitAdvanceLine(int i) throws IOException {
        int cursor = this.output.getCursor();
        this.output.writeByte(2);
        this.output.writeSleb128(i);
        this.line += i;
        if (this.annotateTo != null || this.debugPrint != null) {
            annotate(this.output.getCursor() - cursor, String.format("line = %d", Integer.valueOf(this.line)));
        }
    }

    private void emitAdvancePc(int i) throws IOException {
        int cursor = this.output.getCursor();
        this.output.writeByte(1);
        this.output.writeUleb128(i);
        this.address += i;
        if (this.annotateTo != null || this.debugPrint != null) {
            annotate(this.output.getCursor() - cursor, String.format("%04x: advance pc", Integer.valueOf(this.address)));
        }
    }

    private void emitUnsignedLeb128(int i) throws IOException {
        if (i < 0) {
            throw new RuntimeException("Signed value where unsigned required: " + i);
        }
        this.output.writeUleb128(i);
    }

    private void emitEndSequence() {
        this.output.writeByte(0);
        if (this.annotateTo != null || this.debugPrint != null) {
            annotate(1, "end sequence");
        }
    }
}
