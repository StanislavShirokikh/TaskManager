package org.example.dto;

public class SubTask extends AbstractTask {
    private Integer epicId;

    public Integer getEpicId() {
        return epicId;
    }
    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }
    @Override
    public String toString() {
        return "SubTask{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                ", epicId=" + getEpicId() +
                "'}'";
    }
}
