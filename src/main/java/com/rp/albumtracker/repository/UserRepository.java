package com.rp.albumtracker.repository;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import com.rp.albumtracker.model.User;


public interface UserRepository extends ListCrudRepository<User, Integer> {
  Optional<User> findByEmail(String email);
}
