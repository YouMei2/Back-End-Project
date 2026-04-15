package com.hehorhii.restful_api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    // Важно: имя метода должно соответствовать полю в сущности (userId)
    List<Diary> findByUserId(Long userId);
}