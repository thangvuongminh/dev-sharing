package com.example.identityservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "user_interest")
public class UserInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "interest_name",nullable = false)
    String interestName;
    @Column(name = "interest_level",nullable = false)
    Byte interestLevel;
    @Column(name = "created_at", nullable = false)
    Instant createAt;
    @Column(name = "updated_at", nullable = false)
    Instant updateAt;
    @Column(name = "created_by", nullable = false)
    long createBy;
    @Column(name = "updated_by", nullable = false)
    long updateBy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    UserProfile userProfile;
}
