package com.cibertec.springboot.web.app.controllers;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.springboot.web.app.models.entity.Rol;
import com.cibertec.springboot.web.app.models.entity.Socio;
import com.cibertec.springboot.web.app.models.entity.Usuario;
import com.cibertec.springboot.web.app.models.service.ISocioService;
import com.cibertec.springboot.web.app.models.service.IUsuarioService;

@Controller
@RequestMapping(value = "/socio")
@SessionAttributes("socio")
public class SocioController {

	@Autowired
	private HttpSession session;
	@Autowired
	private ISocioService socioService;
	@Autowired
	private IUsuarioService usuarioService;
	
	@RequestMapping(value = "/registroDatos")
	public String registrar(Model model) {
		model.addAttribute("titulo","Formulario de Socio");
		model.addAttribute("socio", new Socio());
		return "socio/registroDatos";
	}
	
	@RequestMapping(value = "/registroDatos", method = RequestMethod.POST)
	public String registrarDatos(Model model, @Valid @ModelAttribute Socio socio, BindingResult result) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Socio");
			return "socio/registroDatos";
		}
		
		
		model.addAttribute("socio", socio);
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("titulo", "Registrar Usuario");
		return "socio/registroUsuario";
	}
	
	@RequestMapping(value = "/registroUsuario", method = RequestMethod.POST)
	public String registrarUsuario(Model model, @Valid @ModelAttribute Usuario usuario, BindingResult result,
			SessionStatus status, RedirectAttributes attributes) {
		
		if (model.getAttribute("socio") == null)
			return "redirect:/socio/registroDatos";
		
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Registrar Usuario");
			return "socio/registroUsuario";
		}
		
		Socio socio = (Socio) model.getAttribute("socio");
		usuario.setContraseña(new BCryptPasswordEncoder().encode(usuario.getContraseña()));
		usuario.setRol(new Rol());
		usuario.getRol().setId(3);
		usuario.setEstado(1);
		usuarioService.save(usuario);
		socio.setFecRegistro(new Date());
		socio.setDeuda(0);
		socio.setUsuario(usuarioService.findByNombre(usuario.getNombre()));
		socioService.save(socio);
		status.setComplete();
		attributes.addFlashAttribute("success", "Registro exitoso! ya puede iniciar sesión");
		return "redirect:/login";
	}
	
	@RequestMapping(value = "/actualizarDatos")
	public String actualizarDatos(Model model) {
		Socio socio = (Socio) session.getAttribute("login");
		model.addAttribute("titulo","Actualizar Datos");
		model.addAttribute("socio", socio);
		return "socio/actualizarDatos";
	}
	
	@RequestMapping(value = "/actualizarDatos", method = RequestMethod.POST)
	public String actualizarDatos(Model model, @Valid @ModelAttribute Socio socio, BindingResult result,
			SessionStatus status, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Actualizar Datos");
			return "socio/actualizarDatos";
		}
		
		socioService.save(socio);
		session.setAttribute("login", socio);
		status.setComplete();
		attributes.addFlashAttribute("success", "Datos personales actualizados correctamente");
		return "redirect:/index";
	}
	
}
