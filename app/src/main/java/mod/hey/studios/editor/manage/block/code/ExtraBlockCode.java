package mod.hey.studios.editor.manage.block.code;

import a.a.a.Fx;
import a.a.a.Gx;
import com.besome.sketch.beans.BlockBean;
import java.util.ArrayList;
import mod.hey.studios.editor.manage.block.ExtraBlockInfo;
import mod.hey.studios.editor.manage.block.v2.BlockLoader;
import mod.hey.studios.lib.code_editor.ColorScheme;

public class ExtraBlockCode {
    public Fx fx;

    public ExtraBlockCode(Fx fx2) {
        this.fx = fx2;
    }

    public int getBlockType(BlockBean blockBean, int i) {
        int i2;
        if (((Gx) blockBean.getParamClassInfo().get(i)).b("boolean")) {
            i2 = 0;
        } else if (((Gx) blockBean.getParamClassInfo().get(i)).b("double")) {
            i2 = 1;
        } else if (((Gx) blockBean.getParamClassInfo().get(i)).b("String")) {
            i2 = 2;
        } else {
            i2 = 3;
        }
        return i2;
    }

    public String getCodeExtraBlock(BlockBean blockBean, String str) {
        String str2;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < blockBean.parameters.size(); i++) {
            switch (getBlockType(blockBean, i)) {
                case 0:
                    if (!((String) blockBean.parameters.get(i)).toString().isEmpty()) {
                        arrayList.add(this.fx.a(((String) blockBean.parameters.get(i)).toString(), getBlockType(blockBean, i), blockBean.opCode));
                        break;
                    } else {
                        arrayList.add("true");
                        break;
                    }
                case 1:
                    if (!((String) blockBean.parameters.get(i)).toString().isEmpty()) {
                        arrayList.add(this.fx.a(((String) blockBean.parameters.get(i)).toString(), getBlockType(blockBean, i), blockBean.opCode));
                        break;
                    } else {
                        arrayList.add("0");
                        break;
                    }
                case ColorScheme.XML /*{ENCODED_INT: 2}*/:
                    if (!((String) blockBean.parameters.get(i)).toString().isEmpty()) {
                        arrayList.add(this.fx.a(((String) blockBean.parameters.get(i)).toString(), getBlockType(blockBean, i), blockBean.opCode));
                        break;
                    } else {
                        arrayList.add("\"\"");
                        break;
                    }
                default:
                    if (!((String) blockBean.parameters.get(i)).toString().isEmpty()) {
                        arrayList.add(this.fx.a(((String) blockBean.parameters.get(i)).toString(), getBlockType(blockBean, i), blockBean.opCode));
                        break;
                    } else {
                        arrayList.add("");
                        break;
                    }
            }
        }
        if (blockBean.subStack1 >= 0) {
            arrayList.add(this.fx.a(String.valueOf(blockBean.subStack1), str));
        } else {
            arrayList.add(" ");
        }
        if (blockBean.subStack2 >= 0) {
            arrayList.add(this.fx.a(String.valueOf(blockBean.subStack2), str));
        } else {
            arrayList.add(" ");
        }
        ExtraBlockInfo blockInfo = BlockLoader.getBlockInfo(blockBean.opCode);
        if (blockInfo.isMissing) {
            blockInfo = BlockLoader.getBlockFromProject(this.fx.e.sc_id, blockBean.opCode);
        }
        if (arrayList.size() > 0) {
            str2 = String.format(blockInfo.getCode(), (String[]) arrayList.toArray(new String[arrayList.size()]));
        } else if (!blockInfo.getCode().isEmpty()) {
            str2 = blockInfo.getCode();
        } else {
            str2 = "";
        }
        return str2;
    }
}
