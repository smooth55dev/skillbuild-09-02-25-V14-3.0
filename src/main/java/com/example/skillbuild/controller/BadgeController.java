package com.example.skillbuild.controller;

import com.example.skillbuild.domain.AppUser;
import com.example.skillbuild.domain.UserBadge;
import com.example.skillbuild.repository.AppUserRepository;
import com.example.skillbuild.service.BadgeService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/badges")
public class BadgeController {

    private final BadgeService badgeService;
    private final AppUserRepository userRepo;

    public BadgeController(BadgeService badgeService, AppUserRepository userRepo) {
        this.badgeService = badgeService;
        this.userRepo = userRepo;
    }

    @GetMapping
    public String viewBadges(Model model) {
        String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        AppUser user = userRepo.findByEmail(email).orElseThrow();
        List<UserBadge> badges = badgeService.getUserBadges(user);
        model.addAttribute("badges", badges);
        return "appUsers/badges";
    }
}
