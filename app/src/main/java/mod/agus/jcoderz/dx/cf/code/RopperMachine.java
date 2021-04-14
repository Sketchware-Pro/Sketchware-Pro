package mod.agus.jcoderz.dx.cf.code;

import java.util.ArrayList;
import mod.agus.jcoderz.dx.cf.iface.Method;
import mod.agus.jcoderz.dx.cf.iface.MethodList;
import mod.agus.jcoderz.dx.rop.code.AccessFlags;
import mod.agus.jcoderz.dx.rop.code.FillArrayDataInsn;
import mod.agus.jcoderz.dx.rop.code.Insn;
import mod.agus.jcoderz.dx.rop.code.PlainCstInsn;
import mod.agus.jcoderz.dx.rop.code.PlainInsn;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.Rop;
import mod.agus.jcoderz.dx.rop.code.Rops;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.rop.code.SwitchInsn;
import mod.agus.jcoderz.dx.rop.code.ThrowingCstInsn;
import mod.agus.jcoderz.dx.rop.code.ThrowingInsn;
import mod.agus.jcoderz.dx.rop.code.TranslationAdvice;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstFieldRef;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstNat;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.util.IntList;

final class RopperMachine extends ValueAwareMachine {
    private static final CstType ARRAY_REFLECT_TYPE = new CstType(Type.internClassName("java/lang/reflect/Array"));
    private static final CstMethodRef MULTIANEWARRAY_METHOD = new CstMethodRef(ARRAY_REFLECT_TYPE, new CstNat(new CstString("newInstance"), new CstString("(Ljava/lang/Class;[I)Ljava/lang/Object;")));
    private final TranslationAdvice advice;
    private boolean blockCanThrow;
    private TypeList catches;
    private boolean catchesUsed;
    private int extraBlockCount;
    private boolean hasJsr;
    private final ArrayList<Insn> insns;
    private final int maxLocals;
    private final ConcreteMethod method;
    private final MethodList methods;
    private int primarySuccessorIndex;
    private ReturnAddress returnAddress;
    private Rop returnOp;
    private SourcePosition returnPosition;
    private boolean returns;
    private final Ropper ropper;

    public RopperMachine(Ropper ropper2, ConcreteMethod concreteMethod, TranslationAdvice translationAdvice, MethodList methodList) {
        super(concreteMethod.getEffectiveDescriptor());
        if (methodList == null) {
            throw new NullPointerException("methods == null");
        } else if (ropper2 == null) {
            throw new NullPointerException("ropper == null");
        } else if (translationAdvice == null) {
            throw new NullPointerException("advice == null");
        } else {
            this.ropper = ropper2;
            this.method = concreteMethod;
            this.methods = methodList;
            this.advice = translationAdvice;
            this.maxLocals = concreteMethod.getMaxLocals();
            this.insns = new ArrayList<>(25);
            this.catches = null;
            this.catchesUsed = false;
            this.returns = false;
            this.primarySuccessorIndex = -1;
            this.extraBlockCount = 0;
            this.blockCanThrow = false;
            this.returnOp = null;
            this.returnPosition = null;
        }
    }

    public ArrayList<Insn> getInsns() {
        return this.insns;
    }

    public Rop getReturnOp() {
        return this.returnOp;
    }

    public SourcePosition getReturnPosition() {
        return this.returnPosition;
    }

    public void startBlock(TypeList typeList) {
        this.catches = typeList;
        this.insns.clear();
        this.catchesUsed = false;
        this.returns = false;
        this.primarySuccessorIndex = 0;
        this.extraBlockCount = 0;
        this.blockCanThrow = false;
        this.hasJsr = false;
        this.returnAddress = null;
    }

    public boolean wereCatchesUsed() {
        return this.catchesUsed;
    }

    public boolean returns() {
        return this.returns;
    }

    public int getPrimarySuccessorIndex() {
        return this.primarySuccessorIndex;
    }

    public int getExtraBlockCount() {
        return this.extraBlockCount;
    }

