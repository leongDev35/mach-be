package com.leong.mach.mangaApp.creater.creater;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CreaterInitializer implements CommandLineRunner {
    @Autowired
    private CreaterRepository repository;

    @Override
    public void run(String... args) throws Exception {
        // for (int i = 1; i <= 1000; i++) {
        //     String name = "Creater " + i;
        //     String avatarUrl = "https://example.com/avatar" + i + ".jpg";
        //     String biography = "Biography for Creater " + i;

        //     // Kiểm tra nếu Creater đã tồn tại theo tên thì không lưu
        //     if (!repository.findByName(name).isPresent()) {
        //         Creater creater = Creater.builder()
        //                 .name(name)
        //                 .avatarUrl(avatarUrl)
        //                 .biography(biography)
        //                 .build();

        //         repository.save(creater); // Lưu Creater vào cơ sở dữ liệu
        //     }
        // }
    }
}
