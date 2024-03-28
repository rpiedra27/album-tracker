/* package com.rp.albumtracker.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.rp.albumtracker.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
  private final PasswordEncoder encoder;
  private final UserRepository userRepository;

  public UserService(PasswordEncoder encoder, UserRepository userRepository) {
    this.encoder = encoder;
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    System.out.println("In the user details service");
    return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user does not exist"));
  }

} */