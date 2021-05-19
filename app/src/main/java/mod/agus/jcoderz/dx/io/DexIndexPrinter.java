package mod.agus.jcoderz.dx.io;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import mod.agus.jcoderz.dex.ClassDef;
import mod.agus.jcoderz.dex.Dex;
import mod.agus.jcoderz.dex.FieldId;
import mod.agus.jcoderz.dex.MethodId;
import mod.agus.jcoderz.dex.ProtoId;
import mod.agus.jcoderz.dex.TableOfContents;

public final class DexIndexPrinter {
    private Dex dex;
    private final TableOfContents tableOfContents = this.dex.getTableOfContents();

    public DexIndexPrinter(File file) throws IOException {
        this.dex = new Dex(file);
    }

    public static void main(String[] strArr) throws IOException {
        DexIndexPrinter dexIndexPrinter = new DexIndexPrinter(new File(strArr[0]));
        dexIndexPrinter.printMap();
        dexIndexPrinter.printStrings();
        dexIndexPrinter.printTypeIds();
        dexIndexPrinter.printProtoIds();
        dexIndexPrinter.printFieldIds();
        dexIndexPrinter.printMethodIds();
        dexIndexPrinter.printTypeLists();
        dexIndexPrinter.printClassDefs();
    }

    private void printMap() {
        TableOfContents.Section[] sectionArr = this.tableOfContents.sections;
        for (TableOfContents.Section section : sectionArr) {
            if (section.off != -1) {
                System.out.println("section " + Integer.toHexString(section.type) + " off=" + Integer.toHexString(section.off) + " size=" + Integer.toHexString(section.size) + " byteCount=" + Integer.toHexString(section.byteCount));
            }
        }
    }

    private void printStrings() throws IOException {
        int i = 0;
        Iterator<String> it = this.dex.strings().iterator();
        while (it.hasNext()) {
            System.out.println("string " + i + ": " + it.next());
            i++;
        }
    }

    private void printTypeIds() throws IOException {
        int i = 0;
        Iterator<Integer> it = this.dex.typeIds().iterator();
        while (it.hasNext()) {
            System.out.println("type " + i + ": " + this.dex.strings().get(it.next().intValue()));
            i++;
        }
    }

    private void printProtoIds() throws IOException {
        int i = 0;
        Iterator<ProtoId> it = this.dex.protoIds().iterator();
        while (it.hasNext()) {
            System.out.println("proto " + i + ": " + it.next());
            i++;
        }
    }

    private void printFieldIds() throws IOException {
        int i = 0;
        Iterator<FieldId> it = this.dex.fieldIds().iterator();
        while (it.hasNext()) {
            System.out.println("field " + i + ": " + it.next());
            i++;
        }
    }

    private void printMethodIds() throws IOException {
        int i = 0;
        Iterator<MethodId> it = this.dex.methodIds().iterator();
        while (it.hasNext()) {
            System.out.println("methodId " + i + ": " + it.next());
            i++;
        }
    }

    private void printTypeLists() throws IOException {
        if (this.tableOfContents.typeLists.off == -1) {
            System.out.println("No type lists");
            return;
        }
        Dex.Section open = this.dex.open(this.tableOfContents.typeLists.off);
        for (int i = 0; i < this.tableOfContents.typeLists.size; i++) {
            int readInt = open.readInt();
            System.out.print("Type list i=" + i + ", size=" + readInt + ", elements=");
            for (int i2 = 0; i2 < readInt; i2++) {
                System.out.print(" " + this.dex.typeNames().get(open.readShort()));
            }
            if (readInt % 2 == 1) {
                open.readShort();
            }
            System.out.println();
        }
    }

    private void printClassDefs() {
        int i = 0;
        Iterator<ClassDef> it = this.dex.classDefs().iterator();
        while (it.hasNext()) {
            System.out.println("class def " + i + ": " + it.next());
            i++;
        }
    }
}
