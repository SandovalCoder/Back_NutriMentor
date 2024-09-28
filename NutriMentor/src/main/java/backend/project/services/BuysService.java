package backend.project.services;

import backend.project.entities.Buys;

import java.math.BigDecimal;
import java.util.List;

public interface BuysService {

    List<Buys> listAll();

    Buys insertBuys(Buys buys);

    Buys updateBuys(Buys buys);

    void deleteBuys(Long id);

    Buys findById(Long id);

    //Query
    Long countTotalPurchasesByClient(Long clientId);
    BigDecimal calculateTotalSpendingByClient(Long clientId);
}
