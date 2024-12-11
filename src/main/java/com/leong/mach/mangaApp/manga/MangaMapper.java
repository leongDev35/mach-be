package com.leong.mach.mangaApp.manga;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.leong.mach.mangaApp.AltTitle.AltTitle;
import com.leong.mach.mangaApp.creater.creater.Creater;
import com.leong.mach.mangaApp.creater.creater.CreaterRepository;
import com.leong.mach.mangaApp.creater.mangaCreaterRole.MangaCreaterRole;
import com.leong.mach.mangaApp.creater.mangaCreaterRole.MangaCreaterRoleRepository;
import com.leong.mach.mangaApp.tag.Tag;
import com.leong.mach.mangaApp.tag.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MangaMapper {

    private final TagRepository tagRepository;


    public Manga toManga(MangaRequest request) {
        List<Tag> tags = tagRepository.findAllByNameIn(request.listTags());
        List<MangaCreaterRole> emptyRoleList = new ArrayList<>();
        List<AltTitle> emptyAltTitles = new ArrayList<>();
        
        Manga newManga = Manga.builder()
                .name(request.name())
                .tags(tags)
                .createrRoles(emptyRoleList)
                .altTitles(emptyAltTitles)
                .contentRating(request.contentRating())
                .publicationStatus(request.publicationStatus())
                .originalLanguage(request.originalLanguage())
                .coverImage(request.coverImage())
                .releaseYear(request.releaseYear())
                .description(request.description())
                .build();
                
        return newManga;
    }

    // ! chỉ định nhưng trường được xuất hiện
    public MangaResponse toMangaResponse(Manga manga) {
        return MangaResponse.builder()
                .name(manga.getName())
                .id(manga.getId())
                .tags(manga.getTags())
                .coverImage(manga.getCoverImage())
                .build();
    }

     public MangaResponse toMangaFindByIdUser(Manga manga) {
        return MangaResponse.builder()
                .name(manga.getName())
                .id(manga.getId())
                .originalLanguage(manga.getOriginalLanguage())
                .description(manga.getDescription())
                .coverImage(manga.getCoverImage())
                .build();
    }
}
