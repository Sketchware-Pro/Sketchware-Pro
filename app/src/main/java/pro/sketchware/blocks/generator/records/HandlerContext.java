package pro.sketchware.blocks.generator.records;

import pro.sketchware.blocks.generator.utils.BlockParamUtil;
import pro.sketchware.blocks.generator.resources.BlocksCategories;
import com.besome.sketch.beans.BlockBean;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public record HandlerContext(BlockParamUtil blockParamUtil,
                             BlocksCategories blocksCategories, AtomicInteger idCounter,
                             List<BlockBean> blockBeans, List<String> noNextBlocks) {
}