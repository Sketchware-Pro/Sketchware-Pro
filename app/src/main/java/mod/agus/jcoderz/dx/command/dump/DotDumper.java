package mod.agus.jcoderz.dx.command.dump;

import mod.agus.jcoderz.dx.cf.code.ConcreteMethod;
import mod.agus.jcoderz.dx.cf.code.Ropper;
import mod.agus.jcoderz.dx.cf.direct.DirectClassFile;
import mod.agus.jcoderz.dx.cf.direct.StdAttributeFactory;
import mod.agus.jcoderz.dx.cf.iface.Member;
import mod.agus.jcoderz.dx.cf.iface.Method;
import mod.agus.jcoderz.dx.cf.iface.ParseObserver;
import mod.agus.jcoderz.dx.rop.code.AccessFlags;
import mod.agus.jcoderz.dx.rop.code.BasicBlock;
import mod.agus.jcoderz.dx.rop.code.BasicBlockList;
import mod.agus.jcoderz.dx.rop.code.DexTranslationAdvice;
import mod.agus.jcoderz.dx.rop.code.RopMethod;
import mod.agus.jcoderz.dx.ssa.Optimizer;
import mod.agus.jcoderz.dx.util.ByteArray;
import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.IntList;

public class DotDumper implements ParseObserver {
    private final Args args;
    private final byte[] bytes;
    private DirectClassFile classFile;
    private final String filePath;
    private final boolean optimize;
    private final boolean strictParse;

    static void dump(byte[] bArr, String str, Args args2) {
        new DotDumper(bArr, str, args2).run();
    }

    DotDumper(byte[] bArr, String str, Args args2) {
        this.bytes = bArr;
        this.filePath = str;
        this.strictParse = args2.strictParse;
        this.optimize = args2.optimize;
        this.args = args2;
    }

    private void run() {
        ByteArray byteArray = new ByteArray(this.bytes);
        this.classFile = new DirectClassFile(byteArray, this.filePath, this.strictParse);
        this.classFile.setAttributeFactory(StdAttributeFactory.THE_ONE);
        this.classFile.getMagic();
        DirectClassFile directClassFile = new DirectClassFile(byteArray, this.filePath, this.strictParse);
        directClassFile.setAttributeFactory(StdAttributeFactory.THE_ONE);
        directClassFile.setObserver(this);
        directClassFile.getMagic();
    }

    /* access modifiers changed from: protected */
    public boolean shouldDumpMethod(String str) {
        return this.args.method == null || this.args.method.equals(str);
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.ParseObserver
    public void changeIndent(int i) {
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.ParseObserver
    public void parsed(ByteArray byteArray, int i, int i2, String str) {
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.ParseObserver
    public void startParsingMember(ByteArray byteArray, int i, String str, String str2) {
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.ParseObserver
    public void endParsingMember(ByteArray byteArray, int i, String str, String str2, Member member) {
        if ((member instanceof Method) && shouldDumpMethod(str)) {
            ConcreteMethod concreteMethod = new ConcreteMethod((Method) member, this.classFile, true, true);
            DexTranslationAdvice dexTranslationAdvice = DexTranslationAdvice.THE_ONE;
            RopMethod convert = Ropper.convert(concreteMethod, dexTranslationAdvice, this.classFile.getMethods());
            if (this.optimize) {
                boolean isStatic = AccessFlags.isStatic(concreteMethod.getAccessFlags());
                convert = Optimizer.optimize(convert, BaseDumper.computeParamWidth(concreteMethod, isStatic), isStatic, true, dexTranslationAdvice);
            }
            System.out.println("digraph " + str + "{");
            System.out.println("\tfirst -> n" + Hex.u2(convert.getFirstLabel()) + ";");
            BasicBlockList blocks = convert.getBlocks();
            int size = blocks.size();
            for (int i2 = 0; i2 < size; i2++) {
                BasicBlock basicBlock = blocks.get(i2);
                int label = basicBlock.getLabel();
                IntList successors = basicBlock.getSuccessors();
                if (successors.size() == 0) {
                    System.out.println("\tn" + Hex.u2(label) + " -> returns;");
                } else if (successors.size() == 1) {
                    System.out.println("\tn" + Hex.u2(label) + " -> n" + Hex.u2(successors.get(0)) + ";");
                } else {
                    System.out.print("\tn" + Hex.u2(label) + " -> {");
                    for (int i3 = 0; i3 < successors.size(); i3++) {
                        int i4 = successors.get(i3);
                        if (i4 != basicBlock.getPrimarySuccessor()) {
                            System.out.print(" n" + Hex.u2(i4) + " ");
                        }
                    }
                    System.out.println("};");
                    System.out.println("\tn" + Hex.u2(label) + " -> n" + Hex.u2(basicBlock.getPrimarySuccessor()) + " [label=\"primary\"];");
                }
            }
            System.out.println("}");
        }
    }
}
