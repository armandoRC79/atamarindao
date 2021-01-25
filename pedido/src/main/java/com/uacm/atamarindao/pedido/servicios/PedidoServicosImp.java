package com.uacm.atamarindao.pedido.servicios;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uacm.atamarindao.pedido.entidad.Pedido;
import com.uacm.atamarindao.pedido.repositorio.PedidoRepositorio;
import com.uacm.atamarindao.pedido.repositorio.ProductoPedidoRepositorio;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PedidoServicosImp implements PedidoServicios {
	
    @Autowired
    PedidoRepositorio pedidoRepositorio;

    @Autowired
    ProductoPedidoRepositorio productoPedidoRespositorio;

	@Override
	public List<Pedido> findPedidoAll() {
		return pedidoRepositorio.findAll();
	}

	@Override
	public Pedido createPedido(Pedido pedido) {
        Pedido pedidoDB = pedidoRepositorio.findByNumberoPedido(pedido.getNumberoPedido());
        if (pedidoDB !=null){
            return  pedidoDB;
        }
        pedido.setStatus("CREATED");
 //       pedidoDB = invoiceRepository.save(invoice);
 //       pedidoDB.getProductos().forEach( productoPedido -> {
  //          productClient.updateStockProduct( productoPedido.getProductId(), productoPedido.getQuantity() * -1);
 //       });

        return pedidoRepositorio.save(pedido);
	}

	@Override
	public Pedido updatePedido(Pedido pedido) {
        Pedido pedidoDB = getPedido(pedido.getId());
        if (pedidoDB == null){
            return  null;
        }
        pedidoDB.setUsuarioId(pedido.getUsuarioId());
        pedidoDB.setNumberoPedido(pedido.getNumberoPedido());
        pedidoDB.setFecha(pedido.getFecha());
        pedidoDB.setProductos(pedido.getProductos());;
        pedidoDB.setStatus(pedido.getStatus());
        pedidoDB.setUsuario(pedido.getUsuario());
        return pedidoRepositorio.save(pedidoDB);
	}

	@Override
	public Pedido deletePedido(Pedido pedido) {
        Pedido pedidoDB = getPedido(pedido.getId());
        if (pedidoDB == null){
            return  null;
        }
        pedidoDB.setStatus("DELETED");
        return pedidoRepositorio.save(pedidoDB);
	}

	@Override
	public Pedido getPedido(Long id) {
		
        Pedido pedido= pedidoRepositorio.findById(id).orElse(null);
       /* if (null != pedido ){
            Usuario usuario = customerClient.getCustomer(pedido.getUsuarioId()).getBody();
            pedido.setCustomer(usuario);
            List<ProductoPedido> listaProductos=pedido.getProductos().stream().map(invoiceItem -> {
                Producto producto = productClient.getProduct(invoiceItem.getProductId()).getBody();
                invoiceItem.setProduct(producto);
                return listaProductos;
            }).collect(Collectors.toList());
            pedido.setProductos(listaProductos);
        }*/
        return pedido ;
	}

}
