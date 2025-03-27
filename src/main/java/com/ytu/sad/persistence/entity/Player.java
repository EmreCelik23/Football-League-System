package com.ytu.sad.persistence.entity;

import com.ytu.sad.persistence.enums.PlayerRoleEnum;
import com.ytu.sad.persistence.enums.PositionEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString(exclude = "team")
@EqualsAndHashCode(exclude = "team")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PositionEnum position;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlayerRoleEnum role;

    private String nationality;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    private Integer number;

    private Integer goals;

    private Integer assists;

    private Integer yellowCards;

    private Integer redCards;

    @PrePersist
    protected void onCreate() {
        if (goals == null) goals = 0;
        if (assists == null) assists = 0;
        if (yellowCards == null) yellowCards = 0;
        if (redCards == null) redCards = 0;
    }

}