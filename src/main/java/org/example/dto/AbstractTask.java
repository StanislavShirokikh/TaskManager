package org.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractTask {
    private String name;
    private String description;
    private Integer id;
    private Status status;
}
