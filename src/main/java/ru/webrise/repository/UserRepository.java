package ru.webrise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.webrise.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findUserByName(String name);
}
