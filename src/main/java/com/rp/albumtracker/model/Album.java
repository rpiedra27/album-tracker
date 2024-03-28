package com.rp.albumtracker.model;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "album")
public class Album {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  @NotEmpty
  String name;

  String cover;

  Double rating;

  @Range(min = 1, max = 2024)
  Integer year;

  @Range(min = 1)
  Integer length;

  @OneToMany(mappedBy = "album")
  Set<Song> albumSongs;

  @NotEmpty
  @ManyToMany(mappedBy = "artistAlbums", cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE
  }, fetch = FetchType.LAZY)
  @JsonBackReference
  Set<Artist> artists = new HashSet<>();

  @ManyToMany
  @JoinTable(name = "genres_album",
   joinColumns = @JoinColumn(name = "album_id"),
   inverseJoinColumns = @JoinColumn(name = "genre_id"))
  private Set<Genre> albumGenres;

  private Album() {
  }

  public Album(String name, String cover,
      Double rating, Integer year, Integer length) {
    this.name = name;
    this.cover = cover;
    this.rating = rating;
    this.year = year;
    this.length = length;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getCover() {
    return cover;
  }

  public Double getRating() {
    return rating;
  }

  public Integer getYear() {
    return year;
  }

  public Integer getLength() {
    return length;
  }

  public Set<Artist> getArtists() {
    return artists;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCover(String cover) {
    this.cover = cover;
  }

  public void setRating(Double rating) {
    this.rating = rating;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public void setLength(Integer length) {
    this.length = length;
  }

  public void addArtist(Artist artist) {
    artists.add(artist);
    artist.getAlbums().add(this);
  }

  public void removeArtist(Artist artist) {
    artists.remove(artist);
    artist.getAlbums().remove(this);
  }

}
