package com.rp.albumtracker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rp.albumtracker.DTO.NewAlbumDTO;
import com.rp.albumtracker.controller.AlbumController;
import com.rp.albumtracker.model.Album;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AlbumControllerIntegrationTest {

  @Autowired
  private AlbumController albumController;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void contextLoads() throws Exception {
    assertThat(albumController).isNotNull();
  }

  @Test
  void shouldFindAllAlbums() {
    Album[] albums = restTemplate.getForObject("/album", Album[].class);
    assertThat(albums.length).isGreaterThan(1);
  }

  @Test
  void shouldFindPostWhenValidPostID() {
    ResponseEntity<Album> response = restTemplate.exchange("/album/1", HttpMethod.GET, null, Album.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
  }

  @Test
  void shouldThrowNotFoundWhenInvalidPostID() {
    ResponseEntity<Album> response = restTemplate.exchange("/album/999", HttpMethod.GET, null, Album.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  void shouldCreateNewAlbumWhenAlbumIsValid() {
    Integer[] artistArray = { 3 };
    NewAlbumDTO album = new NewAlbumDTO("boom", "asas.com", 5.0, 1974, 77, artistArray);

    ResponseEntity<Album> response = restTemplate.exchange("/album", HttpMethod.POST,
        new HttpEntity<NewAlbumDTO>(album), Album.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getName()).isEqualTo("boom");
    assertThat(response.getBody().getYear()).isEqualTo(1974);
    assertThat(response.getBody().getLength()).isEqualTo(77);
  }

  @Test
  void shouldNotCreateNewAlbumWhenValidationFails() {
    Integer[] artistArray = { 3 };
    NewAlbumDTO album = new NewAlbumDTO("", "asas.com", 5.0, 0, 77, artistArray);
    ResponseEntity<Album> response = restTemplate.exchange("/album", HttpMethod.POST,
        new HttpEntity<NewAlbumDTO>(album),
        Album.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void shouldUpdateAlbumWhenAlbumIsValid() {

  }

  @Test
  void shouldDeleteWithValidID() {
  }

}
