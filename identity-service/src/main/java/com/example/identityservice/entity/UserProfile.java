package com.example.identityservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_profile")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "birthday", nullable = false)
    LocalDate birthDate;
    @Column(name = "gender", nullable = false)
    GenderEnum gender;
    @Column(name = "address", nullable = false)
    String address;
    @Column(name = "bio", nullable = false)
    String bio;
    @Column(name = "avatar", nullable = false)
    String avatar;
    @Column(name = "company", nullable = false)
    String company;
    @Column(name = "year_of_experience", nullable = false)
    Byte yearOfExperience;
    @Column(name = "education_level", nullable = false)
    Byte educationLevel;
    @Column(name = "linkedin", nullable = false)
    String linkedin;
    @Column(name = "facebook", nullable = false)
    String facebook;
    @Column(name = "personal_website", nullable = false)
    String personalWebsite;
    @Column(name = "created_at", nullable = false)
    Instant createAt;
    @Column(name = "updated_at", nullable = false)
    Instant updateAt;
    @Column(name = "created_by", nullable = false)
    long createBy;
    @Column(name = "updated_by", nullable = false)
    long updateBy;
    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id",nullable = false)
    User user;
    @OneToMany(mappedBy = "userProfile", fetch = FetchType.LAZY)
    List<UserInterest> userInterest;
}
