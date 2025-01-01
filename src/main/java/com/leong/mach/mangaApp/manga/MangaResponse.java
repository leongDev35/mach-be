package com.leong.mach.mangaApp.manga;

import java.time.Year;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.leong.mach.mangaApp.AltTitle.AltTitle;
import com.leong.mach.mangaApp.chapter.Chapter;
import com.leong.mach.mangaApp.creater.mangaCreaterRole.MangaCreaterRole;
import com.leong.mach.mangaApp.tag.Tag;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MangaResponse {
    private Integer id;
    private String name;
    private Year releaseYear;
    private String description;
    private String coverImage;
    private String originalLanguage;
    private ContentRating contentRating;
    private PublicationStatus publicationStatus;
    private List<Chapter> chapters;
    private List<AltTitle> altTitles;
    private List<MangaCreaterRole> createrRoles;
    private List<MangaCreaterRole> authors;
    private List<MangaCreaterRole> artists;
    private List<Tag> tags;
}
