package backend.project.serviceimpl;

import backend.project.entities.Buys;
import backend.project.exceptions.InvalidDataException;
import backend.project.exceptions.ResourceNotFoundException;
import backend.project.repositories.BuysRepository;
import backend.project.repositories.ClientRepository;
import backend.project.services.BuysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BuysServiceImpl implements BuysService {

    @Autowired
    private BuysRepository buysRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Buys updateBuys(Buys buys) {
        Buys buysFound = findById(buys.getOrderNumber());
        if (buysFound == null) {
            throw new ResourceNotFoundException("Buys with id: " + buys.getOrderNumber() + " cannot be found");
        }
        if (buys.getTotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidDataException("Total must be greater than zero");
        }
        buysFound.setOrderDate(buys.getOrderDate());
        buysFound.setTotal(buys.getTotal());
        return buysRepository.save(buysFound);
    }

    @Override
    public Buys insertBuys(Buys buys) {
        if (!clientRepository.existsById(buys.getClient().getId())) {
            throw new ResourceNotFoundException("Client with id: " + buys.getClient().getId() + " cannot be found");
        }
        if (buys.getTotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidDataException("Total must be greater than zero");
        }
        return buysRepository.save(buys);
    }

    @Override
    public void deleteBuys(Long id) {
        Buys buysFound = findById(id);
        if (buysFound == null) {
            throw new ResourceNotFoundException("Buys with id: " + id + " cannot be found");
        }
        buysRepository.delete(buysFound);
    }

    @Override
    public Buys findById(Long id) {
        return buysRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Buys with id: " + id + " cannot be found"));
    }

    @Override
    public List<Buys> listAll() {
        return buysRepository.findAll();
    }

    //Querys
    @Override
    public Long countTotalPurchasesByClient(Long clientId) {
        return buysRepository.countTotalPurchasesByClient(clientId);
    }
    @Override
    public BigDecimal calculateTotalSpendingByClient(Long clientId) {
        return buysRepository.calculateTotalSpendingByClient(clientId);
    }
}

