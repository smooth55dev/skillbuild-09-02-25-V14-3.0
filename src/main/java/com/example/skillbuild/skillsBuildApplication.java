package com.example.skillbuild;

import com.example.skillbuild.config.PasswordEncoder;
import com.example.skillbuild.domain.AppUser;
import com.example.skillbuild.domain.Course;
import com.example.skillbuild.repository.AppUserRepository;
import com.example.skillbuild.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class skillsBuildApplication implements CommandLineRunner {

    @Autowired
    private AppUserRepository repo;

    @Autowired
    private CourseRepository repocourse;

    @Autowired
    private PasswordEncoder encoder;

    public static void main(String[] args) {
        SpringApplication.run(skillsBuildApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        AppUser specialAppUser = new AppUser();
        specialAppUser.setName("Administrator");
        specialAppUser.setEmail("admin@leicester.com");
        specialAppUser.setUniversity("Leicester");
        specialAppUser.setPassword(encoder.encoder().encode("password"));
        specialAppUser.setSpecial(true);

        specialAppUser = repo.save(specialAppUser);

        // Initialize Courses
        List<Course> courses = new ArrayList<>();

        courses.add(createCourse("Artificial Intelligence", "Learn about AI with IBM.",
                "https://academic.ibm.com/a2mt/downloads/artificial_intelligence#/", "AI", "Advanced"));
        courses.add(createCourse("Capstone", "A capstone project from IBM.",
                "https://academic.ibm.com/a2mt/downloads/capstone#/", "AI", "Intermediate"));
        courses.add(createCourse("Data Science", "Dive into the world of data science.",
                "https://academic.ibm.com/a2mt/downloads/data_science#/", "AI", "Intermediate"));
        courses.add(createCourse("IBM Automation", "Learn IBM automation tools.",
                "https://academic.ibm.com/a2mt/downloads/ibm_automation#/", "Cloud", "Beginner"));
        courses.add(createCourse("IBM Cloud", "Explore the IBM Cloud platform.",
                "https://academic.ibm.com/a2mt/downloads/ibm_cloud#/", "Cloud", "Advanced"));
        courses.add(createCourse("IBM Engineering", "Engineering solutions with IBM.",
                "https://academic.ibm.com/a2mt/downloads/ibm_engineering#/", "Cloud", "Intermediate"));
        courses.add(createCourse("IBM Security", "Learn IBM security practices.",
                "https://academic.ibm.com/a2mt/downloads/ibm_security#/", "Security", "Advanced"));
        courses.add(createCourse("IBM Z", "Get started with IBM Z mainframe.",
                "https://academic.ibm.com/a2mt/downloads/ibm_z#/", "Cloud", "Beginner"));
        courses.add(createCourse("Power Systems", "Explore IBM Power Systems.",
                "https://academic.ibm.com/a2mt/downloads/power_systems#/", "Cloud", "Intermediate"));
        courses.add(createCourse("Quantum Computing", "Dive into IBM Quantum Computing.",
                "https://academic.ibm.com/a2mt/downloads/quantum_computing#/", "AI", "Advanced"));

        repocourse.saveAll(courses);
    }

    private Course createCourse(String name, String description, String url, String category, String skillLevel) {
        Course course = new Course();
        course.setName(name);
        course.setDescription(description);
        course.setUrl(url);
        course.setCategory(category);
        course.setSkillLevel(skillLevel);
        return course;
    }

    // Clears any cached authentication session
    @Component
    public static class SessionCleaner implements CommandLineRunner {
        @Override
        public void run(String... args) throws Exception {
            SecurityContextHolder.clearContext();
        }
    }
}
