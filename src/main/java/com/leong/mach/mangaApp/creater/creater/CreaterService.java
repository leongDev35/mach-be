package com.leong.mach.mangaApp.creater.creater;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor

public class CreaterService {

    private final CreaterRepository createrRepository;


    public Creater createCreater(CreaterRequest request) {
        Boolean createrCheck = createrRepository.findByName(request.name()).isPresent();
        if (createrCheck) {
            throw new IllegalStateException("Creater with name '" + request.name() + "' already exists.");
        }
        var creater = Creater.builder()
        .name(request.name())
        .build();
        return createrRepository.save(creater);
    }

     public List<Creater> findCreaterByName(String name) {
        return createrRepository.findByNameContainingIgnoreCase(name, PageRequest.of(0, 5));
    }

    public boolean doesCreaterExist(String name) {
        return createrRepository.findByName(name).isPresent();
    }


    public List<Creater> getAllCreaters() {
        return createrRepository.findAll();
    }

    public Creater getCreaterById(Integer id) {
        return createrRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Creater not found"));
    }

    public void deleteCreater(Integer id) {
        createrRepository.deleteById(id);
    }
}