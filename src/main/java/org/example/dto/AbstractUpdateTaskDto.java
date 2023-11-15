package org.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractUpdateTaskDto {
    private String name;
    private String description;
    private Status status;
    private int id;

}
