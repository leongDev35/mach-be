package com.leong.mach.mangaApp.manga;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.leong.mach.mangaApp.AltTitle.AltTitle;

import jakarta.validation.constraints.NotNull;

public record MangaRequest(
        @NotNull(message = "Name is mandatory") String name,
        List<String> listTags,
        List<String> listAuthor,
        List<String> listArtist,
        List<AltTitle> altTitles,
        ContentRating contentRating,
        PublicationStatus publicationStatus,
        String originalLanguage,
        LocalDateTime releaseAddedDate,
        Year releaseYear,
        String description,
        String coverImage) {
}