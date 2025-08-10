package com.loopers.spock.example.domain.user.attribute


import spock.lang.Specification

class EmailSpec extends Specification {

    def "올바른 형식의 이메일이면 true를, 아니면 false를 반환한다"() {
        when:
        def valid = Email.isValid(value)

        then:
        valid == expected

        where:
        value                          | expected
        null                           | false
        ""                             | false
        " "                            | false
        "foo.test.com"                 | false
        "bar@example.c"                | false
        "alpha/beta/gamma@test.org"    | false
        "zeta001@whitehouse.alphabeta" | false
        "omega002@bluehouse.org2"      | false
        "alpha@mail.co"                | true
        "beta123-admin@naver.com"      | true
        "foo_bar+tag1-tag2@gmail.com"  | true
        "1234@5678.org"                | true
    }

    def "이메일 도메인에서 최상위 도메인을 제외한 나머지의 길이를 구한다"() {
        expect:
        email.domain.substring(0, email.domain.indexOf('.')).length() == expectedLength

        where:
        email                                    | expectedLength
        new Email("alpha@mail.co")               | 4
        new Email("beta123-admin@naver.com")     | 5
        new Email("foo_bar+tag1-tag2@gmail.com") | 6
        new Email("1234@5678.org")               | 4
    }

}
