package com.example.SpringS.Security;

import com.example.SpringS.Entity.Enum.Token;
import com.example.SpringS.Entity.Enum.User;
import com.example.SpringS.Repository.JpaTokenRepository;
import com.example.SpringS.Repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component

public class CustomCsrfTokenRepository implements CsrfTokenRepository {
    private final JpaTokenRepository jpaTokenRepository;
    private final UserRepository userRepository;


    @Autowired
    public CustomCsrfTokenRepository(JpaTokenRepository jpaTokenRepository,
                                     UserRepository userRepository) {
        this.jpaTokenRepository = jpaTokenRepository;
        this.userRepository = userRepository;
    }


    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        String uuid = UUID.randomUUID().toString();
        return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", uuid);
    }


    @Override
    public void saveToken(CsrfToken csrfToken,
                          HttpServletRequest request,
                          HttpServletResponse response) {

        String identifier = request.getHeader("X-IDENTIFIER");
        if (identifier == null) return;

        // User mövcud deyilsə, heç bir token saxlanmasın
        Optional<User> userOptional = userRepository.findByUsername(identifier);
        if (userOptional.isEmpty()) return;

        Optional<Token> existingToken = jpaTokenRepository.findTokenByIdentifier(identifier);

        if (existingToken.isPresent()) {
            Token token = existingToken.get();
            token.setToken(csrfToken.getToken());
            jpaTokenRepository.save(token);
        } else {
            Token token = new Token();
            token.setToken(csrfToken.getToken());
            token.setIdentifier(identifier);
            token.setUser(userOptional.get());

            jpaTokenRepository.save(token);
        }
    }


    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        String identifier = request.getHeader("X-IDENTIFIER");
        Optional<Token> existingToken = jpaTokenRepository.findTokenByIdentifier(identifier);
        if (existingToken.isPresent()) {
            Token token = existingToken.get();
            return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", token.getToken());
        }
        return null;
    }

}
