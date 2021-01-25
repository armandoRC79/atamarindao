package com.uacm.atamarindao.pedido.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uacm.atamarindao.pedido.entidad.Pedido;

public interface PedidoRepositorio extends JpaRepository<Pedido, Long>{
	
    public List<Pedido> findByUsuarioId(Long usuarioId );
    public Pedido findByNumberoPedido(String numberoPedido);

}
