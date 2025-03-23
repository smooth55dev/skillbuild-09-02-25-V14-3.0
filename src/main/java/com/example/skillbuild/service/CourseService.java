package com.example.skillbuild.service;

import com.example.skillbuild.domain.Course;
import com.example.skillbuild.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public List<Course> searchCourses(String keyword) {
        return courseRepository.searchCourses(keyword);
    }

    public List<Course> filterByCategory(String category) {
        return category != null && !category.trim().isEmpty() ? courseRepository.findByCategory(category) : List.of();
    }

    public List<Course> filterBySkillLevel(String skillLevel) {
        return skillLevel != null && !skillLevel.trim().isEmpty() ? courseRepository.findBySkillLevel(skillLevel) : List.of();
    }

    public List<Course> advancedSearch(String category, String skillLevel, String keyword) {
        return courseRepository.advancedSearch(
                category != null && !category.trim().isEmpty() ? category : null,
                skillLevel != null && !skillLevel.trim().isEmpty() ? skillLevel : null,
                keyword != null && !keyword.trim().isEmpty() ? keyword : null
        );
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
