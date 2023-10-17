package org.example;

public class GeneratorId {
    private int taskId = 0;
    private void increaseId() {
        ++taskId;
    }
    public int getTaskId() {
        increaseId();
        return taskId;
    }
}
