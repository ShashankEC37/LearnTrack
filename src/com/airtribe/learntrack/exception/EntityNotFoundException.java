package com.airtribe.learntrack.exception;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String entityName, int id) {
        super(entityName + " with ID " + id + " was not found.");
    }
}
