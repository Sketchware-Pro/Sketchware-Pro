/*
 * Copyright (C) 2017 The Android Open Source Project
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
package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstNat;
import mod.agus.jcoderz.dx.rop.cst.CstProtoRef;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;

/**
 * An invoke-polymorphic instruction. This is a throwing instruction with
 * multiple constants.
 */
public class InvokePolymorphicInsn extends mod.agus.jcoderz.dx.rop.code.Insn {
    /** Default descriptor for signature polymorphic methods. */
    private static final mod.agus.jcoderz.dx.rop.cst.CstString DEFAULT_DESCRIPTOR =
            new mod.agus.jcoderz.dx.rop.cst.CstString("([Ljava/lang/Object;)Ljava/lang/Object;");

    /** Descriptor for VarHandle set methods. */
    private static final mod.agus.jcoderz.dx.rop.cst.CstString VARHANDLE_SET_DESCRIPTOR =
            new mod.agus.jcoderz.dx.rop.cst.CstString("([Ljava/lang/Object;)V");

    /** Descriptor for VarHandle compare-and-set methods. */
    private static final mod.agus.jcoderz.dx.rop.cst.CstString VARHANDLE_COMPARE_AND_SET_DESCRIPTOR =
            new mod.agus.jcoderz.dx.rop.cst.CstString("([Ljava/lang/Object;)Z");

    /** {@code non-null;} list of exceptions caught */
    private final TypeList catches;

    /**
     * {@code non-null;} method as it appears at the call site of the original
     * invoke-virtual instruction. This is used to construct the invoke method
     * to target and the call-site prototype.
     */
    private final mod.agus.jcoderz.dx.rop.cst.CstMethodRef callSiteMethod;

    /**
     * {@code non-null;} signature polymorphic method.
     */
    private final mod.agus.jcoderz.dx.rop.cst.CstMethodRef polymorphicMethod;

    /**
     * {@code non-null;} the call site prototype.
     */
    private final mod.agus.jcoderz.dx.rop.cst.CstProtoRef callSiteProto;

    /**
     * Constructs an instance.
     *
     * @param opcode {@code non-null;} the opcode
     * @param position {@code non-null;} source position
     * @param sources {@code non-null;} specs for all the sources
     * @param catches {@code non-null;} list of exceptions caught
     * @param callSiteMethod {@code non-null;} the method called by
     * invoke-virtual that this instance will replace.
     */
    public InvokePolymorphicInsn(mod.agus.jcoderz.dx.rop.code.Rop opcode, SourcePosition position, mod.agus.jcoderz.dx.rop.code.RegisterSpecList sources, TypeList catches,
                                 mod.agus.jcoderz.dx.rop.cst.CstMethodRef callSiteMethod) {
        super(opcode, position, null, sources);

        if (opcode.getBranchingness() != Rop.BRANCH_THROW) {
            throw new IllegalArgumentException("opcode with invalid branchingness: " + opcode.getBranchingness());
        }

        if (catches == null) {
            throw new NullPointerException("catches == null");
        }
        this.catches = catches;

        if (callSiteMethod == null) {
            throw new NullPointerException("callSiteMethod == null");
        } else if (!callSiteMethod.isSignaturePolymorphic()) {
            throw new IllegalArgumentException("callSiteMethod is not signature polymorphic");
        }

        this.callSiteMethod = callSiteMethod;
        this.polymorphicMethod = makePolymorphicMethod(callSiteMethod);
        this.callSiteProto = makeCallSiteProto(callSiteMethod);
    }

    /** {@inheritDoc} */
    @Override
    public TypeList getCatches() {
        return this.catches;
    }

    /** {@inheritDoc} */
    @Override
    public void accept(Visitor visitor) {
        visitor.visitInvokePolymorphicInsn(this);
    }

    /** {@inheritDoc} */
    @Override
    public mod.agus.jcoderz.dx.rop.code.Insn withAddedCatch(Type type) {
        return new InvokePolymorphicInsn(getOpcode(), getPosition(),
                getSources(), catches.withAddedType(type), getCallSiteMethod());
    }

    /** {@inheritDoc} */
    @Override
    public mod.agus.jcoderz.dx.rop.code.Insn withRegisterOffset(int delta) {
        return new InvokePolymorphicInsn(getOpcode(), getPosition(),
                getSources().withOffset(delta),
                catches, getCallSiteMethod());
    }

    /** {@inheritDoc} */
    @Override
    public Insn withNewRegisters(RegisterSpec result, RegisterSpecList sources) {
        return new InvokePolymorphicInsn(getOpcode(), getPosition(),
                sources, catches, getCallSiteMethod());
    }

