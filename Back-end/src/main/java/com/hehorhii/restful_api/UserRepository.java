package com.hehorhii.restful_api;

import org.springframework.data.jpa.repository.JpaRepository;

public interface  UserRepository extends JpaRepository<User, Long> {
}
