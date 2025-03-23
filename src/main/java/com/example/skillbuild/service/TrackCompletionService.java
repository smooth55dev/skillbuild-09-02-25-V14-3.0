package com.example.skillbuild.service;

import com.example.skillbuild.domain.CompletionTracker;
import com.example.skillbuild.domain.CompletionTracker.QuizStatus;
import com.example.skillbuild.repository.CompletionTrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TrackCompletionService {

    @Autowired
    private CompletionTrackerRepository completionTrackerRepository;

    // Get all completed courses for a student
    public List<CompletionTracker> getCompletedCourses(String studentUsername) {
        return completionTrackerRepository.findByStudentUsername(studentUsername);
    }

    // Mark a course as completed (after passing quiz)
    public void markCourseAsCompleted(String studentUsername, String courseName) {
        CompletionTracker completion = new CompletionTracker(
                studentUsername,
                courseName,
                LocalDateTime.now(),
                "PASSED"  // Default status when quiz is passed
        );
        completionTrackerRepository.save(completion);
    }

    // Check if a student has completed a specific course
    public boolean isCourseCompleted(String studentUsername, String courseName) {
        return completionTrackerRepository.findByStudentUsername(studentUsername)
                .stream()
                .anyMatch(c -> c.getCourseName().equals(courseName) && c.getQuizStatus() == QuizStatus.PASSED);
    }
}