    /**
     * Gets the method as it appears at the call site of the original
     * invoke-virtual instruction.
     *
     * @return {@code non-null;} the original method reference
     */
    public mod.agus.jcoderz.dx.rop.cst.CstMethodRef getCallSiteMethod() {
        return callSiteMethod;
    }

    /**
     * Gets the method to be invoked. This will be will either be
     * {@code java.lang.invoke.MethodHandle.invoke()} or
     * {@code java.lang.invoke.MethodHandle.invokeExact()}.
     *
     * @return {@code non-null;} method reference to be invoked
     */
    public mod.agus.jcoderz.dx.rop.cst.CstMethodRef getPolymorphicMethod() {
        return polymorphicMethod;
    }

    /**
     * Gets the call site prototype. The call site prototype is provided
     * as an argument to invoke-polymorphic to enable type checking and
     * type conversion.
     *
     * @return {@code non-null;} Prototype reference for call site
     */
    public mod.agus.jcoderz.dx.rop.cst.CstProtoRef getCallSiteProto() {
        return callSiteProto;
    }

    /** {@inheritDoc} */
    @Override
    public String getInlineString() {
        return getPolymorphicMethod().toString() + " " +
            getCallSiteProto().toString() + " " +
            ThrowingInsn.toCatchString(catches);
    }

    private static mod.agus.jcoderz.dx.rop.cst.CstMethodRef makePolymorphicMethod(final mod.agus.jcoderz.dx.rop.cst.CstMethodRef callSiteMethod) {
        mod.agus.jcoderz.dx.rop.cst.CstType definingClass= callSiteMethod.getDefiningClass();
        CstString cstMethodName = callSiteMethod.getNat().getName();
        String methodName = callSiteMethod.getNat().getName().getString();

        if (definingClass.equals(mod.agus.jcoderz.dx.rop.cst.CstType.METHOD_HANDLE)) {
            if (methodName.equals("invoke") || methodName.equals("invokeExact")) {
                mod.agus.jcoderz.dx.rop.cst.CstNat cstNat = new mod.agus.jcoderz.dx.rop.cst.CstNat(cstMethodName, DEFAULT_DESCRIPTOR);
                return new mod.agus.jcoderz.dx.rop.cst.CstMethodRef(definingClass, cstNat);
            }
        }

        if (definingClass.equals(CstType.VAR_HANDLE)) {
            switch (methodName) {
                case "compareAndExchange":
                case "compareAndExchangeAcquire":
                case "compareAndExchangeRelease":
                case "get":
                case "getAcquire":
                case "getAndAdd":
                case "getAndAddAcquire":
                case "getAndAddRelease":
                case "getAndBitwiseAnd":
                case "getAndBitwiseAndAcquire":
                case "getAndBitwiseAndRelease":
                case "getAndBitwiseOr":
                case "getAndBitwiseOrAcquire":
                case "getAndBitwiseOrRelease":
                case "getAndBitwiseXor":
                case "getAndBitwiseXorAcquire":
                case "getAndBitwiseXorRelease":
                case "getAndSet":
                case "getAndSetAcquire":
                case "getAndSetRelease":
                case "getOpaque":
                case "getVolatile":
                {
                    mod.agus.jcoderz.dx.rop.cst.CstNat cstNat = new mod.agus.jcoderz.dx.rop.cst.CstNat(cstMethodName, DEFAULT_DESCRIPTOR);
                    return new mod.agus.jcoderz.dx.rop.cst.CstMethodRef(definingClass, cstNat);
                }
                case "set":
                case "setOpaque":
                case "setRelease":
                case "setVolatile":
                {
                    mod.agus.jcoderz.dx.rop.cst.CstNat cstNat = new mod.agus.jcoderz.dx.rop.cst.CstNat(cstMethodName, VARHANDLE_SET_DESCRIPTOR);
                    return new mod.agus.jcoderz.dx.rop.cst.CstMethodRef(definingClass, cstNat);
                }
                case "compareAndSet":
                case "weakCompareAndSet":
                case "weakCompareAndSetAcquire":
                case "weakCompareAndSetPlain":
                case "weakCompareAndSetRelease":
                {
                    mod.agus.jcoderz.dx.rop.cst.CstNat cstNat = new CstNat(cstMethodName, VARHANDLE_COMPARE_AND_SET_DESCRIPTOR);
                    return new mod.agus.jcoderz.dx.rop.cst.CstMethodRef(definingClass, cstNat);
                }
                default:
                    break;
            }
        }
        throw new IllegalArgumentException("Unknown signature polymorphic method: " +
                                           callSiteMethod.toHuman());
    }

    private static mod.agus.jcoderz.dx.rop.cst.CstProtoRef makeCallSiteProto(final CstMethodRef callSiteMethod) {
        return new CstProtoRef(callSiteMethod.getPrototype(true));
    }
}
