package com.uacm.atamarindao.usuario.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uacm.atamarindao.usuario.entidad.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>{

}
