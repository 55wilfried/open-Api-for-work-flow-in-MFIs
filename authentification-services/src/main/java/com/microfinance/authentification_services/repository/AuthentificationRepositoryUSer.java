package com.microfinance.authentification_services.repository;

import com.microfinance.authentification_services.dto.CollectUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthentificationRepositoryUSer extends JpaRepository<CollectUser, String> {
    @Query("SELECT u FROM CollectUser u WHERE LOWER(u.username) =LOWER(:username)")
    CollectUser findByUserName(@Param("username") String userName);
}
