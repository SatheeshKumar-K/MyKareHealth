package com.kare.health.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kare.health.model.User;

public class UserPrincipal implements UserDetails {

	private Long userId;

	private String username;
	private String gender;
	@JsonIgnore
	private String email;
	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public UserPrincipal(Long userId, String username, String gender, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.userId = userId;
		this.username = username;
		this.gender = gender;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public static UserPrincipal create(User user) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(user.getRole()));
		return new UserPrincipal(user.getUserId(), user.getUserName(), user.getGender(), user.getEmail(),
				user.getPassword(), authorities);
	}

	public Long getUserId() {
		return userId;
	}

	public String getUserName() {
		return username;
	}

	public String getGender() {
		return gender;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserPrincipal that = (UserPrincipal) o;
		return Objects.equals(userId, that.userId);
	}

	@Override
	public int hashCode() {

		return Objects.hash(userId);
	}

	@Override
	public String getUsername() {
		return username;
	}
}
