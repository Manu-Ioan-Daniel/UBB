package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.repos.PlayerRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PlayerService {

    private final  PlayerRepository playerRepo;

    public boolean playerExists(String porecla){
        return playerRepo.findByPorecla(porecla).isPresent();
    }



}
