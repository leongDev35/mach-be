package com.leong.mach.mangaApp.manga;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.leong.mach.mangaApp.AltTitle.AltTitle;
import com.leong.mach.mangaApp.AltTitle.AltTitleMapper;
import com.leong.mach.mangaApp.chapter.ChapterMapper;
import com.leong.mach.mangaApp.chapter.ChapterRepository;
import com.leong.mach.mangaApp.chapter.ChapterResponse;
import com.leong.mach.mangaApp.chapter.chapterDTO.ChapterBasicDTO;
import com.leong.mach.mangaApp.creater.creater.Creater;
import com.leong.mach.mangaApp.creater.creater.CreaterRepository;
import com.leong.mach.mangaApp.creater.mangaCreaterRole.MangaCreaterRole;
import com.leong.mach.mangaApp.creater.mangaCreaterRole.MangaCreaterRoleRepository;
import com.leong.mach.mangaApp.creater.mangaCreaterRole.Role;
import com.leong.mach.mangaApp.manga.mangaDTO.MangaBasicDTO;
import com.leong.mach.mangaApp.manga.mangaDTO.RecentlyAdded;
import com.leong.mach.mangaApp.tag.Tag;
import com.leong.mach.mangaApp.tag.TagDTO;
import com.leong.mach.mangaApp.tag.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MangaMapper {

        private final TagRepository tagRepository;
        private final ChapterRepository chapterRepository;

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
                                .releaseAddedDate(request.releaseAddedDate())
                                .originalLanguage(request.originalLanguage())
                                .coverImage(request.coverImage())
                                .releaseYear(request.releaseYear())
                                .description(request.description())
                                .build();

                return newManga;
        }

        // ! chỉ định nhưng trường được xuất hiện
        public MangaResponse toMangaResponse(Manga manga) {
                // Lọc danh sách createrRoles để lấy danh sách author
                List<MangaCreaterRole> authors = manga.getCreaterRoles().stream()
                                .filter(role -> Role.AUTHOR.equals(role.getRole()))
                                .collect(Collectors.toList());

                // Lọc danh sách createrRoles để lấy danh sách artist
                List<MangaCreaterRole> artists = manga.getCreaterRoles().stream()
                                // .filter(role -> "ARTIST".equals(role.getRole())) // Lọc theo vai trò ARTIST
                                .filter(role -> Role.ARTIST.equals(role.getRole()))
                                .collect(Collectors.toList());

                // lọc các trường chapter được trả về
                List<ChapterResponse> chapters = ChapterMapper.toChapterResponseList(manga.getChapters());

                // tag
                Map<String, List<TagDTO>> groupedTags = manga.getTags().stream()
                                .collect(Collectors.groupingBy(
                                                tag -> tag.getType().toString(),
                                                Collectors.mapping(
                                                                tag -> new TagDTO(tag.getId(), tag.getName()),
                                                                Collectors.toList() // Value: List<TagDTO>
                                                )));

                return MangaResponse.builder()
                                .name(manga.getName())
                                .id(manga.getId())
                                .tags(manga.getTags())
                                .tagResponse(groupedTags)
                                .coverImage(manga.getCoverImage())
                                .description(manga.getDescription())
                                .originalLanguage(manga.getOriginalLanguage())
                                .contentRating(manga.getContentRating())
                                .publicationStatus(manga.getPublicationStatus())
                                .chapters(chapters)
                                .altTitleResponse(AltTitleMapper.toChapterResponseList(manga.getAltTitles()))
                                .createrRoles(manga.getCreaterRoles())
                                .authors(authors)
                                .artists(artists)
                                .releaseYear(manga.getReleaseYear())
                                .build();
        }

        public MangaResponse toMangaResponseOnlyNameManga(Manga manga) {
                return MangaResponse.builder()
                                .name(manga.getName())
                                .id(manga.getId())
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

        public static MangaResponse toMangaResponseInChapterPage(Manga manga) {
                List<ChapterResponse> chapters = ChapterMapper.toChapterResponseListInChapterPage(manga.getChapters());

                return MangaResponse.builder()
                                .name(manga.getName())
                                .id(manga.getId())
                                .chapters(chapters)
                                .build();
        }

        public static MangaResponse toMangaResponseInLatestUpdate(Manga manga) {

                return MangaResponse.builder()
                                .name(manga.getName())
                                .id(manga.getId())
                                .coverImage(manga.getCoverImage())
                                .build();
        }

        public static MangaResponse toMangaResponseWithTag(Manga manga) {
                // Lọc danh sách createrRoles để lấy danh sách author
                List<MangaCreaterRole> authors = manga.getCreaterRoles().stream()
                                .filter(role -> Role.AUTHOR.equals(role.getRole()))
                                .collect(Collectors.toList());

                // Lọc danh sách createrRoles để lấy danh sách artist
                List<MangaCreaterRole> artists = manga.getCreaterRoles().stream()
                                // .filter(role -> "ARTIST".equals(role.getRole())) // Lọc theo vai trò ARTIST
                                .filter(role -> Role.ARTIST.equals(role.getRole()))
                                .collect(Collectors.toList());
                return MangaResponse.builder()
                                .name(manga.getName())
                                .id(manga.getId())
                                .description(manga.getDescription())
                                .coverImage(manga.getCoverImage())
                                .tags(manga.getTags())
                                .authors(authors)
                                .artists(artists)
                                .build();
        }

        

        public List<RecentlyAdded> toRecentlyAddedList(List<MangaBasicDTO> mangaDTOList) {
                return mangaDTOList.stream()
                                .map(manga -> RecentlyAdded.builder()
                                                .manga(manga)
                                                .firstChapterList(chapterRepository
                                                                .findListFirstChapterByMangaId(manga.getId()))
                                                .build())
                                .collect(Collectors.toList());
                
        }
}
