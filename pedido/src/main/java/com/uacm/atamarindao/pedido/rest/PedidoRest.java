package com.uacm.atamarindao.pedido.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uacm.atamarindao.pedido.entidad.Pedido;
import com.uacm.atamarindao.pedido.servicios.PedidoServicios;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/pedidos")
public class PedidoRest {

	@Autowired
	private PedidoServicios pedidoServicos;
	
    @GetMapping
    public ResponseEntity<List<Pedido>> listAllPedidos() {
        List<Pedido> pedidos = pedidoServicos.findPedidoAll();
        if (pedidos.isEmpty()) {
            return  ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(pedidos);
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<Pedido> getPedido(@PathVariable("id") long id) {
        log.info("Fetching Invoice with id {}", id);
        Pedido pedido  = pedidoServicos.getPedido(id);
        if (null == pedido) {
            log.error("Invoice with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(pedido);
    }
    
    @PostMapping
    public ResponseEntity<Pedido> createPedido(@Valid @RequestBody Pedido pedido, BindingResult result) {
        log.info("Creating Invoice : {}", pedido);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Pedido pedidoDB = pedidoServicos.createPedido(pedido);

        return  ResponseEntity.status( HttpStatus.CREATED).body(pedidoDB);
    }
    
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updatePedido(@PathVariable("id") long id, @RequestBody Pedido pedido) {
        log.info("Updating Invoice with id {}", id);

        pedido.setId(id);
        Pedido pedidoActual=pedidoServicos.updatePedido(pedido);

        if (pedidoActual == null) {
            log.error("Unable to update. Invoice with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(pedidoActual);
    }
    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Pedido> deleteInvoice(@PathVariable("id") long id) {
        log.info("Fetching & Deleting Invoice with id {}", id);

        Pedido pedido = pedidoServicos.getPedido(id);
        if (pedido == null) {
            log.error("Unable to delete. Invoice with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        pedido = pedidoServicos.deletePedido(pedido);
        return ResponseEntity.ok(pedido);
    }
    
    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String> error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMensaje errorMessage = ErrorMensaje.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

}
