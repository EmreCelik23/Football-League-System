package com.ytu.sad.persistence.entity;

import com.ytu.sad.persistence.enums.MatchStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "match")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "home_team_id", nullable = false)
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team_id", nullable = false)
    private Team awayTeam;

    private String referee;

    @Column(nullable = false)
    private LocalDateTime date;

    private String stadium;

    @Column(name = "home_score", columnDefinition = "INT DEFAULT 0")
    private Integer homeScore = 0;

    @Column(name = "away_score", columnDefinition = "INT DEFAULT 0")
    private Integer awayScore = 0;

    @Enumerated(EnumType.STRING)
    private MatchStatusEnum status;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @OrderBy("minute ASC")
    private List<GoalEvent> goals;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @OrderBy("minute ASC")
    private List<CardEvent> yellowCards;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @OrderBy("minute ASC")
    private List<CardEvent> redCards;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @OrderBy("minute ASC")
    private List<SubstitutionEvent> substitutions;

    @PrePersist
    public void prePersist() {
        if (homeScore == null) homeScore = 0;
        if (awayScore == null) awayScore = 0;
        if (goals == null) goals = new ArrayList<>();
        if (yellowCards == null) yellowCards = new ArrayList<>();
        if (redCards == null) redCards = new ArrayList<>();
        if (substitutions == null) substitutions = new ArrayList<>();
    }
}
