package com.leong.mach.mangaApp.genres;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer>  {
    boolean existsByName(String name);
}
