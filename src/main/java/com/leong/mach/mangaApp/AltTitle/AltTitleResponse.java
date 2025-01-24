package com.leong.mach.mangaApp.AltTitle;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AltTitleResponse {
    private String title;
    private String language;
    private String flag;
}
