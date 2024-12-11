package com.leong.mach.mangaApp.AltTitle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leong.mach.mangaApp.manga.Manga;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AltTitle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String language;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "manga_id", nullable = false)
    private Manga manga;
}
