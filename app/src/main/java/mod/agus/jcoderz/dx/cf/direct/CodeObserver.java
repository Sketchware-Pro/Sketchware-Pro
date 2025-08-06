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

package mod.agus.jcoderz.dx.cf.direct;

import java.util.ArrayList;

import mod.agus.jcoderz.dx.cf.code.ByteOps;
import mod.agus.jcoderz.dx.cf.code.BytecodeArray;
import mod.agus.jcoderz.dx.cf.code.SwitchList;
import mod.agus.jcoderz.dx.cf.iface.ParseObserver;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstDouble;
import mod.agus.jcoderz.dx.rop.cst.CstFloat;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.rop.cst.CstKnownNull;
import mod.agus.jcoderz.dx.rop.cst.CstLong;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.ByteArray;
import mod.agus.jcoderz.dx.util.Hex;

/**
 * Bytecode visitor to use when "observing" bytecode getting parsed.
 */
public class CodeObserver implements BytecodeArray.Visitor {
    /** {@code non-null;} actual array of bytecode */
    private final mod.agus.jcoderz.dx.util.ByteArray bytes;

    /** {@code non-null;} observer to inform of parsing */
    private final mod.agus.jcoderz.dx.cf.iface.ParseObserver observer;

    /**
     * Constructs an instance.
     *
     * @param bytes {@code non-null;} actual array of bytecode
     * @param observer {@code non-null;} observer to inform of parsing
     */
    public CodeObserver(ByteArray bytes, ParseObserver observer) {
        if (bytes == null) {
            throw new NullPointerException("bytes == null");
        }

        if (observer == null) {
            throw new NullPointerException("observer == null");
        }

        this.bytes = bytes;
        this.observer = observer;
    }

    /** {@inheritDoc} */
    @Override
    public void visitInvalid(int opcode, int offset, int length) {
        observer.parsed(bytes, offset, length, header(offset));
    }

    /** {@inheritDoc} */
    @Override
    public void visitNoArgs(int opcode, int offset, int length, mod.agus.jcoderz.dx.rop.type.Type type) {
        observer.parsed(bytes, offset, length, header(offset));
    }

    /** {@inheritDoc} */
    @Override
    public void visitLocal(int opcode, int offset, int length,
                           int idx, Type type, int value) {
        String idxStr = (length <= 3) ? mod.agus.jcoderz.dx.util.Hex.u1(idx) : mod.agus.jcoderz.dx.util.Hex.u2(idx);
        boolean argComment = (length == 1);
        String valueStr = "";

        if (opcode == mod.agus.jcoderz.dx.cf.code.ByteOps.IINC) {
            valueStr = ", #" +
                ((length <= 3) ? mod.agus.jcoderz.dx.util.Hex.s1(value) : mod.agus.jcoderz.dx.util.Hex.s2(value));
        }

        String catStr = "";
        if (type.isCategory2()) {
            catStr = (argComment ? "," : " //") + " category-2";
        }

        observer.parsed(bytes, offset, length,
                        header(offset) + (argComment ? " // " : " ") +
                        idxStr + valueStr + catStr);
    }

    /** {@inheritDoc} */
    @Override
    public void visitConstant(int opcode, int offset, int length,
                              mod.agus.jcoderz.dx.rop.cst.Constant cst, int value) {
        if (cst instanceof CstKnownNull) {
            // This is aconst_null.
            visitNoArgs(opcode, offset, length, null);
            return;
        }

        if (cst instanceof CstInteger) {
            visitLiteralInt(opcode, offset, length, value);
            return;
        }

        if (cst instanceof mod.agus.jcoderz.dx.rop.cst.CstLong) {
            visitLiteralLong(opcode, offset, length,
                             ((CstLong) cst).getValue());
            return;
        }

        if (cst instanceof mod.agus.jcoderz.dx.rop.cst.CstFloat) {
            visitLiteralFloat(opcode, offset, length,
                              ((CstFloat) cst).getIntBits());
            return;
        }

        if (cst instanceof mod.agus.jcoderz.dx.rop.cst.CstDouble) {
            visitLiteralDouble(opcode, offset, length,
                             ((CstDouble) cst).getLongBits());
            return;
        }

        String valueStr = "";
        if (value != 0) {
            valueStr = ", ";
            if (opcode == mod.agus.jcoderz.dx.cf.code.ByteOps.MULTIANEWARRAY) {
                valueStr += mod.agus.jcoderz.dx.util.Hex.u1(value);
            } else {
                valueStr += mod.agus.jcoderz.dx.util.Hex.u2(value);
            }
        }

        observer.parsed(bytes, offset, length,
                        header(offset) + " " + cst + valueStr);
    }

    /** {@inheritDoc} */
    @Override
    public void visitBranch(int opcode, int offset, int length,
                            int target) {
        String targetStr = (length <= 3) ? mod.agus.jcoderz.dx.util.Hex.u2(target) : mod.agus.jcoderz.dx.util.Hex.u4(target);
        observer.parsed(bytes, offset, length,
                        header(offset) + " " + targetStr);
    }

