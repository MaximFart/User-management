package ru.webrise.model.dto;

import ru.webrise.model.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserDTO {
    private Long id;
    private String name;
    private Set<SubscriptionDTO> subscriptions;

    public UserDTO(Long id, String name, Set<SubscriptionDTO> subscriptions) {
        this.id = id;
        this.name = name;
        this.subscriptions = subscriptions;
    }

    public UserDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id= id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name= name;
    }

    public Set<SubscriptionDTO> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<SubscriptionDTO> subscriptions) {
        this.subscriptions= subscriptions;
    }
}
