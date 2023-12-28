package com.thinklogics_backend.controller;

import com.thinklogics_backend.model.Login;
import com.thinklogics_backend.model.Register;
import com.thinklogics_backend.model.User;
import com.thinklogics_backend.response.MyResponse;
import com.thinklogics_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
//@CrossOrigin(origins = "*")
@CrossOrigin("*")

@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<MyResponse> registerUser(@RequestBody Register register) {
        return ResponseEntity.ok().body(userService.registerUser(mapRegisterToUser(register)));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<MyResponse> verifyOtp(@RequestBody User user) {
        return ResponseEntity.ok().body(userService.verifyOtp(user, user.getOtp()));
    }

    @PostMapping("/login")
    public ResponseEntity<MyResponse> login(@RequestBody Login login) {
        return userService.authenticate(login);
    }

    private User mapRegisterToUser(Register register) {
        return User.builder()
                .firstName(register.getFirstName())
                .lastName(register.getLastName())
                .email(register.getEmail())
                .password(register.getPassword())
                .phoneNumber(register.getPhoneNumber())

                .dob(register.getDob())
                .confirmPassword(register.getConfirmPassword())
                .build();
    }
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/findById/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        Optional<User> userOptional = userService.getUserById(userId);

        if (userOptional.isPresent()) {
            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
