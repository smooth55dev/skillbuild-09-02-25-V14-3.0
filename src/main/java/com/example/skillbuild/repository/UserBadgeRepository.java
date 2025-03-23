package com.example.skillbuild.repository;

import com.example.skillbuild.domain.AppUser;
import com.example.skillbuild.domain.Badge;
import com.example.skillbuild.domain.UserBadge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {
    List<UserBadge> findByUser(AppUser user);
    boolean existsByUserAndBadge(AppUser user, Badge badge);
}
