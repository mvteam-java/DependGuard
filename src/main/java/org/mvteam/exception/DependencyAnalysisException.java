package org.mvteam.exception;

public class DependencyAnalysisException extends Exception {
    public DependencyAnalysisException(String message) {
        super(message);
    }

    public DependencyAnalysisException(String message, Throwable cause) {
        super(message, cause);
    }
}
