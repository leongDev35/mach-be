package com.leong.mach.mangaApp.artist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.leong.mach.mangaApp.genres.Genre;

@Component
public class ArtistInitializer implements CommandLineRunner {
    @Autowired
    private ArtistRepository artistRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Artist> artistsList = List.of(
                Artist.builder().name("Artist Name 1").avatarUrl("https://example.com/avatar1.jpg")
                        .biography("Biography for artist 1").build(),
                Artist.builder().name("Artist Name 2").avatarUrl("https://example.com/avatar2.jpg")
                        .biography("Biography for artist 2").build(),
                Artist.builder().name("Artist Name 3").avatarUrl("https://example.com/avatar3.jpg")
                        .biography("Biography for artist 3").build(),
                Artist.builder().name("Oda").avatarUrl("https://example.com/avatar3.jpg")
                        .biography("Biography for artist 3").build());

        for (Artist artist : artistsList) {
            if (!artistRepository.findByName(artist.getName()).isPresent()) {
                artistRepository.save(artist);
            }
        }
    }
}
