package org.example.network.responses;

import lombok.Data;
import org.example.models.Config;

import java.util.List;

@Data
public class ConfigResponse {

    public List<Config> configs;

}
