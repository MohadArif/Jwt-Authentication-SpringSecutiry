package com.example.jwtAutenticationDemo.repository;

import com.example.jwtAutenticationDemo.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfo,Integer> {
    Optional<UserInfo> findByusername(String username);
}
