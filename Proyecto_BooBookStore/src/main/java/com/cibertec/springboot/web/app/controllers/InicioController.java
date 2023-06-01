package com.cibertec.springboot.web.app.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cibertec.springboot.web.app.models.service.IEmpleadoService;
import com.cibertec.springboot.web.app.models.service.ISocioService;

@Controller
public class InicioController {
	
	@Autowired
	private HttpSession session;
	@Autowired
	private IEmpleadoService empleadoService;
	@Autowired
	private ISocioService socioService;
	
	@GetMapping(value = {"/","/login"})
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = "/login_success", method = RequestMethod.POST)
	public String loginSuccess() {
		return "redirect:/index";
	}

	@RequestMapping(value = "/login_error", method = RequestMethod.POST)
	public String loginError(Model model) {
		model.addAttribute("error", "El nombre de usuario y la contraseña no coinciden");
		return "/login";
	}
	
	@RequestMapping(value="/index")
	public String index(Model model, Authentication authentication) {
		model.addAttribute("titulo", "Bienvenido a BooBook Store");
		String rol = "";
		for (GrantedAuthority t : authentication.getAuthorities()) {
			rol = t.getAuthority();
		}
		if (rol.equals("Socio"))
			session.setAttribute("login", socioService.findbyUsuario(authentication.getName()));
		else
			session.setAttribute("login", empleadoService.findByUsuario(authentication.getName()));
		return "index";
	}
}
