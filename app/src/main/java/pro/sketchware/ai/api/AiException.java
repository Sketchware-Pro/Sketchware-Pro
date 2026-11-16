package pro.sketchware.ai.api;

import androidx.annotation.NonNull;

/**
 * Thrown when a request to an AI provider fails. The message is always meant to be shown to the
 * user as-is.
 */
public class AiException extends Exception {

    public static final int NO_STATUS = -1;

    /**
     * The HTTP status code of the failed request, or {@link #NO_STATUS} if no response arrived.
     */
    private final int statusCode;

    public AiException(@NonNull String message) {
        this(message, NO_STATUS, null);
    }

    public AiException(@NonNull String message, Throwable cause) {
        this(message, NO_STATUS, cause);
    }

    public AiException(@NonNull String message, int statusCode, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
