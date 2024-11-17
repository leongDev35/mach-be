package com.leong.mach.mangaApp.manga;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.leong.mach.mangaApp.artist.Artist;
import com.leong.mach.mangaApp.author.Author;
import com.leong.mach.mangaApp.chapter.Chapter;
import com.leong.mach.mangaApp.genres.Genre;
import com.leong.mach.mangaApp.theme.Theme;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MangaResponse {
    private Integer id;
    private String name;
    private LocalDate releaseDate;
    private String description;
    private String coverImage;
    private List<Chapter> chapters;
    private List<Genre> genres;
    private List<Theme> themes;
    private List<Author> authors;
    private List<Artist> artists;
}
