package com.leong.mach.mangaApp.tag;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagDTO {
    private Integer id;
    private String name;
}