package org.example.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "configs")
@Getter
@NoArgsConstructor
public class Config extends org.example.models.Entity {

    private int n;
    private String numbers;

}
