package com.rp.albumtracker.repository;

import org.springframework.data.repository.ListCrudRepository;
import com.rp.albumtracker.model.Song;

public interface SongRepository extends ListCrudRepository<Song, Integer> {
}
