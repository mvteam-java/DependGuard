package org.mvteam.exception;

public class ReportGenerationException extends Exception {
    public ReportGenerationException(String message) {
        super(message);
    }

    public ReportGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
