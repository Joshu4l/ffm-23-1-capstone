package de.groupsethero.backend.exceptions;

public class GeolocationInsertException extends RuntimeException {
    public GeolocationInsertException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
