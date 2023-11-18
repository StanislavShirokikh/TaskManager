package org.example.controller.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public abstract class AbstractCreateTaskRequest {
    private String name;
    private String description;
}
