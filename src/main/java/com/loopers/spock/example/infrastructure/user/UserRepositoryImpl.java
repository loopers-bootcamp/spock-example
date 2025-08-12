package com.loopers.spock.example.infrastructure.user;

import com.loopers.spock.example.domain.user.User;
import com.loopers.spock.example.domain.user.UserRepository;
import com.loopers.spock.example.domain.user.attribute.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public List<User> findByEmail(Email email) {
        return userJpaRepository.findByEmail(email);
    }

    @Override
    public User saveUser(User user) {
        return userJpaRepository.save(user);
    }

}
