package com.leong.mach.mangaApp.page;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse {
    private Integer id;

    private String imageUrl;

    private Integer pageOrder;
}
