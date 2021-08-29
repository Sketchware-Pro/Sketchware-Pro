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

import mod.agus.jcoderz.dx.rop.cst.CstCallSiteRef;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Prototype;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;
import mod.agus.jcoderz.dx.util.Hex;

/**
 * {@link Machine} which keeps track of known values but does not do
 * smart/realistic reference type calculations.
 */
public class ValueAwareMachine extends BaseMachine {
    /**
     * Constructs an instance.
     *
     * @param prototype {@code non-null;} the prototype for the associated
     * method
     */
    public ValueAwareMachine(Prototype prototype) {
        super(prototype);
    }

    /** {@inheritDoc} */
    @Override
    public void run(Frame frame, int offset, int opcode) {
        switch (opcode) {
            case mod.agus.jcoderz.dx.cf.code.ByteOps.NOP:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IASTORE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.POP:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.POP2:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IFEQ:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IFNE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IFLT:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IFGE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IFGT:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IFLE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ICMPEQ:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ICMPNE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ICMPLT:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ICMPGE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ICMPGT:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ICMPLE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ACMPEQ:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IF_ACMPNE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.GOTO:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.RET:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.LOOKUPSWITCH:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IRETURN:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.RETURN:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.PUTSTATIC:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.PUTFIELD:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ATHROW:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.MONITORENTER:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.MONITOREXIT:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IFNULL:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IFNONNULL: {
                // Nothing to do for these ops in this class.
                clearResult();
                break;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.LDC:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.LDC2_W: {
                setResult((mod.agus.jcoderz.dx.rop.type.TypeBearer) getAuxCst());
                break;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ILOAD:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ISTORE: {
                setResult(arg(0));
                break;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IALOAD:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IADD:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ISUB:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IMUL:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IDIV:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IREM:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.INEG:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ISHL:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ISHR:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IUSHR:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IAND:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IOR:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IXOR:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IINC:
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
            case mod.agus.jcoderz.dx.cf.code.ByteOps.D2F:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.I2B:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.I2C:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.I2S:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.LCMP:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.FCMPL:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.FCMPG:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DCMPL:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DCMPG:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ARRAYLENGTH: {
                setResult(getAuxType());
                break;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DUP:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DUP_X1:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DUP_X2:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DUP2:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DUP2_X1:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DUP2_X2:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.SWAP: {
                clearResult();
                for (int pattern = getAuxInt(); pattern != 0; pattern >>= 4) {
                    int which = (pattern & 0x0f) - 1;
                    addResult(arg(which));
                }
                break;
            }

            case mod.agus.jcoderz.dx.cf.code.ByteOps.JSR: {
                setResult(new ReturnAddress(getAuxTarget()));
                break;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.GETSTATIC:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.GETFIELD:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.INVOKEVIRTUAL:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.INVOKESTATIC:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.INVOKEINTERFACE: {
                mod.agus.jcoderz.dx.rop.type.Type type = ((mod.agus.jcoderz.dx.rop.type.TypeBearer) getAuxCst()).getType();
                if (type == mod.agus.jcoderz.dx.rop.type.Type.VOID) {
                    clearResult();
                } else {
                    setResult(type);
                }
                break;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.INVOKESPECIAL: {
                mod.agus.jcoderz.dx.rop.type.Type thisType = arg(0).getType();
                if (thisType.isUninitialized()) {
                    frame.makeInitialized(thisType);
                }
                mod.agus.jcoderz.dx.rop.type.Type type = ((TypeBearer) getAuxCst()).getType();
                if (type == mod.agus.jcoderz.dx.rop.type.Type.VOID) {
                    clearResult();
                } else {
                    setResult(type);
                }
                break;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.INVOKEDYNAMIC: {
                mod.agus.jcoderz.dx.rop.type.Type type = ((CstCallSiteRef) getAuxCst()).getReturnType();
                if (type == mod.agus.jcoderz.dx.rop.type.Type.VOID) {
                    clearResult();
                } else {
                    setResult(type);
                }
                break;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.NEW: {
                mod.agus.jcoderz.dx.rop.type.Type type = ((mod.agus.jcoderz.dx.rop.cst.CstType) getAuxCst()).getClassType();
                setResult(type.asUninitialized(offset));
                break;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.NEWARRAY:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.CHECKCAST:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.MULTIANEWARRAY: {
                mod.agus.jcoderz.dx.rop.type.Type type = ((mod.agus.jcoderz.dx.rop.cst.CstType) getAuxCst()).getClassType();
                setResult(type);
                break;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ANEWARRAY: {
                mod.agus.jcoderz.dx.rop.type.Type type = ((CstType) getAuxCst()).getClassType();
                setResult(type.getArrayType());
                break;
            }
            case ByteOps.INSTANCEOF: {
                setResult(Type.INT);
                break;
            }
            default: {
                throw new RuntimeException("shouldn't happen: " +
                                           Hex.u1(opcode));
            }
        }

        storeResults(frame);
    }
}
