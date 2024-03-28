package com.rp.albumtracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "album")
public class Song {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  @NotEmpty
  String name;

  @NotEmpty
  Integer length;

  @ManyToOne
  @JoinColumn(name="album_id", nullable=false)
  @JsonIgnore
  private Album album;  

  private Song() {
  }

  public Song(String name, Integer length) {
    this.name = name;
    this.length = length;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Integer getLength() {
    return length;
  }

}
