package com.hehorhii.restful_api;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// UserRepository provides data access methods for User entities.
// This interface extends JpaRepository for CRUD operations on users and includes custom queries.
public interface  UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    List<User> findByCodeIsNotNullAndCodeCreatedAtBefore(LocalDateTime tenMinutesAgo);
}
