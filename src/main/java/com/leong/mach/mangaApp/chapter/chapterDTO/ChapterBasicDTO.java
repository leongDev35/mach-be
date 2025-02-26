package com.leong.mach.mangaApp.chapter.chapterDTO;

import java.time.LocalDateTime;

import com.leong.mach.common.TimeAgoUtil;
import com.leong.mach.user.User;

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
public class ChapterBasicDTO {
    private Integer id;
    private String name;
    private Integer chapterNumber;
    private String releaseDate;

    public ChapterBasicDTO(Integer id, String name, Integer chapterNumber, LocalDateTime releaseDate) {
        this.id = id;
        this.name = name;
        this.chapterNumber = chapterNumber;
        this.releaseDate = TimeAgoUtil.timeAgoChapter(releaseDate); // Chuyển đổi tại constructor
    }
}
