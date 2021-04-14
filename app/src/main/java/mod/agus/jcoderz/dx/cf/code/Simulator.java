package mod.agus.jcoderz.dx.cf.code;

import java.util.ArrayList;
import mod.agus.jcoderz.dx.cf.code.BytecodeArray;
import mod.agus.jcoderz.dx.cf.code.LocalVariableList;
import mod.agus.jcoderz.dx.rop.code.LocalItem;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstFieldRef;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.rop.cst.CstInterfaceMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Prototype;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.Hex;

public class Simulator {
    private static final String LOCAL_MISMATCH_ERROR = "This is symptomatic of .class transformation tools that ignore local variable information.";
    private final BytecodeArray code;
    private final LocalVariableList localVariables;
    private final Machine machine;
    private final SimVisitor visitor;

    public Simulator(Machine machine2, ConcreteMethod concreteMethod) {
        if (machine2 == null) {
            throw new NullPointerException("machine == null");
        } else if (concreteMethod == null) {
            throw new NullPointerException("method == null");
        } else {
            this.machine = machine2;
            this.code = concreteMethod.getCode();
            this.localVariables = concreteMethod.getLocalVariables();
            this.visitor = new SimVisitor();
        }
    }

    public void simulate(ByteBlock byteBlock, Frame frame) {
        int end = byteBlock.getEnd();
        this.visitor.setFrame(frame);
        try {
            int start = byteBlock.getStart();
            while (start < end) {
                int parseInstruction = this.code.parseInstruction(start, this.visitor);
                this.visitor.setPreviousOffset(start);
                start += parseInstruction;
            }
        } catch (SimException e) {
            frame.annotate(e);
            throw e;
        }
    }

    public int simulate(int i, Frame frame) {
        this.visitor.setFrame(frame);
        return this.code.parseInstruction(i, this.visitor);
    }

    public static SimException illegalTos() {
        return new SimException("stack mismatch: illegal top-of-stack for opcode");
    }

    public static Type requiredArrayTypeFor(Type type, Type type2) {
        if (type2 == Type.KNOWN_NULL) {
            return type.getArrayType();
        }
        if (type == Type.OBJECT && type2.isArray() && type2.getComponentType().isReference()) {
            return type2;
        }
        if (type == Type.BYTE && type2 == Type.BOOLEAN_ARRAY) {
            return Type.BOOLEAN_ARRAY;
        }
        return type.getArrayType();
    }

    public class SimVisitor implements BytecodeArray.Visitor {
        private Frame frame = null;
        private final Machine machine;
        private int previousOffset;

        public SimVisitor() {
            this.machine = Simulator.this.machine;
        }

        public void setFrame(Frame frame2) {
            if (frame2 == null) {
                throw new NullPointerException("frame == null");
            }
            this.frame = frame2;
        }

        @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
        public void visitInvalid(int i, int i2, int i3) {
            throw new SimException("invalid opcode " + Hex.u1(i));
        }

