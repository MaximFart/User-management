package ru.webrise.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.webrise.model.User;
import ru.webrise.model.dto.UserDTO;
import ru.webrise.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Получить пользователя по ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> ResponseEntity.ok().body(user.convertToDto(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Создать нового пользователя
    @PostMapping("")
    public UserDTO createUser(@RequestBody User user) {
        return user.convertToDto(userService.save(user));
    }

    // Обновить пользователя
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.findById(id)
                .map(user -> {
                    user.setName(userDetails.getName());
                    return ResponseEntity.ok().body(user.convertToDto(userService.save(user)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Удалить пользователя
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> {
                    userService.delete(user);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
