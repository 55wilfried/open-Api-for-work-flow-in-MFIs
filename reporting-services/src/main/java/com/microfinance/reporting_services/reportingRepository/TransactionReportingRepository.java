package com.microfinance.reporting_services.reportingRepository;

import com.microfinance.reporting_services.dto.OperationCollecte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionReportingRepository extends JpaRepository<OperationCollecte, String> {

    @Query("SELECT u FROM OperationCollecte u WHERE LOWER(u.num) =LOWER(:num)")
    OperationCollecte findByNum(@Param("num") String num);
    @Query("SELECT e FROM OperationCollecte e " +
            "WHERE (:startDate is null or e.dateOp >= :startDate) " +
            "AND (:endDate is null or e.dateOp <= :endDate) " +
            "AND (:numCol is null or e.numCol = :numCol) " +
            "AND (:codage is null or e.codage = :codage) " +
            "AND (:numcli is null or e.numcli = :numcli)")
    List<OperationCollecte> findEntitiesByParamsAndDateRange(Date startDate, Date endDate, String numCol, String codage, String numcli);




}
