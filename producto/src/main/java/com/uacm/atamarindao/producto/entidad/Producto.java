package com.uacm.atamarindao.producto.entidad;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "tbl_productos")
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message = "El nombre no debe ser vacio")
    @Size (max = 100)
    private String nombre;
    
    @Size (max = 50)
    private String imagen;
    
    @Size (max = 300)
    private String descripcion;
    
    @Positive(message = "El stock debe ser mayor o igual que cero")
    private Double stock;
    
    @Positive(message = "El precio debe ser mayor o igual que cero")
    private Double precio;
    
    @NotEmpty(message = "Sin estatus asignado")
    private String status;

}
