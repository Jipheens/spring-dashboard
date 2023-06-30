package com.dash.controllers;

import com.dash.dto.CredencialDTO;
import com.dash.dto.TokenDTO;
import com.dash.exceptions.InvalidPasswordException;
import com.dash.exceptions.UserNotFoundException;
import com.dash.models.User;
import com.dash.services.UserService;
import com.dash.security.JwtService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Register a user")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successfully registered user"),
            @ApiResponse(code = 400, message = "Validation Error")
    })
    public User registerUser(@RequestBody @Valid User user) {
        return userService.createUser(user);
    }

    @PostMapping("/auth")
    @ApiOperation("Generate an access token after user authentication")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successfully generated token"),
            @ApiResponse(code = 400, message = "Validation error")
    })
    public TokenDTO authenticate(@RequestBody CredencialDTO credencialDTO) {
        try {
            User user = userService.getUserByEmail(credencialDTO.getEmail());

            if (user.getPassword().equals(credencialDTO.getPassword())) {
                userService.authenticate(user.getEmail(), user.getPassword());
                String token = jwtService.generateToken(user);
                return new TokenDTO(user.getEmail(), token);
            } else {
                throw new InvalidPasswordException();
            }
        } catch (UserNotFoundException | InvalidPasswordException e) {
            throw new UsernameNotFoundException("Invalid email or password");
        }
    }


    @PostMapping("/logout")
    @ApiOperation("Logout the authenticated user")
    @ApiResponse(code = 200, message = "Successfully logged out")
    public ResponseEntity<String> logout() {
        // Clear the authentication from the SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityContextHolder.clearContext();
        }
        return ResponseEntity.ok("Successfully logged out");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) {
        User updatedUser = userService.updateUser(userId, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/profile/{email}")
    public ResponseEntity<User> getUserProfile(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
