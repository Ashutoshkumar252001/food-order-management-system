package com.restaurant.exceptions;

public class NoRecordAvailableException extends RuntimeException {
        public NoRecordAvailableException(String message) {
            super(message);
        }
}
