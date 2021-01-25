package com.uacm.atamarindao.producto;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.uacm.atamarindao.producto.entidad.Producto;
import com.uacm.atamarindao.producto.repositorio.ProductoRepositorio;
import com.uacm.atamarindao.producto.servicios.ProductoServicios;
import com.uacm.atamarindao.producto.servicios.ProductoServiciosImp;

@SpringBootTest
public class ProductoServiciosMockTest {

    @Mock
    private ProductoRepositorio productRepository;

    private ProductoServicios productService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        productService =  new ProductoServiciosImp( productRepository);
        Producto computer =  Producto.builder()
                .id(1L)
                .nombre("computer")
                .imagen("/tama.jpg")
                .descripcion("pesososso")
                .precio(Double.parseDouble("12.5"))
                .stock(Double.parseDouble("5"))
                .build();

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(computer));
        Mockito.when(productRepository.save(computer)).thenReturn(computer);

    }
    
    @Test
   public void whenValidGetID_ThenReturnProduct(){
        Producto found = productService.getProduct(1L).get();
       Assertions.assertThat(found.getNombre()).isEqualTo("computer");

   }
}
