package com.example.skillbuild.repository;

import com.example.skillbuild.domain.CompletionTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CompletionTrackerRepository extends JpaRepository<CompletionTracker, Long> {
    List<CompletionTracker> findByStudentUsername(String studentUsername);
}
