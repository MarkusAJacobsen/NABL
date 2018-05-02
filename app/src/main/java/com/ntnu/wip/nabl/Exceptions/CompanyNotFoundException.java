package com.ntnu.wip.nabl.Exceptions;

/**
 * Exception used in the cause of a needed Company, but not present
 */
public class CompanyNotFoundException extends Exception {
    private String message;
    private Throwable cause;

    /**
     * Empty default constructor
     */
    public CompanyNotFoundException(){
        super();
    }

    /**
     * Constructor which allows to specify own message and cause
     * @param message String
     * @param cause Throwable
     */
    public CompanyNotFoundException(String message, Throwable cause) {
        super(message, cause);

        this.message = message;
        this.cause = cause;
    }

    /**
     * Getter for message
     * @return String
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Getter for cause
     * @return Throwable
     */
    @Override
    public Throwable getCause() {
        return cause;
    }
}
