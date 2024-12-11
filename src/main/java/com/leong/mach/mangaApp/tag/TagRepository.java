package com.leong.mach.mangaApp.tag;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer>  {
    boolean existsByName(String name);

    List<Tag> findAllByNameIn(List<String> tagNames);

}
