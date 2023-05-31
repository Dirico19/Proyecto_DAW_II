package com.cibertec.springboot.web.app.controllers;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.springboot.web.app.models.entity.Libro;
import com.cibertec.springboot.web.app.models.entity.Prestamo;
import com.cibertec.springboot.web.app.models.entity.Socio;
import com.cibertec.springboot.web.app.models.service.ILibroService;
import com.cibertec.springboot.web.app.models.service.IPrestamoService;
import com.cibertec.springboot.web.app.util.paginator.PageRender;

@Controller
@SessionAttributes(names = {"prestamo","libro","usuario"})
@RequestMapping(value = "/prestamo")
public class PrestamoController {

	@Autowired
	private HttpSession session;
	@Autowired
	private ILibroService libroService;
	@Autowired
	private IPrestamoService prestamoService;
	
	// Confirmación de Prestamos - Controllers
	
	@RequestMapping(value = "/solicitar/{idlibro}")
	public String solicitarPrestamo(@PathVariable(value = "idlibro") Long idlibro, Model model) {
		Libro libro = libroService.findOne(idlibro);
		model.addAttribute("titulo", "Préstamo de Libro");
		model.addAttribute("libro", libro);
		
		return "prestamo/solicitar";
	}
	
	@RequestMapping(value = "/confirmar", method = RequestMethod.POST)
	public String confirmarPrestamo(Model model, RedirectAttributes attributes, SessionStatus status) {
		Libro libro = (Libro) model.getAttribute("libro");
		Socio socio = (Socio) session.getAttribute("login");
		
		if (prestamoService.devolucionesPendientes(socio.getId()) >= 5) {
			attributes.addFlashAttribute("error", "No se pudo realizar el préstamo, tiene demasiadas devoluciones pendientes.");
			return "redirect:/libro/listado";
		}
		if (prestamoService.morasPendientes(socio.getId()) > 0) {
			attributes.addFlashAttribute("error", "No se pudo realizar el préstamo, primero debe cancelar todas sus moras.");
			return "redirect:/libro/listado";
		}
		
		Prestamo prestamo = new Prestamo();
		prestamo.setSocio(socio);
		prestamo.setLibro(libro);
		prestamo.setFecPrestamo(new Date());
		prestamo.setFecLimite(new Date(new Date().getTime() + 1728000000L));
		prestamo.setEstado("Pendiente");
		prestamo.setMora("No");
		try {
			prestamoService.save(prestamo);
			status.setComplete();
			attributes.addFlashAttribute("success", "Préstamo realizado correctamente.");
		} catch (Exception e) {
			attributes.addFlashAttribute("error", "No se pudo realizar el préstamo, asegúrese que haya stock suficiente.");
		}
		
		return "redirect:/libro/listado";
	}
	
	@RequestMapping(value = "/devolver/{idPrestamo}")
	public String solicitarDevolucion(@PathVariable(value = "idPrestamo") int idPrestamo, Model model) {
		Prestamo prestamo = prestamoService.findOne(idPrestamo);
		model.addAttribute("titulo", "Devolución de libro");
		model.addAttribute("prestamo", prestamo);
		
		return "prestamo/devolver";
	}
	
	@RequestMapping(value = "/devolver", method = RequestMethod.POST)
	public String confirmarDevolucion(Model model, RedirectAttributes attributes, SessionStatus status) {
		Prestamo prestamo = (Prestamo) model.getAttribute("prestamo");
		
		prestamo.setFecDevolucion(new Date());
		prestamo.setEstado("Devuelto");
		
		if (prestamo.getFecDevolucion().getTime() >= prestamo.getFecLimite().getTime())
			prestamo.setMora("Si");
		
		prestamoService.save(prestamo);
		status.setComplete();
		attributes.addFlashAttribute("success", "Devolución realizada correctamente.");

		return "redirect:/prestamo/listado";
	}
	
	@RequestMapping(value="/listado", method = RequestMethod.GET)
	public String listadoPrestamo(@RequestParam(name="page", defaultValue = "0") 
	int page, Model model, String nueva, Authentication authentication) {
		String rol = ""; Socio socio = null;
		for (GrantedAuthority t : authentication.getAuthorities())
			rol = t.getAuthority();
		if (rol.equals("Socio")) {
			socio = (Socio) session.getAttribute("login");
			model.addAttribute("prestamos", prestamoService.findByIdSocio(socio.getId()));
			model.addAttribute("titulo", "Mis Préstamos");
		}
		else {
			Pageable pageRequest = PageRequest.of(page, 5);
			Page<Prestamo> prestamos = prestamoService.findAll(pageRequest);
			PageRender<Prestamo> pageRender = new PageRender<>("/prestamo/listado", prestamos);
			model.addAttribute("prestamos", prestamos);
			model.addAttribute("titulo", "Listado de Préstamos");
			model.addAttribute("page", pageRender);
		}
		
		return "prestamo/listado";
	}
	
	@RequestMapping(value="/busqueda", method = RequestMethod.GET)
	public String buscarPorFecha(@RequestParam(name="fecha", defaultValue = "false") String fecha, Model model, Authentication authentication) {
		if (fecha != "") {
			String rol = ""; Socio socio = null;
			for (GrantedAuthority t : authentication.getAuthorities())
				rol = t.getAuthority();
			if (rol.equals("Socio")) {
				socio = (Socio) session.getAttribute("login");
				model.addAttribute("prestamos", prestamoService.findByDateAndSocio(fecha, socio.getId()));
				model.addAttribute("titulo", "Mis Préstamos");
			}
			else {
				model.addAttribute("titulo", "Listado de Préstamos");
				model.addAttribute("prestamos", prestamoService.findByDate(fecha));	
			}
			
		}
		return "prestamo/busqueda";
	}
	
}
