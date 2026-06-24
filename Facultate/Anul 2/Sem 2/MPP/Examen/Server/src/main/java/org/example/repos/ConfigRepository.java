package org.example.repos;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConfigRepository {
    private final SessionFactory sessionFactory;

    public List<List<Integer>> getConfig(int n){

    }
}
