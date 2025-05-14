package ru.webrise.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.webrise.model.User;
import ru.webrise.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User save(User user) {
        logger.info("Сохранение пользователя в БД: {}", user);
        return userRepository.save(user);
    }

    @Transactional
    public Optional<User> findById(Long id) {
        logger.info("Поиск пользователя по ID: {}", id);
        return userRepository.findById(id);
    }

    @Transactional
    public void delete(User user) {
        logger.info("Удаление пользователя: {}", user);
        userRepository.delete(user);
    }
}
