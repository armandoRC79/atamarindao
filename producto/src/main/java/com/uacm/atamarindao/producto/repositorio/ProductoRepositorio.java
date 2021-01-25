package com.uacm.atamarindao.producto.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uacm.atamarindao.producto.entidad.Producto;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Long>{

}
