package template.template.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import template.template.domain.StatisticiJoc;
import template.template.repository.GameStatsRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameStatsService {

    private final GameStatsRepo gameStatsRepo;

    public void save(StatisticiJoc statisticiJoc) {
        gameStatsRepo.save(statisticiJoc);
    }

    public List<StatisticiJoc> getStatisticiByPlayer(String playerName){
        return gameStatsRepo.getStatisticiByPlayer(playerName);
    }
}
