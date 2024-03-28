package com.rp.albumtracker.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "genre")
public class Genre {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;
  @NotEmpty
  String name;

  @OneToMany(mappedBy = "artistGenres")
  Set<Artist> artists;

  @OneToMany(mappedBy = "albumGenres")
  Set<Album> albums;

  private Genre() {
  }

  public Genre(String name) {
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

}
