package org.example.controller.requests;

public class CreateTaskRequest extends AbstractCreateTaskRequest {

    @Override
    public String toString() {
        return "CreateTaskRequest{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                '}';
    }
}
