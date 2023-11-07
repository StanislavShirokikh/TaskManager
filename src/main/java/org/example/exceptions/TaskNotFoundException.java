package org.example.exceptions;

import java.io.UncheckedIOException;

public class TaskNotFoundException extends IllegalArgumentException {
    public TaskNotFoundException(String s) {
        super(s);
    }
}
