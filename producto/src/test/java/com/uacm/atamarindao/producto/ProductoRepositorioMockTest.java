package com.uacm.atamarindao.producto;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.uacm.atamarindao.producto.entidad.Producto;
import com.uacm.atamarindao.producto.repositorio.ProductoRepositorio;

@DataJpaTest
public class ProductoRepositorioMockTest {
/*
	@Autowired
	private ProductoRepositorio productoRepositorio;
	
	@Test
	public void agregaProducto_retornaListaDeProducto() {
        Producto product01 = Producto.builder()
                .id(4L)
                .nombre("computer")
                .imagen("/tama.jpg")
                .descripcion("pesososso")
                .precio(Double.parseDouble("12.5"))
                .stock(Double.parseDouble("5"))
                .build();
        productoRepositorio.save(product01);

        List<Producto> founds= productoRepositorio.findAll();

        Assertions.assertThat(founds.size()).isEqualTo(4);
	}*/
	
	@Autowired
	private ProductoRepositorio productoRespositorio;
	
	@Test
	public void agregaProducto_retornaListaDeProducto() {
		Producto producto = Producto.builder()
				.id(4L)
				.nombre("banderillas")
				.descripcion("banderilla de tamarindo con chile")
				.imagen("banderilla.jpg")
				.status("disponible")
				.precio(Double.parseDouble("6"))
				.stock(Double.parseDouble("200"))
				.build();
		
		productoRespositorio.save(producto);
		
		List<Producto> productos = productoRespositorio.findAll();
		
		Assertions.assertThat(productos.size()).isEqualTo(4);
	}

}
