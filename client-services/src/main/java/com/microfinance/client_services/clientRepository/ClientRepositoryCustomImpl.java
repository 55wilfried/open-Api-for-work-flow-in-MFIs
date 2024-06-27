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
        String sql = "SELECT TOP 1 c.num FROM ClientCollecte c WHERE c.numCol = ? ORDER BY c.num DESC";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, numCol); // Use positional parameter for native query

        String lastClientNumber = (String) query.getSingleResult();

        // If no result found, start with the initial number
        if (lastClientNumber == null) {
            return numCol + "0001"; // Assuming the initial format is C010001
        }

        // Extract numeric part and increment it
        int numericPart = Integer.parseInt(lastClientNumber.substring(numCol.length())) + 1;
        String newClientNumber = numCol + String.format("%04d", numericPart); // Format as C0100001

        return newClientNumber;
    }
}