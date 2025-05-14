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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "subscription_user",
            joinColumns = @JoinColumn(name = "subscription_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id",
            referencedColumnName = "id"))
    private Set<Subscription> subscriptions;

    public User() {
    }

    public UserDTO convertToDto(User user) {
        Set<SubscriptionDTO> subsDto = new HashSet<>();
        if (user.getSubscriptions() != null) {
            subsDto = user.getSubscriptions().stream()
                    .map(sub -> new SubscriptionDTO(sub.getId(), sub.getName()))
                    .collect(Collectors.toSet());
        }

        return new UserDTO(user.getId(), user.getName(), subsDto);
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

    public Set<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
