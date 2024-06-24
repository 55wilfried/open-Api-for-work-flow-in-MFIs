package com.microfinance.transaction_services.transactionRepository;

import com.microfinance.transaction_services.models.OperationCollecte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<OperationCollecte,String> {
    @Query("Select u From OperationCollecte u where LOWER(u.codage) =Lower(:codage)")
    List<OperationCollecte> findAllByCodage(@Param("codage") String codage);

    @Query("Select u From OperationCollecte u where LOWER(u.numCol) =Lower(:numCol)")
    List<OperationCollecte> findAllOperationByCollector(@Param("numCol") String numCol);

    @Query("SELECT u FROM OperationCollecte u WHERE LOWER(u.num) =LOWER(:num)")
    OperationCollecte findByNum(@Param("num") String num);

    @Query("SELECT e FROM OperationCollecte e " +
            "WHERE (:startDate is null or e.dateOp >= :startDate) " +
            "AND (:endDate is null or e.dateOp <= :endDate) " +
            "AND (:numCol is null or e.numCol = :numCol) " +
            "AND (:codage is null or e.codage = :codage) " +
            "AND (:numcli is null or e.numcli = :numcli)")
    List<OperationCollecte> findEntitiesByParamsAndDateRange(Date startDate, Date endDate, String numCol, String codage, String numcli);

    @Query("SELECT MAX(o.num) FROM OperationCollecte o WHERE LOWER(o.numCol) = LOWER(:numCol)")
    String findMaxNumByCollector(@Param("numCol") String collector);


}
