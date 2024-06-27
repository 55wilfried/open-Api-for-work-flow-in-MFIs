package com.microfinance.transaction_services.transactionRepository;

import com.microfinance.transaction_services.dto.OperationCollecte;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class TransacionByParams {

    @Autowired
    private EntityManager entityManager;

    public List<OperationCollecte> findOperations(String num, Date dateOP_start, Date dateOP_end, String numCol, String codage, String numcli) {
        StringBuilder jpql = new StringBuilder("SELECT o FROM OperationCollecte o WHERE 1=1 ");

        if (num != null) {
            jpql.append("AND o.num = :num ");
        }
        if (dateOP_start != null) {
            jpql.append("AND o.dateOp >= :dateOP_start ");
        }
        if (dateOP_end != null) {
            jpql.append("AND o.dateOp <= :dateOP_end ");
        }
        if (numCol != null) {
            jpql.append("AND o.numCol = :numCol ");
        }
        if (codage != null) {
            jpql.append("AND o.codage = :codage ");
        }
        if (numcli != null) {
            jpql.append("AND o.numcli = :numcli ");
        }

        Query query = entityManager.createQuery(jpql.toString(), OperationCollecte.class);

        if (num != null) {
            query.setParameter("num", num);
        }
        if (dateOP_start != null) {
            query.setParameter("dateOP_start", dateOP_start);
        }
        if (dateOP_end != null) {
            query.setParameter("dateOP_end", dateOP_end);
        }
        if (numCol != null) {
            query.setParameter("numCol", numCol);
        }
        if (codage != null) {
            query.setParameter("codage", codage);
        }
        if (numcli != null) {
            query.setParameter("numcli", numcli);
        }

        return query.getResultList();
    }
}
