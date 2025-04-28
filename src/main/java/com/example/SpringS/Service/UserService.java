package com.example.SpringS.Service;

import com.example.SpringS.Entity.Enum.Otp;
import com.example.SpringS.Entity.Enum.User;
import com.example.SpringS.Entity.Enum.UserAuth;
import com.example.SpringS.Repository.OtpRepository;
import com.example.SpringS.Repository.UserRepository;
import com.example.SpringS.Repository.UserRepositoryAuth;
import com.example.SpringS.Security.GenerateCodeUtil;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepositoryAuth userRepositoryAuth;
    private final OtpRepository otpRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserRepositoryAuth userRepositoryAuth, OtpRepository otpRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepositoryAuth = userRepositoryAuth;
        this.otpRepository = otpRepository;
    }

    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, User user) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setUsername(user.getUsername());
            updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
            updatedUser.setRole(user.getRole());
            return userRepository.save(updatedUser);
        }
        return null;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }



    public void addUser(UserAuth userAuth) {
        userAuth.setPassword(passwordEncoder.encode(userAuth.getPassword()));
        userRepositoryAuth.save(userAuth);
    }





    public void auth(UserAuth userAuth) {
        Optional<UserAuth> o = userRepositoryAuth.findUserByUsername(userAuth.getUsername());
        if (o.isPresent()) {
            UserAuth u = o.get();
            if (passwordEncoder.matches(userAuth.getPassword(), u.getPassword())) {
                renewOtp(u);
            } else {
                throw new BadCredentialsException("Bad credentials.");
            }
        } else {
            throw new BadCredentialsException("Bad credentials.");
        }
    }

    private void renewOtp(UserAuth u) {
        String code = GenerateCodeUtil.generateCode();
        Optional<Otp> userOtp = otpRepository.findByUserAuth_Username(u.getUsername());

        if (userOtp.isPresent()) {
            Otp otp = userOtp.get();
            otp.setCode(code);
            otpRepository.save(otp); // əlavə olundu
        } else {
            Otp otp = new Otp();
            otp.setCode(code);
            otp.setUserAuth(u); // ƏSAS DÜZƏLİŞ BURADA
            otpRepository.save(otp);
            otp.setUsername(u.getUsername()); // <<<<<<<<<<<<<< Əlavə et
        }
    }

    public boolean check(Otp otpToValidate) {
        Optional<Otp> userOtp =
                otpRepository.findByUserAuth_Username(
                        otpToValidate.getUsername());
        if (userOtp.isPresent()) {
            Otp otp = userOtp.get();
            if (otpToValidate.getCode().equals(otp.getCode())) {
                return true;
            }
        }
        return false;
    }

}