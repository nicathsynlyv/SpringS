package com.example.SpringS.Repository;

import com.example.SpringS.Entity.Enum.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByUserAuth_Username(String username); // Düzəliş edildi
}
