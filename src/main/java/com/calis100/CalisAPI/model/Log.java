package com.calis100.CalisAPI.model;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Log")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "challenge_id", nullable = false)
    private Challenge challenge;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updateAt = LocalDateTime.now();

    @Column(name = "last_update")
    private LocalDateTime lastUpdate = updateAt;

    @Column(name = "pushups")
    private int pushups;

    @Column(name = "situps")
    private int situps;

    @Column(name = "squats")
    private int squats;

    @Column(name = "pullups")
    private int pullups;

    @Column(name = "plank_minutes")
    private int plankMinutes;

    @Column(name = "run_miles")
    private int runMiles;

}
