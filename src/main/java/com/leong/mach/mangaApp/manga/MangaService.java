package com.leong.mach.mangaApp.manga;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MangaService {

    private final MangaRepository mangaRepository;
    private final MangaMapper mangaMapper;

    public Integer save(MangaRequest request) {
        Manga manga = mangaMapper.toManga(request);
        return mangaRepository.save(manga).getId();
    }

    public MangaResponse findById(Integer mangaId) {
        return mangaRepository.findById(mangaId)
                .map(mangaMapper::toMangaResponse)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID: " + mangaId));
    }

    public List<Manga> findAllByGenreNames(List<String> genreNames) {


        return mangaRepository.findAllByGenreNames(
            genreNames, 
            genreNames.size()
        );
    }

    public List<Manga> findAllByGenresThemesAndArtists(List<String> genreNames, List<String> themeNames, List<String> artistNames) {
        System.out.println(genreNames);
        System.out.println(themeNames);
        System.out.println(artistNames);
        return mangaRepository.findAllByGenresThemesAndArtists(
            genreNames, 
            themeNames, 
            artistNames, 
            genreNames.size(), 
            themeNames.size(), 
            artistNames.size()
        );
    }

}
