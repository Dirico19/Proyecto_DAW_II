package com.cibertec.springboot.web.app.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.springboot.web.app.models.entity.Empleado;
import com.cibertec.springboot.web.app.models.entity.Socio;
import com.cibertec.springboot.web.app.models.entity.Usuario;
import com.cibertec.springboot.web.app.models.service.IUsuarioService;
import com.cibertec.springboot.web.app.util.paginator.PageRender;


@Controller
@RequestMapping(value = "/usuario")
public class UsuarioController {
	
	@Autowired
	private HttpSession session;
	@Autowired
	private IUsuarioService usuarioService;
	
	@RequestMapping(value="/admin/usuarios", method = RequestMethod.GET)
	public String listadoUsuarios(@RequestParam(name="page", defaultValue = "0") 
	int page, Model modeloListado) {
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Usuario> usuarios = usuarioService.findAll(pageRequest);
		PageRender<Usuario> pageRender = new PageRender<>("/admin/usuarios", usuarios);
		
		modeloListado.addAttribute("titulo", "Listado de Usuarios");
		modeloListado.addAttribute("usuarios", usuarios);
		modeloListado.addAttribute("page", pageRender);
		return "listado_usuario";
	}
	
	@RequestMapping(value = "/cambiarContraseña")
	public String cambiarContraseña(Model model) {
		model.addAttribute("titulo", "Cambiar Contraseña");
		return "usuario/cambiarContraseña";
	}
	
	@RequestMapping(value = "/cambiarContraseña", method = RequestMethod.POST)
	public String cambiarContraseña(Model model, @RequestParam(name = "actual", defaultValue = "") String actual,
			@RequestParam(name = "nueva", defaultValue = "") String nueva, Authentication authentication) {
		String rol = ""; Socio socio = null; Empleado empleado = null; Usuario usuario;
		for (GrantedAuthority t : authentication.getAuthorities())
			rol = t.getAuthority();
		if (rol.equals("Socio")) {
			socio = (Socio) session.getAttribute("login");
			usuario = socio.getUsuario();
		}
		else {
			empleado = (Empleado) session.getAttribute("login");
			usuario = empleado.getUsuario();
		}
		
		if (nueva.isBlank()) {
			model.addAttribute("error", "Debe escribir una contraseña nueva");
		} else {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			if (encoder.matches(actual, usuario.getContraseña())) {
				usuario.setContraseña(encoder.encode(nueva));
				usuarioService.save(usuario);
				usuario = usuarioService.findByNombre(usuario.getNombre());
				if (socio != null) {socio.setUsuario(usuario); session.setAttribute("login", socio);}
				if (empleado != null) {empleado.setUsuario(usuario); session.setAttribute("login", empleado);}
				model.addAttribute("success", "La contraseña se ha cambiado correctamente");
			} else {
				model.addAttribute("error", "La contraseña actual es incorrecta");
			}
		}
		
		model.addAttribute("titulo", "Cambiar Contraseña");
		return "usuario/cambiarContraseña";
	}

}