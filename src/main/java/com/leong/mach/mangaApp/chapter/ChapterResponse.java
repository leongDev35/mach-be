package com.leong.mach.mangaApp.chapter;

import java.time.LocalDate;

import com.leong.mach.mangaApp.manga.Manga;
import com.leong.mach.user.UserResponse;

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
public class ChapterResponse {
    
    private String name;
    private Integer chapterNumber;
    private LocalDate releaseDate;
    private UserResponse user;
    private Manga manga;

}
