package com.leong.mach.mangaApp.tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.leong.mach.mangaApp.tag.Tag.TagType;

@Component
public class TagInitializer implements CommandLineRunner {


    @Autowired
    private TagRepository tagRepository;


    private void handleInitializeTag(List<String> tagNameList, TagType type) {
        for (String tagName : tagNameList) {
            if (!tagRepository.existsByName(tagName)) {
                Tag tag = Tag.builder()
                        .name(tagName) 
                        .type(type) 
                        .build();
                tagRepository.save(tag);
            }
        }
    }

    @Override
    public void run(String... args) throws Exception {
        // Danh sách các theme cần lưu vào database
        List<String> themeNames = List.of(
                "Aliens", "Animals", "Cooking", "Crossdressing", "Delinquents", "Demons", "Genderswap",
                "Ghosts", "Gyaru", "Harem", "Incest", "Loli", "Mafia", "Magic", "Martial Arts", "Military",
                "Monster Girls", "Monsters", "Music", "Ninja", "Office Workers", "Police", "Post-Apocalyptic",
                "Reincarnation", "Reverse Harem", "Samurai", "School Life", "Shota", "Supernatural", "Survival",
                "Time Travel", "Traditional Games", "Vampires", "Video Games", "Villainess", "Virtual Reality",
                "Zombies"
        // Thêm các theme khác vào đây
        );
        List<String> genreNames = List.of(
            "Action", "Adventure", "Boys' Love", "Comedy", "Crime", 
            "Drama", "Fantasy", "Girls' Love", "Historical", "Horror", 
            "Isekai", "Magical Girls", "MeRcha", "Medical", "Mystery", 
            "Philosophical", "Psychological", "Romance", "Sci-Fi", 
            "Slice of Life", "Sports", "Superhero", "Thriller", 
            "Tragedy", "Wuxia"
        );
        List<String> contentWarningNames = List.of(
            "Gore", "Sexual Violence"
        );
        List<String> formatNames = List.of(
            "4-Koma",
            "Adaptation",
            "Anthology",
            "Award Winning",
            "Doujinshi",
            "Fan Colored",
            "Full Color",
            "Long Strip",
            "Official Colored",
            "Oneshot",
            "Self-Published",
            "Web Comic"
        );

    
        handleInitializeTag(themeNames,TagType.THEME);
        handleInitializeTag(genreNames,TagType.GENRE);
        handleInitializeTag(contentWarningNames,TagType.CONTENT_WARNING);
        handleInitializeTag(formatNames,TagType.FORMAT);

   
    }
}
