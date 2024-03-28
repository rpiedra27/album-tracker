package com.rp.albumtracker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.rp.albumtracker.model.User;
import com.rp.albumtracker.repository.UserRepository;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
  private final UserRepository repository;

  public UserController(UserRepository repository) {
    this.repository = repository;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public void create(@Valid @RequestBody User album) {
    repository.save(album);
  }

  //@RolesAllowed("ROLE_USER")
  @GetMapping("")
  public List<User> findAll() {
    return repository.findAll();
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PutMapping("/{id}")
  public void update(@RequestBody User user, @PathVariable Integer id) {
    if (repository.findById(id) != null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }
    repository.save(user);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Integer id) {
    repository.deleteById(id);
  }
}
