package org.example.exceptions;

public class SubTaskNotFoundException extends IllegalArgumentException{
    public SubTaskNotFoundException(String s) {
        super(s);
    }
}