    public boolean canThrow() {
        return this.blockCanThrow;
    }

    public boolean hasJsr() {
        return this.hasJsr;
    }

    public boolean hasRet() {
        return this.returnAddress != null;
    }

    public ReturnAddress getReturnAddress() {
        return this.returnAddress;
    }

    @Override // mod.agus.jcoderz.dx.cf.code.ValueAwareMachine, mod.agus.jcoderz.dx.cf.code.Machine
    public void run(Frame frame, int i, int i2) {
        RegisterSpecList registerSpecList;
        PlainInsn plainInsn;
        RegisterSpec registerSpec;
        Rop rop;
        CstType cstType;
        Constant constant;
        int i3;
        Insn plainInsn2;
        ThrowingCstInsn throwingCstInsn;
        int size = frame.getStack().size() + this.maxLocals;
        RegisterSpecList sources = getSources(i2, size);
        int size2 = sources.size();
        super.run(frame, i, i2);
        SourcePosition makeSourcePosistion = this.method.makeSourcePosistion(i);
        RegisterSpec localTarget = getLocalTarget(i2 == 54);
        int resultCount = resultCount();
        if (resultCount == 0) {
            localTarget = null;
            switch (i2) {
                case 87:
                case 88:
                    return;
            }
        } else if (localTarget == null) {
            if (resultCount == 1) {
                localTarget = RegisterSpec.make(size, result(0));
            } else {
                int firstTempStackReg = this.ropper.getFirstTempStackReg();
                RegisterSpec[] registerSpecArr = new RegisterSpec[size2];
                for (int i4 = 0; i4 < size2; i4++) {
                    RegisterSpec registerSpec2 = sources.get(i4);
                    TypeBearer typeBearer = registerSpec2.getTypeBearer();
                    RegisterSpec withReg = registerSpec2.withReg(firstTempStackReg);
                    this.insns.add(new PlainInsn(Rops.opMove(typeBearer), makeSourcePosistion, withReg, registerSpec2));
                    registerSpecArr[i4] = withReg;
                    firstTempStackReg += registerSpec2.getCategory();
                }
                for (int auxInt = getAuxInt(); auxInt != 0; auxInt >>= 4) {
                    RegisterSpec registerSpec3 = registerSpecArr[(auxInt & 15) - 1];
                    TypeBearer typeBearer2 = registerSpec3.getTypeBearer();
                    this.insns.add(new PlainInsn(Rops.opMove(typeBearer2), makeSourcePosistion, registerSpec3.withReg(size), registerSpec3));
                    size += typeBearer2.getType().getCategory();
                }
                return;
            }
        }
        TypeBearer typeBearer3 = localTarget != null ? localTarget : Type.VOID;
        Constant auxCst = getAuxCst();
        if (i2 == 197) {
            this.blockCanThrow = true;
            this.extraBlockCount = 6;
            RegisterSpec make = RegisterSpec.make(localTarget.getNextReg(), Type.INT_ARRAY);
            this.insns.add(new ThrowingCstInsn(Rops.opFilledNewArray(Type.INT_ARRAY, size2), makeSourcePosistion, sources, this.catches, CstType.INT_ARRAY));
            this.insns.add(new PlainInsn(Rops.opMoveResult(Type.INT_ARRAY), makeSourcePosistion, make, RegisterSpecList.EMPTY));
            int i5 = 0;
            Type classType = ((CstType) auxCst).getClassType();
            while (i5 < size2) {
                i5++;
                classType = classType.getComponentType();
            }
            RegisterSpec make2 = RegisterSpec.make(localTarget.getReg(), Type.CLASS);
            if (classType.isPrimitive()) {
                throwingCstInsn = new ThrowingCstInsn(Rops.GET_STATIC_OBJECT, makeSourcePosistion, RegisterSpecList.EMPTY, this.catches, CstFieldRef.forPrimitiveType(classType));
            } else {
                throwingCstInsn = new ThrowingCstInsn(Rops.CONST_OBJECT, makeSourcePosistion, RegisterSpecList.EMPTY, this.catches, new CstType(classType));
            }
            this.insns.add(throwingCstInsn);
            this.insns.add(new PlainInsn(Rops.opMoveResultPseudo(make2.getType()), makeSourcePosistion, make2, RegisterSpecList.EMPTY));
            RegisterSpec make3 = RegisterSpec.make(localTarget.getReg(), Type.OBJECT);
            this.insns.add(new ThrowingCstInsn(Rops.opInvokeStatic(MULTIANEWARRAY_METHOD.getPrototype()), makeSourcePosistion, RegisterSpecList.make(make2, make), this.catches, MULTIANEWARRAY_METHOD));
            this.insns.add(new PlainInsn(Rops.opMoveResult(MULTIANEWARRAY_METHOD.getPrototype().getReturnType()), makeSourcePosistion, make3, RegisterSpecList.EMPTY));
            i2 = 192;
            registerSpecList = RegisterSpecList.make(make3);
        } else if (i2 == 168) {
            this.hasJsr = true;
            return;
        } else if (i2 == 169) {
            try {
                this.returnAddress = (ReturnAddress) arg(0);
                return;
            } catch (ClassCastException e) {
                throw new RuntimeException("Argument to RET was not a ReturnAddress", e);
            }
        } else {
            registerSpecList = sources;
        }
        int jopToRopOpcode = jopToRopOpcode(i2, auxCst);
        Rop ropFor = Rops.ropFor(jopToRopOpcode, typeBearer3, registerSpecList, auxCst);
        if (localTarget != null && ropFor.isCallLike()) {
            this.extraBlockCount++;
            plainInsn = new PlainInsn(Rops.opMoveResult(((CstMethodRef) auxCst).getPrototype().getReturnType()), makeSourcePosistion, localTarget, RegisterSpecList.EMPTY);
            registerSpec = null;
        } else if (localTarget == null || !ropFor.canThrow()) {
            plainInsn = null;
            registerSpec = localTarget;
        } else {
            this.extraBlockCount++;
            plainInsn = new PlainInsn(Rops.opMoveResultPseudo(localTarget.getTypeBearer()), makeSourcePosistion, localTarget, RegisterSpecList.EMPTY);
            registerSpec = null;
        }
        if (jopToRopOpcode == 41) {
            rop = ropFor;
            cstType = CstType.intern(ropFor.getResult());
        } else {
            if (auxCst == null && size2 == 2) {
                TypeBearer typeBearer4 = registerSpecList.get(0).getTypeBearer();
                TypeBearer typeBearer5 = registerSpecList.get(1).getTypeBearer();
                if ((typeBearer5.isConstant() || typeBearer4.isConstant()) && this.advice.hasConstantOperation(ropFor, registerSpecList.get(0), registerSpecList.get(1))) {
                    if (typeBearer5.isConstant()) {
                        Constant constant2 = (Constant) typeBearer5;
                        registerSpecList = registerSpecList.withoutLast();
                        if (ropFor.getOpcode() == 15) {
                            i3 = 14;
                            constant = CstInteger.make(-((CstInteger) typeBearer5).getValue());
                        } else {
                            constant = constant2;
                            i3 = jopToRopOpcode;
                        }
                    } else {
                        registerSpecList = registerSpecList.withoutFirst();
                        constant = (Constant) typeBearer4;
                        i3 = jopToRopOpcode;
                    }
                    cstType = (CstType) constant;
                    rop = Rops.ropFor(i3, typeBearer3, registerSpecList, constant);
                    jopToRopOpcode = i3;
                }
            }
            rop = ropFor;
            cstType = (CstType) auxCst;
        }
        SwitchList auxCases = getAuxCases();
        ArrayList<Constant> initValues = getInitValues();
        boolean canThrow = rop.canThrow();
        this.blockCanThrow |= canThrow;
        if (auxCases != null) {
            if (auxCases.size() == 0) {
                plainInsn2 = new PlainInsn(Rops.GOTO, makeSourcePosistion, (RegisterSpec) null, RegisterSpecList.EMPTY);
                this.primarySuccessorIndex = 0;
            } else {
                IntList values = auxCases.getValues();
                plainInsn2 = new SwitchInsn(rop, makeSourcePosistion, registerSpec, registerSpecList, values);
                this.primarySuccessorIndex = values.size();
            }
        } else if (jopToRopOpcode == 33) {
            if (registerSpecList.size() != 0) {
                RegisterSpec registerSpec4 = registerSpecList.get(0);
                TypeBearer typeBearer6 = registerSpec4.getTypeBearer();
                if (registerSpec4.getReg() != 0) {
                    this.insns.add(new PlainInsn(Rops.opMove(typeBearer6), makeSourcePosistion, RegisterSpec.make(0, typeBearer6), registerSpec4));
                }
            }
            plainInsn2 = new PlainInsn(Rops.GOTO, makeSourcePosistion, (RegisterSpec) null, RegisterSpecList.EMPTY);
            this.primarySuccessorIndex = 0;
            updateReturnOp(rop, makeSourcePosistion);
            this.returns = true;
        } else if (cstType != null) {
            if (canThrow) {
                ThrowingCstInsn throwingCstInsn2 = new ThrowingCstInsn(rop, makeSourcePosistion, registerSpecList, this.catches, cstType);
                this.catchesUsed = true;
                this.primarySuccessorIndex = this.catches.size();
                plainInsn2 = throwingCstInsn2;
            } else {
                plainInsn2 = new PlainCstInsn(rop, makeSourcePosistion, registerSpec, registerSpecList, cstType);
            }
        } else if (canThrow) {
            plainInsn2 = new ThrowingInsn(rop, makeSourcePosistion, registerSpecList, this.catches);
            this.catchesUsed = true;
            if (i2 == 191) {
                this.primarySuccessorIndex = -1;
            } else {
                this.primarySuccessorIndex = this.catches.size();
            }
        } else {
            plainInsn2 = new PlainInsn(rop, makeSourcePosistion, registerSpec, registerSpecList);
        }
        this.insns.add(plainInsn2);
        if (plainInsn != null) {
            this.insns.add(plainInsn);
        }
        if (initValues != null) {
            this.extraBlockCount++;
            this.insns.add(new FillArrayDataInsn(Rops.FILL_ARRAY_DATA, makeSourcePosistion, RegisterSpecList.make(plainInsn.getResult()), initValues, cstType));
        }
    }

