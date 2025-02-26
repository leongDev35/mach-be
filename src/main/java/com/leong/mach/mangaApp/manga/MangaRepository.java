package com.leong.mach.mangaApp.manga;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.leong.mach.mangaApp.creater.creater.Creater;
import com.leong.mach.mangaApp.manga.mangaDTO.MangaBasicDTO;

public interface MangaRepository extends JpaRepository<Manga, Integer> {
    @Query("SELECT new com.leong.mach.mangaApp.manga.mangaDTO.MangaBasicDTO(m.id, m.name, m.coverImage, m.description) FROM Manga m WHERE m.name LIKE %:name%")
    List<MangaBasicDTO> findByNameContainingIgnoreCase(String name, Pageable pageable);

    //! tìm các manga được add gần nhất, tối đa 15 manga
    @Query("SELECT new com.leong.mach.mangaApp.manga.mangaDTO.MangaBasicDTO(m.id, m.name, m.coverImage, m.description) FROM Manga m ORDER BY m.releaseAddedDate DESC")
    List<MangaBasicDTO> findRecentlyAddedManga(Pageable pageable);

    Page<Manga> findAll(Pageable pageable);
    
    List<Manga> findByUserId(Integer userId);
    // // ! tìm tất cả manga có tất cả GENRE
    // @Query("SELECT m FROM Manga m JOIN m.genres g WHERE g.name IN :genreNames
    // GROUP BY m.id HAVING COUNT(DISTINCT g.name) = :size")
    // List<Manga> findAllByGenreNames(@Param("genreNames") List<String> genreNames,
    // @Param("size") long size);

    // // ! tìm tất cả manga có tag như này
    // @Query("SELECT m FROM Manga m " +
    // "JOIN m.genres g " +
    // "JOIN m.themes t " +
    // "JOIN m.artists a " +
    // "WHERE g.name IN :genreNames " +
    // "AND t.name IN :themeNames " +
    // "AND a.name IN :artistNames " +
    // "GROUP BY m.id " +
    // "HAVING COUNT(DISTINCT g.name) = :genreSize " +
    // "AND COUNT(DISTINCT t.name) = :themeSize " +
    // "AND COUNT(DISTINCT a.name) = :artistSize")
    // List<Manga> findAllByGenresThemesAndArtists(
    // @Param("genreNames") List<String> genreNames,
    // @Param("themeNames") List<String> themeNames,
    // @Param("artistNames") List<String> artistNames,
    // @Param("genreSize") long genreSize,
    // @Param("themeSize") long themeSize,
    // @Param("artistSize") long artistSize);
}
