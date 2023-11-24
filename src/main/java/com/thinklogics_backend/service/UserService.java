package com.thinklogics_backend.service;

import com.thinklogics_backend.config.JwtUtils;
import com.thinklogics_backend.model.ERole;
import com.thinklogics_backend.model.Login;
import com.thinklogics_backend.model.Register;
import com.thinklogics_backend.model.User;
import com.thinklogics_backend.repository.UserRepository;
import com.thinklogics_backend.response.MyResponse;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Value("${spring.mail.host}")
    private String emailHost;

    @Value("${spring.mail.port}")
    private int emailPort;

    @Value("${spring.mail.username}")
    private String emailUsername;

    @Value("${spring.mail.password}")
    private String emailPassword;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean emailSmtpAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean emailSmtpStartTlsEnable;

    @Transactional
    public MyResponse registerUser(User user) {
        // Check if the email already exists in the database
        if (userRepository.existsByEmail(user.getEmail())) {
            return MyResponse.builder().statusText("Registration failed. User with this email already exists.").build();
        }
        // Check if password and confirm password match
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            return MyResponse.builder().statusText("Registration failed. Passwords do not match.").build();
        }

        String generatedOtp = generateOtp();
        user.setOtp(generatedOtp);
        user.setStatus("Inactive");
        user.setRole(ERole.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
         user.setConfirmPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        // Send OTP via email
        sendEmail(user.getEmail(), "Email Verification", "Your OTP: " + generatedOtp);

        return MyResponse.builder().statusText("Registration successful. An OTP has been sent to your email for verification.").user(user).build();
    }

    @Transactional
    public MyResponse verifyOtp(User user, String otp) {
        User storedUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (storedUser == null) {
            return MyResponse.builder().statusText("User not found").build();
        }

        if (verifyOtp1(storedUser, otp)) {
            storedUser.setStatus("Active");
            storedUser.setOtp(null);
            userRepository.save(storedUser);
            return MyResponse.builder().statusText("Email verification successful. Your account is now active.").user(storedUser).build();
        } else {
            return MyResponse.builder().statusText("Invalid OTP. Email verification failed.").build();
        }
    }

    @Transactional
    public MyResponse sendOtp(User user) {
        User storedUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (storedUser == null) {
            return MyResponse.builder().statusText("User not found").build();
        }

        String generatedOtp = generateOtp();
        storedUser.setOtp(generatedOtp);
        userRepository.save(storedUser);

        // Send OTP via email
        sendEmail(user.getEmail(), "Email Verification", "Your OTP: " + generatedOtp);

        return MyResponse.builder().statusText("OTP has been sent to your email for verification.").user(storedUser).build();
    }

    @Transactional
    public void sendEmail(String toEmail, String subject, String message) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(message, true);

            javaMailSender.send(mimeMessage);
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean verifyOtp1(User user, String otp) {
        if (user.getOtp() != null && user.getOtp().equals(otp)) {
            return true;
        }
        return false;
    }

    private String generateOtp() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[4];
        secureRandom.nextBytes(randomBytes);

        return Base64.getEncoder().encodeToString(randomBytes).substring(0, 6);
    }
    public User updateUser(User user) {
        User updatedUser = User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .phoneNumber(user.getPhoneNumber())
                .dob(user.getDob())
                .status(user.getStatus())
                .confirmPassword(passwordEncoder.encode(user.getConfirmPassword()))
                .role(user.getRole())
                .userId(user.getUserId())
                .build();
        return userRepository.save(updatedUser);
    }

    public String deleteUser(long userId) {
        userRepository.deleteById( userId);
        return "Deleted user with ID " + userId;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(long userId) {
        return userRepository.findByUserId(userId);
    }

    public ResponseEntity<MyResponse> authenticate(Login login) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(MyResponse.builder().statusText("Invalid username or password").build());
        }

        Optional<User> userDetails = userRepository.findByEmail(login.getEmail());
        String token = jwtUtils.generateToken(userDetails.get());
            String role = userDetails.get().getAuthorities().stream().findFirst().get().getAuthority();
        return ResponseEntity.ok(MyResponse.builder().token(token).user(userDetails.get()).role(role).statusText("Login Successful").build());
    }
}
