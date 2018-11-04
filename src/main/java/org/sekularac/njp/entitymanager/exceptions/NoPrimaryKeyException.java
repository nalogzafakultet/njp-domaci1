package org.sekularac.njp.entitymanager.exceptions;

public class NoPrimaryKeyException extends RuntimeException {

    public NoPrimaryKeyException(String exceptionText) {
        super(exceptionText);
    }
}
