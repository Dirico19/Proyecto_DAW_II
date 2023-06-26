package com.cibertec.springboot.web.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.springboot.web.app.models.entity.Libro;
import com.cibertec.springboot.web.app.models.service.ICategoriaService;
import com.cibertec.springboot.web.app.models.service.ILibroService;
import com.cibertec.springboot.web.app.models.service.IUploadFileService;
import com.cibertec.springboot.web.app.util.constants.Constant;
import com.cibertec.springboot.web.app.util.paginator.PageRender;
import com.google.gson.Gson;

@Controller
@SessionAttributes(names = {"libro", "empleado"})
@RequestMapping(value="/libro")
public class LibroController {

	@Autowired
	private ILibroService libroService;	
	
	@Autowired
	private ICategoriaService categoriaService;
	
	@Autowired
	private IUploadFileService uploadFileService;	
	
	@GetMapping(value="/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {		
		Resource recurso = null;		
		try {
			recurso = uploadFileService.load(filename);
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}
	
	@RequestMapping(value="/listado", method = RequestMethod.GET)
	public String listado(@RequestParam(name="page", defaultValue = "0") 
	int page, Model modeloListado, Authentication authentication) {
		Page<Libro> libros = libroService.findAll(page, Constant.PAGE_SIZE);
		PageRender<Libro> pageRender = new PageRender<>("/libro/listado", libros);
		
		modeloListado.addAttribute("titulo", "Listado de Libros");
		modeloListado.addAttribute("libros", libros);
		modeloListado.addAttribute("page", pageRender);
		
		return "libro/listado";
	}
	
	@RequestMapping(value = "/registro")
	public String registrar(Model model) {
		model.addAttribute("titulo","Formulario de Libro");
		model.addAttribute("libro", new Libro());
		model.addAttribute("categorias",categoriaService.findAll());
		return "libro/registro";
	}
	
	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	public String guardar(Model model, @Valid @ModelAttribute Libro libro, BindingResult result, @RequestParam("file") MultipartFile foto,
			SessionStatus status, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Libro");
			model.addAttribute("categorias", categoriaService.findAll());
			return "libro/registro";
		}
		
		if (!foto.isEmpty()) {
			
			if (libro.getId() != null && libro.getId() > 0 &&
					libro.getFoto() != null && libro.getFoto().length()> 0) {
				uploadFileService.delete(libro.getFoto());
			}
			String uniqueFilename = null;
			try {
				uniqueFilename = uploadFileService.copy(foto);
			} catch (IOException e) {
				e.printStackTrace();
			}		
			
			attributes.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");
			libro.setFoto(uniqueFilename);
		}
		ResponseEntity<String> response = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		if (libro.getId() != null) {
			response = libroService.update(libro);
		} else {
			response = libroService.save(libro);			
		}
		if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
		    attributes.addFlashAttribute("success", "Libro guardado correctamente.");
		} else {
		    attributes.addFlashAttribute("error", "Ocurrió un error al guardar.");
		}
		status.setComplete();
		return "redirect:/libro/listado";
	}
	
	@RequestMapping(value = "/editar/{id}")
	public String editar(Model model, @PathVariable Long id, RedirectAttributes attributes) {
		Libro libro = null;
		ResponseEntity<String> response = libroService.findOne(id);
		if (response.getStatusCode() == HttpStatus.OK) {
			Gson gson = new Gson();
			libro = gson.fromJson(response.getBody(), Libro.class);
			model.addAttribute("titulo","Formulario de Libro");
			model.addAttribute("libro", libro);
			model.addAttribute("categorias",categoriaService.findAll());
		} else {
			attributes.addFlashAttribute("error", "No se encontró un libro con el id indicado.");
			return "redirect:/libro/listado";
		}
		
		return "libro/registro";
	}
	
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes attributes) {
		Libro libro = null;
		ResponseEntity<String> response = libroService.findOne(id);
		if (response.getStatusCode() == HttpStatus.OK) {
			Gson gson = new Gson();
			libro = gson.fromJson(response.getBody(), Libro.class);
		} else {
			attributes.addFlashAttribute("error", "No se encontró un libro con el id indicado.");
			return "redirect:/libro/listado";
		}
		
		response = libroService.delete(id);
		if (response.getStatusCode() == HttpStatus.OK) {
			attributes.addFlashAttribute("success", "Libro eliminado correctamente.");
			if (libro != null && libro.getFoto() != null) {
				if (uploadFileService.delete(libro.getFoto())) {
					attributes.addFlashAttribute("info", "Foto " + libro.getFoto() + " eliminada con éxito.");
				}
			}
		} else {
		    attributes.addFlashAttribute("error", "Ocurrió un error al eliminar.");
		}
		return "redirect:/libro/listado";
	}
	
}