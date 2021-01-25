package com.uacm.atamarindao.pedido.modelo;

import lombok.Data;

@Data
public class Producto {
    private Long id;

    private String nombre;
    private String descripcion;
    private String imagen;
    private Double stock;
    private Double precio;
    private String status;
}
