package backend.project.services;

import backend.project.entities.BuyDetail;

import java.util.List;

public interface BuyDetailService {

    List<BuyDetail> listAll();

    BuyDetail insertBuyDetail(BuyDetail buyDetail);

    BuyDetail updateBuyDetail(BuyDetail buyDetail);

    void deleteBuyDetail(Long id);

    BuyDetail findById(Long id);

    //Querys
    List<Object[]> totalProductsConsumedByCategory(Long clientId);
}
