package pro.sketchware.blocks.generator.resources;

import java.util.ArrayList;
import java.util.HashMap;

import pro.sketchware.blocks.generator.utils.TranslatorUtils;

public class BlocksCategories {

    private final ArrayList<HashMap<String, Object>> stringBlocks = new ArrayList<>();
    private final ArrayList<HashMap<String, Object>> booleanBlocks = new ArrayList<>();
    private final ArrayList<HashMap<String, Object>> doubleBlocks = new ArrayList<>();
    private final ArrayList<HashMap<String, Object>> regularBlocks = new ArrayList<>();

    public void loadBlocks(ArrayList<HashMap<String, Object>> blocks) {
        for (HashMap<String, Object> block : blocks) {
            String type = TranslatorUtils.safeGetString(block.get("type"));
            switch (type) {
                case "s" -> stringBlocks.add(block);
                case "b" -> booleanBlocks.add(block);
                case "d" -> doubleBlocks.add(block);
                case " ", "c", "e", "f" -> regularBlocks.add(block);
            }
        }
    }

    public ArrayList<HashMap<String, Object>> getStringBlocks() {
        return stringBlocks;
    }

    public ArrayList<HashMap<String, Object>> getBooleanBlocks() {
        return booleanBlocks;
    }

    public ArrayList<HashMap<String, Object>> getDoubleBlocks() {
        return doubleBlocks;
    }

    public ArrayList<HashMap<String, Object>> getRegularBlocks() {
        return regularBlocks;
    }

}
