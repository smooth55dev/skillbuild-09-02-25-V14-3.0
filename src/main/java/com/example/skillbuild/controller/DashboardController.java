package com.example.skillbuild.controller;

import com.example.skillbuild.domain.Course;
import com.example.skillbuild.repository.CourseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @details Manages the user dashboard, displaying relevant data after login.
 * Handles HTTP requests like:
 * GET /dashboard → Loads the dashboard page.
 * POST /dashboard/action → Handles dashboard actions.
 * Ensures only authenticated users can access the dashboard (using Spring Security).
 * May fetch user-specific data like profile details, analytics, or notifications.
 * This class controls what users see and interact with on the dashboard.
 */
@Controller
public class DashboardController {

    private final CourseRepository courseRepository;

    public DashboardController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // Fetch all courses from the database
        List<Course> courses = courseRepository.findAll();
        System.out.println("Courses retrieved: " + courses);

        // Pass courses to the JSP view
        model.addAttribute("courses", courses);

        // Return JSP page name (ensure it's placed in WEB-INF/views/appUsers/)
        return "appUsers/dashboard";
    }
}
