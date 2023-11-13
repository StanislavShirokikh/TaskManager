package org.example.controller.requests;

public class CreateEpicRequest extends AbstractCreateTaskRequest{
    @Override
    public String toString() {
        return "CreateEpicRequest{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                '}';
    }
}
