package org.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractSaveTaskDto {
    private String name;
    private String description;
}
