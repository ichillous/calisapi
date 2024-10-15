package com.calis100.CalisAPI.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
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

    @Column(name = "log_date", nullable = false)
    private Date logDate;

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
