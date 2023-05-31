package com.cibertec.springboot.web.app.controllers;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.springboot.web.app.models.entity.Empleado;
import com.cibertec.springboot.web.app.models.entity.Rol;
import com.cibertec.springboot.web.app.models.entity.Usuario;
import com.cibertec.springboot.web.app.models.service.IEmpleadoService;
import com.cibertec.springboot.web.app.models.service.IUsuarioService;
import com.cibertec.springboot.web.app.util.paginator.PageRender;

@Controller
@RequestMapping(value = "/empleado")
@SessionAttributes("empleado")
public class EmpleadoController {
	
	@Autowired
	private IEmpleadoService empleadoService;
	
	@Autowired 
	private IUsuarioService usuarioService;
	
	@RequestMapping(value="/listado", method = RequestMethod.GET)
	public String listar(@RequestParam(name="page", defaultValue = "0") 
	int page, Model modeloListado) {
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Empleado> empleados = empleadoService.findAll(pageRequest);
		PageRender<Empleado> pageRender = new PageRender<>("/empleado/listado", empleados);
		
		modeloListado.addAttribute("titulo", "Listado de Empleados");
		modeloListado.addAttribute("empleados", empleados);
		modeloListado.addAttribute("page", pageRender);
		System.out.println(modeloListado.getAttribute("empleado"));
		return "empleado/listado";
	}
	
	@RequestMapping(value = "/registro")
	public String registrar(Model model) {
		model.addAttribute("titulo","Formulario de Empleado");
		model.addAttribute("empleado", new Empleado());
		return "empleado/registro";
	}
	
	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	public String guardar(Model model, @Valid @ModelAttribute Empleado empleado,
			BindingResult result, SessionStatus status, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Empleado");
			return "empleado/registro";
		}
		
		Usuario usuario; int idRol = empleado.getUsuario().getId();
		
		if (empleado.getId() == 0) {
			String nombre = empleado.getCorreo().split("@")[0];
			String contraseña = UUID.randomUUID().toString().replace("-", "");
			usuario = new Usuario();
			usuario.setNombre(nombre);
			usuario.setContraseña(new BCryptPasswordEncoder().encode(contraseña));
			usuario.setRol(new Rol());
			usuario.getRol().setId(idRol);
			usuario.setEstado(1);
			usuarioService.save(usuario);
			usuario = usuarioService.findByNombre(usuario.getNombre());
			empleado.setUsuario(usuario);
			attributes.addFlashAttribute("info", "Contraseña: " + contraseña);
			System.out.println("contraseña por defecto: " + contraseña);
		} else {
			Empleado emp = empleadoService.findOne(empleado.getId());
			usuario = emp.getUsuario();
			usuario.setRol(new Rol());
			usuario.getRol().setId(idRol);
			usuarioService.save(usuario);
			usuario = usuarioService.findByNombre(usuario.getNombre());
			empleado.setUsuario(usuario);
		}
		
		empleadoService.save(empleado);
		status.setComplete();
		
		attributes.addFlashAttribute("success", "Empleado guardado correctamente.");
		return "redirect:/empleado/listado";
	}
	
	@RequestMapping(value = "/editar/{id}")
	public String editar(@PathVariable int id, Model model) {
		Empleado empleado = empleadoService.findOne(id);
		empleado.getUsuario().setId(empleado.getUsuario().getRol().getId());
		model.addAttribute("titulo", "Formulario de Empleado");
		model.addAttribute("empleado", empleado);
		return "empleado/registro";
	}
	
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable int id, RedirectAttributes attributes) {
		empleadoService.delete(id);
		attributes.addFlashAttribute("success", "Empleado eliminado correctamente.");
		return "redirect:/empleado/listado";
	}
	
}
