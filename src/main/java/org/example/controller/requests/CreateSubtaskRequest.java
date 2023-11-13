package org.example.controller.requests;

public class CreateSubtaskRequest extends AbstractCreateTaskRequest{
    private Integer epicId;

    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "CreateSubtaskRequest{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                '}';
    }
}
