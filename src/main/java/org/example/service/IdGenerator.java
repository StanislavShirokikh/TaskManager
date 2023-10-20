package org.example.service;

public class IdGenerator {
    private int sequence = 0;

    public int getSequence() {
        return sequence++;
    }
}
