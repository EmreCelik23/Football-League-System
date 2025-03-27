package com.ytu.sad.persistence.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamDTO {
    private Integer id;
    @NotNull(message = "Team Name cannot be null")
    private String name;
    private String city;
    private String stadium;
    @Min(value = 1500, message = "Founded year must be a 4-digit year (between 1500 and 2100)")
    @Max(value = 2100, message = "Founded year must be a 4-digit year (between 1500 and 2100)")
    private Integer foundedYear;
    private String owner;
    private String manager;
    private Integer squadSize;
}
