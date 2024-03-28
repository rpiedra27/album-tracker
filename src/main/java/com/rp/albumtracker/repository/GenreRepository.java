package com.rp.albumtracker.repository;

import org.springframework.data.repository.ListCrudRepository;
import com.rp.albumtracker.model.Genre;

public interface GenreRepository extends ListCrudRepository<Genre, Integer> {
}
