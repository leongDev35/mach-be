package com.leong.mach.mangaApp.manga.mangaDTO;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.leong.mach.mangaApp.chapter.chapterDTO.ChapterBasicDTO;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecentlyAdded {
    MangaBasicDTO manga;
    List<ChapterBasicDTO> firstChapterList;
}
