package mod.agus.jcoderz.dx.merge;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import mod.agus.jcoderz.dex.Annotation;
import mod.agus.jcoderz.dex.ClassData;
import mod.agus.jcoderz.dex.ClassDef;
import mod.agus.jcoderz.dex.Code;
import mod.agus.jcoderz.dex.Dex;
import mod.agus.jcoderz.dex.DexException;
import mod.agus.jcoderz.dex.DexIndexOverflowException;
import mod.agus.jcoderz.dex.FieldId;
import mod.agus.jcoderz.dex.MethodId;
import mod.agus.jcoderz.dex.ProtoId;
import mod.agus.jcoderz.dex.TableOfContents;
import mod.agus.jcoderz.dex.TypeList;

public final class DexMerger {
    private static final byte DBG_ADVANCE_LINE = 2;
    private static final byte DBG_ADVANCE_PC = 1;
    private static final byte DBG_END_LOCAL = 5;
    private static final byte DBG_END_SEQUENCE = 0;
    private static final byte DBG_RESTART_LOCAL = 6;
    private static final byte DBG_SET_EPILOGUE_BEGIN = 8;
    private static final byte DBG_SET_FILE = 9;
    private static final byte DBG_SET_PROLOGUE_END = 7;
    private static final byte DBG_START_LOCAL = 3;
    private static final byte DBG_START_LOCAL_EXTENDED = 4;
    private final Dex.Section annotationOut;
    private final Dex.Section annotationSetOut;
    private final Dex.Section annotationSetRefListOut;
    private final Dex.Section annotationsDirectoryOut;
    private final Dex.Section classDataOut;
    private final Dex.Section codeOut;
    private final CollisionPolicy collisionPolicy;
    private int compactWasteThreshold;
    private final TableOfContents contentsOut;
    private final Dex.Section debugInfoOut;
    private final Dex dexOut;
    private final Dex[] dexes;
    private final Dex.Section encodedArrayOut;
    private final Dex.Section headerOut;
    private final Dex.Section idsDefsOut;
    private final IndexMap[] indexMaps;
    private final InstructionTransformer instructionTransformer;
    private final Dex.Section mapListOut;
    private final Dex.Section stringDataOut;
    private final Dex.Section typeListOut;
    private final WriterSizes writerSizes;

    public DexMerger(Dex[] dexArr, CollisionPolicy collisionPolicy2) throws IOException {
        this(dexArr, collisionPolicy2, new WriterSizes(dexArr));
    }

    private DexMerger(Dex[] dexArr, CollisionPolicy collisionPolicy2, WriterSizes writerSizes2) throws IOException {
        this.compactWasteThreshold = 1048576;
        this.dexes = dexArr;
        this.collisionPolicy = collisionPolicy2;
        this.writerSizes = writerSizes2;
        this.dexOut = new Dex(writerSizes2.size());
        this.indexMaps = new IndexMap[dexArr.length];
        for (int i = 0; i < dexArr.length; i++) {
            this.indexMaps[i] = new IndexMap(this.dexOut, dexArr[i].getTableOfContents());
        }
        this.instructionTransformer = new InstructionTransformer();
        this.headerOut = this.dexOut.appendSection(writerSizes2.header, "header");
        this.idsDefsOut = this.dexOut.appendSection(writerSizes2.idsDefs, "ids defs");
        this.contentsOut = this.dexOut.getTableOfContents();
        this.contentsOut.dataOff = this.dexOut.getNextSectionStart();
        this.contentsOut.mapList.off = this.dexOut.getNextSectionStart();
        this.contentsOut.mapList.size = 1;
        this.mapListOut = this.dexOut.appendSection(writerSizes2.mapList, "map list");
        this.contentsOut.typeLists.off = this.dexOut.getNextSectionStart();
        this.typeListOut = this.dexOut.appendSection(writerSizes2.typeList, "type list");
        this.contentsOut.annotationSetRefLists.off = this.dexOut.getNextSectionStart();
        this.annotationSetRefListOut = this.dexOut.appendSection(writerSizes2.annotationsSetRefList, "annotation set ref list");
        this.contentsOut.annotationSets.off = this.dexOut.getNextSectionStart();
        this.annotationSetOut = this.dexOut.appendSection(writerSizes2.annotationsSet, "annotation sets");
        this.contentsOut.classDatas.off = this.dexOut.getNextSectionStart();
        this.classDataOut = this.dexOut.appendSection(writerSizes2.classData, "class data");
        this.contentsOut.codes.off = this.dexOut.getNextSectionStart();
        this.codeOut = this.dexOut.appendSection(writerSizes2.code, "code");
        this.contentsOut.stringDatas.off = this.dexOut.getNextSectionStart();
        this.stringDataOut = this.dexOut.appendSection(writerSizes2.stringData, "string data");
        this.contentsOut.debugInfos.off = this.dexOut.getNextSectionStart();
        this.debugInfoOut = this.dexOut.appendSection(writerSizes2.debugInfo, "debug info");
        this.contentsOut.annotations.off = this.dexOut.getNextSectionStart();
        this.annotationOut = this.dexOut.appendSection(writerSizes2.annotation, "annotation");
        this.contentsOut.encodedArrays.off = this.dexOut.getNextSectionStart();
        this.encodedArrayOut = this.dexOut.appendSection(writerSizes2.encodedArray, "encoded array");
        this.contentsOut.annotationsDirectories.off = this.dexOut.getNextSectionStart();
        this.annotationsDirectoryOut = this.dexOut.appendSection(writerSizes2.annotationsDirectory, "annotations directory");
        this.contentsOut.dataSize = this.dexOut.getNextSectionStart() - this.contentsOut.dataOff;
    }

