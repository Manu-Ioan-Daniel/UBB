package org.example.models;

import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@jakarta.persistence.Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "players")
public class Player extends Entity {
    private String porecla;
}
