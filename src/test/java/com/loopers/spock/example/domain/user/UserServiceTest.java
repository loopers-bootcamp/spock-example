package com.loopers.spock.example.domain.user;

import com.loopers.spock.example.common.error.BusinessException;
import com.loopers.spock.example.common.error.CommonErrorType;
import com.loopers.spock.example.domain.user.attribute.Email;
import com.loopers.spock.example.domain.user.attribute.Gender;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@MockitoSettings
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @DisplayName("이미 가입된 회원의 이름이면, BusinessException이 발생한다.")
    @Test
    void throwException_whenUserExistsByName() {
        // given
        UserCommand.Join command = UserCommand.Join.builder()
                .userName("gildong")
                .gender(Gender.MALE)
                .birthDate(LocalDate.of(1990, 1, 1))
                .email(new Email("gildong.hong@example.com"))
                .build();
        given(userRepository.findByName(command.getUserName()))
                .willReturn(Optional.of(Instancio.create(User.class)));

        // when & then
        assertThatException()
                .isThrownBy(() -> userService.join(command))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorType", CommonErrorType.CONFLICT);

        verify(userRepository, times(1)).findByName(command.getUserName());
        verify(userRepository, never()).saveUser(any(User.class));
    }

    @DisplayName("이미 가입된 회원의 이름이 아니고 모든 속성이 올바르면, 사용자 정보를 반환한다.")
    @Test
    void returnUser_whenUserDoesNotExistByName() {
        // given
        UserCommand.Join command = UserCommand.Join.builder()
                .userName("gildong")
                .gender(Gender.MALE)
                .birthDate(LocalDate.of(1990, 1, 1))
                .email(new Email("gildong.hong@example.com"))
                .build();
        given(userRepository.findByName(command.getUserName()))
                .willReturn(Optional.empty());

        // when
        User joinedUser = userService.join(command);

        // then
        assertThat(joinedUser).isNotNull();
        assertThat(joinedUser.getName()).isEqualTo(command.getUserName());
        assertThat(joinedUser.getGender()).isEqualTo(command.getGender());
        assertThat(joinedUser.getBirthDate()).isEqualTo(command.getBirthDate());
        assertThat(joinedUser.getEmail()).isEqualTo(command.getEmail());

        verify(userRepository, times(1)).findByName(command.getUserName());
        verify(userRepository, times(1)).saveUser(any(User.class));
    }

}
