package com.uacm.atamarindao.producto.servicios;

import java.util.List;
import java.util.Optional;

import com.uacm.atamarindao.producto.entidad.Producto;

public interface ProductoServicios {
   
	public List<Producto> getAllProducts();
    public Optional<Producto> getProduct(Long id);
    public Producto createProducto(Producto producto);
    public Producto updateProduct(Producto producto);
    public Producto deleteProduct(Long id);
    public Producto updateStock(Long id, Double cantidad);
}
