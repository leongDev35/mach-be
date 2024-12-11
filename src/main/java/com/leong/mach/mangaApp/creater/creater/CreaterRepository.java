package com.leong.mach.mangaApp.creater.creater;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreaterRepository extends JpaRepository<Creater, Integer> {
    Optional<Creater> findByName(String name);

    // Tìm tác giả theo tên hoặc một phần tên
    List<Creater> findByNameContainingIgnoreCase(String name, Pageable pageable);

    List<Creater> findAllByNameIn(List<String> createrNames);

}
