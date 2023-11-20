package org.example.dao.exceptions;

public class EpicNotFoundException extends IllegalArgumentException{
    public EpicNotFoundException(String s) {
        super(s);
    }
}
