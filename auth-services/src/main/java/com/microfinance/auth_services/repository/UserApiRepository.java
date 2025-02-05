package com.microfinance.auth_services.repository;



import com.microfinance.auth_services.dto.UserApi;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserApiRepository extends JpaRepository<UserApi, Long> {
    Optional<UserApi> findByUsername(String username);
}
