package com.uacm.atamarindao.pedido.entidad;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uacm.atamarindao.pedido.modelo.Usuario;

import lombok.Data;

@Data
@Entity
@Table(name = "tlb_pedidos")
public class Pedido {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "usuario_id")
    private Long usuarioId;
    
    private String numberoPedido;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Valid
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "pedido_id")
    private List<ProductoPedido> productos;
    
    private String status;
    
    @Transient
    private Usuario usuario;
    
    public Pedido(){
        productos = new ArrayList<>();
    }

    @PrePersist
    public void prePersist() {
        this.fecha = new Date();
    }

}
