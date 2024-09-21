package backend.project.controllers;

import backend.project.dtos.DTODetalleCompra;
import backend.project.entities.DetalleCompra;
import backend.project.services.DetalleCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class DetalleCompraController {
    @Autowired
    DetalleCompraService detallecompraService;

    @GetMapping("/detallescompras")
    public ResponseEntity<List<DetalleCompra>> listAll(){
        return new ResponseEntity<>(detallecompraService.listAll(), HttpStatus.OK);
    }

    @PostMapping("/detallescompras")
    public ResponseEntity<DetalleCompra> addDetalleCompra(@RequestBody DTODetalleCompra dtoDetalleCompra){
        DetalleCompra newdetallecompra =detallecompraService.addDetalleCompra(dtoDetalleCompra);
        if (newdetallecompra == null){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(newdetallecompra, HttpStatus.CREATED);
    }

    @DeleteMapping("/detallescompras/{id}")
    public ResponseEntity<HttpStatus> deleteDetalleCompra(@PathVariable("id") Long id){
        detallecompraService.deleteCompra(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/detallescompras/{id}")
    public ResponseEntity<DetalleCompra> updateDetalleCompra(@PathVariable("id") Long id, @RequestBody DTODetalleCompra dtoDetalleCompra){
        DetalleCompra.setId(id);
        DetalleCompra updatedetalleCompra = detallecompraService.updateDetalleCompra(dtoDetalleCompra);
        return new ResponseEntity<>(updatedetalleCompra, HttpStatus.OK);
    }

    @GetMapping("/detallescompras/{id}")
    public ResponseEntity<DetalleCompra> detailsById(@PathVariable("id") Long id){
        DetalleCompra compraFound = detallecompraService.findById(id);
        if (compraFound!=null) {
            return new ResponseEntity<>(compraFound, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
