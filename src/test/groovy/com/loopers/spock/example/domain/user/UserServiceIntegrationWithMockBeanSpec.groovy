package com.loopers.spock.example.domain.user

import com.loopers.spock.example.domain.user.attribute.Email
import com.loopers.spock.example.domain.user.attribute.Gender
import org.instancio.Instancio
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.LocalDate

@SpringBootTest
class UserServiceIntegrationWithMockBeanSpec extends Specification {

    @Autowired
    private UserService userService

    @SpringBean
    private UserRepository userRepository = Mock()

    def "이미 가입된 회원의 이름이 아니고 모든 속성이 올바르면, 사용자 정보를 반환한다"() {
        given:
        def command = UserCommand.Join.builder()
                .userName("gildong")
                .gender(Gender.MALE)
                .birthDate(LocalDate.of(1990, 1, 1))
                .email(new Email("gildong.hong@example.com"))
                .build()

        def userId = Instancio.create(Long)

        when:
        def user = userService.join(command)

        then:
        user != null
        user.id == userId
        user.name == command.userName
        user.gender == command.gender
        user.birthDate == command.birthDate
        user.email == command.email

        and:
        1 * userRepository.findByName(command.userName) >> Optional.empty()
        1 * userRepository.saveUser(_) >> { User u ->
            u.@id = userId
            return u
        }
    }

}
