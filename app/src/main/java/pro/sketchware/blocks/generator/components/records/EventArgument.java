package pro.sketchware.blocks.generator.components.records;

public record EventArgument(String varName, String blockType, String blockTypeName) {

    public EventArgument(String varName, String blockType) {
        this(varName, blockType, "");
    }

    public String opCode() {
        return "getArg";
    }

}
