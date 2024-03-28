package com.rp.albumtracker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.rp.albumtracker.exception.ResourceNotFoundException;
import com.rp.albumtracker.model.Artist;
import com.rp.albumtracker.repository.ArtistRepository;

@Service
public class ArtistService {
  private ArtistRepository artistRepository;

  public ArtistService(ArtistRepository artistRepository) {
    this.artistRepository = artistRepository;
  }

  public List<Artist> findAllById(List<Integer> artistIds) {
    List<Artist> artists = new ArrayList<Artist>();
    for (Integer artist : artistIds) {
      Optional<Artist> artistObj = artistRepository.findById(artist);
      if (artistObj.isPresent()) {
        Artist currentArtist = artistObj.get();
        artists.add(currentArtist);
      } else {
        throw new ResourceNotFoundException("Artist with ID " + artist + " not found");
      }
    }
    return artists;
  }

}
