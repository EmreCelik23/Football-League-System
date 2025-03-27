package com.ytu.sad.persistence.entity;

import com.ytu.sad.persistence.dto.PlayerDTO;
import com.ytu.sad.persistence.enums.PlayerRoleEnum;
import com.ytu.sad.persistence.mapper.PlayerMapper;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"homeMatches", "awayMatches", "players"})
@EqualsAndHashCode(exclude = {"homeMatches", "awayMatches", "players"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    private String city;

    private String stadium;

    @Column(name = "founded_year")
    private Integer foundedYear;

    private String owner;

    private String manager;

    @Column(name = "squad_size")
    private Integer squadSize;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Player> players;

    @OneToMany(mappedBy = "homeTeam", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("date ASC")
    private List<Match> homeMatches;

    @OneToMany(mappedBy = "awayTeam", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("date ASC")
    private List<Match> awayMatches;

    @PrePersist
    public void prePersist() {
        if (players == null) players = new ArrayList<>();
        if (homeMatches == null) homeMatches = new ArrayList<>();
        if (awayMatches == null) awayMatches = new ArrayList<>();
    }

    @Transient
    public List<Match> getAllMatches() {
        List<Match> allMatches = new ArrayList<>();
        allMatches.addAll(homeMatches);
        allMatches.addAll(awayMatches);
        allMatches.sort(Comparator.comparing(Match::getDate));
        return allMatches;
    }

    public List<Player> getStartingPlayers() {
        return players.stream()
                .filter(player -> player.getRole() == PlayerRoleEnum.STARTING)
                .toList();
    }

    public List<Player> getReservePlayers() {
        return players.stream()
                .filter(player -> player.getRole() == PlayerRoleEnum.RESERVE)
                .toList();
    }
}
