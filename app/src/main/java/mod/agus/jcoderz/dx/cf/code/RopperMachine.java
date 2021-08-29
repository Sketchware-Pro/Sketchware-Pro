/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dx.cf.iface.Method;
import mod.agus.jcoderz.dx.cf.iface.MethodList;

import java.util.ArrayList;

import mod.agus.jcoderz.dx.rop.code.AccessFlags;
import mod.agus.jcoderz.dx.rop.code.FillArrayDataInsn;
import mod.agus.jcoderz.dx.rop.code.Insn;
import mod.agus.jcoderz.dx.rop.code.InvokePolymorphicInsn;
import mod.agus.jcoderz.dx.rop.code.PlainCstInsn;
import mod.agus.jcoderz.dx.rop.code.PlainInsn;
import mod.agus.jcoderz.dx.rop.code.RegOps;
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
import mod.agus.jcoderz.dx.rop.cst.CstCallSiteRef;
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

/**
 * Machine implementation for use by {@link mod.agus.jcoderz.dx.cf.code.Ropper}.
 */
/*package*/ final class RopperMachine extends ValueAwareMachine {
    /** {@code non-null;} array reflection class */
    private static final mod.agus.jcoderz.dx.rop.cst.CstType ARRAY_REFLECT_TYPE =
        new mod.agus.jcoderz.dx.rop.cst.CstType(mod.agus.jcoderz.dx.rop.type.Type.internClassName("java/lang/reflect/Array"));

    /**
     * {@code non-null;} method constant for use in converting
     * {@code multianewarray} instructions
     */
    private static final mod.agus.jcoderz.dx.rop.cst.CstMethodRef MULTIANEWARRAY_METHOD =
        new mod.agus.jcoderz.dx.rop.cst.CstMethodRef(ARRAY_REFLECT_TYPE,
                         new CstNat(new mod.agus.jcoderz.dx.rop.cst.CstString("newInstance"),
                                    new CstString("(Ljava/lang/Class;[I)" +
                                                "Ljava/lang/Object;")));

    /** {@code non-null;} {@link mod.agus.jcoderz.dx.cf.code.Ropper} controlling this instance */
    private final mod.agus.jcoderz.dx.cf.code.Ropper ropper;

    /** {@code non-null;} method being converted */
    private final ConcreteMethod method;

    /** {@code non-null:} list of methods from the class whose method is being converted */
    private final MethodList methods;

    /** {@code non-null;} translation advice */
    private final mod.agus.jcoderz.dx.rop.code.TranslationAdvice advice;

    /** max locals of the method */
    private final int maxLocals;

    /** {@code non-null;} instructions for the rop basic block in-progress */
    private final ArrayList<mod.agus.jcoderz.dx.rop.code.Insn> insns;

    /** {@code non-null;} catches for the block currently being processed */
    private mod.agus.jcoderz.dx.rop.type.TypeList catches;

    /** whether the catches have been used in an instruction */
    private boolean catchesUsed;

    /** whether the block contains a {@code return} */
    private boolean returns;

    /** primary successor index */
    private int primarySuccessorIndex;

    /** {@code >= 0;} number of extra basic blocks required */
    private int extraBlockCount;

    /** true if last processed block ends with a jsr or jsr_W*/
    private boolean hasJsr;

    /** true if an exception can be thrown by the last block processed */
    private boolean blockCanThrow;

    /**
     * If non-null, the ReturnAddress that was used by the terminating ret
     * instruction. If null, there was no ret instruction encountered.
     */

    private ReturnAddress returnAddress;

    /**
     * {@code null-ok;} the appropriate {@code return} op or {@code null}
     * if it is not yet known
     */
    private mod.agus.jcoderz.dx.rop.code.Rop returnOp;

    /**
     * {@code null-ok;} the source position for the return block or {@code null}
     * if it is not yet known
     */
    private mod.agus.jcoderz.dx.rop.code.SourcePosition returnPosition;

    /**
     * Constructs an instance.
     *
     * @param ropper {@code non-null;} ropper controlling this instance
     * @param method {@code non-null;} method being converted
     * @param advice {@code non-null;} translation advice to use
     * @param methods {@code non-null;} list of methods defined by the class
     *     that defines {@code method}.
     */
    public RopperMachine(Ropper ropper, ConcreteMethod method,
                         TranslationAdvice advice, MethodList methods) {
        super(method.getEffectiveDescriptor());

        if (methods == null) {
            throw new NullPointerException("methods == null");
        }

        if (ropper == null) {
            throw new NullPointerException("ropper == null");
        }

        if (advice == null) {
            throw new NullPointerException("advice == null");
        }

        this.ropper = ropper;
        this.method = method;
        this.methods = methods;
        this.advice = advice;
        this.maxLocals = method.getMaxLocals();
        this.insns = new ArrayList<mod.agus.jcoderz.dx.rop.code.Insn>(25);
        this.catches = null;
        this.catchesUsed = false;
        this.returns = false;
        this.primarySuccessorIndex = -1;
        this.extraBlockCount = 0;
        this.blockCanThrow = false;
        this.returnOp = null;
        this.returnPosition = null;
    }

    /**
     * Gets the instructions array. It is shared and gets modified by
     * subsequent calls to this instance.
     *
     * @return {@code non-null;} the instructions array
     */
    public ArrayList<mod.agus.jcoderz.dx.rop.code.Insn> getInsns() {
        return insns;
    }

    /**
     * Gets the return opcode encountered, if any.
     *
     * @return {@code null-ok;} the return opcode
     */
    public mod.agus.jcoderz.dx.rop.code.Rop getReturnOp() {
        return returnOp;
    }

    /**
     * Gets the return position, if known.
     *
     * @return {@code null-ok;} the return position
     */
    public mod.agus.jcoderz.dx.rop.code.SourcePosition getReturnPosition() {
        return returnPosition;
    }

    /**
     * Gets ready to start working on a new block. This will clear the
     * {@link #insns} list, set {@link #catches}, reset whether it has
     * been used, reset whether the block contains a
     * {@code return}, and reset {@link #primarySuccessorIndex}.
     */
    public void startBlock(mod.agus.jcoderz.dx.rop.type.TypeList catches) {
        this.catches = catches;

        insns.clear();
        catchesUsed = false;
        returns = false;
        primarySuccessorIndex = 0;
        extraBlockCount = 0;
        blockCanThrow = false;
        hasJsr = false;
        returnAddress = null;
    }

    /**
     * Gets whether {@link #catches} was used. This indicates that the
     * last instruction in the block is one of the ones that can throw.
     *
     * @return whether {@code catches} has been used
     */
    public boolean wereCatchesUsed() {
        return catchesUsed;
    }

    /**
     * Gets whether the block just processed ended with a
     * {@code return}.
     *
     * @return whether the block returns
     */
    public boolean returns() {
        return returns;
    }

    /**
     * Gets the primary successor index. This is the index into the
     * successors list where the primary may be found or
     * {@code -1} if there are successors but no primary
     * successor. This may return something other than
     * {@code -1} in the case of an instruction with no
     * successors at all (primary or otherwise).
     *
     * @return {@code >= -1;} the primary successor index
     */
    public int getPrimarySuccessorIndex() {
        return primarySuccessorIndex;
    }

    /**
     * Gets how many extra blocks will be needed to represent the
     * block currently being translated. Each extra block should consist
     * of one instruction from the end of the original block.
     *
     * @return {@code >= 0;} the number of extra blocks needed
     */
    public int getExtraBlockCount() {
        return extraBlockCount;
    }

    /**
     * @return true if at least one of the insn processed since the last
     * call to startBlock() can throw.
     */
    public boolean canThrow() {
        return blockCanThrow;
    }

    /**
     * @return true if a JSR has ben encountered since the last call to
     * startBlock()
     */
    public boolean hasJsr() {
        return hasJsr;
    }

    /**
     * @return {@code true} if a {@code ret} has ben encountered since
     * the last call to {@code startBlock()}
     */
    public boolean hasRet() {
        return returnAddress != null;
    }

    /**
     * @return {@code null-ok;} return address of a {@code ret}
     * instruction if encountered since last call to startBlock().
     * {@code null} if no ret instruction encountered.
     */
    public ReturnAddress getReturnAddress() {
        return returnAddress;
    }

    /** {@inheritDoc} */
    @Override
    public void run(Frame frame, int offset, int opcode) {
        /*
         * This is the stack pointer after the opcode's arguments have been
         * popped.
         */
        int stackPointer = maxLocals + frame.getStack().size();

        // The sources have to be retrieved before super.run() gets called.
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList sources = getSources(opcode, stackPointer);
        int sourceCount = sources.size();

        super.run(frame, offset, opcode);

        mod.agus.jcoderz.dx.rop.code.SourcePosition pos = method.makeSourcePosistion(offset);
        mod.agus.jcoderz.dx.rop.code.RegisterSpec localTarget = getLocalTarget(opcode == mod.agus.jcoderz.dx.cf.code.ByteOps.ISTORE);
        int destCount = resultCount();
        mod.agus.jcoderz.dx.rop.code.RegisterSpec dest;

        if (destCount == 0) {
            dest = null;
            switch (opcode) {
                case mod.agus.jcoderz.dx.cf.code.ByteOps.POP:
                case mod.agus.jcoderz.dx.cf.code.ByteOps.POP2: {
                    // These simply don't appear in the rop form.
                    return;
                }
            }
        } else if (localTarget != null) {
            dest = localTarget;
        } else if (destCount == 1) {
            dest = mod.agus.jcoderz.dx.rop.code.RegisterSpec.make(stackPointer, result(0));
        } else {
            /*
             * This clause only ever applies to the stack manipulation
             * ops that have results (that is, dup* and swap but not
             * pop*).
             *
             * What we do is first move all the source registers into
             * the "temporary stack" area defined for the method, and
             * then move stuff back down onto the main "stack" in the
             * arrangement specified by the stack op pattern.
             *
             * Note: This code ends up emitting a lot of what will
             * turn out to be superfluous moves (e.g., moving back and
             * forth to the same local when doing a dup); however,
             * that makes this code a bit easier (and goodness knows
             * it doesn't need any extra complexity), and all the SSA
             * stuff is going to want to deal with this sort of
             * superfluous assignment anyway, so it should be a wash
             * in the end.
             */
            int scratchAt = ropper.getFirstTempStackReg();
            mod.agus.jcoderz.dx.rop.code.RegisterSpec[] scratchRegs = new mod.agus.jcoderz.dx.rop.code.RegisterSpec[sourceCount];

            for (int i = 0; i < sourceCount; i++) {
                mod.agus.jcoderz.dx.rop.code.RegisterSpec src = sources.get(i);
                mod.agus.jcoderz.dx.rop.type.TypeBearer type = src.getTypeBearer();
                mod.agus.jcoderz.dx.rop.code.RegisterSpec scratch = src.withReg(scratchAt);
                insns.add(new mod.agus.jcoderz.dx.rop.code.PlainInsn(mod.agus.jcoderz.dx.rop.code.Rops.opMove(type), pos, scratch, src));
                scratchRegs[i] = scratch;
                scratchAt += src.getCategory();
            }

            for (int pattern = getAuxInt(); pattern != 0; pattern >>= 4) {
                int which = (pattern & 0x0f) - 1;
                mod.agus.jcoderz.dx.rop.code.RegisterSpec scratch = scratchRegs[which];
                mod.agus.jcoderz.dx.rop.type.TypeBearer type = scratch.getTypeBearer();
                insns.add(new mod.agus.jcoderz.dx.rop.code.PlainInsn(mod.agus.jcoderz.dx.rop.code.Rops.opMove(type), pos,
                                        scratch.withReg(stackPointer),
                                        scratch));
                stackPointer += type.getType().getCategory();
            }
            return;
        }

        mod.agus.jcoderz.dx.rop.type.TypeBearer destType = (dest != null) ? dest : mod.agus.jcoderz.dx.rop.type.Type.VOID;
        mod.agus.jcoderz.dx.rop.cst.Constant cst = getAuxCst();
        int ropOpcode;
        mod.agus.jcoderz.dx.rop.code.Rop rop;
        mod.agus.jcoderz.dx.rop.code.Insn insn;

        if (opcode == mod.agus.jcoderz.dx.cf.code.ByteOps.MULTIANEWARRAY) {
            blockCanThrow = true;

            // Add the extra instructions for handling multianewarray.

            extraBlockCount = 6;

            /*
             * Add an array constructor for the int[] containing all the
             * dimensions.
             */
            mod.agus.jcoderz.dx.rop.code.RegisterSpec dimsReg =
                mod.agus.jcoderz.dx.rop.code.RegisterSpec.make(dest.getNextReg(), mod.agus.jcoderz.dx.rop.type.Type.INT_ARRAY);
            rop = mod.agus.jcoderz.dx.rop.code.Rops.opFilledNewArray(mod.agus.jcoderz.dx.rop.type.Type.INT_ARRAY, sourceCount);
            insn = new mod.agus.jcoderz.dx.rop.code.ThrowingCstInsn(rop, pos, sources, catches,
                    mod.agus.jcoderz.dx.rop.cst.CstType.INT_ARRAY);
            insns.add(insn);

            // Add a move-result for the new-filled-array
            rop = mod.agus.jcoderz.dx.rop.code.Rops.opMoveResult(mod.agus.jcoderz.dx.rop.type.Type.INT_ARRAY);
            insn = new mod.agus.jcoderz.dx.rop.code.PlainInsn(rop, pos, dimsReg, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY);
            insns.add(insn);

            /*
             * Add a const-class instruction for the specified array
             * class.
             */

            /*
             * Remove as many dimensions from the originally specified
             * class as are given in the explicit list of dimensions,
             * so as to pass the right component class to the standard
             * Java library array constructor.
             */
            mod.agus.jcoderz.dx.rop.type.Type componentType = ((mod.agus.jcoderz.dx.rop.cst.CstType) cst).getClassType();
            for (int i = 0; i < sourceCount; i++) {
                componentType = componentType.getComponentType();
            }

            mod.agus.jcoderz.dx.rop.code.RegisterSpec classReg =
                mod.agus.jcoderz.dx.rop.code.RegisterSpec.make(dest.getReg(), mod.agus.jcoderz.dx.rop.type.Type.CLASS);

            if (componentType.isPrimitive()) {
                /*
                 * The component type is primitive (e.g., int as opposed
                 * to Integer), so we have to fetch the corresponding
                 * TYPE class.
                 */
                mod.agus.jcoderz.dx.rop.cst.CstFieldRef typeField =
                    CstFieldRef.forPrimitiveType(componentType);
                insn = new mod.agus.jcoderz.dx.rop.code.ThrowingCstInsn(mod.agus.jcoderz.dx.rop.code.Rops.GET_STATIC_OBJECT, pos,
                                           mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY,
                                           catches, typeField);
            } else {
                /*
                 * The component type is an object type, so just make a
                 * normal class reference.
                 */
                insn = new mod.agus.jcoderz.dx.rop.code.ThrowingCstInsn(mod.agus.jcoderz.dx.rop.code.Rops.CONST_OBJECT, pos,
                                           mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY, catches,
                                           new mod.agus.jcoderz.dx.rop.cst.CstType(componentType));
            }

            insns.add(insn);

            // Add a move-result-pseudo for the get-static or const
            rop = mod.agus.jcoderz.dx.rop.code.Rops.opMoveResultPseudo(classReg.getType());
            insn = new mod.agus.jcoderz.dx.rop.code.PlainInsn(rop, pos, classReg, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY);
            insns.add(insn);

            /*
             * Add a call to the "multianewarray method," that is,
             * Array.newInstance(class, dims). Note: The result type
             * of newInstance() is Object, which is why the last
             * instruction in this sequence is a cast to the right
             * type for the original instruction.
             */

            mod.agus.jcoderz.dx.rop.code.RegisterSpec objectReg =
                mod.agus.jcoderz.dx.rop.code.RegisterSpec.make(dest.getReg(), mod.agus.jcoderz.dx.rop.type.Type.OBJECT);

            insn = new mod.agus.jcoderz.dx.rop.code.ThrowingCstInsn(
                    mod.agus.jcoderz.dx.rop.code.Rops.opInvokeStatic(MULTIANEWARRAY_METHOD.getPrototype()),
                    pos, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.make(classReg, dimsReg),
                    catches, MULTIANEWARRAY_METHOD);
            insns.add(insn);

            // Add a move-result.
            rop = mod.agus.jcoderz.dx.rop.code.Rops.opMoveResult(MULTIANEWARRAY_METHOD.getPrototype()
                    .getReturnType());
            insn = new mod.agus.jcoderz.dx.rop.code.PlainInsn(rop, pos, objectReg, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY);
            insns.add(insn);

            /*
             * And finally, set up for the remainder of this method to
             * add an appropriate cast.
             */

            opcode = mod.agus.jcoderz.dx.cf.code.ByteOps.CHECKCAST;
            sources = mod.agus.jcoderz.dx.rop.code.RegisterSpecList.make(objectReg);
        } else if (opcode == mod.agus.jcoderz.dx.cf.code.ByteOps.JSR) {
            // JSR has no Rop instruction
            hasJsr = true;
            return;
        } else if (opcode == mod.agus.jcoderz.dx.cf.code.ByteOps.RET) {
            try {
                returnAddress = (ReturnAddress)arg(0);
            } catch (ClassCastException ex) {
                throw new RuntimeException(
                        "Argument to RET was not a ReturnAddress", ex);
            }
            // RET has no Rop instruction.
            return;
        }

        ropOpcode = jopToRopOpcode(opcode, cst);
        rop = mod.agus.jcoderz.dx.rop.code.Rops.ropFor(ropOpcode, destType, sources, cst);

        mod.agus.jcoderz.dx.rop.code.Insn moveResult = null;
        if (dest != null && rop.isCallLike()) {
            /*
             * We're going to want to have a move-result in the next
             * basic block.
             */
            extraBlockCount++;

            Type returnType;
            if (rop.getOpcode() == mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_CUSTOM) {
                returnType = ((CstCallSiteRef) cst).getReturnType();
            } else {
                returnType = ((mod.agus.jcoderz.dx.rop.cst.CstMethodRef) cst).getPrototype().getReturnType();
            }
            moveResult = new mod.agus.jcoderz.dx.rop.code.PlainInsn(mod.agus.jcoderz.dx.rop.code.Rops.opMoveResult(returnType),
                                       pos, dest, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY);

            dest = null;
        } else if (dest != null && rop.canThrow()) {
            /*
             * We're going to want to have a move-result-pseudo in the
             * next basic block.
             */
            extraBlockCount++;

            moveResult = new mod.agus.jcoderz.dx.rop.code.PlainInsn(
                    mod.agus.jcoderz.dx.rop.code.Rops.opMoveResultPseudo(dest.getTypeBearer()),
                    pos, dest, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY);

            dest = null;
        }
        if (ropOpcode == mod.agus.jcoderz.dx.rop.code.RegOps.NEW_ARRAY) {
            /*
             * In the original bytecode, this was either a primitive
             * array constructor "newarray" or an object array
             * constructor "anewarray". In the former case, there is
             * no explicit constant, and in the latter, the constant
             * is for the element type and not the array type. The rop
             * instruction form for both of these is supposed to be
             * the resulting array type, so we initialize / alter
             * "cst" here, accordingly. Conveniently enough, the rop
             * opcode already gets constructed with the proper array
             * type.
             */
            cst = CstType.intern(rop.getResult());
        } else if ((cst == null) && (sourceCount == 2)) {
            mod.agus.jcoderz.dx.rop.type.TypeBearer firstType = sources.get(0).getTypeBearer();
            mod.agus.jcoderz.dx.rop.type.TypeBearer lastType = sources.get(1).getTypeBearer();

            if ((lastType.isConstant() || firstType.isConstant()) &&
                 advice.hasConstantOperation(rop, sources.get(0),
                                             sources.get(1))) {

                if (lastType.isConstant()) {
                    /*
                     * The target architecture has an instruction that can
                     * build in the constant found in the second argument,
                     * so pull it out of the sources and just use it as a
                     * constant here.
                     */
                    cst = (mod.agus.jcoderz.dx.rop.cst.Constant) lastType;
                    sources = sources.withoutLast();

                    // For subtraction, change to addition and invert constant
                    if (rop.getOpcode() == mod.agus.jcoderz.dx.rop.code.RegOps.SUB) {
                        ropOpcode = mod.agus.jcoderz.dx.rop.code.RegOps.ADD;
                        mod.agus.jcoderz.dx.rop.cst.CstInteger cstInt = (mod.agus.jcoderz.dx.rop.cst.CstInteger) lastType;
                        cst = CstInteger.make(-cstInt.getValue());
                    }
                } else {
                    /*
                     * The target architecture has an instruction that can
                     * build in the constant found in the first argument,
                     * so pull it out of the sources and just use it as a
                     * constant here.
                     */
                    cst = (mod.agus.jcoderz.dx.rop.cst.Constant) firstType;
                    sources = sources.withoutFirst();
                }

                rop = mod.agus.jcoderz.dx.rop.code.Rops.ropFor(ropOpcode, destType, sources, cst);
            }
        }

        SwitchList cases = getAuxCases();
        ArrayList<mod.agus.jcoderz.dx.rop.cst.Constant> initValues = getInitValues();
        boolean canThrow = rop.canThrow();

        blockCanThrow |= canThrow;

        if (cases != null) {
            if (cases.size() == 0) {
                // It's a default-only switch statement. It can happen!
                insn = new mod.agus.jcoderz.dx.rop.code.PlainInsn(mod.agus.jcoderz.dx.rop.code.Rops.GOTO, pos, null,
                                     mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY);
                primarySuccessorIndex = 0;
            } else {
                IntList values = cases.getValues();
                insn = new SwitchInsn(rop, pos, dest, sources, values);
                primarySuccessorIndex = values.size();
            }
        } else if (ropOpcode == mod.agus.jcoderz.dx.rop.code.RegOps.RETURN) {
            /*
             * Returns get turned into the combination of a move (if
             * non-void and if the return doesn't already mention
             * register 0) and a goto (to the return block).
             */
            if (sources.size() != 0) {
                mod.agus.jcoderz.dx.rop.code.RegisterSpec source = sources.get(0);
                TypeBearer type = source.getTypeBearer();
                if (source.getReg() != 0) {
                    insns.add(new mod.agus.jcoderz.dx.rop.code.PlainInsn(mod.agus.jcoderz.dx.rop.code.Rops.opMove(type), pos,
                                            mod.agus.jcoderz.dx.rop.code.RegisterSpec.make(0, type),
                                            source));
                }
            }
            insn = new mod.agus.jcoderz.dx.rop.code.PlainInsn(mod.agus.jcoderz.dx.rop.code.Rops.GOTO, pos, null, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY);
            primarySuccessorIndex = 0;
            updateReturnOp(rop, pos);
            returns = true;
        } else if (cst != null) {
            if (canThrow) {
                if (rop.getOpcode() == mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_POLYMORPHIC) {
                    insn = makeInvokePolymorphicInsn(rop, pos, sources, catches, cst);
                } else {
                    insn = new ThrowingCstInsn(rop, pos, sources, catches, cst);
                }
                catchesUsed = true;
                primarySuccessorIndex = catches.size();
            } else {
                insn = new PlainCstInsn(rop, pos, dest, sources, cst);
            }
        } else if (canThrow) {
            insn = new ThrowingInsn(rop, pos, sources, catches);
            catchesUsed = true;
            if (opcode == mod.agus.jcoderz.dx.cf.code.ByteOps.ATHROW) {
                /*
                 * The op athrow is the only one where it's possible
                 * to have non-empty successors and yet not have a
                 * primary successor.
                 */
                primarySuccessorIndex = -1;
            } else {
                primarySuccessorIndex = catches.size();
            }
        } else {
            insn = new PlainInsn(rop, pos, dest, sources);
        }

        insns.add(insn);

        if (moveResult != null) {
            insns.add(moveResult);
        }

        /*
         * If initValues is non-null, it means that the parser has
         * seen a group of compatible constant initialization
         * bytecodes that are applied to the current newarray. The
         * action we take here is to convert these initialization
         * bytecodes into a single fill-array-data ROP which lays out
         * all the constant values in a table.
         */
        if (initValues != null) {
            extraBlockCount++;
            insn = new FillArrayDataInsn(Rops.FILL_ARRAY_DATA, pos,
                    mod.agus.jcoderz.dx.rop.code.RegisterSpecList.make(moveResult.getResult()), initValues,
                    cst);
            insns.add(insn);
        }
    }

    /**
     * Helper for {@link #run}, which gets the list of sources for the.
     * instruction.
     *
     * @param opcode the opcode being translated
     * @param stackPointer {@code >= 0;} the stack pointer after the
     * instruction's arguments have been popped
     * @return {@code non-null;} the sources
     */
    private mod.agus.jcoderz.dx.rop.code.RegisterSpecList getSources(int opcode, int stackPointer) {
        int count = argCount();

        if (count == 0) {
            // We get an easy out if there aren't any sources.
            return mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY;
        }

        int localIndex = getLocalIndex();
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList sources;

        if (localIndex >= 0) {
            // The instruction is operating on a local variable.
            sources = new mod.agus.jcoderz.dx.rop.code.RegisterSpecList(1);
            sources.set(0, mod.agus.jcoderz.dx.rop.code.RegisterSpec.make(localIndex, arg(0)));
        } else {
            sources = new mod.agus.jcoderz.dx.rop.code.RegisterSpecList(count);
            int regAt = stackPointer;
            for (int i = 0; i < count; i++) {
                mod.agus.jcoderz.dx.rop.code.RegisterSpec spec = mod.agus.jcoderz.dx.rop.code.RegisterSpec.make(regAt, arg(i));
                sources.set(i, spec);
                regAt += spec.getCategory();
            }

            switch (opcode) {
                case mod.agus.jcoderz.dx.cf.code.ByteOps.IASTORE: {
                    /*
                     * The Java argument order for array stores is
                     * (array, index, value), but the rop argument
                     * order is (value, array, index). The following
                     * code gets the right arguments in the right
                     * places.
                     */
                    if (count != 3) {
                        throw new RuntimeException("shouldn't happen");
                    }
                    mod.agus.jcoderz.dx.rop.code.RegisterSpec array = sources.get(0);
                    mod.agus.jcoderz.dx.rop.code.RegisterSpec index = sources.get(1);
                    mod.agus.jcoderz.dx.rop.code.RegisterSpec value = sources.get(2);
                    sources.set(0, value);
                    sources.set(1, array);
                    sources.set(2, index);
                    break;
                }
                case mod.agus.jcoderz.dx.cf.code.ByteOps.PUTFIELD: {
                    /*
                     * Similar to above: The Java argument order for
                     * putfield is (object, value), but the rop
                     * argument order is (value, object).
                     */
                    if (count != 2) {
                        throw new RuntimeException("shouldn't happen");
                    }
                    mod.agus.jcoderz.dx.rop.code.RegisterSpec obj = sources.get(0);
                    RegisterSpec value = sources.get(1);
                    sources.set(0, value);
                    sources.set(1, obj);
                    break;
                }
            }
        }

        sources.setImmutable();
        return sources;
    }

    /**
     * Sets or updates the information about the return block.
     *
     * @param op {@code non-null;} the opcode to use
     * @param pos {@code non-null;} the position to use
     */
    private void updateReturnOp(mod.agus.jcoderz.dx.rop.code.Rop op, mod.agus.jcoderz.dx.rop.code.SourcePosition pos) {
        if (op == null) {
            throw new NullPointerException("op == null");
        }

        if (pos == null) {
            throw new NullPointerException("pos == null");
        }

        if (returnOp == null) {
            returnOp = op;
            returnPosition = pos;
        } else {
            if (returnOp != op) {
                throw new SimException("return op mismatch: " + op + ", " +
                                       returnOp);
            }

            if (pos.getLine() > returnPosition.getLine()) {
                // Pick the largest line number to be the "canonical" return.
                returnPosition = pos;
            }
        }
    }

    /**
     * Gets the register opcode for the given Java opcode.
     *
     * @param jop {@code jop >= 0;} the Java opcode
     * @param cst {@code null-ok;} the constant argument, if any
     * @return {@code >= 0;} the corresponding register opcode
     */
    private int jopToRopOpcode(int jop, mod.agus.jcoderz.dx.rop.cst.Constant cst) {
        switch (jop) {
            case mod.agus.jcoderz.dx.cf.code.ByteOps.POP:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.POP2:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DUP:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DUP_X1:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DUP_X2:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DUP2:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DUP2_X1:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DUP2_X2:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.SWAP:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.JSR:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.RET:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.MULTIANEWARRAY: {
                // These need to be taken care of specially.
                break;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.NOP: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.NOP;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.LDC:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.LDC2_W: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.CONST;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ILOAD:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ISTORE: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.MOVE;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IALOAD: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.AGET;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IASTORE: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.APUT;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IADD:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IINC: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.ADD;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ISUB: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.SUB;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IMUL: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.MUL;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IDIV: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.DIV;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IREM: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.REM;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.INEG: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.NEG;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ISHL: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.SHL;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ISHR: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.SHR;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IUSHR: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.USHR;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IAND: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.AND;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IOR: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.OR;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IXOR: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.XOR;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.I2L:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.I2F:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.I2D:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.L2I:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.L2F:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.L2D:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.F2I:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.F2L:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.F2D:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.D2I:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.D2L:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.D2F: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.CONV;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.I2B: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.TO_BYTE;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.I2C: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.TO_CHAR;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.I2S: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.TO_SHORT;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.LCMP:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.FCMPL:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DCMPL: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.CMPL;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.FCMPG:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DCMPG: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.CMPG;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IFEQ:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ICMPEQ:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ACMPEQ:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IFNULL: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.IF_EQ;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IFNE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ICMPNE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ACMPNE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IFNONNULL: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.IF_NE;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IFLT:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ICMPLT: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.IF_LT;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IFGE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ICMPGE: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.IF_GE;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IFGT:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ICMPGT: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.IF_GT;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IFLE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ICMPLE: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.IF_LE;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.GOTO: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.GOTO;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.LOOKUPSWITCH: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.SWITCH;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IRETURN:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.RETURN: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.RETURN;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.GETSTATIC: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.GET_STATIC;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.PUTSTATIC: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.PUT_STATIC;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.GETFIELD: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.GET_FIELD;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.PUTFIELD: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.PUT_FIELD;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.INVOKEVIRTUAL: {
                mod.agus.jcoderz.dx.rop.cst.CstMethodRef ref = (mod.agus.jcoderz.dx.rop.cst.CstMethodRef) cst;
                // The java bytecode specification does not explicitly disallow
                // invokevirtual calls to any instance method, though it
                // specifies that instance methods and private methods "should" be
                // called using "invokespecial" instead of "invokevirtual".
                // Several bytecode tools generate "invokevirtual" instructions for
                // invocation of private methods.
                //
                // The dalvik opcode specification on the other hand allows
                // invoke-virtual to be used only with "normal" virtual methods,
                // i.e, ones that are not private, static, final or constructors.
                // We therefore need to transform invoke-virtual calls to private
                // instance methods to invoke-direct opcodes.
                //
                // Note that it assumes that all methods for a given class are
                // defined in the same dex file.
                //
                // NOTE: This is a slow O(n) loop, and can be replaced with a
                // faster implementation (at the cost of higher memory usage)
                // if it proves to be a hot area of code.
                if (ref.getDefiningClass().equals(method.getDefiningClass())) {
                    for (int i = 0; i < methods.size(); ++i) {
                        final Method m = methods.get(i);
                        if (AccessFlags.isPrivate(m.getAccessFlags()) &&
                                ref.getNat().equals(m.getNat())) {
                            return mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_DIRECT;
                        }
                    }
                }
                // If the method reference is a signature polymorphic method
                // substitute invoke-polymorphic for invoke-virtual. This only
                // affects MethodHandle.invoke and MethodHandle.invokeExact.
                if (ref.isSignaturePolymorphic()) {
                    return mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_POLYMORPHIC;
                }
                return mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_VIRTUAL;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.INVOKESPECIAL: {
                /*
                 * Determine whether the opcode should be
                 * INVOKE_DIRECT or INVOKE_SUPER. See vmspec-2 section 6
                 * on "invokespecial" as well as section 4.8.2 (7th
                 * bullet point) for the gory details.
                 */
                /* TODO: Consider checking that invoke-special target
                 * method is private, or constructor since otherwise ART
                 * verifier will reject it.
                 */
                mod.agus.jcoderz.dx.rop.cst.CstMethodRef ref = (mod.agus.jcoderz.dx.rop.cst.CstMethodRef) cst;
                if (ref.isInstanceInit() ||
                    (ref.getDefiningClass().equals(method.getDefiningClass()))) {
                    return mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_DIRECT;
                }
                return mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_SUPER;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.INVOKESTATIC: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_STATIC;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.INVOKEINTERFACE: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_INTERFACE;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.INVOKEDYNAMIC: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_CUSTOM;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.NEW: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.NEW_INSTANCE;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.NEWARRAY:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ANEWARRAY: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.NEW_ARRAY;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ARRAYLENGTH: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.ARRAY_LENGTH;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ATHROW: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.THROW;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.CHECKCAST: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.CHECK_CAST;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.INSTANCEOF: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.INSTANCE_OF;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.MONITORENTER: {
                return mod.agus.jcoderz.dx.rop.code.RegOps.MONITOR_ENTER;
            }
            case ByteOps.MONITOREXIT: {
                return RegOps.MONITOR_EXIT;
            }
        }

        throw new RuntimeException("shouldn't happen");
    }

    private Insn makeInvokePolymorphicInsn(Rop rop, SourcePosition pos, RegisterSpecList sources,
                                           TypeList catches, Constant cst) {
        mod.agus.jcoderz.dx.rop.cst.CstMethodRef cstMethodRef = (CstMethodRef) cst;
        return new InvokePolymorphicInsn(rop, pos, sources, catches, cstMethodRef);
    }
}
