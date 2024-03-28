package com.rp.albumtracker.DTO;

import java.util.Set;
import jakarta.validation.constraints.NotEmpty;

public record ArtistDTO(
    @NotEmpty Integer id,
    @NotEmpty String name,
    String picture,
    Set<String> albumNames,
    Set<String> genres) {
}
