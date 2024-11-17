package com.leong.mach.mangaApp.artist;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Integer>{
    Optional<Artist> findByName(String name);
}