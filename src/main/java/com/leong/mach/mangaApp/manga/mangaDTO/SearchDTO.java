package com.leong.mach.mangaApp.manga.mangaDTO;

import java.util.List;

import com.leong.mach.mangaApp.creater.creater.Creater;

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
public class SearchDTO {
    private List<MangaBasicDTO> mangas;
    private List<Creater> creaters;
}
