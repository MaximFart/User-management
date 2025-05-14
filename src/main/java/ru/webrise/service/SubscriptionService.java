package ru.webrise.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.webrise.exception.ObjectNotFoundException;
import ru.webrise.model.Subscription;
import ru.webrise.model.User;
import ru.webrise.repository.SubscriptionRepository;
import ru.webrise.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SubscriptionService {
    private static final Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Subscription save(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    @Transactional
    public Optional<Subscription> findByName(String name) {
        logger.info("Поиск подписки по имени: {}", name);
        return subscriptionRepository.findSubscriptionsByName(name);
    }

    @Transactional
    public Set<Subscription> findSubscriptionsByUser(Long userId) {
        logger.info("Поиск подпискок у пользователя: {}", userId);
        return userRepository
                .findById(userId).orElseThrow(() -> new ObjectNotFoundException("User - " + userId + " not found"))
                .getSubscriptions();
    }

    @Transactional
    public void removeSubscriptionsByUser(Long userId, Long subId) {
        logger.info("Удаление подписки - {}, у пользователя: {}", subId, userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException("User - " + userId + " not found"));
        Subscription subscription = subscriptionRepository.findById(subId).orElseThrow(() -> new ObjectNotFoundException("Subscription - " + subId + " not found"));
        if (user.getSubscriptions().contains(subscription)) {
            user.getSubscriptions().remove(subscription);
            userRepository.save(user);
        } else {
            throw new ObjectNotFoundException("Subscription - " + subId + "not contain in User - " + userId);
        }
    }

    @Transactional
    public List<Subscription> findTop3Subscriptions() {
        logger.info("Поиск топ 3 подписок");
        return subscriptionRepository.findTop3Subscriptions();
    }
}
