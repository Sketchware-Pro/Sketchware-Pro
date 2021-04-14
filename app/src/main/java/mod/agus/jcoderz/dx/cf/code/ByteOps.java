package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dx.util.Hex;

public class ByteOps {
    public static final int AALOAD = 50;
    public static final int AASTORE = 83;
    public static final int ACONST_NULL = 1;
    public static final int ALOAD = 25;
    public static final int ALOAD_0 = 42;
    public static final int ALOAD_1 = 43;
    public static final int ALOAD_2 = 44;
    public static final int ALOAD_3 = 45;
    public static final int ANEWARRAY = 189;
    public static final int ARETURN = 176;
    public static final int ARRAYLENGTH = 190;
    public static final int ASTORE = 58;
    public static final int ASTORE_0 = 75;
    public static final int ASTORE_1 = 76;
    public static final int ASTORE_2 = 77;
    public static final int ASTORE_3 = 78;
    public static final int ATHROW = 191;
    public static final int BALOAD = 51;
    public static final int BASTORE = 84;
    public static final int BIPUSH = 16;
    public static final int CALOAD = 52;
    public static final int CASTORE = 85;
    public static final int CHECKCAST = 192;
    public static final int CPOK_Class = 512;
    public static final int CPOK_Double = 256;
    public static final int CPOK_Fieldref = 2048;
    public static final int CPOK_Float = 64;
    public static final int CPOK_Integer = 32;
    public static final int CPOK_InterfaceMethodref = 8192;
    public static final int CPOK_Long = 128;
    public static final int CPOK_Methodref = 4096;
    public static final int CPOK_String = 1024;
    public static final int D2F = 144;
    public static final int D2I = 142;
    public static final int D2L = 143;
    public static final int DADD = 99;
    public static final int DALOAD = 49;
    public static final int DASTORE = 82;
    public static final int DCMPG = 152;
    public static final int DCMPL = 151;
    public static final int DCONST_0 = 14;
    public static final int DCONST_1 = 15;
    public static final int DDIV = 111;
    public static final int DLOAD = 24;
    public static final int DLOAD_0 = 38;
    public static final int DLOAD_1 = 39;
    public static final int DLOAD_2 = 40;
    public static final int DLOAD_3 = 41;
    public static final int DMUL = 107;
    public static final int DNEG = 119;
    public static final int DREM = 115;
    public static final int DRETURN = 175;
    public static final int DSTORE = 57;
    public static final int DSTORE_0 = 71;
    public static final int DSTORE_1 = 72;
    public static final int DSTORE_2 = 73;
    public static final int DSTORE_3 = 74;
    public static final int DSUB = 103;
    public static final int DUP = 89;
    public static final int DUP2 = 92;
    public static final int DUP2_X1 = 93;
    public static final int DUP2_X2 = 94;
    public static final int DUP_X1 = 90;
    public static final int DUP_X2 = 91;
    public static final int F2D = 141;
    public static final int F2I = 139;
    public static final int F2L = 140;
    public static final int FADD = 98;
    public static final int FALOAD = 48;
    public static final int FASTORE = 81;
    public static final int FCMPG = 150;
    public static final int FCMPL = 149;
    public static final int FCONST_0 = 11;
    public static final int FCONST_1 = 12;
    public static final int FCONST_2 = 13;
    public static final int FDIV = 110;
    public static final int FLOAD = 23;
    public static final int FLOAD_0 = 34;
    public static final int FLOAD_1 = 35;
    public static final int FLOAD_2 = 36;
    public static final int FLOAD_3 = 37;
    public static final int FMT_BRANCH = 7;
    public static final int FMT_CPI = 9;
    public static final int FMT_INVALID = 0;
    public static final int FMT_INVOKEINTERFACE = 13;
    public static final int FMT_LDC = 14;
    public static final int FMT_LITERAL_BYTE = 12;
    public static final int FMT_LOCAL_1 = 10;
    public static final int FMT_LOCAL_2 = 11;
    public static final int FMT_LOOKUPSWITCH = 17;
    public static final int FMT_MASK = 31;
    public static final int FMT_MULTIANEWARRAY = 18;
    public static final int FMT_NO_ARGS = 1;
    public static final int FMT_NO_ARGS_LOCALS_1 = 2;
    public static final int FMT_NO_ARGS_LOCALS_2 = 3;
    public static final int FMT_NO_ARGS_LOCALS_3 = 4;
    public static final int FMT_NO_ARGS_LOCALS_4 = 5;
    public static final int FMT_NO_ARGS_LOCALS_5 = 6;
    public static final int FMT_SIPUSH = 15;
    public static final int FMT_TABLESWITCH = 16;
    public static final int FMT_WIDE = 19;
    public static final int FMT_WIDE_BRANCH = 8;
    public static final int FMUL = 106;
    public static final int FNEG = 118;
    public static final int FREM = 114;
    public static final int FRETURN = 174;
    public static final int FSTORE = 56;
    public static final int FSTORE_0 = 67;
    public static final int FSTORE_1 = 68;
    public static final int FSTORE_2 = 69;
    public static final int FSTORE_3 = 70;
    public static final int FSUB = 102;
    public static final int GETFIELD = 180;
    public static final int GETSTATIC = 178;
    public static final int GOTO = 167;
    public static final int GOTO_W = 200;
    public static final int I2B = 145;
    public static final int I2C = 146;
    public static final int I2D = 135;
    public static final int I2F = 134;
    public static final int I2L = 133;
    public static final int I2S = 147;
    public static final int IADD = 96;
    public static final int IALOAD = 46;
    public static final int IAND = 126;
    public static final int IASTORE = 79;
    public static final int ICONST_0 = 3;
    public static final int ICONST_1 = 4;
    public static final int ICONST_2 = 5;
    public static final int ICONST_3 = 6;
    public static final int ICONST_4 = 7;
    public static final int ICONST_5 = 8;
    public static final int ICONST_M1 = 2;
    public static final int IDIV = 108;
    public static final int IFEQ = 153;
    public static final int IFGE = 156;
    public static final int IFGT = 157;
    public static final int IFLE = 158;
    public static final int IFLT = 155;
    public static final int IFNE = 154;
    public static final int IFNONNULL = 199;
    public static final int IFNULL = 198;
    public static final int IF_ACMPEQ = 165;
    public static final int IF_ACMPNE = 166;
    public static final int IF_ICMPEQ = 159;
    public static final int IF_ICMPGE = 162;
    public static final int IF_ICMPGT = 163;
    public static final int IF_ICMPLE = 164;
    public static final int IF_ICMPLT = 161;
    public static final int IF_ICMPNE = 160;
    public static final int IINC = 132;
    public static final int ILOAD = 21;
    public static final int ILOAD_0 = 26;
    public static final int ILOAD_1 = 27;
    public static final int ILOAD_2 = 28;
    public static final int ILOAD_3 = 29;
    public static final int IMUL = 104;
    public static final int INEG = 116;
    public static final int INSTANCEOF = 193;
    public static final int INVOKEDYNAMIC = 186;
    public static final int INVOKEINTERFACE = 185;
    public static final int INVOKESPECIAL = 183;
    public static final int INVOKESTATIC = 184;
    public static final int INVOKEVIRTUAL = 182;
    public static final int IOR = 128;
    public static final int IREM = 112;
    public static final int IRETURN = 172;
    public static final int ISHL = 120;
    public static final int ISHR = 122;
    public static final int ISTORE = 54;
    public static final int ISTORE_0 = 59;
    public static final int ISTORE_1 = 60;
    public static final int ISTORE_2 = 61;
    public static final int ISTORE_3 = 62;
    public static final int ISUB = 100;
    public static final int IUSHR = 124;
    public static final int IXOR = 130;
    public static final int JSR = 168;
    public static final int JSR_W = 201;
    public static final int L2D = 138;
    public static final int L2F = 137;
    public static final int L2I = 136;
    public static final int LADD = 97;
    public static final int LALOAD = 47;
    public static final int LAND = 127;
    public static final int LASTORE = 80;
    public static final int LCMP = 148;
    public static final int LCONST_0 = 9;
    public static final int LCONST_1 = 10;
    public static final int LDC = 18;
    public static final int LDC2_W = 20;
    public static final int LDC_W = 19;
    public static final int LDIV = 109;
    public static final int LLOAD = 22;
    public static final int LLOAD_0 = 30;
    public static final int LLOAD_1 = 31;
    public static final int LLOAD_2 = 32;
    public static final int LLOAD_3 = 33;
    public static final int LMUL = 105;
    public static final int LNEG = 117;
    public static final int LOOKUPSWITCH = 171;
    public static final int LOR = 129;
    public static final int LREM = 113;
    public static final int LRETURN = 173;
    public static final int LSHL = 121;
    public static final int LSHR = 123;
    public static final int LSTORE = 55;
    public static final int LSTORE_0 = 63;
    public static final int LSTORE_1 = 64;
    public static final int LSTORE_2 = 65;
    public static final int LSTORE_3 = 66;
    public static final int LSUB = 101;
    public static final int LUSHR = 125;
    public static final int LXOR = 131;
    public static final int MONITORENTER = 194;
    public static final int MONITOREXIT = 195;
    public static final int MULTIANEWARRAY = 197;
    public static final int NEW = 187;
    public static final int NEWARRAY = 188;
    public static final int NEWARRAY_BOOLEAN = 4;
    public static final int NEWARRAY_BYTE = 8;
    public static final int NEWARRAY_CHAR = 5;
    public static final int NEWARRAY_DOUBLE = 7;
    public static final int NEWARRAY_FLOAT = 6;
    public static final int NEWARRAY_INT = 10;
    public static final int NEWARRAY_LONG = 11;
    public static final int NEWARRAY_SHORT = 9;
    public static final int NOP = 0;
    private static final String OPCODE_DETAILS = "00 - nop;01 - aconst_null;02 - iconst_m1;03 - iconst_0;04 - iconst_1;05 - iconst_2;06 - iconst_3;07 - iconst_4;08 - iconst_5;09 - lconst_0;0a - lconst_1;0b - fconst_0;0c - fconst_1;0d - fconst_2;0e - dconst_0;0f - dconst_1;10 y bipush;11 S sipush;12 L:IFcs ldc;13 p:IFcs ldc_w;14 p:DJ ldc2_w;15 l iload;16 m lload;17 l fload;18 m dload;19 l aload;1a 0 iload_0;1b 1 iload_1;1c 2 iload_2;1d 3 iload_3;1e 1 lload_0;1f 2 lload_1;20 3 lload_2;21 4 lload_3;22 0 fload_0;23 1 fload_1;24 2 fload_2;25 3 fload_3;26 1 dload_0;27 2 dload_1;28 3 dload_2;29 4 dload_3;2a 0 aload_0;2b 1 aload_1;2c 2 aload_2;2d 3 aload_3;2e - iaload;2f - laload;30 - faload;31 - daload;32 - aaload;33 - baload;34 - caload;35 - saload;36 - istore;37 - lstore;38 - fstore;39 - dstore;3a - astore;3b 0 istore_0;3c 1 istore_1;3d 2 istore_2;3e 3 istore_3;3f 1 lstore_0;40 2 lstore_1;41 3 lstore_2;42 4 lstore_3;43 0 fstore_0;44 1 fstore_1;45 2 fstore_2;46 3 fstore_3;47 1 dstore_0;48 2 dstore_1;49 3 dstore_2;4a 4 dstore_3;4b 0 astore_0;4c 1 astore_1;4d 2 astore_2;4e 3 astore_3;4f - iastore;50 - lastore;51 - fastore;52 - dastore;53 - aastore;54 - bastore;55 - castore;56 - sastore;57 - pop;58 - pop2;59 - dup;5a - dup_x1;5b - dup_x2;5c - dup2;5d - dup2_x1;5e - dup2_x2;5f - swap;60 - iadd;61 - ladd;62 - fadd;63 - dadd;64 - isub;65 - lsub;66 - fsub;67 - dsub;68 - imul;69 - lmul;6a - fmul;6b - dmul;6c - idiv;6d - ldiv;6e - fdiv;6f - ddiv;70 - irem;71 - lrem;72 - frem;73 - drem;74 - ineg;75 - lneg;76 - fneg;77 - dneg;78 - ishl;79 - lshl;7a - ishr;7b - lshr;7c - iushr;7d - lushr;7e - iand;7f - land;80 - ior;81 - lor;82 - ixor;83 - lxor;84 l iinc;85 - i2l;86 - i2f;87 - i2d;88 - l2i;89 - l2f;8a - l2d;8b - f2i;8c - f2l;8d - f2d;8e - d2i;8f - d2l;90 - d2f;91 - i2b;92 - i2c;93 - i2s;94 - lcmp;95 - fcmpl;96 - fcmpg;97 - dcmpl;98 - dcmpg;99 b ifeq;9a b ifne;9b b iflt;9c b ifge;9d b ifgt;9e b ifle;9f b if_icmpeq;a0 b if_icmpne;a1 b if_icmplt;a2 b if_icmpge;a3 b if_icmpgt;a4 b if_icmple;a5 b if_acmpeq;a6 b if_acmpne;a7 b goto;a8 b jsr;a9 l ret;aa T tableswitch;ab U lookupswitch;ac - ireturn;ad - lreturn;ae - freturn;af - dreturn;b0 - areturn;b1 - return;b2 p:f getstatic;b3 p:f putstatic;b4 p:f getfield;b5 p:f putfield;b6 p:m invokevirtual;b7 p:m invokespecial;b8 p:m invokestatic;b9 I:i invokeinterface;bb p:c new;bc y newarray;bd p:c anewarray;be - arraylength;bf - athrow;c0 p:c checkcast;c1 p:c instanceof;c2 - monitorenter;c3 - monitorexit;c4 W wide;c5 M:c multianewarray;c6 b ifnull;c7 b ifnonnull;c8 c goto_w;c9 c jsr_w;";
    private static final int[] OPCODE_INFO = new int[256];
    private static final String[] OPCODE_NAMES = new String[256];
    public static final int POP = 87;
    public static final int POP2 = 88;
    public static final int PUTFIELD = 181;
    public static final int PUTSTATIC = 179;
    public static final int RET = 169;
    public static final int RETURN = 177;
    public static final int SALOAD = 53;
    public static final int SASTORE = 86;
    public static final int SIPUSH = 17;
    public static final int SWAP = 95;
    public static final int TABLESWITCH = 170;
    public static final int WIDE = 196;

