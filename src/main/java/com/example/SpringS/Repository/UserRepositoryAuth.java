package com.example.SpringS.Repository;

import com.example.SpringS.Entity.Enum.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepositoryAuth extends JpaRepository<UserAuth, Long> {
    Optional<UserAuth> findUserByUsername(String username);

}
