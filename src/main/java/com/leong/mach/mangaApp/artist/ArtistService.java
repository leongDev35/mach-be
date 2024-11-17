package com.leong.mach.mangaApp.artist;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.leong.mach.mangaApp.manga.Manga;
import com.leong.mach.user.User;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistRepository artistRepository;

    //! Đăng ký Artist
    public void register(ArtistRequest request) {
        // ! check artist name 

        Boolean artistCheck = artistRepository.findByName(request.name()).isPresent();
        if (artistCheck) {
            throw new IllegalStateException("Artist with name '" + request.name() + "' already exists.");
        }

        var artist = Artist.builder() //! tạo 1 User object từ request
                .name(request.name())
                .build();
                artistRepository.save(artist); //! lưu user vào trong DB
    }

    //! Get All Manga
    public List<Manga> getAllMangaByArtist(Integer artistId) {
        Optional<Artist> artistOptional = artistRepository.findById(artistId);
        if (artistOptional.isPresent()) {
            Artist artist = artistOptional.get();
            return artist.getMangas();  // Trả về danh sách Role
        } else {
            throw new RuntimeException("Artist not found with artist id: " + artistId);
        }
    }

}
