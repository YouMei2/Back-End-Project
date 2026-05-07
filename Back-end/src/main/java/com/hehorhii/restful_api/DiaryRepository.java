package com.hehorhii.restful_api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// DiaryRepository provides data access methods for Diary entities.
// This interface extends JpaRepository for CRUD operations on diary entries.
@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByUserId(Long userId);
}