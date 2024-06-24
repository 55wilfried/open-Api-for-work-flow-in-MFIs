package com.microfinance.client_services.clientRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class ClientRepositoryCustomImpl implements ClientRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public String getNextClientNumber(String numCol) {
        String sql = "SELECT c.num FROM ClientCollecte c WHERE c.numCol = :numCol ORDER BY c.num DESC LIMIT 1";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("numCol", numCol);
        String lastClientNumber = (String) query.getSingleResult();

        // Extract numeric part and increment it
        int numericPart = Integer.parseInt(lastClientNumber.substring(numCol.length())) + 1;
        String newClientNumber = numCol + String.format("%04d", numericPart); // Format as C0100001

        return newClientNumber;
    }
}