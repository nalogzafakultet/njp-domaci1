package org.sekularac.njp.entitymanager.exceptions;

public class NoJoinColumnException extends RuntimeException {
    public NoJoinColumnException(String exceptionText) {
        super(exceptionText);
    }
}
