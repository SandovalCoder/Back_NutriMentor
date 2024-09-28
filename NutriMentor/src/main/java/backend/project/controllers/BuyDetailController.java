package backend.project.controllers;

import backend.project.entities.BuyDetail;
import backend.project.services.BuyDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class BuyDetailController {

    @Autowired
    private BuyDetailService buyDetailService;

    @GetMapping("/buy-details")
    public ResponseEntity<List<BuyDetail>> listAllBuyDetails() {
        return new ResponseEntity<>(buyDetailService.listAll(), HttpStatus.OK);
    }

    @PostMapping("/buy-details")
    public ResponseEntity<BuyDetail> addBuyDetail(@RequestBody BuyDetail buyDetail) {
        BuyDetail newBuyDetail = buyDetailService.insertBuyDetail(buyDetail);
        if (newBuyDetail == null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(newBuyDetail, HttpStatus.CREATED);
    }

    @DeleteMapping("/buy-details/{id}")
    public ResponseEntity<HttpStatus> deleteBuyDetail(@PathVariable("id") Long id) {
        buyDetailService.deleteBuyDetail(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/buy-details/{id}")
    public ResponseEntity<BuyDetail> updateBuyDetail(@PathVariable("id") Long id, @RequestBody BuyDetail buyDetail) {
        buyDetail.setId(id);
        BuyDetail updatedBuyDetail = buyDetailService.updateBuyDetail(buyDetail);
        return new ResponseEntity<>(updatedBuyDetail, HttpStatus.OK);
    }

    @GetMapping("/buy-details/{id}")
    public ResponseEntity<BuyDetail> detailsById(@PathVariable("id") Long id) {
        BuyDetail buyDetailFound = buyDetailService.findById(id);
        if (buyDetailFound != null) {
            return new ResponseEntity<>(buyDetailFound, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Querys
    @GetMapping("/buy-details/category-consumed/{clientId}")
    public ResponseEntity<List<Object[]>> totalProductsConsumedByCategory(@PathVariable Long clientId) {
        List<Object[]> totalProducts = buyDetailService.totalProductsConsumedByCategory(clientId);
        return new ResponseEntity<>(totalProducts, HttpStatus.OK);
    }
}

