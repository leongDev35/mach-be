package com.leong.mach.mangaApp.creater.mangaCreaterRole;
import jakarta.persistence.*;
import lombok.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leong.mach.mangaApp.creater.creater.Creater;
import com.leong.mach.mangaApp.manga.Manga;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "manga_creater_role")


public class MangaCreaterRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "manga_id", nullable = false)
    private Manga manga;

    @ManyToOne
    @JoinColumn(name = "creater_id", nullable = false)
    private Creater creater;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
 
    // public enum Role {
    //     AUTHOR,
    //     ARTIST
    // }
    
}
