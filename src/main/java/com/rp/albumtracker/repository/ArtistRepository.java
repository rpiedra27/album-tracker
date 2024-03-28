package com.rp.albumtracker.repository;

import org.springframework.data.repository.ListCrudRepository;
import com.rp.albumtracker.model.Artist;

public interface ArtistRepository extends ListCrudRepository<Artist, Integer> {
}
