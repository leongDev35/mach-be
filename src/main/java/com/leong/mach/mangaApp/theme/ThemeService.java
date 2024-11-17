package com.leong.mach.mangaApp.theme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.List;

@Service
public class ThemeService {

    // @Autowired
    // private ThemeRepository themeRepository;

    // @PostConstruct
    // public void initThemes() {
    //     List<String> genres = List.of(
    //         "Aliens", "Animals", "Cooking", "Crossdressing", "Delinquents", "Demons", "Genderswap",
    // "Ghosts", "Gyaru", "Harem", "Incest", "Loli", "Mafia", "Magic", "Martial Arts", "Military",
    // "Monster Girls", "Monsters", "Music", "Ninja", "Office Workers", "Police", "Post-Apocalyptic",
    // "Reincarnation", "Reverse Harem", "Samurai", "School Life", "Shota", "Supernatural", "Survival",
    // "Time Travel", "Traditional Games", "Vampires", "Video Games", "Villainess", "Virtual Reality", "Zombies"
    //     );

    //     for (String genre : genres) {
    //         if (!themeRepository.existsByName(genre)) {
    //             Theme theme = new Theme();
    //             theme.setName(genre);
    //             themeRepository.save(theme);
    //         }
    //     }
    // }
}
