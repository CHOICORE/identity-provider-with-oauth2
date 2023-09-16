package me.choicore.likeapuppy.core.user.jpa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private String description;
    private String street;
    private String city;
    private String state;
    private String country;
    private String postalCode;

    @Builder
    public Address(
            Long id,
            Long userId,
            String description,
            String street,
            String city,
            String state,
            String country,
            String postalCode
    ) {
        this.id = id;
        this.userId = userId;
        this.description = description;
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
    }
}

