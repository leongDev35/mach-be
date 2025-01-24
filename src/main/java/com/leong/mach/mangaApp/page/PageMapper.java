package com.leong.mach.mangaApp.page;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PageMapper {
    
    public static PageResponse toPageResponse(Page page) {
        return PageResponse.builder()
                .id(page.getId())
                .imageUrl(page.getImageUrl())
                .pageOrder(page.getPageOrder())
                .build();
    }

    public static List<PageResponse> toListPageResponse(List<Page> pages) {
        return pages.stream()
                .map(PageMapper::toPageResponse)
                .collect(Collectors.toList());
    }

}
