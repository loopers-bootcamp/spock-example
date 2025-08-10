package com.loopers.spock.example.infrastructure.user;

import com.loopers.spock.example.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);

}
