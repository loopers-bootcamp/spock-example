package com.loopers.spock.example.domain.user;

import com.loopers.spock.example.common.configuration.jpa.converter.EmailConverter;
import com.loopers.spock.example.common.error.BusinessException;
import com.loopers.spock.example.common.error.CommonErrorType;
import com.loopers.spock.example.domain.user.attribute.Email;
import com.loopers.spock.example.domain.user.attribute.Gender;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Pattern;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    /**
     * 아이디
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long id;

    /**
     * 이름
     */
    @Column(name = "user_name", nullable = false, unique = true)
    private String name;

    /**
     * 성별
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    /**
     * 생년월일
     */
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    /**
     * 이메일
     */
    @Convert(converter = EmailConverter.class)
    @Column(name = "email", nullable = false)
    private Email email;

    // -------------------------------------------------------------------------------------------------

    @Version
    private Long version;

    // -------------------------------------------------------------------------------------------------

    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\d]{1,10}$");

    @Builder
    private User(String name, Gender gender, LocalDate birthDate, Email email) {
        if (!StringUtils.hasText(name) || !NAME_PATTERN.matcher(name).matches()) {
            throw new BusinessException(CommonErrorType.INVALID, "이름은 영문 및 숫자로 10자 이내여야 합니다.");
        }

        if (gender == null) {
            throw new BusinessException(CommonErrorType.INVALID, "올바르지 않은 성별입니다.");
        }

        if (birthDate == null) {
            throw new BusinessException(CommonErrorType.INVALID, "올바르지 않은 생년월일입니다.");
        }

        if (email == null) {
            throw new BusinessException(CommonErrorType.INVALID, "올바르지 않은 이메일입니다.");
        }

        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.email = email;
    }

    public void changeEmail(Email newEmail) {
        if (newEmail == null) {
            throw new BusinessException(CommonErrorType.INVALID, "올바르지 않은 이메일입니다.");
        }

        if (Objects.equals(this.email, newEmail)) {
            throw new BusinessException(CommonErrorType.CONFLICT, "기존 이메일과 동일한 이메일입니다.");
        }

        this.email = newEmail;
    }

}
