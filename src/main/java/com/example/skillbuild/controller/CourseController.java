// CourseController.java
package com.example.skillbuild.controller;

import com.example.skillbuild.domain.Course;
import com.example.skillbuild.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/courses")
@CrossOrigin(origins = "*")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    @ResponseBody
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Course getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id).orElse(null);
    }
    @GetMapping("/searchPage")
    public String showSearchPage() {
        return "search";  // This should match `search.jsp`
    }


    @GetMapping("/search")
    public String searchCourses(@RequestParam(required = false) String keyword,
                                @RequestParam(required = false) String category,
                                @RequestParam(required = false) String skillLevel,
                                Model model) {

        List<Course> filteredCourses = courseService.advancedSearch(category, skillLevel, keyword);
        model.addAttribute("courses", filteredCourses);
        return "searchResults"; // Return JSP view name
    }

    @PostMapping
    @ResponseBody
    public Course addCourse(@RequestBody Course course) {
        return courseService.saveCourse(course);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
    }
}
