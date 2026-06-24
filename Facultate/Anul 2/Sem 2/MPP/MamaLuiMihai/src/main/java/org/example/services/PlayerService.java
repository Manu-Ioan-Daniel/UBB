package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.repos.PlayerRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public boolean existaPorecla(String porecla) {
        return playerRepository.findByPorecla(porecla).isPresent();
    }
}
