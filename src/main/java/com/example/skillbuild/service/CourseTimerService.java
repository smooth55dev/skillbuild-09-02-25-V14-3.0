package com.example.skillbuild.service;

import com.example.skillbuild.domain.CourseTimer;
import com.example.skillbuild.repository.CourseTimerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CourseTimerService {

    @Autowired
    private CourseTimerRepository courseTimerRepository;

    // Start Timer
    public CourseTimer startTimer(int userId, String name) {
        CourseTimer timer = new CourseTimer(userId, name, LocalDateTime.now());
        return courseTimerRepository.save(timer);
    }

    // Stop Timer
    public CourseTimer stopTimer(Long timerId) {
        Optional<CourseTimer> timerOpt = courseTimerRepository.findById(timerId);
        if (timerOpt.isPresent()) {
            CourseTimer timer = timerOpt.get();
            timer.setStopTime(LocalDateTime.now());
            return courseTimerRepository.save(timer);
        }
        throw new RuntimeException("Timer not found");
    }

    // Get Timers for a User
    public List<CourseTimer> getTimersForUser(int userId) {
        return courseTimerRepository.findByUserId(userId);
    }
}

