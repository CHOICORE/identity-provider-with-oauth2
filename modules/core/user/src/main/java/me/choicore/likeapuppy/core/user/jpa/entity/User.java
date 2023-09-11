package me.choicore.likeapuppy.core.user.jpa.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private Credential credential;

	@Embedded
	private Username username;

	private String nickname;

	private String picture;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

	@Embedded
	private AccountStatus accountStatus;

	private Instant registeredAt;

	private Instant modifiedAt;


	@Builder
	public User(
			final Long id,
			final Credential credential,
			final Username username,
			final String nickname,
			final String picture,
			final Gender gender,
			final List<GrantedAuthority> grantedAuthorities,
			final AccountStatus accountStatus,
			final Instant registeredAt
	) {
		this.id = id;
		this.credential = credential;
		this.username = username;
		this.nickname = nickname;
		this.picture = picture;
		this.gender = gender;
		this.grantedAuthorities = grantedAuthorities;
		this.accountStatus = accountStatus;
		this.registeredAt = registeredAt;
	}

	public void addAuthority(final GrantedAuthority grantedAuthority) {
		if (this.grantedAuthorities == null) this.grantedAuthorities = new ArrayList<>();
		this.grantedAuthorities.add(grantedAuthority);
	}
}
