package com.leong.mach.mangaApp.chapter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leong.mach.mangaApp.manga.Manga;
import com.leong.mach.mangaApp.page.Page;
import com.leong.mach.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer chapterNumber;

    private LocalDate releaseDate;

    @OneToMany(mappedBy = "chapter")
    @JsonIgnore
    private List<Page> page;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User uploadByUser;

    @ManyToOne
    @JoinColumn(name = "manga_id")
    private Manga manga;
}
