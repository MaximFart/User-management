package ru.webrise.model;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;
import ru.webrise.model.dto.SubscriptionDTO;
import ru.webrise.model.dto.UserDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Entity
@Table(name = "subscriptions")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToMany(mappedBy = "subscriptions")
    private Set<User> users;

    public Subscription() {
    }

    public SubscriptionDTO convertToDto(Subscription subscription) {
        Set<UserDTO> subsDto = new HashSet<>();
        if (subscription.getUsers() != null) {
            subsDto = subscription.getUsers().stream()
                    .map(sub -> new UserDTO(sub.getId(), sub.getName()))
                    .collect(Collectors.toSet());
        }

        return new SubscriptionDTO(subscription.getId(), subscription.getName(), subsDto);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
