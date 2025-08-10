package com.loopers.spock.example.domain.user

import com.blogspot.toomuchcoding.spock.subjcollabs.Collaborator
import com.blogspot.toomuchcoding.spock.subjcollabs.Subject
import com.loopers.spock.example.common.error.BusinessException
import com.loopers.spock.example.common.error.CommonErrorType
import com.loopers.spock.example.domain.user.attribute.Email
import com.loopers.spock.example.domain.user.attribute.Gender
import spock.lang.Specification

import java.time.LocalDate

class UserServiceSpec extends Specification {

    @Subject
    private UserService userService

    @Collaborator
    private UserRepository userRepository = Mock()

    def "이미 가입된 회원의 이름이면, BusinessException이 발생한다"() {
        given:
        def command = UserCommand.Join.builder()
                .userName("gildong")
                .gender(Gender.MALE)
                .birthDate(LocalDate.of(1990, 1, 1))
                .email(new Email("gildong.hong@example.com"))
                .build()

        when:
        userService.join(command)

        then:
        def e = thrown(BusinessException)
        e.errorType == CommonErrorType.CONFLICT

        and:
        1 * userRepository.findByName(command.userName) >> Optional.of(Stub(User))
        0 * userRepository.saveUser(_)
    }

    def "이미 가입된 회원의 이름이 아니고 모든 속성이 올바르면, 사용자 정보를 반환한다"() {
        given:
        def command = UserCommand.Join.builder()
                .userName("gildong")
                .gender(Gender.MALE)
                .birthDate(LocalDate.of(1990, 1, 1))
                .email(new Email("gildong.hong@example.com"))
                .build()

        when:
        def joinedUser = userService.join(command)

        then:
        joinedUser != null
        joinedUser.name == command.userName
        joinedUser.gender == command.gender
        joinedUser.birthDate == command.birthDate
        joinedUser.email == command.email

        and:
        1 * userRepository.findByName(command.userName) >> Optional.empty()
        1 * userRepository.saveUser(_)
    }

}
