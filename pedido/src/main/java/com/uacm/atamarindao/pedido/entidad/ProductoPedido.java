package com.uacm.atamarindao.pedido.entidad;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Positive;

import com.uacm.atamarindao.pedido.modelo.Producto;

import lombok.Data;


@Entity
@Data
@Table(name = "tbl_pedido_productos")
public class ProductoPedido {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Positive(message = "El stock debe ser mayor que cero")
    private Double cantidad;
    
    private Double  precio;

    private Long productoId;

    @Transient
    private Double subTotal;

    @Transient
    private Producto producto;
    
    public ProductoPedido(){
        this.cantidad=(double) 0;
        this.precio=(double) 0;

    }

    public Double getSubTotal(){
        if (this.precio >0  && this.cantidad >0 ){
            return this.cantidad * this.precio;
        }else {
            return (double) 0;
        }
    }


}
