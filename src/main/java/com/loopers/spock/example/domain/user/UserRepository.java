package com.loopers.spock.example.domain.user;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByName(String name);

    User saveUser(User user);

}
