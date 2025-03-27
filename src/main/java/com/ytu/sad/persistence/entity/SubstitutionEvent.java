package com.ytu.sad.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "substitution_event")
public class SubstitutionEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne
    @JoinColumn(name = "out_player_id", nullable = false)
    private Player outPlayer;

    @ManyToOne
    @JoinColumn(name = "in_player_id", nullable = false)
    private Player inPlayer;

    @Column(nullable = false)
    private Integer minute;

    @PrePersist
    protected void validateSubstitution() {
        if (minute < 1 || minute > 90) {
            throw new IllegalArgumentException("Invalid substitution minute: " + minute);
        }
    }
}