    public static void main(String[] strArr) throws IOException {
        if (strArr.length < 2) {
            printUsage();
            return;
        }
        Dex[] dexArr = new Dex[(strArr.length - 1)];
        for (int i = 1; i < strArr.length; i++) {
            dexArr[i - 1] = new Dex(new File(strArr[i]));
        }
        new DexMerger(dexArr, CollisionPolicy.KEEP_FIRST).merge().writeTo(new File(strArr[0]));
    }

    private static void printUsage() {
        System.out.println("Usage: DexMerger <out.dex> <a.dex> <b.dex> ...");
        System.out.println();
        System.out.println("If a class is defined in several dex, the class found in the first dex will be used.");
    }

    public void setCompactWasteThreshold(int i) {
        this.compactWasteThreshold = i;
    }

    private Dex mergeDexes() throws IOException {
        mergeStringIds();
        mergeTypeIds();
        mergeTypeLists();
        mergeProtoIds();
        mergeFieldIds();
        mergeMethodIds();
        mergeAnnotations();
        unionAnnotationSetsAndDirectories();
        mergeClassDefs();
        this.contentsOut.header.off = 0;
        this.contentsOut.header.size = 1;
        this.contentsOut.fileSize = this.dexOut.getLength();
        this.contentsOut.computeSizesFromOffsets();
        this.contentsOut.writeHeader(this.headerOut);
        this.contentsOut.writeMap(this.mapListOut);
        this.dexOut.writeHashes();
        return this.dexOut;
    }

    public Dex merge() throws IOException {
        if (this.dexes.length == 1) {
            return this.dexes[0];
        }
        if (this.dexes.length == 0) {
            return null;
        }
        long nanoTime = System.nanoTime();
        Dex mergeDexes = mergeDexes();
        WriterSizes writerSizes2 = new WriterSizes(this);
        int size = this.writerSizes.size() - writerSizes2.size();
        if (size > this.compactWasteThreshold) {
            mergeDexes = new DexMerger(new Dex[]{this.dexOut, new Dex(0)}, CollisionPolicy.FAIL, writerSizes2).mergeDexes();
            System.out.printf("Result compacted from %.1fKiB to %.1fKiB to save %.1fKiB%n", Float.valueOf(((float) this.dexOut.getLength()) / 1024.0f), Float.valueOf(((float) mergeDexes.getLength()) / 1024.0f), Float.valueOf(((float) size) / 1024.0f));
        }
        long nanoTime2 = System.nanoTime() - nanoTime;
        for (int i = 0; i < this.dexes.length; i++) {
            System.out.printf("Merged dex #%d (%d defs/%.1fKiB)%n", Integer.valueOf(i + 1), Integer.valueOf(this.dexes[i].getTableOfContents().classDefs.size), Float.valueOf(((float) this.dexes[i].getLength()) / 1024.0f));
        }
        System.out.printf("Result is %d defs/%.1fKiB. Took %.1fs%n", Integer.valueOf(mergeDexes.getTableOfContents().classDefs.size), Float.valueOf(((float) mergeDexes.getLength()) / 1024.0f), Float.valueOf(((float) nanoTime2) / 1.0E9f));
        return mergeDexes;
    }

    private void mergeStringIds() {
        new IdMerger<String>(this, this.idsDefsOut) {
            private Dex.Section stringDataOut;
            private com.android.dex.TableOfContents contentsOut;
            private Dex.Section idsDefsOut;

            @Override // mod.agus.jcoderz.dx.merge.DexMerger.IdMerger
            /* bridge */ /* synthetic */ void write(String str) {
                write(str);
            }

            @Override // mod.agus.jcoderz.dx.merge.DexMerger.IdMerger
            TableOfContents.Section getSection(TableOfContents tableOfContents) {
                return tableOfContents.stringIds;
            }

            @Override // mod.agus.jcoderz.dx.merge.DexMerger.IdMerger
            String read(Dex.Section section, IndexMap indexMap, int i) {
                return section.readString();
            }

            @Override // mod.agus.jcoderz.dx.merge.DexMerger.IdMerger
            void updateIndex(int i, IndexMap indexMap, int i2, int i3) {
                indexMap.stringIds[i2] = i3;
            }

        }.mergeSorted();
    }

