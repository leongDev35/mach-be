package com.leong.mach.mangaApp.manga;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leong.mach.mangaApp.AltTitle.AltTitle;
import com.leong.mach.mangaApp.chapter.Chapter;
import com.leong.mach.mangaApp.creater.mangaCreaterRole.MangaCreaterRole;
import com.leong.mach.mangaApp.tag.Tag;
import com.leong.mach.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import static jakarta.persistence.FetchType.EAGER;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Manga {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        @Column(unique = true)
        private String name;
        @Column(name = "release_year")
        private Year releaseYear;
        @Lob //! được sử dụng để chỉ định rằng một thuộc tính của entity sẽ được lưu trữ dưới dạng một Large Object (LOB) trong cơ sở dữ liệu.
        @Column(columnDefinition = "TEXT")
        private String description;
        private String coverImage;
        private LocalDateTime releaseAddedDate;

        @Column(name = "original_language", nullable = false)
        private String originalLanguage;

        @Enumerated(EnumType.STRING)
        @Column(name = "content_rating", nullable = false)
        private ContentRating contentRating;

        @Enumerated(EnumType.STRING)
        @Column(name = "publication_status", nullable = false)
        private PublicationStatus publicationStatus;

        @OneToMany(mappedBy = "manga")
        @JsonIgnore
        private List<Chapter> chapters;

        @ManyToMany(fetch = EAGER, cascade = CascadeType.MERGE) // ! quan trọng
        @JoinTable(name = "manga_tag", // ! tên bảng
                        joinColumns = @JoinColumn(name = "manga_id"), // ! liên kết với khóa ngoại của bảng đầu
                        inverseJoinColumns = @JoinColumn(name = "tag_id") // ! liên kết với khóa ngoại của bảng chính
                                                                          // thứ 2
        )
        private List<Tag> tags;

        @OneToMany(mappedBy = "manga", cascade = CascadeType.ALL)
        private List<MangaCreaterRole> createrRoles;

        @OneToMany(mappedBy = "manga", cascade = CascadeType.ALL)
        private List<AltTitle> altTitles;

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        public void addMangaCreaterRole(MangaCreaterRole mangaCreaterRole) {
                this.createrRoles.add(mangaCreaterRole);
        }

        public void addAltTitleList(AltTitle title) {
                this.altTitles.add(title);
        }
}
