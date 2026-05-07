package com.hehorhii.restful_api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// HabitsRepository provides data access methods for Habits entities.
// This interface extends JpaRepository for CRUD operations on habits.
@Repository
public interface HabitsRepository extends JpaRepository<Habits, Long> {
    List<Habits> findByUserId(Long userId);
}
