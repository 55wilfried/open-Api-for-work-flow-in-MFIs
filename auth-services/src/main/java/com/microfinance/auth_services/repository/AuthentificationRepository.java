package com.microfinance.auth_services.repository;


import com.microfinance.auth_services.dto.Collecteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthentificationRepository extends JpaRepository<Collecteur, String> {
    @Query("SELECT u FROM Collecteur u WHERE LOWER(u.num) =LOWER(:num)")
    Collecteur findCollectorByNum(@Param("num") String num);
}
