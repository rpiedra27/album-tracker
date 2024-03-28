package com.rp.albumtracker.controller;

import java.util.List;
import java.util.Optional;

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
import com.rp.albumtracker.model.Genre;
import com.rp.albumtracker.repository.GenreRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/genre")
@CrossOrigin
public class GenreController {
  private final GenreRepository repository;

  public GenreController(GenreRepository repository) {
    this.repository = repository;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public void create(@Valid @RequestBody Genre genre) {
    repository.save(genre);
  }

  //@RolesAllowed("ROLE_USER")
  @GetMapping("")
  public List<Genre> findAll() {
    return repository.findAll();
  }

  @GetMapping("/{id}")
  public Optional<Genre> findById(@PathVariable Integer id) {
    return repository.findById(id);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PutMapping("/{id}")
  public void update(@RequestBody Genre genre, @PathVariable Integer id) {
    if (repository.findById(id) != null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found");
    }
    repository.save(genre);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Integer id) {
    repository.deleteById(id);
  }
}
