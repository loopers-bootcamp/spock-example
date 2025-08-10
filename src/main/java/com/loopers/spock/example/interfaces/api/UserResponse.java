package com.loopers.spock.example.interfaces.api;

import com.loopers.spock.example.domain.user.User;
import com.loopers.spock.example.domain.user.attribute.Email;
import com.loopers.spock.example.domain.user.attribute.Gender;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserResponse {

    @Getter
    @Builder
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Join {
        private final Long userId;
        private final String userName;
        private final Gender gender;
        private final LocalDate birthDate;
        private final Email email;

        public static Join from(User user) {
            return builder()
                    .userId(user.getId())
                    .userName(user.getName())
                    .gender(user.getGender())
                    .birthDate(user.getBirthDate())
                    .email(user.getEmail())
                    .build();
        }
    }

}