    static {
        int i;
        int length = OPCODE_DETAILS.length();
        int i2 = 0;
        while (i2 < length) {
            int digit = Character.digit(OPCODE_DETAILS.charAt(i2 + 1), 16) | (Character.digit(OPCODE_DETAILS.charAt(i2), 16) << 4);
            switch (OPCODE_DETAILS.charAt(i2 + 3)) {
                case '-':
                    i = 1;
                    break;
                case '0':
                    i = 2;
                    break;
                case '1':
                    i = 3;
                    break;
                case '2':
                    i = 4;
                    break;
                case '3':
                    i = 5;
                    break;
                case '4':
                    i = 6;
                    break;
                case 'I':
                    i = 13;
                    break;
                case 'L':
                    i = 14;
                    break;
                case 'M':
                    i = 18;
                    break;
                case 'S':
                    i = 15;
                    break;
                case 'T':
                    i = 16;
                    break;
                case 'U':
                    i = 17;
                    break;
                case 'W':
                    i = 19;
                    break;
                case 'b':
                    i = 7;
                    break;
                case 'c':
                    i = 8;
                    break;
                case 'l':
                    i = 10;
                    break;
                case 'm':
                    i = 11;
                    break;
                case 'p':
                    i = 9;
                    break;
                case 'y':
                    i = 12;
                    break;
                default:
                    i = 0;
                    break;
            }
            int i3 = i2 + 5;
            if (OPCODE_DETAILS.charAt(i3 - 1) == ':') {
                while (true) {
                    switch (OPCODE_DETAILS.charAt(i3)) {
                        case 'D':
                            i |= 256;
                            i3++;
                        case 'F':
                            i |= 64;
                            i3++;
                        case 'I':
                            i |= 32;
                            i3++;
                        case 'J':
                            i |= 128;
                            i3++;
                        case 'c':
                            i |= 512;
                            i3++;
                        case 'f':
                            i |= 2048;
                            i3++;
                        case 'i':
                            i |= 8192;
                            i3++;
                        case 'm':
                            i |= 4096;
                            i3++;
                        case 's':
                            i |= 1024;
                            i3++;
                        default:
                            i3++;
                            break;
                    }
                }
            }
            int indexOf = OPCODE_DETAILS.indexOf(59, i3);
            OPCODE_INFO[digit] = i;
            OPCODE_NAMES[digit] = OPCODE_DETAILS.substring(i3, indexOf);
            i2 = indexOf + 1;
        }
    }

    private ByteOps() {
    }

    public static String opName(int i) {
        String str = OPCODE_NAMES[i];
        if (str != null) {
            return str;
        }
        String str2 = "unused_" + Hex.u1(i);
        OPCODE_NAMES[i] = str2;
        return str2;
    }

    public static int opInfo(int i) {
        return OPCODE_INFO[i];
    }
}
