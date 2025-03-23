package com.example.skillbuild.controller;

import com.example.skillbuild.domain.CourseTimer;
import com.example.skillbuild.service.CourseTimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timers")
public class CourseTimerController {

    private final CourseTimerService courseTimerService;

    public CourseTimerController(CourseTimerService courseTimerService) {
        this.courseTimerService = courseTimerService;
    }

    // Start a Timer
    @GetMapping("/start")
    public CourseTimer startTimer(@RequestParam int userId, @RequestParam String name) {
        CourseTimer timer = courseTimerService.startTimer(userId, name);
        return ResponseEntity.ok(timer).getBody();
    }

    // Stop a Timer
    @PostMapping("/stop") //maybe GetMapping
    public CourseTimer stopTimer(@RequestParam Long timerId) {
        return courseTimerService.stopTimer(timerId);
    }

    // Get Timers for a User
    @GetMapping("/{userId}")
    public List<CourseTimer> getTimersForUser(@PathVariable int userId) {
        return courseTimerService.getTimersForUser(userId);
    }
}
