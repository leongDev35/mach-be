package com.leong.mach.mangaApp.manga;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leong.mach.mangaApp.chapter.Chapter;

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
@RequestMapping("manga")
@RequiredArgsConstructor
public class MangaController {
    private final MangaService mangaService;

    @PostMapping("")
    public ResponseEntity<?> saveManga(@Valid @RequestBody MangaRequest request) {

        return ResponseEntity.ok(mangaService.save(request));
    }

    @GetMapping("/{manga-id}")
    public ResponseEntity<MangaResponse> findMangaById(
            @PathVariable("manga-id") Integer mangaId) {
        return ResponseEntity.ok(mangaService.findById(mangaId));
    }

    @GetMapping("/by-genres")
    public ResponseEntity<?> findMangaByGenres(
        @RequestParam(required = false) List<String> genreNames) {
            System.out.println(genreNames);
        return ResponseEntity.ok(mangaService.findAllByGenreNames(genreNames));
    }

    @GetMapping("/by-custom")
    public ResponseEntity<?> findAllByCustomTag(
        @RequestParam(required = false) List<String> genreNames,
        @RequestParam(required = false) List<String> themeNames,
        @RequestParam(required = false) List<String> artistNames) {
        return ResponseEntity.ok(mangaService.findAllByGenresThemesAndArtists(genreNames,themeNames,artistNames));
    }

}
