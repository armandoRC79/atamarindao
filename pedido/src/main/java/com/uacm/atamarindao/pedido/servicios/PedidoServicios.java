package com.uacm.atamarindao.pedido.servicios;

import java.util.List;

import com.uacm.atamarindao.pedido.entidad.Pedido;

public interface PedidoServicios {
	
    public List<Pedido> findPedidoAll();
    public Pedido createPedido(Pedido pedido);
    public Pedido updatePedido(Pedido pedido);
    public Pedido deletePedido(Pedido pedido);
    public Pedido getPedido(Long id);

}
