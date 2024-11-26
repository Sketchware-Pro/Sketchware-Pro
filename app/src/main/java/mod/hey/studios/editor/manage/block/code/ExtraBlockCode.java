package mod.hey.studios.editor.manage.block.code;

import com.besome.sketch.beans.BlockBean;

import java.util.ArrayList;

import a.a.a.Fx;
import a.a.a.Gx;
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

    public ExtraBlockCode(Fx fxVar) {
        fx = fxVar;
    }

    public int getBlockType(BlockBean blockBean, int parameterIndex) {
        int blockType;

        Gx paramClassInfo = blockBean.getParamClassInfo().get(parameterIndex);

        if (paramClassInfo.b("boolean")) {
            blockType = 0;
        } else if (paramClassInfo.b("double")) {
            blockType = 1;
        } else if (paramClassInfo.b("String")) {
            blockType = 2;
        } else {
            blockType = 3;
        }

        return blockType;
    }

    public String getCodeExtraBlock(BlockBean blockBean, String var2) {
        ArrayList<String> parameters = new ArrayList<>();

        for (int i = 0; i < blockBean.parameters.size(); i++) {
            String parameterValue = blockBean.parameters.get(i);

            switch (getBlockType(blockBean, i)) {
                case 0:
                    if (parameterValue.isEmpty()) {
                        parameters.add("true");
                    } else {
                        parameters.add(fx.a(parameterValue, getBlockType(blockBean, i), blockBean.opCode));
                    }
                    break;

                case 1:
                    if (parameterValue.isEmpty()) {
                        parameters.add("0");
                    } else {
                        parameters.add(fx.a(parameterValue, getBlockType(blockBean, i), blockBean.opCode));
                    }
                    break;

                case 2:
                    if (parameterValue.isEmpty()) {
                        parameters.add("\"\"");
                    } else {
                        parameters.add(fx.a(parameterValue, getBlockType(blockBean, i), blockBean.opCode));
                    }
                    break;

                default:
                    if (parameterValue.isEmpty()) {
                        parameters.add("");
                    } else {
                        parameters.add(fx.a(parameterValue, getBlockType(blockBean, i), blockBean.opCode));
                    }
            }
        }

        if (blockBean.subStack1 >= 0) {
            parameters.add(fx.a(String.valueOf(blockBean.subStack1), var2));
        } else {
            parameters.add(" ");
        }

        if (blockBean.subStack2 >= 0) {
            parameters.add(fx.a(String.valueOf(blockBean.subStack2), var2));
        } else {
            parameters.add(" ");
        }

        ExtraBlockInfo blockInfo = BlockLoader.getBlockInfo(blockBean.opCode);

        if (blockInfo.isMissing) {
            blockInfo = BlockLoader.getBlockFromProject(fx.buildConfig.sc_id, blockBean.opCode);
        }

        String formattedCode;
        if (!parameters.isEmpty()) {
            try {
                formattedCode = String.format(blockInfo.getCode(), parameters.toArray(new Object[0]));
            } catch (Exception e) {
                formattedCode = "/* Failed to resolve Custom Block's code: " + e + " */";
            }
        } else {
            formattedCode = blockInfo.getCode();
        }

        return formattedCode;
    }
}
