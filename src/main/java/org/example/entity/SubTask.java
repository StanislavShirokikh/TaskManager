package org.example.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.entity.AbstractTask;

@Getter
@Setter
public class SubTask extends AbstractTask {
    private Integer epicId;

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
