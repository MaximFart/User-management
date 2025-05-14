package ru.webrise.model.dto;

import ru.webrise.model.Subscription;

import java.util.Set;
import java.util.stream.Collectors;

public class SubscriptionDTO {
    private Long id;
    private String name;
    private Set<UserDTO> users;



    public SubscriptionDTO(Long id, String name, Set<UserDTO> users) {
        this.id= id;
        this.name= name;
        this.users = users;

    }

    public SubscriptionDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SubscriptionDTO convertToDto(Subscription subscription) {
        Set<UserDTO> subsDto = subscription.getUsers().stream()
                .map(sub -> new UserDTO(sub.getId(), sub.getName()))
                .collect(Collectors.toSet());

        return new SubscriptionDTO(subscription.getId(), subscription.getName(), subsDto);
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
}
