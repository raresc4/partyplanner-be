package org.example.backend.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.example.backend.dto.ChangePasswordRequestDto;
import org.example.backend.dto.LoginDto;
import org.example.backend.exception.ConflictException;
import org.example.backend.exception.NotFoundException;
import org.example.backend.models.Event;
import org.example.backend.models.Task;
import org.example.backend.models.User;
import org.example.backend.repository.EventRepository;
import org.example.backend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final EventService eventService;
    private final EventRepository eventRepository;

    public User createUser(User user) {
        Optional<User> existingUser = userRepository.findUserByUsername(user.getUsername());
        if(existingUser.isPresent()) throw new ConflictException("User already exists");

        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
        user.setAccountCreationDate(new Date());
        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public void login(LoginDto loginDto, HttpServletResponse response) {
        Optional<User> user = userRepository.findUserByUsername(loginDto.getUsername());
        if(user.isEmpty()) throw new NotFoundException("User not found");

        if(!BCrypt.checkpw(loginDto.getPassword(), user.get().getPassword())) {
            throw new ConflictException("Invalid credentials");
        }

        setCookie(jwtService.getToken(loginDto.getUsername()), response);
    }

    public void changePassword(ChangePasswordRequestDto changePasswordRequestDto) {
        User user = userRepository.findUserByUsername(changePasswordRequestDto.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if(!BCrypt.checkpw(changePasswordRequestDto.getOldPassword(), user.getPassword())) {
            throw new ConflictException("Old password is incorrect");
        }

        if(BCrypt.checkpw(changePasswordRequestDto.getNewPassword(), user.getPassword())) {
            throw new ConflictException("New password cannot be the same as the old one");
        }

        user.setPassword(BCrypt.hashpw(changePasswordRequestDto.getNewPassword(), BCrypt.gensalt(10)));
        userRepository.save(user);
    }

    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", "");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public void deleteUser(String username, HttpServletResponse response) {
        Optional<User> user = userRepository.findUserByUsername(username);
        if(user.isEmpty()) throw new NotFoundException("User not found");
        List<Event> events = eventService.getUserEvents(username);
        events.forEach(event -> {
            event.getInvolvedUsers().remove(username);
            if(event.getAdmin().equals(username)) {
                event.setAdmin(event.getInvolvedUsers().isEmpty() ? null : event.getInvolvedUsers().get(0));
            }
            List<Task> newTasks = new ArrayList<>();
            event.getTasks().forEach(task -> {
                if(!task.getAssignee().equals(username)) {
                    newTasks.add(task);
                }
            });
            event.setTasks(newTasks);
        });
        eventRepository.saveAll(events);
        logout(response);
        userRepository.deleteUserByUsername(username);
    }

    private void setCookie(String token, HttpServletResponse response) {
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(true);
        response.addCookie(cookie);
    }
}
