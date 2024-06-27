package com.microfinance.reporting_services.reportingRepository;

import com.microfinance.reporting_services.dto.ClientCollecte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ClientReportRepository extends JpaRepository<ClientCollecte, String> {

    @Query("SELECT u FROM ClientCollecte u WHERE LOWER(u.num) =LOWER(:num)")
    ClientCollecte findByNum(@Param("num") String num);

    @Query("SELECT e FROM ClientCollecte e " +
            "WHERE (:startDate is null or e.dateCrea >= :startDate) " +
            "AND (:endDate is null or e.dateCrea <= :endDate) " +
            "AND (:numCol is null or e.numCol = :numCol) " +
            "AND (:codage is null or e.codage = :codage) " +
            "AND (:num is null or e.num = :num)")
    List<ClientCollecte> findEntitiesByParamsAndDateRange(Date startDate, Date endDate, String numCol, String codage, String num);

}
