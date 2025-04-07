package mod.hey.studios.project.custom_blocks;

import android.os.Environment;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import a.a.a.eC;
import a.a.a.hC;
import a.a.a.jC;
import a.a.a.kq;
import mod.hey.studios.editor.manage.block.ExtraBlockInfo;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class CustomBlocksManager {
    final String sc_id;
    ArrayList<BlockBean> blocks;
    ArrayList<ExtraBlockInfo> custom_blocks;

    public CustomBlocksManager(String sc_id) {
        this.sc_id = sc_id;

        load();
    }

    public ArrayList<BlockBean> getUsedBlocks() {
        return blocks;
    }

    public ExtraBlockInfo getExtraBlockInfo(String name) {
        return getExtraBlockInfoByName(name).orElse(null);
    }

    public boolean contains(String name) {
        return getExtraBlockInfoByName(name).isPresent();
    }

    private Optional<ExtraBlockInfo> getExtraBlockInfoByName(String name) {
        if (custom_blocks != null && !custom_blocks.isEmpty()) {
            for (ExtraBlockInfo info : custom_blocks) {
                if (info.getName().equals(name)) {
                    return Optional.of(info);
                }
            }
        }
        return Optional.empty();
    }

    private void load() {
        blocks = new ArrayList<>();

        ArrayList<String> usedBlocks = new ArrayList<>();

        hC hc = jC.b(sc_id);
        eC ec = jC.a(sc_id);

        for (ProjectFileBean bean : hc.b()) {
            for (Map.Entry<String, ArrayList<BlockBean>> entry : ec.b(bean.getJavaName()).entrySet()) {
                for (BlockBean block : entry.getValue()) {
                    if (!(block.opCode.equals("definedFunc")
                            || block.opCode.equals("getVar")
                            || block.opCode.equals("getArg"))) {
                        if (kq.a(block.opCode, block.type) == -7711273) {
                            if (!usedBlocks.contains(block.opCode)) {
                                usedBlocks.add(block.opCode);

                                blocks.add(block);
                            }
                        }
                    }
                }
            }
        }

        File customBlocksConfig = new File(Environment.getExternalStorageDirectory(),
                ".sketchware/data/" + sc_id + "/custom_blocks");
        if (customBlocksConfig.exists()) {
            try {
                custom_blocks = new Gson().fromJson(
                        FileUtil.readFile(customBlocksConfig.getAbsolutePath()),
                        new TypeToken<ArrayList<ExtraBlockInfo>>() {
                        }.getType());
            } catch (Exception e) {
                SketchwareUtil.toastError("Failed to get Custom Blocks : " + e.getMessage());
            }
        }

    }

    public String getCustomBlockCode(String opCode) {
        try {
            for (ExtraBlockInfo info : custom_blocks) {
                if (info.getName().equals(opCode)) {
                    return info.getCode();
                }
            }
        } catch (Exception ignored) {
        }
        return "";
    }

    public String getCustomBlockSpec2(String opCode) {
        try {
            for (ExtraBlockInfo info : custom_blocks) {
                if (info.getName().equals(opCode)) {
                    return info.getSpec2();
                }
            }
        } catch (Exception ignored) {
        }
        return "";
    }


    /*public String getCustomBlocksJsonPath() {
        return new File(
            Environment.getExternalStorageDirectory(),
            ".sketchware/data/" + sc_id + "/custom_blocks")
            .getAbsolutePath();
    }

    public void writeCustomBlocksJson() {
        ArrayList<ExtraBlockInfo> blockss = new ArrayList<>();

        for (BlockBean bean : getUsedBlocks()) {
            blockss.add(BlockLoader.getBlockInfo(bean.opCode));
        }

        if (blockss.size() != 0) {
            FileUtil.writeFile(getCustomBlocksJsonPath(), new Gson().toJson(blockss));
        }
    }*/
}


/*package mod.hey.studios.project.custom_blocks;

import java.util.ArrayList;
import com.besome.sketch.beans.ProjectFileBean;
import java.util.Map;
import com.besome.sketch.beans.BlockBean;
import a.a.a.kq;
import a.a.a.hC;
import a.a.a.eC;
import a.a.a.jC;
import mod.hey.studios.editor.manage.block.v2.BlockLoader;
import mod.hey.studios.editor.manage.block.ExtraBlockInfo;
import pro.sketchware.utility.FileUtil;
import com.google.gson.Gson;
import java.io.File;
import android.os.Environment;

public class CustomBlocksManager {
    String sc_id;
    ArrayList<BlockBean> blocks;

    public CustomBlocksManager(String sc_id) {
        this.sc_id = sc_id;

        load();
    }

    public ArrayList<BlockBean> getUsedBlocks() {
        return blocks;
    }

    private void load() {

        blocks = new ArrayList<>();

        ArrayList<String> usedBlocks = new ArrayList<>();

        hC hc = jC.b(sc_id);
        eC ec = jC.a(sc_id);

        for (ProjectFileBean bean : hc.b())
        {

            for (Map.Entry<String, ArrayList<BlockBean>> entry : ec.b(bean.getJavaName()).entrySet())
            {

                for (BlockBean block : entry.getValue())
                {

                    if (!(block.opCode.equals("definedFunc") || block.opCode.equals("getVar") || block.opCode.equals("getArg")))
                    {
                        if (kq.a(block.opCode, block.type) == -7711273)
                        {
                            if (!usedBlocks.contains(block.opCode))
                            {
                                usedBlocks.add(block.opCode);

                                blocks.add(block);
                            }
                        }
                    }

                }

            }

        }
    }
    
    
    public String getCustomBlocksJsonPath() {
        return new File(Environment.getExternalStorageDirectory(), ".sketchware/data/"+sc_id+"/custom_blocks").getAbsolutePath();
    }
    
    
    public void writeCustomBlocksJson() {
        ArrayList<ExtraBlockInfo> blockss = new ArrayList<>();

        for(BlockBean bean : getUsedBlocks()) {
            blockss.add(BlockLoader.getBlockInfo(bean.opCode));
        }
        
        if(blockss.size() != 0) {
            FileUtil.writeFile(getCustomBlocksJsonPath(), new Gson().toJson(blockss));
        }
    }
    
    
}
*/
