package com.leong.mach.mangaApp.chapter;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.leong.mach.mangaApp.manga.Manga;
import com.leong.mach.mangaApp.manga.MangaRepository;
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
        Optional<Manga> mangaOptional = mangaRepository.findById(request.mangaId());
        if (userOptional.isPresent() && mangaOptional.isPresent()) {
            User user = userOptional.get();
            Manga manga = mangaOptional.get();
            return Chapter.builder()
                    .name(request.name())
                    .releaseDate(request.releaseDate())
                    .chapterNumber(request.chapterNumber())
                    .uploadByUser(user)
                    .manga(manga)
                    .build();
        } else {
            throw new RuntimeException("User not found with id: " + request.uploadUserId());
        }

    }

    public ChapterResponse toChapterResponse(Chapter chapter) {
        UserResponse userResponse = userMapper.toUserResponse(chapter.getUploadByUser());
        return ChapterResponse.builder()
                .name(chapter.getName())
                .chapterNumber(chapter.getChapterNumber())
                .releaseDate(chapter.getReleaseDate())
                .manga(chapter.getManga())
                .user(userResponse)
                .build();
    }

}
