package com.library.bookhub.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.library.bookhub.entity.User;
import com.library.bookhub.repository.MemberRepository;

@Service
public class SecurityUserService implements UserDetailsService{
	
	@Autowired
	private MemberRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByUsername(username);
		MyUserDetails userDetails = new MyUserDetails(user);
		
		return userDetails;
	}
	
}
