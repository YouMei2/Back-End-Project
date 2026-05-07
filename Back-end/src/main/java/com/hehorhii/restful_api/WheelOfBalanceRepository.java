package com.hehorhii.restful_api;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// WheelOfBalanceRepository provides data access methods for WheelOfBalance entities.
// This interface extends JpaRepository for CRUD operations on wheel of balance assessments.
public interface WheelOfBalanceRepository extends JpaRepository<WheelOfBalance, Long> {
    Optional<WheelOfBalance> findByUserId(Long userId);
}