        @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
        public void visitNoArgs(int i, int i2, int i3, Type type) {
            int i4;
            Type type2;
            int i5 = 2;
            switch (i) {
                case 0:
                    this.machine.clearArgs();
                    break;
                case 46:
                    Type requiredArrayTypeFor = Simulator.requiredArrayTypeFor(type, this.frame.getStack().peekType(1));
                    type = requiredArrayTypeFor.getComponentType();
                    this.machine.popArgs(this.frame, requiredArrayTypeFor, Type.INT);
                    break;
                case 79:
                    ExecutionStack stack = this.frame.getStack();
                    if (!type.isCategory1()) {
                        i5 = 3;
                    }
                    Type peekType = stack.peekType(i5);
                    boolean peekLocal = stack.peekLocal(i5);
                    Type requiredArrayTypeFor2 = Simulator.requiredArrayTypeFor(type, peekType);
                    if (peekLocal) {
                        type = requiredArrayTypeFor2.getComponentType();
                    }
                    this.machine.popArgs(this.frame, requiredArrayTypeFor2, Type.INT, type);
                    break;
                case 87:
                    if (!this.frame.getStack().peekType(0).isCategory2()) {
                        this.machine.popArgs(this.frame, 1);
                        break;
                    } else {
                        throw Simulator.illegalTos();
                    }
                case 88:
                case 92:
                    ExecutionStack stack2 = this.frame.getStack();
                    if (stack2.peekType(0).isCategory2()) {
                        this.machine.popArgs(this.frame, 1);
                        i4 = 17;
                    } else if (stack2.peekType(1).isCategory1()) {
                        this.machine.popArgs(this.frame, 2);
                        i4 = 8481;
                    } else {
                        throw Simulator.illegalTos();
                    }
                    if (i == 92) {
                        this.machine.auxIntArg(i4);
                        break;
                    }
                    break;
                case 89:
                    if (!this.frame.getStack().peekType(0).isCategory2()) {
                        this.machine.popArgs(this.frame, 1);
                        this.machine.auxIntArg(17);
                        break;
                    } else {
                        throw Simulator.illegalTos();
                    }
                case 90:
                    ExecutionStack stack3 = this.frame.getStack();
                    if (stack3.peekType(0).isCategory1() && stack3.peekType(1).isCategory1()) {
                        this.machine.popArgs(this.frame, 2);
                        this.machine.auxIntArg(530);
                        break;
                    } else {
                        throw Simulator.illegalTos();
                    }
                case 91:
                    ExecutionStack stack4 = this.frame.getStack();
                    if (stack4.peekType(0).isCategory2()) {
                        throw Simulator.illegalTos();
                    } else if (stack4.peekType(1).isCategory2()) {
                        this.machine.popArgs(this.frame, 2);
                        this.machine.auxIntArg(530);
                        break;
                    } else if (stack4.peekType(2).isCategory1()) {
                        this.machine.popArgs(this.frame, 3);
                        this.machine.auxIntArg(12819);
                        break;
                    } else {
                        throw Simulator.illegalTos();
                    }
                case 93:
                    ExecutionStack stack5 = this.frame.getStack();
                    if (!stack5.peekType(0).isCategory2()) {
                        if (!stack5.peekType(1).isCategory2() && !stack5.peekType(2).isCategory2()) {
                            this.machine.popArgs(this.frame, 3);
                            this.machine.auxIntArg(205106);
                            break;
                        } else {
                            throw Simulator.illegalTos();
                        }
                    } else if (!stack5.peekType(2).isCategory2()) {
                        this.machine.popArgs(this.frame, 2);
                        this.machine.auxIntArg(530);
                        break;
                    } else {
                        throw Simulator.illegalTos();
                    }
                case 94:
                    ExecutionStack stack6 = this.frame.getStack();
                    if (stack6.peekType(0).isCategory2()) {
                        if (stack6.peekType(2).isCategory2()) {
                            this.machine.popArgs(this.frame, 2);
                            this.machine.auxIntArg(530);
                            break;
                        } else if (stack6.peekType(3).isCategory1()) {
                            this.machine.popArgs(this.frame, 3);
                            this.machine.auxIntArg(12819);
                            break;
                        } else {
                            throw Simulator.illegalTos();
                        }
                    } else if (!stack6.peekType(1).isCategory1()) {
                        throw Simulator.illegalTos();
                    } else if (stack6.peekType(2).isCategory2()) {
                        this.machine.popArgs(this.frame, 3);
                        this.machine.auxIntArg(205106);
                        break;
                    } else if (stack6.peekType(3).isCategory1()) {
                        this.machine.popArgs(this.frame, 4);
                        this.machine.auxIntArg(4399427);
                        break;
                    } else {
                        throw Simulator.illegalTos();
                    }
                case 95:
                    ExecutionStack stack7 = this.frame.getStack();
                    if (stack7.peekType(0).isCategory1() && stack7.peekType(1).isCategory1()) {
                        this.machine.popArgs(this.frame, 2);
                        this.machine.auxIntArg(18);
                        break;
                    } else {
                        throw Simulator.illegalTos();
                    }
                case 96:
                case 100:
                case 104:
                case 108:
                case 112:
                case 126:
                case 128:
                case 130:
                    this.machine.popArgs(this.frame, type, type);
                    break;
                case 116:
                    this.machine.popArgs(this.frame, type);
                    break;
                case 120:
                case 122:
                case 124:
                    this.machine.popArgs(this.frame, type, Type.INT);
                    break;
                case 133:
                case 134:
                case 135:
                case 145:
                case 146:
                case 147:
                    this.machine.popArgs(this.frame, Type.INT);
                    break;
                case 136:
                case 137:
                case 138:
                    this.machine.popArgs(this.frame, Type.LONG);
                    break;
                case 139:
                case 140:
                case 141:
                    this.machine.popArgs(this.frame, Type.FLOAT);
                    break;
                case 142:
                case 143:
                case 144:
                    this.machine.popArgs(this.frame, Type.DOUBLE);
                    break;
                case 148:
                    this.machine.popArgs(this.frame, Type.LONG, Type.LONG);
                    break;
                case 149:
                case 150:
                    this.machine.popArgs(this.frame, Type.FLOAT, Type.FLOAT);
                    break;
                case 151:
                case 152:
                    this.machine.popArgs(this.frame, Type.DOUBLE, Type.DOUBLE);
                    break;
                case 172:
                    if (type == Type.OBJECT) {
                        type2 = this.frame.getStack().peekType(0);
                    } else {
                        type2 = type;
                    }
                    this.machine.popArgs(this.frame, type);
                    checkReturnType(type2);
                    break;
                case 177:
                    this.machine.clearArgs();
                    checkReturnType(Type.VOID);
                    break;
                case 190:
                    Type peekType2 = this.frame.getStack().peekType(0);
                    if (peekType2.isArrayOrKnownNull()) {
                        this.machine.popArgs(this.frame, Type.OBJECT);
                        break;
                    } else {
                        throw new SimException("type mismatch: expected array type but encountered " + peekType2.toHuman());
                    }
                case 191:
                case 194:
                case 195:
                    this.machine.popArgs(this.frame, Type.OBJECT);
                    break;
                default:
                    visitInvalid(i, i2, i3);
                    return;
            }
            this.machine.auxType(type);
            this.machine.run(this.frame, i2, i);
        }

