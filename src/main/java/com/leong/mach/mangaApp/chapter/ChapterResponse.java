package com.leong.mach.mangaApp.chapter;


import java.util.List;

import com.leong.mach.mangaApp.manga.Manga;
import com.leong.mach.mangaApp.page.PageResponse;
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
    private Integer id;
    private String name;
    private Integer chapterNumber;
    private String releaseDate;
    private String language;
    private UserResponse user;
    private List<PageResponse> pages;
    private Manga manga;
}
