package com.uacm.atamarindao.producto.rest;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uacm.atamarindao.producto.entidad.Producto;
import com.uacm.atamarindao.producto.servicios.ProductoServicios;

@RestController
@RequestMapping (value = "/productos")
public class ProductoRest {
	 
	@Autowired
	private ProductoServicios productoServicio ;

	@GetMapping
	public ResponseEntity<List<Producto>> listProduct(){
		List<Producto> productos = productoServicio.getAllProducts();
		ResponseEntity<List<Producto>> respuesta = ResponseEntity.noContent().build();
		
		if(!productos.isEmpty())
			respuesta = ResponseEntity.ok(productos);
		
		return respuesta;
	}
	
    @GetMapping(value = "/{id}")
    public ResponseEntity<Producto> getProduct(@PathVariable("id") Long id) {
        Producto producto =  productoServicio.getProduct(id).get();
        ResponseEntity<Producto> respuesta = ResponseEntity.notFound().build();
        if (null!=producto){
            respuesta =  ResponseEntity.ok(producto);
        }
        return respuesta;
    }
    
    @PostMapping
    public ResponseEntity<Producto> createProduct(@RequestBody Producto producto, BindingResult result){
    	if(result.hasErrors()) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatoMensaje(result));
    	}
        Producto productoCreado =  productoServicio.createProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Producto> updateProduct(@PathVariable("id") Long id, @RequestBody Producto producto){
        producto.setId(id);
        Producto productDB =  productoServicio.updateProduct(producto);
        ResponseEntity<Producto> respuesta = ResponseEntity.notFound().build();
        if (productDB != null){
            respuesta = ResponseEntity.ok(productDB);
        }
        return respuesta;
    }
    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Producto> deleteProduct(@PathVariable("id") Long id){
        Producto productDelete = productoServicio.deleteProduct(id);
        ResponseEntity<Producto> respuesta = ResponseEntity.notFound().build();
        if (productDelete != null){
            respuesta = ResponseEntity.ok(productDelete);
        }
        return respuesta;
    }
    
    @PutMapping (value = "/{id}/stock")
    public ResponseEntity<Producto> updateStockProduct(@PathVariable  Long id ,@RequestParam(name = "cantidad", required = true) Double cantidad){
        Producto product = productoServicio.updateStock(id, cantidad);
        if (product == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
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