        private void checkReturnType(Type type) {
            Type returnType = this.machine.getPrototype().getReturnType();
            if (!Merger.isPossiblyAssignableFrom(returnType, type)) {
                throw new SimException("return type mismatch: prototype indicates " + returnType.toHuman() + ", but encountered type " + type.toHuman());
            }
        }

        @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
        public void visitLocal(int i, int i2, int i3, int i4, Type type, int i5) {
            Type type2;
            LocalItem localItem = null;
            LocalVariableList.Item pcAndIndexToLocal = Simulator.this.localVariables.pcAndIndexToLocal(i == 54 ? i2 + i3 : i2, i4);
            if (pcAndIndexToLocal != null) {
                Type type3 = pcAndIndexToLocal.getType();
                if (type3.getBasicFrameType() != type.getBasicFrameType()) {
                    BaseMachine.throwLocalMismatch(type, type3);
                    return;
                }
                type2 = type3;
            } else {
                type2 = type;
            }
            switch (i) {
                case 21:
                case 169:
                    this.machine.localArg(this.frame, i4);
                    this.machine.localInfo(pcAndIndexToLocal != null);
                    this.machine.auxType(type);
                    break;
                case 54:
                    LocalItem localItem2 = pcAndIndexToLocal == null ? null : pcAndIndexToLocal.getLocalItem();
                    this.machine.popArgs(this.frame, type);
                    this.machine.auxType(type);
                    this.machine.localTarget(i4, type2, localItem2);
                    break;
                case 132:
                    if (pcAndIndexToLocal != null) {
                        localItem = pcAndIndexToLocal.getLocalItem();
                    }
                    this.machine.localArg(this.frame, i4);
                    this.machine.localTarget(i4, type2, localItem);
                    this.machine.auxType(type);
                    this.machine.auxIntArg(i5);
                    this.machine.auxCstArg(CstInteger.make(i5));
                    break;
                default:
                    visitInvalid(i, i2, i3);
                    return;
            }
            this.machine.run(this.frame, i2, i);
        }

