package com.leong.mach.mangaApp.theme;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<Theme, Integer> {
    boolean existsByName(String name);
}

