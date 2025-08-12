package com.loopers.spock.example.domain.user;

import com.loopers.spock.example.common.error.BusinessException;
import com.loopers.spock.example.common.error.CommonErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User join(UserCommand.Join command) {
        userRepository.findByName(command.getUserName())
                .ifPresent(user -> {
                    throw new BusinessException(CommonErrorType.CONFLICT, "이미 가입된 사용자입니다.");
                });

        User user = User.builder()
                .name(command.getUserName())
                .gender(command.getGender())
                .birthDate(command.getBirthDate())
                .email(command.getEmail())
                .build();
        userRepository.saveUser(user);

        return user;
    }

    @Transactional
    public User changeEmail(UserCommand.ChangeEmail command) {
        User user = userRepository.findByName(command.getUserName())
                .orElseThrow(() -> new BusinessException(CommonErrorType.NOT_FOUND, "존재하지 않는 사용자입니다."));

        user.changeEmail(command.getEmail());
        userRepository.saveUser(user);

        return user;
    }

}