    private RegisterSpecList getSources(int i, int i2) {
        RegisterSpecList registerSpecList;
        int argCount = argCount();
        if (argCount == 0) {
            return RegisterSpecList.EMPTY;
        }
        int localIndex = getLocalIndex();
        if (localIndex < 0) {
            registerSpecList = new RegisterSpecList(argCount);
            for (int i3 = 0; i3 < argCount; i3++) {
                RegisterSpec make = RegisterSpec.make(i2, arg(i3));
                registerSpecList.set(i3, make);
                i2 += make.getCategory();
            }
            switch (i) {
                case 79:
                    if (argCount == 3) {
                        RegisterSpec registerSpec = registerSpecList.get(0);
                        RegisterSpec registerSpec2 = registerSpecList.get(1);
                        registerSpecList.set(0, registerSpecList.get(2));
                        registerSpecList.set(1, registerSpec);
                        registerSpecList.set(2, registerSpec2);
                        break;
                    } else {
                        throw new RuntimeException("shouldn't happen");
                    }
                case 181:
                    if (argCount == 2) {
                        RegisterSpec registerSpec3 = registerSpecList.get(0);
                        registerSpecList.set(0, registerSpecList.get(1));
                        registerSpecList.set(1, registerSpec3);
                        break;
                    } else {
                        throw new RuntimeException("shouldn't happen");
                    }
            }
        } else {
            registerSpecList = new RegisterSpecList(1);
            registerSpecList.set(0, RegisterSpec.make(localIndex, arg(0)));
        }
        registerSpecList.setImmutable();
        return registerSpecList;
    }