        @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
        public void visitConstant(int i, int i2, int i3, Constant constant, int i4) {
            CstMethodRef cstMethodRef;
            switch (i) {
                case 179:
                    this.machine.popArgs(this.frame, ((CstFieldRef) constant).getType());
                    break;
                case 180:
                case 192:
                case 193:
                    this.machine.popArgs(this.frame, Type.OBJECT);
                    break;
                case 181:
                    this.machine.popArgs(this.frame, Type.OBJECT, ((CstFieldRef) constant).getType());
                    break;
                case 182:
                case 183:
                    cstMethodRef = (CstMethodRef) constant;
                    this.machine.popArgs(this.frame, ((CstMethodRef) cstMethodRef).getPrototype(false));
                    constant = cstMethodRef;
                    break;
                case 184:
                    this.machine.popArgs(this.frame, ((CstMethodRef) constant).getPrototype(true));
                    break;
                case 185:
                    cstMethodRef = ((CstInterfaceMethodRef) constant).toMethodRef();
                    this.machine.popArgs(this.frame, ((CstMethodRef) cstMethodRef).getPrototype(false));
                    constant = cstMethodRef;
                    break;
                case 186:
                case 187:
                case 188:
                case 190:
                case 191:
                case 194:
                case 195:
                case 196:
                default:
                    this.machine.clearArgs();
                    break;
                case 189:
                    this.machine.popArgs(this.frame, Type.INT);
                    break;
                case 197:
                    this.machine.popArgs(this.frame, Prototype.internInts(Type.VOID, i4));
                    break;
            }
            this.machine.auxIntArg(i4);
            this.machine.auxCstArg(constant);
            this.machine.run(this.frame, i2, i);
        }

        @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
        public void visitBranch(int i, int i2, int i3, int i4) {
            switch (i) {
                case 153:
                case 154:
                case 155:
                case 156:
                case 157:
                case 158:
                    this.machine.popArgs(this.frame, Type.INT);
                    break;
                case 159:
                case 160:
                case 161:
                case 162:
                case 163:
                case 164:
                    this.machine.popArgs(this.frame, Type.INT, Type.INT);
                    break;
                case 165:
                case 166:
                    this.machine.popArgs(this.frame, Type.OBJECT, Type.OBJECT);
                    break;
                case 167:
                case 168:
                case 200:
                case 201:
                    this.machine.clearArgs();
                    break;
                case 169:
                case 170:
                case 171:
                case 172:
                case 173:
                case 174:
                case 175:
                case 176:
                case 177:
                case 178:
                case 179:
                case 180:
                case 181:
                case 182:
                case 183:
                case 184:
                case 185:
                case 186:
                case 187:
                case 188:
                case 189:
                case 190:
                case 191:
                case 192:
                case 193:
                case 194:
                case 195:
                case 196:
                case 197:
                default:
                    visitInvalid(i, i2, i3);
                    return;
                case 198:
                case 199:
                    this.machine.popArgs(this.frame, Type.OBJECT);
                    break;
            }
            this.machine.auxTargetArg(i4);
            this.machine.run(this.frame, i2, i);
        }

        @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
        public void visitSwitch(int i, int i2, int i3, SwitchList switchList, int i4) {
            this.machine.popArgs(this.frame, Type.INT);
            this.machine.auxIntArg(i4);
            this.machine.auxSwitchArg(switchList);
            this.machine.run(this.frame, i2, i);
        }

        @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
        public void visitNewarray(int i, int i2, CstType cstType, ArrayList<Constant> arrayList) {
            this.machine.popArgs(this.frame, Type.INT);
            this.machine.auxInitValues(arrayList);
            this.machine.auxCstArg(cstType);
            this.machine.run(this.frame, i, 188);
        }

        @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
        public void setPreviousOffset(int i) {
            this.previousOffset = i;
        }

        @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
        public int getPreviousOffset() {
            return this.previousOffset;
        }
    }
}
