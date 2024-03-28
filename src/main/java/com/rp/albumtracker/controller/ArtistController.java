package com.rp.albumtracker.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.rp.albumtracker.DTO.ArtistDTO;
import com.rp.albumtracker.mapper.ArtistMapper;
import com.rp.albumtracker.model.Album;
import com.rp.albumtracker.model.Artist;
import com.rp.albumtracker.repository.ArtistRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/artist")
@CrossOrigin
public class ArtistController {
  private final ArtistRepository repository;

  public ArtistController(ArtistRepository repository) {
    this.repository = repository;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public void create(@Valid @RequestBody Artist artist) {
    /*
     * Artist newArtist = new Artist("Interpol", "x.com");
     * 
     * Album newAlbum = new Album("TOTBL", "x.com", 5.0, 2002, 45);
     * Album newAlbum2 = new Album("Antics", "x.com", 5.0, 2004, 45);
     * 
     * newArtist.addAlbum(newAlbum);
     * newArtist.addAlbum(newAlbum2);
     */

    repository.save(artist);
  }

  // @RolesAllowed("ROLE_USER")
  @GetMapping("")
  public List<ArtistDTO> findAll() {
    List<Artist> artistList = repository.findAll();
    List<ArtistDTO> artistDTOList = new ArrayList<ArtistDTO>();
    for (Artist artist : artistList) {
      artistDTOList.add(ArtistMapper.INSTANCE.artistToArtistDTO(artist));
    }
    return artistDTOList;
  }

  @GetMapping("/{id}")
  public Optional<Artist> findById(@PathVariable Integer id) {
    return repository.findById(id);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PutMapping("/{id}")
  public void replace(@RequestBody Artist artist, @PathVariable Integer id) {
    if (repository.findById(id) != null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found");
    }
    repository.save(artist);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Integer id) {
    repository.deleteById(id);
  }
}
