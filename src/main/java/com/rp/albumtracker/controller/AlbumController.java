package com.rp.albumtracker.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.rp.albumtracker.DTO.NewAlbumDTO;
import com.rp.albumtracker.exception.ResourceNotFoundException;
import com.rp.albumtracker.model.Album;
import com.rp.albumtracker.repository.AlbumRepository;
import com.rp.albumtracker.service.AlbumService;
import com.rp.albumtracker.service.S3Service;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/album")
@CrossOrigin
public class AlbumController {
  private final AlbumService albumService;
  private final S3Service s3Service;
  private final AlbumRepository albumRepository;

  public AlbumController(AlbumService albumService, AlbumRepository albumRepository, S3Service s3Service) {
    this.albumService = albumService;
    this.albumRepository = albumRepository;
    this.s3Service = s3Service;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public Album create(@RequestBody @Valid NewAlbumDTO newAlbum) {
    return albumService.create(newAlbum);
  }

  @GetMapping("/picture")
  public void readText() throws IOException {
    s3Service.readFile();
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/picture")
  public String uploadPicture(@RequestParam(name = "file") MultipartFile multipartFile) throws IOException {
    File convFile = new File(multipartFile.getOriginalFilename());
    FileOutputStream fos = new FileOutputStream(convFile);
    fos.write(multipartFile.getBytes());
    fos.close();

    InputStream targetStream = new FileInputStream(convFile);
    return s3Service.uploadFile(targetStream, multipartFile.getOriginalFilename());
  }

  // @RolesAllowed("ROLE_USER")
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("")
  public List<Album> findAll() {
    return albumRepository.findAll();
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/{id}")
  public Optional<Album> findById(@PathVariable Integer id) {
    return Optional
        .ofNullable(albumRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Album with ID " + id + " not found")));
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PutMapping("/{id}")
  public void replace(@PathVariable Integer id, @RequestBody Map<String, Object> albumMap) {
    albumService.replace(albumMap, id);
  }

  @ResponseStatus(HttpStatus.OK)
  // @RolesAllowed("ROLE_USER")
  @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
  public Album update(@PathVariable Integer id, @RequestBody JsonPatch jsonPatch)
      throws JsonProcessingException, IllegalArgumentException, JsonPatchException {
    return albumService.update(id, jsonPatch);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  @Transactional
  public void delete(@PathVariable Integer id) {
    albumRepository.deleteById(id);
  }
}
