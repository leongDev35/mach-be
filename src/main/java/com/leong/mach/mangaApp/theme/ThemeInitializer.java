package com.leong.mach.mangaApp.theme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ThemeInitializer implements CommandLineRunner {
    @Autowired
    private ThemeRepository themeRepository;



    @Override
    public void run(String... args) throws Exception {
        // Danh sách các theme cần lưu vào database
        List<String> themeNames = List.of(
            "Aliens", "Animals", "Cooking", "Crossdressing", "Delinquents", "Demons", "Genderswap",
    "Ghosts", "Gyaru", "Harem", "Incest", "Loli", "Mafia", "Magic", "Martial Arts", "Military",
    "Monster Girls", "Monsters", "Music", "Ninja", "Office Workers", "Police", "Post-Apocalyptic",
    "Reincarnation", "Reverse Harem", "Samurai", "School Life", "Shota", "Supernatural", "Survival",
    "Time Travel", "Traditional Games", "Vampires", "Video Games", "Villainess", "Virtual Reality", "Zombies"
            // Thêm các theme khác vào đây
        );
        
        for (String genre : themeNames) {
            if (!themeRepository.existsByName(genre)) {
                Theme theme = new Theme();
                theme.setName(genre);
                themeRepository.save(theme);
            }
        }
    }
}
