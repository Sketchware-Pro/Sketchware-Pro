package mod.hey.studios.project.custom_blocks;

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
import mod.agus.jcoderz.lib.FileUtil;
import com.google.gson.Gson;
import java.io.File;
import android.os.Environment;

public class CustomBlocksManager {
    final String sc_id;
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
import mod.agus.jcoderz.lib.FileUtil;
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
