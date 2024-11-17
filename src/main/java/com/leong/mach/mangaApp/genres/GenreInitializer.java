package com.leong.mach.mangaApp.genres;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class GenreInitializer implements CommandLineRunner {
    @Autowired
    private GenreRepository genreRepository;

    @Override
    public void run(String... args) throws Exception {
        // Danh sách các theme cần lưu vào database
        List<String> genreNames = List.of(
            "Action", "Adventure", "Boys' Love", "Comedy", "Crime", 
            "Drama", "Fantasy", "Girls' Love", "Historical", "Horror", 
            "Isekai", "Magical Girls", "MeRcha", "Medical", "Mystery", 
            "Philosophical", "Psychological", "Romance", "Sci-Fi", 
            "Slice of Life", "Sports", "Superhero", "Thriller", 
            "Tragedy", "Wuxia"
        );
        
        for (String genre : genreNames) {
            if (!genreRepository.existsByName(genre)) {
                Genre theme = new Genre();
                theme.setName(genre);
                genreRepository.save(theme);
            }
        }
    }
}

