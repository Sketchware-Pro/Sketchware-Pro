package pro.sketchware.ai.generator;

import androidx.annotation.NonNull;

/**
 * Thrown when a model's answer can't be turned into a project. The message describes the problem
 * precisely enough to be sent back to the model, so it can correct its answer.
 */
public class AiSpecException extends Exception {

    public AiSpecException(@NonNull String message) {
        super(message);
    }

    public AiSpecException(@NonNull String message, Throwable cause) {
        super(message, cause);
    }
}
