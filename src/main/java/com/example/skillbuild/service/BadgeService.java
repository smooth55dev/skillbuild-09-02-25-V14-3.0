package com.example.skillbuild.service;

import com.example.skillbuild.domain.AppUser;
import com.example.skillbuild.domain.Badge;
import com.example.skillbuild.domain.UserBadge;
import com.example.skillbuild.repository.BadgeRepository;
import com.example.skillbuild.repository.UserBadgeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BadgeService {

    private final BadgeRepository badgeRepo;
    private final UserBadgeRepository userBadgeRepo;

    public BadgeService(BadgeRepository badgeRepo, UserBadgeRepository userBadgeRepo) {
        this.badgeRepo = badgeRepo;
        this.userBadgeRepo = userBadgeRepo;
    }

    public void awardBadge(AppUser user, String badgeName) {
        Badge badge = badgeRepo.findByName(badgeName)
                .orElseThrow(() -> new RuntimeException("Badge not found"));

        if (!userBadgeRepo.existsByUserAndBadge(user, badge)) {
            UserBadge userBadge = new UserBadge();
            userBadge.setUser(user);
            userBadge.setBadge(badge);
            userBadge.setEarnedAt(LocalDateTime.now());
            userBadgeRepo.save(userBadge);
        }
    }

    public List<UserBadge> getUserBadges(AppUser user) {
        return userBadgeRepo.findByUser(user);
    }
}
