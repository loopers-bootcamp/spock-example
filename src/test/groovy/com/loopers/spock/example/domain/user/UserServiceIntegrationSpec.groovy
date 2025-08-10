package com.loopers.spock.example.domain.user

import com.loopers.spock.example.domain.user.attribute.Email
import com.loopers.spock.example.domain.user.attribute.Gender
import com.loopers.spock.example.test.util.DatabaseCleanUp
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.LocalDate

@SpringBootTest
class UserServiceIntegrationSpec extends Specification {

    @Autowired
    private UserService userService

    @Autowired
    private EntityManager entityManager
    @Autowired
    private DatabaseCleanUp databaseCleanUp

    def "이미 가입된 회원의 이름이 아니고 모든 속성이 올바르면, 사용자 정보를 반환한다"() {
        given:
        def command = UserCommand.Join.builder()
                .userName("gildong")
                .gender(Gender.MALE)
                .birthDate(LocalDate.of(1990, 1, 1))
                .email(new Email("gildong.hong@example.com"))
                .build()

        when:
        def user = userService.join(command)

        then:
        user != null
        user.id != null
        user.name == command.userName
        user.gender == command.gender
        user.birthDate == command.birthDate
        user.email == command.email

        and:
        def foundUser = entityManager.find(User, user.id)
        foundUser.id == user.id

        cleanup:
        databaseCleanUp.truncateAllTables()
    }

}
