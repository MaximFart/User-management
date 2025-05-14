package ru.webrise.service;

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
        return subscriptionRepository.findSubscriptionsByName(name);
    }

    @Transactional
    public Set<Subscription> findSubscriptionsByUser(Long userId) {
        return userRepository
                .findById(userId).orElseThrow(() -> new ObjectNotFoundException("User - " + userId + " not found"))
                .getSubscriptions();
    }

    @Transactional
    public void removeSubscriptionsByUser(Long userId, Long subId) {
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
        return subscriptionRepository.findTop3Subscriptions();
    }
}
