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
@Table(name = "team_standings")
public class TeamStandings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "team_id", nullable = false, unique = true)
    private Team team;

    @Column(nullable = false)
    private Integer points;

    @Column(name = "matches_played", nullable = false)
    private Integer matchesPlayed;

    @Column(nullable = false)
    private Integer wins;

    @Column(nullable = false)
    private Integer draws;

    @Column(nullable = false)
    private Integer losses;

    @Column(name = "goals_scored", nullable = false)
    private Integer goalsScored;

    @Column(name = "goals_conceded", nullable = false)
    private Integer goalsConceded;

    @Column(name = "goal_difference")
    private Integer goalDifference;

    @PrePersist
    protected void onCreate() {
        points = 0;
        matchesPlayed = 0;
        wins = 0;
        draws = 0;
        losses = 0;
        goalsScored = 0;
        goalsConceded = 0;
        goalDifference = 0;
    }
}