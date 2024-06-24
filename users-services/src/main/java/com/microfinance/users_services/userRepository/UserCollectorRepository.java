package com.microfinance.users_services.userRepository;


import com.microfinance.users_services.models.Collecteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserCollectorRepository extends JpaRepository<Collecteur,String> {

    @Query("Select u From Collecteur u where LOWER(u.codage) =Lower(:codage)")
    List<Collecteur> findAllByCodage(@Param("codage") String codage);

    @Query("SELECT u FROM Collecteur u WHERE LOWER(u.num) =LOWER(:num)")
    Collecteur findByNum(@Param("num") String num);

    @Query("SELECT MAX(c.num) FROM Collecteur c")
    String findMaxNum();
}
