package com.leong.mach.mangaApp.tag;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leong.mach.mangaApp.manga.Manga;

import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
//! index không có ràng buộc duy nhất
// @Table(name = "tag", indexes = @Index(name = "idx_tag_name", columnList = "name"))
//! index có ràng buộc duy nhất
@Table(name = "tag", uniqueConstraints = @UniqueConstraint(name = "unique_tag_name", columnNames = "name"))
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private TagType type;

    // Getters and Setters

    public enum TagType {
        CONTENT_WARNING,
        FORMAT,
        GENRE,
        THEME,
        DEMOGRAPHIC
    }

    @ManyToMany(mappedBy =  "tags")
    @JsonIgnore
    private List<Manga> mangas;
}