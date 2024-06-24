package com.microfinance.authentification_services.authentificationRepository;

import com.microfinance.authentification_services.repositoryModel.Collecteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuthentificationRepository extends JpaRepository<Collecteur, String> {
    @Query("SELECT u FROM Collecteur u WHERE LOWER(u.num) =LOWER(:num)")
    Collecteur findCollectorByNum(@Param("num") String num);
}
