package com.example.skillbuild.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CompletionTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentUsername;
    private String courseName;
    private LocalDateTime completionTime;

    @Enumerated(EnumType.STRING)
    private QuizStatus quizStatus;

    public enum QuizStatus {
        PENDING, PASSED, FAILED;

        public static QuizStatus fromString(String status) {
            try {
                return QuizStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid quiz status: " + status);
            }
        }
    }

    // Constructor with parameters
    public CompletionTracker(String studentUsername, String courseName, LocalDateTime completionTime, String quizStatus) {
        this.studentUsername = studentUsername;
        this.courseName = courseName;
        this.completionTime = completionTime;
        this.quizStatus = QuizStatus.fromString(quizStatus);
    }
}
