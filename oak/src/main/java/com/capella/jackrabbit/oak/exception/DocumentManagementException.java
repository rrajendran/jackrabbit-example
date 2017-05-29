package com.capella.jackrabbit.oak.exception;

/**
 * @author Ramesh Rajendran
 */
public class DocumentManagementException extends RuntimeException {
    public DocumentManagementException(String message, Exception e) {
        super(message, e);
    }
}
