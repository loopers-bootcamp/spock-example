package com.loopers.spock.example.domain.user.attribute;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class EmailTest {

    @DisplayName("올바른 형식의 이메일이면 true를, 아니면 false를 반환한다.")
    @CsvSource(textBlock = """
                                                 | false
                    ''                           | false
                    ' '                          | false
                    foo.test.com                 | false
                    bar@example.c                | false
                    alpha/beta/gamma@test.org    | false
                    zeta001@whitehouse.alphabeta | false
                    omega002@bluehouse.org2      | false
                    alpha@mail.co                | true
                    beta123-admin@naver.com      | true
                    foo_bar+tag1-tag2@gmail.com  | true
                    1234@5678.org                | true
            """, delimiter = '|')
    @ParameterizedTest
    void checkIfEmailIsValid(String value, boolean expected) {
        // when
        boolean valid = Email.isValid(value);

        // then
        assertThat(valid).isEqualTo(expected);
    }

    @DisplayName("이메일 도메인에서 최상위 도메인을 제외한 나머지의 길이를 구한다.")
    @CsvSource(textBlock = """
            alpha@mail.co               | 4
            beta123-admin@naver.com     | 5
            foo_bar+tag1-tag2@gmail.com | 6
            1234@5678.org               | 4
            """, delimiter = '|')
    @ParameterizedTest
    void returnLengthDomainExceptTopLevelDomainFromEmail(String value, int expectedLength) {
        // given
        Email email = new Email(value);

        // expect
        assertThat(email.getDomain().substring(0, email.getDomain().indexOf('.')).length()).isEqualTo(expectedLength);
    }

}