    private void mergeTypeIds() {
        new IdMerger<Integer>(this, this.idsDefsOut) {
            private Dex.Section idsDefsOut;
            /* class mod.agus.jcoderz.dx.merge.DexMerger.2 */


            @Override // mod.agus.jcoderz.dx.merge.DexMerger.IdMerger
            void write(Integer num) {
                write(num);
            }

            @Override // mod.agus.jcoderz.dx.merge.DexMerger.IdMerger
            TableOfContents.Section getSection(TableOfContents tableOfContents) {
                return tableOfContents.typeIds;
            }

            @Override // mod.agus.jcoderz.dx.merge.DexMerger.IdMerger
            Integer read(Dex.Section section, IndexMap indexMap, int i) {
                return Integer.valueOf(indexMap.adjustString(section.readInt()));
            }

            @Override // mod.agus.jcoderz.dx.merge.DexMerger.IdMerger
            void updateIndex(int i, IndexMap indexMap, int i2, int i3) {
                if (i3 < 0 || i3 > 65535) {
                    throw new DexIndexOverflowException("type ID not in [0, 0xffff]: " + i3);
                }
                indexMap.typeIds[i2] = (short) i3;
            }
        }.mergeSorted();
    }

    private void mergeTypeLists() {
        new IdMerger<TypeList>(this, this.typeListOut) {
            /* class mod.agus.jcoderz.dx.merge.DexMerger.3 */

            /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Comparable] */
            @Override // mod.agus.jcoderz.dx.merge.DexMerger.IdMerger
            /* bridge */ /* synthetic */ void write(TypeList typeList) {
                write(typeList);
            }

            @Override // mod.agus.jcoderz.dx.merge.DexMerger.IdMerger
            TableOfContents.Section getSection(TableOfContents tableOfContents) {
                return tableOfContents.typeLists;
            }

            @Override // mod.agus.jcoderz.dx.merge.DexMerger.IdMerger
            TypeList read(Dex.Section section, IndexMap indexMap, int i) {
                return indexMap.adjustTypeList(section.readTypeList());
            }

            @Override // mod.agus.jcoderz.dx.merge.DexMerger.IdMerger
            void updateIndex(int i, IndexMap indexMap, int i2, int i3) {
                indexMap.putTypeListOffset(i, this.typeListOut.getPosition());
            }

        }.mergeUnsorted();
    }

    private void mergeProtoIds() {
        new IdMerger<ProtoId>(this, this.idsDefsOut) {
            /* class mod.agus.jcoderz.dx.merge.DexMerger.4 */

            /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Comparable] */
            @Override // mod.agus.jcoderz.dx.merge.DexMerger.IdMerger
            void write(ProtoId protoId) {
                write(protoId);
            }

            @Override // mod.agus.jcoderz.dx.merge.DexMerger.IdMerger
            TableOfContents.Section getSection(TableOfContents tableOfContents) {
                return tableOfContents.protoIds;
            }

            @Override // mod.agus.jcoderz.dx.merge.DexMerger.IdMerger
            ProtoId read(Dex.Section section, IndexMap indexMap, int i) {
                return indexMap.adjust(section.readProtoId());
            }

            @Override // mod.agus.jcoderz.dx.merge.DexMerger.IdMerger
            void updateIndex(int i, IndexMap indexMap, int i2, int i3) {
                if (i3 < 0 || i3 > 65535) {
                    throw new DexIndexOverflowException("proto ID not in [0, 0xffff]: " + i3);
                }
                indexMap.protoIds[i2] = (short) i3;
            }
        }.mergeSorted();
    }

    private void mergeFieldIds() {
        new IdMerger<FieldId>(this, this.idsDefsOut) {
            /* class mod.agus.jcoderz.dx.merge.DexMerger.5 */

            /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Comparable] */
            @Override // mod.agus.jcoderz.dx.merge.DexMerger.IdMerger
            /* bridge */ /* synthetic */ void write(FieldId fieldId) {
                write(fieldId);
            }

            @Override // mod.agus.jcoderz.dx.merge.DexMerger.IdMerger
            TableOfContents.Section getSection(TableOfContents tableOfContents) {
                return tableOfContents.fieldIds;
            }

            @Override // mod.agus.jcoderz.dx.merge.DexMerger.IdMerger
            FieldId read(Dex.Section section, IndexMap indexMap, int i) {
                return indexMap.adjust(section.readFieldId());
            }

            @Override // mod.agus.jcoderz.dx.merge.DexMerger.IdMerger
            void updateIndex(int i, IndexMap indexMap, int i2, int i3) {
                if (i3 < 0 || i3 > 65535) {
                    throw new DexIndexOverflowException("field ID not in [0, 0xffff]: " + i3);
                }
                indexMap.fieldIds[i2] = (short) i3;
            }

        }.mergeSorted();
    }

    private void mergeMethodIds() {
        new IdMerger<MethodId>(this, this.idsDefsOut) {
            @Override // mod.agus.jcoderz.dx.merge.DexMerger.IdMerger
            TableOfContents.Section getSection(TableOfContents tableOfContents) {
                return tableOfContents.methodIds;
            }

            @Override // mod.agus.jcoderz.dx.merge.DexMerger.IdMerger
            MethodId read(Dex.Section section, IndexMap indexMap, int i) {
                return indexMap.adjust(section.readMethodId());
            }

            @Override // mod.agus.jcoderz.dx.merge.DexMerger.IdMerger
            void updateIndex(int i, IndexMap indexMap, int i2, int i3) {
                if (i3 < 0 || i3 > 65535) {
                    throw new DexIndexOverflowException("method ID not in [0, 0xffff]: " + i3);
                }
                indexMap.methodIds[i2] = (short) i3;
            }

            @Override
            void write(MethodId methodId) {

            }

        }.mergeSorted();
    }

    private void mergeAnnotations() {
        new IdMerger<Annotation>(this, this.annotationOut) {
            /* class mod.agus.jcoderz.dx.merge.DexMerger.7 */

            /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Comparable] */
            @Override // mod.agus.jcoderz.dx.merge.DexMerger.IdMerger
            /* bridge */ /* synthetic */ void write(Annotation annotation) {
                write(annotation);
            }

            @Override // mod.agus.jcoderz.dx.merge.DexMerger.IdMerger
            TableOfContents.Section getSection(TableOfContents tableOfContents) {
                return tableOfContents.annotations;
            }

            @Override // mod.agus.jcoderz.dx.merge.DexMerger.IdMerger
            Annotation read(Dex.Section section, IndexMap indexMap, int i) {
                return indexMap.adjust(section.readAnnotation());
            }

            @Override // mod.agus.jcoderz.dx.merge.DexMerger.IdMerger
            void updateIndex(int i, IndexMap indexMap, int i2, int i3) {
                indexMap.putAnnotationOffset(i, this.annotationOut.getPosition());
            }
        }.mergeUnsorted();
    }

    private void mergeClassDefs() {
        SortableType[] sortedTypes = getSortedTypes();
        this.contentsOut.classDefs.off = this.idsDefsOut.getPosition();
        this.contentsOut.classDefs.size = sortedTypes.length;
        for (SortableType sortableType : sortedTypes) {
            transformClassDef(sortableType.getDex(), sortableType.getClassDef(), sortableType.getIndexMap());
        }
    }

    private SortableType[] getSortedTypes() {
        boolean z;
        SortableType[] sortableTypeArr = new SortableType[this.contentsOut.typeIds.size];
        for (int i = 0; i < this.dexes.length; i++) {
            readSortableTypes(sortableTypeArr, this.dexes[i], this.indexMaps[i]);
        }
        do {
            z = true;
            for (SortableType sortableType : sortableTypeArr) {
                if (sortableType != null && !sortableType.isDepthAssigned()) {
                    z &= sortableType.tryAssignDepth(sortableTypeArr);
                }
            }
        } while (!z);
        Arrays.sort(sortableTypeArr, SortableType.NULLS_LAST_ORDER);
        int indexOf = Arrays.asList(sortableTypeArr).indexOf(null);
        if (indexOf != -1) {
            return (SortableType[]) Arrays.copyOfRange(sortableTypeArr, 0, indexOf);
        }
        return sortableTypeArr;
    }

    private void readSortableTypes(SortableType[] sortableTypeArr, Dex dex, IndexMap indexMap) {
        for (ClassDef classDef : dex.classDefs()) {
            SortableType adjust = indexMap.adjust(new SortableType(dex, indexMap, classDef));
            int typeIndex = adjust.getTypeIndex();
            if (sortableTypeArr[typeIndex] == null) {
                sortableTypeArr[typeIndex] = adjust;
            } else if (this.collisionPolicy != CollisionPolicy.KEEP_FIRST) {
                throw new DexException("Multiple dex files define " + ((String) dex.typeNames().get(classDef.getTypeIndex())));
            }
        }
    }

    private void unionAnnotationSetsAndDirectories() {
        for (int i = 0; i < this.dexes.length; i++) {
            transformAnnotationSets(this.dexes[i], this.indexMaps[i]);
        }
        for (int i2 = 0; i2 < this.dexes.length; i2++) {
            transformAnnotationSetRefLists(this.dexes[i2], this.indexMaps[i2]);
        }
        for (int i3 = 0; i3 < this.dexes.length; i3++) {
            transformAnnotationDirectories(this.dexes[i3], this.indexMaps[i3]);
        }
        for (int i4 = 0; i4 < this.dexes.length; i4++) {
            transformStaticValues(this.dexes[i4], this.indexMaps[i4]);
        }
    }

    private void transformAnnotationSets(Dex dex, IndexMap indexMap) {
        TableOfContents.Section section = dex.getTableOfContents().annotationSets;
        if (section.exists()) {
            Dex.Section open = dex.open(section.off);
            for (int i = 0; i < section.size; i++) {
                transformAnnotationSet(indexMap, open);
            }
        }
    }

    private void transformAnnotationSetRefLists(Dex dex, IndexMap indexMap) {
        TableOfContents.Section section = dex.getTableOfContents().annotationSetRefLists;
        if (section.exists()) {
            Dex.Section open = dex.open(section.off);
            for (int i = 0; i < section.size; i++) {
                transformAnnotationSetRefList(indexMap, open);
            }
        }
    }

    private void transformAnnotationDirectories(Dex dex, IndexMap indexMap) {
        TableOfContents.Section section = dex.getTableOfContents().annotationsDirectories;
        if (section.exists()) {
            Dex.Section open = dex.open(section.off);
            for (int i = 0; i < section.size; i++) {
                transformAnnotationDirectory(open, indexMap);
            }
        }
    }

    private void transformStaticValues(Dex dex, IndexMap indexMap) {
        TableOfContents.Section section = dex.getTableOfContents().encodedArrays;
        if (section.exists()) {
            Dex.Section open = dex.open(section.off);
            for (int i = 0; i < section.size; i++) {
                transformStaticValues(open, indexMap);
            }
        }
    }

    private void transformClassDef(Dex dex, ClassDef classDef, IndexMap indexMap) {
        this.idsDefsOut.assertFourByteAligned();
        this.idsDefsOut.writeInt(classDef.getTypeIndex());
        this.idsDefsOut.writeInt(classDef.getAccessFlags());
        this.idsDefsOut.writeInt(classDef.getSupertypeIndex());
        this.idsDefsOut.writeInt(classDef.getInterfacesOffset());
        this.idsDefsOut.writeInt(indexMap.adjustString(classDef.getSourceFileIndex()));
        this.idsDefsOut.writeInt(indexMap.adjustAnnotationDirectory(classDef.getAnnotationsOffset()));
        if (classDef.getClassDataOffset() == 0) {
            this.idsDefsOut.writeInt(0);
        } else {
            this.idsDefsOut.writeInt(this.classDataOut.getPosition());
            transformClassData(dex, dex.readClassData(classDef), indexMap);
        }
        this.idsDefsOut.writeInt(indexMap.adjustStaticValues(classDef.getStaticValuesOffset()));
    }

    private void transformAnnotationDirectory(Dex.Section section, IndexMap indexMap) {
        this.contentsOut.annotationsDirectories.size++;
        this.annotationsDirectoryOut.assertFourByteAligned();
        indexMap.putAnnotationDirectoryOffset(section.getPosition(), this.annotationsDirectoryOut.getPosition());
        this.annotationsDirectoryOut.writeInt(indexMap.adjustAnnotationSet(section.readInt()));
        int readInt = section.readInt();
        this.annotationsDirectoryOut.writeInt(readInt);
        int readInt2 = section.readInt();
        this.annotationsDirectoryOut.writeInt(readInt2);
        int readInt3 = section.readInt();
        this.annotationsDirectoryOut.writeInt(readInt3);
        for (int i = 0; i < readInt; i++) {
            this.annotationsDirectoryOut.writeInt(indexMap.adjustField(section.readInt()));
            this.annotationsDirectoryOut.writeInt(indexMap.adjustAnnotationSet(section.readInt()));
        }
        for (int i2 = 0; i2 < readInt2; i2++) {
            this.annotationsDirectoryOut.writeInt(indexMap.adjustMethod(section.readInt()));
            this.annotationsDirectoryOut.writeInt(indexMap.adjustAnnotationSet(section.readInt()));
        }
        for (int i3 = 0; i3 < readInt3; i3++) {
            this.annotationsDirectoryOut.writeInt(indexMap.adjustMethod(section.readInt()));
            this.annotationsDirectoryOut.writeInt(indexMap.adjustAnnotationSetRefList(section.readInt()));
        }
    }

    private void transformAnnotationSet(IndexMap indexMap, Dex.Section section) {
        this.contentsOut.annotationSets.size++;
        this.annotationSetOut.assertFourByteAligned();
        indexMap.putAnnotationSetOffset(section.getPosition(), this.annotationSetOut.getPosition());
        int readInt = section.readInt();
        this.annotationSetOut.writeInt(readInt);
        for (int i = 0; i < readInt; i++) {
            this.annotationSetOut.writeInt(indexMap.adjustAnnotation(section.readInt()));
        }
    }

    private void transformAnnotationSetRefList(IndexMap indexMap, Dex.Section section) {
        this.contentsOut.annotationSetRefLists.size++;
        this.annotationSetRefListOut.assertFourByteAligned();
        indexMap.putAnnotationSetRefListOffset(section.getPosition(), this.annotationSetRefListOut.getPosition());
        int readInt = section.readInt();
        this.annotationSetRefListOut.writeInt(readInt);
        for (int i = 0; i < readInt; i++) {
            this.annotationSetRefListOut.writeInt(indexMap.adjustAnnotationSet(section.readInt()));
        }
    }

    private void transformClassData(Dex dex, ClassData classData, IndexMap indexMap) {
        this.contentsOut.classDatas.size++;
        ClassData.Field[] staticFields = classData.getStaticFields();
        ClassData.Field[] instanceFields = classData.getInstanceFields();
        ClassData.Method[] directMethods = classData.getDirectMethods();
        ClassData.Method[] virtualMethods = classData.getVirtualMethods();
        this.classDataOut.writeUleb128(staticFields.length);
        this.classDataOut.writeUleb128(instanceFields.length);
        this.classDataOut.writeUleb128(directMethods.length);
        this.classDataOut.writeUleb128(virtualMethods.length);
        transformFields(indexMap, staticFields);
        transformFields(indexMap, instanceFields);
        transformMethods(dex, indexMap, directMethods);
        transformMethods(dex, indexMap, virtualMethods);
    }

    private void transformFields(IndexMap indexMap, ClassData.Field[] fieldArr) {
        int i = 0;
        int length = fieldArr.length;
        int i2 = 0;
        while (i < length) {
            ClassData.Field field = fieldArr[i];
            int adjustField = indexMap.adjustField(field.getFieldIndex());
            this.classDataOut.writeUleb128(adjustField - i2);
            this.classDataOut.writeUleb128(field.getAccessFlags());
            i++;
            i2 = adjustField;
        }
    }

    private void transformMethods(Dex dex, IndexMap indexMap, ClassData.Method[] methodArr) {
        int length = methodArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            ClassData.Method method = methodArr[i];
            int adjustMethod = indexMap.adjustMethod(method.getMethodIndex());
            this.classDataOut.writeUleb128(adjustMethod - i2);
            this.classDataOut.writeUleb128(method.getAccessFlags());
            if (method.getCodeOffset() == 0) {
                this.classDataOut.writeUleb128(0);
            } else {
                this.codeOut.alignToFourBytesWithZeroFill();
                this.classDataOut.writeUleb128(this.codeOut.getPosition());
                transformCode(dex, dex.readCode(method), indexMap);
            }
            i++;
            i2 = adjustMethod;
        }
    }

    private void transformCode(Dex dex, Code code, IndexMap indexMap) {
        this.contentsOut.codes.size++;
        this.codeOut.assertFourByteAligned();
        this.codeOut.writeUnsignedShort(code.getRegistersSize());
        this.codeOut.writeUnsignedShort(code.getInsSize());
        this.codeOut.writeUnsignedShort(code.getOutsSize());
        Code.Try[] tries = code.getTries();
        Code.CatchHandler[] catchHandlers = code.getCatchHandlers();
        this.codeOut.writeUnsignedShort(tries.length);
        int debugInfoOffset = code.getDebugInfoOffset();
        if (debugInfoOffset != 0) {
            this.codeOut.writeInt(this.debugInfoOut.getPosition());
            transformDebugInfoItem(dex.open(debugInfoOffset), indexMap);
        } else {
            this.codeOut.writeInt(0);
        }
        short[] transform = this.instructionTransformer.transform(indexMap, code.getInstructions());
        this.codeOut.writeInt(transform.length);
        this.codeOut.write(transform);
        if (tries.length > 0) {
            if (transform.length % 2 == 1) {
                this.codeOut.writeShort((short) 0);
            }
            Dex.Section open = this.dexOut.open(this.codeOut.getPosition());
            this.codeOut.skip(tries.length * 8);
            transformTries(open, tries, transformCatchHandlers(indexMap, catchHandlers));
        }
    }

    private int[] transformCatchHandlers(IndexMap indexMap, Code.CatchHandler[] catchHandlerArr) {
        int position = this.codeOut.getPosition();
        this.codeOut.writeUleb128(catchHandlerArr.length);
        int[] iArr = new int[catchHandlerArr.length];
        for (int i = 0; i < catchHandlerArr.length; i++) {
            iArr[i] = this.codeOut.getPosition() - position;
            transformEncodedCatchHandler(catchHandlerArr[i], indexMap);
        }
        return iArr;
    }

    private void transformTries(Dex.Section section, Code.Try[] tryArr, int[] iArr) {
        for (Code.Try r2 : tryArr) {
            section.writeInt(r2.getStartAddress());
            section.writeUnsignedShort(r2.getInstructionCount());
            section.writeUnsignedShort(iArr[r2.getCatchHandlerIndex()]);
        }
    }

    private void transformDebugInfoItem(Dex.Section section, IndexMap indexMap) {
        this.contentsOut.debugInfos.size++;
        this.debugInfoOut.writeUleb128(section.readUleb128());
        int readUleb128 = section.readUleb128();
        this.debugInfoOut.writeUleb128(readUleb128);
        for (int i = 0; i < readUleb128; i++) {
            this.debugInfoOut.writeUleb128p1(indexMap.adjustString(section.readUleb128p1()));
        }
        while (true) {
            byte readByte = section.readByte();
            this.debugInfoOut.writeByte(readByte);
            switch (readByte) {
                case 0:
                    return;
                case 1:
                    this.debugInfoOut.writeUleb128(section.readUleb128());
                    break;
                case 2:
                    this.debugInfoOut.writeSleb128(section.readSleb128());
                    break;
                case 3:
                case 4:
                    this.debugInfoOut.writeUleb128(section.readUleb128());
                    this.debugInfoOut.writeUleb128p1(indexMap.adjustString(section.readUleb128p1()));
                    this.debugInfoOut.writeUleb128p1(indexMap.adjustType(section.readUleb128p1()));
                    if (readByte == 4) {
                        this.debugInfoOut.writeUleb128p1(indexMap.adjustString(section.readUleb128p1()));
                        break;
                    } else {
                        break;
                    }
                case 5:
                case 6:
                    this.debugInfoOut.writeUleb128(section.readUleb128());
                    break;
                case 9:
                    this.debugInfoOut.writeUleb128p1(indexMap.adjustString(section.readUleb128p1()));
                    break;
            }
        }
    }

    private void transformEncodedCatchHandler(Code.CatchHandler catchHandler, IndexMap indexMap) {
        int catchAllAddress = catchHandler.getCatchAllAddress();
        int[] typeIndexes = catchHandler.getTypeIndexes();
        int[] addresses = catchHandler.getAddresses();
        if (catchAllAddress != -1) {
            this.codeOut.writeSleb128(-typeIndexes.length);
        } else {
            this.codeOut.writeSleb128(typeIndexes.length);
        }
        for (int i = 0; i < typeIndexes.length; i++) {
            this.codeOut.writeUleb128(indexMap.adjustType(typeIndexes[i]));
            this.codeOut.writeUleb128(addresses[i]);
        }
        if (catchAllAddress != -1) {
            this.codeOut.writeUleb128(catchAllAddress);
        }
    }

    private void transformStaticValues(Dex.Section section, IndexMap indexMap) {
        this.contentsOut.encodedArrays.size++;
        indexMap.putStaticValuesOffset(section.getPosition(), this.encodedArrayOut.getPosition());
        indexMap.adjustEncodedArray(section.readEncodedArray()).writeTo(this.encodedArrayOut);
    }

    private static class WriterSizes {
        private int annotation;
        private int annotationsDirectory;
        private int annotationsSet;
        private int annotationsSetRefList;
        private int classData;
        private int code;
        private int debugInfo;
        private int encodedArray;
        private int header = 112;
        private int idsDefs;
        private int mapList;
        private int stringData;
        private int typeList;

        public WriterSizes(Dex[] dexArr) {
            for (Dex dex : dexArr) {
                plus(dex.getTableOfContents(), false);
            }
            fourByteAlign();
        }

        public WriterSizes(DexMerger dexMerger) {
            this.header = dexMerger.headerOut.used();
            this.idsDefs = dexMerger.idsDefsOut.used();
            this.mapList = dexMerger.mapListOut.used();
            this.typeList = dexMerger.typeListOut.used();
            this.classData = dexMerger.classDataOut.used();
            this.code = dexMerger.codeOut.used();
            this.stringData = dexMerger.stringDataOut.used();
            this.debugInfo = dexMerger.debugInfoOut.used();
            this.encodedArray = dexMerger.encodedArrayOut.used();
            this.annotationsDirectory = dexMerger.annotationsDirectoryOut.used();
            this.annotationsSet = dexMerger.annotationSetOut.used();
            this.annotationsSetRefList = dexMerger.annotationSetRefListOut.used();
            this.annotation = dexMerger.annotationOut.used();
            fourByteAlign();
        }

        private static int fourByteAlign(int i) {
            return (i + 3) & -4;
        }

        private void plus(TableOfContents tableOfContents, boolean z) {
            this.idsDefs += (tableOfContents.stringIds.size * 4) + (tableOfContents.typeIds.size * 4) + (tableOfContents.protoIds.size * 12) + (tableOfContents.fieldIds.size * 8) + (tableOfContents.methodIds.size * 8) + (tableOfContents.classDefs.size * 32);
            this.mapList = (tableOfContents.sections.length * 12) + 4;
            this.typeList += fourByteAlign(tableOfContents.typeLists.byteCount);
            this.stringData += tableOfContents.stringDatas.byteCount;
            this.annotationsDirectory += tableOfContents.annotationsDirectories.byteCount;
            this.annotationsSet += tableOfContents.annotationSets.byteCount;
            this.annotationsSetRefList += tableOfContents.annotationSetRefLists.byteCount;
            if (z) {
                this.code += tableOfContents.codes.byteCount;
                this.classData += tableOfContents.classDatas.byteCount;
                this.encodedArray += tableOfContents.encodedArrays.byteCount;
                this.annotation += tableOfContents.annotations.byteCount;
                this.debugInfo += tableOfContents.debugInfos.byteCount;
                return;
            }
            this.code += (int) Math.ceil(((double) tableOfContents.codes.byteCount) * 1.25d);
            this.classData += (int) Math.ceil(((double) tableOfContents.classDatas.byteCount) * 1.34d);
            this.encodedArray += tableOfContents.encodedArrays.byteCount * 2;
            this.annotation += (int) Math.ceil((double) (tableOfContents.annotations.byteCount * 2));
            this.debugInfo += tableOfContents.debugInfos.byteCount * 2;
        }

        private void fourByteAlign() {
            this.header = fourByteAlign(this.header);
            this.idsDefs = fourByteAlign(this.idsDefs);
            this.mapList = fourByteAlign(this.mapList);
            this.typeList = fourByteAlign(this.typeList);
            this.classData = fourByteAlign(this.classData);
            this.code = fourByteAlign(this.code);
            this.stringData = fourByteAlign(this.stringData);
            this.debugInfo = fourByteAlign(this.debugInfo);
            this.encodedArray = fourByteAlign(this.encodedArray);
            this.annotationsDirectory = fourByteAlign(this.annotationsDirectory);
            this.annotationsSet = fourByteAlign(this.annotationsSet);
            this.annotationsSetRefList = fourByteAlign(this.annotationsSetRefList);
            this.annotation = fourByteAlign(this.annotation);
        }

        public int size() {
            return this.header + this.idsDefs + this.mapList + this.typeList + this.classData + this.code + this.stringData + this.debugInfo + this.encodedArray + this.annotationsDirectory + this.annotationsSet + this.annotationsSetRefList + this.annotation;
        }
    }

    abstract class IdMerger<T extends Comparable<T>> {
        private final Dex.Section out;
        protected Dex.Section typeListOut;
        protected Dex.Section annotationOut;

        abstract TableOfContents.Section getSection(TableOfContents tableOfContents);

        abstract T read(Dex.Section section, IndexMap indexMap, int i);

        abstract void updateIndex(int i, IndexMap indexMap, int i2, int i3);

        abstract void write(T t);

        protected IdMerger(DexMerger dexMerger, Dex.Section section) {
            this.out = section;
        }

        /* JADX DEBUG: Multi-variable search result rejected for r15v0, resolved type: mod.agus.jcoderz.dx.merge.DexMerger$IdMerger<T extends java.lang.Comparable<T>> */
        /* JADX WARN: Multi-variable type inference failed */
        public final void mergeSorted() {
            int i = 0;
            TableOfContents.Section[] sectionArr = new TableOfContents.Section[DexMerger.this.dexes.length];
            Dex.Section[] sectionArr2 = new Dex.Section[DexMerger.this.dexes.length];
            int[] iArr = new int[DexMerger.this.dexes.length];
            int[] iArr2 = new int[DexMerger.this.dexes.length];
            TreeMap treeMap = new TreeMap();
            for (int i2 = 0; i2 < DexMerger.this.dexes.length; i2++) {
                sectionArr[i2] = getSection(DexMerger.this.dexes[i2].getTableOfContents());
                sectionArr2[i2] = sectionArr[i2].exists() ? DexMerger.this.dexes[i2].open(sectionArr[i2].off) : null;
                iArr[i2] = readIntoMap(sectionArr2[i2], sectionArr[i2], DexMerger.this.indexMaps[i2], iArr2[i2], treeMap, i2);
            }
            getSection(DexMerger.this.contentsOut).off = this.out.getPosition();
            while (!treeMap.isEmpty()) {
                Map.Entry pollFirstEntry = treeMap.pollFirstEntry();
                List value = (List) pollFirstEntry.getValue();
                for (int j = 0, valueSize = value.size(); j < valueSize; j++) {
                    Integer num = (Integer) value.get(j);
                    int i3 = iArr[num.intValue()];
                    IndexMap indexMap = DexMerger.this.indexMaps[num.intValue()];
                    int intValue = num.intValue();
                    int i4 = iArr2[intValue];
                    iArr2[intValue] = i4 + 1;
                    updateIndex(i3, indexMap, i4, i);
                    iArr[num.intValue()] = readIntoMap(sectionArr2[num.intValue()], sectionArr[num.intValue()], DexMerger.this.indexMaps[num.intValue()], iArr2[num.intValue()], treeMap, num.intValue());
                }
                write((T) pollFirstEntry.getKey());
                i++;
            }
            getSection(DexMerger.this.contentsOut).size = i;
        }

        private int readIntoMap(Dex.Section section, TableOfContents.Section section2, IndexMap indexMap, int i, TreeMap<T, List<Integer>> treeMap, int i2) {
            int position = section != null ? section.getPosition() : -1;
            if (i < section2.size) {
                T read = read(section, indexMap, i);
                List<Integer> list = treeMap.get(read);
                if (list == null) {
                    list = new ArrayList<>();
                    treeMap.put(read, list);
                }
                list.add(new Integer(i2));
            }
            return position;
        }
        public final void mergeUnsorted() {
            int i;
            getSection(DexMerger.this.contentsOut).off = this.out.getPosition();
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < DexMerger.this.dexes.length; i2++) {
                arrayList.addAll(readUnsortedValues(DexMerger.this.dexes[i2], DexMerger.this.indexMaps[i2]));
            }
            Collections.sort(arrayList);
            int i3 = 0;
            for (int i4 = 0; i4 < arrayList.size(); i4 = i) {
                i = i4 + 1;
                UnsortedValue unsortedValue = (UnsortedValue) arrayList.get(i4);
                updateIndex(unsortedValue.offset, unsortedValue.indexMap, unsortedValue.index, i3 - 1);
                while (i < arrayList.size() && unsortedValue.compareTo((UnsortedValue) arrayList.get(i)) == 0) {
                    UnsortedValue unsortedValue2 = (UnsortedValue) arrayList.get(i);
                    updateIndex(unsortedValue2.offset, unsortedValue2.indexMap, unsortedValue2.index, i3 - 1);
                    i++;
                }
                write(unsortedValue.value);
                i3++;
            }
            getSection(DexMerger.this.contentsOut).size = i3;
        }

        private List<IdMerger<T>.UnsortedValue> readUnsortedValues(Dex dex, IndexMap indexMap) {
            TableOfContents.Section section = getSection(dex.getTableOfContents());
            if (!section.exists()) {
                return Collections.emptyList();
            }
            ArrayList arrayList = new ArrayList();
            Dex.Section open = dex.open(section.off);
            for (int i = 0; i < section.size; i++) {
                arrayList.add(new UnsortedValue(dex, indexMap, read(open, indexMap, 0), i, open.getPosition()));
            }
            return arrayList;
        }

        class UnsortedValue implements Comparable<IdMerger<T>.UnsortedValue> {
            final int index;
            final IndexMap indexMap;
            final int offset;
            final Dex source;
            final T value;

            @Override // java.lang.Comparable
            public /* bridge */ /* synthetic */ int compareTo(IdMerger<T>.UnsortedValue unsortedValue) {
                return compareTo(unsortedValue);
            }

            UnsortedValue(Dex dex, IndexMap indexMap2, T t, int i, int i2) {
                this.source = dex;
                this.indexMap = indexMap2;
                this.value = t;
                this.index = i;
                this.offset = i2;
            }

            public int compareToTwo(IdMerger<T>.UnsortedValue unsortedValue) {
                return this.value.compareTo(unsortedValue.value);
            }
        }
    }
}
