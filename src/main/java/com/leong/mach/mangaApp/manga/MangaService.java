package com.leong.mach.mangaApp.manga;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leong.mach.mangaApp.AltTitle.AltTitle;
import com.leong.mach.mangaApp.creater.creater.Creater;
import com.leong.mach.mangaApp.creater.creater.CreaterRepository;
import com.leong.mach.mangaApp.creater.mangaCreaterRole.MangaCreaterRole;
// import com.leong.mach.mangaApp.creater.mangaCreaterRole.MangaCreaterRole.Role;
import com.leong.mach.mangaApp.creater.mangaCreaterRole.MangaCreaterRoleRepository;
import com.leong.mach.mangaApp.creater.mangaCreaterRole.Role;
import com.leong.mach.user.User;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MangaService {

    private final MangaRepository mangaRepository;
    private final MangaMapper mangaMapper;
    private final CreaterRepository createrRepository;
    private final MangaCreaterRoleRepository mangaCreaterRoleRepository;

    public static List<String> mergeLists(List<String> listAuthor, List<String> listArtist) {
        // Tạo Set để gộp và loại bỏ phần tử trùng lặp
        Set<String> mergedSet = new HashSet<>();

        // Thêm tất cả phần tử từ listAuthor và listArtist vào Set
        mergedSet.addAll(listAuthor);
        mergedSet.addAll(listArtist);

        // Chuyển Set thành List và trả về
        return new ArrayList<>(mergedSet);
    }

    public Integer save(MangaRequest request, User user) throws MessagingException {
        System.out.println(user.getEmail());
        Manga manga = mangaMapper.toManga(request);
        manga.setUser(user);
        List<String> mergedList = mergeLists(request.listAuthor(), request.listArtist());

        for (String creater : mergedList) {
            // Kiểm tra xem tác giả với ID đã tồn tại hay chưa
            if (!createrRepository.findByName(creater).isPresent()) {
                var newCreater = Creater.builder()
                        .name(creater)
                        .build();
                createrRepository.save(newCreater); // Lưu tác giả mới vào cơ sở dữ liệu
            }
        }

        for (String author : request.listAuthor()) {
            createrRepository.findByName(author).ifPresent(creater -> {
                MangaCreaterRole mangaCreaterRole = MangaCreaterRole.builder()
                        .manga(manga)
                        .creater(creater)
                        .role(Role.AUTHOR)
                        .build();
                manga.addMangaCreaterRole(mangaCreaterRole); // Gán vào Manga
            });
        }
        for (String artist : request.listArtist()) {
            createrRepository.findByName(artist).ifPresent(creater -> {
                MangaCreaterRole mangaCreaterRole = MangaCreaterRole.builder()
                        .manga(manga)
                        .creater(creater)
                        .role(Role.ARTIST)
                        .build();
                manga.addMangaCreaterRole(mangaCreaterRole); // Gán vào Manga
            });
        }
        for (AltTitle title : request.altTitles()) {
            AltTitle altTitle = AltTitle.builder()
                    .title(title.getTitle())
                    .manga(manga)
                    .language(title.getLanguage())
                    .build();
            manga.addAltTitleList(altTitle);
        }

        mangaRepository.save(manga);
        return manga.getId();
    }

    public MangaResponse findById(Integer mangaId) {
        return mangaRepository.findById(mangaId)
                .map(mangaMapper::toMangaResponse)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID: " + mangaId));
    }

    public List<MangaResponse> getMangasByUserId(Integer userId) {
        return mangaRepository.findByUserId(userId).stream()
                .map(mangaMapper::toMangaFindByIdUser)
                .collect(Collectors.toList());
    }

    /**
     * Xóa một Manga theo ID.
     * 
     * @param mangaId ID của Manga cần xóa
     * @throws RuntimeException nếu không tìm thấy Manga
     */
    @Transactional
    public void deleteManga(Integer mangaId) {
        Manga manga = mangaRepository.findById(mangaId)
                .orElseThrow(() -> new RuntimeException("Manga not found with id " + mangaId));

        mangaRepository.delete(manga);
    }
    // public List<Manga> findAllByGenreNames(List<String> genreNames) {

    // return mangaRepository.findAllByGenreNames(
    // genreNames,
    // genreNames.size()
    // );
    // }

    // public List<Manga> findAllByGenresThemesAndArtists(List<String> genreNames,
    // List<String> themeNames, List<String> artistNames) {
    // System.out.println(genreNames);
    // System.out.println(themeNames);
    // System.out.println(artistNames);
    // return mangaRepository.findAllByGenresThemesAndArtists(
    // genreNames,
    // themeNames,
    // artistNames,
    // genreNames.size(),
    // themeNames.size(),
    // artistNames.size()
    // );
    // }

}
