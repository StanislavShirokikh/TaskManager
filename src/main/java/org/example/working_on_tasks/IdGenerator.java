package org.example.working_on_tasks;

public class IdGenerator {
    private int sequence = 0;

    public int getSequence() {
        return sequence++;
    }
}
