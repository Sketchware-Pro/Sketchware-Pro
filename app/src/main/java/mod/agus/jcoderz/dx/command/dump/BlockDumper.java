package mod.agus.jcoderz.dx.command.dump;

import org.eclipse.jdt.internal.compiler.codegen.ConstantPool;

import java.io.PrintStream;

import mod.agus.jcoderz.dx.cf.code.BasicBlocker;
import mod.agus.jcoderz.dx.cf.code.ByteBlock;
import mod.agus.jcoderz.dx.cf.code.ByteBlockList;
import mod.agus.jcoderz.dx.cf.code.ByteCatchList;
import mod.agus.jcoderz.dx.cf.code.BytecodeArray;
import mod.agus.jcoderz.dx.cf.code.ConcreteMethod;
import mod.agus.jcoderz.dx.cf.code.Ropper;
import mod.agus.jcoderz.dx.cf.direct.CodeObserver;
import mod.agus.jcoderz.dx.cf.direct.DirectClassFile;
import mod.agus.jcoderz.dx.cf.direct.StdAttributeFactory;
import mod.agus.jcoderz.dx.cf.iface.Member;
import mod.agus.jcoderz.dx.cf.iface.Method;
import mod.agus.jcoderz.dx.rop.code.AccessFlags;
import mod.agus.jcoderz.dx.rop.code.BasicBlock;
import mod.agus.jcoderz.dx.rop.code.BasicBlockList;
import mod.agus.jcoderz.dx.rop.code.DexTranslationAdvice;
import mod.agus.jcoderz.dx.rop.code.InsnList;
import mod.agus.jcoderz.dx.rop.code.RopMethod;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.ssa.Optimizer;
import mod.agus.jcoderz.dx.util.ByteArray;
import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.IntList;

public class BlockDumper extends BaseDumper {
    protected DirectClassFile classFile = null;
    protected boolean suppressDump = true;
    private boolean first = true;
    private final boolean optimize;
    private final boolean rop;

    BlockDumper(byte[] bArr, PrintStream printStream, String str, boolean z, Args args) {
        super(bArr, printStream, str, args);
        this.rop = z;
        this.optimize = args.optimize;
    }

    public static void dump(byte[] bArr, PrintStream printStream, String str, boolean z, Args args) {
        new BlockDumper(bArr, printStream, str, z, args).dump();
    }

    public void dump() {
        ByteArray byteArray = new ByteArray(getBytes());
        this.classFile = new DirectClassFile(byteArray, getFilePath(), getStrictParse());
        this.classFile.setAttributeFactory(StdAttributeFactory.THE_ONE);
        this.classFile.getMagic();
        DirectClassFile directClassFile = new DirectClassFile(byteArray, getFilePath(), getStrictParse());
        directClassFile.setAttributeFactory(StdAttributeFactory.THE_ONE);
        directClassFile.setObserver(this);
        directClassFile.getMagic();
    }

    @Override
    // mod.agus.jcoderz.dx.command.dump.BaseDumper, mod.agus.jcoderz.dx.cf.iface.ParseObserver
    public void changeIndent(int i) {
        if (!this.suppressDump) {
            super.changeIndent(i);
        }
    }

    @Override
    // mod.agus.jcoderz.dx.command.dump.BaseDumper, mod.agus.jcoderz.dx.cf.iface.ParseObserver
    public void parsed(ByteArray byteArray, int i, int i2, String str) {
        if (!this.suppressDump) {
            super.parsed(byteArray, i, i2, str);
        }
    }

    /* access modifiers changed from: protected */
    public boolean shouldDumpMethod(String str) {
        return this.args.method == null || this.args.method.equals(str);
    }

    @Override
    // mod.agus.jcoderz.dx.command.dump.BaseDumper, mod.agus.jcoderz.dx.cf.iface.ParseObserver
    public void startParsingMember(ByteArray byteArray, int i, String str, String str2) {
        if (str2.indexOf(40) >= 0 && shouldDumpMethod(str)) {
            setAt(byteArray, i);
            this.suppressDump = false;
            if (this.first) {
                this.first = false;
            } else {
                parsed(byteArray, i, 0, "\n");
            }
            parsed(byteArray, i, 0, "method " + str + " " + str2);
            this.suppressDump = true;
        }
    }

    @Override
    // mod.agus.jcoderz.dx.command.dump.BaseDumper, mod.agus.jcoderz.dx.cf.iface.ParseObserver
    public void endParsingMember(ByteArray byteArray, int i, String str, String str2, Member member) {
        if ((member instanceof Method) && shouldDumpMethod(str) && (member.getAccessFlags() & 1280) == 0) {
            ConcreteMethod concreteMethod = new ConcreteMethod((Method) member, this.classFile, true, true);
            if (this.rop) {
                ropDump(concreteMethod);
            } else {
                regularDump(concreteMethod);
            }
        }
    }

