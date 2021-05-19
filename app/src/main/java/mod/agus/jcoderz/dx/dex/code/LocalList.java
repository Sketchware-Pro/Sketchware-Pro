package mod.agus.jcoderz.dx.dex.code;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecSet;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.FixedSizeList;

public final class LocalList extends FixedSizeList {
    public static final LocalList EMPTY = new LocalList(0);
    private static final boolean DEBUG = false;

    public LocalList(int i) {
        super(i);
    }

    public static LocalList make(DalvInsnList dalvInsnList) {
        int size = dalvInsnList.size();
        MakeState makeState = new MakeState(size);
        for (int i = 0; i < size; i++) {
            DalvInsn dalvInsn = dalvInsnList.get(i);
            if (dalvInsn instanceof LocalSnapshot) {
                makeState.snapshot(dalvInsn.getAddress(), ((LocalSnapshot) dalvInsn).getLocals());
            } else if (dalvInsn instanceof LocalStart) {
                makeState.startLocal(dalvInsn.getAddress(), ((LocalStart) dalvInsn).getLocal());
            }
        }
        return makeState.finish();
    }

    private static void debugVerify(LocalList localList) {
        try {
            debugVerify0(localList);
        } catch (RuntimeException e) {
            int size = localList.size();
            for (int i = 0; i < size; i++) {
                System.err.println(localList.get(i));
            }
            throw e;
        }
    }

    private static void debugVerify0(LocalList localList) {
        int size = localList.size();
        Entry[] entryArr = new Entry[65536];
        for (int i = 0; i < size; i++) {
            Entry entry = localList.get(i);
            int register = entry.getRegister();
            if (entry.isStart()) {
                Entry entry2 = entryArr[register];
                if (entry2 == null || !entry.matches(entry2)) {
                    entryArr[register] = entry;
                } else {
                    throw new RuntimeException("redundant start at " + Integer.toHexString(entry.getAddress()) + ": got " + entry + "; had " + entry2);
                }
            } else if (entryArr[register] == null) {
                throw new RuntimeException("redundant end at " + Integer.toHexString(entry.getAddress()));
            } else {
                int address = entry.getAddress();
                boolean z = false;
                for (int i2 = i + 1; i2 < size; i2++) {
                    Entry entry3 = localList.get(i2);
                    if (entry3.getAddress() != address) {
                        break;
                    }
                    if (entry3.getRegisterSpec().getReg() == register) {
                        if (!entry3.isStart()) {
                            throw new RuntimeException("redundant end at " + Integer.toHexString(address));
                        } else if (entry.getDisposition() != Disposition.END_REPLACED) {
                            throw new RuntimeException("improperly marked end at " + Integer.toHexString(address));
                        } else {
                            z = true;
                        }
                    }
                }
                if (z || entry.getDisposition() != Disposition.END_REPLACED) {
                    entryArr[register] = null;
                } else {
                    throw new RuntimeException("improper end replacement claim at " + Integer.toHexString(address));
                }
            }
        }
    }

    public Entry get(int i) {
        return (Entry) get0(i);
    }

    public void set(int i, Entry entry) {
        set0(i, entry);
    }

    public void debugPrint(PrintStream printStream, String str) {
        int size = size();
        for (int i = 0; i < size; i++) {
            printStream.print(str);
            printStream.println(get(i));
        }
    }

    public enum Disposition {
        START,
        END_SIMPLY,
        END_REPLACED,
        END_MOVED,
        END_CLOBBERED_BY_PREV,
        END_CLOBBERED_BY_NEXT
    }

    public static class Entry implements Comparable<Entry> {
        private final int address;
        private final Disposition disposition;
        private final RegisterSpec spec;
        private final CstType type;

        public Entry(int i, Disposition disposition2, RegisterSpec registerSpec) {
            if (i < 0) {
                throw new IllegalArgumentException("address < 0");
            } else if (disposition2 == null) {
                throw new NullPointerException("disposition == null");
            } else {
                try {
                    if (registerSpec.getLocalItem() == null) {
                        throw new NullPointerException("spec.getLocalItem() == null");
                    }
                    this.address = i;
                    this.disposition = disposition2;
                    this.spec = registerSpec;
                    this.type = CstType.intern(registerSpec.getType());
                } catch (NullPointerException e) {
                    throw new NullPointerException("spec == null");
                }
            }
        }

        public String toString() {
            return Integer.toHexString(this.address) + " " + this.disposition + " " + this.spec;
        }

