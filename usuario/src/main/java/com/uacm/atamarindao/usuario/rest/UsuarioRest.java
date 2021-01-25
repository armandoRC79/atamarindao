package com.uacm.atamarindao.usuario.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.uacm.atamarindao.usuario.entidad.Usuario;
import com.uacm.atamarindao.usuario.servicios.UsuarioServicios;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping (value = "/usuarios")
public class UsuarioRest {
	
	@Autowired
	private UsuarioServicios usuarioServicio;

	@GetMapping
	public ResponseEntity<List<Usuario>> listUser(){
        List<Usuario> pedidos = usuarioServicio.findUsuarioAll();
        if (pedidos.isEmpty()) {
            return  ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(pedidos);
	}
	
    @GetMapping(value = "/{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable("id") long id) {
        log.info("Fetching Customer with id {}", id);
        Usuario usuario = usuarioServicio.getUsuario(id).get();
        if (  null == usuario) {
            log.error("Customer with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(usuario);
    }
    
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario, BindingResult result) {
    	if(result.hasErrors()) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatoMensaje(result));
    	}
        Usuario usuarioCreado =  usuarioServicio.createUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
    }
    
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable("id") long id, @RequestBody Usuario usuario) {
        log.info("Updating Customer with id {}", id);

        Usuario usuarioActual = usuarioServicio.getUsuario(id).get();

        if ( null == usuarioActual ) {
            log.error("Unable to update. Customer with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        usuario.setId(id);
        usuarioActual = usuarioServicio.updateUsuario(usuario);
        return  ResponseEntity.ok(usuarioActual);
    }
    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Usuario> deleteCustomer(@PathVariable("id") long id) {
        log.info("Fetching & Deleting Customer with id {}", id);

        Usuario usuario = usuarioServicio.getUsuario(id).get();
        if ( null == usuario ) {
            log.error("Unable to delete. Customer with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        usuario = usuarioServicio.deleteUsuario(usuario);
        return  ResponseEntity.ok(usuario);
    }

    private String formatoMensaje( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
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
