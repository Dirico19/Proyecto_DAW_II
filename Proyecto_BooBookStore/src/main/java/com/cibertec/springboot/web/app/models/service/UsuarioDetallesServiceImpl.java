package com.cibertec.springboot.web.app.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cibertec.springboot.web.app.models.DAO.UsuarioRepository;
import com.cibertec.springboot.web.app.models.entity.Usuario;
import com.cibertec.springboot.web.app.models.entity.UsuarioDetalles;

public class UsuarioDetallesServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByNombre(username);
		
		if (usuario == null)
			throw new UsernameNotFoundException("El usuario no existe");
		
		return new UsuarioDetalles(usuario);
	}

}
