package com.leong.mach.mangaApp.chapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.leong.mach.mangaApp.AltTitle.AltTitle;
import com.leong.mach.mangaApp.manga.MangaResponse;
import com.leong.mach.mangaApp.page.Page;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChapterService {

    private final ChapterRepository chapterRepository;
    private final ChapterMapper chapterMapper;

    public Chapter save(ChapterRequest request) {
        //! check if chapter number already exist, return error if exist
        boolean isChapterExist = chapterRepository.existsByMangaIdAndUploadByUserIdAndChapterNumber(request.mangaId(), request.uploadUserId(),request.chapterNumber());
        System.out.println("isChapterExist");
        System.out.println(isChapterExist);
        if (isChapterExist) {
            throw new RuntimeException("Chapter already exist");
        }
        //! end check
        Chapter chapter = chapterMapper.toChapter(request);
        for (Page page : request.pages()) {
            Page newPage = Page.builder()
                    .chapter(chapter)
                    .imageUrl(page.getImageUrl())
                    .pageOrder(page.getPageOrder())
                    .build();
            chapter.addPage(newPage);
        }
        return chapterRepository.save(chapter);
    }

    public ChapterResponse findById(Integer chapterId) {
        return ChapterMapper.toChapterDetail(chapterRepository.findById(chapterId).get());
    }

    public List<ChapterResponse> getAllChapterInManga(Integer mangaId) {
        return chapterRepository.findByMangaId(mangaId).stream()
                .map(ChapterMapper::toChapterResponse)
                .collect(Collectors.toList());
    }

    public List<ChapterResponse> getLatestChaptersForMangas() {

        return chapterRepository.findLatestChaptersForMangas(PageRequest.of(0, 32)).stream()
                .map(ChapterMapper::toChapterResponseInLastestUpdate)
                .collect(Collectors.toList());
    }

}
