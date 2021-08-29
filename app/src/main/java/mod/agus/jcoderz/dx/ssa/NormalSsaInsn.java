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

package mod.agus.jcoderz.dx.ssa;

import mod.agus.jcoderz.dx.rop.code.Insn;
import mod.agus.jcoderz.dx.rop.code.LocalItem;
import mod.agus.jcoderz.dx.rop.code.RegOps;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.Rop;

/**
 * A "normal" (non-phi) instruction in SSA form. Always wraps a rop insn.
 */
public final class NormalSsaInsn extends SsaInsn implements Cloneable {
    /** {@code non-null;} rop insn that we're wrapping */
    private mod.agus.jcoderz.dx.rop.code.Insn insn;

    /**
     * Creates an instance.
     *
     * @param insn Rop insn to wrap
     * @param block block that contains this insn
     */
    NormalSsaInsn(final mod.agus.jcoderz.dx.rop.code.Insn insn, final SsaBasicBlock block) {
        super(insn.getResult(), block);
        this.insn = insn;
    }

    /** {@inheritDoc} */
    @Override
    public final void mapSourceRegisters(RegisterMapper mapper) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList oldSources = insn.getSources();
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList newSources = mapper.map(oldSources);

        if (newSources != oldSources) {
            insn = insn.withNewRegisters(getResult(), newSources);
            getBlock().getParent().onSourcesChanged(this, oldSources);
        }
    }

    /**
     * Changes one of the insn's sources. New source should be of same type
     * and category.
     *
     * @param index {@code >=0;} index of source to change
     * @param newSpec spec for new source
     */
    public final void changeOneSource(int index, mod.agus.jcoderz.dx.rop.code.RegisterSpec newSpec) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList origSources = insn.getSources();
        int sz = origSources.size();
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList newSources = new mod.agus.jcoderz.dx.rop.code.RegisterSpecList(sz);

        for (int i = 0; i < sz; i++) {
            newSources.set(i, i == index ? newSpec : origSources.get(i));
        }

        newSources.setImmutable();

        mod.agus.jcoderz.dx.rop.code.RegisterSpec origSpec = origSources.get(index);
        if (origSpec.getReg() != newSpec.getReg()) {
            /*
             * If the register remains unchanged, we're only changing
             * the type or local var name so don't update use list
             */
            getBlock().getParent().onSourceChanged(this, origSpec, newSpec);
        }

        insn = insn.withNewRegisters(getResult(), newSources);
    }

    /**
     * Changes the source list of the insn. New source list should be the
     * same size and consist of sources of identical types.
     *
     * @param newSources non-null new sources list.
     */
    public final void setNewSources (mod.agus.jcoderz.dx.rop.code.RegisterSpecList newSources) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList origSources = insn.getSources();

        if (origSources.size() != newSources.size()) {
            throw new RuntimeException("Sources counts don't match");
        }

        insn = insn.withNewRegisters(getResult(), newSources);
    }

    /** {@inheritDoc} */
    @Override
    public NormalSsaInsn clone() {
        return (NormalSsaInsn) super.clone();
    }

    /**
     * Like rop.Insn.getSources().
     *
     * @return {@code null-ok;} sources list
     */
    @Override
    public mod.agus.jcoderz.dx.rop.code.RegisterSpecList getSources() {
        return insn.getSources();
    }

    /** {@inheritDoc} */
    @Override
    public String toHuman() {
        return toRopInsn().toHuman();
    }

    /** {@inheritDoc} */
    @Override
    public mod.agus.jcoderz.dx.rop.code.Insn toRopInsn() {
        return insn.withNewRegisters(getResult(), insn.getSources());
    }

    /**
     * @return the Rop opcode for this insn
     */
    @Override
    public mod.agus.jcoderz.dx.rop.code.Rop getOpcode() {
        return insn.getOpcode();
    }

    /** {@inheritDoc} */
    @Override
    public mod.agus.jcoderz.dx.rop.code.Insn getOriginalRopInsn() {
        return insn;
    }

    /** {@inheritDoc} */
    @Override
    public mod.agus.jcoderz.dx.rop.code.RegisterSpec getLocalAssignment() {
        RegisterSpec assignment;

        if (insn.getOpcode().getOpcode() == mod.agus.jcoderz.dx.rop.code.RegOps.MARK_LOCAL) {
            assignment = insn.getSources().get(0);
        } else {
            assignment = getResult();
        }

        if (assignment == null) {
            return null;
        }

        LocalItem local = assignment.getLocalItem();

        if (local == null) {
            return null;
        }

        return assignment;
    }

    /**
     * Upgrades this insn to a version that represents the constant source
     * literally. If the upgrade is not possible, this does nothing.
     *
     * @see Insn#withSourceLiteral
     */
    public void upgradeToLiteral() {
        RegisterSpecList oldSources = insn.getSources();

        insn = insn.withSourceLiteral();
        getBlock().getParent().onSourcesChanged(this, oldSources);
    }

    /**
     * @return true if this is a move (but not a move-operand) instruction
     */
    @Override
    public boolean isNormalMoveInsn() {
        return insn.getOpcode().getOpcode() == mod.agus.jcoderz.dx.rop.code.RegOps.MOVE;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isMoveException() {
        return insn.getOpcode().getOpcode() == mod.agus.jcoderz.dx.rop.code.RegOps.MOVE_EXCEPTION;
    }

    /** {@inheritDoc} */
    @Override
    public boolean canThrow() {
        return insn.canThrow();
    }

    /** {@inheritDoc} */
    @Override
    public void accept(Visitor v) {
        if (isNormalMoveInsn()) {
            v.visitMoveInsn(this);
        } else {
            v.visitNonMoveInsn(this);
        }
    }

    /** {@inheritDoc} */
    @Override
    public  boolean isPhiOrMove() {
        return isNormalMoveInsn();
    }

    /**
     * {@inheritDoc}
     *
     * TODO: Increase the scope of this.
     */
    @Override
    public boolean hasSideEffect() {
        mod.agus.jcoderz.dx.rop.code.Rop opcode = getOpcode();

        if (opcode.getBranchingness() != Rop.BRANCH_NONE) {
            return true;
        }

        boolean hasLocalSideEffect
            = Optimizer.getPreserveLocals() && getLocalAssignment() != null;

        switch (opcode.getOpcode()) {
            case mod.agus.jcoderz.dx.rop.code.RegOps.MOVE_RESULT:
            case mod.agus.jcoderz.dx.rop.code.RegOps.MOVE:
            case RegOps.CONST:
                return hasLocalSideEffect;
            default:
                return true;
        }
    }
}
