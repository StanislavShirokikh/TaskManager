package org.example.controller.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.example.dto.Status;

@Getter
@Setter
public abstract class AbstractUpdateTaskRequest {
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private Status status;
    private int id;
}
