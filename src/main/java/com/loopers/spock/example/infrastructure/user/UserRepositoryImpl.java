package com.loopers.spock.example.infrastructure.user;

import com.loopers.spock.example.domain.user.User;
import com.loopers.spock.example.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findByName(String name) {
        return userJpaRepository.findByName(name);
    }

    @Override
    public User saveUser(User user) {
        return userJpaRepository.save(user);
    }

}