        public boolean equals(Object obj) {
            return (obj instanceof Entry) && compareTo((Entry) obj) == 0;
        }

        public int compareTo(Entry entry) {
            if (this.address < entry.address) {
                return -1;
            }
            if (this.address > entry.address) {
                return 1;
            }
            boolean isStart = isStart();
            if (isStart != entry.isStart()) {
                return !isStart ? -1 : 1;
            }
            return this.spec.compareTo(entry.spec);
        }

        public int getAddress() {
            return this.address;
        }

        public Disposition getDisposition() {
            return this.disposition;
        }

        public boolean isStart() {
            return this.disposition == Disposition.START;
        }

        public CstString getName() {
            return this.spec.getLocalItem().getName();
        }

        public CstString getSignature() {
            return this.spec.getLocalItem().getSignature();
        }

        public CstType getType() {
            return this.type;
        }

        public int getRegister() {
            return this.spec.getReg();
        }

        public RegisterSpec getRegisterSpec() {
            return this.spec;
        }

        public boolean matches(RegisterSpec registerSpec) {
            return this.spec.equalsUsingSimpleType(registerSpec);
        }

        public boolean matches(Entry entry) {
            return matches(entry.spec);
        }

        public Entry withDisposition(Disposition disposition2) {
            return disposition2 == this.disposition ? this : new Entry(this.address, disposition2, this.spec);
        }
    }

    public static class MakeState {
        private final ArrayList<Entry> result;
        private int[] endIndices = null;
        private final int lastAddress = 0;
        private int nullResultCount = 0;
        private RegisterSpecSet regs = null;

        public MakeState(int i) {
            this.result = new ArrayList<>(i);
        }

        private static RegisterSpec filterSpec(RegisterSpec registerSpec) {
            if (registerSpec == null || registerSpec.getType() != Type.KNOWN_NULL) {
                return registerSpec;
            }
            return registerSpec.withType(Type.OBJECT);
        }

        private void aboutToProcess(int i, int i2) {
            boolean z = this.endIndices == null;
            if (i == this.lastAddress && !z) {
                return;
            }
            if (i < this.lastAddress) {
                throw new RuntimeException("shouldn't happen");
            } else if (z || i2 >= this.endIndices.length) {
                int i3 = i2 + 1;
                RegisterSpecSet registerSpecSet = new RegisterSpecSet(i3);
                int[] iArr = new int[i3];
                Arrays.fill(iArr, -1);
                if (!z) {
                    registerSpecSet.putAll(this.regs);
                    System.arraycopy(this.endIndices, 0, iArr, 0, this.endIndices.length);
                }
                this.regs = registerSpecSet;
                this.endIndices = iArr;
            }
        }

        public void snapshot(int i, RegisterSpecSet registerSpecSet) {
            int maxSize = registerSpecSet.getMaxSize();
            aboutToProcess(i, maxSize - 1);
            for (int i2 = 0; i2 < maxSize; i2++) {
                RegisterSpec registerSpec = this.regs.get(i2);
                RegisterSpec filterSpec = filterSpec(registerSpecSet.get(i2));
                if (registerSpec == null) {
                    if (filterSpec != null) {
                        startLocal(i, filterSpec);
                    }
                } else if (filterSpec == null) {
                    endLocal(i, registerSpec);
                } else if (!filterSpec.equalsUsingSimpleType(registerSpec)) {
                    endLocal(i, registerSpec);
                    startLocal(i, filterSpec);
                }
            }
        }

        public void startLocal(int i, RegisterSpec registerSpec) {
            RegisterSpec registerSpec2;
            RegisterSpec registerSpec3;
            int reg = registerSpec.getReg();
            RegisterSpec filterSpec = filterSpec(registerSpec);
            aboutToProcess(i, reg);
            RegisterSpec registerSpec4 = this.regs.get(reg);
            if (!filterSpec.equalsUsingSimpleType(registerSpec4)) {
                RegisterSpec findMatchingLocal = this.regs.findMatchingLocal(filterSpec);
                if (findMatchingLocal != null) {
                    addOrUpdateEnd(i, Disposition.END_MOVED, findMatchingLocal);
                }
                int i2 = this.endIndices[reg];
                if (registerSpec4 != null) {
                    add(i, Disposition.END_REPLACED, registerSpec4);
                } else if (i2 >= 0) {
                    Entry entry = this.result.get(i2);
                    if (entry.getAddress() == i) {
                        if (entry.matches(filterSpec)) {
                            this.result.set(i2, null);
                            this.nullResultCount++;
                            this.regs.put(filterSpec);
                            this.endIndices[reg] = -1;
                            return;
                        }
                        this.result.set(i2, entry.withDisposition(Disposition.END_REPLACED));
                    }
                }
                if (reg > 0 && (registerSpec3 = this.regs.get(reg - 1)) != null && registerSpec3.isCategory2()) {
                    addOrUpdateEnd(i, Disposition.END_CLOBBERED_BY_NEXT, registerSpec3);
                }
                if (filterSpec.isCategory2() && (registerSpec2 = this.regs.get(reg + 1)) != null) {
                    addOrUpdateEnd(i, Disposition.END_CLOBBERED_BY_PREV, registerSpec2);
                }
                add(i, Disposition.START, filterSpec);
            }
        }

