package me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.choicore.likeapuppy.core.infrastructure.user.persistence.jpa.entity.id.UserId;

import java.time.LocalDate;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user_profile")
public class UserProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UserId userId;

    private String nickname;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private GenderEntity gender;

    private LocalDate dateOfBirth;

    private String aboutMe;

}

