package com.uacm.atamarindao.producto.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.uacm.atamarindao.producto.entidad.Producto;
import com.uacm.atamarindao.producto.repositorio.ProductoRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoServiciosImp implements ProductoServicios{
	
	private final ProductoRepositorio productoRepositorio;

	@Override
	public List<Producto> getAllProducts() {
		return productoRepositorio.findAll();
	}

	@Override
	public Optional<Producto> getProduct(Long id) {
		return productoRepositorio.findById(id);
	}

	@Override
	public Producto createProducto(Producto producto) {
		producto.setStatus("EN EXISTENCIA");
		return productoRepositorio.save(producto);
	}

	@Override
	public Producto updateProduct(Producto producto) {
		Producto productoBD = getProduct(producto.getId()).get();
		
		if(productoBD == null) {
			return null;
		}
		
		productoBD.setNombre(producto.getNombre());
		productoBD.setDescripcion(producto.getDescripcion());
		productoBD.setImagen(producto.getImagen());
		productoBD.setPrecio(producto.getPrecio());
			
		return productoRepositorio.save(productoBD);
	}

	@Override
	public Producto deleteProduct(Long id) {
        Producto productDB = getProduct(id).get();
        if (null == productDB){
            return null;
        }
        productDB.setStatus("DELETED");
        return productoRepositorio.save(productDB);
	}

	@Override
	public Producto updateStock(Long id, Double cantidad) {
        Producto productoDB = getProduct(id).get();
        if (null == productoDB){
            return null;
        }
        Double stock =  productoDB.getStock() + cantidad;
        productoDB.setStock(stock);
        return productoRepositorio.save(productoDB);
	}

}
