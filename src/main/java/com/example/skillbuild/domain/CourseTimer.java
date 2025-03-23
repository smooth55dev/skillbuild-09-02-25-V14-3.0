package com.example.skillbuild.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "course_timers")
public class CourseTimer {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
//    @Column(columnDefinition = "integer default 0")
    private int userId;

    @Column(nullable = false)
    private String name;

    private LocalDateTime startTime;
    private LocalDateTime stopTime;

    // Constructors
    public CourseTimer() {}

    public CourseTimer(int userId, String name, LocalDateTime startTime) {
        this.userId = userId;
        this.name = name;
        this.startTime = startTime;
    }

}

