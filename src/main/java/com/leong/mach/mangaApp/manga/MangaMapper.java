package com.leong.mach.mangaApp.manga;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.leong.mach.mangaApp.artist.Artist;
import com.leong.mach.mangaApp.artist.ArtistRepository;
import com.leong.mach.mangaApp.genres.Genre;
import com.leong.mach.mangaApp.genres.GenreRepository;
import com.leong.mach.mangaApp.theme.Theme;
import com.leong.mach.mangaApp.theme.ThemeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MangaMapper {

    private final GenreRepository genreRepository;
    private final ThemeRepository themeRepository;
    private final ArtistRepository artistRepository;

    public Manga toManga(MangaRequest request) {
        List<Genre> genres = genreRepository.findAllById(request.listGenreId());
        List<Theme> themes = themeRepository.findAllById(request.listThemeId());
        List<Artist> artists = artistRepository.findAllById(request.listArtistId());
        System.out.println(genres);
        return Manga.builder()
        .name(request.name())
        .genres(genres)
        .themes(themes)
        .artists(artists)
        .coverImage(request.coverImage())
        .releaseDate(request.releaseDate())
        .description(request.description())
        .build();
    }

    //! chỉ định nhưng trường được xuất hiện
    public MangaResponse toMangaResponse(Manga manga) { 
        return MangaResponse.builder()
        .name(manga.getName())
        .id(manga.getId())
        .genres(manga.getGenres())
        .artists(manga.getArtists())
        .authors(manga.getAuthors())
        .coverImage(manga.getCoverImage())
        .build();
    }
}
