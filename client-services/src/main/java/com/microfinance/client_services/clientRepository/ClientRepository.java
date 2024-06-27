package com.microfinance.client_services.clientRepository;

import com.microfinance.client_services.models.ClientCollecte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<ClientCollecte,String>, ClientRepositoryCustom {
    @Query("SELECT u FROM ClientCollecte u WHERE LOWER(u.num) =LOWER(:num)")
    ClientCollecte findByNum(@Param("num") String num);

    @Query("Select u From ClientCollecte u where LOWER(u.nom) =Lower(:nom)")
    ClientCollecte findByName(@Param("nom") String name);

    @Query("Select u From ClientCollecte u where LOWER(u.numCol) =Lower(:numCol)")
    List<ClientCollecte> findAllByCollector(@Param("numCol") String numCol);

    @Query("Select u From ClientCollecte u where LOWER(u.codage) =Lower(:codage)")
    List<ClientCollecte> findAllByCodage(@Param("codage") String codage);



    /*@Query("SELECT c FROM ClientCollecte c WHERE c.:key = :value")
    ClientCollecte findByKeyValue(@Param("key") String key, @Param("value") String value);*/

}


