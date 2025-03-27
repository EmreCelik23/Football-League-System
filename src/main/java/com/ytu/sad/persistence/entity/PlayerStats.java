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
@Table(name = "player_stats")
public class PlayerStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Column(name = "matches_played")
    private Integer matchesPlayed;
    private Integer goals;
    private Integer assists;
    private Integer yellowCards;
    private Integer redCards;
    @Column(name = "minutes_played")
    private Integer minutesPlayed;

    public Double getGoalsPerMatch() {
        return (minutesPlayed > 0) ? (double) (goals * 90) / matchesPlayed : 0.0;
    }

    public Double getAssistsPerMatch() {
        return (minutesPlayed > 0) ? (double) (assists * 90) / matchesPlayed : 0.0;
    }

    @PrePersist
    protected void onCreate() {
        matchesPlayed = 0;
        goals = 0;
        assists = 0;
        yellowCards = 0;
        redCards = 0;
        minutesPlayed = 0;
    }
}
