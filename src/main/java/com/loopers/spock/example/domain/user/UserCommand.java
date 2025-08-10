package com.loopers.spock.example.domain.user;

import com.loopers.spock.example.domain.user.attribute.Email;
import com.loopers.spock.example.domain.user.attribute.Gender;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserCommand {

    @Getter
    @Builder
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Join {
        private final String userName;
        private final Gender gender;
        private final LocalDate birthDate;
        private final Email email;
    }

}
