package com.leong.mach.mangaApp.manga.mangaDTO;

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
public class MangaBasicDTO {
    private Integer id;
    private String name;
    private String coverImage;
    private String description;
}
