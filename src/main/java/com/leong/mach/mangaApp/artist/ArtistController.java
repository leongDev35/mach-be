package com.leong.mach.mangaApp.artist;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leong.mach.common.ApiResponse;
import com.leong.mach.mangaApp.manga.Manga;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/artist")
@RequiredArgsConstructor
public class ArtistController {
    
    public final ArtistService service;
  
    @PostMapping("")
    public ResponseEntity<?> postMethodName(@RequestBody @Valid ArtistRequest request) throws MessagingException{
        System.out.println(1);
        service.register(request);
        return ResponseEntity.status(OK)
                .body(
                    new ApiResponse("Created new artist")
                );
    }

    @GetMapping("/mangas")
    public ResponseEntity<?> getAllMangaByArtist(@RequestParam Integer artistId) {
        
        List<Manga> mangas = service.getAllMangaByArtist(artistId);
        return ResponseEntity.ok(mangas);
    }
   
    
    

}
