package com.leong.mach.mangaApp.creater.creater;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/creaters")
@RequiredArgsConstructor
public class CreaterController {

    private final CreaterService createrService;

    @PostMapping
    public ResponseEntity<Creater> createCreater(@RequestBody @Valid CreaterRequest request) {
        return ResponseEntity.ok(createrService.createCreater(request));
    }

    @GetMapping
    public ResponseEntity<List<Creater>> getAllCreaters() {
        return ResponseEntity.ok(createrService.getAllCreaters());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Creater> getCreaterById(@PathVariable Integer id) {
        return ResponseEntity.ok(createrService.getCreaterById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Creater>> searchCreatersByName(@RequestParam String name) {
         if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<Creater> creaters = createrService.findCreaterByName(name);
        return ResponseEntity.ok(creaters);
    }
    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkCreaterExists(@RequestParam String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }

        boolean exists = createrService.doesCreaterExist(name);
        return ResponseEntity.ok(exists);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCreater(@PathVariable Integer id) {
        createrService.deleteCreater(id);
        return ResponseEntity.noContent().build();
    }
}