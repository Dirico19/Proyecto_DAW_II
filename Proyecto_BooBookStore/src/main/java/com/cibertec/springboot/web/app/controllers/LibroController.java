package com.cibertec.springboot.web.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
import com.cibertec.springboot.web.app.util.paginator.PageRender;

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
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Libro> libros = libroService.findAll(pageRequest);
		PageRender<Libro> pageRender = new PageRender<>("/libro/listado", libros);
				
		modeloListado.addAttribute("titulo", "Listado de Libros");
		modeloListado.addAttribute("libros", libros);
		modeloListado.addAttribute("page", pageRender);
		
		System.out.println(modeloListado.getAttribute("empleado"));
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
		
		libroService.save(libro);
		status.setComplete();
		attributes.addFlashAttribute("success", "Libro guardado correctamente.");
		return "redirect:/libro/listado";
	}
	
	@RequestMapping(value = "/editar/{id}")
	public String editar(Model model, @PathVariable Long id) {
		
		model.addAttribute("titulo","Formulario de Libro");
		model.addAttribute("libro", libroService.findOne(id));
		model.addAttribute("categorias",categoriaService.findAll());
		return "libro/registro";
	}
	
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes attributes) {
		Libro libro = libroService.findOne(id);
		libroService.delete(id);
		attributes.addFlashAttribute("success", "Libro eliminado correctamente.");
		if (uploadFileService.delete(libro.getFoto())) {
			attributes.addAttribute("info", "Foto " + libro.getFoto() + " eliminada con éxito.");
	}
		return "redirect:/libro/listado";
	}
	
}