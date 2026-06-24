package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.models.Config;
import org.example.repos.ConfigRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConfigService {

    private final ConfigRepository configRepository;

    public List<Config> getConfigs(int n) {
        return configRepository.getConfigs(n);
    }

}
