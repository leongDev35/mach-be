package com.leong.mach.mangaApp.AltTitle;

import java.util.List;
import java.util.stream.Collectors;

import com.leong.mach.common.CommonFunc;
import com.leong.mach.mangaApp.chapter.Chapter;
import com.leong.mach.mangaApp.chapter.ChapterMapper;
import com.leong.mach.mangaApp.chapter.ChapterResponse;

public class AltTitleMapper {
 
    public static AltTitleResponse toAltTitleResponse(AltTitle altTitle) {
        return AltTitleResponse.builder()
                .title(altTitle.getTitle())
                .language(altTitle.getLanguage())
                .flag(CommonFunc.COUNTRY_FLAG_MAP.get(altTitle.getLanguage()))
                .build();
    }

     public static List<AltTitleResponse> toChapterResponseList(List<AltTitle> altTitles) {
        return altTitles.stream()
                .map(AltTitleMapper::toAltTitleResponse)
                .collect(Collectors.toList());
    }
}
