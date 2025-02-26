package com.leong.mach.mangaApp.chapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.leong.mach.common.CommonFunc;
import com.leong.mach.common.TimeAgoUtil;
import com.leong.mach.mangaApp.manga.Manga;
import com.leong.mach.mangaApp.manga.MangaMapper;
import com.leong.mach.mangaApp.manga.MangaRepository;
import com.leong.mach.mangaApp.page.Page;
import com.leong.mach.mangaApp.page.PageMapper;
import com.leong.mach.user.User;
import com.leong.mach.user.UserMapper;
import com.leong.mach.user.UserRepository;
import com.leong.mach.user.UserResponse;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChapterMapper {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MangaRepository mangaRepository;

    public Chapter toChapter(ChapterRequest request) {
        Optional<User> userOptional = userRepository.findById(request.uploadUserId());

        List<Page> emptyPages = new ArrayList<>();

        if (!userOptional.isPresent()) {
            throw new RuntimeException("User not found with id: " + request.uploadUserId());
        }

        Optional<Manga> mangaOptional = mangaRepository.findById(request.mangaId());
        if (!mangaOptional.isPresent()) {
            throw new RuntimeException("Manga not found with id: " + request.mangaId());
        }

        Manga manga = mangaOptional.get();
        return Chapter.builder()
                .name(request.chapterName())
                .releaseDate(request.releaseDate())
                .chapterNumber(request.chapterNumber())
                .uploadByUser(userOptional.get())
                .manga(manga)
                .pages(emptyPages)
                .build();

    }

    public static ChapterResponse toChapterResponse(Chapter chapter) {
        UserResponse userResponse = UserMapper.toUserResponse(chapter.getUploadByUser());
        return ChapterResponse.builder()
                .id(chapter.getId())
                .name(chapter.getName())
                .language(CommonFunc.COUNTRY_FLAG_MAP.get("Vietnamese"))
                .chapterNumber(chapter.getChapterNumber())
                .releaseDate(TimeAgoUtil.timeAgo(chapter.getReleaseDate()))
                .user(userResponse)
                .build();
    }

    public static ChapterResponse toChapterResponseInChapterPage(Chapter chapter) {
        return ChapterResponse.builder()
                .id(chapter.getId())
                .chapterNumber(chapter.getChapterNumber())
                .build();
    }

    public static List<ChapterResponse> toChapterResponseList(List<Chapter> chapters) {
        return chapters.stream()
                .map(ChapterMapper::toChapterResponse)
                .collect(Collectors.toList());
    }

    public static ChapterResponse toChapterResponseInLastestUpdate(Chapter chapter) {
        return ChapterResponse.builder()
                .id(chapter.getId())
                .chapterNumber(chapter.getChapterNumber())
                .name(chapter.getName())
                .manga(MangaMapper.toMangaResponseInLatestUpdate(chapter.getManga()))
                .releaseDate(TimeAgoUtil.timeAgo(chapter.getReleaseDate()))
                .language(CommonFunc.COUNTRY_FLAG_MAP.get("Vietnamese"))
                .build();
    }

    public static List<ChapterResponse> toChapterResponseListInLastestUpdate(List<Chapter> chapters) {
        return chapters.stream()
                .map(ChapterMapper::toChapterResponseInLastestUpdate)
                .collect(Collectors.toList());
    }

    public static List<ChapterResponse> toChapterResponseListInChapterPage(List<Chapter> chapters) {
        return chapters.stream()
                .map(ChapterMapper::toChapterResponseInChapterPage)
                .collect(Collectors.toList());
    }


    public static ChapterResponse toChapterDetail(Chapter chapter) {
        UserResponse userResponse = UserMapper.toUserResponse(chapter.getUploadByUser());
        return ChapterResponse.builder()
                .id(chapter.getId())
                .name(chapter.getName())
                .pages(PageMapper.toListPageResponse(chapter.getPages()))
                .manga(MangaMapper.toMangaResponseInChapterPage(chapter.getManga()))
                .chapterNumber(chapter.getChapterNumber())
                .user(userResponse)
                .build();
    }

}
