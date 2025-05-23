package pro.sketchware.blocks.generator.records;

public record RequiredBlockType(String blockType, String blockTypeName) {

    public RequiredBlockType(String blockType) {
        this(blockType, null);
    }

}
