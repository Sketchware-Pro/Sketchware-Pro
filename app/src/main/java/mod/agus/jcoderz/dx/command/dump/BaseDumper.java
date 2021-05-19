package mod.agus.jcoderz.dx.command.dump;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;

import mod.agus.jcoderz.dx.cf.code.ConcreteMethod;
import mod.agus.jcoderz.dx.cf.iface.Member;
import mod.agus.jcoderz.dx.cf.iface.ParseObserver;
import mod.agus.jcoderz.dx.util.ByteArray;
import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.IndentingWriter;
import mod.agus.jcoderz.dx.util.TwoColumnOutput;

public abstract class BaseDumper implements ParseObserver {
    private final byte[] bytes;
    private final String filePath;
    private final int hexCols;
    private final PrintStream out;
    private final boolean rawBytes;
    private final boolean strictParse;
    private final int width;
    protected Args args;
    private int at;
    private int indent;
    private String separator;

    public BaseDumper(byte[] bArr, PrintStream printStream, String str, Args args2) {
        this.bytes = bArr;
        this.rawBytes = args2.rawBytes;
        this.out = printStream;
        this.width = args2.width <= 0 ? 79 : args2.width;
        this.filePath = str;
        this.strictParse = args2.strictParse;
        this.indent = 0;
        this.separator = this.rawBytes ? "|" : "";
        this.at = 0;
        this.args = args2;
        int i = (((this.width - 5) / 15) + 1) & -2;
        if (i < 6) {
            i = 6;
        } else if (i > 10) {
            i = 10;
        }
        this.hexCols = i;
    }

    static int computeParamWidth(ConcreteMethod concreteMethod, boolean z) {
        return concreteMethod.getEffectiveDescriptor().getParameterTypes().getWordCount();
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.ParseObserver
    public void changeIndent(int i) {
        this.indent += i;
        this.separator = this.rawBytes ? "|" : "";
        for (int i2 = 0; i2 < this.indent; i2++) {
            this.separator = this.separator + "  ";
        }
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.ParseObserver
    public void parsed(ByteArray byteArray, int i, int i2, String str) {
        int underlyingOffset = byteArray.underlyingOffset(i, getBytes());
        boolean rawBytes2 = getRawBytes();
        if (underlyingOffset < this.at) {
            println("<dump skipped backwards to " + Hex.u4(underlyingOffset) + ">");
            this.at = underlyingOffset;
        } else if (underlyingOffset > this.at) {
            print(twoColumns(rawBytes2 ? hexDump(this.at, underlyingOffset - this.at) : "", "<skipped to " + Hex.u4(underlyingOffset) + ">"));
            this.at = underlyingOffset;
        }
        print(twoColumns(rawBytes2 ? hexDump(underlyingOffset, i2) : "", str));
        this.at += i2;
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.ParseObserver
    public void startParsingMember(ByteArray byteArray, int i, String str, String str2) {
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.ParseObserver
    public void endParsingMember(ByteArray byteArray, int i, String str, String str2, Member member) {
    }

    public final int getAt() {
        return this.at;
    }

    public final void setAt(ByteArray byteArray, int i) {
        this.at = byteArray.underlyingOffset(i, this.bytes);
    }

    public final byte[] getBytes() {
        return this.bytes;
    }

    public final String getFilePath() {
        return this.filePath;
    }

    public final boolean getStrictParse() {
        return this.strictParse;
    }

    public final void print(String str) {
        this.out.print(str);
    }

    public final void println(String str) {
        this.out.println(str);
    }

    public final boolean getRawBytes() {
        return this.rawBytes;
    }

    public final int getWidth1() {
        if (this.rawBytes) {
            return (this.hexCols * 2) + 5 + (this.hexCols / 2);
        }
        return 0;
    }

    public final int getWidth2() {
        return (this.width - (this.rawBytes ? getWidth1() + 1 : 0)) - (this.indent * 2);
    }

    public final String hexDump(int i, int i2) {
        return Hex.dump(this.bytes, i, i2, i, this.hexCols, 4);
    }

    public final String twoColumns(String str, String str2) {
        int width1 = getWidth1();
        int width2 = getWidth2();
        if (width1 != 0) {
            return TwoColumnOutput.toString(str, width1, this.separator, str2, width2);
        }
        try {
            int length = str2.length();
            StringWriter stringWriter = new StringWriter(length * 2);
            IndentingWriter indentingWriter = new IndentingWriter(stringWriter, width2, this.separator);
            indentingWriter.write(str2);
            if (length == 0 || str2.charAt(length - 1) != '\n') {
                indentingWriter.write(10);
            }
            indentingWriter.flush();
            return stringWriter.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
