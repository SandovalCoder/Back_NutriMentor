package backend.project.controllers;

import backend.project.dtos.DTOCompra;
import backend.project.entities.Compra;
import backend.project.services.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CompraController {
    @Autowired
    CompraService compraService;

    @GetMapping("/compras")
    public ResponseEntity<List<Compra>> listAll(){
        return new ResponseEntity<>(compraService.listAll(), HttpStatus.OK);
    }

    @PostMapping("/compras")
    public ResponseEntity<Compra> addCompra(@RequestBody DTOCompra dtoCompra){
        Compra newcompra =compraService.addCompra(dtoCompra);
        if (newcompra == null){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(newcompra, HttpStatus.CREATED);
    }

    @DeleteMapping("/compras/{nroOrden}")
    public ResponseEntity<HttpStatus> deleteCompra(@PathVariable("nroOrden") Long id){
        compraService.deleteCompra(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/compras/{nroOrden}")
    public ResponseEntity<Compra> updateCompra(@PathVariable("nroOrden") Long id, @RequestBody DTOCompra dtoCompra){
        Compra.setId(id);
        Compra updatedCompra = compraService.updateCompra(dtoCompra);
        return new ResponseEntity<>(updatedCompra, HttpStatus.OK);
    }

    @GetMapping("/compras/{nroOrden}")
    public ResponseEntity<Compra> detailsById(@PathVariable("nroOrden") Long id){
        Compra compraFound = compraService.findById(id);
        if (compraFound!=null) {
            return new ResponseEntity<>(compraFound, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
