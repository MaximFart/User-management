package ru.webrise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.webrise.model.Subscription;
import ru.webrise.model.User;
import ru.webrise.model.dto.SubscriptionDTO;
import ru.webrise.model.dto.SubscriptionTopDTO;
import ru.webrise.service.SubscriptionService;
import ru.webrise.service.UserService;
//import ru.webrise.service.UserSubscriptionService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping()
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;

    //Добавить подписку пользователю
    @PostMapping("/api/users/{userId}/subscriptions")
    public ResponseEntity<String> addSubscriptionByUser(@PathVariable Long userId, @RequestBody Subscription subscription) {
        User user = userService.findById(userId).orElse(null);
        Subscription sub = subscriptionService.findByName(subscription.getName()).orElse(null);

        if (user == null || sub == null) {
            return ResponseEntity.badRequest().body("Пользователь или подписка не найдена");
        }

        user.getSubscriptions().add(sub);

        userService.save(user);

        return ResponseEntity.ok("Подписка добавлена пользователю");
    }

    // Получить подписки пользователя
    @GetMapping("/api/users/{userId}/subscriptions")
    public Set<SubscriptionDTO> createSubscription(@PathVariable Long userId) {
        return subscriptionService.findSubscriptionsByUser(userId).stream()
                .map(subscription -> subscription.convertToDto(subscription))
                .collect(Collectors.toSet());
    }

    // Удалить подписку по ID
    @DeleteMapping("/api/users/{userId}/subscriptions/{subId}")
    public ResponseEntity<Object> deleteSubscription(@PathVariable Long userId, @PathVariable Long subId) {
        subscriptionService.removeSubscriptionsByUser(userId, subId);
        return ResponseEntity.ok().build();
    }

    //получить ТОП-3 популярных подписок
    @GetMapping("/api/subscriptions/top")
    public List<SubscriptionTopDTO> getTop3Subscriptions() {
        List<Subscription> topSubscriptions = subscriptionService.findTop3Subscriptions();

        return topSubscriptions.stream()
                .map(sub -> new SubscriptionTopDTO(sub.getId(), sub.getName(), (long)sub.getUsers().size()))
                .collect(Collectors.toList());
    }
}