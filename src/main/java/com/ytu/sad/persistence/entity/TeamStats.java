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
@Table(name = "team_stats")
public class TeamStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "team_id", nullable = false, unique = true)
    private Team team;

    @Column(name = "matches_played", nullable = false)
    private Integer matchesPlayed;

    @Column(name = "points", nullable = false)
    private Integer points;

    @Column(name = "goals_scored", nullable = false)
    private Integer goalsScored;

    @Column(name = "assists", nullable = false)
    private Integer assists;

    @Column(name = "yellow_cards", nullable = false)
    private Integer yellowCards;

    @Column(name = "red_cards", nullable = false)
    private Integer redCards;

    @Column(name = "goals_conceded", nullable = false)
    private Integer goalsConceded;

    @Column(name = "clean_sheets", nullable = false)
    private Integer cleanSheets;

    @Transient
    public Double getPointsPerMatch() {
        return (matchesPlayed > 0) ? (double) points / matchesPlayed : 0.0;
    }

    @Transient
    public Double getGoalsPerMatch() {
        return (matchesPlayed > 0) ? (double) goalsScored / matchesPlayed : 0.0;
    }

    @Transient
    public Double getAssistsPerMatch() {
        return (matchesPlayed > 0) ? (double) assists / matchesPlayed : 0.0;
    }

    @Transient
    public Double getGoalsConcededPerMatch() {
        return (matchesPlayed > 0) ? (double) goalsConceded / matchesPlayed : 0.0;
    }

    @Transient
    public Double getYellowCardsPerMatch() {
        return (matchesPlayed > 0) ? (double) yellowCards / matchesPlayed : 0.0;
    }

    @Transient
    public Double getRedCardsPerMatch() {
        return (matchesPlayed > 0) ? (double) redCards / matchesPlayed : 0.0;
    }

    @PrePersist
    protected void onCreate() {
        points = 0;
        matchesPlayed = 0;
        goalsScored = 0;
        assists = 0;
        yellowCards = 0;
        redCards = 0;
        goalsConceded = 0;
        cleanSheets = 0;
    }
}