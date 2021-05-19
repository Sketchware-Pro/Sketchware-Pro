package mod.agus.jcoderz.dx.dex.code;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import mod.agus.jcoderz.dex.util.ExceptionWithContext;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstBaseMethodRef;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.FixedSizeList;
import mod.agus.jcoderz.dx.util.IndentingWriter;

public final class DalvInsnList extends FixedSizeList {
    private final int regCount;

    public DalvInsnList(int i, int i2) {
        super(i);
        this.regCount = i2;
    }

    public static DalvInsnList makeImmutable(ArrayList<DalvInsn> arrayList, int i) {
        int size = arrayList.size();
        DalvInsnList dalvInsnList = new DalvInsnList(size, i);
        for (int i2 = 0; i2 < size; i2++) {
            dalvInsnList.set(i2, arrayList.get(i2));
        }
        dalvInsnList.setImmutable();
        return dalvInsnList;
    }

    public DalvInsn get(int i) {
        return (DalvInsn) get0(i);
    }

    public void set(int i, DalvInsn dalvInsn) {
        set0(i, dalvInsn);
    }

    public int codeSize() {
        int size = size();
        if (size == 0) {
            return 0;
        }
        return get(size - 1).getNextAddress();
    }

    public void writeTo(AnnotatedOutput annotatedOutput) {
        String str;
        int cursor = annotatedOutput.getCursor();
        int size = size();
        if (annotatedOutput.annotates()) {
            boolean isVerbose = annotatedOutput.isVerbose();
            for (int i = 0; i < size; i++) {
                DalvInsn dalvInsn = (DalvInsn) get0(i);
                int codeSize = dalvInsn.codeSize() * 2;
                if (codeSize != 0 || isVerbose) {
                    str = dalvInsn.listingString("  ", annotatedOutput.getAnnotationWidth(), true);
                } else {
                    str = null;
                }
                if (str != null) {
                    annotatedOutput.annotate(codeSize, str);
                } else if (codeSize != 0) {
                    annotatedOutput.annotate(codeSize, "");
                }
            }
        }
        for (int i2 = 0; i2 < size; i2++) {
            DalvInsn dalvInsn2 = (DalvInsn) get0(i2);
            try {
                dalvInsn2.writeTo(annotatedOutput);
            } catch (RuntimeException e) {
                throw ExceptionWithContext.withContext(e, "...while writing " + dalvInsn2);
            }
        }
        int cursor2 = (annotatedOutput.getCursor() - cursor) / 2;
        if (cursor2 != codeSize()) {
            throw new RuntimeException("write length mismatch; expected " + codeSize() + " but actually wrote " + cursor2);
        }
    }

    public int getRegistersSize() {
        return this.regCount;
    }

    public int getOutsSize() {
        boolean z;
        int parameterWordCount;
        int size = size();
        int i = 0;
        int i2 = 0;
        while (i < size) {
            DalvInsn dalvInsn = (DalvInsn) get0(i);
            if (!(dalvInsn instanceof CstInsn)) {
                parameterWordCount = i2;
            } else {
                Constant constant = ((CstInsn) dalvInsn).getConstant();
                if (!(constant instanceof CstBaseMethodRef)) {
                    parameterWordCount = i2;
                } else {
                    z = dalvInsn.getOpcode().getFamily() == 113;
                    parameterWordCount = ((CstBaseMethodRef) constant).getParameterWordCount(z);
                    if (parameterWordCount <= i2) {
                        parameterWordCount = i2;
                    }
                }
            }
            i++;
            i2 = parameterWordCount;
        }
        return i2;
    }

    public void debugPrint(Writer writer, String str, boolean z) throws IOException {
        String str2;
        IndentingWriter indentingWriter = new IndentingWriter(writer, 0, str);
        int size = size();
        for (int i = 0; i < size; i++) {
            DalvInsn dalvInsn = (DalvInsn) get0(i);
            if (dalvInsn.codeSize() != 0 || z) {
                str2 = dalvInsn.listingString("", 0, z);
            } else {
                str2 = null;
            }
            if (str2 != null) {
                indentingWriter.write(str2);
            }
        }
        try {
            indentingWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void debugPrint(OutputStream outputStream, String str, boolean z) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        debugPrint(outputStreamWriter, str, z);
        try {
            outputStreamWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
