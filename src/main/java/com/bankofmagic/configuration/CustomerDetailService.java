package com.bankofmagic.configuration;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bankofmagic.entities.Customer;
import com.bankofmagic.repository.CustomerRepository;

@Service
public class CustomerDetailService implements UserDetailsService {

	CustomerRepository customerRepository;

	String[] roles = { "ROLE_ADMIN", "ROLE_CUSTOMER" };

	public CustomerDetailService(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Customer user = customerRepository.findByUsername(username);
		Set<GrantedAuthority> authorities;
		if(user==null) {
			
			throw new UsernameNotFoundException("Username not found"+username);
		}else {
			
			authorities = new HashSet<>();
			authorities.add(new SimpleGrantedAuthority(roles[user.getIdRole()]));
		}
		
		return new User(user.getUsername(), user.getPassword(),authorities);
	}

}
