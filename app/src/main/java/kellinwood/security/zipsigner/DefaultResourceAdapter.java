package kellinwood.security.zipsigner;

/**
 * Default resource adapter.
 */
public class DefaultResourceAdapter implements ResourceAdapter {

    @Override
    public String getString(Item item, Object... args) {

        return switch (item) {
            case INPUT_SAME_AS_OUTPUT_ERROR ->
                    "Input and output files are the same.  Specify a different name for the output.";
            case AUTO_KEY_SELECTION_ERROR -> "Unable to auto-select key for signing " + args[0];
            case LOADING_CERTIFICATE_AND_KEY -> "Loading certificate and private key";
            case PARSING_CENTRAL_DIRECTORY -> "Parsing the input's central directory";
            case GENERATING_MANIFEST -> "Generating manifest";
            case GENERATING_SIGNATURE_FILE -> "Generating signature file";
            case GENERATING_SIGNATURE_BLOCK -> "Generating signature block file";
            case COPYING_ZIP_ENTRY -> String.format("Copying zip entry %d of %d", args[0], args[1]);
            default -> throw new IllegalArgumentException("Unknown item " + item);
        };

    }
}
