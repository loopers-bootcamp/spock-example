package com.loopers.spock.example.interfaces.api;

import com.loopers.spock.example.domain.user.User;
import com.loopers.spock.example.domain.user.UserCommand;
import com.loopers.spock.example.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponse.Join join(@RequestBody UserRequest.Join request) {
        UserCommand.Join command = UserCommand.Join.builder()
                .userName(request.getUserName())
                .gender(request.getGender())
                .birthDate(request.getBirthDate())
                .email(request.getEmail())
                .build();
        User user = userService.join(command);

        return UserResponse.Join.from(user);
    }

}
