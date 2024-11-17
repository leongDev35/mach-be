package com.leong.mach.mangaApp.artist;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * ArtistRequest
 */
public record ArtistRequest(
        @NotNull(message = "Name is mandatory") String name) {
}
