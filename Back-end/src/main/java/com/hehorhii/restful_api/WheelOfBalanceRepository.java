package com.hehorhii.restful_api;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WheelOfBalanceRepository extends JpaRepository<WheelOfBalance, Long> {
    Optional<WheelOfBalance> findByUserId(Long userId);
}