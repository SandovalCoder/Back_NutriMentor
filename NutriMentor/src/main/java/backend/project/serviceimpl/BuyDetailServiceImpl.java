package backend.project.serviceimpl;

import backend.project.entities.BuyDetail;
import backend.project.exceptions.InvalidDataException;
import backend.project.exceptions.ResourceNotFoundException;
import backend.project.repositories.BuyDetailRepository;
import backend.project.services.BuyDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BuyDetailServiceImpl implements BuyDetailService {

    @Autowired
    private BuyDetailRepository buyDetailRepository;

    @Override
    public BuyDetail updateBuyDetail(BuyDetail buyDetail) {
        BuyDetail buyDetailFound = findById(buyDetail.getId());
        if (buyDetailFound == null) {
            throw new ResourceNotFoundException("BuyDetail with id: " + buyDetail.getId() + " cannot be found");
        }
        if (buyDetail.getQuantity() <= 0) {
            throw new InvalidDataException("Quantity must be greater than zero");
        }
        buyDetailFound.setQuantity(buyDetail.getQuantity());
        buyDetailFound.setSubtotal(buyDetail.getSubtotal());
        return buyDetailRepository.save(buyDetailFound);
    }

    @Override
    public BuyDetail insertBuyDetail(BuyDetail buyDetail) {
        if (buyDetail.getQuantity() <= 0) {
            throw new InvalidDataException("Quantity must be greater than zero");
        }
        if (buyDetail.getSubtotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidDataException("Subtotal must be greater than zero");
        }
        return buyDetailRepository.save(buyDetail);
    }

    @Override
    public void deleteBuyDetail(Long id) {
        BuyDetail buyDetailFound = findById(id);
        if (buyDetailFound == null) {
            throw new ResourceNotFoundException("BuyDetail with id: " + id + " cannot be found");
        }
        buyDetailRepository.delete(buyDetailFound);
    }

    @Override
    public BuyDetail findById(Long id) {
        return buyDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BuyDetail with id: " + id + " cannot be found"));
    }

    @Override
    public List<BuyDetail> listAll() {
        return buyDetailRepository.findAll();
    }

    //Querys
    @Override
    public List<Object[]> totalProductsConsumedByCategory(Long clientId) {
        return buyDetailRepository.totalProductsConsumedByCategory(clientId);
    }
}
