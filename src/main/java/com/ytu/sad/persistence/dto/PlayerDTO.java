package com.ytu.sad.persistence.dto;

import com.ytu.sad.persistence.enums.PlayerRoleEnum;
import com.ytu.sad.persistence.enums.PositionEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDTO {
    private Integer id;
    @NotNull(message = "Player Name cannot be null")
    private String name;
    private Integer age;
    @NotNull(message = "Player Position cannot be null")
    private PositionEnum position;
    @NotNull(message = "Player Role cannot be null")
    private PlayerRoleEnum role;
    private String nationality;
    @NotNull(message = "Team ID cannot be null")
    private Integer teamId;
    private String teamName;
    private Integer number;
    @Min(value = 0, message = "Career goals count cannot be negative")
    private Integer goals;
    @Min(value = 0, message = "Career assists count cannot be negative")
    private Integer assists;
    @Min(value = 0, message = "Career yellow cards count cannot be negative")
    private Integer yellowCards;
    @Min(value = 0, message = "Career red cards count cannot be negative")
    private Integer redCards;
}