    private void updateReturnOp(Rop rop, SourcePosition sourcePosition) {
        if (rop == null) {
            throw new NullPointerException("op == null");
        } else if (sourcePosition == null) {
            throw new NullPointerException("pos == null");
        } else if (this.returnOp == null) {
            this.returnOp = rop;
            this.returnPosition = sourcePosition;
        } else if (this.returnOp != rop) {
            throw new SimException("return op mismatch: " + rop + ", " + this.returnOp);
        } else if (sourcePosition.getLine() > this.returnPosition.getLine()) {
            this.returnPosition = sourcePosition;
        }
    }

    private int jopToRopOpcode(int i, Constant constant) {
        switch (i) {
            case 0:
                return 1;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 19:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 97:
            case 98:
            case 99:
            case 101:
            case 102:
            case 103:
            case 105:
            case 106:
            case 107:
            case 109:
            case 110:
            case 111:
            case 113:
            case 114:
            case 115:
            case 117:
            case 118:
            case 119:
            case 121:
            case 123:
            case 125:
            case 127:
            case 129:
            case 131:
            case 168:
            case 169:
            case 170:
            case 173:
            case 174:
            case 175:
            case 176:
            case 186:
            case 196:
            case 197:
            default:
                throw new RuntimeException("shouldn't happen");
            case 18:
            case 20:
                return 5;
            case 21:
            case 54:
                return 2;
            case 46:
                return 38;
            case 79:
                return 39;
            case 96:
            case 132:
                return 14;
            case 100:
                return 15;
            case 104:
                return 16;
            case 108:
                return 17;
            case 112:
                return 18;
            case 116:
                return 19;
            case 120:
                return 23;
            case 122:
                return 24;
            case 124:
                return 25;
            case 126:
                return 20;
            case 128:
                return 21;
            case 130:
                return 22;
            case 133:
            case 134:
            case 135:
            case 136:
            case 137:
            case 138:
            case 139:
            case 140:
            case 141:
            case 142:
            case 143:
            case 144:
                return 29;
            case 145:
                return 30;
            case 146:
                return 31;
            case 147:
                return 32;
            case 148:
            case 149:
            case 151:
                return 27;
            case 150:
            case 152:
                return 28;
            case 153:
            case 159:
            case 165:
            case 198:
                return 7;
            case 154:
            case 160:
            case 166:
            case 199:
                return 8;
            case 155:
            case 161:
                return 9;
            case 156:
            case 162:
                return 10;
            case 157:
            case 163:
                return 12;
            case 158:
            case 164:
                return 11;
            case 167:
                return 6;
            case 171:
                return 13;
            case 172:
            case 177:
                return 33;
            case 178:
                return 46;
            case 179:
                return 48;
            case 180:
                return 45;
            case 181:
                return 47;
            case 182:
                CstMethodRef cstMethodRef = (CstMethodRef) constant;
                if (cstMethodRef.getDefiningClass().equals(this.method.getDefiningClass())) {
                    for (int i2 = 0; i2 < this.methods.size(); i2++) {
                        Method method2 = this.methods.get(i2);
                        if (AccessFlags.isPrivate(method2.getAccessFlags()) && cstMethodRef.getNat().equals(method2.getNat())) {
                            return 52;
                        }
                    }
                }
                return 50;
            case 183:
                CstMethodRef cstMethodRef2 = (CstMethodRef) constant;
                if (cstMethodRef2.isInstanceInit() || cstMethodRef2.getDefiningClass().equals(this.method.getDefiningClass()) || !this.method.getAccSuper()) {
                    return 52;
                }
                return 51;
            case 184:
                return 49;
            case 185:
                return 53;
            case 187:
                return 40;
            case 188:
            case 189:
                return 41;
            case 190:
                return 34;
            case 191:
                return 35;
            case 192:
                return 43;
            case 193:
                return 44;
            case 194:
                return 36;
            case 195:
                return 37;
        }
    }
}
