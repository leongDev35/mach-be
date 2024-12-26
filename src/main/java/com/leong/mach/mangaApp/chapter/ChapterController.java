package com.leong.mach.mangaApp.chapter;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("chapter")
@RequiredArgsConstructor
public class ChapterController {

    private final ChapterService chapterService;

    @PostMapping("")
    public ResponseEntity<?> saveChapter(@Valid @RequestBody ChapterRequest request) {
        return ResponseEntity.ok(chapterService.save(request));
    }

    @GetMapping("path")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }

    @GetMapping("/{manga-id}")
    public List<ChapterResponse> getAllChapterInManga(@PathVariable("manga-id") Integer mangaId) {

        return chapterService.getAllChapterInManga(mangaId);
    }

}
