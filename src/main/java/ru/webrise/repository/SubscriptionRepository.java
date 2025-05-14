package ru.webrise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.webrise.model.Subscription;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findSubscriptionsByName(String name);

    @Query("SELECT s FROM Subscription s LEFT JOIN s.users u GROUP BY s ORDER BY COUNT(u) DESC")
    List<Subscription> findTop3Subscriptions();
}
