package com.example.identityservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "user_role")
public class UserRole {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id",nullable = false)
    Role role;
    @Column(name = "created_at", nullable = false)
    Instant createAt;
    @Column(name = "updated_at", nullable = false)
    Instant updateAt;
    @Column(name = "created_by", nullable = false)
    long createBy;
    @Column(name = "updated_by", nullable = false)
    long updateBy;
}
