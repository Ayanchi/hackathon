package com.example.hackathon.service.Impl;


import com.example.hackathon.dto.user.UserRequest;
import com.example.hackathon.dto.authenticate.AuthenticationRequest;
import com.example.hackathon.dto.authenticate.AuthenticationResponse;
import com.example.hackathon.dto.user.UserResponse;
import com.example.hackathon.entities.Person;
import com.example.hackathon.entities.User;
import com.example.hackathon.enums.Role;
import com.example.hackathon.exception.BadCredentialsException;
import com.example.hackathon.repository.PersonRepository;
import com.example.hackathon.repository.UserRepository;
import com.example.hackathon.security.JwtTokenProvider;
import com.example.hackathon.service.AuthenticationService;
import com.example.hackathon.service.EmailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final EmailSenderService emailSenderService;

    @Override
    @Transactional
    public ResponseEntity<?> userRegister(UserRequest request) {
        Random random = new Random();
        Integer verificationCode = random.nextInt(9000) + 1000;
       // emailSenderService.sendEmail(request.getEmail(), "Verification Code", "The verification code: ", verificationCode);
        User user = new User();
        if (userRepository.findAllByEmail(request.getEmail()).size()>1) {
            throw new BadCredentialsException("this user already registered!");
        }
        if (request.getEmail().contains("@")) {// проверка почти на пренадлежность
            user.setEmail(request.getEmail());
        }
        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));// декодим(защивровать) пароль
        }
        Person person = new Person();
        person.setFirstname(request.getFirstname());
        person.setLastname(request.getLastname());
        user.setVerifyCode(verificationCode);
        user.setPerson(person);
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        personRepository.save(person);
        user.setRole(Role.USER);

        userRepository.save(user);
        return ResponseEntity.ok(convertAuthentication(user));//конвертация на ответ    }
    }
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Optional<User> optionalAuth = userRepository.findByEmail(request.getEmail());
        if (optionalAuth.isEmpty()) {// проверка пользователя из базы данных
            throw new BadCredentialsException("User not found with email: " + request.getEmail());
        }

        User auth = optionalAuth.get();

        userRepository.save(auth);


        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()));
        } catch (AuthenticationException e) {
            // Обработка ошибки аутентификации, например, неверный email или пароль
            throw new BadCredentialsException("Authentication failed: " + e.getMessage() + request.getEmail());
        }

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new BadCredentialsException("User not found"));
        String token = jwtTokenProvider.createToken(user.getEmail(), userRepository.findByEmail(user.getEmail()).get().getRole());


        return AuthenticationResponse.builder()// сторим для ответа(токен тоже дается после регистрации поэтому необязтельно логиниться)
                .user(convertToResponse(user))
                .accessToken(token)
                .build();
    }

    @Override
    public ResponseEntity<?> adminRegister(UserRequest request) {
        User user = new User();
        if (userRepository.findAllByEmail(request.getEmail()).size()>1) {
            throw new BadCredentialsException("this user already registered!");
        }
        if (request.getEmail().contains("@")) {// проверка почти на пренадлежность
            user.setEmail(request.getEmail());
        }
        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));// декодим(защивровать) пароль
        }
       user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());

        user.setRole(Role.ADMIN);

        userRepository.save(user);
        return ResponseEntity.ok(convertAuthentication(user));//конвертация на ответ    }
    }

    private AuthenticationResponse convertAuthentication(User user) {
            AuthenticationResponse response = new AuthenticationResponse();
            response.setUser(convertToResponse(user));
            UserResponse userResponse = new UserResponse();
            userResponse.setFirstname(user.getFirstname());
            userResponse.setLastname(user.getLastname());
            userResponse.setRole(user.getRole());
            userResponse.setEmail(user.getEmail());
            userResponse.setId(user.getId());
            response.setUser(userResponse);
            if (userRepository.findAllByEmail(user.getEmail()).size()>1){
                throw new BadCredentialsException("this user already registered!");
            }
            String token = jwtTokenProvider.createToken(user.getEmail(), userRepository.findByEmail(user.getEmail()).get().getRole());
            response.setAccessToken(token);
        System.out.println(response.toString());
        return response;
        }
        private UserResponse convertToResponse(User user) {
            UserResponse userResponse = new UserResponse();

            userResponse.setId(user.getId());
            userResponse.setEmail(user.getEmail());
            userResponse.setRole(user.getRole());
            return userResponse;
        }
}
