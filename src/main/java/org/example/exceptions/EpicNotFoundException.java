package org.example.exceptions;

public class EpicNotFoundException extends IllegalArgumentException{
    public EpicNotFoundException(String s) {
        super(s);
    }
}
