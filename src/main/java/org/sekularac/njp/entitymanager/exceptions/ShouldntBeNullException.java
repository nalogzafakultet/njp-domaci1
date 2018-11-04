package org.sekularac.njp.entitymanager.exceptions;

public class ShouldntBeNullException extends RuntimeException {
    public ShouldntBeNullException(String exceptionText) {
        super(exceptionText);
    }
}
