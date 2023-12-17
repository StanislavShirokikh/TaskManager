package org.example.controller.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractUpdateTaskRequest {
    @NotBlank
    private String name;
    private String description;
    private int id;
}
