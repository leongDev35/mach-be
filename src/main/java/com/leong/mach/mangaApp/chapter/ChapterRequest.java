package com.leong.mach.mangaApp.chapter;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.leong.mach.mangaApp.manga.Manga;
import com.leong.mach.user.User;

/**
 * ArtistRequest
 */
public record ChapterRequest(
        @NotNull(message = "Name is mandatory") 
        @NotEmpty(message = "name not null") 
        String name,
        @NotNull(message = "Chapter number is mandatory")
        Integer chapterNumber,
        LocalDate releaseDate,
        Integer uploadUserId,
        Integer mangaId) {

}