package com.hehorhii.restful_api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// TaskRepository provides data access methods for Task entities.
// This interface extends JpaRepository for CRUD operations on tasks.
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
}