    private void regularDump(ConcreteMethod concreteMethod) {
        String human;
        BytecodeArray code = concreteMethod.getCode();
        ByteArray bytes = code.getBytes();
        ByteBlockList identifyBlocks = BasicBlocker.identifyBlocks(concreteMethod);
        int size = identifyBlocks.size();
        CodeObserver codeObserver = new CodeObserver(bytes, this);
        setAt(bytes, 0);
        this.suppressDump = false;
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            ByteBlock byteBlock = identifyBlocks.get(i2);
            int start = byteBlock.getStart();
            int end = byteBlock.getEnd();
            if (i < start) {
                parsed(bytes, i, start - i, "dead code " + Hex.u2(i) + ".." + Hex.u2(start));
            }
            parsed(bytes, start, 0, "block " + Hex.u2(byteBlock.getLabel()) + ": " + Hex.u2(start) + ".." + Hex.u2(end));
            changeIndent(1);
            while (start < end) {
                int parseInstruction = code.parseInstruction(start, codeObserver);
                codeObserver.setPreviousOffset(start);
                start += parseInstruction;
            }
            IntList successors = byteBlock.getSuccessors();
            int size2 = successors.size();
            if (size2 == 0) {
                parsed(bytes, end, 0, "returns");
            } else {
                for (int i3 = 0; i3 < size2; i3++) {
                    parsed(bytes, end, 0, "next " + Hex.u2(successors.get(i3)));
                }
            }
            ByteCatchList catches = byteBlock.getCatches();
            int size3 = catches.size();
            for (int i4 = 0; i4 < size3; i4++) {
                ByteCatchList.Item item = catches.get(i4);
                CstType exceptionClass = item.getExceptionClass();
                StringBuilder sb = new StringBuilder("catch ");
                if (exceptionClass == CstType.OBJECT) {
                    human = "<any>";
                } else {
                    human = exceptionClass.toHuman();
                }
                parsed(bytes, end, 0, sb.append(human).append(" -> ").append(Hex.u2(item.getHandlerPc())).toString());
            }
            changeIndent(-1);
            i2++;
            i = end;
        }
        int size4 = bytes.size();
        if (i < size4) {
            parsed(bytes, i, size4 - i, "dead code " + Hex.u2(i) + ".." + Hex.u2(size4));
        }
        this.suppressDump = true;
    }

    private void ropDump(ConcreteMethod concreteMethod) {
        DexTranslationAdvice dexTranslationAdvice = DexTranslationAdvice.THE_ONE;
        ByteArray bytes = concreteMethod.getCode().getBytes();
        RopMethod convert = Ropper.convert(concreteMethod, dexTranslationAdvice, this.classFile.getMethods());
        StringBuffer stringBuffer = new StringBuffer((int) ConstantPool.CONSTANTPOOL_INITIAL_SIZE);
        if (this.optimize) {
            boolean isStatic = AccessFlags.isStatic(concreteMethod.getAccessFlags());
            convert = Optimizer.optimize(convert, computeParamWidth(concreteMethod, isStatic), isStatic, true, dexTranslationAdvice);
        }
        BasicBlockList blocks = convert.getBlocks();
        int[] labelsInOrder = blocks.getLabelsInOrder();
        stringBuffer.append("first " + Hex.u2(convert.getFirstLabel()) + "\n");
        for (int i : labelsInOrder) {
            BasicBlock basicBlock = blocks.get(blocks.indexOfLabel(i));
            stringBuffer.append("block ");
            stringBuffer.append(Hex.u2(i));
            stringBuffer.append("\n");
            IntList labelToPredecessors = convert.labelToPredecessors(i);
            int size = labelToPredecessors.size();
            for (int i2 = 0; i2 < size; i2++) {
                stringBuffer.append("  pred ");
                stringBuffer.append(Hex.u2(labelToPredecessors.get(i2)));
                stringBuffer.append("\n");
            }
            InsnList insns = basicBlock.getInsns();
            int size2 = insns.size();
            for (int i3 = 0; i3 < size2; i3++) {
                insns.get(i3);
                stringBuffer.append("  ");
                stringBuffer.append(insns.get(i3).toHuman());
                stringBuffer.append("\n");
            }
            IntList successors = basicBlock.getSuccessors();
            int size3 = successors.size();
            if (size3 == 0) {
                stringBuffer.append("  returns\n");
            } else {
                int primarySuccessor = basicBlock.getPrimarySuccessor();
                for (int i4 = 0; i4 < size3; i4++) {
                    int i5 = successors.get(i4);
                    stringBuffer.append("  next ");
                    stringBuffer.append(Hex.u2(i5));
                    if (size3 != 1 && i5 == primarySuccessor) {
                        stringBuffer.append(" *");
                    }
                    stringBuffer.append("\n");
                }
            }
        }
        this.suppressDump = false;
        setAt(bytes, 0);
        parsed(bytes, 0, bytes.size(), stringBuffer.toString());
        this.suppressDump = true;
    }
}
