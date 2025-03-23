package com.example.skillbuild.domain;

import jakarta.persistence.*;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String url;
    private String category;

    @Enumerated(EnumType.STRING)  // Store as String in DB
    private SkillLevel skillLevel;

    public enum SkillLevel {
        BEGINNER, INTERMEDIATE, ADVANCED, EXPERT;

        public static SkillLevel fromString(String skillLevel) {
            try {
                return SkillLevel.valueOf(skillLevel.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid skill level: " + skillLevel);
            }
        }
    }

    // Default constructor
    public Course() {}

    // Constructor with parameters
    public Course(String name, String description, String url, String category, String skillLevel) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.category = category;
        this.skillLevel = SkillLevel.fromString(skillLevel);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public SkillLevel getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = SkillLevel.fromString(skillLevel);
    }
}
