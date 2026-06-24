package org.example.repos;

import lombok.RequiredArgsConstructor;
import org.example.models.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PlayerRepository {

    private final SessionFactory sessionFactory;

    public Optional<Player> findByPorecla(String porecla) {
        try (Session session = sessionFactory.openSession()) {
            Player player = session.createQuery(
                            "FROM Player WHERE porecla = :porecla", Player.class)
                    .setParameter("porecla", porecla)
                    .uniqueResult();

            return Optional.ofNullable(player);
        }
    }
}