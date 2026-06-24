package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.repos.ConfigRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigService {

    private final ConfigRepository configRepository;

}
