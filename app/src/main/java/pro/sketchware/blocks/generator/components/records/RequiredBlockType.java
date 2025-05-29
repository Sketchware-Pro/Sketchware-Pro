package pro.sketchware.blocks.generator.components.records;

public record RequiredBlockType(String blockType, String blockTypeName) {

    public RequiredBlockType(String blockType) {
        this(blockType, null);
    }

}
