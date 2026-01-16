package org.teamscore.individualTask.exceptions;

public class CannotDeleteEntityException extends RuntimeException {
    public CannotDeleteEntityException(String message) {
        super(message);
    }
}
