package com.rp.albumtracker.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.rp.albumtracker.DTO.ArtistDTO;
import com.rp.albumtracker.model.Album;
import com.rp.albumtracker.model.Artist;

@Mapper
public interface ArtistMapper {
  ArtistMapper INSTANCE = Mappers.getMapper(ArtistMapper.class);

  @Mapping(source = "albums", target = "albumNames")
  ArtistDTO artistToArtistDTO(Artist artist);

  default Set<String> mapAlbumsToAlbumNames(Set<Album> albums) {
    return albums.stream()
        .map(Album::getName)
        .collect(Collectors.toSet());
  }
}
