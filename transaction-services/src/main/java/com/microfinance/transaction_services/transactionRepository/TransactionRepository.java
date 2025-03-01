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

    @Query("SELECT o FROM OperationCollecte o " +
            "WHERE (:num IS NULL OR o.num = :num) " +
            "AND (:dateOp_start IS NULL OR o.dateOp >= :dateOp_start) " +
            "AND (:dateOp_end IS NULL OR o.dateOp <= :dateOp_end) " +
            "AND (:numCol IS NULL OR o.numCol = :numCol) " +
            "AND (:codage IS NULL OR o.codage = :codage) " +
            "AND (:numcli IS NULL OR o.numcli = :numcli)")
    List<OperationCollecte> findOperations(
            @Param("num") String num,
            @Param("dateOp_start") Date dateOP_start,
            @Param("dateOp_end") Date dateOP_end,
            @Param("numCol") String numCol,
            @Param("codage") String codage,
            @Param("numcli") String numcli
    );


    @Query("SELECT MAX(o.num) FROM OperationCollecte o WHERE LOWER(o.numCol) = LOWER(:numCol)")
    String findMaxNumByCollector(@Param("numCol") String collector);




}
