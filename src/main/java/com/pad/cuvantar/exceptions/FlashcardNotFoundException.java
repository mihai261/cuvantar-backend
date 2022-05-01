package com.pad.cuvantar.exceptions;

public class FlashcardNotFoundException extends Exception{
    public FlashcardNotFoundException(String message) {
        super(message);
    }

    public FlashcardNotFoundException(String message, Throwable e) {
        super(message, e);
    }
}
