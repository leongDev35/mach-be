package com.leong.mach.mangaApp.manga;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;

public record MangaRequest(
    @NotNull(message = "Name is mandatory") String name,
    List<Integer> listGenreId,
    List<Integer> listThemeId,
    List<Integer> listAuthorId,
    List<Integer> listArtistId,
    @JsonFormat(pattern = "dd-MM-yyyy") 
    LocalDate releaseDate,
    String description,
    String coverImage
) {
}