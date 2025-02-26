package com.leong.mach.mangaApp.chapter;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.leong.mach.mangaApp.chapter.chapterDTO.ChapterBasicDTO;
import com.leong.mach.mangaApp.manga.Manga;
import com.leong.mach.user.User;

public interface ChapterRepository extends JpaRepository<Chapter, Integer> {
    // Tìm tất cả các Chapter dựa trên manga_id
    List<Chapter> findByMangaId(Integer mangaId);

    // ! tìm các chapter được tạo gần nhất của tối đa 32 manga
    @Query("""
            SELECT c FROM Chapter c
            WHERE c.id IN (
                SELECT MAX(c2.id) FROM Chapter c2
                GROUP BY c2.manga
            )
            ORDER BY c.releaseDate DESC
            """)
    List<Chapter> findLatestChaptersForMangas(Pageable pageable);

    //! return list first chapter base on mangaId
    @Query("SELECT new com.leong.mach.mangaApp.chapter.chapterDTO.ChapterBasicDTO(c.id, c.name, c.chapterNumber, c.releaseDate) "
            +
            "FROM Chapter c WHERE c.manga.id = :mangaId AND c.chapterNumber = 1")
    List<ChapterBasicDTO> findListFirstChapterByMangaId(@Param("mangaId") Integer mangaId);

    boolean existsByMangaIdAndUploadByUserIdAndChapterNumber(Integer mangaId, Integer uploadUserId,
            Integer chapterNumber);
}
