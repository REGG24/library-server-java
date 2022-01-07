package com.regg.library.model;

public class RecordNotFoundException extends java.lang.Exception {

    public RecordNotFoundException(Long id, String type) {
        super("The " + type + " with id " + id+ " was not found");
    }

}