package com.rp.albumtracker.repository;

import org.springframework.data.repository.ListCrudRepository;
import com.rp.albumtracker.model.Album;

public interface AlbumRepository extends ListCrudRepository<Album, Integer> {
  boolean existsAlbumByName(String name);
}
