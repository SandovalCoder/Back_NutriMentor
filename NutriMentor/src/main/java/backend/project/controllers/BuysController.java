package backend.project.controllers;

import backend.project.entities.Buys;
import backend.project.services.BuysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class BuysController {

    @Autowired
    private BuysService buysService;

    @GetMapping("/buys")
    public ResponseEntity<List<Buys>> listAllBuys() {
        return new ResponseEntity<>(buysService.listAll(), HttpStatus.OK);
    }

    @PostMapping("/buys")
    public ResponseEntity<Buys> addBuys(@RequestBody Buys buys) {
        Buys newBuys = buysService.insertBuys(buys);
        if (newBuys == null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(newBuys, HttpStatus.CREATED);
    }

    @DeleteMapping("/buys/{id}")
    public ResponseEntity<HttpStatus> deleteBuys(@PathVariable("id") Long id) {
        buysService.deleteBuys(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/buys/{id}")
    public ResponseEntity<Buys> updateBuys(@PathVariable("id") Long id, @RequestBody Buys buys) {
        buys.setOrderNumber(id);
        Buys updatedBuys = buysService.updateBuys(buys);
        return new ResponseEntity<>(updatedBuys, HttpStatus.OK);
    }

    @GetMapping("/buys/{id}")
    public ResponseEntity<Buys> detailsById(@PathVariable("id") Long id) {
        Buys buysFound = buysService.findById(id);
        if (buysFound != null) {
            return new ResponseEntity<>(buysFound, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Querys
    @GetMapping("/buys/total-purchases/{clientId}")
    public ResponseEntity<Long> countTotalPurchasesByClient(@PathVariable Long clientId) {
        Long count = buysService.countTotalPurchasesByClient(clientId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
    @GetMapping("/buys/total-spending/{clientId}")
    public ResponseEntity<BigDecimal> calculateTotalSpendingByClient(@PathVariable Long clientId) {
        BigDecimal totalSpending = buysService.calculateTotalSpendingByClient(clientId);
        return new ResponseEntity<>(totalSpending, HttpStatus.OK);
    }
}

