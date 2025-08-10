package com.loopers.spock.example.interfaces.api

import com.loopers.spock.example.domain.user.User
import com.loopers.spock.example.domain.user.UserService
import com.loopers.spock.example.domain.user.attribute.Email
import com.loopers.spock.example.domain.user.attribute.Gender
import org.instancio.Instancio
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import spock.lang.Specification

import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerWithMockBeanSpec extends Specification {

    private static final String BASE_ENDPOINT = "/api/v1/users"

    @SpringBean
    private UserService userService = Mock()

    @Autowired
    private TestRestTemplate testRestTemplate

    def "올바른 회원 가입정보를 주면, 가입에 성공한다"() {
        given:
        def requestUrl = "$BASE_ENDPOINT"
        def body = UserRequest.Join.builder()
                .userName("gildong")
                .gender(Gender.MALE)
                .birthDate(LocalDate.of(1990, 1, 1))
                .email(new Email("gildong.hong@example.com"))
                .build()

        def userId = Instancio.create(Long)

        when:
        def response = testRestTemplate.exchange(
                requestUrl,
                HttpMethod.POST,
                new HttpEntity<>(body),
                new ParameterizedTypeReference<UserResponse.Join>() {
                }
        )

        then:
        response.statusCode.is2xxSuccessful()
        response.body.userId == userId
        response.body.userName == body.userName
        response.body.gender == body.gender
        response.body.birthDate == body.birthDate
        response.body.email == body.email

        and:
        1 * userService.join(_) >> Stub(User) {
            id >> userId
            name >> body.userName
            gender >> body.gender
            birthDate >> body.birthDate
            email >> body.email
        }
    }

}
