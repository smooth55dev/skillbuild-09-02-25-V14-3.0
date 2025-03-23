package com.example.skillbuild.controller;

import com.example.skillbuild.domain.Course;
import com.example.skillbuild.repository.CourseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class MainController {

    private final CourseRepository courseRepository;

    public MainController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
   
    @RequestMapping("/")
    public String start(Model model) {
        // Fetch courses from the database
        List<Course> courses = courseRepository.findAll();
        System.out.println("Courses retrieved for Start Page: " + courses);

        // Pass courses to JSP
        model.addAttribute("courses", courses);

        return "start"; // Ensure this maps to start.jsp
    }

}

