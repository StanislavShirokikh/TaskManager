package org.example.controller.requests;

public class CreateSubtaskRequest extends AbstractCreateTaskRequest{
    @Override
    public String toString() {
        return "CreateSubtaskRequest{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                '}';
    }
}
