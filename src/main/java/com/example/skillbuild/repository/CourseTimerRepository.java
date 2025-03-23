package com.example.skillbuild.repository;

import com.example.skillbuild.domain.CourseTimer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseTimerRepository extends JpaRepository<CourseTimer, Long> {
    List<CourseTimer> findByUserId(int userId);
}
