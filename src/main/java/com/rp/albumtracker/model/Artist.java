package com.rp.albumtracker.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotEmpty;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;

@Entity
@Table(name = "artist")
public class Artist {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotEmpty
  private String name;

  private String picture;

  @ManyToMany(cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE
  }, fetch = FetchType.EAGER)
  @JoinTable(name = "artist_album",
   joinColumns = @JoinColumn(name = "artist_id"),
   inverseJoinColumns = @JoinColumn(name = "album_id"))
  @JsonManagedReference
  private Set<Album> artistAlbums = new HashSet<>();

  @ManyToMany
  @JoinTable(name = "genres_artist",
   joinColumns = @JoinColumn(name = "artist_id"),
   inverseJoinColumns = @JoinColumn(name = "genre_id"))
  private Set<Genre> artistGenres;

  private Artist() {
  }

  public Set<Album> getAlbums() {
    return artistAlbums;
  }

  public Artist(String name, String picture) {
    this.name = name;
    this.picture = picture;
  }

  public Integer getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getPicture() {
    return this.picture;
  }

  public void addAlbum(Album album) {
    artistAlbums.add(album);
    album.getArtists().add(this);
  }

  public void removeAlbum(Album album) {
    artistAlbums.remove(album);
    album.getArtists().remove(this);
  }

}