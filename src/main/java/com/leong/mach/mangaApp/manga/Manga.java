package com.leong.mach.mangaApp.manga;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leong.mach.mangaApp.artist.Artist;
import com.leong.mach.mangaApp.author.Author;
import com.leong.mach.mangaApp.chapter.Chapter;
import com.leong.mach.mangaApp.genres.Genre;
import com.leong.mach.mangaApp.theme.Theme;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static jakarta.persistence.FetchType.EAGER;

enum TransactionType {
        ONGOING,
        COMPLETED,
        HIATUS, // ! bị gián đoạn
        CANCELLED
}

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Manga {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(unique = true)
        private String name;
        @JsonFormat(pattern = "dd-MM-yyyy")
        private LocalDate releaseDate;
        private String description;
        private String coverImage;
        @OneToMany(mappedBy = "manga")
        @JsonIgnore
        private List<Chapter> chapters;

        @ManyToMany(fetch = EAGER, cascade = CascadeType.MERGE) // ! quan trọng
        @JoinTable(name = "manga_genre", // ! tên bảng
                        joinColumns = @JoinColumn(name = "manga_id"), // ! liên kết với khóa ngoại của bảng đầu
                        inverseJoinColumns = @JoinColumn(name = "genre_id") // ! liên kết với khóa ngoại của bảng chính
                                                                            // thứ 2
        )
        private List<Genre> genres;

        @ManyToMany(fetch = EAGER, cascade = CascadeType.MERGE)
        @JoinTable(name = "manga_theme", joinColumns = @JoinColumn(name = "manga_id"), inverseJoinColumns = @JoinColumn(name = "theme_id"))
        private List<Theme> themes;

        @ManyToMany(fetch = EAGER, cascade = CascadeType.MERGE)
        @JoinTable(name = "manga_author", joinColumns = @JoinColumn(name = "manga_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
        private List<Author> authors;

        @ManyToMany(fetch = EAGER, cascade = CascadeType.MERGE)
        @JoinTable(name = "manga_artist", joinColumns = @JoinColumn(name = "manga_id"), inverseJoinColumns = @JoinColumn(name = "artist_id"))
        private List<Artist> artists;
}
