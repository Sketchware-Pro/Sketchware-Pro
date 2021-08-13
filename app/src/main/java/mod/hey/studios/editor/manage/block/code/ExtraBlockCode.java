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
	
	public ExtraBlockCode(Fx mfx) {
		fx = mfx;
	}
	
	public int getBlockType(BlockBean blockBean, int i) {
		int blockType;
		if (blockBean.getParamClassInfo().get(i).b("boolean")) {
			blockType = 0;
		} else if (blockBean.getParamClassInfo().get(i).b("double")) {
			blockType = 1;
		} else if (blockBean.getParamClassInfo().get(i).b("String")) {
			blockType = 2;
		} else {
			blockType = 3;
		}
		
		return blockType;
	}
	
	//changed 6.3.0
	public String getCodeExtraBlock(BlockBean blockBean, String str) {
		try {
			ArrayList<String> arrayList = new ArrayList<>();
			
			for (int i = 0; i < blockBean.parameters.size(); i++) {
				int blockType = getBlockType(blockBean, i);
				
				switch (blockType) {
					case 0:
					if (((String) blockBean.parameters.get(i)).isEmpty()) {
						arrayList.add("true");
					} else {
						arrayList.add(fx.a((String) blockBean.parameters.get(i), getBlockType(blockBean, i), blockBean.opCode));
					}
					break;
					case 1:
					if (((String) blockBean.parameters.get(i)).isEmpty()) {
						arrayList.add("0");
					} else {
						arrayList.add(fx.a((String) blockBean.parameters.get(i), getBlockType(blockBean, i), blockBean.opCode));
					}
					break;
					case 2:
					if (((String) blockBean.parameters.get(i)).isEmpty()) {
						arrayList.add("\"\"");
					} else {
						arrayList.add(fx.a((String) blockBean.parameters.get(i), getBlockType(blockBean, i), blockBean.opCode));
					}
					break;
					default:
					if (((String) blockBean.parameters.get(i)).isEmpty()) {
						arrayList.add("");
					} else {
						arrayList.add(fx.a((String) blockBean.parameters.get(i), getBlockType(blockBean, i), blockBean.opCode));
					}
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
			
			//6.3.0
			if (blockInfo.isMissing) {
				blockInfo = BlockLoader.getBlockFromProject(fx.e.sc_id, blockBean.opCode);
			}
			
			String code = blockInfo.getCode();
			for (int i = 0; i < arrayList.size(); i++) {
				String parameter = (String) arrayList.get(i);
				code = code.replace("%".concat(String.valueOf(i + 1).concat("$s")), parameter);
				code = code.replaceFirst("%s", parameter);
			}
			return code;
		} catch (Exception error) {
			StringBuilder sb = new StringBuilder("/* Error: ");
			sb.append(error.toString());
			sb.append("*/");
			return sb.toString();
		}
	}
}
