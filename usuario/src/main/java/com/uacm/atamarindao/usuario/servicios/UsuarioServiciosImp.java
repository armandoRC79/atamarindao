package com.uacm.atamarindao.usuario.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uacm.atamarindao.usuario.entidad.Usuario;
import com.uacm.atamarindao.usuario.repositorio.UsuarioRepositorio;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioServiciosImp implements UsuarioServicios {
	
	@Autowired
	UsuarioRepositorio usuarioRepositorio;
	
	public List<Usuario> findUsuarioAll() {
		return usuarioRepositorio.findAll();
	}

	@Override
	public Optional<Usuario> getUsuario(Long id) {
        return  usuarioRepositorio.findById(id);
	}

	@Override
	public Usuario createUsuario(Usuario usuario) {
		usuario.setStatus("CREADO");
		return usuarioRepositorio.save(usuario);
	}

	@Override
	public Usuario updateUsuario(Usuario usuario) {
        Usuario usuarioDB = getUsuario(usuario.getId()).get();
        if (usuarioDB == null){
            return  null;
        }
        usuarioDB.setNombre(usuario.getNombre());
        usuarioDB.setPassword(usuario.getPassword());
        usuarioDB.setRol(usuario.getRol());

        return  usuarioRepositorio.save(usuarioDB);
	}

	@Override
	public Usuario deleteUsuario(Usuario usuario) {
        Usuario usuarioDB = getUsuario(usuario.getId()).get();
        if (usuarioDB ==null){
            return  null;
        }
        usuario.setStatus("DELETED");
        return usuarioRepositorio.save(usuario);
	}

}
