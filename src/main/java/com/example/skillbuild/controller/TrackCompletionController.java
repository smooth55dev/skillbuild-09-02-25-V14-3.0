package com.example.skillbuild.controller;

import com.example.skillbuild.domain.AppUser;
import com.example.skillbuild.domain.CompletionTracker;
import com.example.skillbuild.repository.AppUserRepository;
import com.example.skillbuild.service.BadgeService;
import com.example.skillbuild.service.TrackCompletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller  // <-- Change @RestController to @Controller
@RequestMapping("/trackCompletion")
public class TrackCompletionController {

    @Autowired
    private TrackCompletionService trackCompletionService;
    @Autowired
    private BadgeService badgeService;

    @Autowired
    private AppUserRepository userRepo;

    @PostMapping("/complete")
    @ResponseBody
    public String markCourseAsCompleted(@RequestParam String username, @RequestParam String courseName) {
        if (trackCompletionService.isCourseCompleted(username, courseName)) {
            return "Course is already completed!";
        }

        trackCompletionService.markCourseAsCompleted(username, courseName);

        AppUser user = userRepo.findByEmail(username).orElseThrow();

        // Award badges
        badgeService.awardBadge(user, "First Steps"); // example rule
        long completedCount = trackCompletionService.getCompletedCourses(username).size();
        if (completedCount >= 3) {
            badgeService.awardBadge(user, "Achiever");
        }

        return "Course marked as completed!";
    }


    // ✅ Serve the JSP Page
    @GetMapping
    public String showTrackCompletionPage() {
        return "appUsers/trackCompletion";  // Matches WEB-INF/views/appUsers/trackCompletion.jsp
    }

    // ✅ Get Completed Courses for the Logged-in Student
    @GetMapping("/completed/{username}")
    @ResponseBody  // <-- Keep JSON response for API calls
    public List<CompletionTracker> getCompletedCourses(@PathVariable String username) {
        return trackCompletionService.getCompletedCourses(username);
    }
}


