package com.example.skillbuild.repository;

import com.example.skillbuild.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // Search by keyword in name or description
    @Query("SELECT c FROM Course c WHERE "
            + "(:keyword IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) "
            + "OR LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Course> searchCourses(@Param("keyword") String keyword);

    // Filter by category
    List<Course> findByCategory(String category);

    // Filter by skill level
    List<Course> findBySkillLevel(String skillLevel);

    // Advanced search: combine category, skill level, and keyword filters
    @Query("SELECT c FROM Course c WHERE "
            + "(:category IS NULL OR LOWER(c.category) = LOWER(:category)) "
            + "AND (:skillLevel IS NULL OR LOWER(c.skillLevel) = LOWER(:skillLevel)) "
            + "AND (:keyword IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) "
            + "OR LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Course> advancedSearch(@Param("category") String category,
                                @Param("skillLevel") String skillLevel,
                                @Param("keyword") String keyword);
}
