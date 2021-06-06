package mod.hey.studios.editor.manage.block.code;

import com.besome.sketch.beans.BlockBean;

import java.util.ArrayList;

import a.a.a.Fx;
import mod.hey.studios.editor.manage.block.ExtraBlockInfo;
import mod.hey.studios.editor.manage.block.v2.BlockLoader;

public class ExtraBlockCode {

    public Fx fx;

    public ExtraBlockCode(Fx fx) {
        this.fx = fx;
    }

    public int getBlockType(BlockBean blockBean, int i) {
        int i2;
        if (blockBean.getParamClassInfo().get(i).b("boolean")) {
            i2 = 0;
        } else if (blockBean.getParamClassInfo().get(i).b("double")) {
            i2 = 1;
        } else if (blockBean.getParamClassInfo().get(i).b("String")) {
            i2 = 2;
        } else {
            i2 = 3;
        }
        return i2;
    }

    public String getCodeExtraBlock(BlockBean blockBean, String str) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < blockBean.parameters.size(); i++) {
            switch (getBlockType(blockBean, i)) {
                case 0:
                    if (!blockBean.parameters.get(i).isEmpty()) {
                        arrayList.add(fx.a(blockBean.parameters.get(i), getBlockType(blockBean, i), blockBean.opCode));
                    } else {
                        arrayList.add("true");
                    }

                    break;

                case 1:
                    if (!blockBean.parameters.get(i).isEmpty()) {
                        arrayList.add(fx.a(blockBean.parameters.get(i), getBlockType(blockBean, i), blockBean.opCode));
                    } else {
                        arrayList.add("0");
                    }

                    break;

                case 2:
                    if (!blockBean.parameters.get(i).isEmpty()) {
                        arrayList.add(fx.a(blockBean.parameters.get(i), getBlockType(blockBean, i), blockBean.opCode));
                    } else {
                        arrayList.add("\"\"");
                    }

                    break;

                default:
                    if (!blockBean.parameters.get(i).isEmpty()) {
                        arrayList.add(fx.a(blockBean.parameters.get(i), getBlockType(blockBean, i), blockBean.opCode));
                    } else {
                        arrayList.add("");
                    }

                    break;
            }
        }

        if (blockBean.subStack1 >= 0) {
            arrayList.add(fx.a(String.valueOf(blockBean.subStack1), str));
        } else {
            arrayList.add(" ");
        }

        if (blockBean.subStack2 >= 0) {
            arrayList.add(fx.a(String.valueOf(blockBean.subStack2), str));
        } else {
            arrayList.add(" ");
        }

        ExtraBlockInfo blockInfo = BlockLoader.getBlockInfo(blockBean.opCode);

        if (blockInfo.isMissing) {
            blockInfo = BlockLoader.getBlockFromProject(fx.e.sc_id, blockBean.opCode);
        }

        if (arrayList.size() > 0) {
            return String.format(blockInfo.getCode(), arrayList.toArray(new Object[0]));
        } else if (!blockInfo.getCode().isEmpty()) {
            return blockInfo.getCode();
        } else {
            return "";
        }
    }
}
