package com.microfinance.client_services.clientRepository;

import com.microfinance.client_services.models.ClientCollecte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientCollecte, String>, ClientRepositoryCustom {

    @Query("SELECT u FROM ClientCollecte u WHERE LOWER(u.num) = LOWER(:num)")
    Optional<ClientCollecte> findByNum(@Param("num") String num);

    @Query("SELECT u FROM ClientCollecte u WHERE LOWER(u.nom) = LOWER(:nom)")
    Optional<ClientCollecte> findByName(@Param("nom") String name);

    @Query("SELECT u FROM ClientCollecte u WHERE LOWER(u.numCol) = LOWER(:numCol)")
    List<ClientCollecte> findAllByCollector(@Param("numCol") String numCol);

    @Query("SELECT u FROM ClientCollecte u WHERE LOWER(u.codage) = LOWER(:codage)")
    List<ClientCollecte> findAllByCodage(@Param("codage") String codage);
}
