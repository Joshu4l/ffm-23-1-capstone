package de.groupsethero.backend.exceptions;

public class GeolocationRetrievalException extends RuntimeException {
    public GeolocationRetrievalException(String exceptionMessage) {
        super(exceptionMessage);
    }
}