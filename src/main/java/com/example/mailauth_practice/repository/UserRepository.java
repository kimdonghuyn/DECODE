package com.example.mailauth_practice.repository;

import com.example.mailauth_practice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> { // JpaRepository<어떤 엔티티를 받냐, 엔티티의 PK의 타입이 뭐냐>

    UserEntity findByUserId(String userId);

    boolean existsByUserId(String userId);
}
