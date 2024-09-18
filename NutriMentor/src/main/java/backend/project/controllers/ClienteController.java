package backend.project.controllers;

import backend.project.entities.Cliente;
import backend.project.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ClienteController {
    @Autowired
    ClienteService clienteService;

    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> listAll(){
        return new ResponseEntity<>(clienteService.listAll(), HttpStatus.OK);
    }

    @PostMapping("/clientes")
    public ResponseEntity<Cliente> addCliente(@RequestBody Cliente cliente){
        Cliente newCliente =clienteService.addCliente(cliente);
        if (newCliente == null){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(newCliente, HttpStatus.CREATED);
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<HttpStatus> deleteCliente(@PathVariable("id") Long id){
        clienteService.deleteCliente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable("id") Long id, @RequestBody Cliente cliente){
        cliente.setId(id);
        Cliente updatedCliente = clienteService.updateCliente(cliente);
        return new ResponseEntity<>(updatedCliente, HttpStatus.OK);
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<Cliente> detailsById(@PathVariable("id") Long id){
        Cliente clienteFound = clienteService.findById(id);
        if (clienteFound!=null) {
            return new ResponseEntity<>(clienteFound, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