        public void endLocal(int i, RegisterSpec registerSpec) {
            endLocal(i, registerSpec, Disposition.END_SIMPLY);
        }

        public void endLocal(int i, RegisterSpec registerSpec, Disposition disposition) {
            int reg = registerSpec.getReg();
            RegisterSpec filterSpec = filterSpec(registerSpec);
            aboutToProcess(i, reg);
            if (this.endIndices[reg] < 0 && !checkForEmptyRange(i, filterSpec)) {
                add(i, disposition, filterSpec);
            }
        }

        private boolean checkForEmptyRange(int i, RegisterSpec registerSpec) {
            boolean z = false;
            int size = this.result.size() - 1;
            while (size >= 0) {
                Entry entry = this.result.get(size);
                if (entry != null) {
                    if (entry.getAddress() != i) {
                        return false;
                    }
                    if (entry.matches(registerSpec)) {
                        break;
                    }
                }
                size--;
            }
            this.regs.remove(registerSpec);
            this.result.set(size, null);
            this.nullResultCount++;
            int reg = registerSpec.getReg();
            int i2 = size - 1;
            Entry entry2 = null;
            while (true) {
                if (i2 < 0) {
                    break;
                }
                entry2 = this.result.get(i2);
                if (entry2 != null && entry2.getRegisterSpec().getReg() == reg) {
                    z = true;
                    break;
                }
                i2--;
            }
            if (z) {
                this.endIndices[reg] = i2;
                if (entry2.getAddress() == i) {
                    this.result.set(i2, entry2.withDisposition(Disposition.END_SIMPLY));
                }
            }
            return true;
        }

        private void add(int i, Disposition disposition, RegisterSpec registerSpec) {
            int reg = registerSpec.getReg();
            this.result.add(new Entry(i, disposition, registerSpec));
            if (disposition == Disposition.START) {
                this.regs.put(registerSpec);
                this.endIndices[reg] = -1;
                return;
            }
            this.regs.remove(registerSpec);
            this.endIndices[reg] = this.result.size() - 1;
        }

        private void addOrUpdateEnd(int i, Disposition disposition, RegisterSpec registerSpec) {
            if (disposition == Disposition.START) {
                throw new RuntimeException("shouldn't happen");
            }
            int i2 = this.endIndices[registerSpec.getReg()];
            if (i2 >= 0) {
                Entry entry = this.result.get(i2);
                if (entry.getAddress() == i && entry.getRegisterSpec().equals(registerSpec)) {
                    this.result.set(i2, entry.withDisposition(disposition));
                    this.regs.remove(registerSpec);
                    return;
                }
            }
            endLocal(i, registerSpec, disposition);
        }

        public LocalList finish() {
            aboutToProcess(Integer.MAX_VALUE, 0);
            int size = this.result.size();
            int i = size - this.nullResultCount;
            if (i == 0) {
                return LocalList.EMPTY;
            }
            Entry[] entryArr = new Entry[i];
            if (size == i) {
                this.result.toArray(entryArr);
            } else {
                Iterator<Entry> it = this.result.iterator();
                int i2 = 0;
                while (it.hasNext()) {
                    Entry next = it.next();
                    if (next != null) {
                        entryArr[i2] = next;
                        i2++;
                    }
                }
            }
            Arrays.sort(entryArr);
            LocalList localList = new LocalList(i);
            for (int i3 = 0; i3 < i; i3++) {
                localList.set(i3, entryArr[i3]);
            }
            localList.setImmutable();
            return localList;
        }
    }
}
