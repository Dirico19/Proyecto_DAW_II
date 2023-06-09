package com.cibertec.springboot.web.app.controllers;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.cibertec.springboot.web.app.models.entity.Pago;
import com.cibertec.springboot.web.app.models.entity.Prestamo;
import com.cibertec.springboot.web.app.models.entity.Socio;
import com.cibertec.springboot.web.app.models.service.IPagoService;
import com.cibertec.springboot.web.app.models.service.IPrestamoService;
import com.cibertec.springboot.web.app.util.paginator.PageRender;

@Controller
@SessionAttributes(names = {"prestamo","libro","usuario"})
@RequestMapping(value = "/pago")
public class PagoController {

	@Autowired
	private HttpSession session;
	@Autowired
	private IPrestamoService prestamoService;
	@Autowired
	private IPagoService pagoService;
	
	@RequestMapping(value = "/realizar/{idPrestamo}")
	public String realizar(@PathVariable(value = "idPrestamo") int idPrestamo, Model model) {
		Prestamo prestamo = prestamoService.findOne(idPrestamo);
		model.addAttribute("titulo", "Pago de Mora por Tardanza");
		model.addAttribute("prestamo", prestamo);
		
		return "pago/realizar";
	}
	
	@RequestMapping(value = "/confirmar", method = RequestMethod.POST)
	public String confirmar(Model model, RedirectAttributes attributes, SessionStatus status) {
		Prestamo prestamo = (Prestamo) model.getAttribute("prestamo");
		
		Pago pago = new Pago();
		pago.setPrestamo(prestamo);
		pago.setFecPago(new Date());
		pago.setMonto(5);
		
		pagoService.save(pago);
		status.setComplete();
		attributes.addFlashAttribute("success", "Pago de mora realizado correctamente.");

		return "redirect:/prestamo/listado";
	}
	
	@RequestMapping(value="/listado", method = RequestMethod.GET)
	public String listadoPagos(@RequestParam(name="page", defaultValue = "0") int page,
			@RequestParam(name="fechaInicio", defaultValue = "1900-01-01", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
			@RequestParam(name="fechaFin", defaultValue = "3000-01-01", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin,
			Model model, Authentication authentication) {
		String rol = ""; Socio socio = null;
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Pago> pagos = null;
		
		for (GrantedAuthority t : authentication.getAuthorities())
			rol = t.getAuthority();
		
		if (rol.equals("Socio")) {
			socio = (Socio) session.getAttribute("login");
			pagos = pagoService.findByDateAndSocio(fechaInicio, fechaFin, socio.getId(), pageRequest);
			model.addAttribute("titulo", "Mis Pagos");
		}
		else {
			pagos = pagoService.findByDate(fechaInicio, fechaFin, pageRequest);
			model.addAttribute("titulo", "Listado de Pagos");
		}
		
		PageRender<Pago> pageRender = new PageRender<>("/pago/listado", pagos);
		model.addAttribute("pagos", pagos);
		model.addAttribute("page", pageRender);
		
		return "pago/listado";
		}
	
}
