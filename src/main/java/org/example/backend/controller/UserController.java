package org.example.backend.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.backend.dto.LoginDto;
import org.example.backend.dto.UserDto;
import org.example.backend.mapper.UserMapper;
import org.example.backend.service.JwtService;
import org.example.backend.dto.ChangePasswordRequestDto;
import org.example.backend.models.User;
import org.example.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    @GetMapping("/getLoggedUser")
    public ResponseEntity<UserDto> getLoggedUser(@CookieValue(value = "token", required = false) String token) {
        if(token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(userMapper.toDto(userService.getUserByUsername(jwtService.getUsername(token))));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        userService.logout(response);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> findByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userMapper.toDto(userService.getUserByUsername(username)));
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid LoginDto loginDto, HttpServletResponse response) {
        userService.login(loginDto, response);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid UserDto userDto) {
        User user = userService.createUser(userMapper.toModel(userDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDto(user));
    }

    @PutMapping("/changePassword")
    public ResponseEntity<Void> changePassword(@RequestBody @Valid ChangePasswordRequestDto changePasswordRequestDto) {
        userService.changePassword(changePasswordRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username, HttpServletResponse response) {
        userService.deleteUser(username, response);
        return ResponseEntity.noContent().build();
    }
}
