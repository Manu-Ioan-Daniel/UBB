package org.example.models;

import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@jakarta.persistence.Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "players")
public class Player extends Entity{

    private String porecla;
}
