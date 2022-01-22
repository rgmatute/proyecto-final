package com.example.demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Catalogo;
import com.example.demo.services.CatalogoServices;

@RestController
@RequestMapping("open-api")
public class CatalogoController {
	
	@Autowired
	CatalogoServices catalogoServices;
	
	@GetMapping("/catalogos")
	public List<Catalogo> findAll() {
		return catalogoServices.findAll();
	}
	
	@PostMapping("/catalogos")
	public Catalogo save(@RequestBody Catalogo catalogo) {
		return catalogoServices.save(catalogo);
	}
	
	@GetMapping("/catalogos/{id}")
	public Catalogo findById(@PathVariable String id) {
		return catalogoServices.findById(id);
	}
	
	@DeleteMapping("/catalogos/{id}")
	public void delete(String id) {
		catalogoServices.delete(id);
	}

}
