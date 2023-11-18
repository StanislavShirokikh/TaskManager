package org.example.controller.requests;

import lombok.Getter;
import lombok.Setter;
import org.example.dto.Status;

@Getter
@Setter
public abstract class AbstractUpdateTaskRequest {
    private String name;
    private String description;
    private Status status;
    private int id;
}
