package com.uacm.atamarindao.usuario.servicios;

import java.util.List;
import java.util.Optional;

import com.uacm.atamarindao.usuario.entidad.Usuario;

public interface UsuarioServicios {
	    public List<Usuario> findUsuarioAll();
	    public Optional<Usuario> getUsuario(Long id);
	    public Usuario createUsuario(Usuario usuario);
	    public Usuario updateUsuario(Usuario usuario);
	    public Usuario deleteUsuario(Usuario usuario);
}
