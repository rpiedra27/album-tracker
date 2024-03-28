package com.rp.albumtracker.DTO;

import org.hibernate.validator.constraints.Range;
import jakarta.validation.constraints.NotEmpty;

public record NewAlbumDTO(
    @NotEmpty String name,
    String cover,
    Double rating,
    @Range(min = 1, max = 2024) Integer year,
    @Range(min = 1) Integer length,
    @NotEmpty Integer[] artists) {
}
