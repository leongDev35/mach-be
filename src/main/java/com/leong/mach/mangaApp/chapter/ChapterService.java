package com.leong.mach.mangaApp.chapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.leong.mach.mangaApp.AltTitle.AltTitle;
import com.leong.mach.mangaApp.page.Page;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChapterService {

    private final ChapterRepository chapterRepository;
    private final ChapterMapper chapterMapper;

    public Chapter save(ChapterRequest request) {
        Chapter chapter = chapterMapper.toChapter(request);

        for (Page page : request.pages()) {
            Page newPage = Page.builder()
                    .chapter(chapter)
                    .imageUrl(page.getImageUrl())
                    .build();
            chapter.addPage(newPage);
        }
        return chapterRepository.save(chapter);
    }

    public List<ChapterResponse> getAllChapterInManga(Integer mangaId) {
        return chapterRepository.findByMangaId(mangaId).stream()
                .map(chapterMapper::toChapterResponse)
                .collect(Collectors.toList());

    }

}
