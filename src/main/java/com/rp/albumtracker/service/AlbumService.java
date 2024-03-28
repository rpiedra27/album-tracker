package com.rp.albumtracker.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.rp.albumtracker.model.Album;
import com.rp.albumtracker.model.Artist;
import com.rp.albumtracker.repository.AlbumRepository;
import com.rp.albumtracker.DTO.NewAlbumDTO;
import com.rp.albumtracker.exception.ResourceExistsException;
import com.rp.albumtracker.exception.ResourceNotFoundException;

@Service
public class AlbumService {
  private final AlbumRepository albumRepository;
  private final ArtistService artistService;
  private final ObjectMapper objectMapper;

  public AlbumService(AlbumRepository albumRepository, ObjectMapper objectMapper,
      ArtistService artistService) {
    this.albumRepository = albumRepository;
    this.objectMapper = objectMapper;
    this.artistService = artistService;
  }

  public Album create(NewAlbumDTO newAlbum) {
    Album album = new Album(newAlbum.name(), newAlbum.cover(),
        newAlbum.rating(),
        newAlbum.year(), newAlbum.length());
    if (albumRepository.existsAlbumByName(newAlbum.name())) {
      throw new ResourceExistsException("Album with that name already exists");
    } else {
      List<Artist> artists = artistService.findAllById(Arrays.asList(newAlbum.artists()));
      for (Artist artist : artists)
        album.addArtist(artist);
      return albumRepository.save(album);
    }
  }

  public Album replace(Map<String, Object> albumMap, Integer id) {
    Album newAlbum = new Album((String) albumMap.get("name"), (String) albumMap.get("cover"),
        (Double) albumMap.get("rating"),
        (Integer) albumMap.get("year"), (Integer) albumMap.get("length"));
    List<Integer> artistIds = (List<Integer>) albumMap.get("artists");
    List<Artist> artists = artistService.findAllById(artistIds);

    // TODO: handle repeated album name
    /*
     * if (albumService.existsAlbumByName(album.getName())) {
     * throw new ResponseStatusException(HttpStatus.CONFLICT,
     * "Album with that name already exists");
     */

    return albumRepository.findById(id)
        .map(album -> {
          album.setName(newAlbum.getName());
          album.setCover(newAlbum.getCover());
          album.setRating(newAlbum.getRating());
          album.setYear(newAlbum.getYear());
          album.setLength(newAlbum.getLength());
          for (Artist artist : artists)
            album.addArtist(artist);
          return albumRepository.save(album);
        })
        .orElseGet(() -> {
          for (Artist artist : artists)
            newAlbum.addArtist(artist);
          return albumRepository.save(newAlbum);
        });
  }

  public Album update(Integer id, JsonPatch jsonPatch)
      throws JsonProcessingException, IllegalArgumentException, JsonPatchException {
    // TODO: handle artist change
    Optional<Album> albumObj = albumRepository.findById(id);
    if (albumObj.isPresent()) {
      JsonNode patched = jsonPatch.apply(objectMapper.convertValue(albumObj, JsonNode.class));
      return albumRepository.save(objectMapper.treeToValue(patched, Album.class));
    } else {
      throw new ResourceNotFoundException("Album with ID " + id + " not found");
    }
  }
}
