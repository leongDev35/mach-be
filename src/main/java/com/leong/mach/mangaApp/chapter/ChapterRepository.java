package com.leong.mach.mangaApp.chapter;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepository  extends JpaRepository<Chapter,Integer>{
     // Tìm tất cả các Chapter dựa trên manga_id
    List<Chapter> findByMangaId(Integer mangaId);
}
