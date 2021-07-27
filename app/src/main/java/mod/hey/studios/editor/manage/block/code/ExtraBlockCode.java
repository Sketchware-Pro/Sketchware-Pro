package mod.hey.studios.editor.manage.block.code;

import com.besome.sketch.beans.BlockBean;

import java.util.ArrayList;

import a.a.a.Fx;
import mod.hey.studios.editor.manage.block.ExtraBlockInfo;
import mod.hey.studios.editor.manage.block.v2.BlockLoader;

/*
 * Copied from:
 * Lmod/agus/jcoderz/editor/manage/block/codeblock/ManageCodeExtraBlock;
 * (For editing/organizing purposes.)
 *
 * 6.3.0
 */
public class ExtraBlockCode {

    public Fx fx;

    public ExtraBlockCode(Fx var1) {
        this.fx = var1;
    }

    public int getBlockType(BlockBean var1, int var2) {
        int var3;
        if (var1.getParamClassInfo().get(var2).b("boolean")) {
            var3 = 0;
        } else if (var1.getParamClassInfo().get(var2).b("double")) {
            var3 = 1;
        } else if (var1.getParamClassInfo().get(var2).b("String")) {
            var3 = 2;
        } else {
            var3 = 3;
        }

        return var3;
    }

    //changed 6.3.0
    public String getCodeExtraBlock(BlockBean var1, String var2) {
        ArrayList<String> var4 = new ArrayList<>();

        for (int var3 = 0; var3 < var1.parameters.size(); ++var3) {
            switch (this.getBlockType(var1, var3)) {
                case 0:
                    if (!var1.parameters.get(var3).isEmpty()) {
                        var4.add(this.fx.a(var1.parameters.get(var3), this.getBlockType(var1, var3), var1.opCode));
                    } else {
                        var4.add("true");
                    }
                    break;
                case 1:
                    if (!var1.parameters.get(var3).isEmpty()) {
                        var4.add(this.fx.a(var1.parameters.get(var3), this.getBlockType(var1, var3), var1.opCode));
                    } else {
                        var4.add("0");
                    }
                    break;
                case 2:
                    if (!var1.parameters.get(var3).isEmpty()) {
                        var4.add(this.fx.a(var1.parameters.get(var3), this.getBlockType(var1, var3), var1.opCode));
                    } else {
                        var4.add("\"\"");
                    }
                    break;
                default:
                    if (!var1.parameters.get(var3).isEmpty()) {
                        var4.add(this.fx.a(var1.parameters.get(var3), this.getBlockType(var1, var3), var1.opCode));
                    } else {
                        var4.add("");
                    }
            }
        }

        if (var1.subStack1 >= 0) {
            var4.add(this.fx.a(String.valueOf(var1.subStack1), var2));
        } else {
            var4.add(" ");
        }

        if (var1.subStack2 >= 0) {
            var4.add(this.fx.a(String.valueOf(var1.subStack2), var2));
        } else {
            var4.add(" ");
        }

        ExtraBlockInfo var5 = BlockLoader.getBlockInfo(var1.opCode);

        //6.3.0
        if (var5.isMissing) {
            var5 = BlockLoader.getBlockFromProject(fx.e.sc_id, var1.opCode);
        }

        String var6;
        if (var4.size() > 0) {
            var6 = String.format(var5.getCode(), var4.toArray(new Object[0]));
        } else if (!var5.getCode().isEmpty()) {
            var6 = var5.getCode();
        } else {
            var6 = "";
        }

        return var6;
    }
}
