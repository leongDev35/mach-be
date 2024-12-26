package com.leong.mach.mangaApp.manga;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leong.mach.common.ApiResponse;
import com.leong.mach.mangaApp.chapter.Chapter;
import com.leong.mach.user.User;
import com.leong.mach.user.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.OK;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("manga")
@RequiredArgsConstructor
public class MangaController {
    private final MangaService mangaService;
    private final UserRepository userRepository;

    @PostMapping("")
    public ResponseEntity<?> saveManga(@Valid @RequestBody MangaRequest request, Principal principal)
            throws MessagingException {
        User user = userRepository.findByEmail(principal.getName()).get();

        mangaService.save(request, user);
        return ResponseEntity.status(OK)
                .body(
                        new ApiResponse("Created new manga"));
    }

    @GetMapping("/{manga-id}")
    public ResponseEntity<MangaResponse> findMangaById(
            @PathVariable("manga-id") Integer mangaId) {
        return ResponseEntity.ok(mangaService.findById(mangaId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MangaResponse>> getMangasByUserId(@PathVariable Integer userId) {
        List<MangaResponse> mangas = mangaService.getMangasByUserId(userId);
        return ResponseEntity.ok(mangas);
    }

    @DeleteMapping("/{mangaId}")
    public ResponseEntity<Void> deleteManga(@PathVariable Integer mangaId) {
        try {
            mangaService.deleteManga(mangaId);
            return ResponseEntity.noContent().build(); // Trả về mã trạng thái HTTP 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Trả về mã trạng thái HTTP 404 Not Found nếu không tìm thấy
                                                      // Manga
        }
    }

    // @GetMapping("/by-genres")
    // public ResponseEntity<?> findMangaByGenres(
    // @RequestParam(required = false) List<String> genreNames) {
    // System.out.println(genreNames);
    // return ResponseEntity.ok(mangaService.findAllByGenreNames(genreNames));
    // }

    // @GetMapping("/by-custom")
    // public ResponseEntity<?> findAllByCustomTag(
    // @RequestParam(required = false) List<String> genreNames,
    // @RequestParam(required = false) List<String> themeNames,
    // @RequestParam(required = false) List<String> artistNames) {
    // return
    // ResponseEntity.ok(mangaService.findAllByGenresThemesAndArtists(genreNames,themeNames,artistNames));
    // }

}
