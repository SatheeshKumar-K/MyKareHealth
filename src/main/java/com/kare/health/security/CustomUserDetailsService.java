package com.kare.health.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kare.health.exception.ResourceNotFoundException;
import com.kare.health.model.User;
import com.kare.health.repository.UserRepository;
import com.kare.health.util.RecordStatusUtil;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(email);
		if (user != null) {
			if (user.getStatus() == RecordStatusUtil.RECORD_ACTIVE) {
				return UserPrincipal.create(user);
			} else if (user.getStatus() == RecordStatusUtil.RECORD_INACTIVE)
				throw new DisabledException("This User is Inactive please Contact Admin!");
			else if (user.getStatus() == RecordStatusUtil.RECORD_DELETED)
				throw new DisabledException("User Not Found");
		} else {
			throw new UsernameNotFoundException("User not found with username: " + email);
		}

		return null;
	}

	@Transactional
	public UserDetails loadUserById(Long id) {
		User user = userRepository.findByUserId(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		return UserPrincipal.create(user);
	}
}