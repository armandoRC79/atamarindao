package com.uacm.atamarindao.pedido.modelo;

import lombok.Data;

@Data
public class Usuario {
	
    private Long id;
    
    private String nombre;
	private String password;
	private String rol;

}
