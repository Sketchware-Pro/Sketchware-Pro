package pro.sketchware.blocks.generator.resources;

import static mod.hey.studios.moreblock.ReturnMoreblockManager.getMbType;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import a.a.a.jC;
import pro.sketchware.blocks.generator.records.RequiredMoreBlockType;

public class MoreBlockDataLoader {

    private final String sc_id;

    public MoreBlockDataLoader(String sc_id) {
        this.sc_id = sc_id;
    }

    public ArrayList<HashMap<String, Object>> getMoreBlocks(String javaName) {
        ArrayList<HashMap<String, Object>> moreBlocks = new ArrayList<>();

        for (Pair<String, String> moreBlock : jC.a(sc_id).i(javaName)) {
            moreBlocks.add(getBlockHashMap(moreBlock.first, moreBlock.second));
        }

        return moreBlocks;
    }

    public HashMap<String, Object> getBlockHashMap(String blockName, String spec) {
        RequiredMoreBlockType requiredMoreBlockType = getMoreBlockType(blockName);
        Pair<ArrayList<String>, ArrayList<String>> blockParamsHolders = getMoreBlockParamsHolders(spec);
        String moreBlockMethodName = getMoreBlockMethodName(blockName);
        String moreBlockSpec = moreBlockMethodName;

        String blockCode = String.format("_%s(%s)",
                moreBlockMethodName,
                String.join(", ", blockParamsHolders.second)
        );
        int staring = spec.indexOf("]");
        int ending = blockParamsHolders.first.isEmpty() ? spec.length() : spec.indexOf(blockParamsHolders.first.get(0));
        if (staring != -1 && ending != -1) {
            moreBlockSpec += spec.substring(staring + 1, ending);
        }

        if (requiredMoreBlockType.type().equals(" ")) {
            blockCode += ";";
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("moreBlockName", "_" + moreBlockMethodName);
        hashMap.put("name", "definedFunc");
        hashMap.put("type", requiredMoreBlockType.type());
        hashMap.put("typeName", requiredMoreBlockType.typeName());
        hashMap.put("code", blockCode);
        hashMap.put("spec", moreBlockSpec.trim() + String.join(" ", blockParamsHolders.first));
        return hashMap;
    }

    public Pair<ArrayList<String>, ArrayList<String>> getMoreBlockParamsHolders(String spec) {
        ArrayList<String> params = new ArrayList<>(Arrays.asList(spec.split(" ")));
        ArrayList<String> fullParam = new ArrayList<>();
        ArrayList<String> subParam = new ArrayList<>();

        for (int i = 1; i < params.size(); i++) {
            String param = params.get(i);
            int lastDot = param.lastIndexOf('.');
            if (lastDot == -1 || !param.startsWith("%")) {
                continue;
            }
            fullParam.add(param);
            subParam.add(param.substring(0, lastDot).replaceAll("%m\\.(\\w+)", "%s"));
        }

        return new Pair<>(fullParam, subParam);
    }

    public String getMoreBlockMethodName(String blockName) {
        int staring = blockName.indexOf("[");
        if (staring == -1) {
            return blockName;
        } else {
            return blockName.substring(0, staring);
        }
    }

    public RequiredMoreBlockType getMoreBlockType(String blockName) {
        String moreBlockType = getMbType(blockName);
        return switch (moreBlockType) {
            case "void" -> new RequiredMoreBlockType(" ", "");
            case "String" -> new RequiredMoreBlockType("s", "");
            case "double" -> new RequiredMoreBlockType("d", "");
            case "boolean" -> new RequiredMoreBlockType("b", "");
            default -> new RequiredMoreBlockType("v", moreBlockType);
        };
    }

}