    /** {@inheritDoc} */
    @Override
    public void visitSwitch(int opcode, int offset, int length,
                            SwitchList cases, int padding) {
        int sz = cases.size();
        StringBuilder sb = new StringBuilder(sz * 20 + 100);

        sb.append(header(offset));
        if (padding != 0) {
            sb.append(" // padding: " + mod.agus.jcoderz.dx.util.Hex.u4(padding));
        }
        sb.append('\n');

        for (int i = 0; i < sz; i++) {
            sb.append("  ");
            sb.append(mod.agus.jcoderz.dx.util.Hex.s4(cases.getValue(i)));
            sb.append(": ");
            sb.append(mod.agus.jcoderz.dx.util.Hex.u2(cases.getTarget(i)));
            sb.append('\n');
        }

        sb.append("  default: ");
        sb.append(mod.agus.jcoderz.dx.util.Hex.u2(cases.getDefaultTarget()));

        observer.parsed(bytes, offset, length, sb.toString());
    }

    /** {@inheritDoc} */
    @Override
    public void visitNewarray(int offset, int length, CstType cst,
            ArrayList<Constant> intVals) {
        String commentOrSpace = (length == 1) ? " // " : " ";
        String typeName = cst.getClassType().getComponentType().toHuman();

        observer.parsed(bytes, offset, length,
                        header(offset) + commentOrSpace + typeName);
    }

    /** {@inheritDoc} */
    @Override
    public void setPreviousOffset(int offset) {
        // Do nothing
    }

    /** {@inheritDoc} */
    @Override
    public int getPreviousOffset() {
        return -1;
    }

    /**
     * Helper to produce the first bit of output for each instruction.
     *
     * @param offset the offset to the start of the instruction
     */
    private String header(int offset) {
        /*
         * Note: This uses the original bytecode, not the
         * possibly-transformed one.
         */
        int opcode = bytes.getUnsignedByte(offset);
        String name = mod.agus.jcoderz.dx.cf.code.ByteOps.opName(opcode);

        if (opcode == mod.agus.jcoderz.dx.cf.code.ByteOps.WIDE) {
            opcode = bytes.getUnsignedByte(offset + 1);
            name += " " + mod.agus.jcoderz.dx.cf.code.ByteOps.opName(opcode);
        }

        return mod.agus.jcoderz.dx.util.Hex.u2(offset) + ": " + name;
    }

    /**
     * Helper for {@link #visitConstant} where the constant is an
     * {@code int}.
     *
     * @param opcode the opcode
     * @param offset offset to the instruction
     * @param length instruction length
     * @param value constant value
     */
    private void visitLiteralInt(int opcode, int offset, int length,
            int value) {
        String commentOrSpace = (length == 1) ? " // " : " ";
        String valueStr;

        opcode = bytes.getUnsignedByte(offset); // Compare with orig op below.
        if ((length == 1) || (opcode == mod.agus.jcoderz.dx.cf.code.ByteOps.BIPUSH)) {
            valueStr = "#" + mod.agus.jcoderz.dx.util.Hex.s1(value);
        } else if (opcode == ByteOps.SIPUSH) {
            valueStr = "#" + mod.agus.jcoderz.dx.util.Hex.s2(value);
        } else {
            valueStr = "#" + mod.agus.jcoderz.dx.util.Hex.s4(value);
        }

        observer.parsed(bytes, offset, length,
                        header(offset) + commentOrSpace + valueStr);
    }

    /**
     * Helper for {@link #visitConstant} where the constant is a
     * {@code long}.
     *
     * @param opcode the opcode
     * @param offset offset to the instruction
     * @param length instruction length
     * @param value constant value
     */
    private void visitLiteralLong(int opcode, int offset, int length,
            long value) {
        String commentOrLit = (length == 1) ? " // " : " #";
        String valueStr;

        if (length == 1) {
            valueStr = mod.agus.jcoderz.dx.util.Hex.s1((int) value);
        } else {
            valueStr = mod.agus.jcoderz.dx.util.Hex.s8(value);
        }

        observer.parsed(bytes, offset, length,
                        header(offset) + commentOrLit + valueStr);
    }

    /**
     * Helper for {@link #visitConstant} where the constant is a
     * {@code float}.
     *
     * @param opcode the opcode
     * @param offset offset to the instruction
     * @param length instruction length
     * @param bits constant value, as float-bits
     */
    private void visitLiteralFloat(int opcode, int offset, int length,
            int bits) {
        String optArg = (length != 1) ? " #" + mod.agus.jcoderz.dx.util.Hex.u4(bits) : "";

        observer.parsed(bytes, offset, length,
                        header(offset) + optArg + " // " +
                        Float.intBitsToFloat(bits));
    }

    /**
     * Helper for {@link #visitConstant} where the constant is a
     * {@code double}.
     *
     * @param opcode the opcode
     * @param offset offset to the instruction
     * @param length instruction length
     * @param bits constant value, as double-bits
     */
    private void visitLiteralDouble(int opcode, int offset, int length,
            long bits) {
        String optArg = (length != 1) ? " #" + Hex.u8(bits) : "";

        observer.parsed(bytes, offset, length,
                        header(offset) + optArg + " // " +
                        Double.longBitsToDouble(bits));
    }
}
